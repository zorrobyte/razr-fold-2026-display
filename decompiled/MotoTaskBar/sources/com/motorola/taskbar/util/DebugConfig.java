package com.motorola.taskbar.util;

import android.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public abstract class DebugConfig {
    private static final boolean DEBUG_ALL;
    public static final boolean DEBUG_APP_INITIATE;
    public static final boolean DEBUG_COMMON;
    public static final boolean DEBUG_HEADS_UP;
    public static final boolean DEBUG_MULTI_USER;
    public static final boolean DEBUG_NOTIFICATION;
    public static final boolean DEBUG_QS_PANEL;
    public static final boolean DEBUG_RECENT_TASK_VIEW;
    public static final boolean DEBUG_TASK_BAR_CONTROLLER;
    public static final boolean DEBUG_TASK_BAR_SERVICE;
    public static final boolean DEBUG_TASK_BAR_SERVICE_PROXY;
    public static final boolean DEBUG_TASK_CONTROLLER;
    public static final boolean DEBUG_TASK_SYSTEM_ICON;
    private static final boolean IS_USER_BUILD;

    static {
        boolean zEquals = "user".equals(Build.TYPE);
        IS_USER_BUILD = zEquals;
        boolean z = !zEquals;
        DEBUG_ALL = z;
        DEBUG_APP_INITIATE = true;
        DEBUG_TASK_BAR_SERVICE = true;
        DEBUG_TASK_BAR_CONTROLLER = true;
        DEBUG_TASK_CONTROLLER = true;
        DEBUG_RECENT_TASK_VIEW = true;
        DEBUG_TASK_SYSTEM_ICON = true;
        DEBUG_QS_PANEL = true;
        DEBUG_NOTIFICATION = true;
        DEBUG_HEADS_UP = true;
        DEBUG_MULTI_USER = true;
        DEBUG_TASK_BAR_SERVICE_PROXY = true;
        DEBUG_COMMON = z;
    }
}
