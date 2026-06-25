package androidx.media3.session;

import android.os.Bundle;
import androidx.media3.common.util.Util;

/* JADX INFO: loaded from: classes.dex */
class ConnectionRequest {
    public final Bundle connectionHints;
    public final int controllerInterfaceVersion;
    public final int libraryVersion;
    public final int maxCommandsForMediaItems;
    public final String packageName;
    public final int pid;
    private static final String FIELD_LIBRARY_VERSION = Util.intToStringMaxRadix(0);
    private static final String FIELD_PACKAGE_NAME = Util.intToStringMaxRadix(1);
    private static final String FIELD_PID = Util.intToStringMaxRadix(2);
    private static final String FIELD_CONNECTION_HINTS = Util.intToStringMaxRadix(3);
    private static final String FIELD_CONTROLLER_INTERFACE_VERSION = Util.intToStringMaxRadix(4);
    private static final String FIELD_MAX_COMMANDS_FOR_MEDIA_ITEM = Util.intToStringMaxRadix(5);

    private ConnectionRequest(int i, int i2, String str, int i3, Bundle bundle, int i4) {
        this.libraryVersion = i;
        this.controllerInterfaceVersion = i2;
        this.packageName = str;
        this.pid = i3;
        this.connectionHints = bundle;
        this.maxCommandsForMediaItems = i4;
    }

    public ConnectionRequest(String str, int i, Bundle bundle, int i2) {
        this(1006000001, 7, str, i, new Bundle(bundle), i2);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(FIELD_LIBRARY_VERSION, this.libraryVersion);
        bundle.putString(FIELD_PACKAGE_NAME, this.packageName);
        bundle.putInt(FIELD_PID, this.pid);
        bundle.putBundle(FIELD_CONNECTION_HINTS, this.connectionHints);
        bundle.putInt(FIELD_CONTROLLER_INTERFACE_VERSION, this.controllerInterfaceVersion);
        bundle.putInt(FIELD_MAX_COMMANDS_FOR_MEDIA_ITEM, this.maxCommandsForMediaItems);
        return bundle;
    }
}
