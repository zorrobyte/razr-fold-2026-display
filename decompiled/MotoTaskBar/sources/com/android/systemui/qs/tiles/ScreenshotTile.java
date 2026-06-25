package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.motorola.extendscreenshot.ScreenshotController;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$string;

/* JADX INFO: loaded from: classes.dex */
public class ScreenshotTile extends QSTileImpl {
    private final ScreenshotController mScreenshotController;

    public ScreenshotTile(QSHost qSHost, Looper looper, Handler handler, ScreenshotController screenshotController, ActivityStarter activityStarter) {
        super(qSHost, looper, handler, activityStarter);
        this.mScreenshotController = screenshotController;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick(View view) {
        this.mScreenshotController.takeScreenshot(1, view.getContext().getDisplayId());
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleUpdateState(QSTile.State state, Object obj) {
        Log.d(this.TAG, "update state");
        state.label = this.mContext.getString(R$string.global_action_screenshot);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.State newTileState() {
        Log.d(this.TAG, "init state");
        QSTile.State state = new QSTile.State();
        state.icon = QSTileImpl.ResourceIcon.get(R$drawable.zz_moto_ic_qs_screenshot);
        state.state = 1;
        state.secondaryLabel = "";
        state.handlesLongClick = false;
        return state;
    }
}
