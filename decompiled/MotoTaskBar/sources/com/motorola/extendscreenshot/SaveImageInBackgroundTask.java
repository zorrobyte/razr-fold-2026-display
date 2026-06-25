package com.motorola.extendscreenshot;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Slog;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
class SaveImageInBackgroundTask extends AsyncTask {
    private final Context mContext;
    private final String mImageFileName;
    private final long mImageTime;
    private final GlobalScreenshot$SaveImageInBackgroundData mParams;
    private final String mScreenshotId;
    private final Random mRandom = new Random();
    private final GlobalScreenshot$SavedImageData mImageData = new GlobalScreenshot$SavedImageData();

    SaveImageInBackgroundTask(Context context, GlobalScreenshot$SaveImageInBackgroundData globalScreenshot$SaveImageInBackgroundData) {
        this.mContext = context;
        this.mParams = globalScreenshot$SaveImageInBackgroundData;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.mImageTime = jCurrentTimeMillis;
        this.mImageFileName = String.format("Screenshot_%s.png", new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date(jCurrentTimeMillis)));
        this.mScreenshotId = String.format("Screenshot_%s", UUID.randomUUID());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Void doInBackground(Void... voidArr) {
        ContentValues contentValues;
        Uri uriInsert;
        OutputStream outputStreamOpenOutputStream;
        if (isCancelled()) {
            return null;
        }
        Thread.currentThread().setPriority(10);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Bitmap bitmap = this.mParams.image;
        this.mContext.getResources();
        try {
            contentValues = new ContentValues();
            contentValues.put("relative_path", Environment.DIRECTORY_PICTURES + File.separator + Environment.DIRECTORY_SCREENSHOTS);
            contentValues.put("_display_name", this.mImageFileName);
            contentValues.put("mime_type", "image/png");
            contentValues.put("date_added", Long.valueOf(this.mImageTime / 1000));
            contentValues.put("date_modified", Long.valueOf(this.mImageTime / 1000));
            contentValues.put("date_expires", Long.valueOf((this.mImageTime + 86400000) / 1000));
            contentValues.put("is_pending", (Integer) 1);
            uriInsert = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            try {
                outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
                try {
                } finally {
                }
            } catch (Exception e) {
                contentResolver.delete(uriInsert, null);
                throw e;
            }
        } catch (Exception e2) {
            Slog.e("SaveImageInBackgroundTask", "unable to save screenshot", e2);
            this.mParams.clearImage();
            this.mImageData.reset();
            this.mParams.mActionsReadyListener.onActionsReady(this.mImageData);
            this.mParams.finisher.accept(null);
        }
        if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStreamOpenOutputStream)) {
            throw new IOException("Failed to compress");
        }
        if (outputStreamOpenOutputStream != null) {
            outputStreamOpenOutputStream.close();
        }
        ParcelFileDescriptor parcelFileDescriptorOpenFile = contentResolver.openFile(uriInsert, "rw", null);
        try {
            ExifInterface exifInterface = new ExifInterface(parcelFileDescriptorOpenFile.getFileDescriptor());
            exifInterface.setAttribute("Software", "Android " + Build.DISPLAY);
            exifInterface.setAttribute("ImageWidth", Integer.toString(bitmap.getWidth()));
            exifInterface.setAttribute("ImageLength", Integer.toString(bitmap.getHeight()));
            ZonedDateTime zonedDateTimeOfInstant = ZonedDateTime.ofInstant(Instant.ofEpochMilli(this.mImageTime), ZoneId.systemDefault());
            exifInterface.setAttribute("DateTimeOriginal", DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss").format(zonedDateTimeOfInstant));
            exifInterface.setAttribute("SubSecTimeOriginal", DateTimeFormatter.ofPattern("SSS").format(zonedDateTimeOfInstant));
            if (Objects.equals(zonedDateTimeOfInstant.getOffset(), ZoneOffset.UTC)) {
                exifInterface.setAttribute("OffsetTimeOriginal", "+00:00");
            } else {
                exifInterface.setAttribute("OffsetTimeOriginal", DateTimeFormatter.ofPattern("XXX").format(zonedDateTimeOfInstant));
            }
            exifInterface.saveAttributes();
            parcelFileDescriptorOpenFile.close();
            contentValues.clear();
            contentValues.put("is_pending", (Integer) 0);
            contentValues.putNull("date_expires");
            contentResolver.update(uriInsert, contentValues, null, null);
            GlobalScreenshot$SavedImageData globalScreenshot$SavedImageData = this.mImageData;
            globalScreenshot$SavedImageData.uri = uriInsert;
            this.mParams.mActionsReadyListener.onActionsReady(globalScreenshot$SavedImageData);
            this.mParams.finisher.accept(this.mImageData.uri);
            GlobalScreenshot$SaveImageInBackgroundData globalScreenshot$SaveImageInBackgroundData = this.mParams;
            globalScreenshot$SaveImageInBackgroundData.image = null;
            globalScreenshot$SaveImageInBackgroundData.errorMsgResId = 0;
            return null;
        } finally {
        }
        contentResolver.delete(uriInsert, null);
        throw e;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onCancelled(Void r2) {
        this.mImageData.reset();
        this.mParams.mActionsReadyListener.onActionsReady(this.mImageData);
        this.mParams.finisher.accept(null);
        this.mParams.clearImage();
    }

    void setActionsReadyListener(GlobalScreenshot$ActionsReadyListener globalScreenshot$ActionsReadyListener) {
        this.mParams.mActionsReadyListener = globalScreenshot$ActionsReadyListener;
    }
}
