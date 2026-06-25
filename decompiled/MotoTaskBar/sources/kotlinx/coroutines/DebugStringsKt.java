package kotlinx.coroutines;

import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.DispatchedContinuation;

/* JADX INFO: compiled from: DebugStrings.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class DebugStringsKt {
    public static final String getClassSimpleName(Object obj) {
        obj.getClass();
        return obj.getClass().getSimpleName();
    }

    public static final String getHexAddress(Object obj) {
        obj.getClass();
        String hexString = Integer.toHexString(System.identityHashCode(obj));
        hexString.getClass();
        return hexString;
    }

    public static final String toDebugString(Continuation continuation) {
        Object objM2707constructorimpl;
        continuation.getClass();
        if (continuation instanceof DispatchedContinuation) {
            return ((DispatchedContinuation) continuation).toString();
        }
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(continuation + "@" + getHexAddress(continuation));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m2709exceptionOrNullimpl(objM2707constructorimpl) != null) {
            objM2707constructorimpl = continuation.getClass().getName() + "@" + getHexAddress(continuation);
        }
        return (String) objM2707constructorimpl;
    }
}
