package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.DesktopQSTileData;
import com.motorola.taskbar.TaskBarServiceProxy;

/* JADX INFO: compiled from: BluetoothQSTileController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BluetoothQSTileController extends QSTileController {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BluetoothQSTileController(TaskBarServiceProxy taskBarServiceProxy) {
        super(DesktopQSTileData.QSTileType.BLUETOOTH, taskBarServiceProxy);
        taskBarServiceProxy.getClass();
    }
}
