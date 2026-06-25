package X;

import android.os.IBinder;
import android.os.Parcel;
import com.motorola.mobiledesktop.core.bean.AudioSettingInfo;

/* JADX INFO: renamed from: X.k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0025k implements InterfaceC0029m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f326a;

    public final void a(AudioSettingInfo audioSettingInfo) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IAudioSettingChangeCallback");
            parcelObtain.writeInt(1);
            audioSettingInfo.writeToParcel(parcelObtain, 0);
            this.f326a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f326a;
    }
}
