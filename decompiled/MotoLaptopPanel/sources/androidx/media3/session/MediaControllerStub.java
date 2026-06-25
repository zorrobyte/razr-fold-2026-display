package androidx.media3.session;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0;
import android.text.TextUtils;
import androidx.media3.common.Player;
import androidx.media3.common.util.BundleCollectionUtil;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.Util;
import androidx.media3.session.IMediaController;
import androidx.media3.session.PlayerInfo;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import java.lang.ref.WeakReference;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class MediaControllerStub extends IMediaController.Stub {
    private final WeakReference controller;

    interface ControllerTask {
        void run(MediaControllerImplBase mediaControllerImplBase);
    }

    public static /* synthetic */ void $r8$lambda$A3vB1vbvHFHU4wXtrpK5lVKCSoQ(MediaControllerImplBase mediaControllerImplBase, ControllerTask controllerTask) {
        if (mediaControllerImplBase.isReleased()) {
            return;
        }
        controllerTask.run(mediaControllerImplBase);
    }

    public static /* synthetic */ void $r8$lambda$Ny9v1yOR9D9gffRh8Etgk1cyuXo(String str, int i, MediaLibraryService$LibraryParams mediaLibraryService$LibraryParams, MediaBrowserImplBase mediaBrowserImplBase) {
        throw null;
    }

    /* JADX INFO: renamed from: $r8$lambda$f7Ccr-KPKS3G9Op4LiHF2cfRqV8, reason: not valid java name */
    public static /* synthetic */ void m2030$r8$lambda$f7CcrKPKS3G9Op4LiHF2cfRqV8(MediaControllerImplBase mediaControllerImplBase) {
        MediaController mediaControllerImplBase2 = mediaControllerImplBase.getInstance();
        MediaController mediaControllerImplBase3 = mediaControllerImplBase.getInstance();
        mediaControllerImplBase3.getClass();
        mediaControllerImplBase2.runOnApplicationLooper(new MediaControllerImplBase$$ExternalSyntheticLambda16(mediaControllerImplBase3));
    }

    /* JADX INFO: renamed from: $r8$lambda$vRqrs_Zf1HOf-OmKHjf90F7CEh4, reason: not valid java name */
    public static /* synthetic */ void m2032$r8$lambda$vRqrs_Zf1HOfOmKHjf90F7CEh4(String str, int i, MediaLibraryService$LibraryParams mediaLibraryService$LibraryParams, MediaBrowserImplBase mediaBrowserImplBase) {
        throw null;
    }

    public MediaControllerStub(MediaControllerImplBase mediaControllerImplBase) {
        this.controller = new WeakReference(mediaControllerImplBase);
    }

    private void dispatchControllerTaskOnHandler(final ControllerTask controllerTask) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            final MediaControllerImplBase mediaControllerImplBase = (MediaControllerImplBase) this.controller.get();
            if (mediaControllerImplBase == null) {
                return;
            }
            Util.postOrRun(mediaControllerImplBase.getInstance().applicationHandler, new Runnable() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaControllerStub.$r8$lambda$A3vB1vbvHFHU4wXtrpK5lVKCSoQ(mediaControllerImplBase, controllerTask);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    private int getSessionInterfaceVersion() {
        SessionToken connectedToken;
        MediaControllerImplBase mediaControllerImplBase = (MediaControllerImplBase) this.controller.get();
        if (mediaControllerImplBase == null || (connectedToken = mediaControllerImplBase.getConnectedToken()) == null) {
            return -1;
        }
        return connectedToken.getInterfaceVersion();
    }

    private void setControllerFutureResult(int i, Object obj) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            MediaControllerImplBase mediaControllerImplBase = (MediaControllerImplBase) this.controller.get();
            if (mediaControllerImplBase == null) {
                return;
            }
            mediaControllerImplBase.setFutureResult(i, obj);
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public void destroy() {
        this.controller.clear();
    }

    @Override // androidx.media3.session.IMediaController
    public void onAvailableCommandsChangedFromPlayer(int i, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        try {
            final Player.Commands commandsFromBundle = Player.Commands.fromBundle(bundle);
            dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda16
                @Override // androidx.media3.session.MediaControllerStub.ControllerTask
                public final void run(MediaControllerImplBase mediaControllerImplBase) {
                    mediaControllerImplBase.onAvailableCommandsChangedFromPlayer(commandsFromBundle);
                }
            });
        } catch (RuntimeException e) {
            Log.w("MediaControllerStub", "Ignoring malformed Bundle for Commands", e);
        }
    }

    @Override // androidx.media3.session.IMediaController
    public void onAvailableCommandsChangedFromSession(int i, Bundle bundle, Bundle bundle2) {
        if (bundle == null || bundle2 == null) {
            return;
        }
        try {
            final SessionCommands sessionCommandsFromBundle = SessionCommands.fromBundle(bundle);
            try {
                final Player.Commands commandsFromBundle = Player.Commands.fromBundle(bundle2);
                dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda5
                    @Override // androidx.media3.session.MediaControllerStub.ControllerTask
                    public final void run(MediaControllerImplBase mediaControllerImplBase) {
                        mediaControllerImplBase.onAvailableCommandsChangedFromSession(sessionCommandsFromBundle, commandsFromBundle);
                    }
                });
            } catch (RuntimeException e) {
                Log.w("MediaControllerStub", "Ignoring malformed Bundle for Commands", e);
            }
        } catch (RuntimeException e2) {
            Log.w("MediaControllerStub", "Ignoring malformed Bundle for SessionCommands", e2);
        }
    }

    @Override // androidx.media3.session.IMediaController
    public void onChildrenChanged(int i, final String str, final int i2, Bundle bundle) {
        final MediaLibraryService$LibraryParams mediaLibraryService$LibraryParamsFromBundle;
        if (TextUtils.isEmpty(str)) {
            Log.w("MediaControllerStub", "onChildrenChanged(): Ignoring empty parentId");
            return;
        }
        if (i2 < 0) {
            Log.w("MediaControllerStub", "onChildrenChanged(): Ignoring negative itemCount: " + i2);
            return;
        }
        if (bundle == null) {
            mediaLibraryService$LibraryParamsFromBundle = null;
        } else {
            try {
                mediaLibraryService$LibraryParamsFromBundle = MediaLibraryService$LibraryParams.fromBundle(bundle);
            } catch (RuntimeException e) {
                Log.w("MediaControllerStub", "Ignoring malformed Bundle for LibraryParams", e);
                return;
            }
        }
        dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda7
            @Override // androidx.media3.session.MediaControllerStub.ControllerTask
            public final void run(MediaControllerImplBase mediaControllerImplBase) {
                String str2 = str;
                int i3 = i2;
                MediaLibraryService$LibraryParams mediaLibraryService$LibraryParams = mediaLibraryService$LibraryParamsFromBundle;
                MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(mediaControllerImplBase);
                MediaControllerStub.m2032$r8$lambda$vRqrs_Zf1HOfOmKHjf90F7CEh4(str2, i3, mediaLibraryService$LibraryParams, null);
            }
        });
    }

    @Override // androidx.media3.session.IMediaController
    public void onConnected(int i, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        try {
            final ConnectionState connectionStateFromBundle = ConnectionState.fromBundle(bundle);
            dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda11
                @Override // androidx.media3.session.MediaControllerStub.ControllerTask
                public final void run(MediaControllerImplBase mediaControllerImplBase) {
                    mediaControllerImplBase.onConnected(connectionStateFromBundle);
                }
            });
        } catch (RuntimeException e) {
            Log.w("MediaControllerStub", "Malformed Bundle for ConnectionResult. Disconnected from the session.", e);
            onDisconnected(i);
        }
    }

    @Override // androidx.media3.session.IMediaController
    public void onCustomCommand(final int i, Bundle bundle, final Bundle bundle2) {
        if (bundle == null || bundle2 == null) {
            Log.w("MediaControllerStub", "Ignoring custom command with null args.");
            return;
        }
        try {
            final SessionCommand sessionCommandFromBundle = SessionCommand.fromBundle(bundle);
            dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda9
                @Override // androidx.media3.session.MediaControllerStub.ControllerTask
                public final void run(MediaControllerImplBase mediaControllerImplBase) {
                    mediaControllerImplBase.onCustomCommand(i, sessionCommandFromBundle, bundle2);
                }
            });
        } catch (RuntimeException e) {
            Log.w("MediaControllerStub", "Ignoring malformed Bundle for SessionCommand", e);
        }
    }

    @Override // androidx.media3.session.IMediaController
    public void onDisconnected(int i) {
        dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda6
            @Override // androidx.media3.session.MediaControllerStub.ControllerTask
            public final void run(MediaControllerImplBase mediaControllerImplBase) {
                MediaControllerStub.m2030$r8$lambda$f7CcrKPKS3G9Op4LiHF2cfRqV8(mediaControllerImplBase);
            }
        });
    }

    @Override // androidx.media3.session.IMediaController
    public void onError(final int i, Bundle bundle) {
        try {
            final SessionError sessionErrorFromBundle = SessionError.fromBundle(bundle);
            dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda3
                @Override // androidx.media3.session.MediaControllerStub.ControllerTask
                public final void run(MediaControllerImplBase mediaControllerImplBase) {
                    mediaControllerImplBase.onError(i, sessionErrorFromBundle);
                }
            });
        } catch (RuntimeException e) {
            Log.w("MediaControllerStub", "Ignoring malformed Bundle for SessionError", e);
        }
    }

    @Override // androidx.media3.session.IMediaController
    public void onExtrasChanged(int i, final Bundle bundle) {
        if (bundle == null) {
            Log.w("MediaControllerStub", "Ignoring null Bundle for extras");
        } else {
            dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda10
                @Override // androidx.media3.session.MediaControllerStub.ControllerTask
                public final void run(MediaControllerImplBase mediaControllerImplBase) {
                    mediaControllerImplBase.onExtrasChanged(bundle);
                }
            });
        }
    }

    @Override // androidx.media3.session.IMediaController
    public void onLibraryResult(int i, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        try {
            setControllerFutureResult(i, LibraryResult.fromUnknownBundle(bundle));
        } catch (RuntimeException e) {
            Log.w("MediaControllerStub", "Ignoring malformed Bundle for LibraryResult", e);
        }
    }

    @Override // androidx.media3.session.IMediaController
    public void onPeriodicSessionPositionInfoChanged(int i, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        try {
            final SessionPositionInfo sessionPositionInfoFromBundle = SessionPositionInfo.fromBundle(bundle);
            dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda12
                @Override // androidx.media3.session.MediaControllerStub.ControllerTask
                public final void run(MediaControllerImplBase mediaControllerImplBase) {
                    mediaControllerImplBase.notifyPeriodicSessionPositionInfoChanged(sessionPositionInfoFromBundle);
                }
            });
        } catch (RuntimeException e) {
            Log.w("MediaControllerStub", "Ignoring malformed Bundle for SessionPositionInfo", e);
        }
    }

    @Override // androidx.media3.session.IMediaController
    public void onPlayerInfoChanged(int i, Bundle bundle, boolean z) {
        onPlayerInfoChangedWithExclusions(i, bundle, new PlayerInfo.BundlingExclusions(z, true).toBundle());
    }

    @Override // androidx.media3.session.IMediaController
    public void onPlayerInfoChangedWithExclusions(int i, Bundle bundle, Bundle bundle2) {
        if (bundle == null || bundle2 == null) {
            return;
        }
        try {
            int sessionInterfaceVersion = getSessionInterfaceVersion();
            if (sessionInterfaceVersion == -1) {
                return;
            }
            final PlayerInfo playerInfoFromBundle = PlayerInfo.fromBundle(bundle, sessionInterfaceVersion);
            try {
                final PlayerInfo.BundlingExclusions bundlingExclusionsFromBundle = PlayerInfo.BundlingExclusions.fromBundle(bundle2);
                dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda17
                    @Override // androidx.media3.session.MediaControllerStub.ControllerTask
                    public final void run(MediaControllerImplBase mediaControllerImplBase) {
                        mediaControllerImplBase.onPlayerInfoChanged(playerInfoFromBundle, bundlingExclusionsFromBundle);
                    }
                });
            } catch (RuntimeException e) {
                Log.w("MediaControllerStub", "Ignoring malformed Bundle for BundlingExclusions", e);
            }
        } catch (RuntimeException e2) {
            Log.w("MediaControllerStub", "Ignoring malformed Bundle for PlayerInfo", e2);
        }
    }

    @Override // androidx.media3.session.IMediaController
    public void onRenderedFirstFrame(int i) {
        dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda4
            @Override // androidx.media3.session.MediaControllerStub.ControllerTask
            public final void run(MediaControllerImplBase mediaControllerImplBase) {
                mediaControllerImplBase.onRenderedFirstFrame();
            }
        });
    }

    @Override // androidx.media3.session.IMediaController
    public void onSearchResultChanged(int i, final String str, final int i2, Bundle bundle) {
        final MediaLibraryService$LibraryParams mediaLibraryService$LibraryParamsFromBundle;
        if (TextUtils.isEmpty(str)) {
            Log.w("MediaControllerStub", "onSearchResultChanged(): Ignoring empty query");
            return;
        }
        if (i2 < 0) {
            Log.w("MediaControllerStub", "onSearchResultChanged(): Ignoring negative itemCount: " + i2);
            return;
        }
        if (bundle == null) {
            mediaLibraryService$LibraryParamsFromBundle = null;
        } else {
            try {
                mediaLibraryService$LibraryParamsFromBundle = MediaLibraryService$LibraryParams.fromBundle(bundle);
            } catch (RuntimeException e) {
                Log.w("MediaControllerStub", "Ignoring malformed Bundle for LibraryParams", e);
                return;
            }
        }
        dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda15
            @Override // androidx.media3.session.MediaControllerStub.ControllerTask
            public final void run(MediaControllerImplBase mediaControllerImplBase) {
                String str2 = str;
                int i3 = i2;
                MediaLibraryService$LibraryParams mediaLibraryService$LibraryParams = mediaLibraryService$LibraryParamsFromBundle;
                MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(mediaControllerImplBase);
                MediaControllerStub.$r8$lambda$Ny9v1yOR9D9gffRh8Etgk1cyuXo(str2, i3, mediaLibraryService$LibraryParams, null);
            }
        });
    }

    @Override // androidx.media3.session.IMediaController
    public void onSessionActivityChanged(final int i, final PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            Log.w("MediaControllerStub", "Ignoring null session activity intent");
        } else {
            dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda8
                @Override // androidx.media3.session.MediaControllerStub.ControllerTask
                public final void run(MediaControllerImplBase mediaControllerImplBase) {
                    mediaControllerImplBase.onSetSessionActivity(i, pendingIntent);
                }
            });
        }
    }

    @Override // androidx.media3.session.IMediaController
    public void onSessionResult(int i, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        try {
            setControllerFutureResult(i, SessionResult.fromBundle(bundle));
        } catch (RuntimeException e) {
            Log.w("MediaControllerStub", "Ignoring malformed Bundle for SessionResult", e);
        }
    }

    @Override // androidx.media3.session.IMediaController
    public void onSetCustomLayout(final int i, List list) {
        if (list == null) {
            return;
        }
        try {
            final int sessionInterfaceVersion = getSessionInterfaceVersion();
            if (sessionInterfaceVersion == -1) {
                return;
            }
            final ImmutableList immutableListFromBundleList = BundleCollectionUtil.fromBundleList(new Function() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda13
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return CommandButton.fromBundle((Bundle) obj, sessionInterfaceVersion);
                }
            }, list);
            dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda14
                @Override // androidx.media3.session.MediaControllerStub.ControllerTask
                public final void run(MediaControllerImplBase mediaControllerImplBase) {
                    mediaControllerImplBase.onSetCustomLayout(i, immutableListFromBundleList);
                }
            });
        } catch (RuntimeException e) {
            Log.w("MediaControllerStub", "Ignoring malformed Bundle for CommandButton", e);
        }
    }

    @Override // androidx.media3.session.IMediaController
    public void onSetMediaButtonPreferences(final int i, List list) {
        if (list == null) {
            return;
        }
        try {
            final int sessionInterfaceVersion = getSessionInterfaceVersion();
            if (sessionInterfaceVersion == -1) {
                return;
            }
            final ImmutableList immutableListFromBundleList = BundleCollectionUtil.fromBundleList(new Function() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda1
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    return CommandButton.fromBundle((Bundle) obj, sessionInterfaceVersion);
                }
            }, list);
            dispatchControllerTaskOnHandler(new ControllerTask() { // from class: androidx.media3.session.MediaControllerStub$$ExternalSyntheticLambda2
                @Override // androidx.media3.session.MediaControllerStub.ControllerTask
                public final void run(MediaControllerImplBase mediaControllerImplBase) {
                    mediaControllerImplBase.onSetMediaButtonPreferences(i, immutableListFromBundleList);
                }
            });
        } catch (RuntimeException e) {
            Log.w("MediaControllerStub", "Ignoring malformed Bundle for CommandButton", e);
        }
    }
}
