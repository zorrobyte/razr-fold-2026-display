package com.android.systemui.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import com.android.systemui.res.R$styleable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: DelayableMarqueeTextView.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DelayableMarqueeTextView extends SafeMarqueeTextView {
    public static final Companion Companion = new Companion(null);
    private final Runnable enableMarquee;
    private boolean marqueeBlocked;
    private long marqueeDelay;
    private boolean wantsMarquee;

    /* JADX INFO: compiled from: DelayableMarqueeTextView.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DelayableMarqueeTextView(Context context) {
        this(context, null, 0, 0, 14, null);
        context.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DelayableMarqueeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
        context.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DelayableMarqueeTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DelayableMarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        context.getClass();
        this.marqueeDelay = 2000L;
        this.marqueeBlocked = true;
        this.enableMarquee = new Runnable() { // from class: com.android.systemui.util.DelayableMarqueeTextView$enableMarquee$1
            @Override // java.lang.Runnable
            public final void run() {
                if (this.this$0.wantsMarquee) {
                    this.this$0.marqueeBlocked = false;
                    this.this$0.startMarquee();
                }
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.DelayableMarqueeTextView, i, i2);
        typedArrayObtainStyledAttributes.getClass();
        this.marqueeDelay = typedArrayObtainStyledAttributes.getInteger(R$styleable.DelayableMarqueeTextView_marqueeDelay, 2000);
        typedArrayObtainStyledAttributes.recycle();
    }

    public /* synthetic */ DelayableMarqueeTextView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    @Override // com.android.systemui.util.SafeMarqueeTextView
    protected void startMarquee() {
        if (isSelected()) {
            this.wantsMarquee = true;
            if (!this.marqueeBlocked) {
                super.startMarquee();
                return;
            }
            Handler handler = getHandler();
            if (handler == null || handler.hasCallbacks(this.enableMarquee)) {
                return;
            }
            postDelayed(this.enableMarquee, this.marqueeDelay);
        }
    }

    @Override // com.android.systemui.util.SafeMarqueeTextView
    protected void stopMarquee() {
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.enableMarquee);
        }
        this.wantsMarquee = false;
        this.marqueeBlocked = true;
        super.stopMarquee();
    }
}
