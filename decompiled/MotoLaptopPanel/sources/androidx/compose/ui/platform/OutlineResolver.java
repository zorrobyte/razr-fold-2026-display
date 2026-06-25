package androidx.compose.ui.platform;

import android.graphics.Outline;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.RoundRectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Path;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OutlineResolver.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class OutlineResolver {
    private boolean cacheIsDirty;
    private final Outline cachedOutline;
    private Path cachedRrectPath;
    private boolean isSupportedOutline = true;
    private androidx.compose.ui.graphics.Outline outline;
    private boolean outlineNeeded;
    private Path outlinePath;
    private long rectSize;
    private long rectTopLeft;
    private float roundedCornerRadius;
    private Path tmpOpPath;
    private Path tmpPath;
    private RoundRect tmpRoundRect;
    private Path tmpTouchPointPath;
    private boolean usePathForClip;

    public OutlineResolver() {
        Outline outline = new Outline();
        outline.setAlpha(1.0f);
        this.cachedOutline = outline;
        this.rectTopLeft = Offset.Companion.m770getZeroF1C5BW0();
        this.rectSize = Size.Companion.m794getZeroNHjbRc();
    }

    /* JADX INFO: renamed from: isSameBounds-4L21HEs, reason: not valid java name */
    private final boolean m1472isSameBounds4L21HEs(RoundRect roundRect, long j, long j2, float f) {
        if (roundRect == null || !RoundRectKt.isSimple(roundRect)) {
            return false;
        }
        int i = (int) (j >> 32);
        if (roundRect.getLeft() != Float.intBitsToFloat(i)) {
            return false;
        }
        int i2 = (int) (j & 4294967295L);
        return roundRect.getTop() == Float.intBitsToFloat(i2) && roundRect.getRight() == Float.intBitsToFloat(i) + Float.intBitsToFloat((int) (j2 >> 32)) && roundRect.getBottom() == Float.intBitsToFloat(i2) + Float.intBitsToFloat((int) (j2 & 4294967295L)) && Float.intBitsToFloat((int) (roundRect.m778getTopLeftCornerRadiuskKHJgLs() >> 32)) == f;
    }

    private final void updateCache() {
        if (this.cacheIsDirty) {
            this.rectTopLeft = Offset.Companion.m770getZeroF1C5BW0();
            this.roundedCornerRadius = 0.0f;
            this.outlinePath = null;
            this.cacheIsDirty = false;
            this.usePathForClip = false;
            androidx.compose.ui.graphics.Outline outline = this.outline;
            if (outline == null || !this.outlineNeeded || Float.intBitsToFloat((int) (this.rectSize >> 32)) <= 0.0f || Float.intBitsToFloat((int) (this.rectSize & 4294967295L)) <= 0.0f) {
                this.cachedOutline.setEmpty();
                return;
            }
            this.isSupportedOutline = true;
            if (outline instanceof Outline.Rectangle) {
                updateCacheWithRect(((Outline.Rectangle) outline).getRect());
            } else if (outline instanceof Outline.Rounded) {
                updateCacheWithRoundRect(((Outline.Rounded) outline).getRoundRect());
            } else if (outline instanceof Outline.Generic) {
                updateCacheWithPath(((Outline.Generic) outline).getPath());
            }
        }
    }

    private final void updateCacheWithPath(Path path) {
        OutlineVerificationHelper.INSTANCE.setPath(this.cachedOutline, path);
        this.usePathForClip = !this.cachedOutline.canClip();
        this.outlinePath = path;
    }

    private final void updateCacheWithRect(Rect rect) {
        float left = rect.getLeft();
        this.rectTopLeft = Offset.m752constructorimpl((((long) Float.floatToRawIntBits(rect.getTop())) & 4294967295L) | (Float.floatToRawIntBits(left) << 32));
        float right = rect.getRight() - rect.getLeft();
        this.rectSize = Size.m783constructorimpl((((long) Float.floatToRawIntBits(rect.getBottom() - rect.getTop())) & 4294967295L) | (Float.floatToRawIntBits(right) << 32));
        this.cachedOutline.setRect(Math.round(rect.getLeft()), Math.round(rect.getTop()), Math.round(rect.getRight()), Math.round(rect.getBottom()));
    }

    private final void updateCacheWithRoundRect(RoundRect roundRect) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (roundRect.m778getTopLeftCornerRadiuskKHJgLs() >> 32));
        float left = roundRect.getLeft();
        this.rectTopLeft = Offset.m752constructorimpl((((long) Float.floatToRawIntBits(roundRect.getTop())) & 4294967295L) | (Float.floatToRawIntBits(left) << 32));
        float width = roundRect.getWidth();
        this.rectSize = Size.m783constructorimpl((((long) Float.floatToRawIntBits(roundRect.getHeight())) & 4294967295L) | (Float.floatToRawIntBits(width) << 32));
        if (RoundRectKt.isSimple(roundRect)) {
            this.cachedOutline.setRoundRect(Math.round(roundRect.getLeft()), Math.round(roundRect.getTop()), Math.round(roundRect.getRight()), Math.round(roundRect.getBottom()), fIntBitsToFloat);
            this.roundedCornerRadius = fIntBitsToFloat;
            return;
        }
        Path Path = this.cachedRrectPath;
        if (Path == null) {
            Path = AndroidPath_androidKt.Path();
            this.cachedRrectPath = Path;
        }
        Path.reset();
        Path.addRoundRect$default(Path, roundRect, null, 2, null);
        updateCacheWithPath(Path);
    }

    public final void clipToOutline(Canvas canvas) {
        Path clipPath = getClipPath();
        if (clipPath != null) {
            Canvas.m868clipPathmtrdDE$default(canvas, clipPath, 0, 2, null);
            return;
        }
        float f = this.roundedCornerRadius;
        if (f <= 0.0f) {
            Canvas.m869clipRectN_I0leg$default(canvas, Float.intBitsToFloat((int) (this.rectTopLeft >> 32)), Float.intBitsToFloat((int) (this.rectTopLeft & 4294967295L)), Float.intBitsToFloat((int) (this.rectTopLeft >> 32)) + Float.intBitsToFloat((int) (this.rectSize >> 32)), Float.intBitsToFloat((int) (this.rectTopLeft & 4294967295L)) + Float.intBitsToFloat((int) (this.rectSize & 4294967295L)), 0, 16, null);
            return;
        }
        Path Path = this.tmpPath;
        RoundRect roundRect = this.tmpRoundRect;
        if (Path == null || !m1472isSameBounds4L21HEs(roundRect, this.rectTopLeft, this.rectSize, f)) {
            float fIntBitsToFloat = Float.intBitsToFloat((int) (this.rectTopLeft >> 32));
            float fIntBitsToFloat2 = Float.intBitsToFloat((int) (this.rectTopLeft & 4294967295L));
            float fIntBitsToFloat3 = Float.intBitsToFloat((int) (this.rectTopLeft >> 32)) + Float.intBitsToFloat((int) (this.rectSize >> 32));
            float fIntBitsToFloat4 = Float.intBitsToFloat((int) (this.rectTopLeft & 4294967295L)) + Float.intBitsToFloat((int) (this.rectSize & 4294967295L));
            float f2 = this.roundedCornerRadius;
            RoundRect roundRectM781RoundRectgG7oq9Y = RoundRectKt.m781RoundRectgG7oq9Y(fIntBitsToFloat, fIntBitsToFloat2, fIntBitsToFloat3, fIntBitsToFloat4, CornerRadius.m745constructorimpl((((long) Float.floatToRawIntBits(f2)) << 32) | (((long) Float.floatToRawIntBits(f2)) & 4294967295L)));
            if (Path == null) {
                Path = AndroidPath_androidKt.Path();
            } else {
                Path.reset();
            }
            Path.addRoundRect$default(Path, roundRectM781RoundRectgG7oq9Y, null, 2, null);
            this.tmpRoundRect = roundRectM781RoundRectgG7oq9Y;
            this.tmpPath = Path;
        }
        Canvas.m868clipPathmtrdDE$default(canvas, Path, 0, 2, null);
    }

    public final android.graphics.Outline getAndroidOutline() {
        updateCache();
        if (this.outlineNeeded && this.isSupportedOutline) {
            return this.cachedOutline;
        }
        return null;
    }

    public final boolean getCacheIsDirty$ui_release() {
        return this.cacheIsDirty;
    }

    public final Path getClipPath() {
        updateCache();
        return this.outlinePath;
    }

    public final boolean getOutlineClipSupported() {
        return !this.usePathForClip;
    }

    /* JADX INFO: renamed from: isInOutline-k-4lQ0M, reason: not valid java name */
    public final boolean m1473isInOutlinek4lQ0M(long j) {
        androidx.compose.ui.graphics.Outline outline;
        if (this.outlineNeeded && (outline = this.outline) != null) {
            return ShapeContainingUtilKt.isInOutline(outline, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)), this.tmpTouchPointPath, this.tmpOpPath);
        }
        return true;
    }

    /* JADX INFO: renamed from: update-S_szKao, reason: not valid java name */
    public final boolean m1474updateS_szKao(androidx.compose.ui.graphics.Outline outline, float f, boolean z, float f2, long j) {
        this.cachedOutline.setAlpha(f);
        boolean zAreEqual = Intrinsics.areEqual(this.outline, outline);
        boolean z2 = !zAreEqual;
        if (!zAreEqual) {
            this.outline = outline;
            this.cacheIsDirty = true;
        }
        this.rectSize = j;
        boolean z3 = outline != null && (z || f2 > 0.0f);
        if (this.outlineNeeded != z3) {
            this.outlineNeeded = z3;
            this.cacheIsDirty = true;
        }
        return z2;
    }
}
