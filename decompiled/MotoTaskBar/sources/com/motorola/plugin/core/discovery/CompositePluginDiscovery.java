package com.motorola.plugin.core.discovery;

import android.content.Context;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CompositePluginDiscovery.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class CompositePluginDiscovery implements PluginDiscovery {
    private final ArrayList discoveries;

    /* JADX INFO: compiled from: CompositePluginDiscovery.kt */
    final class CompositePluginDiscoverySnapshot extends AbstractSnapshot {
        public List mySnapshots;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CompositePluginDiscoverySnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final List getMySnapshots() {
            List list = this.mySnapshots;
            if (list != null) {
                return list;
            }
            Intrinsics.throwUninitializedPropertyAccessException("mySnapshots");
            throw null;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            Iterator it = getMySnapshots().iterator();
            while (it.hasNext()) {
                ((ISnapshot) it.next()).onSnapshot(iPrinter);
            }
        }

        public final void setMySnapshots(List list) {
            list.getClass();
            this.mySnapshots = list;
        }
    }

    public CompositePluginDiscovery(PluginDiscovery... pluginDiscoveryArr) {
        pluginDiscoveryArr.getClass();
        this.discoveries = CollectionsKt.arrayListOf(Arrays.copyOf(pluginDiscoveryArr, pluginDiscoveryArr.length));
    }

    @Override // com.motorola.plugin.core.discovery.PluginDiscovery
    public List discovery(Context context, String str, PluginPackage pluginPackage, String str2) {
        context.getClass();
        str.getClass();
        ArrayList arrayList = this.discoveries;
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            CollectionsKt.addAll(arrayList2, ((PluginDiscovery) obj).discovery(context, str, pluginPackage, str2));
        }
        return arrayList2;
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        ArrayList arrayList = this.discoveries;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((PluginDiscovery) obj).dispose();
        }
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        CompositePluginDiscoverySnapshot compositePluginDiscoverySnapshot = new CompositePluginDiscoverySnapshot(iSnapshot);
        ArrayList arrayList = this.discoveries;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            arrayList2.add(ISnapshotAware.DefaultImpls.snapshot$default((PluginDiscovery) obj, null, 1, null));
        }
        compositePluginDiscoverySnapshot.setMySnapshots(arrayList2);
        return compositePluginDiscoverySnapshot;
    }
}
