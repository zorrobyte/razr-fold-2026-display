package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.app.animation.Interpolators;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public abstract class StackScrollerDecorView extends ExpandableView {
    private int mAnimationDuration;
    protected View mContent;
    private boolean mContentAnimating;
    private boolean mContentVisible;
    private boolean mIsSecondaryVisible;
    private boolean mIsVisible;
    private boolean mSecondaryAnimating;
    protected View mSecondaryView;

    public StackScrollerDecorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsVisible = true;
        this.mContentVisible = true;
        this.mIsSecondaryVisible = true;
        this.mAnimationDuration = 260;
        this.mSecondaryAnimating = false;
        setClipChildren(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentVisible$0(Consumer consumer, Boolean bool) {
        onContentVisibilityAnimationEnd();
        if (consumer != null) {
            consumer.accept(bool);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSecondaryVisible$1(Consumer consumer, Boolean bool) {
        onContentVisibilityAnimationEnd();
        if (consumer != null) {
            consumer.accept(bool);
        }
    }

    private void onContentVisibilityAnimationEnd() {
        this.mContentAnimating = false;
        if (getVisibility() == 8 || this.mIsVisible) {
            return;
        }
        setVisibility(8);
        setWillBeGone(false);
        notifyHeightChanged(false);
    }

    private void onSecondaryVisibilityAnimationEnd() {
        this.mSecondaryAnimating = false;
        if (this.mSecondaryView == null || getVisibility() == 8 || this.mSecondaryView.getVisibility() == 8 || this.mIsSecondaryVisible) {
            return;
        }
        this.mSecondaryView.setVisibility(8);
    }

    private void setViewVisible(View view, boolean z, boolean z2, final Consumer consumer) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() != 0) {
            view.setVisibility(0);
        }
        view.animate().cancel();
        float f = z ? 1.0f : 0.0f;
        if (z2) {
            view.animate().alpha(f).setInterpolator(z ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT).setDuration(this.mAnimationDuration).setListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.row.StackScrollerDecorView.1
                boolean mCancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    consumer.accept(Boolean.valueOf(this.mCancelled));
                }
            });
            return;
        }
        view.setAlpha(f);
        if (consumer != null) {
            consumer.accept(Boolean.TRUE);
        }
    }

    protected abstract View findContentView();

    protected abstract View findSecondaryView();

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean isTransparent() {
        return true;
    }

    public boolean isVisible() {
        return this.mIsVisible;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mContent = findContentView();
        this.mSecondaryView = findSecondaryView();
        setVisible(false, false);
        setSecondaryVisible(false, false, null);
        setOutlineProvider(null);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void performAddAnimation(long j, long j2, boolean z) {
        setContentVisibleAnimated(true);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void performAddAnimation(long j, long j2, boolean z, Runnable runnable) {
        setContentVisibleAnimated(true);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public long performRemoveAnimation(long j, long j2, float f, boolean z, Runnable runnable, final Runnable runnable2, AnimatorListenerAdapter animatorListenerAdapter) {
        if (runnable != null) {
            runnable.run();
        }
        setContentVisible(false, true, new Consumer() { // from class: com.android.systemui.statusbar.notification.row.StackScrollerDecorView$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                runnable2.run();
            }
        });
        return 0L;
    }

    public void setAnimationDuration(int i) {
        this.mAnimationDuration = i;
    }

    public void setContentVisible(boolean z, boolean z2, final Consumer consumer) {
        if (this.mContentVisible != z) {
            this.mContentAnimating = z2;
            this.mContentVisible = z;
            setViewVisible(this.mContent, z, z2, new Consumer() { // from class: com.android.systemui.statusbar.notification.row.StackScrollerDecorView$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$setContentVisible$0(consumer, (Boolean) obj);
                }
            });
        } else if (consumer != null) {
            consumer.accept(Boolean.TRUE);
        }
        if (this.mContentAnimating) {
            return;
        }
        onContentVisibilityAnimationEnd();
    }

    public void setContentVisibleAnimated(boolean z) {
        setContentVisible(z, true, null);
    }

    protected void setSecondaryVisible(boolean z, boolean z2, final Consumer consumer) {
        if (this.mIsSecondaryVisible != z) {
            this.mSecondaryAnimating = z2;
            this.mIsSecondaryVisible = z;
            setViewVisible(this.mSecondaryView, z, z2, new Consumer() { // from class: com.android.systemui.statusbar.notification.row.StackScrollerDecorView$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$setSecondaryVisible$1(consumer, (Boolean) obj);
                }
            });
        }
        if (this.mSecondaryAnimating) {
            return;
        }
        onSecondaryVisibilityAnimationEnd();
    }

    public void setVisible(boolean z, boolean z2) {
        if (this.mIsVisible != z) {
            this.mIsVisible = z;
            if (!z2) {
                setVisibility(z ? 0 : 8);
                setContentVisible(z, false, null);
                setWillBeGone(false);
                notifyHeightChanged(false);
                return;
            }
            if (z) {
                setVisibility(0);
                setWillBeGone(false);
                notifyHeightChanged(false);
            } else {
                setWillBeGone(true);
            }
            setContentVisible(z, true, null);
        }
    }
}
