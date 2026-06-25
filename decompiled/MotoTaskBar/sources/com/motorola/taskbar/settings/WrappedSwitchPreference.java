package com.motorola.taskbar.settings;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;
import com.motorola.taskbar.guide.R$layout;

/* JADX INFO: loaded from: classes2.dex */
public class WrappedSwitchPreference extends SwitchPreference {
    private Listener mListener;

    class Listener implements CompoundButton.OnCheckedChangeListener {
        Listener() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (WrappedSwitchPreference.this.callChangeListener(Boolean.valueOf(z))) {
                WrappedSwitchPreference.this.setChecked(z);
            } else {
                compoundButton.setChecked(!z);
            }
        }
    }

    public WrappedSwitchPreference(Context context) {
        super(context);
        init();
    }

    public WrappedSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public WrappedSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public WrappedSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    private void init() {
        setWidgetLayoutResource(R$layout.preference_check_box);
        this.mListener = new Listener();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void syncSwitchView(View view) {
        boolean z = view instanceof Switch;
        if (z) {
            ((Switch) view).setOnCheckedChangeListener(null);
        }
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(this.mChecked);
        }
        if (z) {
            ((Switch) view).setOnCheckedChangeListener(this.mListener);
        }
    }

    @Override // androidx.preference.SwitchPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        syncSwitchView(preferenceViewHolder.findViewById(R.id.checkbox));
    }
}
