package androidx.window.layout;

import android.content.Context;
import android.util.Log;
import androidx.window.WindowSdkExtensions;
import androidx.window.core.ConsumerAdapter;
import androidx.window.extensions.layout.WindowLayoutComponent;
import androidx.window.layout.WindowInfoTracker;
import androidx.window.layout.adapter.WindowBackend;
import androidx.window.layout.adapter.extensions.ExtensionWindowBackend;
import androidx.window.layout.adapter.sidecar.SidecarWindowBackend;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.flow.Flow;

/* JADX INFO: compiled from: WindowInfoTracker.kt */
/* JADX INFO: loaded from: classes.dex */
public interface WindowInfoTracker {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: WindowInfoTracker.kt */
    public final class Companion {
        private static final boolean DEBUG = false;
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final String TAG = Reflection.getOrCreateKotlinClass(WindowInfoTracker.class).getSimpleName();
        private static final Lazy extensionBackend$delegate = LazyKt.lazy(new Function0() { // from class: androidx.window.layout.WindowInfoTracker$Companion$extensionBackend$2
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final WindowBackend mo2224invoke() {
                WindowLayoutComponent windowLayoutComponent;
                try {
                    ClassLoader classLoader = WindowInfoTracker.class.getClassLoader();
                    SafeWindowLayoutComponentProvider safeWindowLayoutComponentProvider = classLoader != null ? new SafeWindowLayoutComponentProvider(classLoader, new ConsumerAdapter(classLoader)) : null;
                    if (safeWindowLayoutComponentProvider == null || (windowLayoutComponent = safeWindowLayoutComponentProvider.getWindowLayoutComponent()) == null) {
                        return null;
                    }
                    ExtensionWindowBackend.Companion companion = ExtensionWindowBackend.Companion;
                    classLoader.getClass();
                    return companion.newInstance(windowLayoutComponent, new ConsumerAdapter(classLoader));
                } catch (Throwable unused) {
                    if (WindowInfoTracker.Companion.DEBUG) {
                        Log.d(WindowInfoTracker.Companion.TAG, "Failed to load WindowExtensions");
                    }
                    return null;
                }
            }
        });
        private static WindowInfoTrackerDecorator decorator = EmptyDecorator.INSTANCE;

        private Companion() {
        }

        public final WindowBackend getExtensionBackend$window_release() {
            return (WindowBackend) extensionBackend$delegate.getValue();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final WindowInfoTracker getOrCreate(Context context) {
            context.getClass();
            WindowBackend extensionBackend$window_release = getExtensionBackend$window_release();
            if (extensionBackend$window_release == null) {
                extensionBackend$window_release = SidecarWindowBackend.Companion.getInstance(context);
            }
            return decorator.decorate(new WindowInfoTrackerImpl(new WindowMetricsCalculatorCompat(null, 1, 0 == true ? 1 : 0), extensionBackend$window_release, WindowSdkExtensions.Companion.getInstance()));
        }
    }

    Flow windowLayoutInfo(Context context);
}
