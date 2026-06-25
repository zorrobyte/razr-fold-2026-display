package androidx.media3.session;

import android.os.Bundle;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.DeviceInfo;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.common.TrackSelectionParameters;
import androidx.media3.common.Tracks;
import androidx.media3.common.VideoSize;
import androidx.media3.common.text.CueGroup;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import com.google.common.base.Objects;

/* JADX INFO: loaded from: classes.dex */
class PlayerInfo {
    public static final PlayerInfo DEFAULT;
    private static final String FIELD_AUDIO_ATTRIBUTES;
    private static final String FIELD_CUE_GROUP;
    private static final String FIELD_CURRENT_TRACKS;
    private static final String FIELD_DEVICE_INFO;
    private static final String FIELD_DEVICE_MUTED;
    private static final String FIELD_DEVICE_VOLUME;
    private static final String FIELD_DISCONTINUITY_REASON;
    private static final String FIELD_IN_PROCESS_BINDER;
    private static final String FIELD_IS_LOADING;
    private static final String FIELD_IS_PLAYING;
    static final String FIELD_MAX_SEEK_TO_PREVIOUS_POSITION_MS;
    private static final String FIELD_MEDIA_ITEM_TRANSITION_REASON;
    private static final String FIELD_MEDIA_METADATA;
    static final String FIELD_NEW_POSITION_INFO;
    static final String FIELD_OLD_POSITION_INFO;
    private static final String FIELD_PLAYBACK_ERROR;
    private static final String FIELD_PLAYBACK_PARAMETERS;
    private static final String FIELD_PLAYBACK_STATE;
    private static final String FIELD_PLAYBACK_SUPPRESSION_REASON;
    private static final String FIELD_PLAYLIST_METADATA;
    private static final String FIELD_PLAY_WHEN_READY;
    private static final String FIELD_PLAY_WHEN_READY_CHANGE_REASON;
    private static final String FIELD_REPEAT_MODE;
    static final String FIELD_SEEK_BACK_INCREMENT_MS;
    static final String FIELD_SEEK_FORWARD_INCREMENT_MS;
    static final String FIELD_SESSION_POSITION_INFO;
    private static final String FIELD_SHUFFLE_MODE_ENABLED;
    private static final String FIELD_TIMELINE;
    private static final String FIELD_TIMELINE_CHANGE_REASON;
    private static final String FIELD_TRACK_SELECTION_PARAMETERS;
    private static final String FIELD_VIDEO_SIZE;
    private static final String FIELD_VOLUME;
    public final AudioAttributes audioAttributes;
    public final CueGroup cueGroup;
    public final Tracks currentTracks;
    public final DeviceInfo deviceInfo;
    public final boolean deviceMuted;
    public final int deviceVolume;
    public final int discontinuityReason;
    public final boolean isLoading;
    public final boolean isPlaying;
    public final long maxSeekToPreviousPositionMs;
    public final int mediaItemTransitionReason;
    public final MediaMetadata mediaMetadata;
    public final Player.PositionInfo newPositionInfo;
    public final Player.PositionInfo oldPositionInfo;
    public final boolean playWhenReady;
    public final int playWhenReadyChangeReason;
    public final PlaybackParameters playbackParameters;
    public final int playbackState;
    public final int playbackSuppressionReason;
    public final PlaybackException playerError;
    public final MediaMetadata playlistMetadata;
    public final int repeatMode;
    public final long seekBackIncrementMs;
    public final long seekForwardIncrementMs;
    public final SessionPositionInfo sessionPositionInfo;
    public final boolean shuffleModeEnabled;
    public final Timeline timeline;
    public final int timelineChangeReason;
    public final TrackSelectionParameters trackSelectionParameters;
    public final VideoSize videoSize;
    public final float volume;

