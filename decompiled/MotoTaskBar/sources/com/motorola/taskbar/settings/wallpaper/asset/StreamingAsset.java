package com.motorola.taskbar.settings.wallpaper.asset;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import com.motorola.taskbar.settings.wallpaper.utils.BitmapUtils;
import java.io.IOException;
import java.io.InputStream;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: StreamingAsset.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class StreamingAsset extends AbstractAsset {
    public static final Companion Companion = new Companion(null);
    private Point mDimensions;
    private final Lazy mBitmapRegionDecoder$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.taskbar.settings.wallpaper.asset.StreamingAsset$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final Object mo2224invoke() {
            return this.f$0.openBitmapRegionDecoder();
        }
    });
    private final boolean supportsTiling = true;

    /* JADX INFO: compiled from: StreamingAsset.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int getDegreesRotationForExifOrientation(int i) {
            if (i == 1) {
                return 0;
            }
            if (i == 3) {
                return 180;
            }
            if (i == 6) {
                return 90;
            }
            if (i == 8) {
                return 270;
            }
            Log.w("StreamingAsset", "Unsupported EXIF orientation " + i);
            return 0;
        }
    }

    /* JADX INFO: compiled from: StreamingAsset.kt */
    final class DecodeBitmapAsyncTask extends AsyncTask {
        private final Function1 mReceiver;
        private int mTargetHeight;
        private int mTargetWidth;
        final /* synthetic */ StreamingAsset this$0;

        public DecodeBitmapAsyncTask(StreamingAsset streamingAsset, int i, int i2, Function1 function1) {
            function1.getClass();
            this.this$0 = streamingAsset;
            this.mTargetWidth = i;
            this.mTargetHeight = i2;
            this.mReceiver = function1;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... voidArr) {
            voidArr.getClass();
            int exifOrientation = this.this$0.getExifOrientation();
            if (exifOrientation == 6 || exifOrientation == 8) {
                int i = this.mTargetHeight;
                this.mTargetHeight = this.mTargetWidth;
                this.mTargetWidth = i;
            }
            InputStream inputStreamOpenInputStream = this.this$0.openInputStream();
            if (inputStreamOpenInputStream == null) {
                return null;
            }
            StreamingAsset streamingAsset = this.this$0;
            BitmapFactory.Options options = new BitmapFactory.Options();
            Point pointCalculateRawDimensions = streamingAsset.calculateRawDimensions();
            if (pointCalculateRawDimensions == null) {
                return null;
            }
            options.inSampleSize = BitmapUtils.Companion.calculateInSampleSize(pointCalculateRawDimensions.x, pointCalculateRawDimensions.y, this.mTargetWidth, this.mTargetHeight);
            options.inPreferredConfig = Bitmap.Config.HARDWARE;
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
            streamingAsset.closeInputStream(inputStreamOpenInputStream, "Error closing the input stream used to decode the full bitmap");
            int degreesRotationForExifOrientation = StreamingAsset.Companion.getDegreesRotationForExifOrientation(exifOrientation);
            if (degreesRotationForExifOrientation <= 0) {
                return bitmapDecodeStream;
            }
            Matrix matrix = new Matrix();
            matrix.setRotate(degreesRotationForExifOrientation);
            bitmapDecodeStream.getClass();
            return Bitmap.createBitmap(bitmapDecodeStream, 0, 0, bitmapDecodeStream.getWidth(), bitmapDecodeStream.getHeight(), matrix, false);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            this.mReceiver.invoke(bitmap);
        }
    }

    /* JADX INFO: compiled from: StreamingAsset.kt */
    final class DecodeBitmapRegionAsyncTask extends AsyncTask {
        private Rect mCropRect;
        private final Function1 mReceiver;
        private int mTargetHeight;
        private int mTargetWidth;
        final /* synthetic */ StreamingAsset this$0;

        public DecodeBitmapRegionAsyncTask(StreamingAsset streamingAsset, Rect rect, int i, int i2, Function1 function1) {
            rect.getClass();
            function1.getClass();
            this.this$0 = streamingAsset;
            this.mCropRect = rect;
            this.mTargetWidth = i;
            this.mTargetHeight = i2;
            this.mReceiver = function1;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... voidArr) {
            voidArr.getClass();
            int exifOrientation = this.this$0.getExifOrientation();
            if (exifOrientation == 6 || exifOrientation == 8) {
                int i = this.mTargetHeight;
                this.mTargetHeight = this.mTargetWidth;
                this.mTargetWidth = i;
            }
            Point pointCalculateRawDimensions = this.this$0.calculateRawDimensions();
            if (pointCalculateRawDimensions == null) {
                return null;
            }
            StreamingAsset streamingAsset = this.this$0;
            this.mCropRect = CropRectRotator.Companion.rotateCropRectForExifOrientation(pointCalculateRawDimensions, this.mCropRect, exifOrientation);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = BitmapUtils.Companion.calculateInSampleSize(this.mCropRect.width(), this.mCropRect.height(), this.mTargetWidth, this.mTargetHeight);
            if (streamingAsset.getMBitmapRegionDecoder() == null) {
                return null;
            }
            try {
                BitmapRegionDecoder mBitmapRegionDecoder = streamingAsset.getMBitmapRegionDecoder();
                mBitmapRegionDecoder.getClass();
                Bitmap bitmapDecodeRegion = mBitmapRegionDecoder.decodeRegion(this.mCropRect, options);
                int degreesRotationForExifOrientation = StreamingAsset.Companion.getDegreesRotationForExifOrientation(exifOrientation);
                if (degreesRotationForExifOrientation <= 0) {
                    return bitmapDecodeRegion;
                }
                Matrix matrix = new Matrix();
                matrix.setRotate(degreesRotationForExifOrientation);
                return Bitmap.createBitmap(bitmapDecodeRegion, 0, 0, bitmapDecodeRegion.getWidth(), bitmapDecodeRegion.getHeight(), matrix, false);
            } catch (OutOfMemoryError e) {
                Log.e("StreamingAsset", "Out of memory and unable to decode bitmap region", e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            this.mReceiver.invoke(bitmap);
        }
    }

    /* JADX INFO: compiled from: StreamingAsset.kt */
    final class DecodeDimensionsAsyncTask extends AsyncTask {
        private final Function1 mReceiver;
        final /* synthetic */ StreamingAsset this$0;

        public DecodeDimensionsAsyncTask(StreamingAsset streamingAsset, Function1 function1) {
            function1.getClass();
            this.this$0 = streamingAsset;
            this.mReceiver = function1;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Point doInBackground(Void... voidArr) {
            voidArr.getClass();
            return this.this$0.calculateRawDimensions();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Point point) {
            this.mReceiver.invoke(point);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void closeInputStream(InputStream inputStream, String str) {
        try {
            inputStream.getClass();
            inputStream.close();
        } catch (IOException unused) {
            Log.e("StreamingAsset", str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BitmapRegionDecoder getMBitmapRegionDecoder() {
        return (BitmapRegionDecoder) this.mBitmapRegionDecoder$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BitmapRegionDecoder openBitmapRegionDecoder() throws Throwable {
        InputStream inputStreamOpenInputStream;
        Throwable th;
        try {
            inputStreamOpenInputStream = openInputStream();
            if (inputStreamOpenInputStream == null) {
                closeInputStream(inputStreamOpenInputStream, "Unable to close input stream used to create BitmapRegionDecoder");
                return null;
            }
            try {
                try {
                    BitmapRegionDecoder bitmapRegionDecoderNewInstance = BitmapRegionDecoder.newInstance(inputStreamOpenInputStream, true);
                    closeInputStream(inputStreamOpenInputStream, "Unable to close input stream used to create BitmapRegionDecoder");
                    return bitmapRegionDecoderNewInstance;
                } catch (IOException e) {
                    e = e;
                    Log.w("StreamingAsset", "Unable to open BitmapRegionDecoder", e);
                    closeInputStream(inputStreamOpenInputStream, "Unable to close input stream used to create BitmapRegionDecoder");
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                closeInputStream(inputStreamOpenInputStream, "Unable to close input stream used to create BitmapRegionDecoder");
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            inputStreamOpenInputStream = null;
        } catch (Throwable th3) {
            inputStreamOpenInputStream = null;
            th = th3;
            closeInputStream(inputStreamOpenInputStream, "Unable to close input stream used to create BitmapRegionDecoder");
            throw th;
        }
    }

    public final Point calculateRawDimensions() {
        Point point = this.mDimensions;
        if (point != null) {
            return point;
        }
        InputStream inputStreamOpenInputStream = openInputStream();
        if (inputStreamOpenInputStream == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
        closeInputStream(inputStreamOpenInputStream, "There was an error closing the input stream used to calculate the image's raw dimensions");
        int exifOrientation = getExifOrientation();
        Point point2 = (exifOrientation == 6 || exifOrientation == 8) ? new Point(options.outHeight, options.outWidth) : new Point(options.outWidth, options.outHeight);
        this.mDimensions = point2;
        return point2;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void decodeBitmap(int i, int i2, Function1 function1) {
        function1.getClass();
        new DecodeBitmapAsyncTask(this, i, i2, function1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void decodeBitmapRegion(Rect rect, int i, int i2, Function1 function1) {
        rect.getClass();
        function1.getClass();
        runDecodeBitmapRegionTask(rect, i, i2, function1);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void decodeRawDimensions(Activity activity, Function1 function1) {
        function1.getClass();
        new DecodeDimensionsAsyncTask(this, function1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public final void fetchInputStream(final Function1 function1) {
        function1.getClass();
        new AsyncTask() { // from class: com.motorola.taskbar.settings.wallpaper.asset.StreamingAsset.fetchInputStream.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public InputStream doInBackground(Void... voidArr) {
                voidArr.getClass();
                return StreamingAsset.this.openInputStream();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(InputStream inputStream) {
                function1.invoke(inputStream);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    protected int getExifOrientation() {
        return 1;
    }

    protected abstract InputStream openInputStream();

    public final AsyncTask runDecodeBitmapRegionTask(Rect rect, int i, int i2, Function1 function1) {
        rect.getClass();
        function1.getClass();
        DecodeBitmapRegionAsyncTask decodeBitmapRegionAsyncTask = new DecodeBitmapRegionAsyncTask(this, rect, i, i2, function1);
        decodeBitmapRegionAsyncTask.execute(new Void[0]);
        return decodeBitmapRegionAsyncTask;
    }
}
