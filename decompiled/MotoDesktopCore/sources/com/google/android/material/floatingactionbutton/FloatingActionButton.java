package com.google.android.material.floatingactionbutton;

import C.h;
import H.c;
import N.a;
import O.b;
import O.d;
import O.e;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.C0082t;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.R$animator;
import com.google.android.material.R$dimen;
import com.google.android.material.R$styleable;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.internal.VisibilityAwareImageButton;
import com.google.android.material.stateful.ExtendableSavedState;
import e0.k;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;
import k.AbstractC0143b;
import k.C0146e;
import k.InterfaceC0144c;
import v.l;

/* JADX INFO: loaded from: classes.dex */
@InterfaceC0144c(Behavior.class)
public class FloatingActionButton extends VisibilityAwareImageButton implements a {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ColorStateList f2151b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public PorterDuff.Mode f2152c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public ColorStateList f2153d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public PorterDuff.Mode f2154e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public ColorStateList f2155f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f2156g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f2157h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f2158i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public e f2159j;

    public static class BaseBehavior<T extends FloatingActionButton> extends AbstractC0143b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public Rect f2160a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final boolean f2161b;

        public BaseBehavior() {
            this.f2161b = true;
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.FloatingActionButton_Behavior_Layout);
            this.f2161b = typedArrayObtainStyledAttributes.getBoolean(R$styleable.FloatingActionButton_Behavior_Layout_behavior_autoHide, true);
            typedArrayObtainStyledAttributes.recycle();
        }

        @Override // k.AbstractC0143b
        public final boolean a(View view) {
            ((FloatingActionButton) view).getLeft();
            throw null;
        }

        @Override // k.AbstractC0143b
        public final void c(C0146e c0146e) {
            if (c0146e.f2753h == 0) {
                c0146e.f2753h = 80;
            }
        }

        @Override // k.AbstractC0143b
        public final boolean d(CoordinatorLayout coordinatorLayout, View view, View view2) {
            FloatingActionButton floatingActionButton = (FloatingActionButton) view;
            if (view2 instanceof AppBarLayout) {
                s(coordinatorLayout, (AppBarLayout) view2, floatingActionButton);
            } else {
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                if (layoutParams instanceof C0146e ? ((C0146e) layoutParams).f2746a instanceof BottomSheetBehavior : false) {
                    t(view2, floatingActionButton);
                }
            }
            return false;
        }

        @Override // k.AbstractC0143b
        public final boolean f(CoordinatorLayout coordinatorLayout, View view, int i2) {
            FloatingActionButton floatingActionButton = (FloatingActionButton) view;
            ArrayList arrayListI = coordinatorLayout.i(floatingActionButton);
            int size = arrayListI.size();
            for (int i3 = 0; i3 < size; i3++) {
                View view2 = (View) arrayListI.get(i3);
                if (!(view2 instanceof AppBarLayout)) {
                    ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                    if ((layoutParams instanceof C0146e ? ((C0146e) layoutParams).f2746a instanceof BottomSheetBehavior : false) && t(view2, floatingActionButton)) {
                        break;
                    }
                } else {
                    if (s(coordinatorLayout, (AppBarLayout) view2, floatingActionButton)) {
                        break;
                    }
                }
            }
            coordinatorLayout.p(floatingActionButton, i2);
            return true;
        }

        public final boolean s(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, FloatingActionButton floatingActionButton) {
            if (!(this.f2161b && ((C0146e) floatingActionButton.getLayoutParams()).f2751f == appBarLayout.getId() && floatingActionButton.getUserSetVisibility() == 0)) {
                return false;
            }
            if (this.f2160a == null) {
                this.f2160a = new Rect();
            }
            Rect rect = this.f2160a;
            P.a.a(coordinatorLayout, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                floatingActionButton.g();
            } else {
                floatingActionButton.l();
            }
            return true;
        }

        public final boolean t(View view, FloatingActionButton floatingActionButton) {
            if (!(this.f2161b && ((C0146e) floatingActionButton.getLayoutParams()).f2751f == view.getId() && floatingActionButton.getUserSetVisibility() == 0)) {
                return false;
            }
            if (view.getTop() < (floatingActionButton.getHeight() / 2) + ((ViewGroup.MarginLayoutParams) ((C0146e) floatingActionButton.getLayoutParams())).topMargin) {
                floatingActionButton.g();
            } else {
                floatingActionButton.l();
            }
            return true;
        }
    }

    public static class Behavior extends BaseBehavior<FloatingActionButton> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    private d getImpl() {
        if (this.f2159j == null) {
            this.f2159j = new e(this, new k(this));
        }
        return this.f2159j;
    }

    public final void c() {
        d impl = getImpl();
        if (impl.f226m == null) {
            impl.f226m = new ArrayList();
        }
        impl.f226m.add(null);
    }

    public final void d() {
        d impl = getImpl();
        if (impl.f225l == null) {
            impl.f225l = new ArrayList();
        }
        impl.f225l.add(null);
    }

    @Override // android.widget.ImageView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        getImpl().g(getDrawableState());
    }

    public final void e(Rect rect) {
        WeakHashMap weakHashMap = l.f2836a;
        if (isLaidOut()) {
            rect.set(0, 0, getWidth(), getHeight());
            throw null;
        }
    }

    public final int f(int i2) {
        int i3 = this.f2157h;
        if (i3 != 0) {
            return i3;
        }
        Resources resources = getResources();
        return i2 != -1 ? i2 != 1 ? resources.getDimensionPixelSize(R$dimen.design_fab_size_normal) : resources.getDimensionPixelSize(R$dimen.design_fab_size_mini) : Math.max(resources.getConfiguration().screenWidthDp, resources.getConfiguration().screenHeightDp) < 470 ? f(1) : f(0);
    }

    public final void g() {
        d impl = getImpl();
        VisibilityAwareImageButton visibilityAwareImageButton = impl.f227n;
        if (visibilityAwareImageButton.getVisibility() == 0) {
            if (impl.f214a == 1) {
                return;
            }
        } else if (impl.f214a != 2) {
            return;
        }
        Animator animator = impl.f215b;
        if (animator != null) {
            animator.cancel();
        }
        WeakHashMap weakHashMap = l.f2836a;
        VisibilityAwareImageButton visibilityAwareImageButton2 = impl.f227n;
        if (!visibilityAwareImageButton2.isLaidOut() || visibilityAwareImageButton2.isInEditMode()) {
            visibilityAwareImageButton.a(4, false);
            return;
        }
        c cVar = impl.f217d;
        if (cVar == null) {
            if (impl.f219f == null) {
                impl.f219f = c.a(visibilityAwareImageButton.getContext(), R$animator.design_fab_hide_motion_spec);
            }
            cVar = impl.f219f;
        }
        AnimatorSet animatorSetA = impl.a(cVar, 0.0f, 0.0f, 0.0f);
        animatorSetA.addListener(new O.a(impl));
        ArrayList arrayList = impl.f226m;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                animatorSetA.addListener((Animator.AnimatorListener) it.next());
            }
        }
        animatorSetA.start();
    }

    @Override // android.view.View
    public ColorStateList getBackgroundTintList() {
        return this.f2151b;
    }

    @Override // android.view.View
    public PorterDuff.Mode getBackgroundTintMode() {
        return this.f2152c;
    }

    public float getCompatElevation() {
        return getImpl().c();
    }

    public float getCompatHoveredFocusedTranslationZ() {
        return getImpl().f222i;
    }

    public float getCompatPressedTranslationZ() {
        return getImpl().f223j;
    }

    public Drawable getContentBackground() {
        getImpl().getClass();
        return null;
    }

    public int getCustomSize() {
        return this.f2157h;
    }

    public int getExpandedComponentIdHint() {
        throw null;
    }

    public c getHideMotionSpec() {
        return getImpl().f217d;
    }

    @Deprecated
    public int getRippleColor() {
        ColorStateList colorStateList = this.f2155f;
        if (colorStateList != null) {
            return colorStateList.getDefaultColor();
        }
        return 0;
    }

    public ColorStateList getRippleColorStateList() {
        return this.f2155f;
    }

    public c getShowMotionSpec() {
        return getImpl().f216c;
    }

    public int getSize() {
        return this.f2156g;
    }

    public int getSizeDimension() {
        return f(this.f2156g);
    }

    public ColorStateList getSupportBackgroundTintList() {
        return getBackgroundTintList();
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return getBackgroundTintMode();
    }

    public ColorStateList getSupportImageTintList() {
        return this.f2153d;
    }

    public PorterDuff.Mode getSupportImageTintMode() {
        return this.f2154e;
    }

    public boolean getUseCompatPadding() {
        return this.f2158i;
    }

    public final boolean h() {
        d impl = getImpl();
        if (impl.f227n.getVisibility() != 0) {
            if (impl.f214a != 2) {
                return false;
            }
        } else if (impl.f214a == 1) {
            return false;
        }
        return true;
    }

    public final void i() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        ColorStateList colorStateList = this.f2153d;
        if (colorStateList == null) {
            drawable.clearColorFilter();
            return;
        }
        int colorForState = colorStateList.getColorForState(getDrawableState(), 0);
        PorterDuff.Mode mode = this.f2154e;
        if (mode == null) {
            mode = PorterDuff.Mode.SRC_IN;
        }
        drawable.mutate().setColorFilter(C0082t.j(colorForState, mode));
    }

    public final void j() {
        ArrayList arrayList = getImpl().f226m;
        if (arrayList == null) {
            return;
        }
        arrayList.remove((Object) null);
    }

    @Override // android.widget.ImageView, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        getImpl().e();
    }

    public final void k() {
        ArrayList arrayList = getImpl().f225l;
        if (arrayList == null) {
            return;
        }
        arrayList.remove((Object) null);
    }

    public final void l() {
        d impl = getImpl();
        if (impl.f227n.getVisibility() != 0) {
            if (impl.f214a == 2) {
                return;
            }
        } else if (impl.f214a != 1) {
            return;
        }
        Animator animator = impl.f215b;
        if (animator != null) {
            animator.cancel();
        }
        WeakHashMap weakHashMap = l.f2836a;
        VisibilityAwareImageButton visibilityAwareImageButton = impl.f227n;
        boolean z2 = visibilityAwareImageButton.isLaidOut() && !visibilityAwareImageButton.isInEditMode();
        Matrix matrix = impl.f230q;
        if (!z2) {
            visibilityAwareImageButton.a(0, false);
            visibilityAwareImageButton.setAlpha(1.0f);
            visibilityAwareImageButton.setScaleY(1.0f);
            visibilityAwareImageButton.setScaleX(1.0f);
            impl.f224k = 1.0f;
            matrix.reset();
            visibilityAwareImageButton.getDrawable();
            visibilityAwareImageButton.setImageMatrix(matrix);
            return;
        }
        if (visibilityAwareImageButton.getVisibility() != 0) {
            visibilityAwareImageButton.setAlpha(0.0f);
            visibilityAwareImageButton.setScaleY(0.0f);
            visibilityAwareImageButton.setScaleX(0.0f);
            impl.f224k = 0.0f;
            matrix.reset();
            visibilityAwareImageButton.getDrawable();
            visibilityAwareImageButton.setImageMatrix(matrix);
        }
        c cVar = impl.f216c;
        if (cVar == null) {
            if (impl.f218e == null) {
                impl.f218e = c.a(visibilityAwareImageButton.getContext(), R$animator.design_fab_show_motion_spec);
            }
            cVar = impl.f218e;
        }
        AnimatorSet animatorSetA = impl.a(cVar, 1.0f, 1.0f, 1.0f);
        animatorSetA.addListener(new h(impl));
        ArrayList arrayList = impl.f225l;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                animatorSetA.addListener((Animator.AnimatorListener) it.next());
            }
        }
        animatorSetA.start();
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        d impl = getImpl();
        impl.getClass();
        if (!(impl instanceof e)) {
            if (impl.r == null) {
                impl.r = new b(0, impl);
            }
            impl.f227n.getViewTreeObserver().addOnPreDrawListener(impl.r);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        d impl = getImpl();
        if (impl.r != null) {
            impl.f227n.getViewTreeObserver().removeOnPreDrawListener(impl.r);
            impl.r = null;
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onMeasure(int i2, int i3) {
        getSizeDimension();
        getImpl().j();
        throw null;
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof ExtendableSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        ExtendableSavedState extendableSavedState = (ExtendableSavedState) parcelable;
        super.onRestoreInstanceState(extendableSavedState.f1465a);
        throw null;
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        new ExtendableSavedState(super.onSaveInstanceState());
        throw null;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            e(null);
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i2) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    @Override // android.view.View
    public void setBackgroundResource(int i2) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    @Override // android.view.View
    public void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.f2151b != colorStateList) {
            this.f2151b = colorStateList;
            getImpl().getClass();
        }
    }

    @Override // android.view.View
    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.f2152c != mode) {
            this.f2152c = mode;
            getImpl().getClass();
        }
    }

    public void setCompatElevation(float f2) {
        d impl = getImpl();
        if (impl.f221h != f2) {
            impl.f221h = f2;
            impl.h(f2, impl.f222i, impl.f223j);
        }
    }

    public void setCompatElevationResource(int i2) {
        setCompatElevation(getResources().getDimension(i2));
    }

    public void setCompatHoveredFocusedTranslationZ(float f2) {
        d impl = getImpl();
        if (impl.f222i != f2) {
            impl.f222i = f2;
            impl.h(impl.f221h, f2, impl.f223j);
        }
    }

    public void setCompatHoveredFocusedTranslationZResource(int i2) {
        setCompatHoveredFocusedTranslationZ(getResources().getDimension(i2));
    }

    public void setCompatPressedTranslationZ(float f2) {
        d impl = getImpl();
        if (impl.f223j != f2) {
            impl.f223j = f2;
            impl.h(impl.f221h, impl.f222i, f2);
        }
    }

    public void setCompatPressedTranslationZResource(int i2) {
        setCompatPressedTranslationZ(getResources().getDimension(i2));
    }

    public void setCustomSize(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Custom size must be non-negative");
        }
        this.f2157h = i2;
    }

    public void setExpandedComponentIdHint(int i2) {
        throw null;
    }

    public void setHideMotionSpec(c cVar) {
        getImpl().f217d = cVar;
    }

    public void setHideMotionSpecResource(int i2) {
        setHideMotionSpec(c.a(getContext(), i2));
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        d impl = getImpl();
        impl.f224k = impl.f224k;
        Matrix matrix = impl.f230q;
        matrix.reset();
        VisibilityAwareImageButton visibilityAwareImageButton = impl.f227n;
        visibilityAwareImageButton.getDrawable();
        visibilityAwareImageButton.setImageMatrix(matrix);
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i2) {
        throw null;
    }

    public void setRippleColor(int i2) {
        setRippleColor(ColorStateList.valueOf(i2));
    }

    public void setRippleColor(ColorStateList colorStateList) {
        if (this.f2155f != colorStateList) {
            this.f2155f = colorStateList;
            getImpl().getClass();
        }
    }

    public void setShowMotionSpec(c cVar) {
        getImpl().f216c = cVar;
    }

    public void setShowMotionSpecResource(int i2) {
        setShowMotionSpec(c.a(getContext(), i2));
    }

    public void setSize(int i2) {
        this.f2157h = 0;
        if (i2 != this.f2156g) {
            this.f2156g = i2;
            requestLayout();
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        setBackgroundTintList(colorStateList);
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        setBackgroundTintMode(mode);
    }

    public void setSupportImageTintList(ColorStateList colorStateList) {
        if (this.f2153d != colorStateList) {
            this.f2153d = colorStateList;
            i();
        }
    }

    public void setSupportImageTintMode(PorterDuff.Mode mode) {
        if (this.f2154e != mode) {
            this.f2154e = mode;
            i();
        }
    }

    public void setUseCompatPadding(boolean z2) {
        if (this.f2158i != z2) {
            this.f2158i = z2;
            getImpl().f();
        }
    }
}
