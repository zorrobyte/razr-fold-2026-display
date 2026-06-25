package com.android.systemui.statusbar.policy;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;

/* JADX INFO: compiled from: SmartReplyStateInflater.kt */
/* JADX INFO: loaded from: classes.dex */
final class DelayedOnClickListener implements View.OnClickListener {
    private final View.OnClickListener mActualListener;
    private final long mInitDelayMs;
    private final long mInitTimeMs;

    public DelayedOnClickListener(View.OnClickListener onClickListener, long j) {
        onClickListener.getClass();
        this.mActualListener = onClickListener;
        this.mInitDelayMs = j;
        this.mInitTimeMs = SystemClock.elapsedRealtime();
    }

    private final boolean hasFinishedInitialization() {
        return SystemClock.elapsedRealtime() >= this.mInitTimeMs + this.mInitDelayMs;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        view.getClass();
        if (hasFinishedInitialization()) {
            this.mActualListener.onClick(view);
            return;
        }
        Log.i("SmartReplyViewInflater", "Accidental Smart Suggestion click registered, delay: " + this.mInitDelayMs);
    }
}
