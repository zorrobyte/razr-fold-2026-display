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
import com.android.systemui.qs.tiles.controller.AirplaneQSTileController;
import com.android.systemui.qs.tiles.controller.QSTileControllerCallback;
import com.motorola.taskbar.DesktopQSTileData;
import com.motorola.taskbar.R$drawable;

/* JADX INFO: loaded from: classes.dex */
public class AirplaneModeTile extends QSTileImpl {
    private final QSTileControllerCallback mCallback;
    private final AirplaneQSTileController mController;

    public AirplaneModeTile(QSHost qSHost, Looper looper, Handler handler, ActivityStarter activityStarter, AirplaneQSTileController airplaneQSTileController) {
        super(qSHost, looper, handler, activityStarter);
        QSTileControllerCallback qSTileControllerCallback = new QSTileControllerCallback() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.1
            @Override // com.android.systemui.qs.tiles.controller.QSTileControllerCallback
            public void onDataChanged(DesktopQSTileData desktopQSTileData) {
                AirplaneModeTile.this.refreshState(desktopQSTileData);
            }
        };
        this.mCallback = qSTileControllerCallback;
        this.mController = airplaneQSTileController;
        airplaneQSTileController.observe(getLifecycle(), qSTileControllerCallback);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.AIRPLANE_MODE_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick(View view) {
        this.mController.handleClick(view.getContext().getDisplayId());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        AirplaneQSTileController airplaneQSTileController = this.mController;
        if (airplaneQSTileController == null) {
            return;
        }
        DesktopQSTileData desktopQsTileData = obj instanceof DesktopQSTileData ? (DesktopQSTileData) obj : airplaneQSTileController.getDesktopQsTileData();
        if (desktopQsTileData == null) {
            return;
        }
        boolean zIsOn = desktopQsTileData.isOn();
        booleanState.value = zIsOn;
        booleanState.label = desktopQsTileData.label;
        booleanState.icon = QSTileImpl.ResourceIcon.get(zIsOn ? R$drawable.qs_airplane_icon_on : R$drawable.qs_airplane_icon_off);
        booleanState.state = zIsOn ? 2 : 1;
        booleanState.contentDescription = desktopQsTileData.contentDescription;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }
}
