package com.android.systemui.surfaceeffects.ripple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: MultiRippleView.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MultiRippleView extends View {
    public static final Companion Companion = new Companion(null);
    private final Paint ripplePaint;
    private final ArrayList ripples;

    /* JADX INFO: compiled from: MultiRippleView.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MultiRippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ripples = new ArrayList();
        this.ripplePaint = new Paint();
    }

    public static /* synthetic */ void getRipples$annotations() {
    }

    public final ArrayList getRipples() {
        return this.ripples;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.getClass();
        if (canvas.isHardwareAccelerated()) {
            ArrayList arrayList = this.ripples;
            int size = arrayList.size();
            boolean z = false;
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                RippleAnimation rippleAnimation = (RippleAnimation) obj;
                this.ripplePaint.setShader(rippleAnimation.getRippleShader());
                canvas.drawPaint(this.ripplePaint);
                z = z || rippleAnimation.isPlaying();
            }
            if (z) {
                invalidate();
            }
        }
    }
}
