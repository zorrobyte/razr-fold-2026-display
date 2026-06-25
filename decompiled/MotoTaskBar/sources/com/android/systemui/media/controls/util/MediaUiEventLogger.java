package com.android.systemui.media.controls.util;

import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;

/* JADX INFO: compiled from: MediaUiEventLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaUiEventLogger {
    private final InstanceIdSequence instanceIdSequence;
    private final UiEventLogger logger;

    public MediaUiEventLogger(UiEventLogger uiEventLogger) {
        uiEventLogger.getClass();
        this.logger = uiEventLogger;
        this.instanceIdSequence = new InstanceIdSequence(1048576);
    }

    public final InstanceId getNewInstanceId() {
        InstanceId instanceIdNewInstanceId = this.instanceIdSequence.newInstanceId();
        instanceIdNewInstanceId.getClass();
        return instanceIdNewInstanceId;
    }

    public final void logActiveConvertedToResume(int i, String str, InstanceId instanceId) {
        str.getClass();
        instanceId.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.ACTIVE_TO_RESUME, i, str, instanceId);
    }

    public final void logActiveMediaAdded(int i, String str, InstanceId instanceId, int i2) {
        MediaUiEvent mediaUiEvent;
        str.getClass();
        instanceId.getClass();
        if (i2 == 0) {
            mediaUiEvent = MediaUiEvent.LOCAL_MEDIA_ADDED;
        } else if (i2 == 1) {
            mediaUiEvent = MediaUiEvent.CAST_MEDIA_ADDED;
        } else {
            if (i2 != 2) {
                throw new IllegalArgumentException("Unknown playback location");
            }
            mediaUiEvent = MediaUiEvent.REMOTE_MEDIA_ADDED;
        }
        this.logger.logWithInstanceId(mediaUiEvent, i, str, instanceId);
    }

    public final void logCarouselPosition(int i) {
    }

    public final void logCarouselSettings() {
        this.logger.log(MediaUiEvent.OPEN_SETTINGS_CAROUSEL);
    }

    public final void logLongPressDismiss(int i, String str, InstanceId instanceId) {
        str.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.DISMISS_LONG_PRESS, i, str, instanceId);
    }

    public final void logLongPressOpen(int i, String str, InstanceId instanceId) {
        str.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.OPEN_LONG_PRESS, i, str, instanceId);
    }

    public final void logLongPressSettings(int i, String str, InstanceId instanceId) {
        str.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.OPEN_SETTINGS_LONG_PRESS, i, str, instanceId);
    }

    public final void logMediaCarouselPage(int i) {
        this.logger.logWithPosition(MediaUiEvent.CAROUSEL_PAGE, 0, (String) null, i);
    }

    public final void logMediaRemoved(int i, String str, InstanceId instanceId) {
        str.getClass();
        instanceId.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_REMOVED, i, str, instanceId);
    }

    public final void logMediaTimeout(int i, String str, InstanceId instanceId) {
        str.getClass();
        instanceId.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_TIMEOUT, i, str, instanceId);
    }

    public final void logMultipleMediaPlayersInCarousel(int i, String str, InstanceId instanceId) {
        str.getClass();
        instanceId.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_MULTIPLE_PLAYERS, i, str, instanceId);
    }

    public final void logOpenBroadcastDialog(int i, String str, InstanceId instanceId) {
        str.getClass();
        instanceId.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_OPEN_BROADCAST_DIALOG, i, str, instanceId);
    }

    public final void logOpenOutputSwitcher(int i, String str, InstanceId instanceId) {
        str.getClass();
        instanceId.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.OPEN_OUTPUT_SWITCHER, i, str, instanceId);
    }

    public final void logPlaybackLocationChange(int i, String str, InstanceId instanceId, int i2) {
        MediaUiEvent mediaUiEvent;
        str.getClass();
        instanceId.getClass();
        if (i2 == 0) {
            mediaUiEvent = MediaUiEvent.TRANSFER_TO_LOCAL;
        } else if (i2 == 1) {
            mediaUiEvent = MediaUiEvent.TRANSFER_TO_CAST;
        } else {
            if (i2 != 2) {
                throw new IllegalArgumentException("Unknown playback location");
            }
            mediaUiEvent = MediaUiEvent.TRANSFER_TO_REMOTE;
        }
        this.logger.logWithInstanceId(mediaUiEvent, i, str, instanceId);
    }

    public final void logResumeMediaAdded(int i, String str, InstanceId instanceId) {
        str.getClass();
        instanceId.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.RESUME_MEDIA_ADDED, i, str, instanceId);
    }

    public final void logSeek(int i, String str, InstanceId instanceId) {
        str.getClass();
        instanceId.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.ACTION_SEEK, i, str, instanceId);
    }

    public final void logSingleMediaPlayerInCarousel(int i, String str, InstanceId instanceId) {
        str.getClass();
        instanceId.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_SINGLE_PLAYER, i, str, instanceId);
    }

    public final void logSwipeDismiss() {
        this.logger.log(MediaUiEvent.DISMISS_SWIPE);
    }

    public final void logTapAction(int i, int i2, String str, InstanceId instanceId) {
        str.getClass();
        instanceId.getClass();
    }

    public final void logTapContentView(int i, String str, InstanceId instanceId) {
        str.getClass();
        instanceId.getClass();
        this.logger.logWithInstanceId(MediaUiEvent.MEDIA_TAP_CONTENT_VIEW, i, str, instanceId);
    }
}
