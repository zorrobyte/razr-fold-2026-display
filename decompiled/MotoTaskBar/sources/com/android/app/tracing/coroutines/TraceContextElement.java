package com.android.app.tracing.coroutines;

import android.os.PerfettoTrace;
import android.os.Trace;
import com.android.app.tracing.coroutines.TraceContextElement;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CopyableThreadContextElement;

/* JADX INFO: compiled from: TraceContextElement.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TraceContextElement extends CoroutineTraceName implements CopyableThreadContextElement, CoroutineContext.Element {
    public static final Key Key = new Key(null);
    private final AtomicInteger childCoroutineCount;
    private final int childDepth;
    private final TraceData contextTraceData;
    private int continuationCount;
    private int continuationId;
    private final String copyForChildTraceMessage;
    private String coroutineTraceName;
    private final int currentId;
    private final boolean isRoot;
    private final String mergeForChildTraceMessage;
    private final String nameWithId;
    private final Function1 shouldIgnoreClassName;
    private final boolean usePerfettoSdk;
    private final boolean walkStackForDefaultNames;

    /* JADX INFO: compiled from: TraceContextElement.kt */
    public final class Key extends AbstractCoroutineContextKey {
        private Key() {
            super(CoroutineTraceName.Key, new Function1() { // from class: com.android.app.tracing.coroutines.TraceContextElement$Key$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return TraceContextElement.Key._init_$lambda$0((CoroutineContext.Element) obj);
                }
            });
        }

        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final TraceContextElement _init_$lambda$0(CoroutineContext.Element element) {
            element.getClass();
            if (element instanceof TraceContextElement) {
                return (TraceContextElement) element;
            }
            return null;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Incorrect type for immutable var: ssa=java.lang.Integer, code=java.lang.String, for r12v0, types: [java.lang.Integer] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r12v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.StringBuilder] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public TraceContextElement(java.lang.String r7, boolean r8, boolean r9, boolean r10, kotlin.jvm.functions.Function1 r11, java.lang.String r12, java.lang.String r13, int r14) {
        /*
            Method dump skipped, instruction units count: 245
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.app.tracing.coroutines.TraceContextElement.<init>(java.lang.String, boolean, boolean, boolean, kotlin.jvm.functions.Function1, java.lang.Integer, java.lang.String, int):void");
    }

    private final TraceContextElement createChildContext(String str) {
        String str2;
        String str3 = "";
        if (str == null && this.walkStackForDefaultNames) {
            str = TraceContextElementKt.walkStackForClassName(this.shouldIgnoreClassName);
        } else if (str == null) {
            str = "";
        }
        boolean z = this.continuationCount >= 0;
        boolean z2 = this.walkStackForDefaultNames;
        Function1 function1 = this.shouldIgnoreClassName;
        Integer numValueOf = Integer.valueOf(this.currentId);
        AtomicInteger atomicInteger = this.childCoroutineCount;
        if (atomicInteger != null) {
            int iIncrementAndGet = atomicInteger.incrementAndGet();
            if (!this.isRoot) {
                str3 = this.coroutineTraceName + ":";
            }
            str2 = str3 + iIncrementAndGet + "^";
        } else {
            str2 = null;
        }
        return new TraceContextElement(str, false, z, z2, function1, numValueOf, str2, this.childDepth);
    }

    @Override // kotlinx.coroutines.CopyableThreadContextElement
    public CopyableThreadContextElement copyForChild() {
        try {
            Trace.traceBegin(4096L, this.copyForChildTraceMessage);
            TraceContextElement traceContextElementCreateChildContext = createChildContext(this.isRoot ? getName$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform() : null);
            Trace.traceEnd(4096L);
            return traceContextElementCreateChildContext;
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    @Override // kotlinx.coroutines.CopyableThreadContextElement
    public CoroutineContext mergeForChild(CoroutineContext.Element element) {
        element.getClass();
        try {
            Trace.traceBegin(4096L, this.mergeForChildTraceMessage);
            CoroutineTraceName coroutineTraceName = (CoroutineTraceName) element.get(CoroutineTraceName.Key);
            TraceContextElement traceContextElementCreateChildContext = createChildContext(coroutineTraceName != null ? coroutineTraceName.getName$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform() : null);
            Trace.traceEnd(4096L);
            return traceContextElementCreateChildContext;
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    @Override // kotlinx.coroutines.ThreadContextElement
    public void restoreThreadContext(CoroutineContext coroutineContext, TraceData traceData) {
        coroutineContext.getClass();
        TraceStorage traceStorage = (TraceStorage) TraceContextElementKt.getTraceThreadLocal().get();
        if (traceStorage == null || traceStorage.getData$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform() == traceData) {
            return;
        }
        int iRestoreDataForSuspension = traceStorage.restoreDataForSuspension(traceData);
        if (this.usePerfettoSdk) {
            PerfettoTrace.end(PerfettoTraceConfig.COROUTINE_CATEGORY).setFlow(iRestoreDataForSuspension).emit();
        } else {
            Trace.traceEnd(4096L);
        }
    }

    @Override // kotlinx.coroutines.ThreadContextElement
    public TraceData updateThreadContext(CoroutineContext coroutineContext) {
        coroutineContext.getClass();
        TraceStorage traceStorage = (TraceStorage) TraceContextElementKt.getTraceThreadLocal().get();
        if (traceStorage == null) {
            return null;
        }
        TraceData data$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform = traceStorage.getData$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform();
        if (data$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform == this.contextTraceData) {
            return data$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform;
        }
        if (this.usePerfettoSdk) {
            PerfettoTrace.Category category = PerfettoTraceConfig.COROUTINE_CATEGORY;
            String str = this.coroutineTraceName;
            int i = this.continuationCount;
            PerfettoTrace.begin(category, str + (i < 0 ? "" : Integer.valueOf(i))).setTerminatingFlow(this.continuationId).emit();
            this.continuationId = TraceContextElementKt.nextRandomInt();
        } else {
            Trace.traceBegin(4096L, this.coroutineTraceName);
        }
        int i2 = this.continuationCount;
        if (i2 >= 0) {
            this.continuationCount = i2 + 1;
        }
        traceStorage.updateDataForContinuation(this.contextTraceData, this.continuationId);
        return data$frameworks__libs__systemui__tracinglib__core__android_common__tracinglib_platform;
    }
}
