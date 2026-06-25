package com.motorola.plugin.core.channel;

import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.sdk.channel.IDataSetChangedCallback;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DefaultDataSetChangedRegistry.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DefaultDataSetChangedRegistry implements IDataSetChangedRegistryExtension {
    private boolean closed;
    private final WeakHashMap registryMap;
    private final IRemoteChannelExtension remoteChannel;

    /* JADX INFO: compiled from: DefaultDataSetChangedRegistry.kt */
    final class DataSetChangedRegistrySnapshot extends AbstractSnapshot {
        private boolean myClosed;
        private int myInstance;
        public Map myRegistrySnapshotMap;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DataSetChangedRegistrySnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final boolean getMyClosed() {
            return this.myClosed;
        }

        public final int getMyInstance() {
            return this.myInstance;
        }

        public final Map getMyRegistrySnapshotMap() {
            Map map = this.myRegistrySnapshotMap;
            if (map != null) {
                return map;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myRegistrySnapshotMap");
            throw null;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("DataSetRegistry", getMyInstance()).newLine();
            iPrinter.increaseIndent();
            iPrinter.printPair("closed", Boolean.valueOf(getMyClosed()));
            iPrinter.newLine();
            int i = 0;
            for (Object obj : MapsKt.asSequence(getMyRegistrySnapshotMap())) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Map.Entry entry = (Map.Entry) obj;
                IPrinter iPrinter2 = iPrinter;
                IPrinter.DefaultImpls.printIndex$default(iPrinter2, i, Intrinsics.stringPlus("callback=", entry.getKey()), null, 4, null);
                iPrinter2.newLine();
                iPrinter2.increaseIndent();
                ((ISnapshot) entry.getValue()).onSnapshot(iPrinter2);
                iPrinter2.decreaseIndent();
                iPrinter = iPrinter2;
                i = i2;
            }
            IPrinter iPrinter3 = iPrinter;
            iPrinter3.decreaseIndent();
            iPrinter3.newLine();
        }

        public final void setMyClosed(boolean z) {
            this.myClosed = z;
        }

        public final void setMyInstance(int i) {
            this.myInstance = i;
        }

        public final void setMyRegistrySnapshotMap(Map map) {
            map.getClass();
            this.myRegistrySnapshotMap = map;
        }
    }

    /* JADX INFO: compiled from: DefaultDataSetChangedRegistry.kt */
    final class Registry implements ISnapshotAware, Disposable {
        private final WeakReference callbackRef;
        private final HashSet uriSet;

        /* JADX INFO: compiled from: DefaultDataSetChangedRegistry.kt */
        final class RegistrySnapshot extends AbstractSnapshot {
            public List myUris;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public RegistrySnapshot(ISnapshot iSnapshot) {
                super(iSnapshot);
                iSnapshot.getClass();
            }

            public final List getMyUris() {
                List list = this.myUris;
                if (list != null) {
                    return list;
                }
                Intrinsics.throwUninitializedPropertyAccessException("myUris");
                throw null;
            }

            @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
            public void onSnapshot(IPrinter iPrinter) {
                iPrinter.getClass();
                super.onSnapshot(iPrinter);
                iPrinter.printPair("uris", getMyUris());
                iPrinter.newLine();
            }

            public final void setMyUris(List list) {
                list.getClass();
                this.myUris = list;
            }
        }

        public Registry(IDataSetChangedCallback iDataSetChangedCallback) {
            iDataSetChangedCallback.getClass();
            this.callbackRef = new WeakReference(iDataSetChangedCallback);
            this.uriSet = new HashSet();
        }

        @Override // com.motorola.plugin.core.misc.Disposable
        public void dispose() {
            this.uriSet.clear();
        }

        public final void notifyDataSetChanged(List list, Bundle bundle) {
            IDataSetChangedCallback iDataSetChangedCallback;
            list.getClass();
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (this.uriSet.contains((Uri) obj)) {
                    arrayList.add(obj);
                }
            }
            if (arrayList.isEmpty()) {
                arrayList = null;
            }
            if (arrayList == null || (iDataSetChangedCallback = (IDataSetChangedCallback) this.callbackRef.get()) == null) {
                return;
            }
            iDataSetChangedCallback.onDataSetChanged(arrayList, bundle);
        }

        public final void notifyReceivedExtraData(Bundle bundle) {
            bundle.getClass();
            IDataSetChangedCallback iDataSetChangedCallback = (IDataSetChangedCallback) this.callbackRef.get();
            if (iDataSetChangedCallback == null) {
                return;
            }
            iDataSetChangedCallback.onReceivedExtraData(bundle);
        }

        public final void register(Uri... uriArr) {
            uriArr.getClass();
            int length = uriArr.length;
            int i = 0;
            while (i < length) {
                Uri uri = uriArr[i];
                i++;
                if (!this.uriSet.contains(uri)) {
                    this.uriSet.add(uri);
                }
            }
        }

        @Override // com.motorola.plugin.core.misc.ISnapshotAware
        public ISnapshot snapshot(ISnapshot iSnapshot) {
            iSnapshot.getClass();
            RegistrySnapshot registrySnapshot = new RegistrySnapshot(iSnapshot);
            HashSet hashSet = this.uriSet;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(hashSet, 10));
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                arrayList.add(((Uri) it.next()).toString());
            }
            registrySnapshot.setMyUris(CollectionsKt.toList(arrayList));
            return registrySnapshot;
        }

        public final boolean unregister(Uri... uriArr) {
            uriArr.getClass();
            CollectionsKt.removeAll(this.uriSet, uriArr);
            return this.uriSet.isEmpty();
        }
    }

    public DefaultDataSetChangedRegistry(IRemoteChannelExtension iRemoteChannelExtension) {
        iRemoteChannelExtension.getClass();
        this.remoteChannel = iRemoteChannelExtension;
        this.registryMap = new WeakHashMap();
    }

    private final void throwIfNotMainThread(String str) throws IllegalAccessException {
        if (Looper.getMainLooper().isCurrentThread()) {
            return;
        }
        throw new IllegalAccessException("DataSetChangedRegistry#" + str + " method can only invoke via main thread");
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        Collection collectionValues = this.registryMap.values();
        collectionValues.getClass();
        Iterator it = collectionValues.iterator();
        while (it.hasNext()) {
            ((Registry) it.next()).dispose();
        }
        this.registryMap.clear();
        this.closed = true;
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CHANNEL, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.DefaultDataSetChangedRegistry.dispose.2
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + DefaultDataSetChangedRegistry.this.remoteChannel.getToken() + "] Close dataset registry";
            }
        }, 62, null);
    }

    @Override // com.motorola.plugin.core.channel.IDataSetChangedRegistryExtension
    public void notifyDataSetChanged(List list, Bundle bundle) {
        list.getClass();
        Iterator it = this.registryMap.entrySet().iterator();
        while (it.hasNext()) {
            ((Registry) ((Map.Entry) it.next()).getValue()).notifyDataSetChanged(list, bundle);
        }
    }

    @Override // com.motorola.plugin.core.channel.IDataSetChangedRegistryExtension
    public void notifyReceivedExtraData(Bundle bundle) {
        bundle.getClass();
        Iterator it = this.registryMap.entrySet().iterator();
        while (it.hasNext()) {
            ((Registry) ((Map.Entry) it.next()).getValue()).notifyReceivedExtraData(bundle);
        }
    }

    @Override // com.motorola.plugin.sdk.channel.IDataSetChangedRegistry
    public void register(IDataSetChangedCallback iDataSetChangedCallback, Uri... uriArr) throws IllegalAccessException {
        iDataSetChangedCallback.getClass();
        uriArr.getClass();
        throwIfNotMainThread("register");
        if (uriArr.length == 0) {
            return;
        }
        Registry registry = (Registry) this.registryMap.get(iDataSetChangedCallback);
        if (registry == null) {
            registry = new Registry(iDataSetChangedCallback);
            this.registryMap.put(iDataSetChangedCallback, registry);
        }
        registry.register((Uri[]) Arrays.copyOf(uriArr, uriArr.length));
        this.remoteChannel.keepChannelConnectionAlive(true, 4);
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        DataSetChangedRegistrySnapshot dataSetChangedRegistrySnapshot = new DataSetChangedRegistrySnapshot(iSnapshot);
        dataSetChangedRegistrySnapshot.setMyInstance(hashCode());
        dataSetChangedRegistrySnapshot.setMyClosed(this.closed);
        WeakHashMap weakHashMap = this.registryMap;
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(weakHashMap.size()));
        for (Map.Entry entry : weakHashMap.entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue());
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(linkedHashMap.size()));
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            linkedHashMap2.put(entry2.getKey(), ((Registry) entry2.getValue()).snapshot(iSnapshot));
        }
        dataSetChangedRegistrySnapshot.setMyRegistrySnapshotMap(linkedHashMap2);
        return dataSetChangedRegistrySnapshot;
    }

    @Override // com.motorola.plugin.sdk.channel.IDataSetChangedRegistry
    public void unregister(IDataSetChangedCallback iDataSetChangedCallback, Uri... uriArr) throws IllegalAccessException {
        iDataSetChangedCallback.getClass();
        uriArr.getClass();
        throwIfNotMainThread("unregister");
        if (uriArr.length == 0) {
            return;
        }
        Registry registry = (Registry) this.registryMap.get(iDataSetChangedCallback);
        if (Intrinsics.areEqual(registry == null ? null : Boolean.valueOf(registry.unregister((Uri[]) Arrays.copyOf(uriArr, uriArr.length))), Boolean.TRUE)) {
            this.remoteChannel.keepChannelConnectionAlive(false, 4);
            this.registryMap.remove(iDataSetChangedCallback);
        }
    }
}
