package e0;

import X.K0;
import X.v0;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.p2p.WifiP2pManager;

/* JADX INFO: loaded from: classes.dex */
public final class v {

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public static v f2511n;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f2512a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final k f2513b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final WifiP2pManager f2514c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public WifiP2pManager.Channel f2515d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final m f2516e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public j0.q f2517f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public j0.g f2518g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public j0.i f2519h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public j0.o f2520i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public j0.e f2521j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public j0.k f2522k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public j0.m f2523l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public final K0 f2524m;

    public v(Context context) {
        this.f2512a = null;
        m mVar = new m(this);
        this.f2516e = mVar;
        this.f2524m = new K0(4, this);
        this.f2512a = context;
        WifiP2pManager wifiP2pManager = (WifiP2pManager) context.getSystemService("wifip2p");
        this.f2514c = wifiP2pManager;
        this.f2515d = wifiP2pManager.initialize(context, context.getMainLooper(), mVar);
        k kVar = new k();
        kVar.f2495a = context.getSharedPreferences("MotoDesktopCoreSp", 0);
        this.f2513b = kVar;
    }

    public static v b(Context context) {
        if (f2511n == null) {
            synchronized (v.class) {
                try {
                    if (f2511n == null) {
                        f2511n = new v(context);
                    }
                } finally {
                }
            }
        }
        return f2511n;
    }

    public final void a(String str, WifiP2pManager.ActionListener actionListener) {
        if (str == null) {
            return;
        }
        WifiP2pManager.Channel channel = this.f2515d;
        l lVar = new l(this, str, actionListener, 1);
        WifiP2pManager wifiP2pManager = this.f2514c;
        wifiP2pManager.requestPersistentGroupInfo(channel, lVar);
        k kVar = this.f2513b;
        int i2 = ((SharedPreferences) kVar.f2495a).getInt(str, -1);
        v0.a("v", "deletePersistentGroupByAddress deviceAddress:" + str + ", netId=" + i2);
        if (i2 >= 0) {
            wifiP2pManager.deletePersistentGroup(this.f2515d, i2, actionListener);
            SharedPreferences.Editor editorEdit = ((SharedPreferences) kVar.f2495a).edit();
            editorEdit.remove(str);
            editorEdit.commit();
        }
    }
}
