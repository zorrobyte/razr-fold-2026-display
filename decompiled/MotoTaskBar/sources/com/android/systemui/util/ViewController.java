package com.android.systemui.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class ViewController {
    private boolean mInited;
    private View.OnAttachStateChangeListener mOnAttachStateListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.util.ViewController.1
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
            ViewController.this.onViewAttached();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            ViewController.this.onViewDetached();
        }
    };
    protected final View mView;

    protected ViewController(View view) {
        this.mView = view;
    }

    public void addOnAttachStateChangeListener(View.OnAttachStateChangeListener onAttachStateChangeListener) {
        View view = this.mView;
        if (view != null) {
            view.addOnAttachStateChangeListener(onAttachStateChangeListener);
        }
    }

    protected Context getContext() {
        return this.mView.getContext();
    }

    protected Resources getResources() {
        return this.mView.getResources();
    }

    public void init() {
        if (this.mInited) {
            return;
        }
        onInit();
        this.mInited = true;
        if (isAttachedToWindow()) {
            this.mOnAttachStateListener.onViewAttachedToWindow(this.mView);
        }
        addOnAttachStateChangeListener(this.mOnAttachStateListener);
    }

    public boolean isAttachedToWindow() {
        View view = this.mView;
        return view != null && view.isAttachedToWindow();
    }

    protected abstract void onInit();

    protected abstract void onViewAttached();

    protected abstract void onViewDetached();
}
