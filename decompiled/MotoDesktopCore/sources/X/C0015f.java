package X;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: renamed from: X.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0015f implements InterfaceC0019h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f321a;

    public final void a(ComponentName componentName, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IActivityStateChangedCallback");
            Y.r.a(parcelObtain, componentName);
            Y.r.a(parcelObtain, runningTaskInfo);
            parcelObtain.writeInt(z2 ? 1 : 0);
            this.f321a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f321a;
    }

    public final void b(ComponentName componentName, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IActivityStateChangedCallback");
            Y.r.a(parcelObtain, componentName);
            Y.r.a(parcelObtain, runningTaskInfo);
            parcelObtain.writeInt(z2 ? 1 : 0);
            this.f321a.transact(2, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public final void c(ComponentName componentName, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IActivityStateChangedCallback");
            Y.r.a(parcelObtain, componentName);
            Y.r.a(parcelObtain, runningTaskInfo);
            parcelObtain.writeInt(z2 ? 1 : 0);
            this.f321a.transact(3, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
