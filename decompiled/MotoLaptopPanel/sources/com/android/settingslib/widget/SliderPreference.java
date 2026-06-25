package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.R$styleable;
import com.android.settingslib.widget.preference.slider.R$color;
import com.android.settingslib.widget.preference.slider.R$dimen;
import com.android.settingslib.widget.preference.slider.R$layout;
import com.google.android.material.slider.Slider;

/* JADX INFO: loaded from: classes.dex */
public class SliderPreference extends Preference {
    private boolean mAdjustable;
    private final Slider.OnChangeListener mChangeListener;
    private final ColorStateList mHaloColor;
    private int mHapticFeedbackMode;
    private final int mIconEndContentDescriptionId;
    private final int mIconEndId;
    private final int mIconStartContentDescriptionId;
    private final int mIconStartId;
    private int mMax;
    private int mMin;
    private boolean mShowSliderValue;
    private Slider mSlider;
    private int mSliderIncrement;
    private final View.OnKeyListener mSliderKeyListener;
    private int mSliderValue;
    private final int mTextEndId;
    private final int mTextStartId;
    private final ColorStateList mThumbColor;
    private final int mThumbElevation;
    private final int mThumbHeight;
    private final int mThumbStrokeWidth;
    private final int mThumbTrackGapSize;
    private final int mThumbWidth;
    private final int mTickRadius;
    private boolean mTickVisible;
    private final Slider.OnSliderTouchListener mTouchListener;
    private final ColorStateList mTrackActiveColor;
    private final int mTrackHeight;
    private final ColorStateList mTrackInactiveColor;
    private final int mTrackInsideCornerSize;
    private final int mTrackStopIndicatorSize;
    private boolean mTrackingTouch;
    private boolean mUpdatesContinuously;

