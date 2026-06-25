package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotifEvent.kt */
/* JADX INFO: loaded from: classes.dex */
public final class EntryAddedEvent extends NotifEvent {
    private final NotificationEntry entry;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EntryAddedEvent(NotificationEntry notificationEntry) {
        super("onEntryAdded", null);
        notificationEntry.getClass();
        this.entry = notificationEntry;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.getClass();
        notifCollectionListener.onEntryAdded(this.entry);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof EntryAddedEvent) && Intrinsics.areEqual(this.entry, ((EntryAddedEvent) obj).entry);
    }

    public int hashCode() {
        return this.entry.hashCode();
    }

    public String toString() {
        return "EntryAddedEvent(entry=" + this.entry + ")";
    }
}
