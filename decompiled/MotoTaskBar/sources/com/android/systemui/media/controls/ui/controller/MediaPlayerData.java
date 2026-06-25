package com.android.systemui.media.controls.ui.controller;

import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.ui.controller.MediaPlayerData;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaCarouselController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaPlayerData {
    private static final MediaData EMPTY;
    public static final MediaPlayerData INSTANCE = new MediaPlayerData();
    private static final Comparator comparator;
    private static final Map mediaData;
    private static final TreeMap mediaPlayers;
    private static boolean shouldPrioritizeSs;
    private static final LinkedHashMap visibleMediaPlayers;

    /* JADX INFO: compiled from: MediaCarouselController.kt */
    public final class MediaSortKey {
        private final MediaData data;
        private final boolean isSsMediaRec;
        private final boolean isSsReactivated;
        private final String key;
        private final long updateTime;

        public MediaSortKey(boolean z, MediaData mediaData, String str, long j, boolean z2) {
            mediaData.getClass();
            str.getClass();
            this.isSsMediaRec = z;
            this.data = mediaData;
            this.key = str;
            this.updateTime = j;
            this.isSsReactivated = z2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaSortKey)) {
                return false;
            }
            MediaSortKey mediaSortKey = (MediaSortKey) obj;
            return this.isSsMediaRec == mediaSortKey.isSsMediaRec && Intrinsics.areEqual(this.data, mediaSortKey.data) && Intrinsics.areEqual(this.key, mediaSortKey.key) && this.updateTime == mediaSortKey.updateTime && this.isSsReactivated == mediaSortKey.isSsReactivated;
        }

        public final MediaData getData() {
            return this.data;
        }

        public final String getKey() {
            return this.key;
        }

        public final long getUpdateTime() {
            return this.updateTime;
        }

        public int hashCode() {
            return (((((((Boolean.hashCode(this.isSsMediaRec) * 31) + this.data.hashCode()) * 31) + this.key.hashCode()) * 31) + Long.hashCode(this.updateTime)) * 31) + Boolean.hashCode(this.isSsReactivated);
        }

        public final boolean isSsMediaRec() {
            return this.isSsMediaRec;
        }

        public final boolean isSsReactivated() {
            return this.isSsReactivated;
        }

        public String toString() {
            return "MediaSortKey(isSsMediaRec=" + this.isSsMediaRec + ", data=" + this.data + ", key=" + this.key + ", updateTime=" + this.updateTime + ", isSsReactivated=" + this.isSsReactivated + ")";
        }
    }

    static {
        List listEmptyList = CollectionsKt.emptyList();
        List listEmptyList2 = CollectionsKt.emptyList();
        InstanceId instanceIdFakeInstanceId = InstanceId.fakeInstanceId(-1);
        instanceIdFakeInstanceId.getClass();
        EMPTY = new MediaData(-1, false, null, null, null, null, null, listEmptyList, listEmptyList2, null, "INVALID", null, null, null, true, null, 0, false, null, false, null, false, 0L, 0L, instanceIdFakeInstanceId, -1, false, null, 218038784, null);
        final Comparator comparator2 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$compareByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) obj2;
                Boolean boolIsPlaying = mediaSortKey.getData().isPlaying();
                Boolean bool = Boolean.TRUE;
                boolean z = false;
                Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual(boolIsPlaying, bool) && mediaSortKey.getData().getPlaybackLocation() == 0);
                MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) obj;
                if (Intrinsics.areEqual(mediaSortKey2.getData().isPlaying(), bool) && mediaSortKey2.getData().getPlaybackLocation() == 0) {
                    z = true;
                }
                return ComparisonsKt.compareValues(boolValueOf, Boolean.valueOf(z));
            }
        };
        final Comparator comparator3 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator2.compare(obj, obj2);
                if (iCompare != 0) {
                    return iCompare;
                }
                MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) obj2;
                Boolean boolIsPlaying = mediaSortKey.getData().isPlaying();
                Boolean bool = Boolean.TRUE;
                boolean z = false;
                Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual(boolIsPlaying, bool) && mediaSortKey.getData().getPlaybackLocation() == 1);
                MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) obj;
                if (Intrinsics.areEqual(mediaSortKey2.getData().isPlaying(), bool) && mediaSortKey2.getData().getPlaybackLocation() == 1) {
                    z = true;
                }
                return ComparisonsKt.compareValues(boolValueOf, Boolean.valueOf(z));
            }
        };
        final Comparator comparator4 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator3.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues(Boolean.valueOf(((MediaPlayerData.MediaSortKey) obj2).getData().getActive()), Boolean.valueOf(((MediaPlayerData.MediaSortKey) obj).getData().getActive()));
            }
        };
        final Comparator comparator5 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator4.compare(obj, obj2);
                if (iCompare != 0) {
                    return iCompare;
                }
                MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
                return ComparisonsKt.compareValues(Boolean.valueOf(mediaPlayerData.getShouldPrioritizeSs$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() == ((MediaPlayerData.MediaSortKey) obj2).isSsMediaRec()), Boolean.valueOf(mediaPlayerData.getShouldPrioritizeSs$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() == ((MediaPlayerData.MediaSortKey) obj).isSsMediaRec()));
            }
        };
        final Comparator comparator6 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator5.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues(Boolean.valueOf(!((MediaPlayerData.MediaSortKey) obj2).getData().getResumption()), Boolean.valueOf(!((MediaPlayerData.MediaSortKey) obj).getData().getResumption()));
            }
        };
        final Comparator comparator7 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator6.compare(obj, obj2);
                if (iCompare != 0) {
                    return iCompare;
                }
                return ComparisonsKt.compareValues(Boolean.valueOf(((MediaPlayerData.MediaSortKey) obj2).getData().getPlaybackLocation() != 2), Boolean.valueOf(((MediaPlayerData.MediaSortKey) obj).getData().getPlaybackLocation() != 2));
            }
        };
        final Comparator comparator8 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$6
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator7.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues(Long.valueOf(((MediaPlayerData.MediaSortKey) obj2).getData().getLastActive()), Long.valueOf(((MediaPlayerData.MediaSortKey) obj).getData().getLastActive()));
            }
        };
        final Comparator comparator9 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$7
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator8.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues(Long.valueOf(((MediaPlayerData.MediaSortKey) obj2).getUpdateTime()), Long.valueOf(((MediaPlayerData.MediaSortKey) obj).getUpdateTime()));
            }
        };
        Comparator comparator10 = new Comparator() { // from class: com.android.systemui.media.controls.ui.controller.MediaPlayerData$special$$inlined$thenByDescending$8
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator9.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues(((MediaPlayerData.MediaSortKey) obj2).getData().getNotificationKey(), ((MediaPlayerData.MediaSortKey) obj).getData().getNotificationKey());
            }
        };
        comparator = comparator10;
        mediaPlayers = new TreeMap(comparator10);
        mediaData = new LinkedHashMap();
        visibleMediaPlayers = new LinkedHashMap();
    }

    private MediaPlayerData() {
    }

    public static /* synthetic */ void moveIfExists$default(MediaPlayerData mediaPlayerData, String str, String str2, MediaCarouselControllerLogger mediaCarouselControllerLogger, int i, Object obj) {
        if ((i & 4) != 0) {
            mediaCarouselControllerLogger = null;
        }
        mediaPlayerData.moveIfExists(str, str2, mediaCarouselControllerLogger);
    }

    public static /* synthetic */ MediaControlPanel removeMediaPlayer$default(MediaPlayerData mediaPlayerData, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return mediaPlayerData.removeMediaPlayer(str, z);
    }

    public final void addMediaPlayer(String str, MediaData mediaData2, MediaControlPanel mediaControlPanel, SystemClock systemClock, boolean z, MediaCarouselControllerLogger mediaCarouselControllerLogger) {
        str.getClass();
        mediaData2.getClass();
        mediaControlPanel.getClass();
        systemClock.getClass();
        MediaControlPanel mediaControlPanelRemoveMediaPlayer$default = removeMediaPlayer$default(this, str, false, 2, null);
        if (mediaControlPanelRemoveMediaPlayer$default != null && !Intrinsics.areEqual(mediaControlPanelRemoveMediaPlayer$default, mediaControlPanel)) {
            if (mediaCarouselControllerLogger != null) {
                mediaCarouselControllerLogger.logPotentialMemoryLeak(str);
            }
            mediaControlPanelRemoveMediaPlayer$default.onDestroy();
        }
        MediaSortKey mediaSortKey = new MediaSortKey(false, mediaData2, str, systemClock.currentTimeMillis(), z);
        mediaData.put(str, mediaSortKey);
        mediaPlayers.put(mediaSortKey, mediaControlPanel);
        visibleMediaPlayers.put(str, mediaSortKey);
    }

    public final void clear() {
        mediaData.clear();
        mediaPlayers.clear();
        visibleMediaPlayers.clear();
    }

    public final int firstActiveMediaIndex() {
        Set setEntrySet = mediaPlayers.entrySet();
        setEntrySet.getClass();
        int i = 0;
        for (Object obj : setEntrySet) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Map.Entry entry = (Map.Entry) obj;
            if (!((MediaSortKey) entry.getKey()).isSsMediaRec() && ((MediaSortKey) entry.getKey()).getData().getActive()) {
                return i;
            }
            i = i2;
        }
        return -1;
    }

    public final MediaControlPanel getMediaControlPanel(int i) {
        return (MediaControlPanel) mediaPlayers.get(CollectionsKt.elementAt(visiblePlayerKeys(), i));
    }

    public final MediaControlPanel getMediaPlayer(String str) {
        str.getClass();
        MediaSortKey mediaSortKey = (MediaSortKey) mediaData.get(str);
        if (mediaSortKey != null) {
            return (MediaControlPanel) mediaPlayers.get(mediaSortKey);
        }
        return null;
    }

    public final int getMediaPlayerIndex(String str) {
        str.getClass();
        MediaSortKey mediaSortKey = (MediaSortKey) mediaData.get(str);
        Set setEntrySet = mediaPlayers.entrySet();
        setEntrySet.getClass();
        int i = 0;
        for (Object obj : setEntrySet) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            if (Intrinsics.areEqual(((Map.Entry) obj).getKey(), mediaSortKey)) {
                return i;
            }
            i = i2;
        }
        return -1;
    }

    public final boolean getShouldPrioritizeSs$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        return shouldPrioritizeSs;
    }

    public final boolean hasActiveMediaOrRecommendationCard() {
        return firstActiveMediaIndex() != -1;
    }

    public final boolean isSsReactivated(String str) {
        str.getClass();
        MediaSortKey mediaSortKey = (MediaSortKey) mediaData.get(str);
        if (mediaSortKey != null) {
            return mediaSortKey.isSsReactivated();
        }
        return false;
    }

    public final List mediaData() {
        Set<Map.Entry> setEntrySet = mediaData.entrySet();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
        for (Map.Entry entry : setEntrySet) {
            arrayList.add(new Triple(entry.getKey(), ((MediaSortKey) entry.getValue()).getData(), Boolean.valueOf(((MediaSortKey) entry.getValue()).isSsMediaRec())));
        }
        return arrayList;
    }

    public final void moveIfExists(String str, String str2, MediaCarouselControllerLogger mediaCarouselControllerLogger) {
        str2.getClass();
        if (str == null || Intrinsics.areEqual(str, str2)) {
            return;
        }
        Map map = mediaData;
        MediaSortKey mediaSortKey = (MediaSortKey) map.remove(str);
        if (mediaSortKey != null) {
            MediaControlPanel mediaControlPanelRemoveMediaPlayer$default = removeMediaPlayer$default(INSTANCE, str2, false, 2, null);
            if (mediaControlPanelRemoveMediaPlayer$default != null) {
                if (mediaCarouselControllerLogger != null) {
                    mediaCarouselControllerLogger.logPotentialMemoryLeak(str2);
                }
                mediaControlPanelRemoveMediaPlayer$default.onDestroy();
            }
        }
    }

    public final Set playerKeys() {
        Set setKeySet = mediaPlayers.keySet();
        setKeySet.getClass();
        return setKeySet;
    }

    public final Collection players() {
        Collection collectionValues = mediaPlayers.values();
        collectionValues.getClass();
        return collectionValues;
    }

    public final MediaControlPanel removeMediaPlayer(String str, boolean z) {
        str.getClass();
        MediaSortKey mediaSortKey = (MediaSortKey) mediaData.remove(str);
        if (mediaSortKey == null) {
            return null;
        }
        if (z) {
            visibleMediaPlayers.remove(str);
        }
        return (MediaControlPanel) mediaPlayers.remove(mediaSortKey);
    }

    public final void updateVisibleMediaPlayers() {
        visibleMediaPlayers.clear();
        for (MediaSortKey mediaSortKey : playerKeys()) {
            visibleMediaPlayers.put(mediaSortKey.getKey(), mediaSortKey);
        }
    }

    public final Collection visiblePlayerKeys() {
        Collection collectionValues = visibleMediaPlayers.values();
        collectionValues.getClass();
        return collectionValues;
    }
}
