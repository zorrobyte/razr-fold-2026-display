package com.motorola.plugin.core.components.impls;

import android.content.res.Configuration;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: ConfigurationListenerChainedDispatcherImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ConfigurationListenerChainedDispatcherImpl implements ConfigurationListenerChainedDispatcher {
    private final CoroutineScope scope = CoroutineScopeKt.MainScope();
    private final List myCallbacks = new ArrayList();

    /* JADX INFO: compiled from: ConfigurationListenerChainedDispatcherImpl.kt */
    final class ChainedDispatcherImpl implements ConfigurationListenerChainedDispatcher.ChainedDispatcher {
        private final ListIterator iter;

        public ChainedDispatcherImpl(List list) {
            list.getClass();
            this.iter = list.listIterator();
        }

        @Override // com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher.ChainedDispatcher
        public void processNextConfigChanged(Configuration configuration, BitFlag bitFlag) {
            configuration.getClass();
            bitFlag.getClass();
            if (this.iter.hasNext()) {
                ((ConfigurationListenerChainedDispatcher.ConfigurationChainedListener) this.iter.next()).onConfigChanged(configuration, bitFlag, this);
            }
        }
    }

    /* JADX INFO: compiled from: ConfigurationListenerChainedDispatcherImpl.kt */
    final class ConfigurationListenerDispatcherSnapshot extends AbstractSnapshot {
        private int myConfigurationListenerDispatcherInstance;
        public List myPluginListenerSnapshots;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ConfigurationListenerDispatcherSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final int getMyConfigurationListenerDispatcherInstance() {
            return this.myConfigurationListenerDispatcherInstance;
        }

        public final List getMyPluginListenerSnapshots() {
            List list = this.myPluginListenerSnapshots;
            if (list != null) {
                return list;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myPluginListenerSnapshots");
            throw null;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("ConfigurationListenerDispatcher", getMyConfigurationListenerDispatcherInstance()).newLine();
            iPrinter.increaseIndent();
            iPrinter.printSingle("Listeners:");
            iPrinter.printPair("size", Integer.valueOf(getMyPluginListenerSnapshots().size()));
            iPrinter.newLine();
            int i = 0;
            for (Object obj : getMyPluginListenerSnapshots()) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                iPrinter.increaseIndent();
                IPrinter.DefaultImpls.printIndex$default(iPrinter, i, (String) obj, null, 4, null);
                iPrinter.newLine();
                iPrinter.decreaseIndent();
                i = i2;
            }
            iPrinter.decreaseIndent();
        }

        public final void setMyConfigurationListenerDispatcherInstance(int i) {
            this.myConfigurationListenerDispatcherInstance = i;
        }

        public final void setMyPluginListenerSnapshots(List list) {
            list.getClass();
            this.myPluginListenerSnapshots = list;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.ConfigurationListenerChainedDispatcherImpl$onConfigChanged$2, reason: invalid class name */
    /* JADX INFO: compiled from: ConfigurationListenerChainedDispatcherImpl.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ BitFlag $changedFlags;
        final /* synthetic */ Configuration $newConfig;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Configuration configuration, BitFlag bitFlag, Continuation continuation) {
            super(2, continuation);
            this.$newConfig = configuration;
            this.$changedFlags = bitFlag;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ConfigurationListenerChainedDispatcherImpl.this.new AnonymousClass2(this.$newConfig, this.$changedFlags, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            new ChainedDispatcherImpl(CollectionsKt.toList(ConfigurationListenerChainedDispatcherImpl.this.myCallbacks)).processNextConfigChanged(this.$newConfig, this.$changedFlags);
            return Unit.INSTANCE;
        }
    }

    @Override // com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher
    public void addChainedListener(ConfigurationListenerChainedDispatcher.ConfigurationChainedListener... configurationChainedListenerArr) {
        configurationChainedListenerArr.getClass();
        CollectionsKt.addAll(this.myCallbacks, configurationChainedListenerArr);
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        CoroutineScopeKt.cancel$default(this.scope, null, 1, null);
        this.myCallbacks.clear();
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
    public void onConfigChanged(final Configuration configuration, final BitFlag bitFlag) {
        configuration.getClass();
        bitFlag.getClass();
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.ConfigurationListenerChainedDispatcherImpl.onConfigChanged.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "Dispatch configChanged, flags = " + bitFlag + ", config = " + configuration;
            }
        }, 62, null);
        BuildersKt__Builders_commonKt.launch$default(this.scope, Dispatchers.getDefault(), null, new AnonymousClass2(configuration, bitFlag, null), 2, null);
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
    public void onLowMemory() {
        Iterator it = this.myCallbacks.iterator();
        while (it.hasNext()) {
            ((ConfigurationListenerChainedDispatcher.ConfigurationChainedListener) it.next()).onLowMemory();
        }
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
    public void onTrimMemory(int i) {
        Iterator it = this.myCallbacks.iterator();
        while (it.hasNext()) {
            ((ConfigurationListenerChainedDispatcher.ConfigurationChainedListener) it.next()).onTrimMemory(i);
        }
    }

    @Override // com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher
    public void removeChainedListener(ConfigurationListenerChainedDispatcher.ConfigurationChainedListener... configurationChainedListenerArr) {
        configurationChainedListenerArr.getClass();
        CollectionsKt.removeAll(this.myCallbacks, configurationChainedListenerArr);
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        ConfigurationListenerDispatcherSnapshot configurationListenerDispatcherSnapshot = new ConfigurationListenerDispatcherSnapshot(iSnapshot);
        configurationListenerDispatcherSnapshot.setMyConfigurationListenerDispatcherInstance(hashCode());
        List list = this.myCallbacks;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ConfigurationListenerChainedDispatcher.ConfigurationChainedListener) it.next()).toString());
        }
        configurationListenerDispatcherSnapshot.setMyPluginListenerSnapshots(CollectionsKt.toList(arrayList));
        return configurationListenerDispatcherSnapshot;
    }
}
