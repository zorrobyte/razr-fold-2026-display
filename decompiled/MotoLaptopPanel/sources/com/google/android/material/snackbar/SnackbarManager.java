package com.google.android.material.snackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0;

/* JADX INFO: loaded from: classes.dex */
class SnackbarManager {
    private static SnackbarManager snackbarManager;
    private final Object lock = new Object();
    private final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.google.android.material.snackbar.SnackbarManager.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            SnackbarManager snackbarManager2 = SnackbarManager.this;
            MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(message.obj);
            snackbarManager2.handleTimeout(null);
            return true;
        }
    });

    interface Callback {
    }

    abstract class SnackbarRecord {
    }

    private SnackbarManager() {
    }

    private boolean cancelSnackbarLocked(SnackbarRecord snackbarRecord, int i) {
        throw null;
    }

    static SnackbarManager getInstance() {
        if (snackbarManager == null) {
            snackbarManager = new SnackbarManager();
        }
        return snackbarManager;
    }

    private boolean isCurrentSnackbarLocked(Callback callback) {
        return false;
    }

    void handleTimeout(SnackbarRecord snackbarRecord) {
        synchronized (this.lock) {
            cancelSnackbarLocked(snackbarRecord, 2);
        }
    }

    public void pauseTimeout(Callback callback) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    throw null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void restoreTimeoutIfPaused(Callback callback) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    throw null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
