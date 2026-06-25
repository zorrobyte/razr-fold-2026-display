package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import com.android.settingslib.widget.preference.usage.R$layout;

/* JADX INFO: loaded from: classes.dex */
public class TwoLevelUsageProgressBarPreference extends UsageProgressBarPreference {
    private ColorStateList mOtherColorStateList;
    private int mOtherPercent;
    private long mOtherUsage;
    private ColorStateList mPrimaryColorStateList;
    private int mPrimaryPercent;
    private long mPrimaryUsage;
    private long mTotal;

    public TwoLevelUsageProgressBarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTotal = 0L;
        this.mPrimaryUsage = -1L;
        this.mPrimaryPercent = -1;
        this.mOtherUsage = -1L;
        this.mOtherPercent = -1;
        this.mPrimaryColorStateList = null;
        this.mOtherColorStateList = null;
        setLayoutResource(R$layout.preference_two_level_usage_progress_bar);
    }
}
