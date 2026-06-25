package com.android.systemui.statusbar.notification.stack;

import android.content.res.Resources;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import com.android.systemui.Flags;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$integer;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.util.ConvenienceExtensionsKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: NotificationStackSizeCalculator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationStackSizeCalculator {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(NotificationStackSizeCalculator.class, "maxKeyguardNotifications", "getMaxKeyguardNotifications()I", 0)), Reflection.mutableProperty1(new MutablePropertyReference1Impl(NotificationStackSizeCalculator.class, "dividerHeight", "getDividerHeight()F", 0))};
    private final ReadWriteProperty dividerHeight$delegate;
    private final ReadWriteProperty maxKeyguardNotifications$delegate;
    private boolean maxNotificationsExcludesMedia;
    private final MediaDataManager mediaDataManager;
    private final Resources resources;
    private boolean saveSpaceOnLockscreen;

    /* JADX INFO: compiled from: NotificationStackSizeCalculator.kt */
    public final class SpaceNeeded {
        private final float whenEnoughSpace;
        private final float whenSavingSpace;

        public SpaceNeeded(float f, float f2) {
            this.whenEnoughSpace = f;
            this.whenSavingSpace = f2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SpaceNeeded)) {
                return false;
            }
            SpaceNeeded spaceNeeded = (SpaceNeeded) obj;
            return Float.compare(this.whenEnoughSpace, spaceNeeded.whenEnoughSpace) == 0 && Float.compare(this.whenSavingSpace, spaceNeeded.whenSavingSpace) == 0;
        }

        public final float getWhenEnoughSpace() {
            return this.whenEnoughSpace;
        }

        public final float getWhenSavingSpace() {
            return this.whenSavingSpace;
        }

        public int hashCode() {
            return (Float.hashCode(this.whenEnoughSpace) * 31) + Float.hashCode(this.whenSavingSpace);
        }

        public String toString() {
            return "SpaceNeeded(whenEnoughSpace=" + this.whenEnoughSpace + ", whenSavingSpace=" + this.whenSavingSpace + ")";
        }
    }

    /* JADX INFO: compiled from: NotificationStackSizeCalculator.kt */
    final class StackHeight {
        private final float notifsHeight;
        private final float notifsHeightSavingSpace;
        private final float shelfHeightWithSpaceBefore;

        public StackHeight(float f, float f2, float f3) {
            this.notifsHeight = f;
            this.notifsHeightSavingSpace = f2;
            this.shelfHeightWithSpaceBefore = f3;
        }

        public final float component1() {
            return this.notifsHeight;
        }

        public final float component2() {
            return this.notifsHeightSavingSpace;
        }

        public final float component3() {
            return this.shelfHeightWithSpaceBefore;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StackHeight)) {
                return false;
            }
            StackHeight stackHeight = (StackHeight) obj;
            return Float.compare(this.notifsHeight, stackHeight.notifsHeight) == 0 && Float.compare(this.notifsHeightSavingSpace, stackHeight.notifsHeightSavingSpace) == 0 && Float.compare(this.shelfHeightWithSpaceBefore, stackHeight.shelfHeightWithSpaceBefore) == 0;
        }

        public int hashCode() {
            return (((Float.hashCode(this.notifsHeight) * 31) + Float.hashCode(this.notifsHeightSavingSpace)) * 31) + Float.hashCode(this.shelfHeightWithSpaceBefore);
        }

        public String toString() {
            return "StackHeight(notifsHeight=" + this.notifsHeight + ", notifsHeightSavingSpace=" + this.notifsHeightSavingSpace + ", shelfHeightWithSpaceBefore=" + this.shelfHeightWithSpaceBefore + ")";
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1, reason: invalid class name */
    /* JADX INFO: compiled from: NotificationStackSizeCalculator.kt */
    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ float $shelfHeight;
        final /* synthetic */ NotificationStackScrollLayout $stack;
        float F$0;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(NotificationStackScrollLayout notificationStackScrollLayout, float f, Continuation continuation) {
            super(2, continuation);
            this.$stack = notificationStackScrollLayout;
            this.$shelfHeight = f;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = NotificationStackSizeCalculator.this.new AnonymousClass1(this.$stack, this.$shelfHeight, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
            return ((AnonymousClass1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x00d0  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x019c  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x0190 -> B:32:0x0196). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r22) {
            /*
                Method dump skipped, instruction units count: 415
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public NotificationStackSizeCalculator(MediaDataManager mediaDataManager, Resources resources) {
        mediaDataManager.getClass();
        resources.getClass();
        this.mediaDataManager = mediaDataManager;
        this.resources = resources;
        Delegates delegates = Delegates.INSTANCE;
        this.maxKeyguardNotifications$delegate = delegates.notNull();
        this.dividerHeight$delegate = delegates.notNull();
        updateResources();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ExpandableView _get_childrenSequence_$lambda$27(View view) {
        view.getClass();
        return (ExpandableView) view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float calculateGapAndDividerHeight(NotificationStackScrollLayout notificationStackScrollLayout, ExpandableView expandableView, ExpandableView expandableView2, int i) {
        if (i == 0) {
            return 0.0f;
        }
        return notificationStackScrollLayout.calculateGapHeight(expandableView, expandableView2, i) + getDividerHeight();
    }

    private final boolean canShowViewOnLockscreen(ExpandableView expandableView) {
        return (expandableView.hasNoContentHeight() || expandableView.getVisibility() == 8) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final StackHeight computeHeight$lambda$24(Sequence sequence, int i) {
        return (StackHeight) SequencesKt.last(sequence);
    }

    private final Sequence computeHeightPerNotificationLimit(NotificationStackScrollLayout notificationStackScrollLayout, float f) {
        return SequencesKt.sequence(new AnonymousClass1(notificationStackScrollLayout, f, null));
    }

    private final Sequence getChildrenSequence(NotificationStackScrollLayout notificationStackScrollLayout) {
        return SequencesKt.map(ConvenienceExtensionsKt.getChildren(notificationStackScrollLayout), new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackSizeCalculator._get_childrenSequence_$lambda$27((View) obj);
            }
        });
    }

    private final float getDividerHeight() {
        return ((Number) this.dividerHeight$delegate.getValue(this, $$delegatedProperties[1])).floatValue();
    }

    private final int infiniteIfNegative(int i) {
        if (i < 0) {
            return Integer.MAX_VALUE;
        }
        return i;
    }

    private final boolean isShowable(ExpandableView expandableView, boolean z) {
        if (expandableView.getVisibility() == 8 || expandableView.hasNoContentHeight()) {
            return false;
        }
        if (z) {
            return expandableView instanceof ExpandableNotificationRow ? canShowViewOnLockscreen(expandableView) && !((ExpandableNotificationRow) expandableView).isRemoved() : (expandableView instanceof MediaContainerView) && ((MediaContainerView) expandableView).getIntrinsicHeight() != 0;
        }
        return true;
    }

    private final void setDividerHeight(float f) {
        this.dividerHeight$delegate.setValue(this, $$delegatedProperties[1], Float.valueOf(f));
    }

    private final void setMaxKeyguardNotifications(int i) {
        this.maxKeyguardNotifications$delegate.setValue(this, $$delegatedProperties[0], Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Sequence showableChildren(NotificationStackScrollLayout notificationStackScrollLayout) {
        return SequencesKt.filter(getChildrenSequence(notificationStackScrollLayout), new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(NotificationStackSizeCalculator.showableChildren$lambda$28(this.f$0, (ExpandableView) obj));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean showableChildren$lambda$28(NotificationStackSizeCalculator notificationStackSizeCalculator, ExpandableView expandableView) {
        expandableView.getClass();
        return notificationStackSizeCalculator.isShowable(expandableView, notificationStackSizeCalculator.onLockscreen());
    }

    public final float computeHeight(NotificationStackScrollLayout notificationStackScrollLayout, int i, float f) {
        notificationStackScrollLayout.getClass();
        if (NotificationStackSizeCalculatorKt.DEBUG) {
            Log.d("NotifStackSizeCalc", "\n");
        }
        if (NotificationStackSizeCalculatorKt.DEBUG) {
            Log.d("NotifStackSizeCalc", "computeHeight ---");
        }
        final Sequence sequenceComputeHeightPerNotificationLimit = computeHeightPerNotificationLimit(notificationStackScrollLayout, f);
        StackHeight stackHeight = (StackHeight) SequencesKt.elementAtOrElse(sequenceComputeHeightPerNotificationLimit, i, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotificationStackSizeCalculator.computeHeight$lambda$24(sequenceComputeHeightPerNotificationLimit, ((Integer) obj).intValue());
            }
        });
        float fComponent1 = stackHeight.component1();
        float fComponent2 = stackHeight.component2();
        float fComponent3 = stackHeight.component3();
        if (this.saveSpaceOnLockscreen) {
            float f2 = fComponent2 + fComponent3;
            if (NotificationStackSizeCalculatorKt.DEBUG) {
                Log.d("NotifStackSizeCalc", "--- computeHeight(maxNotifs=" + i + ", shelfHeight=" + f + ") -> " + f2 + "=(" + fComponent2 + "+" + fComponent3 + "), | saveSpaceOnLockscreen=" + this.saveSpaceOnLockscreen);
            }
            return f2;
        }
        float f3 = fComponent1 + fComponent3;
        if (NotificationStackSizeCalculatorKt.DEBUG) {
            Log.d("NotifStackSizeCalc", "--- computeHeight(maxNotifs=" + i + ", shelfHeight=" + f + ") -> " + f3 + "=(" + fComponent1 + "+" + fComponent3 + ") | saveSpaceOnLockscreen=" + this.saveSpaceOnLockscreen);
        }
        return f3;
    }

    public final SpaceNeeded getSpaceNeeded(ExpandableView expandableView, int i, ExpandableView expandableView2, NotificationStackScrollLayout notificationStackScrollLayout, boolean z) {
        expandableView.getClass();
        notificationStackScrollLayout.getClass();
        isShowable(expandableView, z);
        float heightWithoutLockscreenConstraints = expandableView.getHeightWithoutLockscreenConstraints();
        float fCalculateGapAndDividerHeight = calculateGapAndDividerHeight(notificationStackScrollLayout, expandableView2, expandableView, i);
        float minHeight = ((!z || ((expandableView instanceof ExpandableNotificationRow) && ((ExpandableNotificationRow) expandableView).getEntry().isStickyAndNotDemoted())) ? heightWithoutLockscreenConstraints : expandableView.getMinHeight(true)) + fCalculateGapAndDividerHeight;
        if (z) {
            heightWithoutLockscreenConstraints = expandableView.getMinHeight(true);
        }
        return new SpaceNeeded(minHeight, heightWithoutLockscreenConstraints + fCalculateGapAndDividerHeight);
    }

    public final boolean onLockscreen() {
        return false;
    }

    public final void updateResources() {
        setMaxKeyguardNotifications(infiniteIfNegative(Flags.notificationMinimalismPrototype() ? SystemProperties.getInt("persist.notification_minimalism_prototype.lock_screen_max_notifs", 1) : this.resources.getInteger(R$integer.keyguard_max_notification_count)));
        this.maxNotificationsExcludesMedia = Flags.notificationMinimalismPrototype();
        setDividerHeight(Math.max(1.0f, this.resources.getDimensionPixelSize(R$dimen.notification_divider_height)));
    }
}
