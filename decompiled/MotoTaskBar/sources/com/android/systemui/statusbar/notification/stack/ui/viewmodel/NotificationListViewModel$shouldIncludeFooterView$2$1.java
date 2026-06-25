package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* JADX INFO: compiled from: NotificationListViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
final class NotificationListViewModel$shouldIncludeFooterView$2$1 extends SuspendLambda implements Function4 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    NotificationListViewModel$shouldIncludeFooterView$2$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        return invoke(((Boolean) obj).booleanValue(), ((Boolean) obj2).booleanValue(), ((Boolean) obj3).booleanValue(), (Continuation) obj4);
    }

    public final Object invoke(boolean z, boolean z2, boolean z3, Continuation continuation) {
        NotificationListViewModel$shouldIncludeFooterView$2$1 notificationListViewModel$shouldIncludeFooterView$2$1 = new NotificationListViewModel$shouldIncludeFooterView$2$1(continuation);
        notificationListViewModel$shouldIncludeFooterView$2$1.Z$0 = z;
        notificationListViewModel$shouldIncludeFooterView$2$1.Z$1 = z2;
        notificationListViewModel$shouldIncludeFooterView$2$1.Z$2 = z3;
        return notificationListViewModel$shouldIncludeFooterView$2$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return !this.Z$0 ? NotificationListViewModel.VisibilityChange.DISAPPEAR_WITH_ANIMATION : this.Z$1 ? NotificationListViewModel.VisibilityChange.DISAPPEAR_WITHOUT_ANIMATION : this.Z$2 ? NotificationListViewModel.VisibilityChange.DISAPPEAR_WITH_ANIMATION : NotificationListViewModel.VisibilityChange.APPEAR_WITH_ANIMATION;
    }
}
