package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.R$styleable;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: androidx.appcompat.widget.q, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0080q {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final View f1283a;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public n0 f1286d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public n0 f1287e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public n0 f1288f;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f1285c = -1;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final C0082t f1284b = C0082t.f();

    public C0080q(View view) {
        this.f1283a = view;
    }

    public final void a() {
        View view = this.f1283a;
        Drawable background = view.getBackground();
        if (background != null) {
            if (this.f1286d != null) {
                if (this.f1288f == null) {
                    this.f1288f = new n0();
                }
                n0 n0Var = this.f1288f;
                n0Var.f1276a = null;
                n0Var.f1279d = false;
                n0Var.f1277b = null;
                n0Var.f1278c = false;
                WeakHashMap weakHashMap = v.l.f2836a;
                ColorStateList backgroundTintList = view.getBackgroundTintList();
                if (backgroundTintList != null) {
                    n0Var.f1279d = true;
                    n0Var.f1276a = backgroundTintList;
                }
                PorterDuff.Mode backgroundTintMode = view.getBackgroundTintMode();
                if (backgroundTintMode != null) {
                    n0Var.f1278c = true;
                    n0Var.f1277b = backgroundTintMode;
                }
                if (n0Var.f1279d || n0Var.f1278c) {
                    C0082t.m(background, n0Var, view.getDrawableState());
                    return;
                }
            }
            n0 n0Var2 = this.f1287e;
            if (n0Var2 != null) {
                C0082t.m(background, n0Var2, view.getDrawableState());
                return;
            }
            n0 n0Var3 = this.f1286d;
            if (n0Var3 != null) {
                C0082t.m(background, n0Var3, view.getDrawableState());
            }
        }
    }

    public final ColorStateList b() {
        n0 n0Var = this.f1287e;
        if (n0Var != null) {
            return n0Var.f1276a;
        }
        return null;
    }

    public final PorterDuff.Mode c() {
        n0 n0Var = this.f1287e;
        if (n0Var != null) {
            return n0Var.f1277b;
        }
        return null;
    }

    public final void d(AttributeSet attributeSet, int i2) {
        View view = this.f1283a;
        f0.b bVarM = f0.b.m(view.getContext(), attributeSet, R$styleable.ViewBackgroundHelper, i2, 0);
        TypedArray typedArray = (TypedArray) bVarM.f2538c;
        try {
            if (typedArray.hasValue(R$styleable.ViewBackgroundHelper_android_background)) {
                this.f1285c = typedArray.getResourceId(R$styleable.ViewBackgroundHelper_android_background, -1);
                ColorStateList colorStateListK = this.f1284b.k(view.getContext(), this.f1285c);
                if (colorStateListK != null) {
                    g(colorStateListK);
                }
            }
            if (typedArray.hasValue(R$styleable.ViewBackgroundHelper_backgroundTint)) {
                ColorStateList colorStateListE = bVarM.e(R$styleable.ViewBackgroundHelper_backgroundTint);
                WeakHashMap weakHashMap = v.l.f2836a;
                view.setBackgroundTintList(colorStateListE);
            }
            if (typedArray.hasValue(R$styleable.ViewBackgroundHelper_backgroundTintMode)) {
                PorterDuff.Mode modeC = G.c(typedArray.getInt(R$styleable.ViewBackgroundHelper_backgroundTintMode, -1), null);
                WeakHashMap weakHashMap2 = v.l.f2836a;
                view.setBackgroundTintMode(modeC);
            }
            bVarM.n();
        } catch (Throwable th) {
            bVarM.n();
            throw th;
        }
    }

    public final void e() {
        this.f1285c = -1;
        g(null);
        a();
    }

    public final void f(int i2) {
        this.f1285c = i2;
        C0082t c0082t = this.f1284b;
        g(c0082t != null ? c0082t.k(this.f1283a.getContext(), i2) : null);
        a();
    }

    public final void g(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.f1286d == null) {
                this.f1286d = new n0();
            }
            n0 n0Var = this.f1286d;
            n0Var.f1276a = colorStateList;
            n0Var.f1279d = true;
        } else {
            this.f1286d = null;
        }
        a();
    }

    public final void h(ColorStateList colorStateList) {
        if (this.f1287e == null) {
            this.f1287e = new n0();
        }
        n0 n0Var = this.f1287e;
        n0Var.f1276a = colorStateList;
        n0Var.f1279d = true;
        a();
    }

    public final void i(PorterDuff.Mode mode) {
        if (this.f1287e == null) {
            this.f1287e = new n0();
        }
        n0 n0Var = this.f1287e;
        n0Var.f1277b = mode;
        n0Var.f1278c = true;
        a();
    }
}
