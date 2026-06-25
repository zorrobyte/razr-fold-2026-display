package com.motorola.taskbar;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.Dependency;
import com.android.systemui.SystemUIFactory;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.motorola.taskbar.IMainUserTaskBarProxy;
import com.motorola.taskbar.ITaskBarProxy;
import com.motorola.taskbar.ITaskBarService;
import com.motorola.taskbar.TaskBarChildUserServerService;
import com.motorola.taskbar.model.NavIconController;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.trackpad.ReadyForProxy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class TaskBarChildUserServerService extends Service {
    private static final boolean DEBUG = DebugConfig.DEBUG_MULTI_USER;
    protected Handler mBgHandler;
    protected DeviceProvisionedController mDeviceProvisionedController;
    protected TaskBarServiceProxy mTaskBarServiceProxy;
    private UserManager mUserManager;
    private SparseArray mChildUserTaskBarServiceConnections = new SparseArray();
    private Binder mBinder = new Binder();
    private final DeviceProvisionedController.DeviceProvisionedListener mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.motorola.taskbar.TaskBarChildUserServerService.1
        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onUserSwitched() {
            TaskBarChildUserServerService taskBarChildUserServerService = TaskBarChildUserServerService.this;
            taskBarChildUserServerService.bindToChildUserTaskBarService(taskBarChildUserServerService.mDeviceProvisionedController.getCurrentUser());
        }
    };
    private final ITaskBarProxy mITaskBarProxy = new ITaskBarProxy.Stub() { // from class: com.motorola.taskbar.TaskBarChildUserServerService.2
        @Override // com.motorola.taskbar.ITaskBarProxy
        public void forceShowKeyguardPresentation() {
        }

        @Override // com.motorola.taskbar.ITaskBarProxy
        public DesktopQSTileData getQSTileDataData(int i) {
            return null;
        }

        @Override // com.motorola.taskbar.ITaskBarProxy
        public void onHardwareDisplayStatusChanged(boolean z, boolean z2) {
            TaskBarChildUserServerService.this.mTaskBarServiceProxy.onHardwareDisplayStatusChanged(z, z2);
        }

        @Override // com.motorola.taskbar.ITaskBarProxy
        public void onQsTileClick(int i, int i2) {
        }

        @Override // com.motorola.taskbar.ITaskBarProxy
        public void onTrackpadStateChanged(boolean z) {
            TaskBarChildUserServerService.this.mTaskBarServiceProxy.onTrackpadStateChanged(z);
        }

        @Override // com.motorola.taskbar.ITaskBarProxy
        public void requestNavIcon(boolean z, int i) {
            TaskBarChildUserServerService.this.mTaskBarServiceProxy.requestNavIcon(z, i);
        }

        @Override // com.motorola.taskbar.ITaskBarProxy
        public void requestNavTrackpadGuide(boolean z) {
            TaskBarChildUserServerService.this.mTaskBarServiceProxy.requestNavTrackpadGuide(z);
        }

        @Override // com.motorola.taskbar.ITaskBarProxy
        public void touchAutoHide(int i) {
            TaskBarChildUserServerService.this.mTaskBarServiceProxy.touchAutoHide(i);
        }
    };
    private final IMainUserTaskBarProxy mIMainUserTaskBarProxy = new IMainUserTaskBarProxy.Stub() { // from class: com.motorola.taskbar.TaskBarChildUserServerService.3
        @Override // com.motorola.taskbar.IMainUserTaskBarProxy
        public void handleThreeFingerGestureFromSecondUser(int i, boolean z) {
            if (TaskBarChildUserServerService.DEBUG) {
                Log.d("TaskBarChildUserServerService", "handleThreeFingerGestureFromSecondUser: " + i + " isModeChooserShowing :" + z);
            }
        }

        @Override // com.motorola.taskbar.IMainUserTaskBarProxy
        public void requestShowDesktopSplashScreenFromSecondUser() {
            TaskBarChildUserServerService.this.mTaskBarServiceProxy.requestShowDesktopSplashScreen();
        }

        @Override // com.motorola.taskbar.IMainUserTaskBarProxy
        public void startTrackpadActivityFromSecondUser(int i, boolean z) {
            ((ReadyForProxy) Dependency.get(ReadyForProxy.class)).startTrackpadActivity(i, z);
        }

        @Override // com.motorola.taskbar.IMainUserTaskBarProxy
        public void updateDisplayChooserModeFromSecondUser(int i, String str) {
            if (TaskBarChildUserServerService.DEBUG) {
                Log.d("TaskBarChildUserServerService", "updateDisplayChooserModeFromSecondUser: " + i + "; mode: " + str);
            }
            TaskBarChildUserServerService.this.mTaskBarServiceProxy.onUpdateDisplayChooserModeFromSecondUser(i, str);
        }

        @Override // com.motorola.taskbar.IMainUserTaskBarProxy
        public void updateTrackpadStateChangedFromSecondUser(boolean z) {
            ((NavIconController) Dependency.get(NavIconController.class)).onTrackpadStateChanged(z);
        }
    };
    private TaskBarServiceCallBack mTaskBarServiceCallBack = new AnonymousClass4();

    /* JADX INFO: renamed from: com.motorola.taskbar.TaskBarChildUserServerService$4, reason: invalid class name */
    class AnonymousClass4 implements TaskBarServiceCallBack {
        public static /* synthetic */ void $r8$lambda$5kpO_S3j1XNBzsd3rXDV2vCIzmk(ChildUserTaskBarServiceConnection childUserTaskBarServiceConnection) {
            try {
                if (childUserTaskBarServiceConnection.mITaskBarService != null) {
                    childUserTaskBarServiceConnection.mITaskBarService.onSystemUIReady();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: renamed from: $r8$lambda$DKG0sj-ZU33ePB_z-6OBZVVnkqQ, reason: not valid java name */
        public static /* synthetic */ void m2273$r8$lambda$DKG0sjZU33ePB_z6OBZVVnkqQ(int i, boolean z, ChildUserTaskBarServiceConnection childUserTaskBarServiceConnection) {
            try {
                if (childUserTaskBarServiceConnection.mITaskBarService != null) {
                    childUserTaskBarServiceConnection.mITaskBarService.setTaskBarImeSwitchButtonVisible(i, z);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: renamed from: $r8$lambda$NWMCONP6IKbQk-gUYsEnDV8TFIc, reason: not valid java name */
        public static /* synthetic */ void m2274$r8$lambda$NWMCONP6IKbQkgUYsEnDV8TFIc(int i, int i2, ChildUserTaskBarServiceConnection childUserTaskBarServiceConnection) {
            try {
                if (childUserTaskBarServiceConnection.mITaskBarService != null) {
                    childUserTaskBarServiceConnection.mITaskBarService.setTaskBarTransitionMode(i, i2);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public static /* synthetic */ void $r8$lambda$Z4ek1hxtzoVHIvj1Cl3sUry2bqc(int i, ChildUserTaskBarServiceConnection childUserTaskBarServiceConnection) {
            try {
                if (childUserTaskBarServiceConnection.mITaskBarService != null) {
                    childUserTaskBarServiceConnection.mITaskBarService.onDisplayRemoved(i);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public static /* synthetic */ void $r8$lambda$ao4Mnv4yAbWCWmJd46ESzkzn6Mo(int i, boolean z, ChildUserTaskBarServiceConnection childUserTaskBarServiceConnection) {
            try {
                if (childUserTaskBarServiceConnection.mITaskBarService != null) {
                    childUserTaskBarServiceConnection.mITaskBarService.updateImeVisible(i, z);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public static /* synthetic */ void $r8$lambda$eZM5YN6aE_9rUzkqGhyoUAz0BwE(int i, ChildUserTaskBarServiceConnection childUserTaskBarServiceConnection) {
            try {
                if (childUserTaskBarServiceConnection.mITaskBarService != null) {
                    childUserTaskBarServiceConnection.mITaskBarService.onDisplayReady(i);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public static /* synthetic */ void $r8$lambda$tPo5HF0JnZTWgRuKMPcM8H55puQ(int i, int i2, ChildUserTaskBarServiceConnection childUserTaskBarServiceConnection) {
            try {
                if (childUserTaskBarServiceConnection.mITaskBarService != null) {
                    childUserTaskBarServiceConnection.mITaskBarService.onTaskbarWindowStateChanged(i, i2);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: renamed from: $r8$lambda$utzMIoAqZXW-40fCOzB2ThBZe8s, reason: not valid java name */
        public static /* synthetic */ void m2277$r8$lambda$utzMIoAqZXW40fCOzB2ThBZe8s(int i, int i2, ChildUserTaskBarServiceConnection childUserTaskBarServiceConnection) {
            try {
                if (childUserTaskBarServiceConnection.mITaskBarService != null) {
                    childUserTaskBarServiceConnection.mITaskBarService.setTaskBarViewVisibility(i, i2);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayReady$5(final int i) {
            TaskBarChildUserServerService.this.loadChildUserTaskBarServiceConnectionList().forEach(new Consumer() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskBarChildUserServerService.AnonymousClass4.$r8$lambda$eZM5YN6aE_9rUzkqGhyoUAz0BwE(i, (TaskBarChildUserServerService.ChildUserTaskBarServiceConnection) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayRemoved$3(final int i) {
            TaskBarChildUserServerService.this.loadChildUserTaskBarServiceConnectionList().forEach(new Consumer() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskBarChildUserServerService.AnonymousClass4.$r8$lambda$Z4ek1hxtzoVHIvj1Cl3sUry2bqc(i, (TaskBarChildUserServerService.ChildUserTaskBarServiceConnection) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestDisplayChooserModeInSecondUser$18() {
            ChildUserTaskBarServiceConnection childUserTaskBarServiceConnection = (ChildUserTaskBarServiceConnection) TaskBarChildUserServerService.this.mChildUserTaskBarServiceConnections.get(TaskBarChildUserServerService.this.mDeviceProvisionedController.getCurrentUser());
            if (childUserTaskBarServiceConnection == null || childUserTaskBarServiceConnection.mITaskBarService == null) {
                return;
            }
            try {
                childUserTaskBarServiceConnection.mITaskBarService.requestDisplayChooserMode();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemUIReady$1() {
            TaskBarChildUserServerService.this.loadChildUserTaskBarServiceConnectionList().forEach(new Consumer() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskBarChildUserServerService.AnonymousClass4.$r8$lambda$5kpO_S3j1XNBzsd3rXDV2vCIzmk((TaskBarChildUserServerService.ChildUserTaskBarServiceConnection) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskbarWindowStateChanged$15(final int i, final int i2) {
            TaskBarChildUserServerService.this.loadChildUserTaskBarServiceConnectionList().forEach(new Consumer() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskBarChildUserServerService.AnonymousClass4.$r8$lambda$tPo5HF0JnZTWgRuKMPcM8H55puQ(i, i2, (TaskBarChildUserServerService.ChildUserTaskBarServiceConnection) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setTaskBarImeSwitchButtonVisible$11(final int i, final boolean z) {
            TaskBarChildUserServerService.this.loadChildUserTaskBarServiceConnectionList().forEach(new Consumer() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskBarChildUserServerService.AnonymousClass4.m2273$r8$lambda$DKG0sjZU33ePB_z6OBZVVnkqQ(i, z, (TaskBarChildUserServerService.ChildUserTaskBarServiceConnection) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setTaskBarTransitionMode$7(final int i, final int i2) {
            TaskBarChildUserServerService.this.loadChildUserTaskBarServiceConnectionList().forEach(new Consumer() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskBarChildUserServerService.AnonymousClass4.m2274$r8$lambda$NWMCONP6IKbQkgUYsEnDV8TFIc(i, i2, (TaskBarChildUserServerService.ChildUserTaskBarServiceConnection) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setTaskBarViewVisibility$9(final int i, final int i2) {
            TaskBarChildUserServerService.this.loadChildUserTaskBarServiceConnectionList().forEach(new Consumer() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskBarChildUserServerService.AnonymousClass4.m2277$r8$lambda$utzMIoAqZXW40fCOzB2ThBZe8s(i, i2, (TaskBarChildUserServerService.ChildUserTaskBarServiceConnection) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateImeVisible$13(final int i, final boolean z) {
            TaskBarChildUserServerService.this.loadChildUserTaskBarServiceConnectionList().forEach(new Consumer() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskBarChildUserServerService.AnonymousClass4.$r8$lambda$ao4Mnv4yAbWCWmJd46ESzkzn6Mo(i, z, (TaskBarChildUserServerService.ChildUserTaskBarServiceConnection) obj);
                }
            });
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void addDesktopIcon(String str, int i, StatusBarIcon statusBarIcon, PendingIntent pendingIntent) {
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onDisplayReady(final int i) {
            TaskBarChildUserServerService.this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDisplayReady$5(i);
                }
            });
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onDisplayRemoved(final int i) {
            TaskBarChildUserServerService.this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDisplayRemoved$3(i);
                }
            });
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onRequestDisplayChooserModeInSecondUser() {
            TaskBarChildUserServerService.this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onRequestDisplayChooserModeInSecondUser$18();
                }
            });
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onSystemUIGone() {
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onSystemUIReady() {
            TaskBarChildUserServerService.this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSystemUIReady$1();
                }
            });
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onTaskbarWindowStateChanged(final int i, final int i2) {
            TaskBarChildUserServerService.this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onTaskbarWindowStateChanged$15(i, i2);
                }
            });
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void removeDesktopIcon(String str, int i) {
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void setTaskBarImeSwitchButtonVisible(final int i, final boolean z) {
            TaskBarChildUserServerService.this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setTaskBarImeSwitchButtonVisible$11(i, z);
                }
            });
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void setTaskBarTransitionMode(final int i, final int i2) {
            TaskBarChildUserServerService.this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setTaskBarTransitionMode$7(i, i2);
                }
            });
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void setTaskBarViewVisibility(final int i, final int i2) {
            TaskBarChildUserServerService.this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setTaskBarViewVisibility$9(i, i2);
                }
            });
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void updateImeVisible(final int i, final boolean z) {
            TaskBarChildUserServerService.this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$4$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateImeVisible$13(i, z);
                }
            });
        }
    }

    class ChildUserTaskBarServiceConnection implements ServiceConnection {
        private final String TAG;
        private final int mUserId;
        private ITaskBarService mITaskBarService = null;
        private boolean mBindingTaskBarService = false;

        ChildUserTaskBarServiceConnection(int i) {
            this.mUserId = i;
            this.TAG = "TaskBarChildUserServerService-" + i;
        }

        private void onBindedTaskBarService() {
        }

        public void bindMotoTaskBarServiceIfNeed() {
            if (this.mITaskBarService != null || this.mBindingTaskBarService) {
                if (TaskBarChildUserServerService.DEBUG) {
                    Log.d(this.TAG, "bindMotoTaskBarServiceIfNeed return: " + this.mITaskBarService + "; binding: " + this.mBindingTaskBarService);
                    return;
                }
                return;
            }
            if (!TaskBarChildUserServerService.this.mUserManager.isUserRunning(UserHandle.of(this.mUserId))) {
                if (TaskBarChildUserServerService.DEBUG) {
                    Log.d(this.TAG, "bindMotoTaskBarServiceIfNeed user is not running: " + this.mUserId);
                    return;
                }
                return;
            }
            if (TaskBarChildUserServerService.DEBUG) {
                Log.d(this.TAG, "start bindMotoTaskBarServiceIfNeed: " + this.mUserId);
            }
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(TaskBarChildUserServerService.this.getPackageName(), TaskBarService.class.getName()));
            if (TaskBarChildUserServerService.this.bindServiceAsUser(intent, this, 1, UserHandle.of(this.mUserId))) {
                this.mBindingTaskBarService = true;
            } else {
                Log.i(this.TAG, "bindMotoTaskBarServiceIfNeed error");
                this.mITaskBarService = null;
            }
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            Log.e(this.TAG, "onBindingDied");
            this.mITaskBarService = null;
            this.mBindingTaskBarService = false;
            bindMotoTaskBarServiceIfNeed();
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            Log.e(this.TAG, "onNullBinding");
            this.mITaskBarService = null;
            this.mBindingTaskBarService = false;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(this.TAG, "onServiceConnected");
            this.mBindingTaskBarService = false;
            ITaskBarService iTaskBarServiceAsInterface = ITaskBarService.Stub.asInterface(iBinder);
            this.mITaskBarService = iTaskBarServiceAsInterface;
            try {
                iTaskBarServiceAsInterface.setTaskBarProxy(TaskBarChildUserServerService.this.mITaskBarProxy);
                this.mITaskBarService.setMainUserTaskBarProxy(TaskBarChildUserServerService.this.mIMainUserTaskBarProxy);
                this.mITaskBarService.onSystemUIReady();
                onBindedTaskBarService();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(this.TAG, "onServiceDisconnected");
            this.mITaskBarService = null;
            this.mBindingTaskBarService = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindToChildUserTaskBarService(int i) {
        ChildUserTaskBarServiceConnection childUserTaskBarServiceConnection;
        if (i == 0) {
            return;
        }
        synchronized (this.mChildUserTaskBarServiceConnections) {
            try {
                childUserTaskBarServiceConnection = (ChildUserTaskBarServiceConnection) this.mChildUserTaskBarServiceConnections.get(i);
                if (childUserTaskBarServiceConnection == null) {
                    childUserTaskBarServiceConnection = new ChildUserTaskBarServiceConnection(i);
                    this.mChildUserTaskBarServiceConnections.put(i, childUserTaskBarServiceConnection);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        childUserTaskBarServiceConnection.bindMotoTaskBarServiceIfNeed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(UserInfo userInfo) {
        bindToChildUserTaskBarService(userInfo.id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List loadChildUserTaskBarServiceConnectionList() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mChildUserTaskBarServiceConnections) {
            for (int i = 0; i < this.mChildUserTaskBarServiceConnections.size(); i++) {
                try {
                    arrayList.add((ChildUserTaskBarServiceConnection) this.mChildUserTaskBarServiceConnections.valueAt(i));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return arrayList;
    }

    private void unBindAllChildUserTaskBarService() {
        synchronized (this.mChildUserTaskBarServiceConnections) {
            for (int i = 0; i < this.mChildUserTaskBarServiceConnections.size(); i++) {
                try {
                    ChildUserTaskBarServiceConnection childUserTaskBarServiceConnection = (ChildUserTaskBarServiceConnection) this.mChildUserTaskBarServiceConnections.valueAt(i);
                    if (childUserTaskBarServiceConnection != null && childUserTaskBarServiceConnection.mITaskBarService != null) {
                        unbindService(childUserTaskBarServiceConnection);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mChildUserTaskBarServiceConnections.clear();
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (DEBUG) {
            Log.d("TaskBarChildUserServerService", "onCreate: " + Process.myUserHandle());
        }
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            Log.w("TaskBarChildUserServerService", "onCreate on not system user: " + Process.myUserHandle());
            stopSelf();
            return;
        }
        SystemUIFactory.getInstance().getRootComponent().inject(this);
        this.mUserManager = (UserManager) getSystemService(UserManager.class);
        this.mTaskBarServiceProxy.addCallback(this.mTaskBarServiceCallBack);
        this.mDeviceProvisionedController.addCallback(this.mDeviceProvisionedListener);
        List users = this.mUserManager.getUsers(true);
        if (users != null) {
            users.forEach(new Consumer() { // from class: com.motorola.taskbar.TaskBarChildUserServerService$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$onCreate$0((UserInfo) obj);
                }
            });
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG) {
            Log.d("TaskBarChildUserServerService", "onDestroy: " + Process.myUserHandle());
        }
        TaskBarServiceProxy taskBarServiceProxy = this.mTaskBarServiceProxy;
        if (taskBarServiceProxy != null) {
            taskBarServiceProxy.removeCallback(this.mTaskBarServiceCallBack);
        }
        DeviceProvisionedController.DeviceProvisionedListener deviceProvisionedListener = this.mDeviceProvisionedListener;
        if (deviceProvisionedListener != null) {
            this.mDeviceProvisionedController.removeCallback(deviceProvisionedListener);
        }
        unBindAllChildUserTaskBarService();
    }
}
