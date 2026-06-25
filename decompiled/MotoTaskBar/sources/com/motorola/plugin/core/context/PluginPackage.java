package com.motorola.plugin.core.context;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PluginPackage.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginPackage implements Parcelable {
    private final PluginId pluginId;
    private final UserHandle userHandle;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.plugin.core.context.PluginPackage$Companion$CREATOR$1
        @Override // android.os.Parcelable.Creator
        public PluginPackage createFromParcel(Parcel parcel) {
            parcel.getClass();
            return new PluginPackage(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public PluginPackage[] newArray(int i) {
            return new PluginPackage[i];
        }
    };

    /* JADX INFO: compiled from: PluginPackage.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ PluginPackage of$default(Companion companion, PluginId pluginId, UserHandle userHandle, int i, Object obj) {
            if ((i & 2) != 0) {
                userHandle = Process.myUserHandle();
                userHandle.getClass();
            }
            return companion.of(pluginId, userHandle);
        }

        public final PluginPackage of(PluginId pluginId, UserHandle userHandle) {
            pluginId.getClass();
            userHandle.getClass();
            return new PluginPackage(pluginId, userHandle);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public PluginPackage(Parcel parcel) {
        parcel.getClass();
        Parcelable parcelable = parcel.readParcelable(PluginId.class.getClassLoader());
        parcelable.getClass();
        Parcelable parcelable2 = parcel.readParcelable(UserHandle.class.getClassLoader());
        parcelable2.getClass();
        this((PluginId) parcelable, (UserHandle) parcelable2);
    }

    public PluginPackage(PluginId pluginId, UserHandle userHandle) {
        pluginId.getClass();
        userHandle.getClass();
        this.pluginId = pluginId;
        this.userHandle = userHandle;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ PluginPackage(PluginId pluginId, UserHandle userHandle, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            userHandle = Process.myUserHandle();
            userHandle.getClass();
        }
        this(pluginId, userHandle);
    }

    public static /* synthetic */ PluginPackage copy$default(PluginPackage pluginPackage, PluginId pluginId, UserHandle userHandle, int i, Object obj) {
        if ((i & 1) != 0) {
            pluginId = pluginPackage.pluginId;
        }
        if ((i & 2) != 0) {
            userHandle = pluginPackage.userHandle;
        }
        return pluginPackage.copy(pluginId, userHandle);
    }

    public final PluginId component1() {
        return this.pluginId;
    }

    public final UserHandle component2() {
        return this.userHandle;
    }

    public final PluginPackage copy(PluginId pluginId, UserHandle userHandle) {
        pluginId.getClass();
        userHandle.getClass();
        return new PluginPackage(pluginId, userHandle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PluginPackage)) {
            return false;
        }
        PluginPackage pluginPackage = (PluginPackage) obj;
        return Intrinsics.areEqual(this.pluginId, pluginPackage.pluginId) && Intrinsics.areEqual(this.userHandle, pluginPackage.userHandle);
    }

    public final PluginId getPluginId() {
        return this.pluginId;
    }

    public final UserHandle getUserHandle() {
        return this.userHandle;
    }

    public int hashCode() {
        return (this.pluginId.hashCode() * 31) + this.userHandle.hashCode();
    }

    public String toString() {
        return "PluginPackage(pluginId=" + this.pluginId + ", userHandle=" + this.userHandle + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeParcelable(this.pluginId, i);
        parcel.writeParcelable(this.userHandle, i);
    }
}
