package H;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;

/* JADX INFO: loaded from: classes.dex */
public final class b implements TypeEvaluator {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final float[] f146a = new float[9];

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final float[] f147b = new float[9];

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Matrix f148c = new Matrix();

    @Override // android.animation.TypeEvaluator
    public final Object evaluate(float f2, Object obj, Object obj2) {
        float[] fArr = this.f146a;
        ((Matrix) obj).getValues(fArr);
        float[] fArr2 = this.f147b;
        ((Matrix) obj2).getValues(fArr2);
        for (int i2 = 0; i2 < 9; i2++) {
            float f3 = fArr2[i2];
            float f4 = fArr[i2];
            fArr2[i2] = ((f3 - f4) * f2) + f4;
        }
        Matrix matrix = this.f148c;
        matrix.setValues(fArr2);
        return matrix;
    }
}
