package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.systemui.res.R$id;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;

/* JADX INFO: loaded from: classes.dex */
public class ExpandableViewState extends ViewState {
    public boolean belowSpeedBump;
    public int clipBottomAmount;
    public int clipTopAmount;
    public boolean headsUpIsVisible;
    public int height;
    public boolean hideSensitive;
    public boolean inShelf;
    public int location;
    public int notGoneIndex;
    private static final int TAG_ANIMATOR_HEIGHT = R$id.height_animator_tag;
    private static final int TAG_ANIMATOR_TOP_INSET = R$id.top_inset_animator_tag;
    private static final int TAG_ANIMATOR_BOTTOM_INSET = R$id.bottom_inset_animator_tag;
    private static final int TAG_END_HEIGHT = R$id.height_animator_end_value_tag;
    private static final int TAG_END_TOP_INSET = R$id.top_inset_animator_end_value_tag;
    private static final int TAG_END_BOTTOM_INSET = R$id.bottom_inset_animator_end_value_tag;
    private static final int TAG_START_HEIGHT = R$id.height_animator_start_value_tag;
    private static final int TAG_START_TOP_INSET = R$id.top_inset_animator_start_value_tag;
    private static final int TAG_START_BOTTOM_INSET = R$id.bottom_inset_animator_start_value_tag;

