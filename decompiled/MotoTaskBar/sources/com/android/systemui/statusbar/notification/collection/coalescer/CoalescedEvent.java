package com.android.systemui.statusbar.notification.collection.coalescer;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CoalescedEvent.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CoalescedEvent {
    private EventBatch batch;
    private final String key;
    private int position;
    private NotificationListenerService.Ranking ranking;
    private StatusBarNotification sbn;

    public CoalescedEvent(String str, int i, StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking, EventBatch eventBatch) {
        str.getClass();
        statusBarNotification.getClass();
        ranking.getClass();
        this.key = str;
        this.position = i;
        this.sbn = statusBarNotification;
        this.ranking = ranking;
        this.batch = eventBatch;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CoalescedEvent)) {
            return false;
        }
        CoalescedEvent coalescedEvent = (CoalescedEvent) obj;
        return Intrinsics.areEqual(this.key, coalescedEvent.key) && this.position == coalescedEvent.position && Intrinsics.areEqual(this.sbn, coalescedEvent.sbn) && Intrinsics.areEqual(this.ranking, coalescedEvent.ranking) && Intrinsics.areEqual(this.batch, coalescedEvent.batch);
    }

    public final EventBatch getBatch() {
        return this.batch;
    }

    public final String getKey() {
        return this.key;
    }

    public final int getPosition() {
        return this.position;
    }

    public final NotificationListenerService.Ranking getRanking() {
        return this.ranking;
    }

    public final StatusBarNotification getSbn() {
        return this.sbn;
    }

    public int hashCode() {
        int iHashCode = ((((((this.key.hashCode() * 31) + Integer.hashCode(this.position)) * 31) + this.sbn.hashCode()) * 31) + this.ranking.hashCode()) * 31;
        EventBatch eventBatch = this.batch;
        return iHashCode + (eventBatch == null ? 0 : eventBatch.hashCode());
    }

    public final void setBatch(EventBatch eventBatch) {
        this.batch = eventBatch;
    }

    public final void setRanking(NotificationListenerService.Ranking ranking) {
        ranking.getClass();
        this.ranking = ranking;
    }

    public String toString() {
        return "CoalescedEvent(key=" + this.key + ")";
    }
}
