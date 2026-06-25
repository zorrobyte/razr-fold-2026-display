package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R$layout;
import androidx.appcompat.view.menu.ActionMenuItemView;
import java.util.ArrayList;

/* JADX INFO: renamed from: androidx.appcompat.widget.k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0074k implements androidx.appcompat.view.menu.A {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f1225a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Context f1226b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public androidx.appcompat.view.menu.o f1227c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final LayoutInflater f1228d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public androidx.appcompat.view.menu.z f1229e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final int f1230f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final int f1231g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public androidx.appcompat.view.menu.C f1232h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public C0072i f1233i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public Drawable f1234j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f1235k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public boolean f1236l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public boolean f1237m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public int f1238n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public int f1239o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public int f1240p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public boolean f1241q;
    public final SparseBooleanArray r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public View f1242s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public C0069f f1243t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public C0069f f1244u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public RunnableC0071h f1245v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public C0070g f1246w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public final e0.k f1247x;

    public C0074k(Context context) {
        int i2 = R$layout.abc_action_menu_layout;
        int i3 = R$layout.abc_action_menu_item_layout;
        this.f1225a = context;
        this.f1228d = LayoutInflater.from(context);
        this.f1230f = i2;
        this.f1231g = i3;
        this.r = new SparseBooleanArray();
        this.f1247x = new e0.k(this);
    }

    @Override // androidx.appcompat.view.menu.A
    public final void a(androidx.appcompat.view.menu.o oVar, boolean z2) {
        j();
        C0069f c0069f = this.f1244u;
        if (c0069f != null && c0069f.b()) {
            c0069f.f854j.dismiss();
        }
        androidx.appcompat.view.menu.z zVar = this.f1229e;
        if (zVar != null) {
            zVar.a(oVar, z2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.appcompat.view.menu.A
    public final boolean b() {
        ArrayList arrayListL;
        int size;
        boolean z2;
        boolean z3;
        androidx.appcompat.view.menu.o oVar = this.f1227c;
        boolean z4 = false;
        if (oVar != null) {
            arrayListL = oVar.l();
            size = arrayListL.size();
        } else {
            arrayListL = null;
            size = 0;
        }
        int i2 = this.f1240p;
        int i3 = this.f1239o;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) this.f1232h;
        int i4 = 0;
        boolean z5 = false;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            z2 = 1;
            if (i4 >= size) {
                break;
            }
            androidx.appcompat.view.menu.q qVar = (androidx.appcompat.view.menu.q) arrayListL.get(i4);
            if (qVar.requiresActionButton()) {
                i5++;
                z3 = z5;
            } else if ((qVar.f834y & 1) == 1) {
                i6++;
                z3 = z5;
            } else {
                z3 = true;
            }
            if (this.f1241q && qVar.f810C) {
                i2 = 0;
            }
            i4++;
            z5 = z3;
        }
        if (this.f1236l && (z5 || i6 + i5 > i2)) {
            i2--;
        }
        int i7 = i2 - i5;
        SparseBooleanArray sparseBooleanArray = this.r;
        sparseBooleanArray.clear();
        int i8 = 0;
        int i9 = 0;
        while (i8 < size) {
            androidx.appcompat.view.menu.q qVar2 = (androidx.appcompat.view.menu.q) arrayListL.get(i8);
            boolean zRequiresActionButton = qVar2.requiresActionButton();
            int i10 = qVar2.f812b;
            if (zRequiresActionButton) {
                View viewI = i(qVar2, this.f1242s, viewGroup);
                if (this.f1242s == null) {
                    this.f1242s = viewI;
                }
                viewI.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                int measuredWidth = viewI.getMeasuredWidth();
                i3 -= measuredWidth;
                if (i9 == 0) {
                    i9 = measuredWidth;
                }
                if (i10 != 0) {
                    sparseBooleanArray.put(i10, z2);
                }
                qVar2.g(z2);
            } else if ((qVar2.f834y & z2) == z2) {
                boolean z6 = sparseBooleanArray.get(i10);
                boolean z7 = ((i7 > 0 || z6) && i3 > 0) ? z2 : z4;
                if (z7 != 0) {
                    View viewI2 = i(qVar2, this.f1242s, viewGroup);
                    if (this.f1242s == null) {
                        this.f1242s = viewI2;
                    }
                    viewI2.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                    int measuredWidth2 = viewI2.getMeasuredWidth();
                    i3 -= measuredWidth2;
                    if (i9 == 0) {
                        i9 = measuredWidth2;
                    }
                    z7 = (z7 ? 1 : 0) & (i3 + i9 > 0 ? (char) 1 : (char) 0);
                }
                if (z7 != 0 && i10 != 0) {
                    sparseBooleanArray.put(i10, true);
                } else if (z6) {
                    sparseBooleanArray.put(i10, false);
                    for (int i11 = 0; i11 < i8; i11++) {
                        androidx.appcompat.view.menu.q qVar3 = (androidx.appcompat.view.menu.q) arrayListL.get(i11);
                        if (qVar3.f812b == i10) {
                            if (qVar3.f()) {
                                i7++;
                            }
                            qVar3.g(false);
                        }
                    }
                }
                if (z7 != 0) {
                    i7--;
                }
                qVar2.g(z7);
                z4 = false;
            } else {
                qVar2.g(z4);
            }
            i8++;
            z2 = 1;
        }
        return z2;
    }

    @Override // androidx.appcompat.view.menu.A
    public final void c(androidx.appcompat.view.menu.z zVar) {
        this.f1229e = zVar;
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean d(androidx.appcompat.view.menu.q qVar) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.A
    public final void e(Context context, androidx.appcompat.view.menu.o oVar) {
        this.f1226b = context;
        LayoutInflater.from(context);
        this.f1227c = oVar;
        Resources resources = context.getResources();
        if (!this.f1237m) {
            this.f1236l = true;
        }
        int i2 = 2;
        this.f1238n = context.getResources().getDisplayMetrics().widthPixels / 2;
        Configuration configuration = context.getResources().getConfiguration();
        int i3 = configuration.screenWidthDp;
        int i4 = configuration.screenHeightDp;
        if (configuration.smallestScreenWidthDp > 600 || i3 > 600 || ((i3 > 960 && i4 > 720) || (i3 > 720 && i4 > 960))) {
            i2 = 5;
        } else if (i3 >= 500 || ((i3 > 640 && i4 > 480) || (i3 > 480 && i4 > 640))) {
            i2 = 4;
        } else if (i3 >= 360) {
            i2 = 3;
        }
        this.f1240p = i2;
        int measuredWidth = this.f1238n;
        if (this.f1236l) {
            if (this.f1233i == null) {
                C0072i c0072i = new C0072i(this, this.f1225a);
                this.f1233i = c0072i;
                if (this.f1235k) {
                    c0072i.setImageDrawable(this.f1234j);
                    this.f1234j = null;
                    this.f1235k = false;
                }
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.f1233i.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            }
            measuredWidth -= this.f1233i.getMeasuredWidth();
        } else {
            this.f1233i = null;
        }
        this.f1239o = measuredWidth;
        float f2 = resources.getDisplayMetrics().density;
        this.f1242s = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.appcompat.view.menu.A
    public final void f() {
        int i2;
        ViewGroup viewGroup = (ViewGroup) this.f1232h;
        ArrayList arrayList = null;
        boolean z2 = false;
        if (viewGroup != null) {
            androidx.appcompat.view.menu.o oVar = this.f1227c;
            if (oVar != null) {
                oVar.i();
                ArrayList arrayListL = this.f1227c.l();
                int size = arrayListL.size();
                i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    androidx.appcompat.view.menu.q qVar = (androidx.appcompat.view.menu.q) arrayListL.get(i3);
                    if (qVar.f()) {
                        View childAt = viewGroup.getChildAt(i2);
                        androidx.appcompat.view.menu.q itemData = childAt instanceof androidx.appcompat.view.menu.B ? ((androidx.appcompat.view.menu.B) childAt).getItemData() : null;
                        View viewI = i(qVar, childAt, viewGroup);
                        if (qVar != itemData) {
                            viewI.setPressed(false);
                            viewI.jumpDrawablesToCurrentState();
                        }
                        if (viewI != childAt) {
                            ViewGroup viewGroup2 = (ViewGroup) viewI.getParent();
                            if (viewGroup2 != null) {
                                viewGroup2.removeView(viewI);
                            }
                            ((ViewGroup) this.f1232h).addView(viewI, i2);
                        }
                        i2++;
                    }
                }
            } else {
                i2 = 0;
            }
            while (i2 < viewGroup.getChildCount()) {
                if (viewGroup.getChildAt(i2) == this.f1233i) {
                    i2++;
                } else {
                    viewGroup.removeViewAt(i2);
                }
            }
        }
        ((View) this.f1232h).requestLayout();
        androidx.appcompat.view.menu.o oVar2 = this.f1227c;
        if (oVar2 != null) {
            oVar2.i();
            ArrayList arrayList2 = oVar2.f789i;
            int size2 = arrayList2.size();
            for (int i4 = 0; i4 < size2; i4++) {
                v.c cVar = ((androidx.appcompat.view.menu.q) arrayList2.get(i4)).f808A;
            }
        }
        androidx.appcompat.view.menu.o oVar3 = this.f1227c;
        if (oVar3 != null) {
            oVar3.i();
            arrayList = oVar3.f790j;
        }
        if (this.f1236l && arrayList != null) {
            int size3 = arrayList.size();
            if (size3 == 1) {
                z2 = !((androidx.appcompat.view.menu.q) arrayList.get(0)).f810C;
            } else if (size3 > 0) {
                z2 = true;
            }
        }
        if (z2) {
            if (this.f1233i == null) {
                this.f1233i = new C0072i(this, this.f1225a);
            }
            ViewGroup viewGroup3 = (ViewGroup) this.f1233i.getParent();
            if (viewGroup3 != this.f1232h) {
                if (viewGroup3 != null) {
                    viewGroup3.removeView(this.f1233i);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.f1232h;
                C0072i c0072i = this.f1233i;
                actionMenuView.getClass();
                C0077n c0077nJ = ActionMenuView.j();
                c0077nJ.f1270c = true;
                actionMenuView.addView(c0072i, c0077nJ);
            }
        } else {
            C0072i c0072i2 = this.f1233i;
            if (c0072i2 != null) {
                Object parent = c0072i2.getParent();
                Object obj = this.f1232h;
                if (parent == obj) {
                    ((ViewGroup) obj).removeView(this.f1233i);
                }
            }
        }
        ((ActionMenuView) this.f1232h).setOverflowReserved(this.f1236l);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.appcompat.view.menu.A
    public final boolean g(androidx.appcompat.view.menu.G g2) {
        boolean z2;
        if (!g2.hasVisibleItems()) {
            return false;
        }
        androidx.appcompat.view.menu.G g3 = g2;
        while (true) {
            androidx.appcompat.view.menu.o oVar = g3.f694A;
            if (oVar == this.f1227c) {
                break;
            }
            g3 = (androidx.appcompat.view.menu.G) oVar;
        }
        ViewGroup viewGroup = (ViewGroup) this.f1232h;
        View view = null;
        view = null;
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            int i2 = 0;
            while (true) {
                if (i2 >= childCount) {
                    break;
                }
                View childAt = viewGroup.getChildAt(i2);
                if ((childAt instanceof androidx.appcompat.view.menu.B) && ((androidx.appcompat.view.menu.B) childAt).getItemData() == g3.f695B) {
                    view = childAt;
                    break;
                }
                i2++;
            }
        }
        if (view == null) {
            return false;
        }
        int i3 = g2.f695B.f811a;
        int size = g2.f786f.size();
        int i4 = 0;
        while (true) {
            if (i4 >= size) {
                z2 = false;
                break;
            }
            MenuItem item = g2.getItem(i4);
            if (item.isVisible() && item.getIcon() != null) {
                z2 = true;
                break;
            }
            i4++;
        }
        C0069f c0069f = new C0069f(this, this.f1226b, g2, view);
        this.f1244u = c0069f;
        c0069f.f852h = z2;
        androidx.appcompat.view.menu.w wVar = c0069f.f854j;
        if (wVar != null) {
            wVar.l(z2);
        }
        C0069f c0069f2 = this.f1244u;
        if (!c0069f2.b()) {
            if (c0069f2.f850f == null) {
                throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
            }
            c0069f2.d(0, 0, false, false);
        }
        androidx.appcompat.view.menu.z zVar = this.f1229e;
        if (zVar != null) {
            zVar.e(g2);
        }
        return true;
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean h(androidx.appcompat.view.menu.q qVar) {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final View i(androidx.appcompat.view.menu.q qVar, View view, ViewGroup viewGroup) {
        View actionView = qVar.getActionView();
        if (actionView == null || qVar.e()) {
            androidx.appcompat.view.menu.B b2 = view instanceof androidx.appcompat.view.menu.B ? (androidx.appcompat.view.menu.B) view : (androidx.appcompat.view.menu.B) this.f1228d.inflate(this.f1231g, viewGroup, false);
            b2.c(qVar);
            ActionMenuItemView actionMenuItemView = (ActionMenuItemView) b2;
            actionMenuItemView.setItemInvoker((ActionMenuView) this.f1232h);
            if (this.f1246w == null) {
                this.f1246w = new C0070g(this);
            }
            actionMenuItemView.setPopupCallback(this.f1246w);
            actionView = (View) b2;
        }
        actionView.setVisibility(qVar.f810C ? 8 : 0);
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        if (!((ActionMenuView) viewGroup).checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(ActionMenuView.k(layoutParams));
        }
        return actionView;
    }

    public final boolean j() {
        Object obj;
        RunnableC0071h runnableC0071h = this.f1245v;
        if (runnableC0071h != null && (obj = this.f1232h) != null) {
            ((View) obj).removeCallbacks(runnableC0071h);
            this.f1245v = null;
            return true;
        }
        C0069f c0069f = this.f1243t;
        if (c0069f == null) {
            return false;
        }
        if (c0069f.b()) {
            c0069f.f854j.dismiss();
        }
        return true;
    }

    public final boolean k() {
        C0069f c0069f = this.f1243t;
        return c0069f != null && c0069f.b();
    }

    public final boolean l() {
        androidx.appcompat.view.menu.o oVar;
        if (!this.f1236l || k() || (oVar = this.f1227c) == null || this.f1232h == null || this.f1245v != null) {
            return false;
        }
        oVar.i();
        if (oVar.f790j.isEmpty()) {
            return false;
        }
        RunnableC0071h runnableC0071h = new RunnableC0071h(this, new C0069f(this, this.f1226b, this.f1227c, this.f1233i));
        this.f1245v = runnableC0071h;
        ((View) this.f1232h).post(runnableC0071h);
        androidx.appcompat.view.menu.z zVar = this.f1229e;
        if (zVar == null) {
            return true;
        }
        zVar.e(null);
        return true;
    }
}
