package com.android.systemui.statusbar.notification.collection.provider;

import android.app.Notification;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public class HighPriorityProvider {
    private final GroupMembershipManager mGroupMembershipManager;
    private final PeopleNotificationIdentifier mPeopleNotificationIdentifier;

    public static /* synthetic */ boolean $r8$lambda$EBk5QnqAHYbYLf6TovOrF50NEjM(NotificationEntry notificationEntry) {
        return notificationEntry.getRanking().getImportance() >= 3;
    }

    public HighPriorityProvider(PeopleNotificationIdentifier peopleNotificationIdentifier, GroupMembershipManager groupMembershipManager) {
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
        this.mGroupMembershipManager = groupMembershipManager;
    }

    private boolean hasHighPriorityCharacteristics(NotificationEntry notificationEntry) {
        if (hasUserSetImportance(notificationEntry)) {
            return false;
        }
        return notificationEntry.getSbn().getNotification().isMediaNotification() || isPeopleNotification(notificationEntry) || isMessagingStyle(notificationEntry);
    }

    private boolean hasHighPriorityChild(ListEntry listEntry, boolean z) {
        List<NotificationEntry> children;
        if ((!(listEntry instanceof NotificationEntry) || this.mGroupMembershipManager.isGroupSummary((NotificationEntry) listEntry)) && (children = this.mGroupMembershipManager.getChildren(listEntry)) != null) {
            for (NotificationEntry notificationEntry : children) {
                if (notificationEntry != listEntry && isHighPriority(notificationEntry, z)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasUserSetImportance(NotificationEntry notificationEntry) {
        return notificationEntry.getRanking().getChannel() != null && notificationEntry.getRanking().getChannel().hasUserSetImportance();
    }

    private boolean isHighPriority(ListEntry listEntry, boolean z) {
        NotificationEntry representativeEntry;
        if (listEntry == null || (representativeEntry = listEntry.getRepresentativeEntry()) == null) {
            return false;
        }
        if (representativeEntry.getRanking().getImportance() < 3) {
            return (z && hasHighPriorityCharacteristics(representativeEntry)) || hasHighPriorityChild(listEntry, z);
        }
        return true;
    }

    private boolean isMessagingStyle(NotificationEntry notificationEntry) {
        return notificationEntry.getSbn().getNotification().isStyle(Notification.MessagingStyle.class);
    }

    private boolean isNotificationEntryWithAtLeastOneImportantChild(ListEntry listEntry) {
        if (listEntry instanceof GroupEntry) {
            return ((GroupEntry) listEntry).getChildren().stream().anyMatch(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return HighPriorityProvider.$r8$lambda$EBk5QnqAHYbYLf6TovOrF50NEjM((NotificationEntry) obj);
                }
            });
        }
        return false;
    }

    private boolean isPeopleNotification(NotificationEntry notificationEntry) {
        return this.mPeopleNotificationIdentifier.getPeopleNotificationType(notificationEntry) != 0;
    }

    public boolean isHighPriority(ListEntry listEntry) {
        return isHighPriority(listEntry, true);
    }

    public boolean isHighPriorityConversation(ListEntry listEntry) {
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        if (representativeEntry == null || !isPeopleNotification(representativeEntry)) {
            return false;
        }
        if (representativeEntry.getRanking().getImportance() >= 3) {
            return true;
        }
        return isNotificationEntryWithAtLeastOneImportantChild(listEntry);
    }
}
