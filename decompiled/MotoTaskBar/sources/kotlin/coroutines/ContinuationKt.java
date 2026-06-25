package kotlin.coroutines;

import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: Continuation.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class ContinuationKt {
    public static final void startCoroutine(Function2 function2, Object obj, Continuation continuation) {
        function2.getClass();
        continuation.getClass();
        Continuation continuationIntercepted = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(function2, obj, continuation));
        Result.Companion companion = Result.Companion;
        continuationIntercepted.resumeWith(Result.m2707constructorimpl(Unit.INSTANCE));
    }
}
