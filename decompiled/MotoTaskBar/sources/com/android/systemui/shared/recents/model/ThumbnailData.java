package com.android.systemui.shared.recents.model;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.util.Log;
import android.window.TaskSnapshot;
import com.android.systemui.plugins.DarkIconDispatcher;
import kotlin.Unit;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThumbnailData.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ThumbnailData {
    public static final Companion Companion = new Companion(null);
    public int appearance;
    public Rect insets;
    public boolean isRealSnapshot;
    private boolean isTranslucent;
    public Rect letterboxInsets;
    private int orientation;
    public boolean reducedResolution;
    public int rotation;
    public float scale;
    private long snapshotId;
    private final Bitmap thumbnail;
    public int windowingMode;

    /* JADX INFO: compiled from: ThumbnailData.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Bitmap makeThumbnail(TaskSnapshot taskSnapshot) {
            Bitmap bitmapWrapHardwareBuffer;
            IllegalArgumentException e;
            HardwareBuffer hardwareBuffer;
            Throwable th;
            Bitmap bitmap = null;
            try {
                hardwareBuffer = taskSnapshot.getHardwareBuffer();
            } catch (IllegalArgumentException e2) {
                bitmapWrapHardwareBuffer = null;
                e = e2;
            }
            try {
            } catch (IllegalArgumentException e3) {
                e = e3;
                Log.e("ThumbnailData", "Unexpected snapshot without USAGE_GPU_SAMPLED_IMAGE: " + taskSnapshot.getHardwareBuffer(), e);
            }
            if (hardwareBuffer != null) {
                try {
                    bitmapWrapHardwareBuffer = Bitmap.wrapHardwareBuffer(hardwareBuffer, taskSnapshot.getColorSpace());
                    try {
                        Unit unit = Unit.INSTANCE;
                        AutoCloseableKt.closeFinally(hardwareBuffer, null);
                        bitmap = bitmapWrapHardwareBuffer;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            throw th;
                        } catch (Throwable th3) {
                            AutoCloseableKt.closeFinally(hardwareBuffer, th);
                            throw th3;
                        }
                    }
                } catch (Throwable th4) {
                    bitmapWrapHardwareBuffer = null;
                    th = th4;
                }
            }
            if (bitmap != null) {
                return bitmap;
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(taskSnapshot.getTaskSize().x, taskSnapshot.getTaskSize().y, Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.getClass();
            bitmapCreateBitmap.eraseColor(DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT);
            return bitmapCreateBitmap;
        }

        public final ThumbnailData fromSnapshot(TaskSnapshot taskSnapshot) {
            taskSnapshot.getClass();
            return new ThumbnailData(makeThumbnail(taskSnapshot), taskSnapshot.getOrientation(), taskSnapshot.getRotation(), new Rect(taskSnapshot.getContentInsets()), new Rect(taskSnapshot.getLetterboxInsets()), taskSnapshot.isLowResolution(), taskSnapshot.isRealSnapshot(), taskSnapshot.isTranslucent(), taskSnapshot.getWindowingMode(), taskSnapshot.getAppearance(), r1.getWidth() / taskSnapshot.getTaskSize().x, taskSnapshot.getId());
        }
    }

    public ThumbnailData() {
        this(null, 0, 0, null, null, false, false, false, 0, 0, 0.0f, 0L, 4095, null);
    }

    public ThumbnailData(Bitmap bitmap, int i, int i2, Rect rect, Rect rect2, boolean z, boolean z2, boolean z3, int i3, int i4, float f, long j) {
        rect.getClass();
        rect2.getClass();
        this.thumbnail = bitmap;
        this.orientation = i;
        this.rotation = i2;
        this.insets = rect;
        this.letterboxInsets = rect2;
        this.reducedResolution = z;
        this.isRealSnapshot = z2;
        this.isTranslucent = z3;
        this.windowingMode = i3;
        this.appearance = i4;
        this.scale = f;
        this.snapshotId = j;
    }

    public /* synthetic */ ThumbnailData(Bitmap bitmap, int i, int i2, Rect rect, Rect rect2, boolean z, boolean z2, boolean z3, int i3, int i4, float f, long j, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? null : bitmap, (i5 & 2) != 0 ? 0 : i, (i5 & 4) != 0 ? -1 : i2, (i5 & 8) != 0 ? new Rect() : rect, (i5 & 16) != 0 ? new Rect() : rect2, (i5 & 32) != 0 ? false : z, (i5 & 64) != 0 ? true : z2, (i5 & 128) != 0 ? false : z3, (i5 & 256) != 0 ? 0 : i3, (i5 & 512) == 0 ? i4 : 0, (i5 & 1024) != 0 ? 1.0f : f, (i5 & 2048) != 0 ? 0L : j);
    }

    public static final ThumbnailData fromSnapshot(TaskSnapshot taskSnapshot) {
        return Companion.fromSnapshot(taskSnapshot);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ThumbnailData)) {
            return false;
        }
        ThumbnailData thumbnailData = (ThumbnailData) obj;
        return Intrinsics.areEqual(this.thumbnail, thumbnailData.thumbnail) && this.orientation == thumbnailData.orientation && this.rotation == thumbnailData.rotation && Intrinsics.areEqual(this.insets, thumbnailData.insets) && Intrinsics.areEqual(this.letterboxInsets, thumbnailData.letterboxInsets) && this.reducedResolution == thumbnailData.reducedResolution && this.isRealSnapshot == thumbnailData.isRealSnapshot && this.isTranslucent == thumbnailData.isTranslucent && this.windowingMode == thumbnailData.windowingMode && this.appearance == thumbnailData.appearance && Float.compare(this.scale, thumbnailData.scale) == 0 && this.snapshotId == thumbnailData.snapshotId;
    }

    public final Bitmap getThumbnail() {
        return this.thumbnail;
    }

    public int hashCode() {
        Bitmap bitmap = this.thumbnail;
        return ((((((((((((((((((((((bitmap == null ? 0 : bitmap.hashCode()) * 31) + Integer.hashCode(this.orientation)) * 31) + Integer.hashCode(this.rotation)) * 31) + this.insets.hashCode()) * 31) + this.letterboxInsets.hashCode()) * 31) + Boolean.hashCode(this.reducedResolution)) * 31) + Boolean.hashCode(this.isRealSnapshot)) * 31) + Boolean.hashCode(this.isTranslucent)) * 31) + Integer.hashCode(this.windowingMode)) * 31) + Integer.hashCode(this.appearance)) * 31) + Float.hashCode(this.scale)) * 31) + Long.hashCode(this.snapshotId);
    }

    public final boolean isTranslucent() {
        return this.isTranslucent;
    }

    public final void recycleBitmap() {
        Bitmap bitmap = this.thumbnail;
        if (bitmap != null) {
            bitmap.recycle();
        }
    }

    public String toString() {
        return "ThumbnailData(thumbnail=" + this.thumbnail + ", orientation=" + this.orientation + ", rotation=" + this.rotation + ", insets=" + this.insets + ", letterboxInsets=" + this.letterboxInsets + ", reducedResolution=" + this.reducedResolution + ", isRealSnapshot=" + this.isRealSnapshot + ", isTranslucent=" + this.isTranslucent + ", windowingMode=" + this.windowingMode + ", appearance=" + this.appearance + ", scale=" + this.scale + ", snapshotId=" + this.snapshotId + ")";
    }
}
