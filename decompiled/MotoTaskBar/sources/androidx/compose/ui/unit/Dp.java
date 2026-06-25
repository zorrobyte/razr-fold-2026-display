package androidx.compose.ui.unit;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Dp.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Dp implements Comparable {
    public static final Companion Companion = new Companion(null);
    private static final float Hairline = m989constructorimpl(0.0f);
    private static final float Infinity = m989constructorimpl(Float.POSITIVE_INFINITY);
    private static final float Unspecified = m989constructorimpl(Float.NaN);

    /* JADX INFO: compiled from: Dp.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static float m989constructorimpl(float f) {
        return f;
    }
}
