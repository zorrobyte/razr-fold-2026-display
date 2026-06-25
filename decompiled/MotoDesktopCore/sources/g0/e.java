package g0;

import X.v0;
import android.companion.virtual.VirtualDeviceManager;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public final class e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final VirtualDeviceManager.VirtualDevice f2557a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final ConcurrentHashMap f2558b = new ConcurrentHashMap();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final HashSet f2559c = new HashSet();

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public c f2560d = null;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public ExecutorService f2561e = null;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final b f2562f = new b(this);

    public e(VirtualDeviceManager.VirtualDevice virtualDevice) {
        this.f2557a = virtualDevice;
    }

    public final void a() {
        synchronized (this.f2559c) {
            try {
                if (this.f2561e == null) {
                    this.f2561e = Executors.newSingleThreadExecutor();
                    this.f2560d = new c(this);
                    v0.a("ScVirtualDevice", "addActivityListener");
                    this.f2557a.addActivityListener(this.f2561e, this.f2560d);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void b(int i2) {
        d dVar = (d) this.f2558b.remove(Integer.valueOf(i2));
        if (dVar != null) {
            dVar.f2555b.release();
            return;
        }
        v0.g("ScVirtualDevice", "not found display to release for " + i2);
    }

    public final void c(a0.b bVar) {
        if (bVar != null) {
            synchronized (this.f2559c) {
                this.f2559c.remove(bVar);
                ((a0.a) bVar).f464a.unlinkToDeath(this.f2562f, 0);
                d();
            }
        }
    }

    public final void d() {
        synchronized (this.f2559c) {
            try {
                if (this.f2559c.isEmpty()) {
                    v0.a("ScVirtualDevice", "removeActivityListener");
                    this.f2557a.removeActivityListener(this.f2560d);
                    this.f2560d = null;
                    this.f2561e.shutdown();
                    this.f2561e.shutdownNow();
                    this.f2561e = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
