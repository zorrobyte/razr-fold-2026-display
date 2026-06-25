package com.android.app.tracing.coroutines;

import android.os.Trace;
import com.android.systemui.Flags;
import java.lang.StackWalker;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: TraceContextElement.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TraceContextElementKt {
    private static final TraceDataThreadLocal traceThreadLocal = new TraceDataThreadLocal();

    public static final CoroutineContext createCoroutineTracingContext(String str, boolean z, boolean z2, boolean z3, boolean z4, Function1 function1) {
        str.getClass();
        if (Flags.coroutineTracing()) {
            return new TraceContextElement(str, true, !z3 && (z || DebugSysProps.alwaysEnableContinuationCounting), z4 || DebugSysProps.alwaysEnableStackWalker, function1, null, z3 ? "" : null, (z3 || !z2) ? -1 : 0);
        }
        return EmptyCoroutineContext.INSTANCE;
    }

    public static /* synthetic */ CoroutineContext createCoroutineTracingContext$default(String str, boolean z, boolean z2, boolean z3, boolean z4, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "UnnamedScope";
        }
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        if ((i & 8) != 0) {
            z3 = false;
        }
        if ((i & 16) != 0) {
            z4 = false;
        }
        if ((i & 32) != 0) {
            function1 = null;
        }
        Function1 function12 = function1;
        boolean z5 = z4;
        boolean z6 = z3;
        return createCoroutineTracingContext(str, z, z2, z6, z5, function12);
    }

    public static final TraceDataThreadLocal getTraceThreadLocal() {
        return traceThreadLocal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int nextRandomInt() {
        return ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String walkStackForClassName(final Function1 function1) {
        Trace.traceBegin(4096L, "walkStackForClassName");
        try {
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = "";
            StackWalker stackWalker = StackWalker.getInstance();
            final Function1 function12 = new Function1() { // from class: com.android.app.tracing.coroutines.TraceContextElementKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return TraceContextElementKt.walkStackForClassName$lambda$2(function1, ref$ObjectRef, (Stream) obj);
                }
            };
            stackWalker.walk(new Function(function12) { // from class: com.android.app.tracing.coroutines.TraceContextElementKt$sam$java_util_function_Function$0
                private final /* synthetic */ Function1 function;

                {
                    function12.getClass();
                    this.function = function12;
                }

                @Override // java.util.function.Function
                public final /* synthetic */ Object apply(Object obj) {
                    return this.function.invoke(obj);
                }
            });
            return (String) ref$ObjectRef.element;
        } catch (Exception unused) {
            return "";
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit walkStackForClassName$lambda$2(final Function1 function1, final Ref$ObjectRef ref$ObjectRef, Stream stream) {
        stream.getClass();
        final Function1 function12 = new Function1() { // from class: com.android.app.tracing.coroutines.TraceContextElementKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(TraceContextElementKt.walkStackForClassName$lambda$2$lambda$0(function1, (StackWalker.StackFrame) obj));
            }
        };
        Optional optionalFindFirst = stream.dropWhile(new Predicate(function12) { // from class: com.android.app.tracing.coroutines.TraceContextElementKt$sam$java_util_function_Predicate$0
            private final /* synthetic */ Function1 function;

            {
                function12.getClass();
                this.function = function12;
            }

            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj) {
                return ((Boolean) this.function.invoke(obj)).booleanValue();
            }
        }).findFirst();
        final Function1 function13 = new Function1() { // from class: com.android.app.tracing.coroutines.TraceContextElementKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return TraceContextElementKt.walkStackForClassName$lambda$2$lambda$1(ref$ObjectRef, (StackWalker.StackFrame) obj);
            }
        };
        optionalFindFirst.ifPresent(new Consumer(function13) { // from class: com.android.app.tracing.coroutines.TraceContextElementKt$sam$java_util_function_Consumer$0
            private final /* synthetic */ Function1 function;

            {
                function13.getClass();
                this.function = function13;
            }

            @Override // java.util.function.Consumer
            public final /* synthetic */ void accept(Object obj) {
                this.function.invoke(obj);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean walkStackForClassName$lambda$2$lambda$0(Function1 function1, StackWalker.StackFrame stackFrame) {
        stackFrame.getClass();
        String className = stackFrame.getClassName();
        className.getClass();
        if (StringsKt.startsWith$default(className, "kotlin", false, 2, (Object) null) || StringsKt.startsWith$default(className, "com.android.app.tracing.", false, 2, (Object) null)) {
            return true;
        }
        return function1 != null && ((Boolean) function1.invoke(className)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit walkStackForClassName$lambda$2$lambda$1(Ref$ObjectRef ref$ObjectRef, StackWalker.StackFrame stackFrame) {
        stackFrame.getClass();
        String className = stackFrame.getClassName();
        className.getClass();
        ref$ObjectRef.element = StringsKt.substringAfterLast$default(className, ".", (String) null, 2, (Object) null) + "." + stackFrame.getMethodName();
        return Unit.INSTANCE;
    }
}
