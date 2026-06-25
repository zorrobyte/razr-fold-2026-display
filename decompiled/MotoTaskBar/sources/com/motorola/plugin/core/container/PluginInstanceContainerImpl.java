package com.motorola.plugin.core.container;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Message;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.channel.ChannelParams;
import com.motorola.plugin.core.channel.IRemoteChannelExtension;
import com.motorola.plugin.core.channel.RemoteChannelFactory;
import com.motorola.plugin.core.channel.RemoteChannelWithPluginId;
import com.motorola.plugin.core.components.DisplayContext;
import com.motorola.plugin.core.components.PluginEnabler;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.components.PluginListenerDispatcher;
import com.motorola.plugin.core.components.PluginWhitelistPolicyExt;
import com.motorola.plugin.core.container.PluginInstanceContainer;
import com.motorola.plugin.core.context.PluginContext;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.discovery.DiscoverInfo;
import com.motorola.plugin.core.discovery.PluginType;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.CleanupResourcesKt;
import com.motorola.plugin.core.misc.DeviceState;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.sdk.Plugin;
import com.motorola.plugin.sdk.channel.ClientId;
import com.motorola.plugins.ViewTypePlugin;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;

/* JADX INFO: compiled from: PluginInstanceContainerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginInstanceContainerImpl implements PluginInstanceContainer {
    public static final Companion Companion = new Companion(null);
    private static final boolean DEBUG_AUTO_RELEASE = false;
    private Job mAutoTimeoutReleaseJob;
    private ClientId mClientId;
    private final CoroutineScope mContainerCoroutineScope;
    private final Context mContext;
    private final Set mDeathRecipient;
    private final DeviceState mDeviceState;
    private boolean mDisposed;
    private boolean mKeepChannelConnectionAlive;
    private final AtomicReference mPluginContextRef;
    private final PluginEnabler mPluginEnabler;
    private final PluginEvent mPluginEvent;
    private final Map mPluginInstancesByAction;
    private PluginListenerDispatcher mPluginListenerDispatcher;
    private PluginPackage mPluginPackage;
    private final PluginWhitelistPolicyExt mPluginWhitelistPolicy;
    private final AtomicReference mRemoteChannelRef;

    /* JADX INFO: compiled from: PluginInstanceContainerImpl.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: PluginInstanceContainerImpl.kt */
    final class PluginInstanceSnapshot extends AbstractSnapshot {
        public ClientId myClientId;
        public PluginPackage myContainerPackage;
        private boolean myKeepChannelConnectionAlive;
        private Job myPendingAutoTimeoutRelease;
        private int myPluginInstanceContainerInstance;
        private ISnapshot myRemoteChannelSnapshot;
        private int mySizeOfPlugins;
        private final Map pluginInfoSnapshots;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PluginInstanceSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
            this.pluginInfoSnapshots = new LinkedHashMap();
            this.myKeepChannelConnectionAlive = true;
        }

        public final ClientId getMyClientId() {
            ClientId clientId = this.myClientId;
            if (clientId != null) {
                return clientId;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myClientId");
            throw null;
        }

        public final PluginPackage getMyContainerPackage() {
            PluginPackage pluginPackage = this.myContainerPackage;
            if (pluginPackage != null) {
                return pluginPackage;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myContainerPackage");
            throw null;
        }

        public final boolean getMyKeepChannelConnectionAlive() {
            return this.myKeepChannelConnectionAlive;
        }

        public final Job getMyPendingAutoTimeoutRelease() {
            return this.myPendingAutoTimeoutRelease;
        }

        public final int getMyPluginInstanceContainerInstance() {
            return this.myPluginInstanceContainerInstance;
        }

        public final ISnapshot getMyRemoteChannelSnapshot() {
            return this.myRemoteChannelSnapshot;
        }

        public final int getMySizeOfPlugins() {
            return this.mySizeOfPlugins;
        }

        public final Map getPluginInfoSnapshots() {
            return this.pluginInfoSnapshots;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("PluginInstanceContainer", getMyPluginInstanceContainerInstance());
            iPrinter.printPair("package", getMyContainerPackage());
            iPrinter.printPair("token", getMyClientId().uniqueId);
            iPrinter.newLine();
            iPrinter.printPair("release_job", getMyPendingAutoTimeoutRelease());
            iPrinter.printPair("keep_channel_alive", Boolean.valueOf(getMyKeepChannelConnectionAlive()));
            iPrinter.newLine();
            iPrinter.increaseIndent();
            iPrinter.printSingle("Plugin instances(" + getMySizeOfPlugins() + ')').newLine();
            int i = 0;
            for (Object obj : getPluginInfoSnapshots().entrySet()) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Map.Entry entry = (Map.Entry) obj;
                iPrinter.increaseIndent();
                IPrinter iPrinter2 = iPrinter;
                IPrinter.DefaultImpls.printIndex$default(iPrinter2, i, entry.getKey(), null, 4, null).newLine();
                ((ISnapshot) entry.getValue()).onSnapshot(iPrinter2);
                iPrinter2.decreaseIndent();
                iPrinter = iPrinter2;
                i = i2;
            }
            IPrinter iPrinter3 = iPrinter;
            ISnapshot myRemoteChannelSnapshot = getMyRemoteChannelSnapshot();
            if (myRemoteChannelSnapshot != null) {
                myRemoteChannelSnapshot.onSnapshot(iPrinter3);
            }
            iPrinter3.decreaseIndent();
            iPrinter3.newLine();
        }

        public final void setMyClientId(ClientId clientId) {
            clientId.getClass();
            this.myClientId = clientId;
        }

        public final void setMyContainerPackage(PluginPackage pluginPackage) {
            pluginPackage.getClass();
            this.myContainerPackage = pluginPackage;
        }

        public final void setMyKeepChannelConnectionAlive(boolean z) {
            this.myKeepChannelConnectionAlive = z;
        }

        public final void setMyPendingAutoTimeoutRelease(Job job) {
            this.myPendingAutoTimeoutRelease = job;
        }

        public final void setMyPluginInstanceContainerInstance(int i) {
            this.myPluginInstanceContainerInstance = i;
        }

        public final void setMyRemoteChannelSnapshot(ISnapshot iSnapshot) {
            this.myRemoteChannelSnapshot = iSnapshot;
        }

        public final void setMySizeOfPlugins(int i) {
            this.mySizeOfPlugins = i;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.container.PluginInstanceContainerImpl$createPluginInstance$2, reason: invalid class name */
    /* JADX INFO: compiled from: PluginInstanceContainerImpl.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ DiscoverInfo $discoverInfo;
        final /* synthetic */ Class $prototypePluginClass;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Class cls, DiscoverInfo discoverInfo, Continuation continuation) {
            super(2, continuation);
            this.$prototypePluginClass = cls;
            this.$discoverInfo = discoverInfo;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PluginInstanceContainerImpl.this.new AnonymousClass2(this.$prototypePluginClass, this.$discoverInfo, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object objWithContext;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PluginInstanceContainerImpl.this.cancelAutoReleaseJob();
                PluginInstanceContainerImpl.this.destroyPluginInstance(this.$prototypePluginClass, 3, this.$discoverInfo);
                PluginEvent.DefaultImpls.recordEvent$default(PluginInstanceContainerImpl.this.mPluginEvent, PluginInstanceContainerImpl.this.containerTag() + " try to load plugin " + ((Object) this.$prototypePluginClass.getName()), null, 2, null);
                Level level = Level.INFO;
                String traceId = this.$discoverInfo.getTraceId();
                final PluginInstanceContainerImpl pluginInstanceContainerImpl = PluginInstanceContainerImpl.this;
                final Class cls = this.$prototypePluginClass;
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, level, false, null, false, traceId, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.createPluginInstance.2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return pluginInstanceContainerImpl.containerTag() + " try load plugin " + ((Object) cls.getName());
                    }
                }, 28, null);
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                PluginInstanceContainerImpl$createPluginInstance$2$pluginInstance$1 pluginInstanceContainerImpl$createPluginInstance$2$pluginInstance$1 = new PluginInstanceContainerImpl$createPluginInstance$2$pluginInstance$1(PluginInstanceContainerImpl.this, this.$discoverInfo, this.$prototypePluginClass, null);
                this.label = 1;
                objWithContext = BuildersKt.withContext(coroutineDispatcher, pluginInstanceContainerImpl$createPluginInstance$2$pluginInstance$1, this);
                if (objWithContext == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                objWithContext = obj;
            }
            PluginInstance pluginInstance = (PluginInstance) objWithContext;
            if (PluginInstanceContainerImpl.this.mDisposed) {
                Level level2 = Level.WARN;
                String traceId2 = this.$discoverInfo.getTraceId();
                final PluginInstanceContainerImpl pluginInstanceContainerImpl2 = PluginInstanceContainerImpl.this;
                final DiscoverInfo discoverInfo = this.$discoverInfo;
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, level2, false, null, false, traceId2, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.createPluginInstance.2.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return pluginInstanceContainerImpl2.containerTag() + " Plugin instance container is disposed during creating plugin instance [" + discoverInfo.getAction() + ']';
                    }
                }, 28, null);
                return Unit.INSTANCE;
            }
            if (PluginInstanceContainerImpl.this.mPluginInstancesByAction.get(this.$discoverInfo.getAction()) != null) {
                PluginInstanceContainerImpl.this.destroyPluginInstance(this.$prototypePluginClass, 3, this.$discoverInfo);
            }
            if (pluginInstance == null) {
                PluginInstanceContainerImpl.this.dispatchPluginFailedLoad(this.$discoverInfo);
                PluginInstanceContainerImpl.this.scheduleAutoReleaseJob();
                return Unit.INSTANCE;
            }
            PluginInstanceContainerImpl.this.cancelAutoReleaseJob();
            IRemoteChannelExtension remoteChannelChecked = PluginInstanceContainerImpl.this.getRemoteChannelChecked();
            if (remoteChannelChecked != null) {
                if (Boxing.boxBoolean(remoteChannelChecked.isClosed()).booleanValue()) {
                    remoteChannelChecked = null;
                }
                if (remoteChannelChecked != null) {
                    PluginInstanceContainerImpl pluginInstanceContainerImpl3 = PluginInstanceContainerImpl.this;
                    remoteChannelChecked.resume();
                    remoteChannelChecked.keepChannelConnectionAlive(pluginInstanceContainerImpl3.mKeepChannelConnectionAlive, 2);
                }
            }
            Level level3 = Level.INFO;
            String traceId3 = this.$discoverInfo.getTraceId();
            final PluginInstanceContainerImpl pluginInstanceContainerImpl4 = PluginInstanceContainerImpl.this;
            final DiscoverInfo discoverInfo2 = this.$discoverInfo;
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, level3, false, null, false, traceId3, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.createPluginInstance.2.5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return pluginInstanceContainerImpl4.containerTag() + " Created plugin instance [" + discoverInfo2.getAction() + ']';
                }
            }, 28, null);
            PluginEvent.DefaultImpls.recordEvent$default(PluginInstanceContainerImpl.this.mPluginEvent, PluginInstanceContainerImpl.this.containerTag() + " create new plugin instance " + ((Object) this.$prototypePluginClass.getName()) + '@' + ExtensionsKt.hashCodeHex(pluginInstance.getPluginInstance()), null, 2, null);
            PluginInstanceContainerImpl.this.mPluginInstancesByAction.put(this.$discoverInfo.getAction(), pluginInstance);
            PluginInstanceContainerImpl.this.dispatchPluginConnected(pluginInstance, this.$discoverInfo);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.container.PluginInstanceContainerImpl$scheduleAutoReleaseJob$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PluginInstanceContainerImpl.kt */
    final class C01932 extends SuspendLambda implements Function2 {
        int label;

        C01932(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PluginInstanceContainerImpl.this.new C01932(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01932) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IRemoteChannelExtension remoteChannelChecked;
            Job job;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (PluginInstanceContainerImpl.this.mPluginInstancesByAction.isEmpty() && (remoteChannelChecked = PluginInstanceContainerImpl.this.getRemoteChannelChecked()) != null) {
                    if (Boxing.boxBoolean(remoteChannelChecked.isClosed()).booleanValue()) {
                        remoteChannelChecked = null;
                    }
                    if (remoteChannelChecked != null) {
                        remoteChannelChecked.pause();
                    }
                }
                long aUTO_RELEASE_TIMEOUT_FOR_RESTRICT$core_stubRelease = PluginInstanceContainerImpl.this.mDeviceState.willRunInRestrictedMode(PluginInstanceContainerImpl.this.mContext) ? PluginInstanceContainer.Companion.getAUTO_RELEASE_TIMEOUT_FOR_RESTRICT$core_stubRelease() : PluginInstanceContainerImpl.this.mDeviceState.isLowRamDevice() ? PluginInstanceContainer.Companion.getAUTO_RELEASE_TIMEOUT_FOR_LOW_RAM$core_stubRelease() : PluginInstanceContainer.Companion.getAUTO_RELEASE_TIMEOUT_DEFAULT$core_stubRelease();
                this.label = 1;
                if (DelayKt.delay(aUTO_RELEASE_TIMEOUT_FOR_RESTRICT$core_stubRelease, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            if (!PluginInstanceContainerImpl.this.mPluginInstancesByAction.isEmpty() || ((job = PluginInstanceContainerImpl.this.mAutoTimeoutReleaseJob) != null && job.isCancelled())) {
                Level level = Level.INFO;
                final PluginInstanceContainerImpl pluginInstanceContainerImpl = PluginInstanceContainerImpl.this;
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.scheduleAutoReleaseJob.2.2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return pluginInstanceContainerImpl.containerTag() + " Schedule auto release job when execute job " + pluginInstanceContainerImpl.mAutoTimeoutReleaseJob;
                    }
                }, 60, null);
                PluginInstanceContainerImpl.this.scheduleAutoReleaseJob();
            } else {
                Level level2 = Level.INFO;
                final PluginInstanceContainerImpl pluginInstanceContainerImpl2 = PluginInstanceContainerImpl.this;
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, level2, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.scheduleAutoReleaseJob.2.3
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return pluginInstanceContainerImpl2.containerTag() + " Broadcast auto release event, " + pluginInstanceContainerImpl2.mAutoTimeoutReleaseJob;
                    }
                }, 60, null);
                Set<PluginInstanceContainer.DeathRecipient> set = PluginInstanceContainerImpl.this.mDeathRecipient;
                PluginInstanceContainerImpl pluginInstanceContainerImpl3 = PluginInstanceContainerImpl.this;
                for (PluginInstanceContainer.DeathRecipient deathRecipient : set) {
                    PluginPackage pluginPackage = pluginInstanceContainerImpl3.mPluginPackage;
                    if (pluginPackage == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mPluginPackage");
                        throw null;
                    }
                    deathRecipient.onPluginInstanceContainerDied(pluginPackage, 6);
                }
                PluginInstanceContainerImpl.this.mAutoTimeoutReleaseJob = null;
            }
            return Unit.INSTANCE;
        }
    }

    public PluginInstanceContainerImpl(@DisplayContext Context context, PluginEvent pluginEvent, PluginEnabler pluginEnabler, PluginWhitelistPolicyExt pluginWhitelistPolicyExt, PluginListenerDispatcher pluginListenerDispatcher, DeviceState deviceState) {
        context.getClass();
        pluginEvent.getClass();
        pluginEnabler.getClass();
        pluginWhitelistPolicyExt.getClass();
        deviceState.getClass();
        this.mContext = context;
        this.mPluginEvent = pluginEvent;
        this.mPluginEnabler = pluginEnabler;
        this.mPluginWhitelistPolicy = pluginWhitelistPolicyExt;
        this.mPluginListenerDispatcher = pluginListenerDispatcher;
        this.mDeviceState = deviceState;
        this.mRemoteChannelRef = new AtomicReference();
        this.mPluginInstancesByAction = new LinkedHashMap();
        this.mContainerCoroutineScope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob$default(null, 1, null).plus(Dispatchers.getMain().getImmediate()));
        this.mDeathRecipient = new LinkedHashSet();
        this.mPluginContextRef = new AtomicReference();
        this.mKeepChannelConnectionAlive = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cancelAutoReleaseJob() {
        if (this.mAutoTimeoutReleaseJob != null) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.cancelAutoReleaseJob.1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return PluginInstanceContainerImpl.this.containerTag() + " Cancel auto release job, " + PluginInstanceContainerImpl.this.mAutoTimeoutReleaseJob;
                }
            }, 60, null);
            Job job = this.mAutoTimeoutReleaseJob;
            if (job != null) {
                Job.DefaultImpls.cancel$default(job, null, 1, null);
            }
            this.mAutoTimeoutReleaseJob = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String containerTag() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        PluginPackage pluginPackage = this.mPluginPackage;
        if (pluginPackage == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mPluginPackage");
            throw null;
        }
        sb.append(pluginPackage.getPluginId().getId());
        sb.append('@');
        sb.append(ExtensionsKt.hashCodeHex(this));
        sb.append(']');
        return sb.toString();
    }

    private final PluginInstance createPluginOrThrow(final DiscoverInfo discoverInfo, Class cls) throws ClassNotFoundException {
        String packageName = discoverInfo.getServiceComponent().getPackageName();
        packageName.getClass();
        final String implementorClazz = discoverInfo.getImplementorClazz();
        Class<?> cls2 = Class.forName(implementorClazz, true, discoverInfo.getClassLoader());
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, null, false, null, false, discoverInfo.getTraceId(), new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.createPluginOrThrow.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return PluginInstanceContainerImpl.this.containerTag() + " Creating plugin [" + implementorClazz + "] for action [" + discoverInfo.getAction() + ']';
            }
        }, 30, null);
        this.mPluginContextRef.compareAndSet(null, new WeakReference(discoverInfo.getPluginContext()));
        Object objCast = Plugin.class.cast(cls2.newInstance());
        objCast.getClass();
        Plugin plugin = (Plugin) objCast;
        this.mRemoteChannelRef.compareAndSet(null, LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.createPluginOrThrow.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final IRemoteChannelExtension mo2224invoke() {
                return PluginInstanceContainerImpl.this.createRemoteChannel(discoverInfo);
            }
        }));
        return new PluginInstance(newClientId(packageName), cls, cls2, plugin, discoverInfo.getPluginComponent(), discoverInfo.getPluginContext(), discoverInfo.getVersionInfo(), discoverInfo.getAction(), (IRemoteChannelExtension) ((Lazy) this.mRemoteChannelRef.get()).getValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IRemoteChannelExtension createRemoteChannel(final DiscoverInfo discoverInfo) {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.DEBUG, false, null, false, discoverInfo.getTraceId(), new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.createRemoteChannel.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return PluginInstanceContainerImpl.this.containerTag() + " creating " + discoverInfo.getPluginType() + " channel for " + discoverInfo.getServiceComponent().getPackageName();
            }
        }, 28, null);
        Context applicationContext = this.mContext.getApplicationContext();
        String action = discoverInfo.getAction();
        ComponentName serviceComponent = discoverInfo.getServiceComponent();
        ClassLoader classLoader = discoverInfo.getClassLoader();
        PluginType pluginType = discoverInfo.getPluginType();
        ClientId clientId = this.mClientId;
        if (clientId == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClientId");
            throw null;
        }
        PluginEvent pluginEvent = this.mPluginEvent;
        DeviceState deviceState = this.mDeviceState;
        applicationContext.getClass();
        IRemoteChannelExtension iRemoteChannelExtensionCreateRemoteChannel = RemoteChannelFactory.Factory.createRemoteChannel(new ChannelParams(applicationContext, action, serviceComponent, classLoader, clientId, pluginType, pluginEvent, deviceState, null, null, 768, null));
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.INFO, false, null, false, discoverInfo.getTraceId(), new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.createRemoteChannel.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return PluginInstanceContainerImpl.this.containerTag() + " created " + discoverInfo.getPluginType() + " channel for " + discoverInfo.getServiceComponent().getPackageName();
            }
        }, 28, null);
        PluginEvent.DefaultImpls.recordEvent$default(this.mPluginEvent, containerTag() + " created " + discoverInfo.getPluginType() + " channel with token " + iRemoteChannelExtensionCreateRemoteChannel.getToken(), null, 2, null);
        return iRemoteChannelExtensionCreateRemoteChannel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void destroyPluginInstance(Class cls, int i, DiscoverInfo discoverInfo) {
        PluginInstance pluginInstance = (PluginInstance) this.mPluginInstancesByAction.remove(ExtensionsKt.getPluginActionOrThrow(cls));
        if (pluginInstance != null) {
            final String str = containerTag() + " destroy plugin instance for " + ((Object) cls.getSimpleName()) + "@0x" + ExtensionsKt.hashCodeHex(pluginInstance.getPluginInstance()) + " with reason [" + PluginInstanceContainer.Companion.disconnectReason2String$core_stubRelease(i) + ']';
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.INFO, false, null, false, discoverInfo == null ? null : discoverInfo.getTraceId(), new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl$destroyPluginInstance$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return str;
                }
            }, 28, null);
            this.mPluginEvent.recordEvent(str, ISnapshotAware.DefaultImpls.snapshot$default(this, null, 1, null));
            dispatchPluginDisconnected(pluginInstance, i, discoverInfo);
        }
        if (i == 2 || i == 3 || i == 4 || i == 5) {
            return;
        }
        scheduleAutoReleaseJob();
    }

    static /* synthetic */ void destroyPluginInstance$default(PluginInstanceContainerImpl pluginInstanceContainerImpl, Class cls, int i, DiscoverInfo discoverInfo, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            discoverInfo = null;
        }
        pluginInstanceContainerImpl.destroyPluginInstance(cls, i, discoverInfo);
    }

    private final boolean disable(PluginInstance pluginInstance, @PluginEnabler.DisableReason int i) {
        final ComponentName pluginComponent = pluginInstance.getPluginComponent();
        int i2 = 0;
        if (this.mPluginWhitelistPolicy.isPluginWhitelisted(pluginComponent)) {
            return false;
        }
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.disable.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return PluginInstanceContainerImpl.this.containerTag() + " Disabling plugin [" + pluginComponent.flattenToShortString() + ']';
            }
        }, 60, null);
        if (i == 3) {
            this.mPluginEvent.recordEvent(Intrinsics.stringPlus(containerTag(), " plugin crash"), ISnapshotAware.DefaultImpls.snapshot$default(pluginInstance, null, 1, null));
            i2 = 1;
        } else if (i != 4) {
            i2 = 5;
        }
        PluginEvent.DefaultImpls.recordEvent$default(this.mPluginEvent, containerTag() + ' ' + containerTag() + " disable plugin [" + pluginComponent.flattenToShortString() + "] for reason " + PluginEnabler.Companion.disableReasonToString(i), null, 2, null);
        destroyPluginInstance(pluginInstance.getPrototypePluginClass(), i2);
        this.mPluginEnabler.setDisabled(pluginComponent, i);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dispatchPluginConnected(final PluginInstance pluginInstance, DiscoverInfo discoverInfo) {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.INFO, false, null, false, discoverInfo.getTraceId(), new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.dispatchPluginConnected.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return PluginInstanceContainerImpl.this.containerTag() + " Dispatch plugin connected " + pluginInstance;
            }
        }, 28, null);
        PluginEvent.DefaultImpls.recordEvent$default(this.mPluginEvent, containerTag() + " plugin " + pluginInstance + " connected", null, 2, null);
        pluginInstance.getPluginInstance().onCreate(this.mContext, pluginInstance.getPluginContext(), new RemoteChannelWithPluginId(pluginInstance.getPluginId(), pluginInstance.getRemoteChannel()));
        PluginListenerDispatcher pluginListenerDispatcher = this.mPluginListenerDispatcher;
        if (pluginListenerDispatcher == null) {
            return;
        }
        pluginListenerDispatcher.dispatchPluginConnected(pluginInstance.getPluginAction(), pluginInstance.getPrototypePluginClass().getName(), pluginInstance.getPluginInstance(), pluginInstance.getPluginContext());
    }

    private final void dispatchPluginDisconnected(final PluginInstance pluginInstance, int i, DiscoverInfo discoverInfo) {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.WARN, false, null, false, discoverInfo == null ? null : discoverInfo.getTraceId(), new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.dispatchPluginDisconnected.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return PluginInstanceContainerImpl.this.containerTag() + " Dispatch plugin disconnected " + pluginInstance;
            }
        }, 28, null);
        String strDisconnectReason2String$core_stubRelease = PluginInstanceContainer.Companion.disconnectReason2String$core_stubRelease(i);
        this.mPluginEvent.recordEvent(containerTag() + " plugin " + pluginInstance + " disconnected for " + strDisconnectReason2String$core_stubRelease, ISnapshotAware.DefaultImpls.snapshot$default(pluginInstance, null, 1, null));
        PluginListenerDispatcher pluginListenerDispatcher = this.mPluginListenerDispatcher;
        if (pluginListenerDispatcher != null) {
            pluginListenerDispatcher.dispatchPluginDisconnected(pluginInstance.getPluginAction(), pluginInstance.getPrototypePluginClass().getName(), pluginInstance.getPluginInstance());
        }
        pluginInstance.getPluginInstance().onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dispatchPluginFailedLoad(final DiscoverInfo discoverInfo) {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.WARN, false, null, false, discoverInfo.getTraceId(), new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.dispatchPluginFailedLoad.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return PluginInstanceContainerImpl.this.containerTag() + " Dispatch failed to load plugin [" + discoverInfo.getAction() + ']';
            }
        }, 28, null);
        PluginListenerDispatcher pluginListenerDispatcher = this.mPluginListenerDispatcher;
        if (pluginListenerDispatcher == null) {
            return;
        }
        pluginListenerDispatcher.dispatchPluginFailedLoad(discoverInfo.getAction(), discoverInfo.getPrototypePluginClazz());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IRemoteChannelExtension getRemoteChannelChecked() {
        Lazy lazy = (Lazy) this.mRemoteChannelRef.get();
        if (lazy == null) {
            return null;
        }
        if (!lazy.isInitialized()) {
            lazy = null;
        }
        if (lazy == null) {
            return null;
        }
        return (IRemoteChannelExtension) lazy.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PluginInstance loadPluginSafely(final DiscoverInfo discoverInfo, Class cls) {
        try {
            return createPluginOrThrow(discoverInfo, cls);
        } catch (Throwable th) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.WARN, false, th, false, null, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.loadPluginSafely.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return PluginInstanceContainerImpl.this.containerTag() + " Couldn't load plugin: [" + discoverInfo.getServiceComponent().getPackageName() + "] traceId: " + discoverInfo.getTraceId();
                }
            }, 52, null);
            return null;
        }
    }

    private final ClientId newClientId(String str) {
        UUID uuidRandomUUID = UUID.randomUUID();
        uuidRandomUUID.getClass();
        return new ClientId(str, ExtensionsKt.simpleString(uuidRandomUUID));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void scheduleAutoReleaseJob() {
        if (this.mPluginInstancesByAction.isEmpty()) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.scheduleAutoReleaseJob.1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return PluginInstanceContainerImpl.this.containerTag() + " Schedule auto release job, previous job: " + PluginInstanceContainerImpl.this.mAutoTimeoutReleaseJob;
                }
            }, 60, null);
            Job job = this.mAutoTimeoutReleaseJob;
            if (job != null) {
                Job.DefaultImpls.cancel$default(job, null, 1, null);
            }
            this.mAutoTimeoutReleaseJob = BuildersKt__Builders_commonKt.launch$default(this.mContainerCoroutineScope, null, null, new C01932(null), 3, null);
        }
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public void attach(PluginPackage pluginPackage) {
        pluginPackage.getClass();
        this.mPluginPackage = pluginPackage;
        String packageName = this.mContext.getPackageName();
        packageName.getClass();
        this.mClientId = newClientId(packageName);
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public boolean checkAndDisable(String str) {
        str.getClass();
        while (true) {
            boolean z = false;
            for (PluginInstance pluginInstance : CollectionsKt.toMutableList(this.mPluginInstancesByAction.values())) {
                String str2 = pluginInstance.getPluginId().packageName;
                str2.getClass();
                if (StringsKt.startsWith$default(str, str2, false, 2, (Object) null)) {
                    if (z || disable(pluginInstance, 3)) {
                        z = true;
                    }
                }
            }
            return z;
        }
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public Object createPluginInstance(DiscoverInfo discoverInfo, Class cls, Continuation continuation) {
        Object objWithContext = BuildersKt.withContext(Dispatchers.getMain().getImmediate(), new AnonymousClass2(cls, discoverInfo, null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public boolean dependsOn(Plugin plugin, Class cls) {
        plugin.getClass();
        cls.getClass();
        for (PluginInstance pluginInstance : CollectionsKt.toMutableList(this.mPluginInstancesByAction.values())) {
            if (Intrinsics.areEqual(pluginInstance.getPluginInstance().getClass().getName(), plugin.getClass().getName())) {
                return pluginInstance.getVersionInfo().hasClass(cls);
            }
        }
        return false;
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public void destroy(final int i) {
        Context context;
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.destroy.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return PluginInstanceContainerImpl.this.containerTag() + " Dispose for " + PluginInstanceContainer.Companion.disconnectReason2String$core_stubRelease(i);
            }
        }, 60, null);
        this.mPluginEvent.recordEvent(containerTag() + " dispose for " + PluginInstanceContainer.Companion.disconnectReason2String$core_stubRelease(i), ISnapshotAware.DefaultImpls.snapshot$default(this, null, 1, null));
        CoroutineScopeKt.cancel$default(this.mContainerCoroutineScope, null, 1, null);
        Collection collectionValues = this.mPluginInstancesByAction.values();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionValues, 10));
        Iterator it = collectionValues.iterator();
        while (it.hasNext()) {
            arrayList.add(((PluginInstance) it.next()).getPluginContext());
        }
        List list = CollectionsKt.toList(arrayList);
        Collection collectionValues2 = this.mPluginInstancesByAction.values();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionValues2, 10));
        Iterator it2 = collectionValues2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(((PluginInstance) it2.next()).getPrototypePluginClass());
        }
        Iterator it3 = CollectionsKt.toList(arrayList2).iterator();
        while (it3.hasNext()) {
            destroyPluginInstance((Class) it3.next(), i);
        }
        this.mPluginInstancesByAction.clear();
        IRemoteChannelExtension remoteChannelChecked = getRemoteChannelChecked();
        if (remoteChannelChecked != null) {
            if (remoteChannelChecked.isClosed()) {
                remoteChannelChecked = null;
            }
            if (remoteChannelChecked != null) {
                remoteChannelChecked.dispose();
                this.mRemoteChannelRef.set(null);
            }
        }
        this.mPluginListenerDispatcher = null;
        this.mDeathRecipient.clear();
        cancelAutoReleaseJob();
        PluginContext pluginContext = (PluginContext) CollectionsKt.firstOrNull(list);
        if (pluginContext != null) {
            pluginContext.dispose();
        }
        WeakReference weakReference = (WeakReference) this.mPluginContextRef.get();
        if (weakReference != null && (context = (Context) weakReference.get()) != null) {
            PluginPackage pluginPackage = this.mPluginPackage;
            if (pluginPackage == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPluginPackage");
                throw null;
            }
            ClassLoader classLoader = context.getClassLoader();
            classLoader.getClass();
            CleanupResourcesKt.cleanupTemporaryResources(pluginPackage, classLoader);
        }
        WeakReference weakReference2 = (WeakReference) this.mPluginContextRef.get();
        if (weakReference2 != null) {
            weakReference2.clear();
        }
        this.mDisposed = true;
        Message messageObtain = Message.obtain();
        messageObtain.what = 120;
        if (!ExtensionsKt.sendToActivityThread(messageObtain)) {
            Runtime.getRuntime().gc();
            Runtime.getRuntime().runFinalization();
        }
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.destroy.8
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return PluginInstanceContainerImpl.this.containerTag() + " Dispose done for " + PluginInstanceContainer.Companion.disconnectReason2String$core_stubRelease(i);
            }
        }, 62, null);
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public void destroyPluginInstance(Class cls, int i) {
        cls.getClass();
        destroyPluginInstance(cls, i, null);
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public boolean disableAll() {
        List mutableList = CollectionsKt.toMutableList(this.mPluginInstancesByAction.values());
        int size = mutableList.size() - 1;
        if (size < 0) {
            return false;
        }
        int i = 0;
        boolean z = false;
        while (true) {
            int i2 = i + 1;
            z = z || disable((PluginInstance) mutableList.get(i), 4);
            if (i2 > size) {
                return z;
            }
            i = i2;
        }
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        destroy(4);
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public Map getAvailablePlugins() {
        Map map = this.mPluginInstancesByAction;
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
        for (Map.Entry entry : map.entrySet()) {
            linkedHashMap.put(entry.getKey(), ((PluginInstance) entry.getValue()).getPluginInstance());
        }
        return linkedHashMap;
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public Plugin getPlugin(Class cls) {
        cls.getClass();
        PluginInstance pluginInstance = (PluginInstance) this.mPluginInstancesByAction.get(ExtensionsKt.getPluginActionOrThrow(cls));
        if (pluginInstance == null) {
            return null;
        }
        return pluginInstance.getPluginInstance();
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public void keepChannelConnectionAlive(boolean z) {
        IRemoteChannelExtension remoteChannelChecked;
        this.mKeepChannelConnectionAlive = z;
        if (getRemoteChannelChecked() == null || (remoteChannelChecked = getRemoteChannelChecked()) == null) {
            return;
        }
        remoteChannelChecked.keepChannelConnectionAlive(this.mKeepChannelConnectionAlive, 2);
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public void linkToDeath(PluginInstanceContainer.DeathRecipient deathRecipient) {
        deathRecipient.getClass();
        this.mDeathRecipient.add(deathRecipient);
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
    public void onConfigChanged(final Configuration configuration, BitFlag bitFlag) {
        Object objM2707constructorimpl;
        configuration.getClass();
        bitFlag.getClass();
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl.onConfigChanged.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return PluginInstanceContainerImpl.this.containerTag() + " dispatch configuration changed to plugin instance " + configuration;
            }
        }, 60, null);
        Iterator it = this.mPluginInstancesByAction.entrySet().iterator();
        while (it.hasNext()) {
            final PluginInstance pluginInstance = (PluginInstance) ((Map.Entry) it.next()).getValue();
            try {
                Result.Companion companion = Result.Companion;
                if (pluginInstance.getPluginInstance() instanceof ViewTypePlugin) {
                    ((ViewTypePlugin) pluginInstance.getPluginInstance()).onConfigurationChanged(new Configuration(configuration));
                }
                objM2707constructorimpl = Result.m2707constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
            }
            Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
            if (thM2709exceptionOrNullimpl != null) {
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_INSTANCE_CONTAINER, Level.WARN, false, thM2709exceptionOrNullimpl, false, null, new Function0() { // from class: com.motorola.plugin.core.container.PluginInstanceContainerImpl$onConfigChanged$2$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return this.this$0.containerTag() + " dispatch configuration changed failed to plugin [" + pluginInstance.getPluginComponent().flattenToShortString() + ']';
                    }
                }, 52, null);
            }
        }
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
    public void onLowMemory() {
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public void onPluginPackageChanged(PluginPackage pluginPackage) {
        pluginPackage.getClass();
        PluginEvent.DefaultImpls.recordEvent$default(this.mPluginEvent, containerTag() + " package [" + pluginPackage + "] changed", null, 2, null);
    }

    @Override // com.motorola.plugin.core.extension.ConfigurationController.ConfigurationListener
    public void onTrimMemory(int i) {
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        PluginInstanceSnapshot pluginInstanceSnapshot = new PluginInstanceSnapshot(iSnapshot);
        ClientId clientId = this.mClientId;
        if (clientId == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClientId");
            throw null;
        }
        ClientId clientIdCopy = clientId.copy();
        clientIdCopy.getClass();
        pluginInstanceSnapshot.setMyClientId(clientIdCopy);
        PluginPackage pluginPackage = this.mPluginPackage;
        if (pluginPackage == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mPluginPackage");
            throw null;
        }
        pluginInstanceSnapshot.setMyContainerPackage(PluginPackage.copy$default(pluginPackage, null, null, 3, null));
        pluginInstanceSnapshot.setMyPluginInstanceContainerInstance(hashCode());
        Map map = MapsKt.toMap(this.mPluginInstancesByAction);
        pluginInstanceSnapshot.setMySizeOfPlugins(map.size());
        for (Map.Entry entry : map.entrySet()) {
            pluginInstanceSnapshot.getPluginInfoSnapshots().put(entry.getKey(), ISnapshotAware.DefaultImpls.snapshot$default((ISnapshotAware) entry.getValue(), null, 1, null));
        }
        IRemoteChannelExtension remoteChannelChecked = getRemoteChannelChecked();
        pluginInstanceSnapshot.setMyRemoteChannelSnapshot(remoteChannelChecked != null ? ISnapshotAware.DefaultImpls.snapshot$default(remoteChannelChecked, null, 1, null) : null);
        pluginInstanceSnapshot.setMyPendingAutoTimeoutRelease(this.mAutoTimeoutReleaseJob);
        pluginInstanceSnapshot.setMyKeepChannelConnectionAlive(this.mKeepChannelConnectionAlive);
        return pluginInstanceSnapshot;
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer
    public void unlinkToDeath(PluginInstanceContainer.DeathRecipient deathRecipient) {
        deathRecipient.getClass();
        this.mDeathRecipient.remove(deathRecipient);
    }
}
