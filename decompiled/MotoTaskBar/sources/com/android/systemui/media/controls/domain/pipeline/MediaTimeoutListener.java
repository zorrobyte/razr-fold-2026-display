package com.android.systemui.media.controls.domain.pipeline;

import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: MediaTimeoutListener.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaTimeoutListener implements MediaDataManager.Listener {
    private final DelayableExecutor mainExecutor;
    private final MediaControllerFactory mediaControllerFactory;
    private final MediaFlags mediaFlags;
    private final Map mediaListeners;
    public Function1 sessionCallback;
    public Function2 stateCallback;
    private final SystemClock systemClock;
    public Function2 timeoutCallback;

    /* JADX INFO: compiled from: MediaTimeoutListener.kt */
    final class PlaybackStateListener extends MediaController.Callback {
        private Runnable cancellation;
        private boolean destroyed;
        private long expiration;
        private String key;
        private PlaybackState lastState;
        private MediaController mediaController;
        private Boolean resumption;
        private MediaSession.Token sessionToken;
        final /* synthetic */ MediaTimeoutListener this$0;
        private boolean timedOut;

        public PlaybackStateListener(MediaTimeoutListener mediaTimeoutListener, String str, MediaData mediaData) {
            str.getClass();
            mediaData.getClass();
            this.this$0 = mediaTimeoutListener;
            this.key = str;
            this.expiration = Long.MAX_VALUE;
            setMediaData(mediaData);
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x004f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private final void processState(android.media.session.PlaybackState r8, boolean r9, java.lang.Boolean r10) {
            /*
                Method dump skipped, instruction units count: 259
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener.PlaybackStateListener.processState(android.media.session.PlaybackState, boolean, java.lang.Boolean):void");
        }

        public final void destroy() {
            MediaController mediaController = this.mediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(this);
            }
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                runnable.run();
            }
            this.destroyed = true;
        }

        public final void doTimeout() {
            this.cancellation = null;
            this.timedOut = true;
            this.expiration = Long.MAX_VALUE;
            this.this$0.getTimeoutCallback().invoke(this.key, Boolean.valueOf(this.timedOut));
        }

        public final void expireMediaTimeout(String str, String str2) {
            str.getClass();
            str2.getClass();
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                runnable.run();
            }
            this.expiration = Long.MAX_VALUE;
            this.cancellation = null;
        }

        public final boolean getDestroyed() {
            return this.destroyed;
        }

        public final boolean isPlaying() {
            PlaybackState playbackState = this.lastState;
            if (playbackState != null) {
                return isPlaying(playbackState.getState());
            }
            return false;
        }

        public final boolean isPlaying(int i) {
            return NotificationMediaManager.isPlayingState(i);
        }

        @Override // android.media.session.MediaController.Callback
        public void onPlaybackStateChanged(PlaybackState playbackState) {
            processState(playbackState, true, this.resumption);
        }

        @Override // android.media.session.MediaController.Callback
        public void onSessionDestroyed() {
            if (!Intrinsics.areEqual(this.resumption, Boolean.TRUE)) {
                this.this$0.getSessionCallback().invoke(this.key);
                destroy();
            } else {
                MediaController mediaController = this.mediaController;
                if (mediaController != null) {
                    mediaController.unregisterCallback(this);
                }
            }
        }

        public final void setKey(String str) {
            str.getClass();
            this.key = str;
        }

        public final void setMediaData(MediaData mediaData) {
            mediaData.getClass();
            this.sessionToken = mediaData.getToken();
            this.destroyed = false;
            MediaController mediaController = this.mediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(this);
            }
            MediaController mediaControllerCreate = mediaData.getToken() != null ? this.this$0.mediaControllerFactory.create(mediaData.getToken()) : null;
            this.mediaController = mediaControllerCreate;
            if (mediaControllerCreate != null) {
                mediaControllerCreate.registerCallback(this);
            }
            MediaController mediaController2 = this.mediaController;
            processState(mediaController2 != null ? mediaController2.getPlaybackState() : null, false, Boolean.valueOf(mediaData.getResumption()));
        }
    }

    public MediaTimeoutListener(MediaControllerFactory mediaControllerFactory, DelayableExecutor delayableExecutor, SystemClock systemClock, MediaFlags mediaFlags) {
        mediaControllerFactory.getClass();
        delayableExecutor.getClass();
        systemClock.getClass();
        mediaFlags.getClass();
        this.mediaControllerFactory = mediaControllerFactory;
        this.mainExecutor = delayableExecutor;
        this.systemClock = systemClock;
        this.mediaFlags = mediaFlags;
        this.mediaListeners = new LinkedHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean areCustomActionListsEqual(List list, List list2) {
        if (list == list2) {
            return true;
        }
        if (list == null || list2 == null || list.size() != list2.size()) {
            return false;
        }
        for (Pair pair : SequencesKt.zip(CollectionsKt.asSequence(list), CollectionsKt.asSequence(list2))) {
            if (!areCustomActionsEqual((PlaybackState.CustomAction) pair.component1(), (PlaybackState.CustomAction) pair.component2())) {
                return false;
            }
        }
        return true;
    }

    private final boolean areCustomActionsEqual(PlaybackState.CustomAction customAction, PlaybackState.CustomAction customAction2) {
        if (!Intrinsics.areEqual(customAction.getAction(), customAction2.getAction()) || !Intrinsics.areEqual(customAction.getName(), customAction2.getName()) || customAction.getIcon() != customAction2.getIcon()) {
            return false;
        }
        if ((customAction.getExtras() == null) != (customAction2.getExtras() == null)) {
            return false;
        }
        if (customAction.getExtras() != null) {
            Set<String> setKeySet = customAction.getExtras().keySet();
            setKeySet.getClass();
            for (String str : setKeySet) {
                if (!Intrinsics.areEqual(customAction.getExtras().get(str), customAction2.getExtras().get(str))) {
                    return false;
                }
            }
        }
        return true;
    }

    public final Function1 getSessionCallback() {
        Function1 function1 = this.sessionCallback;
        if (function1 != null) {
            return function1;
        }
        Intrinsics.throwUninitializedPropertyAccessException("sessionCallback");
        return null;
    }

    public final Function2 getStateCallback() {
        Function2 function2 = this.stateCallback;
        if (function2 != null) {
            return function2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("stateCallback");
        return null;
    }

    public final Function2 getTimeoutCallback() {
        Function2 function2 = this.timeoutCallback;
        if (function2 != null) {
            return function2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("timeoutCallback");
        return null;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataLoaded(final String str, String str2, MediaData mediaData, boolean z, boolean z2) {
        Object objRemove;
        str.getClass();
        mediaData.getClass();
        PlaybackStateListener playbackStateListener = (PlaybackStateListener) this.mediaListeners.get(str);
        if (playbackStateListener != null) {
            boolean destroyed = playbackStateListener.getDestroyed();
            objRemove = playbackStateListener;
            if (!destroyed) {
                return;
            }
        } else {
            objRemove = null;
        }
        if (str2 != null && !Intrinsics.areEqual(str, str2)) {
            objRemove = this.mediaListeners.remove(str2);
        }
        PlaybackStateListener playbackStateListener2 = (PlaybackStateListener) objRemove;
        if (playbackStateListener2 == null) {
            this.mediaListeners.put(str, new PlaybackStateListener(this, str, mediaData));
            return;
        }
        boolean zIsPlaying = playbackStateListener2.isPlaying();
        playbackStateListener2.setMediaData(mediaData);
        playbackStateListener2.setKey(str);
        this.mediaListeners.put(str, playbackStateListener2);
        if (zIsPlaying != playbackStateListener2.isPlaying()) {
            this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener$onMediaDataLoaded$2$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaTimeoutListener.PlaybackStateListener playbackStateListener3 = (MediaTimeoutListener.PlaybackStateListener) this.this$0.mediaListeners.get(str);
                    if (playbackStateListener3 == null || !playbackStateListener3.isPlaying()) {
                        return;
                    }
                    this.this$0.getTimeoutCallback().invoke(str, Boolean.FALSE);
                }
            });
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener, com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataRemoved(String str) {
        str.getClass();
        PlaybackStateListener playbackStateListener = (PlaybackStateListener) this.mediaListeners.remove(str);
        if (playbackStateListener != null) {
            playbackStateListener.destroy();
        }
    }

    public final void setSessionCallback(Function1 function1) {
        function1.getClass();
        this.sessionCallback = function1;
    }

    public final void setStateCallback(Function2 function2) {
        function2.getClass();
        this.stateCallback = function2;
    }

    public final void setTimeoutCallback(Function2 function2) {
        function2.getClass();
        this.timeoutCallback = function2;
    }
}
