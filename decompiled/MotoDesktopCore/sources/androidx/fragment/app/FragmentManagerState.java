package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
final class FragmentManagerState implements Parcelable {
    public static final Parcelable.Creator<FragmentManagerState> CREATOR = new C0091c(1);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public FragmentState[] f1537a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int[] f1538b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public BackStackState[] f1539c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f1540d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f1541e;

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.writeTypedArray(this.f1537a, i2);
        parcel.writeIntArray(this.f1538b);
        parcel.writeTypedArray(this.f1539c, i2);
        parcel.writeInt(this.f1540d);
        parcel.writeInt(this.f1541e);
    }
}
