package com.google.android.setupdesign.util;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupdesign.view.RichTextView;

/* JADX INFO: loaded from: classes.dex */
abstract class TextViewPartnerStyler {

    public class TextPartnerConfigs {
        private final PartnerConfig textColorConfig;
        private final PartnerConfig textFontFamilyConfig;
        private PartnerConfig textFontVariationSettingsConfig;
        private final PartnerConfig textFontWeightConfig;
        private final int textGravity;
        private final PartnerConfig textLinkFontFamilyConfig;
        private final PartnerConfig textLinkedColorConfig;
        private final PartnerConfig textMarginBottomConfig;
        private final PartnerConfig textMarginTopConfig;
        private final PartnerConfig textSizeConfig;

        public TextPartnerConfigs(PartnerConfig partnerConfig, PartnerConfig partnerConfig2, PartnerConfig partnerConfig3, PartnerConfig partnerConfig4, PartnerConfig partnerConfig5, PartnerConfig partnerConfig6, PartnerConfig partnerConfig7, PartnerConfig partnerConfig8, int i) {
            this.textFontVariationSettingsConfig = null;
            this.textColorConfig = partnerConfig;
            this.textLinkedColorConfig = partnerConfig2;
            this.textSizeConfig = partnerConfig3;
            this.textFontFamilyConfig = partnerConfig4;
            this.textFontWeightConfig = partnerConfig5;
            this.textLinkFontFamilyConfig = partnerConfig6;
            this.textMarginTopConfig = partnerConfig7;
            this.textMarginBottomConfig = partnerConfig8;
            this.textGravity = i;
        }

        public TextPartnerConfigs(PartnerConfig partnerConfig, PartnerConfig partnerConfig2, PartnerConfig partnerConfig3, PartnerConfig partnerConfig4, PartnerConfig partnerConfig5, PartnerConfig partnerConfig6, PartnerConfig partnerConfig7, PartnerConfig partnerConfig8, PartnerConfig partnerConfig9, int i) {
            this.textColorConfig = partnerConfig;
            this.textLinkedColorConfig = partnerConfig2;
            this.textSizeConfig = partnerConfig3;
            this.textFontFamilyConfig = partnerConfig4;
            this.textFontWeightConfig = partnerConfig5;
            this.textLinkFontFamilyConfig = partnerConfig6;
            this.textMarginTopConfig = partnerConfig7;
            this.textMarginBottomConfig = partnerConfig8;
            this.textFontVariationSettingsConfig = partnerConfig9;
            this.textGravity = i;
        }

        public PartnerConfig getLinkTextFontFamilyConfig() {
            return this.textLinkFontFamilyConfig;
        }

        public PartnerConfig getTextColorConfig() {
            return this.textColorConfig;
        }

        public PartnerConfig getTextFontFamilyConfig() {
            return this.textFontFamilyConfig;
        }

        public PartnerConfig getTextFontVariationSettingsConfig() {
            return this.textFontVariationSettingsConfig;
        }

        public PartnerConfig getTextFontWeightConfig() {
            return this.textFontWeightConfig;
        }

        public int getTextGravity() {
            return this.textGravity;
        }

        public PartnerConfig getTextLinkedColorConfig() {
            return this.textLinkedColorConfig;
        }

        public PartnerConfig getTextMarginBottom() {
            return this.textMarginBottomConfig;
        }

        public PartnerConfig getTextMarginTop() {
            return this.textMarginTopConfig;
        }

        public PartnerConfig getTextSizeConfig() {
            return this.textSizeConfig;
        }
    }

    public static void applyPartnerCustomizationLightStyle(TextView textView, TextPartnerConfigs textPartnerConfigs) {
        if (textView == null || textPartnerConfigs == null) {
            return;
        }
        applyPartnerCustomizationVerticalMargins(textView, textPartnerConfigs);
        textView.setGravity(textPartnerConfigs.getTextGravity());
    }

