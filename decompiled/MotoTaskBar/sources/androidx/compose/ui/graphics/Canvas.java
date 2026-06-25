package androidx.compose.ui.graphics;

import androidx.compose.ui.geometry.Rect;

/* JADX INFO: compiled from: Canvas.kt */
/* JADX INFO: loaded from: classes.dex */
public interface Canvas {
    /* JADX INFO: renamed from: clipPath-mtrdD-E$default, reason: not valid java name */
    static /* synthetic */ void m264clipPathmtrdDE$default(Canvas canvas, Path path, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: clipPath-mtrdD-E");
        }
        if ((i2 & 2) != 0) {
            i = ClipOp.Companion.m271getIntersectrtfAjoo();
        }
        canvas.mo215clipPathmtrdDE(path, i);
    }

    /* JADX INFO: renamed from: clipRect-N_I0leg$default, reason: not valid java name */
    static /* synthetic */ void m265clipRectN_I0leg$default(Canvas canvas, float f, float f2, float f3, float f4, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: clipRect-N_I0leg");
        }
        if ((i2 & 16) != 0) {
            i = ClipOp.Companion.m271getIntersectrtfAjoo();
        }
        canvas.mo216clipRectN_I0leg(f, f2, f3, f4, i);
    }

    /* JADX INFO: renamed from: clipRect-mtrdD-E$default, reason: not valid java name */
    static /* synthetic */ void m266clipRectmtrdDE$default(Canvas canvas, Rect rect, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: clipRect-mtrdD-E");
        }
        if ((i2 & 2) != 0) {
            i = ClipOp.Companion.m271getIntersectrtfAjoo();
        }
        canvas.m267clipRectmtrdDE(rect, i);
    }

    /* JADX INFO: renamed from: clipPath-mtrdD-E */
    void mo215clipPathmtrdDE(Path path, int i);

    /* JADX INFO: renamed from: clipRect-N_I0leg */
    void mo216clipRectN_I0leg(float f, float f2, float f3, float f4, int i);

    /* JADX INFO: renamed from: clipRect-mtrdD-E, reason: not valid java name */
    default void m267clipRectmtrdDE(Rect rect, int i) {
        mo216clipRectN_I0leg(rect.getLeft(), rect.getTop(), rect.getRight(), rect.getBottom(), i);
    }

    /* JADX INFO: renamed from: concat-58bKbWc */
    void mo217concat58bKbWc(float[] fArr);

    void disableZ();

    void drawRect(float f, float f2, float f3, float f4, Paint paint);

    void enableZ();

    void restore();

    void save();

    void translate(float f, float f2);
}
