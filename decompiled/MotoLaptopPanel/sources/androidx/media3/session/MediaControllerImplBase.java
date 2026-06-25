package androidx.media3.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Pair;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import androidx.collection.ArraySet;
import androidx.media3.common.FlagSet;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.Consumer;
import androidx.media3.common.util.ListenerSet;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.Size;
import androidx.media3.common.util.Util;
import androidx.media3.session.IMediaSession;
import androidx.media3.session.IMediaSessionService;
import androidx.media3.session.MediaController;
import androidx.media3.session.MediaControllerImplBase;
import androidx.media3.session.PlayerInfo;
import androidx.media3.session.SequencedFutureManager;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
class MediaControllerImplBase implements MediaController.MediaControllerImpl {
    private SessionToken connectedToken;
    private final Bundle connectionHints;
    private final Context context;
    protected final MediaControllerStub controllerStub;
    private long currentPositionMs;
    private final IBinder.DeathRecipient deathRecipient;
    private final FlushCommandQueueHandler flushCommandQueueHandler;
    private IMediaSession iSession;
    private final MediaController instance;
    private Player.Commands intersectedPlayerCommands;
    private long lastSetPlayWhenReadyCalledTimeMs;
    private final ListenerSet listeners;
    private PlayerInfo.BundlingExclusions pendingBundlingExclusions;
    private final ArraySet pendingMaskingSequencedFutureNumbers;
    private PlayerInfo pendingPlayerInfo;
    private android.media.session.MediaController platformController;
    private Player.Commands playerCommandsFromPlayer;
    private Player.Commands playerCommandsFromSession;
    private boolean released;
    protected final SequencedFutureManager sequencedFutureManager;
    private SessionServiceConnection serviceConnection;
    private PendingIntent sessionActivity;
    private Bundle sessionExtras;
    private final SurfaceCallback surfaceCallback;
    private final SessionToken token;
    private Surface videoSurface;
    private SurfaceHolder videoSurfaceHolder;
    private TextureView videoTextureView;
    private PlayerInfo playerInfo = PlayerInfo.DEFAULT;
    private Size surfaceSize = Size.UNKNOWN;
    private SessionCommands sessionCommands = SessionCommands.EMPTY;
    private ImmutableList customLayoutOriginal = ImmutableList.of();
    private ImmutableList mediaButtonPreferencesOriginal = ImmutableList.of();
    private ImmutableList resolvedMediaButtonPreferences = ImmutableList.of();
    private ImmutableList resolvedCustomLayout = ImmutableList.of();
    private ImmutableMap commandButtonsForMediaItemsMap = ImmutableMap.of();

    class FlushCommandQueueHandler {
        private final Handler handler;

        public FlushCommandQueueHandler(Looper looper) {
            this.handler = new Handler(looper, new Handler.Callback() { // from class: androidx.media3.session.MediaControllerImplBase$FlushCommandQueueHandler$$ExternalSyntheticLambda0
                @Override // android.os.Handler.Callback
                public final boolean handleMessage(Message message) {
                    return this.f$0.handleMessage(message);
                }
            });
        }

        private void flushCommandQueue() {
            try {
                MediaControllerImplBase.this.iSession.flushCommandQueue(MediaControllerImplBase.this.controllerStub);
            } catch (RemoteException unused) {
                Log.w("MCImplBase", "Error in sending flushCommandQueue");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                flushCommandQueue();
            }
            return true;
        }

        public void release() {
            if (this.handler.hasMessages(1)) {
                flushCommandQueue();
            }
            this.handler.removeCallbacksAndMessages(null);
        }

        public void sendFlushCommandQueueMessage() {
            if (MediaControllerImplBase.this.iSession == null || this.handler.hasMessages(1)) {
                return;
            }
            this.handler.sendEmptyMessage(1);
        }
    }

    final class PeriodInfo {
        private final int index;
        private final long periodPositionUs;

        public PeriodInfo(int i, long j) {
            this.index = i;
            this.periodPositionUs = j;
        }
    }

    interface RemoteSessionTask {
        void run(IMediaSession iMediaSession, int i);
    }

    class SessionServiceConnection implements ServiceConnection {
        private final Bundle connectionHints;

        public SessionServiceConnection(Bundle bundle) {
            this.connectionHints = bundle;
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            MediaController mediaControllerImplBase = MediaControllerImplBase.this.getInstance();
            MediaController mediaControllerImplBase2 = MediaControllerImplBase.this.getInstance();
            mediaControllerImplBase2.getClass();
            mediaControllerImplBase.runOnApplicationLooper(new MediaControllerImplBase$$ExternalSyntheticLambda16(mediaControllerImplBase2));
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MediaController mediaControllerImplBase;
            MediaControllerImplBase$$ExternalSyntheticLambda16 mediaControllerImplBase$$ExternalSyntheticLambda16;
            try {
                try {
                    if (MediaControllerImplBase.this.token.getPackageName().equals(componentName.getPackageName())) {
                        IMediaSessionService iMediaSessionServiceAsInterface = IMediaSessionService.Stub.asInterface(iBinder);
                        if (iMediaSessionServiceAsInterface != null) {
                            iMediaSessionServiceAsInterface.connect(MediaControllerImplBase.this.controllerStub, new ConnectionRequest(MediaControllerImplBase.this.getContext().getPackageName(), Process.myPid(), this.connectionHints, MediaControllerImplBase.this.instance.getMaxCommandsForMediaItems()).toBundle());
                            return;
                        } else {
                            Log.e("MCImplBase", "Service interface is missing.");
                            mediaControllerImplBase = MediaControllerImplBase.this.getInstance();
                            MediaController mediaControllerImplBase2 = MediaControllerImplBase.this.getInstance();
                            mediaControllerImplBase2.getClass();
                            mediaControllerImplBase$$ExternalSyntheticLambda16 = new MediaControllerImplBase$$ExternalSyntheticLambda16(mediaControllerImplBase2);
                        }
                    } else {
                        Log.e("MCImplBase", "Expected connection to " + MediaControllerImplBase.this.token.getPackageName() + " but is connected to " + componentName);
                        mediaControllerImplBase = MediaControllerImplBase.this.getInstance();
                        MediaController mediaControllerImplBase3 = MediaControllerImplBase.this.getInstance();
                        mediaControllerImplBase3.getClass();
                        mediaControllerImplBase$$ExternalSyntheticLambda16 = new MediaControllerImplBase$$ExternalSyntheticLambda16(mediaControllerImplBase3);
                    }
                    mediaControllerImplBase.runOnApplicationLooper(mediaControllerImplBase$$ExternalSyntheticLambda16);
                } catch (RemoteException unused) {
                    Log.w("MCImplBase", "Service " + componentName + " has died prematurely");
                    MediaController mediaControllerImplBase4 = MediaControllerImplBase.this.getInstance();
                    MediaController mediaControllerImplBase5 = MediaControllerImplBase.this.getInstance();
                    mediaControllerImplBase5.getClass();
                    mediaControllerImplBase4.runOnApplicationLooper(new MediaControllerImplBase$$ExternalSyntheticLambda16(mediaControllerImplBase5));
                }
            } catch (Throwable th) {
                MediaController mediaControllerImplBase6 = MediaControllerImplBase.this.getInstance();
                MediaController mediaControllerImplBase7 = MediaControllerImplBase.this.getInstance();
                mediaControllerImplBase7.getClass();
                mediaControllerImplBase6.runOnApplicationLooper(new MediaControllerImplBase$$ExternalSyntheticLambda16(mediaControllerImplBase7));
                throw th;
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            MediaController mediaControllerImplBase = MediaControllerImplBase.this.getInstance();
            MediaController mediaControllerImplBase2 = MediaControllerImplBase.this.getInstance();
            mediaControllerImplBase2.getClass();
            mediaControllerImplBase.runOnApplicationLooper(new MediaControllerImplBase$$ExternalSyntheticLambda16(mediaControllerImplBase2));
        }
    }

    class SurfaceCallback implements SurfaceHolder.Callback, TextureView.SurfaceTextureListener {
        public static /* synthetic */ void $r8$lambda$83wtZSbLSPY3VB0wn6pMXKIaQfQ(SurfaceCallback surfaceCallback, IMediaSession iMediaSession, int i) {
            MediaControllerImplBase mediaControllerImplBase = MediaControllerImplBase.this;
            iMediaSession.setVideoSurface(mediaControllerImplBase.controllerStub, i, mediaControllerImplBase.videoSurface);
        }

