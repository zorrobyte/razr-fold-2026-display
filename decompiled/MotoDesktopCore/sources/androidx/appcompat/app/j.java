package androidx.appcompat.app;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import androidx.appcompat.R$color;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.D;
import androidx.appcompat.widget.y0;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class j implements v.j, D, androidx.appcompat.view.menu.z {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f568a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ q f569b;

    public /* synthetic */ j(q qVar, int i2) {
        this.f568a = i2;
        this.f569b = qVar;
    }

    @Override // androidx.appcompat.view.menu.z
    public void a(androidx.appcompat.view.menu.o oVar, boolean z2) {
        p pVar;
        switch (this.f568a) {
            case 2:
                this.f569b.d(oVar);
                break;
            default:
                androidx.appcompat.view.menu.o oVarK = oVar.k();
                int i2 = 0;
                boolean z3 = oVarK != oVar;
                if (z3) {
                    oVar = oVarK;
                }
                q qVar = this.f569b;
                p[] pVarArr = qVar.f598C;
                int length = pVarArr != null ? pVarArr.length : 0;
                while (true) {
                    if (i2 >= length) {
                        pVar = null;
                    } else {
                        pVar = pVarArr[i2];
                        if (pVar == null || pVar.f586h != oVar) {
                            i2++;
                        }
                    }
                }
                if (pVar != null) {
                    if (!z3) {
                        qVar.e(pVar, z2);
                    } else {
                        qVar.b(pVar.f579a, pVar, oVarK);
                        qVar.e(pVar, true);
                    }
                }
                break;
        }
    }

    @Override // androidx.appcompat.view.menu.z
    public boolean e(androidx.appcompat.view.menu.o oVar) {
        Window.Callback callback;
        switch (this.f568a) {
            case 2:
                Window.Callback callback2 = this.f569b.f613b.getCallback();
                if (callback2 != null) {
                    callback2.onMenuOpened(108, oVar);
                }
                break;
            default:
                if (oVar == null) {
                    q qVar = this.f569b;
                    if (qVar.f633w && (callback = qVar.f613b.getCallback()) != null && !qVar.f601F) {
                        callback.onMenuOpened(108, oVar);
                        break;
                    }
                }
                break;
        }
        return true;
    }

    @Override // v.j
    public v.o g(View view, v.o oVar) {
        int i2;
        boolean z2;
        boolean z3;
        int iC = oVar.c();
        q qVar = this.f569b;
        ActionBarContextView actionBarContextView = qVar.f624m;
        if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            i2 = iC;
            z2 = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) qVar.f624m.getLayoutParams();
            if (qVar.f624m.isShown()) {
                if (qVar.f609N == null) {
                    qVar.f609N = new Rect();
                    qVar.f610O = new Rect();
                }
                Rect rect = qVar.f609N;
                Rect rect2 = qVar.f610O;
                rect.set(0, iC, 0, 0);
                ViewGroup viewGroup = qVar.r;
                Method method = y0.f1347a;
                if (method != null) {
                    try {
                        method.invoke(viewGroup, rect, rect2);
                    } catch (Exception e2) {
                        Log.d("ViewUtils", "Could not invoke computeFitSystemWindows", e2);
                    }
                }
                if (marginLayoutParams.topMargin != (rect2.top == 0 ? iC : 0)) {
                    marginLayoutParams.topMargin = iC;
                    View view2 = qVar.f630t;
                    if (view2 == null) {
                        Context context = qVar.f612a;
                        View view3 = new View(context);
                        qVar.f630t = view3;
                        view3.setBackgroundColor(context.getResources().getColor(R$color.abc_input_method_navigation_guard));
                        qVar.r.addView(qVar.f630t, -1, new ViewGroup.LayoutParams(-1, iC));
                    } else {
                        ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                        if (layoutParams.height != iC) {
                            layoutParams.height = iC;
                            qVar.f630t.setLayoutParams(layoutParams);
                        }
                    }
                    z3 = true;
                } else {
                    z3 = false;
                }
                z = qVar.f630t != null;
                i2 = (qVar.f635y || !z) ? iC : 0;
                boolean z4 = z;
                z = z3;
                z2 = z4;
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                i2 = iC;
                z2 = false;
            } else {
                i2 = iC;
                z2 = false;
                z = false;
            }
            if (z) {
                qVar.f624m.setLayoutParams(marginLayoutParams);
            }
        }
        View view4 = qVar.f630t;
        if (view4 != null) {
            view4.setVisibility(z2 ? 0 : 8);
        }
        if (iC != i2) {
            int iA = oVar.a();
            int iB = oVar.b();
            Object obj = oVar.f2838a;
            oVar = new v.o(((WindowInsets) obj).replaceSystemWindowInsets(iA, i2, iB, ((WindowInsets) obj).getSystemWindowInsetBottom()));
        }
        WeakHashMap weakHashMap = v.l.f2836a;
        WindowInsets windowInsets = (WindowInsets) oVar.f2838a;
        WindowInsets windowInsetsOnApplyWindowInsets = view.onApplyWindowInsets(windowInsets);
        if (windowInsetsOnApplyWindowInsets != windowInsets) {
            windowInsets = new WindowInsets(windowInsetsOnApplyWindowInsets);
        }
        if (windowInsets == null) {
            return null;
        }
        return new v.o(windowInsets);
    }
}
