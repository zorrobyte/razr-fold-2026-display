package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.DesktopQSTileData;
import com.motorola.taskbar.TaskBarServiceProxy;

/* JADX INFO: compiled from: HotspotQSTileController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HotspotQSTileController extends QSTileController {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HotspotQSTileController(TaskBarServiceProxy taskBarServiceProxy) {
        super(DesktopQSTileData.QSTileType.HOTSPOT, taskBarServiceProxy);
        taskBarServiceProxy.getClass();
    }
}
