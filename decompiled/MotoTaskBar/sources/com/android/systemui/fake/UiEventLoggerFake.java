package com.android.systemui.fake;

import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;

/* JADX INFO: loaded from: classes.dex */
public class UiEventLoggerFake implements UiEventLogger {
    public void log(UiEventLogger.UiEventEnum uiEventEnum) {
    }

    public void log(UiEventLogger.UiEventEnum uiEventEnum, int i, String str) {
    }

    public void log(UiEventLogger.UiEventEnum uiEventEnum, InstanceId instanceId) {
    }

    public void logWithInstanceId(UiEventLogger.UiEventEnum uiEventEnum, int i, String str, InstanceId instanceId) {
    }

    public void logWithInstanceIdAndPosition(UiEventLogger.UiEventEnum uiEventEnum, int i, String str, InstanceId instanceId, int i2) {
    }

    public void logWithPosition(UiEventLogger.UiEventEnum uiEventEnum, int i, String str, int i2) {
    }
}
