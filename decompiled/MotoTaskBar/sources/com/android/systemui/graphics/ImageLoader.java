package com.android.systemui.graphics;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.util.Size;
import java.io.IOException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;

/* JADX INFO: compiled from: ImageLoader.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ImageLoader {
    public static final Companion Companion = new Companion(null);
    private final CoroutineDispatcher backgroundDispatcher;
    private final Context defaultContext;

    /* JADX INFO: compiled from: ImageLoader.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void configureDecoderForMaximumSize(ImageDecoder imageDecoder, Size size, int i, int i2) {
            if (i == 0 && i2 == 0) {
                return;
            }
            if (size.getWidth() > i || size.getHeight() > i2) {
                float fMin = Math.min(i <= 0 ? 1.0f : i / size.getWidth(), i2 <= 0 ? 1.0f : i2 / size.getHeight());
                if (fMin < 1.0f) {
                    int width = (int) (size.getWidth() * fMin);
                    int height = (int) (size.getHeight() * fMin);
                    if (Log.isLoggable("ImageLoader", 3)) {
                        Log.d("ImageLoader", "Configured image size to " + width + " x " + height);
                    }
                    imageDecoder.setTargetSize(width, height);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Resources resolveResourcesForIcon(Context context, Icon icon) {
            if (icon.getType() != 2) {
                return null;
            }
            Resources resources = icon.getResources();
            if (resources != null) {
                return resources;
            }
            String resPackage = icon.getResPackage();
            resPackage.getClass();
            if (resPackage.length() == 0 || context.getPackageName().equals(resPackage)) {
                return context.getResources();
            }
            if (Intrinsics.areEqual("android", resPackage)) {
                return Resources.getSystem();
            }
            PackageManager packageManager = context.getPackageManager();
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(resPackage, 9216);
                applicationInfo.getClass();
                return packageManager.getResourcesForApplication(applicationInfo);
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("ImageLoader", "Failed to resolve resource package", e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void tintDrawable(Icon icon, Drawable drawable) {
            if (icon.hasTint()) {
                drawable.mutate();
                drawable.setTintList(icon.getTintList());
                drawable.setTintBlendMode(icon.getTintBlendMode());
            }
        }
    }

    public ImageLoader(Context context, CoroutineDispatcher coroutineDispatcher) {
        context.getClass();
        coroutineDispatcher.getClass();
        this.defaultContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public static /* synthetic */ Drawable loadDrawableSync$default(ImageLoader imageLoader, Icon icon, Context context, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            context = imageLoader.defaultContext;
        }
        Context context2 = context;
        int i5 = (i4 & 4) != 0 ? 4096 : i;
        int i6 = (i4 & 8) != 0 ? 4096 : i2;
        if ((i4 & 16) != 0) {
            i3 = 0;
        }
        return imageLoader.loadDrawableSync(icon, context2, i5, i6, i3);
    }

    public final Drawable loadDrawableSync(ImageDecoder.Source source, final int i, final int i2, final int i3) {
        source.getClass();
        try {
            return ImageDecoder.decodeDrawable(source, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.graphics.ImageLoader.loadDrawableSync.1
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source2) {
                    imageDecoder.getClass();
                    imageInfo.getClass();
                    source2.getClass();
                    Companion companion = ImageLoader.Companion;
                    Size size = imageInfo.getSize();
                    size.getClass();
                    companion.configureDecoderForMaximumSize(imageDecoder, size, i, i2);
                    imageDecoder.setAllocator(i3);
                }
            });
        } catch (IOException e) {
            Log.w("ImageLoader", "Failed to load source " + source, e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.graphics.drawable.Drawable loadDrawableSync(android.graphics.drawable.Icon r4, android.content.Context r5, int r6, int r7, int r8) {
        /*
            r3 = this;
            r4.getClass()
            r5.getClass()
            int r0 = r4.getType()
            r1 = 0
            switch(r0) {
                case 1: goto L74;
                case 2: goto L53;
                case 3: goto L3b;
                case 4: goto L27;
                case 5: goto L14;
                case 6: goto L27;
                default: goto Le;
            }
        Le:
            android.graphics.drawable.Drawable r3 = r3.loadIconDrawable(r4, r5)
            goto L81
        L14:
            android.graphics.drawable.AdaptiveIconDrawable r3 = new android.graphics.drawable.AdaptiveIconDrawable
            android.graphics.drawable.BitmapDrawable r6 = new android.graphics.drawable.BitmapDrawable
            android.content.res.Resources r5 = r5.getResources()
            android.graphics.Bitmap r7 = r4.getBitmap()
            r6.<init>(r5, r7)
            r3.<init>(r1, r6)
            goto L81
        L27:
            android.content.ContentResolver r5 = r5.getContentResolver()
            android.net.Uri r0 = r4.getUri()
            android.graphics.ImageDecoder$Source r5 = android.graphics.ImageDecoder.createSource(r5, r0)
            r5.getClass()
            android.graphics.drawable.Drawable r3 = r3.loadDrawableSync(r5, r6, r7, r8)
            goto L81
        L3b:
            byte[] r5 = r4.getDataBytes()
            int r0 = r4.getDataOffset()
            int r2 = r4.getDataLength()
            android.graphics.ImageDecoder$Source r5 = android.graphics.ImageDecoder.createSource(r5, r0, r2)
            r5.getClass()
            android.graphics.drawable.Drawable r3 = r3.loadDrawableSync(r5, r6, r7, r8)
            goto L81
        L53:
            com.android.systemui.graphics.ImageLoader$Companion r0 = com.android.systemui.graphics.ImageLoader.Companion
            android.content.res.Resources r0 = com.android.systemui.graphics.ImageLoader.Companion.access$resolveResourcesForIcon(r0, r5, r4)
            if (r0 == 0) goto L6f
            int r2 = r4.getResId()
            android.graphics.ImageDecoder$Source r0 = android.graphics.ImageDecoder.createSource(r0, r2)
            r0.getClass()
            android.graphics.drawable.Drawable r6 = r3.loadDrawableSync(r0, r6, r7, r8)
            if (r6 != 0) goto L6d
            goto L6f
        L6d:
            r3 = r6
            goto L81
        L6f:
            android.graphics.drawable.Drawable r3 = r3.loadIconDrawable(r4, r5)
            goto L81
        L74:
            android.graphics.drawable.BitmapDrawable r3 = new android.graphics.drawable.BitmapDrawable
            android.content.res.Resources r5 = r5.getResources()
            android.graphics.Bitmap r6 = r4.getBitmap()
            r3.<init>(r5, r6)
        L81:
            if (r3 == 0) goto L89
            com.android.systemui.graphics.ImageLoader$Companion r5 = com.android.systemui.graphics.ImageLoader.Companion
            com.android.systemui.graphics.ImageLoader.Companion.access$tintDrawable(r5, r4, r3)
            return r3
        L89:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.graphics.ImageLoader.loadDrawableSync(android.graphics.drawable.Icon, android.content.Context, int, int, int):android.graphics.drawable.Drawable");
    }

    public final Drawable loadIconDrawable(Icon icon, Context context) {
        icon.getClass();
        context.getClass();
        Drawable drawableLoadDrawable = icon.loadDrawable(context);
        if (drawableLoadDrawable != null) {
            return drawableLoadDrawable;
        }
        Log.w("ImageLoader", "Failed to load drawable for " + icon);
        return null;
    }

    public final Size loadSizeSync(ImageDecoder.Source source) {
        source.getClass();
        try {
            return ImageDecoder.decodeHeader(source).getSize();
        } catch (IOException e) {
            Log.w("ImageLoader", "Failed to load source " + source, e);
            return null;
        }
    }

    public final Size loadSizeSync(Icon icon, Context context) {
        icon.getClass();
        context.getClass();
        int type = icon.getType();
        if (type != 4 && type != 6) {
            return null;
        }
        ImageDecoder.Source sourceCreateSource = ImageDecoder.createSource(context.getContentResolver(), icon.getUri());
        sourceCreateSource.getClass();
        return loadSizeSync(sourceCreateSource);
    }
}
