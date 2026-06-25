package com.motorola.taskbar.settings;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import com.motorola.taskbar.guide.R$array;
import com.motorola.taskbar.guide.R$string;
import com.motorola.taskbar.guide.R$xml;
import com.motorola.taskbar.settings.MotorolaSettingsSystem;

/* JADX INFO: loaded from: classes2.dex */
public class ScreenTimeoutFragment extends PreferenceFragmentCompat {
    private int defaultPowerSaveVal;
    private int defaultVal;
    private TimePreference lastCheckedPowerPreference;
    private TimePreference lastCheckedPreference;
    private Context mContext;
    private PreferenceScreen mParent;
    private PreferenceCategory mPowerSaveCategory;
    private PreferenceCategory mTimeoutCategory;

    public static ScreenTimeoutFragment getInstance() {
        return new ScreenTimeoutFragment();
    }

    private void initPowerSaveCategory() {
        String[] stringArray = getResources().getStringArray(R$array.power_save_array);
        int[] intArray = getResources().getIntArray(R$array.power_save_times);
        int powerSaveSec = MotorolaSettingsSystem.getPowerSaveSec(this.mContext.getContentResolver(), SettingsActivity.sConnectType);
        this.defaultPowerSaveVal = powerSaveSec;
        if (powerSaveSec == 0) {
            putPowerSaveSec(-1);
            this.defaultPowerSaveVal = -1;
        }
        int i = 0;
        for (String str : stringArray) {
            TimePreference timePreference = new TimePreference(this.mContext);
            timePreference.setTitle(str);
            int i2 = intArray[i];
            if (this.defaultPowerSaveVal == i2) {
                timePreference.setChecked(true);
                this.lastCheckedPowerPreference = timePreference;
            } else {
                timePreference.setChecked(false);
            }
            timePreference.setValue(i2);
            i++;
            timePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: com.motorola.taskbar.settings.ScreenTimeoutFragment$$ExternalSyntheticLambda2
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return this.f$0.lambda$initPowerSaveCategory$1(preference, obj);
                }
            });
            this.mPowerSaveCategory.addPreference(timePreference);
        }
    }

    private void initTimeoutCategory() {
        int i;
        this.mTimeoutCategory.removeAll();
        String[] stringArray = getResources().getStringArray(R$array.timeout_array);
        int[] intArray = getResources().getIntArray(R$array.timeout_times);
        int timeout = MotorolaSettingsSystem.getTimeout(this.mContext.getContentResolver());
        this.defaultVal = timeout;
        int screenOffTimeMax = MotorolaSettingsSystem.getScreenOffTimeMax(this.mContext.getContentResolver());
        if (screenOffTimeMax != 0) {
            i = 0;
            while (i < intArray.length) {
                int i2 = intArray[i];
                if (i2 == screenOffTimeMax) {
                    if (timeout > i2) {
                        timeout = i2;
                    }
                } else if (i2 > screenOffTimeMax) {
                    i--;
                    if (i < 0) {
                        i = 0;
                    }
                    if (timeout >= i2) {
                        timeout = intArray[i];
                    }
                } else {
                    i++;
                }
            }
            i = Integer.MAX_VALUE;
        } else {
            i = Integer.MAX_VALUE;
        }
        if (timeout != this.defaultVal) {
            putTimeOutSec(timeout);
            this.defaultVal = timeout;
        }
        int length = stringArray.length;
        int i3 = 0;
        int i4 = 0;
        while (i3 < length) {
            String str = stringArray[i3];
            if (i4 > i) {
                FooterPreference footerPreference = new FooterPreference(this.mContext);
                footerPreference.setSummary(R$string.footer_summary);
                footerPreference.setAdmin(null);
                this.mTimeoutCategory.addPreference(footerPreference);
                return;
            }
            TimePreference timePreference = new TimePreference(this.mContext);
            timePreference.setTitle(str);
            if (this.defaultVal == intArray[i4]) {
                timePreference.setChecked(true);
                this.lastCheckedPreference = timePreference;
            } else {
                timePreference.setChecked(false);
            }
            timePreference.setValue(intArray[i4]);
            timePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: com.motorola.taskbar.settings.ScreenTimeoutFragment$$ExternalSyntheticLambda0
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return this.f$0.lambda$initTimeoutCategory$2(preference, obj);
                }
            });
            this.mTimeoutCategory.addPreference(timePreference);
            i3++;
            i4++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initPowerSaveCategory$1(Preference preference, Object obj) {
        TimePreference timePreference = (TimePreference) preference;
        if (!((Boolean) obj).booleanValue()) {
            return this.defaultPowerSaveVal != timePreference.getValue();
        }
        try {
            TimePreference timePreference2 = this.lastCheckedPowerPreference;
            if (timePreference2 != null) {
                timePreference2.setChecked(false);
            }
            this.lastCheckedPowerPreference = timePreference;
            putPowerSaveSec(timePreference.getValue());
            this.defaultPowerSaveVal = timePreference.getValue();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initTimeoutCategory$2(Preference preference, Object obj) {
        TimePreference timePreference = (TimePreference) preference;
        if (!((Boolean) obj).booleanValue()) {
            return this.defaultVal != timePreference.getValue();
        }
        try {
            TimePreference timePreference2 = this.lastCheckedPreference;
            if (timePreference2 != null) {
                timePreference2.setChecked(false);
            }
            this.lastCheckedPreference = timePreference;
            putTimeOutSec(timePreference.getValue());
            this.defaultVal = timePreference.getValue();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$updatePowerSaveCategory$0(Preference preference, Object obj) {
        TimePreference timePreference = (TimePreference) preference;
        if (!((Boolean) obj).booleanValue()) {
            return this.defaultPowerSaveVal != timePreference.getValue();
        }
        try {
            TimePreference timePreference2 = this.lastCheckedPowerPreference;
            if (timePreference2 != null) {
                timePreference2.setChecked(false);
            }
            this.lastCheckedPowerPreference = timePreference;
            putPowerSaveSec(timePreference.getValue());
            this.defaultPowerSaveVal = timePreference.getValue();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void putPowerSaveSec(int i) {
        MotorolaSettingsSystem.putPowerSaveSec(this.mContext.getContentResolver(), i, SettingsActivity.sConnectType);
    }

    private void putTimeOutSec(int i) {
        MotorolaSettingsSystem.putTimeout(this.mContext.getContentResolver(), i);
    }

    private void updatePowerSaveCategory() {
        Log.d("ScreenTimeoutFragment", "updatePowerSaveCategory: ");
        this.mPowerSaveCategory.removeAll();
        this.defaultPowerSaveVal = MotorolaSettingsSystem.getPowerSaveSec(this.mContext.getContentResolver(), SettingsActivity.sConnectType);
        int[] intArray = getResources().getIntArray(R$array.power_save_times);
        int powerSaveSecMax = MotorolaSettingsSystem.getPowerSaveSecMax(this.mContext.getContentResolver());
        Log.d("ScreenTimeoutFragment", "updatePowerSaveCategory: powerSaveSecMax = " + powerSaveSecMax);
        int i = this.defaultPowerSaveVal;
        int i2 = Integer.MAX_VALUE;
        if (powerSaveSecMax >= intArray[0]) {
            if (i < 0) {
                i = powerSaveSecMax;
            }
            int i3 = 0;
            while (true) {
                if (i3 >= intArray.length) {
                    break;
                }
                int i4 = intArray[i3];
                if (i4 == powerSaveSecMax) {
                    if (i > i4) {
                        i = i4;
                    }
                    i2 = i3;
                } else if (i4 > powerSaveSecMax) {
                    int i5 = i3 - 1;
                    i2 = i5 < 0 ? 0 : i5;
                    if (i >= i4) {
                        i = intArray[i2];
                    }
                } else {
                    i3++;
                }
            }
        }
        if (i != this.defaultPowerSaveVal) {
            putPowerSaveSec(i);
            this.defaultPowerSaveVal = i;
        }
        int i6 = 0;
        for (String str : getResources().getStringArray(R$array.power_save_array)) {
            if (i6 > i2) {
                FooterPreference footerPreference = new FooterPreference(this.mContext);
                footerPreference.setSummary(R$string.footer_summary);
                footerPreference.setAdmin(null);
                this.mPowerSaveCategory.addPreference(footerPreference);
                return;
            }
            TimePreference timePreference = new TimePreference(this.mContext);
            timePreference.setTitle(str);
            int i7 = intArray[i6];
            if (this.defaultPowerSaveVal == i7) {
                timePreference.setChecked(true);
                this.lastCheckedPowerPreference = timePreference;
            } else {
                timePreference.setChecked(false);
            }
            timePreference.setValue(i7);
            i6++;
            timePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: com.motorola.taskbar.settings.ScreenTimeoutFragment$$ExternalSyntheticLambda1
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    return this.f$0.lambda$updatePowerSaveCategory$0(preference, obj);
                }
            });
            this.mPowerSaveCategory.addPreference(timePreference);
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("ScreenTimeoutFragment", "onCreate: ");
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        Log.d("ScreenTimeoutFragment", "onCreatePreferences: ");
        setPreferencesFromResource(R$xml.timeout, str);
        PreferenceCategory preferenceCategory = (PreferenceCategory) findPreference("time_out_category");
        this.mTimeoutCategory = preferenceCategory;
        this.mParent = (PreferenceScreen) preferenceCategory.getParent();
        this.mPowerSaveCategory = (PreferenceCategory) findPreference("power_save_category");
        if (this.mContext == null) {
            this.mContext = getActivity().getApplicationContext();
        }
        initTimeoutCategory();
        if (SettingsActivity.sConnectType == MotorolaSettingsSystem.ConnectType.DP) {
            Log.d("ScreenTimeoutFragment", "onCreatePreferences: DP");
            this.mParent.removePreference(this.mPowerSaveCategory);
        } else {
            Log.d("ScreenTimeoutFragment", "onCreatePreferences: NOT DP");
            initPowerSaveCategory();
        }
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (SettingsActivity.sConnectType == MotorolaSettingsSystem.ConnectType.DP) {
            this.mParent.removePreference(this.mPowerSaveCategory);
        } else {
            updatePowerSaveCategory();
        }
    }
}
