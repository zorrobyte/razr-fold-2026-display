package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.leanback.R$dimen;

/* JADX INFO: loaded from: classes.dex */
public class ShadowOverlayContainer extends FrameLayout {
    private static final Rect sTempRect = new Rect();
    private float mFocusedZ;
    private boolean mInitialized;
    int mOverlayColor;
    private Paint mOverlayPaint;
    private int mShadowType;
    private float mUnfocusedZ;
    private View mWrappedView;

    public ShadowOverlayContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShadowOverlayContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShadowType = 1;
        useStaticShadow();
        useDynamicShadow();
    }

    public static boolean supportsDynamicShadow() {
        return ShadowHelper.supportsDynamicShadow();
    }

    public static boolean supportsShadow() {
        return StaticShadowHelper.supportsShadow();
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mOverlayPaint == null || this.mOverlayColor == 0) {
            return;
        }
        canvas.drawRect(this.mWrappedView.getLeft(), this.mWrappedView.getTop(), this.mWrappedView.getRight(), this.mWrappedView.getBottom(), this.mOverlayPaint);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View view;
        super.onLayout(z, i, i2, i3, i4);
        if (!z || (view = this.mWrappedView) == null) {
            return;
        }
        Rect rect = sTempRect;
        rect.left = (int) view.getPivotX();
        rect.top = (int) this.mWrappedView.getPivotY();
        offsetDescendantRectToMyCoords(this.mWrappedView, rect);
        setPivotX(rect.left);
        setPivotY(rect.top);
    }

    public void useDynamicShadow() {
        useDynamicShadow(getResources().getDimension(R$dimen.lb_material_shadow_normal_z), getResources().getDimension(R$dimen.lb_material_shadow_focused_z));
    }

    public void useDynamicShadow(float f, float f2) {
        if (this.mInitialized) {
            throw new IllegalStateException("Already initialized");
        }
        if (supportsDynamicShadow()) {
            this.mShadowType = 3;
            this.mUnfocusedZ = f;
            this.mFocusedZ = f2;
        }
    }

    public void useStaticShadow() {
        if (this.mInitialized) {
            throw new IllegalStateException("Already initialized");
        }
        if (supportsShadow()) {
            this.mShadowType = 2;
        }
    }
}
