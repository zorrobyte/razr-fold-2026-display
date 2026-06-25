package com.motorola.plugin.core.discovery;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.context.PluginContext;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.context.VersionInfo;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LocalAppPluginDiscovery.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class LocalAppPluginDiscovery implements PluginDiscovery {
    public static final LocalAppPluginDiscovery INSTANCE = new LocalAppPluginDiscovery();
    private static final Map myLocalPluginMap = new LinkedHashMap();
    private static final ReentrantLock mLock = new ReentrantLock(true);

    /* JADX INFO: compiled from: LocalAppPluginDiscovery.kt */
    final class LocalAppPluginDiscoverySnapshot extends AbstractSnapshot {
        private int myInstance;
        public Map myLocalPlugins;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LocalAppPluginDiscoverySnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final int getMyInstance() {
            return this.myInstance;
        }

        public final Map getMyLocalPlugins() {
            Map map = this.myLocalPlugins;
            if (map != null) {
                return map;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myLocalPlugins");
            throw null;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("Plugin discover(local)", getMyInstance()).newLine();
            iPrinter.increaseIndent();
            Map myLocalPlugins = getMyLocalPlugins();
            iPrinter.increaseIndent();
            iPrinter.printPair("Local registered plugins", Integer.valueOf(myLocalPlugins.size())).newLine();
            iPrinter.increaseIndent();
            int i = 0;
            for (Object obj : myLocalPlugins.entrySet()) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Map.Entry entry = (Map.Entry) obj;
                IPrinter iPrinter2 = iPrinter;
                IPrinter.DefaultImpls.printIndex$default(iPrinter2, i, ((String) entry.getKey()) + " -> " + entry.getValue(), null, 4, null).newLine();
                iPrinter = iPrinter2;
                i = i2;
            }
            IPrinter iPrinter3 = iPrinter;
            iPrinter3.decreaseIndent();
            iPrinter3.decreaseIndent();
            iPrinter3.decreaseIndent();
            iPrinter3.newLine();
        }

        public final void setMyInstance(int i) {
            this.myInstance = i;
        }

        public final void setMyLocalPlugins(Map map) {
            map.getClass();
            this.myLocalPlugins = map;
        }
    }

    private LocalAppPluginDiscovery() {
    }

    private final DiscoverInfo createDiscoverInfo(Pair pair, String str, Context context) {
        VersionInfo versionInfoAddClass = new VersionInfo().addClass((Class) pair.getSecond());
        PluginPackage.Companion companion = PluginPackage.Companion;
        String packageName = context.getPackageName();
        packageName.getClass();
        PluginPackage pluginPackageOf$default = PluginPackage.Companion.of$default(companion, ExtensionsKt.toPluginId(packageName), null, 2, null);
        String name = ((Class) pair.getFirst()).getName();
        String name2 = ((Class) pair.getSecond()).getName();
        ClassLoader classLoader = LocalAppPluginDiscovery.class.getClassLoader();
        classLoader.getClass();
        Resources.Theme theme = context.getTheme();
        theme.getClass();
        ClassLoader classLoader2 = LocalAppPluginDiscovery.class.getClassLoader();
        classLoader2.getClass();
        PluginContext pluginContext = new PluginContext(context, theme, null, classLoader2);
        ComponentName componentNameCreateRelative = ComponentName.createRelative(context, ".");
        componentNameCreateRelative.getClass();
        ComponentName componentNameCreateRelative2 = ComponentName.createRelative(context, ((Class) pair.getSecond()).getName());
        componentNameCreateRelative2.getClass();
        return new DiscoverInfo(str, pluginPackageOf$default, name, name2, classLoader, pluginContext, componentNameCreateRelative, componentNameCreateRelative2, versionInfoAddClass, PluginType.LOCAL);
    }

    @Override // com.motorola.plugin.core.discovery.PluginDiscovery
    public List discovery(Context context, final String str, PluginPackage pluginPackage, String str2) {
        List listArrayListOf;
        context.getClass();
        str.getClass();
        ReentrantLock reentrantLock = mLock;
        reentrantLock.lock();
        try {
            Pair pair = (Pair) myLocalPluginMap.get(str);
            if (pair == null) {
                listArrayListOf = null;
            } else {
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY_LOCAL, Level.INFO, false, null, false, str2, new Function0() { // from class: com.motorola.plugin.core.discovery.LocalAppPluginDiscovery$discovery$1$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "Found [1] plugin for action [" + str + ']';
                    }
                }, 28, null);
                listArrayListOf = CollectionsKt.arrayListOf(INSTANCE.createDiscoverInfo(pair, str, context));
            }
            if (listArrayListOf == null) {
                listArrayListOf = CollectionsKt.emptyList();
            }
            reentrantLock.unlock();
            return listArrayListOf;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        ReentrantLock reentrantLock = mLock;
        reentrantLock.lock();
        try {
            myLocalPluginMap.clear();
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final void registerLocalPlugin(Class cls, Class cls2) {
        cls.getClass();
        cls2.getClass();
        ReentrantLock reentrantLock = mLock;
        reentrantLock.lock();
        try {
            if (cls.isAssignableFrom(cls2)) {
                myLocalPluginMap.put(ExtensionsKt.getPluginActionOrThrow(cls), TuplesKt.to(cls, cls2));
                Unit unit = Unit.INSTANCE;
            } else {
                throw new IllegalStateException(("Cannot convert [" + cls2 + "] to [" + cls + ']').toString());
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        LocalAppPluginDiscoverySnapshot localAppPluginDiscoverySnapshot = new LocalAppPluginDiscoverySnapshot(iSnapshot);
        localAppPluginDiscoverySnapshot.setMyInstance(hashCode());
        ReentrantLock reentrantLock = mLock;
        reentrantLock.lock();
        try {
            Map map = myLocalPluginMap;
            LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
            for (Object obj : map.entrySet()) {
                Object key = ((Map.Entry) obj).getKey();
                Map.Entry entry = (Map.Entry) obj;
                linkedHashMap.put(key, TuplesKt.to(((Class) ((Pair) entry.getValue()).getFirst()).getName(), ((Class) ((Pair) entry.getValue()).getSecond()).getName()));
            }
            reentrantLock.unlock();
            localAppPluginDiscoverySnapshot.setMyLocalPlugins(linkedHashMap);
            return localAppPluginDiscoverySnapshot;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }
}
