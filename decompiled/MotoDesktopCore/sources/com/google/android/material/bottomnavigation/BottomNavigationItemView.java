package com.google.android.material.bottomnavigation;

import android.R;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.FrameLayout;
import androidx.appcompat.view.menu.B;
import androidx.appcompat.view.menu.q;
import java.util.WeakHashMap;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public class BottomNavigationItemView extends FrameLayout implements B {

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final int[] f2089f = {R.attr.state_checked};

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f2090a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f2091b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2092c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public q f2093d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public ColorStateList f2094e;

    @Override // androidx.appcompat.view.menu.B
    public final void c(q qVar) {
        this.f2093d = qVar;
        setCheckable(qVar.isCheckable());
        setChecked(qVar.isChecked());
        setEnabled(qVar.isEnabled());
        setIcon(qVar.getIcon());
        setTitle(qVar.f815e);
        setId(qVar.f811a);
        if (!TextUtils.isEmpty(qVar.f827q)) {
            setContentDescription(qVar.f827q);
        }
        setTooltipText(qVar.r);
        setVisibility(qVar.isVisible() ? 0 : 8);
    }

    @Override // androidx.appcompat.view.menu.B
    public q getItemData() {
        return this.f2093d;
    }

    public int getItemPosition() {
        return this.f2092c;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i2) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i2 + 1);
        q qVar = this.f2093d;
        if (qVar != null && qVar.isCheckable() && this.f2093d.isChecked()) {
            FrameLayout.mergeDrawableStates(iArrOnCreateDrawableState, f2089f);
        }
        return iArrOnCreateDrawableState;
    }

    public void setCheckable(boolean z2) {
        refreshDrawableState();
    }

    public void setChecked(boolean z2) {
        throw null;
    }

    @Override // android.view.View
    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        throw null;
    }

    public void setIcon(Drawable drawable) {
        if (drawable != null) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState != null) {
                drawable = constantState.newDrawable();
            }
            drawable.mutate().setTintList(this.f2094e);
        }
        throw null;
    }

    public void setIconSize(int i2) {
        throw null;
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.f2094e = colorStateList;
        q qVar = this.f2093d;
        if (qVar != null) {
            setIcon(qVar.getIcon());
        }
    }

    public void setItemBackground(int i2) {
        setItemBackground(i2 == 0 ? null : getContext().getDrawable(i2));
    }

    public void setItemBackground(Drawable drawable) {
        WeakHashMap weakHashMap = l.f2836a;
        setBackground(drawable);
    }

    public void setItemPosition(int i2) {
        this.f2092c = i2;
    }

    public void setLabelVisibilityMode(int i2) {
        if (this.f2090a != i2) {
            this.f2090a = i2;
            q qVar = this.f2093d;
            if (qVar != null) {
                setChecked(qVar.isChecked());
            }
        }
    }

    public void setShifting(boolean z2) {
        if (this.f2091b != z2) {
            this.f2091b = z2;
            q qVar = this.f2093d;
            if (qVar != null) {
                setChecked(qVar.isChecked());
            }
        }
    }

    public void setTextAppearanceActive(int i2) {
        throw null;
    }

    public void setTextAppearanceInactive(int i2) {
        throw null;
    }

    public void setTextColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            throw null;
        }
    }

    public void setTitle(CharSequence charSequence) {
        throw null;
    }
}
