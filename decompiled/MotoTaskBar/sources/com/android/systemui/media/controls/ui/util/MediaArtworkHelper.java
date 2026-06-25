package com.android.systemui.media.controls.ui.util;

import android.app.WallpaperColors;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import com.android.systemui.media.controls.ui.animation.MediaColorSchemesKt;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import com.android.systemui.util.ColorUtilKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: MediaArtworkHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaArtworkHelper {
    public static final MediaArtworkHelper INSTANCE = new MediaArtworkHelper();

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.util.MediaArtworkHelper$getWallpaperColor$2, reason: invalid class name */
    /* JADX INFO: compiled from: MediaArtworkHelper.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Context $applicationContext;
        final /* synthetic */ Icon $artworkIcon;
        final /* synthetic */ String $tag;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Icon icon, Context context, String str, Continuation continuation) {
            super(2, continuation);
            this.$artworkIcon = icon;
            this.$applicationContext = context;
            this.$tag = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$artworkIcon, this.$applicationContext, this.$tag, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Icon icon = this.$artworkIcon;
            if (icon == null) {
                return null;
            }
            Context context = this.$applicationContext;
            String str = this.$tag;
            if (icon.getType() != 1 && icon.getType() != 5) {
                Drawable drawableLoadDrawable = icon.loadDrawable(context);
                if (drawableLoadDrawable != null) {
                    return WallpaperColors.fromDrawable(drawableLoadDrawable);
                }
                return null;
            }
            Bitmap bitmap = icon.getBitmap();
            if (!bitmap.isRecycled()) {
                return WallpaperColors.fromBitmap(bitmap);
            }
            Log.d(str, "Cannot load wallpaper color from a recycled bitmap");
            return null;
        }
    }

    private MediaArtworkHelper() {
    }

    public final ColorScheme getColorScheme(Context context, String str, String str2, Style style) {
        context.getClass();
        str.getClass();
        str2.getClass();
        style.getClass();
        try {
            Drawable applicationIcon = context.getPackageManager().getApplicationIcon(str);
            applicationIcon.getClass();
            WallpaperColors wallpaperColorsFromDrawable = WallpaperColors.fromDrawable(applicationIcon);
            wallpaperColorsFromDrawable.getClass();
            return new ColorScheme(wallpaperColorsFromDrawable, true, style);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(str2, "Fail to get media app info", e);
            return null;
        }
    }

    public final Drawable getScaledBackground(Context context, Icon icon, int i, int i2) {
        context.getClass();
        icon.getClass();
        Drawable drawableLoadDrawable = icon.loadDrawable(context);
        Rect rect = new Rect(0, 0, i, i2);
        if (rect.width() > i || rect.height() > i2) {
            rect.offset(-((int) ((rect.width() - i) / 2.0f)), -((int) ((rect.height() - i2) / 2.0f)));
        }
        if (drawableLoadDrawable != null) {
            drawableLoadDrawable.setBounds(rect);
        }
        return drawableLoadDrawable;
    }

    public final Object getWallpaperColor(Context context, CoroutineDispatcher coroutineDispatcher, Icon icon, String str, Continuation continuation) {
        return BuildersKt.withContext(coroutineDispatcher, new AnonymousClass2(icon, context, str, null), continuation);
    }

    public final LayerDrawable setUpGradientColorOnDrawable(Drawable drawable, GradientDrawable gradientDrawable, ColorScheme colorScheme, float f, float f2) {
        gradientDrawable.getClass();
        colorScheme.getClass();
        gradientDrawable.setColors(new int[]{ColorUtilKt.getColorWithAlpha(MediaColorSchemesKt.backgroundStartFromScheme(colorScheme), f), ColorUtilKt.getColorWithAlpha(MediaColorSchemesKt.backgroundEndFromScheme(colorScheme), f2)});
        return new LayerDrawable(new Drawable[]{drawable, gradientDrawable});
    }
}
