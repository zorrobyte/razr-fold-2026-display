package P;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.internal.ParcelableSparseArray;
import com.google.android.material.stateful.ExtendableSavedState;

/* JADX INFO: loaded from: classes.dex */
public final class b implements Parcelable.ClassLoaderCreator {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f233a;

    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        switch (this.f233a) {
            case 0:
                return new ParcelableSparseArray(parcel, null);
            case 1:
                return new ExtendableSavedState(parcel, null);
            default:
                if (parcel.readParcelable(null) == null) {
                    return AbsSavedState.f1464b;
                }
                throw new IllegalStateException("superState must be null");
        }
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
        switch (this.f233a) {
            case 0:
                return new ParcelableSparseArray(parcel, classLoader);
            case 1:
                return new ExtendableSavedState(parcel, classLoader);
            default:
                if (parcel.readParcelable(classLoader) == null) {
                    return AbsSavedState.f1464b;
                }
                throw new IllegalStateException("superState must be null");
        }
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i2) {
        switch (this.f233a) {
            case 0:
                return new ParcelableSparseArray[i2];
            case 1:
                return new ExtendableSavedState[i2];
            default:
                return new AbsSavedState[i2];
        }
    }
}
