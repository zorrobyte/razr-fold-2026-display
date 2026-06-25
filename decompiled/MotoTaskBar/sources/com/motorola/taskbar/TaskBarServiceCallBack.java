package com.motorola.taskbar;

import android.app.PendingIntent;
import com.android.internal.statusbar.StatusBarIcon;

/* JADX INFO: loaded from: classes2.dex */
public interface TaskBarServiceCallBack {
    default void addDesktopIcon(String str, int i, StatusBarIcon statusBarIcon, PendingIntent pendingIntent) {
    }

    default void onBootComplete() {
    }

    default void onDisplayReady(int i) {
    }

    default void onDisplayRemoved(int i) {
    }

    default void onNavIconClicked() {
    }

    default void onOverviewShown() {
    }

    default void onQSTileDataUpdated(DesktopQSTileData desktopQSTileData) {
    }

    default void onRequestDisplayChooserModeInSecondUser() {
    }

    default void onSystemUIGone() {
    }

    default void onSystemUIReady() {
    }

    default void onTaskbarWindowStateChanged(int i, int i2) {
    }

    default void onUpdateDisplayChooserModeFromSecondUser(int i, String str) {
    }

    default void removeDesktopIcon(String str, int i) {
    }

    default void setTaskBarImeSwitchButtonVisible(int i, boolean z) {
    }

    default void setTaskBarTransitionMode(int i, int i2) {
    }

    default void setTaskBarViewVisibility(int i, int i2) {
    }

    default void updateImeVisible(int i, boolean z) {
    }
}
