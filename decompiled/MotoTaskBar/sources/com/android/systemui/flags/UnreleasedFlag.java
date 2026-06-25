package com.android.systemui.flags;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Flag.kt */
/* JADX INFO: loaded from: classes.dex */
public final class UnreleasedFlag extends BooleanFlag {
    private final String name;
    private final String namespace;
    private final boolean overridden;
    private final boolean teamfood;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnreleasedFlag(String str, String str2, boolean z, boolean z2) {
        super(str, str2, false, z, z2);
        str.getClass();
        str2.getClass();
        this.name = str;
        this.namespace = str2;
        this.teamfood = z;
        this.overridden = z2;
    }

    public /* synthetic */ UnreleasedFlag(String str, String str2, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UnreleasedFlag)) {
            return false;
        }
        UnreleasedFlag unreleasedFlag = (UnreleasedFlag) obj;
        return Intrinsics.areEqual(this.name, unreleasedFlag.name) && Intrinsics.areEqual(this.namespace, unreleasedFlag.namespace) && this.teamfood == unreleasedFlag.teamfood && this.overridden == unreleasedFlag.overridden;
    }

    @Override // com.android.systemui.flags.BooleanFlag
    public String getName() {
        return this.name;
    }

    @Override // com.android.systemui.flags.BooleanFlag
    public String getNamespace() {
        return this.namespace;
    }

    @Override // com.android.systemui.flags.BooleanFlag
    public boolean getOverridden() {
        return this.overridden;
    }

    @Override // com.android.systemui.flags.BooleanFlag
    public boolean getTeamfood() {
        return this.teamfood;
    }

    public int hashCode() {
        return (((((this.name.hashCode() * 31) + this.namespace.hashCode()) * 31) + Boolean.hashCode(this.teamfood)) * 31) + Boolean.hashCode(this.overridden);
    }

    public String toString() {
        return "UnreleasedFlag(name=" + this.name + ", namespace=" + this.namespace + ", teamfood=" + this.teamfood + ", overridden=" + this.overridden + ")";
    }
}
