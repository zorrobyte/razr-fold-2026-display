package com.motorola.taskbar.shortcut;

import android.os.Handler;
import android.os.Message;
import com.motorola.internal.policy.IShortcutServiceByDisplay;

/* JADX INFO: loaded from: classes2.dex */
public class ShortcutKeyServiceProxy extends IShortcutServiceByDisplay.Stub {
    private Callbacks mCallbacks;
    private final Object mLock = new Object();
    private final Handler mHandler = new H();

    public interface Callbacks {
        void onShortcutKeyPressed(int i, long j);
    }

    final class H extends Handler {
        private H() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            ShortcutKeyServiceProxy.this.mCallbacks.onShortcutKeyPressed(message.arg1, ((Long) message.obj).longValue());
        }
    }

    public ShortcutKeyServiceProxy(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    public void notifyShortcutKeyPressed(int i, long j) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1, i, 0, Long.valueOf(j)).sendToTarget();
        }
    }
}
