package com.android.systemui.statusbar;

import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.UserManager;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.settings.SecureSettings;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationLockscreenUserManagerImpl_Factory implements Factory {
    private final Provider backgroundExecutorProvider;
    private final Provider broadcastDispatcherProvider;
    private final Provider clickNotifierProvider;
    private final Provider commonNotifCollectionLazyProvider;
    private final Provider contextProvider;
    private final Provider devicePolicyManagerProvider;
    private final Provider deviceProvisionedControllerProvider;
    private final Provider dumpManagerProvider;
    private final Provider featureFlagsProvider;
    private final Provider keyguardManagerProvider;
    private final Provider lockPatternUtilsProvider;
    private final Provider mainExecutorProvider;
    private final Provider secureSettingsProvider;
    private final Provider userManagerProvider;
    private final Provider userTrackerProvider;
    private final Provider visibilityProviderLazyProvider;

    public NotificationLockscreenUserManagerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.devicePolicyManagerProvider = provider3;
        this.userManagerProvider = provider4;
        this.userTrackerProvider = provider5;
        this.visibilityProviderLazyProvider = provider6;
        this.commonNotifCollectionLazyProvider = provider7;
        this.clickNotifierProvider = provider8;
        this.keyguardManagerProvider = provider9;
        this.mainExecutorProvider = provider10;
        this.backgroundExecutorProvider = provider11;
        this.deviceProvisionedControllerProvider = provider12;
        this.secureSettingsProvider = provider13;
        this.dumpManagerProvider = provider14;
        this.lockPatternUtilsProvider = provider15;
        this.featureFlagsProvider = provider16;
    }

    public static NotificationLockscreenUserManagerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        return new NotificationLockscreenUserManagerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16);
    }

    public static NotificationLockscreenUserManagerImpl newInstance(Context context, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, UserManager userManager, UserTracker userTracker, Lazy lazy, Lazy lazy2, NotificationClickNotifier notificationClickNotifier, KeyguardManager keyguardManager, Executor executor, Executor executor2, DeviceProvisionedController deviceProvisionedController, SecureSettings secureSettings, DumpManager dumpManager, LockPatternUtils lockPatternUtils, FeatureFlagsClassic featureFlagsClassic) {
        return new NotificationLockscreenUserManagerImpl(context, broadcastDispatcher, devicePolicyManager, userManager, userTracker, lazy, lazy2, notificationClickNotifier, keyguardManager, executor, executor2, deviceProvisionedController, secureSettings, dumpManager, lockPatternUtils, featureFlagsClassic);
    }

    @Override // javax.inject.Provider
    public NotificationLockscreenUserManagerImpl get() {
        return newInstance((Context) this.contextProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (DevicePolicyManager) this.devicePolicyManagerProvider.get(), (UserManager) this.userManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), DoubleCheck.lazy(this.visibilityProviderLazyProvider), DoubleCheck.lazy(this.commonNotifCollectionLazyProvider), (NotificationClickNotifier) this.clickNotifierProvider.get(), (KeyguardManager) this.keyguardManagerProvider.get(), (Executor) this.mainExecutorProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (FeatureFlagsClassic) this.featureFlagsProvider.get());
    }
}
