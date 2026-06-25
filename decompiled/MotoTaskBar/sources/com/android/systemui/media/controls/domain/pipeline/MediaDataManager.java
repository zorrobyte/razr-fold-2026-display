package com.android.systemui.media.controls.domain.pipeline;

import android.app.PendingIntent;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import android.service.notification.StatusBarNotification;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;

/* JADX INFO: compiled from: MediaDataManager.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MediaDataManager {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: MediaDataManager.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final boolean isMediaNotification(StatusBarNotification statusBarNotification) {
            statusBarNotification.getClass();
            return statusBarNotification.getNotification().isMediaNotification();
        }
    }

    /* JADX INFO: compiled from: MediaDataManager.kt */
    public interface Listener extends MediaDataProcessor.Listener {
        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
        default void onMediaDataRemoved(String str) {
            str.getClass();
        }
    }

    static boolean isMediaNotification(StatusBarNotification statusBarNotification) {
        return Companion.isMediaNotification(statusBarNotification);
    }

    static /* synthetic */ void setInactive$default(MediaDataManager mediaDataManager, String str, boolean z, boolean z2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setInactive");
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        mediaDataManager.mo1360setInactive(str, z, z2);
    }

    void addListener(Listener listener);

    void addResumptionControls(int i, MediaDescription mediaDescription, Runnable runnable, MediaSession.Token token, String str, PendingIntent pendingIntent, String str2);

    boolean dismissMediaData(String str, long j);

    boolean hasActiveMediaOrRecommendation();

    boolean hasAnyMediaOrRecommendation();

    void onNotificationAdded(String str, StatusBarNotification statusBarNotification);

    void onNotificationRemoved(String str);

    void onSwipeToDismiss();

    void removeListener(Listener listener);

    /* JADX INFO: renamed from: setInactive */
    void mo1360setInactive(String str, boolean z, boolean z2);

    void setResumeAction(String str, Runnable runnable);
}
