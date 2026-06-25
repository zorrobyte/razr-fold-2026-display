package com.motorola.taskbar.shortcut;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.WindowManagerGlobal;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.motorola.extendscreenshot.ScreenshotController;
import com.motorola.taskbar.bar.ExternalDisplayModeManager;
import com.motorola.taskbar.bar.QSNotificationPanelController;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.taskbar.recent.RecentsController;
import com.motorola.taskbar.shortcut.ShortcutKeyServiceProxy;
import com.motorola.taskbar.shortcut.record.RecordManager;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.trackpad.ReadyForProxy;

/* JADX INFO: loaded from: classes2.dex */
public class ShortcutKeyDispatcher implements ShortcutKeyServiceProxy.Callbacks {
    private static final boolean DEBUG = DebugConfig.DEBUG_COMMON;
    private final Context mContext;
    private final DeviceProvisionedController mDeviceProvisionedController;
    private final KeyguardManager mKeyguardManager;
    private final QSNotificationPanelController mQSNotificationPanelController;
    private final RecentsController mRecentsController;
    private final RecordManager mRecordManager;
    private final TaskBarController mTaskBarController;
    private ShortcutKeyServiceProxy mShortcutKeyServiceProxy = new ShortcutKeyServiceProxy(this);
    private IWindowManager mWindowManagerService = WindowManagerGlobal.getWindowManagerService();
    protected final long META_MASK = 281474976710656L;
    protected final long ALT_MASK = 8589934592L;
    protected final long CTRL_MASK = 17592186044416L;
    protected final long SHIFT_MASK = 4294967296L;
    protected final long META_LEFT = 117;
    protected final long WIN_N = 281474976710698L;
    protected final long WIN_SLASH = 281474976710732L;
    protected final long ALT_TAB = 8589934653L;
    protected final long PRTSCN = 120;
    protected final long ALT_PRTSCN = 8589934712L;
    protected final long WIN_SHIFT_S = 281479271677999L;
    protected final long CTRL_WIN_SHIFT_S = 299071457722415L;
    protected final long CTRL_WIN_S = 299067162755119L;
    protected final long AIR_REMOTE_HOME = 184;
    protected final long AIR_REMOTE_CIRCLE = 185;
    protected final long AIR_REMOTE_CIRCLE_LONG_CLICK = 281474976710841L;

    public ShortcutKeyDispatcher(Context context, QSNotificationPanelController qSNotificationPanelController, TaskBarController taskBarController, RecentsController recentsController, DeviceProvisionedController deviceProvisionedController, KeyguardManager keyguardManager, RecordManager recordManager) {
        this.mContext = context;
        this.mQSNotificationPanelController = qSNotificationPanelController;
        this.mTaskBarController = taskBarController;
        this.mRecentsController = recentsController;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mKeyguardManager = keyguardManager;
        this.mRecordManager = recordManager;
    }

    private void handleAirRemoteCircle(int i) {
        if (this.mKeyguardManager.isKeyguardLocked()) {
            if (DEBUG) {
                Log.d("ShortcutKeyDispatcher", "handleAirRemoteCircle KeyguardLocked");
            }
            return;
        }
        String displayChooserMode = this.mTaskBarController.getDisplayChooserMode(i);
        displayChooserMode.hashCode();
        switch (displayChooserMode) {
            case "mirror":
                sendKeyEvent(0, 120, i);
                sendKeyEvent(1, 120, i);
                break;
            case "normal":
            case "game":
            case "video":
            case "videochat":
                handleScreenshotShortcuts(120L, i);
                break;
        }
    }

    private void handleAirRemoteCircleLongClick(int i) {
        if (!this.mKeyguardManager.isKeyguardLocked()) {
            this.mRecordManager.toggleRecord(i, true);
        } else if (DEBUG) {
            Log.d("ShortcutKeyDispatcher", "handleAirRemoteCircleLongClick KeyguardLocked");
        }
    }

    private void handleAltTab(int i) {
        if (!this.mKeyguardManager.isKeyguardLocked()) {
            this.mRecentsController.toggleRecents(i, true);
        } else if (DEBUG) {
            Log.d("ShortcutKeyDispatcher", "handleAltTab KeyguardLocked");
        }
    }

    private void handleGuide(int i) {
        if (!this.mKeyguardManager.isKeyguardLocked()) {
            this.mContext.sendBroadcastAsUser(new Intent("com.motorola.taskbar.ACTION_START_MODE_CHOOSER_ACTIVITY"), UserHandle.CURRENT);
        } else if (DEBUG) {
            Log.d("ShortcutKeyDispatcher", "handleGuide KeyguardLocked");
        }
    }

    private void handleScreenshotShortcuts(long j, int i) {
        if (this.mKeyguardManager.isKeyguardLocked()) {
            if (DEBUG) {
                Log.d("ShortcutKeyDispatcher", "handleScreenshotShortcuts KeyguardLocked");
                return;
            }
            return;
        }
        ScreenshotController screenshotController = (ScreenshotController) Dependency.get(ScreenshotController.class);
        if (j == 120 || j == 299067162755119L) {
            screenshotController.takeScreenshot(1, i);
            return;
        }
        if (j == 8589934712L) {
            screenshotController.takeScreenshot(2, i);
        } else if (j == 281479271677999L) {
            screenshotController.takeScreenshot(4, i);
        } else if (j == 299071457722415L) {
            screenshotController.takeScreenshot(16, i);
        }
    }

