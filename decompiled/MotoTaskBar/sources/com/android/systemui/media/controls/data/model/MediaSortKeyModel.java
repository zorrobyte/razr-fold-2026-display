package com.android.systemui.media.controls.data.model;

import com.android.internal.logging.InstanceId;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaSortKeyModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaSortKeyModel {
    private final boolean active;
    private final InstanceId instanceId;
    private final Boolean isPlaying;
    private final boolean isPrioritizedRec;
    private final boolean isResume;
    private final long lastActive;
    private final String notificationKey;
    private final int playbackLocation;
    private final long updateTime;

    public MediaSortKeyModel(boolean z, Boolean bool, int i, boolean z2, boolean z3, long j, String str, long j2, InstanceId instanceId) {
        this.isPrioritizedRec = z;
        this.isPlaying = bool;
        this.playbackLocation = i;
        this.active = z2;
        this.isResume = z3;
        this.lastActive = j;
        this.notificationKey = str;
        this.updateTime = j2;
        this.instanceId = instanceId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaSortKeyModel)) {
            return false;
        }
        MediaSortKeyModel mediaSortKeyModel = (MediaSortKeyModel) obj;
        return this.isPrioritizedRec == mediaSortKeyModel.isPrioritizedRec && Intrinsics.areEqual(this.isPlaying, mediaSortKeyModel.isPlaying) && this.playbackLocation == mediaSortKeyModel.playbackLocation && this.active == mediaSortKeyModel.active && this.isResume == mediaSortKeyModel.isResume && this.lastActive == mediaSortKeyModel.lastActive && Intrinsics.areEqual(this.notificationKey, mediaSortKeyModel.notificationKey) && this.updateTime == mediaSortKeyModel.updateTime && Intrinsics.areEqual(this.instanceId, mediaSortKeyModel.instanceId);
    }

    public final boolean getActive() {
        return this.active;
    }

    public final long getLastActive() {
        return this.lastActive;
    }

    public final String getNotificationKey() {
        return this.notificationKey;
    }

    public final int getPlaybackLocation() {
        return this.playbackLocation;
    }

    public final long getUpdateTime() {
        return this.updateTime;
    }

    public int hashCode() {
        int iHashCode = Boolean.hashCode(this.isPrioritizedRec) * 31;
        Boolean bool = this.isPlaying;
        int iHashCode2 = (((((((((iHashCode + (bool == null ? 0 : bool.hashCode())) * 31) + Integer.hashCode(this.playbackLocation)) * 31) + Boolean.hashCode(this.active)) * 31) + Boolean.hashCode(this.isResume)) * 31) + Long.hashCode(this.lastActive)) * 31;
        String str = this.notificationKey;
        int iHashCode3 = (((iHashCode2 + (str == null ? 0 : str.hashCode())) * 31) + Long.hashCode(this.updateTime)) * 31;
        InstanceId instanceId = this.instanceId;
        return iHashCode3 + (instanceId != null ? instanceId.hashCode() : 0);
    }

    public final Boolean isPlaying() {
        return this.isPlaying;
    }

    public final boolean isPrioritizedRec() {
        return this.isPrioritizedRec;
    }

    public final boolean isResume() {
        return this.isResume;
    }

    public String toString() {
        return "MediaSortKeyModel(isPrioritizedRec=" + this.isPrioritizedRec + ", isPlaying=" + this.isPlaying + ", playbackLocation=" + this.playbackLocation + ", active=" + this.active + ", isResume=" + this.isResume + ", lastActive=" + this.lastActive + ", notificationKey=" + this.notificationKey + ", updateTime=" + this.updateTime + ", instanceId=" + this.instanceId + ")";
    }
}
