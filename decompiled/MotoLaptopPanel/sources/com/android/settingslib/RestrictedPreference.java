package com.android.settingslib;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import com.android.settingslib.widget.TwoTargetPreference;

/* JADX INFO: loaded from: classes.dex */
public class RestrictedPreference extends TwoTargetPreference {
    RestrictedPreferenceHelper mHelper;

    public RestrictedPreference(Context context) {
        this(context, null);
    }

    public RestrictedPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R$attr.preferenceStyle, R.attr.preferenceStyle));
    }

    public RestrictedPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RestrictedPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i, i2, null, -1);
    }

    public RestrictedPreference(Context context, AttributeSet attributeSet, int i, int i2, String str, int i3) {
        super(context, attributeSet, i, i2);
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet, str, i3);
    }

    @Override // androidx.preference.Preference
    public void performClick() {
        if (this.mHelper.performClick()) {
            return;
        }
        super.performClick();
    }
}