    private void handleWinLeft(int i) {
        if (!this.mKeyguardManager.isKeyguardLocked()) {
            this.mTaskBarController.toggleAppTray(i);
        } else if (DEBUG) {
            Log.d("ShortcutKeyDispatcher", "handleWinLeft KeyguardLocked");
        }
    }

    private void handleWinN(int i) {
        if (!this.mKeyguardManager.isKeyguardLocked()) {
            this.mQSNotificationPanelController.requestSwitchNotificationPanel(i);
        } else if (DEBUG) {
            Log.d("ShortcutKeyDispatcher", "handleWinN KeyguardLocked");
        }
    }

    private void handleWinSlash(int i) {
        if (!this.mKeyguardManager.isKeyguardLocked()) {
            this.mTaskBarController.toggleShortcutHelper(i);
        } else if (DEBUG) {
            Log.d("ShortcutKeyDispatcher", "handleWinSlash KeyguardLocked");
        }
    }

    private void sendKeyEvent(int i, int i2, int i3) {
        long jUptimeMillis = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(jUptimeMillis, jUptimeMillis, i, i2, 0, 0, -1, 0, 72, 257);
        keyEvent.setDisplayId(i3);
        InputManager.getInstance().injectInputEvent(keyEvent, 0);
    }

    public void handleAirRemoteHome(int i) {
        String displayChooserMode;
        if (this.mKeyguardManager.isKeyguardLocked()) {
            if (DEBUG) {
                Log.d("ShortcutKeyDispatcher", "handleAirRemoteHome KeyguardLocked");
            }
            return;
        }
        displayChooserMode = this.mTaskBarController.getDisplayChooserMode(i);
        displayChooserMode.hashCode();
        switch (displayChooserMode) {
            case "mirror":
                sendKeyEvent(0, 3, i);
                sendKeyEvent(1, 3, i);
                break;
            case "normal":
                this.mTaskBarController.triggerHomeButton(i);
                break;
            case "game":
            case "video":
            case "videochat":
                ((ExternalDisplayModeManager) Dependency.get(ExternalDisplayModeManager.class)).startGameModeLauncher(this.mContext, displayChooserMode, i);
                break;
        }
    }

    public void handleAirRemoteMenu(int i) {
        boolean zIsSupportMllOnSc;
        if (this.mKeyguardManager.isKeyguardLocked()) {
            if (DEBUG) {
                Log.d("ShortcutKeyDispatcher", "handleAirRemoteMenu KeyguardLocked");
            }
            return;
        }
        String displayChooserMode = this.mTaskBarController.getDisplayChooserMode(i);
        zIsSupportMllOnSc = ((ReadyForProxy) Dependency.get(ReadyForProxy.class)).isSupportMllOnSc();
        Log.d("ShortcutKeyDispatcher", "handleAirRemoteMenu mode: " + displayChooserMode + "; displayId: " + i + " isSupportMllOnSc:" + zIsSupportMllOnSc);
        displayChooserMode.hashCode();
        switch (displayChooserMode) {
            case "mirror":
                this.mContext.getContentResolver().call(Uri.parse("content://com.motorola.launcher3.settings/"), "do_launcher_action", "toggle_app_tray", (Bundle) null);
                break;
            case "normal":
                this.mTaskBarController.toggleAppTray(i);
                break;
            case "game":
            case "video":
            case "videochat":
                Intent intent = new Intent("com.motorola.leanbacklauncher.launchToolkit");
                intent.setPackage(zIsSupportMllOnSc ? "com.motorola.mobiledesktop" : "com.motorola.leanbacklauncher");
                intent.putExtra("displayId", i);
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.motorola.permission.ACCESS_DESKTOP");
                break;
        }
    }

    @Override // com.motorola.taskbar.shortcut.ShortcutKeyServiceProxy.Callbacks
    public void onShortcutKeyPressed(int i, long j) {
        if (this.mDeviceProvisionedController.isDeviceProvisioned() && this.mDeviceProvisionedController.isCurrentUserSetup()) {
            if (j == 281474976710698L) {
                if (DEBUG) {
                    Log.d("ShortcutKeyDispatcher", "on WIN_N Pressed displayId: " + i);
                }
                handleWinN(i);
                return;
            }
            if (281474976710732L == j) {
                handleWinSlash(i);
                return;
            }
            if (117 == j) {
                handleWinLeft(i);
                return;
            }
            if (8589934653L == j) {
                handleAltTab(i);
                return;
            }
            if (172 == j) {
                handleGuide(i);
                return;
            }
            if (184 == j) {
                handleAirRemoteHome(i);
                return;
            }
            if (186 == j) {
                handleAirRemoteMenu(i);
                return;
            }
            if (185 == j) {
                handleAirRemoteCircle(i);
            } else if (281474976710841L == j) {
                handleAirRemoteCircleLongClick(i);
            } else {
                handleScreenshotShortcuts(j, i);
            }
        }
    }

    public void registerShortcutKey(long j) {
        try {
            this.mWindowManagerService.registerShortcutKeyByDisplay(j, this.mShortcutKeyServiceProxy);
        } catch (RemoteException unused) {
        }
    }

    public void start() {
        registerShortcutKey(281474976710698L);
        registerShortcutKey(281474976710732L);
        registerShortcutKey(117L);
        registerShortcutKey(8589934653L);
        registerShortcutKey(120L);
        registerShortcutKey(8589934712L);
        registerShortcutKey(281479271677999L);
        registerShortcutKey(299067162755119L);
        registerShortcutKey(299071457722415L);
        registerShortcutKey(172L);
        registerShortcutKey(184L);
        registerShortcutKey(186L);
        registerShortcutKey(185L);
        registerShortcutKey(281474976710841L);
    }
}
