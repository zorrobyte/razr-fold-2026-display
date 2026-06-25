package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* JADX INFO: compiled from: SafeCollector.common.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class SafeCollector_commonKt {
    public static final void checkContext(final SafeCollector safeCollector, CoroutineContext coroutineContext) {
        safeCollector.getClass();
        coroutineContext.getClass();
        if (((Number) coroutineContext.fold(0, new Function2() { // from class: kotlinx.coroutines.flow.internal.SafeCollector_commonKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(SafeCollector_commonKt.checkContext$lambda$0(safeCollector, ((Integer) obj).intValue(), (CoroutineContext.Element) obj2));
            }
        })).intValue() == safeCollector.collectContextSize) {
            return;
        }
        throw new IllegalStateException(("Flow invariant is violated:\n\t\tFlow was collected in " + safeCollector.collectContext + ",\n\t\tbut emission happened in " + coroutineContext + ".\n\t\tPlease refer to 'flow' documentation or use 'flowOn' instead").toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int checkContext$lambda$0(SafeCollector safeCollector, int i, CoroutineContext.Element element) {
        element.getClass();
        CoroutineContext.Key key = element.getKey();
        CoroutineContext.Element element2 = safeCollector.collectContext.get(key);
        if (key != Job.Key) {
            if (element != element2) {
                return Integer.MIN_VALUE;
            }
            return i + 1;
        }
        Job job = (Job) element2;
        Job jobTransitiveCoroutineParent = transitiveCoroutineParent((Job) element, job);
        if (jobTransitiveCoroutineParent == job) {
            return job == null ? i : i + 1;
        }
        throw new IllegalStateException(("Flow invariant is violated:\n\t\tEmission from another coroutine is detected.\n\t\tChild of " + jobTransitiveCoroutineParent + ", expected child of " + job + ".\n\t\tFlowCollector is not thread-safe and concurrent emissions are prohibited.\n\t\tTo mitigate this restriction please use 'channelFlow' builder instead of 'flow'").toString());
    }

    public static final Job transitiveCoroutineParent(Job job, Job job2) {
        while (job != null) {
            if (job == job2 || !(job instanceof ScopeCoroutine)) {
                return job;
            }
            job = ((ScopeCoroutine) job).getParent();
        }
        return null;
    }
}
