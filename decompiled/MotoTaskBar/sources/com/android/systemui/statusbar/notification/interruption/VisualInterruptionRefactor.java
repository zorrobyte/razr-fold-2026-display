package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.Flags;

/* JADX INFO: compiled from: VisualInterruptionRefactor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VisualInterruptionRefactor {
    public static final VisualInterruptionRefactor INSTANCE = new VisualInterruptionRefactor();

    private VisualInterruptionRefactor() {
    }

    public static final boolean isEnabled() {
        return Flags.visualInterruptionsRefactor();
    }
}
