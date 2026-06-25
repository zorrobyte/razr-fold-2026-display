package androidx.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/* JADX INFO: loaded from: classes.dex */
public class ContentLoadingProgressBar extends ProgressBar {
    private final Runnable mDelayedHide;
    private final Runnable mDelayedShow;
    boolean mDismissed;
    boolean mPostedHide;
    boolean mPostedShow;
    long mStartTime;

    public static /* synthetic */ void $r8$lambda$fIsCaKHnKCEuu_tkcPfpEL5uVhw(ContentLoadingProgressBar contentLoadingProgressBar) {
        contentLoadingProgressBar.mPostedShow = false;
        if (contentLoadingProgressBar.mDismissed) {
            return;
        }
        contentLoadingProgressBar.mStartTime = System.currentTimeMillis();
        contentLoadingProgressBar.setVisibility(0);
    }

    public static /* synthetic */ void $r8$lambda$j9oX7_n69XSaTYRza19W9WmzMU0(ContentLoadingProgressBar contentLoadingProgressBar) {
        contentLoadingProgressBar.mPostedHide = false;
        contentLoadingProgressBar.mStartTime = -1L;
        contentLoadingProgressBar.setVisibility(8);
    }

    public ContentLoadingProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mStartTime = -1L;
        this.mPostedHide = false;
        this.mPostedShow = false;
        this.mDismissed = false;
        this.mDelayedHide = new Runnable() { // from class: androidx.core.widget.ContentLoadingProgressBar$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ContentLoadingProgressBar.$r8$lambda$j9oX7_n69XSaTYRza19W9WmzMU0(this.f$0);
            }
        };
        this.mDelayedShow = new Runnable() { // from class: androidx.core.widget.ContentLoadingProgressBar$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ContentLoadingProgressBar.$r8$lambda$fIsCaKHnKCEuu_tkcPfpEL5uVhw(this.f$0);
            }
        };
    }

    private void removeCallbacks() {
        removeCallbacks(this.mDelayedHide);
        removeCallbacks(this.mDelayedShow);
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks();
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks();
    }
}
