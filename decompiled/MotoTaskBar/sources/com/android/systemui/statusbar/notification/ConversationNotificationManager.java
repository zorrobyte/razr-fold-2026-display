package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.view.View;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Function;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ConversationNotifications.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ConversationNotificationManager {
    public static final Companion Companion = new Companion(null);
    private final Context context;
    private final Handler mainHandler;
    private final CommonNotifCollection notifCollection;
    private boolean notifPanelCollapsed;
    private final ConcurrentHashMap states;

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.ConversationNotificationManager$2, reason: invalid class name */
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
            return new FunctionReferenceImpl(1, ConversationNotificationManager.this, ConversationNotificationManager.class, "onEntryViewBound", "onEntryViewBound(Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;)V", 0);
        }

        public final int hashCode() {
            return getFunctionDelegate().hashCode();
        }

        @Override // com.android.systemui.statusbar.notification.collection.inflation.BindEventManager.Listener
        public final void onViewBound(NotificationEntry notificationEntry) {
            notificationEntry.getClass();
            ConversationNotificationManager.this.onEntryViewBound(notificationEntry);
        }
    }

    /* JADX INFO: compiled from: ConversationNotifications.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: ConversationNotifications.kt */
    final class ConversationState {
        private final Notification notification;
        private final int unreadCount;

        public ConversationState(int i, Notification notification) {
            notification.getClass();
            this.unreadCount = i;
            this.notification = notification;
        }

        public static /* synthetic */ ConversationState copy$default(ConversationState conversationState, int i, Notification notification, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = conversationState.unreadCount;
            }
            if ((i2 & 2) != 0) {
                notification = conversationState.notification;
            }
            return conversationState.copy(i, notification);
        }

        public final ConversationState copy(int i, Notification notification) {
            notification.getClass();
            return new ConversationState(i, notification);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConversationState)) {
                return false;
            }
            ConversationState conversationState = (ConversationState) obj;
            return this.unreadCount == conversationState.unreadCount && Intrinsics.areEqual(this.notification, conversationState.notification);
        }

        public final Notification getNotification() {
            return this.notification;
        }

        public final int getUnreadCount() {
            return this.unreadCount;
        }

        public int hashCode() {
            return (Integer.hashCode(this.unreadCount) * 31) + this.notification.hashCode();
        }

        public String toString() {
            return "ConversationState(unreadCount=" + this.unreadCount + ", notification=" + this.notification + ")";
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.ConversationNotificationManager$updateNotificationRanking$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ConversationNotifications.kt */
    final /* synthetic */ class C01161 extends FunctionReferenceImpl implements Function1 {
        public static final C01161 INSTANCE = new C01161();

        C01161() {
            super(1, Intrinsics.Kotlin.class, "getLayouts", "updateNotificationRanking$getLayouts(Lcom/android/systemui/statusbar/notification/row/NotificationContentView;)Lkotlin/sequences/Sequence;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Sequence invoke(NotificationContentView notificationContentView) {
            notificationContentView.getClass();
            return ConversationNotificationManager.updateNotificationRanking$getLayouts(notificationContentView);
        }
    }

    public ConversationNotificationManager(BindEventManager bindEventManager, Context context, CommonNotifCollection commonNotifCollection, Handler handler) {
        bindEventManager.getClass();
        context.getClass();
        commonNotifCollection.getClass();
        handler.getClass();
        this.context = context;
        this.notifCollection = commonNotifCollection;
        this.mainHandler = handler;
        this.states = new ConcurrentHashMap();
        this.notifPanelCollapsed = true;
        commonNotifCollection.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                notificationEntry.getClass();
                ConversationNotificationManager.this.removeTrackedEntry(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
                rankingMap.getClass();
                ConversationNotificationManager.this.updateNotificationRanking(rankingMap);
            }
        });
        bindEventManager.addListener(new AnonymousClass2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ConversationState getUnreadCount$lambda$5(NotificationEntry notificationEntry, ConversationNotificationManager conversationNotificationManager, Notification.Builder builder, String str, ConversationState conversationState) {
        str.getClass();
        int unreadCount = 1;
        if (conversationState != null) {
            unreadCount = conversationNotificationManager.shouldIncrementUnread(conversationState, builder) ? conversationState.getUnreadCount() + 1 : conversationState.getUnreadCount();
        }
        Notification notification = notificationEntry.getSbn().getNotification();
        notification.getClass();
        return new ConversationState(unreadCount, notification);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onEntryViewBound$updateCount(ConversationNotificationManager conversationNotificationManager, NotificationEntry notificationEntry, boolean z) {
        if (z) {
            if (!conversationNotificationManager.notifPanelCollapsed || notificationEntry.isPinnedAndExpanded()) {
                String key = notificationEntry.getKey();
                key.getClass();
                conversationNotificationManager.resetCount(key);
                ExpandableNotificationRow row = notificationEntry.getRow();
                if (row != null) {
                    conversationNotificationManager.resetBadgeUi(row);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeTrackedEntry(NotificationEntry notificationEntry) {
        this.states.remove(notificationEntry.getKey());
    }

    private final void resetBadgeUi(ExpandableNotificationRow expandableNotificationRow) {
        Sequence sequenceEmptySequence;
        NotificationContentView[] layouts = expandableNotificationRow.getLayouts();
        if (layouts == null || (sequenceEmptySequence = ArraysKt.asSequence(layouts)) == null) {
            sequenceEmptySequence = SequencesKt.emptySequence();
        }
        Iterator it = SequencesKt.mapNotNull(SequencesKt.flatMap(sequenceEmptySequence, new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ConversationNotificationManager.resetBadgeUi$lambda$12((NotificationContentView) obj);
            }
        }), new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ConversationNotificationManager.resetBadgeUi$lambda$13((View) obj);
            }
        }).iterator();
        while (it.hasNext()) {
            ((ConversationLayout) it.next()).setUnreadCount(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence resetBadgeUi$lambda$12(NotificationContentView notificationContentView) {
        View[] allViews = notificationContentView.getAllViews();
        allViews.getClass();
        return ArraysKt.asSequence(allViews);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ConversationLayout resetBadgeUi$lambda$13(View view) {
        if (view instanceof ConversationLayout) {
            return (ConversationLayout) view;
        }
        return null;
    }

    private final void resetCount(String str) {
        this.states.compute(str, new ConversationNotificationManager$sam$java_util_function_BiFunction$0(new Function2() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ConversationNotificationManager.resetCount$lambda$11((String) obj, (ConversationNotificationManager.ConversationState) obj2);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ConversationState resetCount$lambda$11(String str, ConversationState conversationState) {
        str.getClass();
        if (conversationState != null) {
            return ConversationState.copy$default(conversationState, 0, null, 2, null);
        }
        return null;
    }

    private final boolean shouldIncrementUnread(ConversationState conversationState, Notification.Builder builder) {
        if ((conversationState.getNotification().flags & 8) != 0) {
            return false;
        }
        Notification.Builder builderRecoverBuilder = Notification.Builder.recoverBuilder(this.context, conversationState.getNotification());
        builderRecoverBuilder.getClass();
        return Notification.areStyledNotificationsVisiblyDifferent(builderRecoverBuilder, builder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateNotificationRanking(NotificationListenerService.RankingMap rankingMap) {
        NotificationContentView[] layouts;
        Sequence sequenceAsSequence;
        Sequence sequenceFlatMap;
        Sequence sequenceMapNotNull;
        Sequence<ConversationLayout> sequenceFilterNot;
        NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
        Set setKeySet = this.states.keySet();
        setKeySet.getClass();
        for (NotificationEntry notificationEntry : SequencesKt.mapNotNull(CollectionsKt.asSequence(setKeySet), new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ConversationNotificationManager.updateNotificationRanking$lambda$0(this.f$0, (String) obj);
            }
        })) {
            if (rankingMap.getRanking(notificationEntry.getSbn().getKey(), ranking) && ranking.isConversation()) {
                final boolean zIsImportantConversation = ranking.getChannel().isImportantConversation();
                ExpandableNotificationRow row = notificationEntry.getRow();
                if (row != null && (layouts = row.getLayouts()) != null && (sequenceAsSequence = ArraysKt.asSequence(layouts)) != null && (sequenceFlatMap = SequencesKt.flatMap(sequenceAsSequence, C01161.INSTANCE)) != null && (sequenceMapNotNull = SequencesKt.mapNotNull(sequenceFlatMap, new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return ConversationNotificationManager.updateNotificationRanking$lambda$1((View) obj);
                    }
                })) != null && (sequenceFilterNot = SequencesKt.filterNot(sequenceMapNotNull, new Function1() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda6
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Boolean.valueOf(ConversationNotificationManager.updateNotificationRanking$lambda$2(zIsImportantConversation, (ConversationLayout) obj));
                    }
                })) != null) {
                    for (final ConversationLayout conversationLayout : sequenceFilterNot) {
                        if (zIsImportantConversation && notificationEntry.isMarkedForUserTriggeredMovement()) {
                            this.mainHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$updateNotificationRanking$4$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    conversationLayout.setIsImportantConversation(zIsImportantConversation, true);
                                }
                            }, 960L);
                        } else {
                            conversationLayout.setIsImportantConversation(zIsImportantConversation, false);
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence updateNotificationRanking$getLayouts(NotificationContentView notificationContentView) {
        return SequencesKt.sequenceOf(notificationContentView.getContractedChild(), notificationContentView.getExpandedChild(), notificationContentView.getHeadsUpChild());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final NotificationEntry updateNotificationRanking$lambda$0(ConversationNotificationManager conversationNotificationManager, String str) {
        return conversationNotificationManager.notifCollection.getEntry(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ConversationLayout updateNotificationRanking$lambda$1(View view) {
        if (view instanceof ConversationLayout) {
            return (ConversationLayout) view;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean updateNotificationRanking$lambda$2(boolean z, ConversationLayout conversationLayout) {
        conversationLayout.getClass();
        return conversationLayout.isImportantConversation() == z;
    }

    public final int getUnreadCount(final NotificationEntry notificationEntry, final Notification.Builder builder) {
        notificationEntry.getClass();
        builder.getClass();
        Object objCompute = this.states.compute(notificationEntry.getKey(), new ConversationNotificationManager$sam$java_util_function_BiFunction$0(new Function2() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ConversationNotificationManager.getUnreadCount$lambda$5(notificationEntry, this, builder, (String) obj, (ConversationNotificationManager.ConversationState) obj2);
            }
        }));
        objCompute.getClass();
        return ((ConversationState) objCompute).getUnreadCount();
    }

    public final void onEntryViewBound(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        if (notificationEntry.getRanking().isConversation()) {
            ExpandableNotificationRow row = notificationEntry.getRow();
            if (row != null) {
                row.setOnExpansionChangedListener(new ExpandableNotificationRow.OnExpansionChangedListener() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager.onEntryViewBound.1
                    @Override // com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.OnExpansionChangedListener
                    public final void onExpansionChanged(final boolean z) {
                        ExpandableNotificationRow row2 = notificationEntry.getRow();
                        if (row2 == null || !row2.isShown() || !z) {
                            ConversationNotificationManager.onEntryViewBound$updateCount(this, notificationEntry, z);
                            return;
                        }
                        ExpandableNotificationRow row3 = notificationEntry.getRow();
                        final ConversationNotificationManager conversationNotificationManager = this;
                        final NotificationEntry notificationEntry2 = notificationEntry;
                        row3.performOnIntrinsicHeightReached(new Runnable() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager.onEntryViewBound.1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ConversationNotificationManager.onEntryViewBound$updateCount(conversationNotificationManager, notificationEntry2, z);
                            }
                        });
                    }
                });
            }
            ExpandableNotificationRow row2 = notificationEntry.getRow();
            boolean z = false;
            if (row2 != null && row2.isExpanded()) {
                z = true;
            }
            onEntryViewBound$updateCount(this, notificationEntry, z);
        }
    }
}
