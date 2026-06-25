package com.motorola.taskbar.bar;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import android.util.SparseBooleanArray;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.motorola.taskbar.model.HardwareDisplayController;
import com.motorola.trackpad.ReadyForProxy;

/* JADX INFO: loaded from: classes2.dex */
public class ExternalDisplayTutorialManager {
    private static final boolean DEBUG = !Build.IS_USER;
    private final Handler mBgHandler;
    private final Context mContext;
    private final DeviceProvisionedController mDeviceProvisionedController;
    private final HardwareDisplayController mHardwareDisplayController;
    private final Handler mMainHandler;
    private final SparseBooleanArray mTutoredUserIds = new SparseBooleanArray();
    private DeviceProvisionedController.DeviceProvisionedListener mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.motorola.taskbar.bar.ExternalDisplayTutorialManager.1
        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onDeviceProvisionedChanged() {
            ExternalDisplayTutorialManager.this.onDeviceProvisionedOrUserStatusChanged();
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onUserSetupChanged() {
            ExternalDisplayTutorialManager.this.onDeviceProvisionedOrUserStatusChanged();
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onUserSwitched() {
            ExternalDisplayTutorialManager.this.onDeviceProvisionedOrUserStatusChanged();
        }
    };
    private HardwareDisplayController.HardwareDisplayListener mHardwareDisplayListener = new HardwareDisplayController.HardwareDisplayListener() { // from class: com.motorola.taskbar.bar.ExternalDisplayTutorialManager.2
        @Override // com.motorola.taskbar.model.HardwareDisplayController.HardwareDisplayListener
        public void onHardwareDisplayAdded(int i, int i2, boolean z) {
            ExternalDisplayTutorialManager.this.handleHardwareDisplayAdded(i, i2, z);
        }

        @Override // com.motorola.taskbar.model.HardwareDisplayController.HardwareDisplayListener
        public void onHardwareDisplayIdChanged(int i, int i2, int i3, boolean z) {
            ExternalDisplayTutorialManager.this.handleHardwareDisplayIdChanged(i, i2, i3, z);
        }

        @Override // com.motorola.taskbar.model.HardwareDisplayController.HardwareDisplayListener
        public void onHardwareDisplayRemoved(int i, int i2) {
            ExternalDisplayTutorialManager.this.handleHardwareDisplayRemoved(i, i2);
        }
    };

    public ExternalDisplayTutorialManager(Context context, Handler handler, Handler handler2, HardwareDisplayController hardwareDisplayController, DeviceProvisionedController deviceProvisionedController) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mBgHandler = handler2;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mHardwareDisplayController = hardwareDisplayController;
        deviceProvisionedController.addCallback(this.mDeviceProvisionedListener);
        hardwareDisplayController.addCallback(this.mHardwareDisplayListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDeviceProvisionedOrUserStatusChanged$0() {
        int displayId = this.mHardwareDisplayController.getDisplayId();
        if (displayId < 0) {
            return;
        }
        lambda$handleHardwareDisplayAdded$1(displayId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDeviceProvisionedOrUserStatusChanged() {
        if (this.mDeviceProvisionedController.isDeviceProvisioned() && this.mDeviceProvisionedController.isCurrentUserSetup()) {
            this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.bar.ExternalDisplayTutorialManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDeviceProvisionedOrUserStatusChanged$0();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: startTutorialProcess, reason: merged with bridge method [inline-methods] */
    public void lambda$handleHardwareDisplayAdded$1(int i) {
        if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            int currentUser = this.mDeviceProvisionedController.getCurrentUser();
            if (this.mTutoredUserIds.get(currentUser, false) && currentUser == 0) {
                return;
            }
            this.mTutoredUserIds.put(currentUser, true);
            ReadyForProxy readyForProxy = (ReadyForProxy) Dependency.get(ReadyForProxy.class);
            boolean zIsReadyServiceConnected = readyForProxy.isReadyServiceConnected();
            if (DEBUG) {
                Log.d("ExternalDisplayTutorialManager", "startTutorialProcess, isReadyServiceConnected: " + zIsReadyServiceConnected);
            }
            if (zIsReadyServiceConnected) {
                readyForProxy.startExternalDisplayTutorialOrderActivity(i);
            } else {
                readyForProxy.setPendingExecuteExternalTutorial(true);
            }
        }
    }

    public void handleHardwareDisplayAdded(int i, final int i2, boolean z) {
        if (this.mDeviceProvisionedController.isDeviceProvisioned() && this.mDeviceProvisionedController.isCurrentUserSetup()) {
            this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.bar.ExternalDisplayTutorialManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$handleHardwareDisplayAdded$1(i2);
                }
            });
        }
    }

    public void handleHardwareDisplayIdChanged(int i, int i2, int i3, boolean z) {
    }

    public void handleHardwareDisplayRemoved(int i, int i2) {
        this.mTutoredUserIds.clear();
    }

    public void init() {
    }
}
