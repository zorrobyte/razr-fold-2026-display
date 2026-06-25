package com.motorola.taskbar.settings;

import android.content.ComponentName;
import android.content.Intent;
import android.database.ContentObserver;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.motorola.taskbar.guide.R$xml;
import com.motorola.taskbar.settings.RadioSwitchPreference;
import com.motorola.taskbar.settings.prefs.SeekbarPreference;
import com.motorola.taskbar.settings.prefs.ThreeRadioPreference;
import com.motorola.taskbar.settings.utils.Utils;

/* JADX INFO: loaded from: classes2.dex */
public class MKFragment extends PreferenceFragmentCompat implements RadioSwitchPreference.OnCheckedChangeListener, ThreeRadioPreference.OnCheckListener {
    public static final String TAG = MKFragment.class.getSimpleName();
    private Preference languageAndInputPrefs;
    private InputManager mIm;
    private RadioSwitchPreference mRadioSwitchPreference;
    private ContentObserver mSpeedObserver = new ContentObserver(new Handler()) { // from class: com.motorola.taskbar.settings.MKFragment.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            MKFragment.this.onSpeedChanged();
        }
    };
    private SeekbarPreference mouseSpeedPrefs;
    private ThreeRadioPreference pointerSizePrefs;

    public static MKFragment getInstance() {
        return new MKFragment();
    }

    private void initLanguageAndInput() {
        Preference preferenceFindPreference = findPreference("system_settings_language");
        this.languageAndInputPrefs = preferenceFindPreference;
        preferenceFindPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: com.motorola.taskbar.settings.MKFragment$$ExternalSyntheticLambda0
            @Override // androidx.preference.Preference.OnPreferenceClickListener
            public final boolean onPreferenceClick(Preference preference) {
                return this.f$0.lambda$initLanguageAndInput$0(preference);
            }
        });
    }

    private void initMouseSpeedPrefs() {
        this.mouseSpeedPrefs = (SeekbarPreference) findPreference("settings_mouse_speed");
        if (Utils.isHDMI(getContext(), SettingsActivity.sDisplayId)) {
            this.mouseSpeedPrefs.setEnabled(true);
        } else {
            this.mouseSpeedPrefs.setEnabled(false);
        }
        getContext().getContentResolver().registerContentObserver(Settings.System.getUriFor("pointer_speed"), true, this.mSpeedObserver);
    }

    private void initPointerSizePrefs() {
        this.pointerSizePrefs = (ThreeRadioPreference) findPreference("settings_mouse_pointer");
        int pointerSize = MotorolaSettingsSystem.getPointerSize(getContext().getContentResolver());
        Log.d(TAG, "initPointerSizePrefs: " + pointerSize);
        this.pointerSizePrefs.setChecked(pointerSize);
        this.pointerSizePrefs.setOnChangeListener(this);
    }

    private void initReversePrimaryButton() {
        RadioSwitchPreference radioSwitchPreference = (RadioSwitchPreference) findPreference("settings_mouse_primary_button");
        this.mRadioSwitchPreference = radioSwitchPreference;
        radioSwitchPreference.setOnCheckedChangeListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initLanguageAndInput$0(Preference preference) {
        Intent intent = new Intent();
        intent.setComponent(ComponentName.unflattenFromString("com.android.settings/.LanguageSettings"));
        intent.setPackage("com.android.settings");
        intent.addFlags(268435456);
        startActivity(intent);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSpeedChanged() {
        this.mouseSpeedPrefs.updatePointerSpeed();
    }

    private void updateReversePrimaryButton() {
        this.mRadioSwitchPreference.setChecked(MotorolaSettingsSystem.getReversePrimaryButton(getContext().getContentResolver()));
    }

    @Override // com.motorola.taskbar.settings.RadioSwitchPreference.OnCheckedChangeListener
    public void onCheckedChanged(boolean z) {
        MotorolaSettingsSystem.setReversePrimaryButton(getContext().getContentResolver(), z);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mIm = (InputManager) getContext().getSystemService("input");
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        Log.d(TAG, "onCreatePreferences: ");
        setPreferencesFromResource(R$xml.mouse_keyboard, str);
        initMouseSpeedPrefs();
        initPointerSizePrefs();
        initLanguageAndInput();
        initReversePrimaryButton();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.mSpeedObserver != null) {
            getContext().getContentResolver().unregisterContentObserver(this.mSpeedObserver);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        initMouseSpeedPrefs();
        initPointerSizePrefs();
        updateReversePrimaryButton();
    }

    @Override // com.motorola.taskbar.settings.prefs.ThreeRadioPreference.OnCheckListener
    public void onThreeButtonCheckedChanged(int i) {
        Log.d(TAG, "onThreeButtonCheckedChanged: set pointer size: " + i);
        MotorolaSettingsSystem.setPointerSize(getContext().getContentResolver(), i);
    }
}
