package kotlinx.coroutines;

/* JADX INFO: compiled from: JobSupport.kt */
/* JADX INFO: loaded from: classes.dex */
public class JobImpl extends JobSupport implements CompletableJob {
    private final boolean handlesException;

    public JobImpl(Job job) {
        super(true);
        initParentJob(job);
        this.handlesException = handlesException();
    }

    private final boolean handlesException() {
        JobSupport job;
        ChildHandle parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getParentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        ChildHandleNode childHandleNode = parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof ChildHandleNode ? (ChildHandleNode) parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host : null;
        if (childHandleNode != null && (job = childHandleNode.getJob()) != null) {
            while (!job.getHandlesException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                ChildHandle parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 = job.getParentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                ChildHandleNode childHandleNode2 = parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 instanceof ChildHandleNode ? (ChildHandleNode) parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 : null;
                if (childHandleNode2 == null || (job = childHandleNode2.getJob()) == null) {
                }
            }
            return true;
        }
        return false;
    }

    @Override // kotlinx.coroutines.CompletableJob
    public boolean completeExceptionally(Throwable th) {
        th.getClass();
        return makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(new CompletedExceptionally(th, false, 2, null));
    }

    @Override // kotlinx.coroutines.JobSupport
    public boolean getHandlesException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this.handlesException;
    }

    @Override // kotlinx.coroutines.JobSupport
    public boolean getOnCancelComplete$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return true;
    }
}
