package com.android.systemui.media.controls.ui.view;

import android.R;
import android.content.res.Resources;
import android.graphics.Outline;
import android.util.MathUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import androidx.core.view.GestureDetectorCompat;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.settingslib.Utils;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.qs.PageIndicator;
import com.android.systemui.res.R$dimen;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaCarouselScrollHandler.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaCarouselScrollHandler {
    private int carouselHeight;
    private int carouselWidth;
    private final Function1 closeGuts;
    private float contentTranslation;
    private int cornerRadius;
    private final Function0 dismissCallback;
    private boolean falsingProtectionNeeded;
    private final GestureDetectorCompat gestureDetector;
    private final MediaCarouselScrollHandler$gestureListener$1 gestureListener;
    private final Function1 logSmartspaceImpression;
    private final MediaUiEventLogger logger;
    private final DelayableExecutor mainExecutor;
    private ViewGroup mediaContent;
    private final PageIndicator pageIndicator;
    private int playerWidthPlusPadding;
    private boolean qsExpanded;
    private final MediaCarouselScrollHandler$scrollChangedListener$1 scrollChangedListener;
    private int scrollIntoCurrentMedia;
    private final MediaScrollView scrollView;
    private Function1 seekBarUpdateListener;
    private View settingsButton;
    private boolean showsSettingsButton;
    private final Gefingerpoken touchListener;
    private Function0 translationChangedListener;
    private int visibleMediaIndex;
    private boolean visibleToUser;
    public static final Companion Companion = new Companion(null);
    private static final MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1 CONTENT_TRANSLATION = new FloatPropertyCompat() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(MediaCarouselScrollHandler mediaCarouselScrollHandler) {
            mediaCarouselScrollHandler.getClass();
            return mediaCarouselScrollHandler.getContentTranslation();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(MediaCarouselScrollHandler mediaCarouselScrollHandler, float f) {
            mediaCarouselScrollHandler.getClass();
            mediaCarouselScrollHandler.setContentTranslation(f);
        }
    };

    /* JADX INFO: compiled from: MediaCarouselScrollHandler.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.view.GestureDetector$OnGestureListener, com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$gestureListener$1] */
    /* JADX WARN: Type inference failed for: r4v1, types: [android.view.View$OnScrollChangeListener, com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$scrollChangedListener$1] */
    public MediaCarouselScrollHandler(MediaScrollView mediaScrollView, PageIndicator pageIndicator, DelayableExecutor delayableExecutor, Function0 function0, Function0 function02, Function1 function1, Function1 function12, Function1 function13, MediaUiEventLogger mediaUiEventLogger) {
        mediaScrollView.getClass();
        pageIndicator.getClass();
        delayableExecutor.getClass();
        function0.getClass();
        function02.getClass();
        function1.getClass();
        function12.getClass();
        function13.getClass();
        mediaUiEventLogger.getClass();
        this.scrollView = mediaScrollView;
        this.pageIndicator = pageIndicator;
        this.mainExecutor = delayableExecutor;
        this.dismissCallback = function0;
        this.translationChangedListener = function02;
        this.seekBarUpdateListener = function1;
        this.closeGuts = function12;
        this.logSmartspaceImpression = function13;
        this.logger = mediaUiEventLogger;
        ?? r2 = new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$gestureListener$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                motionEvent.getClass();
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                motionEvent2.getClass();
                return this.this$0.onFling(f, f2);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                motionEvent2.getClass();
                MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
                motionEvent.getClass();
                return mediaCarouselScrollHandler.onScroll(motionEvent, motionEvent2, f);
            }
        };
        this.gestureListener = r2;
        Gefingerpoken gefingerpoken = new Gefingerpoken() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$touchListener$1
            @Override // com.android.systemui.Gefingerpoken
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
                motionEvent.getClass();
                return mediaCarouselScrollHandler.onInterceptTouch(motionEvent);
            }

            @Override // com.android.systemui.Gefingerpoken
            public boolean onTouchEvent(MotionEvent motionEvent) {
                MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
                motionEvent.getClass();
                return mediaCarouselScrollHandler.onTouch(motionEvent);
            }
        };
        this.touchListener = gefingerpoken;
        ?? r4 = new View.OnScrollChangeListener() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler$scrollChangedListener$1
            @Override // android.view.View.OnScrollChangeListener
            public void onScrollChange(View view, int i, int i2, int i3, int i4) {
                if (this.this$0.getPlayerWidthPlusPadding() == 0) {
                    return;
                }
                int relativeScrollX = this.this$0.scrollView.getRelativeScrollX();
                MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
                mediaCarouselScrollHandler.onMediaScrollingChanged(relativeScrollX / mediaCarouselScrollHandler.getPlayerWidthPlusPadding(), relativeScrollX % this.this$0.getPlayerWidthPlusPadding());
            }
        };
        this.scrollChangedListener = r4;
        this.gestureDetector = new GestureDetectorCompat(mediaScrollView.getContext(), r2);
        mediaScrollView.setTouchListener(gefingerpoken);
        mediaScrollView.setOverScrollMode(2);
        this.mediaContent = mediaScrollView.getContentContainer();
        mediaScrollView.setOnScrollChangeListener(r4);
        mediaScrollView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                if (outline != null) {
                    outline.setRoundRect(0, 0, MediaCarouselScrollHandler.this.carouselWidth, MediaCarouselScrollHandler.this.carouselHeight, MediaCarouselScrollHandler.this.cornerRadius);
                }
            }
        });
    }

    private final int getMaxTranslation() {
        if (!this.showsSettingsButton) {
            return this.playerWidthPlusPadding;
        }
        View view = this.settingsButton;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
            view = null;
        }
        return view.getWidth();
    }

    public static /* synthetic */ void getTouchListener$annotations() {
    }

    private final boolean isFalseTouch() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean onFling(float f, float f2) {
        float f3 = f * f;
        double d = f2;
        if (f3 < 0.5d * d * d || f3 < 1000000.0f) {
            return false;
        }
        float contentTranslation = this.scrollView.getContentTranslation();
        float maxTranslation = 0.0f;
        if (contentTranslation == 0.0f) {
            int relativeScrollX = this.scrollView.getRelativeScrollX();
            int i = this.playerWidthPlusPadding;
            int i2 = i > 0 ? relativeScrollX / i : 0;
            if (!isRtl() ? f < 0.0f : f > 0.0f) {
                i2++;
            }
            final View childAt = this.mediaContent.getChildAt(Math.min(this.mediaContent.getChildCount() - 1, Math.max(0, i2)));
            this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler.onFling.2
                @Override // java.lang.Runnable
                public final void run() {
                    MediaCarouselScrollHandler.this.scrollView.smoothScrollTo(childAt.getLeft(), MediaCarouselScrollHandler.this.scrollView.getScrollY());
                }
            });
        } else {
            if (Math.signum(f) == Math.signum(contentTranslation) && !isFalseTouch()) {
                maxTranslation = getMaxTranslation() * Math.signum(contentTranslation);
                if (!this.showsSettingsButton) {
                    this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler.onFling.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaCarouselScrollHandler.this.getDismissCallback().mo2224invoke();
                        }
                    }, 100L);
                }
            }
            PhysicsAnimator.Companion.getInstance(this).spring(CONTENT_TRANSLATION, maxTranslation, f, MediaCarouselScrollHandlerKt.translationConfig).start();
            this.scrollView.setAnimationTargetX(maxTranslation);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean onInterceptTouch(MotionEvent motionEvent) {
        return this.gestureDetector.onTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onMediaScrollingChanged(int i, int i2) {
        boolean z = this.scrollIntoCurrentMedia != 0;
        this.scrollIntoCurrentMedia = i2;
        boolean z2 = i2 != 0;
        int i3 = this.visibleMediaIndex;
        if (i != i3 || z != z2) {
            this.visibleMediaIndex = i;
            if (i3 != i && this.visibleToUser) {
                this.logSmartspaceImpression.invoke(Boolean.valueOf(this.qsExpanded));
                this.logger.logMediaCarouselPage(i);
            }
            this.closeGuts.invoke(Boolean.FALSE);
            updatePlayerVisibilities();
        }
        float f = this.visibleMediaIndex;
        int i4 = this.playerWidthPlusPadding;
        float childCount = f + (i4 > 0 ? i2 / i4 : 0.0f);
        if (isRtl()) {
            childCount = (this.mediaContent.getChildCount() - childCount) - 1;
        }
        this.pageIndicator.setLocation(childCount);
        updateClipToOutline();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean onTouch(MotionEvent motionEvent) {
        float maxTranslation;
        boolean z = true;
        boolean z2 = motionEvent.getAction() == 1;
        if (this.gestureDetector.onTouchEvent(motionEvent)) {
            if (!z2) {
                return false;
            }
            this.scrollView.cancelCurrentScroll();
            return true;
        }
        if (motionEvent.getAction() == 2) {
            PhysicsAnimator.Companion.getInstance(this).cancel();
        } else if (z2 || motionEvent.getAction() == 3) {
            int relativeScrollX = this.scrollView.getRelativeScrollX();
            int i = this.playerWidthPlusPadding;
            int i2 = relativeScrollX % i;
            int i3 = i2 > i / 2 ? i - i2 : i2 * (-1);
            if (i3 != 0) {
                if (isRtl()) {
                    i3 = -i3;
                }
                final int scrollX = this.scrollView.getScrollX() + i3;
                this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler.onTouch.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaCarouselScrollHandler.this.scrollView.smoothScrollTo(scrollX, MediaCarouselScrollHandler.this.scrollView.getScrollY());
                    }
                });
            }
            float contentTranslation = this.scrollView.getContentTranslation();
            if (contentTranslation != 0.0f) {
                if (Math.abs(contentTranslation) >= getMaxTranslation() / 2 && !isFalseTouch()) {
                    z = false;
                }
                if (z) {
                    maxTranslation = 0.0f;
                } else {
                    maxTranslation = getMaxTranslation() * Math.signum(contentTranslation);
                    if (!this.showsSettingsButton) {
                        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler.onTouch.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaCarouselScrollHandler.this.getDismissCallback().mo2224invoke();
                            }
                        }, 100L);
                    }
                }
                PhysicsAnimator.Companion.getInstance(this).spring(CONTENT_TRANSLATION, maxTranslation, 0.0f, MediaCarouselScrollHandlerKt.translationConfig).start();
                this.scrollView.setAnimationTargetX(maxTranslation);
            }
        }
        return false;
    }

    public static /* synthetic */ void resetTranslation$default(MediaCarouselScrollHandler mediaCarouselScrollHandler, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        mediaCarouselScrollHandler.resetTranslation(z);
    }

    public static /* synthetic */ void scrollToPlayer$default(MediaCarouselScrollHandler mediaCarouselScrollHandler, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = -1;
        }
        mediaCarouselScrollHandler.scrollToPlayer(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setContentTranslation(float f) {
        this.contentTranslation = f;
        this.mediaContent.setTranslationX(f);
        updateSettingsPresentation();
        this.translationChangedListener.mo2224invoke();
        updateClipToOutline();
    }

    private final void updateClipToOutline() {
        this.scrollView.setClipToOutline((this.contentTranslation == 0.0f && this.scrollIntoCurrentMedia == 0) ? false : true);
    }

    private final void updateMediaPaddings() {
        int dimensionPixelSize = this.scrollView.getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_padding);
        int childCount = this.mediaContent.getChildCount();
        int i = 0;
        while (i < childCount) {
            View childAt = this.mediaContent.getChildAt(i);
            int i2 = i == childCount + (-1) ? 0 : dimensionPixelSize;
            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            layoutParams.getClass();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            if (marginLayoutParams.getMarginEnd() != i2) {
                marginLayoutParams.setMarginEnd(i2);
                childAt.setLayoutParams(marginLayoutParams);
            }
            i++;
        }
    }

    private final void updatePlayerVisibilities() {
        boolean z = this.scrollIntoCurrentMedia != 0;
        int childCount = this.mediaContent.getChildCount();
        int i = 0;
        while (i < childCount) {
            View childAt = this.mediaContent.getChildAt(i);
            int i2 = this.visibleMediaIndex;
            childAt.setVisibility((i == i2 || (i == i2 + 1 && z)) ? 0 : 4);
            i++;
        }
    }

    private final void updateSettingsPresentation() {
        View view = null;
        if (this.showsSettingsButton) {
            View view2 = this.settingsButton;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
                view2 = null;
            }
            if (view2.getWidth() > 0) {
                float map = MathUtils.map(0.0f, getMaxTranslation(), 0.0f, 1.0f, Math.abs(this.contentTranslation));
                float f = 1.0f - map;
                View view3 = this.settingsButton;
                if (view3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
                    view3 = null;
                }
                float width = (-view3.getWidth()) * f * 0.3f;
                if (isRtl()) {
                    if (this.contentTranslation > 0.0f) {
                        float width2 = this.scrollView.getWidth() - width;
                        View view4 = this.settingsButton;
                        if (view4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
                            view4 = null;
                        }
                        width = -(width2 - view4.getWidth());
                    } else {
                        width = -width;
                    }
                } else if (this.contentTranslation <= 0.0f) {
                    float width3 = this.scrollView.getWidth() - width;
                    View view5 = this.settingsButton;
                    if (view5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
                        view5 = null;
                    }
                    width = width3 - view5.getWidth();
                }
                float f2 = f * 50;
                View view6 = this.settingsButton;
                if (view6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
                    view6 = null;
                }
                view6.setRotation(f2 * (-Math.signum(this.contentTranslation)));
                float fSaturate = MathUtils.saturate(MathUtils.map(0.5f, 1.0f, 0.0f, 1.0f, map));
                View view7 = this.settingsButton;
                if (view7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
                    view7 = null;
                }
                view7.setAlpha(fSaturate);
                View view8 = this.settingsButton;
                if (view8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
                    view8 = null;
                }
                view8.setVisibility(fSaturate != 0.0f ? 0 : 4);
                View view9 = this.settingsButton;
                if (view9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
                    view9 = null;
                }
                view9.setTranslationX(width);
                View view10 = this.settingsButton;
                if (view10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
                    view10 = null;
                }
                int height = this.scrollView.getHeight();
                View view11 = this.settingsButton;
                if (view11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
                } else {
                    view = view11;
                }
                view10.setTranslationY((height - view.getHeight()) / 2.0f);
                return;
            }
        }
        View view12 = this.settingsButton;
        if (view12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
        } else {
            view = view12;
        }
        view.setVisibility(4);
    }

    public final float getContentTranslation() {
        return this.contentTranslation;
    }

    public final Function0 getDismissCallback() {
        return this.dismissCallback;
    }

    public final int getPlayerWidthPlusPadding() {
        return this.playerWidthPlusPadding;
    }

    public final boolean getQsExpanded() {
        return this.qsExpanded;
    }

    public final int getVisibleMediaIndex() {
        return this.visibleMediaIndex;
    }

    public final boolean getVisibleToUser() {
        return this.visibleToUser;
    }

    public final boolean isRtl() {
        return this.scrollView.isLayoutRtl();
    }

    public final void onPlayersChanged() {
        updatePlayerVisibilities();
        updateMediaPaddings();
    }

    public final void onPrePlayerRemoved(TransitionLayout transitionLayout) {
        int iIndexOfChild = this.mediaContent.indexOfChild(transitionLayout);
        int i = this.visibleMediaIndex;
        boolean z = true;
        boolean z2 = iIndexOfChild <= i;
        if (z2) {
            this.visibleMediaIndex = Math.max(0, i - 1);
        }
        if (!isRtl() || this.visibleMediaIndex == 0) {
            z = z2;
        } else if (z2) {
            z = false;
        }
        if (z) {
            MediaScrollView mediaScrollView = this.scrollView;
            mediaScrollView.setScrollX(Math.max(mediaScrollView.getScrollX() - this.playerWidthPlusPadding, 0));
        }
    }

    public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f) {
        motionEvent.getClass();
        motionEvent2.getClass();
        float x = motionEvent2.getX() - motionEvent.getX();
        float contentTranslation = this.scrollView.getContentTranslation();
        if (contentTranslation == 0.0f && this.scrollView.canScrollHorizontally((int) (-x))) {
            return false;
        }
        float fSignum = contentTranslation - f;
        float fAbs = Math.abs(fSignum);
        if (fAbs > getMaxTranslation() && Math.signum(f) != Math.signum(contentTranslation)) {
            fSignum = Math.abs(contentTranslation) > ((float) getMaxTranslation()) ? contentTranslation - (f * 0.2f) : Math.signum(fSignum) * (getMaxTranslation() + ((fAbs - getMaxTranslation()) * 0.2f));
        }
        if (Math.signum(fSignum) != Math.signum(contentTranslation) && contentTranslation != 0.0f && this.scrollView.canScrollHorizontally(-((int) fSignum))) {
            fSignum = 0.0f;
        }
        PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(this);
        if (companion.isRunning()) {
            companion.spring(CONTENT_TRANSLATION, fSignum, 0.0f, MediaCarouselScrollHandlerKt.translationConfig).start();
        } else {
            setContentTranslation(fSignum);
        }
        this.scrollView.setAnimationTargetX(fSignum);
        return true;
    }

    public final void onSettingsButtonUpdated(View view) {
        view.getClass();
        this.settingsButton = view;
        Resources resources = view.getResources();
        View view2 = this.settingsButton;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settingsButton");
            view2 = null;
        }
        this.cornerRadius = resources.getDimensionPixelSize(Utils.getThemeAttr(view2.getContext(), R.attr.dialogCornerRadius));
        updateSettingsPresentation();
        this.scrollView.invalidateOutline();
    }

    public final void resetTranslation(boolean z) {
        if (this.scrollView.getContentTranslation() == 0.0f) {
            return;
        }
        if (z) {
            PhysicsAnimator.Companion.getInstance(this).spring(CONTENT_TRANSLATION, 0.0f, MediaCarouselScrollHandlerKt.translationConfig).start();
            this.scrollView.setAnimationTargetX(0.0f);
        } else {
            PhysicsAnimator.Companion.getInstance(this).cancel();
            setContentTranslation(0.0f);
        }
    }

    public final void scrollToPlayer(int i, int i2) {
        if (i >= 0 && i < this.mediaContent.getChildCount()) {
            this.scrollView.setRelativeScrollX(i * this.playerWidthPlusPadding);
        }
        final View childAt = this.mediaContent.getChildAt(Math.min(this.mediaContent.getChildCount() - 1, i2));
        this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler.scrollToPlayer.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaCarouselScrollHandler.this.scrollView.smoothScrollTo(childAt.getLeft(), MediaCarouselScrollHandler.this.scrollView.getScrollY());
            }
        }, 100L);
    }

    public final void scrollToStart() {
        this.scrollView.setRelativeScrollX(0);
    }

    public final void setCarouselBounds(int i, int i2) {
        int i3 = this.carouselHeight;
        if (i2 == i3 && i == i3) {
            return;
        }
        this.carouselWidth = i;
        this.carouselHeight = i2;
        this.scrollView.invalidateOutline();
    }

    public final void setFalsingProtectionNeeded(boolean z) {
        this.falsingProtectionNeeded = z;
    }

    public final void setPlayerWidthPlusPadding(int i) {
        this.playerWidthPlusPadding = i;
        int i2 = this.visibleMediaIndex * i;
        int i3 = this.scrollIntoCurrentMedia;
        this.scrollView.setRelativeScrollX(i3 > i ? i2 + (i - (i3 - i)) : i2 + i3);
    }

    public final void setShowsSettingsButton(boolean z) {
        this.showsSettingsButton = z;
    }

    public final void setVisibleToUser(boolean z) {
        if (this.visibleToUser != z) {
            this.visibleToUser = z;
            this.seekBarUpdateListener.invoke(Boolean.valueOf(z));
        }
    }
}
