package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Exceptions.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class JobCancellationException extends CancellationException {
    private final transient Job _job;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JobCancellationException(String str, Throwable th, Job job) {
        super(str);
        str.getClass();
        job.getClass();
        this._job = job;
        if (th != null) {
            initCause(th);
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof JobCancellationException)) {
            return false;
        }
        JobCancellationException jobCancellationException = (JobCancellationException) obj;
        return Intrinsics.areEqual(jobCancellationException.getMessage(), getMessage()) && Intrinsics.areEqual(jobCancellationException.getJob$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(), getJob$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) && Intrinsics.areEqual(jobCancellationException.getCause(), getCause());
    }

    @Override // java.lang.Throwable
    public Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }

    public final Job getJob$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        Job job = this._job;
        return job == null ? NonCancellable.INSTANCE : job;
    }

    public int hashCode() {
        String message = getMessage();
        message.getClass();
        int iHashCode = message.hashCode() * 31;
        Job job$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getJob$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        int iHashCode2 = (iHashCode + (job$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null ? job$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.hashCode() : 0)) * 31;
        Throwable cause = getCause();
        return iHashCode2 + (cause != null ? cause.hashCode() : 0);
    }

    @Override // java.lang.Throwable
    public String toString() {
        return super.toString() + "; job=" + getJob$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
    }
}
