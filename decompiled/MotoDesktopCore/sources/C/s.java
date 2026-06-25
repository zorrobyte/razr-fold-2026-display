package C;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.widget.ListView;
import h.AbstractC0137d;
import h.C0135b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class s implements Cloneable {

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public static final int[] f69u = {2, 1, 3, 4};

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public static final n f70v = new n(0);

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public static final ThreadLocal f71w = new ThreadLocal();

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public ArrayList f82k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public ArrayList f83l;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public Y.r f89s;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final String f72a = getClass().getName();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public long f73b = -1;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public long f74c = -1;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public TimeInterpolator f75d = null;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final ArrayList f76e = new ArrayList();

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final ArrayList f77f = new ArrayList();

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public z f78g = new z(0);

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public z f79h = new z(0);

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public x f80i = null;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final int[] f81j = f69u;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public final ArrayList f84m = new ArrayList();

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public int f85n = 0;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public boolean f86o = false;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public boolean f87p = false;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public ArrayList f88q = null;
    public ArrayList r = new ArrayList();

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public n f90t = f70v;

    public static void c(z zVar, View view, y yVar) {
        ((C0135b) zVar.f105a).put(view, yVar);
        int id = view.getId();
        if (id >= 0) {
            SparseArray sparseArray = (SparseArray) zVar.f107c;
            if (sparseArray.indexOfKey(id) >= 0) {
                sparseArray.put(id, null);
            } else {
                sparseArray.put(id, view);
            }
        }
        WeakHashMap weakHashMap = v.l.f2836a;
        String transitionName = view.getTransitionName();
        if (transitionName != null) {
            C0135b c0135b = (C0135b) zVar.f106b;
            if (c0135b.containsKey(transitionName)) {
                c0135b.put(transitionName, null);
            } else {
                c0135b.put(transitionName, view);
            }
        }
        if (view.getParent() instanceof ListView) {
            ListView listView = (ListView) view.getParent();
            if (listView.getAdapter().hasStableIds()) {
                long itemIdAtPosition = listView.getItemIdAtPosition(listView.getPositionForView(view));
                h.e eVar = (h.e) zVar.f108d;
                if (eVar.f2584a) {
                    eVar.c();
                }
                if (AbstractC0137d.b(eVar.f2585b, eVar.f2587d, itemIdAtPosition) < 0) {
                    view.setHasTransientState(true);
                    eVar.e(itemIdAtPosition, view);
                    return;
                }
                View view2 = (View) eVar.d(itemIdAtPosition, null);
                if (view2 != null) {
                    view2.setHasTransientState(false);
                    eVar.e(itemIdAtPosition, null);
                }
            }
        }
    }

    public static C0135b o() {
        ThreadLocal threadLocal = f71w;
        C0135b c0135b = (C0135b) threadLocal.get();
        if (c0135b != null) {
            return c0135b;
        }
        C0135b c0135b2 = new C0135b();
        threadLocal.set(c0135b2);
        return c0135b2;
    }

    public static boolean t(y yVar, y yVar2, String str) {
        Object obj = yVar.f102a.get(str);
        Object obj2 = yVar2.f102a.get(str);
        if (obj == null && obj2 == null) {
            return false;
        }
        if (obj == null || obj2 == null) {
            return true;
        }
        return !obj.equals(obj2);
    }

    public void A(Y.r rVar) {
        this.f89s = rVar;
    }

    public void B(TimeInterpolator timeInterpolator) {
        this.f75d = timeInterpolator;
    }

    public void C(n nVar) {
        if (nVar == null) {
            this.f90t = f70v;
        } else {
            this.f90t = nVar;
        }
    }

    public void D() {
    }

    public void E(long j2) {
        this.f73b = j2;
    }

    public final void F() {
        if (this.f85n == 0) {
            ArrayList arrayList = this.f88q;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.f88q.clone();
                int size = arrayList2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((r) arrayList2.get(i2)).c();
                }
            }
            this.f87p = false;
        }
        this.f85n++;
    }

    public String G(String str) {
        String str2 = str + getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + ": ";
        if (this.f74c != -1) {
            str2 = str2 + "dur(" + this.f74c + ") ";
        }
        if (this.f73b != -1) {
            str2 = str2 + "dly(" + this.f73b + ") ";
        }
        if (this.f75d != null) {
            str2 = str2 + "interp(" + this.f75d + ") ";
        }
        ArrayList arrayList = this.f76e;
        int size = arrayList.size();
        ArrayList arrayList2 = this.f77f;
        if (size <= 0 && arrayList2.size() <= 0) {
            return str2;
        }
        String str3 = str2 + "tgts(";
        if (arrayList.size() > 0) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (i2 > 0) {
                    str3 = str3 + ", ";
                }
                str3 = str3 + arrayList.get(i2);
            }
        }
        if (arrayList2.size() > 0) {
            for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                if (i3 > 0) {
                    str3 = str3 + ", ";
                }
                str3 = str3 + arrayList2.get(i3);
            }
        }
        return str3 + ")";
    }

    public void a(r rVar) {
        if (this.f88q == null) {
            this.f88q = new ArrayList();
        }
        this.f88q.add(rVar);
    }

    public void b(View view) {
        this.f77f.add(view);
    }

    public abstract void d(y yVar);

    public final void e(View view, boolean z2) {
        if (view == null) {
            return;
        }
        view.getId();
        if (view.getParent() instanceof ViewGroup) {
            y yVar = new y();
            yVar.f103b = view;
            if (z2) {
                g(yVar);
            } else {
                d(yVar);
            }
            yVar.f104c.add(this);
            f(yVar);
            if (z2) {
                c(this.f78g, view, yVar);
            } else {
                c(this.f79h, view, yVar);
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                e(viewGroup.getChildAt(i2), z2);
            }
        }
    }

    public void f(y yVar) {
    }

    public abstract void g(y yVar);

    public final void h(ViewGroup viewGroup, boolean z2) {
        i(z2);
        ArrayList arrayList = this.f76e;
        int size = arrayList.size();
        ArrayList arrayList2 = this.f77f;
        if (size <= 0 && arrayList2.size() <= 0) {
            e(viewGroup, z2);
            return;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            View viewFindViewById = viewGroup.findViewById(((Integer) arrayList.get(i2)).intValue());
            if (viewFindViewById != null) {
                y yVar = new y();
                yVar.f103b = viewFindViewById;
                if (z2) {
                    g(yVar);
                } else {
                    d(yVar);
                }
                yVar.f104c.add(this);
                f(yVar);
                if (z2) {
                    c(this.f78g, viewFindViewById, yVar);
                } else {
                    c(this.f79h, viewFindViewById, yVar);
                }
            }
        }
        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
            View view = (View) arrayList2.get(i3);
            y yVar2 = new y();
            yVar2.f103b = view;
            if (z2) {
                g(yVar2);
            } else {
                d(yVar2);
            }
            yVar2.f104c.add(this);
            f(yVar2);
            if (z2) {
                c(this.f78g, view, yVar2);
            } else {
                c(this.f79h, view, yVar2);
            }
        }
    }

    public final void i(boolean z2) {
        if (z2) {
            ((C0135b) this.f78g.f105a).clear();
            ((SparseArray) this.f78g.f107c).clear();
            ((h.e) this.f78g.f108d).a();
        } else {
            ((C0135b) this.f79h.f105a).clear();
            ((SparseArray) this.f79h.f107c).clear();
            ((h.e) this.f79h.f108d).a();
        }
    }

    @Override // 
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public s clone() {
        try {
            s sVar = (s) super.clone();
            sVar.r = new ArrayList();
            sVar.f78g = new z(0);
            sVar.f79h = new z(0);
            sVar.f82k = null;
            sVar.f83l = null;
            return sVar;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public Animator k(ViewGroup viewGroup, y yVar, y yVar2) {
        return null;
    }

    public void l(ViewGroup viewGroup, z zVar, z zVar2, ArrayList arrayList, ArrayList arrayList2) {
        Animator animatorK;
        int i2;
        View view;
        y yVar;
        Animator animator;
        y yVar2;
        C0135b c0135bO = o();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            y yVar3 = (y) arrayList.get(i3);
            y yVar4 = (y) arrayList2.get(i3);
            if (yVar3 != null && !yVar3.f104c.contains(this)) {
                yVar3 = null;
            }
            if (yVar4 != null && !yVar4.f104c.contains(this)) {
                yVar4 = null;
            }
            if (!(yVar3 == null && yVar4 == null) && ((yVar3 == null || yVar4 == null || r(yVar3, yVar4)) && (animatorK = k(viewGroup, yVar3, yVar4)) != null)) {
                String str = this.f72a;
                if (yVar4 != null) {
                    view = yVar4.f103b;
                    String[] strArrP = p();
                    if (view == null || strArrP == null || strArrP.length <= 0) {
                        i2 = size;
                        animator = animatorK;
                        yVar2 = null;
                    } else {
                        yVar2 = new y();
                        yVar2.f103b = view;
                        y yVar5 = (y) ((C0135b) zVar2.f105a).get(view);
                        i2 = size;
                        if (yVar5 != null) {
                            for (String str2 : strArrP) {
                                yVar2.f102a.put(str2, yVar5.f102a.get(str2));
                            }
                        }
                        int i4 = c0135bO.f2611c;
                        int i5 = 0;
                        while (true) {
                            if (i5 >= i4) {
                                animator = animatorK;
                                break;
                            }
                            q qVar = (q) c0135bO.get((Animator) c0135bO.h(i5));
                            if (qVar.f66c != null && qVar.f64a == view && qVar.f65b.equals(str) && qVar.f66c.equals(yVar2)) {
                                animator = null;
                                break;
                            }
                            i5++;
                        }
                    }
                    animatorK = animator;
                    yVar = yVar2;
                } else {
                    i2 = size;
                    view = yVar3.f103b;
                    yVar = null;
                }
                if (animatorK != null) {
                    B b2 = A.f3a;
                    E e2 = new E(viewGroup);
                    q qVar2 = new q();
                    qVar2.f64a = view;
                    qVar2.f65b = str;
                    qVar2.f66c = yVar;
                    qVar2.f67d = e2;
                    qVar2.f68e = this;
                    c0135bO.put(animatorK, qVar2);
                    this.r.add(animatorK);
                }
            } else {
                i2 = size;
            }
            i3++;
            size = i2;
        }
        for (int i6 = 0; i6 < sparseIntArray.size(); i6++) {
            Animator animator2 = (Animator) this.r.get(sparseIntArray.keyAt(i6));
            animator2.setStartDelay(animator2.getStartDelay() + (((long) sparseIntArray.valueAt(i6)) - Long.MAX_VALUE));
        }
    }

    public final void m() {
        int i2 = this.f85n - 1;
        this.f85n = i2;
        if (i2 == 0) {
            ArrayList arrayList = this.f88q;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.f88q.clone();
                int size = arrayList2.size();
                for (int i3 = 0; i3 < size; i3++) {
                    ((r) arrayList2.get(i3)).a(this);
                }
            }
            for (int i4 = 0; i4 < ((h.e) this.f78g.f108d).f(); i4++) {
                View view = (View) ((h.e) this.f78g.f108d).g(i4);
                if (view != null) {
                    WeakHashMap weakHashMap = v.l.f2836a;
                    view.setHasTransientState(false);
                }
            }
            for (int i5 = 0; i5 < ((h.e) this.f79h.f108d).f(); i5++) {
                View view2 = (View) ((h.e) this.f79h.f108d).g(i5);
                if (view2 != null) {
                    WeakHashMap weakHashMap2 = v.l.f2836a;
                    view2.setHasTransientState(false);
                }
            }
            this.f87p = true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x002d, code lost:
    
        if (r3 < 0) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x002f, code lost:
    
        if (r7 == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0031, code lost:
    
        r5 = r5.f83l;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0034, code lost:
    
        r5 = r5.f82k;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x003d, code lost:
    
        return (C.y) r5.get(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:?, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final C.y n(android.view.View r6, boolean r7) {
        /*
            r5 = this;
            C.x r0 = r5.f80i
            if (r0 == 0) goto L9
            C.y r5 = r0.n(r6, r7)
            return r5
        L9:
            if (r7 == 0) goto Le
            java.util.ArrayList r0 = r5.f82k
            goto L10
        Le:
            java.util.ArrayList r0 = r5.f83l
        L10:
            r1 = 0
            if (r0 != 0) goto L14
            return r1
        L14:
            int r2 = r0.size()
            r3 = 0
        L19:
            if (r3 >= r2) goto L2c
            java.lang.Object r4 = r0.get(r3)
            C.y r4 = (C.y) r4
            if (r4 != 0) goto L24
            return r1
        L24:
            android.view.View r4 = r4.f103b
            if (r4 != r6) goto L29
            goto L2d
        L29:
            int r3 = r3 + 1
            goto L19
        L2c:
            r3 = -1
        L2d:
            if (r3 < 0) goto L3d
            if (r7 == 0) goto L34
            java.util.ArrayList r5 = r5.f83l
            goto L36
        L34:
            java.util.ArrayList r5 = r5.f82k
        L36:
            java.lang.Object r5 = r5.get(r3)
            r1 = r5
            C.y r1 = (C.y) r1
        L3d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: C.s.n(android.view.View, boolean):C.y");
    }

    public String[] p() {
        return null;
    }

    public final y q(View view, boolean z2) {
        x xVar = this.f80i;
        if (xVar != null) {
            return xVar.q(view, z2);
        }
        return (y) ((C0135b) (z2 ? this.f78g : this.f79h).f105a).get(view);
    }

    public boolean r(y yVar, y yVar2) {
        if (yVar == null || yVar2 == null) {
            return false;
        }
        String[] strArrP = p();
        if (strArrP == null) {
            Iterator it = yVar.f102a.keySet().iterator();
            while (it.hasNext()) {
                if (t(yVar, yVar2, (String) it.next())) {
                }
            }
            return false;
        }
        for (String str : strArrP) {
            if (!t(yVar, yVar2, str)) {
            }
        }
        return false;
        return true;
    }

    public final boolean s(View view) {
        int id = view.getId();
        ArrayList arrayList = this.f76e;
        int size = arrayList.size();
        ArrayList arrayList2 = this.f77f;
        return (size == 0 && arrayList2.size() == 0) || arrayList.contains(Integer.valueOf(id)) || arrayList2.contains(view);
    }

    public final String toString() {
        return G("");
    }

    public void u(View view) {
        if (this.f87p) {
            return;
        }
        C0135b c0135bO = o();
        int i2 = c0135bO.f2611c;
        B b2 = A.f3a;
        WindowId windowId = view.getWindowId();
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            q qVar = (q) c0135bO.j(i3);
            if (qVar.f64a != null) {
                E e2 = qVar.f67d;
                if ((e2 instanceof E) && e2.f25a.equals(windowId)) {
                    ((Animator) c0135bO.h(i3)).pause();
                }
            }
        }
        ArrayList arrayList = this.f88q;
        if (arrayList != null && arrayList.size() > 0) {
            ArrayList arrayList2 = (ArrayList) this.f88q.clone();
            int size = arrayList2.size();
            for (int i4 = 0; i4 < size; i4++) {
                ((r) arrayList2.get(i4)).b();
            }
        }
        this.f86o = true;
    }

    public void v(r rVar) {
        ArrayList arrayList = this.f88q;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(rVar);
        if (this.f88q.size() == 0) {
            this.f88q = null;
        }
    }

    public void w(View view) {
        this.f77f.remove(view);
    }

    public void x(ViewGroup viewGroup) {
        if (this.f86o) {
            if (!this.f87p) {
                C0135b c0135bO = o();
                int i2 = c0135bO.f2611c;
                B b2 = A.f3a;
                WindowId windowId = viewGroup.getWindowId();
                for (int i3 = i2 - 1; i3 >= 0; i3--) {
                    q qVar = (q) c0135bO.j(i3);
                    if (qVar.f64a != null) {
                        E e2 = qVar.f67d;
                        if ((e2 instanceof E) && e2.f25a.equals(windowId)) {
                            ((Animator) c0135bO.h(i3)).resume();
                        }
                    }
                }
                ArrayList arrayList = this.f88q;
                if (arrayList != null && arrayList.size() > 0) {
                    ArrayList arrayList2 = (ArrayList) this.f88q.clone();
                    int size = arrayList2.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        ((r) arrayList2.get(i4)).d();
                    }
                }
            }
            this.f86o = false;
        }
    }

    public void y() {
        F();
        C0135b c0135bO = o();
        for (Animator animator : this.r) {
            if (c0135bO.containsKey(animator)) {
                F();
                if (animator != null) {
                    animator.addListener(new o(this, c0135bO));
                    long j2 = this.f74c;
                    if (j2 >= 0) {
                        animator.setDuration(j2);
                    }
                    long j3 = this.f73b;
                    if (j3 >= 0) {
                        animator.setStartDelay(j3);
                    }
                    TimeInterpolator timeInterpolator = this.f75d;
                    if (timeInterpolator != null) {
                        animator.setInterpolator(timeInterpolator);
                    }
                    animator.addListener(new p(0, this));
                    animator.start();
                }
            }
        }
        this.r.clear();
        m();
    }

    public void z(long j2) {
        this.f74c = j2;
    }
}
