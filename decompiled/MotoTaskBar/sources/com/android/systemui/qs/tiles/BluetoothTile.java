package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tiles.controller.BluetoothQSTileController;
import com.android.systemui.qs.tiles.controller.QSTileControllerCallback;
import com.android.systemui.res.R$drawable;
import com.android.systemui.res.R$string;
import com.motorola.taskbar.DesktopQSTileData;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothTile extends QSTileImpl {
    private final QSTileControllerCallback mCallback;
    private final BluetoothQSTileController mController;

    public BluetoothTile(QSHost qSHost, Looper looper, Handler handler, ActivityStarter activityStarter, BluetoothQSTileController bluetoothQSTileController) {
        super(qSHost, looper, handler, activityStarter);
        QSTileControllerCallback qSTileControllerCallback = new QSTileControllerCallback() { // from class: com.android.systemui.qs.tiles.BluetoothTile.1
            @Override // com.android.systemui.qs.tiles.controller.QSTileControllerCallback
            public void onDataChanged(DesktopQSTileData desktopQSTileData) {
                BluetoothTile.this.refreshState(desktopQSTileData);
            }
        };
        this.mCallback = qSTileControllerCallback;
        this.mController = bluetoothQSTileController;
        bluetoothQSTileController.observe(getLifecycle(), qSTileControllerCallback);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.BLUETOOTH_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick(View view) {
        this.mController.handleClick(view.getContext().getDisplayId());
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleSecondaryClick(View view) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        BluetoothQSTileController bluetoothQSTileController = this.mController;
        if (bluetoothQSTileController == null) {
            return;
        }
        DesktopQSTileData desktopQsTileData = obj instanceof DesktopQSTileData ? (DesktopQSTileData) obj : bluetoothQSTileController.getDesktopQsTileData();
        if (desktopQsTileData == null) {
            return;
        }
        boolean zIsOn = desktopQsTileData.isOn();
        booleanState.isTransient = desktopQsTileData.isTransient;
        booleanState.dualTarget = true;
        booleanState.value = zIsOn;
        booleanState.label = desktopQsTileData.label;
        booleanState.secondaryLabel = desktopQsTileData.secondaryLabel;
        booleanState.contentDescription = this.mContext.getString(R$string.accessibility_quick_settings_bluetooth);
        booleanState.stateDescription = desktopQsTileData.stateDescription;
        booleanState.state = desktopQsTileData.state;
        if (!zIsOn) {
            booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.qs_bluetooth_icon_off);
        } else if (desktopQsTileData.isConnected) {
            booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.qs_bluetooth_icon_on);
        } else if (booleanState.isTransient) {
            booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.qs_bluetooth_icon_search);
        } else {
            booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.qs_bluetooth_icon_off);
        }
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.forceExpandIcon = false;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.qs.QSTile
    public boolean isAvailable() {
        return true;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }
}