    public static void applyPartnerCustomizationStyle(TextView textView, TextPartnerConfigs textPartnerConfigs) {
        boolean z;
        String string;
        Typeface typefaceCreate;
        int color;
        int color2;
        if (textView == null || textPartnerConfigs == null) {
            return;
        }
        Context context = textView.getContext();
        if (textPartnerConfigs.getTextColorConfig() != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(textPartnerConfigs.getTextColorConfig()) && (color2 = PartnerConfigHelper.get(context).getColor(context, textPartnerConfigs.getTextColorConfig())) != 0) {
            textView.setTextColor(color2);
        }
        if (textPartnerConfigs.getTextLinkedColorConfig() != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(textPartnerConfigs.getTextLinkedColorConfig()) && !PartnerStyleHelper.useDynamicColor(textView) && (color = PartnerConfigHelper.get(context).getColor(context, textPartnerConfigs.getTextLinkedColorConfig())) != 0) {
            textView.setLinkTextColor(color);
        }
        if (textPartnerConfigs.getTextSizeConfig() != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(textPartnerConfigs.getTextSizeConfig())) {
            float dimension = PartnerConfigHelper.get(context).getDimension(context, textPartnerConfigs.getTextSizeConfig(), 0.0f);
            if (dimension > 0.0f) {
                textView.setTextSize(0, dimension);
            }
        }
        Typeface typefaceCreate2 = null;
        if (textPartnerConfigs.getTextFontVariationSettingsConfig() == null || !PartnerConfigHelper.get(context).isPartnerConfigAvailable(textPartnerConfigs.getTextFontVariationSettingsConfig())) {
            z = false;
            string = null;
        } else {
            string = PartnerConfigHelper.get(context).getString(context, textPartnerConfigs.getTextFontVariationSettingsConfig());
            z = isFontVariationSupported(string);
        }
        if (textPartnerConfigs.getTextFontFamilyConfig() != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(textPartnerConfigs.getTextFontFamilyConfig()) && !z) {
            typefaceCreate2 = Typeface.create(PartnerConfigHelper.get(context).getString(context, textPartnerConfigs.getTextFontFamilyConfig()), 0);
        }
        if (PartnerConfigHelper.isFontWeightEnabled(context) && textPartnerConfigs.getTextFontWeightConfig() != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(textPartnerConfigs.getTextFontWeightConfig()) && !z) {
            int integer = PartnerConfigHelper.get(context).getInteger(context, textPartnerConfigs.getTextFontWeightConfig(), 400);
            if (typefaceCreate2 == null) {
                typefaceCreate2 = textView.getTypeface();
            }
            typefaceCreate2 = Typeface.create(typefaceCreate2, integer, false);
        }
        if (typefaceCreate2 != null) {
            textView.setTypeface(typefaceCreate2);
        }
        if (isFontVariationSupported(string)) {
            try {
                textView.setFontVariationSettings(string);
            } catch (Exception e) {
                Log.e("TextViewPartnerStyler", "Failed to set font variation settings: " + e.getMessage());
            }
        }
        if ((textView instanceof RichTextView) && textPartnerConfigs.getLinkTextFontFamilyConfig() != null && PartnerConfigHelper.get(context).isPartnerConfigAvailable(textPartnerConfigs.getLinkTextFontFamilyConfig()) && (typefaceCreate = Typeface.create(PartnerConfigHelper.get(context).getString(context, textPartnerConfigs.getLinkTextFontFamilyConfig()), 0)) != null) {
            ((RichTextView) textView).setSpanTypeface(typefaceCreate);
        }
        applyPartnerCustomizationVerticalMargins(textView, textPartnerConfigs);
        textView.setGravity(textPartnerConfigs.getTextGravity());
    }

    private static void applyPartnerCustomizationVerticalMargins(TextView textView, TextPartnerConfigs textPartnerConfigs) {
        if (textPartnerConfigs.getTextMarginTop() == null && textPartnerConfigs.getTextMarginBottom() == null) {
            return;
        }
        Context context = textView.getContext();
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
            layoutParams2.setMargins(layoutParams2.leftMargin, (textPartnerConfigs.getTextMarginTop() == null || !PartnerConfigHelper.get(context).isPartnerConfigAvailable(textPartnerConfigs.getTextMarginTop())) ? layoutParams2.topMargin : (int) PartnerConfigHelper.get(context).getDimension(context, textPartnerConfigs.getTextMarginTop()), layoutParams2.rightMargin, (textPartnerConfigs.getTextMarginBottom() == null || !PartnerConfigHelper.get(context).isPartnerConfigAvailable(textPartnerConfigs.getTextMarginBottom())) ? layoutParams2.bottomMargin : (int) PartnerConfigHelper.get(context).getDimension(context, textPartnerConfigs.getTextMarginBottom()));
            textView.setLayoutParams(layoutParams);
        }
    }

    private static boolean isFontVariationSupported(String str) {
        return (str == null || TextUtils.isEmpty(str)) ? false : true;
    }
}
