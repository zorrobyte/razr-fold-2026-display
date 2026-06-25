package com.android.systemui.util.kotlin;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* JADX INFO: compiled from: FlowDumper.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FlowDumperImpl implements Dumpable {
    private final DumpManager dumpManager;
    private final String dumpManagerName;
    private final ConcurrentHashMap flowCollectionMap;
    private AtomicBoolean registered;
    private final ConcurrentHashMap sharedFlowMap;
    private final ConcurrentHashMap stateFlowMap;

    /* JADX INFO: renamed from: com.android.systemui.util.kotlin.FlowDumperImpl$dumpWhileCollecting$1, reason: invalid class name */
    /* JADX INFO: compiled from: FlowDumper.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $dumpName;
        final /* synthetic */ Flow $this_dumpWhileCollecting;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ FlowDumperImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str, FlowDumperImpl flowDumperImpl, Flow flow, Continuation continuation) {
            super(2, continuation);
            this.$dumpName = str;
            this.this$0 = flowDumperImpl;
            this.$this_dumpWhileCollecting = flow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$dumpName, this.this$0, this.$this_dumpWhileCollecting, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((AnonymousClass1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Pair pair;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                pair = (Pair) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.flowCollectionMap.remove(pair);
                    this.this$0.updateRegistration(false);
                    return Unit.INSTANCE;
                } catch (Throwable th) {
                    th = th;
                    this.this$0.flowCollectionMap.remove(pair);
                    this.this$0.updateRegistration(false);
                    throw th;
                }
            }
            ResultKt.throwOnFailure(obj);
            final FlowCollector flowCollector = (FlowCollector) this.L$0;
            final Pair pair2 = TuplesKt.to(this.$dumpName, this.this$0.getIdString(flowCollector));
            try {
                Flow flow = this.$this_dumpWhileCollecting;
                final FlowDumperImpl flowDumperImpl = this.this$0;
                FlowCollector flowCollector2 = new FlowCollector() { // from class: com.android.systemui.util.kotlin.FlowDumperImpl.dumpWhileCollecting.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        flowDumperImpl.flowCollectionMap.put(pair2, obj2 == null ? "null" : obj2);
                        flowDumperImpl.updateRegistration(true);
                        Object objEmit = flowCollector.emit(obj2, continuation);
                        return objEmit == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEmit : Unit.INSTANCE;
                    }
                };
                this.L$0 = pair2;
                this.label = 1;
                if (flow.collect(flowCollector2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                pair = pair2;
                this.this$0.flowCollectionMap.remove(pair);
                this.this$0.updateRegistration(false);
                return Unit.INSTANCE;
            } catch (Throwable th2) {
                th = th2;
                pair = pair2;
                this.this$0.flowCollectionMap.remove(pair);
                this.this$0.updateRegistration(false);
                throw th;
            }
        }
    }

    public FlowDumperImpl(DumpManager dumpManager, String str) {
        this.dumpManager = dumpManager;
        this.stateFlowMap = new ConcurrentHashMap();
        this.sharedFlowMap = new ConcurrentHashMap();
        this.flowCollectionMap = new ConcurrentHashMap();
        if (str == null) {
            str = "[" + getIdString(this) + "] " + getClass().getSimpleName();
        }
        this.dumpManagerName = str;
        this.registered = new AtomicBoolean(false);
    }

    public /* synthetic */ FlowDumperImpl(DumpManager dumpManager, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(dumpManager, (i & 2) != 0 ? null : str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getIdString(Object obj) {
        String hexString = Integer.toHexString(System.identityHashCode(obj));
        hexString.getClass();
        return hexString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateRegistration(boolean z) {
        if (this.dumpManager == null) {
            return;
        }
        if (z && this.registered.get()) {
            return;
        }
        synchronized (this.registered) {
            try {
                boolean z2 = (this.stateFlowMap.isEmpty() && this.sharedFlowMap.isEmpty() && this.flowCollectionMap.isEmpty()) ? false : true;
                if (this.registered.getAndSet(z2) != z2) {
                    if (z2) {
                        this.dumpManager.registerCriticalDumpable(this.dumpManagerName, this);
                    } else {
                        this.dumpManager.unregisterDumpable(this.dumpManagerName);
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Flow dumpWhileCollecting(Flow flow, String str) {
        flow.getClass();
        str.getClass();
        return kotlinx.coroutines.flow.FlowKt.flow(new AnonymousClass1(str, this, flow, null));
    }
}
