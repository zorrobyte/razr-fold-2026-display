package Y;

import X.P0;
import X.Q0;
import X.v0;
import X.w0;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.hardware.display.DisplayManager;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.os.UserManager;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.motorola.android.provider.MotorolaSettings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

/* JADX INFO: loaded from: classes.dex */
public final class i {

    /* JADX INFO: renamed from: S, reason: collision with root package name */
    public static i f369S;

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public boolean f370A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public boolean f371B;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public Timer f372C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public final TelecomManager f373D;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public int f374E;

    /* JADX INFO: renamed from: F, reason: collision with root package name */
    public boolean f375F;

    /* JADX INFO: renamed from: G, reason: collision with root package name */
    public boolean f376G;

    /* JADX INFO: renamed from: H, reason: collision with root package name */
    public boolean f377H;

    /* JADX INFO: renamed from: I, reason: collision with root package name */
    public boolean f378I;

    /* JADX INFO: renamed from: J, reason: collision with root package name */
    public boolean f379J;

    /* JADX INFO: renamed from: K, reason: collision with root package name */
    public boolean f380K;

    /* JADX INFO: renamed from: L, reason: collision with root package name */
    public boolean f381L;

    /* JADX INFO: renamed from: M, reason: collision with root package name */
    public boolean f382M;

    /* JADX INFO: renamed from: N, reason: collision with root package name */
    public int f383N;

    /* JADX INFO: renamed from: O, reason: collision with root package name */
    public String f384O;

    /* JADX INFO: renamed from: P, reason: collision with root package name */
    public boolean f385P;

    /* JADX INFO: renamed from: Q, reason: collision with root package name */
    public final HandlerThread f386Q;

    /* JADX INFO: renamed from: R, reason: collision with root package name */
    public final Handler f387R;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final g f388a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Object f389b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final String f390c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final ArrayList f391d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final Context f392e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final MediaRouter2Manager f393f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public List f394g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final ArrayList f395h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f396i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public int f397j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final AudioManager f398k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public boolean f399l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public boolean f400m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public int f401n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public BluetoothHeadset f402o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public BluetoothAdapter f403p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public List f404q;
    public String r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public int f405s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public boolean f406t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public String f407u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public final DisplayManager f408v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public int f409w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public int f410x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public boolean f411y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public boolean f412z;

    public i(Context context) {
        f fVar = new f(this);
        this.f388a = new g(this);
        this.f389b = new Object();
        this.f391d = new ArrayList();
        this.f395h = new ArrayList();
        this.f396i = false;
        this.f397j = -1;
        this.f399l = false;
        this.f400m = false;
        this.f401n = 0;
        this.f402o = null;
        this.f403p = null;
        this.r = "";
        this.f405s = -1;
        this.f406t = true;
        this.f407u = "";
        this.f409w = -1;
        this.f410x = -1;
        this.f411y = false;
        this.f412z = true;
        this.f370A = false;
        this.f371B = false;
        this.f374E = -1;
        this.f375F = false;
        this.f376G = false;
        this.f377H = false;
        this.f378I = false;
        this.f379J = false;
        this.f380K = false;
        this.f381L = false;
        this.f382M = false;
        this.f383N = -1;
        this.f384O = "";
        this.f385P = false;
        this.f392e = context;
        this.f390c = context.getPackageName();
        MediaRouter2Manager mediaRouter2Manager = MediaRouter2Manager.getInstance(context);
        this.f393f = mediaRouter2Manager;
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        this.f398k = audioManager;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        this.f408v = displayManager;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.f373D = (TelecomManager) context.getSystemService("telecom");
        HandlerThread handlerThread = new HandlerThread("SCAudioThread");
        this.f386Q = handlerThread;
        handlerThread.start();
        this.f387R = new Handler(this.f386Q.getLooper());
        new q(context, audioManager, mediaRouter2Manager, displayManager, telephonyManager).f423c.add(fVar);
        this.f396i = Q0.f288a;
        this.f375F = r.T(audioManager.getActiveRecordingConfigurations());
        if (!r.N()) {
            MotorolaSettings.Secure.putInt(context.getContentResolver(), "is_allow_rf_audio_input", 0);
        }
        this.f376G = MotorolaSettings.Secure.getInt(context.getContentResolver(), "is_allow_rf_audio_input", 1) != 1;
        m();
        r();
    }

