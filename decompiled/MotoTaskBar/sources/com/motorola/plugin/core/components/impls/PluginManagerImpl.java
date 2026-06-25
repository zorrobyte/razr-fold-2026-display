package com.motorola.plugin.core.components.impls;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Looper;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.PluginListener;
import com.motorola.plugin.core.components.AppContext;
import com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher;
import com.motorola.plugin.core.components.PackageEventMonitor;
import com.motorola.plugin.core.components.PluginEnabler;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.components.PluginInfoManager;
import com.motorola.plugin.core.components.PluginListenerDispatcher;
import com.motorola.plugin.core.components.PluginManager;
import com.motorola.plugin.core.components.PluginProviderInfoProvider;
import com.motorola.plugin.core.components.PluginSubscriber;
import com.motorola.plugin.core.components.PluginSubscriberAbility;
import com.motorola.plugin.core.components.PluginWhitelistPolicy;
import com.motorola.plugin.core.components.PluginWhitelistPolicyExt;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.discovery.PluginDiscovery;
import com.motorola.plugin.core.extension.ConfigurationController;
import com.motorola.plugin.core.extension.ExtensionController;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.DeviceState;
import com.motorola.plugin.core.misc.DisposableContainer;
import com.motorola.plugin.core.misc.DisposableKt;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.core.misc.MarkFlag;
import com.motorola.plugin.core.misc.PluginExceptionHandler;
import com.motorola.plugin.sdk.BuildConfig;
import com.motorola.plugin.sdk.Plugin;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* JADX INFO: compiled from: PluginManagerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginManagerImpl implements PluginManager, PackageEventMonitor.PackageEventCallback, PluginSubscriberAbility, ConfigurationController.ConfigurationListener, ISnapshotAware {
    private final Context appContext;
    private boolean isInitialized;
    private final ConfigurationController mConfigurationController;
    private final ConfigurationListenerChainedDispatcher mConfigurationListenerChainedDispatcher;
    private final CoroutineScope mCoroutineScope;
    private final DeviceState mDeviceState;
    private final DisposableContainer mDisposable;
    private final Lazy mExtensionController;
    private final PackageEventMonitor mPackageEventMonitor;
    private final PluginDiscovery mPluginDiscovery;
    private final PluginEnabler mPluginEnabler;
    private final PluginEvent mPluginEvent;
    private final PluginExceptionHandler mPluginExceptionHandler;
    private final PluginInfoManager mPluginInfoManager;
    private final PluginListenerDispatcher mPluginListenerDispatcher;
    private final PluginSubscriber mPluginSubscriber;
    private final PluginWhitelistPolicyExt mPluginWhitelistPolicy;
    private final PluginProviderInfoProvider pluginProviderInfoProvider;

    /* JADX INFO: compiled from: PluginManagerImpl.kt */
    final class PluginServiceSnapshot extends AbstractSnapshot {
        public ISnapshot myConfigurationListenerDispatcherSnapshot;
        public ISnapshot myDeviceStateSnapshot;
        public ISnapshot myPluginDiscoverySnapshot;
        public ISnapshot myPluginEventSnapshot;
        public ISnapshot myPluginInfoCacheSnapshot;
        public ISnapshot myPluginListenerDispatcherSnapshot;
        private int myPluginManagerInstance;
        public ISnapshot myPluginSubscriberSnapshot;
        public ISnapshot myPluginWhitelistPolicySnapshot;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PluginServiceSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final ISnapshot getMyConfigurationListenerDispatcherSnapshot() {
            ISnapshot iSnapshot = this.myConfigurationListenerDispatcherSnapshot;
            if (iSnapshot != null) {
                return iSnapshot;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myConfigurationListenerDispatcherSnapshot");
            throw null;
        }

        public final ISnapshot getMyDeviceStateSnapshot() {
            ISnapshot iSnapshot = this.myDeviceStateSnapshot;
            if (iSnapshot != null) {
                return iSnapshot;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myDeviceStateSnapshot");
            throw null;
        }

        public final ISnapshot getMyPluginDiscoverySnapshot() {
            ISnapshot iSnapshot = this.myPluginDiscoverySnapshot;
            if (iSnapshot != null) {
                return iSnapshot;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myPluginDiscoverySnapshot");
            throw null;
        }

        public final ISnapshot getMyPluginEventSnapshot() {
            ISnapshot iSnapshot = this.myPluginEventSnapshot;
            if (iSnapshot != null) {
                return iSnapshot;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myPluginEventSnapshot");
            throw null;
        }

        public final ISnapshot getMyPluginInfoCacheSnapshot() {
            ISnapshot iSnapshot = this.myPluginInfoCacheSnapshot;
            if (iSnapshot != null) {
                return iSnapshot;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myPluginInfoCacheSnapshot");
            throw null;
        }

        public final ISnapshot getMyPluginListenerDispatcherSnapshot() {
            ISnapshot iSnapshot = this.myPluginListenerDispatcherSnapshot;
            if (iSnapshot != null) {
                return iSnapshot;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myPluginListenerDispatcherSnapshot");
            throw null;
        }

        public final int getMyPluginManagerInstance() {
            return this.myPluginManagerInstance;
        }

        public final ISnapshot getMyPluginSubscriberSnapshot() {
            ISnapshot iSnapshot = this.myPluginSubscriberSnapshot;
            if (iSnapshot != null) {
                return iSnapshot;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myPluginSubscriberSnapshot");
            throw null;
        }

        public final ISnapshot getMyPluginWhitelistPolicySnapshot() {
            ISnapshot iSnapshot = this.myPluginWhitelistPolicySnapshot;
            if (iSnapshot != null) {
                return iSnapshot;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myPluginWhitelistPolicySnapshot");
            throw null;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("PluginService", getMyPluginManagerInstance()).newLine();
            iPrinter.increaseIndent();
            iPrinter.printSingle("Version:").newLine();
            iPrinter.increaseIndent();
            iPrinter.printPair("Core", "3.1.33").printPair("Plugins", "3.1.33").printPair("SDK", BuildConfig.VERSION_NAME).newLine();
            iPrinter.decreaseIndent();
            getMyDeviceStateSnapshot().onSnapshot(iPrinter);
            getMyPluginListenerDispatcherSnapshot().onSnapshot(iPrinter);
            getMyConfigurationListenerDispatcherSnapshot().onSnapshot(iPrinter);
            getMyPluginWhitelistPolicySnapshot().onSnapshot(iPrinter);
            getMyPluginInfoCacheSnapshot().onSnapshot(iPrinter);
            getMyPluginSubscriberSnapshot().onSnapshot(iPrinter);
            getMyPluginDiscoverySnapshot().onSnapshot(iPrinter);
            getMyPluginEventSnapshot().onSnapshot(iPrinter);
            iPrinter.decreaseIndent();
            iPrinter.newLine();
        }

        public final void setMyConfigurationListenerDispatcherSnapshot(ISnapshot iSnapshot) {
            iSnapshot.getClass();
            this.myConfigurationListenerDispatcherSnapshot = iSnapshot;
        }

        public final void setMyDeviceStateSnapshot(ISnapshot iSnapshot) {
            iSnapshot.getClass();
            this.myDeviceStateSnapshot = iSnapshot;
        }

        public final void setMyPluginDiscoverySnapshot(ISnapshot iSnapshot) {
            iSnapshot.getClass();
            this.myPluginDiscoverySnapshot = iSnapshot;
        }

        public final void setMyPluginEventSnapshot(ISnapshot iSnapshot) {
            iSnapshot.getClass();
            this.myPluginEventSnapshot = iSnapshot;
        }

        public final void setMyPluginInfoCacheSnapshot(ISnapshot iSnapshot) {
            iSnapshot.getClass();
            this.myPluginInfoCacheSnapshot = iSnapshot;
        }

        public final void setMyPluginListenerDispatcherSnapshot(ISnapshot iSnapshot) {
            iSnapshot.getClass();
            this.myPluginListenerDispatcherSnapshot = iSnapshot;
        }

        public final void setMyPluginManagerInstance(int i) {
            this.myPluginManagerInstance = i;
        }

        public final void setMyPluginSubscriberSnapshot(ISnapshot iSnapshot) {
            iSnapshot.getClass();
            this.myPluginSubscriberSnapshot = iSnapshot;
        }

        public final void setMyPluginWhitelistPolicySnapshot(ISnapshot iSnapshot) {
            iSnapshot.getClass();
            this.myPluginWhitelistPolicySnapshot = iSnapshot;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginManagerImpl$dispose$1, reason: invalid class name */
    /* JADX INFO: compiled from: PluginManagerImpl.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Runtime.getRuntime().gc();
            Runtime.getRuntime().runFinalization();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginManagerImpl$onPluginPackageChanged$3, reason: invalid class name */
    /* JADX INFO: compiled from: PluginManagerImpl.kt */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ ComponentName $componentName;
        final /* synthetic */ MarkFlag $markFlag;
        final /* synthetic */ PluginPackage $pluginPackage;
        int label;

        /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginManagerImpl$onPluginPackageChanged$3$1, reason: invalid class name */
        /* JADX INFO: compiled from: PluginManagerImpl.kt */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ MarkFlag $markFlag;
            final /* synthetic */ PluginPackage $pluginPackage;
            int label;
            final /* synthetic */ PluginManagerImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(PluginManagerImpl pluginManagerImpl, PluginPackage pluginPackage, MarkFlag markFlag, Continuation continuation) {
                super(2, continuation);
                this.this$0 = pluginManagerImpl;
                this.$pluginPackage = pluginPackage;
                this.$markFlag = markFlag;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, this.$pluginPackage, this.$markFlag, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.mPluginInfoManager.onPluginPackageChanged(this.$pluginPackage, this.$markFlag);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(PluginPackage pluginPackage, ComponentName componentName, MarkFlag markFlag, Continuation continuation) {
            super(2, continuation);
            this.$pluginPackage = pluginPackage;
            this.$componentName = componentName;
            this.$markFlag = markFlag;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PluginManagerImpl.this.new AnonymousClass3(this.$pluginPackage, this.$componentName, this.$markFlag, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PluginManagerImpl.this.mPluginSubscriber.onPluginPackageRemoved(this.$pluginPackage, this.$componentName);
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(PluginManagerImpl.this, this.$pluginPackage, this.$markFlag, null);
                this.label = 1;
                if (BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            PluginManagerImpl.this.mPluginSubscriber.onPluginPackageChanged(this.$pluginPackage, this.$componentName, this.$markFlag);
            PluginManagerImpl.this.mPluginListenerDispatcher.dispatchPluginPackageChanged(this.$pluginPackage, this.$markFlag.copy(true));
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginManagerImpl$startScanPluginLoop$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PluginManagerImpl.kt */
    final class C01701 extends SuspendLambda implements Function2 {
        int label;

        /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginManagerImpl$startScanPluginLoop$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: PluginManagerImpl.kt */
        final class C00381 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PluginManagerImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00381(PluginManagerImpl pluginManagerImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = pluginManagerImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00381(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00381) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.mPluginListenerDispatcher.dispatchPluginPackageChanged(PluginPackage.Companion.of$default(PluginPackage.Companion, ExtensionsKt.toPluginId(""), null, 2, null), MarkFlag.Companion.getEMPTY());
                return Unit.INSTANCE;
            }
        }

        C01701(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PluginManagerImpl.this.new C01701(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01701) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PluginManagerImpl.this.mPluginInfoManager.startGatheringPluginInfoList();
                PluginManagerImpl.this.mPluginSubscriber.initialize();
                PluginManagerImpl.this.setInitialized(true);
                MainCoroutineDispatcher main = Dispatchers.getMain();
                C00381 c00381 = new C00381(PluginManagerImpl.this, null);
                this.label = 1;
                if (BuildersKt.withContext(main, c00381, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public PluginManagerImpl(@AppContext Context context, PluginWhitelistPolicyExt pluginWhitelistPolicyExt, PluginEnabler pluginEnabler, PluginEvent pluginEvent, PluginDiscovery pluginDiscovery, PackageEventMonitor packageEventMonitor, PluginInfoManager pluginInfoManager, PluginProviderInfoProvider pluginProviderInfoProvider, Lazy lazy, PluginListenerDispatcher pluginListenerDispatcher, PluginSubscriber pluginSubscriber, ConfigurationController configurationController, ConfigurationListenerChainedDispatcher configurationListenerChainedDispatcher, PluginExceptionHandler pluginExceptionHandler, DeviceState deviceState, DisposableContainer disposableContainer) {
        context.getClass();
        pluginWhitelistPolicyExt.getClass();
        pluginEnabler.getClass();
        pluginEvent.getClass();
        pluginDiscovery.getClass();
        packageEventMonitor.getClass();
        pluginInfoManager.getClass();
        pluginProviderInfoProvider.getClass();
        lazy.getClass();
        pluginListenerDispatcher.getClass();
        pluginSubscriber.getClass();
        configurationController.getClass();
        configurationListenerChainedDispatcher.getClass();
        pluginExceptionHandler.getClass();
        deviceState.getClass();
        disposableContainer.getClass();
        this.appContext = context;
        this.mPluginWhitelistPolicy = pluginWhitelistPolicyExt;
        this.mPluginEnabler = pluginEnabler;
        this.mPluginEvent = pluginEvent;
        this.mPluginDiscovery = pluginDiscovery;
        this.mPackageEventMonitor = packageEventMonitor;
        this.mPluginInfoManager = pluginInfoManager;
        this.mExtensionController = lazy;
        this.mPluginListenerDispatcher = pluginListenerDispatcher;
        this.mPluginSubscriber = pluginSubscriber;
        this.mConfigurationController = configurationController;
        this.mConfigurationListenerChainedDispatcher = configurationListenerChainedDispatcher;
        this.mPluginExceptionHandler = pluginExceptionHandler;
        this.mDeviceState = deviceState;
        this.mDisposable = disposableContainer;
        CoroutineScope coroutineScopeMainScope = CoroutineScopeKt.MainScope();
        this.mCoroutineScope = coroutineScopeMainScope;
        DisposableKt.add(disposableContainer, coroutineScopeMainScope);
        disposableContainer.add(pluginDiscovery);
        disposableContainer.add(packageEventMonitor);
        disposableContainer.add(pluginSubscriber);
        disposableContainer.add(pluginListenerDispatcher);
        disposableContainer.add(pluginInfoManager);
        disposableContainer.add(configurationController);
        disposableContainer.add(configurationListenerChainedDispatcher);
        disposableContainer.add(deviceState);
        disposableContainer.add(pluginEvent);
        this.pluginProviderInfoProvider = pluginProviderInfoProvider;
    }

    private final void startScanPluginLoop() {
        setInitialized(false);
        BuildersKt__Builders_commonKt.launch$default(this.mCoroutineScope, Dispatchers.getDefault(), null, new C01701(null), 2, null);
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public void addPluginListener(PluginListener pluginListener) {
        PluginManager.DefaultImpls.addPluginListener(this, pluginListener);
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public void addPluginListener(Class cls, PluginListener pluginListener) {
        cls.getClass();
        pluginListener.getClass();
        this.mPluginListenerDispatcher.addPluginListener(cls, pluginListener);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public boolean dependsOn(Plugin plugin, Class cls) {
        plugin.getClass();
        cls.getClass();
        return this.mPluginSubscriber.dependsOn(plugin, cls);
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        this.mDisposable.dispose();
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(null), 3, null);
    }

    @Override // com.motorola.plugin.core.misc.Dumpable
    public void dump(IPrinter iPrinter) {
        iPrinter.getClass();
        ISnapshotAware.DefaultImpls.snapshot$default(this, null, 1, null).onSnapshot(iPrinter);
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter) {
        PluginManager.DefaultImpls.dump(this, str, fileDescriptor, printWriter);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public Map getAvailablePlugins() {
        return this.mPluginSubscriber.getAvailablePlugins();
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public ExtensionController getExtensionController() {
        Object obj = this.mExtensionController.get();
        obj.getClass();
        return (ExtensionController) obj;
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public Plugin getPlugin(Class cls, PluginPackage pluginPackage) {
        cls.getClass();
        return this.mPluginSubscriber.getPlugin(cls, pluginPackage);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public Plugin getPlugin(String str, PluginPackage pluginPackage) {
        str.getClass();
        return this.mPluginSubscriber.getPlugin(str, pluginPackage);
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public ConfigurationController getPluginConfigurationController() {
        return this.mConfigurationController;
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public PluginProviderInfoProvider getPluginProviderInfoProvider() {
        return this.pluginProviderInfoProvider;
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public PluginWhitelistPolicy getPluginWhitelistPolicy() {
        return this.mPluginWhitelistPolicy;
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public void initialize() {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginManagerImpl.initialize.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return StringsKt.trimIndent("\n                Plugin framework initialized in host " + ((Object) PluginManagerImpl.this.appContext.getPackageName()) + ":\n                Core: 3.1.33\n                Plugins: 3.1.33\n                SDK: 3.1.1-1\n            ");
            }
        }, 60, null);
        this.mPluginEvent.onInitialized();
        this.mPluginExceptionHandler.attach(this);
        PackageEventMonitor packageEventMonitor = this.mPackageEventMonitor;
        Looper mainLooper = Looper.getMainLooper();
        mainLooper.getClass();
        packageEventMonitor.startListening(this, mainLooper);
        this.mConfigurationListenerChainedDispatcher.addChainedListener(this.mPluginInfoManager, this.mPluginSubscriber);
        this.mConfigurationController.addCallback(this);
        startScanPluginLoop();
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public boolean isInitialized() {
        return this.isInitialized;
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public boolean isPluginSubscribed(Class cls, PluginPackage pluginPackage) {
        cls.getClass();
        return this.mPluginSubscriber.isPluginSubscribed(cls, pluginPackage);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public boolean isPluginSubscribed(String str, PluginPackage pluginPackage) {
        str.getClass();
        return this.mPluginSubscriber.isPluginSubscribed(str, pluginPackage);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void keepChannelConnectionAlive(boolean z) {
        this.mPluginSubscriber.keepChannelConnectionAlive(z);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public boolean launchPlugin(Intent intent, PluginPackage pluginPackage) {
        intent.getClass();
        return this.mPluginSubscriber.launchPlugin(intent, pluginPackage);
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
    public void onConfigChanged(Configuration configuration, BitFlag bitFlag) {
        configuration.getClass();
        bitFlag.getClass();
        this.mConfigurationListenerChainedDispatcher.onConfigChanged(configuration, bitFlag);
    }

    @Override // com.motorola.plugin.core.components.PackageEventMonitor.PackageEventCallback
    public void onDisablePlugin(ComponentName componentName) {
        componentName.getClass();
        if (this.mPluginWhitelistPolicy.isPluginWhitelisted(componentName)) {
            return;
        }
        this.mPluginEnabler.setDisabled(componentName, 2);
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
    public void onLowMemory() {
        this.mConfigurationListenerChainedDispatcher.onLowMemory();
    }

    @Override // com.motorola.plugin.core.components.PackageEventMonitor.PackageEventCallback
    public void onPluginPackageChanged(final PluginPackage pluginPackage, ComponentName componentName, final MarkFlag markFlag) {
        pluginPackage.getClass();
        markFlag.getClass();
        if (ExtensionsKt.isPrimary(pluginPackage.getUserHandle())) {
            this.mPluginWhitelistPolicy.onPluginPackageChanged(pluginPackage, markFlag);
            if (markFlag.deleted() || this.mPluginWhitelistPolicy.isPluginWhitelisted(pluginPackage.getPluginId())) {
                if (Intrinsics.areEqual(pluginPackage.getPluginId().getId(), this.appContext.getPackageName())) {
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginManagerImpl.onPluginPackageChanged.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return "Ignore host package changed event [" + pluginPackage + "], " + markFlag;
                        }
                    }, 60, null);
                    return;
                }
                PluginEvent.DefaultImpls.recordEvent$default(this.mPluginEvent, "package [" + pluginPackage + "], " + markFlag, null, 2, null);
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginManagerImpl.onPluginPackageChanged.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "onPluginPackageChanged [" + pluginPackage + "], " + markFlag;
                    }
                }, 60, null);
                BuildersKt__Builders_commonKt.launch$default(this.mCoroutineScope, null, null, new AnonymousClass3(pluginPackage, componentName, markFlag, null), 3, null);
            }
        }
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
    public void onTrimMemory(int i) {
        this.mConfigurationListenerChainedDispatcher.onTrimMemory(i);
    }

    @Override // com.motorola.plugin.core.components.PackageEventMonitor.PackageEventCallback
    public void onUserUnlocked() {
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public void removePluginListener(PluginListener pluginListener) {
        PluginManager.DefaultImpls.removePluginListener(this, pluginListener);
    }

    @Override // com.motorola.plugin.core.components.PluginManager
    public void removePluginListener(Class cls, PluginListener pluginListener) {
        cls.getClass();
        pluginListener.getClass();
        this.mPluginListenerDispatcher.removePluginListener(cls, pluginListener);
    }

    public void setInitialized(boolean z) {
        this.isInitialized = z;
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        PluginServiceSnapshot pluginServiceSnapshot = new PluginServiceSnapshot(iSnapshot);
        pluginServiceSnapshot.setMyPluginManagerInstance(hashCode());
        pluginServiceSnapshot.setMyDeviceStateSnapshot(ISnapshotAware.DefaultImpls.snapshot$default(this.mDeviceState, null, 1, null));
        pluginServiceSnapshot.setMyPluginInfoCacheSnapshot(ISnapshotAware.DefaultImpls.snapshot$default(this.mPluginInfoManager, null, 1, null));
        pluginServiceSnapshot.setMyPluginSubscriberSnapshot(ISnapshotAware.DefaultImpls.snapshot$default(this.mPluginSubscriber, null, 1, null));
        pluginServiceSnapshot.setMyPluginWhitelistPolicySnapshot(ISnapshotAware.DefaultImpls.snapshot$default(this.mPluginWhitelistPolicy, null, 1, null));
        pluginServiceSnapshot.setMyPluginListenerDispatcherSnapshot(ISnapshotAware.DefaultImpls.snapshot$default(this.mPluginListenerDispatcher, null, 1, null));
        pluginServiceSnapshot.setMyConfigurationListenerDispatcherSnapshot(ISnapshotAware.DefaultImpls.snapshot$default(this.mConfigurationListenerChainedDispatcher, null, 1, null));
        pluginServiceSnapshot.setMyPluginDiscoverySnapshot(ISnapshotAware.DefaultImpls.snapshot$default(this.mPluginDiscovery, null, 1, null));
        pluginServiceSnapshot.setMyPluginEventSnapshot(ISnapshotAware.DefaultImpls.snapshot$default(this.mPluginEvent, null, 1, null));
        return pluginServiceSnapshot;
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void subscribePlugin(Class cls, PluginPackage pluginPackage) {
        cls.getClass();
        this.mPluginSubscriber.subscribePlugin(cls, pluginPackage);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void subscribePlugin(String str, PluginPackage pluginPackage) {
        str.getClass();
        this.mPluginSubscriber.subscribePlugin(str, pluginPackage);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void unsubscribePlugin(Class cls, PluginPackage pluginPackage) {
        cls.getClass();
        this.mPluginSubscriber.unsubscribePlugin(cls, pluginPackage);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void unsubscribePlugin(String str, PluginPackage pluginPackage) {
        str.getClass();
        this.mPluginSubscriber.unsubscribePlugin(str, pluginPackage);
    }
}
