package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import com.android.settingslib.widget.MainSwitchPreference;

/* JADX INFO: loaded from: classes.dex */
public class RestrictedMainSwitchPreference extends MainSwitchPreference {
    RestrictedPreferenceHelper mHelper;

    public RestrictedMainSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet);
    }
}
