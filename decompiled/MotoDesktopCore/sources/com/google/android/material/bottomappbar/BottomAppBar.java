package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import h.k;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import k.AbstractC0143b;
import k.C0146e;
import k.InterfaceC0142a;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public class BottomAppBar extends Toolbar implements InterfaceC0142a {

    /* JADX INFO: renamed from: U, reason: collision with root package name */
    public static final /* synthetic */ int f2073U = 0;

    /* JADX INFO: renamed from: P, reason: collision with root package name */
    public Animator f2074P;

    /* JADX INFO: renamed from: Q, reason: collision with root package name */
    public Animator f2075Q;

    /* JADX INFO: renamed from: R, reason: collision with root package name */
    public int f2076R;

    /* JADX INFO: renamed from: S, reason: collision with root package name */
    public boolean f2077S;

    /* JADX INFO: renamed from: T, reason: collision with root package name */
    public boolean f2078T;

    public static class Behavior extends HideBottomViewOnScrollBehavior<BottomAppBar> {

        /* JADX INFO: renamed from: d, reason: collision with root package name */
        public final Rect f2079d;

        public Behavior() {
            this.f2079d = new Rect();
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f2079d = new Rect();
        }

        @Override // com.google.android.material.behavior.HideBottomViewOnScrollBehavior, k.AbstractC0143b
        public final boolean f(CoordinatorLayout coordinatorLayout, View view, int i2) {
            Animator animator;
            BottomAppBar bottomAppBar = (BottomAppBar) view;
            int i3 = BottomAppBar.f2073U;
            FloatingActionButton floatingActionButtonW = bottomAppBar.w();
            if (floatingActionButtonW != null) {
                ((C0146e) floatingActionButtonW.getLayoutParams()).f2749d = 17;
                floatingActionButtonW.j();
                floatingActionButtonW.k();
                floatingActionButtonW.c();
                floatingActionButtonW.d();
                this.f2079d.set(0, 0, floatingActionButtonW.getMeasuredWidth(), floatingActionButtonW.getMeasuredHeight());
                throw null;
            }
            Animator animator2 = bottomAppBar.f2075Q;
            if ((animator2 == null || !animator2.isRunning()) && ((animator = bottomAppBar.f2074P) == null || !animator.isRunning())) {
                BottomAppBar.u(bottomAppBar);
                throw null;
            }
            coordinatorLayout.p(bottomAppBar, i2);
            this.f2063a = bottomAppBar.getMeasuredHeight();
            return false;
        }

        @Override // k.AbstractC0143b
        public final boolean p(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i2, int i3) {
            BottomAppBar bottomAppBar = (BottomAppBar) view;
            return bottomAppBar.getHideOnScroll() && super.p(coordinatorLayout, bottomAppBar, view2, view3, i2, i3);
        }

        @Override // com.google.android.material.behavior.HideBottomViewOnScrollBehavior
        public final void s(View view) {
            BottomAppBar bottomAppBar = (BottomAppBar) view;
            super.s(bottomAppBar);
            int i2 = BottomAppBar.f2073U;
            FloatingActionButton floatingActionButtonW = bottomAppBar.w();
            if (floatingActionButtonW != null) {
                Rect rect = this.f2079d;
                floatingActionButtonW.e(rect);
                float measuredHeight = floatingActionButtonW.getMeasuredHeight() - rect.height();
                floatingActionButtonW.clearAnimation();
                floatingActionButtonW.animate().translationY((-floatingActionButtonW.getPaddingBottom()) + measuredHeight).setInterpolator(H.a.f143b).setDuration(175L);
            }
        }

        @Override // com.google.android.material.behavior.HideBottomViewOnScrollBehavior
        public final void t(View view) {
            BottomAppBar bottomAppBar = (BottomAppBar) view;
            super.t(bottomAppBar);
            int i2 = BottomAppBar.f2073U;
            FloatingActionButton floatingActionButtonW = bottomAppBar.w();
            if (floatingActionButtonW != null) {
                floatingActionButtonW.clearAnimation();
                floatingActionButtonW.animate().translationY(bottomAppBar.getFabTranslationY()).setInterpolator(H.a.f144c).setDuration(225L);
            }
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new c();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f2080c;

        /* JADX INFO: renamed from: d, reason: collision with root package name */
        public boolean f2081d;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f2080c = parcel.readInt();
            this.f2081d = parcel.readInt() != 0;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f2080c);
            parcel.writeInt(this.f2081d ? 1 : 0);
        }
    }

    private ActionMenuView getActionMenuView() {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof ActionMenuView) {
                return (ActionMenuView) childAt;
            }
        }
        return null;
    }

    private float getFabTranslationX() {
        return x(this.f2076R);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getFabTranslationY() {
        boolean z2 = this.f2078T;
        FloatingActionButton floatingActionButtonW = w();
        if (floatingActionButtonW == null) {
            return 0.0f;
        }
        Rect rect = new Rect();
        floatingActionButtonW.e(rect);
        float fHeight = rect.height();
        if (fHeight == 0.0f) {
            fHeight = floatingActionButtonW.getMeasuredHeight();
        }
        float height = floatingActionButtonW.getHeight() - rect.bottom;
        float height2 = floatingActionButtonW.getHeight() - rect.height();
        float f2 = (fHeight / 2.0f) + (-getCradleVerticalOffset()) + height;
        float paddingBottom = height2 - floatingActionButtonW.getPaddingBottom();
        float f3 = -getMeasuredHeight();
        if (!z2) {
            f2 = paddingBottom;
        }
        return f3 + f2;
    }

    public static void u(BottomAppBar bottomAppBar) {
        bottomAppBar.getFabTranslationX();
        throw null;
    }

    public ColorStateList getBackgroundTint() {
        throw null;
    }

    @Override // k.InterfaceC0142a
    public AbstractC0143b getBehavior() {
        return new Behavior();
    }

    public float getCradleVerticalOffset() {
        throw null;
    }

    public int getFabAlignmentMode() {
        return this.f2076R;
    }

    public float getFabCradleMargin() {
        throw null;
    }

    public float getFabCradleRoundedCornerRadius() {
        throw null;
    }

    public boolean getHideOnScroll() {
        return this.f2077S;
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        Animator animator = this.f2075Q;
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = this.f2074P;
        if (animator2 != null) {
            animator2.cancel();
        }
        getFabTranslationX();
        throw null;
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f1465a);
        this.f2076R = savedState.f2080c;
        this.f2078T = savedState.f2081d;
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f2080c = this.f2076R;
        savedState.f2081d = this.f2078T;
        return savedState;
    }

    public void setBackgroundTint(ColorStateList colorStateList) {
        throw null;
    }

    public void setCradleVerticalOffset(float f2) {
        if (f2 != getCradleVerticalOffset()) {
            throw null;
        }
    }

    public void setFabAlignmentMode(int i2) {
        int i3;
        FloatingActionButton floatingActionButtonW;
        if (this.f2076R != i2) {
            WeakHashMap weakHashMap = l.f2836a;
            if (isLaidOut()) {
                Animator animator = this.f2074P;
                if (animator != null) {
                    animator.cancel();
                }
                ArrayList arrayList = new ArrayList();
                if (this.f2078T) {
                    throw null;
                }
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(w(), "translationX", x(i2));
                objectAnimatorOfFloat.setDuration(300L);
                arrayList.add(objectAnimatorOfFloat);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList);
                this.f2074P = animatorSet;
                animatorSet.addListener(new a(this, 0));
                this.f2074P.start();
            }
        }
        boolean z2 = this.f2078T;
        WeakHashMap weakHashMap2 = l.f2836a;
        if (isLaidOut()) {
            Animator animator2 = this.f2075Q;
            if (animator2 != null) {
                animator2.cancel();
            }
            ArrayList arrayList2 = new ArrayList();
            FloatingActionButton floatingActionButtonW2 = w();
            if (floatingActionButtonW2 == null || !floatingActionButtonW2.h()) {
                z2 = false;
                i3 = 0;
            } else {
                i3 = i2;
            }
            ActionMenuView actionMenuView = getActionMenuView();
            if (actionMenuView != null) {
                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(actionMenuView, "alpha", 1.0f);
                if ((this.f2078T || (z2 && (floatingActionButtonW = w()) != null && floatingActionButtonW.h())) && (this.f2076R == 1 || i3 == 1)) {
                    ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(actionMenuView, "alpha", 0.0f);
                    objectAnimatorOfFloat3.addListener(new b(this, actionMenuView, i3, z2));
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    animatorSet2.setDuration(150L);
                    animatorSet2.playSequentially(objectAnimatorOfFloat3, objectAnimatorOfFloat2);
                    arrayList2.add(animatorSet2);
                } else if (actionMenuView.getAlpha() < 1.0f) {
                    arrayList2.add(objectAnimatorOfFloat2);
                }
            }
            AnimatorSet animatorSet3 = new AnimatorSet();
            animatorSet3.playTogether(arrayList2);
            this.f2075Q = animatorSet3;
            animatorSet3.addListener(new a(this, 1));
            this.f2075Q.start();
        }
        this.f2076R = i2;
    }

    public void setFabCradleMargin(float f2) {
        if (f2 != getFabCradleMargin()) {
            throw null;
        }
    }

    public void setFabCradleRoundedCornerRadius(float f2) {
        if (f2 != getFabCradleRoundedCornerRadius()) {
            throw null;
        }
    }

    public void setFabDiameter(int i2) {
        throw null;
    }

    public void setHideOnScroll(boolean z2) {
        this.f2077S = z2;
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setSubtitle(CharSequence charSequence) {
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setTitle(CharSequence charSequence) {
    }

    public final FloatingActionButton w() {
        if (!(getParent() instanceof CoordinatorLayout)) {
            return null;
        }
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getParent();
        List list = (List) ((k) coordinatorLayout.f1374b.f106b).get(this);
        ArrayList<View> arrayList = coordinatorLayout.f1376d;
        arrayList.clear();
        if (list != null) {
            arrayList.addAll(list);
        }
        for (View view : arrayList) {
            if (view instanceof FloatingActionButton) {
                return (FloatingActionButton) view;
            }
        }
        return null;
    }

    public final int x(int i2) {
        WeakHashMap weakHashMap = l.f2836a;
        boolean z2 = getLayoutDirection() == 1;
        if (i2 == 1) {
            return (getMeasuredWidth() / 2) * (z2 ? -1 : 1);
        }
        return 0;
    }
}
