package com.motorola.mobiledesktop.core;

import F.e;
import F.f;
import X.A0;
import X.B0;
import X.C0;
import X.C0005a;
import X.C0013e;
import X.C0024j0;
import X.C0040t;
import X.G0;
import X.H0;
import X.I0;
import X.InterfaceC0010c0;
import X.InterfaceC0028l0;
import X.InterfaceC0039s;
import X.InterfaceC0041u;
import X.J0;
import X.K0;
import X.L0;
import X.M0;
import X.N0;
import X.O0;
import X.P;
import X.P0;
import X.Q;
import X.Q0;
import X.S;
import X.T;
import X.v0;
import X.w0;
import X.z0;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.Instrumentation;
import android.app.Service;
import android.app.StatusBarManager;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothCsipSetCoordinator;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.audiopolicy.AudioPolicy;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.telephony.TelephonyManager;
import android.util.SparseArray;
import android.view.inputmethod.InputMethodManager;
import com.motorola.internal.inputmethod.IMotoInputMethodListener;
import com.motorola.mobiledesktop.core.uinput.UInputManager;
import com.motorola.smartconnect.core.proxy.BaseProxy;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import e0.A;
import e0.b;
import e0.d;
import e0.i;
import e0.j;
import e0.k;
import e0.v;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MotoDesktopService extends Service {
    public static final String[] w0 = {"com.lenovo.hec.lenovoextend", "com.motorola.mobiledesktop"};
    public static final String[] x0 = {"8fd0dd6e143b9911c2ebc08154a63633f79bdeede2ef6fea819d0539d84bf780", "c463fa2a0351086dd6328d9daf6218146ee1651521c1cb6b4c538f85eeec7a3c", "be8804a55e9045a580d204979f6efb15c058731aa1021b78c046feab8ae76a26"};

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public InterfaceC0039s f2243A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public WifiManager f2244B;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public BluetoothManager f2245C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public PowerManager f2246D;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public ActivityManager f2247E;

    /* JADX INFO: renamed from: F, reason: collision with root package name */
    public ActivityTaskManager f2248F;

    /* JADX INFO: renamed from: G, reason: collision with root package name */
    public b f2249G;

    /* JADX INFO: renamed from: H, reason: collision with root package name */
    public AudioManager f2250H;

    /* JADX INFO: renamed from: I, reason: collision with root package name */
    public AlarmManager f2251I;

    /* JADX INFO: renamed from: J, reason: collision with root package name */
    public DevicePolicyManager f2252J;

    /* JADX INFO: renamed from: K, reason: collision with root package name */
    public AudioTrack f2253K;

    /* JADX INFO: renamed from: L, reason: collision with root package name */
    public AudioTrack f2254L;

    /* JADX INFO: renamed from: M, reason: collision with root package name */
    public AudioTrack f2255M;

    /* JADX INFO: renamed from: N, reason: collision with root package name */
    public InputMethodManager f2256N;
    public P0 V;

    /* JADX INFO: renamed from: W, reason: collision with root package name */
    public BluetoothA2dp f2264W;

    /* JADX INFO: renamed from: X, reason: collision with root package name */
    public BluetoothHeadset f2265X;

    /* JADX INFO: renamed from: Y, reason: collision with root package name */
    public BluetoothLeAudio f2266Y;
    public BluetoothCsipSetCoordinator Z;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public DisplayManager f2267a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public InputManager f2269b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Instrumentation f2271c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public VirtualDisplay f2273d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public IVirtualDisplayCallback f2275e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public InterfaceC0028l0 f2277f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public Handler f2279g;

    /* JADX INFO: renamed from: g0, reason: collision with root package name */
    public L0 f2280g0;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public ClipboardManager f2281h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public IRdpPimaryClipChangedListener f2282i;

    /* JADX INFO: renamed from: i0, reason: collision with root package name */
    public C0013e f2283i0;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public IRdpPointerStateChangedListener f2284j;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public UsbManager f2292q;
    public AudioRecord r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public StatusBarManager f2293s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public A f2294t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public i f2295u;
    public InterfaceC0010c0 u0;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public f f2296v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public d f2297w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public v f2298x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public TelephonyManager f2299y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public DisplayManager f2300z;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final HashSet f2286k = new HashSet();

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final HashSet f2287l = new HashSet();

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public final SparseArray f2288m = new SparseArray();

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final SparseArray f2289n = new SparseArray();

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public final SparseArray f2290o = new SparseArray();

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public final Object f2291p = new Object();

    /* JADX INFO: renamed from: O, reason: collision with root package name */
    public final HashSet f2257O = new HashSet();

    /* JADX INFO: renamed from: P, reason: collision with root package name */
    public boolean f2258P = false;

    /* JADX INFO: renamed from: Q, reason: collision with root package name */
    public int f2259Q = -1;

    /* JADX INFO: renamed from: R, reason: collision with root package name */
    public boolean f2260R = false;

    /* JADX INFO: renamed from: S, reason: collision with root package name */
    public UInputManager f2261S = null;

    /* JADX INFO: renamed from: T, reason: collision with root package name */
    public f0.b f2262T = null;

    /* JADX INFO: renamed from: U, reason: collision with root package name */
    public f f2263U = null;

    /* JADX INFO: renamed from: a0, reason: collision with root package name */
    public final Object f2268a0 = new Object();

    /* JADX INFO: renamed from: b0, reason: collision with root package name */
    public K0 f2270b0 = null;

    /* JADX INFO: renamed from: c0, reason: collision with root package name */
    public final SparseArray f2272c0 = new SparseArray();

    /* JADX INFO: renamed from: d0, reason: collision with root package name */
    public int f2274d0 = 0;

    /* JADX INFO: renamed from: e0, reason: collision with root package name */
    public final HashMap f2276e0 = new HashMap();

    /* JADX INFO: renamed from: f0, reason: collision with root package name */
    public final B0 f2278f0 = new B0(0, this);
    public final e h0 = new e(2, this);

    /* JADX INFO: renamed from: j0, reason: collision with root package name */
    public final C0 f2285j0 = new C0();
    public final G0 k0 = new G0(this);
    public final HashMap l0 = new HashMap();
    public boolean m0 = false;
    public final H0 n0 = new H0(this);
    public boolean o0 = false;
    public final I0 p0 = new I0(this);
    public final J0 q0 = new J0(this);
    public final HashMap r0 = new HashMap();
    public final HashMap s0 = new HashMap();
    public final z0 t0 = new z0(0, this);
    public final A0 v0 = new A0(this);

    public static boolean a(MotoDesktopService motoDesktopService, BluetoothAdapter bluetoothAdapter, P0 p0, List list, Integer num) {
        motoDesktopService.getClass();
        if (!list.contains(num)) {
            v0.b("MotoDesktopService", "unsupported bt profile");
            return false;
        }
        if (bluetoothAdapter.getProfileProxy(motoDesktopService.getApplicationContext(), p0, num.intValue())) {
            return true;
        }
        v0.b("MotoDesktopService", "get bt profile proxy failed");
        return false;
    }

    public static Object b(MotoDesktopService motoDesktopService, Context context, String str) {
        motoDesktopService.getClass();
        Object objNewInstance = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.motorola.mobiledesktop", 0);
            if (applicationInfo == null) {
                v0.a("MotoDesktopService", "com.motorola.mobiledesktop not install.");
            } else {
                DexClassLoader dexClassLoader = new DexClassLoader(applicationInfo.sourceDir, context.getCacheDir().getAbsolutePath(), null, MotoDesktopService.class.getClassLoader());
                Method declaredMethod = BaseDexClassLoader.class.getDeclaredMethod("findClass", String.class);
                declaredMethod.setAccessible(true);
                objNewInstance = ((Class) declaredMethod.invoke(dexClassLoader, str)).getConstructor(Context.class).newInstance(context);
            }
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "loadFromDesktopDex", e2);
        }
        return objNewInstance;
    }

    public static void c(MotoDesktopService motoDesktopService, InterfaceC0041u interfaceC0041u) {
        synchronized (motoDesktopService.r0) {
            motoDesktopService.r0.remove(((C0040t) interfaceC0041u).f332a);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void d(com.motorola.mobiledesktop.core.MotoDesktopService r5, int r6) {
        /*
            java.lang.String r0 = "illegal access from uid: "
            boolean r1 = r5.f2260R
            if (r1 == 0) goto L7
            goto L43
        L7:
            java.util.HashMap r1 = r5.s0
            monitor-enter(r1)
            java.util.HashMap r2 = r5.s0     // Catch: java.lang.Throwable -> L18
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Throwable -> L18
            boolean r2 = r2.containsValue(r3)     // Catch: java.lang.Throwable -> L18
            if (r2 == 0) goto L1a
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L18
            goto L43
        L18:
            r5 = move-exception
            goto L56
        L1a:
            android.content.pm.PackageManager r2 = r5.getPackageManager()     // Catch: java.lang.Throwable -> L18
            java.lang.String[] r2 = r2.getPackagesForUid(r6)     // Catch: java.lang.Throwable -> L18
            if (r2 == 0) goto L32
            int r3 = r2.length     // Catch: java.lang.Throwable -> L18
            r4 = 1
            if (r3 < r4) goto L32
            r3 = 0
            r2 = r2[r3]     // Catch: java.lang.Throwable -> L18
            boolean r3 = r5.t(r2)     // Catch: java.lang.Throwable -> L18
            if (r3 == 0) goto L32
            goto L33
        L32:
            r2 = 0
        L33:
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch: java.lang.Throwable -> L18
            if (r3 != 0) goto L44
            java.util.HashMap r5 = r5.s0     // Catch: java.lang.Throwable -> L18
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Throwable -> L18
            r5.put(r2, r6)     // Catch: java.lang.Throwable -> L18
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L18
        L43:
            return
        L44:
            java.lang.SecurityException r5 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L18
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L18
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L18
            r2.append(r6)     // Catch: java.lang.Throwable -> L18
            java.lang.String r6 = r2.toString()     // Catch: java.lang.Throwable -> L18
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L18
            throw r5     // Catch: java.lang.Throwable -> L18
        L56:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L18
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.mobiledesktop.core.MotoDesktopService.d(com.motorola.mobiledesktop.core.MotoDesktopService, int):void");
    }

    public static ArrayList p(Signature[] signatureArr) {
        String string;
        ArrayList arrayList = new ArrayList();
        for (Signature signature : signatureArr) {
            if (signature != null) {
                byte[] byteArray = signature.toByteArray();
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
                    messageDigest.update(byteArray);
                    string = new BigInteger(1, messageDigest.digest()).toString(16);
                } catch (NoSuchAlgorithmException unused) {
                    string = null;
                }
                arrayList.add(string);
            }
        }
        return arrayList;
    }

    public final void e() {
        synchronized (this.f2276e0) {
            try {
                Iterator it = this.f2276e0.values().iterator();
                while (it.hasNext()) {
                    ((BaseProxy) it.next()).onDestroy();
                }
                this.f2276e0.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void f(Q q2) {
        synchronized (this.f2291p) {
            O0 o0M = m(q2);
            if (o0M != null) {
                int i2 = o0M.f283a;
                synchronized (this.f2291p) {
                    try {
                        Iterator it = this.f2287l.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                this.f2269b.deregisterOnPointerPositionChangedListener((M0) this.f2289n.get(o0M.f283a));
                                this.f2289n.remove(o0M.f283a);
                                break;
                            }
                            if (((O0) it.next()).f283a == i2) {
                            }
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public final void g(T t2) {
        synchronized (this.f2291p) {
            O0 o0N = n(t2);
            if (o0N != null) {
                int i2 = o0N.f283a;
                synchronized (this.f2291p) {
                    try {
                        Iterator it = this.f2286k.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                N0 n0 = (N0) this.f2288m.get(o0N.f283a);
                                v0.e("MotoDesktopService", "deregisterOnPointerStateChangedListener type: " + o0N.f283a);
                                this.f2269b.deregisterOnPointerStateChangedListener(n0);
                                this.f2288m.remove(o0N.f283a);
                                break;
                            }
                            if (((O0) it.next()).f283a == i2) {
                            }
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public final ArrayList h(int i2) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.f2291p) {
            try {
                for (O0 o0 : this.f2286k) {
                    if (o0.f283a == i2) {
                        arrayList.add((T) o0.f284b);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final void i() {
        try {
            AudioTrack audioTrack = this.f2255M;
            if (audioTrack != null) {
                audioTrack.stop();
                this.f2255M.release();
                this.f2255M = null;
            }
        } catch (Exception e2) {
            v0.a("MotoDesktopService", "releaseAudioTrackForDialCall " + e2.getMessage());
            this.f2255M = null;
        }
    }

    public final void j() {
        try {
            AudioTrack audioTrack = this.f2254L;
            if (audioTrack != null) {
                audioTrack.stop();
                this.f2254L.release();
                this.f2254L = null;
            }
        } catch (Exception e2) {
            v0.a("MotoDesktopService", "releaseAudioTrackForIpCall " + e2.getMessage());
            this.f2254L = null;
        }
    }

    public final void k() {
        try {
            AudioTrack audioTrack = this.f2253K;
            if (audioTrack != null) {
                audioTrack.stop();
                AudioManager audioManager = this.f2250H;
                if (audioManager != null) {
                    AudioPolicy audioPolicy = Q0.f293f;
                    if (audioPolicy != null) {
                        audioManager.unregisterAudioPolicyAsync(audioPolicy);
                        Q0.f293f = null;
                    }
                } else {
                    boolean z2 = Q0.f288a;
                }
                this.f2253K.release();
                this.f2253K = null;
            }
        } catch (Exception e2) {
            v0.a("MotoDesktopService", "releaseAudioTrackForMic " + e2.getMessage());
            this.f2253K = null;
        }
    }

    public final void l() {
        if (this.f2273d != null) {
            IVirtualDisplayCallback iVirtualDisplayCallback = this.f2275e;
            J0 j02 = this.q0;
            if (iVirtualDisplayCallback != null) {
                iVirtualDisplayCallback.asBinder().unlinkToDeath(j02, 0);
            }
            this.f2275e = null;
            InterfaceC0028l0 interfaceC0028l0 = this.f2277f;
            if (interfaceC0028l0 != null) {
                ((C0024j0) interfaceC0028l0).f325a.unlinkToDeath(j02, 0);
            }
            this.f2277f = null;
            w0.b(this.f2273d.getDisplay() != null ? this.f2273d.getDisplay().getDisplayId() : 0, "releaseDisplay, displayId = ", "MotoDesktopService");
            this.f2273d.release();
            this.f2273d = null;
        }
    }

    public final O0 m(Q q2) {
        synchronized (this.f2291p) {
            try {
                Iterator it = this.f2287l.iterator();
                while (it.hasNext()) {
                    O0 o0 = (O0) it.next();
                    if (((P) ((Q) o0.f284b)).f285a == ((P) q2).f285a) {
                        it.remove();
                        return o0;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final O0 n(T t2) {
        synchronized (this.f2291p) {
            try {
                Iterator it = this.f2286k.iterator();
                while (it.hasNext()) {
                    O0 o0 = (O0) it.next();
                    if (((S) ((T) o0.f284b)).f298a == ((S) t2).f298a) {
                        it.remove();
                        return o0;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void o() {
        synchronized (this.p0) {
            try {
                if (this.o0) {
                    v0.a("MotoDesktopService", "removePrimaryClipChangedListenerInternal");
                    this.f2281h.removePrimaryClipChangedListener(this.p0);
                    this.o0 = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        v0.e("MotoDesktopService", "onBind");
        return this.k0;
    }

    @Override // android.app.Service
    public final void onCreate() {
        b bVar;
        int i2 = 0;
        v0.a("MotoDesktopService", "onCreate");
        this.f2267a = (DisplayManager) getSystemService("display");
        this.f2269b = (InputManager) getApplicationContext().getSystemService("input");
        if (UserHandle.getUserHandleForUid(Process.myUid()).isSystem()) {
            this.f2269b.setShowPointer(1, true);
        }
        this.f2271c = new Instrumentation();
        this.f2281h = (ClipboardManager) getSystemService("clipboard");
        this.f2279g = new Handler();
        this.f2292q = (UsbManager) getSystemService(UsbManager.class);
        this.f2293s = (StatusBarManager) getSystemService("statusbar");
        this.f2294t = new A(getApplicationContext());
        this.f2298x = v.b(getApplicationContext());
        this.f2296v = new f(getApplicationContext(), 4);
        this.f2297w = d.d(getApplicationContext());
        this.f2299y = (TelephonyManager) getSystemService("phone");
        this.f2244B = (WifiManager) getSystemService("wifi");
        this.f2245C = (BluetoothManager) getSystemService("bluetooth");
        this.f2250H = (AudioManager) getSystemService("audio");
        Context applicationContext = getApplicationContext();
        i iVar = new i();
        iVar.f2488c = applicationContext;
        this.f2295u = iVar;
        this.f2251I = (AlarmManager) getSystemService("alarm");
        this.f2252J = (DevicePolicyManager) getSystemService("device_policy");
        this.f2297w.a(this.f2278f0);
        boolean z2 = Q0.f288a;
        String str = SystemProperties.get("ro.build.tags", "");
        boolean z3 = !("user".equals(SystemProperties.get("ro.build.type", "")) && str != null && str.contains("release-keys"));
        this.f2260R = z3;
        if (z3) {
            v0.a("MotoDesktopService", "mIsBuildTestKeys: " + this.f2260R);
        }
        s();
        v vVar = this.f2298x;
        vVar.getClass();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.p2p.STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.p2p.DISCOVERY_STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.p2p.PEERS_CHANGED");
        intentFilter.addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.p2p.THIS_DEVICE_CHANGED");
        vVar.f2512a.registerReceiver(vVar.f2524m, intentFilter);
        this.f2300z = (DisplayManager) getSystemService("display");
        this.f2246D = (PowerManager) getSystemService("power");
        this.V = new P0(0, this);
        this.f2248F = (ActivityTaskManager) getSystemService("activity_task");
        this.f2247E = (ActivityManager) getSystemService("activity");
        synchronized (b.class) {
            try {
                if (b.f2449m == null) {
                    synchronized (b.class) {
                        try {
                            if (b.f2449m == null) {
                                b.f2449m = new b(this);
                            }
                        } finally {
                        }
                    }
                }
                bVar = b.f2449m;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.f2249G = bVar;
        this.f2256N = (InputMethodManager) getSystemService("input_method");
        IMotoInputMethodListener l0 = new L0();
        l0.f277a = this;
        this.f2280g0 = l0;
        this.f2256N.registerMotoInputMethodListener(l0);
        k kVar = new k(this);
        i iVar2 = this.f2295u;
        iVar2.getClass();
        v0.a("PackageChangeManager", "packageChangedCallback= " + kVar);
        iVar2.f2487b = kVar;
        if (((j) iVar2.f2491f) == null) {
            iVar2.f2491f = new j(iVar2, 2);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter2.addDataScheme("package");
            ((Context) iVar2.f2488c).registerReceiverForAllUsers((j) iVar2.f2491f, intentFilter2, null, null);
        }
        Context applicationContext2 = getApplicationContext();
        C0013e c0013e = new C0013e();
        Handler handler = new Handler();
        c0013e.f319b = handler;
        C0005a c0005a = new C0005a(c0013e, handler, i2);
        Context applicationContext3 = applicationContext2.getApplicationContext();
        c0013e.f318a = applicationContext3;
        applicationContext3.getContentResolver().registerContentObserver(C0013e.f314d, true, c0005a);
        this.f2283i0 = c0013e;
        this.f2261S = UInputManager.getInstance(this);
        Context applicationContext4 = getApplicationContext();
        if (f0.b.f2534e == null) {
            synchronized (f0.b.class) {
                try {
                    if (f0.b.f2534e == null) {
                        f0.b.f2534e = new f0.b(applicationContext4);
                    }
                } finally {
                }
            }
        }
        this.f2262T = f0.b.f2534e;
        Context applicationContext5 = getApplicationContext();
        if (f.f122c == null) {
            synchronized (f0.b.class) {
                try {
                    if (f.f122c == null) {
                        f.f122c = new f(applicationContext5, 6);
                    }
                } finally {
                }
            }
        }
        this.f2263U = f.f122c;
    }

    @Override // android.app.Service
    public final void onDestroy() {
        SparseArray sparseArray;
        SparseArray sparseArray2;
        super.onDestroy();
        v0.a("MotoDesktopService", "onDestroy");
        int i2 = 0;
        while (true) {
            sparseArray = this.f2288m;
            if (i2 >= sparseArray.size()) {
                break;
            }
            this.f2269b.deregisterOnPointerStateChangedListener((N0) sparseArray.valueAt(i2));
            i2++;
        }
        sparseArray.clear();
        int i3 = 0;
        while (true) {
            sparseArray2 = this.f2289n;
            if (i3 >= sparseArray2.size()) {
                break;
            }
            this.f2269b.deregisterOnPointerPositionChangedListener((M0) sparseArray2.valueAt(i3));
            i3++;
        }
        sparseArray2.clear();
        if (UserHandle.getUserHandleForUid(Process.myUid()).isSystem()) {
            this.f2269b.setShowPointer(1, true);
        }
        this.f2279g.removeCallbacksAndMessages(null);
        o();
        if (this.m0) {
            this.m0 = false;
            this.f2269b.deregisterOnPointerStateChangedListener(this.n0);
        }
        d.d(getApplicationContext()).g(this.f2278f0);
        v vVar = this.f2298x;
        vVar.f2512a.unregisterReceiver(vVar.f2524m);
        this.f2298x.getClass();
        v.f2511n = null;
        d.d(getApplicationContext()).i(false, false, "");
        this.f2256N.unregisterMotoInputMethodListener(this.f2280g0);
        this.f2280g0.f277a = null;
        q();
        A a2 = this.f2294t;
        a2.getClass();
        v0.e("A", "clearConnectWifiDisplay");
        a2.f2427f = null;
        A a3 = this.f2294t;
        if (a3.f2421D != -1) {
            a3.f2430i.unregisterNetworkCallback(a3.f2419B);
            a3.f2432k.removeDesktopIcon("wifi_quality", a3.f2421D);
            a3.f2421D = -1;
        }
        this.f2280g0 = null;
        this.V = null;
        HashMap map = this.l0;
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            ServiceConnection serviceConnection = (ServiceConnection) map.get((String) it.next());
            if (serviceConnection != null) {
                unbindService(serviceConnection);
            }
        }
        map.clear();
        e();
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        v0.e("MotoDesktopService", "onUnbind");
        return super.onUnbind(intent);
    }

    public final void q() {
        if (this.f2270b0 != null) {
            v0.a("MotoDesktopService", "unRegisterMiracastReceiver");
            try {
                try {
                    unregisterReceiver(this.f2270b0);
                } catch (IllegalArgumentException e2) {
                    v0.h("MotoDesktopService", "unRegisterMiracastReceiver error", e2);
                }
            } finally {
                this.f2270b0 = null;
            }
        }
    }

    public final void r() {
        synchronized (this.f2291p) {
            try {
                if (this.u0 != null) {
                    this.u0 = null;
                    this.f2244B.unregisterSoftApCallback(this.v0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void s() {
        int packageUid;
        synchronized (this.s0) {
            int i2 = 0;
            while (true) {
                try {
                    String[] strArr = w0;
                    if (i2 < 2) {
                        try {
                            packageUid = getPackageManager().getPackageUid(strArr[i2], 0);
                        } catch (PackageManager.NameNotFoundException unused) {
                            packageUid = -10000;
                        }
                        if (packageUid != -10000) {
                            String[] strArr2 = w0;
                            if (t(strArr2[i2])) {
                                this.s0.put(strArr2[i2], Integer.valueOf(packageUid));
                            } else if (!this.f2260R) {
                                v0.a("MotoDesktopService", "illegal signature from package: " + strArr2[i2]);
                            }
                        }
                        i2++;
                    } else {
                        v0.e("MotoDesktopService", "update client uid: " + this.s0.toString());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final boolean t(String str) {
        SigningInfo signingInfo;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 3; i2++) {
            arrayList.add(x0[i2]);
        }
        ArrayList arrayListP = null;
        try {
            PackageInfo packageInfo = getApplicationContext().getPackageManager().getPackageInfo(str, 134217728);
            if (packageInfo != null && (signingInfo = packageInfo.signingInfo) != null) {
                arrayListP = signingInfo.hasMultipleSigners() ? p(packageInfo.signingInfo.getApkContentsSigners()) : p(packageInfo.signingInfo.getSigningCertificateHistory());
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (arrayListP != null && arrayListP.size() > 0) {
            Iterator it = arrayListP.iterator();
            while (it.hasNext()) {
                if (!arrayList.contains((String) it.next())) {
                    return false;
                }
            }
            Iterator it2 = arrayListP.iterator();
            while (it2.hasNext()) {
                if (arrayList.contains((String) it2.next())) {
                    return true;
                }
            }
        }
        return false;
    }
}
