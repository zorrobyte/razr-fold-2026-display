package com.motorola.plugin.core.provider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextThemeWrapper;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher;
import com.motorola.plugin.core.components.DisplayContext;
import com.motorola.plugin.core.components.PluginEnabler;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.components.PluginWhitelistPolicyExt;
import com.motorola.plugin.core.context.PluginClassLoaderFilter;
import com.motorola.plugin.core.context.PluginContext;
import com.motorola.plugin.core.context.PluginId;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.context.SharedInPluginClassLoader;
import com.motorola.plugin.core.context.VersionInfo;
import com.motorola.plugin.core.discovery.CompatibilityInfo;
import com.motorola.plugin.core.discovery.PluginInfo;
import com.motorola.plugin.core.discovery.PluginInfoParserKt;
import com.motorola.plugin.core.extension.ConfigurationController;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.BitFlagKt;
import com.motorola.plugin.core.misc.CleanupResourcesKt;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.MarkFlag;
import com.motorola.plugin.core.misc.NotPluginClassException;
import com.motorola.plugin.core.rule.IRule;
import com.motorola.plugin.core.utils.HiddenApiKt;
import com.motorola.plugin.sdk.BuildConfig;
import com.motorola.plugin.sdk.Plugin;
import com.motorola.plugin.sdk.channel.RemoteService;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SideLoadPluginProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class SideLoadPluginProvider implements SharedLockPluginInfoProvider {
    private final ConfigurationController configurationController;
    private final Context hostContext;
    private boolean mDisposed;
    private final Map mIgnoredPluginMap;
    private Lock mLock;
    private final Map mPluginContextMap;
    private final Map mPluginInfoByPluginPackage;
    private final Lazy myPluginParentClassLoader$delegate;
    private final PluginEnabler pluginEnabler;
    private final PluginEvent pluginEvent;
    private final PluginWhitelistPolicyExt pluginWhitelistPolicy;
    private final String tag;

    /* JADX INFO: compiled from: SideLoadPluginProvider.kt */
    final class PluginInfoCacheSnapshot extends AbstractSnapshot {
        public Map myIgnoredPluginMap;
        private int myInstalledPluginInfoSize;
        private int myPluginInfoInstance;
        public String myPluginInfoInstanceTag;
        public Map myPluginInfoMap;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PluginInfoCacheSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final Map getMyIgnoredPluginMap() {
            Map map = this.myIgnoredPluginMap;
            if (map != null) {
                return map;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myIgnoredPluginMap");
            throw null;
        }

        public final int getMyInstalledPluginInfoSize() {
            return this.myInstalledPluginInfoSize;
        }

        public final int getMyPluginInfoInstance() {
            return this.myPluginInfoInstance;
        }

        public final String getMyPluginInfoInstanceTag() {
            String str = this.myPluginInfoInstanceTag;
            if (str != null) {
                return str;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myPluginInfoInstanceTag");
            throw null;
        }

        public final Map getMyPluginInfoMap() {
            Map map = this.myPluginInfoMap;
            if (map != null) {
                return map;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myPluginInfoMap");
            throw null;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("PluginProvider(" + getMyPluginInfoInstanceTag() + ')', getMyPluginInfoInstance()).newLine();
            iPrinter.increaseIndent();
            iPrinter.printSingle("InstalledPlugins: ").printPair("size", Integer.valueOf(getMyInstalledPluginInfoSize())).newLine();
            for (Map.Entry entry : getMyPluginInfoMap().entrySet()) {
                iPrinter.increaseIndent();
                iPrinter.printPair("package", entry.getKey()).newLine();
                iPrinter.increaseIndent();
                int i = 0;
                for (Object obj : (Iterable) entry.getValue()) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IPrinter.DefaultImpls.printIndex$default(iPrinter, i, (PluginInfo) obj, null, 4, null);
                    iPrinter.newLine();
                    i = i2;
                }
                iPrinter.decreaseIndent();
                iPrinter.newLine();
                iPrinter.decreaseIndent();
            }
            iPrinter.printSingle("IgnoredPlugins: ").printPair("size", Integer.valueOf(getMyIgnoredPluginMap().size())).newLine();
            for (Map.Entry entry2 : getMyIgnoredPluginMap().entrySet()) {
                String str = (String) entry2.getKey();
                String str2 = (String) entry2.getValue();
                iPrinter.increaseIndent();
                iPrinter.printPair("component", str).newLine();
                iPrinter.printPair("reason", str2).newLine();
                iPrinter.decreaseIndent();
            }
            iPrinter.decreaseIndent();
            iPrinter.newLine();
        }

        public final void setMyIgnoredPluginMap(Map map) {
            map.getClass();
            this.myIgnoredPluginMap = map;
        }

        public final void setMyInstalledPluginInfoSize(int i) {
            this.myInstalledPluginInfoSize = i;
        }

        public final void setMyPluginInfoInstance(int i) {
            this.myPluginInfoInstance = i;
        }

        public final void setMyPluginInfoInstanceTag(String str) {
            str.getClass();
            this.myPluginInfoInstanceTag = str;
        }

        public final void setMyPluginInfoMap(Map map) {
            map.getClass();
            this.myPluginInfoMap = map;
        }
    }

    public SideLoadPluginProvider(String str, @DisplayContext Context context, PluginWhitelistPolicyExt pluginWhitelistPolicyExt, ConfigurationController configurationController, PluginEnabler pluginEnabler, PluginEvent pluginEvent) {
        str.getClass();
        context.getClass();
        pluginWhitelistPolicyExt.getClass();
        configurationController.getClass();
        pluginEnabler.getClass();
        pluginEvent.getClass();
        this.tag = str;
        this.hostContext = context;
        this.pluginWhitelistPolicy = pluginWhitelistPolicyExt;
        this.configurationController = configurationController;
        this.pluginEnabler = pluginEnabler;
        this.pluginEvent = pluginEvent;
        this.mPluginInfoByPluginPackage = new LinkedHashMap();
        this.mIgnoredPluginMap = new LinkedHashMap();
        this.mPluginContextMap = new LinkedHashMap();
        this.myPluginParentClassLoader$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$myPluginParentClassLoader$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final SharedInPluginClassLoader mo2224invoke() {
                ClassLoader classLoader = this.this$0.getClass().getClassLoader();
                classLoader.getClass();
                return new SharedInPluginClassLoader(new PluginClassLoaderFilter(classLoader, BuildConfig.LIBRARY_PACKAGE_NAME, com.motorola.plugins.BuildConfig.LIBRARY_PACKAGE_NAME, "com.motorola.android.checkin.provider", "motorola.core_services", "motorola.proxy.input", "motorola.wrap.android", "com.motorola.android.provider"));
            }
        });
    }

    private final void checkPluginCompatibility(final PluginInfo pluginInfo) {
        Object objM2707constructorimpl;
        Object objM2707constructorimpl2;
        Object objM2707constructorimpl3;
        Object objM2707constructorimpl4;
        Object objM2707constructorimpl5;
        String className;
        Object objM2707constructorimpl6;
        Throwable thM2709exceptionOrNullimpl;
        String implementorPluginClass;
        Object objM2707constructorimpl7;
        Throwable thM2709exceptionOrNullimpl2;
        String prototypePluginClass;
        Object objM2707constructorimpl8;
        Throwable thM2709exceptionOrNullimpl3;
        BitFlag bitFlagWrap = BitFlag.Companion.wrap(0);
        BitFlagKt.add(bitFlagWrap, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider.checkPluginCompatibility.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public final int invoke() {
                return ((Number) ExtensionsKt.ifElse(SideLoadPluginProvider.this.pluginWhitelistPolicy.isPluginWhitelisted(pluginInfo.getPluginPackage().getPluginId()), 0, 8)).intValue();
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
            public /* bridge */ /* synthetic */ Object mo2224invoke() {
                return Integer.valueOf(invoke());
            }
        });
        BitFlagKt.add(bitFlagWrap, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider.checkPluginCompatibility.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public final int invoke() {
                int disableReason = SideLoadPluginProvider.this.pluginEnabler.getDisableReason(pluginInfo.getPluginComponent());
                return (disableReason == 1 || disableReason == 2 || disableReason == 3 || disableReason == 4) ? 16 : 0;
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public /* bridge */ /* synthetic */ Object mo2224invoke() {
                return Integer.valueOf(invoke());
            }
        });
        try {
            Result.Companion companion = Result.Companion;
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$checkPluginCompatibility$prototypePluginClass$1$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "Validate plugin prototype [" + pluginInfo.getPluginPackage().getPluginId() + '/' + pluginInfo.getPrototypePluginClass() + ']';
                }
            }, 60, null);
            prototypePluginClass = pluginInfo.getPrototypePluginClass();
            try {
                objM2707constructorimpl8 = Result.m2707constructorimpl(Class.forName(prototypePluginClass, false, getMyPluginParentClassLoader()).asSubclass(Plugin.class));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM2707constructorimpl8 = Result.m2707constructorimpl(ResultKt.createFailure(th));
            }
            thM2709exceptionOrNullimpl3 = Result.m2709exceptionOrNullimpl(objM2707constructorimpl8);
        } catch (Throwable th2) {
            Result.Companion companion3 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th2));
        }
        if (thM2709exceptionOrNullimpl3 != null) {
            throw new NotPluginClassException(prototypePluginClass, thM2709exceptionOrNullimpl3);
        }
        objM2707constructorimpl8.getClass();
        objM2707constructorimpl = Result.m2707constructorimpl((Class) objM2707constructorimpl8);
        Throwable thM2709exceptionOrNullimpl4 = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl4 != null) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.WARN, false, thM2709exceptionOrNullimpl4, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$checkPluginCompatibility$prototypePluginClass$2$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "Plugin [" + pluginInfo.getPluginPackage().getPluginId() + '/' + pluginInfo.getPrototypePluginClass() + "] is invalid plugin prototype subclass";
                }
            }, 52, null);
            bitFlagWrap.add(4);
        }
        if (Result.m2711isFailureimpl(objM2707constructorimpl)) {
            objM2707constructorimpl = null;
        }
        Class cls = (Class) objM2707constructorimpl;
        try {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$checkPluginCompatibility$implementorPluginClass$1$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "Validate plugin implementor [" + pluginInfo.getPluginPackage().getPluginId() + '/' + pluginInfo.getImplementorPluginClass() + ']';
                }
            }, 60, null);
            implementorPluginClass = pluginInfo.getImplementorPluginClass();
            try {
                objM2707constructorimpl7 = Result.m2707constructorimpl(Class.forName(implementorPluginClass, false, pluginInfo.getClassLoader()).asSubclass(Plugin.class));
            } catch (Throwable th3) {
                Result.Companion companion4 = Result.Companion;
                objM2707constructorimpl7 = Result.m2707constructorimpl(ResultKt.createFailure(th3));
            }
            thM2709exceptionOrNullimpl2 = Result.m2709exceptionOrNullimpl(objM2707constructorimpl7);
        } catch (Throwable th4) {
            Result.Companion companion5 = Result.Companion;
            objM2707constructorimpl2 = Result.m2707constructorimpl(ResultKt.createFailure(th4));
        }
        if (thM2709exceptionOrNullimpl2 != null) {
            throw new NotPluginClassException(implementorPluginClass, thM2709exceptionOrNullimpl2);
        }
        objM2707constructorimpl7.getClass();
        objM2707constructorimpl2 = Result.m2707constructorimpl((Class) objM2707constructorimpl7);
        Throwable thM2709exceptionOrNullimpl5 = Result.m2709exceptionOrNullimpl(objM2707constructorimpl2);
        if (thM2709exceptionOrNullimpl5 != null) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.WARN, false, thM2709exceptionOrNullimpl5, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$checkPluginCompatibility$implementorPluginClass$2$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "Plugin [" + pluginInfo.getPluginPackage().getPluginId() + '/' + pluginInfo.getImplementorPluginClass() + "] is invalid plugin implementor subclass";
                }
            }, 52, null);
            bitFlagWrap.add(4);
        }
        if (Result.m2711isFailureimpl(objM2707constructorimpl2)) {
            objM2707constructorimpl2 = null;
        }
        Class cls2 = (Class) objM2707constructorimpl2;
        try {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$checkPluginCompatibility$implementorRemoteServiceClass$1$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "Validate plugin channel service [" + pluginInfo.getPluginPackage().getPluginId() + '/' + pluginInfo.getChannelServiceComponent().getClassName() + ']';
                }
            }, 60, null);
            className = pluginInfo.getChannelServiceComponent().getClassName();
            className.getClass();
            try {
                objM2707constructorimpl6 = Result.m2707constructorimpl(Class.forName(className, false, pluginInfo.getClassLoader()).asSubclass(RemoteService.class));
            } catch (Throwable th5) {
                Result.Companion companion6 = Result.Companion;
                objM2707constructorimpl6 = Result.m2707constructorimpl(ResultKt.createFailure(th5));
            }
            thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl6);
        } catch (Throwable th6) {
            Result.Companion companion7 = Result.Companion;
            objM2707constructorimpl3 = Result.m2707constructorimpl(ResultKt.createFailure(th6));
        }
        if (thM2709exceptionOrNullimpl != null) {
            throw new NotPluginClassException(className, thM2709exceptionOrNullimpl);
        }
        objM2707constructorimpl6.getClass();
        objM2707constructorimpl3 = Result.m2707constructorimpl((Class) objM2707constructorimpl6);
        Throwable thM2709exceptionOrNullimpl6 = Result.m2709exceptionOrNullimpl(objM2707constructorimpl3);
        if (thM2709exceptionOrNullimpl6 != null) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.WARN, false, thM2709exceptionOrNullimpl6, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$checkPluginCompatibility$implementorRemoteServiceClass$2$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "Plugin [" + pluginInfo.getPluginPackage().getPluginId() + '/' + pluginInfo.getChannelServiceComponent().getClassName() + "] is invalid RemoteService subclass";
                }
            }, 52, null);
            bitFlagWrap.add(4);
        }
        Class cls3 = (Class) (Result.m2711isFailureimpl(objM2707constructorimpl3) ? null : objM2707constructorimpl3);
        if (cls != null && cls2 != null) {
            try {
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$checkPluginCompatibility$3$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return Intrinsics.stringPlus("Validate plugin version for ", pluginInfo.getPluginPackage().getPluginId());
                    }
                }, 60, null);
                pluginInfo.setVersionInfo(VersionInfo.Companion.checkVersionOrThrow(cls, cls2));
                objM2707constructorimpl5 = Result.m2707constructorimpl(Unit.INSTANCE);
            } catch (Throwable th7) {
                Result.Companion companion8 = Result.Companion;
                objM2707constructorimpl5 = Result.m2707constructorimpl(ResultKt.createFailure(th7));
            }
            final Throwable thM2709exceptionOrNullimpl7 = Result.m2709exceptionOrNullimpl(objM2707constructorimpl5);
            if (thM2709exceptionOrNullimpl7 != null) {
                BitFlagKt.add(bitFlagWrap, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$checkPluginCompatibility$4$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    public final int invoke() {
                        final String str;
                        Throwable th8 = thM2709exceptionOrNullimpl7;
                        if (!(th8 instanceof VersionInfo.InvalidVersionException)) {
                            return 32;
                        }
                        if (((VersionInfo.InvalidVersionException) th8).isTooNew()) {
                            str = "Plugin [" + pluginInfo.getPluginComponent().flattenToShortString() + "] is too new.\nCheck to see if sdk upgrade is available.\n" + ((Object) thM2709exceptionOrNullimpl7.getMessage());
                        } else {
                            str = "Plugin [" + pluginInfo.getPluginComponent().flattenToShortString() + "] is too old.\nContact plugin developer to get an updated version.\n" + ((Object) thM2709exceptionOrNullimpl7.getMessage());
                        }
                        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$checkPluginCompatibility$4$1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* JADX INFO: renamed from: invoke */
                            public final Object mo2224invoke() {
                                return str;
                            }
                        }, 60, null);
                        return ((Number) ExtensionsKt.ifElse(((VersionInfo.InvalidVersionException) thM2709exceptionOrNullimpl7).isTooNew(), 2, 1)).intValue();
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public /* bridge */ /* synthetic */ Object mo2224invoke() {
                        return Integer.valueOf(invoke());
                    }
                });
            }
        }
        if (cls3 != null) {
            try {
                objM2707constructorimpl4 = Result.m2707constructorimpl(VersionInfo.Companion.checkVersionOrThrow(RemoteService.class, cls3));
            } catch (Throwable th8) {
                Result.Companion companion9 = Result.Companion;
                objM2707constructorimpl4 = Result.m2707constructorimpl(ResultKt.createFailure(th8));
            }
            final Throwable thM2709exceptionOrNullimpl8 = Result.m2709exceptionOrNullimpl(objM2707constructorimpl4);
            if (thM2709exceptionOrNullimpl8 != null) {
                BitFlagKt.add(bitFlagWrap, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$checkPluginCompatibility$5$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    public final int invoke() {
                        final String str;
                        Throwable th9 = thM2709exceptionOrNullimpl8;
                        if (!(th9 instanceof VersionInfo.InvalidVersionException)) {
                            return 32;
                        }
                        if (((VersionInfo.InvalidVersionException) th9).isTooNew()) {
                            str = "Plugin [" + pluginInfo.getChannelServiceComponent().flattenToShortString() + "] is too new.\nCheck to see if sdk upgrade is available.\n" + ((Object) thM2709exceptionOrNullimpl8.getMessage());
                        } else {
                            str = "Plugin [" + pluginInfo.getChannelServiceComponent().flattenToShortString() + "] is too old.\nContact plugin developer to get an updated version.\n" + ((Object) thM2709exceptionOrNullimpl8.getMessage());
                        }
                        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$checkPluginCompatibility$5$2$1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* JADX INFO: renamed from: invoke */
                            public final Object mo2224invoke() {
                                return str;
                            }
                        }, 60, null);
                        return ((Number) ExtensionsKt.ifElse(((VersionInfo.InvalidVersionException) thM2709exceptionOrNullimpl8).isTooNew(), 2, 1)).intValue();
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public /* bridge */ /* synthetic */ Object mo2224invoke() {
                        return Integer.valueOf(invoke());
                    }
                });
            }
            Result.m2706boximpl(objM2707constructorimpl4);
        }
        pluginInfo.setCompatibilityInfo(new CompatibilityInfo(bitFlagWrap.getEmpty(), bitFlagWrap.getMyFlags()));
    }

    private final PluginContext createPluginContext(ApplicationInfo applicationInfo, PluginInfo pluginInfo) throws PackageManager.NameNotFoundException {
        Context contextCreateApplicationContextExt;
        PluginId pluginId = pluginInfo.getPluginPackage().getPluginId();
        String packageName = this.hostContext.getPackageName();
        packageName.getClass();
        if (Intrinsics.areEqual(pluginId, ExtensionsKt.toPluginId(packageName))) {
            Context context = this.hostContext;
            contextCreateApplicationContextExt = new ContextThemeWrapper(context, context.getTheme());
        } else {
            contextCreateApplicationContextExt = HiddenApiKt.createApplicationContextExt(this.hostContext, applicationInfo, 0);
            if (this.hostContext.isDeviceProtectedStorage()) {
                contextCreateApplicationContextExt = contextCreateApplicationContextExt.createDeviceProtectedStorageContext();
                contextCreateApplicationContextExt.getClass();
            }
        }
        Context contextWrapperPluginContext = wrapperPluginContext(pluginInfo, contextCreateApplicationContextExt);
        Resources.Theme theme = contextWrapperPluginContext.getTheme();
        theme.getClass();
        return new PluginContext(contextWrapperPluginContext, theme, this.configurationController, pluginInfo.getClassLoader());
    }

    private final boolean filterBeforeGatheringPluginInfo(ServiceInfo serviceInfo, boolean z, ComponentName componentName) {
        String strFlattenToShortString = componentName.flattenToShortString();
        strFlattenToShortString.getClass();
        Bundle bundle = serviceInfo.metaData;
        if (bundle == null || bundle.isEmpty()) {
            ignoreComponent$default(this, strFlattenToShortString, "no plugin meta data found", null, 4, null);
            return true;
        }
        Object obj = bundle.get("com.motorola.plugin.provider");
        Integer num = obj instanceof Integer ? (Integer) obj : null;
        if (num == null || num.intValue() == 0) {
            ignoreComponent$default(this, strFlattenToShortString, "no meta data with [com.motorola.plugin.provider]", null, 4, null);
            return true;
        }
        if (z) {
            return false;
        }
        if (serviceInfo.applicationInfo.uid != 0 && !serviceInfo.exported) {
            ignoreComponent$default(this, strFlattenToShortString, "not exported", null, 4, null);
            return true;
        }
        if (Intrinsics.areEqual(serviceInfo.permission, "com.motorola.myscreen.permission.ACCESS_MYSCREEN")) {
            return false;
        }
        ignoreComponent$default(this, strFlattenToShortString, "no permission [com.motorola.myscreen.permission.ACCESS_MYSCREEN], current is [" + ((Object) serviceInfo.permission) + ']', null, 4, null);
        return true;
    }

    private final void gatheringPluginInfo(ServiceInfo serviceInfo) {
        List<PluginInfo> listGatheringPluginInfoFromServiceInfo;
        SideLoadPluginProvider sideLoadPluginProvider = this;
        boolean zAreEqual = Intrinsics.areEqual(sideLoadPluginProvider.hostContext.getPackageName(), serviceInfo.packageName);
        ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
        if (sideLoadPluginProvider.filterBeforeGatheringPluginInfo(serviceInfo, zAreEqual, componentName) || (listGatheringPluginInfoFromServiceInfo = sideLoadPluginProvider.gatheringPluginInfoFromServiceInfo(serviceInfo, componentName)) == null) {
            return;
        }
        for (final PluginInfo pluginInfo : listGatheringPluginInfoFromServiceInfo) {
            if (pluginInfo.getEnabled()) {
                List rules = pluginInfo.getRules();
                if (rules == null || !rules.isEmpty()) {
                    Iterator it = rules.iterator();
                    while (it.hasNext()) {
                        if (!((IRule) it.next()).match(getHostContext())) {
                            String strFlattenToShortString = pluginInfo.getPluginComponent().flattenToShortString();
                            strFlattenToShortString.getClass();
                            sideLoadPluginProvider = this;
                            ignoreComponent$default(sideLoadPluginProvider, strFlattenToShortString, "self request", null, 4, null);
                            break;
                        }
                    }
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                checkPluginCompatibility(pluginInfo);
                final long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.VERBOSE, false, null, jCurrentTimeMillis2 > 50, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$gatheringPluginInfo$1$3$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "check plugin compatibility for action [" + pluginInfo.getAction() + "] with " + pluginInfo.getPluginComponent().flattenToShortString() + " cost " + jCurrentTimeMillis2 + " ms";
                    }
                }, 44, null);
                Map map = this.mPluginInfoByPluginPackage;
                PluginPackage pluginPackage = pluginInfo.getPluginPackage();
                Object arrayList = map.get(pluginPackage);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    map.put(pluginPackage, arrayList);
                }
                ((List) arrayList).add(pluginInfo);
                onPluginInfoAdded(pluginInfo);
                sideLoadPluginProvider = this;
            } else {
                String strFlattenToShortString2 = pluginInfo.getPluginComponent().flattenToShortString();
                strFlattenToShortString2.getClass();
                ignoreComponent$default(sideLoadPluginProvider, strFlattenToShortString2, "self disabled", null, 4, null);
                sideLoadPluginProvider = this;
            }
        }
    }

    private final List gatheringPluginInfoFromServiceInfo(final ServiceInfo serviceInfo, ComponentName componentName) {
        XmlResourceParser xmlResourceParserLoadXmlMetaData = loadXmlMetaData(serviceInfo, "com.motorola.plugin.provider");
        if (xmlResourceParserLoadXmlMetaData == null) {
            String strFlattenToShortString = componentName.flattenToShortString();
            strFlattenToShortString.getClass();
            ignoreComponent$default(this, strFlattenToShortString, "open plugin meta data failed", null, 4, null);
            return null;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        ClassLoader classLoaderCreatePluginClassLoader = createPluginClassLoader(serviceInfo, getMyPluginParentClassLoader());
        if (classLoaderCreatePluginClassLoader == null) {
            return null;
        }
        final long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.VERBOSE, false, null, jCurrentTimeMillis2 > 50, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider.gatheringPluginInfoFromServiceInfo.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "create plugin class loader cost " + jCurrentTimeMillis2 + " ms for " + ((Object) serviceInfo.packageName);
            }
        }, 44, null);
        String str = serviceInfo.applicationInfo.sourceDir;
        str.getClass();
        return PluginInfoParserKt.parsePluginInfoFromXml(componentName, xmlResourceParserLoadXmlMetaData, classLoaderCreatePluginClassLoader, str);
    }

    private final void gatheringPluginInfoList(PluginPackage pluginPackage, MarkFlag markFlag) {
        Lock lock = this.mLock;
        if (lock == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLock");
            throw null;
        }
        lock.lock();
        try {
            gatheringPluginInfoListLocked(pluginPackage, markFlag);
            Unit unit = Unit.INSTANCE;
        } finally {
            lock.unlock();
        }
    }

    private final void gatheringPluginInfoListLocked(final PluginPackage pluginPackage, MarkFlag markFlag) {
        PluginInfo pluginInfo;
        if (pluginPackage != null) {
            List list = (List) this.mPluginInfoByPluginPackage.remove(pluginPackage);
            if (list != null && (pluginInfo = (PluginInfo) CollectionsKt.firstOrNull(list)) != null) {
                CleanupResourcesKt.cleanupPluginInfo(pluginInfo);
            }
            this.mPluginContextMap.remove(pluginPackage);
            if (markFlag.deleted() || !this.pluginWhitelistPolicy.isPluginWhitelisted(pluginPackage.getPluginId())) {
                return;
            }
        } else {
            Map map = this.mPluginInfoByPluginPackage;
            ArrayList arrayList = new ArrayList();
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                PluginInfo pluginInfo2 = (PluginInfo) CollectionsKt.firstOrNull((List) ((Map.Entry) it.next()).getValue());
                if (pluginInfo2 != null) {
                    arrayList.add(pluginInfo2);
                }
            }
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                CleanupResourcesKt.cleanupPluginInfo((PluginInfo) obj);
            }
            this.mPluginInfoByPluginPackage.clear();
            Iterator it2 = this.mPluginContextMap.entrySet().iterator();
            while (it2.hasNext()) {
                PluginContext pluginContext = (PluginContext) ((WeakReference) ((Map.Entry) it2.next()).getValue()).get();
                if (pluginContext != null) {
                    pluginContext.dispose();
                }
            }
            this.mPluginContextMap.clear();
        }
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.VERBOSE, false, null, pluginPackage != null, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider.gatheringPluginInfoListLocked.5
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "gathering plugin info for package [" + pluginPackage + ']';
            }
        }, 44, null);
        List listGatheringPluginServiceInfoList = gatheringPluginServiceInfoList(pluginPackage == null ? null : pluginPackage.getPluginId());
        PluginEvent.DefaultImpls.recordEvent$default(this.pluginEvent, "gathering plugin info list for package [" + pluginPackage + "], size = " + listGatheringPluginServiceInfoList.size(), null, 2, null);
        Iterator it3 = listGatheringPluginServiceInfoList.iterator();
        while (it3.hasNext()) {
            gatheringPluginInfo((ServiceInfo) it3.next());
        }
    }

    private final ClassLoader getMyPluginParentClassLoader() {
        return (ClassLoader) this.myPluginParentClassLoader$delegate.getValue();
    }

    private final void ignoreComponent(final String str, final String str2, Level level) {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider.ignoreComponent.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "Ignore [" + str + "] due to " + str2;
            }
        }, 60, null);
        this.mIgnoredPluginMap.put(str, str2);
    }

    static /* synthetic */ void ignoreComponent$default(SideLoadPluginProvider sideLoadPluginProvider, String str, String str2, Level level, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: ignoreComponent");
        }
        if ((i & 4) != 0) {
            level = Level.ERROR;
        }
        sideLoadPluginProvider.ignoreComponent(str, str2, level);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onConfigChanged$lambda-41$lambda-40, reason: not valid java name */
    public static final void m2223onConfigChanged$lambda41$lambda40(SideLoadPluginProvider sideLoadPluginProvider, ConfigurationListenerChainedDispatcher.ChainedDispatcher chainedDispatcher, Configuration configuration, BitFlag bitFlag) {
        sideLoadPluginProvider.getClass();
        chainedDispatcher.getClass();
        configuration.getClass();
        bitFlag.getClass();
        if (sideLoadPluginProvider.mDisposed) {
            return;
        }
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.DEBUG, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$onConfigChanged$1$2$1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "config change event dispatch success";
            }
        }, 60, null);
        chainedDispatcher.processNextConfigChanged(configuration, bitFlag);
        if (sideLoadPluginProvider.mDisposed) {
            return;
        }
        sideLoadPluginProvider.onConfigChanged(configuration, bitFlag);
    }

    protected abstract ClassLoader createPluginClassLoader(ServiceInfo serviceInfo, ClassLoader classLoader);

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        Lock lock = this.mLock;
        if (lock == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLock");
            throw null;
        }
        lock.lock();
        try {
            Map map = this.mPluginInfoByPluginPackage;
            ArrayList arrayList = new ArrayList();
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                PluginInfo pluginInfo = (PluginInfo) CollectionsKt.firstOrNull((List) ((Map.Entry) it.next()).getValue());
                if (pluginInfo != null) {
                    arrayList.add(pluginInfo);
                }
            }
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                CleanupResourcesKt.cleanupPluginInfo((PluginInfo) obj);
            }
            this.mPluginInfoByPluginPackage.clear();
            this.mIgnoredPluginMap.clear();
            Iterator it2 = this.mPluginContextMap.entrySet().iterator();
            while (it2.hasNext()) {
                PluginContext pluginContext = (PluginContext) ((WeakReference) ((Map.Entry) it2.next()).getValue()).get();
                if (pluginContext != null) {
                    pluginContext.dispose();
                }
            }
            this.mPluginContextMap.clear();
            this.mDisposed = true;
            Unit unit = Unit.INSTANCE;
            lock.unlock();
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    protected abstract List gatheringPluginServiceInfoList(PluginId pluginId);

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public List getAvailableRemotePluginList() {
        return CollectionsKt.emptyList();
    }

    protected final Context getHostContext() {
        return this.hostContext;
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public List getInstalledPluginInfoList() {
        Lock lock = this.mLock;
        if (lock == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLock");
            throw null;
        }
        lock.lock();
        try {
            Map map = this.mPluginInfoByPluginPackage;
            ArrayList arrayList = new ArrayList();
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, (List) ((Map.Entry) it.next()).getValue());
            }
            return arrayList;
        } finally {
            lock.unlock();
        }
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public PluginContext getPluginContext(PluginInfo pluginInfo) {
        pluginInfo.getClass();
        Lock lock = this.mLock;
        PluginContext pluginContextCreatePluginContext = null;
        if (lock == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLock");
            throw null;
        }
        lock.lock();
        try {
            WeakReference weakReference = (WeakReference) this.mPluginContextMap.get(pluginInfo.getPluginPackage());
            PluginContext pluginContext = weakReference == null ? null : (PluginContext) weakReference.get();
            if (pluginContext != null && !pluginContext.isDisposed()) {
                return pluginContext;
            }
            ApplicationInfo pluginPackageAppInfo = getPluginPackageAppInfo(pluginInfo.getPluginPackage());
            if (pluginPackageAppInfo != null) {
                pluginContextCreatePluginContext = createPluginContext(pluginPackageAppInfo, pluginInfo);
                this.mPluginContextMap.put(pluginInfo.getPluginPackage(), new WeakReference(pluginContextCreatePluginContext));
            }
            return pluginContextCreatePluginContext;
        } finally {
            lock.unlock();
        }
    }

    protected abstract ApplicationInfo getPluginPackageAppInfo(PluginPackage pluginPackage);

    @Override // com.motorola.plugin.core.provider.SharedLockPluginInfoProvider
    public void installSharedLock(ISharedLock iSharedLock) {
        iSharedLock.getClass();
        this.mLock = iSharedLock.getSharedLock();
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public boolean launchPlugin(Intent intent, PluginPackage pluginPackage) {
        intent.getClass();
        return false;
    }

    protected abstract XmlResourceParser loadXmlMetaData(ServiceInfo serviceInfo, String str);

    protected void onConfigChanged(Configuration configuration, BitFlag bitFlag) {
        configuration.getClass();
        bitFlag.getClass();
    }

    @Override // com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher.ConfigurationChainedListener
    public final void onConfigChanged(final Configuration configuration, final BitFlag bitFlag, final ConfigurationListenerChainedDispatcher.ChainedDispatcher chainedDispatcher) {
        Object objM2707constructorimpl;
        configuration.getClass();
        bitFlag.getClass();
        chainedDispatcher.getClass();
        Lock lock = this.mLock;
        if (lock == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLock");
            throw null;
        }
        lock.lock();
        try {
            Collection collectionValues = this.mPluginContextMap.values();
            ArrayList arrayList = new ArrayList();
            Iterator it = collectionValues.iterator();
            while (it.hasNext()) {
                PluginContext pluginContext = (PluginContext) ((WeakReference) it.next()).get();
                String packageName = pluginContext == null ? null : pluginContext.getPackageName();
                if (packageName != null) {
                    arrayList.add(packageName);
                }
            }
            Object[] array = arrayList.toArray(new String[0]);
            if (array == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            String[] strArr = (String[]) array;
            lock.unlock();
            if (strArr.length == 0) {
                chainedDispatcher.processNextConfigChanged(configuration, bitFlag);
                return;
            }
            try {
                Result.Companion companion = Result.Companion;
                Message messageObtain = Message.obtain();
                messageObtain.what = 133;
                messageObtain.arg1 = 3;
                messageObtain.obj = strArr;
                ExtensionsKt.sendToActivityThread(messageObtain);
                Message messageObtain2 = Message.obtain((Handler) null, new Runnable() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SideLoadPluginProvider.m2223onConfigChanged$lambda41$lambda40(this.f$0, chainedDispatcher, configuration, bitFlag);
                    }
                });
                messageObtain2.getClass();
                objM2707constructorimpl = Result.m2707constructorimpl(Boolean.valueOf(ExtensionsKt.sendToActivityThread(messageObtain2)));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
            }
            Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
            if (thM2709exceptionOrNullimpl != null) {
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.ERROR, false, thM2709exceptionOrNullimpl, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$onConfigChanged$2$1
                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "config change event dispatch failed";
                    }
                }, 52, null);
            }
        } catch (Throwable th2) {
            lock.unlock();
            throw th2;
        }
    }

    protected void onInitialized() {
        this.pluginWhitelistPolicy.onInitialized();
    }

    @Override // com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher.ConfigurationChainedListener
    public void onLowMemory() {
    }

    protected void onPluginInfoAdded(PluginInfo pluginInfo) {
        pluginInfo.getClass();
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public void onPluginPackageChanged(PluginPackage pluginPackage, MarkFlag markFlag) {
        pluginPackage.getClass();
        markFlag.getClass();
        gatheringPluginInfoList(pluginPackage, markFlag);
    }

    @Override // com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher.ConfigurationChainedListener
    public void onTrimMemory(int i) {
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public List queryPluginInfo(String str, PluginPackage pluginPackage) {
        str.getClass();
        Lock lock = this.mLock;
        if (lock == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLock");
            throw null;
        }
        lock.lock();
        int i = 0;
        boolean z = pluginPackage != null;
        try {
            Map map = this.mPluginInfoByPluginPackage;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry entry : map.entrySet()) {
                if (Intrinsics.areEqual((PluginPackage) entry.getKey(), pluginPackage)) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
            }
            Map map2 = (Map) ExtensionsKt.ifElse(z, linkedHashMap, this.mPluginInfoByPluginPackage);
            ArrayList arrayList = new ArrayList();
            Iterator it = map2.entrySet().iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, (List) ((Map.Entry) it.next()).getValue());
            }
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                PluginInfo pluginInfo = (PluginInfo) obj;
                if (Intrinsics.areEqual(str, "com.motorola.plugin.action.PLUGIN_DISCOVERY") || Intrinsics.areEqual(pluginInfo.getAction(), str)) {
                    arrayList2.add(obj);
                }
            }
            lock.unlock();
            return arrayList2;
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        PluginInfoCacheSnapshot pluginInfoCacheSnapshot = new PluginInfoCacheSnapshot(iSnapshot);
        pluginInfoCacheSnapshot.setMyPluginInfoInstance(hashCode());
        pluginInfoCacheSnapshot.setMyPluginInfoInstanceTag(this.tag);
        pluginInfoCacheSnapshot.setMyInstalledPluginInfoSize(getInstalledPluginInfoList().size());
        Lock lock = this.mLock;
        if (lock == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLock");
            throw null;
        }
        lock.lock();
        try {
            Map map = MapsKt.toMap(this.mPluginInfoByPluginPackage);
            lock.unlock();
            pluginInfoCacheSnapshot.setMyPluginInfoMap(map);
            lock = this.mLock;
            if (lock == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mLock");
                throw null;
            }
            lock.lock();
            try {
                Map map2 = MapsKt.toMap(this.mIgnoredPluginMap);
                lock.unlock();
                pluginInfoCacheSnapshot.setMyIgnoredPluginMap(map2);
                return pluginInfoCacheSnapshot;
            } finally {
            }
        } finally {
        }
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public void startGatheringPluginInfoList() {
        onInitialized();
        long jCurrentTimeMillis = System.currentTimeMillis();
        gatheringPluginInfoList(null, MarkFlag.Companion.getEMPTY());
        final long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.provider.SideLoadPluginProvider$startGatheringPluginInfoList$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "gathering plugin info list cost " + jCurrentTimeMillis2 + " ms";
            }
        }, 60, null);
        PluginEvent.DefaultImpls.recordEvent$default(this.pluginEvent, "gathering plugin info list cost " + jCurrentTimeMillis2 + " ms", null, 2, null);
    }

    protected Context wrapperPluginContext(PluginInfo pluginInfo, Context context) {
        pluginInfo.getClass();
        context.getClass();
        return context;
    }
}
