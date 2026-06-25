package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import com.android.settingslib.widget.preference.segmentedbutton.R$layout;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SegmentedButtonPreference.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SegmentedButtonPreference extends Preference {
    private List buttonLabels;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SegmentedButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SegmentedButtonPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        context.getClass();
        this.buttonLabels = new ArrayList();
        setLayoutResource(R$layout.settingslib_expressive_preference_segmentedbutton);
    }

    public /* synthetic */ SegmentedButtonPreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }
}