    /* JADX INFO: renamed from: $r8$lambda$CbGU9H6RzsbGnNIl116ZT-60CvY, reason: not valid java name */
    public static /* synthetic */ void m1972$r8$lambda$CbGU9H6RzsbGnNIl116ZT60CvY(boolean z, ExpandableView expandableView, ValueAnimator valueAnimator) {
        if (z) {
            expandableView.setClipTopAmount(((Integer) valueAnimator.getAnimatedValue()).intValue());
        } else {
            expandableView.setClipBottomAmount(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
    }

    private void startClipAnimation(final ExpandableView expandableView, AnimationProperties animationProperties, final boolean z) {
        Integer num = (Integer) ViewState.getChildTag(expandableView, z ? TAG_START_TOP_INSET : TAG_START_BOTTOM_INSET);
        Integer num2 = (Integer) ViewState.getChildTag(expandableView, z ? TAG_END_TOP_INSET : TAG_END_BOTTOM_INSET);
        int i = z ? this.clipTopAmount : this.clipBottomAmount;
        if (num2 == null || num2.intValue() != i) {
            ValueAnimator valueAnimator = (ValueAnimator) ViewState.getChildTag(expandableView, z ? TAG_ANIMATOR_TOP_INSET : TAG_ANIMATOR_BOTTOM_INSET);
            AnimationFilter animationFilter = animationProperties.getAnimationFilter();
            if ((z && !animationFilter.animateTopInset) || !z) {
                if (valueAnimator == null) {
                    if (z) {
                        expandableView.setClipTopAmount(i);
                        return;
                    } else {
                        expandableView.setClipBottomAmount(i);
                        return;
                    }
                }
                PropertyValuesHolder[] values = valueAnimator.getValues();
                int iIntValue = num.intValue() + (i - num2.intValue());
                values[0].setIntValues(iIntValue, i);
                expandableView.setTag(z ? TAG_START_TOP_INSET : TAG_START_BOTTOM_INSET, Integer.valueOf(iIntValue));
                expandableView.setTag(z ? TAG_END_TOP_INSET : TAG_END_BOTTOM_INSET, Integer.valueOf(i));
                valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
                return;
            }
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(z ? expandableView.getClipTopAmount() : expandableView.getClipBottomAmount(), i);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    ExpandableViewState.m1972$r8$lambda$CbGU9H6RzsbGnNIl116ZT60CvY(z, expandableView, valueAnimator2);
                }
            });
            valueAnimatorOfInt.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            valueAnimatorOfInt.setDuration(ViewState.cancelAnimatorAndGetNewDuration(animationProperties.duration, valueAnimator));
            if (animationProperties.delay > 0 && (valueAnimator == null || valueAnimator.getAnimatedFraction() == 0.0f)) {
                valueAnimatorOfInt.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(null);
            if (animationFinishListener != null) {
                valueAnimatorOfInt.addListener(animationFinishListener);
            }
            valueAnimatorOfInt.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    expandableView.setTag(z ? ExpandableViewState.TAG_ANIMATOR_TOP_INSET : ExpandableViewState.TAG_ANIMATOR_BOTTOM_INSET, null);
                    expandableView.setTag(z ? ExpandableViewState.TAG_START_TOP_INSET : ExpandableViewState.TAG_START_BOTTOM_INSET, null);
                    expandableView.setTag(z ? ExpandableViewState.TAG_END_TOP_INSET : ExpandableViewState.TAG_END_BOTTOM_INSET, null);
                }
            });
            ViewState.startAnimator(valueAnimatorOfInt, animationFinishListener);
            expandableView.setTag(z ? TAG_ANIMATOR_TOP_INSET : TAG_ANIMATOR_BOTTOM_INSET, valueAnimatorOfInt);
            expandableView.setTag(z ? TAG_START_TOP_INSET : TAG_START_BOTTOM_INSET, Integer.valueOf(z ? expandableView.getClipTopAmount() : expandableView.getClipBottomAmount()));
            expandableView.setTag(z ? TAG_END_TOP_INSET : TAG_END_BOTTOM_INSET, Integer.valueOf(i));
        }
    }

    private void startHeightAnimation(final ExpandableView expandableView, AnimationProperties animationProperties) {
        int i = TAG_START_HEIGHT;
        Integer num = (Integer) ViewState.getChildTag(expandableView, i);
        int i2 = TAG_END_HEIGHT;
        Integer num2 = (Integer) ViewState.getChildTag(expandableView, i2);
        int i3 = this.height;
        if (num2 == null || num2.intValue() != i3) {
            int i4 = TAG_ANIMATOR_HEIGHT;
            ValueAnimator valueAnimator = (ValueAnimator) ViewState.getChildTag(expandableView, i4);
            if (!animationProperties.getAnimationFilter().animateHeight) {
                if (valueAnimator == null) {
                    expandableView.setActualHeight(i3, false);
                    return;
                }
                PropertyValuesHolder[] values = valueAnimator.getValues();
                int iIntValue = num.intValue() + (i3 - num2.intValue());
                values[0].setIntValues(iIntValue, i3);
                expandableView.setTag(i, Integer.valueOf(iIntValue));
                expandableView.setTag(i2, Integer.valueOf(i3));
                valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
                return;
            }
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(expandableView.getActualHeight(), i3);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    expandableView.setActualHeight(((Integer) valueAnimator2.getAnimatedValue()).intValue(), false);
                }
            });
            valueAnimatorOfInt.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            valueAnimatorOfInt.setDuration(ViewState.cancelAnimatorAndGetNewDuration(animationProperties.duration, valueAnimator));
            if (animationProperties.delay > 0 && (valueAnimator == null || valueAnimator.getAnimatedFraction() == 0.0f)) {
                valueAnimatorOfInt.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(null);
            if (animationFinishListener != null) {
                valueAnimatorOfInt.addListener(animationFinishListener);
            }
            valueAnimatorOfInt.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ExpandableViewState.2
                boolean mWasCancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.mWasCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    expandableView.setTag(ExpandableViewState.TAG_ANIMATOR_HEIGHT, null);
                    expandableView.setTag(ExpandableViewState.TAG_START_HEIGHT, null);
                    expandableView.setTag(ExpandableViewState.TAG_END_HEIGHT, null);
                    expandableView.setActualHeightAnimating(false);
                    if (this.mWasCancelled) {
                        return;
                    }
                    ExpandableView expandableView2 = expandableView;
                    if (expandableView2 instanceof ExpandableNotificationRow) {
                        ((ExpandableNotificationRow) expandableView2).setGroupExpansionChanging(false);
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    this.mWasCancelled = false;
                }
            });
            ViewState.startAnimator(valueAnimatorOfInt, animationFinishListener);
            expandableView.setTag(i4, valueAnimatorOfInt);
            expandableView.setTag(i, Integer.valueOf(expandableView.getActualHeight()));
            expandableView.setTag(i2, Integer.valueOf(i3));
            expandableView.setActualHeightAnimating(true);
        }
    }

    @Override // com.android.systemui.statusbar.notification.stack.ViewState
    public void animateTo(View view, AnimationProperties animationProperties) {
        super.animateTo(view, animationProperties);
        if (view instanceof ExpandableView) {
            ExpandableView expandableView = (ExpandableView) view;
            AnimationFilter animationFilter = animationProperties.getAnimationFilter();
            if (this.height != expandableView.getActualHeight()) {
                startHeightAnimation(expandableView, animationProperties);
            } else {
                abortAnimation(view, TAG_ANIMATOR_HEIGHT);
            }
            if (this.clipTopAmount != expandableView.getClipTopAmount()) {
                startClipAnimation(expandableView, animationProperties, true);
            } else {
                abortAnimation(view, TAG_ANIMATOR_TOP_INSET);
            }
            if (this.clipBottomAmount != expandableView.getClipBottomAmount()) {
                startClipAnimation(expandableView, animationProperties, false);
            } else {
                abortAnimation(view, TAG_ANIMATOR_BOTTOM_INSET);
            }
            expandableView.setBelowSpeedBump(this.belowSpeedBump);
            expandableView.setHideSensitive(this.hideSensitive, animationFilter.animateHideSensitive, animationProperties.delay, animationProperties.duration);
            if (animationProperties.wasAdded(view) && !this.hidden) {
                expandableView.performAddAnimation(animationProperties.delay, animationProperties.duration, false);
            }
            if (!expandableView.isInShelf() && this.inShelf) {
                expandableView.setTransformingInShelf(true);
            }
            expandableView.setInShelf(this.inShelf);
            if (this.headsUpIsVisible) {
                expandableView.setHeadsUpIsVisible();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.stack.ViewState
    public void applyToView(View view) {
        super.applyToView(view);
        if (view instanceof ExpandableView) {
            ExpandableView expandableView = (ExpandableView) view;
            int actualHeight = expandableView.getActualHeight();
            int i = this.height;
            if (actualHeight != i) {
                expandableView.setActualHeight(i, false);
            }
            expandableView.setHideSensitive(this.hideSensitive, false, 0L, 0L);
            expandableView.setBelowSpeedBump(this.belowSpeedBump);
            float clipTopAmount = expandableView.getClipTopAmount();
            int i2 = this.clipTopAmount;
            if (clipTopAmount != i2) {
                expandableView.setClipTopAmount(i2);
            }
            float clipBottomAmount = expandableView.getClipBottomAmount();
            int i3 = this.clipBottomAmount;
            if (clipBottomAmount != i3) {
                expandableView.setClipBottomAmount(i3);
            }
            expandableView.setTransformingInShelf(false);
            expandableView.setInShelf(this.inShelf);
            if (this.headsUpIsVisible) {
                expandableView.setHeadsUpIsVisible();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.stack.ViewState
    public void cancelAnimations(View view) {
        super.cancelAnimations(view);
        Animator animator = (Animator) ViewState.getChildTag(view, TAG_ANIMATOR_HEIGHT);
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = (Animator) ViewState.getChildTag(view, TAG_ANIMATOR_TOP_INSET);
        if (animator2 != null) {
            animator2.cancel();
        }
    }

    @Override // com.android.systemui.statusbar.notification.stack.ViewState
    public void copyFrom(ViewState viewState) {
        super.copyFrom(viewState);
        if (viewState instanceof ExpandableViewState) {
            ExpandableViewState expandableViewState = (ExpandableViewState) viewState;
            this.height = expandableViewState.height;
            this.hideSensitive = expandableViewState.hideSensitive;
            this.belowSpeedBump = expandableViewState.belowSpeedBump;
            this.clipTopAmount = expandableViewState.clipTopAmount;
            this.notGoneIndex = expandableViewState.notGoneIndex;
            this.location = expandableViewState.location;
            this.headsUpIsVisible = expandableViewState.headsUpIsVisible;
        }
    }
}
