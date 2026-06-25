package com.android.systemui.lifecycle;

import android.view.View;
import android.view.ViewTreeObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/* JADX INFO: compiled from: RepeatWhenAttached.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ViewLifecycleOwner implements LifecycleOwner {
    private final LifecycleRegistry registry;
    private final View view;
    private final ViewTreeObserver.OnWindowFocusChangeListener windowFocusListener;
    private final ViewTreeObserver.OnWindowVisibilityChangeListener windowVisibleListener;

    public ViewLifecycleOwner(View view) {
        view.getClass();
        this.view = view;
        this.windowVisibleListener = new ViewTreeObserver.OnWindowVisibilityChangeListener() { // from class: com.android.systemui.lifecycle.ViewLifecycleOwner$windowVisibleListener$1
            @Override // android.view.ViewTreeObserver.OnWindowVisibilityChangeListener
            public final void onWindowVisibilityChanged(int i) {
                this.this$0.updateState();
            }
        };
        this.windowFocusListener = new ViewTreeObserver.OnWindowFocusChangeListener() { // from class: com.android.systemui.lifecycle.ViewLifecycleOwner$windowFocusListener$1
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                this.this$0.updateState();
            }
        };
        this.registry = new LifecycleRegistry(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateState() {
        this.registry.setCurrentState(this.view.getWindowVisibility() != 0 ? Lifecycle.State.CREATED : !this.view.hasWindowFocus() ? Lifecycle.State.STARTED : Lifecycle.State.RESUMED);
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.registry;
    }

    public final void onCreate() {
        this.registry.setCurrentState(Lifecycle.State.CREATED);
        this.view.getViewTreeObserver().addOnWindowVisibilityChangeListener(this.windowVisibleListener);
        this.view.getViewTreeObserver().addOnWindowFocusChangeListener(this.windowFocusListener);
        updateState();
    }

    public final void onDestroy() {
        this.view.getViewTreeObserver().removeOnWindowVisibilityChangeListener(this.windowVisibleListener);
        this.view.getViewTreeObserver().removeOnWindowFocusChangeListener(this.windowFocusListener);
        this.registry.setCurrentState(Lifecycle.State.DESTROYED);
    }
}
