package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import android.util.Log;
import com.android.internal.logging.InstanceId;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDataLoadingModel;
import com.android.systemui.media.controls.util.MediaFlags;
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

/* JADX INFO: compiled from: MediaDataFilterImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaDataFilterImpl implements MediaDataManager.Listener {
    private final Set _listeners;
    private final BroadcastSender broadcastSender;
    private final Context context;
    private final Executor executor;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    public MediaDataProcessor mediaDataProcessor;
    private final MediaFilterRepository mediaFilterRepository;
    private final MediaFlags mediaFlags;
    private final SystemClock systemClock;
    private final MediaDataFilterImpl$userTrackerCallback$1 userTrackerCallback;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.media.controls.domain.pipeline.MediaDataFilterImpl$userTrackerCallback$1, com.android.systemui.settings.UserTracker$Callback] */
    public MediaDataFilterImpl(Context context, UserTracker userTracker, BroadcastSender broadcastSender, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor, SystemClock systemClock, MediaFlags mediaFlags, MediaFilterRepository mediaFilterRepository) {
        context.getClass();
        userTracker.getClass();
        broadcastSender.getClass();
        notificationLockscreenUserManager.getClass();
        executor.getClass();
        systemClock.getClass();
        mediaFlags.getClass();
        mediaFilterRepository.getClass();
        this.context = context;
        this.broadcastSender = broadcastSender;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.executor = executor;
        this.systemClock = systemClock;
        this.mediaFlags = mediaFlags;
        this.mediaFilterRepository = mediaFilterRepository;
        this._listeners = new LinkedHashSet();
        ?? r1 = new UserTracker.Callback() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataFilterImpl$userTrackerCallback$1
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

    private final String getKey(InstanceId instanceId) {
        Map map = (Map) this.mediaFilterRepository.getAllUserEntries().getValue();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (Intrinsics.areEqual(((MediaData) entry.getValue()).getInstanceId(), instanceId)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        return (String) CollectionsKt.first(linkedHashMap.keySet());
    }

    public final boolean addListener(MediaDataProcessor.Listener listener) {
        listener.getClass();
        return this._listeners.add(listener);
    }

    public final Set getListeners() {
        return CollectionsKt.toSet(this._listeners);
    }

    public final MediaDataProcessor getMediaDataProcessor() {
        MediaDataProcessor mediaDataProcessor = this.mediaDataProcessor;
        if (mediaDataProcessor != null) {
            return mediaDataProcessor;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mediaDataProcessor");
        return null;
    }

    public final void handleProfileChanged$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        for (Map.Entry entry : ((Map) this.mediaFilterRepository.getAllUserEntries().getValue()).entrySet()) {
            String str = (String) entry.getKey();
            MediaData mediaData = (MediaData) entry.getValue();
            if (!this.lockscreenUserManager.isProfileAvailable(mediaData.getUserId())) {
                Log.d("MediaDataFilter", "Removing " + str + " after profile change");
                this.mediaFilterRepository.removeSelectedUserMediaEntry(mediaData.getInstanceId(), mediaData);
                this.mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Removed(mediaData.getInstanceId()));
                Iterator it = getListeners().iterator();
                while (it.hasNext()) {
                    ((MediaDataProcessor.Listener) it.next()).onMediaDataRemoved(str);
                }
            }
        }
    }

    public final void handleUserSwitched$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        Set listeners = getListeners();
        List<InstanceId> mutableList = CollectionsKt.toMutableList((Collection) ((Map) this.mediaFilterRepository.getSelectedUserEntries().getValue()).keySet());
        this.mediaFilterRepository.clearSelectedUserMedia();
        for (InstanceId instanceId : mutableList) {
            Log.d("MediaDataFilter", "Removing " + instanceId + " after user change");
            this.mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Removed(instanceId));
            String key = getKey(instanceId);
            if (key != null) {
                Iterator it = listeners.iterator();
                while (it.hasNext()) {
                    ((MediaDataProcessor.Listener) it.next()).onMediaDataRemoved(key);
                }
            }
        }
        for (Map.Entry entry : ((Map) this.mediaFilterRepository.getAllUserEntries().getValue()).entrySet()) {
            String str = (String) entry.getKey();
            MediaData mediaData = (MediaData) entry.getValue();
            if (this.lockscreenUserManager.isCurrentProfile(mediaData.getUserId())) {
                Log.d("MediaDataFilter", "Re-adding " + str + " with instanceId=" + mediaData.getInstanceId() + " after user change");
                this.mediaFilterRepository.addSelectedUserMediaEntry(mediaData);
                this.mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Loaded(mediaData.getInstanceId(), false, 2, null));
                Iterator it2 = listeners.iterator();
                while (it2.hasNext()) {
                    MediaDataProcessor.Listener.onMediaDataLoaded$default((MediaDataProcessor.Listener) it2.next(), str, null, mediaData, false, false, 24, null);
                }
            }
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, boolean z2) {
        str.getClass();
        mediaData.getClass();
        if (str2 != null && !Intrinsics.areEqual(str2, str)) {
            this.mediaFilterRepository.removeMediaEntry(str2);
        }
        this.mediaFilterRepository.addMediaEntry(str, mediaData);
        if (this.lockscreenUserManager.isCurrentProfile(mediaData.getUserId()) && this.lockscreenUserManager.isProfileAvailable(mediaData.getUserId())) {
            this.mediaFilterRepository.addSelectedUserMediaEntry(mediaData);
            this.mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Loaded(mediaData.getInstanceId(), false, 2, null));
            Iterator it = getListeners().iterator();
            while (it.hasNext()) {
                MediaDataProcessor.Listener.onMediaDataLoaded$default((MediaDataProcessor.Listener) it.next(), str, str2, mediaData, false, false, 24, null);
            }
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener, com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataRemoved(String str) {
        str.getClass();
        MediaData mediaDataRemoveMediaEntry = this.mediaFilterRepository.removeMediaEntry(str);
        if (mediaDataRemoveMediaEntry != null) {
            InstanceId instanceId = mediaDataRemoveMediaEntry.getInstanceId();
            if (this.mediaFilterRepository.removeSelectedUserMediaEntry(instanceId) != null) {
                this.mediaFilterRepository.addMediaDataLoadingState(new MediaDataLoadingModel.Removed(instanceId));
                Iterator it = getListeners().iterator();
                while (it.hasNext()) {
                    ((MediaDataProcessor.Listener) it.next()).onMediaDataRemoved(str);
                }
            }
        }
    }

    public final void onSwipeToDismiss() {
        Log.d("MediaDataFilter", "Media carousel swiped away");
        for (Map.Entry entry : ((Map) this.mediaFilterRepository.getAllUserEntries().getValue()).entrySet()) {
            String str = (String) entry.getKey();
            if (((Map) this.mediaFilterRepository.getSelectedUserEntries().getValue()).containsKey(((MediaData) entry.getValue()).getInstanceId())) {
                getMediaDataProcessor().setInactive(str, true, true);
            }
        }
    }

    public final boolean removeListener(MediaDataProcessor.Listener listener) {
        listener.getClass();
        return this._listeners.remove(listener);
    }
}
