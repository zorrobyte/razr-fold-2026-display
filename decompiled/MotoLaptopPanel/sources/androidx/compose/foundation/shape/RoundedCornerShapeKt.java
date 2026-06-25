package androidx.compose.foundation.shape;

/* JADX INFO: compiled from: RoundedCornerShape.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RoundedCornerShapeKt {
    private static final RoundedCornerShape CircleShape = RoundedCornerShape(50);

    public static final RoundedCornerShape RoundedCornerShape(int i) {
        return RoundedCornerShape(CornerSizeKt.CornerSize(i));
    }

    public static final RoundedCornerShape RoundedCornerShape(CornerSize cornerSize) {
        return new RoundedCornerShape(cornerSize, cornerSize, cornerSize, cornerSize);
    }

    /* JADX INFO: renamed from: RoundedCornerShape-0680j_4, reason: not valid java name */
    public static final RoundedCornerShape m192RoundedCornerShape0680j_4(float f) {
        return RoundedCornerShape(CornerSizeKt.m191CornerSize0680j_4(f));
    }

    /* JADX INFO: renamed from: RoundedCornerShape-a9UjIt4, reason: not valid java name */
    public static final RoundedCornerShape m193RoundedCornerShapea9UjIt4(float f, float f2, float f3, float f4) {
        return new RoundedCornerShape(CornerSizeKt.m191CornerSize0680j_4(f), CornerSizeKt.m191CornerSize0680j_4(f2), CornerSizeKt.m191CornerSize0680j_4(f3), CornerSizeKt.m191CornerSize0680j_4(f4));
    }

    public static final RoundedCornerShape getCircleShape() {
        return CircleShape;
    }
}
