package com.motorola.taskbar.model;

import com.android.systemui.statusbar.policy.CallbackController;

/* JADX INFO: loaded from: classes2.dex */
public interface HardwareDisplayController extends CallbackController {

    public interface HardwareDisplayListener {
        void onHardwareDisplayAdded(int i, int i2, boolean z);

        void onHardwareDisplayIdChanged(int i, int i2, int i3, boolean z);

        void onHardwareDisplayRemoved(int i, int i2);
    }

    int getDisplayId();

    boolean isDesktopMode();
}
