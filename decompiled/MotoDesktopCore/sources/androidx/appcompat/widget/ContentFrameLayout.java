package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes.dex */
public class ContentFrameLayout extends FrameLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public TypedValue f984a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public TypedValue f985b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public TypedValue f986c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public TypedValue f987d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public TypedValue f988e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public TypedValue f989f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final Rect f990g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public D f991h;

    public ContentFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.f990g = new Rect();
    }

    public final void a(Rect rect) {
        fitSystemWindows(rect);
    }

    public TypedValue getFixedHeightMajor() {
        if (this.f988e == null) {
            this.f988e = new TypedValue();
        }
        return this.f988e;
    }

    public TypedValue getFixedHeightMinor() {
        if (this.f989f == null) {
            this.f989f = new TypedValue();
        }
        return this.f989f;
    }

    public TypedValue getFixedWidthMajor() {
        if (this.f986c == null) {
            this.f986c = new TypedValue();
        }
        return this.f986c;
    }

    public TypedValue getFixedWidthMinor() {
        if (this.f987d == null) {
            this.f987d = new TypedValue();
        }
        return this.f987d;
    }

    public TypedValue getMinWidthMajor() {
        if (this.f984a == null) {
            this.f984a = new TypedValue();
        }
        return this.f984a;
    }

    public TypedValue getMinWidthMinor() {
        if (this.f985b == null) {
            this.f985b = new TypedValue();
        }
        return this.f985b;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        D d2 = this.f991h;
        if (d2 != null) {
            d2.getClass();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        C0074k c0074k;
        super.onDetachedFromWindow();
        D d2 = this.f991h;
        if (d2 != null) {
            androidx.appcompat.app.q qVar = ((androidx.appcompat.app.j) d2).f569b;
            E e2 = qVar.f620i;
            if (e2 != null) {
                ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) e2;
                actionBarOverlayLayout.e();
                ActionMenuView actionMenuView = ((v0) actionBarOverlayLayout.f895e).f1320a.f1154a;
                if (actionMenuView != null && (c0074k = actionMenuView.f921t) != null) {
                    c0074k.j();
                    C0069f c0069f = c0074k.f1244u;
                    if (c0069f != null && c0069f.b()) {
                        c0069f.f854j.dismiss();
                    }
                }
            }
            if (qVar.f625n != null) {
                qVar.f613b.getDecorView().removeCallbacks(qVar.f626o);
                if (qVar.f625n.isShowing()) {
                    try {
                        qVar.f625n.dismiss();
                    } catch (IllegalArgumentException unused) {
                    }
                }
                qVar.f625n = null;
            }
            v.m mVar = qVar.f627p;
            if (mVar != null) {
                mVar.b();
            }
            androidx.appcompat.view.menu.o oVar = qVar.k(0).f586h;
            if (oVar != null) {
                oVar.c(true);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00de  */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onMeasure(int r17, int r18) {
        /*
            Method dump skipped, instruction units count: 229
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ContentFrameLayout.onMeasure(int, int):void");
    }

    public void setAttachListener(D d2) {
        this.f991h = d2;
    }
}
