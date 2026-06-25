package androidx.window.layout.adapter.extensions;

import androidx.window.core.ConsumerAdapter;
import androidx.window.core.ExtensionsUtil;
import androidx.window.extensions.layout.WindowLayoutComponent;
import androidx.window.layout.adapter.WindowBackend;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ExtensionWindowBackend.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ExtensionWindowBackend implements WindowBackend {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: ExtensionWindowBackend.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final WindowBackend newInstance(WindowLayoutComponent windowLayoutComponent, ConsumerAdapter consumerAdapter) {
            windowLayoutComponent.getClass();
            consumerAdapter.getClass();
            int safeVendorApiLevel = ExtensionsUtil.INSTANCE.getSafeVendorApiLevel();
            return safeVendorApiLevel >= 9 ? new ExtensionWindowBackendApi9(windowLayoutComponent, consumerAdapter) : safeVendorApiLevel >= 6 ? new ExtensionWindowBackendApi6(windowLayoutComponent, consumerAdapter) : safeVendorApiLevel >= 2 ? new ExtensionWindowBackendApi2(windowLayoutComponent, consumerAdapter) : safeVendorApiLevel == 1 ? new ExtensionWindowBackendApi1(windowLayoutComponent, consumerAdapter) : new ExtensionWindowBackendApi0();
        }
    }
}
