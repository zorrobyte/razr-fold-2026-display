package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.util.Log;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.media.controls.shared.model.MediaData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaSessionBasedFilter.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaSessionBasedFilter implements MediaDataManager.Listener {
    private final Executor backgroundExecutor;
    private final Executor foregroundExecutor;
    private final Map keyedTokens;
    private final Set listeners;
    private final LinkedHashMap packageControllers;
    private final MediaSessionBasedFilter$sessionListener$1 sessionListener;
    private final MediaSessionManager sessionManager;
    private final Set tokensWithNotifications;

    /* JADX INFO: compiled from: MediaSessionBasedFilter.kt */
    final class TokenId {
        private final int id;

        public TokenId(int i) {
            this.id = i;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public TokenId(MediaSession.Token token) {
            this(token.hashCode());
            token.getClass();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof TokenId) && this.id == ((TokenId) obj).id;
        }

        public int hashCode() {
            return Integer.hashCode(this.id);
        }

        public String toString() {
            return "TokenId(id=" + this.id + ")";
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter$sessionListener$1] */
    public MediaSessionBasedFilter(Context context, MediaSessionManager mediaSessionManager, Executor executor, Executor executor2) {
        context.getClass();
        mediaSessionManager.getClass();
        executor.getClass();
        executor2.getClass();
        this.sessionManager = mediaSessionManager;
        this.foregroundExecutor = executor;
        this.backgroundExecutor = executor2;
        this.listeners = new LinkedHashSet();
        this.packageControllers = new LinkedHashMap();
        this.keyedTokens = new LinkedHashMap();
        this.tokensWithNotifications = new LinkedHashSet();
        this.sessionListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter$sessionListener$1
            @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
            public void onActiveSessionsChanged(List list) {
                this.this$0.handleControllersChanged(list);
            }
        };
        executor2.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSessionBasedFilter.this.sessionManager.addOnActiveSessionsChangedListener(MediaSessionBasedFilter.this.sessionListener, null);
                MediaSessionBasedFilter mediaSessionBasedFilter = MediaSessionBasedFilter.this;
                mediaSessionBasedFilter.handleControllersChanged(mediaSessionBasedFilter.sessionManager.getActiveSessions(null));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dispatchMediaDataLoaded(final String str, final String str2, final MediaData mediaData, final boolean z) {
        this.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter.dispatchMediaDataLoaded.1
            @Override // java.lang.Runnable
            public final void run() {
                Set set = CollectionsKt.toSet(MediaSessionBasedFilter.this.listeners);
                String str3 = str;
                String str4 = str2;
                MediaData mediaData2 = mediaData;
                boolean z2 = z;
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    MediaDataProcessor.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str3, str4, mediaData2, z2, false, 16, null);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dispatchMediaDataRemoved(final String str) {
        this.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter.dispatchMediaDataRemoved.1
            @Override // java.lang.Runnable
            public final void run() {
                Set set = CollectionsKt.toSet(MediaSessionBasedFilter.this.listeners);
                String str2 = str;
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleControllersChanged(List list) {
        this.packageControllers.clear();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                MediaController mediaController = (MediaController) it.next();
                List list2 = (List) this.packageControllers.get(mediaController.getPackageName());
                if (list2 != null) {
                    list2.add(mediaController);
                } else {
                    this.packageControllers.put(mediaController.getPackageName(), CollectionsKt.mutableListOf(mediaController));
                }
            }
        }
        if (list != null) {
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                MediaSession.Token sessionToken = ((MediaController) it2.next()).getSessionToken();
                sessionToken.getClass();
                arrayList.add(new TokenId(sessionToken));
            }
            this.tokensWithNotifications.retainAll(arrayList);
        }
    }

    public final boolean addListener(MediaDataManager.Listener listener) {
        listener.getClass();
        return this.listeners.add(listener);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataLoaded(final String str, final String str2, final MediaData mediaData, final boolean z, boolean z2) {
        str.getClass();
        mediaData.getClass();
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter.onMediaDataLoaded.1
            @Override // java.lang.Runnable
            public final void run() {
                ArrayList arrayList;
                Set set;
                MediaSession.Token token = mediaData.getToken();
                if (token != null) {
                    this.tokensWithNotifications.add(new TokenId(token));
                }
                String str3 = str2;
                boolean z3 = (str3 == null || Intrinsics.areEqual(str, str3)) ? false : true;
                if (z3 && (set = (Set) this.keyedTokens.remove(str2)) != null) {
                }
                if (mediaData.getToken() != null) {
                    Set set2 = (Set) this.keyedTokens.get(str);
                    if (set2 != null) {
                        set2.add(new TokenId(mediaData.getToken()));
                    }
                }
                List list = (List) this.packageControllers.get(mediaData.getPackageName());
                MediaController mediaController = null;
                if (list != null) {
                    arrayList = new ArrayList();
                    for (Object obj : list) {
                        MediaController.PlaybackInfo playbackInfo = ((MediaController) obj).getPlaybackInfo();
                        if (playbackInfo != null && playbackInfo.getPlaybackType() == 2) {
                            arrayList.add(obj);
                        }
                    }
                } else {
                    arrayList = null;
                }
                if (arrayList != null && arrayList.size() == 1) {
                    mediaController = (MediaController) CollectionsKt.firstOrNull((List) arrayList);
                }
                if (!z3 && mediaController != null && !Intrinsics.areEqual(mediaController.getSessionToken(), mediaData.getToken())) {
                    Set set3 = this.tokensWithNotifications;
                    MediaSession.Token sessionToken = mediaController.getSessionToken();
                    sessionToken.getClass();
                    if (set3.contains(new TokenId(sessionToken))) {
                        Log.d("MediaSessionBasedFilter", "filtering key=" + str + " local=" + mediaData.getToken() + " remote=" + mediaController.getSessionToken());
                        Object obj2 = this.keyedTokens.get(str);
                        obj2.getClass();
                        MediaSession.Token sessionToken2 = mediaController.getSessionToken();
                        sessionToken2.getClass();
                        if (((Set) obj2).contains(new TokenId(sessionToken2))) {
                            return;
                        }
                        this.dispatchMediaDataRemoved(str);
                        return;
                    }
                }
                this.dispatchMediaDataLoaded(str, str2, mediaData, z);
            }
        });
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener, com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataRemoved(final String str) {
        str.getClass();
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter.onMediaDataRemoved.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSessionBasedFilter.this.keyedTokens.remove(str);
                MediaSessionBasedFilter.this.dispatchMediaDataRemoved(str);
            }
        });
    }
}
