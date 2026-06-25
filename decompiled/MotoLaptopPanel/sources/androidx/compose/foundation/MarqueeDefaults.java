package androidx.compose.foundation;

import androidx.compose.ui.unit.Dp;

/* JADX INFO: compiled from: BasicMarquee.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MarqueeDefaults {
    public static final MarqueeDefaults INSTANCE = new MarqueeDefaults();
    private static final int Iterations = 3;
    private static final int RepeatDelayMillis = 1200;
    private static final MarqueeSpacing Spacing = MarqueeSpacing.Companion.fractionOfContainer(0.33333334f);
    private static final float Velocity = Dp.m1877constructorimpl(30);

    private MarqueeDefaults() {
    }

    public final int getIterations() {
        return Iterations;
    }

    public final int getRepeatDelayMillis() {
        return RepeatDelayMillis;
    }

    public final MarqueeSpacing getSpacing() {
        return Spacing;
    }

    /* JADX INFO: renamed from: getVelocity-D9Ej5fM, reason: not valid java name */
    public final float m115getVelocityD9Ej5fM() {
        return Velocity;
    }
}
