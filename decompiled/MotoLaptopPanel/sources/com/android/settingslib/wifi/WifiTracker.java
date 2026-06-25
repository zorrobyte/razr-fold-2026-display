package com.android.settingslib.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.NetworkRequest;
import android.net.NetworkScoreManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.net.wifi.hotspot2.OsuProvider;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;
import androidx.lifecycle.LifecycleObserver;
import com.android.settingslib.R$string;
import com.android.settingslib.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes.dex */
public class WifiTracker implements LifecycleObserver {
    static final long MAX_SCAN_RESULT_AGE_MILLIS = 15000;
    public static boolean sVerboseLogging;
    private final ConnectivityManager mConnectivityManager;
    private final Context mContext;
    private final IntentFilter mFilter;
    private WifiInfo mLastInfo;
    private NetworkInfo mLastNetworkInfo;
    private final WifiListenerExecutor mListener;
    private long mMaxSpeedLabelScoreCacheAge;
    private final NetworkRequest mNetworkRequest;
    private final NetworkScoreManager mNetworkScoreManager;
    private boolean mNetworkScoringUiEnabled;
    private boolean mRegistered;
    Scanner mScanner;
    private WifiNetworkScoreCache mScoreCache;
    private final WifiManager mWifiManager;
    Handler mWorkHandler;
    private HandlerThread mWorkThread;
    private final AtomicBoolean mConnected = new AtomicBoolean(false);
    private final Object mLock = new Object();
    private final List mInternalAccessPoints = new ArrayList();
    private final Set mRequestedScores = new ArraySet();
    private boolean mStaleScanResults = true;
    private boolean mLastScanSucceeded = true;
    private final HashMap mScanResultCache = new HashMap();
    final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.settingslib.wifi.WifiTracker.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                WifiTracker.this.updateWifiState(intent.getIntExtra("wifi_state", 4));
                return;
            }
            if ("android.net.wifi.SCAN_RESULTS".equals(action)) {
                WifiTracker.this.mStaleScanResults = false;
                WifiTracker.this.mLastScanSucceeded = intent.getBooleanExtra("resultsUpdated", true);
                WifiTracker.this.fetchScansAndConfigsAndUpdateAccessPoints();
                return;
            }
            if ("android.net.wifi.CONFIGURED_NETWORKS_CHANGE".equals(action) || "android.net.wifi.LINK_CONFIGURATION_CHANGED".equals(action)) {
                WifiTracker.this.fetchScansAndConfigsAndUpdateAccessPoints();
                return;
            }
            if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                WifiTracker.this.updateNetworkInfo((NetworkInfo) intent.getParcelableExtra("networkInfo"));
                WifiTracker.this.fetchScansAndConfigsAndUpdateAccessPoints();
            } else if ("android.net.wifi.RSSI_CHANGED".equals(action)) {
                WifiTracker.this.updateNetworkInfo(null);
            }
        }
    };

    class Scanner extends Handler {
        private int mRetry;
        final /* synthetic */ WifiTracker this$0;

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            if (this.this$0.mWifiManager.startScan()) {
                this.mRetry = 0;
            } else {
                int i = this.mRetry + 1;
                this.mRetry = i;
                if (i >= 3) {
                    this.mRetry = 0;
                    if (this.this$0.mContext != null) {
                        Toast.makeText(this.this$0.mContext, R$string.wifi_fail_to_scan, 1).show();
                        return;
                    }
                    return;
                }
            }
            sendEmptyMessageDelayed(0, 10000L);
        }

        boolean isScanning() {
            return hasMessages(0);
        }

        void pause() {
            if (WifiTracker.isVerboseLoggingEnabled()) {
                Log.d("WifiTracker", "Scanner pause");
            }
            this.mRetry = 0;
            removeMessages(0);
        }

        void resume() {
            if (WifiTracker.isVerboseLoggingEnabled()) {
                Log.d("WifiTracker", "Scanner resume");
            }
            if (hasMessages(0)) {
                return;
            }
            sendEmptyMessage(0);
        }
    }

    public interface WifiListener {
        void onAccessPointsChanged();

        void onConnectedChanged();

        void onWifiStateChanged(int i);
    }

    class WifiListenerExecutor implements WifiListener {
        private final WifiListener mDelegatee;

        public WifiListenerExecutor(WifiListener wifiListener) {
            this.mDelegatee = wifiListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onWifiStateChanged$0(int i) {
            this.mDelegatee.onWifiStateChanged(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$runAndLog$1(String str, Runnable runnable) {
            if (WifiTracker.this.mRegistered) {
                if (WifiTracker.isVerboseLoggingEnabled()) {
                    Log.i("WifiTracker", str);
                }
                runnable.run();
            }
        }

        private void runAndLog(final Runnable runnable, final String str) {
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.settingslib.wifi.WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$runAndLog$1(str, runnable);
                }
            });
        }

        @Override // com.android.settingslib.wifi.WifiTracker.WifiListener
        public void onAccessPointsChanged() {
            final WifiListener wifiListener = this.mDelegatee;
            wifiListener.getClass();
            runAndLog(new Runnable() { // from class: com.android.settingslib.wifi.WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    wifiListener.onAccessPointsChanged();
                }
            }, "Invoking onAccessPointsChanged callback");
        }

        @Override // com.android.settingslib.wifi.WifiTracker.WifiListener
        public void onConnectedChanged() {
            final WifiListener wifiListener = this.mDelegatee;
            wifiListener.getClass();
            runAndLog(new Runnable() { // from class: com.android.settingslib.wifi.WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    wifiListener.onConnectedChanged();
                }
            }, "Invoking onConnectedChanged callback");
        }

        @Override // com.android.settingslib.wifi.WifiTracker.WifiListener
        public void onWifiStateChanged(final int i) {
            runAndLog(new Runnable() { // from class: com.android.settingslib.wifi.WifiTracker$WifiListenerExecutor$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onWifiStateChanged$0(i);
                }
            }, String.format("Invoking onWifiStateChanged callback with state %d", Integer.valueOf(i)));
        }
    }

    WifiTracker(Context context, WifiListener wifiListener, WifiManager wifiManager, ConnectivityManager connectivityManager, NetworkScoreManager networkScoreManager, IntentFilter intentFilter) {
        boolean z = false;
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mListener = new WifiListenerExecutor(wifiListener);
        this.mConnectivityManager = connectivityManager;
        if (wifiManager != null && wifiManager.isVerboseLoggingEnabled()) {
            z = true;
        }
        sVerboseLogging = z;
        this.mFilter = intentFilter;
        this.mNetworkRequest = new NetworkRequest.Builder().clearCapabilities().addCapability(15).addTransportType(1).build();
        this.mNetworkScoreManager = networkScoreManager;
        HandlerThread handlerThread = new HandlerThread("WifiTracker{" + Integer.toHexString(System.identityHashCode(this)) + "}", 10);
        handlerThread.start();
        setWorkThread(handlerThread);
    }

    private static final boolean DBG() {
        return Log.isLoggable("WifiTracker", 3);
    }

    private void clearAccessPointsAndConditionallyUpdate() {
        synchronized (this.mLock) {
            try {
                if (!this.mInternalAccessPoints.isEmpty()) {
                    this.mInternalAccessPoints.clear();
                    conditionallyNotifyListeners();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void conditionallyNotifyListeners() {
        if (this.mStaleScanResults) {
            return;
        }
        this.mListener.onAccessPointsChanged();
    }

    private void evictOldScans() {
        long j = this.mLastScanSucceeded ? MAX_SCAN_RESULT_AGE_MILLIS : 30000L;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        Iterator it = this.mScanResultCache.values().iterator();
        while (it.hasNext()) {
            if (jElapsedRealtime - (((ScanResult) it.next()).timestamp / 1000) > j) {
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchScansAndConfigsAndUpdateAccessPoints() {
        List listFilterScanResultsByCapabilities = filterScanResultsByCapabilities(this.mWifiManager.getScanResults());
        if (isVerboseLoggingEnabled()) {
            Log.i("WifiTracker", "Fetched scan results: " + listFilterScanResultsByCapabilities);
        }
        updateAccessPoints(listFilterScanResultsByCapabilities, this.mWifiManager.getConfiguredNetworks());
    }

    private List filterScanResultsByCapabilities(List list) {
        if (list == null) {
            return null;
        }
        boolean zIsEnhancedOpenSupported = this.mWifiManager.isEnhancedOpenSupported();
        boolean zIsWpa3SaeSupported = this.mWifiManager.isWpa3SaeSupported();
        boolean zIsWpa3SuiteBSupported = this.mWifiManager.isWpa3SuiteBSupported();
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ScanResult scanResult = (ScanResult) it.next();
            if (scanResult.capabilities.contains("PSK")) {
                arrayList.add(scanResult);
            } else if ((!scanResult.capabilities.contains("SUITE_B_192") || zIsWpa3SuiteBSupported) && ((!scanResult.capabilities.contains("SAE") || zIsWpa3SaeSupported) && (!scanResult.capabilities.contains("OWE") || zIsEnhancedOpenSupported))) {
                arrayList.add(scanResult);
            } else if (isVerboseLoggingEnabled()) {
                Log.v("WifiTracker", "filterScanResultsByCapabilities: Filtering SSID " + scanResult.SSID + " with capabilities: " + scanResult.capabilities);
            }
        }
        return arrayList;
    }

    private AccessPoint getCachedByKey(List list, String str) {
        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            AccessPoint accessPoint = (AccessPoint) listIterator.next();
            if (accessPoint.getKey().equals(str)) {
                listIterator.remove();
                return accessPoint;
            }
        }
        return null;
    }

    private AccessPoint getCachedOrCreate(List list, List list2) {
        AccessPoint cachedByKey = getCachedByKey(list2, AccessPoint.getKey(this.mContext, (ScanResult) list.get(0)));
        if (cachedByKey == null) {
            return new AccessPoint(this.mContext, list);
        }
        cachedByKey.setScanResults(list);
        return cachedByKey;
    }

    private AccessPoint getCachedOrCreateOsu(OsuProvider osuProvider, List list, List list2) {
        AccessPoint cachedByKey = getCachedByKey(list2, AccessPoint.getKey(osuProvider));
        if (cachedByKey == null) {
            return new AccessPoint(this.mContext, osuProvider, list);
        }
        cachedByKey.setScanResults(list);
        return cachedByKey;
    }

    private AccessPoint getCachedOrCreatePasspoint(WifiConfiguration wifiConfiguration, List list, List list2, List list3) {
        AccessPoint cachedByKey = getCachedByKey(list3, AccessPoint.getKey(wifiConfiguration));
        if (cachedByKey == null) {
            return new AccessPoint(this.mContext, wifiConfiguration, list, list2);
        }
        cachedByKey.update(wifiConfiguration);
        cachedByKey.setScanResultsPasspoint(list, list2);
        return cachedByKey;
    }

    private WifiConfiguration getWifiConfigurationForNetworkId(int i, List list) {
        if (list == null) {
            return null;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) it.next();
            if (this.mLastInfo != null && i == wifiConfiguration.networkId) {
                return wifiConfiguration;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSaeOrOwe(WifiConfiguration wifiConfiguration) {
        int security = AccessPoint.getSecurity(wifiConfiguration);
        return security == 5 || security == 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isVerboseLoggingEnabled() {
        return sVerboseLogging || Log.isLoggable("WifiTracker", 2);
    }

    private void requestScoresForNetworkKeys(Collection collection) {
        if (collection.isEmpty()) {
            return;
        }
        if (DBG()) {
            Log.d("WifiTracker", "Requesting scores for Network Keys: " + collection);
        }
        this.mNetworkScoreManager.requestScores((NetworkKey[]) collection.toArray(new NetworkKey[collection.size()]));
        synchronized (this.mLock) {
            this.mRequestedScores.addAll(collection);
        }
    }

    private void updateAccessPoints(List list, List list2) {
        WifiInfo wifiInfo = this.mLastInfo;
        WifiConfiguration wifiConfigurationForNetworkId = wifiInfo != null ? getWifiConfigurationForNetworkId(wifiInfo.getNetworkId(), list2) : null;
        synchronized (this.mLock) {
            try {
                ArrayMap arrayMapUpdateScanResultCache = updateScanResultCache(list);
                List arrayList = new ArrayList(this.mInternalAccessPoints);
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                for (Map.Entry entry : arrayMapUpdateScanResultCache.entrySet()) {
                    Iterator it = ((List) entry.getValue()).iterator();
                    while (it.hasNext()) {
                        NetworkKey networkKeyCreateFromScanResult = NetworkKey.createFromScanResult((ScanResult) it.next());
                        if (networkKeyCreateFromScanResult != null && !this.mRequestedScores.contains(networkKeyCreateFromScanResult)) {
                            arrayList3.add(networkKeyCreateFromScanResult);
                        }
                    }
                    final AccessPoint cachedOrCreate = getCachedOrCreate((List) entry.getValue(), arrayList);
                    List list3 = (List) list2.stream().filter(new Predicate() { // from class: com.android.settingslib.wifi.WifiTracker$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return cachedOrCreate.matches((WifiConfiguration) obj);
                        }
                    }).collect(Collectors.toList());
                    int size = list3.size();
                    if (size == 0) {
                        cachedOrCreate.update(null);
                    } else if (size == 1) {
                        cachedOrCreate.update((WifiConfiguration) list3.get(0));
                    } else {
                        Optional optionalFindFirst = list3.stream().filter(new Predicate() { // from class: com.android.settingslib.wifi.WifiTracker$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return WifiTracker.isSaeOrOwe((WifiConfiguration) obj);
                            }
                        }).findFirst();
                        if (optionalFindFirst.isPresent()) {
                            cachedOrCreate.update((WifiConfiguration) optionalFindFirst.get());
                        } else {
                            cachedOrCreate.update((WifiConfiguration) list3.get(0));
                        }
                    }
                    arrayList2.add(cachedOrCreate);
                }
                ArrayList arrayList4 = new ArrayList(this.mScanResultCache.values());
                arrayList2.addAll(updatePasspointAccessPoints(this.mWifiManager.getAllMatchingWifiConfigs(arrayList4), arrayList));
                arrayList2.addAll(updateOsuAccessPoints(this.mWifiManager.getMatchingOsuProviders(arrayList4), arrayList));
                if (this.mLastInfo != null && this.mLastNetworkInfo != null) {
                    int size2 = arrayList2.size();
                    int i = 0;
                    while (i < size2) {
                        Object obj = arrayList2.get(i);
                        i++;
                        ((AccessPoint) obj).update(wifiConfigurationForNetworkId, this.mLastInfo, this.mLastNetworkInfo);
                    }
                }
                if (arrayList2.isEmpty() && wifiConfigurationForNetworkId != null) {
                    AccessPoint accessPoint = new AccessPoint(this.mContext, wifiConfigurationForNetworkId);
                    accessPoint.update(wifiConfigurationForNetworkId, this.mLastInfo, this.mLastNetworkInfo);
                    arrayList2.add(accessPoint);
                    arrayList3.add(NetworkKey.createFromWifiInfo(this.mLastInfo));
                }
                requestScoresForNetworkKeys(arrayList3);
                int size3 = arrayList2.size();
                int i2 = 0;
                while (i2 < size3) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    ((AccessPoint) obj2).update(this.mScoreCache, this.mNetworkScoringUiEnabled, this.mMaxSpeedLabelScoreCacheAge);
                }
                Collections.sort(arrayList2);
                if (DBG()) {
                    Log.d("WifiTracker", "------ Dumping AccessPoints that were not seen on this scan ------");
                    Iterator it2 = this.mInternalAccessPoints.iterator();
                    while (it2.hasNext()) {
                        String title = ((AccessPoint) it2.next()).getTitle();
                        int size4 = arrayList2.size();
                        int i3 = 0;
                        while (true) {
                            if (i3 >= size4) {
                                Log.d("WifiTracker", "Did not find " + title + " in this scan");
                                break;
                            }
                            Object obj3 = arrayList2.get(i3);
                            i3++;
                            AccessPoint accessPoint2 = (AccessPoint) obj3;
                            if (accessPoint2.getTitle() == null || !accessPoint2.getTitle().equals(title)) {
                            }
                        }
                    }
                    Log.d("WifiTracker", "---- Done dumping AccessPoints that were not seen on this scan ----");
                }
                this.mInternalAccessPoints.clear();
                this.mInternalAccessPoints.addAll(arrayList2);
            } catch (Throwable th) {
                throw th;
            }
        }
        conditionallyNotifyListeners();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNetworkInfo(NetworkInfo networkInfo) {
        if (!isWifiEnabled()) {
            clearAccessPointsAndConditionallyUpdate();
            return;
        }
        if (networkInfo != null) {
            this.mLastNetworkInfo = networkInfo;
            if (DBG()) {
                Log.d("WifiTracker", "mLastNetworkInfo set: " + this.mLastNetworkInfo);
            }
            if (networkInfo.isConnected() != this.mConnected.getAndSet(networkInfo.isConnected())) {
                this.mListener.onConnectedChanged();
            }
        }
        this.mLastInfo = this.mWifiManager.getConnectionInfo();
        if (DBG()) {
            Log.d("WifiTracker", "mLastInfo set as: " + this.mLastInfo);
        }
        WifiInfo wifiInfo = this.mLastInfo;
        WifiConfiguration wifiConfigurationForNetworkId = wifiInfo != null ? getWifiConfigurationForNetworkId(wifiInfo.getNetworkId(), this.mWifiManager.getConfiguredNetworks()) : null;
        synchronized (this.mLock) {
            try {
                boolean z = false;
                boolean z2 = false;
                for (int size = this.mInternalAccessPoints.size() - 1; size >= 0; size--) {
                    AccessPoint accessPoint = (AccessPoint) this.mInternalAccessPoints.get(size);
                    boolean zIsActive = accessPoint.isActive();
                    if (accessPoint.update(wifiConfigurationForNetworkId, this.mLastInfo, this.mLastNetworkInfo)) {
                        if (zIsActive != accessPoint.isActive()) {
                            z = true;
                            z2 = true;
                        } else {
                            z2 = true;
                        }
                    }
                    if (accessPoint.update(this.mScoreCache, this.mNetworkScoringUiEnabled, this.mMaxSpeedLabelScoreCacheAge)) {
                        z = true;
                        z2 = true;
                    }
                }
                if (z) {
                    Collections.sort(this.mInternalAccessPoints);
                }
                if (z2) {
                    conditionallyNotifyListeners();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNetworkScores() {
        synchronized (this.mLock) {
            boolean z = false;
            for (int i = 0; i < this.mInternalAccessPoints.size(); i++) {
                try {
                    if (((AccessPoint) this.mInternalAccessPoints.get(i)).update(this.mScoreCache, this.mNetworkScoringUiEnabled, this.mMaxSpeedLabelScoreCacheAge)) {
                        z = true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z) {
                Collections.sort(this.mInternalAccessPoints);
                conditionallyNotifyListeners();
            }
        }
    }

    private ArrayMap updateScanResultCache(List list) {
        List list2;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ScanResult scanResult = (ScanResult) it.next();
            String str = scanResult.SSID;
            if (str != null && !str.isEmpty()) {
                this.mScanResultCache.put(scanResult.BSSID, scanResult);
            }
        }
        evictOldScans();
        ArrayMap arrayMap = new ArrayMap();
        for (ScanResult scanResult2 : this.mScanResultCache.values()) {
            String str2 = scanResult2.SSID;
            if (str2 != null && str2.length() != 0 && !scanResult2.capabilities.contains("[IBSS]")) {
                String key = AccessPoint.getKey(this.mContext, scanResult2);
                if (arrayMap.containsKey(key)) {
                    list2 = (List) arrayMap.get(key);
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayMap.put(key, arrayList);
                    list2 = arrayList;
                }
                list2.add(scanResult2);
            }
        }
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWifiState(int i) {
        if (isVerboseLoggingEnabled()) {
            Log.d("WifiTracker", "updateWifiState: " + i);
        }
        if (i == 3) {
            synchronized (this.mLock) {
                try {
                    Scanner scanner = this.mScanner;
                    if (scanner != null) {
                        scanner.resume();
                    }
                } finally {
                }
            }
        } else {
            clearAccessPointsAndConditionallyUpdate();
            this.mLastInfo = null;
            this.mLastNetworkInfo = null;
            synchronized (this.mLock) {
                try {
                    Scanner scanner2 = this.mScanner;
                    if (scanner2 != null) {
                        scanner2.pause();
                    }
                } finally {
                }
            }
            this.mStaleScanResults = true;
        }
        this.mListener.onWifiStateChanged(i);
    }

    void forceUpdate() {
        this.mLastInfo = this.mWifiManager.getConnectionInfo();
        this.mLastNetworkInfo = this.mConnectivityManager.getNetworkInfo(this.mWifiManager.getCurrentNetwork());
        fetchScansAndConfigsAndUpdateAccessPoints();
    }

    public boolean isWifiEnabled() {
        WifiManager wifiManager = this.mWifiManager;
        return wifiManager != null && wifiManager.isWifiEnabled();
    }

    void setWorkThread(HandlerThread handlerThread) {
        this.mWorkThread = handlerThread;
        this.mWorkHandler = new Handler(handlerThread.getLooper());
        this.mScoreCache = new WifiNetworkScoreCache(this.mContext, new WifiNetworkScoreCache.CacheListener(this.mWorkHandler) { // from class: com.android.settingslib.wifi.WifiTracker.1
            public void networkCacheUpdated(List list) {
                if (WifiTracker.this.mRegistered) {
                    if (Log.isLoggable("WifiTracker", 2)) {
                        Log.v("WifiTracker", "Score cache was updated with networks: " + list);
                    }
                    WifiTracker.this.updateNetworkScores();
                }
            }
        });
    }

    List updateOsuAccessPoints(Map map, List list) {
        ArrayList arrayList = new ArrayList();
        Set setKeySet = this.mWifiManager.getMatchingPasspointConfigsForOsuProviders(map.keySet()).keySet();
        for (OsuProvider osuProvider : map.keySet()) {
            if (!setKeySet.contains(osuProvider)) {
                arrayList.add(getCachedOrCreateOsu(osuProvider, (List) map.get(osuProvider), list));
            }
        }
        return arrayList;
    }

    List updatePasspointAccessPoints(List list, List list2) {
        ArrayList arrayList = new ArrayList();
        ArraySet arraySet = new ArraySet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            if (arraySet.add(wifiConfiguration.FQDN)) {
                arrayList.add(getCachedOrCreatePasspoint(wifiConfiguration, (List) ((Map) pair.second).get(0), (List) ((Map) pair.second).get(1), list2));
            }
        }
        return arrayList;
    }
}
