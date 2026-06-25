package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import com.android.settingslib.widget.theme.R$layout;

/* JADX INFO: loaded from: classes.dex */
public class AppPreference extends Preference {
    public AppPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        setLayoutResource(SettingsThemeHelper.isExpressiveTheme(context) ? R$layout.settingslib_expressive_preference : com.android.settingslib.widget.preference.app.R$layout.preference_app);
    }
}
