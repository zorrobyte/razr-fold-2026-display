package com.motorola.taskbar.sysicons;

import android.content.Context;
import android.hardware.input.InputManager;
import android.util.AttributeSet;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.KeyButtonDrawable;
import com.android.systemui.statusbar.policy.KeyButtonView;
import com.android.systemui.statusbar.policy.WifiIcons;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.WifiStatusMonitor;

/* JADX INFO: loaded from: classes2.dex */
public class WifiSysIconView extends KeyButtonView implements WifiStatusMonitor.WifiStatusListener {
    private static final int[] WIFI_FULL_ICONS = WifiIcons.WIFI_FULL_ICONS;
    private static final int[] WIFI_NO_INTERNET_ICONS = WifiIcons.WIFI_NO_INTERNET_ICONS;
    private WifiStatusMonitor mWifiStatusMonitor;

    public WifiSysIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WifiSysIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public WifiSysIconView(Context context, AttributeSet attributeSet, int i, InputManager inputManager) {
        super(context, attributeSet, i, inputManager);
    }

    private KeyButtonDrawable getKeyButtonDrawable(int i) {
        return KeyButtonDrawable.create(getContext(), i, false);
    }

    public static int getWifiConnectedIcon(String str, int i, boolean z, String str2, int i2) {
        int iMax = Math.max(0, i);
        int[] iArr = WIFI_FULL_ICONS;
        int iMin = Math.min(iMax, iArr.length - 1);
        return i2 == 6 ? z ? WifiIcons.WIFI_6_NO_INTERNET_ICONS[iMin] : WifiIcons.WIFI_6_FULL_ICONS[iMin] : i2 == 8 ? z ? WifiIcons.WIFI_7_NO_INTERNET_ICONS[iMin] : WifiIcons.WIFI_7_FULL_ICONS[iMin] : z ? WIFI_NO_INTERNET_ICONS[iMin] : iArr[iMin];
    }

    @Override // android.widget.ImageView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        WifiStatusMonitor wifiStatusMonitor = (WifiStatusMonitor) Dependency.get(WifiStatusMonitor.class);
        this.mWifiStatusMonitor = wifiStatusMonitor;
        wifiStatusMonitor.addCallback((WifiStatusMonitor.WifiStatusListener) this);
    }

    @Override // com.android.systemui.statusbar.policy.KeyButtonView, android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mWifiStatusMonitor.removeCallback((WifiStatusMonitor.WifiStatusListener) this);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override // com.motorola.taskbar.WifiStatusMonitor.WifiStatusListener
    public void onWifiStatusChanged(int i, boolean z, String str, int i2, boolean z2, String str2, int i3) {
        int wifiConnectedIcon = R$drawable.ic_qs_wifi_disconnected;
        if (i != 0 && i != 1 && i != 2 && i == 3 && z) {
            wifiConnectedIcon = getWifiConnectedIcon(str, i2, z2, str2, i3);
        }
        setImageDrawable(getKeyButtonDrawable(wifiConnectedIcon));
        this.mHoverWrapper.setToolTipText(str2);
    }
}
