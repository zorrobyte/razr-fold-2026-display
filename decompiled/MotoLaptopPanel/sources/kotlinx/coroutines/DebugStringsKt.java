package kotlinx.coroutines;

import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.DispatchedContinuation;

/* JADX INFO: compiled from: DebugStrings.kt */
/* JADX INFO: loaded from: classes.dex */
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
        Object objM2192constructorimpl;
        continuation.getClass();
        if (continuation instanceof DispatchedContinuation) {
            return ((DispatchedContinuation) continuation).toString();
        }
        try {
            Result.Companion companion = Result.Companion;
            objM2192constructorimpl = Result.m2192constructorimpl(continuation + "@" + getHexAddress(continuation));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2192constructorimpl = Result.m2192constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m2193exceptionOrNullimpl(objM2192constructorimpl) != null) {
            objM2192constructorimpl = continuation.getClass().getName() + "@" + getHexAddress(continuation);
        }
        return (String) objM2192constructorimpl;
    }
}
