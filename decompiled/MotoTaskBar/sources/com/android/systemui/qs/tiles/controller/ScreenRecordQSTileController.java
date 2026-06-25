package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.DesktopQSTileData;
import com.motorola.taskbar.TaskBarServiceProxy;

/* JADX INFO: compiled from: ScreenRecordQSTileController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ScreenRecordQSTileController extends QSTileController {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenRecordQSTileController(TaskBarServiceProxy taskBarServiceProxy) {
        super(DesktopQSTileData.QSTileType.SCREEN_RECORD, taskBarServiceProxy);
        taskBarServiceProxy.getClass();
    }
}
