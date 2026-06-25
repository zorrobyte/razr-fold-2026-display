package com.android.systemui.statusbar.policy;

import android.app.IActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.telephony.TelephonyManager;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class SensitiveNotificationProtectionControllerImpl_Factory implements Factory {
    private final Provider activityManagerProvider;
    private final Provider bgExecutorProvider;
    private final Provider contextProvider;
    private final Provider mainHandlerProvider;
    private final Provider mediaProjectionManagerProvider;
    private final Provider packageManagerProvider;
    private final Provider settingsProvider;
    private final Provider telephonyManagerProvider;

    public SensitiveNotificationProtectionControllerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.settingsProvider = provider2;
        this.mediaProjectionManagerProvider = provider3;
        this.activityManagerProvider = provider4;
        this.packageManagerProvider = provider5;
        this.telephonyManagerProvider = provider6;
        this.mainHandlerProvider = provider7;
        this.bgExecutorProvider = provider8;
    }

    public static SensitiveNotificationProtectionControllerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new SensitiveNotificationProtectionControllerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static SensitiveNotificationProtectionControllerImpl newInstance(Context context, GlobalSettings globalSettings, MediaProjectionManager mediaProjectionManager, IActivityManager iActivityManager, PackageManager packageManager, TelephonyManager telephonyManager, Handler handler, Executor executor) {
        return new SensitiveNotificationProtectionControllerImpl(context, globalSettings, mediaProjectionManager, iActivityManager, packageManager, telephonyManager, handler, executor);
    }

    @Override // javax.inject.Provider
    public SensitiveNotificationProtectionControllerImpl get() {
        return newInstance((Context) this.contextProvider.get(), (GlobalSettings) this.settingsProvider.get(), (MediaProjectionManager) this.mediaProjectionManagerProvider.get(), (IActivityManager) this.activityManagerProvider.get(), (PackageManager) this.packageManagerProvider.get(), (TelephonyManager) this.telephonyManagerProvider.get(), (Handler) this.mainHandlerProvider.get(), (Executor) this.bgExecutorProvider.get());
    }
}
