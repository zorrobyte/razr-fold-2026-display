package androidx.media3.session;

import android.content.Context;
import android.media.session.MediaController;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Pair;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.DeviceInfo;
import androidx.media3.common.FlagSet;
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
import androidx.media3.common.util.BitmapLoader;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.Consumer;
import androidx.media3.common.util.ListenerSet;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.Util;
import androidx.media3.session.LegacyConversions;
import androidx.media3.session.MediaController;
import androidx.media3.session.MediaControllerImplLegacy;
import androidx.media3.session.legacy.MediaBrowserCompat;
import androidx.media3.session.legacy.MediaControllerCompat;
import androidx.media3.session.legacy.MediaMetadataCompat;
import androidx.media3.session.legacy.MediaSessionCompat;
import androidx.media3.session.legacy.PlaybackStateCompat;
import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
class MediaControllerImplLegacy implements MediaController.MediaControllerImpl {
    private final BitmapLoader bitmapLoader;
    private MediaBrowserCompat browserCompat;
    private boolean connected;
    private final Bundle connectionHints;
    final Context context;
    private MediaControllerCompat controllerCompat;
    private final ControllerCompatCallback controllerCompatCallback;
    private boolean hasPendingExtrasChange;
    private final MediaController instance;
    private final ListenerSet listeners;
    private boolean released;
    private final SessionToken token;
    private LegacyPlayerInfo legacyPlayerInfo = new LegacyPlayerInfo();
    private LegacyPlayerInfo pendingLegacyPlayerInfo = new LegacyPlayerInfo();
    private ControllerInfo controllerInfo = new ControllerInfo();
    private long currentPositionMs = -9223372036854775807L;
    private long lastSetPlayWhenReadyCalledTimeMs = -9223372036854775807L;
    private final ImmutableList commandButtonsForMediaItems = ImmutableList.of();

    /* JADX INFO: renamed from: androidx.media3.session.MediaControllerImplLegacy$1, reason: invalid class name */
    abstract class AnonymousClass1 extends ResultReceiver {
    }

    class ConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
        private ConnectionCallback() {
        }

        /* synthetic */ ConnectionCallback(MediaControllerImplLegacy mediaControllerImplLegacy, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // androidx.media3.session.legacy.MediaBrowserCompat.ConnectionCallback
        public void onConnected() {
            MediaBrowserCompat browserCompat = MediaControllerImplLegacy.this.getBrowserCompat();
            if (browserCompat != null) {
                MediaControllerImplLegacy.this.connectToSession(browserCompat.getSessionToken());
            }
        }

        @Override // androidx.media3.session.legacy.MediaBrowserCompat.ConnectionCallback
        public void onConnectionFailed() {
            MediaControllerImplLegacy.this.getInstance().release();
        }

        @Override // androidx.media3.session.legacy.MediaBrowserCompat.ConnectionCallback
        public void onConnectionSuspended() {
            MediaControllerImplLegacy.this.getInstance().release();
        }
    }

    final class ControllerCompatCallback extends MediaControllerCompat.Callback {
        private final Handler pendingChangesHandler;

        public static /* synthetic */ boolean $r8$lambda$MKdHaWcKsTwxSUnwXlWo1Izjol0(ControllerCompatCallback controllerCompatCallback, Message message) {
            controllerCompatCallback.getClass();
            if (message.what == 1) {
                MediaControllerImplLegacy mediaControllerImplLegacy = MediaControllerImplLegacy.this;
                mediaControllerImplLegacy.handleNewLegacyParameters(false, mediaControllerImplLegacy.pendingLegacyPlayerInfo);
            }
            return true;
        }

        public static /* synthetic */ void $r8$lambda$RvV0VD2XUHZOkh1nFIJyudxBnyU(ControllerCompatCallback controllerCompatCallback, String str, Bundle bundle, MediaController.Listener listener) {
            MediaController mediaControllerImplLegacy = MediaControllerImplLegacy.this.getInstance();
            Bundle bundle2 = Bundle.EMPTY;
            SessionCommand sessionCommand = new SessionCommand(str, bundle2);
            if (bundle == null) {
                bundle = bundle2;
            }
            MediaControllerImplLegacy.ignoreFuture(listener.onCustomCommand(mediaControllerImplLegacy, sessionCommand, bundle));
        }

        public static /* synthetic */ void $r8$lambda$kJjl2I8bYyNHIUlRfb0t4U6_olU(ControllerCompatCallback controllerCompatCallback, boolean z, MediaController.Listener listener) {
            controllerCompatCallback.getClass();
            Bundle bundle = new Bundle();
            bundle.putBoolean("androidx.media3.session.ARGUMENT_CAPTIONING_ENABLED", z);
            MediaControllerImplLegacy.ignoreFuture(listener.onCustomCommand(MediaControllerImplLegacy.this.getInstance(), new SessionCommand("androidx.media3.session.SESSION_COMMAND_ON_CAPTIONING_ENABLED_CHANGED", Bundle.EMPTY), bundle));
        }

        public ControllerCompatCallback(Looper looper) {
            this.pendingChangesHandler = new Handler(looper, new Handler.Callback() { // from class: androidx.media3.session.MediaControllerImplLegacy$ControllerCompatCallback$$ExternalSyntheticLambda1
                @Override // android.os.Handler.Callback
                public final boolean handleMessage(Message message) {
                    return MediaControllerImplLegacy.ControllerCompatCallback.$r8$lambda$MKdHaWcKsTwxSUnwXlWo1Izjol0(this.f$0, message);
                }
            });
        }

        private void startWaitingForPendingChanges() {
            if (this.pendingChangesHandler.hasMessages(1)) {
                return;
            }
            this.pendingChangesHandler.sendEmptyMessageDelayed(1, 500L);
        }

        @Override // androidx.media3.session.legacy.MediaControllerCompat.Callback
        public void onAudioInfoChanged(MediaControllerCompat.PlaybackInfo playbackInfo) {
            MediaControllerImplLegacy mediaControllerImplLegacy = MediaControllerImplLegacy.this;
            mediaControllerImplLegacy.pendingLegacyPlayerInfo = mediaControllerImplLegacy.pendingLegacyPlayerInfo.copyWithPlaybackInfoCompat(playbackInfo);
            startWaitingForPendingChanges();
        }

