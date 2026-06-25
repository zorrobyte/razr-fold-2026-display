package com.android.systemui.qs.tileimpl;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.settingslib.Utils;
import com.android.systemui.qs.QSIconView;
import com.android.systemui.qs.QSTile;
import com.android.systemui.res.R$dimen;
import com.motorola.taskbar.R$attr;
import com.motorola.taskbar.R$id;
import java.util.Objects;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes.dex */
public class QSIconViewImpl extends QSIconView {
    private boolean mAnimationEnabled;
    private ValueAnimator mColorAnimator;
    private boolean mDisabledByPolicy;
    private long mHighestScheduledIconChangeTransactionId;
    protected final View mIcon;
    protected int mIconSizePx;
    QSTile.Icon mLastIcon;
    private long mScheduledIconChangeTransactionId;
    private int mState;
    private int mTint;

    class EndRunnableAnimatorListener extends AnimatorListenerAdapter {
        private Runnable mRunnable;

        EndRunnableAnimatorListener(Runnable runnable) {
            this.mRunnable = runnable;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            super.onAnimationCancel(animator);
            this.mRunnable.run();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            this.mRunnable.run();
        }
    }

    public QSIconViewImpl(Context context) {
        super(context);
        this.mAnimationEnabled = true;
        this.mState = -1;
        this.mDisabledByPolicy = false;
        this.mScheduledIconChangeTransactionId = -1L;
        this.mHighestScheduledIconChangeTransactionId = -1L;
        this.mColorAnimator = new ValueAnimator();
        this.mIconSizePx = context.getResources().getDimensionPixelSize(R$dimen.qs_icon_size);
        View viewCreateIcon = createIcon();
        this.mIcon = viewCreateIcon;
        addView(viewCreateIcon);
        this.mColorAnimator.setDuration(350L);
    }

