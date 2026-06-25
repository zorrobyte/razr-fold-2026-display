package com.google.android.setupcompat.internal;

import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.template.FooterButton;

/* JADX INFO: loaded from: classes.dex */
public class FooterButtonPartnerConfig {
    private final PartnerConfig buttonBackgroundConfig;
    private final PartnerConfig buttonDisableAlphaConfig;
    private final PartnerConfig buttonDisableBackgroundConfig;
    private final PartnerConfig buttonDisableTextColorConfig;
    private final PartnerConfig buttonIconConfig;
    private final PartnerConfig buttonMarginStartConfig;
    private final PartnerConfig buttonMinHeightConfig;
    private final PartnerConfig buttonRadiusConfig;
    private final PartnerConfig buttonRippleColorAlphaConfig;
    private final PartnerConfig buttonTextColorConfig;
    private final PartnerConfig buttonTextSizeConfig;
    private final PartnerConfig buttonTextStyleConfig;
    private final PartnerConfig buttonTextTypeFaceConfig;
    private final PartnerConfig buttonTextWeightConfig;
    private final int partnerTheme;

    public class Builder {
        private final FooterButton footerButton;
        private int partnerTheme;
        private PartnerConfig buttonBackgroundConfig = null;
        private PartnerConfig buttonDisableAlphaConfig = null;
        private PartnerConfig buttonDisableBackgroundConfig = null;
        private PartnerConfig buttonDisableTextColorConfig = null;
        private PartnerConfig buttonIconConfig = null;
        private PartnerConfig buttonTextColorConfig = null;
        private PartnerConfig buttonMarginStartConfig = null;
        private PartnerConfig buttonTextSizeConfig = null;
        private PartnerConfig buttonMinHeight = null;
        private PartnerConfig buttonTextTypeFaceConfig = null;
        private PartnerConfig buttonTextWeightConfig = null;
        private PartnerConfig buttonTextStyleConfig = null;
        private PartnerConfig buttonRadiusConfig = null;
        private PartnerConfig buttonRippleColorAlphaConfig = null;

        public Builder(FooterButton footerButton) {
            this.footerButton = footerButton;
            if (footerButton != null) {
                this.partnerTheme = footerButton.getTheme();
            }
        }

        public FooterButtonPartnerConfig build() {
            return new FooterButtonPartnerConfig(this.partnerTheme, this.buttonBackgroundConfig, this.buttonDisableAlphaConfig, this.buttonDisableBackgroundConfig, this.buttonDisableTextColorConfig, this.buttonIconConfig, this.buttonTextColorConfig, this.buttonMarginStartConfig, this.buttonTextSizeConfig, this.buttonMinHeight, this.buttonTextTypeFaceConfig, this.buttonTextWeightConfig, this.buttonTextStyleConfig, this.buttonRadiusConfig, this.buttonRippleColorAlphaConfig);
        }

        public Builder setButtonBackgroundConfig(PartnerConfig partnerConfig) {
            this.buttonBackgroundConfig = partnerConfig;
            return this;
        }

        public Builder setButtonDisableAlphaConfig(PartnerConfig partnerConfig) {
            this.buttonDisableAlphaConfig = partnerConfig;
            return this;
        }

        public Builder setButtonDisableBackgroundConfig(PartnerConfig partnerConfig) {
            this.buttonDisableBackgroundConfig = partnerConfig;
            return this;
        }

        public Builder setButtonDisableTextColorConfig(PartnerConfig partnerConfig) {
            this.buttonDisableTextColorConfig = partnerConfig;
            return this;
        }

        public Builder setButtonIconConfig(PartnerConfig partnerConfig) {
            this.buttonIconConfig = partnerConfig;
            return this;
        }

        public Builder setButtonMinHeight(PartnerConfig partnerConfig) {
            this.buttonMinHeight = partnerConfig;
            return this;
        }

        public Builder setButtonRadiusConfig(PartnerConfig partnerConfig) {
            this.buttonRadiusConfig = partnerConfig;
            return this;
        }

        public Builder setButtonRippleColorAlphaConfig(PartnerConfig partnerConfig) {
            this.buttonRippleColorAlphaConfig = partnerConfig;
            return this;
        }

