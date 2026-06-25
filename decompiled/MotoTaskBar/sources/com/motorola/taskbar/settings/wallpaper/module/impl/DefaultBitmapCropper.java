package com.motorola.taskbar.settings.wallpaper.module.impl;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;
import com.motorola.taskbar.settings.wallpaper.module.BitmapCropper;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt;

/* JADX INFO: compiled from: DefaultBitmapCropper.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DefaultBitmapCropper implements BitmapCropper {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: DefaultBitmapCropper.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: DefaultBitmapCropper.kt */
    final class ScaleBitmapTask extends AsyncTask {
        private Bitmap mBitmap;
        private final BitmapCropper.Callback mCallback;
        private final Rect mCropRect;
        private Throwable mThrowable;

        public ScaleBitmapTask(Bitmap bitmap, Rect rect, BitmapCropper.Callback callback) {
            rect.getClass();
            callback.getClass();
            this.mBitmap = bitmap;
            this.mCropRect = rect;
            this.mCallback = callback;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Boolean doInBackground(Unit... unitArr) {
            unitArr.getClass();
            Bitmap bitmap = this.mBitmap;
            boolean z = false;
            if (bitmap != null) {
                try {
                    bitmap.getClass();
                    this.mBitmap = Bitmap.createScaledBitmap(bitmap, this.mCropRect.width(), this.mCropRect.height(), true);
                    z = true;
                } catch (OutOfMemoryError e) {
                    Log.w("DefaultBitmapCropper", "Not enough memory to fit the final cropped and scaled bitmap to size", e);
                    this.mThrowable = e;
                }
            }
            return Boolean.valueOf(z);
        }

        @Override // android.os.AsyncTask
        public /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
            onPostExecute(((Boolean) obj).booleanValue());
        }

        protected void onPostExecute(boolean z) {
            if (!z) {
                this.mCallback.onError(this.mThrowable);
                return;
            }
            BitmapCropper.Callback callback = this.mCallback;
            Bitmap bitmap = this.mBitmap;
            bitmap.getClass();
            callback.onBitmapCropped(bitmap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit cropAndScaleBitmap$lambda$0(Rect rect, BitmapCropper.Callback callback, Bitmap bitmap) {
        new ScaleBitmapTask(bitmap, rect, callback).execute(new Unit[0]);
        return Unit.INSTANCE;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.BitmapCropper
    public void cropAndScaleBitmap(IAsset iAsset, float f, final Rect rect, final BitmapCropper.Callback callback) {
        iAsset.getClass();
        rect.getClass();
        callback.getClass();
        iAsset.decodeBitmapRegion(new Rect(MathKt.roundToInt(rect.left / f), MathKt.roundToInt(rect.top / f), MathKt.roundToInt(rect.right / f), MathKt.roundToInt(rect.bottom / f)), rect.width(), rect.height(), new Function1() { // from class: com.motorola.taskbar.settings.wallpaper.module.impl.DefaultBitmapCropper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DefaultBitmapCropper.cropAndScaleBitmap$lambda$0(rect, callback, (Bitmap) obj);
            }
        });
    }
}
