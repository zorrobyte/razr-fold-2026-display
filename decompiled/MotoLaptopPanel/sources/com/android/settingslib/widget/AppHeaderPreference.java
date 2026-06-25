package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import com.android.settingslib.widget.preference.app.R$layout;

/* JADX INFO: loaded from: classes.dex */
public class AppHeaderPreference extends Preference {
    private boolean mIsInstantApp;

    public AppHeaderPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setLayoutResource(R$layout.app_header_preference);
        setSelectable(false);
        setIsInstantApp(false);
    }

    public void setIsInstantApp(boolean z) {
        if (this.mIsInstantApp != z) {
            this.mIsInstantApp = z;
            notifyChanged();
        }
    }
}
