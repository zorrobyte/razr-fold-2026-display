package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.PreferenceGroup;
import com.android.settingslib.widget.theme.R$layout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ExpandablePreference.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ExpandablePreference extends PreferenceGroup {
    public static final Companion Companion = new Companion(null);
    private boolean isDirty;

    /* JADX INFO: compiled from: ExpandablePreference.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ExpandablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExpandablePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        context.getClass();
        this.isDirty = true;
        setLayoutResource(R$layout.settingslib_expressive_preference);
        setWidgetLayoutResource(com.android.settingslib.widget.preference.expandable.R$layout.settingslib_widget_expandable_icon);
    }

    public /* synthetic */ ExpandablePreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }
}
