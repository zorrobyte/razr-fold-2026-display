package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tiles.controller.QSTileControllerCallback;
import com.android.systemui.qs.tiles.controller.WifiQSTileController;
import com.android.systemui.statusbar.policy.WifiIcons;
import com.motorola.taskbar.DesktopQSTileData;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$string;

/* JADX INFO: loaded from: classes.dex */
public class WifiTile extends QSTileImpl {
    private static final Intent WIFI_SETTINGS = new Intent("android.settings.WIFI_SETTINGS");
    private final QSTileControllerCallback mCallback;
    private final WifiQSTileController mController;

    public WifiTile(QSHost qSHost, Looper looper, Handler handler, ActivityStarter activityStarter, WifiQSTileController wifiQSTileController) {
        super(qSHost, looper, handler, activityStarter);
        QSTileControllerCallback qSTileControllerCallback = new QSTileControllerCallback() { // from class: com.android.systemui.qs.tiles.WifiTile.1
            @Override // com.android.systemui.qs.tiles.controller.QSTileControllerCallback
            public void onDataChanged(DesktopQSTileData desktopQSTileData) {
                WifiTile.this.refreshState(desktopQSTileData);
            }
        };
        this.mCallback = qSTileControllerCallback;
        this.mController = wifiQSTileController;
        wifiQSTileController.observe(getLifecycle(), qSTileControllerCallback);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return WIFI_SETTINGS;
    }

    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_wifi_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick(View view) {
        this.mController.handleClick(view.getContext().getDisplayId());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.SignalState signalState, Object obj) {
        if (this.DEBUG) {
            Log.d(this.TAG, "handleUpdateState arg=" + obj);
        }
        WifiQSTileController wifiQSTileController = this.mController;
        if (wifiQSTileController == null) {
            return;
        }
        DesktopQSTileData desktopQsTileData = obj instanceof DesktopQSTileData ? (DesktopQSTileData) obj : wifiQSTileController.getDesktopQsTileData();
        if (desktopQsTileData == null) {
            return;
        }
        Resources resources = this.mContext.getResources();
        signalState.state = desktopQsTileData.state;
        signalState.secondaryLabel = desktopQsTileData.secondaryLabel;
        boolean z = true;
        signalState.dualTarget = true;
        signalState.label = desktopQsTileData.label;
        boolean zIsOn = desktopQsTileData.isOn();
        signalState.value = zIsOn;
        signalState.activityIn = desktopQsTileData.activityIn;
        signalState.activityOut = desktopQsTileData.activityOut;
        if (signalState.state == 0) {
            signalState.icon = QSTileImpl.ResourceIcon.get(WifiIcons.QS_WIFI_DISABLED);
        } else if (desktopQsTileData.isTransient) {
            signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_signal_wifi_transient_animation);
        } else if (!zIsOn) {
            signalState.icon = QSTileImpl.ResourceIcon.get(WifiIcons.QS_WIFI_DISABLED);
        } else if (desktopQsTileData.isConnected) {
            signalState.icon = QSTileImpl.ResourceIcon.get(this.mController.getWifiConnectedIcon());
        } else {
            signalState.icon = QSTileImpl.ResourceIcon.get(WifiIcons.QS_WIFI_NO_NETWORK);
        }
        signalState.stateDescription = desktopQsTileData.stateDescription;
        signalState.contentDescription = desktopQsTileData.contentDescription;
        signalState.dualLabelContentDescription = resources.getString(R$string.accessibility_quick_settings_open_settings, getTileLabel());
        signalState.expandedAccessibilityClassName = Switch.class.getName();
        int i = signalState.state;
        if (i != 1 && i != 0) {
            z = false;
        }
        signalState.noDefaultNetwork = z;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.qs.QSTile
    public boolean isAvailable() {
        return true;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.SignalState newTileState() {
        return new QSTile.SignalState();
    }
}
