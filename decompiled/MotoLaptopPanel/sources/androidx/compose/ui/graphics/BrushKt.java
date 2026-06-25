package androidx.compose.ui.graphics;

import android.graphics.Shader;

/* JADX INFO: compiled from: Brush.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class BrushKt {
    public static final ShaderBrush ShaderBrush(final Shader shader) {
        return new ShaderBrush() { // from class: androidx.compose.ui.graphics.BrushKt.ShaderBrush.1
            @Override // androidx.compose.ui.graphics.ShaderBrush
            /* JADX INFO: renamed from: createShader-uvyYCjk, reason: not valid java name */
            public Shader mo867createShaderuvyYCjk(long j) {
                return shader;
            }
        };
    }
}
