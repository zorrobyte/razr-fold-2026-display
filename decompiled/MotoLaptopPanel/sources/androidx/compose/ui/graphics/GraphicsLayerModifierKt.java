package androidx.compose.ui.graphics;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectableValueKt;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: GraphicsLayerModifier.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class GraphicsLayerModifierKt {
    public static final Modifier graphicsLayer(Modifier modifier, Function1 function1) {
        return modifier.then(new BlockGraphicsLayerElement(function1));
    }

    /* JADX INFO: renamed from: graphicsLayer-Ap8cVGQ, reason: not valid java name */
    public static final /* synthetic */ Modifier m915graphicsLayerAp8cVGQ(Modifier modifier, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, long j, Shape shape, boolean z, RenderEffect renderEffect, long j2, long j3, int i) {
        return m917graphicsLayer_6ThJ44(modifier, f, f2, f3, f4, f5, f6, f7, f8, f9, f10, j, shape, z, renderEffect, j2, j3, i, BlendMode.Companion.m862getSrcOver0nO6VwU(), null);
    }

    /* JADX INFO: renamed from: graphicsLayer-_6ThJ44, reason: not valid java name */
    public static final Modifier m917graphicsLayer_6ThJ44(Modifier modifier, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, long j, Shape shape, boolean z, RenderEffect renderEffect, long j2, long j3, int i, int i2, ColorFilter colorFilter) {
        return modifier.then(new GraphicsLayerElement(f, f2, f3, f4, f5, f6, f7, f8, f9, f10, j, shape, z, renderEffect, j2, j3, i, i2, colorFilter, null));
    }

    /* JADX INFO: renamed from: graphicsLayer-_6ThJ44$default, reason: not valid java name */
    public static /* synthetic */ Modifier m918graphicsLayer_6ThJ44$default(Modifier modifier, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, long j, Shape shape, boolean z, RenderEffect renderEffect, long j2, long j3, int i, int i2, ColorFilter colorFilter, int i3, Object obj) {
        return m917graphicsLayer_6ThJ44(modifier, (i3 & 1) != 0 ? 1.0f : f, (i3 & 2) != 0 ? 1.0f : f2, (i3 & 4) == 0 ? f3 : 1.0f, (i3 & 8) != 0 ? 0.0f : f4, (i3 & 16) != 0 ? 0.0f : f5, (i3 & 32) != 0 ? 0.0f : f6, (i3 & 64) != 0 ? 0.0f : f7, (i3 & 128) != 0 ? 0.0f : f8, (i3 & 256) == 0 ? f9 : 0.0f, (i3 & 512) != 0 ? 8.0f : f10, (i3 & 1024) != 0 ? TransformOrigin.Companion.m1017getCenterSzJe1aQ() : j, (i3 & 2048) != 0 ? RectangleShapeKt.getRectangleShape() : shape, (i3 & 4096) != 0 ? false : z, (i3 & 8192) != 0 ? null : renderEffect, (i3 & 16384) != 0 ? GraphicsLayerScopeKt.getDefaultShadowColor() : j2, (32768 & i3) != 0 ? GraphicsLayerScopeKt.getDefaultShadowColor() : j3, (65536 & i3) != 0 ? CompositingStrategy.Companion.m906getAutoNrFUSI() : i, (i3 & 131072) != 0 ? BlendMode.Companion.m862getSrcOver0nO6VwU() : i2, (i3 & 262144) != 0 ? null : colorFilter);
    }

    public static final Modifier toolingGraphicsLayer(Modifier modifier) {
        return InspectableValueKt.isDebugInspectorInfoEnabled() ? modifier.then(m918graphicsLayer_6ThJ44$default(Modifier.Companion, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L, null, false, null, 0L, 0L, 0, 0, null, 524287, null)) : modifier;
    }
}
