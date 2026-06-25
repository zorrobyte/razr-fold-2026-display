package androidx.appcompat.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
class AppCompatDelegateImpl$PanelFeatureState$SavedState implements Parcelable {
    public static final Parcelable.Creator<AppCompatDelegateImpl$PanelFeatureState$SavedState> CREATOR = new o();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f472a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f473b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Bundle f474c;

    public static AppCompatDelegateImpl$PanelFeatureState$SavedState a(Parcel parcel, ClassLoader classLoader) {
        AppCompatDelegateImpl$PanelFeatureState$SavedState appCompatDelegateImpl$PanelFeatureState$SavedState = new AppCompatDelegateImpl$PanelFeatureState$SavedState();
        appCompatDelegateImpl$PanelFeatureState$SavedState.f472a = parcel.readInt();
        boolean z2 = parcel.readInt() == 1;
        appCompatDelegateImpl$PanelFeatureState$SavedState.f473b = z2;
        if (z2) {
            appCompatDelegateImpl$PanelFeatureState$SavedState.f474c = parcel.readBundle(classLoader);
        }
        return appCompatDelegateImpl$PanelFeatureState$SavedState;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f472a);
        parcel.writeInt(this.f473b ? 1 : 0);
        if (this.f473b) {
            parcel.writeBundle(this.f474c);
        }
    }
}
