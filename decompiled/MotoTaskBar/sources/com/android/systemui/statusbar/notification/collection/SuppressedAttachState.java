package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SuppressedAttachState.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SuppressedAttachState {
    public static final Companion Companion = new Companion(null);
    private GroupEntry parent;
    private NotifSection section;
    private boolean wasPruneSuppressed;

    /* JADX INFO: compiled from: SuppressedAttachState.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final SuppressedAttachState create() {
            return new SuppressedAttachState(null, 0 == true ? 1 : 0, false, 0 == true ? 1 : 0);
        }
    }

    private SuppressedAttachState(NotifSection notifSection, GroupEntry groupEntry, boolean z) {
        this.section = notifSection;
        this.parent = groupEntry;
        this.wasPruneSuppressed = z;
    }

    public /* synthetic */ SuppressedAttachState(NotifSection notifSection, GroupEntry groupEntry, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(notifSection, groupEntry, z);
    }

    public final void clone(SuppressedAttachState suppressedAttachState) {
        suppressedAttachState.getClass();
        this.parent = suppressedAttachState.parent;
        this.section = suppressedAttachState.section;
        this.wasPruneSuppressed = suppressedAttachState.wasPruneSuppressed;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SuppressedAttachState)) {
            return false;
        }
        SuppressedAttachState suppressedAttachState = (SuppressedAttachState) obj;
        return Intrinsics.areEqual(this.section, suppressedAttachState.section) && Intrinsics.areEqual(this.parent, suppressedAttachState.parent) && this.wasPruneSuppressed == suppressedAttachState.wasPruneSuppressed;
    }

    public final GroupEntry getParent() {
        return this.parent;
    }

    public final NotifSection getSection() {
        return this.section;
    }

    public final boolean getWasPruneSuppressed() {
        return this.wasPruneSuppressed;
    }

    public int hashCode() {
        NotifSection notifSection = this.section;
        int iHashCode = (notifSection == null ? 0 : notifSection.hashCode()) * 31;
        GroupEntry groupEntry = this.parent;
        return ((iHashCode + (groupEntry != null ? groupEntry.hashCode() : 0)) * 31) + Boolean.hashCode(this.wasPruneSuppressed);
    }

    public final void reset() {
        this.parent = null;
        this.section = null;
        this.wasPruneSuppressed = false;
    }

    public final void setParent(GroupEntry groupEntry) {
        this.parent = groupEntry;
    }

    public final void setSection(NotifSection notifSection) {
        this.section = notifSection;
    }

    public final void setWasPruneSuppressed(boolean z) {
        this.wasPruneSuppressed = z;
    }

    public String toString() {
        return "SuppressedAttachState(section=" + this.section + ", parent=" + this.parent + ", wasPruneSuppressed=" + this.wasPruneSuppressed + ")";
    }
}
