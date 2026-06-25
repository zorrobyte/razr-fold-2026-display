package com.motorola.taskbar.util;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class ActivityManagerWrapperExt {
    private static final ActivityManagerWrapperExt sInstance = new ActivityManagerWrapperExt();
    private final TaskStackChangeListenersExt mTaskStackChangeListeners = new TaskStackChangeListenersExt(Looper.getMainLooper());

    private ActivityManagerWrapperExt() {
    }

    public static ActivityManagerWrapperExt getInstance() {
        return sInstance;
    }

    public ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() {
        try {
            return ActivityTaskManager.getService().getFocusedRootTaskInfo();
        } catch (RemoteException e) {
            Log.e("ActivityManagerWrapperExt", "Failed to get recent tasks", e);
            return null;
        }
    }

    public List getRecentTasks(int i, int i2) {
        try {
            return ActivityTaskManager.getService().getRecentTasks(i, 3, i2).getList();
        } catch (RemoteException e) {
            Log.e("ActivityManagerWrapperExt", "Failed to get recent tasks", e);
            return new ArrayList();
        }
    }

    public void registerTaskStackListener(TaskStackChangeListenerExt taskStackChangeListenerExt) {
        synchronized (this.mTaskStackChangeListeners) {
            this.mTaskStackChangeListeners.addListener(ActivityManager.getService(), taskStackChangeListenerExt);
        }
    }
}
