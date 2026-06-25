package com.google.android.material.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.canvas.CanvasCompat;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.ClampedCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.shape.ShapeableDelegate;

/* JADX INFO: loaded from: classes.dex */
public class MaskableFrameLayout extends FrameLayout implements Maskable, Shapeable {
    private View.OnHoverListener hoverListener;
    private boolean isHovered;
    private final RectF maskRect;
    private float maskXPercentage;
    private Boolean savedForceCompatClippingEnabled;
    private final Rect screenBoundsRect;
    private ShapeAppearanceModel shapeAppearanceModel;
    private final ShapeableDelegate shapeableDelegate;

    public static /* synthetic */ CornerSize $r8$lambda$Agw_urQ8Ww6aiBeJU9RbO6qyqA8(CornerSize cornerSize) {
        return cornerSize instanceof AbsoluteCornerSize ? ClampedCornerSize.createFromCornerSize((AbsoluteCornerSize) cornerSize) : cornerSize;
    }

    public MaskableFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaskableFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.maskXPercentage = -1.0f;
        this.maskRect = new RectF();
        this.screenBoundsRect = new Rect();
        this.shapeableDelegate = ShapeableDelegate.create(this);
        this.savedForceCompatClippingEnabled = null;
        this.isHovered = false;
        setShapeAppearanceModel(ShapeAppearanceModel.builder(context, attributeSet, i, 0, 0).build());
    }

    private void onMaskChanged() {
        this.shapeableDelegate.onMaskChanged(this, this.maskRect);
    }

    private void updateMaskRectForMaskXPercentage() {
        if (this.maskXPercentage != -1.0f) {
            float fLerp = AnimationUtils.lerp(0.0f, getWidth() / 2.0f, 0.0f, 1.0f, this.maskXPercentage);
            setMaskRectF(new RectF(fLerp, 0.0f, getWidth() - fLerp, getHeight()));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        this.shapeableDelegate.maybeClip(canvas, new CanvasCompat.CanvasOperation() { // from class: com.google.android.material.carousel.MaskableFrameLayout$$ExternalSyntheticLambda1
            @Override // com.google.android.material.canvas.CanvasCompat.CanvasOperation
            public final void run(Canvas canvas2) {
                super/*android.widget.FrameLayout*/.dispatchDraw(canvas2);
            }
        });
    }

    @Override // android.view.View
    public void getFocusedRect(Rect rect) {
        RectF rectF = this.maskRect;
        rect.set((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Boolean bool = this.savedForceCompatClippingEnabled;
        if (bool != null) {
            this.shapeableDelegate.setForceCompatClippingEnabled(this, bool.booleanValue());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        this.savedForceCompatClippingEnabled = Boolean.valueOf(this.shapeableDelegate.isForceCompatClippingEnabled());
        this.shapeableDelegate.setForceCompatClippingEnabled(this, true);
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (!this.maskRect.isEmpty() && (action == 9 || action == 10 || action == 7)) {
            if (!this.maskRect.contains(motionEvent.getX(), motionEvent.getY())) {
                if (this.isHovered && this.hoverListener != null) {
                    motionEvent.setAction(10);
                    this.hoverListener.onHover(this, motionEvent);
                }
                this.isHovered = false;
                return false;
            }
        }
        if (this.hoverListener != null) {
            if (!this.isHovered && action == 7) {
                motionEvent.setAction(9);
                this.isHovered = true;
            }
            if (action == 7 || action == 9) {
                this.isHovered = true;
            }
            this.hoverListener.onHover(this, motionEvent);
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.getBoundsInScreen(this.screenBoundsRect);
        if (getX() > 0.0f) {
            this.screenBoundsRect.left = (int) (r0.left + this.maskRect.left);
        }
        if (getY() > 0.0f) {
            this.screenBoundsRect.top = (int) (r0.top + this.maskRect.top);
        }
        Rect rect = this.screenBoundsRect;
        rect.right = rect.left + Math.round(this.maskRect.width());
        Rect rect2 = this.screenBoundsRect;
        rect2.bottom = rect2.top + Math.round(this.maskRect.height());
        accessibilityNodeInfo.setBoundsInScreen(this.screenBoundsRect);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.maskRect.isEmpty()) {
            if (!this.maskRect.contains(motionEvent.getX(), motionEvent.getY())) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.maskXPercentage != -1.0f) {
            updateMaskRectForMaskXPercentage();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.maskRect.isEmpty() && motionEvent.getAction() == 0) {
            if (!this.maskRect.contains(motionEvent.getX(), motionEvent.getY())) {
                return false;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setForceCompatClipping(boolean z) {
        this.shapeableDelegate.setForceCompatClippingEnabled(this, z);
    }

    @Override // com.google.android.material.carousel.Maskable
    public void setMaskRectF(RectF rectF) {
        this.maskRect.set(rectF);
        onMaskChanged();
    }

    @Override // android.view.View
    public void setOnHoverListener(View.OnHoverListener onHoverListener) {
        this.hoverListener = onHoverListener;
    }

    @Override // com.google.android.material.shape.Shapeable
    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        ShapeAppearanceModel shapeAppearanceModelWithTransformedCornerSizes = shapeAppearanceModel.withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator() { // from class: com.google.android.material.carousel.MaskableFrameLayout$$ExternalSyntheticLambda0
            @Override // com.google.android.material.shape.ShapeAppearanceModel.CornerSizeUnaryOperator
            public final CornerSize apply(CornerSize cornerSize) {
                return MaskableFrameLayout.$r8$lambda$Agw_urQ8Ww6aiBeJU9RbO6qyqA8(cornerSize);
            }
        });
        this.shapeAppearanceModel = shapeAppearanceModelWithTransformedCornerSizes;
        this.shapeableDelegate.onShapeAppearanceChanged(this, shapeAppearanceModelWithTransformedCornerSizes);
    }
}
