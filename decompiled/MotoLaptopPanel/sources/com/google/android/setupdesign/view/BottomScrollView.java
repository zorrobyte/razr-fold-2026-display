package com.google.android.setupdesign.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/* JADX INFO: loaded from: classes.dex */
public class BottomScrollView extends ScrollView {
    private final Runnable checkScrollRunnable;
    private BottomScrollListener listener;
    private boolean requiringScroll;
    private int scrollThreshold;

    public interface BottomScrollListener {
        void onRequiresScroll();

        void onScrolledToBottom();
    }

    public BottomScrollView(Context context) {
        super(context);
        this.requiringScroll = false;
        this.checkScrollRunnable = new Runnable() { // from class: com.google.android.setupdesign.view.BottomScrollView.1
            @Override // java.lang.Runnable
            public void run() {
                BottomScrollView.this.checkScroll();
            }
        };
    }

    public BottomScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.requiringScroll = false;
        this.checkScrollRunnable = new Runnable() { // from class: com.google.android.setupdesign.view.BottomScrollView.1
            @Override // java.lang.Runnable
            public void run() {
                BottomScrollView.this.checkScroll();
            }
        };
    }

    public BottomScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.requiringScroll = false;
        this.checkScrollRunnable = new Runnable() { // from class: com.google.android.setupdesign.view.BottomScrollView.1
            @Override // java.lang.Runnable
            public void run() {
                BottomScrollView.this.checkScroll();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkScroll() {
        if (this.listener != null) {
            if (getScrollY() >= this.scrollThreshold) {
                this.listener.onScrolledToBottom();
            } else {
                if (this.requiringScroll) {
                    return;
                }
                this.requiringScroll = true;
                this.listener.onRequiresScroll();
            }
        }
    }

    public BottomScrollListener getBottomScrollListener() {
        return this.listener;
    }

    @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        View childAt = getChildAt(0);
        if (childAt != null) {
            this.scrollThreshold = Math.max(0, ((childAt.getMeasuredHeight() - i4) + i2) - getPaddingBottom());
        }
        if (i4 - i2 > 0) {
            post(this.checkScrollRunnable);
        }
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (i4 != i2) {
            checkScroll();
        }
    }
}
