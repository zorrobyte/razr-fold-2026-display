package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.Flags;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.shared.HeadsUpRowKey;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackInteractor;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import com.android.systemui.util.ui.AnimatedValue;
import com.android.systemui.util.ui.AnimatedValueKt;
import java.util.Optional;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: NotificationListViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationListViewModel extends FlowDumperImpl {
    private final Lazy areNotificationsHiddenInShade$delegate;
    private final Optional footer;
    private final Lazy hasClearableAlertingNotifications$delegate;
    private final Lazy hasFilteredOutSeenNotifications$delegate;
    private final Lazy hasNonClearableSilentNotifications$delegate;
    private final Lazy hasPinnedHeadsUpRow$delegate;
    private final Lazy headsUpAnimationsEnabled$delegate;
    private final HeadsUpNotificationInteractor headsUpNotificationInteractor;
    private final Lazy isImportantForAccessibility$delegate;
    private final Optional logger;
    private final Lazy pinnedHeadsUpRows$delegate;
    private final Lazy shouldHideFooterView$delegate;
    private final Lazy shouldIncludeFooterView$delegate;
    private final Lazy shouldShowEmptyShadeView$delegate;
    private final Lazy topHeadsUpRow$delegate;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: NotificationListViewModel.kt */
    public final class VisibilityChange {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ VisibilityChange[] $VALUES;
        private final boolean canAnimate;
        private final boolean visible;
        public static final VisibilityChange DISAPPEAR_WITHOUT_ANIMATION = new VisibilityChange("DISAPPEAR_WITHOUT_ANIMATION", 0, false, false);
        public static final VisibilityChange DISAPPEAR_WITH_ANIMATION = new VisibilityChange("DISAPPEAR_WITH_ANIMATION", 1, false, true);
        public static final VisibilityChange APPEAR_WITH_ANIMATION = new VisibilityChange("APPEAR_WITH_ANIMATION", 2, true, true);

        private static final /* synthetic */ VisibilityChange[] $values() {
            return new VisibilityChange[]{DISAPPEAR_WITHOUT_ANIMATION, DISAPPEAR_WITH_ANIMATION, APPEAR_WITH_ANIMATION};
        }

        static {
            VisibilityChange[] visibilityChangeArr$values = $values();
            $VALUES = visibilityChangeArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(visibilityChangeArr$values);
        }

        private VisibilityChange(String str, int i, boolean z, boolean z2) {
            this.visible = z;
            this.canAnimate = z2;
        }

        public static VisibilityChange valueOf(String str) {
            return (VisibilityChange) Enum.valueOf(VisibilityChange.class, str);
        }

        public static VisibilityChange[] values() {
            return (VisibilityChange[]) $VALUES.clone();
        }

        public final boolean getVisible() {
            return this.visible;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationListViewModel(Optional optional, Optional optional2, final ActiveNotificationsInteractor activeNotificationsInteractor, final NotificationStackInteractor notificationStackInteractor, HeadsUpNotificationInteractor headsUpNotificationInteractor, final RemoteInputInteractor remoteInputInteractor, final SeenNotificationsInteractor seenNotificationsInteractor, final CoroutineDispatcher coroutineDispatcher, DumpManager dumpManager) {
        super(dumpManager, null, 2, null);
        optional.getClass();
        optional2.getClass();
        activeNotificationsInteractor.getClass();
        notificationStackInteractor.getClass();
        headsUpNotificationInteractor.getClass();
        remoteInputInteractor.getClass();
        seenNotificationsInteractor.getClass();
        coroutineDispatcher.getClass();
        dumpManager.getClass();
        this.footer = optional;
        this.logger = optional2;
        this.headsUpNotificationInteractor = headsUpNotificationInteractor;
        this.isImportantForAccessibility$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationListViewModel.isImportantForAccessibility_delegate$lambda$0(this.f$0, activeNotificationsInteractor, notificationStackInteractor, coroutineDispatcher);
            }
        });
        this.shouldShowEmptyShadeView$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationListViewModel.shouldShowEmptyShadeView_delegate$lambda$1(this.f$0, activeNotificationsInteractor, notificationStackInteractor, coroutineDispatcher);
            }
        });
        this.shouldHideFooterView$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationListViewModel.shouldHideFooterView_delegate$lambda$2();
            }
        });
        this.shouldIncludeFooterView$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationListViewModel.shouldIncludeFooterView_delegate$lambda$5(this.f$0, activeNotificationsInteractor, notificationStackInteractor, remoteInputInteractor, coroutineDispatcher);
            }
        });
        this.areNotificationsHiddenInShade$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationListViewModel.areNotificationsHiddenInShade_delegate$lambda$6();
            }
        });
        this.hasFilteredOutSeenNotifications$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationListViewModel.hasFilteredOutSeenNotifications_delegate$lambda$7(this.f$0, seenNotificationsInteractor);
            }
        });
        this.hasClearableAlertingNotifications$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationListViewModel.hasClearableAlertingNotifications_delegate$lambda$8(this.f$0, activeNotificationsInteractor);
            }
        });
        this.hasNonClearableSilentNotifications$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationListViewModel.hasNonClearableSilentNotifications_delegate$lambda$9(this.f$0, activeNotificationsInteractor);
            }
        });
        this.topHeadsUpRow$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationListViewModel.topHeadsUpRow_delegate$lambda$10(this.f$0);
            }
        });
        this.pinnedHeadsUpRows$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationListViewModel.pinnedHeadsUpRows_delegate$lambda$11(this.f$0);
            }
        });
        this.headsUpAnimationsEnabled$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationListViewModel.headsUpAnimationsEnabled_delegate$lambda$12();
            }
        });
        this.hasPinnedHeadsUpRow$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return NotificationListViewModel.hasPinnedHeadsUpRow_delegate$lambda$13(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow areNotificationsHiddenInShade_delegate$lambda$6() {
        return FlowKt.flowOf(Boolean.FALSE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow hasClearableAlertingNotifications_delegate$lambda$8(NotificationListViewModel notificationListViewModel, ActiveNotificationsInteractor activeNotificationsInteractor) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        FooterViewRefactor footerViewRefactor = FooterViewRefactor.INSTANCE;
        boolean zNotificationsFooterViewRefactor = Flags.notificationsFooterViewRefactor();
        if (!zNotificationsFooterViewRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "FooterViewRefactor") + " to be enabled.");
        }
        return !zNotificationsFooterViewRefactor ? FlowKt.flowOf(Boolean.FALSE) : notificationListViewModel.dumpWhileCollecting(activeNotificationsInteractor.getHasClearableAlertingNotifications(), "hasClearableAlertingNotifications");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow hasFilteredOutSeenNotifications_delegate$lambda$7(NotificationListViewModel notificationListViewModel, SeenNotificationsInteractor seenNotificationsInteractor) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        FooterViewRefactor footerViewRefactor = FooterViewRefactor.INSTANCE;
        boolean zNotificationsFooterViewRefactor = Flags.notificationsFooterViewRefactor();
        if (!zNotificationsFooterViewRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "FooterViewRefactor") + " to be enabled.");
        }
        return !zNotificationsFooterViewRefactor ? FlowKt.flowOf(Boolean.FALSE) : notificationListViewModel.dumpWhileCollecting(seenNotificationsInteractor.getHasFilteredOutSeenNotifications(), "hasFilteredOutSeenNotifications");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow hasNonClearableSilentNotifications_delegate$lambda$9(NotificationListViewModel notificationListViewModel, ActiveNotificationsInteractor activeNotificationsInteractor) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        FooterViewRefactor footerViewRefactor = FooterViewRefactor.INSTANCE;
        boolean zNotificationsFooterViewRefactor = Flags.notificationsFooterViewRefactor();
        if (!zNotificationsFooterViewRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "FooterViewRefactor") + " to be enabled.");
        }
        return !zNotificationsFooterViewRefactor ? FlowKt.flowOf(Boolean.FALSE) : notificationListViewModel.dumpWhileCollecting(activeNotificationsInteractor.getHasNonClearableSilentNotifications(), "hasNonClearableSilentNotifications");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow hasPinnedHeadsUpRow_delegate$lambda$13(NotificationListViewModel notificationListViewModel) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        boolean zNotificationsHeadsUpRefactor = Flags.notificationsHeadsUpRefactor();
        if (!zNotificationsHeadsUpRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "NotificationsHeadsUpRefactor") + " to be enabled.");
        }
        return !zNotificationsHeadsUpRefactor ? FlowKt.flowOf(Boolean.FALSE) : notificationListViewModel.dumpWhileCollecting(notificationListViewModel.headsUpNotificationInteractor.getHasPinnedRows(), "hasPinnedHeadsUpRow");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow headsUpAnimationsEnabled_delegate$lambda$12() {
        return FlowKt.flowOf(Boolean.FALSE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow isImportantForAccessibility_delegate$lambda$0(NotificationListViewModel notificationListViewModel, ActiveNotificationsInteractor activeNotificationsInteractor, NotificationStackInteractor notificationStackInteractor, CoroutineDispatcher coroutineDispatcher) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        FooterViewRefactor footerViewRefactor = FooterViewRefactor.INSTANCE;
        boolean zNotificationsFooterViewRefactor = Flags.notificationsFooterViewRefactor();
        if (!zNotificationsFooterViewRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "FooterViewRefactor") + " to be enabled.");
        }
        return !zNotificationsFooterViewRefactor ? FlowKt.flowOf(Boolean.TRUE) : FlowKt.flowOn(notificationListViewModel.dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.combine(activeNotificationsInteractor.getAreAnyNotificationsPresent(), notificationStackInteractor.isShowingOnLockscreen(), new NotificationListViewModel$isImportantForAccessibility$2$1(null))), "isImportantForAccessibility"), coroutineDispatcher);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow pinnedHeadsUpRows_delegate$lambda$11(NotificationListViewModel notificationListViewModel) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        boolean zNotificationsHeadsUpRefactor = Flags.notificationsHeadsUpRefactor();
        if (!zNotificationsHeadsUpRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "NotificationsHeadsUpRefactor") + " to be enabled.");
        }
        return !zNotificationsHeadsUpRefactor ? FlowKt.flowOf(SetsKt.emptySet()) : notificationListViewModel.dumpWhileCollecting(notificationListViewModel.headsUpNotificationInteractor.getPinnedHeadsUpRows(), "pinnedHeadsUpRows");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow shouldHideFooterView_delegate$lambda$2() {
        return FlowKt.flowOf(Boolean.FALSE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow shouldIncludeFooterView_delegate$lambda$5(NotificationListViewModel notificationListViewModel, ActiveNotificationsInteractor activeNotificationsInteractor, NotificationStackInteractor notificationStackInteractor, RemoteInputInteractor remoteInputInteractor, CoroutineDispatcher coroutineDispatcher) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        FooterViewRefactor footerViewRefactor = FooterViewRefactor.INSTANCE;
        boolean zNotificationsFooterViewRefactor = Flags.notificationsFooterViewRefactor();
        if (!zNotificationsFooterViewRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "FooterViewRefactor") + " to be enabled.");
        }
        if (!zNotificationsFooterViewRefactor) {
            return FlowKt.flowOf(new AnimatedValue.NotAnimating(Boolean.FALSE));
        }
        final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.combine(activeNotificationsInteractor.getAreAnyNotificationsPresent(), notificationStackInteractor.isShowingOnLockscreen(), remoteInputInteractor.isRemoteInputActive(), new NotificationListViewModel$shouldIncludeFooterView$2$1(null)), new Function2() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Boolean.valueOf(NotificationListViewModel.shouldIncludeFooterView_delegate$lambda$5$lambda$3((NotificationListViewModel.VisibilityChange) obj, (NotificationListViewModel.VisibilityChange) obj2));
            }
        });
        return FlowKt.flowOn(notificationListViewModel.dumpWhileCollecting(AnimatedValueKt.toAnimatedValueFlow(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldIncludeFooterView_delegate$lambda$5$$inlined$map$1

            /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldIncludeFooterView_delegate$lambda$5$$inlined$map$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: Emitters.kt */
            public final class AnonymousClass2 implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldIncludeFooterView_delegate$lambda$5$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldIncludeFooterView_delegate$lambda$5$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldIncludeFooterView_delegate$lambda$5$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldIncludeFooterView_delegate$lambda$5$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldIncludeFooterView_delegate$lambda$5$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldIncludeFooterView_delegate$lambda$5$$inlined$map$1$2$1
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
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$VisibilityChange r5 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel.VisibilityChange) r5
                        com.android.systemui.util.ui.AnimatableEvent r6 = new com.android.systemui.util.ui.AnimatableEvent
                        boolean r5 = r5.getVisible()
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldIncludeFooterView_delegate$lambda$5$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        }), "shouldIncludeFooterView"), coroutineDispatcher);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean shouldIncludeFooterView_delegate$lambda$5$lambda$3(VisibilityChange visibilityChange, VisibilityChange visibilityChange2) {
        visibilityChange.getClass();
        visibilityChange2.getClass();
        return visibilityChange.getVisible() == visibilityChange2.getVisible();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow shouldShowEmptyShadeView_delegate$lambda$1(NotificationListViewModel notificationListViewModel, ActiveNotificationsInteractor activeNotificationsInteractor, NotificationStackInteractor notificationStackInteractor, CoroutineDispatcher coroutineDispatcher) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        FooterViewRefactor footerViewRefactor = FooterViewRefactor.INSTANCE;
        boolean zNotificationsFooterViewRefactor = Flags.notificationsFooterViewRefactor();
        if (!zNotificationsFooterViewRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "FooterViewRefactor") + " to be enabled.");
        }
        return !zNotificationsFooterViewRefactor ? FlowKt.flowOf(Boolean.FALSE) : FlowKt.flowOn(notificationListViewModel.dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.combine(activeNotificationsInteractor.getAreAnyNotificationsPresent(), notificationStackInteractor.isShowingOnLockscreen(), new NotificationListViewModel$shouldShowEmptyShadeView$2$1(null))), "shouldShowEmptyShadeView"), coroutineDispatcher);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Flow topHeadsUpRow_delegate$lambda$10(NotificationListViewModel notificationListViewModel) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        boolean zNotificationsHeadsUpRefactor = Flags.notificationsHeadsUpRefactor();
        if (!zNotificationsHeadsUpRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "NotificationsHeadsUpRefactor") + " to be enabled.");
        }
        return !zNotificationsHeadsUpRefactor ? FlowKt.flowOf(null) : notificationListViewModel.dumpWhileCollecting(notificationListViewModel.headsUpNotificationInteractor.getTopHeadsUpRow(), "topHeadsUpRow");
    }

    public final Object elementKeyFor(HeadsUpRowKey headsUpRowKey) {
        headsUpRowKey.getClass();
        return this.headsUpNotificationInteractor.elementKeyFor(headsUpRowKey);
    }

    public final Flow getAreNotificationsHiddenInShade() {
        return (Flow) this.areNotificationsHiddenInShade$delegate.getValue();
    }

    public final Optional getFooter() {
        return this.footer;
    }

    public final Flow getHasClearableAlertingNotifications() {
        return (Flow) this.hasClearableAlertingNotifications$delegate.getValue();
    }

    public final Flow getHasFilteredOutSeenNotifications() {
        return (Flow) this.hasFilteredOutSeenNotifications$delegate.getValue();
    }

    public final Flow getHasNonClearableSilentNotifications() {
        return (Flow) this.hasNonClearableSilentNotifications$delegate.getValue();
    }

    public final Flow getHasPinnedHeadsUpRow() {
        return (Flow) this.hasPinnedHeadsUpRow$delegate.getValue();
    }

    public final Flow getHeadsUpAnimationsEnabled() {
        return (Flow) this.headsUpAnimationsEnabled$delegate.getValue();
    }

    public final Optional getLogger() {
        return this.logger;
    }

    public final Flow getPinnedHeadsUpRows() {
        return (Flow) this.pinnedHeadsUpRows$delegate.getValue();
    }

    public final Flow getShouldHideFooterView() {
        return (Flow) this.shouldHideFooterView$delegate.getValue();
    }

    public final Flow getShouldIncludeFooterView() {
        return (Flow) this.shouldIncludeFooterView$delegate.getValue();
    }

    public final Flow getShouldShowEmptyShadeView() {
        return (Flow) this.shouldShowEmptyShadeView$delegate.getValue();
    }

    public final Flow getTopHeadsUpRow() {
        return (Flow) this.topHeadsUpRow$delegate.getValue();
    }

    public final Flow isImportantForAccessibility() {
        return (Flow) this.isImportantForAccessibility$delegate.getValue();
    }

    public final void setHeadsUpAnimatingAway(boolean z) {
        this.headsUpNotificationInteractor.setHeadsUpAnimatingAway(z);
    }
}
