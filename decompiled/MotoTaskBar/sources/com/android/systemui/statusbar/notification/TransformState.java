package com.android.systemui.statusbar.notification;

import android.util.Pools;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingPropertyAnimator;
import com.android.internal.widget.ViewClippingUtil;
import com.android.systemui.res.R$id;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* JADX INFO: loaded from: classes.dex */
public class TransformState {
    private boolean mAlignEnd;
    private boolean mSameAsAny;
    protected TransformInfo mTransformInfo;
    protected View mTransformedView;
    public static final int ALIGN_END_TAG = R$id.align_transform_end_tag;
    private static final int TRANSFORMATION_START_X = R$id.transformation_start_x_tag;
    private static final int TRANSFORMATION_START_Y = R$id.transformation_start_y_tag;
    private static final int TRANSFORMATION_START_SCLALE_X = R$id.transformation_start_scale_x_tag;
    private static final int TRANSFORMATION_START_SCLALE_Y = R$id.transformation_start_scale_y_tag;
    private static Pools.SimplePool sInstancePool = new Pools.SimplePool(40);
    private static ViewClippingUtil.ClippingParameters CLIPPING_PARAMETERS = new ViewClippingUtil.ClippingParameters() { // from class: com.android.systemui.statusbar.notification.TransformState.1
        public void onClippingStateChanged(View view, boolean z) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (z) {
                    expandableNotificationRow.setClipToActualHeight(true);
                } else if (expandableNotificationRow.isChildInGroup()) {
                    expandableNotificationRow.setClipToActualHeight(false);
                }
            }
        }

        public boolean shouldFinish(View view) {
            if (view instanceof ExpandableNotificationRow) {
                return !((ExpandableNotificationRow) view).isChildInGroup();
            }
            return false;
        }
    };
    private int[] mOwnPosition = new int[2];
    private float mTransformationEndY = -1.0f;
    private float mTransformationEndX = -1.0f;
    protected Interpolator mDefaultInterpolator = Interpolators.FAST_OUT_SLOW_IN;

    public interface TransformInfo {
        boolean isAnimating();
    }

    public static TransformState createFrom(View view, TransformInfo transformInfo) {
        if (view instanceof TextView) {
            TextViewTransformState textViewTransformStateObtain = TextViewTransformState.obtain();
            textViewTransformStateObtain.initFrom(view, transformInfo);
            return textViewTransformStateObtain;
        }
        if (view.getId() == 16908784) {
            ActionListTransformState actionListTransformStateObtain = ActionListTransformState.obtain();
            actionListTransformStateObtain.initFrom(view, transformInfo);
            return actionListTransformStateObtain;
        }
        if (view.getId() == 16909378) {
            MessagingLayoutTransformState messagingLayoutTransformStateObtain = MessagingLayoutTransformState.obtain();
            messagingLayoutTransformStateObtain.initFrom(view, transformInfo);
            return messagingLayoutTransformStateObtain;
        }
        if (view instanceof MessagingImageMessage) {
            MessagingImageTransformState messagingImageTransformStateObtain = MessagingImageTransformState.obtain();
            messagingImageTransformStateObtain.initFrom(view, transformInfo);
            return messagingImageTransformStateObtain;
        }
        if (view instanceof ImageView) {
            ImageTransformState imageTransformStateObtain = ImageTransformState.obtain();
            imageTransformStateObtain.initFrom(view, transformInfo);
            return imageTransformStateObtain;
        }
        if (view instanceof ProgressBar) {
            ProgressTransformState progressTransformStateObtain = ProgressTransformState.obtain();
            progressTransformStateObtain.initFrom(view, transformInfo);
            return progressTransformStateObtain;
        }
        TransformState transformStateObtain = obtain();
        transformStateObtain.initFrom(view, transformInfo);
        return transformStateObtain;
    }

    public static TransformState obtain() {
        TransformState transformState = (TransformState) sInstancePool.acquire();
        return transformState != null ? transformState : new TransformState();
    }

    private void setTransformationStartScaleX(float f) {
        this.mTransformedView.setTag(TRANSFORMATION_START_SCLALE_X, Float.valueOf(f));
    }

    private void setTransformationStartScaleY(float f) {
        this.mTransformedView.setTag(TRANSFORMATION_START_SCLALE_Y, Float.valueOf(f));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0111 A[PHI: r5
      0x0111: PHI (r5v7 float) = (r5v6 float), (r5v9 float) binds: [B:59:0x00f9, B:64:0x010a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void transformViewTo(com.android.systemui.statusbar.notification.TransformState r19, int r20, com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation r21, float r22) {
        /*
            Method dump skipped, instruction units count: 332
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.TransformState.transformViewTo(com.android.systemui.statusbar.notification.TransformState, int, com.android.systemui.statusbar.ViewTransformationHelper$CustomTransformation, float):void");
    }

    public void abortTransformation() {
        View view = this.mTransformedView;
        int i = TRANSFORMATION_START_X;
        Float fValueOf = Float.valueOf(-1.0f);
        view.setTag(i, fValueOf);
        this.mTransformedView.setTag(TRANSFORMATION_START_Y, fValueOf);
        this.mTransformedView.setTag(TRANSFORMATION_START_SCLALE_X, fValueOf);
        this.mTransformedView.setTag(TRANSFORMATION_START_SCLALE_Y, fValueOf);
    }

    public void appear(float f, TransformableView transformableView) {
        if (f == 0.0f) {
            prepareFadeIn();
        }
        CrossFadeHelper.fadeIn(this.mTransformedView, f, true);
    }

    public void disappear(float f, TransformableView transformableView) {
        CrossFadeHelper.fadeOut(this.mTransformedView, f);
    }

    public void ensureVisible() {
        if (this.mTransformedView.getVisibility() == 4 || this.mTransformedView.getAlpha() != 1.0f) {
            this.mTransformedView.setAlpha(1.0f);
            this.mTransformedView.setVisibility(0);
        }
    }

    protected int getContentHeight() {
        return this.mTransformedView.getHeight();
    }

    protected int getContentWidth() {
        return this.mTransformedView.getWidth();
    }

    public int[] getLaidOutLocationOnScreen() {
        int[] locationOnScreen = getLocationOnScreen();
        locationOnScreen[0] = (int) (locationOnScreen[0] - this.mTransformedView.getTranslationX());
        locationOnScreen[1] = (int) (locationOnScreen[1] - this.mTransformedView.getTranslationY());
        return locationOnScreen;
    }

    public int[] getLocationOnScreen() {
        this.mTransformedView.getLocationOnScreen(this.mOwnPosition);
        this.mOwnPosition[0] = (int) (r0[0] - ((1.0f - this.mTransformedView.getScaleX()) * this.mTransformedView.getPivotX()));
        this.mOwnPosition[1] = (int) (r0[1] - ((1.0f - this.mTransformedView.getScaleY()) * this.mTransformedView.getPivotY()));
        int[] iArr = this.mOwnPosition;
        iArr[1] = iArr[1] - (MessagingPropertyAnimator.getTop(this.mTransformedView) - MessagingPropertyAnimator.getLayoutTop(this.mTransformedView));
        return this.mOwnPosition;
    }

    public float getTransformationStartScaleX() {
        Object tag = this.mTransformedView.getTag(TRANSFORMATION_START_SCLALE_X);
        if (tag == null) {
            return -1.0f;
        }
        return ((Float) tag).floatValue();
    }

    public float getTransformationStartScaleY() {
        Object tag = this.mTransformedView.getTag(TRANSFORMATION_START_SCLALE_Y);
        if (tag == null) {
            return -1.0f;
        }
        return ((Float) tag).floatValue();
    }

    public float getTransformationStartX() {
        Object tag = this.mTransformedView.getTag(TRANSFORMATION_START_X);
        if (tag == null) {
            return -1.0f;
        }
        return ((Float) tag).floatValue();
    }

    public float getTransformationStartY() {
        Object tag = this.mTransformedView.getTag(TRANSFORMATION_START_Y);
        if (tag == null) {
            return -1.0f;
        }
        return ((Float) tag).floatValue();
    }

    public View getTransformedView() {
        return this.mTransformedView;
    }

    public void initFrom(View view, TransformInfo transformInfo) {
        this.mTransformedView = view;
        this.mTransformInfo = transformInfo;
        this.mAlignEnd = Boolean.TRUE.equals(view.getTag(ALIGN_END_TAG));
    }

    public void prepareFadeIn() {
        resetTransformedView();
    }

    public void recycle() {
        reset();
        if (getClass() == TransformState.class) {
            sInstancePool.release(this);
        }
    }

    protected void reset() {
        this.mTransformedView = null;
        this.mTransformInfo = null;
        this.mSameAsAny = false;
        this.mTransformationEndX = -1.0f;
        this.mTransformationEndY = -1.0f;
        this.mAlignEnd = false;
        this.mDefaultInterpolator = Interpolators.FAST_OUT_SLOW_IN;
    }

    protected void resetTransformedView() {
        this.mTransformedView.setTranslationX(0.0f);
        this.mTransformedView.setTranslationY(0.0f);
        this.mTransformedView.setScaleX(1.0f);
        this.mTransformedView.setScaleY(1.0f);
        setClippingDeactivated(this.mTransformedView, false);
        abortTransformation();
    }

    protected boolean sameAs(TransformState transformState) {
        return this.mSameAsAny;
    }

    protected void setClippingDeactivated(View view, boolean z) {
        ViewClippingUtil.setClippingDeactivated(view, z, CLIPPING_PARAMETERS);
    }

    public void setDefaultInterpolator(Interpolator interpolator) {
        this.mDefaultInterpolator = interpolator;
    }

    public void setIsSameAsAnyView(boolean z) {
        this.mSameAsAny = z;
    }

    public void setTransformationEndY(float f) {
        this.mTransformationEndY = f;
    }

    public void setTransformationStartX(float f) {
        this.mTransformedView.setTag(TRANSFORMATION_START_X, Float.valueOf(f));
    }

    public void setTransformationStartY(float f) {
        this.mTransformedView.setTag(TRANSFORMATION_START_Y, Float.valueOf(f));
    }

    public void setVisible(boolean z, boolean z2) {
        if (z2 || this.mTransformedView.getVisibility() != 8) {
            if (this.mTransformedView.getVisibility() != 8) {
                this.mTransformedView.setVisibility(z ? 0 : 4);
            }
            this.mTransformedView.animate().cancel();
            this.mTransformedView.setAlpha(z ? 1.0f : 0.0f);
            resetTransformedView();
        }
    }

    protected boolean transformRightEdge(TransformState transformState) {
        boolean z = false;
        boolean z2 = this.mAlignEnd && transformState.mAlignEnd;
        if (this.mTransformedView.isLayoutRtl() && transformState.mTransformedView.isLayoutRtl()) {
            z = true;
        }
        return z2 ^ z;
    }

    protected boolean transformScale(TransformState transformState) {
        return sameAs(transformState);
    }

    public void transformViewFrom(TransformState transformState, float f) {
        this.mTransformedView.animate().cancel();
        if (sameAs(transformState)) {
            ensureVisible();
        } else {
            CrossFadeHelper.fadeIn(this.mTransformedView, f, true);
        }
        transformViewFullyFrom(transformState, f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v18 */
    protected void transformViewFrom(TransformState transformState, int i, ViewTransformationHelper.CustomTransformation customTransformation, float f) {
        ?? r5;
        Interpolator customInterpolator;
        Interpolator customInterpolator2;
        View view = this.mTransformedView;
        boolean z = (i & 1) != 0;
        boolean z2 = (i & 16) != 0;
        int contentHeight = getContentHeight();
        int contentHeight2 = transformState.getContentHeight();
        boolean z3 = (contentHeight2 == contentHeight || contentHeight2 == 0 || contentHeight == 0) ? false : true;
        int contentWidth = getContentWidth();
        int contentWidth2 = transformState.getContentWidth();
        boolean z4 = (contentWidth2 == contentWidth || contentWidth2 == 0 || contentWidth == 0) ? false : true;
        boolean z5 = (z3 || z4) && transformScale(transformState);
        boolean zTransformRightEdge = transformRightEdge(transformState);
        if (f == 0.0f || ((z && getTransformationStartX() == -1.0f) || ((z2 && getTransformationStartY() == -1.0f) || ((z5 && getTransformationStartScaleX() == -1.0f && z4) || (z5 && getTransformationStartScaleY() == -1.0f && z3))))) {
            int[] laidOutLocationOnScreen = f != 0.0f ? transformState.getLaidOutLocationOnScreen() : transformState.getLocationOnScreen();
            int[] laidOutLocationOnScreen2 = getLaidOutLocationOnScreen();
            if (customTransformation == 0 || !customTransformation.initTransformation(this, transformState)) {
                if (z) {
                    if (zTransformRightEdge) {
                        setTransformationStartX((laidOutLocationOnScreen[0] + transformState.getTransformedView().getWidth()) - (laidOutLocationOnScreen2[0] + view.getWidth()));
                    } else {
                        setTransformationStartX(laidOutLocationOnScreen[0] - laidOutLocationOnScreen2[0]);
                    }
                }
                if (z2) {
                    setTransformationStartY(laidOutLocationOnScreen[1] - laidOutLocationOnScreen2[1]);
                }
                View transformedView = transformState.getTransformedView();
                if (z5 && z4) {
                    setTransformationStartScaleX((contentWidth2 * transformedView.getScaleX()) / contentWidth);
                    view.setPivotX(zTransformRightEdge ? view.getWidth() : 0.0f);
                } else {
                    setTransformationStartScaleX(-1.0f);
                }
                if (z5 && z3) {
                    setTransformationStartScaleY((contentHeight2 * transformedView.getScaleY()) / contentHeight);
                    view.setPivotY(0.0f);
                } else {
                    setTransformationStartScaleY(-1.0f);
                }
            }
            if (!z) {
                setTransformationStartX(-1.0f);
            }
            if (!z2) {
                setTransformationStartY(-1.0f);
            }
            if (!z5) {
                setTransformationStartScaleX(-1.0f);
                setTransformationStartScaleY(-1.0f);
            }
            r5 = 1;
            setClippingDeactivated(view, true);
        } else {
            r5 = 1;
        }
        float interpolation = this.mDefaultInterpolator.getInterpolation(f);
        if (z) {
            view.setTranslationX(NotificationUtils.interpolate(getTransformationStartX(), 0.0f, (customTransformation == 0 || (customInterpolator2 = customTransformation.getCustomInterpolator(r5, r5)) == null) ? interpolation : customInterpolator2.getInterpolation(f)));
        }
        if (z2) {
            view.setTranslationY(NotificationUtils.interpolate(getTransformationStartY(), 0.0f, (customTransformation == 0 || (customInterpolator = customTransformation.getCustomInterpolator(16, true)) == null) ? interpolation : customInterpolator.getInterpolation(f)));
        }
        if (z5) {
            float transformationStartScaleX = getTransformationStartScaleX();
            if (transformationStartScaleX != -1.0f) {
                view.setScaleX(NotificationUtils.interpolate(transformationStartScaleX, 1.0f, interpolation));
            }
            float transformationStartScaleY = getTransformationStartScaleY();
            if (transformationStartScaleY != -1.0f) {
                view.setScaleY(NotificationUtils.interpolate(transformationStartScaleY, 1.0f, interpolation));
            }
        }
    }

    public void transformViewFullyFrom(TransformState transformState, float f) {
        transformViewFrom(transformState, 17, null, f);
    }

    public void transformViewFullyFrom(TransformState transformState, ViewTransformationHelper.CustomTransformation customTransformation, float f) {
        transformViewFrom(transformState, 17, customTransformation, f);
    }

    public void transformViewFullyTo(TransformState transformState, float f) {
        transformViewTo(transformState, 17, null, f);
    }

    public void transformViewFullyTo(TransformState transformState, ViewTransformationHelper.CustomTransformation customTransformation, float f) {
        transformViewTo(transformState, 17, customTransformation, f);
    }

    public boolean transformViewTo(TransformState transformState, float f) {
        this.mTransformedView.animate().cancel();
        if (!sameAs(transformState)) {
            CrossFadeHelper.fadeOut(this.mTransformedView, f);
            transformViewFullyTo(transformState, f);
            return true;
        }
        if (this.mTransformedView.getVisibility() != 0) {
            return false;
        }
        this.mTransformedView.setAlpha(0.0f);
        this.mTransformedView.setVisibility(4);
        return false;
    }

    public void transformViewVerticalFrom(TransformState transformState, float f) {
        transformViewFrom(transformState, 16, null, f);
    }

    public void transformViewVerticalFrom(TransformState transformState, ViewTransformationHelper.CustomTransformation customTransformation, float f) {
        transformViewFrom(transformState, 16, customTransformation, f);
    }

    public void transformViewVerticalTo(TransformState transformState, float f) {
        transformViewTo(transformState, 16, null, f);
    }

    public void transformViewVerticalTo(TransformState transformState, ViewTransformationHelper.CustomTransformation customTransformation, float f) {
        transformViewTo(transformState, 16, customTransformation, f);
    }
}
