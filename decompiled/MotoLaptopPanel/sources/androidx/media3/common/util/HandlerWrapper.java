package androidx.media3.common.util;

import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public interface HandlerWrapper {

    public interface Message {
    }

    Looper getLooper();

    boolean hasMessages(int i);

    Message obtainMessage(int i);

    boolean sendMessageAtFrontOfQueue(Message message);
}
