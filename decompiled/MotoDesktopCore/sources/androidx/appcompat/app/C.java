package androidx.appcompat.app;

import X.w0;
import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$bool;
import androidx.appcompat.R$id;
import androidx.appcompat.R$styleable;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.F;
import androidx.appcompat.widget.InterfaceC0067d;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.p0;
import androidx.appcompat.widget.u0;
import androidx.appcompat.widget.v0;
import d.InterfaceC0124a;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class C extends Y.r implements InterfaceC0067d {

    /* JADX INFO: renamed from: F, reason: collision with root package name */
    public static final AccelerateInterpolator f485F = new AccelerateInterpolator();

    /* JADX INFO: renamed from: G, reason: collision with root package name */
    public static final DecelerateInterpolator f486G = new DecelerateInterpolator();

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public boolean f487A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public boolean f488B;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public final A f489C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public final A f490D;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public final e0.k f491E;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public Context f492h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public Context f493i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public ActionBarOverlayLayout f494j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public ActionBarContainer f495k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public F f496l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public ActionBarContextView f497m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final View f498n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public boolean f499o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public B f500p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public B f501q;
    public InterfaceC0124a r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public boolean f502s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public final ArrayList f503t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public int f504u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public boolean f505v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public boolean f506w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public boolean f507x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public boolean f508y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public d.j f509z;

    public C(Activity activity, boolean z2) {
        new ArrayList();
        this.f503t = new ArrayList();
        this.f504u = 0;
        this.f505v = true;
        this.f508y = true;
        this.f489C = new A(this, 0);
        this.f490D = new A(this, 1);
        this.f491E = new e0.k(this);
        View decorView = activity.getWindow().getDecorView();
        t0(decorView);
        if (z2) {
            return;
        }
        this.f498n = decorView.findViewById(R.id.content);
    }

    public C(Dialog dialog) {
        new ArrayList();
        this.f503t = new ArrayList();
        this.f504u = 0;
        this.f505v = true;
        this.f508y = true;
        this.f489C = new A(this, 0);
        this.f490D = new A(this, 1);
        this.f491E = new e0.k(this);
        t0(dialog.getWindow().getDecorView());
    }

    @Override // Y.r
    public final Context E() {
        if (this.f493i == null) {
            TypedValue typedValue = new TypedValue();
            this.f492h.getTheme().resolveAttribute(R$attr.actionBarWidgetTheme, typedValue, true);
            int i2 = typedValue.resourceId;
            if (i2 != 0) {
                this.f493i = new ContextThemeWrapper(this.f492h, i2);
            } else {
                this.f493i = this.f492h;
            }
        }
        return this.f493i;
    }

    @Override // Y.r
    public final void W() {
        u0(this.f492h.getResources().getBoolean(R$bool.abc_action_bar_embed_tabs));
    }

    @Override // Y.r
    public final boolean Y(int i2, KeyEvent keyEvent) {
        androidx.appcompat.view.menu.o oVar;
        B b2 = this.f500p;
        if (b2 == null || (oVar = b2.f481d) == null) {
            return false;
        }
        oVar.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
        return oVar.performShortcut(i2, keyEvent, 0);
    }

    @Override // Y.r
    public final void f0(boolean z2) {
        if (this.f499o) {
            return;
        }
        int i2 = z2 ? 4 : 0;
        v0 v0Var = (v0) this.f496l;
        int i3 = v0Var.f1321b;
        this.f499o = true;
        v0Var.a((i2 & 4) | (i3 & (-5)));
    }

    @Override // Y.r
    public final boolean j() {
        p0 p0Var;
        F f2 = this.f496l;
        if (f2 == null || (p0Var = ((v0) f2).f1320a.f1149K) == null || p0Var.f1281b == null) {
            return false;
        }
        p0 p0Var2 = ((v0) f2).f1320a.f1149K;
        androidx.appcompat.view.menu.q qVar = p0Var2 == null ? null : p0Var2.f1281b;
        if (qVar == null) {
            return true;
        }
        qVar.collapseActionView();
        return true;
    }

    @Override // Y.r
    public final void j0(boolean z2) {
        d.j jVar;
        this.f487A = z2;
        if (z2 || (jVar = this.f509z) == null) {
            return;
        }
        jVar.a();
    }

    @Override // Y.r
    public final void m0(CharSequence charSequence) {
        v0 v0Var = (v0) this.f496l;
        if (v0Var.f1327h) {
            return;
        }
        v0Var.f1328i = charSequence;
        if ((v0Var.f1321b & 8) != 0) {
            v0Var.f1320a.setTitle(charSequence);
        }
    }

    @Override // Y.r
    public final d.b o0(F.f fVar) {
        B b2 = this.f500p;
        if (b2 != null) {
            b2.a();
        }
        this.f494j.setHideOnContentScrollEnabled(false);
        ActionBarContextView actionBarContextView = this.f497m;
        actionBarContextView.removeAllViews();
        actionBarContextView.f882l = null;
        actionBarContextView.f873c = null;
        B b3 = new B(this, this.f497m.getContext(), fVar);
        androidx.appcompat.view.menu.o oVar = b3.f481d;
        oVar.w();
        try {
            if (!b3.f482e.d(b3, oVar)) {
                return null;
            }
            this.f500p = b3;
            b3.i();
            this.f497m.c(b3);
            s0(true);
            this.f497m.sendAccessibilityEvent(32);
            return b3;
        } finally {
            oVar.v();
        }
    }

    @Override // Y.r
    public final void q(boolean z2) {
        if (z2 == this.f502s) {
            return;
        }
        this.f502s = z2;
        ArrayList arrayList = this.f503t;
        if (arrayList.size() <= 0) {
            return;
        }
        w0.c(arrayList.get(0));
        throw null;
    }

    public final void s0(boolean z2) {
        v.m mVarH;
        v.m mVarH2;
        if (z2) {
            if (!this.f507x) {
                this.f507x = true;
                ActionBarOverlayLayout actionBarOverlayLayout = this.f494j;
                if (actionBarOverlayLayout != null) {
                    actionBarOverlayLayout.setShowingForActionMode(true);
                }
                v0(false);
            }
        } else if (this.f507x) {
            this.f507x = false;
            ActionBarOverlayLayout actionBarOverlayLayout2 = this.f494j;
            if (actionBarOverlayLayout2 != null) {
                actionBarOverlayLayout2.setShowingForActionMode(false);
            }
            v0(false);
        }
        ActionBarContainer actionBarContainer = this.f495k;
        WeakHashMap weakHashMap = v.l.f2836a;
        if (!actionBarContainer.isLaidOut()) {
            if (z2) {
                ((v0) this.f496l).f1320a.setVisibility(4);
                this.f497m.setVisibility(0);
                return;
            } else {
                ((v0) this.f496l).f1320a.setVisibility(0);
                this.f497m.setVisibility(8);
                return;
            }
        }
        if (z2) {
            v0 v0Var = (v0) this.f496l;
            mVarH = v.l.a(v0Var.f1320a);
            mVarH.a(0.0f);
            mVarH.c(100L);
            mVarH.d(new u0(v0Var, 4));
            mVarH2 = this.f497m.h(0, 200L);
        } else {
            v0 v0Var2 = (v0) this.f496l;
            v.m mVarA = v.l.a(v0Var2.f1320a);
            mVarA.a(1.0f);
            mVarA.c(200L);
            mVarA.d(new u0(v0Var2, 0));
            mVarH = this.f497m.h(8, 100L);
            mVarH2 = mVarA;
        }
        d.j jVar = new d.j();
        ArrayList arrayList = jVar.f2404a;
        arrayList.add(mVarH);
        View view = (View) mVarH.f2837a.get();
        long duration = view != null ? view.animate().getDuration() : 0L;
        View view2 = (View) mVarH2.f2837a.get();
        if (view2 != null) {
            view2.animate().setStartDelay(duration);
        }
        arrayList.add(mVarH2);
        jVar.b();
    }

    @Override // Y.r
    public final int t() {
        return ((v0) this.f496l).f1321b;
    }

    public final void t0(View view) {
        F wrapper;
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) view.findViewById(R$id.decor_content_parent);
        this.f494j = actionBarOverlayLayout;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setActionBarVisibilityCallback(this);
        }
        KeyEvent.Callback callbackFindViewById = view.findViewById(R$id.action_bar);
        if (callbackFindViewById instanceof F) {
            wrapper = (F) callbackFindViewById;
        } else {
            if (!(callbackFindViewById instanceof Toolbar)) {
                throw new IllegalStateException("Can't make a decor toolbar out of ".concat(callbackFindViewById != null ? callbackFindViewById.getClass().getSimpleName() : "null"));
            }
            wrapper = ((Toolbar) callbackFindViewById).getWrapper();
        }
        this.f496l = wrapper;
        this.f497m = (ActionBarContextView) view.findViewById(R$id.action_context_bar);
        ActionBarContainer actionBarContainer = (ActionBarContainer) view.findViewById(R$id.action_bar_container);
        this.f495k = actionBarContainer;
        F f2 = this.f496l;
        if (f2 == null || this.f497m == null || actionBarContainer == null) {
            throw new IllegalStateException(C.class.getSimpleName().concat(" can only be used with a compatible window decor layout"));
        }
        Context context = ((v0) f2).f1320a.getContext();
        this.f492h = context;
        if ((((v0) this.f496l).f1321b & 4) != 0) {
            this.f499o = true;
        }
        int i2 = context.getApplicationInfo().targetSdkVersion;
        this.f496l.getClass();
        u0(context.getResources().getBoolean(R$bool.abc_action_bar_embed_tabs));
        TypedArray typedArrayObtainStyledAttributes = this.f492h.obtainStyledAttributes(null, R$styleable.ActionBar, R$attr.actionBarStyle, 0);
        if (typedArrayObtainStyledAttributes.getBoolean(R$styleable.ActionBar_hideOnContentScroll, false)) {
            ActionBarOverlayLayout actionBarOverlayLayout2 = this.f494j;
            if (!actionBarOverlayLayout2.f898h) {
                throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
            }
            this.f488B = true;
            actionBarOverlayLayout2.setHideOnContentScrollEnabled(true);
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.ActionBar_elevation, 0);
        if (dimensionPixelSize != 0) {
            ActionBarContainer actionBarContainer2 = this.f495k;
            WeakHashMap weakHashMap = v.l.f2836a;
            actionBarContainer2.setElevation(dimensionPixelSize);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public final void u0(boolean z2) {
        if (z2) {
            this.f495k.setTabContainer(null);
            v0 v0Var = (v0) this.f496l;
            ScrollingTabContainerView scrollingTabContainerView = v0Var.f1322c;
            if (scrollingTabContainerView != null) {
                ViewParent parent = scrollingTabContainerView.getParent();
                Toolbar toolbar = v0Var.f1320a;
                if (parent == toolbar) {
                    toolbar.removeView(v0Var.f1322c);
                }
            }
            v0Var.f1322c = null;
        } else {
            v0 v0Var2 = (v0) this.f496l;
            ScrollingTabContainerView scrollingTabContainerView2 = v0Var2.f1322c;
            if (scrollingTabContainerView2 != null) {
                ViewParent parent2 = scrollingTabContainerView2.getParent();
                Toolbar toolbar2 = v0Var2.f1320a;
                if (parent2 == toolbar2) {
                    toolbar2.removeView(v0Var2.f1322c);
                }
            }
            v0Var2.f1322c = null;
            this.f495k.setTabContainer(null);
        }
        this.f496l.getClass();
        ((v0) this.f496l).f1320a.setCollapsible(false);
        this.f494j.setHasNonEmbeddedTabs(false);
    }

    public final void v0(boolean z2) {
        boolean z3 = this.f507x || !this.f506w;
        View view = this.f498n;
        e0.k kVar = this.f491E;
        if (!z3) {
            if (this.f508y) {
                this.f508y = false;
                d.j jVar = this.f509z;
                if (jVar != null) {
                    jVar.a();
                }
                int i2 = this.f504u;
                A a2 = this.f489C;
                if (i2 != 0 || (!this.f487A && !z2)) {
                    a2.c();
                    return;
                }
                this.f495k.setAlpha(1.0f);
                this.f495k.setTransitioning(true);
                d.j jVar2 = new d.j();
                float f2 = -this.f495k.getHeight();
                if (z2) {
                    this.f495k.getLocationInWindow(new int[]{0, 0});
                    f2 -= r12[1];
                }
                v.m mVarA = v.l.a(this.f495k);
                mVarA.e(f2);
                View view2 = (View) mVarA.f2837a.get();
                if (view2 != null) {
                    view2.animate().setUpdateListener(kVar != null ? new I.b(kVar, view2) : null);
                }
                boolean z4 = jVar2.f2408e;
                ArrayList arrayList = jVar2.f2404a;
                if (!z4) {
                    arrayList.add(mVarA);
                }
                if (this.f505v && view != null) {
                    v.m mVarA2 = v.l.a(view);
                    mVarA2.e(f2);
                    if (!jVar2.f2408e) {
                        arrayList.add(mVarA2);
                    }
                }
                AccelerateInterpolator accelerateInterpolator = f485F;
                boolean z5 = jVar2.f2408e;
                if (!z5) {
                    jVar2.f2406c = accelerateInterpolator;
                }
                if (!z5) {
                    jVar2.f2405b = 250L;
                }
                if (!z5) {
                    jVar2.f2407d = a2;
                }
                this.f509z = jVar2;
                jVar2.b();
                return;
            }
            return;
        }
        if (this.f508y) {
            return;
        }
        this.f508y = true;
        d.j jVar3 = this.f509z;
        if (jVar3 != null) {
            jVar3.a();
        }
        this.f495k.setVisibility(0);
        int i3 = this.f504u;
        A a3 = this.f490D;
        if (i3 == 0 && (this.f487A || z2)) {
            this.f495k.setTranslationY(0.0f);
            float f3 = -this.f495k.getHeight();
            if (z2) {
                this.f495k.getLocationInWindow(new int[]{0, 0});
                f3 -= r12[1];
            }
            this.f495k.setTranslationY(f3);
            d.j jVar4 = new d.j();
            v.m mVarA3 = v.l.a(this.f495k);
            mVarA3.e(0.0f);
            View view3 = (View) mVarA3.f2837a.get();
            if (view3 != null) {
                view3.animate().setUpdateListener(kVar != null ? new I.b(kVar, view3) : null);
            }
            boolean z6 = jVar4.f2408e;
            ArrayList arrayList2 = jVar4.f2404a;
            if (!z6) {
                arrayList2.add(mVarA3);
            }
            if (this.f505v && view != null) {
                view.setTranslationY(f3);
                v.m mVarA4 = v.l.a(view);
                mVarA4.e(0.0f);
                if (!jVar4.f2408e) {
                    arrayList2.add(mVarA4);
                }
            }
            DecelerateInterpolator decelerateInterpolator = f486G;
            boolean z7 = jVar4.f2408e;
            if (!z7) {
                jVar4.f2406c = decelerateInterpolator;
            }
            if (!z7) {
                jVar4.f2405b = 250L;
            }
            if (!z7) {
                jVar4.f2407d = a3;
            }
            this.f509z = jVar4;
            jVar4.b();
        } else {
            this.f495k.setAlpha(1.0f);
            this.f495k.setTranslationY(0.0f);
            if (this.f505v && view != null) {
                view.setTranslationY(0.0f);
            }
            a3.c();
        }
        ActionBarOverlayLayout actionBarOverlayLayout = this.f494j;
        if (actionBarOverlayLayout != null) {
            WeakHashMap weakHashMap = v.l.f2836a;
            actionBarOverlayLayout.requestApplyInsets();
        }
    }
}
