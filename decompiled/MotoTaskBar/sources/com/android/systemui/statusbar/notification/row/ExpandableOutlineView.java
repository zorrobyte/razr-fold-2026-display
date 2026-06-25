package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.android.systemui.res.R$bool;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.shared.NotificationsImprovedHunAnimation;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;

/* JADX INFO: loaded from: classes.dex */
public abstract class ExpandableOutlineView extends ExpandableView {
    private static final Path EMPTY_PATH = new Path();
    private boolean mAlwaysRoundBothCorners;
    private boolean mCustomOutline;
    protected boolean mDismissUsingRowTranslationX;
    private float mOutlineAlpha;
    private final Rect mOutlineRect;
    private final ViewOutlineProvider mProvider;
    private RoundableState mRoundableState;
    private float[] mTmpCornerRadii;
    private Path mTmpPath;

    public ExpandableOutlineView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOutlineRect = new Rect();
        this.mOutlineAlpha = -1.0f;
        this.mTmpPath = new Path();
        this.mDismissUsingRowTranslationX = true;
        this.mTmpCornerRadii = new float[8];
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: com.android.systemui.statusbar.notification.row.ExpandableOutlineView.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                if (ExpandableOutlineView.this.mCustomOutline || ExpandableOutlineView.this.hasRoundedCorner() || ExpandableOutlineView.this.mAlwaysRoundBothCorners) {
                    Path clipPath = ExpandableOutlineView.this.getClipPath(false);
                    if (clipPath != null) {
                        outline.setPath(clipPath);
                    }
                } else {
                    ExpandableOutlineView expandableOutlineView = ExpandableOutlineView.this;
                    int translation = !expandableOutlineView.mDismissUsingRowTranslationX ? (int) expandableOutlineView.getTranslation() : 0;
                    int iMax = Math.max(translation, 0);
                    ExpandableOutlineView expandableOutlineView2 = ExpandableOutlineView.this;
                    int i = expandableOutlineView2.mClipTopAmount;
                    outline.setRect(iMax, i, expandableOutlineView2.getWidth() + Math.min(translation, 0), Math.max(ExpandableOutlineView.this.getActualHeight() - ExpandableOutlineView.this.mClipBottomAmount, i));
                }
                outline.setAlpha(ExpandableOutlineView.this.mOutlineAlpha);
            }
        };
        this.mProvider = viewOutlineProvider;
        setOutlineProvider(viewOutlineProvider);
        initDimens();
    }

    private void initDimens() {
        Resources resources = getResources();
        boolean z = resources.getBoolean(R$bool.config_clipNotificationsToOutline);
        this.mAlwaysRoundBothCorners = z;
        float dimension = z ? resources.getDimension(R$dimen.notification_shadow_radius) : resources.getDimensionPixelSize(R$dimen.notification_corner_radius);
        RoundableState roundableState = this.mRoundableState;
        if (roundableState == null) {
            this.mRoundableState = new RoundableState(this, this, dimension);
        } else {
            roundableState.setMaxRadius(dimension);
        }
        setClipToOutline(this.mAlwaysRoundBothCorners);
    }

    public void applyRoundnessAndInvalidate() {
        invalidateOutline();
        super.applyRoundnessAndInvalidate();
    }

    protected boolean childNeedsClipping(View view) {
        return false;
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        Path customClipPath;
        canvas.save();
        Path path = null;
        if (childNeedsClipping(view)) {
            customClipPath = getCustomClipPath(view);
            if (customClipPath == null) {
                customClipPath = getClipPath(false);
            }
            if (this.mDismissUsingRowTranslationX && (view instanceof NotificationChildrenContainer)) {
                path = customClipPath;
                customClipPath = null;
            }
        } else {
            customClipPath = null;
        }
        if (view instanceof NotificationChildrenContainer) {
            ((NotificationChildrenContainer) view).setChildClipPath(path);
        }
        if (customClipPath != null) {
            canvas.clipPath(customClipPath);
        }
        boolean zDrawChild = super.drawChild(canvas, view, j);
        canvas.restore();
        return zDrawChild;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.statusbar.notification.Roundable
    public int getClipHeight() {
        return this.mCustomOutline ? this.mOutlineRect.height() : super.getClipHeight();
    }

    protected Path getClipPath(boolean z) {
        int iMax;
        int i;
        int width;
        int iMax2;
        float maxRadius = this.mAlwaysRoundBothCorners ? getMaxRadius() : getTopCornerRadius();
        if (this.mCustomOutline) {
            Rect rect = this.mOutlineRect;
            iMax = rect.left;
            i = rect.top;
            width = rect.right;
            iMax2 = rect.bottom;
        } else {
            int translation = (this.mDismissUsingRowTranslationX || z) ? 0 : (int) getTranslation();
            int i2 = (int) (this.mExtraWidthForClipping / 2.0f);
            iMax = Math.max(translation, 0) - i2;
            i = this.mClipTopAmount;
            width = getWidth() + i2 + Math.min(translation, 0);
            iMax2 = Math.max(this.mMinimumHeightForClipping, Math.max(getActualHeight() - this.mClipBottomAmount, (int) (i + maxRadius)));
        }
        int i3 = iMax2;
        int i4 = iMax;
        int i5 = i;
        int i6 = width;
        int i7 = i3 - i5;
        if (i7 == 0) {
            return EMPTY_PATH;
        }
        float maxRadius2 = this.mAlwaysRoundBothCorners ? getMaxRadius() : getBottomCornerRadius();
        if (!NotificationsImprovedHunAnimation.isEnabled()) {
            float f = maxRadius + maxRadius2;
            float f2 = i7;
            if (f > f2) {
                float f3 = f - f2;
                float topRoundness = getTopRoundness();
                float bottomRoundness = getBottomRoundness();
                float f4 = f3 * topRoundness;
                float f5 = topRoundness + bottomRoundness;
                maxRadius -= f4 / f5;
                maxRadius2 -= (f3 * bottomRoundness) / f5;
            }
        }
        getRoundedRectPath(i4, i5, i6, i3, maxRadius, maxRadius2, this.mTmpPath);
        return this.mTmpPath;
    }

    public Path getCustomClipPath(View view) {
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public float getOutlineAlpha() {
        return this.mOutlineAlpha;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public int getOutlineTranslation() {
        if (this.mCustomOutline) {
            return this.mOutlineRect.left;
        }
        if (this.mDismissUsingRowTranslationX) {
            return 0;
        }
        return (int) getTranslation();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.statusbar.notification.Roundable
    public RoundableState getRoundableState() {
        return this.mRoundableState;
    }

    public void getRoundedRectPath(int i, int i2, int i3, int i4, float f, float f2, Path path) {
        path.reset();
        float[] fArr = this.mTmpCornerRadii;
        fArr[0] = f;
        fArr[1] = f;
        fArr[2] = f;
        fArr[3] = f;
        fArr[4] = f2;
        fArr[5] = f2;
        fArr[6] = f2;
        fArr[7] = f2;
        path.addRoundRect(i, i2, i3, i4, fArr, Path.Direction.CW);
    }

    protected boolean isClippingNeeded() {
        return this.mAlwaysRoundBothCorners || this.mCustomOutline || ((getTranslation() > 0.0f ? 1 : (getTranslation() == 0.0f ? 0 : -1)) != 0 && !this.mDismissUsingRowTranslationX);
    }

    protected boolean needsOutline() {
        return isChildInGroup() ? isGroupExpanded() && !isGroupExpansionChanging() : (isSummaryWithChildren() && isGroupExpanded() && !isGroupExpansionChanging()) ? false : true;
    }

    public void onDensityOrFontScaleChanged() {
        initDimens();
        applyRoundnessAndInvalidate();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setActualHeight(int i, boolean z) {
        int actualHeight = getActualHeight();
        super.setActualHeight(i, z);
        if (actualHeight != i) {
            applyRoundnessAndInvalidate();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setClipBottomAmount(int i) {
        int clipBottomAmount = getClipBottomAmount();
        super.setClipBottomAmount(i);
        if (clipBottomAmount != i) {
            applyRoundnessAndInvalidate();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setClipTopAmount(int i) {
        int clipTopAmount = getClipTopAmount();
        super.setClipTopAmount(i);
        if (clipTopAmount != i) {
            applyRoundnessAndInvalidate();
        }
    }

    public void setDismissUsingRowTranslationX(boolean z) {
        this.mDismissUsingRowTranslationX = z;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setExtraWidthForClipping(float f) {
        super.setExtraWidthForClipping(f);
        invalidate();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setMinimumHeightForClipping(int i) {
        super.setMinimumHeightForClipping(i);
        invalidate();
    }

    protected void setOutlineAlpha(float f) {
        if (f != this.mOutlineAlpha) {
            this.mOutlineAlpha = f;
            applyRoundnessAndInvalidate();
        }
    }

    protected void setOutlineRect(float f, float f2, float f3, float f4) {
        this.mCustomOutline = true;
        this.mOutlineRect.set((int) f, (int) f2, (int) f3, (int) f4);
        this.mOutlineRect.bottom = (int) Math.max(f2, r6.bottom);
        this.mOutlineRect.right = (int) Math.max(f, r5.right);
        applyRoundnessAndInvalidate();
    }

    protected void setOutlineRect(RectF rectF) {
        if (rectF != null) {
            setOutlineRect(rectF.left, rectF.top, rectF.right, rectF.bottom);
        } else {
            this.mCustomOutline = false;
            applyRoundnessAndInvalidate();
        }
    }

    public void updateOutline() {
        if (this.mCustomOutline) {
            return;
        }
        setOutlineProvider(needsOutline() ? this.mProvider : null);
    }
}
