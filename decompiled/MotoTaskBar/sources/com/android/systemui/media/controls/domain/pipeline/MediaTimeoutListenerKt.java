package com.android.systemui.media.controls.domain.pipeline;

import android.os.SystemProperties;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: MediaTimeoutListener.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class MediaTimeoutListenerKt {
    private static final long PAUSED_MEDIA_TIMEOUT = SystemProperties.getLong("debug.sysui.media_timeout", TimeUnit.MINUTES.toMillis(10));
    private static final long RESUME_MEDIA_TIMEOUT = SystemProperties.getLong("debug.sysui.media_timeout_resume", TimeUnit.DAYS.toMillis(2));

    public static final long getPAUSED_MEDIA_TIMEOUT() {
        return PAUSED_MEDIA_TIMEOUT;
    }

    public static /* synthetic */ void getPAUSED_MEDIA_TIMEOUT$annotations() {
    }

    public static final long getRESUME_MEDIA_TIMEOUT() {
        return RESUME_MEDIA_TIMEOUT;
    }

    public static /* synthetic */ void getRESUME_MEDIA_TIMEOUT$annotations() {
    }
}
