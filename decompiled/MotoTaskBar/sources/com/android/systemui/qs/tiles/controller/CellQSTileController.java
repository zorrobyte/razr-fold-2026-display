package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.DesktopQSTileData;
import com.motorola.taskbar.TaskBarServiceProxy;

/* JADX INFO: compiled from: CellQSTileController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CellQSTileController extends QSTileController {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CellQSTileController(TaskBarServiceProxy taskBarServiceProxy) {
        super(DesktopQSTileData.QSTileType.MOBILE_DATA, taskBarServiceProxy);
        taskBarServiceProxy.getClass();
    }
}
