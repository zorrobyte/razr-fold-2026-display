package androidx.graphics.shapes;

/* JADX INFO: compiled from: Cubic.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MutableCubic extends Cubic {
    public MutableCubic() {
        super(null, 1, null);
    }

    private final void transformOnePoint(PointTransformer pointTransformer, int i) {
        int i2 = i + 1;
        long jMo1990transformXgqJiTY = pointTransformer.mo1990transformXgqJiTY(getPoints$graphics_shapes()[i], getPoints$graphics_shapes()[i2]);
        getPoints$graphics_shapes()[i] = Float.intBitsToFloat((int) (jMo1990transformXgqJiTY >> 32));
        getPoints$graphics_shapes()[i2] = Float.intBitsToFloat((int) (4294967295L & jMo1990transformXgqJiTY));
    }

    public final void transform(PointTransformer pointTransformer) {
        pointTransformer.getClass();
        transformOnePoint(pointTransformer, 0);
        transformOnePoint(pointTransformer, 2);
        transformOnePoint(pointTransformer, 4);
        transformOnePoint(pointTransformer, 6);
    }
}
