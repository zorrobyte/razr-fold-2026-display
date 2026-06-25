package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.util.time.SystemClock;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LegacyMediaDataFilterImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LegacyMediaDataFilterImpl implements MediaDataManager.Listener {
    private final Set _listeners;
    private final LinkedHashMap allEntries;
    private final BroadcastSender broadcastSender;
    private final Context context;
    private final Executor executor;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    private final MediaUiEventLogger logger;
    public MediaDataManager mediaDataManager;
    private final MediaFlags mediaFlags;
    private final SystemClock systemClock;
    private final LinkedHashMap userEntries;
    private final UserTracker userTracker;
    private final LegacyMediaDataFilterImpl$userTrackerCallback$1 userTrackerCallback;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataFilterImpl$userTrackerCallback$1, com.android.systemui.settings.UserTracker$Callback] */
    public LegacyMediaDataFilterImpl(Context context, UserTracker userTracker, BroadcastSender broadcastSender, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor, SystemClock systemClock, MediaUiEventLogger mediaUiEventLogger, MediaFlags mediaFlags) {
        context.getClass();
        userTracker.getClass();
        broadcastSender.getClass();
        notificationLockscreenUserManager.getClass();
        executor.getClass();
        systemClock.getClass();
        mediaUiEventLogger.getClass();
        mediaFlags.getClass();
        this.context = context;
        this.userTracker = userTracker;
        this.broadcastSender = broadcastSender;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.executor = executor;
        this.systemClock = systemClock;
        this.logger = mediaUiEventLogger;
        this.mediaFlags = mediaFlags;
        this._listeners = new LinkedHashSet();
        this.allEntries = new LinkedHashMap();
        this.userEntries = new LinkedHashMap();
        ?? r1 = new UserTracker.Callback() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataFilterImpl$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public void onProfilesChanged(List list) {
                list.getClass();
                this.this$0.handleProfileChanged$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core();
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public void onUserChanged(int i, Context context2) {
                context2.getClass();
                this.this$0.handleUserSwitched$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core();
            }
        };
        this.userTrackerCallback = r1;
        userTracker.addCallback(r1, executor);
    }

    public final boolean addListener(MediaDataManager.Listener listener) {
        listener.getClass();
        return this._listeners.add(listener);
    }

    public final Set getListeners() {
        return CollectionsKt.toSet(this._listeners);
    }

    public final MediaDataManager getMediaDataManager() {
        MediaDataManager mediaDataManager = this.mediaDataManager;
        if (mediaDataManager != null) {
            return mediaDataManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mediaDataManager");
        return null;
    }

    public final void handleProfileChanged$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        for (Map.Entry entry : this.allEntries.entrySet()) {
            String str = (String) entry.getKey();
            MediaData mediaData = (MediaData) entry.getValue();
            if (!this.lockscreenUserManager.isProfileAvailable(mediaData.getUserId())) {
                Log.d("MediaDataFilter", "Removing " + str + " after profile change");
                this.userEntries.remove(str, mediaData);
                Iterator it = getListeners().iterator();
                while (it.hasNext()) {
                    ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str);
                }
            }
        }
    }

    public final void handleUserSwitched$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        Set<MediaDataManager.Listener> listeners = getListeners();
        Set setKeySet = this.userEntries.keySet();
        setKeySet.getClass();
        List<String> mutableList = CollectionsKt.toMutableList((Collection) setKeySet);
        this.userEntries.clear();
        for (String str : mutableList) {
            Log.d("MediaDataFilter", "Removing " + str + " after user change");
            for (MediaDataManager.Listener listener : listeners) {
                str.getClass();
                listener.onMediaDataRemoved(str);
            }
        }
        for (Map.Entry entry : this.allEntries.entrySet()) {
            String str2 = (String) entry.getKey();
            MediaData mediaData = (MediaData) entry.getValue();
            if (this.lockscreenUserManager.isCurrentProfile(mediaData.getUserId())) {
                Log.d("MediaDataFilter", "Re-adding " + str2 + " after user change");
                this.userEntries.put(str2, mediaData);
                Iterator it = listeners.iterator();
                while (it.hasNext()) {
                    MediaDataProcessor.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str2, null, mediaData, false, false, 24, null);
                }
            }
        }
    }

    public final boolean hasActiveMediaOrRecommendation() {
        LinkedHashMap linkedHashMap = this.userEntries;
        if (linkedHashMap.isEmpty()) {
            return false;
        }
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            if (((MediaData) ((Map.Entry) it.next()).getValue()).getActive()) {
                return true;
            }
        }
        return false;
    }

    public final boolean hasAnyMediaOrRecommendation() {
        return !this.userEntries.isEmpty();
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, boolean z2) {
        str.getClass();
        mediaData.getClass();
        if (str2 != null && !Intrinsics.areEqual(str2, str)) {
            this.allEntries.remove(str2);
        }
        this.allEntries.put(str, mediaData);
        if (this.lockscreenUserManager.isCurrentProfile(mediaData.getUserId()) && this.lockscreenUserManager.isProfileAvailable(mediaData.getUserId())) {
            if (str2 != null && !Intrinsics.areEqual(str2, str)) {
                this.userEntries.remove(str2);
            }
            this.userEntries.put(str, mediaData);
            Iterator it = getListeners().iterator();
            while (it.hasNext()) {
                MediaDataProcessor.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str, str2, mediaData, false, false, 24, null);
            }
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener, com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataRemoved(String str) {
        str.getClass();
        this.allEntries.remove(str);
        if (((MediaData) this.userEntries.remove(str)) != null) {
            Iterator it = getListeners().iterator();
            while (it.hasNext()) {
                ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str);
            }
        }
    }

    public final void onSwipeToDismiss() {
        Log.d("MediaDataFilter", "Media carousel swiped away");
        Set setKeySet = this.userEntries.keySet();
        setKeySet.getClass();
        for (String str : CollectionsKt.toSet(setKeySet)) {
            MediaDataManager mediaDataManager = getMediaDataManager();
            str.getClass();
            mediaDataManager.mo1360setInactive(str, true, true);
        }
    }

    public final boolean removeListener(MediaDataManager.Listener listener) {
        listener.getClass();
        return this._listeners.remove(listener);
    }

    public final void setMediaDataManager(MediaDataManager mediaDataManager) {
        mediaDataManager.getClass();
        this.mediaDataManager = mediaDataManager;
    }
}
