package kotlinx.coroutines.internal;

import _COROUTINE.ArtificialStackFrames;
import kotlin.Result;
import kotlin.ResultKt;

/* JADX INFO: compiled from: StackTraceRecovery.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class StackTraceRecoveryKt {
    private static final StackTraceElement ARTIFICIAL_FRAME = new ArtificialStackFrames().coroutineBoundary();
    private static final String baseContinuationImplClassName;
    private static final String stackTraceRecoveryClassName;

    static {
        Object objM2707constructorimpl;
        Object objM2707constructorimpl2;
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(Class.forName("kotlin.coroutines.jvm.internal.BaseContinuationImpl").getCanonicalName());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        baseContinuationImplClassName = (String) (Result.m2709exceptionOrNullimpl(objM2707constructorimpl) == null ? objM2707constructorimpl : "kotlin.coroutines.jvm.internal.BaseContinuationImpl");
        try {
            objM2707constructorimpl2 = Result.m2707constructorimpl(StackTraceRecoveryKt.class.getCanonicalName());
        } catch (Throwable th2) {
            Result.Companion companion3 = Result.Companion;
            objM2707constructorimpl2 = Result.m2707constructorimpl(ResultKt.createFailure(th2));
        }
        if (Result.m2709exceptionOrNullimpl(objM2707constructorimpl2) != null) {
            objM2707constructorimpl2 = "kotlinx.coroutines.internal.StackTraceRecoveryKt";
        }
        stackTraceRecoveryClassName = (String) objM2707constructorimpl2;
    }

    public static final Throwable recoverStackTrace(Throwable th) {
        th.getClass();
        return th;
    }
}
