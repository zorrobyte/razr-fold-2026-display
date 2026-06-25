package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* JADX INFO: compiled from: DisposableHandleExt.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DisposableHandleExtKt {

    /* JADX INFO: renamed from: com.android.systemui.util.kotlin.DisposableHandleExtKt$awaitCancellationThenDispose$1, reason: invalid class name */
    /* JADX INFO: compiled from: DisposableHandleExt.kt */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DisposableHandleExtKt.awaitCancellationThenDispose(null, this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object awaitCancellationThenDispose(kotlinx.coroutines.DisposableHandle r4, kotlin.coroutines.Continuation r5) {
        /*
            boolean r0 = r5 instanceof com.android.systemui.util.kotlin.DisposableHandleExtKt.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.util.kotlin.DisposableHandleExtKt$awaitCancellationThenDispose$1 r0 = (com.android.systemui.util.kotlin.DisposableHandleExtKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.util.kotlin.DisposableHandleExtKt$awaitCancellationThenDispose$1 r0 = new com.android.systemui.util.kotlin.DisposableHandleExtKt$awaitCancellationThenDispose$1
            r0.<init>(r5)
        L18:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L37
            if (r2 == r3) goto L2d
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2d:
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.DisposableHandle r4 = (kotlinx.coroutines.DisposableHandle) r4
            kotlin.ResultKt.throwOnFailure(r5)     // Catch: java.lang.Throwable -> L35
            goto L45
        L35:
            r5 = move-exception
            goto L4b
        L37:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.L$0 = r4     // Catch: java.lang.Throwable -> L35
            r0.label = r3     // Catch: java.lang.Throwable -> L35
            java.lang.Object r5 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)     // Catch: java.lang.Throwable -> L35
            if (r5 != r1) goto L45
            return r1
        L45:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L35
            r5.<init>()     // Catch: java.lang.Throwable -> L35
            throw r5     // Catch: java.lang.Throwable -> L35
        L4b:
            r4.dispose()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.DisposableHandleExtKt.awaitCancellationThenDispose(kotlinx.coroutines.DisposableHandle, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
