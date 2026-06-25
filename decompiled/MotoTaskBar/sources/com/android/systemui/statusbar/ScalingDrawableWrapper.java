package com.android.systemui.statusbar;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;

/* JADX INFO: loaded from: classes.dex */
public class ScalingDrawableWrapper extends DrawableWrapper {
    private float mScaleFactor;

    public ScalingDrawableWrapper(Drawable drawable, float f) {
        super(drawable);
        this.mScaleFactor = f;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return (int) (super.getIntrinsicHeight() * this.mScaleFactor);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return (int) (super.getIntrinsicWidth() * this.mScaleFactor);
    }
}
