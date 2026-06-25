package com.android.systemui.lifecycle;

import android.os.Trace;
import android.view.View;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.systemui.Flags;
import com.android.systemui.util.Assert;
import com.android.systemui.util.Compile;
import java.lang.StackWalker;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.DisposableHandle;

/* JADX INFO: compiled from: RepeatWhenAttached.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RepeatWhenAttachedKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final ViewLifecycleOwner createLifecycleOwnerAndRun(String str, View view, CoroutineContext coroutineContext, Function3 function3) {
        ViewLifecycleOwner viewLifecycleOwner = new ViewLifecycleOwner(view);
        viewLifecycleOwner.onCreate();
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner), null, null, new RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1(function3, viewLifecycleOwner, view, null), 3, null);
        return viewLifecycleOwner;
    }

    private static final String inferTraceSectionName() {
        try {
            Trace.traceBegin(4096L, "RepeatWhenAttachedKt#inferTraceSectionName");
            StackWalker stackWalker = StackWalker.getInstance();
            final Function1 function1 = new Function1() { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return RepeatWhenAttachedKt.inferTraceSectionName$lambda$1((Stream) obj);
                }
            };
            Optional optional = (Optional) stackWalker.walk(new Function(function1) { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$sam$java_util_function_Function$0
                private final /* synthetic */ Function1 function;

                {
                    function1.getClass();
                    this.function = function1;
                }

                @Override // java.util.function.Function
                public final /* synthetic */ Object apply(Object obj) {
                    return this.function.invoke(obj);
                }
            });
            if (!optional.isPresent()) {
                return "repeatWhenAttached";
            }
            Object obj = optional.get();
            obj.getClass();
            StackWalker.StackFrame stackFrame = (StackWalker.StackFrame) obj;
            return stackFrame.getClassName() + "#" + stackFrame.getMethodName() + ":" + stackFrame.getLineNumber() + " [repeatWhenAttached]";
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Optional inferTraceSectionName$lambda$1(Stream stream) {
        final RepeatWhenAttachedKt$inferTraceSectionName$interestingFrame$1$1 repeatWhenAttachedKt$inferTraceSectionName$interestingFrame$1$1 = RepeatWhenAttachedKt$inferTraceSectionName$interestingFrame$1$1.INSTANCE;
        return stream.filter(new Predicate(repeatWhenAttachedKt$inferTraceSectionName$interestingFrame$1$1) { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$sam$java_util_function_Predicate$0
            private final /* synthetic */ Function1 function;

            {
                repeatWhenAttachedKt$inferTraceSectionName$interestingFrame$1$1.getClass();
                this.function = repeatWhenAttachedKt$inferTraceSectionName$interestingFrame$1$1;
            }

            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj) {
                return ((Boolean) this.function.invoke(obj)).booleanValue();
            }
        }).limit(5L).findFirst();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isFrameInteresting(StackWalker.StackFrame stackFrame) {
        return (Intrinsics.areEqual(stackFrame.getClassName(), "com.android.systemui.lifecycle.RepeatWhenAttachedKt") || Intrinsics.areEqual(stackFrame.getClassName(), "com.android.systemui.util.kotlin.JavaAdapterKt")) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.view.View$OnAttachStateChangeListener, com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1] */
    public static final DisposableHandle repeatWhenAttached(final View view, CoroutineContext coroutineContext, final Function3 function3) {
        view.getClass();
        coroutineContext.getClass();
        function3.getClass();
        Assert.isMainThread();
        final CoroutineContext coroutineContextPlus = Dispatchers.getMain().plus(TraceContextElementKt.createCoroutineTracingContext$default(null, false, false, false, false, null, 63, null)).plus(coroutineContext);
        final String strInferTraceSectionName = (Compile.IS_DEBUG && Flags.coroutineTracing()) ? inferTraceSectionName() : "repeatWhenAttached";
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        final ?? r1 = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view2) {
                view2.getClass();
                Assert.isMainThread();
                ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) ref$ObjectRef.element;
                if (viewLifecycleOwner != null) {
                    viewLifecycleOwner.onDestroy();
                }
                ref$ObjectRef.element = RepeatWhenAttachedKt.createLifecycleOwnerAndRun(strInferTraceSectionName, view, coroutineContextPlus, function3);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view2) {
                view2.getClass();
                ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) ref$ObjectRef.element;
                if (viewLifecycleOwner != null) {
                    viewLifecycleOwner.onDestroy();
                }
                ref$ObjectRef.element = null;
            }
        };
        view.addOnAttachStateChangeListener(r1);
        if (view.isAttachedToWindow()) {
            ref$ObjectRef.element = createLifecycleOwnerAndRun(strInferTraceSectionName, view, coroutineContextPlus, function3);
        }
        return new DisposableHandle() { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt.repeatWhenAttached.1
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                Assert.isMainThread();
                ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) ref$ObjectRef.element;
                if (viewLifecycleOwner != null) {
                    viewLifecycleOwner.onDestroy();
                }
                ref$ObjectRef.element = null;
                view.removeOnAttachStateChangeListener(r1);
            }
        };
    }

    public static /* synthetic */ DisposableHandle repeatWhenAttached$default(View view, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        return repeatWhenAttached(view, coroutineContext, function3);
    }
}
