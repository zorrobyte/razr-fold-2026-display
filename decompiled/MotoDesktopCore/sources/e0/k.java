package e0;

import android.os.SystemClock;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroupOverlay;
import android.view.WindowInsets;
import androidx.appcompat.view.menu.G;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.C0074k;
import androidx.appcompat.widget.InterfaceC0078o;
import androidx.appcompat.widget.S;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.r0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;
import k.C0146e;

/* JADX INFO: loaded from: classes.dex */
public final class k implements S, androidx.appcompat.view.menu.z, androidx.appcompat.view.menu.m, InterfaceC0078o, v.j {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static k f2494b;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Object f2495a;

    public /* synthetic */ k(Object obj) {
        this.f2495a = obj;
    }

    public static k h() {
        if (f2494b == null) {
            synchronized (k.class) {
                try {
                    if (f2494b == null) {
                        f2494b = new k();
                    }
                } finally {
                }
            }
        }
        return f2494b;
    }

    public static int i(int i2, int i3) {
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < i2; i6++) {
            i4++;
            if (i4 == i3) {
                i5++;
                i4 = 0;
            } else if (i4 > i3) {
                i5++;
                i4 = 1;
            }
        }
        return i4 + 1 > i3 ? i5 + 1 : i5;
    }

    @Override // androidx.appcompat.view.menu.z
    public void a(androidx.appcompat.view.menu.o oVar, boolean z2) {
        if (oVar instanceof G) {
            ((G) oVar).f694A.k().c(false);
        }
        androidx.appcompat.view.menu.z zVar = ((C0074k) this.f2495a).f1229e;
        if (zVar != null) {
            zVar.a(oVar, z2);
        }
    }

    @Override // androidx.appcompat.widget.S
    public void b(androidx.appcompat.view.menu.o oVar, androidx.appcompat.view.menu.q qVar) {
        androidx.appcompat.view.menu.j jVar = (androidx.appcompat.view.menu.j) this.f2495a;
        jVar.f752g.removeCallbacksAndMessages(null);
        ArrayList arrayList = jVar.f754i;
        int size = arrayList.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            } else if (oVar == ((androidx.appcompat.view.menu.i) arrayList.get(i2)).f743b) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 == -1) {
            return;
        }
        int i3 = i2 + 1;
        jVar.f752g.postAtTime(new androidx.appcompat.view.menu.h(this, i3 < arrayList.size() ? (androidx.appcompat.view.menu.i) arrayList.get(i3) : null, qVar, oVar), oVar, SystemClock.uptimeMillis() + 200);
    }

    @Override // androidx.appcompat.view.menu.m
    public boolean c(androidx.appcompat.view.menu.o oVar, MenuItem menuItem) {
        InterfaceC0078o interfaceC0078o = ((ActionMenuView) this.f2495a).f917A;
        if (interfaceC0078o == null) {
            return false;
        }
        r0 r0Var = ((Toolbar) ((k) interfaceC0078o).f2495a).f1145G;
        return r0Var != null ? ((androidx.appcompat.app.u) r0Var).f644a.f650j.f2410a.onMenuItemSelected(0, menuItem) : false;
    }

    @Override // androidx.appcompat.widget.S
    public void d(androidx.appcompat.view.menu.o oVar, MenuItem menuItem) {
        ((androidx.appcompat.view.menu.j) this.f2495a).f752g.removeCallbacksAndMessages(oVar);
    }

    @Override // androidx.appcompat.view.menu.z
    public boolean e(androidx.appcompat.view.menu.o oVar) {
        if (oVar == null) {
            return false;
        }
        int i2 = ((G) oVar).f695B.f811a;
        C0074k c0074k = (C0074k) this.f2495a;
        c0074k.getClass();
        androidx.appcompat.view.menu.z zVar = c0074k.f1229e;
        if (zVar != null) {
            return zVar.e(oVar);
        }
        return false;
    }

    @Override // androidx.appcompat.view.menu.m
    public void f(androidx.appcompat.view.menu.o oVar) {
        androidx.appcompat.view.menu.m mVar = ((ActionMenuView) this.f2495a).f923v;
        if (mVar != null) {
            mVar.f(oVar);
        }
    }

    @Override // v.j
    public v.o g(View view, v.o oVar) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) this.f2495a;
        if (!Objects.equals(coordinatorLayout.f1385m, oVar)) {
            coordinatorLayout.f1385m = oVar;
            boolean z2 = oVar != null && oVar.c() > 0;
            coordinatorLayout.f1386n = z2;
            coordinatorLayout.setWillNotDraw(!z2 && coordinatorLayout.getBackground() == null);
            if (!((WindowInsets) oVar.f2838a).isConsumed()) {
                int childCount = coordinatorLayout.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = coordinatorLayout.getChildAt(i2);
                    WeakHashMap weakHashMap = v.l.f2836a;
                    if (childAt.getFitsSystemWindows() && ((C0146e) childAt.getLayoutParams()).f2746a != null && ((WindowInsets) oVar.f2838a).isConsumed()) {
                        break;
                    }
                }
            }
            coordinatorLayout.requestLayout();
        }
        return oVar;
    }

    public void j() {
        ((SparseIntArray) this.f2495a).clear();
    }

    public void k(View view) {
        ((ViewGroupOverlay) this.f2495a).remove(view);
    }
}
