package androidx.compose.ui.text.platform.style;

import android.graphics.Paint;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;

/* JADX INFO: compiled from: DrawStyleSpan.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DrawStyleSpan_androidKt {
    /* JADX INFO: renamed from: toAndroidCap-BeK7IIE, reason: not valid java name */
    public static final Paint.Cap m1713toAndroidCapBeK7IIE(int i) {
        StrokeCap.Companion companion = StrokeCap.Companion;
        return StrokeCap.m995equalsimpl0(i, companion.m998getButtKaPHkGw()) ? Paint.Cap.BUTT : StrokeCap.m995equalsimpl0(i, companion.m999getRoundKaPHkGw()) ? Paint.Cap.ROUND : StrokeCap.m995equalsimpl0(i, companion.m1000getSquareKaPHkGw()) ? Paint.Cap.SQUARE : Paint.Cap.BUTT;
    }

    /* JADX INFO: renamed from: toAndroidJoin-Ww9F2mQ, reason: not valid java name */
    public static final Paint.Join m1714toAndroidJoinWw9F2mQ(int i) {
        StrokeJoin.Companion companion = StrokeJoin.Companion;
        return StrokeJoin.m1002equalsimpl0(i, companion.m1006getMiterLxFBmk8()) ? Paint.Join.MITER : StrokeJoin.m1002equalsimpl0(i, companion.m1007getRoundLxFBmk8()) ? Paint.Join.ROUND : StrokeJoin.m1002equalsimpl0(i, companion.m1005getBevelLxFBmk8()) ? Paint.Join.BEVEL : Paint.Join.MITER;
    }
}
