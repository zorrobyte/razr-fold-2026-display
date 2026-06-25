package com.android.systemui.statusbar.notification.icon;

import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.statusbar.StatusBarIconView;

/* JADX INFO: loaded from: classes.dex */
public final class IconPack {
    private final StatusBarIconView mAodIcon;
    private final boolean mAreIconsAvailable;
    private boolean mIsImportantConversation;
    private StatusBarIcon mPeopleAvatarDescriptor;
    private final StatusBarIconView mShelfIcon;
    private StatusBarIcon mSmallIconDescriptor;
    private final StatusBarIconView mStatusBarIcon;

    private IconPack(boolean z, StatusBarIconView statusBarIconView, StatusBarIconView statusBarIconView2, StatusBarIconView statusBarIconView3, IconPack iconPack) {
        this.mAreIconsAvailable = z;
        this.mStatusBarIcon = statusBarIconView;
        this.mShelfIcon = statusBarIconView2;
        this.mAodIcon = statusBarIconView3;
        if (iconPack != null) {
            this.mIsImportantConversation = iconPack.mIsImportantConversation;
        }
    }

    public static IconPack buildEmptyPack(IconPack iconPack) {
        return new IconPack(false, null, null, null, iconPack);
    }

    public static IconPack buildPack(StatusBarIconView statusBarIconView, StatusBarIconView statusBarIconView2, StatusBarIconView statusBarIconView3, IconPack iconPack) {
        return new IconPack(true, statusBarIconView, statusBarIconView2, statusBarIconView3, iconPack);
    }

    public StatusBarIconView getAodIcon() {
        return this.mAodIcon;
    }

    public boolean getAreIconsAvailable() {
        return this.mAreIconsAvailable;
    }

    StatusBarIcon getPeopleAvatarDescriptor() {
        return this.mPeopleAvatarDescriptor;
    }

    public StatusBarIconView getShelfIcon() {
        return this.mShelfIcon;
    }

    StatusBarIcon getSmallIconDescriptor() {
        return this.mSmallIconDescriptor;
    }

    public StatusBarIconView getStatusBarIcon() {
        return this.mStatusBarIcon;
    }

    boolean isImportantConversation() {
        return this.mIsImportantConversation;
    }

    void setImportantConversation(boolean z) {
        this.mIsImportantConversation = z;
    }

    void setPeopleAvatarDescriptor(StatusBarIcon statusBarIcon) {
        this.mPeopleAvatarDescriptor = statusBarIcon;
    }

    void setSmallIconDescriptor(StatusBarIcon statusBarIcon) {
        this.mSmallIconDescriptor = statusBarIcon;
    }
}
