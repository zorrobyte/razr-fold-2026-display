package com.android.launcher3.icons.mono;

import android.content.Context;
import android.graphics.Bitmap;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.ThemedBitmap;
import com.android.launcher3.icons.mono.ThemedIconDrawable;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: MonoThemedBitmap.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MonoThemedBitmap implements ThemedBitmap {
    private final Function1 colorProvider;
    private final Bitmap mono;
    private final Bitmap whiteShadowLayer;

    public MonoThemedBitmap(Bitmap bitmap, Bitmap bitmap2, Function1 function1) {
        bitmap.getClass();
        bitmap2.getClass();
        this.mono = bitmap;
        this.whiteShadowLayer = bitmap2;
        this.colorProvider = function1;
    }

    @Override // com.android.launcher3.icons.ThemedBitmap
    public FastBitmapDrawable newDrawable(BitmapInfo bitmapInfo, Context context) {
        int[] colors;
        bitmapInfo.getClass();
        context.getClass();
        Function1 function1 = this.colorProvider;
        if (function1 == null || (colors = (int[]) function1.invoke(context)) == null) {
            colors = ThemedIconDrawable.Companion.getColors(context);
        }
        FastBitmapDrawable fastBitmapDrawableNewDrawable = new ThemedIconDrawable.ThemedConstantState(bitmapInfo, this.mono, this.whiteShadowLayer, colors[0], colors[1]).newDrawable();
        fastBitmapDrawableNewDrawable.getClass();
        return fastBitmapDrawableNewDrawable;
    }
}
