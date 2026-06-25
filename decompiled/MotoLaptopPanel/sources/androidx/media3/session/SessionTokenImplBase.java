package androidx.media3.session;

import android.content.ComponentName;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import androidx.core.app.BundleCompat;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import androidx.media3.session.SessionToken;
import com.google.common.base.Objects;

/* JADX INFO: loaded from: classes.dex */
final class SessionTokenImplBase implements SessionToken.SessionTokenImpl {
    private final ComponentName componentName;
    private final Bundle extras;
    private final IBinder iSession;
    private final int interfaceVersion;
    private final int libraryVersion;
    private final String packageName;
    private final MediaSession.Token platformToken;
    private final String serviceName;
    private final int type;
    private final int uid;
    private static final String FIELD_UID = Util.intToStringMaxRadix(0);
    private static final String FIELD_TYPE = Util.intToStringMaxRadix(1);
    private static final String FIELD_LIBRARY_VERSION = Util.intToStringMaxRadix(2);
    private static final String FIELD_PACKAGE_NAME = Util.intToStringMaxRadix(3);
    private static final String FIELD_SERVICE_NAME = Util.intToStringMaxRadix(4);
    private static final String FIELD_COMPONENT_NAME = Util.intToStringMaxRadix(5);
    private static final String FIELD_ISESSION = Util.intToStringMaxRadix(6);
    private static final String FIELD_EXTRAS = Util.intToStringMaxRadix(7);
    private static final String FIELD_INTERFACE_VERSION = Util.intToStringMaxRadix(8);
    private static final String FIELD_PLATFORM_TOKEN = Util.intToStringMaxRadix(9);

    public SessionTokenImplBase(int i, int i2, int i3, int i4, String str, IMediaSession iMediaSession, Bundle bundle, MediaSession.Token token) {
        this(i, i2, i3, i4, (String) Assertions.checkNotNull(str), "", null, iMediaSession.asBinder(), (Bundle) Assertions.checkNotNull(bundle), token);
    }

    private SessionTokenImplBase(int i, int i2, int i3, int i4, String str, String str2, ComponentName componentName, IBinder iBinder, Bundle bundle, MediaSession.Token token) {
        this.uid = i;
        this.type = i2;
        this.libraryVersion = i3;
        this.interfaceVersion = i4;
        this.packageName = str;
        this.serviceName = str2;
        this.componentName = componentName;
        this.iSession = iBinder;
        this.extras = bundle;
        this.platformToken = token;
    }

    public static SessionTokenImplBase fromBundle(Bundle bundle, MediaSession.Token token) {
        String str = FIELD_UID;
        Assertions.checkArgument(bundle.containsKey(str), "uid should be set.");
        int i = bundle.getInt(str);
        String str2 = FIELD_TYPE;
        Assertions.checkArgument(bundle.containsKey(str2), "type should be set.");
        int i2 = bundle.getInt(str2);
        int i3 = bundle.getInt(FIELD_LIBRARY_VERSION, 0);
        int i4 = bundle.getInt(FIELD_INTERFACE_VERSION, 0);
        String strCheckNotEmpty = Assertions.checkNotEmpty(bundle.getString(FIELD_PACKAGE_NAME), "package name should be set.");
        String string = bundle.getString(FIELD_SERVICE_NAME, "");
        IBinder binder = BundleCompat.getBinder(bundle, FIELD_ISESSION);
        ComponentName componentName = (ComponentName) bundle.getParcelable(FIELD_COMPONENT_NAME);
        Bundle bundle2 = bundle.getBundle(FIELD_EXTRAS);
        MediaSession.Token token2 = (MediaSession.Token) bundle.getParcelable(FIELD_PLATFORM_TOKEN);
        MediaSession.Token token3 = token2 != null ? token2 : token;
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        return new SessionTokenImplBase(i, i2, i3, i4, strCheckNotEmpty, string, componentName, binder, bundle2, token3);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SessionTokenImplBase)) {
            return false;
        }
        SessionTokenImplBase sessionTokenImplBase = (SessionTokenImplBase) obj;
        return this.uid == sessionTokenImplBase.uid && this.type == sessionTokenImplBase.type && this.libraryVersion == sessionTokenImplBase.libraryVersion && this.interfaceVersion == sessionTokenImplBase.interfaceVersion && TextUtils.equals(this.packageName, sessionTokenImplBase.packageName) && TextUtils.equals(this.serviceName, sessionTokenImplBase.serviceName) && Objects.equal(this.componentName, sessionTokenImplBase.componentName) && Objects.equal(this.iSession, sessionTokenImplBase.iSession) && Objects.equal(this.platformToken, sessionTokenImplBase.platformToken);
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public Object getBinder() {
        return this.iSession;
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public ComponentName getComponentName() {
        return this.componentName;
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public int getInterfaceVersion() {
        return this.interfaceVersion;
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public String getPackageName() {
        return this.packageName;
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public MediaSession.Token getPlatformToken() {
        return this.platformToken;
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public String getServiceName() {
        return this.serviceName;
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public int getType() {
        return this.type;
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public int getUid() {
        return this.uid;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.uid), Integer.valueOf(this.type), Integer.valueOf(this.libraryVersion), Integer.valueOf(this.interfaceVersion), this.packageName, this.serviceName, this.componentName, this.iSession, this.platformToken);
    }

    @Override // androidx.media3.session.SessionToken.SessionTokenImpl
    public boolean isLegacySession() {
        return false;
    }

    public String toString() {
        return "SessionToken {pkg=" + this.packageName + " type=" + this.type + " libraryVersion=" + this.libraryVersion + " interfaceVersion=" + this.interfaceVersion + " service=" + this.serviceName + " IMediaSession=" + this.iSession + " extras=" + this.extras + "}";
    }
}
