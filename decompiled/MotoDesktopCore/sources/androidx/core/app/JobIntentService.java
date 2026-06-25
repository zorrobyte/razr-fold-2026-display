package androidx.core.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.util.HashMap;
import l.AsyncTaskC0147a;
import l.JobServiceEngineC0148b;

/* JADX INFO: loaded from: classes.dex */
public abstract class JobIntentService extends Service {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public JobServiceEngineC0148b f1392a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public AsyncTaskC0147a f1393b;

    static {
        new HashMap();
    }

    public abstract void a();

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        JobServiceEngineC0148b jobServiceEngineC0148b = this.f1392a;
        if (jobServiceEngineC0148b != null) {
            return jobServiceEngineC0148b.getBinder();
        }
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        this.f1392a = new JobServiceEngineC0148b(this);
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i2, int i3) {
        return 2;
    }
}
