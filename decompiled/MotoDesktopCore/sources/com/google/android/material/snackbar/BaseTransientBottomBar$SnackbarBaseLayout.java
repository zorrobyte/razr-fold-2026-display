package com.google.android.material.snackbar;

import U.a;
import U.b;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import com.google.android.material.R$styleable;
import e0.k;
import java.util.WeakHashMap;
import v.l;
import w.AccessibilityManagerTouchExplorationStateChangeListenerC0162a;

/* JADX INFO: loaded from: classes.dex */
public class BaseTransientBottomBar$SnackbarBaseLayout extends FrameLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final AccessibilityManager f2188a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final k f2189b;

    public BaseTransientBottomBar$SnackbarBaseLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SnackbarLayout);
        if (typedArrayObtainStyledAttributes.hasValue(R$styleable.SnackbarLayout_elevation)) {
            float dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SnackbarLayout_elevation, 0);
            WeakHashMap weakHashMap = l.f2836a;
            setElevation(dimensionPixelSize);
        }
        typedArrayObtainStyledAttributes.recycle();
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        this.f2188a = accessibilityManager;
        k kVar = new k(this);
        this.f2189b = kVar;
        accessibilityManager.addTouchExplorationStateChangeListener(new AccessibilityManagerTouchExplorationStateChangeListenerC0162a(kVar));
        setClickableOrFocusableBasedOnAccessibility(accessibilityManager.isTouchExplorationEnabled());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setClickableOrFocusableBasedOnAccessibility(boolean z2) {
        setClickable(!z2);
        setFocusable(z2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        WeakHashMap weakHashMap = l.f2836a;
        requestApplyInsets();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        k kVar = this.f2189b;
        if (kVar == null) {
            return;
        }
        this.f2188a.removeTouchExplorationStateChangeListener(new AccessibilityManagerTouchExplorationStateChangeListenerC0162a(kVar));
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
    }

    public void setOnAttachStateChangeListener(a aVar) {
    }

    public void setOnLayoutChangeListener(b bVar) {
    }
}
