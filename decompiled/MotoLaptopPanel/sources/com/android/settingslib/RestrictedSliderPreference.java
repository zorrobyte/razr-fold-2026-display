package com.android.settingslib;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import com.android.settingslib.widget.SliderPreference;

/* JADX INFO: loaded from: classes.dex */
public class RestrictedSliderPreference extends SliderPreference {
    private final RestrictedPreferenceHelper mHelper;

    public RestrictedSliderPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R$attr.preferenceStyle, R.attr.preferenceStyle));
    }

    public RestrictedSliderPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, null, -1);
    }

    public RestrictedSliderPreference(Context context, AttributeSet attributeSet, int i, String str, int i2) {
        super(context, attributeSet, i);
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet, str, i2);
    }

    @Override // androidx.preference.Preference
    public void performClick() {
        if (this.mHelper.performClick()) {
            return;
        }
        super.performClick();
    }
}
