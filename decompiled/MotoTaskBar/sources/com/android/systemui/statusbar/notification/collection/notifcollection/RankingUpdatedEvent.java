package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.service.notification.NotificationListenerService;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotifEvent.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RankingUpdatedEvent extends NotifEvent {
    private final NotificationListenerService.RankingMap rankingMap;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RankingUpdatedEvent(NotificationListenerService.RankingMap rankingMap) {
        super("onRankingUpdate", null);
        rankingMap.getClass();
        this.rankingMap = rankingMap;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.getClass();
        notifCollectionListener.onRankingUpdate(this.rankingMap);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof RankingUpdatedEvent) && Intrinsics.areEqual(this.rankingMap, ((RankingUpdatedEvent) obj).rankingMap);
    }

    public int hashCode() {
        return this.rankingMap.hashCode();
    }

    public String toString() {
        return "RankingUpdatedEvent(rankingMap=" + this.rankingMap + ")";
    }
}