    public class Builder {
        private AudioAttributes audioAttributes;
        private CueGroup cueGroup;
        private Tracks currentTracks;
        private DeviceInfo deviceInfo;
        private boolean deviceMuted;
        private int deviceVolume;
        private int discontinuityReason;
        private boolean isLoading;
        private boolean isPlaying;
        private long maxSeekToPreviousPositionMs;
        private int mediaItemTransitionReason;
        private MediaMetadata mediaMetadata;
        private Player.PositionInfo newPositionInfo;
        private Player.PositionInfo oldPositionInfo;
        private boolean playWhenReady;
        private int playWhenReadyChangeReason;
        private PlaybackParameters playbackParameters;
        private int playbackState;
        private int playbackSuppressionReason;
        private PlaybackException playerError;
        private MediaMetadata playlistMetadata;
        private int repeatMode;
        private long seekBackIncrementMs;
        private long seekForwardIncrementMs;
        private SessionPositionInfo sessionPositionInfo;
        private boolean shuffleModeEnabled;
        private Timeline timeline;
        private int timelineChangeReason;
        private TrackSelectionParameters trackSelectionParameters;
        private VideoSize videoSize;
        private float volume;

        public Builder(PlayerInfo playerInfo) {
            this.playerError = playerInfo.playerError;
            this.mediaItemTransitionReason = playerInfo.mediaItemTransitionReason;
            this.sessionPositionInfo = playerInfo.sessionPositionInfo;
            this.oldPositionInfo = playerInfo.oldPositionInfo;
            this.newPositionInfo = playerInfo.newPositionInfo;
            this.discontinuityReason = playerInfo.discontinuityReason;
            this.playbackParameters = playerInfo.playbackParameters;
            this.repeatMode = playerInfo.repeatMode;
            this.shuffleModeEnabled = playerInfo.shuffleModeEnabled;
            this.timeline = playerInfo.timeline;
            this.timelineChangeReason = playerInfo.timelineChangeReason;
            this.videoSize = playerInfo.videoSize;
            this.playlistMetadata = playerInfo.playlistMetadata;
            this.volume = playerInfo.volume;
            this.audioAttributes = playerInfo.audioAttributes;
            this.cueGroup = playerInfo.cueGroup;
            this.deviceInfo = playerInfo.deviceInfo;
            this.deviceVolume = playerInfo.deviceVolume;
            this.deviceMuted = playerInfo.deviceMuted;
            this.playWhenReady = playerInfo.playWhenReady;
            this.playWhenReadyChangeReason = playerInfo.playWhenReadyChangeReason;
            this.isPlaying = playerInfo.isPlaying;
            this.isLoading = playerInfo.isLoading;
            this.playbackSuppressionReason = playerInfo.playbackSuppressionReason;
            this.playbackState = playerInfo.playbackState;
            this.mediaMetadata = playerInfo.mediaMetadata;
            this.seekBackIncrementMs = playerInfo.seekBackIncrementMs;
            this.seekForwardIncrementMs = playerInfo.seekForwardIncrementMs;
            this.maxSeekToPreviousPositionMs = playerInfo.maxSeekToPreviousPositionMs;
            this.currentTracks = playerInfo.currentTracks;
            this.trackSelectionParameters = playerInfo.trackSelectionParameters;
        }

        public PlayerInfo build() {
            Assertions.checkState(this.timeline.isEmpty() || this.sessionPositionInfo.positionInfo.mediaItemIndex < this.timeline.getWindowCount());
            return new PlayerInfo(this.playerError, this.mediaItemTransitionReason, this.sessionPositionInfo, this.oldPositionInfo, this.newPositionInfo, this.discontinuityReason, this.playbackParameters, this.repeatMode, this.shuffleModeEnabled, this.videoSize, this.timeline, this.timelineChangeReason, this.playlistMetadata, this.volume, this.audioAttributes, this.cueGroup, this.deviceInfo, this.deviceVolume, this.deviceMuted, this.playWhenReady, this.playWhenReadyChangeReason, this.playbackSuppressionReason, this.playbackState, this.isPlaying, this.isLoading, this.mediaMetadata, this.seekBackIncrementMs, this.seekForwardIncrementMs, this.maxSeekToPreviousPositionMs, this.currentTracks, this.trackSelectionParameters);
        }

        public Builder setCurrentTracks(Tracks tracks) {
            this.currentTracks = tracks;
            return this;
        }

        public Builder setDiscontinuityReason(int i) {
            this.discontinuityReason = i;
            return this;
        }

        public Builder setIsPlaying(boolean z) {
            this.isPlaying = z;
            return this;
        }

