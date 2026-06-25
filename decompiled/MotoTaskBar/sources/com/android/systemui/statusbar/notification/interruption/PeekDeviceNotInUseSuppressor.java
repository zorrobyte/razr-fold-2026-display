package com.android.systemui.statusbar.notification.interruption;

import android.os.PowerManager;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PeekDeviceNotInUseSuppressor extends VisualInterruptionCondition {
    private final int displayId;
    private final PowerManager powerManager;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeekDeviceNotInUseSuppressor(int i, PowerManager powerManager) {
        super(SetsKt.setOf(VisualInterruptionType.PEEK), "device not in use");
        powerManager.getClass();
        this.displayId = i;
        this.powerManager = powerManager;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionCondition
    public boolean shouldSuppress() {
        return !this.powerManager.isInteractive(this.displayId);
    }
}
