package androidx.compose.ui.text.style;

import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;

/* JADX INFO: compiled from: TextForegroundStyle.kt */
/* JADX INFO: loaded from: classes.dex */
public interface TextForegroundStyle {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: TextForegroundStyle.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        /* JADX INFO: renamed from: from-8_81llA, reason: not valid java name */
        public final TextForegroundStyle m960from8_81llA(long j) {
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
        public long mo884getColor0d7_KjU() {
            return Color.Companion.m291getUnspecified0d7_KjU();
        }
    }

    float getAlpha();

    Brush getBrush();

    /* JADX INFO: renamed from: getColor-0d7_KjU */
    long mo884getColor0d7_KjU();
}