        public Builder setNewPositionInfo(Player.PositionInfo positionInfo) {
            this.newPositionInfo = positionInfo;
            return this;
        }

        public Builder setOldPositionInfo(Player.PositionInfo positionInfo) {
            this.oldPositionInfo = positionInfo;
            return this;
        }

        public Builder setPlayWhenReady(boolean z) {
            this.playWhenReady = z;
            return this;
        }

        public Builder setPlayWhenReadyChangeReason(int i) {
            this.playWhenReadyChangeReason = i;
            return this;
        }

        public Builder setPlaybackState(int i) {
            this.playbackState = i;
            return this;
        }

        public Builder setPlaybackSuppressionReason(int i) {
            this.playbackSuppressionReason = i;
            return this;
        }

        public Builder setPlayerError(PlaybackException playbackException) {
            this.playerError = playbackException;
            return this;
        }

        public Builder setSessionPositionInfo(SessionPositionInfo sessionPositionInfo) {
            this.sessionPositionInfo = sessionPositionInfo;
            return this;
        }

        public Builder setTimeline(Timeline timeline) {
            this.timeline = timeline;
            return this;
        }
    }

    public class BundlingExclusions {
        public final boolean areCurrentTracksExcluded;
        public final boolean isTimelineExcluded;
        public static final BundlingExclusions NONE = new BundlingExclusions(false, false);
        private static final String FIELD_IS_TIMELINE_EXCLUDED = Util.intToStringMaxRadix(0);
        private static final String FIELD_ARE_CURRENT_TRACKS_EXCLUDED = Util.intToStringMaxRadix(1);

        public BundlingExclusions(boolean z, boolean z2) {
            this.isTimelineExcluded = z;
            this.areCurrentTracksExcluded = z2;
        }

