package androidx.media3.common;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.Util;

/* JADX INFO: loaded from: classes.dex */
public class PlaybackException extends Exception {
    public final int errorCode;
    public final Bundle extras;
    public final long timestampMs;
    private static final String FIELD_INT_ERROR_CODE = Util.intToStringMaxRadix(0);
    private static final String FIELD_LONG_TIMESTAMP_MS = Util.intToStringMaxRadix(1);
    private static final String FIELD_STRING_MESSAGE = Util.intToStringMaxRadix(2);
    private static final String FIELD_STRING_CAUSE_CLASS_NAME = Util.intToStringMaxRadix(3);
    private static final String FIELD_STRING_CAUSE_MESSAGE = Util.intToStringMaxRadix(4);
    private static final String FIELD_BUNDLE_EXTRAS = Util.intToStringMaxRadix(5);

    protected PlaybackException(Bundle bundle) {
        this(bundle.getString(FIELD_STRING_MESSAGE), getCauseFromBundle(bundle), bundle.getInt(FIELD_INT_ERROR_CODE, 1000), getExtrasFromBundle(bundle), bundle.getLong(FIELD_LONG_TIMESTAMP_MS, SystemClock.elapsedRealtime()));
    }

    public PlaybackException(String str, Throwable th, int i, Bundle bundle) {
        this(str, th, i, bundle, Clock.DEFAULT.elapsedRealtime());
    }

    protected PlaybackException(String str, Throwable th, int i, Bundle bundle, long j) {
        super(str, th);
        this.errorCode = i;
        this.extras = bundle;
        this.timestampMs = j;
    }

    private static RemoteException createRemoteException(String str) {
        return new RemoteException(str);
    }

    private static Throwable createThrowable(Class cls, String str) {
        return (Throwable) cls.getConstructor(String.class).newInstance(str);
    }

    public static PlaybackException fromBundle(Bundle bundle) {
        return new PlaybackException(bundle);
    }

    private static Throwable getCauseFromBundle(Bundle bundle) {
        String string = bundle.getString(FIELD_STRING_CAUSE_CLASS_NAME);
        String string2 = bundle.getString(FIELD_STRING_CAUSE_MESSAGE);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            Class<?> cls = Class.forName(string, true, PlaybackException.class.getClassLoader());
            Throwable thCreateThrowable = Throwable.class.isAssignableFrom(cls) ? createThrowable(cls, string2) : null;
            return thCreateThrowable == null ? createRemoteException(string2) : thCreateThrowable;
        } catch (Throwable unused) {
            return createRemoteException(string2);
        }
    }

    private static Bundle getExtrasFromBundle(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(FIELD_BUNDLE_EXTRAS);
        return bundle2 != null ? bundle2 : Bundle.EMPTY;
    }

    public boolean errorInfoEquals(PlaybackException playbackException) {
        if (this == playbackException) {
            return true;
        }
        if (playbackException != null && getClass() == playbackException.getClass()) {
            Throwable cause = getCause();
            Throwable cause2 = playbackException.getCause();
            if (cause == null || cause2 == null) {
                if (cause == null && cause2 == null) {
                }
            } else if (!Util.areEqual(cause.getMessage(), cause2.getMessage()) || !Util.areEqual(cause.getClass(), cause2.getClass())) {
                return false;
            }
            if (this.errorCode == playbackException.errorCode && Util.areEqual(getMessage(), playbackException.getMessage()) && this.timestampMs == playbackException.timestampMs) {
                return true;
            }
        }
        return false;
    }
}
