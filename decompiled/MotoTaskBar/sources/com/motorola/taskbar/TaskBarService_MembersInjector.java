package com.motorola.taskbar;

/* JADX INFO: loaded from: classes2.dex */
public abstract class TaskBarService_MembersInjector {
    public static void injectMTaskBarServiceProxy(TaskBarService taskBarService, TaskBarServiceProxy taskBarServiceProxy) {
        taskBarService.mTaskBarServiceProxy = taskBarServiceProxy;
    }
}
