package g0;

import X.v0;
import android.os.IBinder;

/* JADX INFO: loaded from: classes.dex */
public final class b implements IBinder.DeathRecipient {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ e f2552a;

    public b(e eVar) {
        this.f2552a = eVar;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied(IBinder iBinder) {
        v0.b("ScVirtualDevice", "binderDied");
        e eVar = this.f2552a;
        int i2 = -1;
        for (d dVar : eVar.f2558b.values()) {
            if (iBinder == dVar.f2556c) {
                i2 = dVar.f2554a;
            }
        }
        if (i2 != -1) {
            eVar.b(i2);
        }
        a0.b bVar = null;
        for (a0.b bVar2 : eVar.f2559c) {
            if (((a0.a) bVar2).f464a == iBinder) {
                bVar = bVar2;
            }
        }
        if (bVar != null) {
            eVar.c(bVar);
        }
    }
}
