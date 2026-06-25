package androidx.appcompat.widget;

import android.content.Context;
import android.view.View;
import androidx.appcompat.R$attr;

/* JADX INFO: renamed from: androidx.appcompat.widget.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0069f extends androidx.appcompat.view.menu.y {

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public final /* synthetic */ int f1207m = 0;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final /* synthetic */ C0074k f1208n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0069f(C0074k c0074k, Context context, androidx.appcompat.view.menu.G g2, View view) {
        super(R$attr.actionOverflowMenuStyle, 0, context, view, g2, false);
        this.f1208n = c0074k;
        if (!g2.f695B.f()) {
            View view2 = c0074k.f1233i;
            this.f850f = view2 == null ? (View) c0074k.f1232h : view2;
        }
        e0.k kVar = c0074k.f1247x;
        this.f853i = kVar;
        androidx.appcompat.view.menu.w wVar = this.f854j;
        if (wVar != null) {
            wVar.c(kVar);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0069f(C0074k c0074k, Context context, androidx.appcompat.view.menu.o oVar, C0072i c0072i) {
        super(R$attr.actionOverflowMenuStyle, 0, context, c0072i, oVar, true);
        this.f1208n = c0074k;
        this.f851g = 8388613;
        e0.k kVar = c0074k.f1247x;
        this.f853i = kVar;
        androidx.appcompat.view.menu.w wVar = this.f854j;
        if (wVar != null) {
            wVar.c(kVar);
        }
    }

    @Override // androidx.appcompat.view.menu.y
    public final void c() {
        switch (this.f1207m) {
            case 0:
                C0074k c0074k = this.f1208n;
                c0074k.f1244u = null;
                c0074k.getClass();
                super.c();
                break;
            default:
                C0074k c0074k2 = this.f1208n;
                androidx.appcompat.view.menu.o oVar = c0074k2.f1227c;
                if (oVar != null) {
                    oVar.c(true);
                }
                c0074k2.f1243t = null;
                super.c();
                break;
        }
    }
}
