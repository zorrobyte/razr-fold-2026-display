package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.policy.AvalancheController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpManagerPhone_Factory implements Factory {
    private final Provider avalancheControllerProvider;
    private final Provider configurationControllerProvider;
    private final Provider contextProvider;
    private final Provider executorProvider;
    private final Provider globalSettingsProvider;
    private final Provider groupMembershipManagerProvider;
    private final Provider handlerProvider;
    private final Provider javaAdapterProvider;
    private final Provider systemClockProvider;
    private final Provider uiEventLoggerProvider;
    private final Provider visualStabilityProvider;

    public HeadsUpManagerPhone_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        this.contextProvider = provider;
        this.groupMembershipManagerProvider = provider2;
        this.visualStabilityProvider = provider3;
        this.configurationControllerProvider = provider4;
        this.handlerProvider = provider5;
        this.globalSettingsProvider = provider6;
        this.systemClockProvider = provider7;
        this.executorProvider = provider8;
        this.uiEventLoggerProvider = provider9;
        this.javaAdapterProvider = provider10;
        this.avalancheControllerProvider = provider11;
    }

    public static HeadsUpManagerPhone_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        return new HeadsUpManagerPhone_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static HeadsUpManagerPhone newInstance(Context context, GroupMembershipManager groupMembershipManager, VisualStabilityProvider visualStabilityProvider, ConfigurationController configurationController, Handler handler, GlobalSettings globalSettings, SystemClock systemClock, DelayableExecutor delayableExecutor, UiEventLogger uiEventLogger, JavaAdapter javaAdapter, AvalancheController avalancheController) {
        return new HeadsUpManagerPhone(context, groupMembershipManager, visualStabilityProvider, configurationController, handler, globalSettings, systemClock, delayableExecutor, uiEventLogger, javaAdapter, avalancheController);
    }

    @Override // javax.inject.Provider
    public HeadsUpManagerPhone get() {
        return newInstance((Context) this.contextProvider.get(), (GroupMembershipManager) this.groupMembershipManagerProvider.get(), (VisualStabilityProvider) this.visualStabilityProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (Handler) this.handlerProvider.get(), (GlobalSettings) this.globalSettingsProvider.get(), (SystemClock) this.systemClockProvider.get(), (DelayableExecutor) this.executorProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (JavaAdapter) this.javaAdapterProvider.get(), (AvalancheController) this.avalancheControllerProvider.get());
    }
}
