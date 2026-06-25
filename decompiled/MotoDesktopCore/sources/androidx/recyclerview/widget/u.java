package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public final class u {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1910a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ v f1911b;

    public /* synthetic */ u(v vVar, int i2) {
        this.f1910a = i2;
        this.f1911b = vVar;
    }

    public final View a(int i2) {
        switch (this.f1910a) {
        }
        return this.f1911b.o(i2);
    }

    public final int b(View view) {
        switch (this.f1910a) {
            case 0:
                w wVar = (w) view.getLayoutParams();
                this.f1911b.getClass();
                return view.getRight() + ((w) view.getLayoutParams()).f1919a.right + ((ViewGroup.MarginLayoutParams) wVar).rightMargin;
            default:
                w wVar2 = (w) view.getLayoutParams();
                this.f1911b.getClass();
                return view.getBottom() + ((w) view.getLayoutParams()).f1919a.bottom + ((ViewGroup.MarginLayoutParams) wVar2).bottomMargin;
        }
    }

    public final int c(View view) {
        switch (this.f1910a) {
            case 0:
                w wVar = (w) view.getLayoutParams();
                this.f1911b.getClass();
                return (view.getLeft() - ((w) view.getLayoutParams()).f1919a.left) - ((ViewGroup.MarginLayoutParams) wVar).leftMargin;
            default:
                w wVar2 = (w) view.getLayoutParams();
                this.f1911b.getClass();
                return (view.getTop() - ((w) view.getLayoutParams()).f1919a.top) - ((ViewGroup.MarginLayoutParams) wVar2).topMargin;
        }
    }

    public final int d() {
        switch (this.f1910a) {
            case 0:
                v vVar = this.f1911b;
                return vVar.f1917f - vVar.u();
            default:
                v vVar2 = this.f1911b;
                return vVar2.f1918g - vVar2.s();
        }
    }

    public final int e() {
        switch (this.f1910a) {
            case 0:
                return this.f1911b.t();
            default:
                return this.f1911b.v();
        }
    }
}
