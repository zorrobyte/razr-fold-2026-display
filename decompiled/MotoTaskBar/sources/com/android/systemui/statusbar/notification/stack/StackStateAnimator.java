package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Property;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.shared.NotificationsImprovedHunAnimation;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/* JADX INFO: loaded from: classes.dex */
public class StackStateAnimator {
    private final AnimationProperties mAnimationProperties;
    private ValueAnimator mBottomOverScrollAnimator;
    private long mCurrentAdditionalDelay;
    private long mCurrentLength;
    int mGoToFullShadeAppearingTranslation;
    private int mHeadsUpAppearHeightBottom;
    float mHeadsUpAppearStartAboveScreen;
    public NotificationStackScrollLayout mHostLayout;
    private StackStateLogger mLogger;
    private boolean mShadeExpanded;
    private int mStackTopMargin;
    private ValueAnimator mTopOverScrollAnimator;
    private final ExpandableViewState mTmpState = new ExpandableViewState();
    private ArrayList mNewEvents = new ArrayList();
    private ArrayList mNewAddChildren = new ArrayList();
    private HashSet mHeadsUpAppearChildren = new HashSet();
    private HashSet mHeadsUpDisappearChildren = new HashSet();
    private HashSet mAnimatorSet = new HashSet();
    private Stack mAnimationListenerPool = new Stack();
    private AnimationFilter mAnimationFilter = new AnimationFilter();
    private ArrayList mTransientViewsToRemove = new ArrayList();

    /* JADX INFO: renamed from: $r8$lambda$BwacokJ5AsmUySiHZh-uJ5lHg3U, reason: not valid java name */
    public static /* synthetic */ void m2049$r8$lambda$BwacokJ5AsmUySiHZhuJ5lHg3U(ExpandableView expandableView) {
        expandableView.setInRemovalAnimation(false);
        expandableView.removeFromTransientContainer();
    }

    /* JADX INFO: renamed from: $r8$lambda$kNo3_IZ-Hd5dHOYaCMeH86a8wIQ, reason: not valid java name */
    public static /* synthetic */ void m2051$r8$lambda$kNo3_IZHd5dHOYaCMeH86a8wIQ(ExpandableView expandableView, Runnable runnable) {
        expandableView.setInRemovalAnimation(false);
        if (runnable != null) {
            runnable.run();
        }
    }

