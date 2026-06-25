package X;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: renamed from: X.g0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0018g0 implements InterfaceC0020h0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public IBinder f322a;

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f322a;
    }

    public final void onTaskCreated(int i2, ComponentName componentName) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ITaskStackListener");
            parcelObtain.writeInt(i2);
            if (componentName != null) {
                parcelObtain.writeInt(1);
                componentName.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            this.f322a.transact(2, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public final void onTaskDisplayChanged(int i2, int i3) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ITaskStackListener");
            parcelObtain.writeInt(i2);
            parcelObtain.writeInt(i3);
            this.f322a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public final void onTaskFocusChanged(int i2, boolean z2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ITaskStackListener");
            parcelObtain.writeInt(i2);
            parcelObtain.writeInt(z2 ? 1 : 0);
            this.f322a.transact(6, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ITaskStackListener");
            if (runningTaskInfo != null) {
                parcelObtain.writeInt(1);
                runningTaskInfo.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            this.f322a.transact(4, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public final void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ITaskStackListener");
            if (runningTaskInfo != null) {
                parcelObtain.writeInt(1);
                runningTaskInfo.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            this.f322a.transact(5, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public final void onTaskRemoved(int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ITaskStackListener");
            parcelObtain.writeInt(i2);
            this.f322a.transact(3, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
