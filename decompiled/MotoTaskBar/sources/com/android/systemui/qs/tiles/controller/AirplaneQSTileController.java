package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.DesktopQSTileData;
import com.motorola.taskbar.TaskBarServiceProxy;

/* JADX INFO: compiled from: AirplaneQSTileController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AirplaneQSTileController extends QSTileController {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AirplaneQSTileController(TaskBarServiceProxy taskBarServiceProxy) {
        super(DesktopQSTileData.QSTileType.AIRPLANE_MODE, taskBarServiceProxy);
        taskBarServiceProxy.getClass();
    }
}
