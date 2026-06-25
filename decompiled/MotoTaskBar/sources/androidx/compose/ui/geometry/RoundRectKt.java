package androidx.compose.ui.geometry;

/* JADX INFO: compiled from: RoundRect.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RoundRectKt {
    public static final RoundRect RoundRect(float f, float f2, float f3, float f4, float f5, float f6) {
        long jM175constructorimpl = CornerRadius.m175constructorimpl((((long) Float.floatToRawIntBits(f5)) << 32) | (((long) Float.floatToRawIntBits(f6)) & 4294967295L));
        return new RoundRect(f, f2, f3, f4, jM175constructorimpl, jM175constructorimpl, jM175constructorimpl, jM175constructorimpl, null);
    }

    /* JADX INFO: renamed from: RoundRect-gG7oq9Y, reason: not valid java name */
    public static final RoundRect m205RoundRectgG7oq9Y(float f, float f2, float f3, float f4, long j) {
        return RoundRect(f, f2, f3, f4, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
    }

    public static final Rect getBoundingRect(RoundRect roundRect) {
        return new Rect(roundRect.getLeft(), roundRect.getTop(), roundRect.getRight(), roundRect.getBottom());
    }

    public static final boolean isSimple(RoundRect roundRect) {
        long jM203getTopLeftCornerRadiuskKHJgLs = roundRect.m203getTopLeftCornerRadiuskKHJgLs();
        return (jM203getTopLeftCornerRadiuskKHJgLs >>> 32) == (jM203getTopLeftCornerRadiuskKHJgLs & 4294967295L) && roundRect.m203getTopLeftCornerRadiuskKHJgLs() == roundRect.m204getTopRightCornerRadiuskKHJgLs() && roundRect.m203getTopLeftCornerRadiuskKHJgLs() == roundRect.m202getBottomRightCornerRadiuskKHJgLs() && roundRect.m203getTopLeftCornerRadiuskKHJgLs() == roundRect.m201getBottomLeftCornerRadiuskKHJgLs();
    }
}
