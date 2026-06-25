package com.android.systemui.statusbar;

import android.util.SparseArray;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: loaded from: classes.dex */
public interface NotificationLockscreenUserManager {

    public interface NotificationStateChangedListener {
        void onNotificationStateChanged();
    }

    public interface UserChangedListener {
        default void onCurrentProfilesChanged(SparseArray sparseArray) {
        }

        default void onUserChanged(int i) {
        }

        default void onUserRemoved(int i) {
        }
    }

    void addNotificationStateChangedListener(NotificationStateChangedListener notificationStateChangedListener);

    void addUserChangedListener(UserChangedListener userChangedListener);

    int getCurrentUserId();

    boolean isAnyProfilePublicMode();

    boolean isCurrentProfile(int i);

    boolean isLockscreenPublicMode(int i);

    boolean isProfileAvailable(int i);

    boolean needsRedaction(NotificationEntry notificationEntry);

    boolean needsSeparateWorkChallenge(int i);

    void setUpWithPresenter(NotificationPresenter notificationPresenter);

    boolean userAllowsPrivateNotificationsInPublic(int i);
}