    private void animateGrayScale(int i, int i2, final ImageView imageView, Runnable runnable) {
        this.mColorAnimator.cancel();
        if (!this.mAnimationEnabled || !ValueAnimator.areAnimatorsEnabled()) {
            setTint(imageView, i2);
            runnable.run();
            return;
        }
        PropertyValuesHolder propertyValuesHolderOfInt = PropertyValuesHolder.ofInt("color", i, i2);
        propertyValuesHolderOfInt.setEvaluator(ArgbEvaluator.getInstance());
        this.mColorAnimator.setValues(propertyValuesHolderOfInt);
        this.mColorAnimator.removeAllListeners();
        this.mColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$animateGrayScale$1(imageView, valueAnimator);
            }
        });
        this.mColorAnimator.addListener(new EndRunnableAnimatorListener(runnable));
        this.mColorAnimator.start();
    }

    private static int getIconColorForState(Context context, QSTile.State state) {
        int i;
        if (state.disabledByPolicy || (i = state.state) == 0) {
            return Utils.getColorAttrDefaultColor(context, R$attr.outline);
        }
        if (i == 1) {
            return Utils.getColorAttrDefaultColor(context, R$attr.onShadeInactiveVariant);
        }
        if (i == 2) {
            return Utils.getColorAttrDefaultColor(context, R$attr.onShadeActive);
        }
        Log.e("QSIconView", "Invalid state " + state);
        return 0;
    }

    private long getNextIconTransactionId() {
        long j = this.mHighestScheduledIconChangeTransactionId + 1;
        this.mHighestScheduledIconChangeTransactionId = j;
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$animateGrayScale$1(ImageView imageView, ValueAnimator valueAnimator) {
        setTint(imageView, ((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setIcon$0(long j, ImageView imageView, QSTile.State state, boolean z) {
        if (this.mScheduledIconChangeTransactionId == j) {
            updateIcon(imageView, state, z);
        }
    }

    private boolean shouldAnimate(ImageView imageView) {
        return this.mAnimationEnabled && imageView.isShown() && imageView.getDrawable() != null;
    }

    protected View createIcon() {
        ImageView imageView = new ImageView(((ViewGroup) this).mContext);
        imageView.setId(R.id.icon);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return imageView;
    }

    protected final int exactly(int i) {
        return View.MeasureSpec.makeMeasureSpec(i, 1073741824);
    }

    protected int getColor(QSTile.State state) {
        return getIconColorForState(getContext(), state);
    }

    protected int getIconMeasureMode() {
        return 1073741824;
    }

    protected final void layout(View view, int i, int i2) {
        view.layout(i, i2, view.getMeasuredWidth() + i, view.getMeasuredHeight() + i2);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mIconSizePx = getContext().getResources().getDimensionPixelSize(R$dimen.qs_icon_size);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layout(this.mIcon, (getMeasuredWidth() - this.mIcon.getMeasuredWidth()) / 2, 0);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        this.mIcon.measure(View.MeasureSpec.makeMeasureSpec(size, getIconMeasureMode()), exactly(this.mIconSizePx));
        setMeasuredDimension(size, this.mIcon.getMeasuredHeight());
    }

    protected void setIcon(final ImageView imageView, final QSTile.State state, final boolean z) {
        if (state.state == this.mState && state.disabledByPolicy == this.mDisabledByPolicy) {
            updateIcon(imageView, state, z);
            return;
        }
        int color = getColor(state);
        this.mState = state.state;
        this.mDisabledByPolicy = state.disabledByPolicy;
        if (this.mTint == 0 || !z || !shouldAnimate(imageView)) {
            setTint(imageView, color);
            updateIcon(imageView, state, z);
        } else {
            final long nextIconTransactionId = getNextIconTransactionId();
            this.mScheduledIconChangeTransactionId = nextIconTransactionId;
            animateGrayScale(this.mTint, color, imageView, new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setIcon$0(nextIconTransactionId, imageView, state, z);
                }
            });
        }
    }

    public void setIcon(QSTile.State state, boolean z) {
        setIcon((ImageView) this.mIcon, state, z);
    }

    public void setTint(ImageView imageView, int i) {
        imageView.setImageTintList(ColorStateList.valueOf(i));
        this.mTint = i;
    }

    @Override // android.view.View
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        sb.append("state=" + this.mState);
        sb.append(", tint=" + this.mTint);
        if (this.mLastIcon != null) {
            sb.append(", lastIcon=" + this.mLastIcon.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v2, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r3v4, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r8v0, types: [android.widget.ImageView] */
    protected void updateIcon(ImageView imageView, QSTile.State state, boolean z) {
        this.mScheduledIconChangeTransactionId = -1L;
        Supplier supplier = state.iconSupplier;
        QSTile.Icon icon = supplier != null ? (QSTile.Icon) supplier.get() : state.icon;
        int i = R$id.qs_icon_tag;
        if (Objects.equals(icon, imageView.getTag(i))) {
            return;
        }
        boolean z2 = z && shouldAnimate(imageView);
        this.mLastIcon = icon;
        ?? drawable = icon != null ? z2 ? icon.getDrawable(((ViewGroup) this).mContext) : icon.getInvisibleDrawable(((ViewGroup) this).mContext) : 0;
        int padding = icon != null ? icon.getPadding() : 0;
        if (drawable != 0) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            drawable = drawable;
            if (constantState != null) {
                drawable = drawable.getConstantState().newDrawable();
            }
            drawable.setAutoMirrored(false);
            drawable.setLayoutDirection(getLayoutDirection());
        }
        Object drawable2 = imageView.getDrawable();
        if (drawable2 instanceof Animatable2) {
            ((Animatable2) drawable2).clearAnimationCallbacks();
        }
        imageView.setImageDrawable(drawable);
        imageView.setTag(i, icon);
        imageView.setPadding(0, padding, 0, padding);
        if (drawable instanceof Animatable2) {
            final Animatable2 animatable2 = (Animatable2) drawable;
            animatable2.start();
            if (!z2) {
                animatable2.stop();
            } else if (state.isTransient) {
                animatable2.registerAnimationCallback(new Animatable2.AnimationCallback(this) { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl.1
                    @Override // android.graphics.drawable.Animatable2.AnimationCallback
                    public void onAnimationEnd(Drawable drawable3) {
                        animatable2.start();
                    }
                });
            }
        }
    }
}
