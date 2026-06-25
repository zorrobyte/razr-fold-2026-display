package com.motorola.taskbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.statusbar.policy.CallbackController;
import com.motorola.taskbar.WifiStatusMonitor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class WifiStatusMonitor implements CallbackController {
    private boolean mConnected;
    private final ConnectivityManager mConnectivityManager;
    private final Context mContext;
    private boolean mEnabled;
    private boolean mIsCaptivePortal;
    private boolean mIsDefaultNetwork;
    private boolean mIsProblematical;
    private int mLevel;
    private int mRssi;
    private String mSsid;
    private int mState;
    private String mStatusLabel;
    private WifiInfo mWifiInfo;
    private final WifiManager mWifiManager;
    private int mWifiStandard;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final NetworkRequest mNetworkRequest = new NetworkRequest.Builder().clearCapabilities().addCapability(15).addTransportType(1).build();
    private final ConnectivityManager.NetworkCallback mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.motorola.taskbar.WifiStatusMonitor.1
        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            WifiStatusMonitor.this.mWifiNetwork = network;
            WifiStatusMonitor.this.updateStatusLabel();
            WifiStatusMonitor.this.notifyWifiStatusChanged();
        }
    };
    private final ConnectivityManager.NetworkCallback mDefaultNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.motorola.taskbar.WifiStatusMonitor.2
        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            WifiStatusMonitor.this.mDefaultNetwork = network;
            WifiStatusMonitor.this.mDefaultNetworkCapabilities = networkCapabilities;
            WifiStatusMonitor.this.updateStatusLabel();
            WifiStatusMonitor.this.notifyWifiStatusChanged();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            WifiStatusMonitor.this.mDefaultNetwork = null;
            WifiStatusMonitor.this.mDefaultNetworkCapabilities = null;
            WifiStatusMonitor.this.updateStatusLabel();
            WifiStatusMonitor.this.notifyWifiStatusChanged();
        }
    };
    private Network mDefaultNetwork = null;
    private NetworkCapabilities mDefaultNetworkCapabilities = null;
    private List mListeners = new ArrayList();
    private Network mWifiNetwork = null;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.motorola.taskbar.WifiStatusMonitor.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            WifiStatusMonitor.this.handleBroadcast(intent);
        }
    };

    public interface WifiStatusListener {
        void onWifiStatusChanged(int i, boolean z, String str, int i2, boolean z2, String str2, int i3);
    }

    public WifiStatusMonitor(Context context) {
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        fetchInitialState();
        setListening(true);
    }

    private void fetchInitialState() {
        if (this.mWifiManager == null) {
            return;
        }
        updateWifiState();
        NetworkInfo networkInfo = this.mConnectivityManager.getNetworkInfo(1);
        boolean z = networkInfo != null && networkInfo.isConnected();
        this.mConnected = z;
        this.mWifiInfo = null;
        this.mSsid = null;
        if (z) {
            WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
            this.mWifiInfo = connectionInfo;
            if (connectionInfo != null) {
                if (connectionInfo.isPasspointAp() || this.mWifiInfo.isOsuAp()) {
                    this.mSsid = this.mWifiInfo.getPasspointProviderFriendlyName();
                } else {
                    this.mSsid = getValidSsid(this.mWifiInfo);
                }
                this.mWifiStandard = getWifiStandard(this.mWifiInfo);
                updateRssi(this.mWifiInfo.getRssi());
                maybeRequestNetworkScore();
            }
        }
        updateStatusLabel();
    }

    private String getValidSsid(WifiInfo wifiInfo) {
        String ssid = wifiInfo.getSSID();
        if (ssid != null && !"<unknown ssid>".equals(ssid)) {
            return ssid;
        }
        List<WifiConfiguration> configuredNetworks = this.mWifiManager.getConfiguredNetworks();
        int size = configuredNetworks.size();
        for (int i = 0; i < size; i++) {
            if (configuredNetworks.get(i).networkId == wifiInfo.getNetworkId()) {
                return configuredNetworks.get(i).SSID;
            }
        }
        return null;
    }

    private int getWifiStandard(WifiInfo wifiInfo) {
        if (wifiInfo == null) {
            return -1;
        }
        return wifiInfo.getWifiStandard();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBroadcast(Intent intent) {
        if (this.mWifiManager == null) {
            return;
        }
        String action = intent.getAction();
        if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
            updateWifiState();
            updateStatusLabel();
        } else if (action.equals("android.net.wifi.STATE_CHANGE")) {
            updateWifiState();
            NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
            boolean z = networkInfo != null && networkInfo.isConnected();
            this.mConnected = z;
            this.mWifiInfo = null;
            this.mSsid = null;
            if (z) {
                WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
                this.mWifiInfo = connectionInfo;
                if (connectionInfo != null) {
                    if (connectionInfo.isPasspointAp() || this.mWifiInfo.isOsuAp()) {
                        this.mSsid = this.mWifiInfo.getPasspointProviderFriendlyName();
                    } else {
                        this.mSsid = getValidSsid(this.mWifiInfo);
                    }
                    this.mWifiStandard = getWifiStandard(this.mWifiInfo);
                    updateRssi(this.mWifiInfo.getRssi());
                    maybeRequestNetworkScore();
                }
            }
            updateStatusLabel();
        } else if (action.equals("android.net.wifi.RSSI_CHANGED")) {
            updateRssi(intent.getIntExtra("newRssi", -200));
            updateStatusLabel();
        } else if ("android.intent.action.LOCALE_CHANGED".equals(action)) {
            refreshLocale();
        }
        notifyWifiStatusChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyWifiStatusChanged$0(WifiStatusListener wifiStatusListener) {
        if (this.mListeners.contains(wifiStatusListener)) {
            wifiStatusListener.onWifiStatusChanged(this.mState, this.mConnected, this.mSsid, this.mLevel, this.mIsProblematical, this.mStatusLabel, this.mWifiStandard);
        }
    }

    private void maybeRequestNetworkScore() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyWifiStatusChanged() {
        new ArrayList(this.mListeners).forEach(new Consumer() { // from class: com.motorola.taskbar.WifiStatusMonitor$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$notifyWifiStatusChanged$0((WifiStatusMonitor.WifiStatusListener) obj);
            }
        });
    }

    private void updateRssi(int i) {
        this.mRssi = i;
        this.mLevel = WifiManager.calculateSignalLevel(i, 5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStatusLabel() {
        NetworkCapabilities networkCapabilities;
        this.mIsProblematical = true;
        int i = this.mState;
        if (i == 0) {
            this.mStatusLabel = this.mContext.getString(R$string.wifi_status_turning_off);
            return;
        }
        if (i == 1) {
            this.mStatusLabel = this.mContext.getString(R$string.wifi_status_wifi_off_label);
            return;
        }
        if (i == 2) {
            this.mStatusLabel = this.mContext.getString(R$string.wifi_status_turning_on);
            return;
        }
        if (i == 4) {
            this.mStatusLabel = this.mContext.getString(R$string.wifi_status_wifi_off_label);
            return;
        }
        if (!this.mConnected) {
            this.mStatusLabel = this.mContext.getString(R$string.wifi_status_wifi_not_connected);
            return;
        }
        Network network = this.mWifiNetwork;
        if (network == null || !network.equals(this.mDefaultNetwork)) {
            this.mIsDefaultNetwork = false;
            networkCapabilities = this.mConnectivityManager.getNetworkCapabilities(this.mWifiNetwork);
        } else {
            this.mIsDefaultNetwork = true;
            networkCapabilities = this.mDefaultNetworkCapabilities;
        }
        this.mIsCaptivePortal = false;
        if (networkCapabilities != null) {
            if (networkCapabilities.hasCapability(17)) {
                this.mStatusLabel = this.mContext.getString(R$string.wifi_status_sign_in_required);
                this.mIsCaptivePortal = true;
                return;
            } else if (networkCapabilities.hasCapability(24)) {
                this.mStatusLabel = this.mContext.getString(R$string.wifi_limited_connection);
                return;
            } else if (!networkCapabilities.hasCapability(16)) {
                this.mStatusLabel = this.mContext.getString(R$string.wifi_status_no_internet);
                return;
            }
        }
        this.mIsProblematical = false;
        this.mStatusLabel = this.mContext.getString(R$string.wifi_status_connected_via_ssid, this.mSsid);
    }

    private void updateWifiState() {
        int wifiState = this.mWifiManager.getWifiState();
        this.mState = wifiState;
        this.mEnabled = wifiState == 3;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(WifiStatusListener wifiStatusListener) {
        this.mListeners.add(wifiStatusListener);
        wifiStatusListener.onWifiStatusChanged(this.mState, this.mConnected, this.mSsid, this.mLevel, this.mIsProblematical, this.mStatusLabel, this.mWifiStandard);
    }

    public int getLevel() {
        return this.mLevel;
    }

    public int getWifiStandard() {
        return this.mWifiStandard;
    }

    public boolean isProblematical() {
        return this.mIsProblematical;
    }

    public void refreshLocale() {
        updateStatusLabel();
        notifyWifiStatusChanged();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(WifiStatusListener wifiStatusListener) {
        this.mListeners.remove(wifiStatusListener);
    }

    public void setListening(boolean z) {
        if (!z) {
            this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
            this.mConnectivityManager.unregisterNetworkCallback(this.mDefaultNetworkCallback);
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
            return;
        }
        this.mConnectivityManager.registerNetworkCallback(this.mNetworkRequest, this.mNetworkCallback, this.mHandler);
        this.mConnectivityManager.registerDefaultNetworkCallback(this.mDefaultNetworkCallback, this.mHandler);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.RSSI_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter, 2);
    }
}
