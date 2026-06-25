package E;

import android.os.Parcel;
import android.util.SparseIntArray;

/* JADX INFO: loaded from: classes.dex */
public final class c extends b {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Parcel f111b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f112c;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f114e;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final SparseIntArray f110a = new SparseIntArray();

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f113d = -1;

    public c(Parcel parcel, int i2, int i3, String str) {
        this.f111b = parcel;
        this.f112c = i3;
        this.f114e = i2;
    }

    @Override // E.b
    public final boolean b(int i2) {
        Parcel parcel;
        int iDataPosition;
        while (true) {
            int i3 = this.f114e;
            int i4 = this.f112c;
            parcel = this.f111b;
            if (i3 >= i4) {
                iDataPosition = -1;
                break;
            }
            parcel.setDataPosition(i3);
            int i5 = parcel.readInt();
            int i6 = parcel.readInt();
            this.f114e += i5;
            if (i6 == i2) {
                iDataPosition = parcel.dataPosition();
                break;
            }
        }
        if (iDataPosition == -1) {
            return false;
        }
        parcel.setDataPosition(iDataPosition);
        return true;
    }

    @Override // E.b
    public final void c(int i2) {
        int i3 = this.f113d;
        SparseIntArray sparseIntArray = this.f110a;
        Parcel parcel = this.f111b;
        if (i3 >= 0) {
            int i4 = sparseIntArray.get(i3);
            int iDataPosition = parcel.dataPosition();
            parcel.setDataPosition(i4);
            parcel.writeInt(iDataPosition - i4);
            parcel.setDataPosition(iDataPosition);
        }
        this.f113d = i2;
        sparseIntArray.put(i2, parcel.dataPosition());
        parcel.writeInt(0);
        parcel.writeInt(i2);
    }
}
