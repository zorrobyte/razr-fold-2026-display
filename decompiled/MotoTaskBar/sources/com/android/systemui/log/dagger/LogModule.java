package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.util.Compile;

/* JADX INFO: loaded from: classes.dex */
public abstract class LogModule {
    public static LogBuffer provideMediaBrowserBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("MediaBrowser", 100);
    }

    public static LogBuffer provideMediaCarouselControllerBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("MediaCarouselCtlrLog", 20);
    }

    public static LogBuffer provideMediaViewLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("MediaView", 100);
    }

    public static LogBuffer provideNotifInflationLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("NotifInflationLog", 250);
    }

    public static LogBuffer provideNotifInteractionLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("NotifInteractionLog", 50);
    }

    public static LogBuffer provideNotificationHeadsUpLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("NotifHeadsUpLog", 1000);
    }

    public static LogBuffer provideNotificationInterruptLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("NotifInterruptLog", 100);
    }

    public static LogBuffer provideNotificationRemoteInputLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("NotifRemoteInputLog", 50, false);
    }

    public static LogBuffer provideNotificationRenderLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("NotifRenderLog", 100);
    }

    public static LogBuffer provideNotificationsLogBuffer(LogBufferFactory logBufferFactory, NotifPipelineFlags notifPipelineFlags) {
        boolean z = Compile.IS_DEBUG;
        return logBufferFactory.create("NotifLog", (z && notifPipelineFlags.isDevLoggingEnabled()) ? 10000 : 1000, z);
    }

    public static LogBuffer provideShadeLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("ShadeLog", 500, false);
    }

    public static LogBuffer provideWakeLockLog(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("WakeLockLog", 500, false);
    }
}
