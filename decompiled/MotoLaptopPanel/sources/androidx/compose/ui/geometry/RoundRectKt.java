package androidx.compose.ui.geometry;

/* JADX INFO: compiled from: RoundRect.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RoundRectKt {
    public static final RoundRect RoundRect(float f, float f2, float f3, float f4, float f5, float f6) {
        long jM745constructorimpl = CornerRadius.m745constructorimpl((((long) Float.floatToRawIntBits(f5)) << 32) | (((long) Float.floatToRawIntBits(f6)) & 4294967295L));
        return new RoundRect(f, f2, f3, f4, jM745constructorimpl, jM745constructorimpl, jM745constructorimpl, jM745constructorimpl, null);
    }

    /* JADX INFO: renamed from: RoundRect-ZAM2FJo, reason: not valid java name */
    public static final RoundRect m780RoundRectZAM2FJo(Rect rect, long j, long j2, long j3, long j4) {
        return new RoundRect(rect.getLeft(), rect.getTop(), rect.getRight(), rect.getBottom(), j, j2, j3, j4, null);
    }

    /* JADX INFO: renamed from: RoundRect-gG7oq9Y, reason: not valid java name */
    public static final RoundRect m781RoundRectgG7oq9Y(float f, float f2, float f3, float f4, long j) {
        return RoundRect(f, f2, f3, f4, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
    }

    public static final Rect getBoundingRect(RoundRect roundRect) {
        return new Rect(roundRect.getLeft(), roundRect.getTop(), roundRect.getRight(), roundRect.getBottom());
    }

    public static final boolean isSimple(RoundRect roundRect) {
        long jM778getTopLeftCornerRadiuskKHJgLs = roundRect.m778getTopLeftCornerRadiuskKHJgLs();
        return (jM778getTopLeftCornerRadiuskKHJgLs >>> 32) == (jM778getTopLeftCornerRadiuskKHJgLs & 4294967295L) && roundRect.m778getTopLeftCornerRadiuskKHJgLs() == roundRect.m779getTopRightCornerRadiuskKHJgLs() && roundRect.m778getTopLeftCornerRadiuskKHJgLs() == roundRect.m777getBottomRightCornerRadiuskKHJgLs() && roundRect.m778getTopLeftCornerRadiuskKHJgLs() == roundRect.m776getBottomLeftCornerRadiuskKHJgLs();
    }
}
