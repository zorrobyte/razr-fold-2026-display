package com.android.systemui.qs.tiles.controller;

import com.android.systemui.statusbar.policy.WifiIcons;
import com.motorola.taskbar.DesktopQSTileData;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.WifiStatusMonitor;

/* JADX INFO: compiled from: WifiQSTileController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class WifiQSTileController extends QSTileController implements WifiStatusMonitor.WifiStatusListener {
    private final WifiStatusMonitor wifiStatusMonitor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiQSTileController(TaskBarServiceProxy taskBarServiceProxy, WifiStatusMonitor wifiStatusMonitor) {
        super(DesktopQSTileData.QSTileType.WIFI, taskBarServiceProxy);
        taskBarServiceProxy.getClass();
        wifiStatusMonitor.getClass();
        this.wifiStatusMonitor = wifiStatusMonitor;
    }

    @Override // com.android.systemui.qs.tiles.controller.QSTileController, com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(QSTileControllerCallback qSTileControllerCallback) {
        qSTileControllerCallback.getClass();
        super.addCallback(qSTileControllerCallback);
        if (getCallbacks().size() == 1) {
            this.wifiStatusMonitor.addCallback((WifiStatusMonitor.WifiStatusListener) this);
        }
    }

    public final int getWifiConnectedIcon() {
        return getWifiConnectedIcon(this.wifiStatusMonitor.getLevel(), this.wifiStatusMonitor.isProblematical(), this.wifiStatusMonitor.getWifiStandard());
    }

    public final int getWifiConnectedIcon(int i, boolean z, int i2) {
        double dMax = (int) Math.max(0.0d, i);
        int[] iArr = WifiIcons.WIFI_FULL_ICONS;
        int iMin = (int) Math.min(dMax, iArr.length - 1);
        return i2 != 6 ? i2 != 8 ? z ? WifiIcons.WIFI_NO_INTERNET_ICONS[iMin] : iArr[iMin] : z ? WifiIcons.WIFI_7_NO_INTERNET_ICONS[iMin] : WifiIcons.WIFI_7_FULL_ICONS[iMin] : z ? WifiIcons.WIFI_6_NO_INTERNET_ICONS[iMin] : WifiIcons.WIFI_6_FULL_ICONS[iMin];
    }

    @Override // com.motorola.taskbar.WifiStatusMonitor.WifiStatusListener
    public void onWifiStatusChanged(int i, boolean z, String str, int i2, boolean z2, String str2, int i3) {
        notifyCallBack();
    }

    @Override // com.android.systemui.qs.tiles.controller.QSTileController, com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(QSTileControllerCallback qSTileControllerCallback) {
        qSTileControllerCallback.getClass();
        super.removeCallback(qSTileControllerCallback);
        if (getCallbacks().size() == 0) {
            this.wifiStatusMonitor.removeCallback((WifiStatusMonitor.WifiStatusListener) this);
        }
    }
}
