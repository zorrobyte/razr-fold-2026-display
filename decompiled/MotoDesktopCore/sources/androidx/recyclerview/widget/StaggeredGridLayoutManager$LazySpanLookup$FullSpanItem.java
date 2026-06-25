package androidx.recyclerview.widget;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
class StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem implements Parcelable {
    public static final Parcelable.Creator<StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> CREATOR = new J();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f1831a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1832b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int[] f1833c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f1834d;

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        return "FullSpanItem{mPosition=" + this.f1831a + ", mGapDir=" + this.f1832b + ", mHasUnwantedGapAfter=" + this.f1834d + ", mGapPerSpan=" + Arrays.toString(this.f1833c) + '}';
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f1831a);
        parcel.writeInt(this.f1832b);
        parcel.writeInt(this.f1834d ? 1 : 0);
        int[] iArr = this.f1833c;
        if (iArr == null || iArr.length <= 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(iArr.length);
            parcel.writeIntArray(this.f1833c);
        }
    }
}