        /* JADX INFO: renamed from: $r8$lambda$Ek7MXRviAuSHRapDOHKdlU9tH-I, reason: not valid java name */
        public static /* synthetic */ void m2017$r8$lambda$Ek7MXRviAuSHRapDOHKdlU9tHI(SurfaceCallback surfaceCallback, IMediaSession iMediaSession, int i) {
            MediaControllerImplBase mediaControllerImplBase = MediaControllerImplBase.this;
            iMediaSession.setVideoSurface(mediaControllerImplBase.controllerStub, i, mediaControllerImplBase.videoSurface);
        }

        private SurfaceCallback() {
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            if (MediaControllerImplBase.this.videoTextureView == null || MediaControllerImplBase.this.videoTextureView.getSurfaceTexture() != surfaceTexture) {
                return;
            }
            MediaControllerImplBase.this.videoSurface = new Surface(surfaceTexture);
            MediaControllerImplBase.this.dispatchRemoteSessionTaskWithPlayerCommandAndWaitForFuture(new RemoteSessionTask() { // from class: androidx.media3.session.MediaControllerImplBase$SurfaceCallback$$ExternalSyntheticLambda2
                @Override // androidx.media3.session.MediaControllerImplBase.RemoteSessionTask
                public final void run(IMediaSession iMediaSession, int i3) {
                    MediaControllerImplBase.SurfaceCallback.$r8$lambda$83wtZSbLSPY3VB0wn6pMXKIaQfQ(this.f$0, iMediaSession, i3);
                }
            });
            MediaControllerImplBase.this.maybeNotifySurfaceSizeChanged(i, i2);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            if (MediaControllerImplBase.this.videoTextureView != null && MediaControllerImplBase.this.videoTextureView.getSurfaceTexture() == surfaceTexture) {
                MediaControllerImplBase.this.videoSurface = null;
                MediaControllerImplBase.this.dispatchRemoteSessionTaskWithPlayerCommandAndWaitForFuture(new RemoteSessionTask() { // from class: androidx.media3.session.MediaControllerImplBase$SurfaceCallback$$ExternalSyntheticLambda3
                    @Override // androidx.media3.session.MediaControllerImplBase.RemoteSessionTask
                    public final void run(IMediaSession iMediaSession, int i) {
                        iMediaSession.setVideoSurface(MediaControllerImplBase.this.controllerStub, i, null);
                    }
                });
                MediaControllerImplBase.this.maybeNotifySurfaceSizeChanged(0, 0);
            }
            return true;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            if (MediaControllerImplBase.this.videoTextureView == null || MediaControllerImplBase.this.videoTextureView.getSurfaceTexture() != surfaceTexture) {
                return;
            }
            MediaControllerImplBase.this.maybeNotifySurfaceSizeChanged(i, i2);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            if (MediaControllerImplBase.this.videoSurfaceHolder != surfaceHolder) {
                return;
            }
            MediaControllerImplBase.this.maybeNotifySurfaceSizeChanged(i2, i3);
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (MediaControllerImplBase.this.videoSurfaceHolder != surfaceHolder) {
                return;
            }
            MediaControllerImplBase.this.videoSurface = surfaceHolder.getSurface();
            MediaControllerImplBase.this.dispatchRemoteSessionTaskWithPlayerCommandAndWaitForFuture(new RemoteSessionTask() { // from class: androidx.media3.session.MediaControllerImplBase$SurfaceCallback$$ExternalSyntheticLambda0
                @Override // androidx.media3.session.MediaControllerImplBase.RemoteSessionTask
                public final void run(IMediaSession iMediaSession, int i) {
                    MediaControllerImplBase.SurfaceCallback.m2017$r8$lambda$Ek7MXRviAuSHRapDOHKdlU9tHI(this.f$0, iMediaSession, i);
                }
            });
            Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
            MediaControllerImplBase.this.maybeNotifySurfaceSizeChanged(surfaceFrame.width(), surfaceFrame.height());
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (MediaControllerImplBase.this.videoSurfaceHolder != surfaceHolder) {
                return;
            }
            MediaControllerImplBase.this.videoSurface = null;
            MediaControllerImplBase.this.dispatchRemoteSessionTaskWithPlayerCommandAndWaitForFuture(new RemoteSessionTask() { // from class: androidx.media3.session.MediaControllerImplBase$SurfaceCallback$$ExternalSyntheticLambda1
                @Override // androidx.media3.session.MediaControllerImplBase.RemoteSessionTask
                public final void run(IMediaSession iMediaSession, int i) {
                    iMediaSession.setVideoSurface(MediaControllerImplBase.this.controllerStub, i, null);
                }
            });
            MediaControllerImplBase.this.maybeNotifySurfaceSizeChanged(0, 0);
        }
    }

    public static /* synthetic */ void $r8$lambda$7I4BMKmkJf0mRdDqvj47uQiuW7s(MediaControllerImplBase mediaControllerImplBase, boolean z, boolean z2, int i, MediaController.Listener listener) {
        ListenableFuture listenableFuture = (ListenableFuture) Assertions.checkNotNull(listener.onSetCustomLayout(mediaControllerImplBase.getInstance(), mediaControllerImplBase.resolvedCustomLayout), "MediaController.Listener#onSetCustomLayout() must not return null");
        if (z) {
            listener.onCustomLayoutChanged(mediaControllerImplBase.getInstance(), mediaControllerImplBase.resolvedCustomLayout);
        }
        if (z2) {
            listener.onMediaButtonPreferencesChanged(mediaControllerImplBase.getInstance(), mediaControllerImplBase.resolvedMediaButtonPreferences);
        }
        mediaControllerImplBase.sendControllerResultWhenReady(i, listenableFuture);
    }

    public static /* synthetic */ void $r8$lambda$AOhBEwtlOUjMmovokcr5fKKmPQQ(MediaControllerImplBase mediaControllerImplBase) {
        MediaController mediaControllerImplBase2 = mediaControllerImplBase.getInstance();
        MediaController mediaControllerImplBase3 = mediaControllerImplBase.getInstance();
        mediaControllerImplBase3.getClass();
        mediaControllerImplBase2.runOnApplicationLooper(new MediaControllerImplBase$$ExternalSyntheticLambda16(mediaControllerImplBase3));
    }

    public static /* synthetic */ void $r8$lambda$JozrRlNWXSGbLKKHBURtUrBgq5E(MediaControllerImplBase mediaControllerImplBase) {
        SessionServiceConnection sessionServiceConnection = mediaControllerImplBase.serviceConnection;
        if (sessionServiceConnection != null) {
            mediaControllerImplBase.context.unbindService(sessionServiceConnection);
            mediaControllerImplBase.serviceConnection = null;
        }
        mediaControllerImplBase.controllerStub.destroy();
    }

    public static /* synthetic */ void $r8$lambda$NPSytgFFZmPGpyg8h8n0O8059xw(MediaControllerImplBase mediaControllerImplBase, boolean z, boolean z2, int i, MediaController.Listener listener) {
        ListenableFuture listenableFuture = (ListenableFuture) Assertions.checkNotNull(listener.onSetCustomLayout(mediaControllerImplBase.getInstance(), mediaControllerImplBase.resolvedCustomLayout), "MediaController.Listener#onSetCustomLayout() must not return null");
        if (z) {
            listener.onCustomLayoutChanged(mediaControllerImplBase.getInstance(), mediaControllerImplBase.resolvedCustomLayout);
        }
        if (z2) {
            listener.onMediaButtonPreferencesChanged(mediaControllerImplBase.getInstance(), mediaControllerImplBase.resolvedMediaButtonPreferences);
        }
        mediaControllerImplBase.sendControllerResultWhenReady(i, listenableFuture);
    }

    public static /* synthetic */ void $r8$lambda$Rf9G6DQurnXWlV26sDdvgNb0UlU(MediaControllerImplBase mediaControllerImplBase, Bundle bundle, boolean z, boolean z2, MediaController.Listener listener) {
        listener.onExtrasChanged(mediaControllerImplBase.getInstance(), bundle);
        if (z) {
            listener.onCustomLayoutChanged(mediaControllerImplBase.getInstance(), mediaControllerImplBase.resolvedCustomLayout);
        }
        if (z2) {
            listener.onMediaButtonPreferencesChanged(mediaControllerImplBase.getInstance(), mediaControllerImplBase.resolvedMediaButtonPreferences);
        }
    }

