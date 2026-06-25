package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import androidx.appcompat.R$dimen;
import androidx.appcompat.R$layout;
import androidx.appcompat.widget.I;
import androidx.appcompat.widget.T;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class j extends w implements View.OnKeyListener, PopupWindow.OnDismissListener {

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public static final int f745B = R$layout.abc_cascading_menu_item_layout;

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public boolean f746A;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Context f747b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f748c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final int f749d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final int f750e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final boolean f751f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final Handler f752g;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final ViewTreeObserverOnGlobalLayoutListenerC0062f f755j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final ViewOnAttachStateChangeListenerC0063g f756k;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public View f760o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public View f761p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public int f762q;
    public boolean r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public boolean f763s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public int f764t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public int f765u;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public boolean f767w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public z f768x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public ViewTreeObserver f769y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public PopupWindow.OnDismissListener f770z;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final ArrayList f753h = new ArrayList();

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final ArrayList f754i = new ArrayList();

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final e0.k f757l = new e0.k(this);

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public int f758m = 0;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public int f759n = 0;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public boolean f766v = false;

    public j(Context context, View view, int i2, int i3, boolean z2) {
        this.f755j = new ViewTreeObserverOnGlobalLayoutListenerC0062f(this, i);
        this.f756k = new ViewOnAttachStateChangeListenerC0063g(this, i);
        this.f747b = context;
        this.f760o = view;
        this.f749d = i2;
        this.f750e = i3;
        this.f751f = z2;
        WeakHashMap weakHashMap = v.l.f2836a;
        this.f762q = view.getLayoutDirection() != 1 ? 1 : 0;
        Resources resources = context.getResources();
        this.f748c = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R$dimen.abc_config_prefDialogWidth));
        this.f752g = new Handler();
    }

    @Override // androidx.appcompat.view.menu.A
    public final void a(o oVar, boolean z2) {
        ArrayList arrayList = this.f754i;
        int size = arrayList.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            } else if (oVar == ((i) arrayList.get(i2)).f743b) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 < 0) {
            return;
        }
        int i3 = i2 + 1;
        if (i3 < arrayList.size()) {
            ((i) arrayList.get(i3)).f743b.c(false);
        }
        i iVar = (i) arrayList.remove(i2);
        iVar.f743b.r(this);
        boolean z3 = this.f746A;
        T t2 = iVar.f742a;
        if (z3) {
            t2.f1070y.setExitTransition(null);
            t2.f1070y.setAnimationStyle(0);
        }
        t2.dismiss();
        int size2 = arrayList.size();
        if (size2 > 0) {
            this.f762q = ((i) arrayList.get(size2 - 1)).f744c;
        } else {
            View view = this.f760o;
            WeakHashMap weakHashMap = v.l.f2836a;
            this.f762q = view.getLayoutDirection() == 1 ? 0 : 1;
        }
        if (size2 != 0) {
            if (z2) {
                ((i) arrayList.get(0)).f743b.c(false);
                return;
            }
            return;
        }
        dismiss();
        z zVar = this.f768x;
        if (zVar != null) {
            zVar.a(oVar, true);
        }
        ViewTreeObserver viewTreeObserver = this.f769y;
        if (viewTreeObserver != null) {
            if (viewTreeObserver.isAlive()) {
                this.f769y.removeGlobalOnLayoutListener(this.f755j);
            }
            this.f769y = null;
        }
        this.f761p.removeOnAttachStateChangeListener(this.f756k);
        this.f770z.onDismiss();
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean b() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.A
    public final void c(z zVar) {
        this.f768x = zVar;
    }

    @Override // androidx.appcompat.view.menu.E
    public final void dismiss() {
        ArrayList arrayList = this.f754i;
        int size = arrayList.size();
        if (size > 0) {
            i[] iVarArr = (i[]) arrayList.toArray(new i[size]);
            for (int i2 = size - 1; i2 >= 0; i2--) {
                i iVar = iVarArr[i2];
                if (iVar.f742a.f1070y.isShowing()) {
                    iVar.f742a.dismiss();
                }
            }
        }
    }

    @Override // androidx.appcompat.view.menu.A
    public final void f() {
        Iterator it = this.f754i.iterator();
        while (it.hasNext()) {
            ListAdapter adapter = ((i) it.next()).f742a.f1049c.getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
            }
            ((MenuAdapter) adapter).notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean g(G g2) {
        for (i iVar : this.f754i) {
            if (g2 == iVar.f743b) {
                iVar.f742a.f1049c.requestFocus();
                return true;
            }
        }
        if (!g2.hasVisibleItems()) {
            return false;
        }
        i(g2);
        z zVar = this.f768x;
        if (zVar != null) {
            zVar.e(g2);
        }
        return true;
    }

    @Override // androidx.appcompat.view.menu.E
    public final I getListView() {
        ArrayList arrayList = this.f754i;
        if (arrayList.isEmpty()) {
            return null;
        }
        return ((i) arrayList.get(arrayList.size() - 1)).f742a.f1049c;
    }

    @Override // androidx.appcompat.view.menu.w
    public final void i(o oVar) {
        oVar.b(this, this.f747b);
        if (isShowing()) {
            s(oVar);
        } else {
            this.f753h.add(oVar);
        }
    }

    @Override // androidx.appcompat.view.menu.E
    public final boolean isShowing() {
        ArrayList arrayList = this.f754i;
        return arrayList.size() > 0 && ((i) arrayList.get(0)).f742a.f1070y.isShowing();
    }

    @Override // androidx.appcompat.view.menu.w
    public final void k(View view) {
        if (this.f760o != view) {
            this.f760o = view;
            int i2 = this.f758m;
            WeakHashMap weakHashMap = v.l.f2836a;
            this.f759n = Gravity.getAbsoluteGravity(i2, view.getLayoutDirection());
        }
    }

    @Override // androidx.appcompat.view.menu.w
    public final void l(boolean z2) {
        this.f766v = z2;
    }

    @Override // androidx.appcompat.view.menu.w
    public final void m(int i2) {
        if (this.f758m != i2) {
            this.f758m = i2;
            View view = this.f760o;
            WeakHashMap weakHashMap = v.l.f2836a;
            this.f759n = Gravity.getAbsoluteGravity(i2, view.getLayoutDirection());
        }
    }

    @Override // androidx.appcompat.view.menu.w
    public final void n(int i2) {
        this.r = true;
        this.f764t = i2;
    }

    @Override // androidx.appcompat.view.menu.w
    public final void o(PopupWindow.OnDismissListener onDismissListener) {
        this.f770z = onDismissListener;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        i iVar;
        ArrayList arrayList = this.f754i;
        int size = arrayList.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                iVar = null;
                break;
            }
            iVar = (i) arrayList.get(i2);
            if (!iVar.f742a.f1070y.isShowing()) {
                break;
            } else {
                i2++;
            }
        }
        if (iVar != null) {
            iVar.f743b.c(false);
        }
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i2, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i2 != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    @Override // androidx.appcompat.view.menu.w
    public final void p(boolean z2) {
        this.f767w = z2;
    }

    @Override // androidx.appcompat.view.menu.w
    public final void q(int i2) {
        this.f763s = true;
        this.f765u = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0164  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void s(androidx.appcompat.view.menu.o r18) {
        /*
            Method dump skipped, instruction units count: 462
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.j.s(androidx.appcompat.view.menu.o):void");
    }

    @Override // androidx.appcompat.view.menu.E
    public final void show() {
        if (isShowing()) {
            return;
        }
        ArrayList arrayList = this.f753h;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            s((o) it.next());
        }
        arrayList.clear();
        View view = this.f760o;
        this.f761p = view;
        if (view != null) {
            boolean z2 = this.f769y == null;
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            this.f769y = viewTreeObserver;
            if (z2) {
                viewTreeObserver.addOnGlobalLayoutListener(this.f755j);
            }
            this.f761p.addOnAttachStateChangeListener(this.f756k);
        }
    }
}
