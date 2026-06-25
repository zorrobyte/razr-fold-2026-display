package com.android.systemui.statusbar.policy;

import android.os.Handler;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class DeviceProvisionedControllerImpl_Factory implements Factory {
    private final Provider backgroundHandlerProvider;
    private final Provider dumpManagerProvider;
    private final Provider globalSettingsProvider;
    private final Provider mainExecutorProvider;
    private final Provider secureSettingsProvider;
    private final Provider userTrackerProvider;

    public DeviceProvisionedControllerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.secureSettingsProvider = provider;
        this.globalSettingsProvider = provider2;
        this.userTrackerProvider = provider3;
        this.dumpManagerProvider = provider4;
        this.backgroundHandlerProvider = provider5;
        this.mainExecutorProvider = provider6;
    }

    public static DeviceProvisionedControllerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new DeviceProvisionedControllerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static DeviceProvisionedControllerImpl newInstance(SecureSettings secureSettings, GlobalSettings globalSettings, UserTracker userTracker, DumpManager dumpManager, Handler handler, Executor executor) {
        return new DeviceProvisionedControllerImpl(secureSettings, globalSettings, userTracker, dumpManager, handler, executor);
    }

    @Override // javax.inject.Provider
    public DeviceProvisionedControllerImpl get() {
        return newInstance((SecureSettings) this.secureSettingsProvider.get(), (GlobalSettings) this.globalSettingsProvider.get(), (UserTracker) this.userTrackerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (Handler) this.backgroundHandlerProvider.get(), (Executor) this.mainExecutorProvider.get());
    }
}
