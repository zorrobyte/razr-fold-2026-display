package com.motorola.taskbar;

import android.app.PendingIntent;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.CallbackController;
import com.motorola.taskbar.model.NavIconController;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.trackpad.ReadyForProxy;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
public class TaskBarServiceProxy implements TaskBarServiceCallBack, CallbackController {
    private static final boolean DEBUG = DebugConfig.DEBUG_TASK_BAR_SERVICE_PROXY;
    private final Handler mBgHandler;
    private final Context mContext;
    private final DisplayManager.DisplayListener mDisplayListener;
    private final DisplayManager mDisplayManager;
    private final Handler mHandler;
    private IMainUserTaskBarProxy mIMainUserTaskBarProxy;
    private ITaskBarProxy mITaskBarProxy;
    private final MotoFeature mMotoFeature;
    private final Object mLock = new Object();
    private ArrayList mCallbacks = new ArrayList();
    private boolean mIsSystemUIReady = false;

    public TaskBarServiceProxy(Context context, Handler handler, Handler handler2) {
        DisplayManager.DisplayListener displayListener = new DisplayManager.DisplayListener() { // from class: com.motorola.taskbar.TaskBarServiceProxy.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
                if (Process.myUserHandle().equals(UserHandle.SYSTEM) && TaskBarServiceProxy.this.mITaskBarProxy == null) {
                    Log.d("TaskBarServiceProxy", "mITaskBarProxy is null , so notify display remove here");
                    TaskBarServiceProxy.this.onDisplayRemoved(i);
                }
            }
        };
        this.mDisplayListener = displayListener;
        this.mContext = context;
        this.mHandler = handler;
        this.mBgHandler = handler2;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(displayListener, handler);
        this.mMotoFeature = (MotoFeature) Dependency.get(MotoFeature.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addCallback$1(final TaskBarServiceCallBack taskBarServiceCallBack) {
        synchronized (this.mCallbacks) {
            try {
                this.mCallbacks.add(taskBarServiceCallBack);
                if (this.mIsSystemUIReady) {
                    this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda17
                        @Override // java.lang.Runnable
                        public final void run() {
                            taskBarServiceCallBack.onSystemUIReady();
                        }
                    });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDesktopIcon$13(String str, int i, StatusBarIcon statusBarIcon, PendingIntent pendingIntent) {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((TaskBarServiceCallBack) obj).addDesktopIcon(str, i, statusBarIcon, pendingIntent);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootComplete$19() {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((TaskBarServiceCallBack) obj).onBootComplete();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayReady$7(int i) {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((TaskBarServiceCallBack) obj).onDisplayReady(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayRemoved$6(int i) {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((TaskBarServiceCallBack) obj).onDisplayRemoved(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNavIconClicked$16() {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((TaskBarServiceCallBack) obj).onNavIconClicked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onQSTileDataUpdated$18(DesktopQSTileData desktopQSTileData) {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((TaskBarServiceCallBack) obj).onQSTileDataUpdated(desktopQSTileData);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRequestDisplayChooserModeInSecondUser$17() {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((TaskBarServiceCallBack) obj).onRequestDisplayChooserModeInSecondUser();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemUIGone$5() {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((TaskBarServiceCallBack) obj).onSystemUIGone();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemUIReady$4() {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((TaskBarServiceCallBack) obj).onSystemUIReady();
                }
                this.mIsSystemUIReady = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTaskbarWindowStateChanged$15(int i, int i2) {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    ((TaskBarServiceCallBack) obj).onTaskbarWindowStateChanged(i, i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUpdateDisplayChooserModeFromSecondUser$3(int i, String str) {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((TaskBarServiceCallBack) obj).onUpdateDisplayChooserModeFromSecondUser(i, str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeCallback$2(TaskBarServiceCallBack taskBarServiceCallBack) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.remove(taskBarServiceCallBack);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDesktopIcon$14(String str, int i) {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((TaskBarServiceCallBack) obj).removeDesktopIcon(str, i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTaskBarImeSwitchButtonVisible$10(int i, boolean z) {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((TaskBarServiceCallBack) obj).setTaskBarImeSwitchButtonVisible(i, z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTaskBarTransitionMode$8(int i, int i2) {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    ((TaskBarServiceCallBack) obj).setTaskBarTransitionMode(i, i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTaskBarViewVisibility$9(int i, int i2) {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    ((TaskBarServiceCallBack) obj).setTaskBarViewVisibility(i, i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateImeVisible$12(int i, boolean z) {
        synchronized (this.mCallbacks) {
            try {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((TaskBarServiceCallBack) obj).updateImeVisible(i, z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(final TaskBarServiceCallBack taskBarServiceCallBack) {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$addCallback$1(taskBarServiceCallBack);
            }
        });
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void addDesktopIcon(final String str, final int i, final StatusBarIcon statusBarIcon, final PendingIntent pendingIntent) {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$addDesktopIcon$13(str, i, statusBarIcon, pendingIntent);
            }
        });
    }

    public void forceShowKeyguardPresentation() {
        ITaskBarProxy iTaskBarProxy = this.mITaskBarProxy;
        if (iTaskBarProxy == null) {
            return;
        }
        try {
            iTaskBarProxy.forceShowKeyguardPresentation();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public DesktopQSTileData getQSTileDataData(int i) {
        ITaskBarProxy iTaskBarProxy = this.mITaskBarProxy;
        if (iTaskBarProxy == null) {
            return null;
        }
        try {
            return iTaskBarProxy.getQSTileDataData(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onBootComplete() {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onBootComplete$19();
            }
        });
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onDisplayReady(final int i) {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onDisplayReady$7(i);
            }
        });
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onDisplayRemoved(final int i) {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onDisplayRemoved$6(i);
            }
        });
    }

    public void onHardwareDisplayStatusChanged(boolean z, boolean z2) {
        ITaskBarProxy iTaskBarProxy = this.mITaskBarProxy;
        if (iTaskBarProxy == null) {
            return;
        }
        try {
            iTaskBarProxy.onHardwareDisplayStatusChanged(z, z2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onNavIconClicked() {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onNavIconClicked$16();
            }
        });
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onQSTileDataUpdated(final DesktopQSTileData desktopQSTileData) {
        if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onQSTileDataUpdated$18(desktopQSTileData);
                }
            });
        }
    }

    public void onQsTileClick(int i, int i2) {
        ITaskBarProxy iTaskBarProxy = this.mITaskBarProxy;
        if (iTaskBarProxy == null) {
            return;
        }
        try {
            iTaskBarProxy.onQsTileClick(i, i2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onRequestDisplayChooserModeInSecondUser() {
        if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onRequestDisplayChooserModeInSecondUser$17();
                }
            });
        }
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onSystemUIGone() {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onSystemUIGone$5();
            }
        });
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onSystemUIReady() {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onSystemUIReady$4();
            }
        });
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onTaskbarWindowStateChanged(final int i, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onTaskbarWindowStateChanged$15(i, i2);
            }
        });
    }

    public void onTrackpadStateChanged(boolean z) {
        if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            ((NavIconController) Dependency.get(NavIconController.class)).onTrackpadStateChanged(z);
            return;
        }
        IMainUserTaskBarProxy iMainUserTaskBarProxy = this.mIMainUserTaskBarProxy;
        if (iMainUserTaskBarProxy != null) {
            try {
                iMainUserTaskBarProxy.updateTrackpadStateChangedFromSecondUser(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onUpdateDisplayChooserModeFromSecondUser(final int i, final String str) {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onUpdateDisplayChooserModeFromSecondUser$3(i, str);
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(final TaskBarServiceCallBack taskBarServiceCallBack) {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$removeCallback$2(taskBarServiceCallBack);
            }
        });
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void removeDesktopIcon(final String str, final int i) {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$removeDesktopIcon$14(str, i);
            }
        });
    }

    public void requestDisplayChooserMode() {
        ((ReadyForProxy) Dependency.get(ReadyForProxy.class)).requestShowDesktopSplashScreen();
    }

    public void requestNavIcon(boolean z, int i) {
        if (this.mITaskBarProxy == null) {
            return;
        }
        try {
            if (DEBUG) {
                Log.d("TaskBarServiceProxy", "requestNavIcon: " + z);
            }
            this.mITaskBarProxy.requestNavIcon(z, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void requestNavTrackpadGuide(boolean z) {
        if (this.mITaskBarProxy == null) {
            return;
        }
        try {
            if (DEBUG) {
                Log.d("TaskBarServiceProxy", "requestNavTrackpadGuide: " + z);
            }
            this.mITaskBarProxy.requestNavTrackpadGuide(z);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void requestShowDesktopSplashScreen() {
        if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            requestDisplayChooserMode();
            return;
        }
        IMainUserTaskBarProxy iMainUserTaskBarProxy = this.mIMainUserTaskBarProxy;
        if (iMainUserTaskBarProxy != null) {
            try {
                iMainUserTaskBarProxy.requestShowDesktopSplashScreenFromSecondUser();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMainUserTaskBarProxy(IMainUserTaskBarProxy iMainUserTaskBarProxy) {
        this.mIMainUserTaskBarProxy = iMainUserTaskBarProxy;
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void setTaskBarImeSwitchButtonVisible(final int i, final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setTaskBarImeSwitchButtonVisible$10(i, z);
            }
        });
    }

    public void setTaskBarProxy(ITaskBarProxy iTaskBarProxy) {
        this.mITaskBarProxy = iTaskBarProxy;
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void setTaskBarTransitionMode(final int i, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setTaskBarTransitionMode$8(i, i2);
            }
        });
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void setTaskBarViewVisibility(final int i, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setTaskBarViewVisibility$9(i, i2);
            }
        });
    }

    public void startTrackpadActivityFromSecondUser(int i, boolean z) {
        IMainUserTaskBarProxy iMainUserTaskBarProxy = this.mIMainUserTaskBarProxy;
        if (iMainUserTaskBarProxy != null) {
            try {
                iMainUserTaskBarProxy.startTrackpadActivityFromSecondUser(i, z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void touchAutoHide(int i) {
        ITaskBarProxy iTaskBarProxy = this.mITaskBarProxy;
        if (iTaskBarProxy == null) {
            return;
        }
        try {
            iTaskBarProxy.touchAutoHide(i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void updateDisplayChooserModeFromSecondUser(int i, String str) {
        IMainUserTaskBarProxy iMainUserTaskBarProxy = this.mIMainUserTaskBarProxy;
        if (iMainUserTaskBarProxy != null) {
            try {
                iMainUserTaskBarProxy.updateDisplayChooserModeFromSecondUser(i, str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void updateImeVisible(final int i, final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarServiceProxy$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateImeVisible$12(i, z);
            }
        });
    }
}
