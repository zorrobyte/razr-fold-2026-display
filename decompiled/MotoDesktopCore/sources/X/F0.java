package X;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public final class F0 implements ServiceConnection {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ O f267a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ G0 f268b;

    public F0(G0 g02, O o2) {
        this.f268b = g02;
        this.f267a = o2;
    }

    @Override // android.content.ServiceConnection
    public final void onBindingDied(ComponentName componentName) {
        v0.a("MotoDesktopService", "onBindingDied name:" + componentName);
        try {
            M m2 = (M) this.f267a;
            m2.getClass();
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IPlatformServiceConnection");
                Y.r.b(parcelObtain, componentName);
                m2.f278a.transact(3, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                parcelObtain2.recycle();
                parcelObtain.recycle();
                this.f268b.f270a.l0.remove(componentName.toShortString());
            } catch (Throwable th) {
                parcelObtain2.recycle();
                parcelObtain.recycle();
                throw th;
            }
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "onBindingDied Exception ", e2);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onNullBinding(ComponentName componentName) {
        v0.a("MotoDesktopService", "onNullBinding name:" + componentName);
        try {
            M m2 = (M) this.f267a;
            m2.getClass();
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IPlatformServiceConnection");
                Y.r.b(parcelObtain, componentName);
                m2.f278a.transact(4, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                parcelObtain2.recycle();
                parcelObtain.recycle();
                this.f268b.f270a.l0.remove(componentName.toShortString());
            } catch (Throwable th) {
                parcelObtain2.recycle();
                parcelObtain.recycle();
                throw th;
            }
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "onNullBinding Exception ", e2);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        v0.a("MotoDesktopService", "onServiceConnected name:" + componentName);
        try {
            ((M) this.f267a).a(componentName, iBinder);
            this.f268b.f270a.l0.put(componentName.toShortString(), this);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "onServiceConnected Exception ", e2);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        v0.a("MotoDesktopService", "onServiceDisconnected name:" + componentName);
        try {
            M m2 = (M) this.f267a;
            m2.getClass();
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IPlatformServiceConnection");
                Y.r.b(parcelObtain, componentName);
                m2.f278a.transact(2, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                parcelObtain2.recycle();
                parcelObtain.recycle();
                this.f268b.f270a.l0.remove(componentName.toShortString());
            } catch (Throwable th) {
                parcelObtain2.recycle();
                parcelObtain.recycle();
                throw th;
            }
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "onServiceDisconnected Exception ", e2);
        }
    }
}
