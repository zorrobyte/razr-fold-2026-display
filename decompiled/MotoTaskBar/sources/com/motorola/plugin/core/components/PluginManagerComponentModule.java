package com.motorola.plugin.core.components;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.UserManager;
import android.view.Display;
import android.view.WindowManager;
import androidx.core.content.ContextCompat;
import com.motorola.plugin.core.components.impls.ConfigurationListenerChainedDispatcherImpl;
import com.motorola.plugin.core.components.impls.DummyPluginManager;
import com.motorola.plugin.core.components.impls.PackageEventMonitorImpl;
import com.motorola.plugin.core.components.impls.PluginEnablerImpl;
import com.motorola.plugin.core.components.impls.PluginEventImpl;
import com.motorola.plugin.core.components.impls.PluginInfoManagerImpl;
import com.motorola.plugin.core.components.impls.PluginListenerDispatcherImpl;
import com.motorola.plugin.core.components.impls.PluginManagerImpl;
import com.motorola.plugin.core.components.impls.PluginProviderInfoProviderImpl;
import com.motorola.plugin.core.components.impls.PluginSubscriberImpl;
import com.motorola.plugin.core.components.impls.PluginWhitelistPolicyImpl;
import com.motorola.plugin.core.container.PluginInstanceContainer;
import com.motorola.plugin.core.container.PluginInstanceContainerImpl;
import com.motorola.plugin.core.context.WindowContext;
import com.motorola.plugin.core.discovery.CompositePluginDiscovery;
import com.motorola.plugin.core.discovery.LocalAppPluginDiscovery;
import com.motorola.plugin.core.discovery.PluginDiscovery;
import com.motorola.plugin.core.discovery.ThirdPartyAppPluginDiscovery;
import com.motorola.plugin.core.extension.ConfigurationController;
import com.motorola.plugin.core.extension.ConfigurationControllerImpl;
import com.motorola.plugin.core.extension.ExtensionController;
import com.motorola.plugin.core.extension.ExtensionControllerImpl;
import com.motorola.plugin.core.misc.DisposableContainer;
import com.motorola.plugin.core.misc.DisposableKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;

/* JADX INFO: compiled from: PluginManagerComponentModule.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginManagerComponentModule {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: PluginManagerComponentModule.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final DisposableContainer bindDisposableContainer() {
            return DisposableKt.newDisposableContainer();
        }

        public final UserManager bindUserManager(Context context) {
            context.getClass();
            Object systemService = ContextCompat.getSystemService(context, UserManager.class);
            systemService.getClass();
            return (UserManager) systemService;
        }

        @AppContext
        public final Context provideApplicationContext(Context context) {
            context.getClass();
            Context applicationContext = context.getApplicationContext();
            applicationContext.getClass();
            return applicationContext;
        }

        @DisplayContext
        @PluginScope
        public final Context provideDisplayContext(Context context) {
            Object objM2707constructorimpl;
            context.getClass();
            Object systemService = ContextCompat.getSystemService(context, WindowManager.class);
            systemService.getClass();
            WindowManager windowManager = (WindowManager) systemService;
            try {
                Result.Companion companion = Result.Companion;
                objM2707constructorimpl = Result.m2707constructorimpl(context.getDisplay());
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
            }
            if (Result.m2709exceptionOrNullimpl(objM2707constructorimpl) != null) {
                objM2707constructorimpl = windowManager.getDefaultDisplay();
            }
            Display display = (Display) objM2707constructorimpl;
            if (display == null) {
                Object systemService2 = ContextCompat.getSystemService(context, DisplayManager.class);
                systemService2.getClass();
                display = ((DisplayManager) systemService2).getDisplay(0);
            }
            Bundle bundle = new Bundle();
            bundle.putBoolean("moto.window_scaling_on_cli.scale_context_only", true);
            Unit unit = Unit.INSTANCE;
            Context contextCreateWindowContext = context.createWindowContext(display, 1000, bundle);
            contextCreateWindowContext.getClass();
            return new WindowContext(contextCreateWindowContext);
        }

        public final LocalAppPluginDiscovery provideLocalAppPluginDiscovery() {
            return LocalAppPluginDiscovery.INSTANCE;
        }

        @PluginScope
        public final PluginDiscovery providePluginDiscovery(ThirdPartyAppPluginDiscovery thirdPartyAppPluginDiscovery, LocalAppPluginDiscovery localAppPluginDiscovery) {
            thirdPartyAppPluginDiscovery.getClass();
            localAppPluginDiscovery.getClass();
            return new CompositePluginDiscovery(thirdPartyAppPluginDiscovery, localAppPluginDiscovery);
        }
    }

    static DisposableContainer bindDisposableContainer() {
        return Companion.bindDisposableContainer();
    }

    static UserManager bindUserManager(Context context) {
        return Companion.bindUserManager(context);
    }

    @AppContext
    static Context provideApplicationContext(Context context) {
        return Companion.provideApplicationContext(context);
    }

    @DisplayContext
    @PluginScope
    static Context provideDisplayContext(Context context) {
        return Companion.provideDisplayContext(context);
    }

    static LocalAppPluginDiscovery provideLocalAppPluginDiscovery() {
        return Companion.provideLocalAppPluginDiscovery();
    }

    @PluginScope
    static PluginDiscovery providePluginDiscovery(ThirdPartyAppPluginDiscovery thirdPartyAppPluginDiscovery, LocalAppPluginDiscovery localAppPluginDiscovery) {
        return Companion.providePluginDiscovery(thirdPartyAppPluginDiscovery, localAppPluginDiscovery);
    }

    @PluginScope
    ConfigurationController bindConfigurationController(ConfigurationControllerImpl configurationControllerImpl);

    @PluginScope
    ConfigurationListenerChainedDispatcher bindConfigurationListenerDispatcher(ConfigurationListenerChainedDispatcherImpl configurationListenerChainedDispatcherImpl);

    @PluginScope
    ExtensionController bindExtensionController(ExtensionControllerImpl extensionControllerImpl);

    @PluginScope
    PluginManager bindMockPluginManager(DummyPluginManager dummyPluginManager);

    @PluginScope
    PackageEventMonitor bindPackageEventMonitor(PackageEventMonitorImpl packageEventMonitorImpl);

    @PluginScope
    PluginEnabler bindPluginEnabler(PluginEnablerImpl pluginEnablerImpl);

    @PluginScope
    PluginEvent bindPluginEvent(PluginEventImpl pluginEventImpl);

    @PluginScope
    PluginInfoManager bindPluginInfoManager(PluginInfoManagerImpl pluginInfoManagerImpl);

    PluginInstanceContainer bindPluginInstanceContainer(PluginInstanceContainerImpl pluginInstanceContainerImpl);

    @PluginScope
    PluginListenerDispatcher bindPluginListenerDispatcher(PluginListenerDispatcherImpl pluginListenerDispatcherImpl);

    @PluginScope
    PluginManager bindPluginManager(PluginManagerImpl pluginManagerImpl);

    @PluginScope
    PluginProviderInfoProvider bindPluginProviderInfoProvider(PluginProviderInfoProviderImpl pluginProviderInfoProviderImpl);

    @PluginScope
    PluginSubscriber bindPluginSubscriber(PluginSubscriberImpl pluginSubscriberImpl);

    @PluginScope
    PluginWhitelistPolicyExt bindPluginWhiteListPolicy(PluginWhitelistPolicyImpl pluginWhitelistPolicyImpl);
}
