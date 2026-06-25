package androidx.recyclerview.widget;

import android.os.Trace;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/* JADX INFO: renamed from: androidx.recyclerview.widget.i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0116i implements Runnable {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final ThreadLocal f1891e = new ThreadLocal();

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final C0113f f1892f = new C0113f();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public ArrayList f1893a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public long f1894b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public long f1895c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public ArrayList f1896d;

    public final void a(RecyclerView recyclerView, int i2, int i3) {
        if (recyclerView.f1806m && this.f1894b == 0) {
            this.f1894b = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        C0114g c0114g = recyclerView.f1785U;
        c0114g.f1883a = i2;
        c0114g.f1884b = i3;
    }

    public final void b(long j2) {
        C0115h c0115h;
        RecyclerView recyclerView;
        ArrayList arrayList = this.f1893a;
        int size = arrayList.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            RecyclerView recyclerView2 = (RecyclerView) arrayList.get(i3);
            if (recyclerView2.getWindowVisibility() == 0) {
                C0114g c0114g = recyclerView2.f1785U;
                c0114g.f1885c = 0;
                c0114g.getClass();
                i2 += c0114g.f1885c;
            }
        }
        ArrayList arrayList2 = this.f1896d;
        arrayList2.ensureCapacity(i2);
        for (int i4 = 0; i4 < size; i4++) {
            RecyclerView recyclerView3 = (RecyclerView) arrayList.get(i4);
            if (recyclerView3.getWindowVisibility() == 0) {
                C0114g c0114g2 = recyclerView3.f1785U;
                Math.abs(c0114g2.f1883a);
                Math.abs(c0114g2.f1884b);
                if (c0114g2.f1885c * 2 > 0) {
                    if (arrayList2.size() <= 0) {
                        arrayList2.add(new C0115h());
                    }
                    c0114g2.getClass();
                    throw null;
                }
            }
        }
        Collections.sort(arrayList2, f1892f);
        if (arrayList2.size() <= 0 || (recyclerView = (c0115h = (C0115h) arrayList2.get(0)).f1889d) == null) {
            return;
        }
        int i5 = c0115h.f1890e;
        if (recyclerView.f1793d.k() > 0) {
            RecyclerView.l(recyclerView.f1793d.j(0));
            throw null;
        }
        B b2 = recyclerView.f1787a;
        try {
            recyclerView.p();
            b2.b(i5);
            throw null;
        } catch (Throwable th) {
            recyclerView.q(false);
            throw th;
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Trace.beginSection("RV Prefetch");
            ArrayList arrayList = this.f1893a;
            if (arrayList.isEmpty()) {
                return;
            }
            int size = arrayList.size();
            long jMax = 0;
            for (int i2 = 0; i2 < size; i2++) {
                RecyclerView recyclerView = (RecyclerView) arrayList.get(i2);
                if (recyclerView.getWindowVisibility() == 0) {
                    jMax = Math.max(recyclerView.getDrawingTime(), jMax);
                }
            }
            if (jMax == 0) {
                return;
            }
            b(TimeUnit.MILLISECONDS.toNanos(jMax) + this.f1895c);
        } finally {
            this.f1894b = 0L;
            Trace.endSection();
        }
    }
}
