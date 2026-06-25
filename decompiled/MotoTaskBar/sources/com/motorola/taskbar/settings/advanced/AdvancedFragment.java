package com.motorola.taskbar.settings.advanced;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.taskbar.guide.R$xml;
import com.motorola.taskbar.settings.MotorolaSettingsSystem;
import com.motorola.taskbar.settings.SettingsActivity;
import com.motorola.taskbar.settings.TimePreference;
import com.motorola.taskbar.settings.utils.Utils;
import com.motorola.taskbar.utils.ExtendedFunction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class AdvancedFragment extends PreferenceFragmentCompat {
    private Context mActivityContext;
    private Context mContext;
    private PreferenceCategory mDisplayPreferenceCategory;
    private Handler mHandler;
    private List mSupportedModes = new ArrayList();
    private ContentObserver mDndObserver = new ContentObserver(new Handler()) { // from class: com.motorola.taskbar.settings.advanced.AdvancedFragment.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            AdvancedFragment.this.updateDndSwitch();
        }
    };

    private static String getDisplayModeText(Display.Mode mode) {
        return mode.getPhysicalWidth() + " x " + mode.getPhysicalHeight();
    }

    public static AdvancedFragment getInstance() {
        return new AdvancedFragment();
    }

    private void initAppLaunchPreference() {
        Preference preferenceFindPreference = findPreference("open_same_app");
        if (Utils.isOwnerUser()) {
            return;
        }
        preferenceFindPreference.setVisible(false);
    }

    private boolean isDisplayModeExist(Display.Mode mode) {
        for (Display.Mode mode2 : this.mSupportedModes) {
            if (mode2.getPhysicalWidth() == mode.getPhysicalWidth() && mode2.getPhysicalHeight() == mode.getPhysicalHeight()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDisplayModeMatch(Display.Mode mode, int i, int i2) {
        return mode != null && mode.getPhysicalWidth() == i && mode.getPhysicalHeight() == i2;
    }

    private boolean isSupportDisplayMode() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActiveDisplayMode(Display.Mode mode) {
        if (isSupportDisplayMode()) {
            int size = this.mSupportedModes.size();
            int preferenceCount = this.mDisplayPreferenceCategory.getPreferenceCount();
            int i = 0;
            while (true) {
                if (i >= size) {
                    i = -1;
                    break;
                } else if (isDisplayModeMatch((Display.Mode) this.mSupportedModes.get(i), mode.getPhysicalWidth(), mode.getPhysicalHeight())) {
                    break;
                } else {
                    i++;
                }
            }
            int i2 = 0;
            while (i2 < preferenceCount) {
                ((TimePreference) this.mDisplayPreferenceCategory.getPreference(i2)).setChecked(i2 == i);
                i2++;
            }
        }
    }

    private void updateDesktopSwitch() {
        ((SwitchPreference) findPreference("desktop_auto_add")).setChecked(MotorolaSettingsSystem.autoAddIconToHomeEnable(this.mContext.getContentResolver()));
    }

    private void updateDisplayPreferenceCategory() {
        if (isSupportDisplayMode()) {
            this.mDisplayPreferenceCategory.removeAll();
            int size = this.mSupportedModes.size();
            for (int i = 0; i < size; i++) {
                Display.Mode mode = (Display.Mode) this.mSupportedModes.get(i);
                TimePreference timePreference = new TimePreference(this.mActivityContext);
                timePreference.setTitle(getDisplayModeText(mode));
                timePreference.setValue(i);
                timePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: com.motorola.taskbar.settings.advanced.AdvancedFragment.3
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public boolean onPreferenceClick(Preference preference) {
                        Display.Mode mode2 = (Display.Mode) AdvancedFragment.this.mSupportedModes.get(((TimePreference) preference).getValue());
                        AdvancedFragment.this.updateActiveDisplayMode(mode2);
                        MotorolaSettingsSystem.setDisplayModeSetting(AdvancedFragment.this.mActivityContext.getContentResolver(), SettingsActivity.sConnectType, mode2.getPhysicalWidth(), mode2.getPhysicalHeight(), mode2.getRefreshRate());
                        return true;
                    }
                });
                this.mDisplayPreferenceCategory.addPreference(timePreference);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDndSwitch() {
        ((SwitchPreference) findPreference("settings_dnd")).setChecked(MotorolaSettingsSystem.getDisablePopupNotification(this.mContext.getContentResolver()));
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivityContext = context;
        Display.Mode[] desktopSupportedModes = ExtendedFunction.getDesktopSupportedModes(context.getDisplay());
        this.mSupportedModes.clear();
        for (Display.Mode mode : desktopSupportedModes) {
            if (!isDisplayModeExist(mode)) {
                this.mSupportedModes.add(mode);
            }
        }
        this.mSupportedModes.sort(new Comparator(this) { // from class: com.motorola.taskbar.settings.advanced.AdvancedFragment.2
            @Override // java.util.Comparator
            public int compare(Display.Mode mode2, Display.Mode mode3) {
                int physicalWidth = mode2.getPhysicalWidth();
                int physicalHeight = mode2.getPhysicalHeight();
                int iMax = Math.max(physicalWidth, physicalHeight);
                int iMin = Math.min(physicalWidth, physicalHeight);
                int physicalWidth2 = mode3.getPhysicalWidth();
                int physicalHeight2 = mode3.getPhysicalHeight();
                int iMax2 = Math.max(physicalWidth2, physicalHeight2);
                int iCompare = Integer.compare(Math.min(physicalWidth2, physicalHeight2), iMin);
                int iCompare2 = Integer.compare(iMax2, iMax);
                int iCompare3 = Float.compare(mode3.getRefreshRate(), mode2.getRefreshRate());
                int iCompare4 = Integer.compare(physicalWidth2 / physicalHeight2, physicalWidth / physicalHeight);
                if (iCompare != 0) {
                    return iCompare;
                }
                if (iCompare2 != 0) {
                    return iCompare2;
                }
                if (iCompare3 != 0) {
                    return iCompare3;
                }
                if (iCompare4 != 0) {
                    return iCompare4;
                }
                return 0;
            }
        });
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        HandlerThread handlerThread = new HandlerThread("DesktopHandlerThread", 10);
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
        getContext().getContentResolver().registerContentObserver(MotorolaSettings.System.getUriFor("settings_disable_popup_notification"), false, this.mDndObserver, UserHandle.myUserId());
        initAppLaunchPreference();
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        setPreferencesFromResource(R$xml.advanced, str);
        if (this.mContext == null) {
            this.mContext = getActivity().getApplicationContext();
        }
        this.mDisplayPreferenceCategory = (PreferenceCategory) findPreference("display_setting");
        getPreferenceScreen().removePreference(this.mDisplayPreferenceCategory);
        boolean phoneInWindowEnable = MotorolaSettingsSystem.getPhoneInWindowEnable(this.mContext.getContentResolver());
        SwitchPreference switchPreference = (SwitchPreference) findPreference("settings_phone_in_window");
        switchPreference.setChecked(phoneInWindowEnable);
        switchPreference.setVisible(false);
        updateDndSwitch();
        updateDisplayPreferenceCategory();
        updateDesktopSwitch();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        getContext().getContentResolver().unregisterContentObserver(this.mDndObserver);
        super.onDestroy();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        if (key == null) {
            return super.onPreferenceTreeClick(preference);
        }
        key.hashCode();
        switch (key) {
            case "settings_phone_in_window":
                MotorolaSettingsSystem.putPhoneInWindowEnable(this.mContext.getContentResolver(), ((SwitchPreference) preference).isChecked());
                break;
            case "open_same_app":
                Intent intent = new Intent();
                intent.setAction("com.motorola.taskbar.settings.AppConfiger");
                intent.setPackage(this.mContext.getPackageName());
                intent.setFlags(268500992);
                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                activityOptionsMakeBasic.setLaunchWindowingMode(1);
                activityOptionsMakeBasic.setLaunchDisplayId(SettingsActivity.sDisplayId);
                this.mContext.startActivity(intent, activityOptionsMakeBasic.toBundle());
                break;
            case "desktop_auto_add":
                MotorolaSettingsSystem.setAutoAddIconToHome(this.mContext.getContentResolver(), ((SwitchPreference) preference).isChecked());
                break;
            case "settings_dnd":
                SwitchPreference switchPreference = (SwitchPreference) preference;
                Log.d("AdvancedFragment", "onPreferenceTreeClick: PREFERENCE_DND set to: " + switchPreference.isChecked());
                MotorolaSettingsSystem.putDisablePopupNotification(this.mContext.getContentResolver(), switchPreference.isChecked());
                break;
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        DisplayInfo displayInfo = new DisplayInfo();
        this.mActivityContext.getDisplay().getDisplayInfo(displayInfo);
        updateActiveDisplayMode(new Display.Mode(0, displayInfo.logicalWidth, displayInfo.logicalHeight, 0.0f));
    }
}
