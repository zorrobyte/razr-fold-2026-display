package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.Job;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: Job.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract /* synthetic */ class JobKt__JobKt {

    /* JADX INFO: renamed from: kotlinx.coroutines.JobKt__JobKt$invokeOnCompletion$1, reason: invalid class name */
    /* JADX INFO: compiled from: Job.kt */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        AnonymousClass1(Object obj) {
            super(1, obj, JobNode.class, "invoke", "invoke(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(Throwable th) {
            ((JobNode) this.receiver).invoke(th);
        }
    }

    public static final CompletableJob Job(Job job) {
        return new JobImpl(job);
    }

    public static /* synthetic */ CompletableJob Job$default(Job job, int i, Object obj) {
        if ((i & 1) != 0) {
            job = null;
        }
        return JobKt.Job(job);
    }

    public static final void cancel(CoroutineContext coroutineContext, CancellationException cancellationException) {
        coroutineContext.getClass();
        Job job = (Job) coroutineContext.get(Job.Key);
        if (job != null) {
            job.cancel(cancellationException);
        }
    }

    public static final void cancel(Job job, String str, Throwable th) {
        job.getClass();
        str.getClass();
        job.cancel(ExceptionsKt.CancellationException(str, th));
    }

    public static /* synthetic */ void cancel$default(CoroutineContext coroutineContext, CancellationException cancellationException, int i, Object obj) {
        if ((i & 1) != 0) {
            cancellationException = null;
        }
        JobKt.cancel(coroutineContext, cancellationException);
    }

    public static /* synthetic */ void cancel$default(Job job, String str, Throwable th, int i, Object obj) {
        if ((i & 2) != 0) {
            th = null;
        }
        JobKt.cancel(job, str, th);
    }

    public static final Object cancelAndJoin(Job job, Continuation continuation) {
        Job.DefaultImpls.cancel$default(job, null, 1, null);
        Object objJoin = job.join(continuation);
        return objJoin == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objJoin : Unit.INSTANCE;
    }

    public static final void ensureActive(CoroutineContext coroutineContext) {
        coroutineContext.getClass();
        Job job = (Job) coroutineContext.get(Job.Key);
        if (job != null) {
            JobKt.ensureActive(job);
        }
    }

    public static final void ensureActive(Job job) {
        job.getClass();
        if (!job.isActive()) {
            throw job.getCancellationException();
        }
    }

    public static final Job getJob(CoroutineContext coroutineContext) {
        coroutineContext.getClass();
        Job job = (Job) coroutineContext.get(Job.Key);
        if (job != null) {
            return job;
        }
        throw new IllegalStateException(("Current context doesn't contain Job in it: " + coroutineContext).toString());
    }

    public static final DisposableHandle invokeOnCompletion(Job job, boolean z, JobNode jobNode) {
        job.getClass();
        jobNode.getClass();
        return job instanceof JobSupport ? ((JobSupport) job).invokeOnCompletionInternal$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(z, jobNode) : job.invokeOnCompletion(jobNode.getOnCancelling(), z, new AnonymousClass1(jobNode));
    }

    public static /* synthetic */ DisposableHandle invokeOnCompletion$default(Job job, boolean z, JobNode jobNode, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return JobKt.invokeOnCompletion(job, z, jobNode);
    }

    public static final boolean isActive(CoroutineContext coroutineContext) {
        coroutineContext.getClass();
        Job job = (Job) coroutineContext.get(Job.Key);
        if (job != null) {
            return job.isActive();
        }
        return true;
    }
}
