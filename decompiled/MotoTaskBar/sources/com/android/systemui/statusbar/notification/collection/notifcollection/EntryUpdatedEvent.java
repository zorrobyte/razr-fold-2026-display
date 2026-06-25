package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotifEvent.kt */
/* JADX INFO: loaded from: classes.dex */
public final class EntryUpdatedEvent extends NotifEvent {
    private final NotificationEntry entry;
    private final boolean fromSystem;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EntryUpdatedEvent(NotificationEntry notificationEntry, boolean z) {
        super(z ? "onEntryUpdated" : "onEntryUpdated fromSystem=true", null);
        notificationEntry.getClass();
        this.entry = notificationEntry;
        this.fromSystem = z;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.getClass();
        notifCollectionListener.onEntryUpdated(this.entry, this.fromSystem);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EntryUpdatedEvent)) {
            return false;
        }
        EntryUpdatedEvent entryUpdatedEvent = (EntryUpdatedEvent) obj;
        return Intrinsics.areEqual(this.entry, entryUpdatedEvent.entry) && this.fromSystem == entryUpdatedEvent.fromSystem;
    }

    public int hashCode() {
        return (this.entry.hashCode() * 31) + Boolean.hashCode(this.fromSystem);
    }

    public String toString() {
        return "EntryUpdatedEvent(entry=" + this.entry + ", fromSystem=" + this.fromSystem + ")";
    }
}
