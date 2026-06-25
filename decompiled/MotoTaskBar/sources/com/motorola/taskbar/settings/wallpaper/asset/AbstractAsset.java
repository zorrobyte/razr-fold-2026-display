package com.motorola.taskbar.settings.wallpaper.asset;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: AbstractAsset.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractAsset implements IAsset {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: AbstractAsset.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadDrawable$lambda$0(boolean z, ImageView imageView, Context context, Drawable drawable, Bitmap bitmap) {
        if (!z) {
            imageView.setImageBitmap(bitmap);
            return Unit.INSTANCE;
        }
        Resources resources = context.getResources();
        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{drawable, new BitmapDrawable(resources, bitmap)});
        transitionDrawable.setCrossFadeEnabled(true);
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(resources.getInteger(R.integer.config_shortAnimTime));
        return Unit.INSTANCE;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void loadDrawable(final Context context, final ImageView imageView, int i) {
        context.getClass();
        imageView.getClass();
        final boolean z = imageView.getDrawable() == null;
        final ColorDrawable colorDrawable = new ColorDrawable(i);
        if (z) {
            imageView.setImageDrawable(colorDrawable);
        }
        decodeBitmap(imageView.getWidth() > 0 ? imageView.getWidth() : Math.abs(imageView.getLayoutParams().width), imageView.getHeight() > 0 ? imageView.getHeight() : Math.abs(imageView.getLayoutParams().height), new Function1() { // from class: com.motorola.taskbar.settings.wallpaper.asset.AbstractAsset$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractAsset.loadDrawable$lambda$0(z, imageView, context, colorDrawable, (Bitmap) obj);
            }
        });
    }
}
