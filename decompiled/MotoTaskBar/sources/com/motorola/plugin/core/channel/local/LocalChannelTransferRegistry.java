package com.motorola.plugin.core.channel.local;

import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.channel.local.ILocalChannelTransferRegistry;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.sdk.channel.ClientId;
import com.motorola.plugin.sdk.channel.IRemoteChannelTransfer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LocalChannelTransferRegistry.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class LocalChannelTransferRegistry implements ILocalChannelTransferRegistry {
    public static final LocalChannelTransferRegistry INSTANCE = new LocalChannelTransferRegistry();
    private static final Map myRegistryMap = new HashMap();

    /* JADX INFO: compiled from: LocalChannelTransferRegistry.kt */
    final class LocalRegistrySnapshot extends AbstractSnapshot {
        private final Map registryMap;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LocalRegistrySnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
            this.registryMap = new HashMap();
        }

        public final Map getRegistryMap() {
            return this.registryMap;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printSingle("Local transfer registry:").newLine();
            iPrinter.increaseIndent();
            for (Map.Entry entry : getRegistryMap().entrySet()) {
                iPrinter.printPair((String) entry.getKey(), (String) entry.getValue()).newLine();
            }
            iPrinter.decreaseIndent();
        }
    }

    private LocalChannelTransferRegistry() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: unregister$lambda-0, reason: not valid java name */
    public static final boolean m2207unregister$lambda0(ILocalChannelTransfer iLocalChannelTransfer, final Map.Entry entry) {
        iLocalChannelTransfer.getClass();
        entry.getClass();
        boolean zAreEqual = Intrinsics.areEqual(iLocalChannelTransfer, entry.getValue());
        PluginConfigKt.trace$default(PluginConfigKt.TAG_LOCAL_CHANNEL, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.local.LocalChannelTransferRegistry$unregister$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "Unregister local transfer for " + ((String) entry.getKey()) + " -> " + entry.getValue();
            }
        }, 62, null);
        return zAreEqual;
    }

    public final IRemoteChannelTransfer asRemoteTransfer$core_stubRelease(ILocalChannelTransfer iLocalChannelTransfer) {
        iLocalChannelTransfer.getClass();
        LocalTransferToRemoteTransferAdapter localTransferToRemoteTransferAdapter = iLocalChannelTransfer instanceof LocalTransferToRemoteTransferAdapter ? (LocalTransferToRemoteTransferAdapter) iLocalChannelTransfer : null;
        if (localTransferToRemoteTransferAdapter == null) {
            return null;
        }
        return localTransferToRemoteTransferAdapter.asRemoteTransfer();
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_LOCAL_CHANNEL, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.local.LocalChannelTransferRegistry.dispose.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "Dispose";
            }
        }, 62, null);
        myRegistryMap.clear();
    }

    @Override // com.motorola.plugin.core.channel.local.ILocalChannelTransferRegistry
    public ILocalChannelTransfer getTransfer(String str) {
        str.getClass();
        return (ILocalChannelTransfer) myRegistryMap.get(str);
    }

    @Override // com.motorola.plugin.core.channel.local.ILocalChannelTransferRegistry
    public String[] listTransfers() {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_LOCAL_CHANNEL, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.local.LocalChannelTransferRegistry.listTransfers.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "List transfers";
            }
        }, 62, null);
        Object[] array = myRegistryMap.keySet().toArray(new String[0]);
        if (array != null) {
            return (String[]) array;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    @Override // com.motorola.plugin.core.channel.local.ILocalChannelTransferRegistry
    public void notifyDataChanged(Uri... uriArr) {
        ILocalChannelTransferRegistry.DefaultImpls.notifyDataChanged(this, uriArr);
    }

    @Override // com.motorola.plugin.core.channel.local.ILocalChannelTransferRegistry
    public void notifyDataChanged(Uri[] uriArr, Bundle bundle) throws RemoteException {
        uriArr.getClass();
        Iterator it = myRegistryMap.values().iterator();
        while (it.hasNext()) {
            ((LocalTransferToRemoteTransferAdapter) it.next()).notifyDataChanged(uriArr, bundle);
        }
    }

    @Override // com.motorola.plugin.core.channel.local.ILocalChannelTransferRegistry
    public void register(Class cls, final ILocalChannelTransfer iLocalChannelTransfer) {
        cls.getClass();
        iLocalChannelTransfer.getClass();
        final String pluginActionOrThrow = ExtensionsKt.getPluginActionOrThrow(cls);
        Map map = myRegistryMap;
        if (!map.containsKey(pluginActionOrThrow)) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_LOCAL_CHANNEL, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.local.LocalChannelTransferRegistry.register.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "Register local transfer for " + pluginActionOrThrow + " -> " + iLocalChannelTransfer;
                }
            }, 62, null);
            map.put(pluginActionOrThrow, new LocalTransferToRemoteTransferAdapter(iLocalChannelTransfer));
            return;
        }
        throw new IllegalStateException("Only one transfer can for each plugin : " + pluginActionOrThrow + " -> " + ((Object) cls.getSimpleName()));
    }

    @Override // com.motorola.plugin.core.channel.local.ILocalChannelTransferRegistry
    public void sendExtraDataToClient(ClientId clientId, Bundle bundle) throws RemoteException {
        bundle.getClass();
        Iterator it = myRegistryMap.values().iterator();
        while (it.hasNext()) {
            ((LocalTransferToRemoteTransferAdapter) it.next()).sendExtraDataToClient(clientId, bundle);
        }
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        LocalRegistrySnapshot localRegistrySnapshot = new LocalRegistrySnapshot(iSnapshot);
        for (Map.Entry entry : myRegistryMap.entrySet()) {
            localRegistrySnapshot.getRegistryMap().put((String) entry.getKey(), ((LocalTransferToRemoteTransferAdapter) entry.getValue()).toString());
        }
        return localRegistrySnapshot;
    }

    @Override // com.motorola.plugin.core.channel.local.ILocalChannelTransferRegistry
    public void unregister(final ILocalChannelTransfer iLocalChannelTransfer) {
        iLocalChannelTransfer.getClass();
        myRegistryMap.entrySet().removeIf(new Predicate() { // from class: com.motorola.plugin.core.channel.local.LocalChannelTransferRegistry$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return LocalChannelTransferRegistry.m2207unregister$lambda0(iLocalChannelTransfer, (Map.Entry) obj);
            }
        });
    }

    @Override // com.motorola.plugin.core.channel.local.ILocalChannelTransferRegistry
    public void unregister(Class cls) {
        cls.getClass();
        final String pluginActionOrThrow = ExtensionsKt.getPluginActionOrThrow(cls);
        final LocalTransferToRemoteTransferAdapter localTransferToRemoteTransferAdapter = (LocalTransferToRemoteTransferAdapter) myRegistryMap.remove(pluginActionOrThrow);
        PluginConfigKt.trace$default(PluginConfigKt.TAG_LOCAL_CHANNEL, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.local.LocalChannelTransferRegistry.unregister.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "Unregister local transfer for " + pluginActionOrThrow + " -> " + localTransferToRemoteTransferAdapter;
            }
        }, 62, null);
    }
}
