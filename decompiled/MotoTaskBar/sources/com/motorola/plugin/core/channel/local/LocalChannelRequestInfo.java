package com.motorola.plugin.core.channel.local;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LocalChannelRequestInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class LocalChannelRequestInfo implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    private final Bundle bundle;
    private final String token;

    /* JADX INFO: compiled from: LocalChannelRequestInfo.kt */
    public final class CREATOR implements Parcelable.Creator {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Override // android.os.Parcelable.Creator
        public LocalChannelRequestInfo createFromParcel(Parcel parcel) {
            parcel.getClass();
            return new LocalChannelRequestInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LocalChannelRequestInfo[] newArray(int i) {
            return new LocalChannelRequestInfo[i];
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public LocalChannelRequestInfo(Parcel parcel) {
        parcel.getClass();
        String string = parcel.readString();
        string.getClass();
        Bundle bundle = parcel.readBundle();
        bundle.getClass();
        this(string, bundle);
    }

    public LocalChannelRequestInfo(String str, Bundle bundle) {
        str.getClass();
        bundle.getClass();
        this.token = str;
        this.bundle = bundle;
    }

    public static /* synthetic */ LocalChannelRequestInfo copy$default(LocalChannelRequestInfo localChannelRequestInfo, String str, Bundle bundle, int i, Object obj) {
        if ((i & 1) != 0) {
            str = localChannelRequestInfo.token;
        }
        if ((i & 2) != 0) {
            bundle = localChannelRequestInfo.bundle;
        }
        return localChannelRequestInfo.copy(str, bundle);
    }

    public final String component1() {
        return this.token;
    }

    public final Bundle component2() {
        return this.bundle;
    }

    public final LocalChannelRequestInfo copy(String str, Bundle bundle) {
        str.getClass();
        bundle.getClass();
        return new LocalChannelRequestInfo(str, bundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.bundle.describeContents();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocalChannelRequestInfo)) {
            return false;
        }
        LocalChannelRequestInfo localChannelRequestInfo = (LocalChannelRequestInfo) obj;
        return Intrinsics.areEqual(this.token, localChannelRequestInfo.token) && Intrinsics.areEqual(this.bundle, localChannelRequestInfo.bundle);
    }

    public final Bundle getBundle() {
        return this.bundle;
    }

    public final String getToken() {
        return this.token;
    }

    public int hashCode() {
        return (this.token.hashCode() * 31) + this.bundle.hashCode();
    }

    public String toString() {
        return "LocalChannelRequestInfo(token=" + this.token + ", bundle=" + this.bundle + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeString(this.token);
        parcel.writeBundle(this.bundle);
    }
}
