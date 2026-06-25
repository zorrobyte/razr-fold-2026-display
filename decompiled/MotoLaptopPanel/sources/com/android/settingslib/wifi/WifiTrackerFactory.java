package com.android.settingslib.wifi;

/* JADX INFO: loaded from: classes.dex */
public class WifiTrackerFactory {
    private static WifiTracker sTestingWifiTracker;

    public static void setTestingWifiTracker(WifiTracker wifiTracker) {
        sTestingWifiTracker = wifiTracker;
    }
}
