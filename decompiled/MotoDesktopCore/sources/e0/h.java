package e0;

import X.C0015f;
import X.InterfaceC0019h;
import X.v0;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.Handler;
import android.os.RemoteException;
import motorola.core_services.activity.MotoActivityManager;

/* JADX INFO: loaded from: classes.dex */
public final class h implements MotoActivityManager.OnActivityStateChangedListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ i f2483a;

    public h(i iVar) {
        this.f2483a = iVar;
    }

    public final Handler getHandler() {
        return null;
    }

    public final void onResumedActivityChange(ComponentName componentName, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z2) {
        v0.a("MotoTaskManager", "onResumedActivityChange ComponentName:" + componentName);
        try {
            InterfaceC0019h interfaceC0019h = (InterfaceC0019h) this.f2483a.f2489d;
            if (interfaceC0019h != null) {
                ((C0015f) interfaceC0019h).a(componentName, runningTaskInfo, z2);
            }
        } catch (RemoteException e2) {
            v0.c("MotoTaskManager", "OnActivityStateChangedListener onResumedActivityChange error!", e2);
        }
    }

    public final void onStartedActivityChange(ComponentName componentName, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z2) {
        v0.a("MotoTaskManager", "onStartedActivityChange ComponentName:" + componentName);
        try {
            InterfaceC0019h interfaceC0019h = (InterfaceC0019h) this.f2483a.f2489d;
            if (interfaceC0019h != null) {
                ((C0015f) interfaceC0019h).b(componentName, runningTaskInfo, z2);
            }
        } catch (RemoteException e2) {
            v0.c("MotoTaskManager", "OnActivityStateChangedListener onStartedActivityChange error!", e2);
        }
    }

    public final void onTopResumedActivityChange(ComponentName componentName, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z2) {
        v0.a("MotoTaskManager", "onTopResumedActivityChange ComponentName:" + componentName);
        try {
            InterfaceC0019h interfaceC0019h = (InterfaceC0019h) this.f2483a.f2489d;
            if (interfaceC0019h != null) {
                ((C0015f) interfaceC0019h).c(componentName, runningTaskInfo, z2);
            }
        } catch (RemoteException e2) {
            v0.c("MotoTaskManager", "OnActivityStateChangedListener onTopResumedActivityChange error!", e2);
        }
    }
}
