package com.android.systemui.statusbar.notification.row;

import android.graphics.drawable.Icon;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: BigPictureIconManager.kt */
/* JADX INFO: loaded from: classes.dex */
final class BigPictureIconManager$loadImage$drawableAndState$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Icon $icon;
    int label;
    final /* synthetic */ BigPictureIconManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    BigPictureIconManager$loadImage$drawableAndState$1(BigPictureIconManager bigPictureIconManager, Icon icon, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bigPictureIconManager;
        this.$icon = icon;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BigPictureIconManager$loadImage$drawableAndState$1(this.this$0, this.$icon, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((BigPictureIconManager$loadImage$drawableAndState$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.this$0.loadImageSync(this.$icon);
    }
}
