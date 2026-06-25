package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.Switch;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tiles.controller.CellQSTileController;
import com.android.systemui.qs.tiles.controller.QSTileControllerCallback;
import com.android.systemui.res.R$string;
import com.motorola.taskbar.DesktopQSTileData;
import com.motorola.taskbar.R$drawable;

/* JADX INFO: loaded from: classes.dex */
public class CellularTile extends QSTileImpl {
    private final QSTileControllerCallback mCallback;
    private final CellQSTileController mController;

    public CellularTile(QSHost qSHost, Looper looper, Handler handler, ActivityStarter activityStarter, CellQSTileController cellQSTileController) {
        super(qSHost, looper, handler, activityStarter);
        QSTileControllerCallback qSTileControllerCallback = new QSTileControllerCallback() { // from class: com.android.systemui.qs.tiles.CellularTile.1
            @Override // com.android.systemui.qs.tiles.controller.QSTileControllerCallback
            public void onDataChanged(DesktopQSTileData desktopQSTileData) {
                CellularTile.this.refreshState(desktopQSTileData);
            }
        };
        this.mCallback = qSTileControllerCallback;
        this.mController = cellQSTileController;
        cellQSTileController.observe(getLifecycle(), qSTileControllerCallback);
    }

    static Intent getCellularSettingIntent() {
        Intent intent = new Intent("android.settings.NETWORK_OPERATOR_SETTINGS");
        if (SubscriptionManager.getDefaultDataSubscriptionId() != -1) {
            intent.putExtra("android.provider.extra.SUB_ID", SubscriptionManager.getDefaultDataSubscriptionId());
        }
        return intent;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return ((QSTile.SignalState) getState()).state == 0 ? new Intent("android.settings.WIRELESS_SETTINGS") : getCellularSettingIntent();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick(View view) {
        if (((QSTile.SignalState) getState()).state == 0) {
            return;
        }
        this.mController.handleClick(view.getContext().getDisplayId());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.SignalState signalState, Object obj) {
        CellQSTileController cellQSTileController = this.mController;
        if (cellQSTileController == null) {
            return;
        }
        DesktopQSTileData desktopQsTileData = obj instanceof DesktopQSTileData ? (DesktopQSTileData) obj : cellQSTileController.getDesktopQsTileData();
        if (desktopQsTileData == null) {
            return;
        }
        boolean zIsOn = desktopQsTileData.isOn();
        signalState.label = this.mContext.getResources().getString(R$string.mobile_data);
        signalState.value = zIsOn;
        signalState.activityIn = zIsOn && desktopQsTileData.activityIn;
        signalState.activityOut = zIsOn && desktopQsTileData.activityOut;
        signalState.expandedAccessibilityClassName = Switch.class.getName();
        int i = desktopQsTileData.state;
        signalState.state = i;
        signalState.secondaryLabel = desktopQsTileData.secondaryLabel;
        signalState.contentDescription = desktopQsTileData.contentDescription;
        signalState.stateDescription = desktopQsTileData.stateDescription;
        signalState.noDefaultNetwork = i == 1 || i == 0;
        int i2 = desktopQsTileData.extra;
        if (i2 == 1) {
            signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.zz_moto_ic_qs_invalid_sim);
        } else if (i2 != 2) {
            signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_swap_vert);
        } else {
            signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_no_sim);
        }
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
