package com.android.systemui.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SafeMarqueeTextView.kt */
/* JADX INFO: loaded from: classes.dex */
public class SafeMarqueeTextView extends TextView {
    private boolean safelyIgnoreLayout;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SafeMarqueeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SafeMarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        context.getClass();
    }

    public /* synthetic */ SafeMarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    private final boolean getHasStableWidth() {
        return getLayoutParams().width != -2;
    }

    @Override // android.view.View
    public void requestLayout() {
        if (this.safelyIgnoreLayout) {
            return;
        }
        super.requestLayout();
    }

    protected void startMarquee() {
        boolean z = this.safelyIgnoreLayout;
        this.safelyIgnoreLayout = getHasStableWidth();
        super.startMarquee();
        this.safelyIgnoreLayout = z;
    }

    protected void stopMarquee() {
        boolean z = this.safelyIgnoreLayout;
        this.safelyIgnoreLayout = getHasStableWidth();
        super.stopMarquee();
        this.safelyIgnoreLayout = z;
    }
}
