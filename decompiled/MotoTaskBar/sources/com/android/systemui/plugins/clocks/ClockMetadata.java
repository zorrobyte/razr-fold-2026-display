package com.android.systemui.plugins.clocks;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClockProviderPlugin.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ClockMetadata {
    public static final int $stable = 0;
    private final String clockId;
    private final boolean isDeprecated;
    private final String replacementTarget;

    public ClockMetadata(String str, boolean z, String str2) {
        str.getClass();
        this.clockId = str;
        this.isDeprecated = z;
        this.replacementTarget = str2;
    }

    public /* synthetic */ ClockMetadata(String str, boolean z, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? null : str2);
    }

    public static /* synthetic */ ClockMetadata copy$default(ClockMetadata clockMetadata, String str, boolean z, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clockMetadata.clockId;
        }
        if ((i & 2) != 0) {
            z = clockMetadata.isDeprecated;
        }
        if ((i & 4) != 0) {
            str2 = clockMetadata.replacementTarget;
        }
        return clockMetadata.copy(str, z, str2);
    }

    public final String component1() {
        return this.clockId;
    }

    public final boolean component2() {
        return this.isDeprecated;
    }

    public final String component3() {
        return this.replacementTarget;
    }

    public final ClockMetadata copy(String str, boolean z, String str2) {
        str.getClass();
        return new ClockMetadata(str, z, str2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockMetadata)) {
            return false;
        }
        ClockMetadata clockMetadata = (ClockMetadata) obj;
        return Intrinsics.areEqual(this.clockId, clockMetadata.clockId) && this.isDeprecated == clockMetadata.isDeprecated && Intrinsics.areEqual(this.replacementTarget, clockMetadata.replacementTarget);
    }

    public final String getClockId() {
        return this.clockId;
    }

    public final String getReplacementTarget() {
        return this.replacementTarget;
    }

    public int hashCode() {
        int iHashCode = ((this.clockId.hashCode() * 31) + Boolean.hashCode(this.isDeprecated)) * 31;
        String str = this.replacementTarget;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public final boolean isDeprecated() {
        return this.isDeprecated;
    }

    public String toString() {
        return "ClockMetadata(clockId=" + this.clockId + ", isDeprecated=" + this.isDeprecated + ", replacementTarget=" + this.replacementTarget + ")";
    }
}
