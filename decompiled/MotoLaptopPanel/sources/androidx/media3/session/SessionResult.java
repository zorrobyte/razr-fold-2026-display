package androidx.media3.session;

import android.os.Bundle;
import android.os.SystemClock;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;

/* JADX INFO: loaded from: classes.dex */
public final class SessionResult {
    public final long completionTimeMs;
    public final Bundle extras;
    public final int resultCode;
    public final SessionError sessionError;
    private static final String FIELD_RESULT_CODE = Util.intToStringMaxRadix(0);
    private static final String FIELD_EXTRAS = Util.intToStringMaxRadix(1);
    private static final String FIELD_COMPLETION_TIME_MS = Util.intToStringMaxRadix(2);
    private static final String FIELD_SESSION_ERROR = Util.intToStringMaxRadix(3);

    public SessionResult(int i) {
        this(i, Bundle.EMPTY);
    }

    public SessionResult(int i, Bundle bundle) {
        this(i, bundle, SystemClock.elapsedRealtime(), null);
    }

    private SessionResult(int i, Bundle bundle, long j, SessionError sessionError) {
        Assertions.checkArgument(sessionError == null || i < 0);
        this.resultCode = i;
        this.extras = new Bundle(bundle);
        this.completionTimeMs = j;
        if (sessionError == null && i < 0) {
            sessionError = new SessionError(i, "no error message provided");
        }
        this.sessionError = sessionError;
    }

    public static SessionResult fromBundle(Bundle bundle) {
        int i = bundle.getInt(FIELD_RESULT_CODE, -1);
        Bundle bundle2 = bundle.getBundle(FIELD_EXTRAS);
        long j = bundle.getLong(FIELD_COMPLETION_TIME_MS, SystemClock.elapsedRealtime());
        Bundle bundle3 = bundle.getBundle(FIELD_SESSION_ERROR);
        SessionError sessionErrorFromBundle = bundle3 != null ? SessionError.fromBundle(bundle3) : i != 0 ? new SessionError(i, "no error message provided") : null;
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        return new SessionResult(i, bundle2, j, sessionErrorFromBundle);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(FIELD_RESULT_CODE, this.resultCode);
        bundle.putBundle(FIELD_EXTRAS, this.extras);
        bundle.putLong(FIELD_COMPLETION_TIME_MS, this.completionTimeMs);
        SessionError sessionError = this.sessionError;
        if (sessionError != null) {
            bundle.putBundle(FIELD_SESSION_ERROR, sessionError.toBundle());
        }
        return bundle;
    }
}
