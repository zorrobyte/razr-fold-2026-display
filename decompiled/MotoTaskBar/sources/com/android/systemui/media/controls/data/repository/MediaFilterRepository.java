package com.android.systemui.media.controls.data.repository;

import android.content.Context;
import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.data.model.MediaSortKeyModel;
import com.android.systemui.media.controls.shared.model.MediaCommonModel;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDataLoadingModel;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: MediaFilterRepository.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaFilterRepository {
    private final MutableStateFlow _allUserEntries;
    private final MutableStateFlow _isMediaFromRec;
    private final MutableStateFlow _reactivatedId;
    private final MutableStateFlow _selectedUserEntries;
    private final MutableStateFlow _sortedMedia;
    private final StateFlow allUserEntries;
    private final Comparator comparator;
    private final ConfigurationController configurationController;
    private final StateFlow isMediaFromRec;
    private Locale locale;
    private String mediaFromRecPackageName;
    private final Flow onAnyMediaConfigurationChange;
    private final StateFlow reactivatedId;
    private final StateFlow selectedUserEntries;
    private final StateFlow sortedMedia;
    private final SystemClock systemClock;

    public MediaFilterRepository(Context context, SystemClock systemClock, ConfigurationController configurationController) {
        context.getClass();
        systemClock.getClass();
        configurationController.getClass();
        this.systemClock = systemClock;
        this.configurationController = configurationController;
        this.onAnyMediaConfigurationChange = FlowConflatedKt.conflatedCallbackFlow(new MediaFilterRepository$onAnyMediaConfigurationChange$1(this, context, null));
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._reactivatedId = MutableStateFlow;
        this.reactivatedId = FlowKt.asStateFlow(MutableStateFlow);
        MutableStateFlow MutableStateFlow2 = StateFlowKt.MutableStateFlow(new LinkedHashMap());
        this._selectedUserEntries = MutableStateFlow2;
        this.selectedUserEntries = FlowKt.asStateFlow(MutableStateFlow2);
        MutableStateFlow MutableStateFlow3 = StateFlowKt.MutableStateFlow(new LinkedHashMap());
        this._allUserEntries = MutableStateFlow3;
        this.allUserEntries = FlowKt.asStateFlow(MutableStateFlow3);
        final Comparator comparator = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$compareByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                MediaSortKeyModel mediaSortKeyModel = (MediaSortKeyModel) obj2;
                Boolean boolIsPlaying = mediaSortKeyModel.isPlaying();
                Boolean bool = Boolean.TRUE;
                boolean z = false;
                Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual(boolIsPlaying, bool) && mediaSortKeyModel.getPlaybackLocation() == 0);
                MediaSortKeyModel mediaSortKeyModel2 = (MediaSortKeyModel) obj;
                if (Intrinsics.areEqual(mediaSortKeyModel2.isPlaying(), bool) && mediaSortKeyModel2.getPlaybackLocation() == 0) {
                    z = true;
                }
                return ComparisonsKt.compareValues(boolValueOf, Boolean.valueOf(z));
            }
        };
        final Comparator comparator2 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator.compare(obj, obj2);
                if (iCompare != 0) {
                    return iCompare;
                }
                MediaSortKeyModel mediaSortKeyModel = (MediaSortKeyModel) obj2;
                Boolean boolIsPlaying = mediaSortKeyModel.isPlaying();
                Boolean bool = Boolean.TRUE;
                boolean z = false;
                Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual(boolIsPlaying, bool) && mediaSortKeyModel.getPlaybackLocation() == 1);
                MediaSortKeyModel mediaSortKeyModel2 = (MediaSortKeyModel) obj;
                if (Intrinsics.areEqual(mediaSortKeyModel2.isPlaying(), bool) && mediaSortKeyModel2.getPlaybackLocation() == 1) {
                    z = true;
                }
                return ComparisonsKt.compareValues(boolValueOf, Boolean.valueOf(z));
            }
        };
        final Comparator comparator3 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator2.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues(Boolean.valueOf(((MediaSortKeyModel) obj2).getActive()), Boolean.valueOf(((MediaSortKeyModel) obj).getActive()));
            }
        };
        final Comparator comparator4 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator3.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues(Boolean.valueOf(((MediaSortKeyModel) obj2).isPrioritizedRec()), Boolean.valueOf(((MediaSortKeyModel) obj).isPrioritizedRec()));
            }
        };
        final Comparator comparator5 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator4.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues(Boolean.valueOf(!((MediaSortKeyModel) obj2).isResume()), Boolean.valueOf(!((MediaSortKeyModel) obj).isResume()));
            }
        };
        final Comparator comparator6 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator5.compare(obj, obj2);
                if (iCompare != 0) {
                    return iCompare;
                }
                return ComparisonsKt.compareValues(Boolean.valueOf(((MediaSortKeyModel) obj2).getPlaybackLocation() != 2), Boolean.valueOf(((MediaSortKeyModel) obj).getPlaybackLocation() != 2));
            }
        };
        final Comparator comparator7 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$6
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator6.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues(Long.valueOf(((MediaSortKeyModel) obj2).getLastActive()), Long.valueOf(((MediaSortKeyModel) obj).getLastActive()));
            }
        };
        final Comparator comparator8 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$7
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator7.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues(Long.valueOf(((MediaSortKeyModel) obj2).getUpdateTime()), Long.valueOf(((MediaSortKeyModel) obj).getUpdateTime()));
            }
        };
        Comparator comparator9 = new Comparator() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$special$$inlined$thenByDescending$8
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare = comparator8.compare(obj, obj2);
                return iCompare != 0 ? iCompare : ComparisonsKt.compareValues(((MediaSortKeyModel) obj2).getNotificationKey(), ((MediaSortKeyModel) obj).getNotificationKey());
            }
        };
        this.comparator = comparator9;
        MutableStateFlow MutableStateFlow4 = StateFlowKt.MutableStateFlow(new TreeMap(comparator9));
        this._sortedMedia = MutableStateFlow4;
        this.sortedMedia = FlowKt.asStateFlow(MutableStateFlow4);
        MutableStateFlow MutableStateFlow5 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isMediaFromRec = MutableStateFlow5;
        this.isMediaFromRec = FlowKt.asStateFlow(MutableStateFlow5);
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        locale.getClass();
        this.locale = locale;
    }

    private final boolean canBeRemoved(MediaData mediaData) {
        Boolean boolIsPlaying = mediaData.isPlaying();
        return (boolIsPlaying != null ? boolIsPlaying.booleanValue() ^ true : mediaData.isClearable()) && !mediaData.getActive();
    }

    private final boolean isMediaFromRec(MediaData mediaData) {
        return Intrinsics.areEqual(mediaData.isPlaying(), Boolean.TRUE) && Intrinsics.areEqual(this.mediaFromRecPackageName, mediaData.getPackageName());
    }

    public final void addMediaDataLoadingState(MediaDataLoadingModel mediaDataLoadingModel) {
        mediaDataLoadingModel.getClass();
        TreeMap treeMap = new TreeMap(this.comparator);
        Map map = (Map) this._sortedMedia.getValue();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            Object value = entry.getValue();
            value.getClass();
            MediaCommonModel mediaCommonModel = (MediaCommonModel) value;
            if (!(mediaCommonModel instanceof MediaCommonModel.MediaControl) || !Intrinsics.areEqual(((MediaCommonModel.MediaControl) mediaCommonModel).getMediaLoadedModel().getInstanceId(), mediaDataLoadingModel.getInstanceId())) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        treeMap.putAll(linkedHashMap);
        MediaData mediaData = (MediaData) ((Map) this._selectedUserEntries.getValue()).get(mediaDataLoadingModel.getInstanceId());
        if (mediaData != null) {
            MediaSortKeyModel mediaSortKeyModel = new MediaSortKeyModel(false, mediaData.isPlaying(), mediaData.getPlaybackLocation(), mediaData.getActive(), mediaData.getResumption(), mediaData.getLastActive(), mediaData.getNotificationKey(), this.systemClock.currentTimeMillis(), mediaData.getInstanceId());
            if (mediaDataLoadingModel instanceof MediaDataLoadingModel.Loaded) {
                boolean zIsMediaFromRec = isMediaFromRec(mediaData);
                this._isMediaFromRec.setValue(Boolean.valueOf(zIsMediaFromRec));
                if (zIsMediaFromRec) {
                    this.mediaFromRecPackageName = null;
                }
                treeMap.put(mediaSortKeyModel, new MediaCommonModel.MediaControl((MediaDataLoadingModel.Loaded) mediaDataLoadingModel, canBeRemoved(mediaData)));
            }
        }
        this._sortedMedia.setValue(treeMap);
    }

    public final void addMediaEntry(String str, MediaData mediaData) {
        str.getClass();
        mediaData.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap((Map) this._allUserEntries.getValue());
        linkedHashMap.put(str, mediaData);
        this._allUserEntries.setValue(linkedHashMap);
    }

    public final void addSelectedUserMediaEntry(MediaData mediaData) {
        mediaData.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap((Map) this._selectedUserEntries.getValue());
        linkedHashMap.put(mediaData.getInstanceId(), mediaData);
        this._selectedUserEntries.setValue(linkedHashMap);
    }

    public final void clearSelectedUserMedia() {
        this._selectedUserEntries.setValue(new LinkedHashMap());
    }

    public final StateFlow getAllUserEntries() {
        return this.allUserEntries;
    }

    public final Flow getOnAnyMediaConfigurationChange() {
        return this.onAnyMediaConfigurationChange;
    }

    public final StateFlow getSelectedUserEntries() {
        return this.selectedUserEntries;
    }

    public final StateFlow getSortedMedia() {
        return this.sortedMedia;
    }

    public final StateFlow isMediaFromRec() {
        return this.isMediaFromRec;
    }

    public final MediaData removeMediaEntry(String str) {
        str.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap((Map) this._allUserEntries.getValue());
        MediaData mediaData = (MediaData) linkedHashMap.remove(str);
        this._allUserEntries.setValue(linkedHashMap);
        return mediaData;
    }

    public final MediaData removeSelectedUserMediaEntry(InstanceId instanceId) {
        instanceId.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap((Map) this._selectedUserEntries.getValue());
        MediaData mediaData = (MediaData) linkedHashMap.remove(instanceId);
        this._selectedUserEntries.setValue(linkedHashMap);
        return mediaData;
    }

    public final boolean removeSelectedUserMediaEntry(InstanceId instanceId, MediaData mediaData) {
        instanceId.getClass();
        mediaData.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap((Map) this._selectedUserEntries.getValue());
        if (!linkedHashMap.remove(instanceId, mediaData)) {
            return false;
        }
        this._selectedUserEntries.setValue(linkedHashMap);
        return true;
    }
}
