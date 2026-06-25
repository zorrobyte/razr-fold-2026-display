package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ListAttachState.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ListAttachState {
    public static final Companion Companion = new Companion(null);
    private NotifFilter excludingFilter;
    private String groupPruneReason;
    private GroupEntry parent;
    private NotifPromoter promoter;
    private NotifSection section;
    private int stableIndex;
    private SuppressedAttachState suppressedChanges;

    /* JADX INFO: compiled from: ListAttachState.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ListAttachState create() {
            return new ListAttachState(null, null, null, null, null, SuppressedAttachState.Companion.create(), null);
        }
    }

    private ListAttachState(GroupEntry groupEntry, NotifSection notifSection, NotifFilter notifFilter, NotifPromoter notifPromoter, String str, SuppressedAttachState suppressedAttachState) {
        this.parent = groupEntry;
        this.section = notifSection;
        this.excludingFilter = notifFilter;
        this.promoter = notifPromoter;
        this.groupPruneReason = str;
        this.suppressedChanges = suppressedAttachState;
        this.stableIndex = -1;
    }

    public /* synthetic */ ListAttachState(GroupEntry groupEntry, NotifSection notifSection, NotifFilter notifFilter, NotifPromoter notifPromoter, String str, SuppressedAttachState suppressedAttachState, DefaultConstructorMarker defaultConstructorMarker) {
        this(groupEntry, notifSection, notifFilter, notifPromoter, str, suppressedAttachState);
    }

    public static final ListAttachState create() {
        return Companion.create();
    }

    public final void clone(ListAttachState listAttachState) {
        listAttachState.getClass();
        this.parent = listAttachState.parent;
        this.section = listAttachState.section;
        this.excludingFilter = listAttachState.excludingFilter;
        this.promoter = listAttachState.promoter;
        this.groupPruneReason = listAttachState.groupPruneReason;
        this.suppressedChanges.clone(listAttachState.suppressedChanges);
        this.stableIndex = listAttachState.stableIndex;
    }

    public final void detach() {
        this.parent = null;
        this.section = null;
        this.promoter = null;
        this.stableIndex = -1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ListAttachState)) {
            return false;
        }
        ListAttachState listAttachState = (ListAttachState) obj;
        return Intrinsics.areEqual(this.parent, listAttachState.parent) && Intrinsics.areEqual(this.section, listAttachState.section) && Intrinsics.areEqual(this.excludingFilter, listAttachState.excludingFilter) && Intrinsics.areEqual(this.promoter, listAttachState.promoter) && Intrinsics.areEqual(this.groupPruneReason, listAttachState.groupPruneReason) && Intrinsics.areEqual(this.suppressedChanges, listAttachState.suppressedChanges);
    }

    public final NotifFilter getExcludingFilter() {
        return this.excludingFilter;
    }

    public final String getGroupPruneReason() {
        return this.groupPruneReason;
    }

    public final GroupEntry getParent() {
        return this.parent;
    }

    public final NotifPromoter getPromoter() {
        return this.promoter;
    }

    public final NotifSection getSection() {
        return this.section;
    }

    public final int getSectionIndex() {
        NotifSection notifSection = this.section;
        if (notifSection != null) {
            return notifSection.getIndex();
        }
        return -1;
    }

    public final int getStableIndex() {
        return this.stableIndex;
    }

    public final SuppressedAttachState getSuppressedChanges() {
        return this.suppressedChanges;
    }

    public int hashCode() {
        GroupEntry groupEntry = this.parent;
        int iHashCode = (groupEntry == null ? 0 : groupEntry.hashCode()) * 31;
        NotifSection notifSection = this.section;
        int iHashCode2 = (iHashCode + (notifSection == null ? 0 : notifSection.hashCode())) * 31;
        NotifFilter notifFilter = this.excludingFilter;
        int iHashCode3 = (iHashCode2 + (notifFilter == null ? 0 : notifFilter.hashCode())) * 31;
        NotifPromoter notifPromoter = this.promoter;
        int iHashCode4 = (iHashCode3 + (notifPromoter == null ? 0 : notifPromoter.hashCode())) * 31;
        String str = this.groupPruneReason;
        return ((iHashCode4 + (str != null ? str.hashCode() : 0)) * 31) + this.suppressedChanges.hashCode();
    }

    public final void reset() {
        this.parent = null;
        this.section = null;
        this.excludingFilter = null;
        this.promoter = null;
        this.groupPruneReason = null;
        this.suppressedChanges.reset();
        this.stableIndex = -1;
    }

    public final void setExcludingFilter(NotifFilter notifFilter) {
        this.excludingFilter = notifFilter;
    }

    public final void setGroupPruneReason(String str) {
        this.groupPruneReason = str;
    }

    public final void setParent(GroupEntry groupEntry) {
        this.parent = groupEntry;
    }

    public final void setPromoter(NotifPromoter notifPromoter) {
        this.promoter = notifPromoter;
    }

    public final void setSection(NotifSection notifSection) {
        this.section = notifSection;
    }

    public final void setStableIndex(int i) {
        this.stableIndex = i;
    }

    public String toString() {
        return "ListAttachState(parent=" + this.parent + ", section=" + this.section + ", excludingFilter=" + this.excludingFilter + ", promoter=" + this.promoter + ", groupPruneReason=" + this.groupPruneReason + ", suppressedChanges=" + this.suppressedChanges + ")";
    }
}
