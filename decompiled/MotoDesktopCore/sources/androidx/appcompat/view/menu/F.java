package androidx.appcompat.view.menu;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R$dimen;
import androidx.appcompat.R$layout;
import androidx.appcompat.widget.I;
import androidx.appcompat.widget.T;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class F extends w implements PopupWindow.OnDismissListener, View.OnKeyListener {

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public static final int f674v = R$layout.abc_popup_menu_item_layout;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Context f675b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final o f676c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final MenuAdapter f677d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final boolean f678e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final int f679f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final int f680g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final int f681h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final T f682i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final ViewTreeObserverOnGlobalLayoutListenerC0062f f683j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final ViewOnAttachStateChangeListenerC0063g f684k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public PopupWindow.OnDismissListener f685l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public View f686m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public View f687n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public z f688o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public ViewTreeObserver f689p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public boolean f690q;
    public boolean r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public int f691s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public int f692t = 0;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public boolean f693u;

    public F(int i2, int i3, Context context, View view, o oVar, boolean z2) {
        int i4 = 1;
        this.f683j = new ViewTreeObserverOnGlobalLayoutListenerC0062f(this, i4);
        this.f684k = new ViewOnAttachStateChangeListenerC0063g(this, i4);
        this.f675b = context;
        this.f676c = oVar;
        this.f678e = z2;
        this.f677d = new MenuAdapter(oVar, LayoutInflater.from(context), z2, f674v);
        this.f680g = i2;
        this.f681h = i3;
        Resources resources = context.getResources();
        this.f679f = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R$dimen.abc_config_prefDialogWidth));
        this.f686m = view;
        this.f682i = new T(context, null, i2, i3);
        oVar.b(this, context);
    }

    @Override // androidx.appcompat.view.menu.A
    public final void a(o oVar, boolean z2) {
        if (oVar != this.f676c) {
            return;
        }
        dismiss();
        z zVar = this.f688o;
        if (zVar != null) {
            zVar.a(oVar, z2);
        }
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean b() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.A
    public final void c(z zVar) {
        this.f688o = zVar;
    }

    @Override // androidx.appcompat.view.menu.E
    public final void dismiss() {
        if (isShowing()) {
            this.f682i.dismiss();
        }
    }

    @Override // androidx.appcompat.view.menu.A
    public final void f() {
        this.r = false;
        MenuAdapter menuAdapter = this.f677d;
        if (menuAdapter != null) {
            menuAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean g(G g2) {
        if (g2.hasVisibleItems()) {
            y yVar = new y(this.f680g, this.f681h, this.f675b, this.f687n, g2, this.f678e);
            z zVar = this.f688o;
            yVar.f853i = zVar;
            w wVar = yVar.f854j;
            if (wVar != null) {
                wVar.c(zVar);
            }
            boolean zR = w.r(g2);
            yVar.f852h = zR;
            w wVar2 = yVar.f854j;
            if (wVar2 != null) {
                wVar2.l(zR);
            }
            yVar.f855k = this.f685l;
            this.f685l = null;
            this.f676c.c(false);
            T t2 = this.f682i;
            int width = t2.f1052f;
            int i2 = !t2.f1055i ? 0 : t2.f1053g;
            int i3 = this.f692t;
            View view = this.f686m;
            WeakHashMap weakHashMap = v.l.f2836a;
            if ((Gravity.getAbsoluteGravity(i3, view.getLayoutDirection()) & 7) == 5) {
                width += this.f686m.getWidth();
            }
            if (!yVar.b()) {
                if (yVar.f850f != null) {
                    yVar.d(width, i2, true, true);
                }
            }
            z zVar2 = this.f688o;
            if (zVar2 != null) {
                zVar2.e(g2);
            }
            return true;
        }
        return false;
    }

    @Override // androidx.appcompat.view.menu.E
    public final I getListView() {
        return this.f682i.f1049c;
    }

    @Override // androidx.appcompat.view.menu.w
    public final void i(o oVar) {
    }

    @Override // androidx.appcompat.view.menu.E
    public final boolean isShowing() {
        return !this.f690q && this.f682i.f1070y.isShowing();
    }

    @Override // androidx.appcompat.view.menu.w
    public final void k(View view) {
        this.f686m = view;
    }

    @Override // androidx.appcompat.view.menu.w
    public final void l(boolean z2) {
        this.f677d.setForceShowIcon(z2);
    }

    @Override // androidx.appcompat.view.menu.w
    public final void m(int i2) {
        this.f692t = i2;
    }

    @Override // androidx.appcompat.view.menu.w
    public final void n(int i2) {
        this.f682i.f1052f = i2;
    }

    @Override // androidx.appcompat.view.menu.w
    public final void o(PopupWindow.OnDismissListener onDismissListener) {
        this.f685l = onDismissListener;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        this.f690q = true;
        this.f676c.c(true);
        ViewTreeObserver viewTreeObserver = this.f689p;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.f689p = this.f687n.getViewTreeObserver();
            }
            this.f689p.removeGlobalOnLayoutListener(this.f683j);
            this.f689p = null;
        }
        this.f687n.removeOnAttachStateChangeListener(this.f684k);
        PopupWindow.OnDismissListener onDismissListener = this.f685l;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
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
        this.f693u = z2;
    }

    @Override // androidx.appcompat.view.menu.w
    public final void q(int i2) {
        T t2 = this.f682i;
        t2.f1053g = i2;
        t2.f1055i = true;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // androidx.appcompat.view.menu.E
    public final void show() {
        View view;
        if (isShowing()) {
            return;
        }
        if (this.f690q || (view = this.f686m) == null) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
        this.f687n = view;
        T t2 = this.f682i;
        t2.f1070y.setOnDismissListener(this);
        t2.f1062p = this;
        t2.f1069x = true;
        t2.f1070y.setFocusable(true);
        View view2 = this.f687n;
        boolean z2 = this.f689p == null;
        ViewTreeObserver viewTreeObserver = view2.getViewTreeObserver();
        this.f689p = viewTreeObserver;
        if (z2) {
            viewTreeObserver.addOnGlobalLayoutListener(this.f683j);
        }
        view2.addOnAttachStateChangeListener(this.f684k);
        t2.f1061o = view2;
        t2.f1058l = this.f692t;
        boolean z3 = this.r;
        Context context = this.f675b;
        MenuAdapter menuAdapter = this.f677d;
        if (!z3) {
            this.f691s = w.j(menuAdapter, context, this.f679f);
            this.r = true;
        }
        t2.e(this.f691s);
        t2.f1070y.setInputMethodMode(2);
        t2.f1068w = this.f843a;
        t2.show();
        I i2 = t2.f1049c;
        i2.setOnKeyListener(this);
        if (this.f693u) {
            o oVar = this.f676c;
            if (oVar.f793m != null) {
                FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(context).inflate(R$layout.abc_popup_menu_header_item_layout, (ViewGroup) i2, false);
                TextView textView = (TextView) frameLayout.findViewById(R.id.title);
                if (textView != null) {
                    textView.setText(oVar.f793m);
                }
                frameLayout.setEnabled(false);
                i2.addHeaderView(frameLayout, null, false);
            }
        }
        t2.c(menuAdapter);
        t2.show();
    }
}
