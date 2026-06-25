package e0;

import X.C0018g0;
import X.C0024j0;
import X.C0030m0;
import X.InterfaceC0020h0;
import X.InterfaceC0028l0;
import X.InterfaceC0034o0;
import X.v0;
import X.w0;
import android.app.ActivityManager;
import android.app.TaskStackListener;
import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import com.motorola.mobiledesktop.core.bean.AppInfo;
import com.motorola.mobiledesktop.core.bean.NewMotoRunningTaskInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: e0.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class TaskStackListenerC0127a extends TaskStackListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2447a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f2448b;

    public /* synthetic */ TaskStackListenerC0127a(int i2, Object obj) {
        this.f2447a = i2;
        this.f2448b = obj;
    }

    private final void a(int i2) {
        AppInfo appInfo;
        v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved:" + i2 + ", mAppStreamApps:" + ((b) this.f2448b).f2454e);
        synchronized (((b) this.f2448b).f2455f) {
            try {
                b bVar = (b) this.f2448b;
                InterfaceC0020h0 interfaceC0020h0 = bVar.f2460k;
                if (interfaceC0020h0 != null) {
                    ((C0018g0) interfaceC0020h0).onTaskRemoved(i2);
                } else {
                    AppInfo appInfo2 = null;
                    if (bVar.f2457h != null) {
                        v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved mIVirtualDisplayCallbackV2");
                        Iterator it = ((b) this.f2448b).f2454e.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                appInfo = null;
                                break;
                            }
                            appInfo = (AppInfo) it.next();
                            if (i2 == appInfo.taskId) {
                                v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved appInfo=" + appInfo);
                                break;
                            }
                        }
                        if (appInfo == null) {
                            return;
                        }
                        Thread.sleep(800L);
                        AppInfo appInfoZ = Y.r.z(((b) this.f2448b).f2450a, appInfo.pkg, appInfo.userId);
                        v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved existAppInfo=" + appInfoZ);
                        Set setKeySet = ((b) this.f2448b).f2451b.keySet();
                        if (appInfoZ == null) {
                            String str = appInfo.pkg;
                            v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved pkg=" + str + ", taskId=" + appInfo.taskId + ", mAppStreamPkgs=" + ((b) this.f2448b).f2452c);
                            if (TextUtils.isEmpty(str) || !((b) this.f2448b).f2452c.contains(str)) {
                                if (setKeySet.contains(Integer.valueOf(appInfo.displayId))) {
                                    Context unused = ((b) this.f2448b).f2450a;
                                    AppInfo appInfoF = Y.r.F(appInfo.displayId);
                                    if (appInfoF != null) {
                                        ((C0024j0) ((b) this.f2448b).f2457h).b(appInfoF.displayId, appInfoF.pkg);
                                    }
                                }
                                v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved not app stream task!");
                            } else {
                                ((C0024j0) ((b) this.f2448b).f2457h).c(str);
                                ((b) this.f2448b).f2454e.remove(appInfo);
                                ((b) this.f2448b).f2452c.remove(str);
                                v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved mAppStreamApps.remove:" + appInfo);
                                Context context = ((b) this.f2448b).f2450a;
                                AppInfo appInfoF2 = Y.r.F(appInfo.displayId);
                                if (appInfoF2 != null) {
                                    ((C0024j0) ((b) this.f2448b).f2457h).b(appInfoF2.displayId, appInfoF2.pkg);
                                }
                            }
                        } else {
                            ((b) this.f2448b).f2454e.remove(appInfo);
                            Context unused2 = ((b) this.f2448b).f2450a;
                            AppInfo appInfoF3 = Y.r.F(appInfo.displayId);
                            if (appInfoF3 != null) {
                                ((C0024j0) ((b) this.f2448b).f2457h).b(appInfoF3.displayId, appInfoF3.pkg);
                            }
                            if (setKeySet.contains(Integer.valueOf(appInfoZ.displayId))) {
                                ((b) this.f2448b).f2454e.add(appInfoZ);
                            }
                            v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved the app has the other task, not need to close window ! mAppStreamApps=" + ((b) this.f2448b).f2454e);
                        }
                    }
                    if (((b) this.f2448b).f2458i != null) {
                        v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved mIVirtualDisplayCallbackV3");
                        Iterator it2 = ((b) this.f2448b).f2454e.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            AppInfo appInfo3 = (AppInfo) it2.next();
                            if (i2 == appInfo3.taskId) {
                                v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved appInfo=" + appInfo3);
                                appInfo2 = appInfo3;
                                break;
                            }
                        }
                        if (appInfo2 == null) {
                            return;
                        }
                        Thread.sleep(800L);
                        AppInfo appInfoZ2 = Y.r.z(((b) this.f2448b).f2450a, appInfo2.pkg, appInfo2.userId);
                        v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved existAppInfo=" + appInfoZ2);
                        Set setKeySet2 = ((b) this.f2448b).f2451b.keySet();
                        if (appInfoZ2 == null) {
                            String str2 = appInfo2.pkg;
                            v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved pkg=" + str2 + ", taskId=" + appInfo2.taskId + ", mAppStreamPkgAndUserIds=" + ((b) this.f2448b).f2453d);
                            if (TextUtils.isEmpty(str2) || !((b) this.f2448b).f2453d.contains(appInfo2.pkgAndUserId)) {
                                if (setKeySet2.contains(Integer.valueOf(appInfo2.displayId))) {
                                    Context unused3 = ((b) this.f2448b).f2450a;
                                    AppInfo appInfoF4 = Y.r.F(appInfo2.displayId);
                                    if (appInfoF4 != null) {
                                        ((C0030m0) ((b) this.f2448b).f2458i).b(appInfoF4.displayId, appInfoF4.userId, appInfoF4.pkg);
                                    }
                                }
                                v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved not app stream task!");
                            } else {
                                ((C0030m0) ((b) this.f2448b).f2458i).d(appInfo2.displayId, appInfo2.userId, str2);
                                ((C0030m0) ((b) this.f2448b).f2458i).c(appInfo2.userId, str2);
                                ((b) this.f2448b).f2454e.remove(appInfo2);
                                ((b) this.f2448b).f2453d.remove(appInfo2.pkgAndUserId);
                                v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved mAppStreamApps.remove:" + appInfo2);
                                Context unused4 = ((b) this.f2448b).f2450a;
                                AppInfo appInfoF5 = Y.r.F(appInfo2.displayId);
                                if (appInfoF5 != null) {
                                    ((C0030m0) ((b) this.f2448b).f2458i).b(appInfoF5.displayId, appInfoF5.userId, appInfoF5.pkg);
                                }
                            }
                        } else {
                            ((b) this.f2448b).f2454e.remove(appInfo2);
                            Context unused5 = ((b) this.f2448b).f2450a;
                            AppInfo appInfoF6 = Y.r.F(appInfo2.displayId);
                            if (appInfoF6 != null) {
                                ((C0030m0) ((b) this.f2448b).f2458i).b(appInfoF6.displayId, appInfoF6.userId, appInfoF6.pkg);
                            }
                            if (setKeySet2.contains(Integer.valueOf(appInfoZ2.displayId))) {
                                ((b) this.f2448b).f2454e.add(appInfoZ2);
                            }
                            v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemoved the app has the other task, not need to close window ! mAppStreamApps=" + ((b) this.f2448b).f2454e);
                        }
                    } else {
                        v0.g("AppStreamManager_Core", "TaskStackListener onTaskRemoved mIVirtualDisplayCallbackV3 is null");
                    }
                }
            } catch (Exception e2) {
                v0.c("AppStreamManager_Core", "TaskStackListener onTaskRemoved error!", e2);
            }
        }
    }

    private final void b() {
    }

    public void onActivityRequestedOrientationChanged(int i2, int i3) {
        switch (this.f2447a) {
            case 1:
                super.onActivityRequestedOrientationChanged(i2, i3);
                v0.a("MotoTaskManager", "onActivityRequestedOrientationChanged taskId:" + i2 + ", requestedOrientation:" + i3);
                break;
            default:
                super.onActivityRequestedOrientationChanged(i2, i3);
                break;
        }
    }

    public void onActivityRotation(int i2) {
        switch (this.f2447a) {
            case 1:
                super.onActivityRotation(i2);
                w0.b(i2, "onActivityRotation displayId:", "MotoTaskManager");
                break;
            default:
                super.onActivityRotation(i2);
                break;
        }
    }

    public final void onTaskCreated(int i2, ComponentName componentName) {
        InterfaceC0020h0 interfaceC0020h0;
        switch (this.f2447a) {
            case 0:
                v0.a("AppStreamManager_Core", "TaskStackListener onTaskCreated:" + i2 + ", componentName=" + componentName);
                synchronized (((b) this.f2448b).f2455f) {
                    try {
                        interfaceC0020h0 = ((b) this.f2448b).f2460k;
                    } catch (RemoteException e2) {
                        v0.c("AppStreamManager_Core", "TaskStackListener onTaskCreated error!", e2);
                    }
                    if (interfaceC0020h0 == null) {
                        break;
                    } else {
                        ((C0018g0) interfaceC0020h0).onTaskCreated(i2, componentName);
                        break;
                    }
                }
                return;
            default:
                v0.a("MotoTaskManager", "onTaskCreated taskId:" + i2 + ", componentName:" + componentName);
                try {
                    InterfaceC0020h0 interfaceC0020h02 = (InterfaceC0020h0) ((i) this.f2448b).f2488c;
                    if (interfaceC0020h02 != null) {
                        ((C0018g0) interfaceC0020h02).onTaskCreated(i2, componentName);
                        return;
                    }
                    return;
                } catch (RemoteException e3) {
                    v0.c("MotoTaskManager", "TaskStackListener onTaskCreated error!", e3);
                    return;
                }
        }
    }

    public final void onTaskDisplayChanged(int i2, int i3) {
        switch (this.f2447a) {
            case 0:
                v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged taskId=" + i2 + ", newDisplayId=" + i3);
                synchronized (((b) this.f2448b).f2455f) {
                    try {
                        b bVar = (b) this.f2448b;
                        InterfaceC0020h0 interfaceC0020h0 = bVar.f2460k;
                        if (interfaceC0020h0 != null) {
                            ((C0018g0) interfaceC0020h0).onTaskDisplayChanged(i2, i3);
                        } else {
                            if (bVar.f2457h != null) {
                                v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged mIVirtualDisplayCallbackV2");
                                String strC = Y.r.C(((b) this.f2448b).f2450a, i2);
                                Set setKeySet = ((b) this.f2448b).f2451b.keySet();
                                v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged pkg=" + strC + ", taskId=" + i2 + ", mAppStreamPkgs=" + ((b) this.f2448b).f2452c + ", displayIds=" + setKeySet);
                                StringBuilder sb = new StringBuilder("TaskStackListener onTaskDisplayChanged mAppStreamApps=");
                                sb.append(((b) this.f2448b).f2454e);
                                v0.a("AppStreamManager_Core", sb.toString());
                                if (strC == null || !((b) this.f2448b).f2452c.contains(strC)) {
                                    v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged not app stream task!");
                                } else {
                                    ((C0024j0) ((b) this.f2448b).f2457h).a(Y.r.v(((b) this.f2448b).f2450a));
                                    if (!setKeySet.contains(Integer.valueOf(i3))) {
                                        ((b) this.f2448b).f2452c.remove(strC);
                                        for (AppInfo appInfo : ((b) this.f2448b).f2454e) {
                                            if (strC.equals(appInfo.pkg)) {
                                                v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged matched appInfo=" + appInfo);
                                                Context context = ((b) this.f2448b).f2450a;
                                                AppInfo appInfoF = Y.r.F(appInfo.displayId);
                                                if (appInfoF != null) {
                                                    ((C0024j0) ((b) this.f2448b).f2457h).b(appInfoF.displayId, appInfoF.pkg);
                                                }
                                                return;
                                            }
                                        }
                                        v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged mAppStreamPkgs.remove:" + strC);
                                    }
                                }
                            }
                            if (((b) this.f2448b).f2458i != null) {
                                v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged mIVirtualDisplayCallbackV3");
                                AppInfo appInfoY = Y.r.y(((b) this.f2448b).f2450a, i2);
                                Set setKeySet2 = ((b) this.f2448b).f2451b.keySet();
                                if (appInfoY != null) {
                                    v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged appInfo=" + appInfoY + ", mAppStreamPkgAndUserIds=" + ((b) this.f2448b).f2453d + ", displayIds=" + setKeySet2);
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append("TaskStackListener onTaskDisplayChanged mAppStreamApps=");
                                    sb2.append(((b) this.f2448b).f2454e);
                                    v0.a("AppStreamManager_Core", sb2.toString());
                                    String str = appInfoY.pkgAndUserId;
                                    if (str == null || !((b) this.f2448b).f2453d.contains(str)) {
                                        v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged not app stream task!");
                                        if (setKeySet2.contains(Integer.valueOf(i3))) {
                                            ((b) this.f2448b).f2453d.add(appInfoY.pkgAndUserId);
                                        }
                                        v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged mAppStreamPkgAndUserIds=" + ((b) this.f2448b).f2453d);
                                    } else {
                                        ArrayList arrayListV = Y.r.v(((b) this.f2448b).f2450a);
                                        ((C0030m0) ((b) this.f2448b).f2458i).a(arrayListV);
                                        if (setKeySet2.contains(Integer.valueOf(i3))) {
                                            v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged onTaskFocusChanged");
                                            ((b) this.f2448b).f2461l.onTaskFocusChanged(appInfoY.taskId, true);
                                        } else {
                                            int i4 = 0;
                                            for (int i5 = 0; i5 < arrayListV.size(); i5++) {
                                                if (arrayListV.get(i5) instanceof NewMotoRunningTaskInfo) {
                                                    if (appInfoY.pkgAndUserId.equals(((NewMotoRunningTaskInfo) arrayListV.get(i5)).pkgAndUserId)) {
                                                        i4++;
                                                    }
                                                }
                                            }
                                            if (i4 <= 1) {
                                                ((b) this.f2448b).f2453d.remove(appInfoY.pkgAndUserId);
                                            }
                                            for (AppInfo appInfo2 : ((b) this.f2448b).f2454e) {
                                                String str2 = appInfoY.pkgAndUserId;
                                                if (str2 != null && str2.equals(appInfo2.pkgAndUserId)) {
                                                    v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged matched tempAppInfo=" + appInfo2);
                                                    Context context2 = ((b) this.f2448b).f2450a;
                                                    AppInfo appInfoF2 = Y.r.F(appInfo2.displayId);
                                                    if (appInfoF2 != null) {
                                                        ((C0030m0) ((b) this.f2448b).f2458i).b(appInfoF2.displayId, appInfoF2.userId, appInfoF2.pkg);
                                                    }
                                                    return;
                                                }
                                            }
                                            v0.a("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged mAppStreamPkgs.remove:" + appInfoY.pkgAndUserId);
                                        }
                                    }
                                }
                            } else {
                                v0.g("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged mIVirtualDisplayCallbackV3 is null");
                            }
                        }
                    } catch (Exception e2) {
                        v0.c("AppStreamManager_Core", "TaskStackListener onTaskDisplayChanged error!", e2);
                    }
                    return;
                }
            default:
                v0.a("MotoTaskManager", "onTaskDisplayChanged taskId:" + i2 + ", newDisplayId:" + i3);
                try {
                    InterfaceC0020h0 interfaceC0020h02 = (InterfaceC0020h0) ((i) this.f2448b).f2488c;
                    if (interfaceC0020h02 != null) {
                        ((C0018g0) interfaceC0020h02).onTaskDisplayChanged(i2, i3);
                        return;
                    }
                    return;
                } catch (RemoteException e3) {
                    v0.c("MotoTaskManager", "TaskStackListener onTaskDisplayChanged error!", e3);
                    return;
                }
        }
    }

    public final void onTaskFocusChanged(int i2, boolean z2) {
        switch (this.f2447a) {
            case 0:
                synchronized (((b) this.f2448b).f2455f) {
                    try {
                        try {
                            InterfaceC0020h0 interfaceC0020h0 = ((b) this.f2448b).f2460k;
                            if (interfaceC0020h0 != null) {
                                ((C0018g0) interfaceC0020h0).onTaskFocusChanged(i2, z2);
                            } else if (z2) {
                                v0.a("AppStreamManager_Core", "TaskStackListener onTaskFocusChanged:" + i2);
                                try {
                                    Set setKeySet = ((b) this.f2448b).f2451b.keySet();
                                    AppInfo appInfoY = Y.r.y(((b) this.f2448b).f2450a, i2);
                                    if (appInfoY == null || !setKeySet.contains(Integer.valueOf(appInfoY.displayId))) {
                                        v0.g("AppStreamManager_Core", "TaskStackListener onTaskFocusChanged appInfo is null or not in app streaming display ids");
                                    } else {
                                        ((b) this.f2448b).f2454e.add(appInfoY);
                                        v0.a("AppStreamManager_Core", "TaskStackListener onTaskFocusChanged displayId:" + appInfoY.displayId + ", pkg:" + appInfoY.pkg);
                                        StringBuilder sb = new StringBuilder("TaskStackListener onTaskFocusChanged mAppStreamApps:");
                                        sb.append(((b) this.f2448b).f2454e);
                                        v0.a("AppStreamManager_Core", sb.toString());
                                        InterfaceC0028l0 interfaceC0028l0 = ((b) this.f2448b).f2457h;
                                        if (interfaceC0028l0 != null) {
                                            ((C0024j0) interfaceC0028l0).b(appInfoY.displayId, appInfoY.pkg);
                                        }
                                        InterfaceC0034o0 interfaceC0034o0 = ((b) this.f2448b).f2458i;
                                        if (interfaceC0034o0 != null) {
                                            ((C0030m0) interfaceC0034o0).b(appInfoY.displayId, appInfoY.userId, appInfoY.pkg);
                                        }
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        } catch (RemoteException e3) {
                            v0.c("AppStreamManager_Core", "TaskStackListener onTaskFocusChanged error!", e3);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    break;
                }
                return;
            default:
                v0.a("MotoTaskManager", "onTaskFocusChanged taskId:" + i2 + ", focused:" + z2);
                try {
                    InterfaceC0020h0 interfaceC0020h02 = (InterfaceC0020h0) ((i) this.f2448b).f2488c;
                    if (interfaceC0020h02 != null) {
                        ((C0018g0) interfaceC0020h02).onTaskFocusChanged(i2, z2);
                        return;
                    }
                    return;
                } catch (RemoteException e4) {
                    v0.c("MotoTaskManager", "TaskStackListener onTaskFocusChanged error!", e4);
                    return;
                }
        }
    }

    public void onTaskMovedToBack(ActivityManager.RunningTaskInfo runningTaskInfo) {
        switch (this.f2447a) {
            case 1:
                super.onTaskMovedToBack(runningTaskInfo);
                v0.a("MotoTaskManager", "onTaskMovedToBack taskInfo:" + runningTaskInfo);
                break;
            default:
                super.onTaskMovedToBack(runningTaskInfo);
                break;
        }
    }

    public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
        InterfaceC0020h0 interfaceC0020h0;
        switch (this.f2447a) {
            case 0:
                v0.a("AppStreamManager_Core", "TaskStackListener onTaskMovedToFront:" + runningTaskInfo);
                synchronized (((b) this.f2448b).f2455f) {
                    try {
                        interfaceC0020h0 = ((b) this.f2448b).f2460k;
                    } catch (RemoteException e2) {
                        v0.c("AppStreamManager_Core", "TaskStackListener onTaskMovedToFront error!", e2);
                    }
                    if (interfaceC0020h0 == null) {
                        break;
                    } else {
                        ((C0018g0) interfaceC0020h0).onTaskMovedToFront(runningTaskInfo);
                        break;
                    }
                }
                return;
            default:
                v0.a("MotoTaskManager", "onTaskMovedToFront taskInfo:" + runningTaskInfo);
                try {
                    InterfaceC0020h0 interfaceC0020h02 = (InterfaceC0020h0) ((i) this.f2448b).f2488c;
                    if (interfaceC0020h02 != null) {
                        ((C0018g0) interfaceC0020h02).onTaskMovedToFront(runningTaskInfo);
                        return;
                    }
                    return;
                } catch (RemoteException e3) {
                    v0.c("MotoTaskManager", "TaskStackListener onTaskMovedToFront error!", e3);
                    return;
                }
        }
    }

    public final void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) {
        InterfaceC0020h0 interfaceC0020h0;
        switch (this.f2447a) {
            case 0:
                v0.a("AppStreamManager_Core", "TaskStackListener onTaskRemovalStarted:" + runningTaskInfo.taskId);
                synchronized (((b) this.f2448b).f2455f) {
                    try {
                        interfaceC0020h0 = ((b) this.f2448b).f2460k;
                    } catch (RemoteException e2) {
                        v0.c("AppStreamManager_Core", "TaskStackListener onTaskRemovalStarted error!", e2);
                    }
                    if (interfaceC0020h0 == null) {
                        break;
                    } else {
                        ((C0018g0) interfaceC0020h0).onTaskRemovalStarted(runningTaskInfo);
                        break;
                    }
                }
                return;
            default:
                v0.a("MotoTaskManager", "onTaskRemovalStarted taskInfo:" + runningTaskInfo);
                try {
                    InterfaceC0020h0 interfaceC0020h02 = (InterfaceC0020h0) ((i) this.f2448b).f2488c;
                    if (interfaceC0020h02 != null) {
                        ((C0018g0) interfaceC0020h02).onTaskRemovalStarted(runningTaskInfo);
                        return;
                    }
                    return;
                } catch (RemoteException e3) {
                    v0.c("MotoTaskManager", "TaskStackListener onTaskRemovalStarted error!", e3);
                    return;
                }
        }
    }

    public final void onTaskRemoved(int i2) {
        switch (this.f2447a) {
            case 0:
                a(i2);
                break;
            default:
                w0.b(i2, "onTaskRemoved taskId:", "MotoTaskManager");
                try {
                    InterfaceC0020h0 interfaceC0020h0 = (InterfaceC0020h0) ((i) this.f2448b).f2488c;
                    if (interfaceC0020h0 != null) {
                        ((C0018g0) interfaceC0020h0).onTaskRemoved(i2);
                    }
                } catch (RemoteException e2) {
                    v0.c("MotoTaskManager", "TaskStackListener onTaskRemoved error!", e2);
                }
                break;
        }
    }

    public final void onTaskStackChanged() {
        switch (this.f2447a) {
            case 0:
                break;
            default:
                super.onTaskStackChanged();
                v0.a("MotoTaskManager", "onTaskStackChanged");
                break;
        }
    }
}
