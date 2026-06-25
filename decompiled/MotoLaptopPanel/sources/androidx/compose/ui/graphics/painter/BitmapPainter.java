package androidx.compose.ui.graphics.painter;

import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.FilterQuality;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BitmapPainter.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BitmapPainter extends Painter {
    private float alpha;
    private ColorFilter colorFilter;
    private int filterQuality;
    private final ImageBitmap image;
    private final long size;
    private final long srcOffset;
    private final long srcSize;

    private BitmapPainter(ImageBitmap imageBitmap, long j, long j2) {
        this.image = imageBitmap;
        this.srcOffset = j;
        this.srcSize = j2;
        this.filterQuality = FilterQuality.Companion.m913getLowfv9h1I();
        this.size = m1121validateSizeN5eqBDc(j, j2);
        this.alpha = 1.0f;
    }

    public /* synthetic */ BitmapPainter(ImageBitmap imageBitmap, long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(imageBitmap, (i & 2) != 0 ? IntOffset.Companion.m1913getZeronOccac() : j, (i & 4) != 0 ? IntSize.m1919constructorimpl((((long) imageBitmap.getHeight()) & 4294967295L) | (((long) imageBitmap.getWidth()) << 32)) : j2, null);
    }

    public /* synthetic */ BitmapPainter(ImageBitmap imageBitmap, long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(imageBitmap, j, j2);
    }

    /* JADX INFO: renamed from: validateSize-N5eqBDc, reason: not valid java name */
    private final long m1121validateSizeN5eqBDc(long j, long j2) {
        int i;
        int i2;
        if (IntOffset.m1905getXimpl(j) < 0 || IntOffset.m1906getYimpl(j) < 0 || (i = (int) (j2 >> 32)) < 0 || (i2 = (int) (4294967295L & j2)) < 0 || i > this.image.getWidth() || i2 > this.image.getHeight()) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        return j2;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    protected boolean applyAlpha(float f) {
        this.alpha = f;
        return true;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    protected boolean applyColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BitmapPainter)) {
            return false;
        }
        BitmapPainter bitmapPainter = (BitmapPainter) obj;
        return Intrinsics.areEqual(this.image, bitmapPainter.image) && IntOffset.m1904equalsimpl0(this.srcOffset, bitmapPainter.srcOffset) && IntSize.m1921equalsimpl0(this.srcSize, bitmapPainter.srcSize) && FilterQuality.m910equalsimpl0(this.filterQuality, bitmapPainter.filterQuality);
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    /* JADX INFO: renamed from: getIntrinsicSize-NH-jbRc, reason: not valid java name */
    public long mo1122getIntrinsicSizeNHjbRc() {
        return IntSizeKt.m1930toSizeozmzZPI(this.size);
    }

    public int hashCode() {
        return (((((this.image.hashCode() * 31) + IntOffset.m1907hashCodeimpl(this.srcOffset)) * 31) + IntSize.m1924hashCodeimpl(this.srcSize)) * 31) + FilterQuality.m911hashCodeimpl(this.filterQuality);
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    protected void onDraw(DrawScope drawScope) {
        ImageBitmap imageBitmap = this.image;
        long j = this.srcOffset;
        long j2 = this.srcSize;
        int iRound = Math.round(Float.intBitsToFloat((int) (drawScope.mo1082getSizeNHjbRc() >> 32)));
        long j3 = ((long) iRound) << 32;
        DrawScope.m1073drawImageAZ2fEMs$default(drawScope, imageBitmap, j, j2, 0L, IntSize.m1919constructorimpl((((long) Math.round(Float.intBitsToFloat((int) (drawScope.mo1082getSizeNHjbRc() & 4294967295L)))) & 4294967295L) | j3), this.alpha, null, this.colorFilter, 0, this.filterQuality, 328, null);
    }

    public String toString() {
        return "BitmapPainter(image=" + this.image + ", srcOffset=" + ((Object) IntOffset.m1910toStringimpl(this.srcOffset)) + ", srcSize=" + ((Object) IntSize.m1925toStringimpl(this.srcSize)) + ", filterQuality=" + ((Object) FilterQuality.m912toStringimpl(this.filterQuality)) + ')';
    }
}
