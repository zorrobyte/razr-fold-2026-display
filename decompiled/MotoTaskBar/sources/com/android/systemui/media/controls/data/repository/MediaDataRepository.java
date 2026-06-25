package com.android.systemui.media.controls.data.repository;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.util.MediaFlags;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: MediaDataRepository.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaDataRepository implements Dumpable {
    private final MutableStateFlow _mediaEntries;
    private final StateFlow mediaEntries;
    private final MediaFlags mediaFlags;

    public MediaDataRepository(MediaFlags mediaFlags, DumpManager dumpManager) {
        mediaFlags.getClass();
        dumpManager.getClass();
        this.mediaFlags = mediaFlags;
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(new LinkedHashMap());
        this._mediaEntries = MutableStateFlow;
        this.mediaEntries = FlowKt.asStateFlow(MutableStateFlow);
        dumpManager.registerNormalDumpable("MediaDataRepository", this);
    }

    public final MediaData addMediaEntry(String str, MediaData mediaData) {
        str.getClass();
        mediaData.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap((Map) this._mediaEntries.getValue());
        MediaData mediaData2 = (MediaData) linkedHashMap.put(str, mediaData);
        this._mediaEntries.setValue(linkedHashMap);
        return mediaData2;
    }

    public final StateFlow getMediaEntries() {
        return this.mediaEntries;
    }

    public final MediaData removeMediaEntry(String str) {
        str.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap((Map) this._mediaEntries.getValue());
        MediaData mediaData = (MediaData) linkedHashMap.remove(str);
        this._mediaEntries.setValue(linkedHashMap);
        return mediaData;
    }
}
