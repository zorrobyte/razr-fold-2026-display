package com.motorola.taskbar.settings.fragment;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import com.motorola.taskbar.guide.R$xml;
import com.motorola.taskbar.settings.MotorolaSettingsSystem;

/* JADX INFO: loaded from: classes2.dex */
public class SoundFragment extends PreferenceFragmentCompat {
    private Context mActivityContext;
    private Context mContext;
    private ContentObserver mMultiAudioFocusObserver = new ContentObserver(new Handler()) { // from class: com.motorola.taskbar.settings.fragment.SoundFragment.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            SoundFragment.this.updateMultiAudioFocusSwitch();
        }
    };
    private SwitchPreference playbackPrefs;

    public static SoundFragment getInstance() {
        return new SoundFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMultiAudioFocusSwitch() {
        this.playbackPrefs.setChecked(MotorolaSettingsSystem.getMultiAudioFocusEnable(getContext().getContentResolver()));
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivityContext = context;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getContext().getContentResolver().registerContentObserver(Settings.Secure.getUriFor("multi_audio_focus_enabled"), false, this.mMultiAudioFocusObserver, UserHandle.myUserId());
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle bundle, String str) {
        setPreferencesFromResource(R$xml.sound, str);
        if (this.mContext == null) {
            this.mContext = getActivity().getApplicationContext();
        }
        this.playbackPrefs = (SwitchPreference) findPreference("settings_multi_playback");
        updateMultiAudioFocusSwitch();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        getContext().getContentResolver().unregisterContentObserver(this.mMultiAudioFocusObserver);
        super.onDestroy();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        if (key == null) {
            return super.onPreferenceTreeClick(preference);
        }
        key.hashCode();
        if (key.equals("settings_multi_playback")) {
            boolean zIsChecked = ((SwitchPreference) preference).isChecked();
            Log.d("SoundFragment", "onPreferenceTreeClick: PREFERENCE_MULTI_PLAYBACK set to: " + zIsChecked);
            MotorolaSettingsSystem.setMultiAudioFocusEnable(this.mContext.getContentResolver(), zIsChecked);
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }
}
