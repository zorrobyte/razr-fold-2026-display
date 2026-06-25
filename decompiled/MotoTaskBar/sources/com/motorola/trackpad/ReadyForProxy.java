package com.motorola.trackpad;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.motorola.controller.ITrackpadProxy;
import com.motorola.controller.ITrackpadService;
import com.motorola.taskbar.IReadyForTaskbarProxy;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.TaskBarServiceCallBack;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.bar.ExternalDisplayModeManager;
import com.motorola.taskbar.bar.MirrorPhonePanelController;
import com.motorola.taskbar.bar.QSNotificationPanelController;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.taskbar.model.HardwareDisplayController;
import com.motorola.taskbar.shortcut.ShortcutKeyDispatcher;
import com.motorola.trackpad.ReadyForProxy;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes2.dex */
public class ReadyForProxy {
    private static final boolean DEBUG = !Build.IS_USER;
    private static final String TAG = "ReadyForProxy";
    private final Context mContext;
    private final Handler mHandler;
    private final HardwareDisplayController mHardwareDisplayController;
    private final MotoFeature mMotoFeature;
    private TaskBarController mTaskBarController;
    private final TaskBarServiceProxy mTaskBarServiceProxy;
    private final TrackpadGestureHandler mTrackpadGestureHandler;
    private ITrackpadService mReadyForService = null;
    private boolean mBindingTrackpadService = false;
    private boolean mPendingStartTrackpad = false;
    private int mPendingExternalDisplayId = -1;
    private boolean mPendingMirrorToDesktop = false;
    private CopyOnWriteArrayList mChooseListeners = new CopyOnWriteArrayList();
    private int mRetryBindCount = 0;
    private boolean mPendingStartExternalTutorial = false;
    private CopyOnWriteArrayList mReadyForServiceStatusListeners = new CopyOnWriteArrayList();
    private DeviceProvisionedController.DeviceProvisionedListener mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.motorola.trackpad.ReadyForProxy.1
        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onUserSwitched() {
            if (ReadyForProxy.this.mCurrentUserId != ActivityManager.getCurrentUser()) {
                ReadyForProxy.this.mCurrentUserId = ActivityManager.getCurrentUser();
                if (!ReadyForProxy.this.isReadyForPkgAvailable()) {
                    Log.d(ReadyForProxy.TAG, "onUserSwitched, Readyfor app isn't available, switch to mirror mode ");
                    ((ExternalDisplayModeManager) Dependency.get(ExternalDisplayModeManager.class)).setDesktopEnable(false);
                }
                ReadyForProxy.this.reBindTrackpadServiceIfNeed();
            }
        }
    };
    private final Runnable mRetryBindRunnable = new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy.2
        @Override // java.lang.Runnable
        public void run() {
            ReadyForProxy.this.mHandler.removeCallbacks(ReadyForProxy.this.mRetryBindRunnable);
            ReadyForProxy.this.bindTrackpadServiceIfNeed();
        }
    };
    private final ServiceConnection mTrackpadConnection = new ServiceConnection() { // from class: com.motorola.trackpad.ReadyForProxy.3
        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            Log.i(ReadyForProxy.TAG, "onBindingDied: ");
            ReadyForProxy.this.resetData();
            ReadyForProxy.this.bindTrackpadServiceIfNeed();
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            Log.i(ReadyForProxy.TAG, "onNullBinding: ");
            ReadyForProxy.this.resetData();
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(ReadyForProxy.TAG, "onServiceConnected:  mPendingStartExternalTutorial: " + ReadyForProxy.this.mPendingStartExternalTutorial);
            ReadyForProxy.this.mBindingTrackpadService = false;
            ReadyForProxy.this.mReadyForService = ITrackpadService.Stub.asInterface(iBinder);
            ReadyForProxy.this.mHandler.removeCallbacks(ReadyForProxy.this.mRetryBindRunnable);
            ReadyForProxy.this.mRetryBindCount = 0;
            try {
                ReadyForProxy.this.mReadyForService.setTrackpadProxy(ReadyForProxy.this.mITrackpadProxy);
                ReadyForProxy.this.mReadyForService.setReadyForTaskbarProxy(ReadyForProxy.this.mIReadyForTaskbarProxy);
                if (ReadyForProxy.this.mPendingStartTrackpad && ReadyForProxy.this.mPendingExternalDisplayId == ReadyForProxy.this.mHardwareDisplayController.getDisplayId()) {
                    ReadyForProxy readyForProxy = ReadyForProxy.this;
                    readyForProxy.startTrackpadActivity(readyForProxy.mPendingExternalDisplayId);
                    ReadyForProxy.this.mPendingStartTrackpad = false;
                    ReadyForProxy.this.mPendingMirrorToDesktop = false;
                    ReadyForProxy.this.mPendingExternalDisplayId = -1;
                }
                if (ReadyForProxy.this.mPendingStartExternalTutorial) {
                    ReadyForProxy.this.mPendingStartExternalTutorial = false;
                    ReadyForProxy readyForProxy2 = ReadyForProxy.this;
                    readyForProxy2.startExternalDisplayTutorialOrderActivity(readyForProxy2.mHardwareDisplayController.getDisplayId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ReadyForProxy readyForProxy3 = ReadyForProxy.this;
            readyForProxy3.notifyReadyForServiceStatusChanged(readyForProxy3.mHardwareDisplayController.getDisplayId(), true);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(ReadyForProxy.TAG, "onServiceDisconnected: ");
            ReadyForProxy.this.resetData();
            ReadyForProxy.this.mTaskBarServiceProxy.onTrackpadStateChanged(false);
            ReadyForProxy readyForProxy = ReadyForProxy.this;
            readyForProxy.notifyReadyForServiceStatusChanged(readyForProxy.mHardwareDisplayController.getDisplayId(), false);
        }
    };
    private final ITrackpadProxy mITrackpadProxy = new AnonymousClass4();
    private final IReadyForTaskbarProxy mIReadyForTaskbarProxy = new AnonymousClass5();
    private HardwareDisplayController.HardwareDisplayListener mHardwareDisplayListener = new AnonymousClass6();
    private final Runnable mUnBindTrackpadService = new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy.7
        @Override // java.lang.Runnable
        public void run() {
            if (ReadyForProxy.this.mReadyForService != null) {
                ReadyForProxy.this.mContext.unbindService(ReadyForProxy.this.mTrackpadConnection);
                ReadyForProxy.this.resetData();
            }
        }
    };
    private TaskBarServiceCallBack mTaskBarServiceCallBack = new TaskBarServiceCallBack() { // from class: com.motorola.trackpad.ReadyForProxy.8
        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onBootComplete() {
            Log.i(ReadyForProxy.TAG, "onBootComplete: ");
            ReadyForProxy.this.bindTrackpadServiceIfNeed();
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onNavIconClicked() {
            if (ReadyForProxy.this.isValidHDMIOrWfdDisplay()) {
                ReadyForProxy readyForProxy = ReadyForProxy.this;
                readyForProxy.startTrackpadActivity(readyForProxy.mHardwareDisplayController.getDisplayId(), true);
                return;
            }
            Log.w(ReadyForProxy.TAG, "Nav Icon Clicked error, display invalid : " + ReadyForProxy.this.mHardwareDisplayController.getDisplayId());
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void updateImeVisible(int i, boolean z) {
            if (ReadyForProxy.this.mReadyForService != null) {
                try {
                    Log.d(ReadyForProxy.TAG, "updateImeVisible: " + z);
                    ReadyForProxy.this.mReadyForService.updateImeVisible(i, z);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private int mCurrentUserId = ActivityManager.getCurrentUser();

    /* JADX INFO: renamed from: com.motorola.trackpad.ReadyForProxy$4, reason: invalid class name */
    class AnonymousClass4 extends ITrackpadProxy.Stub {
        AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleGesture$2(int i) {
            ReadyForProxy.this.mTrackpadGestureHandler.handleGesture(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTrackpadStateChanged$3(boolean z) {
            ReadyForProxy.this.mTaskBarServiceProxy.onTrackpadStateChanged(z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestBackToHome$0() {
            ((ShortcutKeyDispatcher) Dependency.get(ShortcutKeyDispatcher.class)).handleAirRemoteHome(ReadyForProxy.this.mHardwareDisplayController.getDisplayId());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestNavTrackpadGuide$4(boolean z) {
            ReadyForProxy.this.mTaskBarServiceProxy.requestNavTrackpadGuide(z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestShowDesktopSplashScreen$1() {
            if (ReadyForProxy.this.mCurrentUserId == 0) {
                ReadyForProxy.this.mTaskBarServiceProxy.requestDisplayChooserMode();
            } else {
                ReadyForProxy.this.mTaskBarServiceProxy.onRequestDisplayChooserModeInSecondUser();
            }
        }

        @Override // com.motorola.controller.ITrackpadProxy
        public void handleGesture(final int i) {
            if (ReadyForProxy.this.isValidHDMIOrWfdDisplay()) {
                ReadyForProxy.this.mHandler.post(new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy$4$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$handleGesture$2(i);
                    }
                });
                return;
            }
            Log.w(ReadyForProxy.TAG, "handleThreeFingerGesture error, display invalid : " + ReadyForProxy.this.mHardwareDisplayController.getDisplayId());
        }

        @Override // com.motorola.controller.ITrackpadProxy
        public void onTrackpadStateChanged(final boolean z) {
            if (ReadyForProxy.this.isValidHDMIOrWfdDisplay() || !z) {
                ReadyForProxy.this.mHandler.post(new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy$4$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onTrackpadStateChanged$3(z);
                    }
                });
                return;
            }
            Log.w(ReadyForProxy.TAG, "TrackpadStateChanged error, display invalid : " + ReadyForProxy.this.mHardwareDisplayController.getDisplayId());
        }

        @Override // com.motorola.controller.ITrackpadProxy
        public void requestBackToHome() {
            if (ReadyForProxy.this.isValidHDMIOrWfdDisplay()) {
                ReadyForProxy.this.mHandler.post(new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy$4$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$requestBackToHome$0();
                    }
                });
                return;
            }
            Log.w(ReadyForProxy.TAG, "requestBackToHome, display invalid : " + ReadyForProxy.this.mHardwareDisplayController.getDisplayId());
        }

        @Override // com.motorola.controller.ITrackpadProxy
        public void requestNavTrackpadGuide(final boolean z) {
            if (ReadyForProxy.this.isValidHDMIOrWfdDisplay()) {
                ReadyForProxy.this.mHandler.post(new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$requestNavTrackpadGuide$4(z);
                    }
                });
                return;
            }
            Log.w(ReadyForProxy.TAG, "requestNavTrackpadGuide error, display invalid : " + ReadyForProxy.this.mHardwareDisplayController.getDisplayId());
        }

        @Override // com.motorola.controller.ITrackpadProxy
        public void requestShowDesktopSplashScreen() {
            if (ReadyForProxy.this.isValidHDMIOrWfdDisplay()) {
                ReadyForProxy.this.mHandler.post(new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy$4$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$requestShowDesktopSplashScreen$1();
                    }
                });
                return;
            }
            Log.w(ReadyForProxy.TAG, "requestShowDesktopSplashScreen error, display invalid : " + ReadyForProxy.this.mHardwareDisplayController.getDisplayId());
        }
    }

    /* JADX INFO: renamed from: com.motorola.trackpad.ReadyForProxy$5, reason: invalid class name */
    class AnonymousClass5 extends IReadyForTaskbarProxy.Stub {
        public static /* synthetic */ void $r8$lambda$BGMjjueuFO6Dh5yVwnuBa6LcpEs(boolean z, int i) {
            QSNotificationPanelController qSNotificationPanelController = (QSNotificationPanelController) Dependency.get(QSNotificationPanelController.class);
            if (z) {
                qSNotificationPanelController.requestShowNotificationPanel(i);
            } else {
                qSNotificationPanelController.requestHideNotificationPanel(i);
            }
        }

        AnonymousClass5() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayModeSelected$0(String str, int i) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            ReadyForProxy.this.mTaskBarController.updateDisplayChooserMode(i, str);
            if ("normal".equals(str)) {
                return;
            }
            ((QSNotificationPanelController) Dependency.get(QSNotificationPanelController.class)).requestHideNotificationPanel(i);
            ((MirrorPhonePanelController) Dependency.get(MirrorPhonePanelController.class)).requestHidePanel(i);
            ((TaskBarController) Dependency.get(TaskBarController.class)).requestHideVolumeDialog(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onModeChooserAnimationStateChanged$1(String str, int i, boolean z) {
            ReadyForProxy.this.notifyChooserAnimStateChanged(str, i, z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onModeChooserShowingStateChanged$2(int i, boolean z) {
            ReadyForProxy.this.notifyModeChooserShowingStateChanged(i, z);
        }

        @Override // com.motorola.taskbar.IReadyForTaskbarProxy
        public int aidlVersion() {
            return 2;
        }

        @Override // com.motorola.taskbar.IReadyForTaskbarProxy
        public void onDisplayModeSelected(final String str, final int i) {
            ReadyForProxy.this.mHandler.post(new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy$5$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDisplayModeSelected$0(str, i);
                }
            });
        }

        @Override // com.motorola.taskbar.IReadyForTaskbarProxy
        public void onModeChooserAnimationStateChanged(final String str, final int i, final boolean z) {
            ReadyForProxy.this.mHandler.post(new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy$5$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onModeChooserAnimationStateChanged$1(str, i, z);
                }
            });
        }

        @Override // com.motorola.taskbar.IReadyForTaskbarProxy
        public void onModeChooserShowingStateChanged(final int i, final boolean z) {
            ReadyForProxy.this.mHandler.post(new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy$5$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onModeChooserShowingStateChanged$2(i, z);
                }
            });
        }

        @Override // com.motorola.taskbar.IReadyForTaskbarProxy
        public void requestQSNotificationPanel(final int i, final boolean z) {
            ReadyForProxy.this.mHandler.post(new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ReadyForProxy.AnonymousClass5.$r8$lambda$BGMjjueuFO6Dh5yVwnuBa6LcpEs(z, i);
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.motorola.trackpad.ReadyForProxy$6, reason: invalid class name */
    class AnonymousClass6 implements HardwareDisplayController.HardwareDisplayListener {
        AnonymousClass6() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHardwareDisplayAdded$0() {
            if (ReadyForProxy.this.isReadyForPkgAvailable()) {
                ReadyForProxy.this.bindTrackpadServiceIfNeed();
            } else {
                Log.d(ReadyForProxy.TAG, "onHardwareDisplayAdded, Readyfor app isn't available, switch to mirror mode ");
                ((ExternalDisplayModeManager) Dependency.get(ExternalDisplayModeManager.class)).setDesktopEnable(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHardwareDisplayIdChanged$1(boolean z, int i, int i2) {
            if (z && ReadyForProxy.this.mPendingStartTrackpad && ReadyForProxy.this.mPendingMirrorToDesktop && i == ReadyForProxy.this.mPendingExternalDisplayId) {
                ReadyForProxy.this.mPendingStartTrackpad = false;
                ReadyForProxy.this.mPendingMirrorToDesktop = false;
                ReadyForProxy.this.mPendingExternalDisplayId = -1;
                ReadyForProxy.this.startTrackpadActivity(i2);
            }
        }

        @Override // com.motorola.taskbar.model.HardwareDisplayController.HardwareDisplayListener
        public void onHardwareDisplayAdded(int i, int i2, boolean z) {
            ReadyForProxy.this.mHandler.post(new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy$6$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onHardwareDisplayAdded$0();
                }
            });
        }

        @Override // com.motorola.taskbar.model.HardwareDisplayController.HardwareDisplayListener
        public void onHardwareDisplayIdChanged(int i, final int i2, final int i3, final boolean z) {
            ReadyForProxy.this.mHandler.post(new Runnable() { // from class: com.motorola.trackpad.ReadyForProxy$6$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onHardwareDisplayIdChanged$1(z, i2, i3);
                }
            });
        }

        @Override // com.motorola.taskbar.model.HardwareDisplayController.HardwareDisplayListener
        public void onHardwareDisplayRemoved(int i, int i2) {
            ReadyForProxy.this.mHandler.postDelayed(ReadyForProxy.this.mUnBindTrackpadService, 180000L);
        }
    }

    public interface OnModeChooserListener {
        default void onModeChooserAnimationStateChange(String str, int i, boolean z) {
        }

        void onModeChooserShowingStateChanged(int i, boolean z);
    }

    public interface OnReadyForServiceStatusListener {
        void onStatusChanged(int i, boolean z);
    }

    public ReadyForProxy(Context context, Handler handler, MotoFeature motoFeature, TaskBarServiceProxy taskBarServiceProxy, HardwareDisplayController hardwareDisplayController, TrackpadGestureHandler trackpadGestureHandler, TaskBarController taskBarController) {
        this.mContext = context;
        this.mHandler = handler;
        this.mMotoFeature = motoFeature;
        this.mTaskBarServiceProxy = taskBarServiceProxy;
        this.mHardwareDisplayController = hardwareDisplayController;
        this.mTrackpadGestureHandler = trackpadGestureHandler;
        this.mTaskBarController = taskBarController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindTrackpadServiceIfNeed() {
        this.mHandler.removeCallbacks(this.mUnBindTrackpadService);
        if (this.mBindingTrackpadService || this.mReadyForService != null || this.mHardwareDisplayController.getDisplayId() == -1 || !isReadyForPkgAvailable()) {
            return;
        }
        if (DEBUG) {
            Log.d(TAG, "bind TrackpadService");
        }
        Intent intent = new Intent();
        intent.setPackage("com.motorola.mobiledesktop");
        intent.setAction("com.motorola.trackpad.action.BIND_SERVICE");
        if (this.mContext.bindServiceAsUser(intent, this.mTrackpadConnection, 1, UserHandle.CURRENT)) {
            this.mBindingTrackpadService = true;
            return;
        }
        Log.i(TAG, "bind Trackpad Service error");
        this.mReadyForService = null;
        int i = this.mRetryBindCount;
        if (i < 5) {
            this.mRetryBindCount = i + 1;
            this.mHandler.postDelayed(this.mRetryBindRunnable, 1000L);
        } else {
            this.mHandler.removeCallbacks(this.mRetryBindRunnable);
            this.mRetryBindCount = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValidHDMIOrWfdDisplay() {
        return this.mMotoFeature.isValidHDMIOrWfdDisplay(this.mHardwareDisplayController.getDisplayId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reBindTrackpadServiceIfNeed() {
        if (DEBUG) {
            Log.d(TAG, "reBindTrackpadServiceIfNeed: ");
        }
        if (this.mReadyForService != null) {
            this.mContext.unbindService(this.mTrackpadConnection);
        }
        resetData();
        bindTrackpadServiceIfNeed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetData() {
        this.mReadyForService = null;
        this.mBindingTrackpadService = false;
        this.mPendingStartTrackpad = false;
        this.mPendingMirrorToDesktop = false;
        this.mPendingExternalDisplayId = -1;
        this.mPendingStartExternalTutorial = false;
        this.mHandler.removeCallbacks(this.mRetryBindRunnable);
        this.mRetryBindCount = 0;
    }

    public void addModeChooserListener(OnModeChooserListener onModeChooserListener) {
        if (onModeChooserListener != null) {
            this.mChooseListeners.add(onModeChooserListener);
        }
    }

    public void addReadyForServiceStatusListener(OnReadyForServiceStatusListener onReadyForServiceStatusListener) {
        if (onReadyForServiceStatusListener != null) {
            this.mReadyForServiceStatusListeners.add(onReadyForServiceStatusListener);
        }
    }

    public boolean isExternalModeChooserShowing(int i) {
        ITrackpadService iTrackpadService = this.mReadyForService;
        if (iTrackpadService == null) {
            Log.w(TAG, "isExternalModeChooserShowing error, mReadyForService is null");
            return false;
        }
        try {
            return iTrackpadService.isExternalModeChooserShowing(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isReadyForPkgAvailable() {
        boolean z = false;
        try {
            ApplicationInfo applicationInfo = this.mContext.createContextAsUser(UserHandle.of(ActivityManager.getCurrentUser()), 0).getPackageManager().getApplicationInfo("com.motorola.mobiledesktop", 0);
            if (applicationInfo != null) {
                if (applicationInfo.enabled) {
                    z = true;
                }
            }
        } catch (Exception unused) {
        }
        if (DEBUG) {
            Log.d(TAG, "Package(com.motorola.mobiledesktop) available: " + z);
        }
        return z;
    }

    public boolean isReadyServiceConnected() {
        return this.mReadyForService != null;
    }

    public boolean isSupportMllOnSc() {
        ITrackpadService iTrackpadService = this.mReadyForService;
        if (iTrackpadService == null) {
            Log.w(TAG, "isSupportMllOnSc error, mReadyForService is null");
            return false;
        }
        try {
            return iTrackpadService.isSupportMllOnSc();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void notifyChooserAnimStateChanged(String str, int i, boolean z) {
        Iterator it = this.mChooseListeners.iterator();
        while (it.hasNext()) {
            ((OnModeChooserListener) it.next()).onModeChooserAnimationStateChange(str, i, z);
        }
    }

    public void notifyModeChooserShowingStateChanged(int i, boolean z) {
        Iterator it = this.mChooseListeners.iterator();
        while (it.hasNext()) {
            ((OnModeChooserListener) it.next()).onModeChooserShowingStateChanged(i, z);
        }
    }

    public void notifyReadyForServiceStatusChanged(int i, boolean z) {
        Iterator it = this.mReadyForServiceStatusListeners.iterator();
        while (it.hasNext()) {
            ((OnReadyForServiceStatusListener) it.next()).onStatusChanged(i, z);
        }
    }

    public void pendingStartTrackpad(boolean z, int i) {
        this.mPendingMirrorToDesktop = z;
        this.mPendingExternalDisplayId = i;
        this.mPendingStartTrackpad = true;
    }

    public void removeModeChooserListener(OnModeChooserListener onModeChooserListener) {
        this.mChooseListeners.remove(onModeChooserListener);
    }

    public void requestShowDesktopSplashScreen() {
        ITrackpadService iTrackpadService = this.mReadyForService;
        if (iTrackpadService == null) {
            Log.w(TAG, "requestShowDesktopSplashScreen error, mReadyForService is null");
            return;
        }
        try {
            iTrackpadService.requestShowDesktopSplashScreen();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setPendingExecuteExternalTutorial(boolean z) {
        this.mPendingStartExternalTutorial = z;
    }

    public void start() {
        this.mTaskBarServiceProxy.addCallback(this.mTaskBarServiceCallBack);
        this.mHardwareDisplayController.addCallback(this.mHardwareDisplayListener);
        ((DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class)).addCallback(this.mDeviceProvisionedListener);
    }

    public void startExternalDisplayTutorialOrderActivity(int i) {
        if (DEBUG) {
            Log.d(TAG, "startExternalDisplayTutorialOrderActivity: " + i);
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.motorola.mobiledesktop", "com.motorola.mobiledesktop.chooser.ExternalDisplayTutorialOrderActivity"));
        intent.putExtra("external_display_id", i);
        intent.setFlags(268468224);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        MotoFeature motoFeature = (MotoFeature) Dependency.get(MotoFeature.class);
        activityOptionsMakeBasic.setLaunchDisplayId((motoFeature.isSupportCli() && motoFeature.isLidClosed()) ? 1 : 0);
        this.mContext.startActivityAsUser(intent, activityOptionsMakeBasic.toBundle(), UserHandle.CURRENT);
    }

    public void startTrackpadActivity(int i) {
        startTrackpadActivity(i, false);
    }

    public void startTrackpadActivity(int i, boolean z) {
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            this.mTaskBarServiceProxy.startTrackpadActivityFromSecondUser(i, z);
            return;
        }
        if (DEBUG) {
            Log.d(TAG, "startTrackpadActivity: " + i + " user :" + Process.myUserHandle());
        }
        if (!ExternalDisplayModeManager.readIsDesktopEnabled(this.mContext) || i == -1) {
            Log.w(TAG, "startTrackpadActivity fail: " + i);
            return;
        }
        int i2 = 0;
        if (this.mReadyForService == null) {
            Log.w(TAG, "startTrackpadActivity fail, bind TrackpadService first");
            pendingStartTrackpad(false, i);
            bindTrackpadServiceIfNeed();
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.motorola.mobiledesktop", "com.motorola.controller.trackpad.TrackpadActivity"));
            intent.setFlags(268435456);
            intent.putExtra("user_trigger", z);
            intent.putExtra("external_display_id", i);
            ActivityOptions activityOptions = new ActivityOptions(new Bundle());
            if (this.mMotoFeature.isSupportCli() && this.mMotoFeature.isLidClosed()) {
                i2 = 1;
            }
            activityOptions.setLaunchDisplayId(i2);
            this.mContext.startActivityAsUser(intent, activityOptions.toBundle(), UserHandle.CURRENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
