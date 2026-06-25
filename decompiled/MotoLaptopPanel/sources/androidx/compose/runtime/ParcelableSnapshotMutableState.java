package androidx.compose.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SnapshotState.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class ParcelableSnapshotMutableState extends SnapshotMutableStateImpl implements Parcelable {
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator CREATOR = new Parcelable.ClassLoaderCreator() { // from class: androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1
        @Override // android.os.Parcelable.Creator
        public ParcelableSnapshotMutableState createFromParcel(Parcel parcel) {
            return createFromParcel(parcel, (ClassLoader) null);
        }

        @Override // android.os.Parcelable.ClassLoaderCreator
        public ParcelableSnapshotMutableState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            SnapshotMutationPolicy snapshotMutationPolicyNeverEqualPolicy;
            if (classLoader == null) {
                classLoader = ParcelableSnapshotMutableState$Companion$CREATOR$1.class.getClassLoader();
            }
            Object value = parcel.readValue(classLoader);
            int i = parcel.readInt();
            if (i == 0) {
                snapshotMutationPolicyNeverEqualPolicy = SnapshotStateKt.neverEqualPolicy();
            } else if (i == 1) {
                snapshotMutationPolicyNeverEqualPolicy = SnapshotStateKt.structuralEqualityPolicy();
            } else {
                if (i != 2) {
                    throw new IllegalStateException("Unsupported MutableState policy " + i + " was restored");
                }
                snapshotMutationPolicyNeverEqualPolicy = SnapshotStateKt.referentialEqualityPolicy();
            }
            return new ParcelableSnapshotMutableState(value, snapshotMutationPolicyNeverEqualPolicy);
        }

        @Override // android.os.Parcelable.Creator
        public ParcelableSnapshotMutableState[] newArray(int i) {
            return new ParcelableSnapshotMutableState[i];
        }
    };

    /* JADX INFO: compiled from: SnapshotState.android.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ParcelableSnapshotMutableState(Object obj, SnapshotMutationPolicy snapshotMutationPolicy) {
        super(obj, snapshotMutationPolicy);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int i2;
        parcel.writeValue(getValue());
        SnapshotMutationPolicy policy = getPolicy();
        if (Intrinsics.areEqual(policy, SnapshotStateKt.neverEqualPolicy())) {
            i2 = 0;
        } else if (Intrinsics.areEqual(policy, SnapshotStateKt.structuralEqualityPolicy())) {
            i2 = 1;
        } else {
            if (!Intrinsics.areEqual(policy, SnapshotStateKt.referentialEqualityPolicy())) {
                throw new IllegalStateException("Only known types of MutableState's SnapshotMutationPolicy are supported");
            }
            i2 = 2;
        }
        parcel.writeInt(i2);
    }
}
