package com.android.systemui.media.controls.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: MediaControlViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
final class MediaControlViewModel$playTurbulenceNoise$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    MediaControlViewModel$playTurbulenceNoise$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke(((Boolean) obj).booleanValue(), ((Boolean) obj2).booleanValue(), (Continuation) obj3);
    }

    public final Object invoke(boolean z, boolean z2, Continuation continuation) {
        MediaControlViewModel$playTurbulenceNoise$1 mediaControlViewModel$playTurbulenceNoise$1 = new MediaControlViewModel$playTurbulenceNoise$1(continuation);
        mediaControlViewModel$playTurbulenceNoise$1.Z$0 = z;
        mediaControlViewModel$playTurbulenceNoise$1.Z$1 = z2;
        return mediaControlViewModel$playTurbulenceNoise$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boxing.boxBoolean(this.Z$0 && this.Z$1);
    }
}
