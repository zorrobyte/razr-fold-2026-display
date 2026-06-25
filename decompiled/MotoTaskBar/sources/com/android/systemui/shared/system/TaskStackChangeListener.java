package com.android.systemui.shared.system;

import android.app.ActivityManager;
import android.content.ComponentName;
import com.android.systemui.shared.recents.model.ThumbnailData;

/* JADX INFO: loaded from: classes.dex */
public interface TaskStackChangeListener {
    default void onActivityDismissingDockedStack() {
    }

    default void onActivityForcedResizable(String str, int i, int i2) {
    }

    default void onActivityLaunchOnSecondaryDisplayFailed() {
    }

    default void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo) {
        onActivityLaunchOnSecondaryDisplayFailed();
    }

    default void onActivityLaunchOnSecondaryDisplayRerouted() {
    }

    default void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo) {
        onActivityLaunchOnSecondaryDisplayRerouted();
    }

    default void onActivityPinned(String str, int i, int i2, int i3) {
    }

    default void onActivityRequestedOrientationChanged(int i, int i2) {
    }

    default void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) {
    }

    default void onActivityRotation(int i) {
    }

    default void onActivityUnpinned() {
    }

    default void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    default void onLockTaskModeChanged(int i) {
    }

    default void onRecentTaskListFrozenChanged(boolean z) {
    }

    void onRecentTaskListUpdated();

    void onTaskCreated(int i, ComponentName componentName);

    default void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    void onTaskDisplayChanged(int i, int i2);

    void onTaskMovedToFront(int i);

    default void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
        onTaskMovedToFront(runningTaskInfo.taskId);
    }

    default void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
    }

    void onTaskRemoved(int i);

    boolean onTaskSnapshotChanged(int i, ThumbnailData thumbnailData);

    default void onTaskStackChanged() {
    }

    void onTaskStackChangedBackground();
}
