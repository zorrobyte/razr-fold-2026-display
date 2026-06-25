package X;

import android.app.ActivityTaskManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.SparseArray;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.mobiledesktop.core.MotoDesktopService;
import e0.TaskStackListenerC0127a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import motorola.core_services.activity.MotoActivityManager;

/* JADX INFO: loaded from: classes.dex */
public final class J0 implements IBinder.DeathRecipient {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MotoDesktopService f274a;

    public J0(MotoDesktopService motoDesktopService) {
        this.f274a = motoDesktopService;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        SparseArray sparseArray;
        v0.e("MotoDesktopService", "binderDied");
        MotoDesktopService motoDesktopService = this.f274a;
        String[] strArr = MotoDesktopService.w0;
        motoDesktopService.l();
        MotoDesktopService motoDesktopService2 = this.f274a;
        if (motoDesktopService2.r != null) {
            v0.a("MotoDesktopService", "releaseAudioRecordInternal");
            motoDesktopService2.r.release();
            motoDesktopService2.r = null;
        }
        v0.a("MotoDesktopService", "releaseAudioTrackInternal");
        motoDesktopService2.k();
        motoDesktopService2.j();
        motoDesktopService2.i();
        Q0.i(false);
        MotorolaSettings.Global.putInt(motoDesktopService2.getApplicationContext().getContentResolver(), "is_rdp_in_recording_call_audio", 0);
        Q0.j(false);
        Q0.h("", -1);
        v0.g("Utils", "setClientBluetoothDeviceAddress = ");
        Q0.f295h = "";
        Q0.k(0, false);
        if (motoDesktopService2.f2243A != null) {
            motoDesktopService2.f2243A = null;
            motoDesktopService2.f2299y.listen(motoDesktopService2.t0, 0);
        }
        Context applicationContext = motoDesktopService2.getApplicationContext();
        if (e0.i.f2485h == null) {
            e0.i.f2485h = new e0.i(applicationContext);
        }
        e0.i iVar = e0.i.f2485h;
        synchronized (((HashMap) iVar.f2491f)) {
            ((HashMap) iVar.f2491f).clear();
        }
        ((ArrayList) iVar.f2490e).clear();
        MotoDesktopService motoDesktopService3 = this.f274a;
        if (motoDesktopService3.m0) {
            motoDesktopService3.m0 = false;
            motoDesktopService3.f2269b.deregisterOnPointerStateChangedListener(motoDesktopService3.n0);
        }
        e0.d.d(this.f274a.getApplicationContext()).i(false, false, "");
        Set setKeySet = this.f274a.f2249G.f2451b.keySet();
        ArrayList<Integer> arrayList = new ArrayList();
        arrayList.addAll(setKeySet);
        for (Integer num : arrayList) {
            MotoDesktopService motoDesktopService4 = this.f274a;
            motoDesktopService4.f2249G.f(motoDesktopService4.q0, num.intValue());
        }
        MotoDesktopService motoDesktopService5 = this.f274a;
        u0 u0Var = motoDesktopService5.f2294t.f2426e;
        if (u0Var != null) {
            ((s0) u0Var).f331a.unlinkToDeath(motoDesktopService5.q0, 0);
        }
        e0.A a2 = this.f274a.f2294t;
        a2.getClass();
        v0.a("A", "setWifiDisplayCallback=null");
        a2.f2426e = null;
        this.f274a.f2294t.i();
        this.f274a.f2295u.e(null);
        e0.i iVar2 = this.f274a.f2295u;
        synchronized (iVar2) {
            e0.j jVar = (e0.j) iVar2.f2491f;
            if (jVar != null) {
                try {
                    try {
                        ((Context) iVar2.f2488c).unregisterReceiver(jVar);
                    } catch (IllegalArgumentException e2) {
                        v0.h("PackageChangeManager", "unregisterOnPackageChangedCallback error", e2);
                    }
                } finally {
                    iVar2.f2491f = null;
                }
            }
        }
        if (i0.a.f2629a) {
            e0.g.a(this.f274a.getApplicationContext()).b();
        }
        MotoDesktopService motoDesktopService6 = this.f274a;
        int i2 = 0;
        while (true) {
            sparseArray = motoDesktopService6.f2272c0;
            if (i2 >= sparseArray.size()) {
                break;
            }
            motoDesktopService6.getApplicationContext().unregisterReceiver((BroadcastReceiver) sparseArray.valueAt(i2));
            i2++;
        }
        sparseArray.clear();
        motoDesktopService6.f2274d0 = 0;
        e0.i iVarB = e0.i.b(this.f274a);
        iVarB.getClass();
        v0.a("MotoTaskManager", "releaseMotoTaskStackListener");
        ((ActivityTaskManager) iVarB.f2486a).unregisterTaskStackListener((TaskStackListenerC0127a) iVarB.f2491f);
        iVarB.f2488c = null;
        e0.i iVarB2 = e0.i.b(this.f274a);
        iVarB2.getClass();
        v0.a("MotoTaskManager", "releaseActivityStateChangedCallback");
        ((MotoActivityManager) iVarB2.f2487b).unregisterActivityStateChangedListener((e0.h) iVarB2.f2490e);
        iVarB2.f2489d = null;
        Iterator it = this.f274a.l0.keySet().iterator();
        while (it.hasNext()) {
            ServiceConnection serviceConnection = (ServiceConnection) this.f274a.l0.get((String) it.next());
            if (serviceConnection != null) {
                this.f274a.unbindService(serviceConnection);
            }
        }
        this.f274a.l0.clear();
        this.f274a.e();
    }
}
