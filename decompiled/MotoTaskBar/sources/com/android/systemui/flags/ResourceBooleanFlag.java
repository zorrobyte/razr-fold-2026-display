package com.android.systemui.flags;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Flag.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ResourceBooleanFlag {
    private final String name;
    private final String namespace;
    private final int resourceId;

    public ResourceBooleanFlag(String str, String str2, int i) {
        str.getClass();
        str2.getClass();
        this.name = str;
        this.namespace = str2;
        this.resourceId = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResourceBooleanFlag)) {
            return false;
        }
        ResourceBooleanFlag resourceBooleanFlag = (ResourceBooleanFlag) obj;
        return Intrinsics.areEqual(this.name, resourceBooleanFlag.name) && Intrinsics.areEqual(this.namespace, resourceBooleanFlag.namespace) && this.resourceId == resourceBooleanFlag.resourceId;
    }

    public String getName() {
        return this.name;
    }

    public int getResourceId() {
        return this.resourceId;
    }

    public int hashCode() {
        return (((this.name.hashCode() * 31) + this.namespace.hashCode()) * 31) + Integer.hashCode(this.resourceId);
    }

    public String toString() {
        return "ResourceBooleanFlag(name=" + this.name + ", namespace=" + this.namespace + ", resourceId=" + this.resourceId + ")";
    }
}
