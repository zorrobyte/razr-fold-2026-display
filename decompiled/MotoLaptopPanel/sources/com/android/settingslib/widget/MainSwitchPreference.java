package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import androidx.preference.TwoStatePreference;
import com.android.settingslib.widget.mainswitch.R$layout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MainSwitchPreference extends TwoStatePreference implements CompoundButton.OnCheckedChangeListener {
    private final List mSwitchChangeListeners;

    public MainSwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MainSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MainSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSwitchChangeListeners = new ArrayList();
        setLayoutResource(SettingsThemeHelper.isExpressiveTheme(context) ? R$layout.settingslib_expressive_main_switch_layout : R$layout.settingslib_main_switch_layout);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        super.setChecked(z);
        Iterator it = this.mSwitchChangeListeners.iterator();
        while (it.hasNext()) {
            ((CompoundButton.OnCheckedChangeListener) it.next()).onCheckedChanged(compoundButton, z);
        }
    }
}
