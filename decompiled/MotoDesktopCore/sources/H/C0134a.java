package h;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: h.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0134a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public h f2565a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public h f2566b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public j f2567c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ int f2568d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f2569e;

    public /* synthetic */ C0134a(int i2, Object obj) {
        this.f2568d = i2;
        this.f2569e = obj;
    }

    public static boolean j(Set set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set2 = (Set) obj;
            try {
                if (set.size() == set2.size()) {
                    if (set.containsAll(set2)) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    public static boolean k(Map map, Collection collection) {
        int size = map.size();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
            }
        }
        return size != map.size();
    }

    public final void a() {
        switch (this.f2568d) {
            case 0:
                ((C0135b) this.f2569e).clear();
                break;
            default:
                ((C0136c) this.f2569e).clear();
                break;
        }
    }

    public final Object b(int i2, int i3) {
        switch (this.f2568d) {
            case 0:
                return ((C0135b) this.f2569e).f2610b[(i2 << 1) + i3];
            default:
                return ((C0136c) this.f2569e).f2578b[i2];
        }
    }

    public final Map c() {
        switch (this.f2568d) {
            case 0:
                return (C0135b) this.f2569e;
            default:
                throw new UnsupportedOperationException("not a map");
        }
    }

    public final int d() {
        switch (this.f2568d) {
            case 0:
                return ((C0135b) this.f2569e).f2611c;
            default:
                return ((C0136c) this.f2569e).f2579c;
        }
    }

    public final int e(Object obj) {
        switch (this.f2568d) {
            case 0:
                return ((C0135b) this.f2569e).e(obj);
            default:
                return ((C0136c) this.f2569e).indexOf(obj);
        }
    }

    public final int f(Object obj) {
        switch (this.f2568d) {
            case 0:
                return ((C0135b) this.f2569e).g(obj);
            default:
                return ((C0136c) this.f2569e).indexOf(obj);
        }
    }

    public final void g(Object obj, Object obj2) {
        switch (this.f2568d) {
            case 0:
                ((C0135b) this.f2569e).put(obj, obj2);
                break;
            default:
                ((C0136c) this.f2569e).add(obj);
                break;
        }
    }

    public final void h(int i2) {
        switch (this.f2568d) {
            case 0:
                ((C0135b) this.f2569e).i(i2);
                break;
            default:
                ((C0136c) this.f2569e).e(i2);
                break;
        }
    }

    public final Object i(int i2, Object obj) {
        switch (this.f2568d) {
            case 0:
                int i3 = (i2 << 1) + 1;
                Object[] objArr = ((C0135b) this.f2569e).f2610b;
                Object obj2 = objArr[i3];
                objArr[i3] = obj;
                return obj2;
            default:
                throw new UnsupportedOperationException("not a map");
        }
    }

    public final Object[] l(int i2, Object[] objArr) {
        int iD = d();
        if (objArr.length < iD) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), iD);
        }
        for (int i3 = 0; i3 < iD; i3++) {
            objArr[i3] = b(i3, i2);
        }
        if (objArr.length > iD) {
            objArr[iD] = null;
        }
        return objArr;
    }
}
