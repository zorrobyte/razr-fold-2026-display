package androidx.graphics.shapes;

import android.graphics.Matrix;
import android.graphics.Path;
import androidx.collection.FloatFloatPair;
import java.util.List;

/* JADX INFO: compiled from: Shapes.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Shapes_androidKt {
    private static final void pathFromCubics(Path path, List list) {
        path.rewind();
        int size = list.size();
        boolean z = true;
        for (int i = 0; i < size; i++) {
            Cubic cubic = (Cubic) list.get(i);
            if (z) {
                path.moveTo(cubic.getAnchor0X(), cubic.getAnchor0Y());
                z = false;
            }
            path.cubicTo(cubic.getControl0X(), cubic.getControl0Y(), cubic.getControl1X(), cubic.getControl1Y(), cubic.getAnchor1X(), cubic.getAnchor1Y());
        }
        path.close();
    }

    public static final Path toPath(Morph morph, float f, Path path) {
        morph.getClass();
        path.getClass();
        pathFromCubics(path, morph.asCubics(f));
        return path;
    }

    public static final RoundedPolygon transformed(RoundedPolygon roundedPolygon, final Matrix matrix) {
        roundedPolygon.getClass();
        matrix.getClass();
        final float[] fArr = new float[2];
        return roundedPolygon.transformed(new PointTransformer() { // from class: androidx.graphics.shapes.Shapes_androidKt.transformed.1
            @Override // androidx.graphics.shapes.PointTransformer
            /* JADX INFO: renamed from: transform-XgqJiTY */
            public final long mo1990transformXgqJiTY(float f, float f2) {
                float[] fArr2 = fArr;
                fArr2[0] = f;
                fArr2[1] = f2;
                matrix.mapPoints(fArr2);
                float[] fArr3 = fArr;
                return FloatFloatPair.m11constructorimpl(fArr3[0], fArr3[1]);
            }
        });
    }
}
