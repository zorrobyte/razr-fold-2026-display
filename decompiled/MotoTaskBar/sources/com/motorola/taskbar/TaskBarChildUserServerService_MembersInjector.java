package com.motorola.taskbar;

import android.os.Handler;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;

/* JADX INFO: loaded from: classes2.dex */
public abstract class TaskBarChildUserServerService_MembersInjector {
    public static void injectMBgHandler(TaskBarChildUserServerService taskBarChildUserServerService, Handler handler) {
        taskBarChildUserServerService.mBgHandler = handler;
    }

    public static void injectMDeviceProvisionedController(TaskBarChildUserServerService taskBarChildUserServerService, DeviceProvisionedController deviceProvisionedController) {
        taskBarChildUserServerService.mDeviceProvisionedController = deviceProvisionedController;
    }

    public static void injectMTaskBarServiceProxy(TaskBarChildUserServerService taskBarChildUserServerService, TaskBarServiceProxy taskBarServiceProxy) {
        taskBarChildUserServerService.mTaskBarServiceProxy = taskBarServiceProxy;
    }
}
