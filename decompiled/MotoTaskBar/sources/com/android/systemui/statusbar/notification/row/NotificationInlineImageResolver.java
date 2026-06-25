package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;
import com.android.internal.widget.ImageResolver;
import com.android.internal.widget.LocalImageResolver;
import com.android.internal.widget.MessagingMessage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NotificationInlineImageResolver implements ImageResolver {
    private static final String TAG = NotificationInlineImageResolver.class.getSimpleName();
    private final Context mContext;
    private final ImageCache mImageCache;
    protected int mMaxImageHeight;
    protected int mMaxImageWidth;
    private Set mWantedUriSet;

    interface ImageCache {
        void cancelRunningTasks();

        Drawable get(Uri uri, long j);

        boolean hasEntry(Uri uri);

        void preload(Uri uri);

        void purge();

        void setImageResolver(NotificationInlineImageResolver notificationInlineImageResolver);
    }

    public NotificationInlineImageResolver(Context context, ImageCache imageCache) {
        this.mContext = context;
        this.mImageCache = imageCache;
        if (imageCache != null) {
            imageCache.setImageResolver(this);
        }
        updateMaxImageSizes();
    }

    private boolean isLowRam() {
        return ActivityManager.isLowRamDeviceStatic();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$preloadImages$0(Uri uri) {
        if (this.mImageCache.hasEntry(uri)) {
            return;
        }
        this.mImageCache.preload(uri);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$waitForPreloadedImages$1(long j, Uri uri) {
        loadImageFromCache(uri, j - SystemClock.elapsedRealtime());
    }

    private Drawable loadImageFromCache(Uri uri, long j) {
        if (!this.mImageCache.hasEntry(uri)) {
            this.mImageCache.preload(uri);
        }
        return this.mImageCache.get(uri, j);
    }

    private void retrieveWantedUriSet(Notification notification) {
        HashSet hashSet = new HashSet();
        Bundle bundle = notification.extras;
        if (bundle == null) {
            return;
        }
        Parcelable[] parcelableArray = bundle.getParcelableArray("android.messages");
        List<Notification.MessagingStyle.Message> messagesFromBundleArray = parcelableArray == null ? null : Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray);
        if (messagesFromBundleArray != null) {
            for (Notification.MessagingStyle.Message message : messagesFromBundleArray) {
                if (MessagingMessage.hasImage(message)) {
                    hashSet.add(message.getDataUri());
                }
            }
        }
        Parcelable[] parcelableArray2 = bundle.getParcelableArray("android.messages.historic");
        List<Notification.MessagingStyle.Message> messagesFromBundleArray2 = parcelableArray2 != null ? Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray2) : null;
        if (messagesFromBundleArray2 != null) {
            for (Notification.MessagingStyle.Message message2 : messagesFromBundleArray2) {
                if (MessagingMessage.hasImage(message2)) {
                    hashSet.add(message2.getDataUri());
                }
            }
        }
        this.mWantedUriSet = hashSet;
    }

    void cancelRunningTasks() {
        if (hasCache()) {
            this.mImageCache.cancelRunningTasks();
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    protected int getMaxImageHeight() {
        return this.mContext.getResources().getDimensionPixelSize(isLowRam() ? R.dimen.notification_headerless_min_height : R.dimen.notification_headerless_margin_twoline);
    }

    protected int getMaxImageWidth() {
        return this.mContext.getResources().getDimensionPixelSize(isLowRam() ? R.dimen.notification_icon_circle_padding : R.dimen.notification_heading_margin_end);
    }

    Set getWantedUriSet() {
        return this.mWantedUriSet;
    }

    public boolean hasCache() {
        return (this.mImageCache == null || isLowRam()) ? false : true;
    }

    public Drawable loadImage(Uri uri) {
        return hasCache() ? loadImageFromCache(uri, 100L) : resolveImage(uri);
    }

    public void preloadImages(Notification notification) {
        if (hasCache()) {
            retrieveWantedUriSet(notification);
            getWantedUriSet().forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$preloadImages$0((Uri) obj);
                }
            });
        }
    }

    public void purgeCache() {
        if (hasCache()) {
            this.mImageCache.purge();
        }
    }

    Drawable resolveImage(Uri uri) {
        try {
            return LocalImageResolver.resolveImage(uri, this.mContext, this.mMaxImageWidth, this.mMaxImageHeight);
        } catch (Exception e) {
            Log.d(TAG, "resolveImage: Can't load image from " + uri, e);
            return null;
        }
    }

    public void updateMaxImageSizes() {
        this.mMaxImageWidth = getMaxImageWidth();
        this.mMaxImageHeight = getMaxImageHeight();
    }

    void waitForPreloadedImages(long j) {
        Set wantedUriSet;
        if (hasCache() && (wantedUriSet = getWantedUriSet()) != null) {
            final long jElapsedRealtime = SystemClock.elapsedRealtime() + j;
            wantedUriSet.forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$waitForPreloadedImages$1(jElapsedRealtime, (Uri) obj);
                }
            });
        }
    }
}
