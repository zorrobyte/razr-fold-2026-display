package com.motorola.plugin.core.discovery;

import android.content.Context;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.components.AppContext;
import com.motorola.plugin.core.components.PluginInfoManager;
import com.motorola.plugin.core.context.PluginContext;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThirdPartyAppPluginDiscovery.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ThirdPartyAppPluginDiscovery implements PluginDiscovery {
    private final Context context;
    private final PluginInfoManager pluginInfoManager;

    /* JADX INFO: compiled from: ThirdPartyAppPluginDiscovery.kt */
    final class ThirdPartyAppPluginDiscoverySnapshot extends AbstractSnapshot {
        public Map myDisabledPlugins;
        public Map myEnabledPlugins;
        private int myInstance;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ThirdPartyAppPluginDiscoverySnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final Map getMyDisabledPlugins() {
            Map map = this.myDisabledPlugins;
            if (map != null) {
                return map;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myDisabledPlugins");
            throw null;
        }

        public final Map getMyEnabledPlugins() {
            Map map = this.myEnabledPlugins;
            if (map != null) {
                return map;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myEnabledPlugins");
            throw null;
        }

        public final int getMyInstance() {
            return this.myInstance;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("Plugin discover(3rd)", getMyInstance()).newLine();
            iPrinter.increaseIndent();
            Map myEnabledPlugins = getMyEnabledPlugins();
            iPrinter.increaseIndent();
            iPrinter.printPair("numCompatiblePlugins", Integer.valueOf(myEnabledPlugins.size())).newLine();
            iPrinter.increaseIndent();
            int i = 0;
            for (Object obj : myEnabledPlugins.entrySet()) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Map.Entry entry = (Map.Entry) obj;
                IPrinter iPrinter2 = iPrinter;
                IPrinter.DefaultImpls.printIndex$default(iPrinter2, i, ((PluginPackage) entry.getKey()).getPluginId(), null, 4, null).newLine();
                iPrinter2.increaseIndent();
                int i3 = 0;
                for (Object obj2 : (Iterable) entry.getValue()) {
                    int i4 = i3 + 1;
                    if (i3 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IPrinter.DefaultImpls.printIndex$default(iPrinter2, i3, (String) obj2, null, 4, null).newLine();
                    i3 = i4;
                }
                iPrinter2.decreaseIndent();
                iPrinter = iPrinter2;
                i = i2;
            }
            IPrinter iPrinter3 = iPrinter;
            iPrinter3.decreaseIndent();
            iPrinter3.decreaseIndent();
            Map myDisabledPlugins = getMyDisabledPlugins();
            iPrinter3.increaseIndent();
            iPrinter3.printPair("numIncompatiblePlugins", Integer.valueOf(myDisabledPlugins.size())).newLine();
            iPrinter3.increaseIndent();
            int i5 = 0;
            for (Object obj3 : myDisabledPlugins.entrySet()) {
                int i6 = i5 + 1;
                if (i5 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Map.Entry entry2 = (Map.Entry) obj3;
                IPrinter.DefaultImpls.printIndex$default(iPrinter3, i5, ((PluginPackage) entry2.getKey()).getPluginId(), null, 4, null).newLine();
                iPrinter3.increaseIndent();
                int i7 = 0;
                for (Object obj4 : (Iterable) entry2.getValue()) {
                    int i8 = i7 + 1;
                    if (i7 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IPrinter.DefaultImpls.printIndex$default(iPrinter3, i7, (String) obj4, null, 4, null).newLine();
                    i7 = i8;
                }
                iPrinter3.decreaseIndent();
                i5 = i6;
            }
            iPrinter3.decreaseIndent();
            iPrinter3.decreaseIndent();
            iPrinter3.decreaseIndent();
            iPrinter3.newLine();
        }

        public final void setMyDisabledPlugins(Map map) {
            map.getClass();
            this.myDisabledPlugins = map;
        }

        public final void setMyEnabledPlugins(Map map) {
            map.getClass();
            this.myEnabledPlugins = map;
        }

        public final void setMyInstance(int i) {
            this.myInstance = i;
        }
    }

    public ThirdPartyAppPluginDiscovery(@AppContext Context context, PluginInfoManager pluginInfoManager) {
        context.getClass();
        pluginInfoManager.getClass();
        this.context = context;
        this.pluginInfoManager = pluginInfoManager;
    }

    private final boolean filterIncompatiblePluginComponent(PluginInfo pluginInfo, boolean z) {
        String strFlattenToShortString = pluginInfo.getPluginComponent().flattenToShortString();
        strFlattenToShortString.getClass();
        BitFlag bitFlagAsFlag = ExtensionsKt.asFlag(pluginInfo.getCompatibilityInfo().getReasonFlags());
        if (bitFlagAsFlag.has(16)) {
            ignoreComponent$default(this, pluginInfo.getAction(), strFlattenToShortString, "not enabled", null, 8, null);
            return true;
        }
        if (bitFlagAsFlag.has(4)) {
            ignoreComponent$default(this, pluginInfo.getAction(), strFlattenToShortString, "invalid plugin class", null, 8, null);
            return true;
        }
        if (bitFlagAsFlag.has(3)) {
            ignoreComponent$default(this, pluginInfo.getAction(), strFlattenToShortString, "incompatible plugin version", null, 8, null);
            return true;
        }
        if (bitFlagAsFlag.has(96)) {
            ignoreComponent$default(this, pluginInfo.getAction(), strFlattenToShortString, "unknown error", null, 8, null);
            return true;
        }
        if (z || !bitFlagAsFlag.has(8)) {
            return false;
        }
        ignoreComponent(pluginInfo.getAction(), strFlattenToShortString, "on production build", Level.WARN);
        return true;
    }

    private final void ignoreComponent(final String str, final String str2, final String str3, Level level) {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.discovery.ThirdPartyAppPluginDiscovery.ignoreComponent.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "Ignore [" + str2 + "] for action [" + str + "] due to " + str3;
            }
        }, 60, null);
    }

    static /* synthetic */ void ignoreComponent$default(ThirdPartyAppPluginDiscovery thirdPartyAppPluginDiscovery, String str, String str2, String str3, Level level, int i, Object obj) {
        if ((i & 8) != 0) {
            level = Level.ERROR;
        }
        thirdPartyAppPluginDiscovery.ignoreComponent(str, str2, str3, level);
    }

    private final DiscoverInfo toDiscoveryInfo(PluginInfo pluginInfo) {
        PluginContext pluginContext;
        if (filterIncompatiblePluginComponent(pluginInfo, Intrinsics.areEqual(this.context.getPackageName(), pluginInfo.getPluginPackage().getPluginId().getId())) || (pluginContext = this.pluginInfoManager.getPluginContext(pluginInfo)) == null) {
            return null;
        }
        return new DiscoverInfo(pluginInfo.getAction(), pluginInfo.getPluginPackage(), pluginInfo.getPrototypePluginClass(), pluginInfo.getImplementorPluginClass(), pluginContext.getClassLoader(), pluginContext, pluginInfo.getChannelServiceComponent(), pluginInfo.getPluginComponent(), pluginInfo.getVersionInfo(), (PluginType) ExtensionsKt.ifElse(pluginInfo.getFlags().has(2), PluginType.REMOTE, PluginType.INSTALL));
    }

    @Override // com.motorola.plugin.core.discovery.PluginDiscovery
    public List discovery(Context context, final String str, PluginPackage pluginPackage, String str2) {
        context.getClass();
        str.getClass();
        List listQueryPluginInfo = this.pluginInfoManager.queryPluginInfo(str, pluginPackage);
        final ArrayList arrayList = new ArrayList();
        Iterator it = listQueryPluginInfo.iterator();
        while (it.hasNext()) {
            DiscoverInfo discoveryInfo = toDiscoveryInfo((PluginInfo) it.next());
            if (discoveryInfo != null) {
                arrayList.add(discoveryInfo);
            }
        }
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.INFO, false, null, false, str2, new Function0() { // from class: com.motorola.plugin.core.discovery.ThirdPartyAppPluginDiscovery$discovery$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                StringBuilder sb = new StringBuilder();
                sb.append("Found [");
                sb.append(arrayList.size());
                sb.append("] plugin");
                sb.append(arrayList.size() == 1 ? "" : "s");
                sb.append(" for action [");
                sb.append(str);
                sb.append(']');
                return sb.toString();
            }
        }, 28, null);
        return arrayList;
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        ThirdPartyAppPluginDiscoverySnapshot thirdPartyAppPluginDiscoverySnapshot = new ThirdPartyAppPluginDiscoverySnapshot(iSnapshot);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (PluginInfo pluginInfo : this.pluginInfoManager.getInstalledPluginInfoList()) {
            if (pluginInfo.getCompatibilityInfo().getOptimisticCompat()) {
                PluginPackage pluginPackage = pluginInfo.getPluginPackage();
                Object arrayList = linkedHashMap.get(pluginPackage);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    linkedHashMap.put(pluginPackage, arrayList);
                }
                ((List) arrayList).add(pluginInfo.getAction());
            } else {
                PluginPackage pluginPackage2 = pluginInfo.getPluginPackage();
                Object arrayList2 = linkedHashMap2.get(pluginPackage2);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                    linkedHashMap2.put(pluginPackage2, arrayList2);
                }
                ((List) arrayList2).add(pluginInfo.getAction());
            }
        }
        thirdPartyAppPluginDiscoverySnapshot.setMyInstance(hashCode());
        thirdPartyAppPluginDiscoverySnapshot.setMyEnabledPlugins(linkedHashMap);
        thirdPartyAppPluginDiscoverySnapshot.setMyDisabledPlugins(linkedHashMap2);
        return thirdPartyAppPluginDiscoverySnapshot;
    }
}
