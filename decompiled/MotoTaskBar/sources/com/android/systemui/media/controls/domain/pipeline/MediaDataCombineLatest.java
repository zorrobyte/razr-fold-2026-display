package com.android.systemui.media.controls.domain.pipeline;

import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaDataCombineLatest.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaDataCombineLatest implements MediaDataManager.Listener, MediaDeviceManager.Listener {
    private final Set listeners = new LinkedHashSet();
    private final Map entries = new LinkedHashMap();

    private final void remove(String str) {
        if (((Pair) this.entries.remove(str)) != null) {
            Iterator it = CollectionsKt.toSet(this.listeners).iterator();
            while (it.hasNext()) {
                ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str);
            }
        }
    }

    private final void update(String str, String str2) {
        String str3 = str;
        Pair pair = (Pair) this.entries.get(str3);
        if (pair == null) {
            pair = TuplesKt.to(null, null);
        }
        MediaData mediaData = (MediaData) pair.component1();
        MediaDeviceData mediaDeviceData = (MediaDeviceData) pair.component2();
        if (mediaData == null || mediaDeviceData == null) {
            return;
        }
        MediaData mediaDataCopy$default = MediaData.copy$default(mediaData, 0, false, null, null, null, null, null, null, null, null, null, null, null, mediaDeviceData, false, null, 0, false, null, false, null, false, 0L, 0L, null, 0, false, null, 268427263, null);
        Iterator it = CollectionsKt.toSet(this.listeners).iterator();
        while (it.hasNext()) {
            MediaDataProcessor.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str3, str2, mediaDataCopy$default, false, false, 24, null);
            str3 = str;
        }
    }

    public final boolean addListener(MediaDataManager.Listener listener) {
        listener.getClass();
        return this.listeners.add(listener);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager.Listener
    public void onKeyRemoved(String str) {
        str.getClass();
        remove(str);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, boolean z2) {
        str.getClass();
        mediaData.getClass();
        if (str2 == null || Intrinsics.areEqual(str2, str) || !this.entries.containsKey(str2)) {
            Map map = this.entries;
            Pair pair = (Pair) map.get(str);
            map.put(str, TuplesKt.to(mediaData, pair != null ? (MediaDeviceData) pair.getSecond() : null));
            update(str, str);
            return;
        }
        Map map2 = this.entries;
        Pair pair2 = (Pair) map2.remove(str2);
        map2.put(str, TuplesKt.to(mediaData, pair2 != null ? (MediaDeviceData) pair2.getSecond() : null));
        update(str, str2);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener, com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataRemoved(String str) {
        str.getClass();
        remove(str);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager.Listener
    public void onMediaDeviceChanged(String str, String str2, MediaDeviceData mediaDeviceData) {
        str.getClass();
        if (str2 == null || Intrinsics.areEqual(str2, str) || !this.entries.containsKey(str2)) {
            Map map = this.entries;
            Pair pair = (Pair) map.get(str);
            map.put(str, TuplesKt.to(pair != null ? (MediaData) pair.getFirst() : null, mediaDeviceData));
            update(str, str);
            return;
        }
        Map map2 = this.entries;
        Pair pair2 = (Pair) map2.remove(str2);
        map2.put(str, TuplesKt.to(pair2 != null ? (MediaData) pair2.getFirst() : null, mediaDeviceData));
        update(str, str2);
    }
}