    public StackStateAnimator(Context context, NotificationStackScrollLayout notificationStackScrollLayout) {
        this.mHostLayout = notificationStackScrollLayout;
        initView(context);
        this.mAnimationProperties = new AnimationProperties() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.1
            @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
            public AnimationFilter getAnimationFilter() {
                return StackStateAnimator.this.mAnimationFilter;
            }

            @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
            public AnimatorListenerAdapter getAnimationFinishListener(Property property) {
                return StackStateAnimator.this.getGlobalAnimationFinishedListener();
            }

            @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
            public boolean wasAdded(View view) {
                return StackStateAnimator.this.mNewAddChildren.contains(view);
            }
        };
    }

    private void adaptDurationWhenGoingToFullShade(ExpandableView expandableView, ExpandableViewState expandableViewState, boolean z, int i) {
        int i2;
        boolean z2 = expandableView instanceof StackScrollerDecorView;
        if ((z || z2) && this.mAnimationFilter.hasGoToFullShadeEvent) {
            if (z2) {
                i2 = 0;
            } else {
                i2 = this.mGoToFullShadeAppearingTranslation;
                this.mAnimationProperties.duration = ((long) (((float) Math.pow(i, 0.699999988079071d)) * 100.0f)) + 514;
            }
            expandableView.setTranslationY(expandableViewState.getYTranslation() + i2);
        }
    }

    private boolean applyWithoutAnimation(ExpandableView expandableView, ExpandableViewState expandableViewState) {
        if (this.mShadeExpanded || ViewState.isAnimatingY(expandableView) || this.mHeadsUpDisappearChildren.contains(expandableView) || this.mHeadsUpAppearChildren.contains(expandableView) || NotificationStackScrollLayout.isPinnedHeadsUp(expandableView)) {
            return false;
        }
        expandableViewState.applyToView(expandableView);
        return true;
    }

    private long calculateChildAnimationDelay(ExpandableViewState expandableViewState, int i) {
        AnimationFilter animationFilter = this.mAnimationFilter;
        if (animationFilter.hasGoToFullShadeEvent) {
            return calculateDelayGoToFullShade(expandableViewState, i);
        }
        long j = animationFilter.customDelay;
        if (j != -1) {
            return j;
        }
        ArrayList arrayList = this.mNewEvents;
        int size = arrayList.size();
        long jMax = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            NotificationStackScrollLayout.AnimationEvent animationEvent = (NotificationStackScrollLayout.AnimationEvent) obj;
            int i3 = animationEvent.animationType;
            long j2 = 80;
            if (i3 != 0) {
                if (i3 != 1) {
                    if (i3 == 2) {
                        j2 = 32;
                    }
                }
                int i4 = expandableViewState.notGoneIndex;
                View view = animationEvent.viewAfterChangingView;
                ExpandableView lastChildNotGone = view == null ? this.mHostLayout.getLastChildNotGone() : (ExpandableView) view;
                if (lastChildNotGone != null) {
                    int i5 = lastChildNotGone.getViewState().notGoneIndex;
                    if (i4 >= i5) {
                        i4++;
                    }
                    jMax = Math.max(((long) Math.max(0, Math.min(2, Math.abs(i4 - i5) - 1))) * j2, jMax);
                }
            } else {
                jMax = Math.max(((long) (2 - Math.max(0, Math.min(2, Math.abs(expandableViewState.notGoneIndex - animationEvent.mChangingView.getViewState().notGoneIndex) - 1)))) * 80, jMax);
            }
        }
        return jMax;
    }

    private long calculateDelayGoToFullShade(ExpandableViewState expandableViewState, int i) {
        return (long) (((float) Math.pow(expandableViewState.notGoneIndex, 0.699999988079071d)) * 48.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AnimatorListenerAdapter getGlobalAnimationFinishedListener() {
        return !this.mAnimationListenerPool.empty() ? (AnimatorListenerAdapter) this.mAnimationListenerPool.pop() : new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.2
            private boolean mWasCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                this.mWasCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                StackStateAnimator.this.mAnimatorSet.remove(animator);
                if (StackStateAnimator.this.mAnimatorSet.isEmpty() && !this.mWasCancelled) {
                    StackStateAnimator.this.onAnimationFinished();
                }
                StackStateAnimator.this.mAnimationListenerPool.push(this);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                this.mWasCancelled = false;
                StackStateAnimator.this.mAnimatorSet.add(animator);
            }
        };
    }

    private float getHeadsUpYTranslationStart(boolean z) {
        return z ? this.mHeadsUpAppearHeightBottom + this.mHeadsUpAppearStartAboveScreen : (-this.mStackTopMargin) - this.mHeadsUpAppearStartAboveScreen;
    }

    private void initAnimationProperties(ExpandableView expandableView, ExpandableViewState expandableViewState, int i) {
        boolean zWasAdded = this.mAnimationProperties.wasAdded(expandableView);
        this.mAnimationProperties.duration = this.mCurrentLength;
        adaptDurationWhenGoingToFullShade(expandableView, expandableViewState, zWasAdded, i);
        this.mAnimationProperties.delay = 0L;
        if (!zWasAdded) {
            if (!this.mAnimationFilter.hasDelays) {
                return;
            }
            if (expandableViewState.getYTranslation() == expandableView.getTranslationY() && expandableViewState.getZTranslation() == expandableView.getTranslationZ() && expandableViewState.getAlpha() == expandableView.getAlpha() && expandableViewState.height == expandableView.getActualHeight() && expandableViewState.clipTopAmount == expandableView.getClipTopAmount()) {
                return;
            }
        }
        this.mAnimationProperties.delay = this.mCurrentAdditionalDelay + calculateChildAnimationDelay(expandableViewState, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processAnimationEvents$0(String str, boolean z, ExpandableView expandableView) {
        this.mLogger.animationStart(str, "ANIMATION_TYPE_REMOVE", z);
        expandableView.setInRemovalAnimation(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processAnimationEvents$1(String str, boolean z, ExpandableView expandableView) {
        this.mLogger.animationEnd(str, "ANIMATION_TYPE_REMOVE", z);
        expandableView.setInRemovalAnimation(false);
        expandableView.removeFromTransientContainer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processAnimationEvents$4(String str) {
        this.mLogger.appearAnimationEnded(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processAnimationEvents$5(String str) {
        this.mLogger.appearAnimationEnded(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processAnimationEvents$6(String str, String str2, boolean z, ExpandableView expandableView) {
        this.mLogger.animationStart(str, str2, z);
        expandableView.setInRemovalAnimation(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processAnimationEvents$7(String str, String str2, boolean z, ExpandableView expandableView, Runnable runnable) {
        this.mLogger.animationEnd(str, str2, z);
        expandableView.setInRemovalAnimation(false);
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAnimationFinished() {
        this.mHostLayout.onChildAnimationFinished();
        ArrayList arrayList = this.mTransientViewsToRemove;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((ExpandableView) obj).removeFromTransientContainer();
        }
        this.mTransientViewsToRemove.clear();
    }

    private boolean processAnimationEvents(ArrayList arrayList) {
        final String key;
        boolean z;
        final boolean z2;
        boolean z3;
        final ExpandableView expandableView;
        Runnable runnable;
        Runnable runnable2;
        Runnable runnable3;
        Runnable runnable4;
        final StackStateAnimator stackStateAnimator = this;
        int size = arrayList.size();
        int i = 0;
        boolean z4 = false;
        while (i < size) {
            int i2 = i + 1;
            NotificationStackScrollLayout.AnimationEvent animationEvent = (NotificationStackScrollLayout.AnimationEvent) arrayList.get(i);
            final ExpandableView expandableView2 = animationEvent.mChangingView;
            if (!(expandableView2 instanceof ExpandableNotificationRow) || stackStateAnimator.mLogger == null) {
                key = null;
                z = false;
                z2 = false;
            } else {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView2;
                boolean zIsHeadsUp = expandableNotificationRow.isHeadsUp();
                key = expandableNotificationRow.getEntry().getKey();
                z2 = zIsHeadsUp;
                z = true;
            }
            int i3 = animationEvent.animationType;
            if (i3 == 0) {
                ExpandableViewState viewState = expandableView2.getViewState();
                if (viewState != null && !viewState.gone) {
                    if (z && z2) {
                        stackStateAnimator.mLogger.logHUNViewAppearingWithAddEvent(key);
                    }
                    viewState.applyToView(expandableView2);
                    stackStateAnimator.mNewAddChildren.add(expandableView2);
                    stackStateAnimator.mNewEvents.add(animationEvent);
                }
            } else {
                if (i3 == 1) {
                    int visibility = expandableView2.getVisibility();
                    if (z) {
                        stackStateAnimator.mLogger.processAnimationEventsRemoval(key, visibility, z2);
                    }
                    if (visibility != 0) {
                        expandableView2.removeFromTransientContainer();
                    } else {
                        float fMax = -1.0f;
                        if (animationEvent.viewAfterChangingView != null) {
                            float translationY = expandableView2.getTranslationY();
                            if (expandableView2 instanceof ExpandableNotificationRow) {
                                View view = animationEvent.viewAfterChangingView;
                                if (view instanceof ExpandableNotificationRow) {
                                    ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) expandableView2;
                                    ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) view;
                                    if (expandableNotificationRow2.isRemoved() && expandableNotificationRow2.wasChildInGroupWhenRemoved() && !expandableNotificationRow3.isChildInGroup()) {
                                        translationY = expandableNotificationRow2.getTranslationWhenRemoved();
                                    }
                                }
                            }
                            float actualHeight = expandableView2.getActualHeight();
                            fMax = Math.max(Math.min(((((ExpandableView) animationEvent.viewAfterChangingView).getViewState().getYTranslation() - (translationY + (actualHeight / 2.0f))) * 2.0f) / actualHeight, 1.0f), -1.0f);
                        }
                        float f = fMax;
                        if (z) {
                            runnable3 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$processAnimationEvents$0(key, z2, expandableView2);
                                }
                            };
                            runnable4 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$processAnimationEvents$1(key, z2, expandableView2);
                                }
                            };
                        } else {
                            runnable3 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    expandableView2.setInRemovalAnimation(true);
                                }
                            };
                            runnable4 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    StackStateAnimator.m2049$r8$lambda$BwacokJ5AsmUySiHZhuJ5lHg3U(expandableView2);
                                }
                            };
                        }
                        expandableView2.performRemoveAnimation(464L, 0L, f, false, runnable3, runnable4, stackStateAnimator.getGlobalAnimationFinishedListener());
                        z4 = true;
                    }
                } else if (i3 == 2) {
                    boolean zIsFullySwipedOut = stackStateAnimator.mHostLayout.isFullySwipedOut(expandableView2);
                    if (z) {
                        stackStateAnimator.mLogger.processAnimationEventsRemoveSwipeOut(key, zIsFullySwipedOut, z2);
                    }
                    if (zIsFullySwipedOut) {
                        expandableView2.removeFromTransientContainer();
                    }
                } else if (i3 == 10) {
                    ((ExpandableNotificationRow) animationEvent.mChangingView).prepareExpansionChanged();
                } else if (NotificationsImprovedHunAnimation.isEnabled() && animationEvent.animationType == 11) {
                    stackStateAnimator.mHeadsUpAppearChildren.add(expandableView2);
                    stackStateAnimator.mTmpState.copyFrom(expandableView2.getViewState());
                    stackStateAnimator.mTmpState.setYTranslation(stackStateAnimator.getHeadsUpYTranslationStart(animationEvent.headsUpFromBottom));
                    stackStateAnimator.mTmpState.applyToView(expandableView2);
                    stackStateAnimator.mAnimationProperties.setCustomInterpolator(View.TRANSLATION_Y, Interpolators.FAST_OUT_SLOW_IN);
                    if (z) {
                        stackStateAnimator.mLogger.logHUNViewAppearing(key);
                        runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$processAnimationEvents$4(key);
                            }
                        };
                    }
                    expandableView2.performAddAnimation(0L, 400L, true, runnable);
                } else {
                    int i4 = animationEvent.animationType;
                    if (i4 == 11) {
                        NotificationsImprovedHunAnimation.assertInLegacyMode();
                        stackStateAnimator.mTmpState.copyFrom(expandableView2.getViewState());
                        if (animationEvent.headsUpFromBottom) {
                            stackStateAnimator.mTmpState.setYTranslation(stackStateAnimator.mHeadsUpAppearHeightBottom);
                        } else {
                            expandableView2.performAddAnimation(0L, 400L, true, z ? new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda6
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$processAnimationEvents$5(key);
                                }
                            } : null);
                        }
                        stackStateAnimator.mHeadsUpAppearChildren.add(expandableView2);
                        if (z) {
                            stackStateAnimator.mLogger.logHUNViewAppearing(key);
                        }
                        stackStateAnimator.mTmpState.applyToView(expandableView2);
                    } else if (i4 == 12 || i4 == 13) {
                        stackStateAnimator.mHeadsUpDisappearChildren.add(expandableView2);
                        stackStateAnimator.mTmpState.copyFrom(expandableView2.getViewState());
                        if (expandableView2.getParent() == null) {
                            stackStateAnimator.mHostLayout.addTransientView(expandableView2, 0);
                            expandableView2.setTransientContainer(stackStateAnimator.mHostLayout);
                            if (NotificationsImprovedHunAnimation.isEnabled()) {
                                stackStateAnimator.mTmpState.setYTranslation(stackStateAnimator.getHeadsUpYTranslationStart(animationEvent.headsUpFromBottom));
                            }
                            runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda7
                                @Override // java.lang.Runnable
                                public final void run() {
                                    expandableView2.removeFromTransientContainer();
                                }
                            };
                        }
                        final Runnable runnable5 = runnable;
                        boolean z5 = ((expandableView2 instanceof ExpandableNotificationRow) && ((ExpandableNotificationRow) expandableView2).isDismissed()) ? false : true;
                        if (z5) {
                            if (z) {
                                final String str = animationEvent.animationType == 12 ? "ANIMATION_TYPE_HEADS_UP_DISAPPEAR" : "ANIMATION_TYPE_HEADS_UP_DISAPPEAR_CLICK";
                                final String str2 = key;
                                expandableView = expandableView2;
                                Runnable runnable6 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda8
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        this.f$0.lambda$processAnimationEvents$6(str2, str, z2, expandableView);
                                    }
                                };
                                stackStateAnimator = this;
                                z3 = true;
                                runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda9
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        this.f$0.lambda$processAnimationEvents$7(str2, str, z2, expandableView, runnable5);
                                    }
                                };
                                runnable2 = runnable6;
                            } else {
                                z3 = true;
                                expandableView = expandableView2;
                                runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda10
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        StackStateAnimator.m2051$r8$lambda$kNo3_IZHd5dHOYaCMeH86a8wIQ(expandableView, runnable5);
                                    }
                                };
                                runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        expandableView.setInRemovalAnimation(true);
                                    }
                                };
                            }
                            Runnable runnable7 = runnable;
                            boolean z6 = z3;
                            ExpandableView expandableView3 = expandableView;
                            stackStateAnimator.mAnimationProperties.delay += expandableView3.performRemoveAnimation(400L, 0L, 0.0f, true, runnable2, runnable7, stackStateAnimator.getGlobalAnimationFinishedListener());
                            if (NotificationsImprovedHunAnimation.isEnabled()) {
                                AnimationProperties animationProperties = stackStateAnimator.mAnimationProperties;
                                animationProperties.duration = 400L;
                                animationProperties.setCustomInterpolator(View.TRANSLATION_Y, Interpolators.FAST_OUT_SLOW_IN_REVERSE);
                                stackStateAnimator.mAnimationProperties.getAnimationFilter().animateY = z6;
                                stackStateAnimator.mTmpState.animateTo(expandableView3, stackStateAnimator.mAnimationProperties);
                            }
                        } else if (runnable5 != null) {
                            runnable5.run();
                        }
                        z4 |= z5;
                    }
                }
                stackStateAnimator.mNewEvents.add(animationEvent);
            }
            i = i2;
        }
        return z4;
    }

    private void updateResources(Context context) {
        this.mGoToFullShadeAppearingTranslation = context.getResources().getDimensionPixelSize(R$dimen.go_to_full_shade_appearing_translation);
        this.mHeadsUpAppearStartAboveScreen = context.getResources().getDimensionPixelSize(R$dimen.heads_up_appear_y_above_screen);
    }

    public void animateOverScrollToAmount(float f, final boolean z, final boolean z2) {
        float currentOverScrollAmount = this.mHostLayout.getCurrentOverScrollAmount(z);
        if (f == currentOverScrollAmount) {
            return;
        }
        cancelOverScrollAnimators(z);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(currentOverScrollAmount, f);
        valueAnimatorOfFloat.setDuration(360L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                StackStateAnimator.this.mHostLayout.setOverScrollAmount(((Float) valueAnimator.getAnimatedValue()).floatValue(), z, false, false, z2);
            }
        });
        valueAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.StackStateAnimator.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (z) {
                    StackStateAnimator.this.mTopOverScrollAnimator = null;
                } else {
                    StackStateAnimator.this.mBottomOverScrollAnimator = null;
                }
            }
        });
        valueAnimatorOfFloat.start();
        if (z) {
            this.mTopOverScrollAnimator = valueAnimatorOfFloat;
        } else {
            this.mBottomOverScrollAnimator = valueAnimatorOfFloat;
        }
    }

    public void cancelOverScrollAnimators(boolean z) {
        ValueAnimator valueAnimator = z ? this.mTopOverScrollAnimator : this.mBottomOverScrollAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public void initView(Context context) {
        updateResources(context);
    }

    public boolean isRunning() {
        return !this.mAnimatorSet.isEmpty();
    }

    protected void setLogger(StackStateLogger stackStateLogger) {
        this.mLogger = stackStateLogger;
    }

    public void startAnimationForEvents(ArrayList arrayList, long j) {
        boolean zProcessAnimationEvents = processAnimationEvents(arrayList);
        int childCount = this.mHostLayout.getChildCount();
        this.mAnimationFilter.applyCombination(this.mNewEvents);
        this.mCurrentAdditionalDelay = j;
        this.mCurrentLength = NotificationStackScrollLayout.AnimationEvent.combineLength(this.mNewEvents);
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            ExpandableView expandableView = (ExpandableView) this.mHostLayout.getChildAt(i2);
            ExpandableViewState viewState = expandableView.getViewState();
            if (viewState != null && expandableView.getVisibility() != 8 && !applyWithoutAnimation(expandableView, viewState)) {
                if (this.mAnimationProperties.wasAdded(expandableView) && i < 5) {
                    i++;
                }
                initAnimationProperties(expandableView, viewState, i);
                viewState.animateTo(expandableView, this.mAnimationProperties);
            }
        }
        if (!isRunning() && !zProcessAnimationEvents) {
            onAnimationFinished();
        }
        this.mHeadsUpAppearChildren.clear();
        this.mHeadsUpDisappearChildren.clear();
        this.mNewEvents.clear();
        this.mNewAddChildren.clear();
    }
}
