package com.motorola.taskbar.settings.prefs;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioGroup;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.motorola.taskbar.guide.R$id;
import com.motorola.taskbar.settings.MKFragment;

/* JADX INFO: loaded from: classes2.dex */
public class ThreeRadioPreference extends Preference implements RadioGroup.OnCheckedChangeListener {
    private int mChecked;
    private OnCheckListener mOnCheckedChangeListener;
    private RadioGroup mRadioGroup;

    public interface OnCheckListener {
        void onThreeButtonCheckedChanged(int i);
    }

    public ThreeRadioPreference(Context context) {
        this(context, null);
    }

    public ThreeRadioPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ThreeRadioPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ThreeRadioPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mChecked = 0;
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        RadioGroup radioGroup = (RadioGroup) preferenceViewHolder.findViewById(R$id.radio_group);
        this.mRadioGroup = radioGroup;
        radioGroup.setOnCheckedChangeListener(this);
        setChecked(this.mChecked);
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int i2;
        if (i == R$id.radio_default) {
            i2 = 1;
        } else if (i == R$id.radio_large) {
            i2 = 2;
        } else {
            if (i != R$id.radio_largest) {
                Log.d(MKFragment.TAG, "onCheckedChanged: checkIndex = 0");
                return;
            }
            i2 = 3;
        }
        OnCheckListener onCheckListener = this.mOnCheckedChangeListener;
        if (onCheckListener != null && i2 != this.mChecked) {
            onCheckListener.onThreeButtonCheckedChanged(i2);
        }
        this.mChecked = i2;
    }

    public void setChecked(int i) {
        RadioGroup radioGroup;
        this.mChecked = i;
        if (i == 1) {
            RadioGroup radioGroup2 = this.mRadioGroup;
            if (radioGroup2 != null) {
                radioGroup2.check(R$id.radio_default);
                return;
            }
            return;
        }
        if (i != 2) {
            if (i == 3 && (radioGroup = this.mRadioGroup) != null) {
                radioGroup.check(R$id.radio_largest);
                return;
            }
            return;
        }
        RadioGroup radioGroup3 = this.mRadioGroup;
        if (radioGroup3 != null) {
            radioGroup3.check(R$id.radio_large);
        }
    }

    public void setOnChangeListener(OnCheckListener onCheckListener) {
        this.mOnCheckedChangeListener = onCheckListener;
    }
}
