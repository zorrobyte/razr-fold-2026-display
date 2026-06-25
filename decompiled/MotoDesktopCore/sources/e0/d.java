package e0;

import X.v0;
import X.w0;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.SystemProperties;
import android.view.Display;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class d implements DisplayManager.DisplayListener {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public static d f2462h;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public String f2463a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public String f2464b;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f2466d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final DisplayManager f2467e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f2468f = false;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final ArrayList f2469g = new ArrayList();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2465c = -1;

    public d(Context context) {
        this.f2467e = (DisplayManager) context.getSystemService(DisplayManager.class);
        b();
    }

    public static int c(Display display) {
        int i2 = SystemProperties.getInt("ro.vendor.mot.hw.lid", 0);
        v0.a("d", "getDisplayType isCLISupported:" + i2 + ", display" + display);
        if (i2 == 1 && display.getDisplayId() == 1) {
            return -1;
        }
        try {
            return Integer.parseInt(Class.forName("android.view.Display").getMethod("getType", null).invoke(display, null).toString());
        } catch (Exception e2) {
            v0.g("d", "getDisplayType error:" + e2.toString());
            return -1;
        }
    }

    public static d d(Context context) {
        if (f2462h == null) {
            synchronized (d.class) {
                try {
                    if (f2462h == null) {
                        f2462h = new d(context);
                    }
                } finally {
                }
            }
        }
        return f2462h;
    }

    public final void a(c cVar) {
        if (cVar == null || this.f2469g.contains(cVar)) {
            v0.a("d", "addConnectStateListener - null or already added");
            return;
        }
        synchronized (this.f2469g) {
            this.f2469g.add(cVar);
            if (this.f2469g.size() == 1) {
                this.f2467e.registerDisplayListener(this, null);
            }
            b();
        }
    }

    public final void b() {
        Display[] displays = this.f2467e.getDisplays();
        if (displays.length == 1) {
            h(-1, -1, "");
        }
        for (Display display : displays) {
            if (display.getDisplayId() != 0) {
                int iC = c(display);
                w0.b(iC, "checkHdmiDisplayAdded - displayType:", "d");
                if (iC == 2) {
                    h(iC, display.getDisplayId(), "");
                    e();
                    return;
                } else if (iC == 3) {
                    h(iC, display.getDisplayId(), display.getName());
                    e();
                    f(display.getDisplayId());
                    return;
                }
            }
        }
    }

    public final void e() {
        synchronized (this.f2469g) {
            try {
                Iterator it = this.f2469g.iterator();
                while (it.hasNext()) {
                    ((c) it.next()).a(this.f2465c, this.f2463a, this.f2464b);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void f(int i2) {
        synchronized (this.f2469g) {
            try {
                Iterator it = this.f2469g.iterator();
                while (it.hasNext()) {
                    ((c) it.next()).b(i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void g(c cVar) {
        if (cVar == null) {
            return;
        }
        synchronized (this.f2469g) {
            this.f2469g.remove(cVar);
            if (this.f2469g.isEmpty()) {
                this.f2467e.unregisterDisplayListener(this);
            }
        }
    }

    public final void h(int i2, int i3, String str) {
        this.f2465c = i2;
        this.f2466d = i3;
        this.f2463a = str;
    }

    public final void i(boolean z2, boolean z3, String str) {
        if (z2) {
            if (this.f2465c != 1) {
                h(1, -1, str);
            }
        } else if (this.f2465c == 1) {
            h(-1, -1, "");
        }
        if (z3) {
            this.f2464b = str;
        } else {
            this.f2464b = "";
        }
        e();
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayAdded(int i2) {
        v0.e("d", "onDisplayAdded - displayId = " + i2);
        Display display = this.f2467e.getDisplay(i2);
        if (display != null) {
            int iC = c(display);
            w0.b(iC, "onDisplayAdded - getDisplayType:", "d");
            if (iC == 2) {
                h(iC, i2, "");
                e();
            } else if (iC == 3) {
                h(iC, i2, display.getName());
                this.f2468f = (display.getFlags() & 64) == 0;
                e();
                f(i2);
            }
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayChanged(int i2) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayRemoved(int i2) {
        v0.e("d", "onDisplayRemoved displayId = " + i2 + ", mDisplayId:" + this.f2466d + ", mDisplayType:" + this.f2465c);
        if (i2 == this.f2466d) {
            if (this.f2465c == 3) {
                synchronized (this.f2469g) {
                    try {
                        Iterator it = this.f2469g.iterator();
                        while (it.hasNext()) {
                            ((c) it.next()).c(i2);
                        }
                    } finally {
                    }
                }
            }
            h(-1, -1, "");
            e();
        }
    }
}
