package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import android.os.Trace;
import com.android.app.tracing.TraceUtils;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: NotificationListViewBinder.kt */
/* JADX INFO: loaded from: classes.dex */
final class NotificationListViewBinder$reinflateAndBindFooter$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FooterViewModel $footerViewModel;
    final /* synthetic */ StateFlow $hasNonClearableSilentNotifications;
    final /* synthetic */ NotificationStackScrollLayout $parentView;
    int I$0;
    long J$0;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NotificationListViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    NotificationListViewBinder$reinflateAndBindFooter$2$1(NotificationStackScrollLayout notificationStackScrollLayout, NotificationListViewBinder notificationListViewBinder, FooterViewModel footerViewModel, StateFlow stateFlow, Continuation continuation) {
        super(2, continuation);
        this.$parentView = notificationStackScrollLayout;
        this.this$0 = notificationListViewBinder;
        this.$footerViewModel = footerViewModel;
        this.$hasNonClearableSilentNotifications = stateFlow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationListViewBinder$reinflateAndBindFooter$2$1 notificationListViewBinder$reinflateAndBindFooter$2$1 = new NotificationListViewBinder$reinflateAndBindFooter$2$1(this.$parentView, this.this$0, this.$footerViewModel, this.$hasNonClearableSilentNotifications, continuation);
        notificationListViewBinder$reinflateAndBindFooter$2$1.L$0 = obj;
        return notificationListViewBinder$reinflateAndBindFooter$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(FooterView footerView, Continuation continuation) {
        return ((NotificationListViewBinder$reinflateAndBindFooter$2$1) create(footerView, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        int iNextInt;
        Throwable th;
        String str;
        long j;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FooterView footerView = (FooterView) this.L$0;
            NotificationStackScrollLayout notificationStackScrollLayout = this.$parentView;
            NotificationListViewBinder notificationListViewBinder = this.this$0;
            FooterViewModel footerViewModel = this.$footerViewModel;
            StateFlow stateFlow = this.$hasNonClearableSilentNotifications;
            TraceUtils traceUtils = TraceUtils.INSTANCE;
            iNextInt = ThreadLocalRandom.current().nextInt();
            Trace.asyncTraceForTrackBegin(4096L, "AsyncTraces", "bind FooterView", iNextInt);
            try {
                notificationStackScrollLayout.setFooterView(footerView);
                this.L$0 = "AsyncTraces";
                this.J$0 = 4096L;
                this.I$0 = iNextInt;
                this.label = 1;
                if (notificationListViewBinder.bindFooter(footerView, footerViewModel, notificationStackScrollLayout, stateFlow, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                str = "AsyncTraces";
                j = 4096;
            } catch (Throwable th2) {
                th = th2;
                str = "AsyncTraces";
                j = 4096;
                Trace.asyncTraceForTrackEnd(j, str, iNextInt);
                throw th;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            iNextInt = this.I$0;
            j = this.J$0;
            str = (String) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
                Trace.asyncTraceForTrackEnd(j, str, iNextInt);
                throw th;
            }
        }
        Unit unit = Unit.INSTANCE;
        Trace.asyncTraceForTrackEnd(j, str, iNextInt);
        return Unit.INSTANCE;
    }
}
