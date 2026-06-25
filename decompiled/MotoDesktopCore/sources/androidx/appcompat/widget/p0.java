package androidx.appcompat.widget;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.appcompat.R$attr;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class p0 implements androidx.appcompat.view.menu.A {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public androidx.appcompat.view.menu.o f1280a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public androidx.appcompat.view.menu.q f1281b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Toolbar f1282c;

    public p0(Toolbar toolbar) {
        this.f1282c = toolbar;
    }

    @Override // androidx.appcompat.view.menu.A
    public final void a(androidx.appcompat.view.menu.o oVar, boolean z2) {
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean b() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean d(androidx.appcompat.view.menu.q qVar) {
        Toolbar toolbar = this.f1282c;
        KeyEvent.Callback callback = toolbar.f1162i;
        if (callback instanceof d.c) {
            ((d.c) callback).onActionViewCollapsed();
        }
        toolbar.removeView(toolbar.f1162i);
        toolbar.removeView(toolbar.f1161h);
        toolbar.f1162i = null;
        ArrayList arrayList = toolbar.f1143E;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            toolbar.addView((View) arrayList.get(size));
        }
        arrayList.clear();
        this.f1281b = null;
        toolbar.requestLayout();
        qVar.f810C = false;
        qVar.f824n.p(false);
        return true;
    }

    @Override // androidx.appcompat.view.menu.A
    public final void e(Context context, androidx.appcompat.view.menu.o oVar) {
        androidx.appcompat.view.menu.q qVar;
        androidx.appcompat.view.menu.o oVar2 = this.f1280a;
        if (oVar2 != null && (qVar = this.f1281b) != null) {
            oVar2.d(qVar);
        }
        this.f1280a = oVar;
    }

    @Override // androidx.appcompat.view.menu.A
    public final void f() {
        if (this.f1281b != null) {
            androidx.appcompat.view.menu.o oVar = this.f1280a;
            if (oVar != null) {
                int size = oVar.f786f.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (this.f1280a.getItem(i2) == this.f1281b) {
                        return;
                    }
                }
            }
            d(this.f1281b);
        }
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean g(androidx.appcompat.view.menu.G g2) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean h(androidx.appcompat.view.menu.q qVar) {
        Toolbar toolbar = this.f1282c;
        if (toolbar.f1161h == null) {
            AppCompatImageButton appCompatImageButton = new AppCompatImageButton(toolbar.getContext(), null, R$attr.toolbarNavigationButtonStyle);
            toolbar.f1161h = appCompatImageButton;
            appCompatImageButton.setImageDrawable(toolbar.f1159f);
            toolbar.f1161h.setContentDescription(toolbar.f1160g);
            q0 q0VarG = Toolbar.g();
            q0VarG.f1289a = (toolbar.f1167n & 112) | 8388611;
            q0VarG.f1290b = 2;
            toolbar.f1161h.setLayoutParams(q0VarG);
            toolbar.f1161h.setOnClickListener(new K.c(4, toolbar));
        }
        ViewParent parent = toolbar.f1161h.getParent();
        if (parent != toolbar) {
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(toolbar.f1161h);
            }
            toolbar.addView(toolbar.f1161h);
        }
        View actionView = qVar.getActionView();
        toolbar.f1162i = actionView;
        this.f1281b = qVar;
        ViewParent parent2 = actionView.getParent();
        if (parent2 != toolbar) {
            if (parent2 instanceof ViewGroup) {
                ((ViewGroup) parent2).removeView(toolbar.f1162i);
            }
            q0 q0VarG2 = Toolbar.g();
            q0VarG2.f1289a = (toolbar.f1167n & 112) | 8388611;
            q0VarG2.f1290b = 2;
            toolbar.f1162i.setLayoutParams(q0VarG2);
            toolbar.addView(toolbar.f1162i);
        }
        for (int childCount = toolbar.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = toolbar.getChildAt(childCount);
            if (((q0) childAt.getLayoutParams()).f1290b != 2 && childAt != toolbar.f1154a) {
                toolbar.removeViewAt(childCount);
                toolbar.f1143E.add(childAt);
            }
        }
        toolbar.requestLayout();
        qVar.f810C = true;
        qVar.f824n.p(false);
        KeyEvent.Callback callback = toolbar.f1162i;
        if (callback instanceof d.c) {
            ((d.c) callback).onActionViewExpanded();
        }
        return true;
    }
}
