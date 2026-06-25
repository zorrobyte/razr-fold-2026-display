package com.motorola.taskbar.settings.wallpaper.asset;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.widget.ImageView;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: IAsset.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface IAsset {

    /* JADX INFO: compiled from: IAsset.kt */
    public abstract class DefaultImpls {
        public static /* synthetic */ void decodeRawDimensions$default(IAsset iAsset, Activity activity, Function1 function1, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decodeRawDimensions");
            }
            if ((i & 1) != 0) {
                activity = null;
            }
            iAsset.decodeRawDimensions(activity, function1);
        }
    }

    void decodeBitmap(int i, int i2, Function1 function1);

    void decodeBitmapRegion(Rect rect, int i, int i2, Function1 function1);

    void decodeRawDimensions(Activity activity, Function1 function1);

    void loadDrawable(Context context, ImageView imageView, int i);
}