    public static /* synthetic */ void $r8$lambda$rrUIVY081WE_bH0bYzfaYw0jlBU(MediaControllerImplBase mediaControllerImplBase, ListenableFuture listenableFuture, int i) {
        SessionResult sessionResult;
        mediaControllerImplBase.getClass();
        try {
            sessionResult = (SessionResult) Assertions.checkNotNull((SessionResult) listenableFuture.get(), "SessionResult must not be null");
        } catch (InterruptedException | ExecutionException e) {
            Log.w("MCImplBase", "Session operation failed", e);
            sessionResult = new SessionResult(-1);
        } catch (CancellationException e2) {
            Log.w("MCImplBase", "Session operation cancelled", e2);
            sessionResult = new SessionResult(1);
        }
        mediaControllerImplBase.sendControllerResult(i, sessionResult);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MediaControllerImplBase(Context context, MediaController mediaController, SessionToken sessionToken, Bundle bundle, Looper looper) {
        Player.Commands commands = Player.Commands.EMPTY;
        this.playerCommandsFromSession = commands;
        this.playerCommandsFromPlayer = commands;
        this.intersectedPlayerCommands = createIntersectedCommandsEnsuringCommandReleaseAvailable(commands, commands);
        this.listeners = new ListenerSet(looper, Clock.DEFAULT, new ListenerSet.IterationFinishedEvent() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda19
            @Override // androidx.media3.common.util.ListenerSet.IterationFinishedEvent
            public final void invoke(Object obj, FlagSet flagSet) {
                ((Player.Listener) obj).onEvents(this.f$0.getInstance(), new Player.Events(flagSet));
            }
        });
        this.instance = mediaController;
        Assertions.checkNotNull(context, "context must not be null");
        Assertions.checkNotNull(sessionToken, "token must not be null");
        this.context = context;
        this.sequencedFutureManager = new SequencedFutureManager();
        this.controllerStub = new MediaControllerStub(this);
        this.pendingMaskingSequencedFutureNumbers = new ArraySet();
        this.token = sessionToken;
        this.connectionHints = bundle;
        this.deathRecipient = new IBinder.DeathRecipient() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda20
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                MediaControllerImplBase.$r8$lambda$AOhBEwtlOUjMmovokcr5fKKmPQQ(this.f$0);
            }
        };
        this.surfaceCallback = new SurfaceCallback();
        this.sessionExtras = Bundle.EMPTY;
        this.serviceConnection = sessionToken.getType() != 0 ? new SessionServiceConnection(bundle) : null;
        this.flushCommandQueueHandler = new FlushCommandQueueHandler(looper);
        this.currentPositionMs = -9223372036854775807L;
        this.lastSetPlayWhenReadyCalledTimeMs = -9223372036854775807L;
    }

    private static int convertRepeatModeForNavigation(int i) {
        if (i == 1) {
            return 0;
        }
        return i;
    }

    private static Player.Commands createIntersectedCommandsEnsuringCommandReleaseAvailable(Player.Commands commands, Player.Commands commands2) {
        Player.Commands commandsIntersect = MediaUtils.intersect(commands, commands2);
        return commandsIntersect.contains(32) ? commandsIntersect : commandsIntersect.buildUpon().add(32).build();
    }

    private ListenableFuture dispatchRemoteSessionTask(IMediaSession iMediaSession, RemoteSessionTask remoteSessionTask, boolean z) {
        if (iMediaSession == null) {
            return Futures.immediateFuture(new SessionResult(-4));
        }
        SequencedFutureManager.SequencedFuture sequencedFutureCreateSequencedFuture = this.sequencedFutureManager.createSequencedFuture(new SessionResult(1));
        int sequenceNumber = sequencedFutureCreateSequencedFuture.getSequenceNumber();
        if (z) {
            this.pendingMaskingSequencedFutureNumbers.add(Integer.valueOf(sequenceNumber));
        }
        try {
            remoteSessionTask.run(iMediaSession, sequenceNumber);
            return sequencedFutureCreateSequencedFuture;
        } catch (RemoteException e) {
            Log.w("MCImplBase", "Cannot connect to the service or the session is gone", e);
            this.pendingMaskingSequencedFutureNumbers.remove(Integer.valueOf(sequenceNumber));
            this.sequencedFutureManager.setFutureResult(sequenceNumber, new SessionResult(-100));
            return sequencedFutureCreateSequencedFuture;
        }
    }

    private void dispatchRemoteSessionTaskWithPlayerCommand(RemoteSessionTask remoteSessionTask) {
        this.flushCommandQueueHandler.sendFlushCommandQueueMessage();
        dispatchRemoteSessionTask(this.iSession, remoteSessionTask, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchRemoteSessionTaskWithPlayerCommandAndWaitForFuture(RemoteSessionTask remoteSessionTask) {
        this.flushCommandQueueHandler.sendFlushCommandQueueMessage();
        ListenableFuture listenableFutureDispatchRemoteSessionTask = dispatchRemoteSessionTask(this.iSession, remoteSessionTask, true);
        try {
            LegacyConversions.getFutureResult(listenableFutureDispatchRemoteSessionTask, 3000L);
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        } catch (TimeoutException e2) {
            if (listenableFutureDispatchRemoteSessionTask instanceof SequencedFutureManager.SequencedFuture) {
                int sequenceNumber = ((SequencedFutureManager.SequencedFuture) listenableFutureDispatchRemoteSessionTask).getSequenceNumber();
                this.pendingMaskingSequencedFutureNumbers.remove(Integer.valueOf(sequenceNumber));
                this.sequencedFutureManager.setFutureResult(sequenceNumber, new SessionResult(-1));
            }
            Log.w("MCImplBase", "Synchronous command takes too long on the session side.", e2);
        }
    }

    private static int getCurrentMediaItemIndexInternal(PlayerInfo playerInfo) {
        int i = playerInfo.sessionPositionInfo.positionInfo.mediaItemIndex;
        if (i == -1) {
            return 0;
        }
        return i;
    }

    private PeriodInfo getPeriodInfo(Timeline timeline, int i, long j) {
        if (timeline.isEmpty()) {
            return null;
        }
        Timeline.Window window = new Timeline.Window();
        Timeline.Period period = new Timeline.Period();
        if (i == -1 || i >= timeline.getWindowCount()) {
            i = timeline.getFirstWindowIndex(getShuffleModeEnabled());
            j = timeline.getWindow(i, window).getDefaultPositionMs();
        }
        return getPeriodInfo(timeline, window, period, i, Util.msToUs(j));
    }

    private static PeriodInfo getPeriodInfo(Timeline timeline, Timeline.Window window, Timeline.Period period, int i, long j) {
        Assertions.checkIndex(i, 0, timeline.getWindowCount());
        timeline.getWindow(i, window);
        if (j == -9223372036854775807L) {
            j = window.getDefaultPositionUs();
            if (j == -9223372036854775807L) {
                return null;
            }
        }
        int i2 = window.firstPeriodIndex;
        timeline.getPeriod(i2, period);
        while (i2 < window.lastPeriodIndex && period.positionInWindowUs != j) {
            int i3 = i2 + 1;
            if (timeline.getPeriod(i3, period).positionInWindowUs > j) {
                break;
            }
            i2 = i3;
        }
        timeline.getPeriod(i2, period);
        return new PeriodInfo(i2, j - period.positionInWindowUs);
    }

    private boolean isPlayerCommandAvailable(int i) {
        if (this.intersectedPlayerCommands.contains(i)) {
            return true;
        }
        Log.w("MCImplBase", "Controller isn't allowed to call command= " + i);
        return false;
    }

    private PlayerInfo maskPositionInfo(PlayerInfo playerInfo, Timeline timeline, PeriodInfo periodInfo) {
        int i = playerInfo.sessionPositionInfo.positionInfo.periodIndex;
        int i2 = periodInfo.index;
        Timeline.Period period = new Timeline.Period();
        timeline.getPeriod(i, period);
        Timeline.Period period2 = new Timeline.Period();
        timeline.getPeriod(i2, period2);
        boolean z = i != i2;
        long j = periodInfo.periodPositionUs;
        long jMsToUs = Util.msToUs(getCurrentPosition()) - period.getPositionInWindowUs();
        if (!z && j == jMsToUs) {
            return playerInfo;
        }
        Assertions.checkState(playerInfo.sessionPositionInfo.positionInfo.adGroupIndex == -1);
        Player.PositionInfo positionInfo = new Player.PositionInfo(null, period.windowIndex, playerInfo.sessionPositionInfo.positionInfo.mediaItem, null, i, Util.usToMs(period.positionInWindowUs + jMsToUs), Util.usToMs(period.positionInWindowUs + jMsToUs), -1, -1);
        timeline.getPeriod(i2, period2);
        Timeline.Window window = new Timeline.Window();
        timeline.getWindow(period2.windowIndex, window);
        Player.PositionInfo positionInfo2 = new Player.PositionInfo(null, period2.windowIndex, window.mediaItem, null, i2, Util.usToMs(period2.positionInWindowUs + j), Util.usToMs(period2.positionInWindowUs + j), -1, -1);
        PlayerInfo playerInfoCopyWithPositionInfos = playerInfo.copyWithPositionInfos(positionInfo, positionInfo2, 1);
        if (z || j < jMsToUs) {
            return playerInfoCopyWithPositionInfos.copyWithSessionPositionInfo(new SessionPositionInfo(positionInfo2, false, SystemClock.elapsedRealtime(), window.getDurationMs(), Util.usToMs(period2.positionInWindowUs + j), MediaUtils.calculateBufferedPercentage(Util.usToMs(period2.positionInWindowUs + j), window.getDurationMs()), 0L, -9223372036854775807L, -9223372036854775807L, Util.usToMs(period2.positionInWindowUs + j)));
        }
        long jMax = Math.max(0L, Util.msToUs(playerInfoCopyWithPositionInfos.sessionPositionInfo.totalBufferedDurationMs) - (j - jMsToUs));
        long j2 = j + jMax;
        return playerInfoCopyWithPositionInfos.copyWithSessionPositionInfo(new SessionPositionInfo(positionInfo2, false, SystemClock.elapsedRealtime(), window.getDurationMs(), Util.usToMs(j2), MediaUtils.calculateBufferedPercentage(Util.usToMs(j2), window.getDurationMs()), Util.usToMs(jMax), -9223372036854775807L, -9223372036854775807L, Util.usToMs(j2)));
    }

    private static PlayerInfo maskTimelineAndPositionInfo(PlayerInfo playerInfo, Timeline timeline, Player.PositionInfo positionInfo, SessionPositionInfo sessionPositionInfo, int i) {
        return new PlayerInfo.Builder(playerInfo).setTimeline(timeline).setOldPositionInfo(playerInfo.sessionPositionInfo.positionInfo).setNewPositionInfo(positionInfo).setSessionPositionInfo(sessionPositionInfo).setDiscontinuityReason(i).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeNotifySurfaceSizeChanged(final int i, final int i2) {
        if (this.surfaceSize.getWidth() == i && this.surfaceSize.getHeight() == i2) {
            return;
        }
        this.surfaceSize = new Size(i, i2);
        this.listeners.sendEvent(24, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda52
            @Override // androidx.media3.common.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((Player.Listener) obj).onSurfaceSizeChanged(i, i2);
            }
        });
    }

    private void notifyPlayerInfoListenersWithReasons(PlayerInfo playerInfo, final PlayerInfo playerInfo2, final Integer num, final Integer num2, final Integer num3, final Integer num4) {
        if (num != null) {
            this.listeners.queueEvent(0, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda23
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onTimelineChanged(playerInfo2.timeline, num.intValue());
                }
            });
        }
        if (num3 != null) {
            this.listeners.queueEvent(11, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda34
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    PlayerInfo playerInfo3 = playerInfo2;
                    ((Player.Listener) obj).onPositionDiscontinuity(playerInfo3.oldPositionInfo, playerInfo3.newPositionInfo, num3.intValue());
                }
            });
        }
        final MediaItem currentMediaItem = playerInfo2.getCurrentMediaItem();
        if (num4 != null) {
            this.listeners.queueEvent(1, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda42
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onMediaItemTransition(currentMediaItem, num4.intValue());
                }
            });
        }
        PlaybackException playbackException = playerInfo.playerError;
        final PlaybackException playbackException2 = playerInfo2.playerError;
        if (playbackException != playbackException2 && (playbackException == null || !playbackException.errorInfoEquals(playbackException2))) {
            this.listeners.queueEvent(10, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda43
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onPlayerErrorChanged(playbackException2);
                }
            });
            if (playbackException2 != null) {
                this.listeners.queueEvent(10, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda44
                    @Override // androidx.media3.common.util.ListenerSet.Event
                    public final void invoke(Object obj) {
                        ((Player.Listener) obj).onPlayerError(playbackException2);
                    }
                });
            }
        }
        if (!playerInfo.currentTracks.equals(playerInfo2.currentTracks)) {
            this.listeners.queueEvent(2, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda45
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onTracksChanged(playerInfo2.currentTracks);
                }
            });
        }
        if (!playerInfo.mediaMetadata.equals(playerInfo2.mediaMetadata)) {
            this.listeners.queueEvent(14, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda46
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onMediaMetadataChanged(playerInfo2.mediaMetadata);
                }
            });
        }
        if (playerInfo.isLoading != playerInfo2.isLoading) {
            this.listeners.queueEvent(3, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda47
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onIsLoadingChanged(playerInfo2.isLoading);
                }
            });
        }
        if (playerInfo.playbackState != playerInfo2.playbackState) {
            this.listeners.queueEvent(4, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda48
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onPlaybackStateChanged(playerInfo2.playbackState);
                }
            });
        }
        if (num2 != null) {
            this.listeners.queueEvent(5, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda49
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onPlayWhenReadyChanged(playerInfo2.playWhenReady, num2.intValue());
                }
            });
        }
        if (playerInfo.playbackSuppressionReason != playerInfo2.playbackSuppressionReason) {
            this.listeners.queueEvent(6, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda24
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onPlaybackSuppressionReasonChanged(playerInfo2.playbackSuppressionReason);
                }
            });
        }
        if (playerInfo.isPlaying != playerInfo2.isPlaying) {
            this.listeners.queueEvent(7, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda25
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onIsPlayingChanged(playerInfo2.isPlaying);
                }
            });
        }
        if (!playerInfo.playbackParameters.equals(playerInfo2.playbackParameters)) {
            this.listeners.queueEvent(12, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda26
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onPlaybackParametersChanged(playerInfo2.playbackParameters);
                }
            });
        }
        if (playerInfo.repeatMode != playerInfo2.repeatMode) {
            this.listeners.queueEvent(8, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda27
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onRepeatModeChanged(playerInfo2.repeatMode);
                }
            });
        }
        if (playerInfo.shuffleModeEnabled != playerInfo2.shuffleModeEnabled) {
            this.listeners.queueEvent(9, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda28
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onShuffleModeEnabledChanged(playerInfo2.shuffleModeEnabled);
                }
            });
        }
        if (!playerInfo.playlistMetadata.equals(playerInfo2.playlistMetadata)) {
            this.listeners.queueEvent(15, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda29
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onPlaylistMetadataChanged(playerInfo2.playlistMetadata);
                }
            });
        }
        if (playerInfo.volume != playerInfo2.volume) {
            this.listeners.queueEvent(22, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda30
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onVolumeChanged(playerInfo2.volume);
                }
            });
        }
        if (!playerInfo.audioAttributes.equals(playerInfo2.audioAttributes)) {
            this.listeners.queueEvent(20, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda31
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onAudioAttributesChanged(playerInfo2.audioAttributes);
                }
            });
        }
        if (!playerInfo.cueGroup.cues.equals(playerInfo2.cueGroup.cues)) {
            this.listeners.queueEvent(27, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda32
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onCues(playerInfo2.cueGroup.cues);
                }
            });
            this.listeners.queueEvent(27, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda33
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onCues(playerInfo2.cueGroup);
                }
            });
        }
        if (!playerInfo.deviceInfo.equals(playerInfo2.deviceInfo)) {
            this.listeners.queueEvent(29, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda35
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onDeviceInfoChanged(playerInfo2.deviceInfo);
                }
            });
        }
        if (playerInfo.deviceVolume != playerInfo2.deviceVolume || playerInfo.deviceMuted != playerInfo2.deviceMuted) {
            this.listeners.queueEvent(30, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda36
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    PlayerInfo playerInfo3 = playerInfo2;
                    ((Player.Listener) obj).onDeviceVolumeChanged(playerInfo3.deviceVolume, playerInfo3.deviceMuted);
                }
            });
        }
        if (!playerInfo.videoSize.equals(playerInfo2.videoSize)) {
            this.listeners.queueEvent(25, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda37
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onVideoSizeChanged(playerInfo2.videoSize);
                }
            });
        }
        if (playerInfo.seekBackIncrementMs != playerInfo2.seekBackIncrementMs) {
            this.listeners.queueEvent(16, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda38
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onSeekBackIncrementChanged(playerInfo2.seekBackIncrementMs);
                }
            });
        }
        if (playerInfo.seekForwardIncrementMs != playerInfo2.seekForwardIncrementMs) {
            this.listeners.queueEvent(17, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda39
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onSeekForwardIncrementChanged(playerInfo2.seekForwardIncrementMs);
                }
            });
        }
        if (playerInfo.maxSeekToPreviousPositionMs != playerInfo2.maxSeekToPreviousPositionMs) {
            this.listeners.queueEvent(18, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda40
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onMaxSeekToPreviousPositionChanged(playerInfo2.maxSeekToPreviousPositionMs);
                }
            });
        }
        if (!playerInfo.trackSelectionParameters.equals(playerInfo2.trackSelectionParameters)) {
            this.listeners.queueEvent(19, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda41
                @Override // androidx.media3.common.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.Listener) obj).onTrackSelectionParametersChanged(playerInfo2.trackSelectionParameters);
                }
            });
        }
        this.listeners.flushEvents();
    }

    private boolean requestConnectToService() {
        int i = Util.SDK_INT >= 29 ? 4097 : 1;
        Intent intent = new Intent("androidx.media3.session.MediaSessionService");
        intent.setClassName(this.token.getPackageName(), this.token.getServiceName());
        if (this.context.bindService(intent, this.serviceConnection, i)) {
            return true;
        }
        Log.w("MCImplBase", "bind to " + this.token + " failed");
        return false;
    }

    private boolean requestConnectToSession(Bundle bundle) {
        try {
            IMediaSession.Stub.asInterface((IBinder) Assertions.checkStateNotNull(this.token.getBinder())).connect(this.controllerStub, this.sequencedFutureManager.obtainNextSequenceNumber(), new ConnectionRequest(this.context.getPackageName(), Process.myPid(), bundle, this.instance.getMaxCommandsForMediaItems()).toBundle());
            return true;
        } catch (RemoteException e) {
            Log.w("MCImplBase", "Failed to call connection request.", e);
            return false;
        }
    }

    private static ImmutableList resolveCustomLayout(List list, List list2, Bundle bundle, SessionCommands sessionCommands, Player.Commands commands) {
        if (!list2.isEmpty()) {
            return CommandButton.copyWithUnavailableButtonsDisabled(list2, sessionCommands, commands);
        }
        boolean z = false;
        boolean z2 = (bundle.getBoolean("android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_PREVIOUS") || commands.containsAny(6, 7)) ? false : true;
        if (!bundle.getBoolean("android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_NEXT") && !commands.containsAny(8, 9)) {
            z = true;
        }
        return CommandButton.getCustomLayoutFromMediaButtonPreferences(list, z2, z);
    }

    private static ImmutableList resolveMediaButtonPreferences(List list, List list2, SessionCommands sessionCommands, Player.Commands commands, Bundle bundle) {
        if (list.isEmpty()) {
            list = CommandButton.getMediaButtonPreferencesFromCustomLayout(list2, commands, bundle);
        }
        return CommandButton.copyWithUnavailableButtonsDisabled(list, sessionCommands, commands);
    }

    private void seekToInternal(int i, long j) {
        int i2;
        int i3;
        PlayerInfo playerInfoMaskPositionInfo;
        Timeline timeline = this.playerInfo.timeline;
        if ((timeline.isEmpty() || i < timeline.getWindowCount()) && !isPlayingAd()) {
            int i4 = getPlaybackState() == 1 ? 1 : 2;
            PlayerInfo playerInfo = this.playerInfo;
            PlayerInfo playerInfoCopyWithPlaybackState = playerInfo.copyWithPlaybackState(i4, playerInfo.playerError);
            PeriodInfo periodInfo = getPeriodInfo(timeline, i, j);
            if (periodInfo == null) {
                i2 = 1;
                i3 = 2;
                Player.PositionInfo positionInfo = new Player.PositionInfo(null, i, null, null, i, j == -9223372036854775807L ? 0L : j, j == -9223372036854775807L ? 0L : j, -1, -1);
                PlayerInfo playerInfo2 = this.playerInfo;
                Timeline timeline2 = playerInfo2.timeline;
                boolean z = this.playerInfo.sessionPositionInfo.isPlayingAd;
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                SessionPositionInfo sessionPositionInfo = this.playerInfo.sessionPositionInfo;
                playerInfoMaskPositionInfo = maskTimelineAndPositionInfo(playerInfo2, timeline2, positionInfo, new SessionPositionInfo(positionInfo, z, jElapsedRealtime, sessionPositionInfo.durationMs, j == -9223372036854775807L ? 0L : j, 0, 0L, sessionPositionInfo.currentLiveOffsetMs, sessionPositionInfo.contentDurationMs, j == -9223372036854775807L ? 0L : j), 1);
            } else {
                i2 = 1;
                i3 = 2;
                playerInfoMaskPositionInfo = maskPositionInfo(playerInfoCopyWithPlaybackState, timeline, periodInfo);
            }
            int i5 = (this.playerInfo.timeline.isEmpty() || playerInfoMaskPositionInfo.sessionPositionInfo.positionInfo.mediaItemIndex == this.playerInfo.sessionPositionInfo.positionInfo.mediaItemIndex) ? 0 : i2;
            if (i5 == 0 && playerInfoMaskPositionInfo.sessionPositionInfo.positionInfo.positionMs == this.playerInfo.sessionPositionInfo.positionInfo.positionMs) {
                return;
            }
            updatePlayerInfo(playerInfoMaskPositionInfo, null, null, Integer.valueOf(i2), i5 != 0 ? Integer.valueOf(i3) : null);
        }
    }

    private void sendControllerResult(int i, SessionResult sessionResult) {
        IMediaSession iMediaSession = this.iSession;
        if (iMediaSession == null) {
            return;
        }
        try {
            iMediaSession.onControllerResult(this.controllerStub, i, sessionResult.toBundle());
        } catch (RemoteException unused) {
            Log.w("MCImplBase", "Error in sending");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendControllerResultWhenReady(final int i, final ListenableFuture listenableFuture) {
        listenableFuture.addListener(new Runnable() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                MediaControllerImplBase.$r8$lambda$rrUIVY081WE_bH0bYzfaYw0jlBU(this.f$0, listenableFuture, i);
            }
        }, MoreExecutors.directExecutor());
    }

    private void setPlayWhenReady(boolean z, int i) {
        int playbackSuppressionReason = getPlaybackSuppressionReason();
        if (playbackSuppressionReason == 1) {
            playbackSuppressionReason = 0;
        }
        PlayerInfo playerInfo = this.playerInfo;
        if (playerInfo.playWhenReady == z && playerInfo.playbackSuppressionReason == playbackSuppressionReason) {
            return;
        }
        this.currentPositionMs = MediaUtils.getUpdatedCurrentPositionMs(playerInfo, this.currentPositionMs, this.lastSetPlayWhenReadyCalledTimeMs, getInstance().getTimeDiffMs());
        this.lastSetPlayWhenReadyCalledTimeMs = SystemClock.elapsedRealtime();
        updatePlayerInfo(this.playerInfo.copyWithPlayWhenReady(z, i, playbackSuppressionReason), null, Integer.valueOf(i), null, null);
    }

    private void updatePlayerInfo(PlayerInfo playerInfo, Integer num, Integer num2, Integer num3, Integer num4) {
        PlayerInfo playerInfo2 = this.playerInfo;
        this.playerInfo = playerInfo;
        notifyPlayerInfoListenersWithReasons(playerInfo2, playerInfo, num, num2, num3, num4);
    }

    private void updateSessionPositionInfoIfNeeded(SessionPositionInfo sessionPositionInfo) {
        if (this.pendingMaskingSequencedFutureNumbers.isEmpty()) {
            SessionPositionInfo sessionPositionInfo2 = this.playerInfo.sessionPositionInfo;
            if (sessionPositionInfo2.eventTimeMs >= sessionPositionInfo.eventTimeMs || !MediaUtils.areSessionPositionInfosInSamePeriodOrAd(sessionPositionInfo, sessionPositionInfo2)) {
                return;
            }
            this.playerInfo = this.playerInfo.copyWithSessionPositionInfo(sessionPositionInfo);
        }
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void addListener(Player.Listener listener) {
        this.listeners.add(listener);
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void connect() {
        boolean zRequestConnectToService;
        if (this.token.getType() == 0) {
            this.serviceConnection = null;
            zRequestConnectToService = requestConnectToSession(this.connectionHints);
        } else {
            this.serviceConnection = new SessionServiceConnection(this.connectionHints);
            zRequestConnectToService = requestConnectToService();
        }
        if (zRequestConnectToService) {
            return;
        }
        MediaController mediaControllerImplBase = getInstance();
        MediaController mediaControllerImplBase2 = getInstance();
        mediaControllerImplBase2.getClass();
        mediaControllerImplBase.runOnApplicationLooper(new MediaControllerImplBase$$ExternalSyntheticLambda16(mediaControllerImplBase2));
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public Player.Commands getAvailableCommands() {
        return this.intersectedPlayerCommands;
    }

    public SessionToken getConnectedToken() {
        return this.connectedToken;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public Bundle getConnectionHints() {
        return this.connectionHints;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public long getContentDuration() {
        return this.playerInfo.sessionPositionInfo.contentDurationMs;
    }

    public Context getContext() {
        return this.context;
    }

    public int getCurrentMediaItemIndex() {
        return getCurrentMediaItemIndexInternal(this.playerInfo);
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public long getCurrentPosition() {
        long updatedCurrentPositionMs = MediaUtils.getUpdatedCurrentPositionMs(this.playerInfo, this.currentPositionMs, this.lastSetPlayWhenReadyCalledTimeMs, getInstance().getTimeDiffMs());
        this.currentPositionMs = updatedCurrentPositionMs;
        return updatedCurrentPositionMs;
    }

    public Timeline getCurrentTimeline() {
        return this.playerInfo.timeline;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public long getDuration() {
        return this.playerInfo.sessionPositionInfo.durationMs;
    }

    MediaController getInstance() {
        return this.instance;
    }

    public long getMaxSeekToPreviousPosition() {
        return this.playerInfo.maxSeekToPreviousPositionMs;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public MediaMetadata getMediaMetadata() {
        return this.playerInfo.mediaMetadata;
    }

    public int getNextMediaItemIndex() {
        if (this.playerInfo.timeline.isEmpty()) {
            return -1;
        }
        return this.playerInfo.timeline.getNextWindowIndex(getCurrentMediaItemIndex(), convertRepeatModeForNavigation(this.playerInfo.repeatMode), this.playerInfo.shuffleModeEnabled);
    }

    public int getPlaybackState() {
        return this.playerInfo.playbackState;
    }

    public int getPlaybackSuppressionReason() {
        return this.playerInfo.playbackSuppressionReason;
    }

    public int getPreviousMediaItemIndex() {
        if (this.playerInfo.timeline.isEmpty()) {
            return -1;
        }
        return this.playerInfo.timeline.getPreviousWindowIndex(getCurrentMediaItemIndex(), convertRepeatModeForNavigation(this.playerInfo.repeatMode), this.playerInfo.shuffleModeEnabled);
    }

    public boolean getShuffleModeEnabled() {
        return this.playerInfo.shuffleModeEnabled;
    }

    public boolean hasNextMediaItem() {
        return getNextMediaItemIndex() != -1;
    }

    public boolean hasPreviousMediaItem() {
        return getPreviousMediaItemIndex() != -1;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public boolean isConnected() {
        return this.iSession != null;
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public boolean isPlaying() {
        return this.playerInfo.isPlaying;
    }

    public boolean isPlayingAd() {
        return this.playerInfo.sessionPositionInfo.isPlayingAd;
    }

    boolean isReleased() {
        return this.released;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyPeriodicSessionPositionInfoChanged(SessionPositionInfo sessionPositionInfo) {
        if (isConnected()) {
            updateSessionPositionInfoIfNeeded(sessionPositionInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onAvailableCommandsChangedFromPlayer(Player.Commands commands) {
        boolean z;
        boolean z2;
        if (isConnected() && !Util.areEqual(this.playerCommandsFromPlayer, commands)) {
            this.playerCommandsFromPlayer = commands;
            Player.Commands commands2 = this.intersectedPlayerCommands;
            Player.Commands commandsCreateIntersectedCommandsEnsuringCommandReleaseAvailable = createIntersectedCommandsEnsuringCommandReleaseAvailable(this.playerCommandsFromSession, commands);
            this.intersectedPlayerCommands = commandsCreateIntersectedCommandsEnsuringCommandReleaseAvailable;
            if (Util.areEqual(commandsCreateIntersectedCommandsEnsuringCommandReleaseAvailable, commands2)) {
                z = false;
                z2 = false;
            } else {
                ImmutableList immutableList = this.resolvedMediaButtonPreferences;
                ImmutableList immutableList2 = this.resolvedCustomLayout;
                ImmutableList immutableListResolveMediaButtonPreferences = resolveMediaButtonPreferences(this.mediaButtonPreferencesOriginal, this.customLayoutOriginal, this.sessionCommands, this.intersectedPlayerCommands, this.sessionExtras);
                this.resolvedMediaButtonPreferences = immutableListResolveMediaButtonPreferences;
                this.resolvedCustomLayout = resolveCustomLayout(immutableListResolveMediaButtonPreferences, this.customLayoutOriginal, this.sessionExtras, this.sessionCommands, this.intersectedPlayerCommands);
                z = !this.resolvedMediaButtonPreferences.equals(immutableList);
                z2 = !this.resolvedCustomLayout.equals(immutableList2);
                this.listeners.sendEvent(13, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda11
                    @Override // androidx.media3.common.util.ListenerSet.Event
                    public final void invoke(Object obj) {
                        ((Player.Listener) obj).onAvailableCommandsChanged(this.f$0.intersectedPlayerCommands);
                    }
                });
            }
            if (z2) {
                getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda12
                    @Override // androidx.media3.common.util.Consumer
                    public final void accept(Object obj) {
                        MediaControllerImplBase mediaControllerImplBase = this.f$0;
                        ((MediaController.Listener) obj).onCustomLayoutChanged(mediaControllerImplBase.getInstance(), mediaControllerImplBase.resolvedCustomLayout);
                    }
                });
            }
            if (z) {
                getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda13
                    @Override // androidx.media3.common.util.Consumer
                    public final void accept(Object obj) {
                        MediaControllerImplBase mediaControllerImplBase = this.f$0;
                        ((MediaController.Listener) obj).onMediaButtonPreferencesChanged(mediaControllerImplBase.getInstance(), mediaControllerImplBase.resolvedMediaButtonPreferences);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onAvailableCommandsChangedFromSession(final SessionCommands sessionCommands, Player.Commands commands) {
        boolean z;
        boolean z2;
        if (isConnected()) {
            boolean zAreEqual = Util.areEqual(this.playerCommandsFromSession, commands);
            boolean zAreEqual2 = Util.areEqual(this.sessionCommands, sessionCommands);
            if (zAreEqual && zAreEqual2) {
                return;
            }
            this.sessionCommands = sessionCommands;
            boolean z3 = false;
            if (zAreEqual) {
                z = false;
            } else {
                this.playerCommandsFromSession = commands;
                Player.Commands commands2 = this.intersectedPlayerCommands;
                Player.Commands commandsCreateIntersectedCommandsEnsuringCommandReleaseAvailable = createIntersectedCommandsEnsuringCommandReleaseAvailable(commands, this.playerCommandsFromPlayer);
                this.intersectedPlayerCommands = commandsCreateIntersectedCommandsEnsuringCommandReleaseAvailable;
                z = !Util.areEqual(commandsCreateIntersectedCommandsEnsuringCommandReleaseAvailable, commands2);
            }
            if (!zAreEqual2 || z) {
                ImmutableList immutableList = this.resolvedMediaButtonPreferences;
                ImmutableList immutableList2 = this.resolvedCustomLayout;
                ImmutableList immutableListResolveMediaButtonPreferences = resolveMediaButtonPreferences(this.mediaButtonPreferencesOriginal, this.customLayoutOriginal, sessionCommands, this.intersectedPlayerCommands, this.sessionExtras);
                this.resolvedMediaButtonPreferences = immutableListResolveMediaButtonPreferences;
                this.resolvedCustomLayout = resolveCustomLayout(immutableListResolveMediaButtonPreferences, this.customLayoutOriginal, this.sessionExtras, sessionCommands, this.intersectedPlayerCommands);
                z2 = !this.resolvedMediaButtonPreferences.equals(immutableList);
                z3 = !this.resolvedCustomLayout.equals(immutableList2);
            } else {
                z2 = false;
            }
            if (z) {
                this.listeners.sendEvent(13, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda7
                    @Override // androidx.media3.common.util.ListenerSet.Event
                    public final void invoke(Object obj) {
                        ((Player.Listener) obj).onAvailableCommandsChanged(this.f$0.intersectedPlayerCommands);
                    }
                });
            }
            if (!zAreEqual2) {
                getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda8
                    @Override // androidx.media3.common.util.Consumer
                    public final void accept(Object obj) {
                        MediaController.Listener listener = (MediaController.Listener) obj;
                        listener.onAvailableSessionCommandsChanged(this.f$0.getInstance(), sessionCommands);
                    }
                });
            }
            if (z3) {
                getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda9
                    @Override // androidx.media3.common.util.Consumer
                    public final void accept(Object obj) {
                        MediaControllerImplBase mediaControllerImplBase = this.f$0;
                        ((MediaController.Listener) obj).onCustomLayoutChanged(mediaControllerImplBase.getInstance(), mediaControllerImplBase.resolvedCustomLayout);
                    }
                });
            }
            if (z2) {
                getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda10
                    @Override // androidx.media3.common.util.Consumer
                    public final void accept(Object obj) {
                        MediaControllerImplBase mediaControllerImplBase = this.f$0;
                        ((MediaController.Listener) obj).onMediaButtonPreferencesChanged(mediaControllerImplBase.getInstance(), mediaControllerImplBase.resolvedMediaButtonPreferences);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onConnected(ConnectionState connectionState) {
        if (this.iSession != null) {
            Log.e("MCImplBase", "Cannot be notified about the connection result many times. Probably a bug or malicious app.");
            getInstance().release();
            return;
        }
        this.iSession = connectionState.sessionBinder;
        this.sessionActivity = connectionState.sessionActivity;
        this.sessionCommands = connectionState.sessionCommands;
        Player.Commands commands = connectionState.playerCommandsFromSession;
        this.playerCommandsFromSession = commands;
        Player.Commands commands2 = connectionState.playerCommandsFromPlayer;
        this.playerCommandsFromPlayer = commands2;
        Player.Commands commandsCreateIntersectedCommandsEnsuringCommandReleaseAvailable = createIntersectedCommandsEnsuringCommandReleaseAvailable(commands, commands2);
        this.intersectedPlayerCommands = commandsCreateIntersectedCommandsEnsuringCommandReleaseAvailable;
        ImmutableList immutableList = connectionState.customLayout;
        this.customLayoutOriginal = immutableList;
        ImmutableList immutableList2 = connectionState.mediaButtonPreferences;
        this.mediaButtonPreferencesOriginal = immutableList2;
        ImmutableList immutableListResolveMediaButtonPreferences = resolveMediaButtonPreferences(immutableList2, immutableList, this.sessionCommands, commandsCreateIntersectedCommandsEnsuringCommandReleaseAvailable, connectionState.sessionExtras);
        this.resolvedMediaButtonPreferences = immutableListResolveMediaButtonPreferences;
        this.resolvedCustomLayout = resolveCustomLayout(immutableListResolveMediaButtonPreferences, this.customLayoutOriginal, connectionState.sessionExtras, this.sessionCommands, this.intersectedPlayerCommands);
        ImmutableMap.Builder builder = new ImmutableMap.Builder();
        for (int i = 0; i < connectionState.commandButtonsForMediaItems.size(); i++) {
            CommandButton commandButton = (CommandButton) connectionState.commandButtonsForMediaItems.get(i);
            SessionCommand sessionCommand = commandButton.sessionCommand;
            if (sessionCommand != null && sessionCommand.commandCode == 0) {
                builder.put(sessionCommand.customAction, commandButton);
            }
        }
        this.commandButtonsForMediaItemsMap = builder.buildOrThrow();
        this.playerInfo = connectionState.playerInfo;
        MediaSession.Token platformToken = connectionState.platformToken;
        if (platformToken == null) {
            platformToken = this.token.getPlatformToken();
        }
        MediaSession.Token token = platformToken;
        if (token != null) {
            this.platformController = new android.media.session.MediaController(this.context, token);
        }
        try {
            connectionState.sessionBinder.asBinder().linkToDeath(this.deathRecipient, 0);
            this.connectedToken = new SessionToken(this.token.getUid(), 0, connectionState.libraryVersion, connectionState.sessionInterfaceVersion, this.token.getPackageName(), connectionState.sessionBinder, connectionState.tokenExtras, token);
            this.sessionExtras = connectionState.sessionExtras;
            getInstance().notifyAccepted();
        } catch (RemoteException unused) {
            getInstance().release();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onCustomCommand(final int i, final SessionCommand sessionCommand, final Bundle bundle) {
        if (isConnected()) {
            getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda4
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    MediaControllerImplBase mediaControllerImplBase = this.f$0;
                    mediaControllerImplBase.sendControllerResultWhenReady(i, (ListenableFuture) Assertions.checkNotNull(((MediaController.Listener) obj).onCustomCommand(mediaControllerImplBase.getInstance(), sessionCommand, bundle), "ControllerCallback#onCustomCommand() must not return null"));
                }
            });
        }
    }

    public void onError(int i, final SessionError sessionError) {
        if (isConnected()) {
            getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda6
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    MediaController.Listener listener = (MediaController.Listener) obj;
                    listener.onError(this.f$0.getInstance(), sessionError);
                }
            });
        }
    }

    public void onExtrasChanged(final Bundle bundle) {
        if (isConnected()) {
            ImmutableList immutableList = this.resolvedMediaButtonPreferences;
            ImmutableList immutableList2 = this.resolvedCustomLayout;
            this.sessionExtras = bundle;
            ImmutableList immutableListResolveMediaButtonPreferences = resolveMediaButtonPreferences(this.mediaButtonPreferencesOriginal, this.customLayoutOriginal, this.sessionCommands, this.intersectedPlayerCommands, bundle);
            this.resolvedMediaButtonPreferences = immutableListResolveMediaButtonPreferences;
            this.resolvedCustomLayout = resolveCustomLayout(immutableListResolveMediaButtonPreferences, this.customLayoutOriginal, this.sessionExtras, this.sessionCommands, this.intersectedPlayerCommands);
            final boolean z = !this.resolvedMediaButtonPreferences.equals(immutableList);
            final boolean z2 = !this.resolvedCustomLayout.equals(immutableList2);
            getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda1
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    MediaControllerImplBase.$r8$lambda$Rf9G6DQurnXWlV26sDdvgNb0UlU(this.f$0, bundle, z2, z, (MediaController.Listener) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onPlayerInfoChanged(PlayerInfo playerInfo, PlayerInfo.BundlingExclusions bundlingExclusions) {
        PlayerInfo.BundlingExclusions bundlingExclusions2;
        if (isConnected()) {
            PlayerInfo playerInfo2 = this.pendingPlayerInfo;
            if (playerInfo2 != null && (bundlingExclusions2 = this.pendingBundlingExclusions) != null) {
                Pair pairMergePlayerInfo = MediaUtils.mergePlayerInfo(playerInfo2, bundlingExclusions2, playerInfo, bundlingExclusions, this.intersectedPlayerCommands);
                PlayerInfo playerInfo3 = (PlayerInfo) pairMergePlayerInfo.first;
                bundlingExclusions = (PlayerInfo.BundlingExclusions) pairMergePlayerInfo.second;
                playerInfo = playerInfo3;
            }
            this.pendingPlayerInfo = null;
            this.pendingBundlingExclusions = null;
            if (!this.pendingMaskingSequencedFutureNumbers.isEmpty()) {
                this.pendingPlayerInfo = playerInfo;
                this.pendingBundlingExclusions = bundlingExclusions;
                return;
            }
            PlayerInfo playerInfo4 = this.playerInfo;
            PlayerInfo playerInfo5 = (PlayerInfo) MediaUtils.mergePlayerInfo(playerInfo4, PlayerInfo.BundlingExclusions.NONE, playerInfo, bundlingExclusions, this.intersectedPlayerCommands).first;
            this.playerInfo = playerInfo5;
            Integer numValueOf = (playerInfo4.oldPositionInfo.equals(playerInfo.oldPositionInfo) && playerInfo4.newPositionInfo.equals(playerInfo.newPositionInfo)) ? null : Integer.valueOf(playerInfo5.discontinuityReason);
            Integer numValueOf2 = !Util.areEqual(playerInfo4.getCurrentMediaItem(), playerInfo5.getCurrentMediaItem()) ? Integer.valueOf(playerInfo5.mediaItemTransitionReason) : null;
            Integer numValueOf3 = !playerInfo4.timeline.equals(playerInfo5.timeline) ? Integer.valueOf(playerInfo5.timelineChangeReason) : null;
            int i = playerInfo4.playWhenReadyChangeReason;
            int i2 = playerInfo5.playWhenReadyChangeReason;
            notifyPlayerInfoListenersWithReasons(playerInfo4, playerInfo5, numValueOf3, (i == i2 && playerInfo4.playWhenReady == playerInfo5.playWhenReady) ? null : Integer.valueOf(i2), numValueOf, numValueOf2);
        }
    }

    public void onRenderedFirstFrame() {
        this.listeners.sendEvent(26, new ListenerSet.Event() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda0
            @Override // androidx.media3.common.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((Player.Listener) obj).onRenderedFirstFrame();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onSetCustomLayout(final int i, List list) {
        if (isConnected()) {
            ImmutableList immutableList = this.resolvedMediaButtonPreferences;
            ImmutableList immutableList2 = this.resolvedCustomLayout;
            this.customLayoutOriginal = ImmutableList.copyOf((Collection) list);
            ImmutableList immutableListResolveMediaButtonPreferences = resolveMediaButtonPreferences(this.mediaButtonPreferencesOriginal, list, this.sessionCommands, this.intersectedPlayerCommands, this.sessionExtras);
            this.resolvedMediaButtonPreferences = immutableListResolveMediaButtonPreferences;
            this.resolvedCustomLayout = resolveCustomLayout(immutableListResolveMediaButtonPreferences, list, this.sessionExtras, this.sessionCommands, this.intersectedPlayerCommands);
            final boolean z = !this.resolvedMediaButtonPreferences.equals(immutableList);
            final boolean z2 = !this.resolvedCustomLayout.equals(immutableList2);
            getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda5
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    MediaControllerImplBase.$r8$lambda$7I4BMKmkJf0mRdDqvj47uQiuW7s(this.f$0, z2, z, i, (MediaController.Listener) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onSetMediaButtonPreferences(final int i, List list) {
        if (isConnected()) {
            ImmutableList immutableList = this.resolvedMediaButtonPreferences;
            ImmutableList immutableList2 = this.resolvedCustomLayout;
            this.mediaButtonPreferencesOriginal = ImmutableList.copyOf((Collection) list);
            ImmutableList immutableListResolveMediaButtonPreferences = resolveMediaButtonPreferences(list, this.customLayoutOriginal, this.sessionCommands, this.intersectedPlayerCommands, this.sessionExtras);
            this.resolvedMediaButtonPreferences = immutableListResolveMediaButtonPreferences;
            this.resolvedCustomLayout = resolveCustomLayout(immutableListResolveMediaButtonPreferences, this.customLayoutOriginal, this.sessionExtras, this.sessionCommands, this.intersectedPlayerCommands);
            final boolean z = !this.resolvedMediaButtonPreferences.equals(immutableList);
            final boolean z2 = !this.resolvedCustomLayout.equals(immutableList2);
            getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda2
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    MediaControllerImplBase.$r8$lambda$NPSytgFFZmPGpyg8h8n0O8059xw(this.f$0, z2, z, i, (MediaController.Listener) obj);
                }
            });
        }
    }

    public void onSetSessionActivity(int i, final PendingIntent pendingIntent) {
        if (isConnected()) {
            this.sessionActivity = pendingIntent;
            getInstance().notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda3
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    MediaController.Listener listener = (MediaController.Listener) obj;
                    listener.onSessionActivityChanged(this.f$0.getInstance(), pendingIntent);
                }
            });
        }
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void pause() {
        if (isPlayerCommandAvailable(1)) {
            dispatchRemoteSessionTaskWithPlayerCommand(new RemoteSessionTask() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda21
                @Override // androidx.media3.session.MediaControllerImplBase.RemoteSessionTask
                public final void run(IMediaSession iMediaSession, int i) {
                    iMediaSession.pause(this.f$0.controllerStub, i);
                }
            });
            setPlayWhenReady(false, 1);
        }
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void play() {
        android.media.session.MediaController mediaController;
        if (!isPlayerCommandAvailable(1)) {
            Log.w("MCImplBase", "Calling play() omitted due to COMMAND_PLAY_PAUSE not being available. If this play command has started the service for instance for playback resumption, this may prevent the service from being started into the foreground.");
            return;
        }
        if (Util.SDK_INT >= 31 && (mediaController = this.platformController) != null) {
            mediaController.getTransportControls().sendCustomAction("androidx.media3.session.SESSION_COMMAND_MEDIA3_PLAY_REQUEST", (Bundle) null);
        }
        dispatchRemoteSessionTaskWithPlayerCommand(new RemoteSessionTask() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda22
            @Override // androidx.media3.session.MediaControllerImplBase.RemoteSessionTask
            public final void run(IMediaSession iMediaSession, int i) {
                iMediaSession.play(this.f$0.controllerStub, i);
            }
        });
        setPlayWhenReady(true, 1);
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void release() {
        IMediaSession iMediaSession = this.iSession;
        if (this.released) {
            return;
        }
        this.released = true;
        this.connectedToken = null;
        this.flushCommandQueueHandler.release();
        this.iSession = null;
        if (iMediaSession != null) {
            int iObtainNextSequenceNumber = this.sequencedFutureManager.obtainNextSequenceNumber();
            try {
                iMediaSession.asBinder().unlinkToDeath(this.deathRecipient, 0);
                iMediaSession.release(this.controllerStub, iObtainNextSequenceNumber);
            } catch (RemoteException unused) {
            }
        }
        this.listeners.release();
        this.sequencedFutureManager.lazyRelease(30000L, new Runnable() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                MediaControllerImplBase.$r8$lambda$JozrRlNWXSGbLKKHBURtUrBgq5E(this.f$0);
            }
        });
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void removeListener(Player.Listener listener) {
        this.listeners.remove(listener);
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void seekTo(final long j) {
        if (isPlayerCommandAvailable(5)) {
            dispatchRemoteSessionTaskWithPlayerCommand(new RemoteSessionTask() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda17
                @Override // androidx.media3.session.MediaControllerImplBase.RemoteSessionTask
                public final void run(IMediaSession iMediaSession, int i) {
                    iMediaSession.seekTo(this.f$0.controllerStub, i, j);
                }
            });
            seekToInternal(getCurrentMediaItemIndex(), j);
        }
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void seekToNext() {
        if (isPlayerCommandAvailable(9)) {
            dispatchRemoteSessionTaskWithPlayerCommand(new RemoteSessionTask() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda15
                @Override // androidx.media3.session.MediaControllerImplBase.RemoteSessionTask
                public final void run(IMediaSession iMediaSession, int i) {
                    iMediaSession.seekToNext(this.f$0.controllerStub, i);
                }
            });
            Timeline currentTimeline = getCurrentTimeline();
            if (currentTimeline.isEmpty() || isPlayingAd()) {
                return;
            }
            if (hasNextMediaItem()) {
                seekToInternal(getNextMediaItemIndex(), -9223372036854775807L);
                return;
            }
            Timeline.Window window = currentTimeline.getWindow(getCurrentMediaItemIndex(), new Timeline.Window());
            if (window.isDynamic && window.isLive()) {
                seekToInternal(getCurrentMediaItemIndex(), -9223372036854775807L);
            }
        }
    }

    @Override // androidx.media3.session.MediaController.MediaControllerImpl
    public void seekToPrevious() {
        if (isPlayerCommandAvailable(7)) {
            dispatchRemoteSessionTaskWithPlayerCommand(new RemoteSessionTask() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda18
                @Override // androidx.media3.session.MediaControllerImplBase.RemoteSessionTask
                public final void run(IMediaSession iMediaSession, int i) {
                    iMediaSession.seekToPrevious(this.f$0.controllerStub, i);
                }
            });
            Timeline currentTimeline = getCurrentTimeline();
            if (currentTimeline.isEmpty() || isPlayingAd()) {
                return;
            }
            boolean zHasPreviousMediaItem = hasPreviousMediaItem();
            Timeline.Window window = currentTimeline.getWindow(getCurrentMediaItemIndex(), new Timeline.Window());
            if (window.isDynamic && window.isLive()) {
                if (zHasPreviousMediaItem) {
                    seekToInternal(getPreviousMediaItemIndex(), -9223372036854775807L);
                }
            } else if (!zHasPreviousMediaItem || getCurrentPosition() > getMaxSeekToPreviousPosition()) {
                seekToInternal(getCurrentMediaItemIndex(), 0L);
            } else {
                seekToInternal(getPreviousMediaItemIndex(), -9223372036854775807L);
            }
        }
    }

    void setFutureResult(final int i, Object obj) {
        this.sequencedFutureManager.setFutureResult(i, obj);
        getInstance().runOnApplicationLooper(new Runnable() { // from class: androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticLambda51
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.pendingMaskingSequencedFutureNumbers.remove(Integer.valueOf(i));
            }
        });
    }
}
