package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: NotificationListViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
final class NotificationListViewModel$shouldShowEmptyShadeView$2$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    NotificationListViewModel$shouldShowEmptyShadeView$2$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke(((Boolean) obj).booleanValue(), ((Boolean) obj2).booleanValue(), (Continuation) obj3);
    }

    public final Object invoke(boolean z, boolean z2, Continuation continuation) {
        NotificationListViewModel$shouldShowEmptyShadeView$2$1 notificationListViewModel$shouldShowEmptyShadeView$2$1 = new NotificationListViewModel$shouldShowEmptyShadeView$2$1(continuation);
        notificationListViewModel$shouldShowEmptyShadeView$2$1.Z$0 = z;
        notificationListViewModel$shouldShowEmptyShadeView$2$1.Z$1 = z2;
        return notificationListViewModel$shouldShowEmptyShadeView$2$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = false;
        if (!z && !z2) {
            z3 = true;
        }
        return Boxing.boxBoolean(z3);
    }
}
