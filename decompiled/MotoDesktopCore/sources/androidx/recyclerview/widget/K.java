package androidx.recyclerview.widget;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/* JADX INFO: loaded from: classes.dex */
public final class K implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        StaggeredGridLayoutManager.SavedState savedState = new StaggeredGridLayoutManager.SavedState();
        savedState.f1835a = parcel.readInt();
        savedState.f1836b = parcel.readInt();
        int i2 = parcel.readInt();
        savedState.f1837c = i2;
        if (i2 > 0) {
            int[] iArr = new int[i2];
            savedState.f1838d = iArr;
            parcel.readIntArray(iArr);
        }
        int i3 = parcel.readInt();
        savedState.f1839e = i3;
        if (i3 > 0) {
            int[] iArr2 = new int[i3];
            savedState.f1840f = iArr2;
            parcel.readIntArray(iArr2);
        }
        savedState.f1842h = parcel.readInt() == 1;
        savedState.f1843i = parcel.readInt() == 1;
        savedState.f1844j = parcel.readInt() == 1;
        savedState.f1841g = parcel.readArrayList(StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem.class.getClassLoader());
        return savedState;
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i2) {
        return new StaggeredGridLayoutManager.SavedState[i2];
    }
}
