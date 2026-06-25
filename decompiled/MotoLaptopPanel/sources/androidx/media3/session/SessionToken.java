package androidx.media3.session;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.ResultReceiver;
import androidx.media3.common.MediaLibraryInfo;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import androidx.media3.session.legacy.MediaControllerCompat;
import androidx.media3.session.legacy.MediaSessionCompat;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;

/* JADX INFO: loaded from: classes.dex */
public final class SessionToken {
    private static final String FIELD_IMPL;
    private static final String FIELD_IMPL_TYPE;
    private final SessionTokenImpl impl;

    interface SessionTokenImpl {
        Object getBinder();

        ComponentName getComponentName();

        int getInterfaceVersion();

        String getPackageName();

        MediaSession.Token getPlatformToken();

        String getServiceName();

        int getType();

        int getUid();

        boolean isLegacySession();
    }

    static {
        MediaLibraryInfo.registerModule("media3.session");
        FIELD_IMPL_TYPE = Util.intToStringMaxRadix(0);
        FIELD_IMPL = Util.intToStringMaxRadix(1);
    }

    SessionToken(int i, int i2, int i3, int i4, String str, IMediaSession iMediaSession, Bundle bundle, MediaSession.Token token) {
        this.impl = new SessionTokenImplBase(i, i2, i3, i4, str, iMediaSession, bundle, token);
    }

    private SessionToken(Bundle bundle, MediaSession.Token token) {
        String str = FIELD_IMPL_TYPE;
        Assertions.checkArgument(bundle.containsKey(str), "Impl type needs to be set.");
        int i = bundle.getInt(str);
        Bundle bundle2 = (Bundle) Assertions.checkNotNull(bundle.getBundle(FIELD_IMPL));
        if (i == 0) {
            this.impl = SessionTokenImplBase.fromBundle(bundle2, token);
        } else {
            this.impl = SessionTokenImplLegacy.fromBundle(bundle2);
        }
    }

    private SessionToken(MediaSessionCompat.Token token, String str, int i, Bundle bundle) {
        this.impl = new SessionTokenImplLegacy(token, str, i, bundle);
    }

    public static ListenableFuture createSessionToken(Context context, MediaSession.Token token) {
        return createSessionToken(context, MediaSessionCompat.Token.fromToken(token));
    }

    private static ListenableFuture createSessionToken(Context context, MediaSessionCompat.Token token) {
        final HandlerThread handlerThread = new HandlerThread("SessionTokenThread");
        handlerThread.start();
        ListenableFuture listenableFutureCreateSessionToken = createSessionToken(context, token, handlerThread.getLooper());
        listenableFutureCreateSessionToken.addListener(new Runnable() { // from class: androidx.media3.session.SessionToken$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                handlerThread.quit();
            }
        }, MoreExecutors.directExecutor());
        return listenableFutureCreateSessionToken;
    }

    private static ListenableFuture createSessionToken(final Context context, final MediaSessionCompat.Token token, Looper looper) {
        Assertions.checkNotNull(context, "context must not be null");
        Assertions.checkNotNull(token, "compatToken must not be null");
        final SettableFuture settableFutureCreate = SettableFuture.create();
        final MediaControllerCompat mediaControllerCompat = new MediaControllerCompat(context, token);
        final String str = (String) Assertions.checkNotNull(mediaControllerCompat.getPackageName());
        final Handler handler = new Handler(looper);
        final Runnable runnable = new Runnable() { // from class: androidx.media3.session.SessionToken$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Context context2 = context;
                String str2 = str;
                settableFutureCreate.set(new SessionToken(token, str2, SessionToken.getUid(context2.getPackageManager(), str2), mediaControllerCompat.getSessionInfo()));
            }
        };
        handler.postDelayed(runnable, 500L);
        mediaControllerCompat.sendCommand("androidx.media3.session.SESSION_COMMAND_REQUEST_SESSION3_TOKEN", null, new ResultReceiver(handler) { // from class: androidx.media3.session.SessionToken.1
            @Override // android.os.ResultReceiver
            protected void onReceiveResult(int i, Bundle bundle) {
                handler.removeCallbacksAndMessages(null);
                try {
                    settableFutureCreate.set(SessionToken.fromBundle(bundle, (MediaSession.Token) token.getToken()));
                } catch (RuntimeException unused) {
                    runnable.run();
                }
            }
        });
        return settableFutureCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SessionToken fromBundle(Bundle bundle, MediaSession.Token token) {
        return new SessionToken(bundle, token);
    }

    private static int getUid(PackageManager packageManager, String str) {
        try {
            return packageManager.getApplicationInfo(str, 0).uid;
        } catch (PackageManager.NameNotFoundException unused) {
            return -1;
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof SessionToken) {
            return this.impl.equals(((SessionToken) obj).impl);
        }
        return false;
    }

    Object getBinder() {
        return this.impl.getBinder();
    }

    ComponentName getComponentName() {
        return this.impl.getComponentName();
    }

    public int getInterfaceVersion() {
        return this.impl.getInterfaceVersion();
    }

    public String getPackageName() {
        return this.impl.getPackageName();
    }

    MediaSession.Token getPlatformToken() {
        return this.impl.getPlatformToken();
    }

    public String getServiceName() {
        return this.impl.getServiceName();
    }

    public int getType() {
        return this.impl.getType();
    }

    public int getUid() {
        return this.impl.getUid();
    }

    public int hashCode() {
        return this.impl.hashCode();
    }

    boolean isLegacySession() {
        return this.impl.isLegacySession();
    }

    public String toString() {
        return this.impl.toString();
    }
}
