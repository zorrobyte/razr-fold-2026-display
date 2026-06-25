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
    private float[] matrixCache = androidx.compose.ui.graphics.Matrix.m305constructorimpl$default(null, 1, null);
    private float[] inverseMatrixCache = androidx.compose.ui.graphics.Matrix.m305constructorimpl$default(null, 1, null);
    private boolean isInverseValid = true;
    private boolean isIdentity = true;

    public LayerMatrixCache(Function2 function2) {
        this.getMatrix = function2;
    }

    /* JADX INFO: renamed from: calculateInverseMatrix-bWbORWo, reason: not valid java name */
    public final float[] m718calculateInverseMatrixbWbORWo(Object obj) {
        float[] fArr = this.inverseMatrixCache;
        if (this.isInverseDirty) {
            this.isInverseValid = InvertMatrixKt.m717invertToJiSxe2E(m719calculateMatrixGrdbGEg(obj), fArr);
            this.isInverseDirty = false;
        }
        if (this.isInverseValid) {
            return fArr;
        }
        return null;
    }

    /* JADX INFO: renamed from: calculateMatrix-GrdbGEg, reason: not valid java name */
    public final float[] m719calculateMatrixGrdbGEg(Object obj) {
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
        AndroidMatrixConversions_androidKt.m220setFromtUYjHk(fArr, matrix);
        this.isDirty = false;
        this.isIdentity = MatrixKt.m317isIdentity58bKbWc(fArr);
        return fArr;
    }

    public final void invalidate() {
        this.isDirty = true;
        this.isInverseDirty = true;
    }

    public final void map(Object obj, MutableRect mutableRect) {
        float[] fArrM719calculateMatrixGrdbGEg = m719calculateMatrixGrdbGEg(obj);
        if (this.isIdentity) {
            return;
        }
        androidx.compose.ui.graphics.Matrix.m309mapimpl(fArrM719calculateMatrixGrdbGEg, mutableRect);
    }

    /* JADX INFO: renamed from: map-R5De75A, reason: not valid java name */
    public final long m720mapR5De75A(Object obj, long j) {
        return !this.isIdentity ? androidx.compose.ui.graphics.Matrix.m308mapMKHz9U(m719calculateMatrixGrdbGEg(obj), j) : j;
    }

    public final void mapInverse(Object obj, MutableRect mutableRect) {
        float[] fArrM718calculateInverseMatrixbWbORWo = m718calculateInverseMatrixbWbORWo(obj);
        if (fArrM718calculateInverseMatrixbWbORWo == null) {
            mutableRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        } else {
            if (this.isIdentity) {
                return;
            }
            androidx.compose.ui.graphics.Matrix.m309mapimpl(fArrM718calculateInverseMatrixbWbORWo, mutableRect);
        }
    }

    /* JADX INFO: renamed from: mapInverse-R5De75A, reason: not valid java name */
    public final long m721mapInverseR5De75A(Object obj, long j) {
        float[] fArrM718calculateInverseMatrixbWbORWo = m718calculateInverseMatrixbWbORWo(obj);
        return fArrM718calculateInverseMatrixbWbORWo == null ? Offset.Companion.m193getInfiniteF1C5BW0() : !this.isIdentity ? androidx.compose.ui.graphics.Matrix.m308mapMKHz9U(fArrM718calculateInverseMatrixbWbORWo, j) : j;
    }

    public final void reset() {
        this.isDirty = false;
        this.isInverseDirty = false;
        this.isIdentity = true;
        this.isInverseValid = true;
        androidx.compose.ui.graphics.Matrix.m310resetimpl(this.matrixCache);
        androidx.compose.ui.graphics.Matrix.m310resetimpl(this.inverseMatrixCache);
    }
}
