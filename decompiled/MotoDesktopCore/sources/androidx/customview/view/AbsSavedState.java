package androidx.customview.view;

import P.b;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbsSavedState implements Parcelable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Parcelable f1465a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final AbsSavedState f1464b = new AnonymousClass1();
    public static final Parcelable.Creator<AbsSavedState> CREATOR = new b(2);

    /* JADX INFO: renamed from: androidx.customview.view.AbsSavedState$1, reason: invalid class name */
    public static class AnonymousClass1 extends AbsSavedState {
    }

    public AbsSavedState() {
        this.f1465a = null;
    }

    public AbsSavedState(Parcel parcel, ClassLoader classLoader) {
        Parcelable parcelable = parcel.readParcelable(classLoader);
        this.f1465a = parcelable == null ? f1464b : parcelable;
    }

    public AbsSavedState(Parcelable parcelable) {
        if (parcelable == null) {
            throw new IllegalArgumentException("superState must not be null");
        }
        this.f1465a = parcelable == f1464b ? null : parcelable;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.f1465a, i2);
    }
}
