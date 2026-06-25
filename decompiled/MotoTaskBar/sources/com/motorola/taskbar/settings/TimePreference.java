package com.motorola.taskbar.settings;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreferenceCompat;
import com.motorola.taskbar.guide.R$layout;

/* JADX INFO: loaded from: classes2.dex */
public class TimePreference extends SwitchPreferenceCompat {
    private Context mContext;
    private final Listener mListener;
    private int value;

    class Listener implements CompoundButton.OnCheckedChangeListener {
        Listener() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, final boolean z) {
            if (TimePreference.this.callChangeListener(Boolean.valueOf(z))) {
                new Handler().post(new Runnable() { // from class: com.motorola.taskbar.settings.TimePreference.Listener.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TimePreference.this.setChecked(z);
                    }
                });
            } else {
                compoundButton.setChecked(!z);
            }
        }
    }

    public TimePreference(Context context) {
        super(context);
        this.mListener = new Listener();
        init(context);
        this.mContext = context;
    }

    public TimePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mListener = new Listener();
        init(context);
        this.mContext = context;
    }

    private void init(Context context) {
        setLayoutResource(R$layout.preference_custom);
        setWidgetLayoutResource(R$layout.preference_radio_button);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void syncSwitchView(View view) {
        boolean z = view instanceof RadioButton;
        if (z) {
            ((RadioButton) view).setOnCheckedChangeListener(null);
        }
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(this.mChecked);
        }
        if (z) {
            ((RadioButton) view).setOnCheckedChangeListener(this.mListener);
        }
    }

    public int getValue() {
        return this.value;
    }

    @Override // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.setDividerAllowedAbove(false);
        preferenceViewHolder.setDividerAllowedBelow(false);
        syncSwitchView(preferenceViewHolder.findViewById(R.id.checkbox));
    }

    public void setValue(int i) {
        this.value = i;
    }
}
