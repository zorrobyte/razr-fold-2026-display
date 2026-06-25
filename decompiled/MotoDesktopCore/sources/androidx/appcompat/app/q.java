package androidx.appcompat.app;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import androidx.appcompat.R$layout;
import androidx.appcompat.R$style;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.ExpandedMenuView;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.C0069f;
import androidx.appcompat.widget.C0074k;
import androidx.appcompat.widget.C0082t;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.E;
import androidx.appcompat.widget.v0;
import androidx.appcompat.widget.w0;
import androidx.appcompat.widget.y0;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class q implements androidx.appcompat.view.menu.m, LayoutInflater.Factory2 {

    /* JADX INFO: renamed from: Q, reason: collision with root package name */
    public static final int[] f595Q = {R.attr.windowBackground};

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public boolean f596A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public boolean f597B;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public p[] f598C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public p f599D;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public boolean f600E;

    /* JADX INFO: renamed from: F, reason: collision with root package name */
    public boolean f601F;

    /* JADX INFO: renamed from: H, reason: collision with root package name */
    public boolean f603H;

    /* JADX INFO: renamed from: I, reason: collision with root package name */
    public m f604I;

    /* JADX INFO: renamed from: J, reason: collision with root package name */
    public boolean f605J;

    /* JADX INFO: renamed from: K, reason: collision with root package name */
    public int f606K;

    /* JADX INFO: renamed from: M, reason: collision with root package name */
    public boolean f608M;

    /* JADX INFO: renamed from: N, reason: collision with root package name */
    public Rect f609N;

    /* JADX INFO: renamed from: O, reason: collision with root package name */
    public Rect f610O;

    /* JADX INFO: renamed from: P, reason: collision with root package name */
    public AppCompatViewInflater f611P;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f612a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Window f613b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Window.Callback f614c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final l f615d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final h f616e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public Y.r f617f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public d.i f618g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public CharSequence f619h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public E f620i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public j f621j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public j f622k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public d.b f623l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public ActionBarContextView f624m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public PopupWindow f625n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public i f626o;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public boolean f628q;
    public ViewGroup r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public TextView f629s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public View f630t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public boolean f631u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public boolean f632v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public boolean f633w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public boolean f634x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public boolean f635y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public boolean f636z;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public v.m f627p = null;

    /* JADX INFO: renamed from: G, reason: collision with root package name */
    public int f602G = -100;

    /* JADX INFO: renamed from: L, reason: collision with root package name */
    public final i f607L = new i(this, 0);

    public q(Context context, Window window, h hVar) {
        int resourceId;
        Drawable drawableI = null;
        this.f612a = context;
        this.f613b = window;
        this.f616e = hVar;
        Window.Callback callback = window.getCallback();
        this.f614c = callback;
        if (callback instanceof l) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        l lVar = new l(this, callback);
        this.f615d = lVar;
        window.setCallback(lVar);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, f595Q);
        if (typedArrayObtainStyledAttributes.hasValue(0) && (resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0)) != 0) {
            drawableI = C0082t.f().i(context, resourceId, true);
        }
        if (drawableI != null) {
            window.setBackgroundDrawable(drawableI);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean a() {
        /*
            Method dump skipped, instruction units count: 218
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.q.a():boolean");
    }

    public final void b(int i2, p pVar, androidx.appcompat.view.menu.o oVar) {
        if (oVar == null) {
            if (pVar == null && i2 >= 0) {
                p[] pVarArr = this.f598C;
                if (i2 < pVarArr.length) {
                    pVar = pVarArr[i2];
                }
            }
            if (pVar != null) {
                oVar = pVar.f586h;
            }
        }
        if ((pVar == null || pVar.f591m) && !this.f601F) {
            this.f614c.onPanelClosed(i2, oVar);
        }
    }

    @Override // androidx.appcompat.view.menu.m
    public final boolean c(androidx.appcompat.view.menu.o oVar, MenuItem menuItem) {
        p pVar;
        Window.Callback callback = this.f613b.getCallback();
        if (callback != null && !this.f601F) {
            androidx.appcompat.view.menu.o oVarK = oVar.k();
            p[] pVarArr = this.f598C;
            int length = pVarArr != null ? pVarArr.length : 0;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    pVar = pVarArr[i2];
                    if (pVar != null && pVar.f586h == oVarK) {
                        break;
                    }
                    i2++;
                } else {
                    pVar = null;
                    break;
                }
            }
            if (pVar != null) {
                return callback.onMenuItemSelected(pVar.f579a, menuItem);
            }
        }
        return false;
    }

    public final void d(androidx.appcompat.view.menu.o oVar) {
        C0074k c0074k;
        if (this.f597B) {
            return;
        }
        this.f597B = true;
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.f620i;
        actionBarOverlayLayout.e();
        ActionMenuView actionMenuView = ((v0) actionBarOverlayLayout.f895e).f1320a.f1154a;
        if (actionMenuView != null && (c0074k = actionMenuView.f921t) != null) {
            c0074k.j();
            C0069f c0069f = c0074k.f1244u;
            if (c0069f != null && c0069f.b()) {
                c0069f.f854j.dismiss();
            }
        }
        Window.Callback callback = this.f613b.getCallback();
        if (callback != null && !this.f601F) {
            callback.onPanelClosed(108, oVar);
        }
        this.f597B = false;
    }

    public final void e(p pVar, boolean z2) {
        n nVar;
        E e2;
        if (z2 && pVar.f579a == 0 && (e2 = this.f620i) != null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) e2;
            actionBarOverlayLayout.e();
            if (((v0) actionBarOverlayLayout.f895e).f1320a.n()) {
                d(pVar.f586h);
                return;
            }
        }
        WindowManager windowManager = (WindowManager) this.f612a.getSystemService("window");
        if (windowManager != null && pVar.f591m && (nVar = pVar.f583e) != null) {
            windowManager.removeView(nVar);
            if (z2) {
                b(pVar.f579a, pVar, null);
            }
        }
        pVar.f589k = false;
        pVar.f590l = false;
        pVar.f591m = false;
        pVar.f584f = null;
        pVar.f592n = true;
        if (this.f599D == pVar) {
            this.f599D = null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0048, code lost:
    
        if (r6.k() != false) goto L20;
     */
    @Override // androidx.appcompat.view.menu.m
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void f(androidx.appcompat.view.menu.o r6) {
        /*
            Method dump skipped, instruction units count: 223
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.q.f(androidx.appcompat.view.menu.o):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00da  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean g(android.view.KeyEvent r7) {
        /*
            Method dump skipped, instruction units count: 325
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.q.g(android.view.KeyEvent):boolean");
    }

    public final void h(int i2) {
        p pVarK = k(i2);
        if (pVarK.f586h != null) {
            Bundle bundle = new Bundle();
            pVarK.f586h.t(bundle);
            if (bundle.size() > 0) {
                pVarK.f594p = bundle;
            }
            pVarK.f586h.w();
            pVarK.f586h.clear();
        }
        pVarK.f593o = true;
        pVarK.f592n = true;
        if ((i2 == 108 || i2 == 0) && this.f620i != null) {
            p pVarK2 = k(0);
            pVarK2.f589k = false;
            s(pVarK2, null);
        }
    }

    public final void i() {
        if (this.f604I == null) {
            if (f0.b.f2535f == null) {
                Context applicationContext = this.f612a.getApplicationContext();
                f0.b.f2535f = new f0.b(applicationContext, (LocationManager) applicationContext.getSystemService("location"));
            }
            this.f604I = new m(this, f0.b.f2535f);
        }
    }

    public final void j() {
        ViewGroup viewGroup;
        int i2 = 1;
        int i3 = 0;
        if (this.f628q) {
            return;
        }
        int[] iArr = R$styleable.AppCompatTheme;
        Context context = this.f612a;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(iArr);
        if (!typedArrayObtainStyledAttributes.hasValue(R$styleable.AppCompatTheme_windowActionBar)) {
            typedArrayObtainStyledAttributes.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (typedArrayObtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_windowNoTitle, false)) {
            t(1);
        } else if (typedArrayObtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_windowActionBar, false)) {
            t(108);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_windowActionBarOverlay, false)) {
            t(109);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_windowActionModeOverlay, false)) {
            t(10);
        }
        this.f636z = typedArrayObtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_android_windowIsFloating, false);
        typedArrayObtainStyledAttributes.recycle();
        Window window = this.f613b;
        window.getDecorView();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        if (this.f596A) {
            viewGroup = this.f635y ? (ViewGroup) layoutInflaterFrom.inflate(R$layout.abc_screen_simple_overlay_action_mode, (ViewGroup) null) : (ViewGroup) layoutInflaterFrom.inflate(R$layout.abc_screen_simple, (ViewGroup) null);
            v.l.c(viewGroup, new j(this, i3));
        } else if (this.f636z) {
            viewGroup = (ViewGroup) layoutInflaterFrom.inflate(R$layout.abc_dialog_title_material, (ViewGroup) null);
            this.f634x = false;
            this.f633w = false;
        } else if (this.f633w) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R$attr.actionBarTheme, typedValue, true);
            viewGroup = (ViewGroup) LayoutInflater.from(typedValue.resourceId != 0 ? new d.d(context, typedValue.resourceId) : context).inflate(R$layout.abc_screen_toolbar, (ViewGroup) null);
            E e2 = (E) viewGroup.findViewById(R$id.decor_content_parent);
            this.f620i = e2;
            e2.setWindowCallback(window.getCallback());
            if (this.f634x) {
                ((ActionBarOverlayLayout) this.f620i).d(109);
            }
            if (this.f631u) {
                ((ActionBarOverlayLayout) this.f620i).d(2);
            }
            if (this.f632v) {
                ((ActionBarOverlayLayout) this.f620i).d(5);
            }
        } else {
            viewGroup = null;
        }
        if (viewGroup == null) {
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.f633w + ", windowActionBarOverlay: " + this.f634x + ", android:windowIsFloating: " + this.f636z + ", windowActionModeOverlay: " + this.f635y + ", windowNoTitle: " + this.f596A + " }");
        }
        if (this.f620i == null) {
            this.f629s = (TextView) viewGroup.findViewById(R$id.title);
        }
        Method method = y0.f1347a;
        try {
            Method method2 = viewGroup.getClass().getMethod("makeOptionalFitsSystemWindows", null);
            if (!method2.isAccessible()) {
                method2.setAccessible(true);
            }
            method2.invoke(viewGroup, null);
        } catch (IllegalAccessException e3) {
            Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", e3);
        } catch (NoSuchMethodException unused) {
            Log.d("ViewUtils", "Could not find method makeOptionalFitsSystemWindows. Oh well...");
        } catch (InvocationTargetException e4) {
            Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", e4);
        }
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(R$id.action_bar_activity_content);
        ViewGroup viewGroup2 = (ViewGroup) window.findViewById(R.id.content);
        if (viewGroup2 != null) {
            while (viewGroup2.getChildCount() > 0) {
                View childAt = viewGroup2.getChildAt(0);
                viewGroup2.removeViewAt(0);
                contentFrameLayout.addView(childAt);
            }
            viewGroup2.setId(-1);
            contentFrameLayout.setId(R.id.content);
            if (viewGroup2 instanceof FrameLayout) {
                ((FrameLayout) viewGroup2).setForeground(null);
            }
        }
        window.setContentView(viewGroup);
        contentFrameLayout.setAttachListener(new j(this, i2));
        this.r = viewGroup;
        Window.Callback callback = this.f614c;
        CharSequence title = callback instanceof Activity ? ((Activity) callback).getTitle() : this.f619h;
        if (!TextUtils.isEmpty(title)) {
            E e5 = this.f620i;
            if (e5 != null) {
                e5.setWindowTitle(title);
            } else {
                Y.r rVar = this.f617f;
                if (rVar != null) {
                    rVar.m0(title);
                } else {
                    TextView textView = this.f629s;
                    if (textView != null) {
                        textView.setText(title);
                    }
                }
            }
        }
        ContentFrameLayout contentFrameLayout2 = (ContentFrameLayout) this.r.findViewById(R.id.content);
        View decorView = window.getDecorView();
        contentFrameLayout2.f990g.set(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        WeakHashMap weakHashMap = v.l.f2836a;
        if (contentFrameLayout2.isLaidOut()) {
            contentFrameLayout2.requestLayout();
        }
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(R$styleable.AppCompatTheme);
        typedArrayObtainStyledAttributes2.getValue(R$styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout2.getMinWidthMajor());
        typedArrayObtainStyledAttributes2.getValue(R$styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout2.getMinWidthMinor());
        if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.AppCompatTheme_windowFixedWidthMajor)) {
            typedArrayObtainStyledAttributes2.getValue(R$styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout2.getFixedWidthMajor());
        }
        if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.AppCompatTheme_windowFixedWidthMinor)) {
            typedArrayObtainStyledAttributes2.getValue(R$styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout2.getFixedWidthMinor());
        }
        if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.AppCompatTheme_windowFixedHeightMajor)) {
            typedArrayObtainStyledAttributes2.getValue(R$styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout2.getFixedHeightMajor());
        }
        if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.AppCompatTheme_windowFixedHeightMinor)) {
            typedArrayObtainStyledAttributes2.getValue(R$styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout2.getFixedHeightMinor());
        }
        typedArrayObtainStyledAttributes2.recycle();
        contentFrameLayout2.requestLayout();
        this.f628q = true;
        p pVarK = k(0);
        if (this.f601F || pVarK.f586h != null) {
            return;
        }
        o(108);
    }

    public final p k(int i2) {
        p[] pVarArr = this.f598C;
        if (pVarArr == null || pVarArr.length <= i2) {
            p[] pVarArr2 = new p[i2 + 1];
            if (pVarArr != null) {
                System.arraycopy(pVarArr, 0, pVarArr2, 0, pVarArr.length);
            }
            this.f598C = pVarArr2;
            pVarArr = pVarArr2;
        }
        p pVar = pVarArr[i2];
        if (pVar != null) {
            return pVar;
        }
        p pVar2 = new p();
        pVar2.f579a = i2;
        pVar2.f592n = false;
        pVarArr[i2] = pVar2;
        return pVar2;
    }

    public final void l() {
        j();
        if (this.f633w && this.f617f == null) {
            Window.Callback callback = this.f614c;
            if (callback instanceof Activity) {
                this.f617f = new C((Activity) callback, this.f634x);
            } else if (callback instanceof Dialog) {
                this.f617f = new C((Dialog) callback);
            }
            Y.r rVar = this.f617f;
            if (rVar != null) {
                rVar.f0(this.f608M);
            }
        }
    }

    public final void m() {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.f612a);
        if (layoutInflaterFrom.getFactory() == null) {
            layoutInflaterFrom.setFactory2(this);
        } else {
            if (layoutInflaterFrom.getFactory2() instanceof q) {
                return;
            }
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    public final void n() {
        l();
        Y.r rVar = this.f617f;
        if (rVar == null || !rVar.M()) {
            o(0);
        }
    }

    public final void o(int i2) {
        this.f606K = (1 << i2) | this.f606K;
        if (this.f605J) {
            return;
        }
        View decorView = this.f613b.getDecorView();
        WeakHashMap weakHashMap = v.l.f2836a;
        decorView.postOnAnimation(this.f607L);
        this.f605J = true;
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        Context dVar;
        View appCompatRatingBar;
        View view2 = null;
        if (this.f611P == null) {
            String string = this.f612a.obtainStyledAttributes(R$styleable.AppCompatTheme).getString(R$styleable.AppCompatTheme_viewInflaterClass);
            if (string == null || AppCompatViewInflater.class.getName().equals(string)) {
                this.f611P = new AppCompatViewInflater();
            } else {
                try {
                    this.f611P = (AppCompatViewInflater) Class.forName(string).getDeclaredConstructor(null).newInstance(null);
                } catch (Throwable th) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + string + ". Falling back to default.", th);
                    this.f611P = new AppCompatViewInflater();
                }
            }
        }
        AppCompatViewInflater appCompatViewInflater = this.f611P;
        int i2 = w0.f1342a;
        appCompatViewInflater.getClass();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.View, 0, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.View_theme, 0);
        if (resourceId != 0) {
            Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
        }
        typedArrayObtainStyledAttributes.recycle();
        dVar = (resourceId == 0 || ((context instanceof d.d) && ((d.d) context).f2353a == resourceId)) ? context : new d.d(context, resourceId);
        str.getClass();
        switch (str) {
            case "RatingBar":
                appCompatRatingBar = new AppCompatRatingBar(dVar, attributeSet);
                break;
            case "CheckedTextView":
                appCompatRatingBar = new AppCompatCheckedTextView(dVar, attributeSet);
                break;
            case "MultiAutoCompleteTextView":
                appCompatRatingBar = new AppCompatMultiAutoCompleteTextView(dVar, attributeSet);
                break;
            case "TextView":
                appCompatRatingBar = new AppCompatTextView(dVar, attributeSet);
                break;
            case "ImageButton":
                appCompatRatingBar = new AppCompatImageButton(dVar, attributeSet, R$attr.imageButtonStyle);
                break;
            case "SeekBar":
                appCompatRatingBar = new AppCompatSeekBar(dVar, attributeSet);
                break;
            case "Spinner":
                appCompatRatingBar = new AppCompatSpinner(dVar, attributeSet, R$attr.spinnerStyle);
                break;
            case "RadioButton":
                appCompatRatingBar = new AppCompatRadioButton(dVar, attributeSet);
                break;
            case "ImageView":
                appCompatRatingBar = new AppCompatImageView(dVar, attributeSet, 0);
                break;
            case "AutoCompleteTextView":
                appCompatRatingBar = new AppCompatAutoCompleteTextView(dVar, attributeSet, R$attr.autoCompleteTextViewStyle);
                break;
            case "CheckBox":
                appCompatRatingBar = new AppCompatCheckBox(dVar, attributeSet);
                break;
            case "EditText":
                appCompatRatingBar = new AppCompatEditText(dVar, attributeSet);
                break;
            case "Button":
                appCompatRatingBar = new AppCompatButton(dVar, attributeSet);
                break;
            default:
                appCompatRatingBar = null;
                break;
        }
        if (appCompatRatingBar == null && context != dVar) {
            Object[] objArr = appCompatViewInflater.f479a;
            if (str.equals("view")) {
                str = attributeSet.getAttributeValue(null, "class");
            }
            try {
                objArr[0] = dVar;
                objArr[1] = attributeSet;
                if (-1 == str.indexOf(46)) {
                    int i3 = 0;
                    while (true) {
                        String[] strArr = AppCompatViewInflater.f477d;
                        if (i3 < 3) {
                            View viewA = appCompatViewInflater.a(dVar, str, strArr[i3]);
                            if (viewA != null) {
                                objArr[0] = null;
                                objArr[1] = null;
                                view2 = viewA;
                            } else {
                                i3++;
                            }
                        }
                    }
                } else {
                    View viewA2 = appCompatViewInflater.a(dVar, str, null);
                    objArr[0] = null;
                    objArr[1] = null;
                    view2 = viewA2;
                }
            } catch (Exception unused) {
            } finally {
                objArr[0] = null;
                objArr[1] = null;
            }
            appCompatRatingBar = view2;
        }
        if (appCompatRatingBar != null) {
            Context context2 = appCompatRatingBar.getContext();
            if (context2 instanceof ContextWrapper) {
                WeakHashMap weakHashMap = v.l.f2836a;
                if (appCompatRatingBar.hasOnClickListeners()) {
                    TypedArray typedArrayObtainStyledAttributes2 = context2.obtainStyledAttributes(attributeSet, AppCompatViewInflater.f476c);
                    String string2 = typedArrayObtainStyledAttributes2.getString(0);
                    if (string2 != null) {
                        appCompatRatingBar.setOnClickListener(new t(appCompatRatingBar, string2));
                    }
                    typedArrayObtainStyledAttributes2.recycle();
                }
            }
        }
        return appCompatRatingBar;
    }

    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    public final void p(Bundle bundle) {
        String strO;
        Window.Callback callback = this.f614c;
        if (callback instanceof Activity) {
            try {
                Activity activity = (Activity) callback;
                try {
                    strO = AbstractC0054a.o(activity, activity.getComponentName());
                } catch (PackageManager.NameNotFoundException e2) {
                    throw new IllegalArgumentException(e2);
                }
            } catch (IllegalArgumentException unused) {
                strO = null;
            }
            if (strO != null) {
                Y.r rVar = this.f617f;
                if (rVar == null) {
                    this.f608M = true;
                } else {
                    rVar.f0(true);
                }
            }
        }
        if (bundle == null || this.f602G != -100) {
            return;
        }
        this.f602G = bundle.getInt("appcompat:local_night_mode", -100);
    }

    public final void q(p pVar, KeyEvent keyEvent) {
        int i2;
        ViewGroup.LayoutParams layoutParams;
        if (pVar.f591m || this.f601F) {
            return;
        }
        int i3 = pVar.f579a;
        Context context = this.f612a;
        if (i3 == 0 && (context.getResources().getConfiguration().screenLayout & 15) == 4) {
            return;
        }
        Window.Callback callback = this.f613b.getCallback();
        if (callback != null && !callback.onMenuOpened(i3, pVar.f586h)) {
            e(pVar, true);
            return;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager != null && s(pVar, keyEvent)) {
            n nVar = pVar.f583e;
            if (nVar != null && !pVar.f592n) {
                View view = pVar.f585g;
                if (view != null && (layoutParams = view.getLayoutParams()) != null && layoutParams.width == -1) {
                    i2 = -1;
                }
                pVar.f590l = false;
                WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(i2, -2, 0, 0, 1002, 8519680, -3);
                layoutParams2.gravity = pVar.f581c;
                layoutParams2.windowAnimations = pVar.f582d;
                windowManager.addView(pVar.f583e, layoutParams2);
                pVar.f591m = true;
            }
            if (nVar == null) {
                l();
                Y.r rVar = this.f617f;
                Context contextE = rVar != null ? rVar.E() : null;
                if (contextE != null) {
                    context = contextE;
                }
                TypedValue typedValue = new TypedValue();
                Resources.Theme themeNewTheme = context.getResources().newTheme();
                themeNewTheme.setTo(context.getTheme());
                themeNewTheme.resolveAttribute(R$attr.actionBarPopupTheme, typedValue, true);
                int i4 = typedValue.resourceId;
                if (i4 != 0) {
                    themeNewTheme.applyStyle(i4, true);
                }
                themeNewTheme.resolveAttribute(R$attr.panelMenuListTheme, typedValue, true);
                int i5 = typedValue.resourceId;
                if (i5 != 0) {
                    themeNewTheme.applyStyle(i5, true);
                } else {
                    themeNewTheme.applyStyle(R$style.Theme_AppCompat_CompactMenu, true);
                }
                d.d dVar = new d.d(context, 0);
                dVar.getTheme().setTo(themeNewTheme);
                pVar.f588j = dVar;
                TypedArray typedArrayObtainStyledAttributes = dVar.obtainStyledAttributes(R$styleable.AppCompatTheme);
                pVar.f580b = typedArrayObtainStyledAttributes.getResourceId(R$styleable.AppCompatTheme_panelBackground, 0);
                pVar.f582d = typedArrayObtainStyledAttributes.getResourceId(R$styleable.AppCompatTheme_android_windowAnimationStyle, 0);
                typedArrayObtainStyledAttributes.recycle();
                pVar.f583e = new n(this, pVar.f588j);
                pVar.f581c = 81;
            } else if (pVar.f592n && nVar.getChildCount() > 0) {
                pVar.f583e.removeAllViews();
            }
            View view2 = pVar.f585g;
            if (view2 != null) {
                pVar.f584f = view2;
            } else {
                if (pVar.f586h == null) {
                    return;
                }
                if (this.f622k == null) {
                    this.f622k = new j(this, 3);
                }
                j jVar = this.f622k;
                if (pVar.f587i == null) {
                    androidx.appcompat.view.menu.l lVar = new androidx.appcompat.view.menu.l(pVar.f588j, R$layout.abc_list_menu_item_layout);
                    pVar.f587i = lVar;
                    lVar.f778f = jVar;
                    androidx.appcompat.view.menu.o oVar = pVar.f586h;
                    oVar.b(lVar, oVar.f781a);
                }
                androidx.appcompat.view.menu.l lVar2 = pVar.f587i;
                n nVar2 = pVar.f583e;
                if (lVar2.f776d == null) {
                    lVar2.f776d = (ExpandedMenuView) lVar2.f774b.inflate(R$layout.abc_expanded_menu_layout, (ViewGroup) nVar2, false);
                    if (lVar2.f779g == null) {
                        lVar2.f779g = new androidx.appcompat.view.menu.k(lVar2);
                    }
                    lVar2.f776d.setAdapter((ListAdapter) lVar2.f779g);
                    lVar2.f776d.setOnItemClickListener(lVar2);
                }
                ExpandedMenuView expandedMenuView = lVar2.f776d;
                pVar.f584f = expandedMenuView;
                if (expandedMenuView == null) {
                    return;
                }
            }
            if (pVar.f584f == null) {
                return;
            }
            if (pVar.f585g == null) {
                androidx.appcompat.view.menu.l lVar3 = pVar.f587i;
                if (lVar3.f779g == null) {
                    lVar3.f779g = new androidx.appcompat.view.menu.k(lVar3);
                }
                if (lVar3.f779g.getCount() <= 0) {
                    return;
                }
            }
            ViewGroup.LayoutParams layoutParams3 = pVar.f584f.getLayoutParams();
            if (layoutParams3 == null) {
                layoutParams3 = new ViewGroup.LayoutParams(-2, -2);
            }
            pVar.f583e.setBackgroundResource(pVar.f580b);
            ViewParent parent = pVar.f584f.getParent();
            if (parent != null && (parent instanceof ViewGroup)) {
                ((ViewGroup) parent).removeView(pVar.f584f);
            }
            pVar.f583e.addView(pVar.f584f, layoutParams3);
            if (!pVar.f584f.hasFocus()) {
                pVar.f584f.requestFocus();
            }
            i2 = -2;
            pVar.f590l = false;
            WindowManager.LayoutParams layoutParams22 = new WindowManager.LayoutParams(i2, -2, 0, 0, 1002, 8519680, -3);
            layoutParams22.gravity = pVar.f581c;
            layoutParams22.windowAnimations = pVar.f582d;
            windowManager.addView(pVar.f583e, layoutParams22);
            pVar.f591m = true;
        }
    }

    public final boolean r(p pVar, int i2, KeyEvent keyEvent) {
        androidx.appcompat.view.menu.o oVar;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((pVar.f589k || s(pVar, keyEvent)) && (oVar = pVar.f586h) != null) {
            return oVar.performShortcut(i2, keyEvent, 1);
        }
        return false;
    }

    public final boolean s(p pVar, KeyEvent keyEvent) {
        E e2;
        E e3;
        Resources.Theme themeNewTheme;
        E e4;
        E e5;
        if (this.f601F) {
            return false;
        }
        if (pVar.f589k) {
            return true;
        }
        p pVar2 = this.f599D;
        if (pVar2 != null && pVar2 != pVar) {
            e(pVar2, false);
        }
        Window.Callback callback = this.f613b.getCallback();
        int i2 = pVar.f579a;
        if (callback != null) {
            pVar.f585g = callback.onCreatePanelView(i2);
        }
        boolean z2 = i2 == 0 || i2 == 108;
        if (z2 && (e5 = this.f620i) != null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) e5;
            actionBarOverlayLayout.e();
            ((v0) actionBarOverlayLayout.f895e).f1332m = true;
        }
        if (pVar.f585g == null && (!z2 || !(this.f617f instanceof x))) {
            androidx.appcompat.view.menu.o oVar = pVar.f586h;
            if (oVar == null || pVar.f593o) {
                if (oVar == null) {
                    Context context = this.f612a;
                    if ((i2 == 0 || i2 == 108) && this.f620i != null) {
                        TypedValue typedValue = new TypedValue();
                        Resources.Theme theme = context.getTheme();
                        theme.resolveAttribute(R$attr.actionBarTheme, typedValue, true);
                        if (typedValue.resourceId != 0) {
                            themeNewTheme = context.getResources().newTheme();
                            themeNewTheme.setTo(theme);
                            themeNewTheme.applyStyle(typedValue.resourceId, true);
                            themeNewTheme.resolveAttribute(R$attr.actionBarWidgetTheme, typedValue, true);
                        } else {
                            theme.resolveAttribute(R$attr.actionBarWidgetTheme, typedValue, true);
                            themeNewTheme = null;
                        }
                        if (typedValue.resourceId != 0) {
                            if (themeNewTheme == null) {
                                themeNewTheme = context.getResources().newTheme();
                                themeNewTheme.setTo(theme);
                            }
                            themeNewTheme.applyStyle(typedValue.resourceId, true);
                        }
                        if (themeNewTheme != null) {
                            d.d dVar = new d.d(context, 0);
                            dVar.getTheme().setTo(themeNewTheme);
                            context = dVar;
                        }
                    }
                    androidx.appcompat.view.menu.o oVar2 = new androidx.appcompat.view.menu.o(context);
                    oVar2.f785e = this;
                    androidx.appcompat.view.menu.o oVar3 = pVar.f586h;
                    if (oVar2 != oVar3) {
                        if (oVar3 != null) {
                            oVar3.r(pVar.f587i);
                        }
                        pVar.f586h = oVar2;
                        androidx.appcompat.view.menu.l lVar = pVar.f587i;
                        if (lVar != null) {
                            oVar2.b(lVar, oVar2.f781a);
                        }
                    }
                    if (pVar.f586h == null) {
                        return false;
                    }
                }
                if (z2 && (e3 = this.f620i) != null) {
                    if (this.f621j == null) {
                        this.f621j = new j(this, 2);
                    }
                    ((ActionBarOverlayLayout) e3).f(pVar.f586h, this.f621j);
                }
                pVar.f586h.w();
                if (!callback.onCreatePanelMenu(i2, pVar.f586h)) {
                    androidx.appcompat.view.menu.o oVar4 = pVar.f586h;
                    if (oVar4 != null) {
                        if (oVar4 != null) {
                            oVar4.r(pVar.f587i);
                        }
                        pVar.f586h = null;
                    }
                    if (z2 && (e2 = this.f620i) != null) {
                        ((ActionBarOverlayLayout) e2).f(null, this.f621j);
                    }
                    return false;
                }
                pVar.f593o = false;
            }
            pVar.f586h.w();
            Bundle bundle = pVar.f594p;
            if (bundle != null) {
                pVar.f586h.s(bundle);
                pVar.f594p = null;
            }
            if (!callback.onPreparePanel(0, pVar.f585g, pVar.f586h)) {
                if (z2 && (e4 = this.f620i) != null) {
                    ((ActionBarOverlayLayout) e4).f(null, this.f621j);
                }
                pVar.f586h.v();
                return false;
            }
            pVar.f586h.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
            pVar.f586h.v();
        }
        pVar.f589k = true;
        pVar.f590l = false;
        this.f599D = pVar;
        return true;
    }

    public final boolean t(int i2) {
        if (i2 == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            i2 = 108;
        } else if (i2 == 9) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            i2 = 109;
        }
        if (this.f596A && i2 == 108) {
            return false;
        }
        if (this.f633w && i2 == 1) {
            this.f633w = false;
        }
        if (i2 == 1) {
            w();
            this.f596A = true;
            return true;
        }
        if (i2 == 2) {
            w();
            this.f631u = true;
            return true;
        }
        if (i2 == 5) {
            w();
            this.f632v = true;
            return true;
        }
        if (i2 == 10) {
            w();
            this.f635y = true;
            return true;
        }
        if (i2 == 108) {
            w();
            this.f633w = true;
            return true;
        }
        if (i2 != 109) {
            return this.f613b.requestFeature(i2);
        }
        w();
        this.f634x = true;
        return true;
    }

    public final void u(int i2) {
        j();
        ViewGroup viewGroup = (ViewGroup) this.r.findViewById(R.id.content);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.f612a).inflate(i2, viewGroup);
        this.f614c.onContentChanged();
    }

    public final void v(CharSequence charSequence) {
        this.f619h = charSequence;
        E e2 = this.f620i;
        if (e2 != null) {
            e2.setWindowTitle(charSequence);
            return;
        }
        Y.r rVar = this.f617f;
        if (rVar != null) {
            rVar.m0(charSequence);
            return;
        }
        TextView textView = this.f629s;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public final void w() {
        if (this.f628q) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }
}
