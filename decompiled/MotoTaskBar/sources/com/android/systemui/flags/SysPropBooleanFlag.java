package com.android.systemui.flags;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Flag.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SysPropBooleanFlag {

    /* JADX INFO: renamed from: default, reason: not valid java name */
    private final boolean f4default;
    private final String name;
    private final String namespace;

    public SysPropBooleanFlag(String str, String str2, boolean z) {
        str.getClass();
        str2.getClass();
        this.name = str;
        this.namespace = str2;
        this.f4default = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SysPropBooleanFlag)) {
            return false;
        }
        SysPropBooleanFlag sysPropBooleanFlag = (SysPropBooleanFlag) obj;
        return Intrinsics.areEqual(this.name, sysPropBooleanFlag.name) && Intrinsics.areEqual(this.namespace, sysPropBooleanFlag.namespace) && this.f4default == sysPropBooleanFlag.f4default;
    }

    public int hashCode() {
        return (((this.name.hashCode() * 31) + this.namespace.hashCode()) * 31) + Boolean.hashCode(this.f4default);
    }

    public String toString() {
        return "SysPropBooleanFlag(name=" + this.name + ", namespace=" + this.namespace + ", default=" + this.f4default + ")";
    }
}
