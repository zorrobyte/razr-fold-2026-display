package com.android.systemui.statusbar.policy;

/* JADX INFO: loaded from: classes.dex */
public interface DeviceProvisionedController extends CallbackController {

    public interface DeviceProvisionedListener {
        default void onDeviceProvisionedChanged() {
        }

        default void onUserSetupChanged() {
        }

        default void onUserSwitched() {
            onUserSetupChanged();
        }
    }

    int getCurrentUser();

    boolean isCurrentUserSetup();

    boolean isDeviceProvisioned();
}
