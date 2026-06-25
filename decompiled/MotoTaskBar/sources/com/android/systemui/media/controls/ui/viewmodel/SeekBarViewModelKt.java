package com.android.systemui.media.controls.ui.viewmodel;

import android.media.session.PlaybackState;
import android.os.SystemClock;

/* JADX INFO: compiled from: SeekBarViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SeekBarViewModelKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final long computePosition(PlaybackState playbackState, long j) {
        long position = playbackState.getPosition();
        if (isInMotion(playbackState)) {
            long lastPositionUpdateTime = playbackState.getLastPositionUpdateTime();
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (lastPositionUpdateTime > 0) {
                position = ((long) (playbackState.getPlaybackSpeed() * (jElapsedRealtime - lastPositionUpdateTime))) + playbackState.getPosition();
                if (j >= 0 && position > j) {
                    return j;
                }
                if (position < 0) {
                    return 0L;
                }
            }
        }
        return position;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isInMotion(PlaybackState playbackState) {
        return playbackState.getState() == 3 || playbackState.getState() == 4 || playbackState.getState() == 5;
    }
}
