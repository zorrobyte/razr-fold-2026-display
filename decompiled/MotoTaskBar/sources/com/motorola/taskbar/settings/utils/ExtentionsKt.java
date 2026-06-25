package com.motorola.taskbar.settings.utils;

import android.graphics.Bitmap;
import android.graphics.Point;
import com.android.systemui.plugins.DarkIconDispatcher;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Extentions.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class ExtentionsKt {
    public static final Bitmap applyFillTransformation(Bitmap bitmap, Point point) {
        bitmap.getClass();
        point.getClass();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(point.x, point.y, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.getClass();
        bitmapCreateBitmap.eraseColor(DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT);
        int width = (bitmap.getWidth() - bitmapCreateBitmap.getWidth()) / 2;
        int height = (bitmap.getHeight() - bitmapCreateBitmap.getHeight()) / 2;
        int[] iArr = new int[RangesKt.coerceAtMost(bitmapCreateBitmap.getWidth(), bitmap.getWidth()) * RangesKt.coerceAtMost(bitmapCreateBitmap.getHeight(), bitmap.getHeight())];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), RangesKt.coerceAtLeast(0, width), RangesKt.coerceAtLeast(0, height), RangesKt.coerceAtMost(bitmapCreateBitmap.getWidth(), bitmap.getWidth()), RangesKt.coerceAtMost(bitmapCreateBitmap.getHeight(), bitmap.getHeight()));
        bitmapCreateBitmap.setPixels(iArr, 0, bitmap.getWidth(), RangesKt.coerceAtLeast(0, width * (-1)), RangesKt.coerceAtLeast(0, height * (-1)), RangesKt.coerceAtMost(bitmapCreateBitmap.getWidth(), bitmap.getWidth()), RangesKt.coerceAtMost(bitmapCreateBitmap.getHeight(), bitmap.getHeight()));
        return bitmapCreateBitmap;
    }
}
