package androidx.media3.session;

import android.content.ComponentName;
import android.media.session.MediaSession;
import android.os.Bundle;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import androidx.media3.session.SessionToken;
import androidx.media3.session.legacy.MediaSessionCompat;
import com.google.common.base.Objects;

/* JADX INFO: loaded from: classes.dex */
final class SessionTokenImplLegacy implements SessionToken.SessionTokenImpl {
    private final ComponentName componentName;
    private final Bundle extras;
    private final MediaSessionCompat.Token legacyToken;
    private final String packageName;
    private final int type;
    private final int uid;
    private static final String FIELD_LEGACY_TOKEN = Util.intToStringMaxRadix(0);
    private static final String FIELD_UID = Util.intToStringMaxRadix(1);
    private static final String FIELD_TYPE = Util.intToStringMaxRadix(2);
    private static final String FIELD_COMPONENT_NAME = Util.intToStringMaxRadix(3);
    private static final String FIELD_PACKAGE_NAME = Util.intToStringMaxRadix(4);
    private static final String FIELD_EXTRAS = Util.intToStringMaxRadix(5);

    private SessionTokenImplLegacy(MediaSessionCompat.Token token, int i, int i2, ComponentName componentName, String str, Bundle bundle) {
        this.legacyToken = token;
        this.uid = i;
        this.type = i2;
        this.componentName = componentName;
        this.packageName = str;
        this.extras = bundle;
    }

    public SessionTokenImplLegacy(MediaSessionCompat.Token token, String str, int i, Bundle bundle) {
        this((MediaSessionCompat.Token) Assertions.checkNotNull(token), i, 100, null, Assertions.checkNotEmpty(str), (Bundle) Assertions.checkNotNull(bundle));
    }

    public static SessionTokenImplLegacy fromBundle(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(FIELD_LEGACY_TOKEN);
        MediaSessionCompat.Token tokenFromBundle = bundle2 == null ? null : MediaSessionCompat.Token.fromBundle(bundle2);
        String str = FIELD_UID;
        Assertions.checkArgument(bundle.containsKey(str), "uid should be set.");
        int i = bundle.getInt(str);
        String str2 = FIELD_TYPE;
        Assertions.checkArgument(bundle.containsKey(str2), "type should be set.");
        int i2 = bundle.getInt(str2);
        ComponentName componentName = (ComponentName) bundle.getParcelable(FIELD_COMPONENT_NAME);
        String strCheckNotEmpty = Assertions.checkNotEmpty(bundle.getString(FIELD_PACKAGE_NAME), "package name should be set.");
        Bundle bundle3 = bundle.getBundle(FIELD_EXTRAS);
        if (bundle3 == null) {
            bundle3 = Bundle.EMPTY;
        }
        return new SessionTokenImplLegacy(tokenFromBundle, i, i2, componentName, strCheckNotEmpty, bundle3);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SessionTokenImplLegacy)) {
            return false;
        }
        SessionTokenImplLegacy sessionTokenImplLegacy = (SessionTokenImplLegacy) obj;
        int i = this.type;
        if (i != sessionTokenImplLegacy.type) {
            return false;
        }
        if (i == 100) {
            return Util.areEqual(this.legacyToken, sessionTokenImplLegacy.legacyToken);
        }
        if (i != 101) {
            return false;
        }
        return Util.areEqual(this.componentName, sessionTokenImplLegacy.componentName);
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public Object getBinder() {
        return this.legacyToken;
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public ComponentName getComponentName() {
        return this.componentName;
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public int getInterfaceVersion() {
        return 0;
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public String getPackageName() {
        return this.packageName;
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public MediaSession.Token getPlatformToken() {
        MediaSessionCompat.Token token = this.legacyToken;
        if (token == null) {
            return null;
        }
        return (MediaSession.Token) token.getToken();
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public String getServiceName() {
        ComponentName componentName = this.componentName;
        return componentName == null ? "" : componentName.getClassName();
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public int getType() {
        return this.type != 101 ? 0 : 2;
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public int getUid() {
        return this.uid;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.type), this.componentName, this.legacyToken);
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public boolean isLegacySession() {
        return true;
    }

    public String toString() {
        return "SessionToken {legacy, uid=" + this.uid + "}";
    }
}
