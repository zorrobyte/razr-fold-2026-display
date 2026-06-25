package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Flag.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class BooleanFlag implements ParcelableFlag {

    /* JADX INFO: renamed from: default, reason: not valid java name */
    private final boolean f3default;
    private final String name;
    private final String namespace;
    private final boolean overridden;
    private final boolean teamfood;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.android.systemui.flags.BooleanFlag$Companion$CREATOR$1
        /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.flags.BooleanFlag$Companion$CREATOR$1$createFromParcel$1] */
        @Override // android.os.Parcelable.Creator
        public BooleanFlag$Companion$CREATOR$1$createFromParcel$1 createFromParcel(final Parcel parcel) {
            parcel.getClass();
            return new BooleanFlag(parcel) { // from class: com.android.systemui.flags.BooleanFlag$Companion$CREATOR$1$createFromParcel$1
                {
                    DefaultConstructorMarker defaultConstructorMarker = null;
                }
            };
        }

        @Override // android.os.Parcelable.Creator
        public BooleanFlag[] newArray(int i) {
            return new BooleanFlag[i];
        }
    };

    /* JADX INFO: compiled from: Flag.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private BooleanFlag(int i, String str, String str2, boolean z, boolean z2, boolean z3) {
        this(str, str2, z, z2, z3);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private BooleanFlag(Parcel parcel) {
        int i = parcel.readInt();
        String string = parcel.readString();
        string = string == null ? "" : string;
        String string2 = parcel.readString();
        this(i, string, string2 == null ? "" : string2, parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean());
    }

    public /* synthetic */ BooleanFlag(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    public BooleanFlag(String str, String str2, boolean z, boolean z2, boolean z3) {
        str.getClass();
        str2.getClass();
        this.name = str;
        this.namespace = str2;
        this.f3default = z;
        this.teamfood = z2;
        this.overridden = z3;
    }

    public Boolean getDefault() {
        return Boolean.valueOf(this.f3default);
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public boolean getOverridden() {
        return this.overridden;
    }

    public boolean getTeamfood() {
        return this.teamfood;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeInt(0);
        parcel.writeString(getName());
        parcel.writeString(getNamespace());
        parcel.writeBoolean(getDefault().booleanValue());
        parcel.writeBoolean(getTeamfood());
        parcel.writeBoolean(getOverridden());
    }
}