    public static void a(i iVar) {
        iVar.getClass();
        if (r.L()) {
            synchronized (iVar.f389b) {
                try {
                    int i2 = iVar.f397j;
                    if (i2 >= 0 && i2 < iVar.f395h.size()) {
                        t tVar = (t) iVar.f395h.get(iVar.f397j);
                        if (tVar instanceof s) {
                            int i3 = ((s) tVar).f436j;
                            iVar.f383N = i3;
                            if (i3 == 3) {
                                iVar.f384O = ((s) tVar).f439m;
                            }
                        } else {
                            int iC = tVar.c();
                            iVar.f383N = iC;
                            if (r.P(iC)) {
                                iVar.f384O = tVar.f441b.getAddress();
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            v0.a("i", "savePreDeviceState preDeviceType = " + iVar.f383N + " preDeviceAddress = " + iVar.f384O);
        }
        if (iVar.f399l) {
            iVar.i(false);
            return;
        }
        if (iVar.f396i) {
            MotorolaSettings.Secure.putInt(iVar.f392e.getContentResolver(), "is_allow_rdp_audio_output", 0);
        }
        if (r.r0(iVar.f408v)) {
            r.i0("persist.desktop.allow_wireless_media", "false");
        }
        new Timer().schedule(new C0050d(iVar, 1), 1500L);
    }

    public static void b(i iVar, int i2) {
        Handler handler = iVar.f387R;
        if (handler != null) {
            handler.postDelayed(new RunnableC0049c(0, iVar), i2);
        }
    }

    public static i u(Context context) {
        if (f369S == null) {
            f369S = new i(context);
        }
        return f369S;
    }

    public final void A(int i2) {
        if (i2 == -1 || !r.N()) {
            return;
        }
        w0.b(i2, "toastUseClientMic displayId = ", "i");
        this.f374E = i2;
        Iterator it = this.f391d.iterator();
        while (it.hasNext()) {
            ((h) it.next()).c();
        }
    }

    public final void B() {
        new Thread(new F.e(3, this)).start();
    }

    public final void c(int i2) {
        boolean zL = r.L();
        DisplayManager displayManager = this.f408v;
        if (zL) {
            w0.b(i2, "audioRouteToDesktop ", "i");
            if (!this.f399l) {
                if (i2 == 1000 && this.f396i) {
                    v(1);
                }
                if (i2 == 1001 && r.r0(displayManager)) {
                    v(3);
                    return;
                }
                return;
            }
            if (i2 == 5 && this.f396i) {
                j(1);
            }
            if (i2 == 4 && r.R(this.f399l, this.f400m)) {
                j(2);
            }
            if (i2 == 6 && r.V(this.f399l, this.f400m) && r.r0(displayManager)) {
                j(3);
                return;
            }
            return;
        }
        w0.b(i2, "audioRouteToDesktop ", "i");
        boolean z2 = this.f399l;
        Context context = this.f392e;
        if (!z2) {
            if (i2 == 1000 && this.f396i) {
                MotorolaSettings.Secure.putInt(context.getContentResolver(), "is_allow_rdp_audio_output", 1);
                x();
            }
            if (i2 == 1001 && r.r0(displayManager)) {
                r.i0("persist.desktop.allow_wireless_media", "true");
                new Thread(new e()).start();
                return;
            }
            return;
        }
        AudioManager audioManager = this.f398k;
        if (i2 == 5 && this.f396i) {
            if (this.f400m) {
                AudioDeviceInfo communicationDevice = audioManager.getCommunicationDevice();
                if (communicationDevice != null) {
                    this.f401n = communicationDevice.getType();
                }
                if (r.P(this.f401n)) {
                    v0.a("i", "audioRouteToDesktop current is BT");
                    try {
                        Thread.sleep(600L);
                    } catch (InterruptedException unused) {
                    }
                    p();
                }
            }
            r.i0("persist.desktop.allow_rdp_call", "true");
            if (MotorolaSettings.Secure.getInt(context.getContentResolver(), "is_allow_rdp_audio_output", 0) == 1) {
                B();
            } else {
                MotorolaSettings.Secure.putInt(context.getContentResolver(), "is_allow_rdp_audio_output", 1);
                x();
            }
        }
        if (i2 == 4 && r.R(this.f399l, this.f400m)) {
            AudioDeviceInfo[] devices = audioManager.getDevices(2);
            int length = devices.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                if (devices[i3].getType() != 9) {
                    i3++;
                } else if (!r.s("persist.desktop.allow_hdmi_call", false)) {
                    r.i0("persist.desktop.allow_hdmi_call", "true");
                    B();
                }
            }
        }
        if (i2 == 6 && r.V(this.f399l, this.f400m) && r.r0(displayManager) && !r.s("persist.desktop.allow_wireless_call", false)) {
            r.i0("persist.desktop.allow_wireless_call", "true");
            B();
        }
    }

    public final void d(boolean z2) {
        int clientUid;
        int iU;
        ApplicationInfo applicationInfo;
        int usage;
        boolean z3 = this.f400m;
        Context context = this.f392e;
        DisplayManager displayManager = this.f408v;
        int i2 = -1;
        if (z3) {
            String defaultDialerPackage = this.f373D.getDefaultDialerPackage();
            v0.a("AudioUtils", "getLastDialCallDisplayId " + defaultDialerPackage);
            if (defaultDialerPackage != null) {
                for (ActivityManager.RunningTaskInfo runningTaskInfo : ActivityTaskManager.getInstance().getTasks(100)) {
                    ComponentName componentName = runningTaskInfo.topActivity;
                    if (componentName != null && defaultDialerPackage.equals(componentName.getPackageName())) {
                        i2 = runningTaskInfo.displayId;
                        if (r.u(displayManager, i2, context) != 0) {
                            break;
                        }
                    }
                }
            }
            iU = r.u(displayManager, i2, context);
            v0.a("i", "autoRoutCallAudioByDisplay dial call displayId = " + i2 + " displayType = " + iU);
        } else {
            try {
                Thread.sleep(800);
            } catch (InterruptedException unused) {
            }
            for (AudioPlaybackConfiguration audioPlaybackConfiguration : this.f398k.getActivePlaybackConfigurations()) {
                if (audioPlaybackConfiguration.isActive() && audioPlaybackConfiguration.getClientUid() != 1000 && ((usage = audioPlaybackConfiguration.getAudioAttributes().getUsage()) == 2 || usage == 3)) {
                    clientUid = audioPlaybackConfiguration.getClientUid();
                    break;
                }
            }
            clientUid = -1;
            if (clientUid != -1) {
                v0.a("AudioUtils", "getLastIpCallDisplayId uid = " + clientUid);
                Iterator it = ActivityTaskManager.getInstance().getTasks(100).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) it.next();
                    ActivityInfo activityInfo = runningTaskInfo2.topActivityInfo;
                    if (activityInfo != null && (applicationInfo = activityInfo.applicationInfo) != null && clientUid == applicationInfo.uid) {
                        i2 = runningTaskInfo2.displayId;
                        break;
                    }
                }
            } else {
                Iterator it2 = ActivityTaskManager.getInstance().getTasks(100).iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) it2.next();
                    ActivityInfo activityInfo2 = runningTaskInfo3.topActivityInfo;
                    if (activityInfo2 != null && activityInfo2.applicationInfo != null) {
                        v0.a("AudioUtils", "getLastIpCallDisplayId uid is null use first running task uid = " + runningTaskInfo3.topActivityInfo.applicationInfo.uid);
                        i2 = runningTaskInfo3.displayId;
                        break;
                    }
                }
            }
            iU = r.u(displayManager, i2, context);
            v0.a("i", "autoRoutCallAudioByDisplay ip call displayId = " + i2 + " displayType = " + iU);
        }
        if (r.L()) {
            j(iU);
            if (iU == 1 && z2 && !this.f376G) {
                A(i2);
                return;
            }
            return;
        }
        if (iU == 0) {
            h();
            return;
        }
        if (iU == 1) {
            c(5);
            if (!z2 || this.f376G) {
                return;
            }
            A(i2);
            return;
        }
        if (iU == 2) {
            c(4);
        } else if (iU == 3) {
            c(6);
        }
    }

