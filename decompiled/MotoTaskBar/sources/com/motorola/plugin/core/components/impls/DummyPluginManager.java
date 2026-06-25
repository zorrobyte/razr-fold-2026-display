package com.motorola.plugin.core.components.impls;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import com.motorola.plugin.core.PluginListener;
import com.motorola.plugin.core.components.PluginManager;
import com.motorola.plugin.core.components.PluginProviderInfoProvider;
import com.motorola.plugin.core.components.PluginWhitelistPolicy;
import com.motorola.plugin.core.context.PluginId;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.extension.ConfigurationController;
import com.motorola.plugin.core.extension.ExtensionController;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.sdk.Plugin;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: DummyPluginManager.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DummyPluginManager implements PluginManager {
    private final Context c;
    private final boolean isInitialized;

    public DummyPluginManager(Context context) {
        context.getClass();
        this.c = context;
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public void addPluginListener(PluginListener pluginListener) {
        PluginManager.DefaultImpls.addPluginListener(this, pluginListener);
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public void addPluginListener(Class cls, PluginListener pluginListener) {
        cls.getClass();
        pluginListener.getClass();
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public boolean dependsOn(Plugin plugin, Class cls) {
        plugin.getClass();
        cls.getClass();
        return false;
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
    }

    @Override // com.motorola.plugin.core.misc.Dumpable
    public void dump(IPrinter iPrinter) {
        iPrinter.getClass();
        iPrinter.printSingle("Nothing to dump for dummy instance");
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter) {
        PluginManager.DefaultImpls.dump(this, str, fileDescriptor, printWriter);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public Map getAvailablePlugins() {
        return MapsKt.emptyMap();
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public ExtensionController getExtensionController() {
        return new ExtensionController() { // from class: com.motorola.plugin.core.components.impls.DummyPluginManager$extensionController$1
            @Override // com.motorola.plugin.core.extension.ExtensionController
            public ExtensionController.ExtensionBuilder newExtension(Class cls) {
                cls.getClass();
                final DummyPluginManager dummyPluginManager = this.this$0;
                return new ExtensionController.ExtensionBuilder() { // from class: com.motorola.plugin.core.components.impls.DummyPluginManager$extensionController$1$newExtension$1
                    @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
                    public ExtensionController.Extension build() {
                        final DummyPluginManager dummyPluginManager2 = dummyPluginManager;
                        return new ExtensionController.Extension() { // from class: com.motorola.plugin.core.components.impls.DummyPluginManager$extensionController$1$newExtension$1$build$1
                            @Override // com.motorola.plugin.core.extension.ExtensionController.Extension
                            public void addCallback(Function1 function1) {
                                function1.getClass();
                            }

                            @Override // com.motorola.plugin.core.extension.ExtensionController.Extension
                            public void clearItem() {
                            }

                            @Override // com.motorola.plugin.core.extension.ExtensionController.Extension
                            public void destroy() {
                            }

                            @Override // com.motorola.plugin.core.misc.Disposable
                            public void dispose() {
                            }

                            @Override // com.motorola.plugin.core.extension.ExtensionController.Extension
                            public Object get() {
                                return null;
                            }

                            @Override // com.motorola.plugin.core.extension.ExtensionController.Extension
                            public Context getContext() {
                                return dummyPluginManager2.c;
                            }

                            @Override // com.motorola.plugin.core.extension.ExtensionController.Extension
                            public Object reload() {
                                return null;
                            }
                        };
                    }

                    @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
                    public ExtensionController.ExtensionBuilder withCallback(Function1 function1) {
                        function1.getClass();
                        return this;
                    }

                    @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
                    public ExtensionController.ExtensionBuilder withDefault(Function0 function0) {
                        function0.getClass();
                        return this;
                    }

                    @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
                    public ExtensionController.ExtensionBuilder withFeature(String str, Function0 function0) {
                        str.getClass();
                        function0.getClass();
                        return this;
                    }

                    @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
                    public ExtensionController.ExtensionBuilder withFeature(Function0 function0, Function0 function02) {
                        function0.getClass();
                        function02.getClass();
                        return this;
                    }

                    @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
                    public ExtensionController.ExtensionBuilder withPlugin(Class cls2) {
                        return ExtensionController.ExtensionBuilder.DefaultImpls.withPlugin(this, cls2);
                    }

                    @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
                    public ExtensionController.ExtensionBuilder withPlugin(Class cls2, Function1 function1) {
                        cls2.getClass();
                        function1.getClass();
                        return this;
                    }

                    @Override // com.motorola.plugin.core.extension.ExtensionController.ExtensionBuilder
                    public ExtensionController.ExtensionBuilder withUiMode(int i, Function0 function0) {
                        function0.getClass();
                        return this;
                    }
                };
            }
        };
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public Plugin getPlugin(Class cls, PluginPackage pluginPackage) {
        cls.getClass();
        return null;
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public Plugin getPlugin(String str, PluginPackage pluginPackage) {
        str.getClass();
        return null;
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public ConfigurationController getPluginConfigurationController() {
        return new ConfigurationController() { // from class: com.motorola.plugin.core.components.impls.DummyPluginManager$pluginConfigurationController$1
            @Override // com.motorola.plugin.core.extension.ConfigurationController
            public void addCallback(ConfigurationController.ConfigurationListener configurationListener) {
                configurationListener.getClass();
            }

            @Override // com.motorola.plugin.core.misc.Disposable
            public void dispose() {
            }

            @Override // com.motorola.plugin.core.extension.ConfigurationController
            public boolean isLayoutRtl() {
                return false;
            }

            @Override // com.motorola.plugin.core.extension.ConfigurationController
            public void notifyThemeChanged() {
            }

            @Override // com.motorola.plugin.core.extension.ConfigurationController
            public void onConfigurationChanged(Configuration configuration) {
                configuration.getClass();
            }

            @Override // com.motorola.plugin.core.extension.ConfigurationController
            public void removeCallback(ConfigurationController.ConfigurationListener configurationListener) {
                configurationListener.getClass();
            }
        };
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public PluginProviderInfoProvider getPluginProviderInfoProvider() {
        return new PluginProviderInfoProvider() { // from class: com.motorola.plugin.core.components.impls.DummyPluginManager$pluginProviderInfoProvider$1
            @Override // com.motorola.plugin.core.components.PluginProviderInfoProvider
            public List getInstalledPluginProviderInfoList(boolean z) {
                return CollectionsKt.emptyList();
            }
        };
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public PluginWhitelistPolicy getPluginWhitelistPolicy() {
        return new PluginWhitelistPolicy() { // from class: com.motorola.plugin.core.components.impls.DummyPluginManager$pluginWhitelistPolicy$1
            private boolean enableAllPluginsIfDebugBuild;

            @Override // com.motorola.plugin.core.components.PluginWhitelistPolicy
            public boolean getEnableAllPluginsIfDebugBuild() {
                return this.enableAllPluginsIfDebugBuild;
            }

            @Override // com.motorola.plugin.core.components.PluginWhitelistPolicy
            @Deprecated
            public void installWhitelistedPlugins(String[] strArr) {
                PluginWhitelistPolicy.DefaultImpls.installWhitelistedPlugins(this, strArr);
            }

            @Override // com.motorola.plugin.core.components.PluginWhitelistPolicy
            public boolean isPluginWhitelisted(ComponentName componentName) {
                componentName.getClass();
                return true;
            }

            @Override // com.motorola.plugin.core.components.PluginWhitelistPolicy
            public boolean isPluginWhitelisted(PluginId pluginId) {
                pluginId.getClass();
                return true;
            }

            @Override // com.motorola.plugin.core.components.PluginWhitelistPolicy
            public void setEnableAllPluginsIfDebugBuild(boolean z) {
                this.enableAllPluginsIfDebugBuild = z;
            }

            @Override // com.motorola.plugin.core.misc.ISnapshotAware
            public ISnapshot snapshot(ISnapshot iSnapshot) {
                iSnapshot.getClass();
                return new ISnapshot() { // from class: com.motorola.plugin.core.components.impls.DummyPluginManager$pluginWhitelistPolicy$1$snapshot$1
                    @Override // com.motorola.plugin.core.misc.Disposable
                    public void dispose() {
                    }

                    @Override // com.motorola.plugin.core.misc.ISnapshot
                    public void onSnapshot(IPrinter iPrinter) {
                        iPrinter.getClass();
                    }
                };
            }
        };
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public void initialize() {
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public boolean isInitialized() {
        return this.isInitialized;
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public boolean isPluginSubscribed(Class cls, PluginPackage pluginPackage) {
        cls.getClass();
        return false;
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public boolean isPluginSubscribed(String str, PluginPackage pluginPackage) {
        str.getClass();
        return false;
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void keepChannelConnectionAlive(boolean z) {
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public boolean launchPlugin(Intent intent, PluginPackage pluginPackage) {
        intent.getClass();
        return false;
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public void removePluginListener(PluginListener pluginListener) {
        PluginManager.DefaultImpls.removePluginListener(this, pluginListener);
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public void removePluginListener(Class cls, PluginListener pluginListener) {
        cls.getClass();
        pluginListener.getClass();
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void subscribePlugin(Class cls, PluginPackage pluginPackage) {
        cls.getClass();
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void subscribePlugin(String str, PluginPackage pluginPackage) {
        str.getClass();
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void unsubscribePlugin(Class cls, PluginPackage pluginPackage) {
        cls.getClass();
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void unsubscribePlugin(String str, PluginPackage pluginPackage) {
        str.getClass();
    }
}
