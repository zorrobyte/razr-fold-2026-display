package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import com.android.settingslib.widget.preference.usage.R$layout;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class UsageProgressBarPreference extends Preference {
    protected final Pattern mNumberPattern;
    private int mPercent;

    public UsageProgressBarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNumberPattern = Pattern.compile("[\\d]*[\\٫.,]?[\\d]+");
        this.mPercent = -1;
        setLayoutResource(R$layout.preference_usage_progress_bar);
    }
}
