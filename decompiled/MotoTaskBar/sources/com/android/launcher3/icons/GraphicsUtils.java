package com.android.launcher3.icons;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import androidx.core.graphics.PathParser;
import com.android.systemui.plugins.DarkIconDispatcher;

/* JADX INFO: loaded from: classes.dex */
public abstract class GraphicsUtils {
    public static Runnable sOnNewBitmapRunnable = new Runnable() { // from class: com.android.launcher3.icons.GraphicsUtils$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            GraphicsUtils.m1145$r8$lambda$qYkZUT8okxcJ16ya9hSJJxzWUk();
        }
    };

    /* JADX INFO: renamed from: $r8$lambda$qYkZUT8okxcJ16ya9hSJJxz-WUk, reason: not valid java name */
    public static /* synthetic */ void m1145$r8$lambda$qYkZUT8okxcJ16ya9hSJJxzWUk() {
    }

    public static int getArea(Region region) {
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rect = new Rect();
        int iWidth = 0;
        while (regionIterator.next(rect)) {
            iWidth += rect.width() * rect.height();
        }
        return iWidth;
    }

    public static int getAttrColor(Context context, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        int color = typedArrayObtainStyledAttributes.getColor(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        return color;
    }

    public static float getFloat(Context context, int i, float f) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        float f2 = typedArrayObtainStyledAttributes.getFloat(0, f);
        typedArrayObtainStyledAttributes.recycle();
        return f2;
    }

    public static Path getShapePath(Context context, int i) {
        Path pathCreatePathFromPathData;
        int i2 = IconProvider.CONFIG_ICON_MASK_RES_ID;
        if (i2 == 0 || (pathCreatePathFromPathData = PathParser.createPathFromPathData(context.getString(i2))) == null) {
            AdaptiveIconDrawable adaptiveIconDrawable = new AdaptiveIconDrawable(new ColorDrawable(DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT), new ColorDrawable(DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT));
            adaptiveIconDrawable.setBounds(0, 0, i, i);
            return new Path(adaptiveIconDrawable.getIconMask());
        }
        float f = i;
        if (f != 100.0f) {
            Matrix matrix = new Matrix();
            float f2 = f / 100.0f;
            matrix.setScale(f2, f2);
            pathCreatePathFromPathData.transform(matrix);
        }
        return pathCreatePathFromPathData;
    }

    public static void noteNewBitmapCreated() {
        sOnNewBitmapRunnable.run();
    }
}
