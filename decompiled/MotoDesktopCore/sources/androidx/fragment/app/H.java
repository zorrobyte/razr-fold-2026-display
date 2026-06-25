package androidx.fragment.app;

import android.view.View;
import h.C0134a;
import h.C0135b;
import java.util.ArrayList;
import java.util.Collection;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class H {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final int[] f1562a = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8};

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final L f1563b = new L();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final O f1564c;

    static {
        O o2 = null;
        try {
            o2 = (O) C.m.class.getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
        }
        f1564c = o2;
    }

    public static void a(ArrayList arrayList, C0135b c0135b, Collection collection) {
        for (int i2 = c0135b.f2611c - 1; i2 >= 0; i2--) {
            View view = (View) c0135b.j(i2);
            WeakHashMap weakHashMap = v.l.f2836a;
            if (collection.contains(view.getTransitionName())) {
                arrayList.add(view);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0093  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void b(androidx.fragment.app.C0090b r16, androidx.fragment.app.C0089a r17, android.util.SparseArray r18, boolean r19, boolean r20) {
        /*
            Method dump skipped, instruction units count: 256
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.H.b(androidx.fragment.app.b, androidx.fragment.app.a, android.util.SparseArray, boolean, boolean):void");
    }

    public static C0135b c(O o2, C0135b c0135b, Object obj, G g2) {
        ArrayList arrayList;
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = g2.f1556a;
        View view = abstractComponentCallbacksC0098j.getView();
        if (c0135b.isEmpty() || obj == null || view == null) {
            c0135b.clear();
            return null;
        }
        C0135b c0135b2 = new C0135b();
        o2.getClass();
        O.h(c0135b2, view);
        C0090b c0090b = g2.f1558c;
        if (g2.f1557b) {
            abstractComponentCallbacksC0098j.getExitTransitionCallback();
            arrayList = c0090b.f1612q;
        } else {
            abstractComponentCallbacksC0098j.getEnterTransitionCallback();
            arrayList = c0090b.r;
        }
        if (arrayList != null) {
            C0134a.k(c0135b2, arrayList);
            C0134a.k(c0135b2, c0135b.values());
        }
        for (int i2 = c0135b.f2611c - 1; i2 >= 0; i2--) {
            if (!c0135b2.containsKey((String) c0135b.j(i2))) {
                c0135b.i(i2);
            }
        }
        return c0135b2;
    }

    public static C0135b d(C0135b c0135b, Object obj, G g2) {
        ArrayList arrayList;
        if (c0135b.isEmpty() || obj == null) {
            c0135b.clear();
            return null;
        }
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = g2.f1559d;
        C0135b c0135b2 = new C0135b();
        O.h(c0135b2, abstractComponentCallbacksC0098j.getView());
        C0090b c0090b = g2.f1561f;
        if (g2.f1560e) {
            abstractComponentCallbacksC0098j.getEnterTransitionCallback();
            arrayList = c0090b.r;
        } else {
            abstractComponentCallbacksC0098j.getExitTransitionCallback();
            arrayList = c0090b.f1612q;
        }
        C0134a.k(c0135b2, arrayList);
        C0134a.k(c0135b, c0135b2.keySet());
        return c0135b2;
    }

    public static O e(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j2) {
        ArrayList arrayList = new ArrayList();
        if (abstractComponentCallbacksC0098j != null) {
            Object exitTransition = abstractComponentCallbacksC0098j.getExitTransition();
            if (exitTransition != null) {
                arrayList.add(exitTransition);
            }
            Object returnTransition = abstractComponentCallbacksC0098j.getReturnTransition();
            if (returnTransition != null) {
                arrayList.add(returnTransition);
            }
            Object sharedElementReturnTransition = abstractComponentCallbacksC0098j.getSharedElementReturnTransition();
            if (sharedElementReturnTransition != null) {
                arrayList.add(sharedElementReturnTransition);
            }
        }
        if (abstractComponentCallbacksC0098j2 != null) {
            Object enterTransition = abstractComponentCallbacksC0098j2.getEnterTransition();
            if (enterTransition != null) {
                arrayList.add(enterTransition);
            }
            Object reenterTransition = abstractComponentCallbacksC0098j2.getReenterTransition();
            if (reenterTransition != null) {
                arrayList.add(reenterTransition);
            }
            Object sharedElementEnterTransition = abstractComponentCallbacksC0098j2.getSharedElementEnterTransition();
            if (sharedElementEnterTransition != null) {
                arrayList.add(sharedElementEnterTransition);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        L l2 = f1563b;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (!l2.e(arrayList.get(i2))) {
                O o2 = f1564c;
                if (o2 != null) {
                    int size2 = arrayList.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        if (o2.e(arrayList.get(i3))) {
                        }
                    }
                    return o2;
                }
                throw new IllegalArgumentException("Invalid Transition types");
            }
        }
        return l2;
    }

    public static ArrayList f(O o2, Object obj, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j, ArrayList arrayList, View view) {
        if (obj == null) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        View view2 = abstractComponentCallbacksC0098j.getView();
        if (view2 != null) {
            o2.getClass();
            O.f(view2, arrayList2);
        }
        if (arrayList != null) {
            arrayList2.removeAll(arrayList);
        }
        if (arrayList2.isEmpty()) {
            return arrayList2;
        }
        arrayList2.add(view);
        o2.b(obj, arrayList2);
        return arrayList2;
    }

    public static View g(C0135b c0135b, G g2, Object obj, boolean z2) {
        ArrayList arrayList;
        C0090b c0090b = g2.f1558c;
        if (obj == null || c0135b == null || (arrayList = c0090b.f1612q) == null || arrayList.isEmpty()) {
            return null;
        }
        return (View) c0135b.get(z2 ? (String) c0090b.f1612q.get(0) : (String) c0090b.r.get(0));
    }

    public static Object h(O o2, Object obj, Object obj2, Object obj3, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j, boolean z2) {
        return (obj == null || obj2 == null || abstractComponentCallbacksC0098j == null) ? true : z2 ? abstractComponentCallbacksC0098j.getAllowReturnTransitionOverlap() : abstractComponentCallbacksC0098j.getAllowEnterTransitionOverlap() ? o2.l(obj2, obj, obj3) : o2.k(obj2, obj, obj3);
    }

    public static void i(O o2, Object obj, Object obj2, C0135b c0135b, boolean z2, C0090b c0090b) {
        ArrayList arrayList = c0090b.f1612q;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        View view = (View) c0135b.get(z2 ? (String) c0090b.r.get(0) : (String) c0090b.f1612q.get(0));
        o2.q(view, obj);
        if (obj2 != null) {
            o2.q(view, obj2);
        }
    }

    public static void j(ArrayList arrayList, int i2) {
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((View) arrayList.get(size)).setVisibility(i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0256  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void k(androidx.fragment.app.z r38, java.util.ArrayList r39, java.util.ArrayList r40, int r41, int r42, boolean r43) {
        /*
            Method dump skipped, instruction units count: 1232
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.H.k(androidx.fragment.app.z, java.util.ArrayList, java.util.ArrayList, int, int, boolean):void");
    }
}
