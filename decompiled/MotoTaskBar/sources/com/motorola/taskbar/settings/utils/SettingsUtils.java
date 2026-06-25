package com.motorola.taskbar.settings.utils;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.taskbar.guide.R$array;
import com.motorola.taskbar.settings.MotorolaSettingsSystem;

/* JADX INFO: loaded from: classes2.dex */
public abstract class SettingsUtils {
    private static DongleCallback mCallback;
    private static Context mContext;
    public static String mDongleNewVersion;
    public static int mDongleUpgradeState;
    public static String mDongleVersion;
    private static BroadcastReceiver mKeyboardHelperReceiver = new BroadcastReceiver() { // from class: com.motorola.taskbar.settings.utils.SettingsUtils.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("display_id", 0);
            if (intExtra == 0 || TextUtils.isEmpty(action)) {
                return;
            }
            action.hashCode();
            if (action.equals("com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS")) {
                SettingsUtils.toggleShortcutHelper(intExtra, false);
            } else if (action.equals("com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS")) {
                SettingsUtils.toggleShortcutHelper(intExtra, true);
            }
        }
    };
    private static ContentObserver mScreenTimeoutObserver = new ContentObserver(new Handler()) { // from class: com.motorola.taskbar.settings.utils.SettingsUtils.2
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            Log.d("SettingsUtils", "mPowerSaveObserver onChange: ");
            SettingsUtils.updateTimeoutIfNeed();
        }
    };
    private static ContentObserver mPowerSaveObserver = new ContentObserver(new Handler()) { // from class: com.motorola.taskbar.settings.utils.SettingsUtils.3
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            Log.d("SettingsUtils", "mPowerSaveObserver onChange: ");
            SettingsUtils.updatePowerSaveIfNeed(MotorolaSettingsSystem.ConnectType.RDP);
            SettingsUtils.updatePowerSaveIfNeed(MotorolaSettingsSystem.ConnectType.MIRROR);
        }
    };
    public static boolean mDongleConnected = false;
    public static boolean mDongleUpgrading = false;
    private static BroadcastReceiver mDongleReceiver = new BroadcastReceiver() { // from class: com.motorola.taskbar.settings.utils.SettingsUtils.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            SettingsUtils.mDongleVersion = intent.getStringExtra("extra_dongle_current_version");
            SettingsUtils.mDongleNewVersion = intent.getStringExtra("extra_dongle_upgrade_version");
            SettingsUtils.mDongleUpgradeState = intent.getIntExtra("extra_dongle_upgrade_state", 0);
            Log.d("dongle", "onReceive mDongleVersion = " + SettingsUtils.mDongleVersion);
            Log.d("dongle", "onReceive mDongleUpgradeState = " + SettingsUtils.mDongleUpgradeState);
            int i = SettingsUtils.mDongleUpgradeState;
            if (4 == i || 5 == i) {
                SettingsUtils.mDongleUpgrading = true;
            } else {
                SettingsUtils.mDongleUpgrading = false;
            }
            if (6 != SettingsUtils.mDongleUpgradeState) {
                SettingsUtils.mDongleConnected = true;
                return;
            }
            SettingsUtils.mDongleConnected = false;
            if (SettingsUtils.mCallback != null) {
                SettingsUtils.mCallback.onDongleRefresh();
            }
        }
    };

    public interface DongleCallback {
        void onDongleRefresh();
    }

    public static void init(Context context) {
        mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS");
        intentFilter.addAction("com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS");
        mContext.registerReceiver(mKeyboardHelperReceiver, intentFilter, 2);
        registerDongleReceiver();
        initTimeOutSettings(context);
        initPowerSaveSettings(context);
    }

    private static void initPowerSaveSettings(Context context) {
        updatePowerSaveIfNeed(MotorolaSettingsSystem.ConnectType.RDP);
        updatePowerSaveIfNeed(MotorolaSettingsSystem.ConnectType.MIRROR);
        context.getContentResolver().registerContentObserver(MotorolaSettings.Global.getUriFor("ready_for_power_save_sec_max"), false, mPowerSaveObserver, UserHandle.myUserId());
    }

    private static void initTimeOutSettings(Context context) {
        updateTimeoutIfNeed();
        context.getContentResolver().registerContentObserver(MotorolaSettings.Global.getUriFor("desktop_max_screen_timeout_seconds"), false, mScreenTimeoutObserver, UserHandle.myUserId());
    }

    public static boolean isDongleConnected() {
        return mDongleConnected;
    }

    public static boolean isDongleUpgradeAvailable() {
        int i = mDongleUpgradeState;
        return i >= 3 && i <= 5;
    }

    public static void registerDongleReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.motorola.mobiledesktop.action_dongle_upgrade_info");
        mContext.registerReceiver(mDongleReceiver, intentFilter, 2);
    }

    public static void removeDongleCallback() {
        mCallback = null;
    }

    public static void setDongleCallback(DongleCallback dongleCallback) {
        mCallback = dongleCallback;
    }

    public static void startDongleUpgrade() {
        mDongleUpgrading = true;
        Intent intent = new Intent("com.motorola.mobiledesktop.action_dongle_upgrade_start");
        intent.setPackage("com.motorola.mobiledesktop");
        mContext.sendBroadcast(intent, "com.motorola.mobiledesktop.permission.INTERNAL");
    }

    public static void toggleShortcutHelper(int i, boolean z) {
        if (i == 0) {
            return;
        }
        if (!z) {
            mContext.sendBroadcast(new Intent("com.motorola.taskbar.settings.DISMISS_HELPER"));
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction("com.motorola.taskbar.settings.SHORTCUT_HELPER");
            intent.setPackage(mContext.getPackageName());
            intent.setFlags(268500992);
            ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            activityOptionsMakeBasic.setLaunchWindowingMode(1);
            activityOptionsMakeBasic.setLaunchDisplayId(i);
            mContext.startActivity(intent, activityOptionsMakeBasic.toBundle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updatePowerSaveIfNeed(MotorolaSettingsSystem.ConnectType connectType) {
        int iMin;
        ContentResolver contentResolver = mContext.getContentResolver();
        int[] intArray = mContext.getResources().getIntArray(R$array.power_save_times);
        int powerSaveSec = MotorolaSettingsSystem.getPowerSaveSec(contentResolver, connectType);
        Log.d("SettingsUtils", "updatePowerSaveIfNeed: " + connectType.name() + " current is" + powerSaveSec);
        int powerSaveSecMax = MotorolaSettingsSystem.getPowerSaveSecMax(contentResolver);
        if (powerSaveSecMax > 0) {
            int i = 0;
            if (powerSaveSecMax < intArray[0]) {
                Log.d("SettingsUtils", "maxShieldTime invalid : " + powerSaveSecMax);
                return;
            }
            iMin = powerSaveSec < 0 ? Math.min(powerSaveSecMax, 3600) : powerSaveSec;
            while (true) {
                if (i >= intArray.length) {
                    break;
                }
                int i2 = intArray[i];
                if (i2 == powerSaveSecMax) {
                    if (iMin > i2) {
                        iMin = i2;
                    }
                } else if (i2 > powerSaveSecMax) {
                    int i3 = i - 1;
                    if (iMin >= i2) {
                        iMin = intArray[i3];
                    }
                } else {
                    i++;
                }
            }
        } else {
            iMin = powerSaveSec;
        }
        if (iMin == powerSaveSec) {
            Log.d("SettingsUtils", "No NEED");
            return;
        }
        MotorolaSettingsSystem.putPowerSaveSec(mContext.getContentResolver(), iMin, connectType);
        Log.d("SettingsUtils", "Update to " + iMin + " caused maxShieldTime is:" + powerSaveSecMax);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateTimeoutIfNeed() {
        int i;
        ContentResolver contentResolver = mContext.getContentResolver();
        int[] intArray = mContext.getResources().getIntArray(R$array.timeout_times);
        int timeout = MotorolaSettingsSystem.getTimeout(contentResolver);
        Log.d("SettingsUtils", "updateTimeoutIfNeed: current is" + timeout);
        int screenOffTimeMax = MotorolaSettingsSystem.getScreenOffTimeMax(contentResolver);
        if (screenOffTimeMax != 0) {
            int i2 = 0;
            if (screenOffTimeMax < intArray[0]) {
                Log.d("SettingsUtils", "maxShieldTime invalid : " + screenOffTimeMax);
                return;
            }
            while (true) {
                if (i2 >= intArray.length) {
                    break;
                }
                i = intArray[i2];
                if (i == screenOffTimeMax) {
                    if (timeout <= i) {
                        break;
                    }
                } else if (i > screenOffTimeMax) {
                    int i3 = i2 - 1;
                    if (timeout >= i) {
                        i = intArray[i3];
                    }
                } else {
                    i2++;
                }
            }
            i = timeout;
        } else {
            i = timeout;
        }
        if (i == timeout) {
            Log.d("SettingsUtils", "No NEED");
            return;
        }
        MotorolaSettingsSystem.putTimeout(mContext.getContentResolver(), i);
        Log.d("SettingsUtils", "Update to " + i + " caused maxShieldTime is:" + screenOffTimeMax);
    }
}
