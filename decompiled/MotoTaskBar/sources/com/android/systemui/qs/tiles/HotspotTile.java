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
import com.android.systemui.qs.tiles.controller.HotspotQSTileController;
import com.android.systemui.qs.tiles.controller.QSTileControllerCallback;
import com.android.systemui.res.R$string;
import com.motorola.taskbar.DesktopQSTileData;
import com.motorola.taskbar.R$drawable;

/* JADX INFO: loaded from: classes.dex */
public class HotspotTile extends QSTileImpl {
    private final QSTileControllerCallback mCallback;
    private final HotspotQSTileController mController;

    public HotspotTile(QSHost qSHost, Looper looper, Handler handler, ActivityStarter activityStarter, HotspotQSTileController hotspotQSTileController) {
        super(qSHost, looper, handler, activityStarter);
        QSTileControllerCallback qSTileControllerCallback = new QSTileControllerCallback() { // from class: com.android.systemui.qs.tiles.HotspotTile.1
            @Override // com.android.systemui.qs.tiles.controller.QSTileControllerCallback
            public void onDataChanged(DesktopQSTileData desktopQSTileData) {
                HotspotTile.this.refreshState(desktopQSTileData);
            }
        };
        this.mCallback = qSTileControllerCallback;
        this.mController = hotspotQSTileController;
        hotspotQSTileController.observe(getLifecycle(), qSTileControllerCallback);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("com.android.settings.WIFI_TETHER_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick(View view) {
        this.mController.handleClick(view.getContext().getDisplayId());
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleDestroy() {
        super.handleDestroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        HotspotQSTileController hotspotQSTileController = this.mController;
        if (hotspotQSTileController == null) {
            return;
        }
        DesktopQSTileData desktopQsTileData = obj instanceof DesktopQSTileData ? (DesktopQSTileData) obj : hotspotQSTileController.getDesktopQsTileData();
        if (desktopQsTileData == null) {
            return;
        }
        booleanState.value = desktopQsTileData.isOn();
        booleanState.label = this.mContext.getString(R$string.quick_settings_hotspot_label);
        booleanState.isTransient = desktopQsTileData.isTransient;
        booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.value ? R$drawable.qs_hotspot_icon_on : R$drawable.qs_hotspot_icon_off);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.contentDescription = desktopQsTileData.contentDescription;
        booleanState.state = desktopQsTileData.state;
        String str = desktopQsTileData.secondaryLabel;
        booleanState.secondaryLabel = str;
        booleanState.stateDescription = str;
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
