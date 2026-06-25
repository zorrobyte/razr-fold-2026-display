package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import androidx.appcompat.R$dimen;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class y {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f845a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final o f846b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final boolean f847c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final int f848d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final int f849e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public View f850f;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public boolean f852h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public z f853i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public w f854j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public PopupWindow.OnDismissListener f855k;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f851g = 8388611;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final x f856l = new x(this);

    public y(int i2, int i3, Context context, View view, o oVar, boolean z2) {
        this.f845a = context;
        this.f846b = oVar;
        this.f850f = view;
        this.f847c = z2;
        this.f848d = i2;
        this.f849e = i3;
    }

    public final w a() {
        w f2;
        if (this.f854j == null) {
            Context context = this.f845a;
            Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            if (Math.min(point.x, point.y) >= context.getResources().getDimensionPixelSize(R$dimen.abc_cascading_menus_min_smallest_width)) {
                f2 = new j(this.f845a, this.f850f, this.f848d, this.f849e, this.f847c);
            } else {
                View view = this.f850f;
                int i2 = this.f849e;
                boolean z2 = this.f847c;
                f2 = new F(this.f848d, i2, this.f845a, view, this.f846b, z2);
            }
            f2.i(this.f846b);
            f2.o(this.f856l);
            f2.k(this.f850f);
            f2.c(this.f853i);
            f2.l(this.f852h);
            f2.m(this.f851g);
            this.f854j = f2;
        }
        return this.f854j;
    }

    public final boolean b() {
        w wVar = this.f854j;
        return wVar != null && wVar.isShowing();
    }

    public void c() {
        this.f854j = null;
        PopupWindow.OnDismissListener onDismissListener = this.f855k;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public final void d(int i2, int i3, boolean z2, boolean z3) {
        w wVarA = a();
        wVarA.p(z3);
        if (z2) {
            int i4 = this.f851g;
            View view = this.f850f;
            WeakHashMap weakHashMap = v.l.f2836a;
            if ((Gravity.getAbsoluteGravity(i4, view.getLayoutDirection()) & 7) == 5) {
                i2 -= this.f850f.getWidth();
            }
            wVarA.n(i2);
            wVarA.q(i3);
            int i5 = (int) ((this.f845a.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            wVarA.f843a = new Rect(i2 - i5, i3 - i5, i2 + i5, i3 + i5);
        }
        wVarA.show();
    }
}
