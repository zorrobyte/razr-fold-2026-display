package com.motorola.plugin.core.context;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PluginId.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginId implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    private final String id;

    /* JADX INFO: compiled from: PluginId.kt */
    public final class CREATOR implements Parcelable.Creator {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Override // android.os.Parcelable.Creator
        public PluginId createFromParcel(Parcel parcel) {
            parcel.getClass();
            return new PluginId(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public PluginId[] newArray(int i) {
            return new PluginId[i];
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public PluginId(Parcel parcel) {
        parcel.getClass();
        String string = parcel.readString();
        string.getClass();
        this(string);
    }

    public PluginId(String str) {
        str.getClass();
        this.id = str;
    }

    public static /* synthetic */ PluginId copy$default(PluginId pluginId, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = pluginId.id;
        }
        return pluginId.copy(str);
    }

    public final String component1() {
        return this.id;
    }

    public final PluginId copy(String str) {
        str.getClass();
        return new PluginId(str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof PluginId) && Intrinsics.areEqual(this.id, ((PluginId) obj).id);
    }

    public final String getId() {
        return this.id;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        return this.id;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeString(this.id);
    }
}
