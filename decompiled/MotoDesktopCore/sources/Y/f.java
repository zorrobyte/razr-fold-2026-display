package Y;

import X.Q0;
import X.v0;

/* JADX INFO: loaded from: classes.dex */
public final class f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ i f360a;

    public f(i iVar) {
        this.f360a = iVar;
    }

    public final void a() {
        int i2;
        int iIndexOf;
        int iIndexOf2;
        this.f360a.x();
        i iVar = this.f360a;
        if (!iVar.f382M || (i2 = iVar.f383N) == -1) {
            return;
        }
        String str = iVar.f384O;
        synchronized (iVar.f389b) {
            try {
                if (r.p(iVar.f395h, iVar.f397j, Q0.f295h)) {
                    v0.a("i", "routingToPreDeviceIfNeeded cur routing is Client bluetooth");
                    if (iVar.f399l) {
                        if (i2 == 3 || i2 == 2) {
                            for (t tVar : iVar.f395h) {
                                if ((tVar instanceof s) && ((s) tVar).f441b.getType() == i2) {
                                    if (i2 == 2) {
                                        iIndexOf2 = iVar.f395h.indexOf(tVar);
                                        break;
                                    }
                                    if (str != null && str.equalsIgnoreCase(((s) tVar).f439m)) {
                                        iIndexOf2 = iVar.f395h.indexOf(tVar);
                                        break;
                                    }
                                }
                            }
                            iIndexOf2 = -1;
                            if (iIndexOf2 != -1) {
                                v0.a("i", "routingToPreDeviceIfNeeded " + iIndexOf2 + " mConnectDeviceIndex = " + iVar.f397j);
                                iVar.n(iIndexOf2, true);
                            }
                        } else {
                            iIndexOf2 = -1;
                            if (iIndexOf2 != -1 && iIndexOf2 != iVar.f397j) {
                                v0.a("i", "routingToPreDeviceIfNeeded " + iIndexOf2 + " mConnectDeviceIndex = " + iVar.f397j);
                                iVar.n(iIndexOf2, true);
                            }
                        }
                    } else if (r.P(i2) || r.S(i2)) {
                        for (t tVar2 : iVar.f395h) {
                            if (tVar2.c() == i2) {
                                if (r.P(i2)) {
                                    if (str != null && str.equalsIgnoreCase(tVar2.f441b.getAddress())) {
                                        iIndexOf = iVar.f395h.indexOf(tVar2);
                                        break;
                                    }
                                } else {
                                    iIndexOf = iVar.f395h.indexOf(tVar2);
                                    break;
                                }
                            }
                        }
                        iIndexOf = -1;
                        if (iIndexOf != -1 && iIndexOf != iVar.f397j) {
                            v0.a("i", "routingToPreDeviceIfNeeded " + iIndexOf + " mConnectDeviceIndex = " + iVar.f397j);
                            iVar.o(iIndexOf, true);
                        }
                    } else {
                        iIndexOf = -1;
                        if (iIndexOf != -1) {
                            v0.a("i", "routingToPreDeviceIfNeeded " + iIndexOf + " mConnectDeviceIndex = " + iVar.f397j);
                            iVar.o(iIndexOf, true);
                        }
                    }
                }
            } finally {
            }
        }
        i.b(this.f360a, 0);
        i iVar2 = this.f360a;
        iVar2.f383N = -1;
        iVar2.f384O = "";
    }
}
