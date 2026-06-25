package androidx.compose.ui.text.style;

import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ShaderBrush;
import androidx.compose.ui.graphics.SolidColor;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TextForegroundStyle.kt */
/* JADX INFO: loaded from: classes.dex */
public interface TextForegroundStyle {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: TextForegroundStyle.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final TextForegroundStyle from(Brush brush, float f) {
            if (brush == null) {
                return Unspecified.INSTANCE;
            }
            if (brush instanceof SolidColor) {
                return m1826from8_81llA(TextDrawStyleKt.m1825modulateDxMtmZc(((SolidColor) brush).m993getValue0d7_KjU(), f));
            }
            if (brush instanceof ShaderBrush) {
                return new BrushStyle((ShaderBrush) brush, f);
            }
            throw new NoWhenBranchMatchedException();
        }

        /* JADX INFO: renamed from: from-8_81llA, reason: not valid java name */
        public final TextForegroundStyle m1826from8_81llA(long j) {
            return j != 16 ? new ColorStyle(j, null) : Unspecified.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: TextForegroundStyle.kt */
    public final class Unspecified implements TextForegroundStyle {
        public static final Unspecified INSTANCE = new Unspecified();

        private Unspecified() {
        }

        @Override // androidx.compose.ui.text.style.TextForegroundStyle
        public float getAlpha() {
            return Float.NaN;
        }

        @Override // androidx.compose.ui.text.style.TextForegroundStyle
        public Brush getBrush() {
            return null;
        }

        @Override // androidx.compose.ui.text.style.TextForegroundStyle
        /* JADX INFO: renamed from: getColor-0d7_KjU */
        public long mo1725getColor0d7_KjU() {
            return Color.Companion.m895getUnspecified0d7_KjU();
        }
    }

    float getAlpha();

    Brush getBrush();

    /* JADX INFO: renamed from: getColor-0d7_KjU */
    long mo1725getColor0d7_KjU();

    default TextForegroundStyle merge(TextForegroundStyle textForegroundStyle) {
        boolean z = textForegroundStyle instanceof BrushStyle;
        return (z && (this instanceof BrushStyle)) ? new BrushStyle(((BrushStyle) textForegroundStyle).getValue(), TextDrawStyleKt.takeOrElse(textForegroundStyle.getAlpha(), new Function0() { // from class: androidx.compose.ui.text.style.TextForegroundStyle.merge.1
            @Override // kotlin.jvm.functions.Function0
            public final Float invoke() {
                return Float.valueOf(TextForegroundStyle.this.getAlpha());
            }
        })) : (!z || (this instanceof BrushStyle)) ? (z || !(this instanceof BrushStyle)) ? textForegroundStyle.takeOrElse(new Function0() { // from class: androidx.compose.ui.text.style.TextForegroundStyle.merge.2
            @Override // kotlin.jvm.functions.Function0
            public final TextForegroundStyle invoke() {
                return TextForegroundStyle.this;
            }
        }) : this : textForegroundStyle;
    }

    default TextForegroundStyle takeOrElse(Function0 function0) {
        return !Intrinsics.areEqual(this, Unspecified.INSTANCE) ? this : (TextForegroundStyle) function0.invoke();
    }
}
