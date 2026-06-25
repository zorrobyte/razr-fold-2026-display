package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import com.android.settingslib.widget.preference.twotarget.R$dimen;
import com.android.settingslib.widget.preference.twotarget.R$layout;

/* JADX INFO: loaded from: classes.dex */
public class TwoTargetPreference extends Preference {
    private int mMediumIconSize;
    private int mSmallIconSize;

    public TwoTargetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public TwoTargetPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context);
    }

    private void init(Context context) {
        setLayoutResource(SettingsThemeHelper.isExpressiveTheme(context) ? R$layout.settingslib_expressive_preference_two_target : R$layout.preference_two_target);
        this.mSmallIconSize = context.getResources().getDimensionPixelSize(R$dimen.two_target_pref_small_icon_size);
        this.mMediumIconSize = context.getResources().getDimensionPixelSize(R$dimen.two_target_pref_medium_icon_size);
        int secondTargetResId = getSecondTargetResId();
        if (secondTargetResId != 0) {
            setWidgetLayoutResource(secondTargetResId);
        }
    }

    protected int getSecondTargetResId() {
        return 0;
    }
}
