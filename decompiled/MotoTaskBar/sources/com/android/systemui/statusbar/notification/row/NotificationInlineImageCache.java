package com.android.systemui.statusbar.notification.row;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageCache;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public class NotificationInlineImageCache implements NotificationInlineImageResolver.ImageCache {
    private static final String TAG = "NotificationInlineImageCache";
    private final ConcurrentHashMap mCache = new ConcurrentHashMap();
    private NotificationInlineImageResolver mResolver;

    class PreloadImageTask extends AsyncTask {
        private final NotificationInlineImageResolver mResolver;

        PreloadImageTask(NotificationInlineImageResolver notificationInlineImageResolver) {
            this.mResolver = notificationInlineImageResolver;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Drawable doInBackground(Uri... uriArr) {
            return this.mResolver.resolveImage(uriArr[0]);
        }
    }

    public static /* synthetic */ void $r8$lambda$MbuUZf7ckqrGK6mNEQlImNxD0h8(Uri uri, PreloadImageTask preloadImageTask) {
        if (preloadImageTask.getStatus() != AsyncTask.Status.FINISHED) {
            preloadImageTask.cancel(true);
        }
    }

    public static /* synthetic */ boolean $r8$lambda$u9ligJEBrFntlNgxmlYwyfqTLBE(Set set, Map.Entry entry) {
        return !set.contains(entry.getKey());
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver.ImageCache
    public void cancelRunningTasks() {
        this.mCache.forEach(new BiConsumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationInlineImageCache$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                NotificationInlineImageCache.$r8$lambda$MbuUZf7ckqrGK6mNEQlImNxD0h8((Uri) obj, (NotificationInlineImageCache.PreloadImageTask) obj2);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver.ImageCache
    public Drawable get(Uri uri, long j) {
        try {
            return (Drawable) ((PreloadImageTask) this.mCache.get(uri)).get(j, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | CancellationException | ExecutionException | TimeoutException e) {
            Log.d(TAG, "get: Failed get image from " + uri + " " + e);
            return null;
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver.ImageCache
    public boolean hasEntry(Uri uri) {
        return this.mCache.containsKey(uri);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver.ImageCache
    public void preload(Uri uri) {
        PreloadImageTask preloadImageTask = new PreloadImageTask(this.mResolver);
        preloadImageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
        this.mCache.put(uri, preloadImageTask);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver.ImageCache
    public void purge() {
        final Set wantedUriSet = this.mResolver.getWantedUriSet();
        this.mCache.entrySet().removeIf(new Predicate() { // from class: com.android.systemui.statusbar.notification.row.NotificationInlineImageCache$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return NotificationInlineImageCache.$r8$lambda$u9ligJEBrFntlNgxmlYwyfqTLBE(wantedUriSet, (Map.Entry) obj);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver.ImageCache
    public void setImageResolver(NotificationInlineImageResolver notificationInlineImageResolver) {
        this.mResolver = notificationInlineImageResolver;
    }
}
