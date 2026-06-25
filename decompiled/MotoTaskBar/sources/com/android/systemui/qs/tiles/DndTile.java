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
import com.android.systemui.qs.tiles.controller.DndQSTileController;
import com.android.systemui.qs.tiles.controller.QSTileControllerCallback;
import com.android.systemui.res.R$drawable;
import com.android.systemui.res.R$string;
import com.motorola.taskbar.DesktopQSTileData;

/* JADX INFO: loaded from: classes.dex */
public class DndTile extends QSTileImpl {
    private static final Intent ZEN_SETTINGS = new Intent("android.settings.ZEN_MODE_SETTINGS");
    private final QSTileControllerCallback mCallback;
    private final DndQSTileController mController;
    private boolean mListening;

    public DndTile(QSHost qSHost, Looper looper, Handler handler, ActivityStarter activityStarter, DndQSTileController dndQSTileController) {
        super(qSHost, looper, handler, activityStarter);
        QSTileControllerCallback qSTileControllerCallback = new QSTileControllerCallback() { // from class: com.android.systemui.qs.tiles.DndTile.1
            @Override // com.android.systemui.qs.tiles.controller.QSTileControllerCallback
            public void onDataChanged(DesktopQSTileData desktopQSTileData) {
                DndTile.this.refreshState(desktopQSTileData);
            }
        };
        this.mCallback = qSTileControllerCallback;
        this.mController = dndQSTileController;
        dndQSTileController.observe(getLifecycle(), qSTileControllerCallback);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return ZEN_SETTINGS;
    }

    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_dnd_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick(View view) {
        this.mController.handleClick(view.getContext().getDisplayId());
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleDestroy() {
        super.handleDestroy();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleSecondaryClick(View view) {
        handleLongClick(view);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        DndQSTileController dndQSTileController = this.mController;
        if (dndQSTileController == null) {
            return;
        }
        DesktopQSTileData desktopQsTileData = obj instanceof DesktopQSTileData ? (DesktopQSTileData) obj : dndQSTileController.getDesktopQsTileData();
        if (desktopQsTileData == null) {
            return;
        }
        boolean zIsOn = desktopQsTileData.isOn();
        booleanState.dualTarget = true;
        booleanState.value = zIsOn;
        booleanState.state = zIsOn ? 2 : 1;
        booleanState.icon = QSTileImpl.ResourceIcon.get(zIsOn ? R$drawable.qs_dnd_icon_on : R$drawable.qs_dnd_icon_off);
        booleanState.label = getTileLabel();
        booleanState.secondaryLabel = desktopQsTileData.secondaryLabel;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_adjust_volume");
        booleanState.contentDescription = desktopQsTileData.contentDescription;
        booleanState.dualLabelContentDescription = this.mContext.getResources().getString(R$string.accessibility_quick_settings_open_settings, getTileLabel());
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.forceExpandIcon = false;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleUserSwitch(int i) {
        super.handleUserSwitch(i);
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
