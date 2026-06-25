package com.motorola.plugin.core.components.impls;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher;
import com.motorola.plugin.core.components.DisplayContext;
import com.motorola.plugin.core.components.PluginEnabler;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.components.PluginInfoManager;
import com.motorola.plugin.core.components.PluginSubscriber;
import com.motorola.plugin.core.components.impls.PluginSubscriberImpl;
import com.motorola.plugin.core.container.PluginInstanceContainer;
import com.motorola.plugin.core.context.PluginId;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.discovery.DiscoverInfo;
import com.motorola.plugin.core.discovery.PluginDiscovery;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.BitFlagKt;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.DisposableContainer;
import com.motorola.plugin.core.misc.DisposableKt;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.core.misc.MarkFlag;
import com.motorola.plugin.core.misc.NotPluginClassException;
import com.motorola.plugin.sdk.Plugin;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import javax.inject.Provider;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;

/* JADX INFO: compiled from: PluginSubscriberImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginSubscriberImpl implements PluginSubscriber, PluginInstanceContainer.DeathRecipient {
    private final Context mContext;
    private final CoroutineScope mCoroutineScope;
    private final DisposableContainer mDisposable;
    private boolean mKeepChannelConnectionAlive;
    private final Map mPICByPluginId;
    private final PluginDiscovery mPluginDiscovery;
    private final PluginEnabler mPluginEnabler;
    private final PluginEvent mPluginEvent;
    private final Lazy mPluginInfoManager;
    private final Provider mPluginInstanceContainerProvider;
    private final Channel mPluginSessionChannel;
    private final Map mPluginSessionsByAction;
    private int mSessionIdGenerator;

    /* JADX INFO: compiled from: PluginSubscriberImpl.kt */
    final class PluginSession {
        public static final Companion Companion = new Companion(null);
        private static final int FLAG_FORCE_PKG = 32;
        private static final int FLAG_STATE_CANCELED = 16;
        private static final int FLAG_STATE_DONE = 4;
        private static final int FLAG_STATE_FAILED = 8;
        private static final int FLAG_STATE_LOADING = 2;
        private static final int FLAG_STATE_PENDING = 1;
        private static final int STATE_MASK = 31;
        private final String action;
        private final int id;
        private String implementorPluginClass;
        private BitFlag myFlag;
        private long myTimeStamp;
        private PluginPackage pluginPackage;
        private final Class prototypePluginClass;

        /* JADX INFO: compiled from: PluginSubscriberImpl.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public PluginSession(int i, String str, Class cls, PluginPackage pluginPackage, String str2) {
            str.getClass();
            cls.getClass();
            this.id = i;
            this.action = str;
            this.prototypePluginClass = cls;
            this.pluginPackage = pluginPackage;
            this.implementorPluginClass = str2;
            BitFlag bitFlagWrap = BitFlag.Companion.wrap(1);
            this.myFlag = bitFlagWrap;
            if (this.pluginPackage != null) {
                BitFlagKt.plusAssign(bitFlagWrap, 32);
            }
        }

        public /* synthetic */ PluginSession(int i, String str, Class cls, PluginPackage pluginPackage, String str2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, str, cls, (i2 & 8) != 0 ? null : pluginPackage, (i2 & 16) != 0 ? null : str2);
        }

        private PluginSession(PluginSession pluginSession) {
            this(pluginSession.id, pluginSession.action, pluginSession.prototypePluginClass, pluginSession.pluginPackage, pluginSession.implementorPluginClass);
            this.myFlag = pluginSession.myFlag;
            this.myTimeStamp = pluginSession.myTimeStamp;
        }

        public static /* synthetic */ PluginSession copy$default(PluginSession pluginSession, int i, String str, Class cls, PluginPackage pluginPackage, String str2, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = pluginSession.id;
            }
            if ((i2 & 2) != 0) {
                str = pluginSession.action;
            }
            if ((i2 & 4) != 0) {
                cls = pluginSession.prototypePluginClass;
            }
            if ((i2 & 8) != 0) {
                pluginPackage = pluginSession.pluginPackage;
            }
            if ((i2 & 16) != 0) {
                str2 = pluginSession.implementorPluginClass;
            }
            String str3 = str2;
            Class cls2 = cls;
            return pluginSession.copy(i, str, cls2, pluginPackage, str3);
        }

        private final void moveToState(int i) {
            this.myFlag.add(i, STATE_MASK);
            this.myTimeStamp = System.currentTimeMillis();
        }

        public final int component1() {
            return this.id;
        }

        public final String component2() {
            return this.action;
        }

        public final Class component3() {
            return this.prototypePluginClass;
        }

        public final PluginPackage component4() {
            return this.pluginPackage;
        }

        public final String component5() {
            return this.implementorPluginClass;
        }

        public final PluginSession copy() {
            return new PluginSession(this);
        }

        public final PluginSession copy(int i, String str, Class cls, PluginPackage pluginPackage, String str2) {
            str.getClass();
            cls.getClass();
            return new PluginSession(i, str, cls, pluginPackage, str2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PluginSession)) {
                return false;
            }
            PluginSession pluginSession = (PluginSession) obj;
            return this.id == pluginSession.id && Intrinsics.areEqual(this.action, pluginSession.action) && Intrinsics.areEqual(this.prototypePluginClass, pluginSession.prototypePluginClass) && Intrinsics.areEqual(this.pluginPackage, pluginSession.pluginPackage) && Intrinsics.areEqual(this.implementorPluginClass, pluginSession.implementorPluginClass);
        }

        public final String getAction() {
            return this.action;
        }

        public final int getId() {
            return this.id;
        }

        public final String getImplementorPluginClass() {
            return this.implementorPluginClass;
        }

        public final long getLastStateChangedDuration() {
            return System.currentTimeMillis() - getTimestamp();
        }

        public final PluginPackage getPluginPackage() {
            return this.pluginPackage;
        }

        public final Class getPrototypePluginClass() {
            return this.prototypePluginClass;
        }

        public final long getTimestamp() {
            return this.myTimeStamp;
        }

        public int hashCode() {
            int iHashCode = ((((Integer.hashCode(this.id) * STATE_MASK) + this.action.hashCode()) * STATE_MASK) + this.prototypePluginClass.hashCode()) * STATE_MASK;
            PluginPackage pluginPackage = this.pluginPackage;
            int iHashCode2 = (iHashCode + (pluginPackage == null ? 0 : pluginPackage.hashCode())) * STATE_MASK;
            String str = this.implementorPluginClass;
            return iHashCode2 + (str != null ? str.hashCode() : 0);
        }

        public final boolean isCanceled() {
            return BitFlagKt.contains(this.myFlag, 16);
        }

        public final boolean isDone() {
            return BitFlagKt.contains(this.myFlag, 4);
        }

        public final boolean isFailed() {
            return BitFlagKt.contains(this.myFlag, 8);
        }

        public final boolean isLoading() {
            return BitFlagKt.contains(this.myFlag, 2);
        }

        public final boolean isPending() {
            return BitFlagKt.contains(this.myFlag, 1);
        }

        public final void moveToCanceledState() {
            moveToPendingState();
            moveToState(16);
        }

        public final void moveToDoneState() {
            moveToState(4);
        }

        public final void moveToFailedState() {
            moveToState(8);
        }

        public final void moveToLoadingState() {
            moveToState(2);
            this.implementorPluginClass = null;
        }

        public final void moveToPendingState() {
            moveToState(1);
            if (!BitFlagKt.contains(this.myFlag, 32)) {
                this.pluginPackage = null;
            }
            this.implementorPluginClass = null;
        }

        public final void setImplementorPluginClass(String str) {
            this.implementorPluginClass = str;
        }

        public final void setPluginPackage(PluginPackage pluginPackage) {
            this.pluginPackage = pluginPackage;
        }

        public final String stateToString() {
            return Intrinsics.stringPlus("state: ", isPending() ? "pending" : isLoading() ? "loading" : isDone() ? "done" : isFailed() ? "failed" : isCanceled() ? "canceled" : "-");
        }

        public String toString() {
            return "Session(id: " + this.id + ", action: " + this.action + ", proto: " + ((Object) this.prototypePluginClass.getName()) + ", impl: " + ((Object) this.implementorPluginClass) + ", package: " + this.pluginPackage + ", " + stateToString() + ')';
        }
    }

    /* JADX INFO: compiled from: PluginSubscriberImpl.kt */
    final class PluginSubscriberSnapshot extends AbstractSnapshot {
        private Map myAvailablePlugins;
        private boolean myKeepChannelConnectionAlive;
        private final Map myPluginInstanceContainerSnapshots;
        private Map myPluginSessionSnapshots;
        private int myPluginSubscriberInstance;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PluginSubscriberSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
            this.myKeepChannelConnectionAlive = true;
            this.myPluginSessionSnapshots = new LinkedHashMap();
            this.myAvailablePlugins = new LinkedHashMap();
            this.myPluginInstanceContainerSnapshots = new LinkedHashMap();
        }

        public final Map getMyAvailablePlugins() {
            return this.myAvailablePlugins;
        }

        public final boolean getMyKeepChannelConnectionAlive() {
            return this.myKeepChannelConnectionAlive;
        }

        public final Map getMyPluginInstanceContainerSnapshots() {
            return this.myPluginInstanceContainerSnapshots;
        }

        public final Map getMyPluginSessionSnapshots() {
            return this.myPluginSessionSnapshots;
        }

        public final int getMyPluginSubscriberInstance() {
            return this.myPluginSubscriberInstance;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("PluginSubscriber", getMyPluginSubscriberInstance()).newLine();
            iPrinter.increaseIndent();
            iPrinter.printPair("keepChannelConnectionAlive", Boolean.valueOf(getMyKeepChannelConnectionAlive())).newLine();
            iPrinter.printSingle("Sessions:");
            iPrinter.printPair("size", Integer.valueOf(getMyPluginSessionSnapshots().size()));
            iPrinter.newLine();
            for (Map.Entry entry : getMyPluginSessionSnapshots().entrySet()) {
                String str = (String) entry.getKey();
                PluginSession pluginSession = (PluginSession) entry.getValue();
                iPrinter.increaseIndent();
                iPrinter.printPair("action", str).newLine();
                iPrinter.printSingle(pluginSession.stateToString()).newLine();
                iPrinter.increaseIndent();
                iPrinter.printPair("packageName", pluginSession.getPluginPackage());
                iPrinter.printPair("prototypeClass", pluginSession.getPrototypePluginClass().getName());
                iPrinter.printPair("timestamp", ExtensionsKt.timestampWithZone(pluginSession.getTimestamp()));
                iPrinter.decreaseIndent();
                iPrinter.newLine();
                iPrinter.decreaseIndent();
            }
            iPrinter.printSingle("AvailablePlugins: ");
            iPrinter.printPair("size", Integer.valueOf(getMyAvailablePlugins().size()));
            iPrinter.newLine();
            for (Map.Entry entry2 : getMyAvailablePlugins().entrySet()) {
                iPrinter.increaseIndent();
                iPrinter.printPair("action", entry2.getKey()).printPair("plugin", entry2.getValue()).newLine();
                iPrinter.decreaseIndent();
            }
            iPrinter.printSingle("Containers:");
            iPrinter.printPair("size", Integer.valueOf(getMyPluginInstanceContainerSnapshots().size()));
            iPrinter.newLine();
            for (Map.Entry entry3 : getMyPluginInstanceContainerSnapshots().entrySet()) {
                String str2 = (String) entry3.getKey();
                ISnapshot iSnapshot = (ISnapshot) entry3.getValue();
                iPrinter.increaseIndent();
                iPrinter.printPair("package", str2).newLine();
                iPrinter.increaseIndent();
                iSnapshot.onSnapshot(iPrinter);
                iPrinter.decreaseIndent();
                iPrinter.decreaseIndent();
            }
            iPrinter.decreaseIndent();
            iPrinter.newLine();
        }

        public final void setMyAvailablePlugins(Map map) {
            map.getClass();
            this.myAvailablePlugins = map;
        }

        public final void setMyKeepChannelConnectionAlive(boolean z) {
            this.myKeepChannelConnectionAlive = z;
        }

        public final void setMyPluginSessionSnapshots(Map map) {
            map.getClass();
            this.myPluginSessionSnapshots = map;
        }

        public final void setMyPluginSubscriberInstance(int i) {
            this.myPluginSubscriberInstance = i;
        }
    }

    /* JADX INFO: compiled from: PluginSubscriberImpl.kt */
    final class SessionWithTraceId {
        private final PluginSession session;
        private final String traceId;

        public SessionWithTraceId(PluginSession pluginSession) {
            pluginSession.getClass();
            this.session = pluginSession;
            UUID uuidRandomUUID = UUID.randomUUID();
            uuidRandomUUID.getClass();
            this.traceId = StringsKt.substring(ExtensionsKt.simpleString(uuidRandomUUID), new IntRange(0, 6));
        }

        public static /* synthetic */ SessionWithTraceId copy$default(SessionWithTraceId sessionWithTraceId, PluginSession pluginSession, int i, Object obj) {
            if ((i & 1) != 0) {
                pluginSession = sessionWithTraceId.session;
            }
            return sessionWithTraceId.copy(pluginSession);
        }

        public final PluginSession component1() {
            return this.session;
        }

        public final SessionWithTraceId copy(PluginSession pluginSession) {
            pluginSession.getClass();
            return new SessionWithTraceId(pluginSession);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof SessionWithTraceId) && Intrinsics.areEqual(this.session, ((SessionWithTraceId) obj).session);
        }

        public final PluginSession getSession() {
            return this.session;
        }

        public final String getTraceId() {
            return this.traceId;
        }

        public int hashCode() {
            return this.session.hashCode();
        }

        public String toString() {
            return "SessionWithTraceId(session=" + this.session + ')';
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$initialize$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PluginSubscriberImpl.kt */
    final class C01741 extends SuspendLambda implements Function2 {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;

        C01741(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PluginSubscriberImpl.this.new C01741(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01741) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0064  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00aa  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00bb  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x00d5  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r29) {
            /*
                Method dump skipped, instruction units count: 237
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.C01741.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$scanPluginForSession$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PluginSubscriberImpl.kt */
    final class C01761 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C01761(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PluginSubscriberImpl.this.scanPluginForSession(null, this);
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$scanPluginForSession$3, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PluginSubscriberImpl.kt */
    final class C01783 extends SuspendLambda implements Function2 {
        final /* synthetic */ PluginSession $session;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01783(PluginSession pluginSession, Continuation continuation) {
            super(2, continuation);
            this.$session = pluginSession;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01783(this.$session, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01783) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boxing.boxBoolean(this.$session.isCanceled() || this.$session.isDone());
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$scanPluginForSession$5, reason: invalid class name */
    /* JADX INFO: compiled from: PluginSubscriberImpl.kt */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        final /* synthetic */ DiscoverInfo $discoverInfo;
        final /* synthetic */ PluginSession $session;
        final /* synthetic */ SessionWithTraceId $sessionWithId;
        Object L$0;
        int label;
        final /* synthetic */ PluginSubscriberImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass5(DiscoverInfo discoverInfo, PluginSubscriberImpl pluginSubscriberImpl, PluginSession pluginSession, SessionWithTraceId sessionWithTraceId, Continuation continuation) {
            super(2, continuation);
            this.$discoverInfo = discoverInfo;
            this.this$0 = pluginSubscriberImpl;
            this.$session = pluginSession;
            this.$sessionWithId = sessionWithTraceId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final PluginInstanceContainer m2216invokeSuspend$lambda0(PluginSubscriberImpl pluginSubscriberImpl, DiscoverInfo discoverInfo, PluginId pluginId) {
            return pluginSubscriberImpl.forkPluginInstanceContainer(discoverInfo.getAction(), discoverInfo.getPluginPackage(), discoverInfo.getTraceId());
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass5(this.$discoverInfo, this.this$0, this.$session, this.$sessionWithId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass5) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            PluginInstanceContainer pluginInstanceContainer;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.$discoverInfo == null) {
                    PluginSession pluginSession = (PluginSession) this.this$0.mPluginSessionsByAction.get(this.$session.getAction());
                    if (pluginSession != null) {
                        pluginSession.moveToFailedState();
                    }
                    PluginEvent.DefaultImpls.recordEvent$default(this.this$0.mPluginEvent, "0 results of discovery plugin for " + this.$session + ", traceId: " + this.$sessionWithId.getTraceId(), null, 2, null);
                    Level level = Level.WARN;
                    String traceId = this.$sessionWithId.getTraceId();
                    final PluginSession pluginSession2 = this.$session;
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, level, false, null, false, traceId, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.scanPluginForSession.5.1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return "No plugin of [" + pluginSession2.getAction() + "] is currently available for " + pluginSession2;
                        }
                    }, 28, null);
                    return Unit.INSTANCE;
                }
                if (this.$session.isCanceled()) {
                    Level level2 = Level.WARN;
                    String traceId2 = this.$discoverInfo.getTraceId();
                    final PluginSession pluginSession3 = this.$session;
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, level2, false, null, false, traceId2, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.scanPluginForSession.5.4
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return Intrinsics.stringPlus("Create plugin session abort after discovery for ", pluginSession3);
                        }
                    }, 28, null);
                    return Unit.INSTANCE;
                }
                Map map = this.this$0.mPICByPluginId;
                PluginId pluginId = this.$discoverInfo.getPluginPackage().getPluginId();
                final PluginSubscriberImpl pluginSubscriberImpl = this.this$0;
                final DiscoverInfo discoverInfo = this.$discoverInfo;
                Object objComputeIfAbsent = map.computeIfAbsent(pluginId, new Function() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$scanPluginForSession$5$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        return PluginSubscriberImpl.AnonymousClass5.m2216invokeSuspend$lambda0(pluginSubscriberImpl, discoverInfo, (PluginId) obj2);
                    }
                });
                objComputeIfAbsent.getClass();
                PluginInstanceContainer pluginInstanceContainer2 = (PluginInstanceContainer) objComputeIfAbsent;
                DiscoverInfo discoverInfo2 = this.$discoverInfo;
                Class prototypePluginClass = this.$session.getPrototypePluginClass();
                this.L$0 = pluginInstanceContainer2;
                this.label = 1;
                if (pluginInstanceContainer2.createPluginInstance(discoverInfo2, prototypePluginClass, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                pluginInstanceContainer = pluginInstanceContainer2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                pluginInstanceContainer = (PluginInstanceContainer) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            if (this.$session.isCanceled()) {
                pluginInstanceContainer.destroyPluginInstance(this.$session.getPrototypePluginClass(), 7);
                Level level3 = Level.WARN;
                String traceId3 = this.$discoverInfo.getTraceId();
                final PluginSession pluginSession4 = this.$session;
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, level3, false, null, false, traceId3, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.scanPluginForSession.5.3
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "Created plugin instance for " + pluginSession4 + ", but session canceled";
                    }
                }, 28, null);
                return Unit.INSTANCE;
            }
            PluginSession pluginSession5 = (PluginSession) this.this$0.mPluginSessionsByAction.get(this.$discoverInfo.getAction());
            if (pluginSession5 == null) {
                return null;
            }
            DiscoverInfo discoverInfo3 = this.$discoverInfo;
            final PluginSession pluginSession6 = this.$session;
            pluginSession5.setPluginPackage(PluginPackage.copy$default(discoverInfo3.getPluginPackage(), null, null, 3, null));
            pluginSession5.setImplementorPluginClass(discoverInfo3.getImplementorClazz());
            pluginSession5.moveToDoneState();
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.INFO, false, null, false, discoverInfo3.getTraceId(), new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$scanPluginForSession$5$2$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return Intrinsics.stringPlus("Created plugin instance for ", pluginSession6);
                }
            }, 28, null);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$subscribePlugin$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PluginSubscriberImpl.kt */
    final class C01791 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $action;
        final /* synthetic */ PluginPackage $pluginPackage;
        final /* synthetic */ Class $prototypePluginClass;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01791(String str, Class cls, PluginPackage pluginPackage, Continuation continuation) {
            super(2, continuation);
            this.$action = str;
            this.$prototypePluginClass = cls;
            this.$pluginPackage = pluginPackage;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PluginSubscriberImpl.this.new C01791(this.$action, this.$prototypePluginClass, this.$pluginPackage, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01791) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final PluginSession pluginSession = (PluginSession) PluginSubscriberImpl.this.mPluginSessionsByAction.get(this.$action);
                if (pluginSession == null) {
                    Level level = Level.INFO;
                    final Class cls = this.$prototypePluginClass;
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, level, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.subscribePlugin.1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return Intrinsics.stringPlus("subscribe plugin ", cls.getName());
                        }
                    }, 56, null);
                    PluginEvent.DefaultImpls.recordEvent$default(PluginSubscriberImpl.this.mPluginEvent, Intrinsics.stringPlus("subscribe plugin ", this.$prototypePluginClass.getName()), null, 2, null);
                    final PluginSession pluginSession2 = new PluginSession(PluginSubscriberImpl.this.getMSessionIdGenerator(), this.$action, this.$prototypePluginClass, this.$pluginPackage, null, 16, null);
                    PluginSubscriberImpl pluginSubscriberImpl = PluginSubscriberImpl.this;
                    String str = this.$action;
                    pluginSession2.moveToPendingState();
                    pluginSubscriberImpl.mPluginSessionsByAction.put(str, pluginSession2);
                    SessionWithTraceId sessionWithTraceId = new SessionWithTraceId(pluginSession2);
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, level, false, null, false, sessionWithTraceId.getTraceId(), new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$subscribePlugin$1$2$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return "Offering new plugin session for " + pluginSession2 + " due to subscribe";
                        }
                    }, 28, null);
                    Channel channel = pluginSubscriberImpl.mPluginSessionChannel;
                    this.label = 1;
                    if (ExtensionsKt.fastSend(channel, sessionWithTraceId, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else if (!Intrinsics.areEqual(this.$prototypePluginClass.getName(), pluginSession.getPrototypePluginClass().getName())) {
                    Level level2 = Level.ERROR;
                    final String str2 = this.$action;
                    final Class cls2 = this.$prototypePluginClass;
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, level2, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.subscribePlugin.1.3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return "Duplicate action [" + str2 + "] between class [" + ((Object) cls2.getName()) + "] and [" + ((Object) pluginSession.getPrototypePluginClass().getName()) + ']';
                        }
                    }, 60, null);
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

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$unsubscribePlugin$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PluginSubscriberImpl.kt */
    final class C01801 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $action;
        final /* synthetic */ Class $prototypePluginClass;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01801(String str, Class cls, Continuation continuation) {
            super(2, continuation);
            this.$action = str;
            this.$prototypePluginClass = cls;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PluginSubscriberImpl.this.new C01801(this.$action, this.$prototypePluginClass, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01801) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            PluginSession pluginSession = (PluginSession) PluginSubscriberImpl.this.mPluginSessionsByAction.get(this.$action);
            if (pluginSession != null) {
                PluginSubscriberImpl pluginSubscriberImpl = PluginSubscriberImpl.this;
                String str = this.$action;
                final Class cls = this.$prototypePluginClass;
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, null, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$unsubscribePlugin$1$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return Intrinsics.stringPlus("unsubscribe plugin ", cls.getName());
                    }
                }, 58, null);
                if (pluginSession.isLoading() || pluginSession.isPending()) {
                    pluginSession.moveToCanceledState();
                }
                pluginSubscriberImpl.mPluginSessionsByAction.remove(str);
                PluginEvent.DefaultImpls.recordEvent$default(pluginSubscriberImpl.mPluginEvent, Intrinsics.stringPlus("unsubscribePlugin plugin ", cls), null, 2, null);
                Map map = pluginSubscriberImpl.mPICByPluginId;
                PluginPackage pluginPackage = pluginSession.getPluginPackage();
                PluginInstanceContainer pluginInstanceContainer = (PluginInstanceContainer) map.get(pluginPackage != null ? pluginPackage.getPluginId() : null);
                if (pluginInstanceContainer != null) {
                    pluginInstanceContainer.destroyPluginInstance(cls, 5);
                }
            }
            return Unit.INSTANCE;
        }
    }

    public PluginSubscriberImpl(@DisplayContext Context context, PluginEnabler pluginEnabler, PluginEvent pluginEvent, DisposableContainer disposableContainer, PluginDiscovery pluginDiscovery, Lazy lazy, Provider provider) {
        context.getClass();
        pluginEnabler.getClass();
        pluginEvent.getClass();
        disposableContainer.getClass();
        pluginDiscovery.getClass();
        lazy.getClass();
        provider.getClass();
        this.mContext = context;
        this.mPluginEnabler = pluginEnabler;
        this.mPluginEvent = pluginEvent;
        this.mDisposable = disposableContainer;
        this.mPluginDiscovery = pluginDiscovery;
        this.mPluginInfoManager = lazy;
        this.mPluginInstanceContainerProvider = provider;
        this.mPluginSessionsByAction = new HashMap();
        Channel channelChannel$default = ChannelKt.Channel$default(Integer.MAX_VALUE, null, null, 6, null);
        this.mPluginSessionChannel = channelChannel$default;
        this.mPICByPluginId = new HashMap();
        this.mKeepChannelConnectionAlive = true;
        CoroutineScope coroutineScopeMainScope = CoroutineScopeKt.MainScope();
        this.mCoroutineScope = coroutineScopeMainScope;
        DisposableKt.add(disposableContainer, coroutineScopeMainScope);
        DisposableKt.add(disposableContainer, channelChannel$default);
        disposableContainer.add(new Disposable() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$$ExternalSyntheticLambda0
            @Override // com.motorola.plugin.core.misc.Disposable
            public final void dispose() {
                PluginSubscriberImpl.m2212_init_$lambda0(this.f$0);
            }
        });
        disposableContainer.add(new Disposable() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$$ExternalSyntheticLambda1
            @Override // com.motorola.plugin.core.misc.Disposable
            public final void dispose() {
                PluginSubscriberImpl.m2213_init_$lambda2(this.f$0);
            }
        });
        disposableContainer.add(new Disposable() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl$$ExternalSyntheticLambda2
            @Override // com.motorola.plugin.core.misc.Disposable
            public final void dispose() {
                PluginSubscriberImpl.m2214_init_$lambda3(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-0, reason: not valid java name */
    public static final void m2212_init_$lambda0(PluginSubscriberImpl pluginSubscriberImpl) {
        pluginSubscriberImpl.getClass();
        pluginSubscriberImpl.mPluginSessionsByAction.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-2, reason: not valid java name */
    public static final void m2213_init_$lambda2(PluginSubscriberImpl pluginSubscriberImpl) {
        pluginSubscriberImpl.getClass();
        Iterator it = pluginSubscriberImpl.mPICByPluginId.values().iterator();
        while (it.hasNext()) {
            ((PluginInstanceContainer) it.next()).dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-3, reason: not valid java name */
    public static final void m2214_init_$lambda3(PluginSubscriberImpl pluginSubscriberImpl) {
        pluginSubscriberImpl.getClass();
        pluginSubscriberImpl.mPICByPluginId.clear();
    }

    private final void destroyPluginInstanceContainer(final PluginPackage pluginPackage, PluginInstanceContainer pluginInstanceContainer, final int i) {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.INFO, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.destroyPluginInstanceContainer.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "Destroy plugin container for " + pluginPackage + " with reason " + PluginInstanceContainer.Companion.disconnectReason2String$core_stubRelease(i);
            }
        }, 56, null);
        pluginInstanceContainer.destroy(i);
        pluginInstanceContainer.unlinkToDeath(this);
        this.mDisposable.remove(pluginInstanceContainer);
    }

    private final void enablePluginIfNeeded(PluginSession pluginSession, MarkFlag markFlag, PluginPackage pluginPackage, final ComponentName componentName) {
        if (componentName == null) {
            String implementorPluginClass = pluginSession.getImplementorPluginClass();
            componentName = implementorPluginClass == null ? null : ComponentName.createRelative(pluginPackage.getPluginId().getId(), implementorPluginClass);
        }
        if (!markFlag.updated() || componentName == null) {
            return;
        }
        int disableReason = this.mPluginEnabler.getDisableReason(componentName);
        if (disableReason == 2 || disableReason == 3 || disableReason == 4) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.enablePluginIfNeeded.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return Intrinsics.stringPlus("Re-enabling previously disabled plugin that has been updated: ", componentName.flattenToShortString());
                }
            }, 60, null);
            this.mPluginEnabler.setEnabled(componentName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PluginInstanceContainer forkPluginInstanceContainer(final String str, final PluginPackage pluginPackage, String str2) {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.INFO, true, null, false, str2, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.forkPluginInstanceContainer.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "Using " + pluginPackage + " to create new plugin container for " + str;
            }
        }, 24, null);
        PluginEvent.DefaultImpls.recordEvent$default(this.mPluginEvent, "create new plugin container using " + pluginPackage + " for " + str + ", traceId: " + str2, null, 2, null);
        Object obj = this.mPluginInstanceContainerProvider.get();
        PluginInstanceContainer pluginInstanceContainer = (PluginInstanceContainer) obj;
        pluginInstanceContainer.attach(pluginPackage);
        pluginInstanceContainer.linkToDeath(this);
        pluginInstanceContainer.keepChannelConnectionAlive(this.mKeepChannelConnectionAlive);
        this.mDisposable.add(pluginInstanceContainer);
        obj.getClass();
        return (PluginInstanceContainer) obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getMSessionIdGenerator() {
        int i = this.mSessionIdGenerator;
        if (i > 100) {
            return 0;
        }
        this.mSessionIdGenerator = i + 1;
        return i;
    }

    private final void rescanPluginInNeeded(final PluginSession pluginSession, MarkFlag markFlag, final PluginPackage pluginPackage) {
        final String strStateToString = pluginSession.stateToString();
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        boolean z = false;
        boolean z2 = pluginSession.isFailed() || pluginSession.isPending();
        ref$BooleanRef.element = z2;
        if (z2 || (markFlag.updated() && pluginSession.isDone() && Intrinsics.areEqual(pluginSession.getPluginPackage(), pluginPackage))) {
            z = true;
        }
        ref$BooleanRef.element = z;
        if (!z && pluginSession.isLoading() && pluginSession.getLastStateChangedDuration() > TimeUnit.SECONDS.toMillis(10L)) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.rescanPluginInNeeded.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "loading plugin for a very long time for id = " + pluginPackage + " request = " + pluginSession + ' ';
                }
            }, 60, null);
            pluginSession.moveToFailedState();
            ref$BooleanRef.element = true;
        }
        Level level = Level.INFO;
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.rescanPluginInNeeded.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "rescan = " + ref$BooleanRef.element + ", session = " + pluginSession + ", lastState = " + strStateToString + " due to pkg " + pluginPackage + " change";
            }
        }, 60, null);
        if (ref$BooleanRef.element) {
            if (pluginSession.isDone()) {
                pluginSession.moveToPendingState();
            }
            SessionWithTraceId sessionWithTraceId = new SessionWithTraceId(pluginSession);
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, level, false, null, false, sessionWithTraceId.getTraceId(), new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.rescanPluginInNeeded.3
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "Offering new plugin session for " + pluginSession + " due to rescan";
                }
            }, 28, null);
            this.mPluginSessionChannel.offer(sessionWithTraceId);
            PluginEvent.DefaultImpls.recordEvent$default(this.mPluginEvent, "offer discovery plugin request = " + pluginSession + " for package " + pluginPackage + ", traceId: " + sessionWithTraceId.getTraceId(), null, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object scanPluginForSession(com.motorola.plugin.core.components.impls.PluginSubscriberImpl.SessionWithTraceId r22, kotlin.coroutines.Continuation r23) {
        /*
            Method dump skipped, instruction units count: 269
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.scanPluginForSession(com.motorola.plugin.core.components.impls.PluginSubscriberImpl$SessionWithTraceId, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public boolean dependsOn(Plugin plugin, Class cls) {
        plugin.getClass();
        cls.getClass();
        Collection collectionValues = this.mPICByPluginId.values();
        if ((collectionValues instanceof Collection) && collectionValues.isEmpty()) {
            return false;
        }
        Iterator it = collectionValues.iterator();
        while (it.hasNext()) {
            if (((PluginInstanceContainer) it.next()).dependsOn(plugin, cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        this.mDisposable.dispose();
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public Map getAvailablePlugins() {
        Collection collectionValues = this.mPICByPluginId.values();
        ArrayList arrayList = new ArrayList();
        Iterator it = collectionValues.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((PluginInstanceContainer) it.next()).getAvailablePlugins().entrySet());
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList, 10)), 16));
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Map.Entry entry = (Map.Entry) obj;
            Pair pair = TuplesKt.to(entry.getKey(), entry.getValue());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return linkedHashMap;
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public Plugin getPlugin(final Class cls, PluginPackage pluginPackage) {
        cls.getClass();
        if (pluginPackage == null) {
            return (Plugin) SequencesKt.firstOrNull(SequencesKt.mapNotNull(CollectionsKt.asSequence(this.mPICByPluginId.values()), new Function1() { // from class: com.motorola.plugin.core.components.impls.PluginSubscriberImpl.getPlugin.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Plugin invoke(PluginInstanceContainer pluginInstanceContainer) {
                    pluginInstanceContainer.getClass();
                    return pluginInstanceContainer.getPlugin(cls);
                }
            }));
        }
        PluginInstanceContainer pluginInstanceContainer = (PluginInstanceContainer) this.mPICByPluginId.get(pluginPackage.getPluginId());
        if (pluginInstanceContainer == null) {
            return null;
        }
        return pluginInstanceContainer.getPlugin(cls);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public Plugin getPlugin(String str, PluginPackage pluginPackage) {
        Object objM2707constructorimpl;
        str.getClass();
        ClassLoader classLoader = PluginSubscriberImpl.class.getClassLoader();
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(Class.forName(str, false, classLoader).asSubclass(Plugin.class));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl != null) {
            throw new NotPluginClassException(str, thM2709exceptionOrNullimpl);
        }
        objM2707constructorimpl.getClass();
        return getPlugin((Class) objM2707constructorimpl, pluginPackage);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriber
    public void initialize() {
        BuildersKt__Builders_commonKt.launch$default(this.mCoroutineScope, Dispatchers.getDefault(), null, new C01741(null), 2, null);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public boolean isPluginSubscribed(Class cls, PluginPackage pluginPackage) {
        cls.getClass();
        return this.mPluginSessionsByAction.get(ExtensionsKt.getPluginActionOrThrow(cls)) != null;
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public boolean isPluginSubscribed(String str, PluginPackage pluginPackage) {
        Object objM2707constructorimpl;
        str.getClass();
        ClassLoader classLoader = PluginSubscriberImpl.class.getClassLoader();
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(Class.forName(str, false, classLoader).asSubclass(Plugin.class));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl != null) {
            throw new NotPluginClassException(str, thM2709exceptionOrNullimpl);
        }
        objM2707constructorimpl.getClass();
        return isPluginSubscribed((Class) objM2707constructorimpl, pluginPackage);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void keepChannelConnectionAlive(boolean z) {
        if (this.mKeepChannelConnectionAlive != z) {
            this.mKeepChannelConnectionAlive = z;
            Iterator it = this.mPICByPluginId.values().iterator();
            while (it.hasNext()) {
                ((PluginInstanceContainer) it.next()).keepChannelConnectionAlive(z);
            }
        }
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public boolean launchPlugin(Intent intent, PluginPackage pluginPackage) {
        intent.getClass();
        return ((PluginInfoManager) this.mPluginInfoManager.get()).launchPlugin(intent, pluginPackage);
    }

    @Override // com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher.ConfigurationChainedListener
    public void onConfigChanged(Configuration configuration, BitFlag bitFlag, ConfigurationListenerChainedDispatcher.ChainedDispatcher chainedDispatcher) {
        configuration.getClass();
        bitFlag.getClass();
        chainedDispatcher.getClass();
        Iterator it = this.mPICByPluginId.values().iterator();
        while (it.hasNext()) {
            ((PluginInstanceContainer) it.next()).onConfigChanged(configuration, bitFlag);
        }
        chainedDispatcher.processNextConfigChanged(configuration, bitFlag);
    }

    @Override // com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher.ConfigurationChainedListener
    public void onLowMemory() {
        Iterator it = this.mPICByPluginId.values().iterator();
        while (it.hasNext()) {
            ((PluginInstanceContainer) it.next()).onLowMemory();
        }
    }

    @Override // com.motorola.plugin.core.container.PluginInstanceContainer.DeathRecipient
    public void onPluginInstanceContainerDied(PluginPackage pluginPackage, int i) {
        pluginPackage.getClass();
        PluginInstanceContainer pluginInstanceContainer = (PluginInstanceContainer) this.mPICByPluginId.remove(pluginPackage.getPluginId());
        if (pluginInstanceContainer == null) {
            return;
        }
        destroyPluginInstanceContainer(pluginPackage, pluginInstanceContainer, i);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriber
    public void onPluginPackageChanged(PluginPackage pluginPackage, ComponentName componentName, MarkFlag markFlag) {
        pluginPackage.getClass();
        markFlag.getClass();
        Iterator it = this.mPluginSessionsByAction.entrySet().iterator();
        while (it.hasNext()) {
            PluginSession pluginSession = (PluginSession) ((Map.Entry) it.next()).getValue();
            if (!markFlag.deleted()) {
                enablePluginIfNeeded(pluginSession, markFlag, pluginPackage, componentName);
                rescanPluginInNeeded(pluginSession, markFlag, pluginPackage);
            } else if (Intrinsics.areEqual(pluginSession.getPluginPackage(), pluginPackage)) {
                pluginSession.moveToPendingState();
            }
        }
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriber
    public void onPluginPackageRemoved(PluginPackage pluginPackage, ComponentName componentName) {
        pluginPackage.getClass();
        PluginInstanceContainer pluginInstanceContainer = (PluginInstanceContainer) this.mPICByPluginId.remove(pluginPackage.getPluginId());
        if (pluginInstanceContainer == null) {
            return;
        }
        destroyPluginInstanceContainer(pluginPackage, pluginInstanceContainer, 2);
    }

    @Override // com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher.ConfigurationChainedListener
    public void onTrimMemory(int i) {
        Iterator it = this.mPICByPluginId.values().iterator();
        while (it.hasNext()) {
            ((PluginInstanceContainer) it.next()).onTrimMemory(i);
        }
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        PluginSubscriberSnapshot pluginSubscriberSnapshot = new PluginSubscriberSnapshot(iSnapshot);
        pluginSubscriberSnapshot.setMyPluginSubscriberInstance(hashCode());
        pluginSubscriberSnapshot.setMyKeepChannelConnectionAlive(this.mKeepChannelConnectionAlive);
        for (Map.Entry entry : this.mPluginSessionsByAction.entrySet()) {
            pluginSubscriberSnapshot.getMyPluginSessionSnapshots().put((String) entry.getKey(), ((PluginSession) entry.getValue()).copy());
        }
        for (Map.Entry entry2 : getAvailablePlugins().entrySet()) {
            pluginSubscriberSnapshot.getMyAvailablePlugins().put(entry2.getKey(), ((Object) entry2.getValue().getClass().getName()) + '@' + ExtensionsKt.hashCodeHex(entry2.getValue()));
        }
        for (Map.Entry entry3 : this.mPICByPluginId.entrySet()) {
            pluginSubscriberSnapshot.getMyPluginInstanceContainerSnapshots().put(((PluginId) entry3.getKey()).getId(), ISnapshotAware.DefaultImpls.snapshot$default((PluginInstanceContainer) entry3.getValue(), null, 1, null));
        }
        return pluginSubscriberSnapshot;
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void subscribePlugin(Class cls, PluginPackage pluginPackage) {
        cls.getClass();
        BuildersKt__Builders_commonKt.launch$default(this.mCoroutineScope, null, null, new C01791(ExtensionsKt.getPluginActionOrThrow(cls), cls, pluginPackage, null), 3, null);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void subscribePlugin(String str, PluginPackage pluginPackage) {
        Object objM2707constructorimpl;
        str.getClass();
        ClassLoader classLoader = PluginSubscriberImpl.class.getClassLoader();
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(Class.forName(str, false, classLoader).asSubclass(Plugin.class));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl != null) {
            throw new NotPluginClassException(str, thM2709exceptionOrNullimpl);
        }
        objM2707constructorimpl.getClass();
        subscribePlugin((Class) objM2707constructorimpl, pluginPackage);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void unsubscribePlugin(Class cls, PluginPackage pluginPackage) {
        cls.getClass();
        BuildersKt__Builders_commonKt.launch$default(this.mCoroutineScope, null, null, new C01801(ExtensionsKt.getPluginActionOrThrow(cls), cls, null), 3, null);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriberAbility
    public void unsubscribePlugin(String str, PluginPackage pluginPackage) {
        Object objM2707constructorimpl;
        str.getClass();
        ClassLoader classLoader = PluginSubscriberImpl.class.getClassLoader();
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(Class.forName(str, false, classLoader).asSubclass(Plugin.class));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl != null) {
            throw new NotPluginClassException(str, thM2709exceptionOrNullimpl);
        }
        objM2707constructorimpl.getClass();
        unsubscribePlugin((Class) objM2707constructorimpl, pluginPackage);
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriber
    public boolean willDisableAllPlugins() {
        boolean z;
        while (true) {
            for (PluginInstanceContainer pluginInstanceContainer : this.mPICByPluginId.values()) {
                z = z || pluginInstanceContainer.disableAll();
            }
            return z;
        }
    }

    @Override // com.motorola.plugin.core.components.PluginSubscriber
    public boolean willDisableAnyPlugin(String str) {
        boolean z;
        str.getClass();
        while (true) {
            for (PluginInstanceContainer pluginInstanceContainer : this.mPICByPluginId.values()) {
                z = z || pluginInstanceContainer.checkAndDisable(str);
            }
            return z;
        }
    }
}
