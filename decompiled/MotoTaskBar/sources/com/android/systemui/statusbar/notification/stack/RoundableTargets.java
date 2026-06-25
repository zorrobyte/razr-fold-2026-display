package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotificationTargetsHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RoundableTargets {
    private final Roundable after;
    private final Roundable before;
    private final ExpandableNotificationRow swiped;

    public RoundableTargets(Roundable roundable, ExpandableNotificationRow expandableNotificationRow, Roundable roundable2) {
        this.before = roundable;
        this.swiped = expandableNotificationRow;
        this.after = roundable2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RoundableTargets)) {
            return false;
        }
        RoundableTargets roundableTargets = (RoundableTargets) obj;
        return Intrinsics.areEqual(this.before, roundableTargets.before) && Intrinsics.areEqual(this.swiped, roundableTargets.swiped) && Intrinsics.areEqual(this.after, roundableTargets.after);
    }

    public final Roundable getAfter() {
        return this.after;
    }

    public final Roundable getBefore() {
        return this.before;
    }

    public final ExpandableNotificationRow getSwiped() {
        return this.swiped;
    }

    public int hashCode() {
        Roundable roundable = this.before;
        int iHashCode = (roundable == null ? 0 : roundable.hashCode()) * 31;
        ExpandableNotificationRow expandableNotificationRow = this.swiped;
        int iHashCode2 = (iHashCode + (expandableNotificationRow == null ? 0 : expandableNotificationRow.hashCode())) * 31;
        Roundable roundable2 = this.after;
        return iHashCode2 + (roundable2 != null ? roundable2.hashCode() : 0);
    }

    public String toString() {
        return "RoundableTargets(before=" + this.before + ", swiped=" + this.swiped + ", after=" + this.after + ")";
    }
}
