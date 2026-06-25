package com.android.systemui.statusbar.notification.stack;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import com.android.systemui.Flags;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.notification.row.ExpandableView;

/* JADX INFO: compiled from: MediaContainerView.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaContainerView extends ExpandableView {
    private int clipHeight;
    private Path clipPath;
    private RectF clipRect;
    private float cornerRadius;

    /* JADX INFO: compiled from: MediaContainerView.kt */
    public final class MediaContainerViewState extends ExpandableViewState {
        private boolean shouldBeVisible;

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public void copyFrom(ViewState viewState) {
            viewState.getClass();
            super.copyFrom(viewState);
            if (viewState instanceof MediaContainerViewState) {
                this.shouldBeVisible = ((MediaContainerViewState) viewState).shouldBeVisible;
            }
        }

        public final boolean getShouldBeVisible() {
            return this.shouldBeVisible;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context.getClass();
        this.clipRect = new RectF();
        this.clipPath = new Path();
        setWillNotDraw(false);
        updateResources();
    }

    private final void assertMediaContainerVisibility(int i) {
        ExpandableViewState viewState = getViewState();
        viewState.getClass();
        if ((viewState instanceof MediaContainerViewState) && !((MediaContainerViewState) viewState).getShouldBeVisible() && i == 0) {
            Log.wtf("MediaContainerView", "MediaContainerView should be GONE but its visibility changed to VISIBLE");
        }
    }

    private final boolean isVisibilityValid(int i) {
        ExpandableViewState viewState = getViewState();
        MediaContainerViewState mediaContainerViewState = viewState instanceof MediaContainerViewState ? (MediaContainerViewState) viewState : null;
        if (mediaContainerViewState == null) {
            return true;
        }
        return !mediaContainerViewState.getShouldBeVisible() ? i == 8 : i != 8;
    }

    private final void updateResources() {
        this.cornerRadius = getContext().getResources().getDimensionPixelSize(R$dimen.notification_corner_radius);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    protected ExpandableViewState createExpandableViewState() {
        return new MediaContainerViewState();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.statusbar.notification.Roundable
    public int getClipHeight() {
        return this.clipHeight;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.getClass();
        super.onDraw(canvas);
        Rect clipBounds = canvas.getClipBounds();
        clipBounds.getClass();
        clipBounds.bottom = getClipHeight();
        this.clipRect.set(clipBounds);
        this.clipPath.reset();
        Path path = this.clipPath;
        RectF rectF = this.clipRect;
        float f = this.cornerRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        canvas.clipPath(this.clipPath);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void performAddAnimation(long j, long j2, boolean z, Runnable runnable) {
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public long performRemoveAnimation(long j, long j2, float f, boolean z, Runnable runnable, Runnable runnable2, AnimatorListenerAdapter animatorListenerAdapter) {
        return 0L;
    }

    public void setClipHeight(int i) {
        this.clipHeight = i;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (!Flags.bindKeyguardMediaVisibility() || isVisibilityValid(i)) {
            super.setVisibility(i);
        }
        assertMediaContainerVisibility(i);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void updateClipping() {
        if (getClipHeight() != getActualHeight()) {
            setClipHeight(getActualHeight());
        }
        invalidate();
    }
}
