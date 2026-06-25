package androidx.recyclerview.widget;

import X.w0;
import android.view.View;
import com.google.android.material.internal.NavigationMenuView;
import java.util.ArrayList;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public final class B {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ArrayList f1719a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final ArrayList f1720b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f1721c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f1722d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public A f1723e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ RecyclerView f1724f;

    public B(NavigationMenuView navigationMenuView) {
        this.f1724f = navigationMenuView;
        ArrayList arrayList = new ArrayList();
        this.f1719a = arrayList;
        this.f1720b = new ArrayList();
        Collections.unmodifiableList(arrayList);
        this.f1721c = 2;
        this.f1722d = 2;
    }

    public final int a(int i2) {
        RecyclerView recyclerView = this.f1724f;
        if (i2 >= 0 && i2 < recyclerView.V.a()) {
            return !recyclerView.V.f1728d ? i2 : recyclerView.f1791c.b(i2, 0);
        }
        throw new IndexOutOfBoundsException("invalid position " + i2 + ". State item count is " + recyclerView.V.a() + recyclerView.j());
    }

    public final void b(int i2) {
        RecyclerView recyclerView = this.f1724f;
        if (i2 < 0 || i2 >= recyclerView.V.a()) {
            throw new IndexOutOfBoundsException("Invalid item position " + i2 + "(" + i2 + "). Item count:" + recyclerView.V.a() + recyclerView.j());
        }
        E e2 = recyclerView.V;
        boolean z2 = e2.f1728d;
        ArrayList arrayList = this.f1719a;
        if (arrayList.size() > 0) {
            w0.c(arrayList.get(0));
            throw null;
        }
        f0.b bVar = recyclerView.f1793d;
        ArrayList arrayList2 = (ArrayList) bVar.f2539d;
        if (arrayList2.size() > 0) {
            View view = (View) arrayList2.get(0);
            ((e0.k) bVar.f2537b).getClass();
            RecyclerView.l(view);
            throw null;
        }
        ArrayList arrayList3 = this.f1720b;
        if (arrayList3.size() > 0) {
            w0.c(arrayList3.get(0));
            throw null;
        }
        int iB = recyclerView.f1791c.b(i2, 0);
        if (iB >= 0) {
            throw null;
        }
        throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + i2 + "(offset:" + iB + ").state:" + e2.a() + recyclerView.j());
    }

    public final void c() {
        v vVar = this.f1724f.f1802i;
        this.f1722d = this.f1721c;
        ArrayList arrayList = this.f1720b;
        int size = arrayList.size() - 1;
        if (size < 0 || arrayList.size() <= this.f1722d) {
            return;
        }
        w0.c(arrayList.get(size));
        int[] iArr = RecyclerView.f1763i0;
        throw null;
    }
}
