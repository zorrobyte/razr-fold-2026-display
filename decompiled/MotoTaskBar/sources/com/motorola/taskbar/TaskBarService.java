package com.motorola.taskbar;

import android.app.PendingIntent;
import android.app.Service;
import android.app.StatusBarManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.SystemUIFactory;
import com.motorola.taskbar.ITaskBarService;
import com.motorola.taskbar.util.DebugConfig;

/* JADX INFO: loaded from: classes2.dex */
public class TaskBarService extends Service {
    private IMainUserTaskBarProxy mIMainUserTaskBarProxy;
    private ITaskBarProxy mITaskBarProxy;
    protected TaskBarServiceProxy mTaskBarServiceProxy;
    private static final String TAG = TaskBarService.class.getSimpleName();
    private static final boolean DEBUG = DebugConfig.DEBUG_TASK_BAR_SERVICE;
    private boolean mBindingTaskBarChildUserServerService = false;
    private IBinder mTaskBarChildUserServerService = null;
    private final ServiceConnection mTaskBarChildUserServerServiceConnection = new ServiceConnection() { // from class: com.motorola.taskbar.TaskBarService.1
        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            Log.e(TaskBarService.TAG, "onBindingDied");
            TaskBarService.this.mTaskBarChildUserServerService = null;
            TaskBarService.this.mBindingTaskBarChildUserServerService = false;
            TaskBarService.this.bindTaskBarChildUserServerServiceIfNeed();
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            Log.e(TaskBarService.TAG, "onNullBinding");
            TaskBarService.this.mTaskBarChildUserServerService = null;
            TaskBarService.this.mBindingTaskBarChildUserServerService = false;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TaskBarService.TAG, "onServiceConnected");
            TaskBarService.this.mBindingTaskBarChildUserServerService = false;
            TaskBarService.this.mTaskBarChildUserServerService = iBinder;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TaskBarService.TAG, "onServiceDisconnected");
            TaskBarService.this.mTaskBarChildUserServerService = null;
            TaskBarService.this.mBindingTaskBarChildUserServerService = false;
        }
    };
    private ITaskBarService.Stub mBinder = new ITaskBarService.Stub() { // from class: com.motorola.taskbar.TaskBarService.2
        @Override // com.motorola.taskbar.ITaskBarService
        public void addDesktopIcon(String str, int i, StatusBarIcon statusBarIcon, PendingIntent pendingIntent) {
            TaskBarService.this.mTaskBarServiceProxy.addDesktopIcon(str, i, statusBarIcon, pendingIntent);
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void onDisplayReady(int i) {
            if (TaskBarService.DEBUG) {
                Log.d(TaskBarService.TAG, "onDisplayReady: " + i);
            }
            TaskBarService.this.mTaskBarServiceProxy.onDisplayReady(i);
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void onDisplayRemoved(int i) {
            if (TaskBarService.DEBUG) {
                Log.d(TaskBarService.TAG, "onDisplayRemoved: " + i);
            }
            TaskBarService.this.mTaskBarServiceProxy.onDisplayRemoved(i);
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void onNavIconClicked() {
            if (TaskBarService.DEBUG) {
                Log.d(TaskBarService.TAG, "onNavIconClicked");
            }
            TaskBarService.this.mTaskBarServiceProxy.onNavIconClicked();
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void onOverviewShown() {
            TaskBarService.this.mTaskBarServiceProxy.onOverviewShown();
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void onQSTileDataUpdated(DesktopQSTileData desktopQSTileData) {
            TaskBarService.this.mTaskBarServiceProxy.onQSTileDataUpdated(desktopQSTileData);
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void onSystemUIReady() {
            if (TaskBarService.DEBUG) {
                Log.d(TaskBarService.TAG, "onSystemUIReady");
            }
            TaskBarService.this.mTaskBarServiceProxy.onSystemUIReady();
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void onTaskbarWindowStateChanged(int i, int i2) {
            if (TaskBarService.DEBUG) {
                Log.d(TaskBarService.TAG, "onTaskbarWindowStateChanged: " + i + "; state = " + StatusBarManager.windowStateToString(i2));
            }
            TaskBarService.this.mTaskBarServiceProxy.onTaskbarWindowStateChanged(i, i2);
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void removeDesktopIcon(String str, int i) {
            TaskBarService.this.mTaskBarServiceProxy.removeDesktopIcon(str, i);
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void requestDisplayChooserMode() {
            TaskBarService.this.mTaskBarServiceProxy.requestDisplayChooserMode();
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void setMainUserTaskBarProxy(IMainUserTaskBarProxy iMainUserTaskBarProxy) {
            if (TaskBarService.DEBUG) {
                Log.d(TaskBarService.TAG, "setTaskBarProxy");
            }
            TaskBarService.this.mIMainUserTaskBarProxy = iMainUserTaskBarProxy;
            TaskBarService taskBarService = TaskBarService.this;
            taskBarService.mTaskBarServiceProxy.setMainUserTaskBarProxy(taskBarService.mIMainUserTaskBarProxy);
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void setTaskBarImeSwitchButtonVisible(int i, boolean z) {
            if (TaskBarService.DEBUG) {
                Log.d(TaskBarService.TAG, "setTaskBarImeSwitchButtonVisible,: " + i + "; visible = " + z);
            }
            TaskBarService.this.mTaskBarServiceProxy.setTaskBarImeSwitchButtonVisible(i, z);
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void setTaskBarProxy(ITaskBarProxy iTaskBarProxy) {
            if (TaskBarService.DEBUG) {
                Log.d(TaskBarService.TAG, "setTaskBarProxy");
            }
            TaskBarService.this.mITaskBarProxy = iTaskBarProxy;
            TaskBarService taskBarService = TaskBarService.this;
            taskBarService.mTaskBarServiceProxy.setTaskBarProxy(taskBarService.mITaskBarProxy);
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void setTaskBarTransitionMode(int i, int i2) {
            if (TaskBarService.DEBUG) {
                Log.d(TaskBarService.TAG, "setTaskBarTransitionMode: " + i);
            }
            TaskBarService.this.mTaskBarServiceProxy.setTaskBarTransitionMode(i, i2);
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void setTaskBarViewVisibility(int i, int i2) {
            if (TaskBarService.DEBUG) {
                Log.d(TaskBarService.TAG, "setTaskBarViewVisibility: " + i + "; visibility = " + i2);
            }
            TaskBarService.this.mTaskBarServiceProxy.setTaskBarViewVisibility(i, i2);
        }

        @Override // com.motorola.taskbar.ITaskBarService
        public void updateImeVisible(int i, boolean z) {
            TaskBarService.this.mTaskBarServiceProxy.updateImeVisible(i, z);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void bindTaskBarChildUserServerServiceIfNeed() {
        if (this.mBindingTaskBarChildUserServerService || this.mTaskBarChildUserServerService != null) {
            return;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(getPackageName(), TaskBarChildUserServerService.class.getName()));
        if (bindService(intent, this.mTaskBarChildUserServerServiceConnection, 1)) {
            this.mBindingTaskBarChildUserServerService = true;
        } else {
            Log.i(TAG, "bind TaskBarChildUserServerService error");
            this.mTaskBarChildUserServerService = null;
        }
    }

    private void unBindTaskBarChildUserServerService() {
        if (this.mTaskBarChildUserServerService != null) {
            unbindService(this.mTaskBarChildUserServerServiceConnection);
            this.mTaskBarChildUserServerService = null;
            this.mBindingTaskBarChildUserServerService = false;
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        SystemUIFactory.getInstance().getRootComponent().inject(this);
        if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            bindTaskBarChildUserServerServiceIfNeed();
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        unBindTaskBarChildUserServerService();
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        super.onUnbind(intent);
        this.mTaskBarServiceProxy.setTaskBarProxy(null);
        this.mTaskBarServiceProxy.onSystemUIGone();
        return false;
    }
}
