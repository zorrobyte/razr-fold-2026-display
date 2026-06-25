package com.android.systemui.util;

import android.provider.DeviceConfig;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class DeviceConfigProxy {
    public void addOnPropertiesChangedListener(String str, Executor executor, DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
        DeviceConfig.addOnPropertiesChangedListener(str, executor, onPropertiesChangedListener);
    }

    public boolean getBoolean(String str, String str2, boolean z) {
        return DeviceConfig.getBoolean(str, str2, z);
    }

    public int getInt(String str, String str2, int i) {
        return DeviceConfig.getInt(str, str2, i);
    }

    public String getProperty(String str, String str2) {
        return DeviceConfig.getProperty(str, str2);
    }
}
