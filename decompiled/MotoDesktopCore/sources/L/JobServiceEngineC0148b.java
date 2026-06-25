package l;

import android.app.job.JobParameters;
import android.app.job.JobServiceEngine;
import android.os.AsyncTask;
import androidx.core.app.JobIntentService;

/* JADX INFO: renamed from: l.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class JobServiceEngineC0148b extends JobServiceEngine {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final JobIntentService f2766a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Object f2767b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public JobParameters f2768c;

    public JobServiceEngineC0148b(JobIntentService jobIntentService) {
        super(jobIntentService);
        this.f2767b = new Object();
        this.f2766a = jobIntentService;
    }

    @Override // android.app.job.JobServiceEngine
    public final boolean onStartJob(JobParameters jobParameters) {
        this.f2768c = jobParameters;
        JobIntentService jobIntentService = this.f2766a;
        if (jobIntentService.f1393b != null) {
            return true;
        }
        AsyncTaskC0147a asyncTaskC0147a = new AsyncTaskC0147a(jobIntentService);
        jobIntentService.f1393b = asyncTaskC0147a;
        asyncTaskC0147a.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        return true;
    }

    @Override // android.app.job.JobServiceEngine
    public final boolean onStopJob(JobParameters jobParameters) {
        AsyncTaskC0147a asyncTaskC0147a = this.f2766a.f1393b;
        if (asyncTaskC0147a != null) {
            asyncTaskC0147a.cancel(false);
        }
        synchronized (this.f2767b) {
            this.f2768c = null;
        }
        return true;
    }
}
