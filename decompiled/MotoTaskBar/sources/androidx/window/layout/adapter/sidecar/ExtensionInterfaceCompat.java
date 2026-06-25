package androidx.window.layout.adapter.sidecar;

import android.app.Activity;
import androidx.window.layout.WindowLayoutInfo;

/* JADX INFO: compiled from: ExtensionInterfaceCompat.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ExtensionInterfaceCompat {

    /* JADX INFO: compiled from: ExtensionInterfaceCompat.kt */
    public interface ExtensionCallbackInterface {
        void onWindowLayoutChanged(Activity activity, WindowLayoutInfo windowLayoutInfo);
    }

    void onWindowLayoutChangeListenerAdded(Activity activity);

    void onWindowLayoutChangeListenerRemoved(Activity activity);

    void setExtensionCallback(ExtensionCallbackInterface extensionCallbackInterface);

    boolean validateExtensionInterface();
}
