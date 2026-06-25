package androidx.media3.session;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.media3.common.MediaLibraryInfo;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.BitmapLoader;
import androidx.media3.common.util.Consumer;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.DataSourceBitmapLoader;
import androidx.media3.session.MediaController;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MediaController implements Player {
    final Handler applicationHandler;
    final ConnectionCallback connectionCallback;
    private boolean connectionNotified;
    private final MediaControllerImpl impl;
    final Listener listener;
    private final int maxCommandsForMediaItems;
    private boolean released;
    private long timeDiffMs;
    private final Timeline.Window window;

    public final class Builder {
        private BitmapLoader bitmapLoader;
        private final Context context;
        private int maxCommandsForMediaItems;
        private final SessionToken token;
        private Bundle connectionHints = Bundle.EMPTY;
        private Listener listener = new Listener() { // from class: androidx.media3.session.MediaController.Builder.1
        };
        private Looper applicationLooper = Util.getCurrentOrMainLooper();

        public Builder(Context context, SessionToken sessionToken) {
            this.context = (Context) Assertions.checkNotNull(context);
            this.token = (SessionToken) Assertions.checkNotNull(sessionToken);
        }

        public ListenableFuture buildAsync() {
            final MediaControllerHolder mediaControllerHolder = new MediaControllerHolder(this.applicationLooper);
            if (this.token.isLegacySession() && this.bitmapLoader == null) {
                this.bitmapLoader = new CacheBitmapLoader(new DataSourceBitmapLoader(this.context));
            }
            final MediaController mediaController = new MediaController(this.context, this.token, this.connectionHints, this.listener, this.applicationLooper, mediaControllerHolder, this.bitmapLoader, this.maxCommandsForMediaItems);
            Util.postOrRun(new Handler(this.applicationLooper), new Runnable() { // from class: androidx.media3.session.MediaController$Builder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    mediaControllerHolder.setController(mediaController);
                }
            });
            return mediaControllerHolder;
        }
    }

    interface ConnectionCallback {
        void onAccepted();

        void onRejected();
    }

    public interface Listener {
        default void onAvailableSessionCommandsChanged(MediaController mediaController, SessionCommands sessionCommands) {
        }

        default ListenableFuture onCustomCommand(MediaController mediaController, SessionCommand sessionCommand, Bundle bundle) {
            return Futures.immediateFuture(new SessionResult(-6));
        }

        default void onCustomLayoutChanged(MediaController mediaController, List list) {
        }

        default void onDisconnected(MediaController mediaController) {
        }

        default void onError(MediaController mediaController, SessionError sessionError) {
        }

        default void onExtrasChanged(MediaController mediaController, Bundle bundle) {
        }

        default void onMediaButtonPreferencesChanged(MediaController mediaController, List list) {
        }

        default void onSessionActivityChanged(MediaController mediaController, PendingIntent pendingIntent) {
        }

        default ListenableFuture onSetCustomLayout(MediaController mediaController, List list) {
            return Futures.immediateFuture(new SessionResult(-6));
        }
    }

    interface MediaControllerImpl {
        void addListener(Player.Listener listener);

        void connect();

        Player.Commands getAvailableCommands();

        Bundle getConnectionHints();

        long getContentDuration();

        long getCurrentPosition();

        long getDuration();

        MediaMetadata getMediaMetadata();

        boolean isConnected();

        boolean isPlaying();

        void pause();

        void play();

        void release();

        void removeListener(Player.Listener listener);

        void seekTo(long j);

        void seekToNext();

        void seekToPrevious();
    }

    /* JADX INFO: renamed from: $r8$lambda$-ghnn475jYMB33P99C1oZ7OGehU, reason: not valid java name */
    public static /* synthetic */ void m2003$r8$lambda$ghnn475jYMB33P99C1oZ7OGehU(MediaController mediaController, Listener listener) {
        mediaController.getClass();
        listener.onDisconnected(mediaController);
    }

    MediaController(Context context, SessionToken sessionToken, Bundle bundle, Listener listener, Looper looper, ConnectionCallback connectionCallback, BitmapLoader bitmapLoader, int i) {
        Assertions.checkNotNull(context, "context must not be null");
        Assertions.checkNotNull(sessionToken, "token must not be null");
        Log.i("MediaController", "Init " + Integer.toHexString(System.identityHashCode(this)) + " [AndroidXMedia3/1.6.0-alpha01] [" + Util.DEVICE_DEBUG_INFO + "]");
        this.window = new Timeline.Window();
        this.timeDiffMs = -9223372036854775807L;
        this.listener = listener;
        this.applicationHandler = new Handler(looper);
        this.connectionCallback = connectionCallback;
        this.maxCommandsForMediaItems = i;
        MediaControllerImpl mediaControllerImplCreateImpl = createImpl(context, sessionToken, bundle, looper, bitmapLoader);
        this.impl = mediaControllerImplCreateImpl;
        mediaControllerImplCreateImpl.connect();
    }

    private void verifyApplicationThread() {
        Assertions.checkState(Looper.myLooper() == getApplicationLooper(), "MediaController method is called from a wrong thread. See javadoc of MediaController for details.");
    }

    public final void addListener(Player.Listener listener) {
        Assertions.checkNotNull(listener, "listener must not be null");
        this.impl.addListener(listener);
    }

    MediaControllerImpl createImpl(Context context, SessionToken sessionToken, Bundle bundle, Looper looper, BitmapLoader bitmapLoader) {
        return sessionToken.isLegacySession() ? new MediaControllerImplLegacy(context, this, sessionToken, bundle, looper, (BitmapLoader) Assertions.checkNotNull(bitmapLoader)) : new MediaControllerImplBase(context, this, sessionToken, bundle, looper);
    }

    public final Looper getApplicationLooper() {
        return this.applicationHandler.getLooper();
    }

    public final Player.Commands getAvailableCommands() {
        verifyApplicationThread();
        return !isConnected() ? Player.Commands.EMPTY : this.impl.getAvailableCommands();
    }

    Bundle getConnectionHints() {
        return this.impl.getConnectionHints();
    }

    public final long getContentDuration() {
        verifyApplicationThread();
        if (isConnected()) {
            return this.impl.getContentDuration();
        }
        return -9223372036854775807L;
    }

    public final long getCurrentPosition() {
        verifyApplicationThread();
        if (isConnected()) {
            return this.impl.getCurrentPosition();
        }
        return 0L;
    }

    public final long getDuration() {
        verifyApplicationThread();
        if (isConnected()) {
            return this.impl.getDuration();
        }
        return -9223372036854775807L;
    }

    int getMaxCommandsForMediaItems() {
        return this.maxCommandsForMediaItems;
    }

    public final MediaMetadata getMediaMetadata() {
        verifyApplicationThread();
        return isConnected() ? this.impl.getMediaMetadata() : MediaMetadata.EMPTY;
    }

    final long getTimeDiffMs() {
        return this.timeDiffMs;
    }

    public final boolean isCommandAvailable(int i) {
        return getAvailableCommands().contains(i);
    }

    public final boolean isConnected() {
        return this.impl.isConnected();
    }

    public final boolean isPlaying() {
        verifyApplicationThread();
        return isConnected() && this.impl.isPlaying();
    }

    final void notifyAccepted() {
        Assertions.checkState(Looper.myLooper() == getApplicationLooper());
        Assertions.checkState(!this.connectionNotified);
        this.connectionNotified = true;
        this.connectionCallback.onAccepted();
    }

    final void notifyControllerListener(Consumer consumer) {
        Assertions.checkState(Looper.myLooper() == getApplicationLooper());
        consumer.accept(this.listener);
    }

    public final void pause() {
        verifyApplicationThread();
        if (isConnected()) {
            this.impl.pause();
        } else {
            Log.w("MediaController", "The controller is not connected. Ignoring pause().");
        }
    }

    public final void play() {
        verifyApplicationThread();
        if (isConnected()) {
            this.impl.play();
        } else {
            Log.w("MediaController", "The controller is not connected. Ignoring play().");
        }
    }

    public final void release() {
        verifyApplicationThread();
        if (this.released) {
            return;
        }
        Log.i("MediaController", "Release " + Integer.toHexString(System.identityHashCode(this)) + " [AndroidXMedia3/1.6.0-alpha01] [" + Util.DEVICE_DEBUG_INFO + "] [" + MediaLibraryInfo.registeredModules() + "]");
        this.released = true;
        this.applicationHandler.removeCallbacksAndMessages(null);
        try {
            this.impl.release();
        } catch (Exception e) {
            Log.d("MediaController", "Exception while releasing impl", e);
        }
        if (this.connectionNotified) {
            notifyControllerListener(new Consumer() { // from class: androidx.media3.session.MediaController$$ExternalSyntheticLambda0
                @Override // androidx.media3.common.util.Consumer
                public final void accept(Object obj) {
                    MediaController.m2003$r8$lambda$ghnn475jYMB33P99C1oZ7OGehU(this.f$0, (MediaController.Listener) obj);
                }
            });
        } else {
            this.connectionNotified = true;
            this.connectionCallback.onRejected();
        }
    }

    public final void removeListener(Player.Listener listener) {
        verifyApplicationThread();
        Assertions.checkNotNull(listener, "listener must not be null");
        this.impl.removeListener(listener);
    }

    final void runOnApplicationLooper(Runnable runnable) {
        Util.postOrRun(this.applicationHandler, runnable);
    }

    public final void seekTo(long j) {
        verifyApplicationThread();
        if (isConnected()) {
            this.impl.seekTo(j);
        } else {
            Log.w("MediaController", "The controller is not connected. Ignoring seekTo().");
        }
    }

    public final void seekToNext() {
        verifyApplicationThread();
        if (isConnected()) {
            this.impl.seekToNext();
        } else {
            Log.w("MediaController", "The controller is not connected. Ignoring seekToNext().");
        }
    }

    public final void seekToPrevious() {
        verifyApplicationThread();
        if (isConnected()) {
            this.impl.seekToPrevious();
        } else {
            Log.w("MediaController", "The controller is not connected. Ignoring seekToPrevious().");
        }
    }
}
