package kotlinx.coroutines.channels;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: Channel.kt */
/* JADX INFO: loaded from: classes.dex */
public interface SendChannel {

    /* JADX INFO: compiled from: Channel.kt */
    public abstract class DefaultImpls {
        public static /* synthetic */ boolean close$default(SendChannel sendChannel, Throwable th, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: close");
            }
            if ((i & 1) != 0) {
                th = null;
            }
            return sendChannel.close(th);
        }
    }

    boolean close(Throwable th);

    void invokeOnClose(Function1 function1);

    boolean isClosedForSend();

    Object send(Object obj, Continuation continuation);

    /* JADX INFO: renamed from: trySend-JP2dKIU */
    Object mo2215trySendJP2dKIU(Object obj);
}
