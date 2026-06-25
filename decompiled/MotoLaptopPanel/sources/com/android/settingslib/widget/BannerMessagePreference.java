package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.preference.Preference;
import com.android.settingslib.widget.preference.banner.R$color;
import com.android.settingslib.widget.preference.banner.R$layout;
import com.android.settingslib.widget.preference.banner.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class BannerMessagePreference extends Preference {
    private static final boolean IS_AT_LEAST_S = true;
    private AttentionLevel mAttentionLevel;
    private int mButtonOrientation;
    private final DismissButtonInfo mDismissButtonInfo;
    private CharSequence mHeader;
    private final ButtonInfo mNegativeButtonInfo;
    private final ButtonInfo mPositiveButtonInfo;
    private CharSequence mSubtitle;

    public enum AttentionLevel {
        HIGH(0, R$color.banner_background_attention_high, R$color.banner_accent_attention_high, R$color.settingslib_banner_button_background_high, R$color.settingslib_banner_filled_button_content_high),
        MEDIUM(1, R$color.banner_background_attention_medium, R$color.banner_accent_attention_medium, R$color.settingslib_banner_button_background_medium, R$color.settingslib_banner_filled_button_content_medium),
        LOW(2, R$color.banner_background_attention_low, R$color.banner_accent_attention_low, R$color.settingslib_banner_button_background_low, R$color.settingslib_banner_filled_button_content_low),
        NORMAL(3, R$color.banner_background_attention_normal, R$color.banner_accent_attention_normal, R$color.settingslib_banner_button_background_normal, R$color.settingslib_banner_filled_button_content_normal);

        private final int mAccentColorResId;
        private final int mAttrValue;
        private final int mBackgroundColorResId;
        private final int mButtonBackgroundColorResId;
        private final int mButtonContentColorResId;

        AttentionLevel(int i, int i2, int i3, int i4, int i5) {
            this.mAttrValue = i;
            this.mBackgroundColorResId = i2;
            this.mAccentColorResId = i3;
            this.mButtonBackgroundColorResId = i4;
            this.mButtonContentColorResId = i5;
        }

        static AttentionLevel fromAttr(int i) {
            for (AttentionLevel attentionLevel : values()) {
                if (attentionLevel.mAttrValue == i) {
                    return attentionLevel;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    class ButtonInfo {
        private boolean mIsVisible = true;
        private boolean mIsEnabled = true;

        ButtonInfo() {
        }
    }

    class DismissButtonInfo {
        private boolean mIsVisible = true;

        DismissButtonInfo() {
        }
    }

    public BannerMessagePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPositiveButtonInfo = new ButtonInfo();
        this.mNegativeButtonInfo = new ButtonInfo();
        this.mDismissButtonInfo = new DismissButtonInfo();
        this.mAttentionLevel = AttentionLevel.HIGH;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        setSelectable(false);
        setLayoutResource(SettingsThemeHelper.isExpressiveTheme(context) ? R$layout.settingslib_expressive_banner_message : R$layout.settingslib_banner_message);
        if (!IS_AT_LEAST_S || attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BannerMessagePreference);
        this.mAttentionLevel = AttentionLevel.fromAttr(typedArrayObtainStyledAttributes.getInt(R$styleable.BannerMessagePreference_attentionLevel, 0));
        this.mSubtitle = typedArrayObtainStyledAttributes.getString(R$styleable.BannerMessagePreference_subtitle);
        this.mHeader = typedArrayObtainStyledAttributes.getString(R$styleable.BannerMessagePreference_bannerHeader);
        this.mButtonOrientation = typedArrayObtainStyledAttributes.getInt(R$styleable.BannerMessagePreference_buttonOrientation, 0);
        typedArrayObtainStyledAttributes.recycle();
    }
}