        @Override // androidx.media3.session.legacy.MediaControllerCompat.Callback
        public void onCaptioningEnabledChanged(final boolean z) {
            MediaControllerImplLegacy.this.getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplLegacy$ControllerCompatCallback$$ExternalSyntheticLambda0
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    MediaControllerImplLegacy.ControllerCompatCallback.$r8$lambda$kJjl2I8bYyNHIUlRfb0t4U6_olU(this.f$0, z, (MediaController.Listener) obj);
                }
            });
        }

        @Override // androidx.media3.session.legacy.MediaControllerCompat.Callback
        public void onExtrasChanged(Bundle bundle) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            MediaControllerImplLegacy mediaControllerImplLegacy = MediaControllerImplLegacy.this;
            mediaControllerImplLegacy.pendingLegacyPlayerInfo = mediaControllerImplLegacy.pendingLegacyPlayerInfo.copyWithSessionExtras(bundle);
            MediaControllerImplLegacy.this.hasPendingExtrasChange = true;
            startWaitingForPendingChanges();
        }

        @Override // androidx.media3.session.legacy.MediaControllerCompat.Callback
        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
            MediaControllerImplLegacy mediaControllerImplLegacy = MediaControllerImplLegacy.this;
            mediaControllerImplLegacy.pendingLegacyPlayerInfo = mediaControllerImplLegacy.pendingLegacyPlayerInfo.copyWithMediaMetadataCompat(mediaMetadataCompat);
            startWaitingForPendingChanges();
        }

        @Override // androidx.media3.session.legacy.MediaControllerCompat.Callback
        public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
            MediaControllerImplLegacy mediaControllerImplLegacy = MediaControllerImplLegacy.this;
            mediaControllerImplLegacy.pendingLegacyPlayerInfo = mediaControllerImplLegacy.pendingLegacyPlayerInfo.copyWithPlaybackStateCompat(MediaControllerImplLegacy.convertToSafePlaybackStateCompat(playbackStateCompat));
            startWaitingForPendingChanges();
        }

        @Override // androidx.media3.session.legacy.MediaControllerCompat.Callback
        public void onQueueChanged(List list) {
            MediaControllerImplLegacy mediaControllerImplLegacy = MediaControllerImplLegacy.this;
            mediaControllerImplLegacy.pendingLegacyPlayerInfo = mediaControllerImplLegacy.pendingLegacyPlayerInfo.copyWithQueue(MediaControllerImplLegacy.convertToNonNullQueueItemList(list));
            startWaitingForPendingChanges();
        }

        @Override // androidx.media3.session.legacy.MediaControllerCompat.Callback
        public void onQueueTitleChanged(CharSequence charSequence) {
            MediaControllerImplLegacy mediaControllerImplLegacy = MediaControllerImplLegacy.this;
            mediaControllerImplLegacy.pendingLegacyPlayerInfo = mediaControllerImplLegacy.pendingLegacyPlayerInfo.copyWithQueueTitle(charSequence);
            startWaitingForPendingChanges();
        }

        @Override // androidx.media3.session.legacy.MediaControllerCompat.Callback
        public void onRepeatModeChanged(int i) {
            MediaControllerImplLegacy mediaControllerImplLegacy = MediaControllerImplLegacy.this;
            mediaControllerImplLegacy.pendingLegacyPlayerInfo = mediaControllerImplLegacy.pendingLegacyPlayerInfo.copyWithRepeatMode(i);
            startWaitingForPendingChanges();
        }

        @Override // androidx.media3.session.legacy.MediaControllerCompat.Callback
        public void onSessionDestroyed() {
            MediaControllerImplLegacy.this.getInstance().release();
        }

        @Override // androidx.media3.session.legacy.MediaControllerCompat.Callback
        public void onSessionEvent(final String str, final Bundle bundle) {
            if (str == null) {
                return;
            }
            MediaControllerImplLegacy.this.getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplLegacy$ControllerCompatCallback$$ExternalSyntheticLambda2
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    MediaControllerImplLegacy.ControllerCompatCallback.$r8$lambda$RvV0VD2XUHZOkh1nFIJyudxBnyU(this.f$0, str, bundle, (MediaController.Listener) obj);
                }
            });
        }

        @Override // androidx.media3.session.legacy.MediaControllerCompat.Callback
        public void onSessionReady() {
            if (!MediaControllerImplLegacy.this.connected) {
                MediaControllerImplLegacy.this.onConnected();
                return;
            }
            MediaControllerImplLegacy mediaControllerImplLegacy = MediaControllerImplLegacy.this;
            mediaControllerImplLegacy.pendingLegacyPlayerInfo = mediaControllerImplLegacy.pendingLegacyPlayerInfo.copyWithExtraBinderGetters(MediaControllerImplLegacy.convertToSafePlaybackStateCompat(MediaControllerImplLegacy.this.controllerCompat.getPlaybackState()), MediaControllerImplLegacy.this.controllerCompat.getRepeatMode(), MediaControllerImplLegacy.this.controllerCompat.getShuffleMode());
            onCaptioningEnabledChanged(MediaControllerImplLegacy.this.controllerCompat.isCaptioningEnabled());
            this.pendingChangesHandler.removeMessages(1);
            MediaControllerImplLegacy mediaControllerImplLegacy2 = MediaControllerImplLegacy.this;
            mediaControllerImplLegacy2.handleNewLegacyParameters(false, mediaControllerImplLegacy2.pendingLegacyPlayerInfo);
        }

        @Override // androidx.media3.session.legacy.MediaControllerCompat.Callback
        public void onShuffleModeChanged(int i) {
            MediaControllerImplLegacy mediaControllerImplLegacy = MediaControllerImplLegacy.this;
            mediaControllerImplLegacy.pendingLegacyPlayerInfo = mediaControllerImplLegacy.pendingLegacyPlayerInfo.copyWithShuffleMode(i);
            startWaitingForPendingChanges();
        }

        public void release() {
            this.pendingChangesHandler.removeCallbacksAndMessages(null);
        }
    }

    class ControllerInfo {
        public final Player.Commands availablePlayerCommands;
        public final SessionCommands availableSessionCommands;
        public final ImmutableList mediaButtonPreferences;
        public final PlayerInfo playerInfo;
        public final SessionError sessionError;
        public final Bundle sessionExtras;

        public ControllerInfo() {
            this.playerInfo = PlayerInfo.DEFAULT.copyWithTimeline(QueueTimeline.DEFAULT);
            this.availableSessionCommands = SessionCommands.EMPTY;
            this.availablePlayerCommands = Player.Commands.EMPTY;
            this.mediaButtonPreferences = ImmutableList.of();
            this.sessionExtras = Bundle.EMPTY;
            this.sessionError = null;
        }

        public ControllerInfo(PlayerInfo playerInfo, SessionCommands sessionCommands, Player.Commands commands, ImmutableList immutableList, Bundle bundle, SessionError sessionError) {
            this.playerInfo = playerInfo;
            this.availableSessionCommands = sessionCommands;
            this.availablePlayerCommands = commands;
            this.mediaButtonPreferences = immutableList;
            this.sessionExtras = bundle == null ? Bundle.EMPTY : bundle;
            this.sessionError = sessionError;
        }
    }

    final class LegacyPlayerInfo {
        public final MediaMetadataCompat mediaMetadataCompat;
        public final MediaControllerCompat.PlaybackInfo playbackInfoCompat;
        public final PlaybackStateCompat playbackStateCompat;
        public final List queue;
        public final CharSequence queueTitle;
        public final int repeatMode;
        public final Bundle sessionExtras;
        public final int shuffleMode;

        public LegacyPlayerInfo() {
            this.playbackInfoCompat = null;
            this.playbackStateCompat = null;
            this.mediaMetadataCompat = null;
            this.queue = Collections.EMPTY_LIST;
            this.queueTitle = null;
            this.repeatMode = 0;
            this.shuffleMode = 0;
            this.sessionExtras = Bundle.EMPTY;
        }

        public LegacyPlayerInfo(LegacyPlayerInfo legacyPlayerInfo) {
            this.playbackInfoCompat = legacyPlayerInfo.playbackInfoCompat;
            this.playbackStateCompat = legacyPlayerInfo.playbackStateCompat;
            this.mediaMetadataCompat = legacyPlayerInfo.mediaMetadataCompat;
            this.queue = legacyPlayerInfo.queue;
            this.queueTitle = legacyPlayerInfo.queueTitle;
            this.repeatMode = legacyPlayerInfo.repeatMode;
            this.shuffleMode = legacyPlayerInfo.shuffleMode;
            this.sessionExtras = legacyPlayerInfo.sessionExtras;
        }

        public LegacyPlayerInfo(MediaControllerCompat.PlaybackInfo playbackInfo, PlaybackStateCompat playbackStateCompat, MediaMetadataCompat mediaMetadataCompat, List list, CharSequence charSequence, int i, int i2, Bundle bundle) {
            this.playbackInfoCompat = playbackInfo;
            this.playbackStateCompat = playbackStateCompat;
            this.mediaMetadataCompat = mediaMetadataCompat;
            this.queue = (List) Assertions.checkNotNull(list);
            this.queueTitle = charSequence;
            this.repeatMode = i;
            this.shuffleMode = i2;
            this.sessionExtras = bundle == null ? Bundle.EMPTY : bundle;
        }

        public LegacyPlayerInfo copyWithExtraBinderGetters(PlaybackStateCompat playbackStateCompat, int i, int i2) {
            return new LegacyPlayerInfo(this.playbackInfoCompat, playbackStateCompat, this.mediaMetadataCompat, this.queue, this.queueTitle, i, i2, this.sessionExtras);
        }

        public LegacyPlayerInfo copyWithMediaMetadataCompat(MediaMetadataCompat mediaMetadataCompat) {
            return new LegacyPlayerInfo(this.playbackInfoCompat, this.playbackStateCompat, mediaMetadataCompat, this.queue, this.queueTitle, this.repeatMode, this.shuffleMode, this.sessionExtras);
        }

        public LegacyPlayerInfo copyWithPlaybackInfoCompat(MediaControllerCompat.PlaybackInfo playbackInfo) {
            return new LegacyPlayerInfo(playbackInfo, this.playbackStateCompat, this.mediaMetadataCompat, this.queue, this.queueTitle, this.repeatMode, this.shuffleMode, this.sessionExtras);
        }

        public LegacyPlayerInfo copyWithPlaybackStateCompat(PlaybackStateCompat playbackStateCompat) {
            return new LegacyPlayerInfo(this.playbackInfoCompat, playbackStateCompat, this.mediaMetadataCompat, this.queue, this.queueTitle, this.repeatMode, this.shuffleMode, this.sessionExtras);
        }

        public LegacyPlayerInfo copyWithQueue(List list) {
            return new LegacyPlayerInfo(this.playbackInfoCompat, this.playbackStateCompat, this.mediaMetadataCompat, list, this.queueTitle, this.repeatMode, this.shuffleMode, this.sessionExtras);
        }

        public LegacyPlayerInfo copyWithQueueTitle(CharSequence charSequence) {
            return new LegacyPlayerInfo(this.playbackInfoCompat, this.playbackStateCompat, this.mediaMetadataCompat, this.queue, charSequence, this.repeatMode, this.shuffleMode, this.sessionExtras);
        }

        public LegacyPlayerInfo copyWithRepeatMode(int i) {
            return new LegacyPlayerInfo(this.playbackInfoCompat, this.playbackStateCompat, this.mediaMetadataCompat, this.queue, this.queueTitle, i, this.shuffleMode, this.sessionExtras);
        }

        public LegacyPlayerInfo copyWithSessionExtras(Bundle bundle) {
            return new LegacyPlayerInfo(this.playbackInfoCompat, this.playbackStateCompat, this.mediaMetadataCompat, this.queue, this.queueTitle, this.repeatMode, this.shuffleMode, bundle);
        }

        public LegacyPlayerInfo copyWithShuffleMode(int i) {
            return new LegacyPlayerInfo(this.playbackInfoCompat, this.playbackStateCompat, this.mediaMetadataCompat, this.queue, this.queueTitle, this.repeatMode, i, this.sessionExtras);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$52StrBIOqSZpQ7D-vS_e6wcs1Qs, reason: not valid java name */
    public static /* synthetic */ void m2019$r8$lambda$52StrBIOqSZpQ7DvS_e6wcs1Qs(MediaControllerImplLegacy mediaControllerImplLegacy) {
        mediaControllerImplLegacy.getClass();
        MediaBrowserCompat mediaBrowserCompat = new MediaBrowserCompat(mediaControllerImplLegacy.context, mediaControllerImplLegacy.token.getComponentName(), new ConnectionCallback(mediaControllerImplLegacy, null), mediaControllerImplLegacy.instance.getConnectionHints());
        mediaControllerImplLegacy.browserCompat = mediaBrowserCompat;
        mediaBrowserCompat.connect();
    }

    public static /* synthetic */ void $r8$lambda$6y6sNOafeXrBS6WhicpQusmFhiY(MediaControllerImplLegacy mediaControllerImplLegacy, MediaSessionCompat.Token token) {
        MediaControllerCompat mediaControllerCompat = new MediaControllerCompat(mediaControllerImplLegacy.context, token);
        mediaControllerImplLegacy.controllerCompat = mediaControllerCompat;
        mediaControllerCompat.registerCallback(mediaControllerImplLegacy.controllerCompatCallback, mediaControllerImplLegacy.getInstance().applicationHandler);
    }

    /* JADX INFO: renamed from: $r8$lambda$C0_2IPf_zzLCkK-giCeYFjRaxPQ, reason: not valid java name */
    public static /* synthetic */ void m2021$r8$lambda$C0_2IPf_zzLCkKgiCeYFjRaxPQ(ControllerInfo controllerInfo, Player.Listener listener) {
        PlayerInfo playerInfo = controllerInfo.playerInfo;
        listener.onDeviceVolumeChanged(playerInfo.deviceVolume, playerInfo.deviceMuted);
    }

    public static /* synthetic */ void $r8$lambda$MaaQ0IusK9godPxSltfKjLAZdVU(MediaControllerImplLegacy mediaControllerImplLegacy) {
        if (mediaControllerImplLegacy.controllerCompat.isSessionReady()) {
            return;
        }
        mediaControllerImplLegacy.onConnected();
    }

    /* JADX INFO: renamed from: $r8$lambda$P-IhqOQa6oi1gv7bqAxJVytQ7GM, reason: not valid java name */
    public static /* synthetic */ void m2022$r8$lambda$PIhqOQa6oi1gv7bqAxJVytQ7GM(MediaControllerImplLegacy mediaControllerImplLegacy, ControllerInfo controllerInfo, MediaController.Listener listener) {
        ignoreFuture(listener.onSetCustomLayout(mediaControllerImplLegacy.getInstance(), controllerInfo.mediaButtonPreferences));
        listener.onCustomLayoutChanged(mediaControllerImplLegacy.getInstance(), controllerInfo.mediaButtonPreferences);
        listener.onMediaButtonPreferencesChanged(mediaControllerImplLegacy.getInstance(), controllerInfo.mediaButtonPreferences);
    }

    public static /* synthetic */ void $r8$lambda$bF_3oWXvnJY21hy47z1bsCIA8r0(ControllerInfo controllerInfo, Player.Listener listener) {
        PlayerInfo playerInfo = controllerInfo.playerInfo;
        listener.onTimelineChanged(playerInfo.timeline, playerInfo.timelineChangeReason);
    }

    public static /* synthetic */ void $r8$lambda$fapNGe03LjIMswMZTd9DJaHrdmw(MediaControllerImplLegacy mediaControllerImplLegacy, ControllerInfo controllerInfo, MediaController.Listener listener) {
        ignoreFuture(listener.onSetCustomLayout(mediaControllerImplLegacy.getInstance(), controllerInfo.mediaButtonPreferences));
        listener.onCustomLayoutChanged(mediaControllerImplLegacy.getInstance(), controllerInfo.mediaButtonPreferences);
        listener.onMediaButtonPreferencesChanged(mediaControllerImplLegacy.getInstance(), controllerInfo.mediaButtonPreferences);
    }

    public MediaControllerImplLegacy(Context context, MediaController mediaController, SessionToken sessionToken, Bundle bundle, Looper looper, BitmapLoader bitmapLoader) {
        this.listeners = new ListenerSet(looper, Clock.DEFAULT, new ListenerSet.IterationFinishedEvent() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda2
            @Override // androidx.media3.common.util.ListenerSet.IterationFinishedEvent
            public final void invoke(Object obj, FlagSet flagSet) {
                ((Player.Listener) obj).onEvents(this.f$0.getInstance(), new Player.Events(flagSet));
            }
        });
        this.context = context;
        this.instance = mediaController;
        this.controllerCompatCallback = new ControllerCompatCallback(looper);
        this.token = sessionToken;
        this.connectionHints = bundle;
        this.bitmapLoader = bitmapLoader;
    }

    private static ControllerInfo buildNewControllerInfo(boolean z, LegacyPlayerInfo legacyPlayerInfo, ControllerInfo controllerInfo, LegacyPlayerInfo legacyPlayerInfo2, String str, long j, boolean z2, int i, long j2, String str2, boolean z3, Context context) {
        int iFindQueueItemIndex;
        MediaMetadata mediaMetadata;
        SessionCommands sessionCommandsConvertToSessionCommands;
        ImmutableList immutableListConvertToMediaButtonPreferences;
        int iConvertToPlaybackState;
        List list = legacyPlayerInfo.queue;
        List list2 = legacyPlayerInfo2.queue;
        boolean z4 = list != list2;
        QueueTimeline queueTimelineCreate = z4 ? QueueTimeline.create(list2) : ((QueueTimeline) controllerInfo.playerInfo.timeline).copy();
        boolean z5 = legacyPlayerInfo.mediaMetadataCompat != legacyPlayerInfo2.mediaMetadataCompat || z;
        long activeQueueId = getActiveQueueId(legacyPlayerInfo.playbackStateCompat);
        long activeQueueId2 = getActiveQueueId(legacyPlayerInfo2.playbackStateCompat);
        boolean z6 = activeQueueId != activeQueueId2 || z;
        long jConvertToDurationMs = LegacyConversions.convertToDurationMs(legacyPlayerInfo2.mediaMetadataCompat);
        if (z5 || z6 || z4) {
            iFindQueueItemIndex = findQueueItemIndex(legacyPlayerInfo2.queue, activeQueueId2);
            MediaMetadataCompat mediaMetadataCompat = legacyPlayerInfo2.mediaMetadataCompat;
            boolean z7 = mediaMetadataCompat != null;
            boolean z8 = z5;
            MediaMetadata mediaMetadataConvertToMediaMetadata = (z7 && z8) ? LegacyConversions.convertToMediaMetadata(mediaMetadataCompat, i) : (z7 || !z6) ? controllerInfo.playerInfo.mediaMetadata : iFindQueueItemIndex == -1 ? MediaMetadata.EMPTY : LegacyConversions.convertToMediaMetadata(((MediaSessionCompat.QueueItem) legacyPlayerInfo2.queue.get(iFindQueueItemIndex)).getDescription(), i);
            if (iFindQueueItemIndex != -1 || !z8) {
                if (iFindQueueItemIndex != -1) {
                    queueTimelineCreate = queueTimelineCreate.copyWithClearedFakeMediaItem();
                    if (z7) {
                        queueTimelineCreate = queueTimelineCreate.copyWithNewMediaItem(iFindQueueItemIndex, LegacyConversions.convertToMediaItem(((MediaItem) Assertions.checkNotNull(queueTimelineCreate.getMediaItemAt(iFindQueueItemIndex))).mediaId, legacyPlayerInfo2.mediaMetadataCompat, i), jConvertToDurationMs);
                    }
                } else {
                    iFindQueueItemIndex = 0;
                }
                mediaMetadata = mediaMetadataConvertToMediaMetadata;
            } else if (z7) {
                Log.w("MCImplLegacy", "Adding a fake MediaItem at the end of the list because there's no QueueItem with the active queue id and current Timeline should have currently playing MediaItem.");
                queueTimelineCreate = queueTimelineCreate.copyWithFakeMediaItem(LegacyConversions.convertToMediaItem(legacyPlayerInfo2.mediaMetadataCompat, i), jConvertToDurationMs);
                iFindQueueItemIndex = queueTimelineCreate.getWindowCount() - 1;
                mediaMetadata = mediaMetadataConvertToMediaMetadata;
            } else {
                queueTimelineCreate = queueTimelineCreate.copyWithClearedFakeMediaItem();
                iFindQueueItemIndex = 0;
                mediaMetadata = mediaMetadataConvertToMediaMetadata;
            }
        } else {
            PlayerInfo playerInfo = controllerInfo.playerInfo;
            iFindQueueItemIndex = playerInfo.sessionPositionInfo.positionInfo.mediaItemIndex;
            mediaMetadata = playerInfo.mediaMetadata;
        }
        int i2 = iFindQueueItemIndex;
        QueueTimeline queueTimeline = queueTimelineCreate;
        MediaControllerCompat.PlaybackInfo playbackInfo = legacyPlayerInfo2.playbackInfoCompat;
        Player.Commands commandsConvertToPlayerCommands = LegacyConversions.convertToPlayerCommands(legacyPlayerInfo2.playbackStateCompat, playbackInfo != null ? playbackInfo.getVolumeControl() : 0, j, z2);
        CharSequence charSequence = legacyPlayerInfo.queueTitle;
        CharSequence charSequence2 = legacyPlayerInfo2.queueTitle;
        MediaMetadata mediaMetadataConvertToMediaMetadata2 = charSequence == charSequence2 ? controllerInfo.playerInfo.playlistMetadata : LegacyConversions.convertToMediaMetadata(charSequence2);
        int iConvertToRepeatMode = LegacyConversions.convertToRepeatMode(legacyPlayerInfo2.repeatMode);
        boolean zConvertToShuffleModeEnabled = LegacyConversions.convertToShuffleModeEnabled(legacyPlayerInfo2.shuffleMode);
        PlaybackStateCompat playbackStateCompat = legacyPlayerInfo.playbackStateCompat;
        PlaybackStateCompat playbackStateCompat2 = legacyPlayerInfo2.playbackStateCompat;
        if (playbackStateCompat != playbackStateCompat2 || z3) {
            sessionCommandsConvertToSessionCommands = LegacyConversions.convertToSessionCommands(playbackStateCompat2, z2);
            immutableListConvertToMediaButtonPreferences = LegacyConversions.convertToMediaButtonPreferences(legacyPlayerInfo2.playbackStateCompat, commandsConvertToPlayerCommands, legacyPlayerInfo2.sessionExtras);
        } else {
            sessionCommandsConvertToSessionCommands = controllerInfo.availableSessionCommands;
            immutableListConvertToMediaButtonPreferences = controllerInfo.mediaButtonPreferences;
        }
        SessionCommands sessionCommands = sessionCommandsConvertToSessionCommands;
        ImmutableList immutableList = immutableListConvertToMediaButtonPreferences;
        PlaybackException playbackExceptionConvertToPlaybackException = LegacyConversions.convertToPlaybackException(legacyPlayerInfo2.playbackStateCompat);
        SessionError sessionErrorConvertToSessionError = LegacyConversions.convertToSessionError(legacyPlayerInfo2.playbackStateCompat, context);
        long jConvertToCurrentPositionMs = LegacyConversions.convertToCurrentPositionMs(legacyPlayerInfo2.playbackStateCompat, legacyPlayerInfo2.mediaMetadataCompat, j2);
        long jConvertToBufferedPositionMs = LegacyConversions.convertToBufferedPositionMs(legacyPlayerInfo2.playbackStateCompat, legacyPlayerInfo2.mediaMetadataCompat, j2);
        int iConvertToBufferedPercentage = LegacyConversions.convertToBufferedPercentage(legacyPlayerInfo2.playbackStateCompat, legacyPlayerInfo2.mediaMetadataCompat, j2);
        long jConvertToTotalBufferedDurationMs = LegacyConversions.convertToTotalBufferedDurationMs(legacyPlayerInfo2.playbackStateCompat, legacyPlayerInfo2.mediaMetadataCompat, j2);
        boolean zConvertToIsPlayingAd = LegacyConversions.convertToIsPlayingAd(legacyPlayerInfo2.mediaMetadataCompat);
        PlaybackParameters playbackParametersConvertToPlaybackParameters = LegacyConversions.convertToPlaybackParameters(legacyPlayerInfo2.playbackStateCompat);
        AudioAttributes audioAttributesConvertToAudioAttributes = LegacyConversions.convertToAudioAttributes(legacyPlayerInfo2.playbackInfoCompat);
        boolean zConvertToPlayWhenReady = LegacyConversions.convertToPlayWhenReady(legacyPlayerInfo2.playbackStateCompat);
        try {
            iConvertToPlaybackState = LegacyConversions.convertToPlaybackState(legacyPlayerInfo2.playbackStateCompat, legacyPlayerInfo2.mediaMetadataCompat, j2);
        } catch (LegacyConversions.ConversionException unused) {
            Log.e("MCImplLegacy", String.format("Received invalid playback state %s from package %s. Keeping the previous state.", Integer.valueOf(legacyPlayerInfo2.playbackStateCompat.getState()), str));
            iConvertToPlaybackState = controllerInfo.playerInfo.playbackState;
        }
        int i3 = iConvertToPlaybackState;
        boolean zConvertToIsPlaying = LegacyConversions.convertToIsPlaying(legacyPlayerInfo2.playbackStateCompat);
        DeviceInfo deviceInfoConvertToDeviceInfo = LegacyConversions.convertToDeviceInfo(legacyPlayerInfo2.playbackInfoCompat, str2);
        int iConvertToDeviceVolume = LegacyConversions.convertToDeviceVolume(legacyPlayerInfo2.playbackInfoCompat);
        boolean zConvertToIsDeviceMuted = LegacyConversions.convertToIsDeviceMuted(legacyPlayerInfo2.playbackInfoCompat);
        PlayerInfo playerInfo2 = controllerInfo.playerInfo;
        return createControllerInfo(queueTimeline, mediaMetadata, i2, mediaMetadataConvertToMediaMetadata2, iConvertToRepeatMode, zConvertToShuffleModeEnabled, sessionCommands, commandsConvertToPlayerCommands, immutableList, legacyPlayerInfo2.sessionExtras, playbackExceptionConvertToPlaybackException, sessionErrorConvertToSessionError, jConvertToDurationMs, jConvertToCurrentPositionMs, jConvertToBufferedPositionMs, iConvertToBufferedPercentage, jConvertToTotalBufferedDurationMs, zConvertToIsPlayingAd, playbackParametersConvertToPlaybackParameters, audioAttributesConvertToAudioAttributes, zConvertToPlayWhenReady, i3, zConvertToIsPlaying, deviceInfoConvertToDeviceInfo, iConvertToDeviceVolume, zConvertToIsDeviceMuted, playerInfo2.seekBackIncrementMs, playerInfo2.seekForwardIncrementMs, playerInfo2.maxSeekToPreviousPositionMs);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static android.util.Pair calculateDiscontinuityAndTransitionReason(androidx.media3.session.MediaControllerImplLegacy.LegacyPlayerInfo r8, androidx.media3.session.MediaControllerImplLegacy.ControllerInfo r9, androidx.media3.session.MediaControllerImplLegacy.LegacyPlayerInfo r10, androidx.media3.session.MediaControllerImplLegacy.ControllerInfo r11, long r12) {
        /*
            androidx.media3.session.PlayerInfo r0 = r9.playerInfo
            androidx.media3.common.Timeline r0 = r0.timeline
            boolean r0 = r0.isEmpty()
            androidx.media3.session.PlayerInfo r1 = r11.playerInfo
            androidx.media3.common.Timeline r1 = r1.timeline
            boolean r1 = r1.isEmpty()
            r2 = 0
            if (r0 == 0) goto L18
            if (r1 == 0) goto L18
        L15:
            r8 = r2
            goto L98
        L18:
            r3 = 3
            r4 = 0
            if (r0 == 0) goto L28
            if (r1 != 0) goto L28
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)
            goto L98
        L28:
            androidx.media3.session.PlayerInfo r9 = r9.playerInfo
            androidx.media3.common.MediaItem r9 = r9.getCurrentMediaItem()
            java.lang.Object r9 = androidx.media3.common.util.Assertions.checkStateNotNull(r9)
            androidx.media3.common.MediaItem r9 = (androidx.media3.common.MediaItem) r9
            androidx.media3.session.PlayerInfo r0 = r11.playerInfo
            androidx.media3.common.Timeline r0 = r0.timeline
            androidx.media3.session.QueueTimeline r0 = (androidx.media3.session.QueueTimeline) r0
            boolean r0 = r0.contains(r9)
            if (r0 != 0) goto L4a
            r8 = 4
            java.lang.Integer r2 = java.lang.Integer.valueOf(r8)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)
            goto L98
        L4a:
            androidx.media3.session.PlayerInfo r0 = r11.playerInfo
            androidx.media3.common.MediaItem r0 = r0.getCurrentMediaItem()
            boolean r9 = r9.equals(r0)
            r0 = 1
            if (r9 == 0) goto L90
            androidx.media3.session.legacy.PlaybackStateCompat r9 = r8.playbackStateCompat
            androidx.media3.session.legacy.MediaMetadataCompat r8 = r8.mediaMetadataCompat
            long r8 = androidx.media3.session.LegacyConversions.convertToCurrentPositionMs(r9, r8, r12)
            androidx.media3.session.legacy.PlaybackStateCompat r1 = r10.playbackStateCompat
            androidx.media3.session.legacy.MediaMetadataCompat r10 = r10.mediaMetadataCompat
            long r12 = androidx.media3.session.LegacyConversions.convertToCurrentPositionMs(r1, r10, r12)
            r5 = 0
            int r10 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r10 != 0) goto L7c
            androidx.media3.session.PlayerInfo r10 = r11.playerInfo
            int r10 = r10.repeatMode
            if (r10 != r0) goto L7c
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r4)
            goto L98
        L7c:
            long r8 = r8 - r12
            long r8 = java.lang.Math.abs(r8)
            r10 = 100
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 <= 0) goto L15
            r8 = 5
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r7 = r2
            r2 = r8
            r8 = r7
            goto L98
        L90:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r0)
        L98:
            android.util.Pair r8 = android.util.Pair.create(r2, r8)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.session.MediaControllerImplLegacy.calculateDiscontinuityAndTransitionReason(androidx.media3.session.MediaControllerImplLegacy$LegacyPlayerInfo, androidx.media3.session.MediaControllerImplLegacy$ControllerInfo, androidx.media3.session.MediaControllerImplLegacy$LegacyPlayerInfo, androidx.media3.session.MediaControllerImplLegacy$ControllerInfo, long):android.util.Pair");
    }

    private void connectToService() {
        getInstance().runOnApplicationLooper(new Runnable() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                MediaControllerImplLegacy.m2019$r8$lambda$52StrBIOqSZpQ7DvS_e6wcs1Qs(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectToSession(final MediaSessionCompat.Token token) {
        getInstance().runOnApplicationLooper(new Runnable() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MediaControllerImplLegacy.$r8$lambda$6y6sNOafeXrBS6WhicpQusmFhiY(this.f$0, token);
            }
        });
        getInstance().applicationHandler.post(new Runnable() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MediaControllerImplLegacy.$r8$lambda$MaaQ0IusK9godPxSltfKjLAZdVU(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List convertToNonNullQueueItemList(List list) {
        return list == null ? Collections.EMPTY_LIST : MediaUtils.removeNullElements(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PlaybackStateCompat convertToSafePlaybackStateCompat(PlaybackStateCompat playbackStateCompat) {
        if (playbackStateCompat == null) {
            return null;
        }
        if (playbackStateCompat.getPlaybackSpeed() > 0.0f) {
            return playbackStateCompat;
        }
        Log.w("MCImplLegacy", "Adjusting playback speed to 1.0f because negative playback speed isn't supported.");
        return new PlaybackStateCompat.Builder(playbackStateCompat).setState(playbackStateCompat.getState(), playbackStateCompat.getPosition(), 1.0f, playbackStateCompat.getLastPositionUpdateTime()).build();
    }

    private static ControllerInfo createControllerInfo(QueueTimeline queueTimeline, MediaMetadata mediaMetadata, int i, MediaMetadata mediaMetadata2, int i2, boolean z, SessionCommands sessionCommands, Player.Commands commands, ImmutableList immutableList, Bundle bundle, PlaybackException playbackException, SessionError sessionError, long j, long j2, long j3, int i3, long j4, boolean z2, PlaybackParameters playbackParameters, AudioAttributes audioAttributes, boolean z3, int i4, boolean z4, DeviceInfo deviceInfo, int i5, boolean z5, long j5, long j6, long j7) {
        SessionPositionInfo sessionPositionInfo = new SessionPositionInfo(createPositionInfo(i, queueTimeline.getMediaItemAt(i), j2, z2), z2, SystemClock.elapsedRealtime(), j, j3, i3, j4, -9223372036854775807L, j, j3);
        Player.PositionInfo positionInfo = SessionPositionInfo.DEFAULT_POSITION_INFO;
        return new ControllerInfo(new PlayerInfo(playbackException, 0, sessionPositionInfo, positionInfo, positionInfo, 0, playbackParameters, i2, z, VideoSize.UNKNOWN, queueTimeline, 0, mediaMetadata2, 1.0f, audioAttributes, CueGroup.EMPTY_TIME_ZERO, deviceInfo, i5, z5, z3, 1, 0, i4, z4, false, mediaMetadata, j5, j6, j7, Tracks.EMPTY, TrackSelectionParameters.DEFAULT_WITHOUT_CONTEXT), sessionCommands, commands, immutableList, bundle, sessionError);
    }

    private static Player.PositionInfo createPositionInfo(int i, MediaItem mediaItem, long j, boolean z) {
        return new Player.PositionInfo(null, i, mediaItem, null, i, j, j, z ? 0 : -1, z ? 0 : -1);
    }

    private static SessionPositionInfo createSessionPositionInfo(Player.PositionInfo positionInfo, boolean z, long j, long j2, int i, long j3) {
        return new SessionPositionInfo(positionInfo, z, SystemClock.elapsedRealtime(), j, j2, i, j3, -9223372036854775807L, j, j2);
    }

    private static int findQueueItemIndex(List list, long j) {
        if (list != null && j != -1) {
            for (int i = 0; i < list.size(); i++) {
                if (((MediaSessionCompat.QueueItem) list.get(i)).getQueueId() == j) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static long getActiveQueueId(PlaybackStateCompat playbackStateCompat) {
        if (playbackStateCompat == null) {
            return -1L;
        }
        return playbackStateCompat.getActiveQueueItemId();
    }

    private static String getRoutingControllerId(MediaControllerCompat mediaControllerCompat) {
        MediaController.PlaybackInfo playbackInfo;
        if (Util.SDK_INT >= 30 && (playbackInfo = ((android.media.session.MediaController) mediaControllerCompat.getMediaController()).getPlaybackInfo()) != null) {
            return playbackInfo.getVolumeControlId();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNewLegacyParameters(boolean z, final LegacyPlayerInfo legacyPlayerInfo) {
        if (this.released || !this.connected) {
            return;
        }
        ControllerInfo controllerInfoBuildNewControllerInfo = buildNewControllerInfo(z, this.legacyPlayerInfo, this.controllerInfo, legacyPlayerInfo, this.controllerCompat.getPackageName(), this.controllerCompat.getFlags(), this.controllerCompat.isSessionReady(), this.controllerCompat.getRatingType(), getInstance().getTimeDiffMs(), getRoutingControllerId(this.controllerCompat), this.hasPendingExtrasChange, this.context);
        Pair pairCalculateDiscontinuityAndTransitionReason = calculateDiscontinuityAndTransitionReason(this.legacyPlayerInfo, this.controllerInfo, legacyPlayerInfo, controllerInfoBuildNewControllerInfo, getInstance().getTimeDiffMs());
        updateControllerInfo(z, legacyPlayerInfo, controllerInfoBuildNewControllerInfo, (Integer) pairCalculateDiscontinuityAndTransitionReason.first, (Integer) pairCalculateDiscontinuityAndTransitionReason.second);
        if (this.hasPendingExtrasChange) {
            this.hasPendingExtrasChange = false;
            getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda25
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    ((MediaController.Listener) obj).onExtrasChanged(this.f$0.getInstance(), legacyPlayerInfo.sessionExtras);
                }
            });
        }
    }

    private boolean hasMedia() {
        return !this.controllerInfo.playerInfo.timeline.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void ignoreFuture(Future future) {
    }

    private boolean isPrepared() {
        return this.controllerInfo.playerInfo.playbackState != 1;
    }

    private void seekToInternal(int i, long j) {
        Integer num;
        Integer num2;
        int i2;
        long j2;
        long j3;
        long j4;
        int i3 = i;
        long j5 = j;
        Assertions.checkArgument(i3 >= 0);
        int currentMediaItemIndex = getCurrentMediaItemIndex();
        Timeline timeline = this.controllerInfo.playerInfo.timeline;
        if ((timeline.isEmpty() || i3 < timeline.getWindowCount()) && !isPlayingAd()) {
            if (i3 != currentMediaItemIndex) {
                long queueId = ((QueueTimeline) this.controllerInfo.playerInfo.timeline).getQueueId(i3);
                if (queueId != -1) {
                    this.controllerCompat.getTransportControls().skipToQueueItem(queueId);
                    num = 2;
                } else {
                    Log.w("MCImplLegacy", "Cannot seek to new media item due to the missing queue Id at media item, mediaItemIndex=" + i3);
                    i3 = currentMediaItemIndex;
                    num = null;
                }
            } else {
                i3 = currentMediaItemIndex;
                num = null;
            }
            long currentPosition = getCurrentPosition();
            if (j5 == -9223372036854775807L) {
                num2 = null;
                j5 = currentPosition;
            } else {
                this.controllerCompat.getTransportControls().seekTo(j5);
                num2 = 1;
            }
            if (num == null) {
                long bufferedPosition = getBufferedPosition();
                long duration = getDuration();
                long jMax = j5 < currentPosition ? j5 : Math.max(j5, bufferedPosition);
                j2 = jMax;
                i2 = duration == -9223372036854775807L ? 0 : (int) ((100 * jMax) / duration);
                j3 = jMax - j5;
                j4 = duration;
            } else {
                i2 = 0;
                j2 = 0;
                j3 = 0;
                j4 = -9223372036854775807L;
            }
            PlayerInfo playerInfoCopyWithSessionPositionInfo = this.controllerInfo.playerInfo.copyWithSessionPositionInfo(createSessionPositionInfo(createPositionInfo(i3, !timeline.isEmpty() ? timeline.getWindow(i3, new Timeline.Window()).mediaItem : null, j5, false), false, j4, j2, i2, j3));
            if (playerInfoCopyWithSessionPositionInfo.playbackState != 1) {
                playerInfoCopyWithSessionPositionInfo = playerInfoCopyWithSessionPositionInfo.copyWithPlaybackState(2, null);
            }
            PlayerInfo playerInfo = playerInfoCopyWithSessionPositionInfo;
            ControllerInfo controllerInfo = this.controllerInfo;
            updateStateMaskedControllerInfo(new ControllerInfo(playerInfo, controllerInfo.availableSessionCommands, controllerInfo.availablePlayerCommands, controllerInfo.mediaButtonPreferences, controllerInfo.sessionExtras, null), num2, num);
        }
    }

    private void updateControllerInfo(boolean z, LegacyPlayerInfo legacyPlayerInfo, final ControllerInfo controllerInfo, final Integer num, final Integer num2) {
        LegacyPlayerInfo legacyPlayerInfo2 = this.legacyPlayerInfo;
        final ControllerInfo controllerInfo2 = this.controllerInfo;
        if (legacyPlayerInfo2 != legacyPlayerInfo) {
            this.legacyPlayerInfo = new LegacyPlayerInfo(legacyPlayerInfo);
        }
        this.pendingLegacyPlayerInfo = this.legacyPlayerInfo;
        this.controllerInfo = controllerInfo;
        if (z) {
            getInstance().notifyAccepted();
            if (controllerInfo2.mediaButtonPreferences.equals(controllerInfo.mediaButtonPreferences)) {
                return;
            }
            getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda4
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    MediaControllerImplLegacy.m2022$r8$lambda$PIhqOQa6oi1gv7bqAxJVytQ7GM(this.f$0, controllerInfo, (MediaController.Listener) obj);
                }
            });
            return;
        }
        if (!controllerInfo2.playerInfo.timeline.equals(controllerInfo.playerInfo.timeline)) {
            this.listeners.queueEvent(0, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda15
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    MediaControllerImplLegacy.$r8$lambda$bF_3oWXvnJY21hy47z1bsCIA8r0(controllerInfo, (Player.Listener) obj);
                }
            });
        }
        if (!Util.areEqual(legacyPlayerInfo2.queueTitle, legacyPlayerInfo.queueTitle)) {
            this.listeners.queueEvent(15, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda17
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onPlaylistMetadataChanged(controllerInfo.playerInfo.playlistMetadata);
                }
            });
        }
        if (num != null) {
            this.listeners.queueEvent(11, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda18
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onPositionDiscontinuity(controllerInfo2.playerInfo.sessionPositionInfo.positionInfo, controllerInfo.playerInfo.sessionPositionInfo.positionInfo, num.intValue());
                }
            });
        }
        if (num2 != null) {
            this.listeners.queueEvent(1, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda19
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onMediaItemTransition(controllerInfo.playerInfo.getCurrentMediaItem(), num2.intValue());
                }
            });
        }
        if (!MediaUtils.areEqualError(legacyPlayerInfo2.playbackStateCompat, legacyPlayerInfo.playbackStateCompat)) {
            final PlaybackException playbackExceptionConvertToPlaybackException = LegacyConversions.convertToPlaybackException(legacyPlayerInfo.playbackStateCompat);
            this.listeners.queueEvent(10, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda20
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onPlayerErrorChanged(playbackExceptionConvertToPlaybackException);
                }
            });
            if (playbackExceptionConvertToPlaybackException != null) {
                this.listeners.queueEvent(10, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda21
                    @Override // androidx.media3.common.util.ListenerSet.Event
                    public final void invoke(Object obj) {
                        ((Player.Listener) obj).onPlayerError(playbackExceptionConvertToPlaybackException);
                    }
                });
            }
        }
        if (legacyPlayerInfo2.mediaMetadataCompat != legacyPlayerInfo.mediaMetadataCompat) {
            this.listeners.queueEvent(14, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda22
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onMediaMetadataChanged(this.f$0.controllerInfo.playerInfo.mediaMetadata);
                }
            });
        }
        if (controllerInfo2.playerInfo.playbackState != controllerInfo.playerInfo.playbackState) {
            this.listeners.queueEvent(4, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda23
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onPlaybackStateChanged(controllerInfo.playerInfo.playbackState);
                }
            });
        }
        if (controllerInfo2.playerInfo.playWhenReady != controllerInfo.playerInfo.playWhenReady) {
            this.listeners.queueEvent(5, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda24
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onPlayWhenReadyChanged(controllerInfo.playerInfo.playWhenReady, 4);
                }
            });
        }
        if (controllerInfo2.playerInfo.isPlaying != controllerInfo.playerInfo.isPlaying) {
            this.listeners.queueEvent(7, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda5
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onIsPlayingChanged(controllerInfo.playerInfo.isPlaying);
                }
            });
        }
        if (!controllerInfo2.playerInfo.playbackParameters.equals(controllerInfo.playerInfo.playbackParameters)) {
            this.listeners.queueEvent(12, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda6
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onPlaybackParametersChanged(controllerInfo.playerInfo.playbackParameters);
                }
            });
        }
        if (controllerInfo2.playerInfo.repeatMode != controllerInfo.playerInfo.repeatMode) {
            this.listeners.queueEvent(8, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda7
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onRepeatModeChanged(controllerInfo.playerInfo.repeatMode);
                }
            });
        }
        if (controllerInfo2.playerInfo.shuffleModeEnabled != controllerInfo.playerInfo.shuffleModeEnabled) {
            this.listeners.queueEvent(9, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda8
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onShuffleModeEnabledChanged(controllerInfo.playerInfo.shuffleModeEnabled);
                }
            });
        }
        if (!controllerInfo2.playerInfo.audioAttributes.equals(controllerInfo.playerInfo.audioAttributes)) {
            this.listeners.queueEvent(20, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda9
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onAudioAttributesChanged(controllerInfo.playerInfo.audioAttributes);
                }
            });
        }
        if (!controllerInfo2.playerInfo.deviceInfo.equals(controllerInfo.playerInfo.deviceInfo)) {
            this.listeners.queueEvent(29, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda10
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onDeviceInfoChanged(controllerInfo.playerInfo.deviceInfo);
                }
            });
        }
        PlayerInfo playerInfo = controllerInfo2.playerInfo;
        int i = playerInfo.deviceVolume;
        PlayerInfo playerInfo2 = controllerInfo.playerInfo;
        if (i != playerInfo2.deviceVolume || playerInfo.deviceMuted != playerInfo2.deviceMuted) {
            this.listeners.queueEvent(30, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda11
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    MediaControllerImplLegacy.m2021$r8$lambda$C0_2IPf_zzLCkKgiCeYFjRaxPQ(controllerInfo, (Player.Listener) obj);
                }
            });
        }
        if (!controllerInfo2.availablePlayerCommands.equals(controllerInfo.availablePlayerCommands)) {
            this.listeners.queueEvent(13, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda12
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onAvailableCommandsChanged(controllerInfo.availablePlayerCommands);
                }
            });
        }
        if (!controllerInfo2.availableSessionCommands.equals(controllerInfo.availableSessionCommands)) {
            getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda13
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    ((MediaController.Listener) obj).onAvailableSessionCommandsChanged(this.f$0.getInstance(), controllerInfo.availableSessionCommands);
                }
            });
        }
        if (!controllerInfo2.mediaButtonPreferences.equals(controllerInfo.mediaButtonPreferences)) {
            getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda14
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    MediaControllerImplLegacy.$r8$lambda$fapNGe03LjIMswMZTd9DJaHrdmw(this.f$0, controllerInfo, (MediaController.Listener) obj);
                }
            });
        }
        if (controllerInfo.sessionError != null) {
            getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplLegacy$$ExternalSyntheticLambda16
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    ((MediaController.Listener) obj).onError(this.f$0.getInstance(), controllerInfo.sessionError);
                }
            });
        }
        this.listeners.flushEvents();
    }

    private void updateStateMaskedControllerInfo(ControllerInfo controllerInfo, Integer num, Integer num2) {
        updateControllerInfo(false, this.legacyPlayerInfo, controllerInfo, num, num2);
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void addListener(Player.Listener listener) {
        this.listeners.add(listener);
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void connect() {
        if (this.token.getType() == 0) {
            connectToSession((MediaSessionCompat.Token) Assertions.checkStateNotNull(this.token.getBinder()));
        } else {
            connectToService();
        }
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public Player.Commands getAvailableCommands() {
        return this.controllerInfo.availablePlayerCommands;
    }

    public MediaBrowserCompat getBrowserCompat() {
        return this.browserCompat;
    }

    public long getBufferedPosition() {
        return this.controllerInfo.playerInfo.sessionPositionInfo.bufferedPositionMs;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public Bundle getConnectionHints() {
        return this.connectionHints;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public long getContentDuration() {
        return getDuration();
    }

    public int getCurrentMediaItemIndex() {
        return this.controllerInfo.playerInfo.sessionPositionInfo.positionInfo.mediaItemIndex;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public long getCurrentPosition() {
        long updatedCurrentPositionMs = MediaUtils.getUpdatedCurrentPositionMs(this.controllerInfo.playerInfo, this.currentPositionMs, this.lastSetPlayWhenReadyCalledTimeMs, getInstance().getTimeDiffMs());
        this.currentPositionMs = updatedCurrentPositionMs;
        return updatedCurrentPositionMs;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public long getDuration() {
        return this.controllerInfo.playerInfo.sessionPositionInfo.durationMs;
    }

    MediaController getInstance() {
        return this.instance;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public MediaMetadata getMediaMetadata() {
        MediaItem currentMediaItem = this.controllerInfo.playerInfo.getCurrentMediaItem();
        return currentMediaItem == null ? MediaMetadata.EMPTY : currentMediaItem.mediaMetadata;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public boolean isConnected() {
        return this.connected;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public boolean isPlaying() {
        return this.controllerInfo.playerInfo.isPlaying;
    }

    public boolean isPlayingAd() {
        return this.controllerInfo.playerInfo.sessionPositionInfo.isPlayingAd;
    }

    void onConnected() {
        if (this.released || this.connected) {
            return;
        }
        this.connected = true;
        handleNewLegacyParameters(true, new LegacyPlayerInfo(this.controllerCompat.getPlaybackInfo(), convertToSafePlaybackStateCompat(this.controllerCompat.getPlaybackState()), this.controllerCompat.getMetadata(), convertToNonNullQueueItemList(this.controllerCompat.getQueue()), this.controllerCompat.getQueueTitle(), this.controllerCompat.getRepeatMode(), this.controllerCompat.getShuffleMode(), this.controllerCompat.getExtras()));
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void pause() {
        setPlayWhenReady(false);
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void play() {
        setPlayWhenReady(true);
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void release() {
        if (this.released) {
            return;
        }
        this.released = true;
        MediaBrowserCompat mediaBrowserCompat = this.browserCompat;
        if (mediaBrowserCompat != null) {
            mediaBrowserCompat.disconnect();
            this.browserCompat = null;
        }
        MediaControllerCompat mediaControllerCompat = this.controllerCompat;
        if (mediaControllerCompat != null) {
            mediaControllerCompat.unregisterCallback(this.controllerCompatCallback);
            this.controllerCompatCallback.release();
            this.controllerCompat = null;
        }
        this.connected = false;
        this.listeners.release();
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void removeListener(Player.Listener listener) {
        this.listeners.remove(listener);
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void seekTo(long j) {
        seekToInternal(getCurrentMediaItemIndex(), j);
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void seekToNext() {
        this.controllerCompat.getTransportControls().skipToNext();
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void seekToPrevious() {
        this.controllerCompat.getTransportControls().skipToPrevious();
    }

    public void setPlayWhenReady(boolean z) {
        PlayerInfo playerInfo = this.controllerInfo.playerInfo;
        if (playerInfo.playWhenReady == z) {
            return;
        }
        this.currentPositionMs = MediaUtils.getUpdatedCurrentPositionMs(playerInfo, this.currentPositionMs, this.lastSetPlayWhenReadyCalledTimeMs, getInstance().getTimeDiffMs());
        this.lastSetPlayWhenReadyCalledTimeMs = SystemClock.elapsedRealtime();
        PlayerInfo playerInfoCopyWithPlayWhenReady = this.controllerInfo.playerInfo.copyWithPlayWhenReady(z, 1, 0);
        ControllerInfo controllerInfo = this.controllerInfo;
        updateStateMaskedControllerInfo(new ControllerInfo(playerInfoCopyWithPlayWhenReady, controllerInfo.availableSessionCommands, controllerInfo.availablePlayerCommands, controllerInfo.mediaButtonPreferences, controllerInfo.sessionExtras, null), null, null);
        if (isPrepared() && hasMedia()) {
            if (z) {
                this.controllerCompat.getTransportControls().play();
            } else {
                this.controllerCompat.getTransportControls().pause();
            }
        }
    }
}