        public static BundlingExclusions fromBundle(Bundle bundle) {
            return new BundlingExclusions(bundle.getBoolean(FIELD_IS_TIMELINE_EXCLUDED, false), bundle.getBoolean(FIELD_ARE_CURRENT_TRACKS_EXCLUDED, false));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BundlingExclusions)) {
                return false;
            }
            BundlingExclusions bundlingExclusions = (BundlingExclusions) obj;
            return this.isTimelineExcluded == bundlingExclusions.isTimelineExcluded && this.areCurrentTracksExcluded == bundlingExclusions.areCurrentTracksExcluded;
        }

        public int hashCode() {
            return Objects.hashCode(Boolean.valueOf(this.isTimelineExcluded), Boolean.valueOf(this.areCurrentTracksExcluded));
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putBoolean(FIELD_IS_TIMELINE_EXCLUDED, this.isTimelineExcluded);
            bundle.putBoolean(FIELD_ARE_CURRENT_TRACKS_EXCLUDED, this.areCurrentTracksExcluded);
            return bundle;
        }
    }

    static {
        SessionPositionInfo sessionPositionInfo = SessionPositionInfo.DEFAULT;
        Player.PositionInfo positionInfo = SessionPositionInfo.DEFAULT_POSITION_INFO;
        PlaybackParameters playbackParameters = PlaybackParameters.DEFAULT;
        VideoSize videoSize = VideoSize.UNKNOWN;
        Timeline timeline = Timeline.EMPTY;
        MediaMetadata mediaMetadata = MediaMetadata.EMPTY;
        DEFAULT = new PlayerInfo(null, 0, sessionPositionInfo, positionInfo, positionInfo, 0, playbackParameters, 0, false, videoSize, timeline, 0, mediaMetadata, 1.0f, AudioAttributes.DEFAULT, CueGroup.EMPTY_TIME_ZERO, DeviceInfo.UNKNOWN, 0, false, false, 1, 0, 1, false, false, mediaMetadata, 5000L, 15000L, 3000L, Tracks.EMPTY, TrackSelectionParameters.DEFAULT_WITHOUT_CONTEXT);
        FIELD_PLAYBACK_PARAMETERS = Util.intToStringMaxRadix(1);
        FIELD_REPEAT_MODE = Util.intToStringMaxRadix(2);
        FIELD_SHUFFLE_MODE_ENABLED = Util.intToStringMaxRadix(3);
        FIELD_TIMELINE = Util.intToStringMaxRadix(4);
        FIELD_VIDEO_SIZE = Util.intToStringMaxRadix(5);
        FIELD_PLAYLIST_METADATA = Util.intToStringMaxRadix(6);
        FIELD_VOLUME = Util.intToStringMaxRadix(7);
        FIELD_AUDIO_ATTRIBUTES = Util.intToStringMaxRadix(8);
        FIELD_DEVICE_INFO = Util.intToStringMaxRadix(9);
        FIELD_DEVICE_VOLUME = Util.intToStringMaxRadix(10);
        FIELD_DEVICE_MUTED = Util.intToStringMaxRadix(11);
        FIELD_PLAY_WHEN_READY = Util.intToStringMaxRadix(12);
        FIELD_PLAY_WHEN_READY_CHANGE_REASON = Util.intToStringMaxRadix(13);
        FIELD_PLAYBACK_SUPPRESSION_REASON = Util.intToStringMaxRadix(14);
        FIELD_PLAYBACK_STATE = Util.intToStringMaxRadix(15);
        FIELD_IS_PLAYING = Util.intToStringMaxRadix(16);
        FIELD_IS_LOADING = Util.intToStringMaxRadix(17);
        FIELD_PLAYBACK_ERROR = Util.intToStringMaxRadix(18);
        FIELD_SESSION_POSITION_INFO = Util.intToStringMaxRadix(19);
        FIELD_MEDIA_ITEM_TRANSITION_REASON = Util.intToStringMaxRadix(20);
        FIELD_OLD_POSITION_INFO = Util.intToStringMaxRadix(21);
        FIELD_NEW_POSITION_INFO = Util.intToStringMaxRadix(22);
        FIELD_DISCONTINUITY_REASON = Util.intToStringMaxRadix(23);
        FIELD_CUE_GROUP = Util.intToStringMaxRadix(24);
        FIELD_MEDIA_METADATA = Util.intToStringMaxRadix(25);
        FIELD_SEEK_BACK_INCREMENT_MS = Util.intToStringMaxRadix(26);
        FIELD_SEEK_FORWARD_INCREMENT_MS = Util.intToStringMaxRadix(27);
        FIELD_MAX_SEEK_TO_PREVIOUS_POSITION_MS = Util.intToStringMaxRadix(28);
        FIELD_TRACK_SELECTION_PARAMETERS = Util.intToStringMaxRadix(29);
        FIELD_CURRENT_TRACKS = Util.intToStringMaxRadix(30);
        FIELD_TIMELINE_CHANGE_REASON = Util.intToStringMaxRadix(31);
        FIELD_IN_PROCESS_BINDER = Util.intToStringMaxRadix(32);
    }

    public PlayerInfo(PlaybackException playbackException, int i, SessionPositionInfo sessionPositionInfo, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2, PlaybackParameters playbackParameters, int i3, boolean z, VideoSize videoSize, Timeline timeline, int i4, MediaMetadata mediaMetadata, float f, AudioAttributes audioAttributes, CueGroup cueGroup, DeviceInfo deviceInfo, int i5, boolean z2, boolean z3, int i6, int i7, int i8, boolean z4, boolean z5, MediaMetadata mediaMetadata2, long j, long j2, long j3, Tracks tracks, TrackSelectionParameters trackSelectionParameters) {
        this.playerError = playbackException;
        this.mediaItemTransitionReason = i;
        this.sessionPositionInfo = sessionPositionInfo;
        this.oldPositionInfo = positionInfo;
        this.newPositionInfo = positionInfo2;
        this.discontinuityReason = i2;
        this.playbackParameters = playbackParameters;
        this.repeatMode = i3;
        this.shuffleModeEnabled = z;
        this.videoSize = videoSize;
        this.timeline = timeline;
        this.timelineChangeReason = i4;
        this.playlistMetadata = mediaMetadata;
        this.volume = f;
        this.audioAttributes = audioAttributes;
        this.cueGroup = cueGroup;
        this.deviceInfo = deviceInfo;
        this.deviceVolume = i5;
        this.deviceMuted = z2;
        this.playWhenReady = z3;
        this.playWhenReadyChangeReason = i6;
        this.playbackSuppressionReason = i7;
        this.playbackState = i8;
        this.isPlaying = z4;
        this.isLoading = z5;
        this.mediaMetadata = mediaMetadata2;
        this.seekBackIncrementMs = j;
        this.seekForwardIncrementMs = j2;
        this.maxSeekToPreviousPositionMs = j3;
        this.currentTracks = tracks;
        this.trackSelectionParameters = trackSelectionParameters;
    }

    public static PlayerInfo fromBundle(Bundle bundle, int i) {
        PlaybackException playbackException;
        long j;
        bundle.getBinder(FIELD_IN_PROCESS_BINDER);
        Bundle bundle2 = bundle.getBundle(FIELD_PLAYBACK_ERROR);
        PlaybackException playbackExceptionFromBundle = bundle2 == null ? null : PlaybackException.fromBundle(bundle2);
        int i2 = bundle.getInt(FIELD_MEDIA_ITEM_TRANSITION_REASON, 0);
        Bundle bundle3 = bundle.getBundle(FIELD_SESSION_POSITION_INFO);
        SessionPositionInfo sessionPositionInfoFromBundle = bundle3 == null ? SessionPositionInfo.DEFAULT : SessionPositionInfo.fromBundle(bundle3);
        Bundle bundle4 = bundle.getBundle(FIELD_OLD_POSITION_INFO);
        Player.PositionInfo positionInfoFromBundle = bundle4 == null ? SessionPositionInfo.DEFAULT_POSITION_INFO : Player.PositionInfo.fromBundle(bundle4);
        Bundle bundle5 = bundle.getBundle(FIELD_NEW_POSITION_INFO);
        Player.PositionInfo positionInfoFromBundle2 = bundle5 == null ? SessionPositionInfo.DEFAULT_POSITION_INFO : Player.PositionInfo.fromBundle(bundle5);
        int i3 = bundle.getInt(FIELD_DISCONTINUITY_REASON, 0);
        Bundle bundle6 = bundle.getBundle(FIELD_PLAYBACK_PARAMETERS);
        PlaybackParameters playbackParametersFromBundle = bundle6 == null ? PlaybackParameters.DEFAULT : PlaybackParameters.fromBundle(bundle6);
        int i4 = bundle.getInt(FIELD_REPEAT_MODE, 0);
        boolean z = bundle.getBoolean(FIELD_SHUFFLE_MODE_ENABLED, false);
        Bundle bundle7 = bundle.getBundle(FIELD_TIMELINE);
        Timeline timelineFromBundle = bundle7 == null ? Timeline.EMPTY : Timeline.fromBundle(bundle7);
        int i5 = bundle.getInt(FIELD_TIMELINE_CHANGE_REASON, 0);
        Bundle bundle8 = bundle.getBundle(FIELD_VIDEO_SIZE);
        VideoSize videoSizeFromBundle = bundle8 == null ? VideoSize.UNKNOWN : VideoSize.fromBundle(bundle8);
        Bundle bundle9 = bundle.getBundle(FIELD_PLAYLIST_METADATA);
        MediaMetadata mediaMetadataFromBundle = bundle9 == null ? MediaMetadata.EMPTY : MediaMetadata.fromBundle(bundle9);
        float f = bundle.getFloat(FIELD_VOLUME, 1.0f);
        Bundle bundle10 = bundle.getBundle(FIELD_AUDIO_ATTRIBUTES);
        AudioAttributes audioAttributesFromBundle = bundle10 == null ? AudioAttributes.DEFAULT : AudioAttributes.fromBundle(bundle10);
        Bundle bundle11 = bundle.getBundle(FIELD_CUE_GROUP);
        CueGroup cueGroupFromBundle = bundle11 == null ? CueGroup.EMPTY_TIME_ZERO : CueGroup.fromBundle(bundle11);
        Bundle bundle12 = bundle.getBundle(FIELD_DEVICE_INFO);
        DeviceInfo deviceInfoFromBundle = bundle12 == null ? DeviceInfo.UNKNOWN : DeviceInfo.fromBundle(bundle12);
        int i6 = bundle.getInt(FIELD_DEVICE_VOLUME, 0);
        boolean z2 = bundle.getBoolean(FIELD_DEVICE_MUTED, false);
        boolean z3 = bundle.getBoolean(FIELD_PLAY_WHEN_READY, false);
        int i7 = bundle.getInt(FIELD_PLAY_WHEN_READY_CHANGE_REASON, 1);
        int i8 = bundle.getInt(FIELD_PLAYBACK_SUPPRESSION_REASON, 0);
        int i9 = bundle.getInt(FIELD_PLAYBACK_STATE, 1);
        boolean z4 = bundle.getBoolean(FIELD_IS_PLAYING, false);
        boolean z5 = bundle.getBoolean(FIELD_IS_LOADING, false);
        Bundle bundle13 = bundle.getBundle(FIELD_MEDIA_METADATA);
        MediaMetadata mediaMetadataFromBundle2 = bundle13 == null ? MediaMetadata.EMPTY : MediaMetadata.fromBundle(bundle13);
        String str = FIELD_SEEK_BACK_INCREMENT_MS;
        if (i < 4) {
            playbackException = playbackExceptionFromBundle;
            j = 0;
        } else {
            playbackException = playbackExceptionFromBundle;
            j = 5000;
        }
        long j2 = bundle.getLong(str, j);
        long j3 = bundle.getLong(FIELD_SEEK_FORWARD_INCREMENT_MS, i < 4 ? 0L : 15000L);
        long j4 = bundle.getLong(FIELD_MAX_SEEK_TO_PREVIOUS_POSITION_MS, i >= 4 ? 3000L : 0L);
        Bundle bundle14 = bundle.getBundle(FIELD_CURRENT_TRACKS);
        Tracks tracksFromBundle = bundle14 == null ? Tracks.EMPTY : Tracks.fromBundle(bundle14);
        Bundle bundle15 = bundle.getBundle(FIELD_TRACK_SELECTION_PARAMETERS);
        return new PlayerInfo(playbackException, i2, sessionPositionInfoFromBundle, positionInfoFromBundle, positionInfoFromBundle2, i3, playbackParametersFromBundle, i4, z, videoSizeFromBundle, timelineFromBundle, i5, mediaMetadataFromBundle, f, audioAttributesFromBundle, cueGroupFromBundle, deviceInfoFromBundle, i6, z2, z3, i7, i8, i9, z4, z5, mediaMetadataFromBundle2, j2, j3, j4, tracksFromBundle, bundle15 == null ? TrackSelectionParameters.DEFAULT_WITHOUT_CONTEXT : TrackSelectionParameters.fromBundle(bundle15));
    }

    private boolean isPlaying(int i, boolean z, int i2) {
        return i == 3 && z && i2 == 0;
    }

    public PlayerInfo copyWithCurrentTracks(Tracks tracks) {
        return new Builder(this).setCurrentTracks(tracks).build();
    }

    public PlayerInfo copyWithPlayWhenReady(boolean z, int i, int i2) {
        return new Builder(this).setPlayWhenReady(z).setPlayWhenReadyChangeReason(i).setPlaybackSuppressionReason(i2).setIsPlaying(isPlaying(this.playbackState, z, i2)).build();
    }

    public PlayerInfo copyWithPlaybackState(int i, PlaybackException playbackException) {
        return new Builder(this).setPlayerError(playbackException).setPlaybackState(i).setIsPlaying(isPlaying(i, this.playWhenReady, this.playbackSuppressionReason)).build();
    }

    public PlayerInfo copyWithPositionInfos(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
        return new Builder(this).setOldPositionInfo(positionInfo).setNewPositionInfo(positionInfo2).setDiscontinuityReason(i).build();
    }

    public PlayerInfo copyWithSessionPositionInfo(SessionPositionInfo sessionPositionInfo) {
        return new Builder(this).setSessionPositionInfo(sessionPositionInfo).build();
    }

    public PlayerInfo copyWithTimeline(Timeline timeline) {
        return new Builder(this).setTimeline(timeline).build();
    }

    public MediaItem getCurrentMediaItem() {
        if (this.timeline.isEmpty()) {
            return null;
        }
        return this.timeline.getWindow(this.sessionPositionInfo.positionInfo.mediaItemIndex, new Timeline.Window()).mediaItem;
    }
}
