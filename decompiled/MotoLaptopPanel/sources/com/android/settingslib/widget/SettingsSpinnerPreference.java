package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import androidx.preference.Preference;
import com.android.settingslib.widget.spinner.R$layout;
import com.android.settingslib.widget.spinner.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class SettingsSpinnerPreference extends Preference implements Preference.OnPreferenceClickListener {
    private AdapterView.OnItemSelectedListener mListener;
    private final AdapterView.OnItemSelectedListener mOnSelectedListener;
    private int mPosition;
    private boolean mShouldPerformClick;

    /* JADX INFO: renamed from: -$$Nest$fgetmAdapter, reason: not valid java name */
    static /* bridge */ /* synthetic */ SettingsSpinnerAdapter m2080$$Nest$fgetmAdapter(SettingsSpinnerPreference settingsSpinnerPreference) {
        settingsSpinnerPreference.getClass();
        return null;
    }

    public SettingsSpinnerPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: com.android.settingslib.widget.SettingsSpinnerPreference.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView adapterView, View view, int i, long j) {
                if (SettingsSpinnerPreference.this.mPosition == i) {
                    return;
                }
                SettingsSpinnerPreference.this.mPosition = i;
                SettingsSpinnerPreference.m2080$$Nest$fgetmAdapter(SettingsSpinnerPreference.this);
                int unused = SettingsSpinnerPreference.this.mPosition;
                throw null;
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView adapterView) {
                if (SettingsSpinnerPreference.this.mListener != null) {
                    SettingsSpinnerPreference.this.mListener.onNothingSelected(adapterView);
                }
            }
        };
        initAttributes(context, attributeSet, 0);
        setOnPreferenceClickListener(this);
    }

    private void initAttributes(Context context, AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SettingsSpinnerPreference, i, 0);
        try {
            int integer = typedArrayObtainStyledAttributes.getInteger(R$styleable.SettingsSpinnerPreference_style, 0);
            int i2 = integer != 2 ? (integer == 3 || integer == 4) ? R$layout.settings_expressive_spinner_preference_outlined : integer != 5 ? R$layout.settings_spinner_preference : R$layout.settings_expressive_spinner_preference_full_outlined : R$layout.settings_expressive_spinner_preference_full;
            typedArrayObtainStyledAttributes.close();
            setLayoutResource(i2);
        } catch (Throwable th) {
            if (typedArrayObtainStyledAttributes != null) {
                try {
                    typedArrayObtainStyledAttributes.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        this.mShouldPerformClick = true;
        notifyChanged();
        return true;
    }
}
