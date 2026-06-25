package androidx.window.layout.adapter.sidecar;

import android.app.Activity;
import android.content.Context;
import androidx.core.util.Consumer;
import androidx.window.core.Version;
import androidx.window.layout.WindowLayoutInfo;
import androidx.window.layout.adapter.WindowBackend;
import androidx.window.layout.adapter.sidecar.ExtensionInterfaceCompat;
import androidx.window.layout.adapter.sidecar.SidecarWindowBackend;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SidecarWindowBackend.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SidecarWindowBackend implements WindowBackend {
    private static volatile SidecarWindowBackend globalInstance;
    private ExtensionInterfaceCompat windowExtension;
    private final CopyOnWriteArrayList windowLayoutChangeCallbacks = new CopyOnWriteArrayList();
    public static final Companion Companion = new Companion(null);
    private static final ReentrantLock globalLock = new ReentrantLock();

    /* JADX INFO: compiled from: SidecarWindowBackend.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final SidecarWindowBackend getInstance(Context context) {
            context.getClass();
            if (SidecarWindowBackend.globalInstance == null) {
                ReentrantLock reentrantLock = SidecarWindowBackend.globalLock;
                reentrantLock.lock();
                try {
                    if (SidecarWindowBackend.globalInstance == null) {
                        SidecarWindowBackend.globalInstance = new SidecarWindowBackend(SidecarWindowBackend.Companion.initAndVerifyExtension(context));
                    }
                    Unit unit = Unit.INSTANCE;
                    reentrantLock.unlock();
                } catch (Throwable th) {
                    reentrantLock.unlock();
                    throw th;
                }
            }
            SidecarWindowBackend sidecarWindowBackend = SidecarWindowBackend.globalInstance;
            sidecarWindowBackend.getClass();
            return sidecarWindowBackend;
        }

        public final ExtensionInterfaceCompat initAndVerifyExtension(Context context) {
            context.getClass();
            try {
                if (isSidecarVersionSupported(SidecarCompat.Companion.getSidecarVersion())) {
                    SidecarCompat sidecarCompat = new SidecarCompat(context);
                    if (sidecarCompat.validateExtensionInterface()) {
                        return sidecarCompat;
                    }
                    return null;
                }
            } catch (Throwable unused) {
            }
            return null;
        }

        public final boolean isSidecarVersionSupported(Version version) {
            return version != null && version.compareTo(Version.Companion.getVERSION_0_1()) >= 0;
        }
    }

    /* JADX INFO: compiled from: SidecarWindowBackend.kt */
    public final class ExtensionListenerImpl implements ExtensionInterfaceCompat.ExtensionCallbackInterface {
        public ExtensionListenerImpl() {
        }

        @Override // androidx.window.layout.adapter.sidecar.ExtensionInterfaceCompat.ExtensionCallbackInterface
        public void onWindowLayoutChanged(Activity activity, WindowLayoutInfo windowLayoutInfo) {
            activity.getClass();
            windowLayoutInfo.getClass();
            for (WindowLayoutChangeCallbackWrapper windowLayoutChangeCallbackWrapper : SidecarWindowBackend.this.getWindowLayoutChangeCallbacks()) {
                if (Intrinsics.areEqual(windowLayoutChangeCallbackWrapper.getActivity(), activity)) {
                    windowLayoutChangeCallbackWrapper.accept(windowLayoutInfo);
                }
            }
        }
    }

    /* JADX INFO: compiled from: SidecarWindowBackend.kt */
    public final class WindowLayoutChangeCallbackWrapper {
        private final Activity activity;
        private final Consumer callback;
        private final Executor executor;
        private WindowLayoutInfo lastInfo;

        public WindowLayoutChangeCallbackWrapper(Activity activity, Executor executor, Consumer consumer) {
            activity.getClass();
            executor.getClass();
            consumer.getClass();
            this.activity = activity;
            this.executor = executor;
            this.callback = consumer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void accept$lambda$0(WindowLayoutChangeCallbackWrapper windowLayoutChangeCallbackWrapper, WindowLayoutInfo windowLayoutInfo) {
            windowLayoutChangeCallbackWrapper.callback.accept(windowLayoutInfo);
        }

        public final void accept(final WindowLayoutInfo windowLayoutInfo) {
            windowLayoutInfo.getClass();
            this.lastInfo = windowLayoutInfo;
            this.executor.execute(new Runnable() { // from class: androidx.window.layout.adapter.sidecar.SidecarWindowBackend$WindowLayoutChangeCallbackWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SidecarWindowBackend.WindowLayoutChangeCallbackWrapper.accept$lambda$0(this.f$0, windowLayoutInfo);
                }
            });
        }

        public final Activity getActivity() {
            return this.activity;
        }

        public final Consumer getCallback() {
            return this.callback;
        }

        public final WindowLayoutInfo getLastInfo() {
            return this.lastInfo;
        }
    }

    public SidecarWindowBackend(ExtensionInterfaceCompat extensionInterfaceCompat) {
        this.windowExtension = extensionInterfaceCompat;
        ExtensionInterfaceCompat extensionInterfaceCompat2 = this.windowExtension;
        if (extensionInterfaceCompat2 != null) {
            extensionInterfaceCompat2.setExtensionCallback(new ExtensionListenerImpl());
        }
    }

    private final void callbackRemovedForActivity(Activity activity) {
        CopyOnWriteArrayList copyOnWriteArrayList = this.windowLayoutChangeCallbacks;
        if (copyOnWriteArrayList == null || !copyOnWriteArrayList.isEmpty()) {
            Iterator it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((WindowLayoutChangeCallbackWrapper) it.next()).getActivity(), activity)) {
                    return;
                }
            }
        }
        ExtensionInterfaceCompat extensionInterfaceCompat = this.windowExtension;
        if (extensionInterfaceCompat != null) {
            extensionInterfaceCompat.onWindowLayoutChangeListenerRemoved(activity);
        }
    }

    private final boolean isActivityRegistered(Activity activity) {
        CopyOnWriteArrayList copyOnWriteArrayList = this.windowLayoutChangeCallbacks;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.isEmpty()) {
            return false;
        }
        Iterator it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((WindowLayoutChangeCallbackWrapper) it.next()).getActivity(), activity)) {
                return true;
            }
        }
        return false;
    }

    public final CopyOnWriteArrayList getWindowLayoutChangeCallbacks() {
        return this.windowLayoutChangeCallbacks;
    }

    @Override // androidx.window.layout.adapter.WindowBackend
    public void registerLayoutChangeCallback(Context context, Executor executor, Consumer consumer) {
        Object next;
        context.getClass();
        executor.getClass();
        consumer.getClass();
        Unit unit = null;
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity != null) {
            ReentrantLock reentrantLock = globalLock;
            reentrantLock.lock();
            try {
                ExtensionInterfaceCompat extensionInterfaceCompat = this.windowExtension;
                if (extensionInterfaceCompat == null) {
                    consumer.accept(new WindowLayoutInfo(CollectionsKt.emptyList()));
                    return;
                }
                boolean zIsActivityRegistered = isActivityRegistered(activity);
                WindowLayoutChangeCallbackWrapper windowLayoutChangeCallbackWrapper = new WindowLayoutChangeCallbackWrapper(activity, executor, consumer);
                this.windowLayoutChangeCallbacks.add(windowLayoutChangeCallbackWrapper);
                if (zIsActivityRegistered) {
                    Iterator it = this.windowLayoutChangeCallbacks.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        } else {
                            next = it.next();
                            if (Intrinsics.areEqual(activity, ((WindowLayoutChangeCallbackWrapper) next).getActivity())) {
                                break;
                            }
                        }
                    }
                    WindowLayoutChangeCallbackWrapper windowLayoutChangeCallbackWrapper2 = (WindowLayoutChangeCallbackWrapper) next;
                    WindowLayoutInfo lastInfo = windowLayoutChangeCallbackWrapper2 != null ? windowLayoutChangeCallbackWrapper2.getLastInfo() : null;
                    if (lastInfo != null) {
                        windowLayoutChangeCallbackWrapper.accept(lastInfo);
                    }
                } else {
                    extensionInterfaceCompat.onWindowLayoutChangeListenerAdded(activity);
                }
                Unit unit2 = Unit.INSTANCE;
                reentrantLock.unlock();
                unit = Unit.INSTANCE;
            } finally {
                reentrantLock.unlock();
            }
        }
        if (unit == null) {
            consumer.accept(new WindowLayoutInfo(CollectionsKt.emptyList()));
        }
    }

    @Override // androidx.window.layout.adapter.WindowBackend
    public void unregisterLayoutChangeCallback(Consumer consumer) {
        consumer.getClass();
        synchronized (globalLock) {
            try {
                if (this.windowExtension == null) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (WindowLayoutChangeCallbackWrapper windowLayoutChangeCallbackWrapper : this.windowLayoutChangeCallbacks) {
                    if (windowLayoutChangeCallbackWrapper.getCallback() == consumer) {
                        arrayList.add(windowLayoutChangeCallbackWrapper);
                    }
                }
                this.windowLayoutChangeCallbacks.removeAll(arrayList);
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    callbackRemovedForActivity(((WindowLayoutChangeCallbackWrapper) obj).getActivity());
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
