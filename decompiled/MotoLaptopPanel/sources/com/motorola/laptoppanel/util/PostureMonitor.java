package com.motorola.laptoppanel.util;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import java.io.PrintWriter;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: PostureMonitor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PostureMonitor {
    private final Flow deviceStateFlow;
    private final DeviceStateManager deviceStateManager;
    private final StateFlow posture;
    private final CoroutineScope scope;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: PostureMonitor.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public PostureMonitor(Context context, CoroutineScope coroutineScope) {
        context.getClass();
        coroutineScope.getClass();
        this.scope = coroutineScope;
        this.deviceStateManager = (DeviceStateManager) context.getSystemService(DeviceStateManager.class);
        Flow flowCallbackFlow = FlowKt.callbackFlow(new PostureMonitor$deviceStateFlow$1(this, context, null));
        this.deviceStateFlow = flowCallbackFlow;
        final Flow flowDebounce = FlowKt.debounce(flowCallbackFlow, 150L);
        this.posture = FlowKt.stateIn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.motorola.laptoppanel.util.PostureMonitor$special$$inlined$map$1

            /* JADX INFO: renamed from: com.motorola.laptoppanel.util.PostureMonitor$special$$inlined$map$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: Emitters.kt */
            public final class AnonymousClass2 implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* JADX INFO: renamed from: com.motorola.laptoppanel.util.PostureMonitor$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.motorola.laptoppanel.util.PostureMonitor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.motorola.laptoppanel.util.PostureMonitor$special$$inlined$map$1$2$1 r0 = (com.motorola.laptoppanel.util.PostureMonitor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.motorola.laptoppanel.util.PostureMonitor$special$$inlined$map$1$2$1 r0 = new com.motorola.laptoppanel.util.PostureMonitor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L54
                    L29:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L31:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        r6 = 3
                        if (r5 == r6) goto L49
                        r6 = 8
                        if (r5 == r6) goto L46
                        com.motorola.laptoppanel.util.Posture r5 = com.motorola.laptoppanel.util.Posture.UNKNOWN
                        goto L4b
                    L46:
                        com.motorola.laptoppanel.util.Posture r5 = com.motorola.laptoppanel.util.Posture.BOOK
                        goto L4b
                    L49:
                        com.motorola.laptoppanel.util.Posture r5 = com.motorola.laptoppanel.util.Posture.LAPTOP
                    L4b:
                        r0.label = r3
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L54
                        return r1
                    L54:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.util.PostureMonitor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowDebounce.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        }), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 5000L, 0L, 2, null), Posture.UNKNOWN);
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.getClass();
        printWriter.println("PostureMonitor State:");
        printWriter.println("  Current Posture: " + this.posture.getValue());
        printWriter.println("  Configuration:");
        printWriter.println("    LAPTOP_STATE_ID: 3");
        printWriter.println("    BOOK_STATE_ID: 8");
        printWriter.println("--------------------------------------------------");
    }

    public final StateFlow getPosture() {
        return this.posture;
    }
}
