package com.motorola.taskbar.settings.wallpaper.module.impl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.motorola.taskbar.settings.utils.ExtentionsKt;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;
import com.motorola.taskbar.settings.wallpaper.asset.StreamingAsset;
import com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo;
import com.motorola.taskbar.settings.wallpaper.module.BitmapCropper;
import com.motorola.taskbar.settings.wallpaper.module.WallpaperManagerCompat;
import com.motorola.taskbar.settings.wallpaper.module.WallpaperPersister;
import com.motorola.taskbar.settings.wallpaper.utils.ScreenSizeCalculator;
import com.motorola.taskbar.settings.wallpaper.utils.WallpaperChangedNotifier;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: DefaultWallpaperPersister.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DefaultWallpaperPersister implements WallpaperPersister {
    public static final Companion Companion = new Companion(null);
    private final Supplier bitmapCropperSupplier;
    private final WallpaperManagerCompat mWallpaperManagerCompat;

    /* JADX INFO: compiled from: DefaultWallpaperPersister.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: DefaultWallpaperPersister.kt */
    final class SetWallpaperTask extends AsyncTask {
        private Bitmap mBitmap;
        private final int mDestination;
        private Point mFillSize;
        private InputStream mInputStream;
        private Point mStretchSize;
        private final WallpaperInfo mWallpaper;
        final /* synthetic */ DefaultWallpaperPersister this$0;

        public SetWallpaperTask(DefaultWallpaperPersister defaultWallpaperPersister, WallpaperInfo wallpaperInfo, Bitmap bitmap, int i, WallpaperPersister.SetWallpaperCallback setWallpaperCallback) {
            wallpaperInfo.getClass();
            this.this$0 = defaultWallpaperPersister;
            this.mWallpaper = wallpaperInfo;
            this.mBitmap = bitmap;
            this.mDestination = i;
        }

        public SetWallpaperTask(DefaultWallpaperPersister defaultWallpaperPersister, WallpaperInfo wallpaperInfo, InputStream inputStream, int i, WallpaperPersister.SetWallpaperCallback setWallpaperCallback) {
            wallpaperInfo.getClass();
            this.this$0 = defaultWallpaperPersister;
            this.mWallpaper = wallpaperInfo;
            this.mInputStream = inputStream;
            this.mDestination = i;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void... voidArr) {
            int streamToWallpaperManagerCompat;
            voidArr.getClass();
            int i = this.mDestination;
            int i2 = i != -1 ? i != 0 ? i != 1 ? 3 : 2 : 1 : 8;
            boolean z = this.mWallpaper.getBackupPermission() == 1;
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null) {
                if (this.mFillSize != null) {
                    bitmap.getClass();
                    Point point = this.mFillSize;
                    point.getClass();
                    this.mBitmap = ExtentionsKt.applyFillTransformation(bitmap, point);
                }
                if (this.mStretchSize != null) {
                    Bitmap bitmap2 = this.mBitmap;
                    bitmap2.getClass();
                    Point point2 = this.mStretchSize;
                    point2.getClass();
                    int i3 = point2.x;
                    Point point3 = this.mStretchSize;
                    point3.getClass();
                    this.mBitmap = Bitmap.createScaledBitmap(bitmap2, i3, point3.y, true);
                }
                DefaultWallpaperPersister defaultWallpaperPersister = this.this$0;
                Bitmap bitmap3 = this.mBitmap;
                bitmap3.getClass();
                streamToWallpaperManagerCompat = defaultWallpaperPersister.setBitmapToWallpaperManagerCompat(bitmap3, z, i2);
            } else {
                InputStream inputStream = this.mInputStream;
                if (inputStream != null) {
                    DefaultWallpaperPersister defaultWallpaperPersister2 = this.this$0;
                    inputStream.getClass();
                    streamToWallpaperManagerCompat = defaultWallpaperPersister2.setStreamToWallpaperManagerCompat(inputStream, z, i2);
                } else {
                    Log.e("WallpaperPersister", "Both the wallpaper bitmap and input stream are null so we're unable to set any kind of wallpaper here.");
                    streamToWallpaperManagerCompat = 0;
                }
            }
            return Boolean.valueOf(streamToWallpaperManagerCompat > 0);
        }

        @Override // android.os.AsyncTask
        public /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
            onPostExecute(((Boolean) obj).booleanValue());
        }

        protected void onPostExecute(boolean z) {
            InputStream inputStream = this.mInputStream;
            if (inputStream != null) {
                try {
                    inputStream.getClass();
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("WallpaperPersister", "Failed to close input stream " + e);
                    return;
                }
            }
            if (z) {
                WallpaperChangedNotifier.INSTANCE.notifyWallpaperChanged();
            }
        }

        public final void setFillSize(Point point) {
            point.getClass();
            if (this.mStretchSize != null) {
                throw new IllegalArgumentException("Can't pass a fill size option if a stretch size is already set.");
            }
            this.mFillSize = point;
        }

        public final void setStretchSize(Point point) {
            point.getClass();
            if (this.mFillSize != null) {
                throw new IllegalArgumentException("Can't pass a stretch size option if a fill size is already set.");
            }
            this.mStretchSize = point;
        }
    }

    public DefaultWallpaperPersister(WallpaperManagerCompat wallpaperManagerCompat, Supplier supplier) {
        wallpaperManagerCompat.getClass();
        supplier.getClass();
        this.mWallpaperManagerCompat = wallpaperManagerCompat;
        this.bitmapCropperSupplier = supplier;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int setBitmapToWallpaperManagerCompat(Bitmap bitmap, boolean z, int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)) {
            try {
                return this.mWallpaperManagerCompat.setStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), null, z, i);
            } catch (IOException unused) {
                Log.e("WallpaperPersister", "unable to write stream to wallpaper manager");
                return 0;
            }
        }
        Log.e("WallpaperPersister", "unable to compress wallpaper");
        try {
            return this.mWallpaperManagerCompat.setBitmap(bitmap, null, z, i);
        } catch (IOException unused2) {
            Log.e("WallpaperPersister", "unable to set wallpaper");
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setIndividualWallpaper(WallpaperInfo wallpaperInfo, Bitmap bitmap, int i, WallpaperPersister.SetWallpaperCallback setWallpaperCallback) {
        new SetWallpaperTask(this, wallpaperInfo, bitmap, i, setWallpaperCallback).execute(new Void[0]);
    }

    private final void setIndividualWallpaper(WallpaperInfo wallpaperInfo, InputStream inputStream, int i, WallpaperPersister.SetWallpaperCallback setWallpaperCallback) {
        new SetWallpaperTask(this, wallpaperInfo, inputStream, i, setWallpaperCallback).execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setIndividualWallpaper$lambda$0(WallpaperPersister.SetWallpaperCallback setWallpaperCallback, DefaultWallpaperPersister defaultWallpaperPersister, WallpaperInfo wallpaperInfo, int i, InputStream inputStream) {
        if (inputStream != null) {
            defaultWallpaperPersister.setIndividualWallpaper(wallpaperInfo, inputStream, i, setWallpaperCallback);
            return Unit.INSTANCE;
        }
        if (setWallpaperCallback != null) {
            setWallpaperCallback.onError(null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setIndividualWallpaper$lambda$1(WallpaperPersister.SetWallpaperCallback setWallpaperCallback, DefaultWallpaperPersister defaultWallpaperPersister, WallpaperInfo wallpaperInfo, int i, Bitmap bitmap) {
        if (bitmap != null) {
            defaultWallpaperPersister.setIndividualWallpaper(wallpaperInfo, bitmap, i, setWallpaperCallback);
            return Unit.INSTANCE;
        }
        if (setWallpaperCallback != null) {
            setWallpaperCallback.onError(null);
        }
        return Unit.INSTANCE;
    }

    private final void setIndividualWallpaperFill(WallpaperInfo wallpaperInfo, Bitmap bitmap, Point point, int i, WallpaperPersister.SetWallpaperCallback setWallpaperCallback) {
        SetWallpaperTask setWallpaperTask = new SetWallpaperTask(this, wallpaperInfo, bitmap, i, setWallpaperCallback);
        setWallpaperTask.setFillSize(point);
        setWallpaperTask.execute(new Void[0]);
    }

    private final void setIndividualWallpaperStretch(WallpaperInfo wallpaperInfo, Bitmap bitmap, Point point, int i, WallpaperPersister.SetWallpaperCallback setWallpaperCallback) {
        SetWallpaperTask setWallpaperTask = new SetWallpaperTask(this, wallpaperInfo, bitmap, i, setWallpaperCallback);
        setWallpaperTask.setStretchSize(point);
        setWallpaperTask.execute(new Void[0]);
    }

    private final void setIndividualWallpaperWithCenterCropPosition(WallpaperInfo wallpaperInfo, IAsset iAsset, Point point, Point point2, int i, WallpaperPersister.SetWallpaperCallback setWallpaperCallback) {
        float fCoerceAtLeast = RangesKt.coerceAtLeast(point2.x / point.x, point2.y / point.y);
        int i2 = (int) (point.x * fCoerceAtLeast);
        int i3 = (int) (point.y * fCoerceAtLeast);
        int i4 = point2.x;
        int i5 = point2.y;
        setIndividualWallpaper(wallpaperInfo, iAsset, new Rect((i2 - i4) / 2, (i3 - i5) / 2, i2 - ((i2 - i4) / 2), i3 - ((i3 - i5) / 2)), fCoerceAtLeast, i, setWallpaperCallback);
    }

    private final void setIndividualWallpaperWithCenterPosition(final WallpaperInfo wallpaperInfo, IAsset iAsset, Point point, final Point point2, final int i, final WallpaperPersister.SetWallpaperCallback setWallpaperCallback) {
        int i2 = point.x;
        if (i2 < point2.x || point.y < point2.y) {
            iAsset.decodeBitmap(i2, point.y, new Function1(setWallpaperCallback, this, wallpaperInfo, point2, i) { // from class: com.motorola.taskbar.settings.wallpaper.module.impl.DefaultWallpaperPersister$$ExternalSyntheticLambda5
                public final /* synthetic */ DefaultWallpaperPersister f$1;
                public final /* synthetic */ WallpaperInfo f$2;
                public final /* synthetic */ Point f$3;
                public final /* synthetic */ int f$4;

                {
                    this.f$1 = this;
                    this.f$2 = wallpaperInfo;
                    this.f$3 = point2;
                    this.f$4 = i;
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return DefaultWallpaperPersister.setIndividualWallpaperWithCenterPosition$lambda$5(null, this.f$1, this.f$2, this.f$3, this.f$4, (Bitmap) obj);
                }
            });
            return;
        }
        int i3 = point.x;
        int i4 = point2.x;
        int i5 = point.y;
        int i6 = point2.y;
        iAsset.decodeBitmapRegion(new Rect((i3 - i4) / 2, (i5 - i6) / 2, i3 - ((i3 - i4) / 2), i5 - ((i5 - i6) / 2)), point2.x, point2.y, new Function1(wallpaperInfo, i, setWallpaperCallback) { // from class: com.motorola.taskbar.settings.wallpaper.module.impl.DefaultWallpaperPersister$$ExternalSyntheticLambda4
            public final /* synthetic */ WallpaperInfo f$1;
            public final /* synthetic */ int f$2;

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DefaultWallpaperPersister.setIndividualWallpaperWithCenterPosition$lambda$4(this.f$0, this.f$1, this.f$2, null, (Bitmap) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setIndividualWallpaperWithCenterPosition$lambda$4(DefaultWallpaperPersister defaultWallpaperPersister, WallpaperInfo wallpaperInfo, int i, WallpaperPersister.SetWallpaperCallback setWallpaperCallback, Bitmap bitmap) {
        defaultWallpaperPersister.setIndividualWallpaper(wallpaperInfo, bitmap, i, setWallpaperCallback);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setIndividualWallpaperWithCenterPosition$lambda$5(WallpaperPersister.SetWallpaperCallback setWallpaperCallback, DefaultWallpaperPersister defaultWallpaperPersister, WallpaperInfo wallpaperInfo, Point point, int i, Bitmap bitmap) {
        if (bitmap != null) {
            defaultWallpaperPersister.setIndividualWallpaperFill(wallpaperInfo, bitmap, point, i, setWallpaperCallback);
            return Unit.INSTANCE;
        }
        if (setWallpaperCallback != null) {
            setWallpaperCallback.onError(null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setIndividualWallpaperWithPosition$lambda$3(final WallpaperPersister.SetWallpaperCallback setWallpaperCallback, int i, final DefaultWallpaperPersister defaultWallpaperPersister, final WallpaperInfo wallpaperInfo, IAsset iAsset, final Point point, final int i2, Point point2) {
        if (point2 == null) {
            if (setWallpaperCallback != null) {
                setWallpaperCallback.onError(null);
            }
            return Unit.INSTANCE;
        }
        if (i == 0) {
            defaultWallpaperPersister.setIndividualWallpaperWithCenterPosition(wallpaperInfo, iAsset, point2, point, i2, setWallpaperCallback);
        } else if (i == 1) {
            defaultWallpaperPersister.setIndividualWallpaperWithCenterCropPosition(wallpaperInfo, iAsset, point2, point, i2, setWallpaperCallback);
        } else if (i != 2) {
            Log.e("WallpaperPersister", "Unsupported wallpaper position option specified: " + i);
            if (setWallpaperCallback != null) {
                setWallpaperCallback.onError(null);
            }
        } else {
            iAsset.decodeBitmap(point.x, point.y, new Function1(wallpaperInfo, point, i2, setWallpaperCallback) { // from class: com.motorola.taskbar.settings.wallpaper.module.impl.DefaultWallpaperPersister$$ExternalSyntheticLambda1
                public final /* synthetic */ WallpaperInfo f$1;
                public final /* synthetic */ Point f$2;
                public final /* synthetic */ int f$3;

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return DefaultWallpaperPersister.setIndividualWallpaperWithPosition$lambda$3$lambda$2(this.f$0, this.f$1, this.f$2, this.f$3, null, (Bitmap) obj);
                }
            });
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setIndividualWallpaperWithPosition$lambda$3$lambda$2(DefaultWallpaperPersister defaultWallpaperPersister, WallpaperInfo wallpaperInfo, Point point, int i, WallpaperPersister.SetWallpaperCallback setWallpaperCallback, Bitmap bitmap) {
        defaultWallpaperPersister.setIndividualWallpaperStretch(wallpaperInfo, bitmap, point, i, setWallpaperCallback);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int setStreamToWallpaperManagerCompat(InputStream inputStream, boolean z, int i) {
        try {
            return this.mWallpaperManagerCompat.setStream(inputStream, null, z, i);
        } catch (IOException unused) {
            return 0;
        }
    }

    public void setIndividualWallpaper(final WallpaperInfo wallpaperInfo, IAsset iAsset, Rect rect, float f, final int i, final WallpaperPersister.SetWallpaperCallback setWallpaperCallback) {
        wallpaperInfo.getClass();
        iAsset.getClass();
        if (rect == null && (iAsset instanceof StreamingAsset)) {
            ((StreamingAsset) iAsset).fetchInputStream(new Function1(setWallpaperCallback, this, wallpaperInfo, i) { // from class: com.motorola.taskbar.settings.wallpaper.module.impl.DefaultWallpaperPersister$$ExternalSyntheticLambda2
                public final /* synthetic */ DefaultWallpaperPersister f$1;
                public final /* synthetic */ WallpaperInfo f$2;
                public final /* synthetic */ int f$3;

                {
                    this.f$1 = this;
                    this.f$2 = wallpaperInfo;
                    this.f$3 = i;
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return DefaultWallpaperPersister.setIndividualWallpaper$lambda$0(null, this.f$1, this.f$2, this.f$3, (InputStream) obj);
                }
            });
            return;
        }
        if (rect == null) {
            Point displaySize = this.mWallpaperManagerCompat.getDisplaySize();
            iAsset.decodeBitmap(displaySize.x, displaySize.y, new Function1(setWallpaperCallback, this, wallpaperInfo, i) { // from class: com.motorola.taskbar.settings.wallpaper.module.impl.DefaultWallpaperPersister$$ExternalSyntheticLambda3
                public final /* synthetic */ DefaultWallpaperPersister f$1;
                public final /* synthetic */ WallpaperInfo f$2;
                public final /* synthetic */ int f$3;

                {
                    this.f$1 = this;
                    this.f$2 = wallpaperInfo;
                    this.f$3 = i;
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return DefaultWallpaperPersister.setIndividualWallpaper$lambda$1(null, this.f$1, this.f$2, this.f$3, (Bitmap) obj);
                }
            });
        } else {
            Object obj = this.bitmapCropperSupplier.get();
            obj.getClass();
            ((BitmapCropper) obj).cropAndScaleBitmap(iAsset, f, rect, new BitmapCropper.Callback(wallpaperInfo, i, setWallpaperCallback) { // from class: com.motorola.taskbar.settings.wallpaper.module.impl.DefaultWallpaperPersister.setIndividualWallpaper.3
                final /* synthetic */ int $destination;
                final /* synthetic */ WallpaperInfo $wallpaperInfo;

                @Override // com.motorola.taskbar.settings.wallpaper.module.BitmapCropper.Callback
                public void onBitmapCropped(Bitmap bitmap) {
                    bitmap.getClass();
                    DefaultWallpaperPersister.this.setIndividualWallpaper(this.$wallpaperInfo, bitmap, this.$destination, (WallpaperPersister.SetWallpaperCallback) null);
                }

                @Override // com.motorola.taskbar.settings.wallpaper.module.BitmapCropper.Callback
                public void onError(Throwable th) {
                }
            });
        }
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.WallpaperPersister
    public void setIndividualWallpaperWithPosition(Activity activity, final WallpaperInfo wallpaperInfo, final int i, final int i2, final WallpaperPersister.SetWallpaperCallback setWallpaperCallback) {
        activity.getClass();
        wallpaperInfo.getClass();
        Object systemService = activity.getSystemService("window");
        systemService.getClass();
        Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
        ScreenSizeCalculator screenSizeCalculator = ScreenSizeCalculator.INSTANCE;
        defaultDisplay.getClass();
        final Point screenSize = screenSizeCalculator.getScreenSize(defaultDisplay);
        final IAsset asset = wallpaperInfo.getAsset(activity);
        asset.getClass();
        asset.decodeRawDimensions(activity, new Function1(setWallpaperCallback, i, this, wallpaperInfo, asset, screenSize, i2) { // from class: com.motorola.taskbar.settings.wallpaper.module.impl.DefaultWallpaperPersister$$ExternalSyntheticLambda0
            public final /* synthetic */ int f$1;
            public final /* synthetic */ DefaultWallpaperPersister f$2;
            public final /* synthetic */ WallpaperInfo f$3;
            public final /* synthetic */ IAsset f$4;
            public final /* synthetic */ Point f$5;
            public final /* synthetic */ int f$6;

            {
                this.f$1 = i;
                this.f$2 = this;
                this.f$3 = wallpaperInfo;
                this.f$4 = asset;
                this.f$5 = screenSize;
                this.f$6 = i2;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DefaultWallpaperPersister.setIndividualWallpaperWithPosition$lambda$3(null, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, (Point) obj);
            }
        });
    }
}
