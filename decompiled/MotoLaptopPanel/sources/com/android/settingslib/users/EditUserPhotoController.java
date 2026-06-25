package com.android.settingslib.users;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.android.internal.util.UserIcons;
import com.android.settingslib.R$string;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.utils.ThreadUtils;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public class EditUserPhotoController {
    private final Activity mActivity;
    private final ActivityStarter mActivityStarter;
    private String mCachedDrawablePath;
    private final ListeningExecutorService mExecutorService;
    private final String mFileAuthority;
    private final ImageView mImageView;
    private final File mImagesDir;
    private Bitmap mNewUserPhotoBitmap;
    private Drawable mNewUserPhotoDrawable;

    public EditUserPhotoController(Activity activity, ActivityStarter activityStarter, ImageView imageView, Bitmap bitmap, Drawable drawable, String str) {
        this(activity, activityStarter, imageView, bitmap, drawable, str, true);
    }

    public EditUserPhotoController(Activity activity, ActivityStarter activityStarter, ImageView imageView, Bitmap bitmap, Drawable drawable, String str, final boolean z) {
        this.mActivity = activity;
        this.mActivityStarter = activityStarter;
        this.mFileAuthority = str;
        File file = new File(activity.getCacheDir(), "multi_user");
        this.mImagesDir = file;
        file.mkdir();
        this.mImageView = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(z, view);
            }
        });
        this.mNewUserPhotoBitmap = bitmap;
        this.mNewUserPhotoDrawable = drawable;
        this.mExecutorService = ThreadUtils.getBackgroundExecutor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(boolean z, View view) {
        showAvatarPicker(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Bitmap lambda$onDefaultIconSelected$1(int i) {
        Resources resources = this.mActivity.getResources();
        return UserIcons.convertToBitmapAtUserIconSize(resources, UserIcons.getDefaultUserIconInColor(resources, i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0034 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ android.graphics.Bitmap lambda$onPhotoCropped$2(android.net.Uri r5) throws java.lang.Throwable {
        /*
            r4 = this;
            java.lang.String r0 = "Cannot close image stream"
            java.lang.String r1 = "EditUserPhotoController"
            r2 = 0
            android.app.Activity r4 = r4.mActivity     // Catch: java.lang.Throwable -> L23 java.io.FileNotFoundException -> L25
            android.content.ContentResolver r4 = r4.getContentResolver()     // Catch: java.lang.Throwable -> L23 java.io.FileNotFoundException -> L25
            java.io.InputStream r4 = r4.openInputStream(r5)     // Catch: java.lang.Throwable -> L23 java.io.FileNotFoundException -> L25
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeStream(r4)     // Catch: java.lang.Throwable -> L1e java.io.FileNotFoundException -> L21
            if (r4 == 0) goto L31
            r4.close()     // Catch: java.io.IOException -> L19
            goto L31
        L19:
            r4 = move-exception
            android.util.Log.w(r1, r0, r4)
            goto L31
        L1e:
            r5 = move-exception
            r2 = r4
            goto L32
        L21:
            r5 = move-exception
            goto L27
        L23:
            r5 = move-exception
            goto L32
        L25:
            r5 = move-exception
            r4 = r2
        L27:
            java.lang.String r3 = "Cannot find image file"
            android.util.Log.w(r1, r3, r5)     // Catch: java.lang.Throwable -> L1e
            if (r4 == 0) goto L31
            r4.close()     // Catch: java.io.IOException -> L19
        L31:
            return r2
        L32:
            if (r2 == 0) goto L3c
            r2.close()     // Catch: java.io.IOException -> L38
            goto L3c
        L38:
            r4 = move-exception
            android.util.Log.w(r1, r0, r4)
        L3c:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.users.EditUserPhotoController.lambda$onPhotoCropped$2(android.net.Uri):android.graphics.Bitmap");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPhotoProcessed$3() {
        this.mCachedDrawablePath = saveNewUserPhotoBitmap().getPath();
    }

    static Bitmap loadNewUserPhotoBitmap(File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    private void onDefaultIconSelected(final int i) {
        Futures.addCallback(this.mExecutorService.submit(new Callable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$onDefaultIconSelected$1(i);
            }
        }), new FutureCallback() { // from class: com.android.settingslib.users.EditUserPhotoController.1
            @Override // com.google.common.util.concurrent.FutureCallback
            public void onFailure(Throwable th) {
                Log.e("EditUserPhotoController", "Error processing default icon", th);
            }

            @Override // com.google.common.util.concurrent.FutureCallback
            public void onSuccess(Bitmap bitmap) {
                EditUserPhotoController.this.onPhotoProcessed(bitmap);
            }
        }, this.mImageView.getContext().getMainExecutor());
    }

    private void onPhotoCropped(final Uri uri) {
        Futures.addCallback(this.mExecutorService.submit(new Callable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.lambda$onPhotoCropped$2(uri);
            }
        }), new FutureCallback() { // from class: com.android.settingslib.users.EditUserPhotoController.2
            @Override // com.google.common.util.concurrent.FutureCallback
            public void onFailure(Throwable th) {
            }

            @Override // com.google.common.util.concurrent.FutureCallback
            public void onSuccess(Bitmap bitmap) {
                EditUserPhotoController.this.onPhotoProcessed(bitmap);
            }
        }, this.mImageView.getContext().getMainExecutor());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPhotoProcessed(Bitmap bitmap) {
        if (bitmap != null) {
            this.mNewUserPhotoBitmap = bitmap;
            this.mExecutorService.submit(new Runnable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onPhotoProcessed$3();
                }
            });
            CircleFramedDrawable circleFramedDrawable = CircleFramedDrawable.getInstance(this.mImageView.getContext(), this.mNewUserPhotoBitmap);
            this.mNewUserPhotoDrawable = circleFramedDrawable;
            this.mImageView.setImageDrawable(circleFramedDrawable);
        }
    }

    private void showAvatarPicker(boolean z) {
        Intent intent = new Intent("com.android.avatarpicker.FULL_SCREEN_ACTIVITY");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra("is_user_new", z);
        intent.setClassName(this.mActivity.getString(R$string.config_avatar_picker_package), this.mActivity.getString(R$string.config_avatar_picker_class));
        intent.putExtra("file_authority", this.mFileAuthority);
        this.mActivityStarter.startActivityForResult(intent, 1004);
    }

    String getCachedDrawablePath() {
        return this.mCachedDrawablePath;
    }

    public Drawable getNewUserPhotoDrawable() {
        return this.mNewUserPhotoDrawable;
    }

    public boolean onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && i == 1004) {
            if (intent.hasExtra("default_icon_tint_color")) {
                onDefaultIconSelected(intent.getIntExtra("default_icon_tint_color", -1));
                return true;
            }
            if (intent.getData() != null) {
                onPhotoCropped(intent.getData());
                return true;
            }
        }
        return false;
    }

    File saveNewUserPhotoBitmap() {
        if (this.mNewUserPhotoBitmap == null) {
            return null;
        }
        try {
            File file = new File(this.mImagesDir, "NewUserPhoto.png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            this.mNewUserPhotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return file;
        } catch (IOException e) {
            Log.e("EditUserPhotoController", "Cannot create temp file", e);
            return null;
        }
    }
}
