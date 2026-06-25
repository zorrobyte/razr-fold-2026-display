package androidx.recyclerview.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public final class o {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final v f1904a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f1905b;

    public o(v vVar, int i2) {
        this.f1905b = i2;
        new Rect();
        this.f1904a = vVar;
    }

    public static o a(v vVar, int i2) {
        if (i2 == 0) {
            return new o(vVar, 0);
        }
        if (i2 == 1) {
            return new o(vVar, 1);
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    public final int b(View view) {
        switch (this.f1905b) {
            case 0:
                w wVar = (w) view.getLayoutParams();
                this.f1904a.getClass();
                return view.getRight() + ((w) view.getLayoutParams()).f1919a.right + ((ViewGroup.MarginLayoutParams) wVar).rightMargin;
            default:
                w wVar2 = (w) view.getLayoutParams();
                this.f1904a.getClass();
                return view.getBottom() + ((w) view.getLayoutParams()).f1919a.bottom + ((ViewGroup.MarginLayoutParams) wVar2).bottomMargin;
        }
    }

    public final int c(View view) {
        switch (this.f1905b) {
            case 0:
                w wVar = (w) view.getLayoutParams();
                this.f1904a.getClass();
                return (view.getLeft() - ((w) view.getLayoutParams()).f1919a.left) - ((ViewGroup.MarginLayoutParams) wVar).leftMargin;
            default:
                w wVar2 = (w) view.getLayoutParams();
                this.f1904a.getClass();
                return (view.getTop() - ((w) view.getLayoutParams()).f1919a.top) - ((ViewGroup.MarginLayoutParams) wVar2).topMargin;
        }
    }

    public final int d() {
        switch (this.f1905b) {
            case 0:
                v vVar = this.f1904a;
                return vVar.f1917f - vVar.u();
            default:
                v vVar2 = this.f1904a;
                return vVar2.f1918g - vVar2.s();
        }
    }

    public final int e() {
        switch (this.f1905b) {
            case 0:
                return this.f1904a.t();
            default:
                return this.f1904a.v();
        }
    }

    public final int f() {
        switch (this.f1905b) {
            case 0:
                v vVar = this.f1904a;
                return (vVar.f1917f - vVar.t()) - vVar.u();
            default:
                v vVar2 = this.f1904a;
                return (vVar2.f1918g - vVar2.v()) - vVar2.s();
        }
    }
}
