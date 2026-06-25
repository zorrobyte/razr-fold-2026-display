package androidx.compose.ui.text.style;

import androidx.compose.ui.graphics.Color;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: TextForegroundStyle.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TextDrawStyleKt {
    /* JADX INFO: renamed from: modulate-DxMtmZc, reason: not valid java name */
    public static final long m1825modulateDxMtmZc(long j, float f) {
        return (Float.isNaN(f) || f >= 1.0f) ? j : Color.m880copywmQWz5c$default(j, Color.m883getAlphaimpl(j) * f, 0.0f, 0.0f, 0.0f, 14, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float takeOrElse(float f, Function0 function0) {
        return Float.isNaN(f) ? ((Number) function0.invoke()).floatValue() : f;
    }
}
