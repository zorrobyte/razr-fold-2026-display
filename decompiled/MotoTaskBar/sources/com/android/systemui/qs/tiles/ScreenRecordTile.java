package com.android.systemui.qs.tiles;

import android.content.Intent;
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
import com.android.systemui.qs.tiles.controller.ScreenRecordQSTileController;
import com.android.systemui.res.R$drawable;
import com.android.systemui.res.R$string;
import com.motorola.taskbar.DesktopQSTileData;

/* JADX INFO: loaded from: classes.dex */
public class ScreenRecordTile extends QSTileImpl {
    private final QSTileControllerCallback mCallback;
    private final ScreenRecordQSTileController mController;

    public ScreenRecordTile(QSHost qSHost, Looper looper, Handler handler, ActivityStarter activityStarter, ScreenRecordQSTileController screenRecordQSTileController) {
        super(qSHost, looper, handler, activityStarter);
        QSTileControllerCallback qSTileControllerCallback = new QSTileControllerCallback() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile.1
            @Override // com.android.systemui.qs.tiles.controller.QSTileControllerCallback
            public void onDataChanged(DesktopQSTileData desktopQSTileData) {
                ScreenRecordTile.this.refreshState(desktopQSTileData);
            }
        };
        this.mCallback = qSTileControllerCallback;
        this.mController = screenRecordQSTileController;
        screenRecordQSTileController.observe(this, qSTileControllerCallback);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        Log.d("ScreenRecordTile", "Start screenrecord settings");
        Intent intent = new Intent();
        intent.setClassName("com.motorola.coresettingsext", "com.motorola.coresettingsext.screenrecord.ScreenRecordActivity");
        return intent;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick(View view) {
        this.mController.handleClick(view.getContext().getDisplayId());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        ScreenRecordQSTileController screenRecordQSTileController = this.mController;
        if (screenRecordQSTileController == null) {
            return;
        }
        DesktopQSTileData desktopQsTileData = obj instanceof DesktopQSTileData ? (DesktopQSTileData) obj : screenRecordQSTileController.getDesktopQsTileData();
        if (desktopQsTileData == null) {
            return;
        }
        booleanState.state = 1;
        boolean zIsOn = desktopQsTileData.isOn();
        booleanState.value = zIsOn;
        booleanState.state = zIsOn ? 2 : 1;
        booleanState.label = desktopQsTileData.label;
        booleanState.secondaryLabel = desktopQsTileData.secondaryLabel;
        booleanState.icon = QSTileImpl.ResourceIcon.get(zIsOn ? R$drawable.qs_screen_record_icon_on : R$drawable.qs_screen_record_icon_off);
        booleanState.forceExpandIcon = booleanState.state == 1;
        booleanState.contentDescription = desktopQsTileData.contentDescription;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.label = this.mContext.getString(R$string.quick_settings_screen_record_label);
        booleanState.handlesLongClick = true;
        return booleanState;
    }
}
