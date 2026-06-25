package androidx.fragment.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
final class FragmentState implements Parcelable {
    public static final Parcelable.Creator<FragmentState> CREATOR = new C0091c(2);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final String f1542a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final int f1543b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final boolean f1544c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final int f1545d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final int f1546e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final String f1547f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final boolean f1548g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final boolean f1549h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final Bundle f1550i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final boolean f1551j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public Bundle f1552k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public AbstractComponentCallbacksC0098j f1553l;

    public FragmentState(Parcel parcel) {
        this.f1542a = parcel.readString();
        this.f1543b = parcel.readInt();
        this.f1544c = parcel.readInt() != 0;
        this.f1545d = parcel.readInt();
        this.f1546e = parcel.readInt();
        this.f1547f = parcel.readString();
        this.f1548g = parcel.readInt() != 0;
        this.f1549h = parcel.readInt() != 0;
        this.f1550i = parcel.readBundle();
        this.f1551j = parcel.readInt() != 0;
        this.f1552k = parcel.readBundle();
    }

    public FragmentState(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        this.f1542a = abstractComponentCallbacksC0098j.getClass().getName();
        this.f1543b = abstractComponentCallbacksC0098j.mIndex;
        this.f1544c = abstractComponentCallbacksC0098j.mFromLayout;
        this.f1545d = abstractComponentCallbacksC0098j.mFragmentId;
        this.f1546e = abstractComponentCallbacksC0098j.mContainerId;
        this.f1547f = abstractComponentCallbacksC0098j.mTag;
        this.f1548g = abstractComponentCallbacksC0098j.mRetainInstance;
        this.f1549h = abstractComponentCallbacksC0098j.mDetached;
        this.f1550i = abstractComponentCallbacksC0098j.mArguments;
        this.f1551j = abstractComponentCallbacksC0098j.mHidden;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f1542a);
        parcel.writeInt(this.f1543b);
        parcel.writeInt(this.f1544c ? 1 : 0);
        parcel.writeInt(this.f1545d);
        parcel.writeInt(this.f1546e);
        parcel.writeString(this.f1547f);
        parcel.writeInt(this.f1548g ? 1 : 0);
        parcel.writeInt(this.f1549h ? 1 : 0);
        parcel.writeBundle(this.f1550i);
        parcel.writeInt(this.f1551j ? 1 : 0);
        parcel.writeBundle(this.f1552k);
    }
}
