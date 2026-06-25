package com.motorola.taskbar.util;

import android.app.ActivityTaskManager;
import android.app.IActivityManager;
import android.app.TaskStackListener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Trace;
import android.util.Log;
import com.android.internal.os.SomeArgs;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class TaskStackChangeListenersExt extends TaskStackListener {
    private static final String TAG = TaskStackChangeListenersExt.class.getSimpleName();
    private final Handler mHandler;
    private boolean mRegistered;
    private final List mTaskStackListeners = new ArrayList();

    final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            synchronized (TaskStackChangeListenersExt.this.mTaskStackListeners) {
                try {
                    if (message.what == 1) {
                        Trace.beginSection("onTaskFocusChanged");
                        for (int size = TaskStackChangeListenersExt.this.mTaskStackListeners.size() - 1; size >= 0; size--) {
                            ((TaskStackChangeListenerExt) TaskStackChangeListenersExt.this.mTaskStackListeners.get(size)).onTaskFocusChanged(message.arg1, message.arg2 != 0);
                        }
                        Trace.endSection();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Object obj = message.obj;
            if (obj instanceof SomeArgs) {
                ((SomeArgs) obj).recycle();
            }
        }
    }

    public TaskStackChangeListenersExt(Looper looper) {
        this.mHandler = new H(looper);
    }

    public void addListener(IActivityManager iActivityManager, TaskStackChangeListenerExt taskStackChangeListenerExt) {
        synchronized (this.mTaskStackListeners) {
            this.mTaskStackListeners.add(taskStackChangeListenerExt);
        }
        if (this.mRegistered) {
            return;
        }
        try {
            ActivityTaskManager.getService().registerTaskStackListener(this);
            this.mRegistered = true;
        } catch (Exception e) {
            Log.w(TAG, "Failed to call registerTaskStackListener", e);
        }
    }

    public void onTaskFocusChanged(int i, boolean z) {
        this.mHandler.obtainMessage(1, i, z ? 1 : 0).sendToTarget();
    }
}
