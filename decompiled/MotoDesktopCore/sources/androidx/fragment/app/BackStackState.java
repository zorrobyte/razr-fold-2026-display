package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
final class BackStackState implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new C0091c(0);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int[] f1487a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final int f1488b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f1489c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final String f1490d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final int f1491e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final int f1492f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final CharSequence f1493g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final int f1494h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final CharSequence f1495i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final ArrayList f1496j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final ArrayList f1497k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final boolean f1498l;

    public BackStackState(Parcel parcel) {
        this.f1487a = parcel.createIntArray();
        this.f1488b = parcel.readInt();
        this.f1489c = parcel.readInt();
        this.f1490d = parcel.readString();
        this.f1491e = parcel.readInt();
        this.f1492f = parcel.readInt();
        Parcelable.Creator creator = TextUtils.CHAR_SEQUENCE_CREATOR;
        this.f1493g = (CharSequence) creator.createFromParcel(parcel);
        this.f1494h = parcel.readInt();
        this.f1495i = (CharSequence) creator.createFromParcel(parcel);
        this.f1496j = parcel.createStringArrayList();
        this.f1497k = parcel.createStringArrayList();
        this.f1498l = parcel.readInt() != 0;
    }

    public BackStackState(C0090b c0090b) {
        int size = c0090b.f1597b.size();
        this.f1487a = new int[size * 6];
        if (!c0090b.f1604i) {
            throw new IllegalStateException("Not on back stack");
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            C0089a c0089a = (C0089a) c0090b.f1597b.get(i3);
            int[] iArr = this.f1487a;
            int i4 = i2 + 1;
            iArr[i2] = c0089a.f1590a;
            int i5 = i2 + 2;
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = c0089a.f1591b;
            iArr[i4] = abstractComponentCallbacksC0098j != null ? abstractComponentCallbacksC0098j.mIndex : -1;
            iArr[i5] = c0089a.f1592c;
            iArr[i2 + 3] = c0089a.f1593d;
            int i6 = i2 + 5;
            iArr[i2 + 4] = c0089a.f1594e;
            i2 += 6;
            iArr[i6] = c0089a.f1595f;
        }
        this.f1488b = c0090b.f1602g;
        this.f1489c = c0090b.f1603h;
        this.f1490d = c0090b.f1605j;
        this.f1491e = c0090b.f1607l;
        this.f1492f = c0090b.f1608m;
        this.f1493g = c0090b.f1609n;
        this.f1494h = c0090b.f1610o;
        this.f1495i = c0090b.f1611p;
        this.f1496j = c0090b.f1612q;
        this.f1497k = c0090b.r;
        this.f1498l = c0090b.f1613s;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.writeIntArray(this.f1487a);
        parcel.writeInt(this.f1488b);
        parcel.writeInt(this.f1489c);
        parcel.writeString(this.f1490d);
        parcel.writeInt(this.f1491e);
        parcel.writeInt(this.f1492f);
        TextUtils.writeToParcel(this.f1493g, parcel, 0);
        parcel.writeInt(this.f1494h);
        TextUtils.writeToParcel(this.f1495i, parcel, 0);
        parcel.writeStringList(this.f1496j);
        parcel.writeStringList(this.f1497k);
        parcel.writeInt(this.f1498l ? 1 : 0);
    }
}
