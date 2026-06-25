package u;

import androidx.appcompat.app.f;

/* JADX INFO: renamed from: u.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0161b extends f {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final Object f2825d;

    public C0161b(int i2) {
        super(i2);
        this.f2825d = new Object();
    }

    @Override // androidx.appcompat.app.f
    public final Object a() {
        Object objA;
        synchronized (this.f2825d) {
            objA = super.a();
        }
        return objA;
    }

    @Override // androidx.appcompat.app.f
    public final boolean c(Object obj) {
        boolean zC;
        synchronized (this.f2825d) {
            zC = super.c(obj);
        }
        return zC;
    }
}
