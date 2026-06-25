package com.android.systemui.statusbar.notification.footer.ui.viewmodel;

import com.android.systemui.res.R$string;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.util.ui.AnimatedValueKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: FooterViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FooterViewModel {
    private final FooterButtonViewModel clearAllButton;
    private final Flow clearAllButtonVisible;

    public FooterViewModel(ActiveNotificationsInteractor activeNotificationsInteractor, SeenNotificationsInteractor seenNotificationsInteractor) {
        activeNotificationsInteractor.getClass();
        seenNotificationsInteractor.getClass();
        final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(activeNotificationsInteractor.getHasClearableNotifications());
        this.clearAllButtonVisible = flowDistinctUntilChanged;
        this.clearAllButton = new FooterButtonViewModel(FlowKt.flowOf(Integer.valueOf(R$string.clear_all_notifications_text)), FlowKt.flowOf(Integer.valueOf(R$string.accessibility_clear_all)), AnimatedValueKt.toAnimatedValueFlow(new Flow() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel$special$$inlined$map$1

            /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel$special$$inlined$map$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: Emitters.kt */
            public final class AnonymousClass2 implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L29:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L31:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        com.android.systemui.util.ui.AnimatableEvent r6 = new com.android.systemui.util.ui.AnimatableEvent
                        java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r5)
                        r2 = 0
                        r6.<init>(r5, r2)
                        r0.label = r3
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        }));
    }

    public final FooterButtonViewModel getClearAllButton() {
        return this.clearAllButton;
    }
}
