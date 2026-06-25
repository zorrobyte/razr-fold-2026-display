package com.android.systemui.statusbar.notification.row;

import android.os.Handler;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSettingsController_Factory implements Factory {
    private final Provider backgroundHandlerProvider;
    private final Provider dumpManagerProvider;
    private final Provider mainHandlerProvider;
    private final Provider secureSettingsProvider;
    private final Provider userTrackerProvider;

    public NotificationSettingsController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.userTrackerProvider = provider;
        this.mainHandlerProvider = provider2;
        this.backgroundHandlerProvider = provider3;
        this.secureSettingsProvider = provider4;
        this.dumpManagerProvider = provider5;
    }

    public static NotificationSettingsController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new NotificationSettingsController_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static NotificationSettingsController newInstance(UserTracker userTracker, Handler handler, Handler handler2, SecureSettings secureSettings, DumpManager dumpManager) {
        return new NotificationSettingsController(userTracker, handler, handler2, secureSettings, dumpManager);
    }

    @Override // javax.inject.Provider
    public NotificationSettingsController get() {
        return newInstance((UserTracker) this.userTrackerProvider.get(), (Handler) this.mainHandlerProvider.get(), (Handler) this.backgroundHandlerProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
