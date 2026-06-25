package kotlinx.coroutines.channels;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

/* JADX INFO: compiled from: Channel.kt */
/* JADX INFO: loaded from: classes2.dex */
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

        public static boolean offer(SendChannel sendChannel, Object obj) throws Throwable {
            Object objMo2736trySendJP2dKIU = sendChannel.mo2736trySendJP2dKIU(obj);
            if (ChannelResult.m2746isSuccessimpl(objMo2736trySendJP2dKIU)) {
                return true;
            }
            Throwable thM2742exceptionOrNullimpl = ChannelResult.m2742exceptionOrNullimpl(objMo2736trySendJP2dKIU);
            if (thM2742exceptionOrNullimpl == null) {
                return false;
            }
            throw StackTraceRecoveryKt.recoverStackTrace(thM2742exceptionOrNullimpl);
        }
    }

    boolean close(Throwable th);

    void invokeOnClose(Function1 function1);

    boolean isClosedForSend();

    boolean offer(Object obj);

    Object send(Object obj, Continuation continuation);

    /* JADX INFO: renamed from: trySend-JP2dKIU */
    Object mo2736trySendJP2dKIU(Object obj);
}
