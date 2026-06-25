package com.motorola.taskbar.util;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.RemoteException;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AppUtils {
    public static String getDesktopRestartModeByPackages(String str) {
        List desktopRestartModeByPackages;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        try {
            desktopRestartModeByPackages = ActivityManager.getService().getDesktopRestartModeByPackages(arrayList);
        } catch (RemoteException e) {
            e.printStackTrace();
            desktopRestartModeByPackages = null;
        }
        return (desktopRestartModeByPackages == null || desktopRestartModeByPackages.isEmpty()) ? "" : (String) desktopRestartModeByPackages.get(0);
    }

    public static int getTopTaskId(int i) {
        ActivityManager.RunningTaskInfo topTaskInfo = getTopTaskInfo(i);
        if (topTaskInfo != null) {
            return topTaskInfo.taskId;
        }
        return -1;
    }

    public static ActivityManager.RunningTaskInfo getTopTaskInfo(int i) {
        try {
            return ActivityTaskManager.getService().getTopVisibleTask(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTopTaskPackage(int i) {
        ActivityManager.RunningTaskInfo topTaskInfo = getTopTaskInfo(i);
        if (topTaskInfo != null) {
            Intent intent = topTaskInfo.baseIntent;
            if (intent != null) {
                return intent.getComponent() != null ? intent.getComponent().getPackageName() : intent.getPackage();
            }
            ComponentName componentName = topTaskInfo.topActivity;
            if (componentName != null) {
                return componentName.getPackageName();
            }
        }
        return null;
    }

    public static void setDesktopRestartModeByPackages(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(str2);
        try {
            ActivityManager.getService().setDesktopRestartModeByPackages(arrayList, arrayList2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
