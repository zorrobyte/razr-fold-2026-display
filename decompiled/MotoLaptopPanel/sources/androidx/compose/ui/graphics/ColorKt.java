package androidx.compose.ui.graphics;

import androidx.compose.ui.graphics.colorspace.ColorSpaces;
import kotlin.ULong;

/* JADX INFO: compiled from: Color.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ColorKt {
    /* JADX WARN: Removed duplicated region for block: B:110:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0129  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final long Color(float r20, float r21, float r22, float r23, androidx.compose.ui.graphics.colorspace.ColorSpace r24) {
        /*
            Method dump skipped, instruction units count: 506
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.ColorKt.Color(float, float, float, float, androidx.compose.ui.graphics.colorspace.ColorSpace):long");
    }

    public static final long Color(int i) {
        return Color.m877constructorimpl(ULong.m2196constructorimpl(ULong.m2196constructorimpl(i) << 32));
    }

    public static final long Color(int i, int i2, int i3, int i4) {
        return Color(((i & 255) << 16) | ((i4 & 255) << 24) | ((i2 & 255) << 8) | (i3 & 255));
    }

    public static final long Color(long j) {
        return Color.m877constructorimpl(ULong.m2196constructorimpl(j << 32));
    }

    public static /* synthetic */ long Color$default(int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 8) != 0) {
            i4 = 255;
        }
        return Color(i, i2, i3, i4);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final long UncheckedColor(float r17, float r18, float r19, float r20, androidx.compose.ui.graphics.colorspace.ColorSpace r21) {
        /*
            Method dump skipped, instruction units count: 353
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.ColorKt.UncheckedColor(float, float, float, float, androidx.compose.ui.graphics.colorspace.ColorSpace):long");
    }

    /* JADX INFO: renamed from: compositeOver--OWjLjI, reason: not valid java name */
    public static final long m899compositeOverOWjLjI(long j, long j2) {
        long jM878convertvNxB06k = Color.m878convertvNxB06k(j, Color.m885getColorSpaceimpl(j2));
        float fM883getAlphaimpl = Color.m883getAlphaimpl(j2);
        float fM883getAlphaimpl2 = Color.m883getAlphaimpl(jM878convertvNxB06k);
        float f = 1.0f - fM883getAlphaimpl2;
        float f2 = (fM883getAlphaimpl * f) + fM883getAlphaimpl2;
        return UncheckedColor(f2 == 0.0f ? 0.0f : ((Color.m887getRedimpl(jM878convertvNxB06k) * fM883getAlphaimpl2) + ((Color.m887getRedimpl(j2) * fM883getAlphaimpl) * f)) / f2, f2 == 0.0f ? 0.0f : ((Color.m886getGreenimpl(jM878convertvNxB06k) * fM883getAlphaimpl2) + ((Color.m886getGreenimpl(j2) * fM883getAlphaimpl) * f)) / f2, f2 != 0.0f ? ((Color.m884getBlueimpl(jM878convertvNxB06k) * fM883getAlphaimpl2) + ((Color.m884getBlueimpl(j2) * fM883getAlphaimpl) * f)) / f2 : 0.0f, f2, Color.m885getColorSpaceimpl(j2));
    }

    /* JADX INFO: renamed from: toArgb-8_81llA, reason: not valid java name */
    public static final int m900toArgb8_81llA(long j) {
        return (int) ULong.m2196constructorimpl(Color.m878convertvNxB06k(j, ColorSpaces.INSTANCE.getSrgb()) >>> 32);
    }
}
