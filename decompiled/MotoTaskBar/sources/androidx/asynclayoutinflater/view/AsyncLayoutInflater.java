package androidx.asynclayoutinflater.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.core.util.Pools$SynchronizedPool;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class AsyncLayoutInflater {
    Handler mHandler;
    private Handler.Callback mHandlerCallback = new AnonymousClass1();
    InflateThread mInflateThread;
    LayoutInflater mInflater;

    /* JADX INFO: renamed from: androidx.asynclayoutinflater.view.AsyncLayoutInflater$1, reason: invalid class name */
    class AnonymousClass1 implements Handler.Callback {
        AnonymousClass1() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            final InflateRequest inflateRequest = (InflateRequest) message.obj;
            if (inflateRequest.view == null) {
                inflateRequest.view = inflateRequest.mInflater.inflate(inflateRequest.resid, inflateRequest.parent, false);
            }
            Executor executor = inflateRequest.mExecutor;
            if (executor != null) {
                executor.execute(new Runnable() { // from class: androidx.asynclayoutinflater.view.AsyncLayoutInflater$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AsyncLayoutInflater.triggerCallbacks(inflateRequest, AsyncLayoutInflater.this.mInflateThread);
                    }
                });
                return true;
            }
            AsyncLayoutInflater.triggerCallbacks(inflateRequest, AsyncLayoutInflater.this.mInflateThread);
            return true;
        }
    }

    class BasicInflater extends LayoutInflater {
        private static final String[] sClassPrefixList = {"android.widget.", "android.webkit.", "android.app."};

        BasicInflater(Context context) {
            super(context);
        }

        @Override // android.view.LayoutInflater
        public LayoutInflater cloneInContext(Context context) {
            return new BasicInflater(context);
        }

        @Override // android.view.LayoutInflater
        protected View onCreateView(String str, AttributeSet attributeSet) {
            View viewCreateView;
            for (String str2 : sClassPrefixList) {
                try {
                    viewCreateView = createView(str, str2, attributeSet);
                } catch (ClassNotFoundException unused) {
                }
                if (viewCreateView != null) {
                    return viewCreateView;
                }
            }
            return super.onCreateView(str, attributeSet);
        }
    }

    class InflateRequest {
        OnInflateFinishedListener callback;
        Executor mExecutor;
        Handler mHandler;
        LayoutInflater mInflater;
        ViewGroup parent;
        int resid;
        View view;

        InflateRequest() {
        }
    }

    class InflateThread extends Thread {
        private static final InflateThread sInstance;
        private ArrayBlockingQueue mQueue = new ArrayBlockingQueue(10);
        private Pools$SynchronizedPool mRequestPool = new Pools$SynchronizedPool(10);

        public static /* synthetic */ void $r8$lambda$EmEnZl0ClLVKgr9SQxO5oL37_RE(InflateThread inflateThread, InflateRequest inflateRequest) {
            inflateThread.getClass();
            AsyncLayoutInflater.triggerCallbacks(inflateRequest, inflateThread);
        }

        static {
            InflateThread inflateThread = new InflateThread();
            sInstance = inflateThread;
            inflateThread.setName("AsyncLayoutInflator");
            inflateThread.start();
        }

        private InflateThread() {
        }

        public static InflateThread getInstance() {
            return sInstance;
        }

        public void enqueue(InflateRequest inflateRequest) {
            try {
                this.mQueue.put(inflateRequest);
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to enqueue async inflate request", e);
            }
        }

        public InflateRequest obtainRequest() {
            InflateRequest inflateRequest = (InflateRequest) this.mRequestPool.acquire();
            return inflateRequest == null ? new InflateRequest() : inflateRequest;
        }

        public void releaseRequest(InflateRequest inflateRequest) {
            inflateRequest.callback = null;
            inflateRequest.mInflater = null;
            inflateRequest.mHandler = null;
            inflateRequest.parent = null;
            inflateRequest.resid = 0;
            inflateRequest.view = null;
            inflateRequest.mExecutor = null;
            this.mRequestPool.release(inflateRequest);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                runInner();
            }
        }

        public void runInner() {
            Executor executor;
            try {
                final InflateRequest inflateRequest = (InflateRequest) this.mQueue.take();
                try {
                    inflateRequest.view = inflateRequest.mInflater.inflate(inflateRequest.resid, inflateRequest.parent, false);
                } catch (RuntimeException e) {
                    Log.w("AsyncLayoutInflater", "Failed to inflate resource in the background! Retrying on the UI thread", e);
                }
                if (inflateRequest.view == null || (executor = inflateRequest.mExecutor) == null) {
                    Message.obtain(inflateRequest.mHandler, 0, inflateRequest).sendToTarget();
                } else {
                    executor.execute(new Runnable() { // from class: androidx.asynclayoutinflater.view.AsyncLayoutInflater$InflateThread$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AsyncLayoutInflater.InflateThread.$r8$lambda$EmEnZl0ClLVKgr9SQxO5oL37_RE(this.f$0, inflateRequest);
                        }
                    });
                }
            } catch (InterruptedException e2) {
                Log.w("AsyncLayoutInflater", e2);
            }
        }
    }

    public interface OnInflateFinishedListener {
        void onInflateFinished(View view, int i, ViewGroup viewGroup);
    }

    public AsyncLayoutInflater(Context context, AsyncLayoutFactory asyncLayoutFactory) {
        BasicInflater basicInflater = new BasicInflater(context);
        this.mInflater = basicInflater;
        basicInflater.setFactory2(asyncLayoutFactory);
        this.mHandler = new Handler(Looper.myLooper(), this.mHandlerCallback);
        this.mInflateThread = InflateThread.getInstance();
    }

    private void inflateInternal(int i, ViewGroup viewGroup, OnInflateFinishedListener onInflateFinishedListener, LayoutInflater layoutInflater, Executor executor) {
        if (onInflateFinishedListener == null) {
            throw new NullPointerException("callback argument may not be null!");
        }
        InflateRequest inflateRequestObtainRequest = this.mInflateThread.obtainRequest();
        inflateRequestObtainRequest.mInflater = layoutInflater;
        inflateRequestObtainRequest.mHandler = this.mHandler;
        inflateRequestObtainRequest.resid = i;
        inflateRequestObtainRequest.parent = viewGroup;
        inflateRequestObtainRequest.callback = onInflateFinishedListener;
        inflateRequestObtainRequest.mExecutor = executor;
        this.mInflateThread.enqueue(inflateRequestObtainRequest);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void triggerCallbacks(InflateRequest inflateRequest, InflateThread inflateThread) {
        inflateRequest.callback.onInflateFinished(inflateRequest.view, inflateRequest.resid, inflateRequest.parent);
        inflateThread.releaseRequest(inflateRequest);
    }

    public void inflate(int i, ViewGroup viewGroup, Executor executor, OnInflateFinishedListener onInflateFinishedListener) {
        inflateInternal(i, viewGroup, onInflateFinishedListener, this.mInflater, executor);
    }
}
