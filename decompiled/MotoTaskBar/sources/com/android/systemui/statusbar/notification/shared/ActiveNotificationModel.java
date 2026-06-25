package com.android.systemui.statusbar.notification.shared;

import android.graphics.drawable.Icon;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ActiveNotificationModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ActiveNotificationModel extends ActiveNotificationEntryModel {
    private final Icon aodIcon;
    private final int bucket;
    private final String groupKey;
    private final Integer instanceId;
    private final boolean isAmbient;
    private final boolean isGroupSummary;
    private final boolean isLastMessageFromReply;
    private final boolean isPulsing;
    private final boolean isRowDismissed;
    private final boolean isSilent;
    private final boolean isSuppressedFromStatusBar;
    private final String key;
    private final String packageName;
    private final Icon shelfIcon;
    private final Icon statusBarIcon;
    private final int uid;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActiveNotificationModel(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, Icon icon, Icon icon2, Icon icon3, int i, String str3, Integer num, boolean z7, int i2) {
        super(null);
        str.getClass();
        str3.getClass();
        this.key = str;
        this.groupKey = str2;
        this.isAmbient = z;
        this.isRowDismissed = z2;
        this.isSilent = z3;
        this.isLastMessageFromReply = z4;
        this.isSuppressedFromStatusBar = z5;
        this.isPulsing = z6;
        this.aodIcon = icon;
        this.shelfIcon = icon2;
        this.statusBarIcon = icon3;
        this.uid = i;
        this.packageName = str3;
        this.instanceId = num;
        this.isGroupSummary = z7;
        this.bucket = i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActiveNotificationModel)) {
            return false;
        }
        ActiveNotificationModel activeNotificationModel = (ActiveNotificationModel) obj;
        return Intrinsics.areEqual(this.key, activeNotificationModel.key) && Intrinsics.areEqual(this.groupKey, activeNotificationModel.groupKey) && this.isAmbient == activeNotificationModel.isAmbient && this.isRowDismissed == activeNotificationModel.isRowDismissed && this.isSilent == activeNotificationModel.isSilent && this.isLastMessageFromReply == activeNotificationModel.isLastMessageFromReply && this.isSuppressedFromStatusBar == activeNotificationModel.isSuppressedFromStatusBar && this.isPulsing == activeNotificationModel.isPulsing && Intrinsics.areEqual(this.aodIcon, activeNotificationModel.aodIcon) && Intrinsics.areEqual(this.shelfIcon, activeNotificationModel.shelfIcon) && Intrinsics.areEqual(this.statusBarIcon, activeNotificationModel.statusBarIcon) && this.uid == activeNotificationModel.uid && Intrinsics.areEqual(this.packageName, activeNotificationModel.packageName) && Intrinsics.areEqual(this.instanceId, activeNotificationModel.instanceId) && this.isGroupSummary == activeNotificationModel.isGroupSummary && this.bucket == activeNotificationModel.bucket;
    }

    public final Icon getAodIcon() {
        return this.aodIcon;
    }

    public final int getBucket() {
        return this.bucket;
    }

    public final String getGroupKey() {
        return this.groupKey;
    }

    public final Integer getInstanceId() {
        return this.instanceId;
    }

    public final String getKey() {
        return this.key;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final Icon getShelfIcon() {
        return this.shelfIcon;
    }

    public final Icon getStatusBarIcon() {
        return this.statusBarIcon;
    }

    public final int getUid() {
        return this.uid;
    }

    public int hashCode() {
        int iHashCode = this.key.hashCode() * 31;
        String str = this.groupKey;
        int iHashCode2 = (((((((((((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Boolean.hashCode(this.isAmbient)) * 31) + Boolean.hashCode(this.isRowDismissed)) * 31) + Boolean.hashCode(this.isSilent)) * 31) + Boolean.hashCode(this.isLastMessageFromReply)) * 31) + Boolean.hashCode(this.isSuppressedFromStatusBar)) * 31) + Boolean.hashCode(this.isPulsing)) * 31;
        Icon icon = this.aodIcon;
        int iHashCode3 = (iHashCode2 + (icon == null ? 0 : icon.hashCode())) * 31;
        Icon icon2 = this.shelfIcon;
        int iHashCode4 = (iHashCode3 + (icon2 == null ? 0 : icon2.hashCode())) * 31;
        Icon icon3 = this.statusBarIcon;
        int iHashCode5 = (((((iHashCode4 + (icon3 == null ? 0 : icon3.hashCode())) * 31) + Integer.hashCode(this.uid)) * 31) + this.packageName.hashCode()) * 31;
        Integer num = this.instanceId;
        return ((((iHashCode5 + (num != null ? num.hashCode() : 0)) * 31) + Boolean.hashCode(this.isGroupSummary)) * 31) + Integer.hashCode(this.bucket);
    }

    public final boolean isAmbient() {
        return this.isAmbient;
    }

    public final boolean isGroupSummary() {
        return this.isGroupSummary;
    }

    public final boolean isLastMessageFromReply() {
        return this.isLastMessageFromReply;
    }

    public final boolean isPulsing() {
        return this.isPulsing;
    }

    public final boolean isRowDismissed() {
        return this.isRowDismissed;
    }

    public final boolean isSilent() {
        return this.isSilent;
    }

    public final boolean isSuppressedFromStatusBar() {
        return this.isSuppressedFromStatusBar;
    }

    public String toString() {
        return "ActiveNotificationModel(key=" + this.key + ", groupKey=" + this.groupKey + ", isAmbient=" + this.isAmbient + ", isRowDismissed=" + this.isRowDismissed + ", isSilent=" + this.isSilent + ", isLastMessageFromReply=" + this.isLastMessageFromReply + ", isSuppressedFromStatusBar=" + this.isSuppressedFromStatusBar + ", isPulsing=" + this.isPulsing + ", aodIcon=" + this.aodIcon + ", shelfIcon=" + this.shelfIcon + ", statusBarIcon=" + this.statusBarIcon + ", uid=" + this.uid + ", packageName=" + this.packageName + ", instanceId=" + this.instanceId + ", isGroupSummary=" + this.isGroupSummary + ", bucket=" + this.bucket + ")";
    }
}