    public final void e(List list) {
        int usage;
        if (this.f399l || !this.f396i || this.f378I) {
            return;
        }
        synchronized (this.f389b) {
            try {
                if (r.L()) {
                    if (r.o(this.f395h, this.f397j, Q0.f295h)) {
                        return;
                    }
                } else if (r.n(this.f395h, this.f397j)) {
                    return;
                }
                DisplayManager displayManager = this.f408v;
                Context context = this.f392e;
                List<ActivityManager.RunningTaskInfo> tasks = ActivityTaskManager.getInstance().getTasks(100);
                Iterator it = list.iterator();
                int iU = -1;
                loop0: while (it.hasNext()) {
                    AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) it.next();
                    if (audioPlaybackConfiguration.isActive() && audioPlaybackConfiguration.getClientUid() != 1000 && ((usage = audioPlaybackConfiguration.getAudioAttributes().getUsage()) == 1 || usage == 14)) {
                        for (ActivityManager.RunningTaskInfo runningTaskInfo : tasks) {
                            ActivityInfo activityInfo = runningTaskInfo.topActivityInfo;
                            if (activityInfo != null && activityInfo.applicationInfo != null && audioPlaybackConfiguration.getClientUid() == runningTaskInfo.topActivityInfo.applicationInfo.uid && (iU = r.u(displayManager, runningTaskInfo.displayId, context)) != 0) {
                                break loop0;
                            }
                        }
                    }
                }
                v(iU);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void f() {
        int iC;
        synchronized (this.f389b) {
            try {
                for (t tVar : this.f395h) {
                    if (!(tVar instanceof s)) {
                        int iC2 = tVar.c();
                        if (iC2 == 9 || iC2 == 1000 || iC2 == 1001) {
                            iC = tVar.c();
                            break;
                        }
                    } else {
                        int i2 = ((s) tVar).f436j;
                        if (i2 == 4 || i2 == 5 || i2 == 6) {
                            iC = ((s) tVar).f436j;
                            break;
                        }
                    }
                }
                iC = -1;
            } catch (Throwable th) {
                throw th;
            }
        }
        c(iC);
    }

    public final void g(int i2) {
        p();
        AudioManager audioManager = this.f398k;
        List<AudioDeviceInfo> availableCommunicationDevices = audioManager.getAvailableCommunicationDevices();
        g gVar = this.f388a;
        if (i2 != 0) {
            if (i2 == 2) {
                for (AudioDeviceInfo audioDeviceInfo : availableCommunicationDevices) {
                    if (r.S(audioDeviceInfo.getType())) {
                        audioManager.setCommunicationDevice(audioDeviceInfo);
                        this.f401n = audioDeviceInfo.getType();
                        gVar.c();
                        return;
                    }
                }
                return;
            }
            return;
        }
        if (!audioManager.isWiredHeadsetOn()) {
            for (AudioDeviceInfo audioDeviceInfo2 : availableCommunicationDevices) {
                if (audioDeviceInfo2.getType() == 1) {
                    audioManager.setCommunicationDevice(audioDeviceInfo2);
                    this.f401n = 1;
                    gVar.c();
                    return;
                }
            }
            return;
        }
        for (AudioDeviceInfo audioDeviceInfo3 : availableCommunicationDevices) {
            if (audioDeviceInfo3.getType() == 2) {
                audioManager.setCommunicationDevice(audioDeviceInfo3);
                this.f401n = 2;
                if (gVar.b()) {
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException unused) {
                    }
                    gVar.c();
                    return;
                }
                return;
            }
        }
    }

    public final void h() {
        if (r.L()) {
            i(true);
        } else {
            i(false);
        }
    }

    public final void i(boolean z2) {
        int iIndexOf;
        int i2 = 0;
        boolean zS = r.s("persist.desktop.allow_hdmi_call", false);
        boolean zS2 = r.s("persist.desktop.allow_wireless_call", false);
        r.i0("persist.desktop.allow_hdmi_call", "false");
        r.i0("persist.desktop.allow_rdp_call", "false");
        r.i0("persist.desktop.allow_wireless_call", "false");
        MotorolaSettings.Secure.putInt(this.f392e.getContentResolver(), "is_allow_rdp_audio_output", 0);
        if (zS) {
            B();
        }
        if (this.f411y && zS2) {
            B();
        }
        if (this.f396i && z2) {
            synchronized (this.f389b) {
                try {
                    if (r.p(this.f395h, this.f397j, Q0.f295h)) {
                        String str = "";
                        boolean z3 = false;
                        boolean z4 = false;
                        for (t tVar : this.f395h) {
                            if (tVar instanceof s) {
                                int type = ((s) tVar).f441b.getType();
                                if (type == 3) {
                                    String str2 = ((s) tVar).f439m;
                                    if (str2 != null && !str2.equalsIgnoreCase(Q0.f295h)) {
                                        if (TextUtils.isEmpty(str) || str2.equalsIgnoreCase(this.f407u)) {
                                            str = str2;
                                        }
                                        z3 = true;
                                    }
                                } else if (type == 2) {
                                    z4 = true;
                                }
                            }
                        }
                        if (z3) {
                            i2 = 3;
                        } else if (z4) {
                            i2 = 2;
                        }
                        v0.a("i", "backClientBluetooth cur routing is Client bluetooth, back to type = " + i2 + " deviceAddress = " + str);
                        Iterator it = this.f395h.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                iIndexOf = -1;
                                break;
                            }
                            t tVar2 = (t) it.next();
                            if ((tVar2 instanceof s) && ((s) tVar2).f441b.getType() == i2) {
                                if (i2 == 3) {
                                    if (str != null && str.equalsIgnoreCase(((s) tVar2).f439m)) {
                                        iIndexOf = this.f395h.indexOf(tVar2);
                                        break;
                                    }
                                } else {
                                    iIndexOf = this.f395h.indexOf(tVar2);
                                    break;
                                }
                            }
                        }
                        if (iIndexOf != -1 && iIndexOf != this.f397j) {
                            v0.a("i", "backClientBluetooth " + iIndexOf + " mConnectDeviceIndex = " + this.f397j);
                            n(iIndexOf, true);
                        }
                    }
                } finally {
                }
            }
        }
    }

    public final void j(int i2) {
        if (!this.f399l || i2 == -1) {
            return;
        }
        if (i2 == 0) {
            h();
            return;
        }
        synchronized (this.f389b) {
            try {
                int iIndexOf = -1;
                for (t tVar : this.f395h) {
                    if (tVar instanceof s) {
                        int i3 = ((s) tVar).f436j;
                        if (i2 != 1) {
                            if (i2 != 2) {
                                if (i2 == 3 && i3 == 6) {
                                    iIndexOf = this.f395h.indexOf(tVar);
                                }
                            } else if (i3 == 4) {
                                iIndexOf = this.f395h.indexOf(tVar);
                            }
                        } else if (this.f382M) {
                            if (i3 == 3 && r.Q(((s) tVar).f439m)) {
                                iIndexOf = this.f395h.indexOf(tVar);
                            }
                        } else if (i3 == 5) {
                            iIndexOf = this.f395h.indexOf(tVar);
                        }
                    }
                }
                if (iIndexOf != -1 && iIndexOf != this.f397j) {
                    v0.a("i", "callSoundRoteTo " + iIndexOf + " mConnectDeviceIndex = " + this.f397j);
                    n(iIndexOf, true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean k(boolean z2) {
        boolean zF;
        boolean zE;
        if (this.f399l) {
            return false;
        }
        synchronized (this.f389b) {
            this.f399l = true;
        }
        this.f406t = MotorolaSettings.Secure.getInt(this.f392e.getContentResolver(), "is_allow_rdp_audio_output", 0) == 1;
        v0.a("i", "mMediaRouteInRdpBeforeCall = " + this.f406t);
        this.f407u = "";
        this.f380K = false;
        synchronized (this.f389b) {
            try {
                int i2 = this.f397j;
                if (i2 >= 0 && i2 < this.f395h.size()) {
                    MediaRoute2Info mediaRoute2Info = ((t) this.f395h.get(this.f397j)).f441b;
                    if (r.P(mediaRoute2Info.getType())) {
                        this.f407u = mediaRoute2Info.getId();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.f379J = false;
        if (!r.L()) {
            synchronized (this.f389b) {
                zE = r.e(this.f395h);
            }
            if (zE) {
                v0.a("i", "begin call, but audio in BT or headset, not auto change");
                h();
            } else {
                d(true);
            }
        }
        w(true);
        if (z2) {
            x();
        }
        if (r.L()) {
            synchronized (this.f389b) {
                zF = r.f(this.f395h, Q0.f295h);
            }
            if (zF) {
                v0.a("i", "begin call, but audio in BT or headset, not auto change");
                h();
            } else {
                d(true);
            }
        }
        return true;
    }

    public final boolean l(boolean z2) {
        List list;
        BluetoothDevice bluetoothDevice;
        if (!this.f399l) {
            return false;
        }
        synchronized (this.f389b) {
            this.f399l = false;
        }
        v0.a("i", "releaseMediaRouteAfterCall mMediaRouteInRdpBeforeCall = " + this.f406t);
        if (this.f406t) {
            MotorolaSettings.Secure.putInt(this.f392e.getContentResolver(), "is_allow_rdp_audio_output", 1);
        } else {
            MotorolaSettings.Secure.putInt(this.f392e.getContentResolver(), "is_allow_rdp_audio_output", 0);
        }
        this.f379J = false;
        if (this.f380K) {
            r.h0(this.f398k, 0, this.f400m);
        }
        this.f380K = false;
        if (this.f377H && !this.f400m && this.f403p != null && (list = this.f404q) != null && list.size() > 0) {
            synchronized (this.f389b) {
                int i2 = this.f397j;
                if (i2 >= 0 && i2 < this.f395h.size()) {
                    t tVar = (t) this.f395h.get(this.f397j);
                    if (tVar instanceof s) {
                        s sVar = (s) tVar;
                        if (sVar.f436j == 3) {
                            Iterator it = this.f404q.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    bluetoothDevice = null;
                                    break;
                                }
                                bluetoothDevice = (BluetoothDevice) it.next();
                                if (bluetoothDevice.getAddress().equalsIgnoreCase(sVar.f439m)) {
                                    break;
                                }
                            }
                            if (bluetoothDevice != null) {
                                v0.a("i", "activeBtAudio = " + bluetoothDevice.getAddress());
                                p();
                                this.f403p.setActiveDevice(bluetoothDevice, 2);
                            }
                        }
                    }
                    Iterator it2 = this.f404q.iterator();
                    if (it2.hasNext()) {
                        BluetoothDevice bluetoothDevice2 = (BluetoothDevice) it2.next();
                        v0.a("i", "activeBtAudio = " + bluetoothDevice2.getAddress());
                        this.f403p.setActiveDevice(bluetoothDevice2, 2);
                    }
                }
            }
        }
        AudioManager audioManager = this.f398k;
        if (audioManager != null) {
            audioManager.clearCommunicationDevice();
        }
        g gVar = this.f388a;
        gVar.f361a = -1;
        gVar.f362b = -1;
        gVar.f363c = -1;
        gVar.f364d = -1;
        gVar.f365e = -1;
        gVar.f366f = -1;
        gVar.f367g = -1;
        w(false);
        if (z2) {
            x();
        }
        if ((this.f377H || r.L()) && ((!this.f396i || !this.f406t) && !this.f407u.isEmpty())) {
            synchronized (this.f389b) {
                try {
                    Iterator it3 = this.f395h.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        t tVar2 = (t) it3.next();
                        if (!(tVar2 instanceof s)) {
                            if (r.P(tVar2.f441b.getType()) && this.f407u.equals(tVar2.f441b.getId())) {
                                tVar2.f442c.transfer(tVar2.f443d, tVar2.f441b, Process.myUserHandle());
                                break;
                            }
                        }
                    }
                    this.f407u = "";
                } finally {
                }
            }
        }
        this.f377H = false;
        return true;
    }

    public final void m() {
        boolean z2 = this.f412z;
        Bundle userRestrictions = ((UserManager) this.f392e.getSystemService("user")).getUserRestrictions();
        if (userRestrictions != null) {
            z2 = !userRestrictions.getBoolean("no_adjust_volume");
        }
        v0.a("i", "checkIfAllowAdjustVolume = " + z2);
        if (this.f412z != z2) {
            this.f412z = z2;
            synchronized (this.f389b) {
                try {
                    Iterator it = this.f391d.iterator();
                    while (it.hasNext()) {
                        ((h) it.next()).a(this.f395h, this.f397j, this.f399l);
                    }
                } finally {
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:137:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x019f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean n(int r17, boolean r18) {
        /*
            Method dump skipped, instruction units count: 704
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: Y.i.n(int, boolean):boolean");
    }

    public final boolean o(int i2, boolean z2) {
        if (this.f397j == i2) {
            return true;
        }
        synchronized (this.f389b) {
            try {
                if (i2 >= this.f395h.size()) {
                    return false;
                }
                t tVar = (t) this.f395h.get(i2);
                if (this.f396i) {
                    if (tVar instanceof C) {
                        if (MotorolaSettings.Secure.getInt(this.f392e.getContentResolver(), "is_allow_rdp_audio_output", 0) != 1) {
                            Q0.i(true);
                        }
                        MotorolaSettings.Secure.putInt(this.f392e.getContentResolver(), "is_allow_rdp_audio_output", 1);
                        this.f397j = i2;
                    } else {
                        Q0.i(false);
                        MotorolaSettings.Secure.putInt(this.f392e.getContentResolver(), "is_allow_rdp_audio_output", 0);
                    }
                }
                if (!this.f411y) {
                    r.n0(false);
                } else if (tVar instanceof D) {
                    r.i0("persist.desktop.allow_wireless_media", "true");
                    r.n0(true);
                } else {
                    r.i0("persist.desktop.allow_wireless_media", "false");
                    r.n0(false);
                }
                if (!z2) {
                    this.f378I = true;
                }
                tVar.getClass();
                tVar.f442c.transfer(tVar.f443d, tVar.f441b, Process.myUserHandle());
                AudioManager audioManager = this.f398k;
                if (audioManager != null && audioManager.isMusicActive()) {
                    synchronized (this.f389b) {
                        int i3 = this.f397j;
                        if (i3 >= 0 && i3 < this.f395h.size()) {
                            t tVar2 = (t) this.f395h.get(this.f397j);
                            if ((tVar instanceof D) || (tVar2 instanceof D) || (tVar instanceof C) || (tVar2 instanceof C)) {
                                new Thread(new e()).start();
                            }
                        }
                    }
                }
                new Timer().schedule(new C0050d(this, 0), 500L);
                this.f397j = i2;
                return true;
            } finally {
            }
        }
    }

    public final void p() {
        if (this.f402o == null || this.f403p == null) {
            return;
        }
        synchronized (this.f389b) {
            try {
                try {
                    if (this.f403p.getActiveDevices(1) != null) {
                        this.f403p.removeActiveDevice(1);
                    }
                    this.f402o.stopScoUsingVirtualVoiceCall();
                    this.f402o.disconnectAudio();
                    this.f377H = true;
                } catch (Exception unused) {
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int q() {
        try {
            return this.f398k.getMode();
        } catch (Exception unused) {
            v0.a("i", "getAudioMode error return MODE_INVALID");
            return -2;
        }
    }

    public final void r() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.f403p = defaultAdapter;
        if (defaultAdapter == null) {
            return;
        }
        try {
            if (defaultAdapter.getSupportedProfiles().contains(1)) {
                this.f403p.getProfileProxy(this.f392e, new P0(1, this), 1);
                this.f381L = true;
            }
        } catch (Exception unused) {
        }
    }

    public final int s(ArrayList arrayList) {
        int type;
        boolean zS = r.s("persist.desktop.allow_wireless_media", true);
        if (this.f411y && zS) {
            r.n0(true);
        } else {
            r.n0(false);
        }
        if (!r.r0(this.f408v) && !zS) {
            r.i0("persist.desktop.allow_wireless_media", "true");
        }
        if (this.f396i && MotorolaSettings.Secure.getInt(this.f392e.getContentResolver(), "is_allow_rdp_audio_output", 0) == 1) {
            for (Object obj : arrayList) {
                if (obj instanceof C) {
                    return arrayList.indexOf(obj);
                }
            }
        }
        AudioAttributes audioAttributesBuild = new AudioAttributes.Builder().setUsage(1).build();
        AudioDeviceAttributes[] audioDeviceAttributesArr = new AudioDeviceAttributes[1];
        AudioManager audioManager = this.f398k;
        audioManager.getDevicesForAttributes(audioAttributesBuild).toArray(audioDeviceAttributesArr);
        if (audioDeviceAttributesArr[0] == null) {
            type = 0;
        } else {
            v0.a("i", "getCurRoutingDevice type =" + audioDeviceAttributesArr[0].getType() + " device = " + audioDeviceAttributesArr[0]);
            type = audioDeviceAttributesArr[0].getType();
        }
        String address = "";
        if (r.P(type)) {
            List devicesForAttributes = audioManager.getDevicesForAttributes(new AudioAttributes.Builder().setUsage(1).build());
            if (devicesForAttributes == null || devicesForAttributes.isEmpty()) {
                v0.a("i", "getCurRoutingDeviceAddress: No devices found for media attributes.");
            } else {
                AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) devicesForAttributes.get(0);
                if (audioDeviceAttributes == null || audioDeviceAttributes.getAddress() == null) {
                    v0.a("i", "getCurRoutingDeviceAddress: first device is null or address is null");
                } else {
                    address = audioDeviceAttributes.getAddress();
                }
            }
        }
        List routingSessions = this.f393f.getRoutingSessions(this.f390c);
        List<String> selectedRoutes = ((RoutingSessionInfo) routingSessions.get(routingSessions.size() - 1)).getSelectedRoutes();
        String str = selectedRoutes.get(selectedRoutes.size() - 1);
        v0.a("i", "currentSelectedRoutes = " + str);
        if (this.f411y && zS) {
            for (Object obj2 : arrayList) {
                if (obj2 instanceof D) {
                    return arrayList.indexOf(obj2);
                }
            }
        }
        if (!this.f411y) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                t tVar = (t) it.next();
                if (tVar.f441b.getType() == type ? r.P(type) ? address.equals(tVar.f441b.getAddress()) : true : false) {
                    return arrayList.indexOf(tVar);
                }
            }
        }
        if (this.f411y) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                t tVar2 = (t) it2.next();
                if (tVar2.f441b.getType() == type) {
                    if (tVar2.h()) {
                        if (!zS || (tVar2 instanceof D)) {
                            if (zS || !(tVar2 instanceof D)) {
                            }
                        }
                    }
                    MediaRoute2Info mediaRoute2Info = tVar2.f441b;
                    if (mediaRoute2Info.getType() == type ? r.P(type) ? address.equals(mediaRoute2Info.getAddress()) : true : false) {
                        return arrayList.indexOf(tVar2);
                    }
                }
            }
        }
        if (!this.f411y) {
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                t tVar3 = (t) it3.next();
                if (tVar3.f441b.getId().equals(str)) {
                    return arrayList.indexOf(tVar3);
                }
            }
        }
        if (this.f411y) {
            Iterator it4 = arrayList.iterator();
            while (it4.hasNext()) {
                t tVar4 = (t) it4.next();
                if (tVar4.f441b.getId().equals(str)) {
                    if (tVar4.h()) {
                        if (!zS || (tVar4 instanceof D)) {
                            if (zS || !(tVar4 instanceof D)) {
                            }
                        }
                    }
                    return arrayList.indexOf(tVar4);
                }
            }
        }
        return 0;
    }

    public final int t(ArrayList arrayList) {
        int type;
        List list;
        String address;
        boolean zS = r.s("persist.desktop.allow_hdmi_call", false);
        boolean zS2 = r.s("persist.desktop.allow_rdp_call", false);
        boolean zS3 = r.s("persist.desktop.allow_wireless_call", false);
        AudioDeviceInfo communicationDevice = this.f398k.getCommunicationDevice();
        if (communicationDevice != null) {
            type = communicationDevice.getType();
            this.f401n = type;
        } else {
            type = 0;
        }
        if (zS) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                t tVar = (t) it.next();
                if ((tVar instanceof s) && ((s) tVar).f436j == 4) {
                    return arrayList.indexOf(tVar);
                }
            }
        }
        if (this.f396i) {
            boolean z2 = MotorolaSettings.Secure.getInt(this.f392e.getContentResolver(), "is_allow_rdp_audio_output", 0) == 1;
            if (this.f400m && z2 && !zS2) {
                r.i0("persist.desktop.allow_rdp_call", "true");
                zS2 = true;
            }
            if (z2 && zS2) {
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    t tVar2 = (t) it2.next();
                    if ((tVar2 instanceof s) && ((s) tVar2).f436j == 5) {
                        return arrayList.indexOf(tVar2);
                    }
                }
            }
        }
        if (this.f411y && zS3) {
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                t tVar3 = (t) it3.next();
                if ((tVar3 instanceof s) && ((s) tVar3).f436j == 6) {
                    return arrayList.indexOf(tVar3);
                }
            }
        }
        if (r.P(type)) {
            if (this.f402o != null && (list = this.f404q) != null && list.size() > 0) {
                Iterator it4 = this.f404q.iterator();
                while (true) {
                    if (!it4.hasNext()) {
                        address = "";
                        break;
                    }
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) it4.next();
                    try {
                        if (this.f402o.isAudioConnected(bluetoothDevice)) {
                            address = bluetoothDevice.getAddress();
                            break;
                        }
                        continue;
                    } catch (Exception unused) {
                    }
                }
                Iterator it5 = arrayList.iterator();
                while (it5.hasNext()) {
                    t tVar4 = (t) it5.next();
                    s sVar = (s) tVar4;
                    if (sVar.f436j == 3 && address.equalsIgnoreCase(sVar.f439m)) {
                        return arrayList.indexOf(tVar4);
                    }
                }
            }
        } else if (type == 2 || type == 1) {
            Iterator it6 = arrayList.iterator();
            while (it6.hasNext()) {
                t tVar5 = (t) it6.next();
                if ((tVar5 instanceof s) && ((s) tVar5).f436j == 0) {
                    return arrayList.indexOf(tVar5);
                }
            }
        } else {
            Iterator it7 = arrayList.iterator();
            while (it7.hasNext()) {
                t tVar6 = (t) it7.next();
                if ((tVar6 instanceof s) && ((s) tVar6).f436j == 2) {
                    return arrayList.indexOf(tVar6);
                }
            }
        }
        return 0;
    }

    public final void v(int i2) {
        if (this.f399l || i2 == -1) {
            return;
        }
        synchronized (this.f389b) {
            try {
                int iIndexOf = -1;
                for (t tVar : this.f395h) {
                    int iC = tVar.c();
                    if (i2 != 0) {
                        if (i2 != 1) {
                            if (i2 != 2) {
                                if (i2 == 3 && iC == 1001) {
                                    iIndexOf = this.f395h.indexOf(tVar);
                                }
                            } else if (iC == 9) {
                                iIndexOf = this.f395h.indexOf(tVar);
                            }
                        } else if (this.f382M) {
                            if (r.P(iC) && r.Q(tVar.f441b.getAddress())) {
                                iIndexOf = this.f395h.indexOf(tVar);
                            }
                        } else if (iC == 1000) {
                            iIndexOf = this.f395h.indexOf(tVar);
                        }
                    } else if (iC == 2) {
                        iIndexOf = this.f395h.indexOf(tVar);
                    }
                }
                if (iIndexOf != -1 && iIndexOf != this.f397j) {
                    v0.a("i", "mediaSoundRoteTo " + iIndexOf + " mConnectDeviceIndex = " + this.f397j);
                    o(iIndexOf, true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void w(boolean z2) {
        if (this.f399l) {
            z2 = true;
        }
        if (this.f370A != z2) {
            this.f370A = z2;
            v0.a("i", "notifyShowAudioIconChangeIfNeed change to " + this.f370A);
            if (!this.f371B) {
                C0050d c0050d = new C0050d(this, 2);
                Timer timer = new Timer();
                this.f372C = timer;
                timer.schedule(c0050d, 500L);
                this.f371B = true;
                return;
            }
            this.f371B = false;
            Timer timer2 = this.f372C;
            if (timer2 != null) {
                timer2.cancel();
                this.f372C = null;
            }
            StringBuilder sb = new StringBuilder("cancelSendAudioIconChange ");
            sb.append(!this.f370A);
            v0.a("i", sb.toString());
        }
    }

    public final void x() {
        int type;
        if (q() == 3 || this.f400m) {
            k(false);
        } else {
            l(false);
        }
        v0.a("i", "mInCallMode  = " + this.f399l);
        boolean z2 = true;
        try {
            if (this.f399l) {
                ArrayList arrayList = new ArrayList();
                this.f411y = false;
                this.f382M = false;
                List availableRoutes = this.f393f.getAvailableRoutes(this.f390c);
                this.f394g = availableRoutes;
                if (availableRoutes.size() > 0) {
                    AudioDeviceInfo communicationDevice = this.f398k.getCommunicationDevice();
                    if (communicationDevice != null) {
                        type = communicationDevice.getType();
                        this.f401n = type;
                    } else {
                        type = 0;
                    }
                    MediaRoute2Info mediaRoute2Info = null;
                    boolean z3 = false;
                    boolean z4 = false;
                    MediaRoute2Info mediaRoute2Info2 = null;
                    MediaRoute2Info mediaRoute2Info3 = null;
                    MediaRoute2Info mediaRoute2Info4 = null;
                    for (MediaRoute2Info mediaRoute2Info5 : this.f394g) {
                        if (r.P(mediaRoute2Info5.getType())) {
                            mediaRoute2Info = mediaRoute2Info5;
                        } else if (r.S(mediaRoute2Info5.getType())) {
                            z4 = true;
                            mediaRoute2Info3 = mediaRoute2Info5;
                        } else if (mediaRoute2Info5.getType() == 9) {
                            if (r.R(this.f399l, this.f400m)) {
                                z3 = true;
                                mediaRoute2Info2 = mediaRoute2Info5;
                            }
                        } else if (mediaRoute2Info5.getType() == 2) {
                            mediaRoute2Info4 = mediaRoute2Info5;
                        }
                    }
                    if (r.V(this.f399l, this.f400m)) {
                        this.f411y = r.r0(this.f408v);
                    }
                    if (!this.f381L) {
                        r();
                    }
                    if (this.f402o != null) {
                        if (mediaRoute2Info == null) {
                            mediaRoute2Info = (MediaRoute2Info) this.f394g.get(0);
                        }
                        try {
                            this.f404q = this.f402o.getConnectedDevices();
                        } catch (Exception unused) {
                        }
                        List list = this.f404q;
                        if (list != null && list.size() > 0) {
                            Iterator it = this.f404q.iterator();
                            while (it.hasNext()) {
                                BluetoothDevice bluetoothDevice = (BluetoothDevice) it.next();
                                if (r.Q(bluetoothDevice.getAddress())) {
                                    this.f382M = z2;
                                }
                                s sVar = new s(this.f392e, this.f393f, mediaRoute2Info, this.f390c, this.f398k, 3, true);
                                sVar.f438l = bluetoothDevice.getName();
                                sVar.f439m = bluetoothDevice.getAddress();
                                arrayList.add(sVar);
                                it = it;
                                z2 = true;
                            }
                        }
                    }
                    if (z4) {
                        if (mediaRoute2Info3 == null) {
                            mediaRoute2Info3 = (MediaRoute2Info) this.f394g.get(0);
                        }
                        arrayList.add(new s(this.f392e, this.f393f, mediaRoute2Info3, this.f390c, this.f398k, 2, false));
                    }
                    if (z3) {
                        if (mediaRoute2Info2 == null) {
                            mediaRoute2Info2 = (MediaRoute2Info) this.f394g.get(0);
                        }
                        arrayList.add(new s(this.f392e, this.f393f, mediaRoute2Info2, this.f390c, this.f398k, 4, false));
                    }
                    if (mediaRoute2Info4 == null) {
                        mediaRoute2Info4 = (MediaRoute2Info) this.f394g.get(0);
                    }
                    if (this.f411y) {
                        arrayList.add(new s(this.f392e, this.f393f, mediaRoute2Info4, this.f390c, this.f398k, 6, false));
                    }
                    arrayList.add(new s(this.f392e, this.f393f, mediaRoute2Info4, this.f390c, this.f398k, 0, false));
                    if (this.f396i && !this.f382M) {
                        s sVar2 = new s(this.f392e, this.f393f, mediaRoute2Info4, this.f390c, this.f398k, 5, r.P(type));
                        sVar2.f446g = this.f376G;
                        arrayList.add(sVar2);
                    }
                    synchronized (this.f389b) {
                        try {
                            if (this.f399l) {
                                this.f397j = t(arrayList);
                                String str = "";
                                Iterator it2 = this.f395h.iterator();
                                while (it2.hasNext()) {
                                    str = str + " device name = " + ((t) it2.next()).f();
                                }
                                v0.a("i", str + " mConnectDeviceIndex = " + this.f397j);
                                this.f395h.clear();
                                this.f395h.addAll(arrayList);
                                Iterator it3 = this.f391d.iterator();
                                while (it3.hasNext()) {
                                    ((h) it3.next()).a(this.f395h, this.f397j, this.f399l);
                                }
                                z();
                            }
                        } finally {
                        }
                    }
                }
            } else {
                ArrayList arrayList2 = new ArrayList();
                this.f411y = false;
                this.f382M = false;
                List<MediaRoute2Info> availableRoutes2 = this.f393f.getAvailableRoutes(this.f390c);
                this.f394g = availableRoutes2;
                for (MediaRoute2Info mediaRoute2Info6 : availableRoutes2) {
                    v0.a("i", "route = " + mediaRoute2Info6.toString());
                    if (r.P(mediaRoute2Info6.getType()) && r.Q(mediaRoute2Info6.getAddress())) {
                        this.f382M = true;
                    }
                    arrayList2.add(new t(this.f392e, this.f393f, mediaRoute2Info6, this.f390c, this.f398k));
                }
                if (arrayList2.size() == 0) {
                    this.f397j = -1;
                } else {
                    if (this.f396i && !this.f382M) {
                        t tVar = (t) arrayList2.get(0);
                        Context context = this.f392e;
                        MediaRouter2Manager mediaRouter2Manager = this.f393f;
                        String str2 = this.f390c;
                        AudioManager audioManager = this.f398k;
                        boolean z5 = this.f375F;
                        boolean z6 = this.f376G;
                        MediaRoute2Info.Builder providerId = new MediaRoute2Info.Builder("ROUTE_ID_BUILTIN_SPEAKER", "RDP").addFeature("android.media.route.feature.LIVE_AUDIO, android.media.route.feature.LIVE_VIDEO, android.media.route.feature.LOCAL_PLAYBACK").setProviderId(tVar.f441b.getProviderId());
                        MediaRoute2Info mediaRoute2Info7 = tVar.f441b;
                        MediaRoute2Info mediaRoute2InfoBuild = providerId.setVolume(mediaRoute2Info7.getVolume()).setVolumeMax(mediaRoute2Info7.getVolumeMax()).setVolumeHandling(1).setConnectionState(2).setClientPackageName(mediaRoute2Info7.getClientPackageName()).build();
                        v0.a("a", "rdpDeviceRoute = " + mediaRoute2InfoBuild);
                        C c2 = new C(context, mediaRouter2Manager, mediaRoute2InfoBuild, str2, audioManager);
                        c2.f348h = audioManager.getStreamMaxVolume(3);
                        c2.f445f = z5;
                        c2.f446g = z6;
                        arrayList2.add(c2);
                    }
                    boolean zR0 = r.r0(this.f408v);
                    this.f411y = zR0;
                    if (zR0) {
                        t tVar2 = (t) arrayList2.get(0);
                        Context context2 = this.f392e;
                        MediaRouter2Manager mediaRouter2Manager2 = this.f393f;
                        String str3 = this.f390c;
                        AudioManager audioManager2 = this.f398k;
                        MediaRoute2Info.Builder providerId2 = new MediaRoute2Info.Builder("ROUTE_ID_BUILTIN_SPEAKER", "Wireless display").addFeature("android.media.route.feature.LIVE_AUDIO, android.media.route.feature.LIVE_VIDEO, android.media.route.feature.LOCAL_PLAYBACK").setProviderId(tVar2.f441b.getProviderId());
                        MediaRoute2Info mediaRoute2Info8 = tVar2.f441b;
                        MediaRoute2Info mediaRoute2InfoBuild2 = providerId2.setVolume(mediaRoute2Info8.getVolume()).setVolumeMax(mediaRoute2Info8.getVolumeMax()).setType(2).setVolumeHandling(1).setConnectionState(2).setClientPackageName(mediaRoute2Info8.getClientPackageName()).build();
                        v0.a("a", "wirelessDisplayDeviceRoute = " + mediaRoute2InfoBuild2);
                        D d2 = new D(context2, mediaRouter2Manager2, mediaRoute2InfoBuild2, str3, audioManager2);
                        d2.f349h = audioManager2.getStreamMaxVolume(3);
                        arrayList2.add(d2);
                    }
                    synchronized (this.f389b) {
                        try {
                            if (!this.f399l) {
                                this.f397j = s(arrayList2);
                                this.f395h.clear();
                                this.f395h.addAll(arrayList2);
                                this.r = ((t) arrayList2.get(this.f397j)).f();
                                this.f405s = ((t) arrayList2.get(this.f397j)).d();
                                v0.a("i", "mConnectDeviceIndex = " + this.f397j + " mCurrentMediaRouteName = " + this.r);
                                Iterator it4 = this.f391d.iterator();
                                while (it4.hasNext()) {
                                    ((h) it4.next()).a(this.f395h, this.f397j, this.f399l);
                                }
                            }
                        } finally {
                        }
                    }
                }
            }
        } catch (Exception unused2) {
        }
    }

    public final void y(boolean z2) {
        if (r.N() && this.f376G != z2) {
            v0.a("i", "setConnectedDeviceCheckboxState = " + z2);
            this.f376G = z2;
            MotorolaSettings.Secure.putInt(this.f392e.getContentResolver(), "is_allow_rf_audio_input", !z2 ? 1 : 0);
            if (this.f396i) {
                synchronized (this.f389b) {
                    try {
                        if (!this.f399l) {
                            Iterator it = this.f395h.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                t tVar = (t) it.next();
                                if (tVar instanceof C) {
                                    tVar.f446g = this.f376G;
                                    break;
                                }
                            }
                        } else {
                            Iterator it2 = this.f395h.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    break;
                                }
                                t tVar2 = (t) it2.next();
                                if ((tVar2 instanceof s) && ((s) tVar2).f436j == 5) {
                                    tVar2.f446g = this.f376G;
                                    z();
                                    break;
                                }
                            }
                        }
                        Iterator it3 = this.f391d.iterator();
                        while (it3.hasNext()) {
                            ((h) it3.next()).a(this.f395h, this.f397j, this.f399l);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    public final void z() {
        s sVar;
        if (this.f400m) {
            synchronized (this.f389b) {
                try {
                    int i2 = this.f397j;
                    sVar = (i2 < 0 || i2 >= this.f395h.size()) ? null : (s) this.f395h.get(this.f397j);
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (sVar == null) {
                return;
            }
            int iQ = q();
            int i3 = sVar.f436j;
            v0.a("i", "setMicStateDuringDialCall audioMode = " + iQ + " mCheckBoxState = " + this.f376G + " deviceType = " + i3);
            if (i3 != 5) {
                if (this.f380K && iQ == 5) {
                    r.h0(this.f398k, 2, this.f400m);
                    return;
                }
                return;
            }
            if (this.f376G) {
                if (iQ == 5) {
                    r.h0(this.f398k, 2, this.f400m);
                    this.f380K = true;
                    return;
                }
                return;
            }
            if (iQ == 2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException unused) {
                }
                r.h0(this.f398k, 5, this.f400m);
                this.f380K = true;
            }
        }
    }
}
