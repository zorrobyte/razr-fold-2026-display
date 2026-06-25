package com.android.systemui.shared.shadow;

import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.TextView;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: DoubleShadowTextHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DoubleShadowTextHelper {
    public static final DoubleShadowTextHelper INSTANCE = new DoubleShadowTextHelper();

    /* JADX INFO: compiled from: DoubleShadowTextHelper.kt */
    public final class ShadowInfo {
        private final float alpha;
        private final float blur;
        private final float offsetX;
        private final float offsetY;

        public ShadowInfo(float f, float f2, float f3, float f4) {
            this.blur = f;
            this.offsetX = f2;
            this.offsetY = f3;
            this.alpha = f4;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ShadowInfo)) {
                return false;
            }
            ShadowInfo shadowInfo = (ShadowInfo) obj;
            return Float.compare(this.blur, shadowInfo.blur) == 0 && Float.compare(this.offsetX, shadowInfo.offsetX) == 0 && Float.compare(this.offsetY, shadowInfo.offsetY) == 0 && Float.compare(this.alpha, shadowInfo.alpha) == 0;
        }

        public final float getAlpha() {
            return this.alpha;
        }

        public final float getBlur() {
            return this.blur;
        }

        public final float getOffsetX() {
            return this.offsetX;
        }

        public final float getOffsetY() {
            return this.offsetY;
        }

        public int hashCode() {
            return (((((Float.hashCode(this.blur) * 31) + Float.hashCode(this.offsetX)) * 31) + Float.hashCode(this.offsetY)) * 31) + Float.hashCode(this.alpha);
        }

        public String toString() {
            return "ShadowInfo(blur=" + this.blur + ", offsetX=" + this.offsetX + ", offsetY=" + this.offsetY + ", alpha=" + this.alpha + ")";
        }
    }

    private DoubleShadowTextHelper() {
    }

    public final void applyShadows(ShadowInfo shadowInfo, ShadowInfo shadowInfo2, TextView textView, Canvas canvas, Function0 function0) {
        shadowInfo.getClass();
        shadowInfo2.getClass();
        textView.getClass();
        canvas.getClass();
        function0.getClass();
        textView.getPaint().setShadowLayer(shadowInfo2.getBlur(), shadowInfo2.getOffsetX(), shadowInfo2.getOffsetY(), Color.argb(shadowInfo2.getAlpha(), 0.0f, 0.0f, 0.0f));
        function0.mo2224invoke();
        canvas.save();
        canvas.clipRect(textView.getScrollX(), textView.getScrollY() + textView.getExtendedPaddingTop(), textView.getScrollX() + textView.getWidth(), textView.getScrollY() + textView.getHeight());
        textView.getPaint().setShadowLayer(shadowInfo.getBlur(), shadowInfo.getOffsetX(), shadowInfo.getOffsetY(), Color.argb(shadowInfo.getAlpha(), 0.0f, 0.0f, 0.0f));
        function0.mo2224invoke();
        canvas.restore();
    }
}
