package h;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: h.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0135b extends k implements Map {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public C0134a f2570h;

    public C0135b(C0135b c0135b) {
        if (c0135b != null) {
            int i2 = c0135b.f2611c;
            b(i2);
            if (this.f2611c != 0) {
                for (int i3 = 0; i3 < i2; i3++) {
                    put(c0135b.h(i3), c0135b.j(i3));
                }
            } else if (i2 > 0) {
                System.arraycopy(c0135b.f2609a, 0, this.f2609a, 0, i2);
                System.arraycopy(c0135b.f2610b, 0, this.f2610b, 0, i2 << 1);
                this.f2611c = i2;
            }
        }
    }

    @Override // java.util.Map
    public final Set entrySet() {
        if (this.f2570h == null) {
            this.f2570h = new C0134a(0, this);
        }
        C0134a c0134a = this.f2570h;
        if (c0134a.f2565a == null) {
            c0134a.f2565a = new h(c0134a, 0);
        }
        return c0134a.f2565a;
    }

    @Override // java.util.Map
    public final Set keySet() {
        if (this.f2570h == null) {
            this.f2570h = new C0134a(0, this);
        }
        C0134a c0134a = this.f2570h;
        if (c0134a.f2566b == null) {
            c0134a.f2566b = new h(c0134a, 1);
        }
        return c0134a.f2566b;
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        b(map.size() + this.f2611c);
        for (Map.Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public final Collection values() {
        if (this.f2570h == null) {
            this.f2570h = new C0134a(0, this);
        }
        C0134a c0134a = this.f2570h;
        if (c0134a.f2567c == null) {
            c0134a.f2567c = new j(c0134a);
        }
        return c0134a.f2567c;
    }
}
