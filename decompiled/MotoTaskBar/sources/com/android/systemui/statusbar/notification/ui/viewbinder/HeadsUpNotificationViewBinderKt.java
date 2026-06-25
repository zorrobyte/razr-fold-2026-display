package com.android.systemui.statusbar.notification.ui.viewbinder;

import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinderKt;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;

/* JADX INFO: compiled from: HeadsUpNotificationViewBinder.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class HeadsUpNotificationViewBinderKt {

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinderKt$isHeadsUpAnimatingAway$1, reason: invalid class name */
    /* JADX INFO: compiled from: HeadsUpNotificationViewBinder.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ NotificationStackScrollLayout $this_isHeadsUpAnimatingAway;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(NotificationStackScrollLayout notificationStackScrollLayout, Continuation continuation) {
            super(2, continuation);
            this.$this_isHeadsUpAnimatingAway = notificationStackScrollLayout;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(NotificationStackScrollLayout notificationStackScrollLayout) {
            notificationStackScrollLayout.setHeadsUpAnimatingAwayListener(null);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_isHeadsUpAnimatingAway, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                this.$this_isHeadsUpAnimatingAway.setHeadsUpAnimatingAwayListener(new Consumer() { // from class: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinderKt.isHeadsUpAnimatingAway.1.1
                    @Override // java.util.function.Consumer
                    public final void accept(Boolean bool) {
                        ProducerScope producerScope2 = producerScope;
                        bool.getClass();
                        producerScope2.mo2736trySendJP2dKIU(bool);
                    }
                });
                final NotificationStackScrollLayout notificationStackScrollLayout = this.$this_isHeadsUpAnimatingAway;
                Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinderKt$isHeadsUpAnimatingAway$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return HeadsUpNotificationViewBinderKt.AnonymousClass1.invokeSuspend$lambda$0(notificationStackScrollLayout);
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow isHeadsUpAnimatingAway(NotificationStackScrollLayout notificationStackScrollLayout) {
        return FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(notificationStackScrollLayout, null));
    }
}
