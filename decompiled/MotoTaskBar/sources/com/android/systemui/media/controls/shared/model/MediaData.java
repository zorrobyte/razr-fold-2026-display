package com.android.systemui.media.controls.shared.model;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import com.android.internal.logging.InstanceId;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaData.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaData {
    public static final Companion Companion = new Companion(null);
    private final List actions;
    private final List actionsToShowInCompact;
    private boolean active;
    private final String app;
    private final Icon appIcon;
    private final int appUid;
    private final CharSequence artist;
    private final Icon artwork;
    private final PendingIntent clickIntent;
    private long createdTimestampMillis;
    private final MediaDeviceData device;
    private boolean hasCheckedForResume;
    private final boolean initialized;
    private final InstanceId instanceId;
    private final boolean isClearable;
    private final boolean isExplicit;
    private final Boolean isPlaying;
    private long lastActive;
    private final String notificationKey;
    private final String packageName;
    private int playbackLocation;
    private Runnable resumeAction;
    private final Double resumeProgress;
    private boolean resumption;
    private final MediaButton semanticActions;
    private final CharSequence song;
    private final MediaSession.Token token;
    private final int userId;

    /* JADX INFO: compiled from: MediaData.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaData(int i, boolean z, String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List list, List list2, MediaButton mediaButton, String str2, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z2, Runnable runnable, int i2, boolean z3, String str3, boolean z4, Boolean bool, boolean z5, long j, long j2, InstanceId instanceId, int i3, boolean z6, Double d) {
        list.getClass();
        list2.getClass();
        str2.getClass();
        instanceId.getClass();
        this.userId = i;
        this.initialized = z;
        this.app = str;
        this.appIcon = icon;
        this.artist = charSequence;
        this.song = charSequence2;
        this.artwork = icon2;
        this.actions = list;
        this.actionsToShowInCompact = list2;
        this.semanticActions = mediaButton;
        this.packageName = str2;
        this.token = token;
        this.clickIntent = pendingIntent;
        this.device = mediaDeviceData;
        this.active = z2;
        this.resumeAction = runnable;
        this.playbackLocation = i2;
        this.resumption = z3;
        this.notificationKey = str3;
        this.hasCheckedForResume = z4;
        this.isPlaying = bool;
        this.isClearable = z5;
        this.lastActive = j;
        this.createdTimestampMillis = j2;
        this.instanceId = instanceId;
        this.appUid = i3;
        this.isExplicit = z6;
        this.resumeProgress = d;
    }

    public /* synthetic */ MediaData(int i, boolean z, String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List list, List list2, MediaButton mediaButton, String str2, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z2, Runnable runnable, int i2, boolean z3, String str3, boolean z4, Boolean bool, boolean z5, long j, long j2, InstanceId instanceId, int i3, boolean z6, Double d, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? -1 : i, (i4 & 2) != 0 ? false : z, (i4 & 4) != 0 ? null : str, (i4 & 8) != 0 ? null : icon, (i4 & 16) != 0 ? null : charSequence, (i4 & 32) != 0 ? null : charSequence2, (i4 & 64) != 0 ? null : icon2, (i4 & 128) != 0 ? CollectionsKt.emptyList() : list, (i4 & 256) != 0 ? CollectionsKt.emptyList() : list2, (i4 & 512) != 0 ? null : mediaButton, (i4 & 1024) != 0 ? "INVALID" : str2, (i4 & 2048) != 0 ? null : token, (i4 & 4096) != 0 ? null : pendingIntent, (i4 & 8192) != 0 ? null : mediaDeviceData, (i4 & 16384) != 0 ? true : z2, (i4 & 32768) != 0 ? null : runnable, (i4 & 65536) != 0 ? 0 : i2, (i4 & 131072) != 0 ? false : z3, (i4 & 262144) != 0 ? null : str3, (i4 & 524288) != 0 ? false : z4, (i4 & 1048576) != 0 ? null : bool, (i4 & 2097152) == 0 ? z5 : true, (i4 & 4194304) != 0 ? 0L : j, (i4 & 8388608) == 0 ? j2 : 0L, (i4 & 16777216) != 0 ? InstanceId.fakeInstanceId(-1) : instanceId, (i4 & 33554432) != 0 ? -1 : i3, (i4 & 67108864) != 0 ? false : z6, (i4 & 134217728) != 0 ? null : d);
    }

    public static /* synthetic */ MediaData copy$default(MediaData mediaData, int i, boolean z, String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List list, List list2, MediaButton mediaButton, String str2, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z2, Runnable runnable, int i2, boolean z3, String str3, boolean z4, Boolean bool, boolean z5, long j, long j2, InstanceId instanceId, int i3, boolean z6, Double d, int i4, Object obj) {
        Double d2;
        boolean z7;
        int i5 = (i4 & 1) != 0 ? mediaData.userId : i;
        boolean z8 = (i4 & 2) != 0 ? mediaData.initialized : z;
        String str4 = (i4 & 4) != 0 ? mediaData.app : str;
        Icon icon3 = (i4 & 8) != 0 ? mediaData.appIcon : icon;
        CharSequence charSequence3 = (i4 & 16) != 0 ? mediaData.artist : charSequence;
        CharSequence charSequence4 = (i4 & 32) != 0 ? mediaData.song : charSequence2;
        Icon icon4 = (i4 & 64) != 0 ? mediaData.artwork : icon2;
        List list3 = (i4 & 128) != 0 ? mediaData.actions : list;
        List list4 = (i4 & 256) != 0 ? mediaData.actionsToShowInCompact : list2;
        MediaButton mediaButton2 = (i4 & 512) != 0 ? mediaData.semanticActions : mediaButton;
        String str5 = (i4 & 1024) != 0 ? mediaData.packageName : str2;
        MediaSession.Token token2 = (i4 & 2048) != 0 ? mediaData.token : token;
        PendingIntent pendingIntent2 = (i4 & 4096) != 0 ? mediaData.clickIntent : pendingIntent;
        MediaDeviceData mediaDeviceData2 = (i4 & 8192) != 0 ? mediaData.device : mediaDeviceData;
        int i6 = i5;
        boolean z9 = (i4 & 16384) != 0 ? mediaData.active : z2;
        Runnable runnable2 = (i4 & 32768) != 0 ? mediaData.resumeAction : runnable;
        int i7 = (i4 & 65536) != 0 ? mediaData.playbackLocation : i2;
        boolean z10 = (i4 & 131072) != 0 ? mediaData.resumption : z3;
        String str6 = (i4 & 262144) != 0 ? mediaData.notificationKey : str3;
        boolean z11 = (i4 & 524288) != 0 ? mediaData.hasCheckedForResume : z4;
        Boolean bool2 = (i4 & 1048576) != 0 ? mediaData.isPlaying : bool;
        boolean z12 = (i4 & 2097152) != 0 ? mediaData.isClearable : z5;
        boolean z13 = z9;
        long j3 = (i4 & 4194304) != 0 ? mediaData.lastActive : j;
        long j4 = (i4 & 8388608) != 0 ? mediaData.createdTimestampMillis : j2;
        InstanceId instanceId2 = (i4 & 16777216) != 0 ? mediaData.instanceId : instanceId;
        int i8 = (i4 & 33554432) != 0 ? mediaData.appUid : i3;
        InstanceId instanceId3 = instanceId2;
        boolean z14 = (i4 & 67108864) != 0 ? mediaData.isExplicit : z6;
        if ((i4 & 134217728) != 0) {
            z7 = z14;
            d2 = mediaData.resumeProgress;
        } else {
            d2 = d;
            z7 = z14;
        }
        return mediaData.copy(i6, z8, str4, icon3, charSequence3, charSequence4, icon4, list3, list4, mediaButton2, str5, token2, pendingIntent2, mediaDeviceData2, z13, runnable2, i7, z10, str6, z11, bool2, z12, j3, j4, instanceId3, i8, z7, d2);
    }

    public final MediaData copy(int i, boolean z, String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List list, List list2, MediaButton mediaButton, String str2, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z2, Runnable runnable, int i2, boolean z3, String str3, boolean z4, Boolean bool, boolean z5, long j, long j2, InstanceId instanceId, int i3, boolean z6, Double d) {
        list.getClass();
        list2.getClass();
        str2.getClass();
        instanceId.getClass();
        return new MediaData(i, z, str, icon, charSequence, charSequence2, icon2, list, list2, mediaButton, str2, token, pendingIntent, mediaDeviceData, z2, runnable, i2, z3, str3, z4, bool, z5, j, j2, instanceId, i3, z6, d);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaData)) {
            return false;
        }
        MediaData mediaData = (MediaData) obj;
        return this.userId == mediaData.userId && this.initialized == mediaData.initialized && Intrinsics.areEqual(this.app, mediaData.app) && Intrinsics.areEqual(this.appIcon, mediaData.appIcon) && Intrinsics.areEqual(this.artist, mediaData.artist) && Intrinsics.areEqual(this.song, mediaData.song) && Intrinsics.areEqual(this.artwork, mediaData.artwork) && Intrinsics.areEqual(this.actions, mediaData.actions) && Intrinsics.areEqual(this.actionsToShowInCompact, mediaData.actionsToShowInCompact) && Intrinsics.areEqual(this.semanticActions, mediaData.semanticActions) && Intrinsics.areEqual(this.packageName, mediaData.packageName) && Intrinsics.areEqual(this.token, mediaData.token) && Intrinsics.areEqual(this.clickIntent, mediaData.clickIntent) && Intrinsics.areEqual(this.device, mediaData.device) && this.active == mediaData.active && Intrinsics.areEqual(this.resumeAction, mediaData.resumeAction) && this.playbackLocation == mediaData.playbackLocation && this.resumption == mediaData.resumption && Intrinsics.areEqual(this.notificationKey, mediaData.notificationKey) && this.hasCheckedForResume == mediaData.hasCheckedForResume && Intrinsics.areEqual(this.isPlaying, mediaData.isPlaying) && this.isClearable == mediaData.isClearable && this.lastActive == mediaData.lastActive && this.createdTimestampMillis == mediaData.createdTimestampMillis && Intrinsics.areEqual(this.instanceId, mediaData.instanceId) && this.appUid == mediaData.appUid && this.isExplicit == mediaData.isExplicit && Intrinsics.areEqual(this.resumeProgress, mediaData.resumeProgress);
    }

    public final List getActions() {
        return this.actions;
    }

    public final List getActionsToShowInCompact() {
        return this.actionsToShowInCompact;
    }

    public final boolean getActive() {
        return this.active;
    }

    public final String getApp() {
        return this.app;
    }

    public final Icon getAppIcon() {
        return this.appIcon;
    }

    public final int getAppUid() {
        return this.appUid;
    }

    public final CharSequence getArtist() {
        return this.artist;
    }

    public final Icon getArtwork() {
        return this.artwork;
    }

    public final PendingIntent getClickIntent() {
        return this.clickIntent;
    }

    public final long getCreatedTimestampMillis() {
        return this.createdTimestampMillis;
    }

    public final MediaDeviceData getDevice() {
        return this.device;
    }

    public final boolean getHasCheckedForResume() {
        return this.hasCheckedForResume;
    }

    public final InstanceId getInstanceId() {
        return this.instanceId;
    }

    public final long getLastActive() {
        return this.lastActive;
    }

    public final String getNotificationKey() {
        return this.notificationKey;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final int getPlaybackLocation() {
        return this.playbackLocation;
    }

    public final Runnable getResumeAction() {
        return this.resumeAction;
    }

    public final Double getResumeProgress() {
        return this.resumeProgress;
    }

    public final boolean getResumption() {
        return this.resumption;
    }

    public final MediaButton getSemanticActions() {
        return this.semanticActions;
    }

    public final CharSequence getSong() {
        return this.song;
    }

    public final MediaSession.Token getToken() {
        return this.token;
    }

    public final int getUserId() {
        return this.userId;
    }

    public int hashCode() {
        int iHashCode = ((Integer.hashCode(this.userId) * 31) + Boolean.hashCode(this.initialized)) * 31;
        String str = this.app;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        Icon icon = this.appIcon;
        int iHashCode3 = (iHashCode2 + (icon == null ? 0 : icon.hashCode())) * 31;
        CharSequence charSequence = this.artist;
        int iHashCode4 = (iHashCode3 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        CharSequence charSequence2 = this.song;
        int iHashCode5 = (iHashCode4 + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
        Icon icon2 = this.artwork;
        int iHashCode6 = (((((iHashCode5 + (icon2 == null ? 0 : icon2.hashCode())) * 31) + this.actions.hashCode()) * 31) + this.actionsToShowInCompact.hashCode()) * 31;
        MediaButton mediaButton = this.semanticActions;
        int iHashCode7 = (((iHashCode6 + (mediaButton == null ? 0 : mediaButton.hashCode())) * 31) + this.packageName.hashCode()) * 31;
        MediaSession.Token token = this.token;
        int iHashCode8 = (iHashCode7 + (token == null ? 0 : token.hashCode())) * 31;
        PendingIntent pendingIntent = this.clickIntent;
        int iHashCode9 = (iHashCode8 + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
        MediaDeviceData mediaDeviceData = this.device;
        int iHashCode10 = (((iHashCode9 + (mediaDeviceData == null ? 0 : mediaDeviceData.hashCode())) * 31) + Boolean.hashCode(this.active)) * 31;
        Runnable runnable = this.resumeAction;
        int iHashCode11 = (((((iHashCode10 + (runnable == null ? 0 : runnable.hashCode())) * 31) + Integer.hashCode(this.playbackLocation)) * 31) + Boolean.hashCode(this.resumption)) * 31;
        String str2 = this.notificationKey;
        int iHashCode12 = (((iHashCode11 + (str2 == null ? 0 : str2.hashCode())) * 31) + Boolean.hashCode(this.hasCheckedForResume)) * 31;
        Boolean bool = this.isPlaying;
        int iHashCode13 = (((((((((((((iHashCode12 + (bool == null ? 0 : bool.hashCode())) * 31) + Boolean.hashCode(this.isClearable)) * 31) + Long.hashCode(this.lastActive)) * 31) + Long.hashCode(this.createdTimestampMillis)) * 31) + this.instanceId.hashCode()) * 31) + Integer.hashCode(this.appUid)) * 31) + Boolean.hashCode(this.isExplicit)) * 31;
        Double d = this.resumeProgress;
        return iHashCode13 + (d != null ? d.hashCode() : 0);
    }

    public final boolean isClearable() {
        return this.isClearable;
    }

    public final boolean isExplicit() {
        return this.isExplicit;
    }

    public final boolean isLocalSession() {
        return this.playbackLocation == 0;
    }

    public final Boolean isPlaying() {
        return this.isPlaying;
    }

    public final void setActive(boolean z) {
        this.active = z;
    }

    public final void setHasCheckedForResume(boolean z) {
        this.hasCheckedForResume = z;
    }

    public final void setLastActive(long j) {
        this.lastActive = j;
    }

    public final void setResumeAction(Runnable runnable) {
        this.resumeAction = runnable;
    }

    public String toString() {
        int i = this.userId;
        boolean z = this.initialized;
        String str = this.app;
        Icon icon = this.appIcon;
        CharSequence charSequence = this.artist;
        CharSequence charSequence2 = this.song;
        return "MediaData(userId=" + i + ", initialized=" + z + ", app=" + str + ", appIcon=" + icon + ", artist=" + ((Object) charSequence) + ", song=" + ((Object) charSequence2) + ", artwork=" + this.artwork + ", actions=" + this.actions + ", actionsToShowInCompact=" + this.actionsToShowInCompact + ", semanticActions=" + this.semanticActions + ", packageName=" + this.packageName + ", token=" + this.token + ", clickIntent=" + this.clickIntent + ", device=" + this.device + ", active=" + this.active + ", resumeAction=" + this.resumeAction + ", playbackLocation=" + this.playbackLocation + ", resumption=" + this.resumption + ", notificationKey=" + this.notificationKey + ", hasCheckedForResume=" + this.hasCheckedForResume + ", isPlaying=" + this.isPlaying + ", isClearable=" + this.isClearable + ", lastActive=" + this.lastActive + ", createdTimestampMillis=" + this.createdTimestampMillis + ", instanceId=" + this.instanceId + ", appUid=" + this.appUid + ", isExplicit=" + this.isExplicit + ", resumeProgress=" + this.resumeProgress + ")";
    }
}
