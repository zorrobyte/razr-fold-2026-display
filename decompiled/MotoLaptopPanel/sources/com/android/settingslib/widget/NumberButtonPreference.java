package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import com.android.settingslib.widget.preference.button.R$layout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NumberButtonPreference.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NumberButtonPreference extends Preference {
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NumberButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NumberButtonPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        context.getClass();
        setPersistent(false);
        setOrder(Integer.MAX_VALUE);
        setLayoutResource(R$layout.settingslib_number_button);
    }

    public /* synthetic */ NumberButtonPreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }
}
