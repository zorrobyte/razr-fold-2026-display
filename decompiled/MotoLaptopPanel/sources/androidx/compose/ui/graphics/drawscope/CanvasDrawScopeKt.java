package androidx.compose.ui.graphics.drawscope;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.InlineClassHelperKt;
import androidx.compose.ui.graphics.Path;

/* JADX INFO: compiled from: CanvasDrawScope.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CanvasDrawScopeKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final DrawTransform asDrawTransform(final DrawContext drawContext) {
        return new DrawTransform() { // from class: androidx.compose.ui.graphics.drawscope.CanvasDrawScopeKt.asDrawTransform.1
            @Override // androidx.compose.ui.graphics.drawscope.DrawTransform
            /* JADX INFO: renamed from: clipPath-mtrdD-E, reason: not valid java name */
            public void mo1067clipPathmtrdDE(Path path, int i) {
                drawContext.getCanvas().mo798clipPathmtrdDE(path, i);
            }

            @Override // androidx.compose.ui.graphics.drawscope.DrawTransform
            /* JADX INFO: renamed from: clipRect-N_I0leg, reason: not valid java name */
            public void mo1068clipRectN_I0leg(float f, float f2, float f3, float f4, int i) {
                drawContext.getCanvas().mo799clipRectN_I0leg(f, f2, f3, f4, i);
            }

            /* JADX INFO: renamed from: getSize-NH-jbRc, reason: not valid java name */
            public long m1069getSizeNHjbRc() {
                return drawContext.mo1065getSizeNHjbRc();
            }

            @Override // androidx.compose.ui.graphics.drawscope.DrawTransform
            public void inset(float f, float f2, float f3, float f4) {
                Canvas canvas = drawContext.getCanvas();
                DrawContext drawContext2 = drawContext;
                long jM783constructorimpl = Size.m783constructorimpl((((long) Float.floatToRawIntBits(Float.intBitsToFloat((int) (m1069getSizeNHjbRc() >> 32)) - (f3 + f))) << 32) | (((long) Float.floatToRawIntBits(Float.intBitsToFloat((int) (m1069getSizeNHjbRc() & 4294967295L)) - (f4 + f2))) & 4294967295L));
                if (!(Float.intBitsToFloat((int) (jM783constructorimpl >> 32)) >= 0.0f && Float.intBitsToFloat((int) (jM783constructorimpl & 4294967295L)) >= 0.0f)) {
                    InlineClassHelperKt.throwIllegalArgumentException("Width and height must be greater than or equal to zero");
                }
                drawContext2.mo1066setSizeuvyYCjk(jM783constructorimpl);
                canvas.translate(f, f2);
            }

            @Override // androidx.compose.ui.graphics.drawscope.DrawTransform
            /* JADX INFO: renamed from: scale-0AR0LA0, reason: not valid java name */
            public void mo1070scale0AR0LA0(float f, float f2, long j) {
                Canvas canvas = drawContext.getCanvas();
                int i = (int) (j >> 32);
                int i2 = (int) (j & 4294967295L);
                canvas.translate(Float.intBitsToFloat(i), Float.intBitsToFloat(i2));
                canvas.scale(f, f2);
                canvas.translate(-Float.intBitsToFloat(i), -Float.intBitsToFloat(i2));
            }

            @Override // androidx.compose.ui.graphics.drawscope.DrawTransform
            /* JADX INFO: renamed from: transform-58bKbWc, reason: not valid java name */
            public void mo1071transform58bKbWc(float[] fArr) {
                drawContext.getCanvas().mo800concat58bKbWc(fArr);
            }

            @Override // androidx.compose.ui.graphics.drawscope.DrawTransform
            public void translate(float f, float f2) {
                drawContext.getCanvas().translate(f, f2);
            }
        };
    }
}
