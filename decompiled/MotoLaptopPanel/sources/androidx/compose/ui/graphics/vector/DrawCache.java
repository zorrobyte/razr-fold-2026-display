package androidx.compose.ui.graphics.vector;

import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.CanvasKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.ImageBitmapConfig;
import androidx.compose.ui.graphics.ImageBitmapKt;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: DrawCache.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DrawCache {
    private Canvas cachedCanvas;
    private ImageBitmap mCachedImage;
    private Density scopeDensity;
    private LayoutDirection layoutDirection = LayoutDirection.Ltr;
    private long size = IntSize.Companion.m1927getZeroYbymL2g();
    private int config = ImageBitmapConfig.Companion.m933getArgb8888_sVssgQ();
    private final CanvasDrawScope cacheScope = new CanvasDrawScope();

    private final void clear(DrawScope drawScope) {
        DrawScope.m1077drawRectnJ9OG0$default(drawScope, Color.Companion.m891getBlack0d7_KjU(), 0L, 0L, 0.0f, null, null, BlendMode.Companion.m835getClear0nO6VwU(), 62, null);
    }

    /* JADX INFO: renamed from: drawCachedImage-FqjB98A, reason: not valid java name */
    public final void m1124drawCachedImageFqjB98A(int i, long j, Density density, LayoutDirection layoutDirection, Function1 function1) {
        this.scopeDensity = density;
        this.layoutDirection = layoutDirection;
        ImageBitmap imageBitmapM938ImageBitmapx__hDU$default = this.mCachedImage;
        Canvas Canvas = this.cachedCanvas;
        if (imageBitmapM938ImageBitmapx__hDU$default == null || Canvas == null || ((int) (j >> 32)) > imageBitmapM938ImageBitmapx__hDU$default.getWidth() || ((int) (j & 4294967295L)) > imageBitmapM938ImageBitmapx__hDU$default.getHeight() || !ImageBitmapConfig.m928equalsimpl0(this.config, i)) {
            imageBitmapM938ImageBitmapx__hDU$default = ImageBitmapKt.m938ImageBitmapx__hDU$default((int) (j >> 32), (int) (4294967295L & j), i, false, null, 24, null);
            Canvas = CanvasKt.Canvas(imageBitmapM938ImageBitmapx__hDU$default);
            this.mCachedImage = imageBitmapM938ImageBitmapx__hDU$default;
            this.cachedCanvas = Canvas;
            this.config = i;
        }
        this.size = j;
        CanvasDrawScope canvasDrawScope = this.cacheScope;
        long jM1930toSizeozmzZPI = IntSizeKt.m1930toSizeozmzZPI(j);
        CanvasDrawScope.DrawParams drawParams = canvasDrawScope.getDrawParams();
        Density densityComponent1 = drawParams.component1();
        LayoutDirection layoutDirectionComponent2 = drawParams.component2();
        Canvas canvasComponent3 = drawParams.component3();
        long jM1062component4NHjbRc = drawParams.m1062component4NHjbRc();
        CanvasDrawScope.DrawParams drawParams2 = canvasDrawScope.getDrawParams();
        drawParams2.setDensity(density);
        drawParams2.setLayoutDirection(layoutDirection);
        drawParams2.setCanvas(Canvas);
        drawParams2.m1064setSizeuvyYCjk(jM1930toSizeozmzZPI);
        Canvas.save();
        clear(canvasDrawScope);
        function1.invoke(canvasDrawScope);
        Canvas.restore();
        CanvasDrawScope.DrawParams drawParams3 = canvasDrawScope.getDrawParams();
        drawParams3.setDensity(densityComponent1);
        drawParams3.setLayoutDirection(layoutDirectionComponent2);
        drawParams3.setCanvas(canvasComponent3);
        drawParams3.m1064setSizeuvyYCjk(jM1062component4NHjbRc);
        imageBitmapM938ImageBitmapx__hDU$default.prepareToDraw();
    }

    public final void drawInto(DrawScope drawScope, float f, ColorFilter colorFilter) {
        ImageBitmap imageBitmap = this.mCachedImage;
        if (!(imageBitmap != null)) {
            InlineClassHelperKt.throwIllegalStateException("drawCachedImage must be invoked first before attempting to draw the result into another destination");
        }
        DrawScope.m1073drawImageAZ2fEMs$default(drawScope, imageBitmap, 0L, this.size, 0L, 0L, f, null, colorFilter, 0, 0, 858, null);
    }

    public final ImageBitmap getMCachedImage() {
        return this.mCachedImage;
    }
}
