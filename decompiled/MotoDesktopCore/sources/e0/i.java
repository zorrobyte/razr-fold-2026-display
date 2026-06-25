package e0;

import X.C0015f;
import X.C0018g0;
import X.C0021i;
import X.InterfaceC0019h;
import X.InterfaceC0020h0;
import X.InterfaceC0023j;
import X.J0;
import X.L;
import X.v0;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import com.motorola.mobiledesktop.core.MotoDesktopService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import motorola.core_services.activity.MotoActivityManager;

/* JADX INFO: loaded from: classes.dex */
public final class i {

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static i f2484g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public static i f2485h;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Object f2486a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object f2487b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Object f2488c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public Object f2489d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public Object f2490e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public Object f2491f;

    public i(Context context) {
        Handler handler = new Handler(Looper.getMainLooper());
        this.f2489d = "";
        this.f2490e = new ArrayList();
        this.f2491f = new HashMap();
        Y.j jVar = new Y.j(0, this);
        this.f2486a = context;
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        this.f2487b = audioManager;
        audioManager.registerAudioPlaybackCallback(jVar, handler);
        this.f2488c = (TelecomManager) context.getSystemService("telecom");
    }

    public static synchronized i b(MotoDesktopService motoDesktopService) {
        try {
            if (f2484g == null) {
                synchronized (i.class) {
                    try {
                        if (f2484g == null) {
                            i iVar = new i();
                            iVar.f2490e = new h(iVar);
                            iVar.f2491f = new TaskStackListenerC0127a(1, iVar);
                            Context applicationContext = motoDesktopService.getApplicationContext();
                            iVar.f2486a = (ActivityTaskManager) applicationContext.getSystemService("activity_task");
                            iVar.f2487b = MotoActivityManager.getInstance(applicationContext);
                            f2484g = iVar;
                        }
                    } finally {
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return f2484g;
    }

    public void a(List list, boolean z2) {
        int mode;
        int usage;
        try {
            mode = ((AudioManager) this.f2487b).getMode();
        } catch (Exception unused) {
            v0.a("AudioUtils", "getAudioMode error return MODE_INVALID");
            mode = -2;
        }
        if (mode == 2 || mode == 1) {
            f(((TelecomManager) this.f2488c).getDefaultDialerPackage(), z2);
            return;
        }
        Iterator it = list.iterator();
        String nameForUid = "";
        while (it.hasNext()) {
            AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) it.next();
            if (audioPlaybackConfiguration.isActive() && audioPlaybackConfiguration.getClientUid() != 1000 && ((usage = audioPlaybackConfiguration.getAudioAttributes().getUsage()) == 2 || usage == 3 || usage == 1 || usage == 14)) {
                int clientUid = audioPlaybackConfiguration.getClientUid();
                boolean zIsEmpty = TextUtils.isEmpty(nameForUid);
                Context context = (Context) this.f2486a;
                if (zIsEmpty) {
                    nameForUid = context.getPackageManager().getNameForUid(clientUid);
                } else {
                    nameForUid = nameForUid + ";" + context.getPackageManager().getNameForUid(clientUid);
                }
            }
        }
        f(nameForUid, z2);
    }

    public void c(J0 j02, InterfaceC0019h interfaceC0019h) {
        v0.a("MotoTaskManager", "setActivityStateChangedCallback" + interfaceC0019h);
        h hVar = (h) this.f2490e;
        if (interfaceC0019h == null) {
            v0.a("MotoTaskManager", "releaseActivityStateChangedCallback");
            ((MotoActivityManager) this.f2487b).unregisterActivityStateChangedListener(hVar);
            this.f2489d = null;
        } else {
            ((MotoActivityManager) this.f2487b).registerActivityStateChangedListener(hVar);
            this.f2489d = interfaceC0019h;
            try {
                ((C0015f) interfaceC0019h).f321a.linkToDeath(j02, 0);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void d(J0 j02, InterfaceC0020h0 interfaceC0020h0) {
        v0.a("MotoTaskManager", "setMotoTaskStackListener" + interfaceC0020h0);
        TaskStackListenerC0127a taskStackListenerC0127a = (TaskStackListenerC0127a) this.f2491f;
        if (interfaceC0020h0 == null) {
            v0.a("MotoTaskManager", "releaseMotoTaskStackListener");
            ((ActivityTaskManager) this.f2486a).unregisterTaskStackListener(taskStackListenerC0127a);
            this.f2488c = null;
        } else {
            ((ActivityTaskManager) this.f2486a).registerTaskStackListener(taskStackListenerC0127a);
            this.f2488c = interfaceC0020h0;
            try {
                ((C0018g0) interfaceC0020h0).f322a.linkToDeath(j02, 0);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    public synchronized void e(L l2) {
        try {
            v0.a("PackageChangeManager", "setPackageChangeCallback=" + l2);
            this.f2486a = l2;
            if (l2 == null) {
                j jVar = (j) this.f2489d;
                if (jVar != null) {
                    ((Context) this.f2488c).unregisterReceiver(jVar);
                    ((Context) this.f2488c).unregisterReceiver((j) this.f2490e);
                    this.f2490e = null;
                    this.f2489d = null;
                }
            } else if (((j) this.f2489d) == null) {
                this.f2489d = new j(this, 0);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
                intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                intentFilter.addDataScheme("package");
                ((Context) this.f2488c).registerReceiverForAllUsers((j) this.f2489d, intentFilter, null, null);
                IntentFilter intentFilter2 = new IntentFilter();
                this.f2490e = new j(this, 1);
                intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
                intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
                intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_ADDED");
                intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_REMOVED");
                ((Context) this.f2488c).registerReceiverForAllUsers((j) this.f2490e, intentFilter2, null, null);
                v0.a("PackageChangeManager", "setPackageChangeCallback registerReceiverForAllUsers");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void f(String str, boolean z2) {
        boolean zEquals;
        if (z2) {
            String str2 = (String) this.f2489d;
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                zEquals = TextUtils.equals(str, str2);
            } else {
                String[] strArrSplit = str.split(";");
                String[] strArrSplit2 = str2.split(";");
                if (strArrSplit.length != strArrSplit2.length) {
                    zEquals = false;
                    break;
                }
                for (String str3 : strArrSplit) {
                    if (!Arrays.asList(strArrSplit2).contains(str3)) {
                        zEquals = false;
                        break;
                    }
                }
                zEquals = true;
            }
            if (zEquals) {
                return;
            }
        }
        this.f2489d = str;
        v0.a("AudioPlaybackListener", "updateAudioPlaybackPackageNameList " + ((String) this.f2489d));
        try {
            for (InterfaceC0023j interfaceC0023j : (ArrayList) this.f2490e) {
                String str4 = (String) this.f2489d;
                C0021i c0021i = (C0021i) interfaceC0023j;
                c0021i.getClass();
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IAudioPbPkgNameListChangeListener");
                    parcelObtain.writeString(str4);
                    c0021i.f323a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            }
        } catch (RemoteException unused) {
        }
    }
}
