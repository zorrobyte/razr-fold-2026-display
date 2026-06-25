package com.motorola.taskbar.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.motorola.taskbar.guide.R$id;
import com.motorola.taskbar.guide.R$styleable;

/* JADX INFO: loaded from: classes2.dex */
public class RadioSwitchPreference extends Preference implements RadioGroup.OnCheckedChangeListener {
    private boolean mChecked;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioOffButton;
    private CharSequence mRadioOffText;
    private RadioButton mRadioOnButton;
    private CharSequence mRadioOnText;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean z);
    }

    public RadioSwitchPreference(Context context) {
        this(context, null);
    }

    public RadioSwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RadioSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RadioSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mChecked = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RadioSwitchPreference, i, i2);
        this.mRadioOnText = typedArrayObtainStyledAttributes.getString(R$styleable.RadioSwitchPreference_radioOnText);
        this.mRadioOffText = typedArrayObtainStyledAttributes.getString(R$styleable.RadioSwitchPreference_radioOffText);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        RadioGroup radioGroup = (RadioGroup) preferenceViewHolder.findViewById(R$id.radio_group);
        this.mRadioGroup = radioGroup;
        radioGroup.setOnCheckedChangeListener(this);
        RadioButton radioButton = (RadioButton) preferenceViewHolder.findViewById(R$id.radio_on);
        this.mRadioOnButton = radioButton;
        radioButton.setText(this.mRadioOnText);
        RadioButton radioButton2 = (RadioButton) preferenceViewHolder.findViewById(R$id.radio_off);
        this.mRadioOffButton = radioButton2;
        radioButton2.setText(this.mRadioOffText);
        setChecked(this.mChecked);
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        boolean z = i == R$id.radio_on;
        OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
        if (onCheckedChangeListener != null && z != this.mChecked) {
            onCheckedChangeListener.onCheckedChanged(z);
        }
        this.mChecked = z;
    }

    public void setChecked(boolean z) {
        this.mChecked = z;
        RadioGroup radioGroup = this.mRadioGroup;
        if (radioGroup != null) {
            radioGroup.check(z ? R$id.radio_on : R$id.radio_off);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }
}
