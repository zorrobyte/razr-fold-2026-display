package com.motorola.taskbar.recent;

import android.os.Handler;

/* JADX INFO: loaded from: classes2.dex */
public abstract class CancellablHandlerRunnable implements Runnable {
    private final Runnable mEndRunnable;
    private final Handler mHandler;
    private boolean mEnded = false;
    private boolean mCanceled = false;

    public CancellablHandlerRunnable(Handler handler, Runnable runnable) {
        this.mHandler = handler;
        this.mEndRunnable = runnable;
    }

    public void cancel() {
        this.mHandler.removeCallbacks(this);
        this.mCanceled = true;
        onEnd();
    }

    protected boolean isCanceled() {
        return this.mCanceled;
    }

    public void onEnd() {
        if (this.mEnded) {
            return;
        }
        this.mEnded = true;
        Runnable runnable = this.mEndRunnable;
        if (runnable != null) {
            runnable.run();
        }
    }
}
