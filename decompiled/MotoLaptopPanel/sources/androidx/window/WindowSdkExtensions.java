package androidx.window;

import androidx.window.core.ExtensionsUtil;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntRange;

/* JADX INFO: compiled from: WindowSdkExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class WindowSdkExtensions {
    public static final Companion Companion = new Companion(null);
    private static WindowSdkExtensionsDecorator decorator = EmptyDecoratorWindowSdk.INSTANCE;
    private final int extensionVersion = ExtensionsUtil.INSTANCE.getSafeVendorApiLevel();

    /* JADX INFO: compiled from: WindowSdkExtensions.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final WindowSdkExtensions getInstance() {
            return WindowSdkExtensions.decorator.decorate(new WindowSdkExtensions() { // from class: androidx.window.WindowSdkExtensions$Companion$getInstance$1
            });
        }
    }

    public int getExtensionVersion() {
        return this.extensionVersion;
    }

    public final void requireExtensionVersion$window_release(int i) {
        if (getExtensionVersion() >= i) {
            return;
        }
        throw new UnsupportedOperationException("This API requires extension version " + i + ", but the device is on " + getExtensionVersion());
    }

    public final void requireExtensionVersion$window_release(IntRange intRange) {
        intRange.getClass();
        int first = intRange.getFirst();
        int last = intRange.getLast();
        int extensionVersion = getExtensionVersion();
        if (first > extensionVersion || extensionVersion > last) {
            throw new UnsupportedOperationException("This API requires extension version " + intRange + ", but the device is on " + getExtensionVersion());
        }
    }
}
