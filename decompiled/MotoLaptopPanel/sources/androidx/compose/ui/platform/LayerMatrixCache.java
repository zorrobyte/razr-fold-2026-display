package androidx.compose.ui.platform;

import android.graphics.Matrix;
import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.AndroidMatrixConversions_androidKt;
import androidx.compose.ui.graphics.MatrixKt;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: LayerMatrixCache.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LayerMatrixCache {
    private Matrix androidMatrixCache;
    private final Function2 getMatrix;
    private boolean isDirty;
    private boolean isInverseDirty;
    private float[] matrixCache = androidx.compose.ui.graphics.Matrix.m941constructorimpl$default(null, 1, null);
    private float[] inverseMatrixCache = androidx.compose.ui.graphics.Matrix.m941constructorimpl$default(null, 1, null);
    private boolean isInverseValid = true;
    private boolean isIdentity = true;

    public LayerMatrixCache(Function2 function2) {
        this.getMatrix = function2;
    }

    /* JADX INFO: renamed from: calculateInverseMatrix-bWbORWo, reason: not valid java name */
    public final float[] m1467calculateInverseMatrixbWbORWo(Object obj) {
        float[] fArr = this.inverseMatrixCache;
        if (this.isInverseDirty) {
            this.isInverseValid = InvertMatrixKt.m1466invertToJiSxe2E(m1468calculateMatrixGrdbGEg(obj), fArr);
            this.isInverseDirty = false;
        }
        if (this.isInverseValid) {
            return fArr;
        }
        return null;
    }

    /* JADX INFO: renamed from: calculateMatrix-GrdbGEg, reason: not valid java name */
    public final float[] m1468calculateMatrixGrdbGEg(Object obj) {
        float[] fArr = this.matrixCache;
        if (!this.isDirty) {
            return fArr;
        }
        Matrix matrix = this.androidMatrixCache;
        if (matrix == null) {
            matrix = new Matrix();
            this.androidMatrixCache = matrix;
        }
        this.getMatrix.invoke(obj, matrix);
        AndroidMatrixConversions_androidKt.m809setFromtUYjHk(fArr, matrix);
        this.isDirty = false;
        this.isIdentity = MatrixKt.m955isIdentity58bKbWc(fArr);
        return fArr;
    }

    public final void invalidate() {
        this.isDirty = true;
        this.isInverseDirty = true;
    }

    public final void map(Object obj, MutableRect mutableRect) {
        float[] fArrM1468calculateMatrixGrdbGEg = m1468calculateMatrixGrdbGEg(obj);
        if (this.isIdentity) {
            return;
        }
        androidx.compose.ui.graphics.Matrix.m945mapimpl(fArrM1468calculateMatrixGrdbGEg, mutableRect);
    }

    /* JADX INFO: renamed from: map-R5De75A, reason: not valid java name */
    public final long m1469mapR5De75A(Object obj, long j) {
        return !this.isIdentity ? androidx.compose.ui.graphics.Matrix.m944mapMKHz9U(m1468calculateMatrixGrdbGEg(obj), j) : j;
    }

    public final void mapInverse(Object obj, MutableRect mutableRect) {
        float[] fArrM1467calculateInverseMatrixbWbORWo = m1467calculateInverseMatrixbWbORWo(obj);
        if (fArrM1467calculateInverseMatrixbWbORWo == null) {
            mutableRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        } else {
            if (this.isIdentity) {
                return;
            }
            androidx.compose.ui.graphics.Matrix.m945mapimpl(fArrM1467calculateInverseMatrixbWbORWo, mutableRect);
        }
    }

    /* JADX INFO: renamed from: mapInverse-R5De75A, reason: not valid java name */
    public final long m1470mapInverseR5De75A(Object obj, long j) {
        float[] fArrM1467calculateInverseMatrixbWbORWo = m1467calculateInverseMatrixbWbORWo(obj);
        return fArrM1467calculateInverseMatrixbWbORWo == null ? Offset.Companion.m768getInfiniteF1C5BW0() : !this.isIdentity ? androidx.compose.ui.graphics.Matrix.m944mapMKHz9U(fArrM1467calculateInverseMatrixbWbORWo, j) : j;
    }

    public final void reset() {
        this.isDirty = false;
        this.isInverseDirty = false;
        this.isIdentity = true;
        this.isInverseValid = true;
        androidx.compose.ui.graphics.Matrix.m946resetimpl(this.matrixCache);
        androidx.compose.ui.graphics.Matrix.m946resetimpl(this.inverseMatrixCache);
    }
}
