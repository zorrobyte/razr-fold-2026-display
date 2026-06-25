package androidx.media3.session;

import android.os.Bundle;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class SessionError {
    public int code;
    public Bundle extras;
    public String message;
    private static final String FIELD_CODE = Util.intToStringMaxRadix(0);
    private static final String FIELD_MESSAGE = Util.intToStringMaxRadix(1);
    private static final String FIELD_EXTRAS = Util.intToStringMaxRadix(2);

    public SessionError(int i, String str) {
        this(i, str, Bundle.EMPTY);
    }

    public SessionError(int i, String str, Bundle bundle) {
        boolean z = true;
        if (i >= 0 && i != 1) {
            z = false;
        }
        Assertions.checkArgument(z);
        this.code = i;
        this.message = str;
        this.extras = bundle;
    }

    public static SessionError fromBundle(Bundle bundle) {
        int i = bundle.getInt(FIELD_CODE, 1000);
        String string = bundle.getString(FIELD_MESSAGE, "");
        Bundle bundle2 = bundle.getBundle(FIELD_EXTRAS);
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        return new SessionError(i, string, bundle2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SessionError)) {
            return false;
        }
        SessionError sessionError = (SessionError) obj;
        return this.code == sessionError.code && Objects.equals(this.message, sessionError.message);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.code), this.message);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(FIELD_CODE, this.code);
        bundle.putString(FIELD_MESSAGE, this.message);
        if (!this.extras.isEmpty()) {
            bundle.putBundle(FIELD_EXTRAS, this.extras);
        }
        return bundle;
    }
}
