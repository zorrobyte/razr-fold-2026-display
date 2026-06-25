package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.preference.CheckBoxPreference;
import com.android.settingslib.widget.preference.selector.R$layout;
import com.android.settingslib.widget.preference.selector.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class SelectorWithWidgetPreference extends CheckBoxPreference {
    static final int DEFAULT_MAX_LINES = 2;
    private int mAppendixVisibility;
    private ImageView mExtraWidget;
    private boolean mIsCheckBox;
    private int mTitleMaxLines;

    public SelectorWithWidgetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAppendixVisibility = -1;
        this.mIsCheckBox = false;
        init(context, attributeSet, 0, 0);
    }

    private void init(Context context, AttributeSet attributeSet, int i, int i2) {
        if (this.mIsCheckBox) {
            setWidgetLayoutResource(R$layout.settingslib_preference_widget_checkbox);
        } else {
            setWidgetLayoutResource(R$layout.settingslib_preference_widget_radiobutton);
        }
        setLayoutResource(R$layout.preference_selector_with_widget);
        setIconSpaceReserved(false);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SelectorWithWidgetPreference, i, i2);
        this.mTitleMaxLines = typedArrayObtainStyledAttributes.getInt(R$styleable.SelectorWithWidgetPreference_titleMaxLines, DEFAULT_MAX_LINES);
        typedArrayObtainStyledAttributes.recycle();
    }

    public View getExtraWidget() {
        return this.mExtraWidget;
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public void onClick() {
    }
}
