package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.SortBySectionTimeFlag;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.icon.ConversationIconManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConversationCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ConversationCoordinator implements Coordinator {
    private static final Companion Companion = new Companion(null);
    private final NodeController conversationHeaderNodeController;
    private final ConversationIconManager conversationIconManager;
    private final HighPriorityProvider highPriorityProvider;
    private final NotifComparator notifComparator;
    private final ConversationCoordinator$notificationPromoter$1 notificationPromoter;
    private final OnBeforeRenderListListener onBeforeRenderListListener;
    private final NotifSectioner peopleAlertingSectioner;
    private final NodeController peopleHeaderController;
    private final PeopleNotificationIdentifier peopleNotificationIdentifier;
    private final NotifSectioner peopleSilentSectioner;
    private final Map promotedEntriesToSummaryOfSameChannel;

    /* JADX INFO: compiled from: ConversationCoordinator.kt */
    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notificationPromoter$1] */
    public ConversationCoordinator(PeopleNotificationIdentifier peopleNotificationIdentifier, ConversationIconManager conversationIconManager, HighPriorityProvider highPriorityProvider, NodeController nodeController) {
        peopleNotificationIdentifier.getClass();
        conversationIconManager.getClass();
        highPriorityProvider.getClass();
        nodeController.getClass();
        this.peopleNotificationIdentifier = peopleNotificationIdentifier;
        this.conversationIconManager = conversationIconManager;
        this.highPriorityProvider = highPriorityProvider;
        this.peopleHeaderController = nodeController;
        this.promotedEntriesToSummaryOfSameChannel = new LinkedHashMap();
        this.onBeforeRenderListListener = new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$onBeforeRenderListListener$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List list) {
                Map map = this.this$0.promotedEntriesToSummaryOfSameChannel;
                ArrayList arrayList = new ArrayList();
                for (Map.Entry entry : map.entrySet()) {
                    NotificationEntry notificationEntry = (NotificationEntry) entry.getKey();
                    NotificationEntry notificationEntry2 = (NotificationEntry) entry.getValue();
                    GroupEntry parent = notificationEntry2.getParent();
                    String key = null;
                    if (parent != null && !Intrinsics.areEqual(parent, notificationEntry.getParent()) && parent.getParent() != null && Intrinsics.areEqual(parent.getSummary(), notificationEntry2)) {
                        List children = parent.getChildren();
                        children.getClass();
                        if (children.isEmpty()) {
                            key = notificationEntry2.getKey();
                        } else {
                            Iterator it = children.iterator();
                            while (it.hasNext()) {
                                if (Intrinsics.areEqual(((NotificationEntry) it.next()).getChannel(), notificationEntry2.getChannel())) {
                                    break;
                                }
                            }
                            key = notificationEntry2.getKey();
                        }
                    }
                    if (key != null) {
                        arrayList.add(key);
                    }
                }
                this.this$0.conversationIconManager.setUnimportantConversations(arrayList);
                this.this$0.promotedEntriesToSummaryOfSameChannel.clear();
            }
        };
        this.notificationPromoter = new NotifPromoter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notificationPromoter$1
            {
                super("ConversationCoordinator");
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter
            public boolean shouldPromoteToTopLevel(NotificationEntry notificationEntry) {
                notificationEntry.getClass();
                NotificationChannel channel = notificationEntry.getChannel();
                boolean z = false;
                if (channel != null && channel.isImportantConversation()) {
                    z = true;
                }
                if (z) {
                    GroupEntry parent = notificationEntry.getParent();
                    NotificationEntry summary = parent != null ? parent.getSummary() : null;
                    if (summary != null && Intrinsics.areEqual(notificationEntry.getChannel(), summary.getChannel())) {
                        this.this$0.promotedEntriesToSummaryOfSameChannel.put(notificationEntry, summary);
                    }
                }
                return z;
            }
        };
        this.peopleAlertingSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$peopleAlertingSectioner$1
            {
                super("People(alerting)", 4);
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NotifComparator getComparator() {
                return null;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NodeController getHeaderNodeController() {
                return this.this$0.conversationHeaderNodeController;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public boolean isInSection(ListEntry listEntry) {
                listEntry.getClass();
                return this.this$0.highPriorityProvider.isHighPriorityConversation(listEntry) || this.this$0.isConversation(listEntry);
            }
        };
        this.peopleSilentSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$peopleSilentSectioner$1
            {
                super("People(silent)", 4);
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NotifComparator getComparator() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                SortBySectionTimeFlag sortBySectionTimeFlag = SortBySectionTimeFlag.INSTANCE;
                throw new IllegalStateException(("Legacy code path not supported when " + ((Object) "android.app.sort_section_by_time") + " is enabled.").toString());
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NodeController getHeaderNodeController() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                SortBySectionTimeFlag sortBySectionTimeFlag = SortBySectionTimeFlag.INSTANCE;
                throw new IllegalStateException(("Legacy code path not supported when " + ((Object) "android.app.sort_section_by_time") + " is enabled.").toString());
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public boolean isInSection(ListEntry listEntry) {
                listEntry.getClass();
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                SortBySectionTimeFlag sortBySectionTimeFlag = SortBySectionTimeFlag.INSTANCE;
                throw new IllegalStateException(("Legacy code path not supported when " + ((Object) "android.app.sort_section_by_time") + " is enabled.").toString());
            }
        };
        this.notifComparator = new NotifComparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator$notifComparator$1
            {
                super("People");
            }

            @Override // java.util.Comparator
            public int compare(ListEntry listEntry, ListEntry listEntry2) {
                listEntry.getClass();
                listEntry2.getClass();
                return Intrinsics.compare(this.this$0.getPeopleType(listEntry2), this.this$0.getPeopleType(listEntry));
            }
        };
        this.conversationHeaderNodeController = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getPeopleType(ListEntry listEntry) {
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        if (representativeEntry != null) {
            return this.peopleNotificationIdentifier.getPeopleNotificationType(representativeEntry);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isConversation(ListEntry listEntry) {
        return getPeopleType(listEntry) != 0;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        notifPipeline.addPromoter(this.notificationPromoter);
        notifPipeline.addOnBeforeRenderListListener(this.onBeforeRenderListListener);
    }

    public final NotifSectioner getPeopleAlertingSectioner() {
        return this.peopleAlertingSectioner;
    }
}
