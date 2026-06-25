package com.motorola.taskbar.recent;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.util.Log;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;

/* JADX INFO: loaded from: classes2.dex */
public class TaskStackChangeLogOutput {
    private static TaskStackChangeLogOutput sInstance;
    private TaskStackChangeListener mTaskStackChangeListener = new TaskStackChangeListener(this) { // from class: com.motorola.taskbar.recent.TaskStackChangeLogOutput.1
        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityDismissingDockedStack() {
            Log.d("TaskStackChangeLogOutput", "onActivityDismissingDockedStack");
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityForcedResizable(String str, int i, int i2) {
            Log.d("TaskStackChangeLogOutput", "onActivityForcedResizable packageName: " + str + "; taskId: " + i + "; reason = " + i2);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityLaunchOnSecondaryDisplayFailed() {
            Log.d("TaskStackChangeLogOutput", "onActivityLaunchOnSecondaryDisplayFailed");
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo) {
            Log.d("TaskStackChangeLogOutput", "onActivityLaunchOnSecondaryDisplayFailed taskInfo: " + runningTaskInfo);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityLaunchOnSecondaryDisplayRerouted() {
            Log.d("TaskStackChangeLogOutput", "onActivityLaunchOnSecondaryDisplayRerouted");
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo) {
            Log.d("TaskStackChangeLogOutput", "onActivityLaunchOnSecondaryDisplayRerouted taskInfo: " + runningTaskInfo);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityPinned(String str, int i, int i2, int i3) {
            Log.d("TaskStackChangeLogOutput", "onActivityPinned");
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityRequestedOrientationChanged(int i, int i2) {
            Log.d("TaskStackChangeLogOutput", "onActivityRequestedOrientationChanged taskId: " + i + "; requestedOrientation: " + i2);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) {
            Log.d("TaskStackChangeLogOutput", "onActivityRestartAttempt task: " + runningTaskInfo + "; homeTaskVisible: " + z + "; clearedTask: " + z2 + "; wasVisible: " + z3);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityRotation(int i) {
            Log.d("TaskStackChangeLogOutput", "onActivityRotation displayId: " + i);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityUnpinned() {
            Log.d("TaskStackChangeLogOutput", "onActivityUnpinned");
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) {
            Log.d("TaskStackChangeLogOutput", "onBackPressedOnTaskRoot taskInfo: " + runningTaskInfo);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onLockTaskModeChanged(int i) {
            Log.d("TaskStackChangeLogOutput", "onLockTaskModeChanged mode: " + i);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onRecentTaskListFrozenChanged(boolean z) {
            Log.d("TaskStackChangeLogOutput", "onRecentTaskListFrozenChanged frozen: " + z);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onRecentTaskListUpdated() {
            Log.d("TaskStackChangeLogOutput", "onRecentTaskListUpdated");
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskCreated(int i, ComponentName componentName) {
            Log.d("TaskStackChangeLogOutput", "onTaskCreated taskId: " + i + "; componentName: " + componentName);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
            Log.d("TaskStackChangeLogOutput", "onTaskDescriptionChanged taskInfo: " + runningTaskInfo);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskDisplayChanged(int i, int i2) {
            Log.d("TaskStackChangeLogOutput", "onTaskDisplayChanged taskId: " + i + "; newDisplayId = " + i2);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskMovedToFront(int i) {
            Log.d("TaskStackChangeLogOutput", "onTaskMovedToFront taskId: " + i);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
            Log.d("TaskStackChangeLogOutput", "onTaskMovedToFront taskInfo: " + runningTaskInfo);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskRemoved(int i) {
            Log.d("TaskStackChangeLogOutput", "onTaskRemoved taskId: " + i);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public boolean onTaskSnapshotChanged(int i, ThumbnailData thumbnailData) {
            Log.d("TaskStackChangeLogOutput", "onTaskSnapshotChanged taskId: " + i);
            return false;
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskStackChanged() {
            Log.d("TaskStackChangeLogOutput", "onTaskStackChanged");
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskStackChangedBackground() {
            Log.d("TaskStackChangeLogOutput", "onTaskStackChangedBackground");
        }
    };

    private TaskStackChangeLogOutput() {
        TaskStackChangeListeners.getInstance().registerTaskStackListener(this.mTaskStackChangeListener);
    }

    public static synchronized void startChangeMonitor() {
        if (sInstance != null) {
            return;
        }
        sInstance = new TaskStackChangeLogOutput();
    }
}
