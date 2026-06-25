package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import com.android.settingslib.widget.SettingsThemeHelper;

/* JADX INFO: loaded from: classes.dex */
public class PrimarySwitchPreference extends RestrictedPreference {
    private boolean mChecked;
    private boolean mCheckedSet;
    private boolean mEnableSwitch;
    private CompoundButton mSwitch;

    public PrimarySwitchPreference(Context context) {
        super(context);
        this.mEnableSwitch = true;
    }

    public PrimarySwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEnableSwitch = true;
    }

    public Boolean getCheckedState() {
        if (this.mCheckedSet) {
            return Boolean.valueOf(this.mChecked);
        }
        return null;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    protected int getSecondTargetResId() {
        return SettingsThemeHelper.isExpressiveTheme(getContext()) ? com.android.settingslib.widget.theme.R$layout.settingslib_expressive_preference_switch : androidx.preference.R$layout.preference_widget_switch_compat;
    }

    public boolean isChecked() {
        return this.mSwitch != null && this.mChecked;
    }

    public boolean isSwitchEnabled() {
        return this.mEnableSwitch;
    }

    public void setChecked(boolean z) {
        if (this.mChecked == z && this.mCheckedSet) {
            return;
        }
        this.mChecked = z;
        this.mCheckedSet = true;
        CompoundButton compoundButton = this.mSwitch;
        if (compoundButton != null) {
            compoundButton.setChecked(z);
        }
    }
}
