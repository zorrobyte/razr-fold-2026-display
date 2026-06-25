package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.DesktopQSTileData;
import com.motorola.taskbar.TaskBarServiceProxy;

/* JADX INFO: compiled from: DndQSTileController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DndQSTileController extends QSTileController {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DndQSTileController(TaskBarServiceProxy taskBarServiceProxy) {
        super(DesktopQSTileData.QSTileType.DO_NOT_DISTURB, taskBarServiceProxy);
        taskBarServiceProxy.getClass();
    }
}
