package com.android.settingslib.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.preference.Preference;
import com.android.settingslib.widget.preference.zerostate.R$layout;
import com.android.settingslib.widget.theme.R$color;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ZeroStatePreference.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ZeroStatePreference extends Preference {
    private final int iconTint;
    private Drawable tintedIcon;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ZeroStatePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ZeroStatePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        context.getClass();
        int color = context.getColor(R$color.settingslib_materialColorOnSecondaryContainer);
        this.iconTint = color;
        setSelectable(false);
        setLayoutResource(R$layout.settingslib_expressive_preference_zerostate);
        Drawable icon = getIcon();
        if (icon != null) {
            Drawable drawableMutate = icon.mutate();
            drawableMutate.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            this.tintedIcon = drawableMutate;
        }
    }

    public /* synthetic */ ZeroStatePreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }
}
