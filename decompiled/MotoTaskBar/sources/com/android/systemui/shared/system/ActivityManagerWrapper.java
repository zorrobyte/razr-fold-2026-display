package com.android.systemui.shared.system;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.window.TaskSnapshot;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;

/* JADX INFO: loaded from: classes.dex */
public class ActivityManagerWrapper {
    private static final ActivityManagerWrapper sInstance = new ActivityManagerWrapper();
    private final ActivityTaskManager mAtm = ActivityTaskManager.getInstance();

    private ActivityManagerWrapper() {
    }

    public static ActivityManagerWrapper getInstance() {
        return sInstance;
    }

    public void closeSystemWindows(String str) {
        try {
            ActivityManager.getService().closeSystemDialogs(str);
        } catch (RemoteException e) {
            Log.w("ActivityManagerWrapper", "Failed to close system windows", e);
        }
    }

    public ThumbnailData getTaskThumbnail(int i, boolean z) {
        TaskSnapshot taskSnapshot = null;
        try {
            taskSnapshot = ActivityTaskManager.getService().getTaskSnapshot(i, z);
            if (taskSnapshot == null) {
                taskSnapshot = ActivityTaskManager.getService().takeTaskSnapshot(i, false);
            }
        } catch (RemoteException e) {
            Log.w("ActivityManagerWrapper", "Failed to retrieve task snapshot", e);
        }
        return taskSnapshot != null ? ThumbnailData.fromSnapshot(taskSnapshot) : new ThumbnailData();
    }

    public boolean isLockTaskKioskModeActive() {
        return ActivityTaskManager.getService().getLockTaskModeState() == 1;
    }

    public void removeTask(int i) {
        try {
            ActivityTaskManager.getService().removeTask(i);
        } catch (RemoteException e) {
            Log.w("ActivityManagerWrapper", "Failed to remove task=" + i, e);
        }
    }

    public boolean startActivityFromRecents(int i, ActivityOptions activityOptions) {
        Bundle bundle;
        if (activityOptions == null) {
            bundle = null;
        } else {
            try {
                bundle = activityOptions.toBundle();
            } catch (Exception unused) {
                return false;
            }
        }
        return ActivityManager.isStartResultSuccessful(ActivityTaskManager.getService().startActivityFromRecents(i, bundle));
    }

    public boolean startActivityFromRecents(Task.TaskKey taskKey, ActivityOptions activityOptions) {
        return startActivityFromRecents(taskKey.id, activityOptions);
    }
}
