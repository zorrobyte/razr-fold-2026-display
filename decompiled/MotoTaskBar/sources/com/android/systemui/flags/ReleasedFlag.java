package com.android.systemui.flags;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Flag.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ReleasedFlag extends BooleanFlag {
    private final String name;
    private final String namespace;
    private final boolean overridden;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReleasedFlag(String str, String str2, boolean z) {
        super(str, str2, true, false, z);
        str.getClass();
        str2.getClass();
        this.name = str;
        this.namespace = str2;
        this.overridden = z;
    }

    public /* synthetic */ ReleasedFlag(String str, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? false : z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ReleasedFlag)) {
            return false;
        }
        ReleasedFlag releasedFlag = (ReleasedFlag) obj;
        return Intrinsics.areEqual(this.name, releasedFlag.name) && Intrinsics.areEqual(this.namespace, releasedFlag.namespace) && this.overridden == releasedFlag.overridden;
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

    public int hashCode() {
        return (((this.name.hashCode() * 31) + this.namespace.hashCode()) * 31) + Boolean.hashCode(this.overridden);
    }

    public String toString() {
        return "ReleasedFlag(name=" + this.name + ", namespace=" + this.namespace + ", overridden=" + this.overridden + ")";
    }
}
