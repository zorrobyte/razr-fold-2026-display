package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: compiled from: HeadsUpManager.kt */
/* JADX INFO: loaded from: classes.dex */
public interface HeadsUpManager extends Dumpable {
    void addListener(OnHeadsUpChangedListener onHeadsUpChangedListener);

    void addSwipedOutNotification(String str);

    boolean canRemoveImmediately(String str);

    int compare(NotificationEntry notificationEntry, NotificationEntry notificationEntry2);

    long getEarliestRemovalTime(String str);

    NotificationEntry getTopEntry();

    boolean isHeadsUpEntry(String str);

    boolean isSnoozed(String str);

    boolean isSticky(String str);

    boolean isTrackingHeadsUp();

    void releaseAllImmediately();

    boolean removeNotification(String str, boolean z);

    void setAnimationStateHandler(AnimationStateHandler animationStateHandler);

    void setExpanded(NotificationEntry notificationEntry, boolean z);

    void setRemoteInputActive(NotificationEntry notificationEntry, boolean z);

    void setUser(int i);

    void setUserActionMayIndirectlyRemove(NotificationEntry notificationEntry);

    void showNotification(NotificationEntry notificationEntry);

    void updateNotification(NotificationEntry notificationEntry, boolean z);
}
