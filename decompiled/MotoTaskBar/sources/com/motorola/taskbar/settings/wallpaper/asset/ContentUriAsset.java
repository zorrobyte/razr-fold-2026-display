package com.motorola.taskbar.settings.wallpaper.asset;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import androidx.exifinterface.media.ExifInterface;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ContentUriAsset.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ContentUriAsset extends StreamingAsset {
    public static final Companion Companion = new Companion(null);
    private final Context mContext;
    private ExifInterface mExifInterface;
    private int mExifOrientation;
    private RequestOptions mRequestOptions;
    private Uri uri;

    /* JADX INFO: compiled from: ContentUriAsset.kt */
    final class BitmapCropTask extends AsyncTask {
        private final Rect mCropRect;
        private final Bitmap mFromBitmap;
        private final Function1 mReceiver;
        final /* synthetic */ ContentUriAsset this$0;

        public BitmapCropTask(ContentUriAsset contentUriAsset, Bitmap bitmap, Rect rect, Function1 function1) {
            rect.getClass();
            function1.getClass();
            this.this$0 = contentUriAsset;
            this.mFromBitmap = bitmap;
            this.mCropRect = rect;
            this.mReceiver = function1;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... voidArr) {
            voidArr.getClass();
            Bitmap bitmap = this.mFromBitmap;
            if (bitmap == null) {
                return null;
            }
            Rect rect = this.mCropRect;
            return Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), this.mCropRect.height());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            this.mReceiver.invoke(bitmap);
        }
    }

    /* JADX INFO: compiled from: ContentUriAsset.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ContentUriAsset(Context context, Uri uri, RequestOptions requestOptions, boolean z) {
        context.getClass();
        uri.getClass();
        requestOptions.getClass();
        this.uri = uri;
        Context applicationContext = context.getApplicationContext();
        applicationContext.getClass();
        this.mContext = applicationContext;
        if (z) {
            BaseRequestOptions baseRequestOptionsApply = requestOptions.apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE).skipMemoryCache(true));
            baseRequestOptionsApply.getClass();
            requestOptions = (RequestOptions) baseRequestOptionsApply;
        }
        this.mRequestOptions = requestOptions;
    }

    public /* synthetic */ ContentUriAsset(Context context, Uri uri, RequestOptions requestOptions, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, uri, (i & 4) != 0 ? RequestOptions.centerCropTransform() : requestOptions, (i & 8) != 0 ? false : z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit decodeBitmapRegion$lambda$1(final ContentUriAsset contentUriAsset, final Function1 function1, final Rect rect, Point point) {
        if (point != null) {
            contentUriAsset.decodeBitmap(point.x, point.y, new Function1() { // from class: com.motorola.taskbar.settings.wallpaper.asset.ContentUriAsset$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ContentUriAsset.decodeBitmapRegion$lambda$1$lambda$0(this.f$0, function1, rect, (Bitmap) obj);
                }
            });
            return Unit.INSTANCE;
        }
        Log.e("ContentUriAsset", "There was an error decoding the asset's raw dimensions with content URI: " + contentUriAsset.uri);
        function1.invoke(null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit decodeBitmapRegion$lambda$1$lambda$0(ContentUriAsset contentUriAsset, Function1 function1, Rect rect, Bitmap bitmap) {
        if (bitmap != null) {
            new BitmapCropTask(contentUriAsset, bitmap, rect, function1).execute(new Void[0]);
            return Unit.INSTANCE;
        }
        Log.e("ContentUriAsset", "There was an error decoding the asset's full bitmap with content URI: " + contentUriAsset.uri);
        function1.invoke(null);
        return Unit.INSTANCE;
    }

    private final void ensureExifInterface() {
        if (this.mExifInterface == null) {
            try {
                InputStream inputStreamOpenInputStream = openInputStream();
                if (inputStreamOpenInputStream != null) {
                    try {
                        this.mExifInterface = new ExifInterface(inputStreamOpenInputStream);
                        Unit unit = Unit.INSTANCE;
                    } finally {
                    }
                }
                CloseableKt.closeFinally(inputStreamOpenInputStream, null);
            } catch (IOException e) {
                Log.w("ContentUriAsset", "Couldn't read stream for " + this.uri, e);
            }
        }
    }

    private final int readExifOrientation() {
        ensureExifInterface();
        ExifInterface exifInterface = this.mExifInterface;
        if (exifInterface != null) {
            exifInterface.getClass();
            return exifInterface.getAttributeInt("Orientation", 0);
        }
        Log.w("ContentUriAsset", "Unable to read EXIF rotation for content URI asset with content URI: " + this.uri);
        return 0;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.StreamingAsset, com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void decodeBitmapRegion(final Rect rect, int i, int i2, final Function1 function1) {
        rect.getClass();
        function1.getClass();
        if (isJpeg() || isPng()) {
            super.decodeBitmapRegion(rect, i, i2, function1);
        } else {
            IAsset.DefaultImpls.decodeRawDimensions$default(this, null, new Function1() { // from class: com.motorola.taskbar.settings.wallpaper.asset.ContentUriAsset$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ContentUriAsset.decodeBitmapRegion$lambda$1(this.f$0, function1, rect, (Point) obj);
                }
            }, 1, null);
        }
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.StreamingAsset
    protected int getExifOrientation() {
        int i = this.mExifOrientation;
        if (i != 0) {
            return i;
        }
        int exifOrientation = readExifOrientation();
        this.mExifOrientation = exifOrientation;
        return exifOrientation;
    }

    public final boolean isJpeg() {
        String type = this.mContext.getContentResolver().getType(this.uri);
        return type != null && Intrinsics.areEqual(type, "image/jpeg");
    }

    public final boolean isPng() {
        String type = this.mContext.getContentResolver().getType(this.uri);
        return type != null && Intrinsics.areEqual(type, "image/png");
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.AbstractAsset, com.motorola.taskbar.settings.wallpaper.asset.IAsset
    public void loadDrawable(Context context, ImageView imageView, int i) {
        context.getClass();
        imageView.getClass();
        Glide.with(context).asDrawable().load(this.uri).apply(this.mRequestOptions.placeholder(new ColorDrawable(i))).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.StreamingAsset
    protected InputStream openInputStream() {
        try {
            return this.mContext.getContentResolver().openInputStream(this.uri);
        } catch (FileNotFoundException e) {
            Log.w("ContentUriAsset", "Image file not found", e);
            return null;
        }
    }
}
