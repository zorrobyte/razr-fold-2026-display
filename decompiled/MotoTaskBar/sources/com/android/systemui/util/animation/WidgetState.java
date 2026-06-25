package com.android.systemui.util.animation;

import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TransitionLayout.kt */
/* JADX INFO: loaded from: classes.dex */
public final class WidgetState {
    private float alpha;
    private boolean gone;
    private int height;
    private int measureHeight;
    private int measureWidth;
    private float scale;
    private int width;
    private float x;
    private float y;

    public WidgetState(float f, float f2, int i, int i2, int i3, int i4, float f3, float f4, boolean z) {
        this.x = f;
        this.y = f2;
        this.width = i;
        this.height = i2;
        this.measureWidth = i3;
        this.measureHeight = i4;
        this.alpha = f3;
        this.scale = f4;
        this.gone = z;
    }

    public /* synthetic */ WidgetState(float f, float f2, int i, int i2, int i3, int i4, float f3, float f4, boolean z, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? 0.0f : f, (i5 & 2) != 0 ? 0.0f : f2, (i5 & 4) != 0 ? 0 : i, (i5 & 8) != 0 ? 0 : i2, (i5 & 16) != 0 ? 0 : i3, (i5 & 32) != 0 ? 0 : i4, (i5 & 64) != 0 ? 1.0f : f3, (i5 & 128) != 0 ? 1.0f : f4, (i5 & 256) != 0 ? false : z);
    }

    public static /* synthetic */ WidgetState copy$default(WidgetState widgetState, float f, float f2, int i, int i2, int i3, int i4, float f3, float f4, boolean z, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            f = widgetState.x;
        }
        if ((i5 & 2) != 0) {
            f2 = widgetState.y;
        }
        if ((i5 & 4) != 0) {
            i = widgetState.width;
        }
        if ((i5 & 8) != 0) {
            i2 = widgetState.height;
        }
        if ((i5 & 16) != 0) {
            i3 = widgetState.measureWidth;
        }
        if ((i5 & 32) != 0) {
            i4 = widgetState.measureHeight;
        }
        if ((i5 & 64) != 0) {
            f3 = widgetState.alpha;
        }
        if ((i5 & 128) != 0) {
            f4 = widgetState.scale;
        }
        if ((i5 & 256) != 0) {
            z = widgetState.gone;
        }
        float f5 = f4;
        boolean z2 = z;
        int i6 = i4;
        float f6 = f3;
        int i7 = i3;
        int i8 = i;
        return widgetState.copy(f, f2, i8, i2, i7, i6, f6, f5, z2);
    }

    public final WidgetState copy(float f, float f2, int i, int i2, int i3, int i4, float f3, float f4, boolean z) {
        return new WidgetState(f, f2, i, i2, i3, i4, f3, f4, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WidgetState)) {
            return false;
        }
        WidgetState widgetState = (WidgetState) obj;
        return Float.compare(this.x, widgetState.x) == 0 && Float.compare(this.y, widgetState.y) == 0 && this.width == widgetState.width && this.height == widgetState.height && this.measureWidth == widgetState.measureWidth && this.measureHeight == widgetState.measureHeight && Float.compare(this.alpha, widgetState.alpha) == 0 && Float.compare(this.scale, widgetState.scale) == 0 && this.gone == widgetState.gone;
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final boolean getGone() {
        return this.gone;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getMeasureHeight() {
        return this.measureHeight;
    }

    public final int getMeasureWidth() {
        return this.measureWidth;
    }

    public final float getScale() {
        return this.scale;
    }

    public final int getWidth() {
        return this.width;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public int hashCode() {
        return (((((((((((((((Float.hashCode(this.x) * 31) + Float.hashCode(this.y)) * 31) + Integer.hashCode(this.width)) * 31) + Integer.hashCode(this.height)) * 31) + Integer.hashCode(this.measureWidth)) * 31) + Integer.hashCode(this.measureHeight)) * 31) + Float.hashCode(this.alpha)) * 31) + Float.hashCode(this.scale)) * 31) + Boolean.hashCode(this.gone);
    }

    public final void initFromLayout(View view) {
        view.getClass();
        boolean z = view.getVisibility() == 8;
        this.gone = z;
        if (!z) {
            this.x = view.getLeft();
            this.y = view.getTop();
            this.width = view.getWidth();
            int height = view.getHeight();
            this.height = height;
            this.measureWidth = this.width;
            this.measureHeight = height;
            this.gone = view.getVisibility() == 8;
            this.alpha = view.getAlpha();
            this.scale = 1.0f;
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.getClass();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
        this.x = layoutParams2.getConstraintWidget().getLeft();
        this.y = layoutParams2.getConstraintWidget().getTop();
        this.width = layoutParams2.getConstraintWidget().getWidth();
        int height2 = layoutParams2.getConstraintWidget().getHeight();
        this.height = height2;
        this.measureHeight = height2;
        this.measureWidth = this.width;
        this.alpha = 0.0f;
        this.scale = 0.0f;
    }

    public final void setAlpha(float f) {
        this.alpha = f;
    }

    public final void setGone(boolean z) {
        this.gone = z;
    }

    public final void setHeight(int i) {
        this.height = i;
    }

    public final void setMeasureHeight(int i) {
        this.measureHeight = i;
    }

    public final void setMeasureWidth(int i) {
        this.measureWidth = i;
    }

    public final void setScale(float f) {
        this.scale = f;
    }

    public final void setWidth(int i) {
        this.width = i;
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final void setY(float f) {
        this.y = f;
    }

    public String toString() {
        return "WidgetState(x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", measureWidth=" + this.measureWidth + ", measureHeight=" + this.measureHeight + ", alpha=" + this.alpha + ", scale=" + this.scale + ", gone=" + this.gone + ")";
    }
}
