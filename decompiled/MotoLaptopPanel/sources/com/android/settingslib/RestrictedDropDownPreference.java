package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.DropDownPreference;

/* JADX INFO: loaded from: classes.dex */
public class RestrictedDropDownPreference extends DropDownPreference {
    private final RestrictedPreferenceHelper mHelper;

    public RestrictedDropDownPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, androidx.preference.R$attr.dropdownPreferenceStyle);
    }

    public RestrictedDropDownPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RestrictedDropDownPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet);
    }

    @Override // androidx.preference.Preference
    public void performClick() {
        if (this.mHelper.performClick()) {
            return;
        }
        super.performClick();
    }
}
