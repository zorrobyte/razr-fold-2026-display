package androidx.compose.ui.graphics;

import android.graphics.Shader;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Brush.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ShaderBrush extends Brush {
    private long createdSize;
    private Shader internalShader;

    public ShaderBrush() {
        super(null);
        this.createdSize = Size.Companion.m793getUnspecifiedNHjbRc();
    }

    @Override // androidx.compose.ui.graphics.Brush
    /* JADX INFO: renamed from: applyTo-Pq9zytI */
    public final void mo866applyToPq9zytI(long j, Paint paint, float f) {
        Shader shaderMo867createShaderuvyYCjk = this.internalShader;
        if (shaderMo867createShaderuvyYCjk == null || !Size.m785equalsimpl0(this.createdSize, j)) {
            if (Size.m790isEmptyimpl(j)) {
                shaderMo867createShaderuvyYCjk = null;
                this.internalShader = null;
                this.createdSize = Size.Companion.m793getUnspecifiedNHjbRc();
            } else {
                shaderMo867createShaderuvyYCjk = mo867createShaderuvyYCjk(j);
                this.internalShader = shaderMo867createShaderuvyYCjk;
                this.createdSize = j;
            }
        }
        long jMo811getColor0d7_KjU = paint.mo811getColor0d7_KjU();
        Color.Companion companion = Color.Companion;
        if (!Color.m882equalsimpl0(jMo811getColor0d7_KjU, companion.m891getBlack0d7_KjU())) {
            paint.mo816setColor8_81llA(companion.m891getBlack0d7_KjU());
        }
        if (!Intrinsics.areEqual(paint.getShader(), shaderMo867createShaderuvyYCjk)) {
            paint.setShader(shaderMo867createShaderuvyYCjk);
        }
        if (paint.getAlpha() == f) {
            return;
        }
        paint.setAlpha(f);
    }

    /* JADX INFO: renamed from: createShader-uvyYCjk */
    public abstract Shader mo867createShaderuvyYCjk(long j);
}
