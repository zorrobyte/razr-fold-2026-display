package com.motorola.plugin.core.components.impls;

import android.content.Intent;
import android.content.res.Configuration;
import com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher;
import com.motorola.plugin.core.components.PluginInfoManager;
import com.motorola.plugin.core.context.PluginContext;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.discovery.PluginInfo;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.core.misc.MarkFlag;
import com.motorola.plugin.core.provider.ISharedLock;
import com.motorola.plugin.core.provider.InstalledPluginProvider;
import com.motorola.plugin.core.provider.RemotePluginProviderFactory;
import com.motorola.plugin.core.provider.SharedLockPluginInfoProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: PluginInfoManagerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginInfoManagerImpl implements PluginInfoManager, ISharedLock {
    private final ReentrantLock mLock;
    private final Sequence providers;

    /* JADX INFO: compiled from: PluginInfoManagerImpl.kt */
    final class PluginInfoCacheSnapshot extends AbstractSnapshot {
        private int myPluginInfoInstance;
        public List myProviderSnapshots;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PluginInfoCacheSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final int getMyPluginInfoInstance() {
            return this.myPluginInfoInstance;
        }

        public final List getMyProviderSnapshots() {
            List list = this.myProviderSnapshots;
            if (list != null) {
                return list;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myProviderSnapshots");
            throw null;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("PluginInfoManager", getMyPluginInfoInstance()).newLine();
            iPrinter.increaseIndent();
            Iterator it = getMyProviderSnapshots().iterator();
            while (it.hasNext()) {
                ((ISnapshot) it.next()).onSnapshot(iPrinter);
            }
            iPrinter.decreaseIndent();
            iPrinter.newLine();
        }

        public final void setMyPluginInfoInstance(int i) {
            this.myPluginInfoInstance = i;
        }

        public final void setMyProviderSnapshots(List list) {
            list.getClass();
            this.myProviderSnapshots = list;
        }
    }

    public PluginInfoManagerImpl(InstalledPluginProvider installedPluginProvider, RemotePluginProviderFactory remotePluginProviderFactory) {
        installedPluginProvider.getClass();
        remotePluginProviderFactory.getClass();
        this.mLock = new ReentrantLock(true);
        ArrayList arrayList = new ArrayList();
        SharedLockPluginInfoProvider sharedLockPluginInfoProvider = remotePluginProviderFactory.get();
        if (sharedLockPluginInfoProvider != null) {
            arrayList.add(sharedLockPluginInfoProvider);
        }
        arrayList.add(installedPluginProvider);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((SharedLockPluginInfoProvider) obj).installSharedLock(this);
        }
        this.providers = CollectionsKt.asSequence(arrayList);
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        Iterator it = this.providers.iterator();
        while (it.hasNext()) {
            ((SharedLockPluginInfoProvider) it.next()).dispose();
        }
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public List getAvailableRemotePluginList() {
        return SequencesKt.toList(SequencesKt.flatMapIterable(this.providers, new Function1() { // from class: com.motorola.plugin.core.components.impls.PluginInfoManagerImpl$availableRemotePluginList$1
            @Override // kotlin.jvm.functions.Function1
            public final List invoke(SharedLockPluginInfoProvider sharedLockPluginInfoProvider) {
                sharedLockPluginInfoProvider.getClass();
                return sharedLockPluginInfoProvider.getAvailableRemotePluginList();
            }
        }));
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public List getInstalledPluginInfoList() {
        return SequencesKt.toList(SequencesKt.flatMapIterable(this.providers, new Function1() { // from class: com.motorola.plugin.core.components.impls.PluginInfoManagerImpl$installedPluginInfoList$1
            @Override // kotlin.jvm.functions.Function1
            public final List invoke(SharedLockPluginInfoProvider sharedLockPluginInfoProvider) {
                sharedLockPluginInfoProvider.getClass();
                return sharedLockPluginInfoProvider.getInstalledPluginInfoList();
            }
        }));
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public PluginContext getPluginContext(final PluginInfo pluginInfo) {
        pluginInfo.getClass();
        return (PluginContext) SequencesKt.firstOrNull(SequencesKt.mapNotNull(this.providers, new Function1() { // from class: com.motorola.plugin.core.components.impls.PluginInfoManagerImpl.getPluginContext.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PluginContext invoke(SharedLockPluginInfoProvider sharedLockPluginInfoProvider) {
                sharedLockPluginInfoProvider.getClass();
                return sharedLockPluginInfoProvider.getPluginContext(pluginInfo);
            }
        }));
    }

    @Override // com.motorola.plugin.core.provider.ISharedLock
    public Lock getSharedLock() {
        return this.mLock;
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public boolean launchPlugin(final Intent intent, final PluginPackage pluginPackage) {
        intent.getClass();
        Boolean bool = (Boolean) SequencesKt.firstOrNull(SequencesKt.filter(SequencesKt.map(this.providers, new Function1() { // from class: com.motorola.plugin.core.components.impls.PluginInfoManagerImpl.launchPlugin.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.valueOf(invoke((SharedLockPluginInfoProvider) obj));
            }

            public final boolean invoke(SharedLockPluginInfoProvider sharedLockPluginInfoProvider) {
                sharedLockPluginInfoProvider.getClass();
                return sharedLockPluginInfoProvider.launchPlugin(intent, pluginPackage);
            }
        }), new Function1() { // from class: com.motorola.plugin.core.components.impls.PluginInfoManagerImpl.launchPlugin.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.valueOf(invoke(((Boolean) obj).booleanValue()));
            }

            public final boolean invoke(boolean z) {
                return z;
            }
        }));
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    @Override // com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher.ConfigurationChainedListener
    public void onConfigChanged(Configuration configuration, BitFlag bitFlag, ConfigurationListenerChainedDispatcher.ChainedDispatcher chainedDispatcher) {
        configuration.getClass();
        bitFlag.getClass();
        chainedDispatcher.getClass();
        Iterator it = this.providers.iterator();
        while (it.hasNext()) {
            ((SharedLockPluginInfoProvider) it.next()).onConfigChanged(configuration, bitFlag, chainedDispatcher);
        }
    }

    @Override // com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher.ConfigurationChainedListener
    public void onLowMemory() {
        Iterator it = this.providers.iterator();
        while (it.hasNext()) {
            ((SharedLockPluginInfoProvider) it.next()).onLowMemory();
        }
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public void onPluginPackageChanged(PluginPackage pluginPackage, MarkFlag markFlag) {
        pluginPackage.getClass();
        markFlag.getClass();
        Iterator it = this.providers.iterator();
        while (it.hasNext()) {
            ((SharedLockPluginInfoProvider) it.next()).onPluginPackageChanged(pluginPackage, markFlag);
        }
    }

    @Override // com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher.ConfigurationChainedListener
    public void onTrimMemory(int i) {
        Iterator it = this.providers.iterator();
        while (it.hasNext()) {
            ((SharedLockPluginInfoProvider) it.next()).onTrimMemory(i);
        }
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public List queryPluginInfo(final String str, final PluginPackage pluginPackage) {
        str.getClass();
        return SequencesKt.toList(SequencesKt.flatMapIterable(this.providers, new Function1() { // from class: com.motorola.plugin.core.components.impls.PluginInfoManagerImpl.queryPluginInfo.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List invoke(SharedLockPluginInfoProvider sharedLockPluginInfoProvider) {
                sharedLockPluginInfoProvider.getClass();
                return sharedLockPluginInfoProvider.queryPluginInfo(str, pluginPackage);
            }
        }));
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        PluginInfoCacheSnapshot pluginInfoCacheSnapshot = new PluginInfoCacheSnapshot(iSnapshot);
        pluginInfoCacheSnapshot.setMyPluginInfoInstance(hashCode());
        pluginInfoCacheSnapshot.setMyProviderSnapshots(SequencesKt.toList(SequencesKt.map(this.providers, new Function1() { // from class: com.motorola.plugin.core.components.impls.PluginInfoManagerImpl$snapshot$1$1
            @Override // kotlin.jvm.functions.Function1
            public final ISnapshot invoke(SharedLockPluginInfoProvider sharedLockPluginInfoProvider) {
                sharedLockPluginInfoProvider.getClass();
                return ISnapshotAware.DefaultImpls.snapshot$default(sharedLockPluginInfoProvider, null, 1, null);
            }
        })));
        return pluginInfoCacheSnapshot;
    }

    @Override // com.motorola.plugin.core.provider.PluginInfoProvider
    public void startGatheringPluginInfoList() {
        Iterator it = this.providers.iterator();
        while (it.hasNext()) {
            ((SharedLockPluginInfoProvider) it.next()).startGatheringPluginInfoList();
        }
    }
}
