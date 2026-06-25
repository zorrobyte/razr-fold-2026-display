package com.android.settingslib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.Preference;
import com.android.settingslib.widget.preference.footer.R$drawable;
import com.android.settingslib.widget.preference.footer.R$layout;
import com.android.settingslib.widget.theme.R$attr;

/* JADX INFO: loaded from: classes.dex */
public class FooterPreference extends Preference {
    private CharSequence mContentDescription;
    int mIconVisibility;
    View.OnClickListener mLearnMoreListener;

    public FooterPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R$attr.footerPreferenceStyle);
        this.mIconVisibility = 0;
        init();
    }

    private void init() {
        setLayoutResource(R$layout.preference_footer);
        if (getIcon() == null) {
            setIcon(R$drawable.settingslib_ic_info_outline_24);
        }
        setOrder(2147483646);
        if (TextUtils.isEmpty(getKey())) {
            setKey("footer_preference");
        }
        setSelectable(false);
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @Override // androidx.preference.Preference
    public CharSequence getSummary() {
        return getTitle();
    }
}