    public SliderPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SliderPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHapticFeedbackMode = 0;
        this.mTickVisible = false;
        this.mSliderKeyListener = new View.OnKeyListener() { // from class: com.android.settingslib.widget.SliderPreference.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i2, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                if ((!SliderPreference.this.mAdjustable && (i2 == 21 || i2 == 22)) || i2 == 23 || i2 == 66) {
                    return false;
                }
                if (SliderPreference.this.mSlider != null) {
                    return SliderPreference.this.mSlider.onKeyDown(i2, keyEvent);
                }
                Log.e("SliderPreference", "Slider view is null and hence cannot be adjusted.");
                return false;
            }
        };
        this.mTouchListener = new Slider.OnSliderTouchListener() { // from class: com.android.settingslib.widget.SliderPreference.2
            @Override // com.google.android.material.slider.BaseOnSliderTouchListener
            public void onStartTrackingTouch(Slider slider) {
                SliderPreference.this.mTrackingTouch = true;
            }

            @Override // com.google.android.material.slider.BaseOnSliderTouchListener
            public void onStopTrackingTouch(Slider slider) {
                SliderPreference.this.mTrackingTouch = false;
                if (((int) slider.getValue()) != SliderPreference.this.mSliderValue) {
                    SliderPreference.this.syncValueInternal(slider);
                }
            }
        };
        this.mChangeListener = new Slider.OnChangeListener() { // from class: com.android.settingslib.widget.SliderPreference.3
            @Override // com.google.android.material.slider.BaseOnChangeListener
            public void onValueChange(Slider slider, float f, boolean z) {
                if (z) {
                    if (SliderPreference.this.mUpdatesContinuously || !SliderPreference.this.mTrackingTouch) {
                        SliderPreference.this.syncValueInternal(slider);
                    }
                }
            }
        };
        setLayoutResource(R$layout.settingslib_expressive_preference_slider);
        setSelectable(false);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SeekBarPreference, i, 0);
        this.mMin = typedArrayObtainStyledAttributes.getInt(R$styleable.SeekBarPreference_min, 0);
        setMax(typedArrayObtainStyledAttributes.getInt(R$styleable.SeekBarPreference_android_max, 100));
        setSliderIncrement(typedArrayObtainStyledAttributes.getInt(R$styleable.SeekBarPreference_seekBarIncrement, 0));
        this.mAdjustable = typedArrayObtainStyledAttributes.getBoolean(R$styleable.SeekBarPreference_adjustable, true);
        this.mShowSliderValue = typedArrayObtainStyledAttributes.getBoolean(R$styleable.SeekBarPreference_showSeekBarValue, false);
        this.mUpdatesContinuously = typedArrayObtainStyledAttributes.getBoolean(R$styleable.SeekBarPreference_updatesContinuously, false);
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.settingslib.widget.preference.slider.R$styleable.SliderPreference);
        this.mTextStartId = typedArrayObtainStyledAttributes2.getResourceId(com.android.settingslib.widget.preference.slider.R$styleable.SliderPreference_textStart, 0);
        this.mTextEndId = typedArrayObtainStyledAttributes2.getResourceId(com.android.settingslib.widget.preference.slider.R$styleable.SliderPreference_textEnd, 0);
        this.mIconStartId = typedArrayObtainStyledAttributes2.getResourceId(com.android.settingslib.widget.preference.slider.R$styleable.SliderPreference_iconStart, 0);
        this.mIconEndId = typedArrayObtainStyledAttributes2.getResourceId(com.android.settingslib.widget.preference.slider.R$styleable.SliderPreference_iconEnd, 0);
        this.mIconStartContentDescriptionId = typedArrayObtainStyledAttributes2.getResourceId(com.android.settingslib.widget.preference.slider.R$styleable.SliderPreference_iconStartContentDescription, 0);
        this.mIconEndContentDescriptionId = typedArrayObtainStyledAttributes2.getResourceId(com.android.settingslib.widget.preference.slider.R$styleable.SliderPreference_iconEndContentDescription, 0);
        typedArrayObtainStyledAttributes2.recycle();
        this.mTrackActiveColor = context.getColorStateList(R$color.sensi_active_track_color);
        this.mTrackInactiveColor = context.getColorStateList(R$color.sensi_inactive_track_color);
        int i2 = R$color.sensi_thumb_color;
        this.mThumbColor = context.getColorStateList(i2);
        this.mHaloColor = context.getColorStateList(i2).withAlpha(20);
        Resources resources = context.getResources();
        this.mTrackHeight = resources.getDimensionPixelSize(R$dimen.settingslib_expressive_slider_track_height);
        this.mTrackInsideCornerSize = resources.getDimensionPixelSize(R$dimen.settingslib_expressive_slider_track_inside_corner_size);
        this.mTrackStopIndicatorSize = resources.getDimensionPixelSize(R$dimen.settingslib_expressive_slider_track_stop_indicator_size);
        this.mThumbWidth = resources.getDimensionPixelSize(R$dimen.settingslib_expressive_slider_thumb_width);
        this.mThumbHeight = resources.getDimensionPixelSize(R$dimen.settingslib_expressive_slider_thumb_height);
        this.mThumbElevation = resources.getDimensionPixelSize(R$dimen.settingslib_expressive_slider_thumb_elevation);
        this.mThumbStrokeWidth = resources.getDimensionPixelSize(R$dimen.settingslib_expressive_slider_thumb_stroke_width);
        this.mThumbTrackGapSize = resources.getDimensionPixelSize(R$dimen.settingslib_expressive_slider_thumb_track_gap_size);
        this.mTickRadius = resources.getDimensionPixelSize(R$dimen.settingslib_expressive_slider_tick_radius);
    }

    private void setValueInternal(int i, boolean z) {
        int i2 = this.mMin;
        if (i < i2) {
            i = i2;
        }
        int i3 = this.mMax;
        if (i > i3) {
            i = i3;
        }
        if (i != this.mSliderValue) {
            this.mSliderValue = i;
            persistInt(i);
            if (z) {
                notifyChanged();
            }
        }
    }

    @Override // androidx.preference.Preference
    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return Integer.valueOf(typedArray.getInt(i, 0));
    }

    public final void setMax(int i) {
        int i2 = this.mMin;
        if (i < i2) {
            i = i2;
        }
        if (i != this.mMax) {
            this.mMax = i;
            notifyChanged();
        }
    }

    public final void setSliderIncrement(int i) {
        if (i != this.mSliderIncrement) {
            this.mSliderIncrement = Math.min(this.mMax - this.mMin, Math.abs(i));
            notifyChanged();
        }
    }

    void syncValueInternal(Slider slider) {
        int value = (int) slider.getValue();
        if (value != this.mSliderValue) {
            if (!callChangeListener(Integer.valueOf(value))) {
                slider.setValue(this.mSliderValue);
                return;
            }
            setValueInternal(value, false);
            int i = this.mHapticFeedbackMode;
            if (i == 1) {
                slider.performHapticFeedback(4);
                return;
            }
            if (i != 2) {
                return;
            }
            int i2 = this.mSliderValue;
            if (i2 == this.mMax || i2 == this.mMin) {
                slider.performHapticFeedback(4);
            }
        }
    }
}
