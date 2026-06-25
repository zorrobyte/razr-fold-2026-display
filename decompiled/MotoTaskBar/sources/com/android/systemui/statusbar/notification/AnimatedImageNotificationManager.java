package com.android.systemui.statusbar.notification;

import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLayout;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.ArrayList;
import kotlin.Function;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ConversationNotifications.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AnimatedImageNotificationManager {
    private final BindEventManager bindEventManager;
    private final HeadsUpManager headsUpManager;
    private boolean isStatusBarExpanded;
    private final CommonNotifCollection notifCollection;

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$bind$2, reason: invalid class name */
    /* JADX INFO: compiled from: ConversationNotifications.kt */
    final /* synthetic */ class AnonymousClass2 implements BindEventManager.Listener, FunctionAdapter {
        AnonymousClass2() {
        }

        public final boolean equals(Object obj) {
            if ((obj instanceof BindEventManager.Listener) && (obj instanceof FunctionAdapter)) {
                return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
            }
            return false;
        }

        @Override // kotlin.jvm.internal.FunctionAdapter
        public final Function getFunctionDelegate() {
            return new AdaptedFunctionReference(1, AnimatedImageNotificationManager.this, AnimatedImageNotificationManager.class, "updateAnimatedImageDrawables", "updateAnimatedImageDrawables(Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;)Lkotlin/Unit;", 8);
        }

        public final int hashCode() {
            return getFunctionDelegate().hashCode();
        }

        @Override // com.android.systemui.statusbar.notification.collection.inflation.BindEventManager.Listener
        public final void onViewBound(NotificationEntry notificationEntry) {
            notificationEntry.getClass();
            AnimatedImageNotificationManager.this.updateAnimatedImageDrawables(notificationEntry);
        }
    }

    public AnimatedImageNotificationManager(CommonNotifCollection commonNotifCollection, BindEventManager bindEventManager, HeadsUpManager headsUpManager) {
        commonNotifCollection.getClass();
        bindEventManager.getClass();
        headsUpManager.getClass();
        this.notifCollection = commonNotifCollection;
        this.bindEventManager = bindEventManager;
        this.headsUpManager = headsUpManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Unit updateAnimatedImageDrawables(NotificationEntry notificationEntry) {
        ExpandableNotificationRow row = notificationEntry.getRow();
        if (row == null) {
            return null;
        }
        updateAnimatedImageDrawables(row, row.isHeadsUp() || this.isStatusBarExpanded);
        return Unit.INSTANCE;
    }

    private final void updateAnimatedImageDrawables(ExpandableNotificationRow expandableNotificationRow, boolean z) {
        Sequence sequenceEmptySequence;
        NotificationContentView[] layouts = expandableNotificationRow.getLayouts();
        if (layouts == null || (sequenceEmptySequence = ArraysKt.asSequence(layouts)) == null) {
            sequenceEmptySequence = SequencesKt.emptySequence();
        }
        for (AnimatedImageDrawable animatedImageDrawable : SequencesKt.mapNotNull(SequencesKt.flatMap(SequencesKt.flatMap(SequencesKt.flatMap(sequenceEmptySequence, new Function1() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AnimatedImageNotificationManager.updateAnimatedImageDrawables$lambda$1((NotificationContentView) obj);
            }
        }), new Function1() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AnimatedImageNotificationManager.updateAnimatedImageDrawables$lambda$2((View) obj);
            }
        }), new Function1() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AnimatedImageNotificationManager.updateAnimatedImageDrawables$lambda$3((MessagingGroup) obj);
            }
        }), new Function1() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AnimatedImageNotificationManager.updateAnimatedImageDrawables$lambda$5((View) obj);
            }
        })) {
            if (z) {
                animatedImageDrawable.start();
            } else {
                animatedImageDrawable.stop();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence updateAnimatedImageDrawables$lambda$1(NotificationContentView notificationContentView) {
        View[] allViews = notificationContentView.getAllViews();
        allViews.getClass();
        return ArraysKt.asSequence(allViews);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence updateAnimatedImageDrawables$lambda$2(View view) {
        ArrayList messagingGroups;
        ArrayList messagingGroups2;
        Sequence sequenceAsSequence;
        ConversationLayout conversationLayout = view instanceof ConversationLayout ? (ConversationLayout) view : null;
        if (conversationLayout != null && (messagingGroups2 = conversationLayout.getMessagingGroups()) != null && (sequenceAsSequence = CollectionsKt.asSequence(messagingGroups2)) != null) {
            return sequenceAsSequence;
        }
        MessagingLayout messagingLayout = view instanceof MessagingLayout ? (MessagingLayout) view : null;
        return (messagingLayout == null || (messagingGroups = messagingLayout.getMessagingGroups()) == null) ? SequencesKt.emptySequence() : CollectionsKt.asSequence(messagingGroups);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence updateAnimatedImageDrawables$lambda$3(MessagingGroup messagingGroup) {
        MessagingLinearLayout messageContainer = messagingGroup.getMessageContainer();
        messageContainer.getClass();
        return ConvenienceExtensionsKt.getChildren(messageContainer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AnimatedImageDrawable updateAnimatedImageDrawables$lambda$5(View view) {
        MessagingImageMessage messagingImageMessage = view instanceof MessagingImageMessage ? (MessagingImageMessage) view : null;
        if (messagingImageMessage != null) {
            Drawable drawable = messagingImageMessage.getDrawable();
            if (drawable instanceof AnimatedImageDrawable) {
                return (AnimatedImageDrawable) drawable;
            }
        }
        return null;
    }

    public final void bind() {
        this.headsUpManager.addListener(new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager.bind.1
            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
                notificationEntry.getClass();
                AnimatedImageNotificationManager.this.updateAnimatedImageDrawables(notificationEntry);
            }
        });
        this.bindEventManager.addListener(new AnonymousClass2());
    }
}
