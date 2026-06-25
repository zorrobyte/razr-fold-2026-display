package androidx.recyclerview.widget;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class J implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem staggeredGridLayoutManager$LazySpanLookup$FullSpanItem = new StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem();
        staggeredGridLayoutManager$LazySpanLookup$FullSpanItem.f1831a = parcel.readInt();
        staggeredGridLayoutManager$LazySpanLookup$FullSpanItem.f1832b = parcel.readInt();
        staggeredGridLayoutManager$LazySpanLookup$FullSpanItem.f1834d = parcel.readInt() == 1;
        int i2 = parcel.readInt();
        if (i2 > 0) {
            int[] iArr = new int[i2];
            staggeredGridLayoutManager$LazySpanLookup$FullSpanItem.f1833c = iArr;
            parcel.readIntArray(iArr);
        }
        return staggeredGridLayoutManager$LazySpanLookup$FullSpanItem;
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i2) {
        return new StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem[i2];
    }
}