        public Builder setMarginStartConfig(PartnerConfig partnerConfig) {
            this.buttonMarginStartConfig = partnerConfig;
            return this;
        }

        public Builder setPartnerTheme(int i) {
            this.partnerTheme = i;
            return this;
        }

        public Builder setTextColorConfig(PartnerConfig partnerConfig) {
            this.buttonTextColorConfig = partnerConfig;
            return this;
        }

        public Builder setTextSizeConfig(PartnerConfig partnerConfig) {
            this.buttonTextSizeConfig = partnerConfig;
            return this;
        }

        public Builder setTextStyleConfig(PartnerConfig partnerConfig) {
            this.buttonTextStyleConfig = partnerConfig;
            return this;
        }

        public Builder setTextTypeFaceConfig(PartnerConfig partnerConfig) {
            this.buttonTextTypeFaceConfig = partnerConfig;
            return this;
        }

        public Builder setTextWeightConfig(PartnerConfig partnerConfig) {
            this.buttonTextWeightConfig = partnerConfig;
            return this;
        }
    }

    private FooterButtonPartnerConfig(int i, PartnerConfig partnerConfig, PartnerConfig partnerConfig2, PartnerConfig partnerConfig3, PartnerConfig partnerConfig4, PartnerConfig partnerConfig5, PartnerConfig partnerConfig6, PartnerConfig partnerConfig7, PartnerConfig partnerConfig8, PartnerConfig partnerConfig9, PartnerConfig partnerConfig10, PartnerConfig partnerConfig11, PartnerConfig partnerConfig12, PartnerConfig partnerConfig13, PartnerConfig partnerConfig14) {
        this.partnerTheme = i;
        this.buttonTextColorConfig = partnerConfig6;
        this.buttonMarginStartConfig = partnerConfig7;
        this.buttonTextSizeConfig = partnerConfig8;
        this.buttonMinHeightConfig = partnerConfig9;
        this.buttonTextTypeFaceConfig = partnerConfig10;
        this.buttonTextWeightConfig = partnerConfig11;
        this.buttonTextStyleConfig = partnerConfig12;
        this.buttonBackgroundConfig = partnerConfig;
        this.buttonDisableAlphaConfig = partnerConfig2;
        this.buttonDisableBackgroundConfig = partnerConfig3;
        this.buttonDisableTextColorConfig = partnerConfig4;
        this.buttonRadiusConfig = partnerConfig13;
        this.buttonIconConfig = partnerConfig5;
        this.buttonRippleColorAlphaConfig = partnerConfig14;
    }

    public PartnerConfig getButtonBackgroundConfig() {
        return this.buttonBackgroundConfig;
    }

    public PartnerConfig getButtonDisableAlphaConfig() {
        return this.buttonDisableAlphaConfig;
    }

    public PartnerConfig getButtonDisableBackgroundConfig() {
        return this.buttonDisableBackgroundConfig;
    }

    public PartnerConfig getButtonDisableTextColorConfig() {
        return this.buttonDisableTextColorConfig;
    }

    public PartnerConfig getButtonIconConfig() {
        return this.buttonIconConfig;
    }

    public PartnerConfig getButtonMarginStartConfig() {
        return this.buttonMarginStartConfig;
    }

    public PartnerConfig getButtonMinHeightConfig() {
        return this.buttonMinHeightConfig;
    }

    public PartnerConfig getButtonRadiusConfig() {
        return this.buttonRadiusConfig;
    }

    public PartnerConfig getButtonRippleColorAlphaConfig() {
        return this.buttonRippleColorAlphaConfig;
    }

    public PartnerConfig getButtonTextColorConfig() {
        return this.buttonTextColorConfig;
    }

    public PartnerConfig getButtonTextSizeConfig() {
        return this.buttonTextSizeConfig;
    }

    public PartnerConfig getButtonTextStyleConfig() {
        return this.buttonTextStyleConfig;
    }

    public PartnerConfig getButtonTextTypeFaceConfig() {
        return this.buttonTextTypeFaceConfig;
    }

    public PartnerConfig getButtonTextWeightConfig() {
        return this.buttonTextWeightConfig;
    }

    public int getPartnerTheme() {
        return this.partnerTheme;
    }
}
