package com.android.systemui.statusbar.notification.collection.render;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NotifStackController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifStats {
    public static final Companion Companion = new Companion(null);
    private static final NotifStats empty = new NotifStats(0, false, false, false, false);
    private final boolean hasClearableAlertingNotifs;
    private final boolean hasClearableSilentNotifs;
    private final boolean hasNonClearableAlertingNotifs;
    private final boolean hasNonClearableSilentNotifs;
    private final int numActiveNotifs;

    /* JADX INFO: compiled from: NotifStackController.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final NotifStats getEmpty() {
            return NotifStats.empty;
        }
    }

    public NotifStats(int i, boolean z, boolean z2, boolean z3, boolean z4) {
        this.numActiveNotifs = i;
        this.hasNonClearableAlertingNotifs = z;
        this.hasClearableAlertingNotifs = z2;
        this.hasNonClearableSilentNotifs = z3;
        this.hasClearableSilentNotifs = z4;
    }

    public static final NotifStats getEmpty() {
        return Companion.getEmpty();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotifStats)) {
            return false;
        }
        NotifStats notifStats = (NotifStats) obj;
        return this.numActiveNotifs == notifStats.numActiveNotifs && this.hasNonClearableAlertingNotifs == notifStats.hasNonClearableAlertingNotifs && this.hasClearableAlertingNotifs == notifStats.hasClearableAlertingNotifs && this.hasNonClearableSilentNotifs == notifStats.hasNonClearableSilentNotifs && this.hasClearableSilentNotifs == notifStats.hasClearableSilentNotifs;
    }

    public final boolean getHasClearableAlertingNotifs() {
        return this.hasClearableAlertingNotifs;
    }

    public final boolean getHasClearableSilentNotifs() {
        return this.hasClearableSilentNotifs;
    }

    public final boolean getHasNonClearableAlertingNotifs() {
        return this.hasNonClearableAlertingNotifs;
    }

    public final boolean getHasNonClearableSilentNotifs() {
        return this.hasNonClearableSilentNotifs;
    }

    public final int getNumActiveNotifs() {
        return this.numActiveNotifs;
    }

    public int hashCode() {
        return (((((((Integer.hashCode(this.numActiveNotifs) * 31) + Boolean.hashCode(this.hasNonClearableAlertingNotifs)) * 31) + Boolean.hashCode(this.hasClearableAlertingNotifs)) * 31) + Boolean.hashCode(this.hasNonClearableSilentNotifs)) * 31) + Boolean.hashCode(this.hasClearableSilentNotifs);
    }

    public String toString() {
        return "NotifStats(numActiveNotifs=" + this.numActiveNotifs + ", hasNonClearableAlertingNotifs=" + this.hasNonClearableAlertingNotifs + ", hasClearableAlertingNotifs=" + this.hasClearableAlertingNotifs + ", hasNonClearableSilentNotifs=" + this.hasNonClearableSilentNotifs + ", hasClearableSilentNotifs=" + this.hasClearableSilentNotifs + ")";
    }
}
