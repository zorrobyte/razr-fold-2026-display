package com.google.android.setupdesign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
public class StickyHeaderRecyclerView extends HeaderRecyclerView {
    private int statusBarInset;
    private View sticky;
    private final RectF stickyRect;

    public StickyHeaderRecyclerView(Context context) {
        super(context);
        this.statusBarInset = 0;
        this.stickyRect = new RectF();
    }

    public StickyHeaderRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.statusBarInset = 0;
        this.stickyRect = new RectF();
    }

    public StickyHeaderRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.statusBarInset = 0;
        this.stickyRect = new RectF();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.stickyRect.contains(motionEvent.getX(), motionEvent.getY())) {
            return super.dispatchTouchEvent(motionEvent);
        }
        RectF rectF = this.stickyRect;
        motionEvent.offsetLocation(-rectF.left, -rectF.top);
        return getHeader().dispatchTouchEvent(motionEvent);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.sticky != null) {
            View header = getHeader();
            int iSave = canvas.save();
            View view = header != null ? header : this.sticky;
            if (view.getTop() + (header != null ? this.sticky.getTop() : 0) < this.statusBarInset || !view.isShown()) {
                this.stickyRect.set(0.0f, (-r0) + this.statusBarInset, view.getWidth(), (view.getHeight() - r0) + this.statusBarInset);
                canvas.translate(0.0f, this.stickyRect.top);
                canvas.clipRect(0, 0, view.getWidth(), view.getHeight());
                view.draw(canvas);
            } else {
                this.stickyRect.setEmpty();
            }
            canvas.restoreToCount(iSave);
        }
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (getFitsSystemWindows()) {
            this.statusBarInset = windowInsets.getSystemWindowInsetTop();
            windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), 0, windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
        }
        return windowInsets;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View header;
        super.onLayout(z, i, i2, i3, i4);
        if (this.sticky == null) {
            updateStickyView();
        }
        if (this.sticky == null || (header = getHeader()) == null || header.getHeight() != 0) {
            return;
        }
        header.layout(0, -header.getMeasuredHeight(), header.getMeasuredWidth(), 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.sticky != null) {
            measureChild(getHeader(), i, i2);
        }
    }

    public void updateStickyView() {
        View header = getHeader();
        if (header != null) {
            this.sticky = header.findViewWithTag("sticky");
        }
    }
}
