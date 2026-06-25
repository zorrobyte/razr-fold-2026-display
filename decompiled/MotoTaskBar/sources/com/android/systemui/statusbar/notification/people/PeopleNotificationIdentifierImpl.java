package com.android.systemui.statusbar.notification.people;

import android.app.NotificationChannel;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: PeopleNotificationIdentifier.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PeopleNotificationIdentifierImpl implements PeopleNotificationIdentifier {
    private final GroupMembershipManager groupManager;
    private final NotificationPersonExtractor personExtractor;

    public PeopleNotificationIdentifierImpl(NotificationPersonExtractor notificationPersonExtractor, GroupMembershipManager groupMembershipManager) {
        notificationPersonExtractor.getClass();
        groupMembershipManager.getClass();
        this.personExtractor = notificationPersonExtractor;
        this.groupManager = groupMembershipManager;
    }

    private final int extractPersonTypeInfo(StatusBarNotification statusBarNotification) {
        return this.personExtractor.isPersonNotification(statusBarNotification) ? 1 : 0;
    }

    private final int getPeopleTypeOfSummary(NotificationEntry notificationEntry) {
        Sequence sequenceAsSequence;
        Sequence map;
        int iUpperBound = 0;
        if (!this.groupManager.isGroupSummary(notificationEntry)) {
            return 0;
        }
        List children = this.groupManager.getChildren(notificationEntry);
        if (children != null && (sequenceAsSequence = CollectionsKt.asSequence(children)) != null && (map = SequencesKt.map(sequenceAsSequence, new Function1() { // from class: com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Integer.valueOf(PeopleNotificationIdentifierImpl.getPeopleTypeOfSummary$lambda$0(this.f$0, (NotificationEntry) obj));
            }
        })) != null) {
            Iterator it = map.iterator();
            while (it.hasNext() && (iUpperBound = upperBound(iUpperBound, ((Number) it.next()).intValue())) != 3) {
            }
        }
        return iUpperBound;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int getPeopleTypeOfSummary$lambda$0(PeopleNotificationIdentifierImpl peopleNotificationIdentifierImpl, NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return peopleNotificationIdentifierImpl.getPeopleNotificationType(notificationEntry);
    }

    private final int getPersonTypeInfo(NotificationListenerService.Ranking ranking) {
        if (!ranking.isConversation()) {
            return 0;
        }
        if (ranking.getConversationShortcutInfo() == null) {
            return 1;
        }
        NotificationChannel channel = ranking.getChannel();
        return (channel == null || !channel.isImportantConversation()) ? 2 : 3;
    }

    private final int upperBound(int i, int i2) {
        return Math.max(i, i2);
    }

    @Override // com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier
    public int getPeopleNotificationType(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        NotificationListenerService.Ranking ranking = notificationEntry.getRanking();
        ranking.getClass();
        int personTypeInfo = getPersonTypeInfo(ranking);
        if (personTypeInfo == 3) {
            return 3;
        }
        StatusBarNotification sbn = notificationEntry.getSbn();
        sbn.getClass();
        int iUpperBound = upperBound(personTypeInfo, extractPersonTypeInfo(sbn));
        if (iUpperBound == 3) {
            return 3;
        }
        return upperBound(iUpperBound, getPeopleTypeOfSummary(notificationEntry));
    }
}
