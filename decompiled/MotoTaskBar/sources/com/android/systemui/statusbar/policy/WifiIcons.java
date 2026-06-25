package com.android.systemui.statusbar.policy;

import com.motorola.taskbar.R$drawable;

/* JADX INFO: loaded from: classes.dex */
public abstract class WifiIcons {
    public static final int[][] QS_WIFI_6_SIGNAL_STRENGTH;
    public static final int QS_WIFI_DISABLED;
    public static final int QS_WIFI_NO_NETWORK;
    public static final int[][] QS_WIFI_SIGNAL_STRENGTH;
    public static final int[] WIFI_6_FULL_ICONS;
    public static final int[] WIFI_6_NO_INTERNET_ICONS;
    static final int[][] WIFI_6_SIGNAL_STRENGTH;
    public static final int[] WIFI_7_FULL_ICONS;
    public static final int[] WIFI_7_NO_INTERNET_ICONS;
    public static final int[] WIFI_FULL_ICONS;
    static final int WIFI_LEVEL_COUNT;
    public static final int[] WIFI_NO_INTERNET_ICONS;
    static final int WIFI_NO_NETWORK;
    static final int[][] WIFI_SIGNAL_STRENGTH;

    static {
        int i = R$drawable.ic_wifi_signal_0;
        int[] iArr = {i, R$drawable.ic_wifi_signal_1, R$drawable.ic_wifi_signal_2, R$drawable.ic_wifi_signal_3, R$drawable.ic_wifi_signal_4};
        WIFI_FULL_ICONS = iArr;
        int[] iArr2 = {R$drawable.ic_qs_wifi_0, R$drawable.ic_qs_wifi_1, R$drawable.ic_qs_wifi_2, R$drawable.ic_qs_wifi_3, R$drawable.ic_qs_wifi_4};
        WIFI_NO_INTERNET_ICONS = iArr2;
        int[][] iArr3 = {iArr2, iArr};
        QS_WIFI_SIGNAL_STRENGTH = iArr3;
        WIFI_SIGNAL_STRENGTH = iArr3;
        QS_WIFI_DISABLED = i;
        QS_WIFI_NO_NETWORK = i;
        WIFI_NO_NETWORK = i;
        WIFI_LEVEL_COUNT = iArr3[0].length;
        int[] iArr4 = {R$drawable.ic_qs_wifi_disconnected, R$drawable.ic_wifi_6_signal_1, R$drawable.ic_wifi_6_signal_2, R$drawable.ic_wifi_6_signal_3, R$drawable.ic_wifi_6_signal_4};
        WIFI_6_FULL_ICONS = iArr4;
        int[] iArr5 = {R$drawable.ic_no_internet_wifi_6_0, R$drawable.ic_no_internet_wifi_6_1, R$drawable.ic_no_internet_wifi_6_2, R$drawable.ic_no_internet_wifi_6_3, R$drawable.ic_no_internet_wifi_6_4};
        WIFI_6_NO_INTERNET_ICONS = iArr5;
        WIFI_7_FULL_ICONS = new int[]{R$drawable.ic_wifi_7_signal_0, R$drawable.ic_wifi_7_signal_1, R$drawable.ic_wifi_7_signal_2, R$drawable.ic_wifi_7_signal_3, R$drawable.ic_wifi_7_signal_4};
        WIFI_7_NO_INTERNET_ICONS = new int[]{R$drawable.ic_no_internet_wifi_7_0, R$drawable.ic_no_internet_wifi_7_1, R$drawable.ic_no_internet_wifi_7_2, R$drawable.ic_no_internet_wifi_7_3, R$drawable.ic_no_internet_wifi_7_4};
        int[][] iArr6 = {iArr5, iArr4};
        QS_WIFI_6_SIGNAL_STRENGTH = iArr6;
        WIFI_6_SIGNAL_STRENGTH = iArr6;
    }
}
