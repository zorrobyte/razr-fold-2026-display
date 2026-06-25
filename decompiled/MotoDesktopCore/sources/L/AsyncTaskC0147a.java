package l;

import android.os.AsyncTask;
import androidx.core.app.JobIntentService;

/* JADX INFO: renamed from: l.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class AsyncTaskC0147a extends AsyncTask {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ JobIntentService f2765a;

    public AsyncTaskC0147a(JobIntentService jobIntentService) {
        this.f2765a = jobIntentService;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x005c A[SYNTHETIC] */
    @Override // android.os.AsyncTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object doInBackground(java.lang.Object[] r5) {
        /*
            r4 = this;
            java.lang.Void[] r5 = (java.lang.Void[]) r5
        L2:
            androidx.core.app.JobIntentService r5 = r4.f2765a
            l.b r0 = r5.f1392a
            r0.getClass()
            l.b r5 = r5.f1392a
            java.lang.Object r0 = r5.f2767b
            monitor-enter(r0)
            android.app.job.JobParameters r1 = r5.f2768c     // Catch: java.lang.Throwable -> L16
            r2 = 0
            if (r1 != 0) goto L18
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L16
        L14:
            r0 = r2
            goto L31
        L16:
            r4 = move-exception
            goto L5d
        L18:
            android.app.job.JobWorkItem r1 = r1.dequeueWork()     // Catch: java.lang.Throwable -> L16
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L16
            if (r1 == 0) goto L14
            android.content.Intent r0 = r1.getIntent()
            androidx.core.app.JobIntentService r3 = r5.f2766a
            java.lang.ClassLoader r3 = r3.getClassLoader()
            r0.setExtrasClassLoader(r3)
            F.f r0 = new F.f
            r0.<init>(r5, r1)
        L31:
            if (r0 == 0) goto L5c
            androidx.core.app.JobIntentService r5 = r4.f2765a
            java.lang.Object r1 = r0.f123a
            android.app.job.JobWorkItem r1 = (android.app.job.JobWorkItem) r1
            r1.getIntent()
            r5.a()
            java.lang.Object r5 = r0.f124b
            l.b r5 = (l.JobServiceEngineC0148b) r5
            java.lang.Object r5 = r5.f2767b
            monitor-enter(r5)
            java.lang.Object r1 = r0.f124b     // Catch: java.lang.Throwable -> L56
            l.b r1 = (l.JobServiceEngineC0148b) r1     // Catch: java.lang.Throwable -> L56
            android.app.job.JobParameters r1 = r1.f2768c     // Catch: java.lang.Throwable -> L56
            if (r1 == 0) goto L58
            java.lang.Object r0 = r0.f123a     // Catch: java.lang.Throwable -> L56
            android.app.job.JobWorkItem r0 = (android.app.job.JobWorkItem) r0     // Catch: java.lang.Throwable -> L56
            r1.completeWork(r0)     // Catch: java.lang.Throwable -> L56
            goto L58
        L56:
            r4 = move-exception
            goto L5a
        L58:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L56
            goto L2
        L5a:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L56
            throw r4
        L5c:
            return r2
        L5d:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L16
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: l.AsyncTaskC0147a.doInBackground(java.lang.Object[]):java.lang.Object");
    }

    @Override // android.os.AsyncTask
    public final void onCancelled(Object obj) {
        this.f2765a.getClass();
    }

    @Override // android.os.AsyncTask
    public final void onPostExecute(Object obj) {
        this.f2765a.getClass();
    }
}
