package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Button;
import androidx.preference.Preference;
import androidx.preference.R$styleable;
import com.android.settingslib.widget.preference.button.R$layout;
import com.google.android.material.button.MaterialButton;

/* JADX INFO: loaded from: classes.dex */
public class ButtonPreference extends Preference {
    private Button mButton;
    private int mGravity;
    private Drawable mIcon;
    private CharSequence mTitle;

    enum ButtonStyle {
        FILLED_NORMAL(0, 0, R$layout.settingslib_expressive_button_filled),
        FILLED_LARGE(0, 1, R$layout.settingslib_expressive_button_filled_large),
        FILLED_EXTRA(0, 2, R$layout.settingslib_expressive_button_filled_extra),
        TONAL_NORMAL(1, 0, R$layout.settingslib_expressive_button_tonal),
        TONAL_LARGE(1, 1, R$layout.settingslib_expressive_button_tonal_large),
        TONAL_EXTRA(1, 2, R$layout.settingslib_expressive_button_tonal_extra),
        OUTLINE_NORMAL(2, 0, R$layout.settingslib_expressive_button_outline),
        OUTLINE_LARGE(2, 1, R$layout.settingslib_expressive_button_outline_large),
        OUTLINE_EXTRA(2, 2, R$layout.settingslib_expressive_button_outline_extra);

        private final int mLayoutId;
        private final int mSize;
        private final int mType;

        ButtonStyle(int i, int i2, int i3) {
            this.mType = i;
            this.mSize = i2;
            this.mLayoutId = i3;
        }

        static int getLayoutId(int i, int i2) {
            for (ButtonStyle buttonStyle : values()) {
                if (buttonStyle.mType == i && buttonStyle.mSize == i2) {
                    return buttonStyle.mLayoutId;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    public ButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ButtonPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        int layoutId = R$layout.settingslib_button_layout;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i, 0);
            this.mTitle = typedArrayObtainStyledAttributes.getText(R$styleable.Preference_android_title);
            this.mIcon = typedArrayObtainStyledAttributes.getDrawable(R$styleable.Preference_android_icon);
            typedArrayObtainStyledAttributes.recycle();
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.settingslib.widget.preference.button.R$styleable.ButtonPreference, i, 0);
            this.mGravity = typedArrayObtainStyledAttributes2.getInt(com.android.settingslib.widget.preference.button.R$styleable.ButtonPreference_android_gravity, 8388611);
            if (SettingsThemeHelper.isExpressiveTheme(context)) {
                layoutId = ButtonStyle.getLayoutId(typedArrayObtainStyledAttributes2.getInt(com.android.settingslib.widget.preference.button.R$styleable.ButtonPreference_buttonPreferenceType, 0), typedArrayObtainStyledAttributes2.getInt(com.android.settingslib.widget.preference.button.R$styleable.ButtonPreference_buttonPreferenceSize, 0));
            }
            typedArrayObtainStyledAttributes2.recycle();
        }
        setLayoutResource(layoutId);
    }

    @Override // androidx.preference.Preference
    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Override // androidx.preference.Preference
    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        Button button = this.mButton;
        if (button == null || drawable == null) {
            return;
        }
        if (button instanceof MaterialButton) {
            ((MaterialButton) button).setIcon(drawable);
            return;
        }
        int iApplyDimension = (int) TypedValue.applyDimension(1, 24.0f, getContext().getResources().getDisplayMetrics());
        drawable.setBounds(0, 0, iApplyDimension, iApplyDimension);
        this.mButton.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
    }
}
