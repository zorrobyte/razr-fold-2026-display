package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* JADX INFO: loaded from: classes.dex */
public class RestrictedSelectorWithWidgetPreference extends SelectorWithWidgetPreference {
    private final RestrictedPreferenceHelper mHelper;

    public RestrictedSelectorWithWidgetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
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
