package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: renamed from: androidx.fragment.app.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0091c implements Parcelable.Creator {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1614a;

    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        switch (this.f1614a) {
            case 0:
                return new BackStackState(parcel);
            case 1:
                FragmentManagerState fragmentManagerState = new FragmentManagerState();
                fragmentManagerState.f1540d = -1;
                fragmentManagerState.f1537a = (FragmentState[]) parcel.createTypedArray(FragmentState.CREATOR);
                fragmentManagerState.f1538b = parcel.createIntArray();
                fragmentManagerState.f1539c = (BackStackState[]) parcel.createTypedArray(BackStackState.CREATOR);
                fragmentManagerState.f1540d = parcel.readInt();
                fragmentManagerState.f1541e = parcel.readInt();
                return fragmentManagerState;
            default:
                return new FragmentState(parcel);
        }
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i2) {
        switch (this.f1614a) {
            case 0:
                return new BackStackState[i2];
            case 1:
                return new FragmentManagerState[i2];
            default:
                return new FragmentState[i2];
        }
    }
}
