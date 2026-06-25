package C;

import X.w0;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.O;
import androidx.transition.R$id;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class m extends O {
    @Override // androidx.fragment.app.O
    public final void a(View view, Object obj) {
        if (obj != null) {
            ((s) obj).b(view);
        }
    }

    @Override // androidx.fragment.app.O
    public final void b(Object obj, ArrayList arrayList) {
        s sVar = (s) obj;
        if (sVar == null) {
            return;
        }
        int i2 = 0;
        if (sVar instanceof x) {
            x xVar = (x) sVar;
            int size = xVar.f99x.size();
            while (i2 < size) {
                b((i2 < 0 || i2 >= xVar.f99x.size()) ? null : (s) xVar.f99x.get(i2), arrayList);
                i2++;
            }
            return;
        }
        if (O.j(sVar.f76e) && O.j(null) && O.j(null) && O.j(sVar.f77f)) {
            int size2 = arrayList.size();
            while (i2 < size2) {
                sVar.b((View) arrayList.get(i2));
                i2++;
            }
        }
    }

    @Override // androidx.fragment.app.O
    public final void c(ViewGroup viewGroup, Object obj) {
        s sVar = (s) obj;
        ArrayList arrayList = w.f96b;
        if (arrayList.contains(viewGroup)) {
            return;
        }
        WeakHashMap weakHashMap = v.l.f2836a;
        if (viewGroup.isLaidOut()) {
            arrayList.add(viewGroup);
            s sVarClone = sVar.clone();
            ArrayList arrayList2 = (ArrayList) w.a().get(viewGroup);
            if (arrayList2 != null && arrayList2.size() > 0) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    ((s) it.next()).u(viewGroup);
                }
            }
            if (sVarClone != null) {
                sVarClone.h(viewGroup, true);
            }
            w0.c(viewGroup.getTag(R$id.transition_current_scene));
            viewGroup.setTag(R$id.transition_current_scene, null);
            if (sVarClone != null) {
                v vVar = new v();
                vVar.f93a = sVarClone;
                vVar.f94b = viewGroup;
                viewGroup.addOnAttachStateChangeListener(vVar);
                viewGroup.getViewTreeObserver().addOnPreDrawListener(vVar);
            }
        }
    }

    @Override // androidx.fragment.app.O
    public final boolean e(Object obj) {
        return obj instanceof s;
    }

    @Override // androidx.fragment.app.O
    public final Object g(Object obj) {
        if (obj != null) {
            return ((s) obj).clone();
        }
        return null;
    }

    @Override // androidx.fragment.app.O
    public final Object k(Object obj, Object obj2, Object obj3) {
        s sVar = (s) obj;
        s sVar2 = (s) obj2;
        s sVar3 = (s) obj3;
        if (sVar != null && sVar2 != null) {
            x xVar = new x();
            xVar.H(sVar);
            xVar.H(sVar2);
            xVar.f100y = false;
            sVar = xVar;
        } else if (sVar == null) {
            sVar = sVar2 != null ? sVar2 : null;
        }
        if (sVar3 == null) {
            return sVar;
        }
        x xVar2 = new x();
        if (sVar != null) {
            xVar2.H(sVar);
        }
        xVar2.H(sVar3);
        return xVar2;
    }

    @Override // androidx.fragment.app.O
    public final Object l(Object obj, Object obj2, Object obj3) {
        x xVar = new x();
        if (obj != null) {
            xVar.H((s) obj);
        }
        if (obj2 != null) {
            xVar.H((s) obj2);
        }
        if (obj3 != null) {
            xVar.H((s) obj3);
        }
        return xVar;
    }

    @Override // androidx.fragment.app.O
    public final void m(View view, Object obj) {
        if (obj != null) {
            ((s) obj).w(view);
        }
    }

    @Override // androidx.fragment.app.O
    public final void n(Object obj, ArrayList arrayList, ArrayList arrayList2) {
        s sVar = (s) obj;
        int i2 = 0;
        if (sVar instanceof x) {
            x xVar = (x) sVar;
            int size = xVar.f99x.size();
            while (i2 < size) {
                n((i2 < 0 || i2 >= xVar.f99x.size()) ? null : (s) xVar.f99x.get(i2), arrayList, arrayList2);
                i2++;
            }
            return;
        }
        if (O.j(sVar.f76e) && O.j(null) && O.j(null)) {
            ArrayList arrayList3 = sVar.f77f;
            if (arrayList3.size() == arrayList.size() && arrayList3.containsAll(arrayList)) {
                int size2 = arrayList2 == null ? 0 : arrayList2.size();
                while (i2 < size2) {
                    sVar.b((View) arrayList2.get(i2));
                    i2++;
                }
                for (int size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                    sVar.w((View) arrayList.get(size3));
                }
            }
        }
    }

    @Override // androidx.fragment.app.O
    public final void o(Object obj, View view, ArrayList arrayList) {
        ((s) obj).a(new k(view, arrayList));
    }

    @Override // androidx.fragment.app.O
    public final void p(Object obj, Object obj2, ArrayList arrayList, Object obj3, ArrayList arrayList2, Object obj4, ArrayList arrayList3) {
        ((s) obj).a(new l(this, obj2, arrayList, obj3, arrayList2, obj4, arrayList3));
    }

    @Override // androidx.fragment.app.O
    public final void q(View view, Object obj) {
        if (view != null) {
            O.i(new Rect(), view);
            ((s) obj).A(new j());
        }
    }

    @Override // androidx.fragment.app.O
    public final void r(Object obj, Rect rect) {
        if (obj != null) {
            ((s) obj).A(new j());
        }
    }

    @Override // androidx.fragment.app.O
    public final void s(Object obj, View view, ArrayList arrayList) {
        x xVar = (x) obj;
        ArrayList arrayList2 = xVar.f77f;
        arrayList2.clear();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            O.d(arrayList2, (View) arrayList.get(i2));
        }
        arrayList2.add(view);
        arrayList.add(view);
        b(xVar, arrayList);
    }

    @Override // androidx.fragment.app.O
    public final void t(Object obj, ArrayList arrayList, ArrayList arrayList2) {
        x xVar = (x) obj;
        if (xVar != null) {
            ArrayList arrayList3 = xVar.f77f;
            arrayList3.clear();
            arrayList3.addAll(arrayList2);
            n(xVar, arrayList, arrayList2);
        }
    }

    @Override // androidx.fragment.app.O
    public final Object u(Object obj) {
        if (obj == null) {
            return null;
        }
        x xVar = new x();
        xVar.H((s) obj);
        return xVar;
    }
}
