package com.android.systemui.media.controls.shared.model;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import com.android.internal.logging.InstanceId;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaControlModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaControlModel {
    private final List actionsToShowInCollapsed;
    private final Icon appIcon;
    private final String appName;
    private final CharSequence artistName;
    private final Icon artwork;
    private final PendingIntent clickIntent;
    private final MediaDeviceData deviceData;
    private final InstanceId instanceId;
    private final boolean isDismissible;
    private final boolean isResume;
    private final List notificationActionButtons;
    private final String packageName;
    private final Double resumeProgress;
    private final MediaButton semanticActionButtons;
    private final boolean showExplicit;
    private final CharSequence songName;
    private final MediaSession.Token token;
    private final int uid;

    public MediaControlModel(int i, String str, InstanceId instanceId, MediaSession.Token token, Icon icon, PendingIntent pendingIntent, String str2, CharSequence charSequence, CharSequence charSequence2, boolean z, Icon icon2, MediaDeviceData mediaDeviceData, MediaButton mediaButton, List list, List list2, boolean z2, boolean z3, Double d) {
        str.getClass();
        instanceId.getClass();
        list.getClass();
        list2.getClass();
        this.uid = i;
        this.packageName = str;
        this.instanceId = instanceId;
        this.token = token;
        this.appIcon = icon;
        this.clickIntent = pendingIntent;
        this.appName = str2;
        this.songName = charSequence;
        this.artistName = charSequence2;
        this.showExplicit = z;
        this.artwork = icon2;
        this.deviceData = mediaDeviceData;
        this.semanticActionButtons = mediaButton;
        this.notificationActionButtons = list;
        this.actionsToShowInCollapsed = list2;
        this.isDismissible = z2;
        this.isResume = z3;
        this.resumeProgress = d;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaControlModel)) {
            return false;
        }
        MediaControlModel mediaControlModel = (MediaControlModel) obj;
        return this.uid == mediaControlModel.uid && Intrinsics.areEqual(this.packageName, mediaControlModel.packageName) && Intrinsics.areEqual(this.instanceId, mediaControlModel.instanceId) && Intrinsics.areEqual(this.token, mediaControlModel.token) && Intrinsics.areEqual(this.appIcon, mediaControlModel.appIcon) && Intrinsics.areEqual(this.clickIntent, mediaControlModel.clickIntent) && Intrinsics.areEqual(this.appName, mediaControlModel.appName) && Intrinsics.areEqual(this.songName, mediaControlModel.songName) && Intrinsics.areEqual(this.artistName, mediaControlModel.artistName) && this.showExplicit == mediaControlModel.showExplicit && Intrinsics.areEqual(this.artwork, mediaControlModel.artwork) && Intrinsics.areEqual(this.deviceData, mediaControlModel.deviceData) && Intrinsics.areEqual(this.semanticActionButtons, mediaControlModel.semanticActionButtons) && Intrinsics.areEqual(this.notificationActionButtons, mediaControlModel.notificationActionButtons) && Intrinsics.areEqual(this.actionsToShowInCollapsed, mediaControlModel.actionsToShowInCollapsed) && this.isDismissible == mediaControlModel.isDismissible && this.isResume == mediaControlModel.isResume && Intrinsics.areEqual(this.resumeProgress, mediaControlModel.resumeProgress);
    }

    public final List getActionsToShowInCollapsed() {
        return this.actionsToShowInCollapsed;
    }

    public final Icon getAppIcon() {
        return this.appIcon;
    }

    public final String getAppName() {
        return this.appName;
    }

    public final CharSequence getArtistName() {
        return this.artistName;
    }

    public final Icon getArtwork() {
        return this.artwork;
    }

    public final PendingIntent getClickIntent() {
        return this.clickIntent;
    }

    public final MediaDeviceData getDeviceData() {
        return this.deviceData;
    }

    public final InstanceId getInstanceId() {
        return this.instanceId;
    }

    public final List getNotificationActionButtons() {
        return this.notificationActionButtons;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final Double getResumeProgress() {
        return this.resumeProgress;
    }

    public final MediaButton getSemanticActionButtons() {
        return this.semanticActionButtons;
    }

    public final boolean getShowExplicit() {
        return this.showExplicit;
    }

    public final CharSequence getSongName() {
        return this.songName;
    }

    public final MediaSession.Token getToken() {
        return this.token;
    }

    public final int getUid() {
        return this.uid;
    }

    public int hashCode() {
        int iHashCode = ((((Integer.hashCode(this.uid) * 31) + this.packageName.hashCode()) * 31) + this.instanceId.hashCode()) * 31;
        MediaSession.Token token = this.token;
        int iHashCode2 = (iHashCode + (token == null ? 0 : token.hashCode())) * 31;
        Icon icon = this.appIcon;
        int iHashCode3 = (iHashCode2 + (icon == null ? 0 : icon.hashCode())) * 31;
        PendingIntent pendingIntent = this.clickIntent;
        int iHashCode4 = (iHashCode3 + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
        String str = this.appName;
        int iHashCode5 = (iHashCode4 + (str == null ? 0 : str.hashCode())) * 31;
        CharSequence charSequence = this.songName;
        int iHashCode6 = (iHashCode5 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        CharSequence charSequence2 = this.artistName;
        int iHashCode7 = (((iHashCode6 + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31) + Boolean.hashCode(this.showExplicit)) * 31;
        Icon icon2 = this.artwork;
        int iHashCode8 = (iHashCode7 + (icon2 == null ? 0 : icon2.hashCode())) * 31;
        MediaDeviceData mediaDeviceData = this.deviceData;
        int iHashCode9 = (iHashCode8 + (mediaDeviceData == null ? 0 : mediaDeviceData.hashCode())) * 31;
        MediaButton mediaButton = this.semanticActionButtons;
        int iHashCode10 = (((((((((iHashCode9 + (mediaButton == null ? 0 : mediaButton.hashCode())) * 31) + this.notificationActionButtons.hashCode()) * 31) + this.actionsToShowInCollapsed.hashCode()) * 31) + Boolean.hashCode(this.isDismissible)) * 31) + Boolean.hashCode(this.isResume)) * 31;
        Double d = this.resumeProgress;
        return iHashCode10 + (d != null ? d.hashCode() : 0);
    }

    public final boolean isDismissible() {
        return this.isDismissible;
    }

    public final boolean isResume() {
        return this.isResume;
    }

    public String toString() {
        int i = this.uid;
        String str = this.packageName;
        InstanceId instanceId = this.instanceId;
        MediaSession.Token token = this.token;
        Icon icon = this.appIcon;
        PendingIntent pendingIntent = this.clickIntent;
        String str2 = this.appName;
        CharSequence charSequence = this.songName;
        CharSequence charSequence2 = this.artistName;
        return "MediaControlModel(uid=" + i + ", packageName=" + str + ", instanceId=" + instanceId + ", token=" + token + ", appIcon=" + icon + ", clickIntent=" + pendingIntent + ", appName=" + str2 + ", songName=" + ((Object) charSequence) + ", artistName=" + ((Object) charSequence2) + ", showExplicit=" + this.showExplicit + ", artwork=" + this.artwork + ", deviceData=" + this.deviceData + ", semanticActionButtons=" + this.semanticActionButtons + ", notificationActionButtons=" + this.notificationActionButtons + ", actionsToShowInCollapsed=" + this.actionsToShowInCollapsed + ", isDismissible=" + this.isDismissible + ", isResume=" + this.isResume + ", resumeProgress=" + this.resumeProgress + ")";
    }
}
