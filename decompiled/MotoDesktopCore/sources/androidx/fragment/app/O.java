package androidx.fragment.app;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import h.C0135b;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class O {
    public static void d(List list, View view) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (list.get(i2) == view) {
                return;
            }
        }
        list.add(view);
        for (int i3 = size; i3 < list.size(); i3++) {
            View view2 = (View) list.get(i3);
            if (view2 instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view2;
                int childCount = viewGroup.getChildCount();
                for (int i4 = 0; i4 < childCount; i4++) {
                    View childAt = viewGroup.getChildAt(i4);
                    int i5 = 0;
                    while (true) {
                        if (i5 >= size) {
                            list.add(childAt);
                            break;
                        } else if (list.get(i5) == childAt) {
                            break;
                        } else {
                            i5++;
                        }
                    }
                }
            }
        }
    }

    public static void f(View view, ArrayList arrayList) {
        if (view.getVisibility() == 0) {
            if (!(view instanceof ViewGroup)) {
                arrayList.add(view);
                return;
            }
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.isTransitionGroup()) {
                arrayList.add(viewGroup);
                return;
            }
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                f(viewGroup.getChildAt(i2), arrayList);
            }
        }
    }

    public static void h(C0135b c0135b, View view) {
        if (view.getVisibility() == 0) {
            WeakHashMap weakHashMap = v.l.f2836a;
            String transitionName = view.getTransitionName();
            if (transitionName != null) {
                c0135b.put(transitionName, view);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    h(c0135b, viewGroup.getChildAt(i2));
                }
            }
        }
    }

    public static void i(Rect rect, View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        rect.set(i2, iArr[1], view.getWidth() + i2, view.getHeight() + iArr[1]);
    }

    public static boolean j(List list) {
        return list == null || list.isEmpty();
    }

    public abstract void a(View view, Object obj);

    public abstract void b(Object obj, ArrayList arrayList);

    public abstract void c(ViewGroup viewGroup, Object obj);

    public abstract boolean e(Object obj);

    public abstract Object g(Object obj);

    public abstract Object k(Object obj, Object obj2, Object obj3);

    public abstract Object l(Object obj, Object obj2, Object obj3);

    public abstract void m(View view, Object obj);

    public abstract void n(Object obj, ArrayList arrayList, ArrayList arrayList2);

    public abstract void o(Object obj, View view, ArrayList arrayList);

    public abstract void p(Object obj, Object obj2, ArrayList arrayList, Object obj3, ArrayList arrayList2, Object obj4, ArrayList arrayList3);

    public abstract void q(View view, Object obj);

    public abstract void r(Object obj, Rect rect);

    public abstract void s(Object obj, View view, ArrayList arrayList);

    public abstract void t(Object obj, ArrayList arrayList, ArrayList arrayList2);

    public abstract Object u(Object obj);
}
