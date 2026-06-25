package androidx.recyclerview.widget;

import X.w0;
import java.util.ArrayList;

/* JADX INFO: renamed from: androidx.recyclerview.widget.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0110c extends t {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public ArrayList f1848e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public ArrayList f1849f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public ArrayList f1850g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public ArrayList f1851h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public ArrayList f1852i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public ArrayList f1853j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public ArrayList f1854k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public ArrayList f1855l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public ArrayList f1856m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public ArrayList f1857n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public ArrayList f1858o;

    public static void d(ArrayList arrayList) {
        int size = arrayList.size() - 1;
        if (size < 0) {
            return;
        }
        w0.c(arrayList.get(size));
        throw null;
    }

    @Override // androidx.recyclerview.widget.t
    public final void b() {
        ArrayList arrayList = this.f1850g;
        int size = arrayList.size() - 1;
        if (size >= 0) {
            w0.c(arrayList.get(size));
            throw null;
        }
        ArrayList arrayList2 = this.f1848e;
        for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
            w0.c(arrayList2.get(size2));
            a();
            arrayList2.remove(size2);
        }
        ArrayList arrayList3 = this.f1849f;
        int size3 = arrayList3.size() - 1;
        if (size3 >= 0) {
            w0.c(arrayList3.get(size3));
            throw null;
        }
        ArrayList arrayList4 = this.f1851h;
        int size4 = arrayList4.size() - 1;
        if (size4 >= 0) {
            w0.c(arrayList4.get(size4));
            throw null;
        }
        arrayList4.clear();
        if (c()) {
            ArrayList arrayList5 = this.f1853j;
            for (int size5 = arrayList5.size() - 1; size5 >= 0; size5--) {
                ArrayList arrayList6 = (ArrayList) arrayList5.get(size5);
                int size6 = arrayList6.size() - 1;
                if (size6 >= 0) {
                    w0.c(arrayList6.get(size6));
                    throw null;
                }
            }
            ArrayList arrayList7 = this.f1852i;
            for (int size7 = arrayList7.size() - 1; size7 >= 0; size7--) {
                ArrayList arrayList8 = (ArrayList) arrayList7.get(size7);
                int size8 = arrayList8.size() - 1;
                if (size8 >= 0) {
                    w0.c(arrayList8.get(size8));
                    throw null;
                }
            }
            ArrayList arrayList9 = this.f1854k;
            for (int size9 = arrayList9.size() - 1; size9 >= 0; size9--) {
                ArrayList arrayList10 = (ArrayList) arrayList9.get(size9);
                int size10 = arrayList10.size() - 1;
                if (size10 >= 0) {
                    w0.c(arrayList10.get(size10));
                    throw null;
                }
            }
            d(this.f1857n);
            d(this.f1856m);
            d(this.f1855l);
            d(this.f1858o);
            ArrayList arrayList11 = this.f1907b;
            if (arrayList11.size() > 0) {
                w0.c(arrayList11.get(0));
                throw null;
            }
            arrayList11.clear();
        }
    }

    @Override // androidx.recyclerview.widget.t
    public final boolean c() {
        return (this.f1849f.isEmpty() && this.f1851h.isEmpty() && this.f1850g.isEmpty() && this.f1848e.isEmpty() && this.f1856m.isEmpty() && this.f1857n.isEmpty() && this.f1855l.isEmpty() && this.f1858o.isEmpty() && this.f1853j.isEmpty() && this.f1852i.isEmpty() && this.f1854k.isEmpty()) ? false : true;
    }
}
