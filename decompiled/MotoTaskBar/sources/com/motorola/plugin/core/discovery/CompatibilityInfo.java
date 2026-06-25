package com.motorola.plugin.core.discovery;

import android.os.Parcel;
import android.os.Parcelable;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.BitFlagKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: CompatibilityInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class CompatibilityInfo implements Parcelable {
    public static final int REASON_DISABLED = 16;
    public static final int REASON_INVALID_CLASS = 4;
    public static final int REASON_INVALID_VERSION_TOO_NEW = 2;
    public static final int REASON_INVALID_VERSION_TOO_OLD = 1;
    public static final int REASON_NON_SECURE = 8;
    public static final int REASON_STUB = 64;
    public static final int REASON_UNKNOWN = 32;
    public static final int REASON_VALID = 0;
    private final boolean optimisticCompat;
    private final int reasonFlags;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.plugin.core.discovery.CompatibilityInfo$Companion$CREATOR$1
        @Override // android.os.Parcelable.Creator
        public CompatibilityInfo createFromParcel(Parcel parcel) {
            parcel.getClass();
            return new CompatibilityInfo(parcel, (DefaultConstructorMarker) null);
        }

        @Override // android.os.Parcelable.Creator
        public CompatibilityInfo[] newArray(int i) {
            return new CompatibilityInfo[i];
        }
    };
    private static final CompatibilityInfo STUB = new CompatibilityInfo(false, 64);

    /* JADX INFO: compiled from: CompatibilityInfo.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final CompatibilityInfo getSTUB() {
            return CompatibilityInfo.STUB;
        }
    }

    private CompatibilityInfo(Parcel parcel) {
        this(parcel.readBoolean(), parcel.readInt());
    }

    public /* synthetic */ CompatibilityInfo(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    public CompatibilityInfo(boolean z, int i) {
        this.optimisticCompat = z;
        this.reasonFlags = i;
    }

    public static /* synthetic */ CompatibilityInfo copy$default(CompatibilityInfo compatibilityInfo, boolean z, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z = compatibilityInfo.optimisticCompat;
        }
        if ((i2 & 2) != 0) {
            i = compatibilityInfo.reasonFlags;
        }
        return compatibilityInfo.copy(z, i);
    }

    public final boolean component1() {
        return this.optimisticCompat;
    }

    public final int component2() {
        return this.reasonFlags;
    }

    public final CompatibilityInfo copy(boolean z, int i) {
        return new CompatibilityInfo(z, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompatibilityInfo)) {
            return false;
        }
        CompatibilityInfo compatibilityInfo = (CompatibilityInfo) obj;
        return this.optimisticCompat == compatibilityInfo.optimisticCompat && this.reasonFlags == compatibilityInfo.reasonFlags;
    }

    public final boolean getOptimisticCompat() {
        return this.optimisticCompat;
    }

    public final int getReasonFlags() {
        return this.reasonFlags;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public int hashCode() {
        boolean z = this.optimisticCompat;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return (r0 * 31) + Integer.hashCode(this.reasonFlags);
    }

    public final String reasonFlagsToString() {
        BitFlag bitFlagWrap = BitFlag.Companion.wrap(this.reasonFlags);
        return BitFlagKt.contains(bitFlagWrap, 1) ? "Version too old" : BitFlagKt.contains(bitFlagWrap, 2) ? "Version too new" : BitFlagKt.contains(bitFlagWrap, 4) ? "Invalid plugin class" : BitFlagKt.contains(bitFlagWrap, 8) ? "Non secure device" : BitFlagKt.contains(bitFlagWrap, 16) ? "Disabled" : BitFlagKt.contains(bitFlagWrap, 32) ? "Unknown" : "Valid";
    }

    public String toString() {
        return "CompatibilityInfo(optimisticCompat=" + this.optimisticCompat + ", reasonFlags=" + this.reasonFlags + '(' + reasonFlagsToString() + ")),";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.getClass();
        parcel.writeBoolean(this.optimisticCompat);
        parcel.writeInt(this.reasonFlags);
    }
}
