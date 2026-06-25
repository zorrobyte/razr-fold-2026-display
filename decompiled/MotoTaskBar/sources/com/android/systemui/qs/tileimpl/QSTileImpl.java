package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.internal.logging.InstanceId;
import com.android.settingslib.RestrictedLockUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSTile;
import com.android.systemui.qs.SideLabelTileLayout;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public abstract class QSTileImpl implements QSTile, LifecycleOwner, Dumpable {
    protected static final Object ARG_SHOW_TRANSIENT_ENABLING = new Object();
    protected final ActivityStarter mActivityStarter;
    protected final Context mContext;
    protected RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    protected final H mHandler;
    protected final QSHost mHost;
    private int mIsFullQs;
    private volatile int mReadyState;
    protected QSTile.State mState;
    private String mTileSpec;
    private QSTile.State mTmpState;
    protected final Handler mUiHandler;
    protected final String TAG = "Tile." + getClass().getSimpleName();
    protected final boolean DEBUG = Log.isLoggable("Tile", 3);
    private final ArraySet mListeners = new ArraySet();
    private int mClickEventId = 0;
    private final ArraySet mCallbacks = new ArraySet();
    private final Object mStaleListener = new Object();
    private final InstanceId mInstanceId = InstanceId.fakeInstanceId(0);
    private final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

    public final class H extends Handler {
        protected static final int STALE = 11;

        protected H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                int i = message.what;
                boolean z = true;
                if (i == 1) {
                    QSTileImpl.this.handleAddCallback((QSTile.Callback) message.obj);
                    return;
                }
                if (i == 8) {
                    QSTileImpl.this.handleRemoveCallbacks();
                    return;
                }
                if (i == 9) {
                    QSTileImpl.this.handleRemoveCallback((QSTile.Callback) message.obj);
                    return;
                }
                if (i == 2) {
                    QSTileImpl.this.handleClick((View) message.obj);
                    return;
                }
                if (i == 3) {
                    QSTileImpl.this.handleSecondaryClick((View) message.obj);
                    return;
                }
                if (i == 4) {
                    QSTileImpl.this.handleLongClick((View) message.obj);
                    return;
                }
                if (i == 5) {
                    QSTileImpl.this.handleRefreshState(message.obj);
                    return;
                }
                if (i == 6) {
                    QSTileImpl.this.handleUserSwitch(message.arg1);
                    return;
                }
                if (i == 7) {
                    QSTileImpl.this.handleDestroy();
                    return;
                }
                if (i == 10) {
                    QSTileImpl qSTileImpl = QSTileImpl.this;
                    Object obj = message.obj;
                    if (message.arg1 == 0) {
                        z = false;
                    }
                    qSTileImpl.handleSetListeningInternal(obj, z);
                    return;
                }
                if (i == 11) {
                    QSTileImpl.this.handleStale();
                } else {
                    if (i == 12) {
                        QSTileImpl.this.handleInitialize();
                        return;
                    }
                    throw new IllegalArgumentException("Unknown msg: " + message.what);
                }
            } catch (Throwable th) {
                Log.w(QSTileImpl.this.TAG, "Error in " + ((String) null), th);
            }
        }
    }

    public class ResourceIcon extends QSTile.Icon {
        private static final SparseArray ICONS = new SparseArray();
        protected final int mResId;

        private ResourceIcon(int i) {
            this.mResId = i;
        }

        public static synchronized QSTile.Icon get(int i) {
            QSTile.Icon resourceIcon;
            SparseArray sparseArray = ICONS;
            resourceIcon = (QSTile.Icon) sparseArray.get(i);
            if (resourceIcon == null) {
                resourceIcon = new ResourceIcon(i);
                sparseArray.put(i, resourceIcon);
            }
            return resourceIcon;
        }

        public boolean equals(Object obj) {
            return (obj instanceof ResourceIcon) && ((ResourceIcon) obj).mResId == this.mResId;
        }

        @Override // com.android.systemui.qs.QSTile.Icon
        public Drawable getDrawable(Context context) {
            return context.getDrawable(this.mResId);
        }

        @Override // com.android.systemui.qs.QSTile.Icon
        public Drawable getInvisibleDrawable(Context context) {
            return context.getDrawable(this.mResId);
        }

        @Override // com.android.systemui.qs.QSTile.Icon
        public String toString() {
            return String.format("ResourceIcon[resId=0x%08x]", Integer.valueOf(this.mResId));
        }
    }

    protected QSTileImpl(QSHost qSHost, Looper looper, Handler handler, ActivityStarter activityStarter) {
        this.mHost = qSHost;
        this.mContext = qSHost.getContext();
        this.mUiHandler = handler;
        this.mHandler = new H(looper);
        this.mActivityStarter = activityStarter;
        resetStates();
        handler.post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAddCallback(QSTile.Callback callback) {
        this.mCallbacks.add(callback);
        callback.onStateChanged(this.mState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemoveCallback(QSTile.Callback callback) {
        this.mCallbacks.remove(callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemoveCallbacks() {
        this.mCallbacks.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetListeningInternal(Object obj, boolean z) {
        if (z) {
            if (this.mListeners.add(obj) && this.mListeners.size() == 1) {
                if (this.DEBUG) {
                    Log.d(this.TAG, "handleSetListening true");
                }
                handleSetListening(z);
                this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$handleSetListeningInternal$1();
                    }
                });
            }
        } else if (this.mListeners.remove(obj) && this.mListeners.size() == 0) {
            if (this.DEBUG) {
                Log.d(this.TAG, "handleSetListening false");
            }
            handleSetListening(z);
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$handleSetListeningInternal$2();
                }
            });
        }
        updateIsFullQs();
    }

    private void handleStateChanged() {
        if (this.mCallbacks.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            ((QSTile.Callback) this.mCallbacks.valueAt(i)).onStateChanged(this.mState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDestroy$3() {
        this.mLifecycle.setCurrentState(Lifecycle.State.DESTROYED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSetListeningInternal$1() {
        if (this.mLifecycle.getCurrentState().equals(Lifecycle.State.DESTROYED)) {
            return;
        }
        this.mLifecycle.setCurrentState(Lifecycle.State.RESUMED);
        if (this.mReadyState == 0) {
            this.mReadyState = 1;
        }
        refreshState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSetListeningInternal$2() {
        if (this.mLifecycle.getCurrentState().equals(Lifecycle.State.DESTROYED)) {
            return;
        }
        this.mLifecycle.setCurrentState(Lifecycle.State.STARTED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
    }

    private void updateIsFullQs() {
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            if (SideLabelTileLayout.class.equals(it.next().getClass())) {
                this.mIsFullQs = 1;
                return;
            }
        }
        this.mIsFullQs = 0;
    }

    @Override // com.android.systemui.qs.QSTile
    public void addCallback(QSTile.Callback callback) {
        this.mHandler.obtainMessage(1, callback).sendToTarget();
    }

    protected void checkIfRestrictionEnforcedByAdminOnly(QSTile.State state, String str) {
        state.disabledByPolicy = false;
    }

    @Override // com.android.systemui.qs.QSTile
    public void click(View view) {
        int i = this.mClickEventId;
        this.mClickEventId = i + 1;
        this.mHandler.obtainMessage(2, i, 0, view).sendToTarget();
    }

    @Override // com.android.systemui.qs.QSTile
    public void destroy() {
        this.mHandler.sendEmptyMessage(7);
    }

    @Override // com.android.systemui.qs.QSTile
    public InstanceId getInstanceId() {
        return this.mInstanceId;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.mLifecycle;
    }

    public abstract Intent getLongClickIntent();

    @Override // com.android.systemui.qs.QSTile
    public String getMetricsSpec() {
        return this.mTileSpec;
    }

    protected long getStaleTimeout() {
        return 600000L;
    }

    public QSTile.State getState() {
        return this.mState;
    }

    @Override // com.android.systemui.qs.QSTile
    public String getTileSpec() {
        return this.mTileSpec;
    }

    protected abstract void handleClick(View view);

    protected void handleDestroy() {
        if (this.mListeners.size() != 0) {
            handleSetListening(false);
            this.mListeners.clear();
        }
        this.mCallbacks.clear();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleDestroy$3();
            }
        });
    }

    protected void handleInitialize() {
    }

    protected void handleLongClick(View view) {
        this.mActivityStarter.postStartActivityDismissingKeyguard(getLongClickIntent(), 0);
    }

    protected final void handleRefreshState(Object obj) {
        handleUpdateState(this.mTmpState, obj);
        boolean zCopyTo = this.mTmpState.copyTo(this.mState);
        if (this.mReadyState == 1) {
            this.mReadyState = 2;
            zCopyTo = true;
        }
        if (zCopyTo) {
            handleStateChanged();
        }
        this.mHandler.removeMessages(11);
        this.mHandler.sendEmptyMessageDelayed(11, getStaleTimeout());
        setListening(this.mStaleListener, false);
    }

    protected void handleSecondaryClick(View view) {
        handleClick(view);
    }

    protected void handleSetListening(boolean z) {
    }

    protected void handleStale() {
        if (this.mListeners.isEmpty()) {
            setListening(this.mStaleListener, true);
        } else {
            refreshState();
        }
    }

    protected abstract void handleUpdateState(QSTile.State state, Object obj);

    protected void handleUserSwitch(int i) {
        handleRefreshState(null);
    }

    public void initialize() {
        this.mHandler.sendEmptyMessage(12);
    }

    @Override // com.android.systemui.qs.QSTile
    public boolean isAvailable() {
        return true;
    }

    @Override // com.android.systemui.qs.QSTile
    public final boolean isListening() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }

    @Override // com.android.systemui.qs.QSTile
    public void longClick(View view) {
        int i = this.mClickEventId;
        this.mClickEventId = i + 1;
        this.mHandler.obtainMessage(4, i, 0, view).sendToTarget();
    }

    public abstract QSTile.State newTileState();

    public void postStale() {
        this.mHandler.sendEmptyMessage(11);
    }

    @Override // com.android.systemui.qs.QSTile
    public void refreshState() {
        refreshState(null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void refreshState(Object obj) {
        this.mHandler.obtainMessage(5, obj).sendToTarget();
    }

    @Override // com.android.systemui.qs.QSTile
    public void removeCallback(QSTile.Callback callback) {
        this.mHandler.obtainMessage(9, callback).sendToTarget();
    }

    @Override // com.android.systemui.qs.QSTile
    public void removeCallbacks() {
        this.mHandler.sendEmptyMessage(8);
    }

    protected final void resetStates() {
        this.mState = newTileState();
        QSTile.State stateNewTileState = newTileState();
        this.mTmpState = stateNewTileState;
        QSTile.State state = this.mState;
        String str = this.mTileSpec;
        state.spec = str;
        stateNewTileState.spec = str;
    }

    @Override // com.android.systemui.qs.QSTile
    public void setListening(Object obj, boolean z) {
        this.mHandler.obtainMessage(10, z ? 1 : 0, 0, obj).sendToTarget();
    }

    public void setTileSpec(String str) {
        this.mTileSpec = str;
        this.mState.spec = str;
        this.mTmpState.spec = str;
    }

    @Override // com.android.systemui.qs.QSTile
    public void userSwitch(int i) {
        this.mHandler.obtainMessage(6, i, 0).sendToTarget();
    }
}
