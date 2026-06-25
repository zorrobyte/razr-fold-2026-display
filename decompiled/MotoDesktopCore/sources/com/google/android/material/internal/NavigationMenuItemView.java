package com.google.android.material.internal;

import F.j;
import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import androidx.appcompat.R$attr;
import androidx.appcompat.view.menu.B;
import androidx.appcompat.view.menu.q;
import androidx.appcompat.widget.M;
import com.google.android.material.R$dimen;
import com.google.android.material.R$drawable;
import com.google.android.material.R$id;
import com.google.android.material.R$layout;
import java.util.WeakHashMap;
import v.b;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public class NavigationMenuItemView extends ForegroundLinearLayout implements B {

    /* JADX INFO: renamed from: F, reason: collision with root package name */
    public static final int[] f2173F = {R.attr.state_checked};

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public q f2174A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public ColorStateList f2175B;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public boolean f2176C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public Drawable f2177D;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public final j f2178E;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public final int f2179v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public boolean f2180w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public boolean f2181x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public final CheckedTextView f2182y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public FrameLayout f2183z;

    public NavigationMenuItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        j jVar = new j(this, 3);
        this.f2178E = jVar;
        setOrientation(0);
        LayoutInflater.from(context).inflate(R$layout.design_navigation_menu_item, (ViewGroup) this, true);
        this.f2179v = context.getResources().getDimensionPixelSize(R$dimen.design_navigation_icon_size);
        CheckedTextView checkedTextView = (CheckedTextView) findViewById(R$id.design_menu_item_text);
        this.f2182y = checkedTextView;
        checkedTextView.setDuplicateParentStateEnabled(true);
        l.b(checkedTextView, jVar);
    }

    private void setActionView(View view) {
        if (view != null) {
            if (this.f2183z == null) {
                this.f2183z = (FrameLayout) ((ViewStub) findViewById(R$id.design_menu_item_action_area_stub)).inflate();
            }
            this.f2183z.removeAllViews();
            this.f2183z.addView(view);
        }
    }

    @Override // androidx.appcompat.view.menu.B
    public final void c(q qVar) {
        StateListDrawable stateListDrawable;
        this.f2174A = qVar;
        setVisibility(qVar.isVisible() ? 0 : 8);
        if (getBackground() == null) {
            TypedValue typedValue = new TypedValue();
            if (getContext().getTheme().resolveAttribute(R$attr.colorControlHighlight, typedValue, true)) {
                stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(f2173F, new ColorDrawable(typedValue.data));
                stateListDrawable.addState(ViewGroup.EMPTY_STATE_SET, new ColorDrawable(0));
            } else {
                stateListDrawable = null;
            }
            WeakHashMap weakHashMap = l.f2836a;
            setBackground(stateListDrawable);
        }
        setCheckable(qVar.isCheckable());
        setChecked(qVar.isChecked());
        setEnabled(qVar.isEnabled());
        setTitle(qVar.f815e);
        setIcon(qVar.getIcon());
        setActionView(qVar.getActionView());
        setContentDescription(qVar.f827q);
        setTooltipText(qVar.r);
        q qVar2 = this.f2174A;
        CharSequence charSequence = qVar2.f815e;
        CheckedTextView checkedTextView = this.f2182y;
        if (charSequence == null && qVar2.getIcon() == null && this.f2174A.getActionView() != null) {
            checkedTextView.setVisibility(8);
            FrameLayout frameLayout = this.f2183z;
            if (frameLayout != null) {
                M m2 = (M) frameLayout.getLayoutParams();
                ((ViewGroup.MarginLayoutParams) m2).width = -1;
                this.f2183z.setLayoutParams(m2);
                return;
            }
            return;
        }
        checkedTextView.setVisibility(0);
        FrameLayout frameLayout2 = this.f2183z;
        if (frameLayout2 != null) {
            M m3 = (M) frameLayout2.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) m3).width = -2;
            this.f2183z.setLayoutParams(m3);
        }
    }

    @Override // androidx.appcompat.view.menu.B
    public q getItemData() {
        return this.f2174A;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i2) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i2 + 1);
        q qVar = this.f2174A;
        if (qVar != null && qVar.isCheckable() && this.f2174A.isChecked()) {
            ViewGroup.mergeDrawableStates(iArrOnCreateDrawableState, f2173F);
        }
        return iArrOnCreateDrawableState;
    }

    public void setCheckable(boolean z2) {
        refreshDrawableState();
        if (this.f2181x != z2) {
            this.f2181x = z2;
            this.f2178E.getClass();
            b.e(this.f2182y, 2048);
        }
    }

    public void setChecked(boolean z2) {
        refreshDrawableState();
        this.f2182y.setChecked(z2);
    }

    public void setHorizontalPadding(int i2) {
        setPadding(i2, 0, i2, 0);
    }

    public void setIcon(Drawable drawable) {
        int i2 = this.f2179v;
        if (drawable != null) {
            if (this.f2176C) {
                Drawable.ConstantState constantState = drawable.getConstantState();
                if (constantState != null) {
                    drawable = constantState.newDrawable();
                }
                drawable = drawable.mutate();
                drawable.setTintList(this.f2175B);
            }
            drawable.setBounds(0, 0, i2, i2);
        } else if (this.f2180w) {
            if (this.f2177D == null) {
                Drawable drawable2 = getResources().getDrawable(R$drawable.navigation_empty_icon, getContext().getTheme());
                this.f2177D = drawable2;
                if (drawable2 != null) {
                    drawable2.setBounds(0, 0, i2, i2);
                }
            }
            drawable = this.f2177D;
        }
        this.f2182y.setCompoundDrawablesRelative(drawable, null, null, null);
    }

    public void setIconPadding(int i2) {
        this.f2182y.setCompoundDrawablePadding(i2);
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.f2175B = colorStateList;
        this.f2176C = colorStateList != null;
        q qVar = this.f2174A;
        if (qVar != null) {
            setIcon(qVar.getIcon());
        }
    }

    public void setNeedsEmptyIcon(boolean z2) {
        this.f2180w = z2;
    }

    public void setTextAppearance(int i2) {
        this.f2182y.setTextAppearance(i2);
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.f2182y.setTextColor(colorStateList);
    }

    public void setTitle(CharSequence charSequence) {
        this.f2182y.setText(charSequence);
    }
}
