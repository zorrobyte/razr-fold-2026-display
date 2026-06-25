package com.android.systemui.util.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimatedRotateDrawable;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.util.Log;
import com.android.app.tracing.TraceUtilsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: DrawableSize.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DrawableSize {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: DrawableSize.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final boolean isAnimated(Drawable drawable) {
            return (drawable instanceof Animatable) || (drawable instanceof Animatable2) || (drawable instanceof AnimatedImageDrawable) || (drawable instanceof AnimatedRotateDrawable) || (drawable instanceof AnimatedStateListDrawable) || (drawable instanceof AnimatedVectorDrawable);
        }

        private final boolean isSimpleBitmap(Drawable drawable) {
            return (drawable.isStateful() || isAnimated(drawable)) ? false : true;
        }

        public final Drawable downscaleToSize(Resources resources, Drawable drawable, int i, int i2) {
            Bitmap.Config config;
            Bitmap bitmap;
            Bitmap bitmap2;
            Bitmap bitmap3;
            resources.getClass();
            drawable.getClass();
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("DrawableSize#downscaleToSize");
            }
            try {
                BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
                int intrinsicWidth = (bitmapDrawable == null || (bitmap3 = bitmapDrawable.getBitmap()) == null) ? drawable.getIntrinsicWidth() : bitmap3.getWidth();
                BitmapDrawable bitmapDrawable2 = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
                int intrinsicHeight = (bitmapDrawable2 == null || (bitmap2 = bitmapDrawable2.getBitmap()) == null) ? drawable.getIntrinsicHeight() : bitmap2.getHeight();
                if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                } else if (intrinsicWidth < i && intrinsicHeight < i2) {
                    if (Log.isLoggable("SysUiDrawableSize", 3)) {
                        Log.d("SysUiDrawableSize", "Not resizing " + intrinsicWidth + " x " + intrinsicHeight + " to " + i + " x " + i2);
                    }
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                        return drawable;
                    }
                } else if (DrawableSize.Companion.isSimpleBitmap(drawable)) {
                    float f = intrinsicWidth;
                    float f2 = intrinsicHeight;
                    float fMin = Math.min(i2 / f2, i / f);
                    int i3 = (int) (f * fMin);
                    int i4 = (int) (f2 * fMin);
                    if (i3 > 0 && i4 > 0) {
                        if (Log.isLoggable("SysUiDrawableSize", 3)) {
                            Log.d("SysUiDrawableSize", "Resizing large drawable (" + drawable.getClass().getSimpleName() + ") from " + intrinsicWidth + " x " + intrinsicHeight + " to " + i3 + " x " + i4);
                        }
                        BitmapDrawable bitmapDrawable3 = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
                        if (bitmapDrawable3 == null || (bitmap = bitmapDrawable3.getBitmap()) == null || (config = bitmap.getConfig()) == null) {
                            config = Bitmap.Config.ARGB_8888;
                        }
                        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i3, i4, config);
                        bitmapCreateBitmap.getClass();
                        Canvas canvas = new Canvas(bitmapCreateBitmap);
                        Rect bounds = drawable.getBounds();
                        bounds.getClass();
                        drawable.setBounds(0, 0, i3, i4);
                        drawable.draw(canvas);
                        drawable.setBounds(bounds);
                        BitmapDrawable bitmapDrawable4 = new BitmapDrawable(resources, bitmapCreateBitmap);
                        if (zIsEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                        return bitmapDrawable4;
                    }
                    Log.w("SysUiDrawableSize", "Attempted to resize " + drawable.getClass().getSimpleName() + " from " + intrinsicWidth + " x " + intrinsicHeight + " to invalid " + i3 + " x " + i4 + ".");
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                        return drawable;
                    }
                } else if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                    return drawable;
                }
                return drawable;
            } catch (Throwable th) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }
    }

    public static final Drawable downscaleToSize(Resources resources, Drawable drawable, int i, int i2) {
        return Companion.downscaleToSize(resources, drawable, i, i2);
    }
}
