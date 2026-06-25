package e0;

import X.C;
import X.v0;
import android.content.Context;
import android.os.Handler;
import com.motorola.android.dragdrop.IMotoDragDropRf;

/* JADX INFO: loaded from: classes.dex */
public final class g {

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public static volatile g f2472k;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f2473a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public IMotoDragDropRf f2474b;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f2476d;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public Handler f2478f;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public C f2480h;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f2475c = false;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f2477e = 0;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final e f2479g = new e(this);

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final F.e f2481i = new F.e(11, this);

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final f f2482j = new f(this);

    public g(Context context) {
        this.f2473a = context.getApplicationContext();
    }

    public static g a(Context context) {
        if (f2472k == null) {
            synchronized (g.class) {
                try {
                    if (f2472k == null) {
                        f2472k = new g(context);
                    }
                } finally {
                }
            }
        }
        return f2472k;
    }

    public final void b() {
        v0.a("FwkDragDropManager", "unbindDragDropService");
        if (!this.f2475c) {
            v0.a("FwkDragDropManager", "Disabled, don't need unbind again");
            return;
        }
        this.f2475c = false;
        this.f2478f.removeCallbacks(this.f2481i);
        this.f2478f = null;
        this.f2476d = false;
        try {
            this.f2473a.unbindService(this.f2482j);
        } catch (Exception e2) {
            v0.h("FwkDragDropManager", "unbindDragDropService", e2);
        }
    }
}
