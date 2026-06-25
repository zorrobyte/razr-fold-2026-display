package g0;

import X.v0;
import X.w0;
import android.companion.virtual.VirtualDeviceManager;
import android.content.ComponentName;
import android.os.Parcel;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class c implements VirtualDeviceManager.ActivityListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ e f2553a;

    public c(e eVar) {
        this.f2553a = eVar;
    }

    public final void onDisplayEmpty(int i2) {
        w0.b(i2, "onDisplayEmpty ", "ScVirtualDevice");
        Iterator it = this.f2553a.f2559c.iterator();
        while (it.hasNext()) {
            try {
                a0.a aVar = (a0.a) ((a0.b) it.next());
                aVar.getClass();
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.companion.virtual.IActivityListener");
                    parcelObtain.writeInt(i2);
                    aVar.f464a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            } catch (Exception unused) {
                continue;
            }
        }
    }

    public final void onTopActivityChanged(int i2, ComponentName componentName) {
        v0.a("ScVirtualDevice", "onTopActivityChanged " + i2 + " " + componentName);
        synchronized (this.f2553a.f2559c) {
            Iterator it = this.f2553a.f2559c.iterator();
            while (it.hasNext()) {
                try {
                    ((a0.a) ((a0.b) it.next())).a(i2, componentName, -1);
                } catch (Exception unused) {
                }
            }
        }
    }

    public final void onTopActivityChanged(int i2, ComponentName componentName, int i3) {
        v0.a("ScVirtualDevice", "onTopActivityChanged " + i2 + " " + componentName + " userId " + i3);
        Iterator it = this.f2553a.f2559c.iterator();
        while (it.hasNext()) {
            try {
                ((a0.a) ((a0.b) it.next())).a(i2, componentName, i3);
            } catch (Exception unused) {
            }
        }
    }
}
