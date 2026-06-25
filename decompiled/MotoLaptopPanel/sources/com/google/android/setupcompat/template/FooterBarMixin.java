package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.R$attr;
import com.google.android.setupcompat.R$bool;
import com.google.android.setupcompat.R$id;
import com.google.android.setupcompat.R$layout;
import com.google.android.setupcompat.R$style;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.internal.FooterButtonPartnerConfig;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.logging.internal.FooterBarMixinMetrics;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.KeyboardHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.view.ButtonBarLayout;

/* JADX INFO: loaded from: classes.dex */
public class FooterBarMixin implements Mixin {
    private static final Logger LOG = new Logger("FooterBarMixin");
    final boolean applyDynamicColor;
    final boolean applyPartnerResources;
    public LinearLayout buttonContainer;
    private final Context context;
    int defaultPadding;
    final int footerBarButtonMiddleSpacing;
    private int footerBarPaddingBottom;
    int footerBarPaddingEnd;
    int footerBarPaddingStart;
    private int footerBarPaddingTop;
    private final int footerBarPrimaryBackgroundColor;
    private final int footerBarPrimaryButtonDisabledTextColor;
    private final int footerBarPrimaryButtonEnabledTextColor;
    private final int footerBarSecondaryBackgroundColor;
    private final int footerBarSecondaryButtonDisabledTextColor;
    private final int footerBarSecondaryButtonEnabledTextColor;
    final boolean footerButtonAlignEnd;
    private final ViewStub footerStub;
    private String hostFragmentName;
    private String hostFragmentTag;
    public final FooterBarMixinMetrics metrics;
    private FooterButton primaryButton;
    private int primaryButtonId;
    public FooterButtonPartnerConfig primaryButtonPartnerConfigForTesting;
    private FooterButton secondaryButton;
    private int secondaryButtonId;
    public FooterButtonPartnerConfig secondaryButtonPartnerConfigForTesting;
    private int tertiaryButtonId;
    public FooterButtonPartnerConfig tertiaryButtonPartnerConfigForTesting;
    final boolean useFullDynamicColor;
    private boolean removeFooterBarWhenEmpty = true;
    private boolean isSecondaryButtonInPrimaryStyle = false;

    public FooterBarMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        FooterBarMixinMetrics footerBarMixinMetrics = new FooterBarMixinMetrics();
        this.metrics = footerBarMixinMetrics;
        Context context = templateLayout.getContext();
        this.context = context;
        this.footerStub = (ViewStub) templateLayout.findManagedViewById(R$id.suc_layout_footer);
        FooterButtonStyleUtils.clearSavedDefaultTextColor();
        boolean z = templateLayout instanceof PartnerCustomizationLayout;
        this.applyPartnerResources = z && ((PartnerCustomizationLayout) templateLayout).shouldApplyPartnerResource();
        this.applyDynamicColor = z && ((PartnerCustomizationLayout) templateLayout).shouldApplyDynamicColor();
        this.useFullDynamicColor = z && ((PartnerCustomizationLayout) templateLayout).useFullDynamicColor();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SucFooterBarMixin, i, 0);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SucFooterBarMixin_sucFooterBarPaddingVertical, 0);
        this.defaultPadding = dimensionPixelSize;
        this.footerBarPaddingTop = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SucFooterBarMixin_sucFooterBarPaddingTop, dimensionPixelSize);
        this.footerBarPaddingBottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SucFooterBarMixin_sucFooterBarPaddingBottom, this.defaultPadding);
        this.footerBarPaddingStart = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SucFooterBarMixin_sucFooterBarPaddingStart, 0);
        this.footerBarPaddingEnd = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SucFooterBarMixin_sucFooterBarPaddingEnd, 0);
        this.footerBarPrimaryBackgroundColor = typedArrayObtainStyledAttributes.getColor(R$styleable.SucFooterBarMixin_sucFooterBarPrimaryFooterBackground, 0);
        this.footerBarSecondaryBackgroundColor = typedArrayObtainStyledAttributes.getColor(R$styleable.SucFooterBarMixin_sucFooterBarSecondaryFooterBackground, 0);
        this.footerButtonAlignEnd = typedArrayObtainStyledAttributes.getBoolean(R$styleable.SucFooterBarMixin_sucFooterBarButtonAlignEnd, false);
        this.footerBarPrimaryButtonEnabledTextColor = typedArrayObtainStyledAttributes.getColor(R$styleable.SucFooterBarMixin_sucFooterBarPrimaryFooterButtonEnabledTextColor, 0);
        this.footerBarSecondaryButtonEnabledTextColor = typedArrayObtainStyledAttributes.getColor(R$styleable.SucFooterBarMixin_sucFooterBarSecondaryFooterButtonEnabledTextColor, 0);
        this.footerBarPrimaryButtonDisabledTextColor = typedArrayObtainStyledAttributes.getColor(R$styleable.SucFooterBarMixin_sucFooterBarPrimaryFooterButtonDisabledTextColor, 0);
        this.footerBarSecondaryButtonDisabledTextColor = typedArrayObtainStyledAttributes.getColor(R$styleable.SucFooterBarMixin_sucFooterBarSecondaryFooterButtonDisabledTextColor, 0);
        this.footerBarButtonMiddleSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SucFooterBarMixin_sucFooterBarButtonMiddleSpacing, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SucFooterBarMixin_sucFooterBarPrimaryFooterButton, 0);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SucFooterBarMixin_sucFooterBarSecondaryFooterButton, 0);
        typedArrayObtainStyledAttributes.recycle();
        FooterButtonInflater footerButtonInflater = new FooterButtonInflater(context);
        if (resourceId2 != 0) {
            setSecondaryButton(footerButtonInflater.inflate(resourceId2));
            footerBarMixinMetrics.logPrimaryButtonInitialStateVisibility(true, true);
        }
        if (resourceId != 0) {
            setPrimaryButton(footerButtonInflater.inflate(resourceId));
            footerBarMixinMetrics.logSecondaryButtonInitialStateVisibility(true, true);
        }
    }

    private View addSpace() {
        LinearLayout linearLayoutEnsureFooterInflated = ensureFooterInflated();
        View view = new View(this.context);
        view.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1.0f));
        view.setVisibility(4);
        linearLayoutEnsureFooterInflated.addView(view);
        return view;
    }

    private void autoSetButtonBarVisibility() {
        Button primaryButtonView = getPrimaryButtonView();
        Button secondaryButtonView = getSecondaryButtonView();
        int i = 0;
        boolean z = primaryButtonView != null && primaryButtonView.getVisibility() == 0;
        boolean z2 = secondaryButtonView != null && secondaryButtonView.getVisibility() == 0;
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout != null) {
            if (!z && !z2) {
                i = this.removeFooterBarWhenEmpty ? 8 : 4;
            }
            linearLayout.setVisibility(i);
        }
    }

    private FooterButton.OnButtonEventListener createButtonEventListener(final int i) {
        return new FooterButton.OnButtonEventListener() { // from class: com.google.android.setupcompat.template.FooterBarMixin.1
        };
    }

    private LinearLayout ensureFooterInflated() {
        if (this.buttonContainer == null) {
            if (this.footerStub == null) {
                throw new IllegalStateException("Footer stub is not found in this template");
            }
            LinearLayout linearLayout = (LinearLayout) inflateFooter(R$layout.suc_footer_button_bar);
            this.buttonContainer = linearLayout;
            onFooterBarInflated(linearLayout);
            onFooterBarApplyPartnerResource(this.buttonContainer);
        }
        return this.buttonContainer;
    }

    private void forceStackButtonInThreeButtonMode(Button button, Button button2, Button button3, int i) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) button2.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) button3.getLayoutParams();
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout instanceof ButtonBarLayout) {
            ((ButtonBarLayout) linearLayout).setStackedButtonForExpressiveStyle(true);
            int i2 = this.footerBarButtonMiddleSpacing / 2;
            layoutParams2.width = i;
            layoutParams2.topMargin = i2;
            button2.setLayoutParams(layoutParams2);
            layoutParams3.width = i;
            layoutParams3.topMargin = i2;
            layoutParams3.bottomMargin = i2;
            button3.setLayoutParams(layoutParams3);
            layoutParams.width = i;
            layoutParams.bottomMargin = i2;
            button.setLayoutParams(layoutParams);
        }
    }

    private static PartnerConfig getDrawablePartnerConfig(int i) {
        switch (i) {
            case 1:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_ADD_ANOTHER;
            case 2:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CANCEL;
            case 3:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CLEAR;
            case 4:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_DONE;
            case 5:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_NEXT;
            case 6:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_OPT_IN;
            case 7:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_SKIP;
            case 8:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_STOP;
            default:
                return null;
        }
    }

    private int getPartnerTheme(FooterButton footerButton, int i, PartnerConfig partnerConfig) {
        int theme = footerButton.getTheme();
        if (footerButton.getTheme() != 0 && !this.applyPartnerResources && !PartnerConfigHelper.isGlifExpressiveEnabled(this.context)) {
            i = theme;
        }
        return this.applyPartnerResources ? PartnerConfigHelper.get(this.context).getColor(this.context, partnerConfig) == 0 ? PartnerConfigHelper.isGlifExpressiveEnabled(this.context) ? R$style.SucGlifMaterialButton_Secondary : R$style.SucPartnerCustomizationButton_Secondary : PartnerConfigHelper.isGlifExpressiveEnabled(this.context) ? R$style.SucGlifMaterialButton_Primary : R$style.SucPartnerCustomizationButton_Primary : i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private IFooterActionButton inflateButton(FooterButton footerButton, FooterButtonPartnerConfig footerButtonPartnerConfig) {
        IFooterActionButton iFooterActionButtonCreateThemedButton = createThemedButton(this.context, footerButtonPartnerConfig.getPartnerTheme());
        Button button = (Button) iFooterActionButtonCreateThemedButton;
        button.setId(View.generateViewId());
        button.setText(footerButton.getText());
        button.setOnClickListener(footerButton);
        button.setVisibility(footerButton.getVisibility());
        button.setEnabled(footerButton.isEnabled());
        if (iFooterActionButtonCreateThemedButton instanceof MaterialFooterActionButton) {
            ((MaterialFooterActionButton) iFooterActionButtonCreateThemedButton).setFooterButton(footerButton);
        } else if (button instanceof FooterActionButton) {
            ((FooterActionButton) iFooterActionButtonCreateThemedButton).setFooterButton(footerButton);
        } else {
            LOG.e("Set the footer button error!");
        }
        footerButton.setOnButtonEventListener(createButtonEventListener(button.getId()));
        return iFooterActionButtonCreateThemedButton;
    }

    private boolean isBothButtons(Button button, Button button2) {
        boolean z = button != null && button.getVisibility() == 0;
        boolean z2 = button2 != null && button2.getVisibility() == 0;
        LOG.atDebug("isPrimaryVisible=" + z + ", isSecondaryVisible=" + z2);
        return z && z2;
    }

    private boolean isPrimaryButtonOnly(Button button, Button button2) {
        boolean z = button != null && button2 == null;
        boolean z2 = (button == null || button2 == null || button2.getVisibility() == 0) ? false : true;
        LOG.atDebug("isPrimaryOnly=" + z + ", isPrimaryOnlyButSecondaryInvisible=" + z2);
        return z || z2;
    }

    private boolean isSecondaryOnly(Button button, Button button2) {
        boolean z = button2 != null && button == null;
        boolean z2 = (button2 == null || button == null || button.getVisibility() == 0) ? false : true;
        LOG.atDebug("isSecondaryOnly=" + z + ", isSecondaryOnlyButPrimaryInvisible=" + z2);
        return z || z2;
    }

    private boolean isThreeButtons(Button button, Button button2, Button button3) {
        boolean z = button3 != null && button3.getVisibility() == 0;
        LOG.atDebug("isTertiaryButtonVisible=" + z);
        return z && isBothButtons(button, button2);
    }

    private boolean isTwoPaneLayout() {
        return this.context.getResources().getBoolean(R$bool.sucTwoPaneLayoutStyle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setButtonWidthForExpressiveStyle$3() {
        int measuredWidth = this.buttonContainer.getMeasuredWidth();
        Button primaryButtonView = getPrimaryButtonView();
        Button secondaryButtonView = getSecondaryButtonView();
        Button tertiaryButtonView = getTertiaryButtonView();
        if (isTwoPaneLayout()) {
            measuredWidth /= 2;
            this.buttonContainer.setGravity(8388613);
        }
        int i = ((measuredWidth - this.footerBarPaddingStart) - this.footerBarPaddingEnd) - this.footerBarButtonMiddleSpacing;
        int i2 = i / 2;
        if (isThreeButtons(primaryButtonView, secondaryButtonView, tertiaryButtonView)) {
            forceStackButtonInThreeButtonMode(primaryButtonView, secondaryButtonView, tertiaryButtonView, i);
            return;
        }
        if (isBothButtons(primaryButtonView, secondaryButtonView)) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) primaryButtonView.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) secondaryButtonView.getLayoutParams();
            if (stackButtonIfTextOverFlow(primaryButtonView, secondaryButtonView, i2, i)) {
                return;
            }
            if (layoutParams != null) {
                layoutParams.width = i2;
                layoutParams.setMarginStart(this.footerBarButtonMiddleSpacing / 2);
                primaryButtonView.setLayoutParams(layoutParams);
            }
            if (layoutParams2 != null) {
                layoutParams2.width = i2;
                layoutParams2.setMarginEnd(this.footerBarButtonMiddleSpacing / 2);
                secondaryButtonView.setLayoutParams(layoutParams2);
                return;
            }
            return;
        }
        if (isPrimaryButtonOnly(primaryButtonView, secondaryButtonView)) {
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) primaryButtonView.getLayoutParams();
            if (layoutParams3 != null) {
                layoutParams3.width = i;
                primaryButtonView.setLayoutParams(layoutParams3);
                return;
            }
            return;
        }
        if (!isSecondaryOnly(primaryButtonView, secondaryButtonView)) {
            LOG.atInfo("There are no button visible in the footer bar.");
            return;
        }
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) secondaryButtonView.getLayoutParams();
        if (layoutParams4 != null) {
            layoutParams4.width = i;
            secondaryButtonView.setLayoutParams(layoutParams4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPrimaryButton$0(Button button) {
        if (KeyboardHelper.isKeyboardFocusEnhancementEnabled(this.context) && KeyboardHelper.hasHardwareKeyboard(this.context)) {
            button.requestFocus();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSecondaryButton$1(Button button) {
        if (KeyboardHelper.isKeyboardFocusEnhancementEnabled(this.context) && KeyboardHelper.hasHardwareKeyboard(this.context)) {
            if (this.primaryButtonId == 0 || getPrimaryButtonView().getVisibility() != 0) {
                button.requestFocus();
            }
        }
    }

    private void onFooterButtonApplyPartnerResource(Button button, FooterButtonPartnerConfig footerButtonPartnerConfig) {
        if (this.applyPartnerResources) {
            FooterButtonStyleUtils.applyButtonPartnerResources(this.context, button, this.applyDynamicColor, button.getId() == this.primaryButtonId, footerButtonPartnerConfig);
            if (this.applyDynamicColor) {
                return;
            }
            updateButtonTextColorWithStates(button, footerButtonPartnerConfig.getButtonTextColorConfig(), footerButtonPartnerConfig.getButtonDisableTextColorConfig());
        }
    }

    private void setEvenlyWeightedButtons(Button button, Button button2, boolean z) {
        LinearLayout.LayoutParams layoutParams;
        LinearLayout.LayoutParams layoutParams2;
        if (button != null && button2 != null && z) {
            button.measure(0, 0);
            int measuredWidth = button.getMeasuredWidth();
            button2.measure(0, 0);
            int iMax = Math.max(measuredWidth, button2.getMeasuredWidth());
            button.getLayoutParams().width = iMax;
            button2.getLayoutParams().width = iMax;
            return;
        }
        if (button != null && (layoutParams2 = (LinearLayout.LayoutParams) button.getLayoutParams()) != null) {
            layoutParams2.width = -2;
            layoutParams2.weight = 0.0f;
            button.setLayoutParams(layoutParams2);
        }
        if (button2 == null || (layoutParams = (LinearLayout.LayoutParams) button2.getLayoutParams()) == null) {
            return;
        }
        layoutParams.width = -2;
        layoutParams.weight = 0.0f;
        button2.setLayoutParams(layoutParams);
    }

    private void updateButtonTextColorWithStates(Button button, PartnerConfig partnerConfig, PartnerConfig partnerConfig2) {
        if (button.isEnabled()) {
            FooterButtonStyleUtils.updateButtonTextEnabledColorWithPartnerConfig(this.context, button, partnerConfig);
        } else {
            FooterButtonStyleUtils.updateButtonTextDisabledColorWithPartnerConfig(this.context, button, partnerConfig2);
        }
    }

    private void updateFooterBarPadding(LinearLayout linearLayout, int i, int i2, int i3, int i4) {
        if (linearLayout == null) {
            return;
        }
        linearLayout.setPadding(i, i2, i3, i4);
    }

    private void updateTextColorForButton(Button button, boolean z, int i) {
        if (z) {
            FooterButtonStyleUtils.updateButtonTextEnabledColor(button, i);
        } else {
            FooterButtonStyleUtils.updateButtonTextDisabledColor(button, i);
        }
    }

    protected IFooterActionButton createThemedButton(Context context, int i) {
        if (PartnerConfigHelper.isGlifExpressiveEnabled(context)) {
            try {
                return i == R$style.SucGlifMaterialButton_Primary ? new MaterialFooterActionButton(new ContextThemeWrapper(context, i), null, R$attr.sucMaterialButtonStyle) : new MaterialFooterActionButton(new ContextThemeWrapper(context, i), null, R$attr.sucMaterialOutlinedButtonStyle);
            } catch (IllegalArgumentException e) {
                LOG.e("Applyed invalid material theme: " + e);
                i = i == R$style.SucGlifMaterialButton_Primary ? R$style.SucPartnerCustomizationButton_Primary : R$style.SucPartnerCustomizationButton_Secondary;
            }
        }
        return (IFooterActionButton) LayoutInflater.from(new ContextThemeWrapper(context, i)).inflate(R$layout.suc_button, (ViewGroup) null, false);
    }

    public LinearLayout getButtonContainer() {
        return this.buttonContainer;
    }

    public PersistableBundle getLoggingMetrics() {
        LOG.atDebug("FooterBarMixin fragment name=" + this.hostFragmentName + ", Tag=" + this.hostFragmentTag);
        PersistableBundle metrics = this.metrics.getMetrics();
        if (PartnerConfigHelper.isEnhancedSetupDesignMetricsEnabled(this.context)) {
            String str = this.hostFragmentName;
            if (str != null) {
                metrics.putString("HostFragmentName", CustomEvent.trimsStringOverMaxLength(str));
            }
            String str2 = this.hostFragmentTag;
            if (str2 != null) {
                metrics.putString("HostFragmentTag", CustomEvent.trimsStringOverMaxLength(str2));
            }
        }
        return metrics;
    }

    int getPaddingBottom() {
        LinearLayout linearLayout = this.buttonContainer;
        return linearLayout != null ? linearLayout.getPaddingBottom() : this.footerStub.getPaddingBottom();
    }

    int getPaddingTop() {
        LinearLayout linearLayout = this.buttonContainer;
        return linearLayout != null ? linearLayout.getPaddingTop() : this.footerStub.getPaddingTop();
    }

    public FooterButton getPrimaryButton() {
        return this.primaryButton;
    }

    public Button getPrimaryButtonView() {
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout == null) {
            return null;
        }
        return (Button) linearLayout.findViewById(this.primaryButtonId);
    }

    public FooterButton getSecondaryButton() {
        return this.secondaryButton;
    }

    public Button getSecondaryButtonView() {
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout == null) {
            return null;
        }
        return (Button) linearLayout.findViewById(this.secondaryButtonId);
    }

    public Button getTertiaryButtonView() {
        if (!PartnerConfigHelper.isGlifExpressiveEnabled(this.context)) {
            LOG.atDebug("Cannot get tertiary button when glif expressive is not enabled.");
            return null;
        }
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout == null) {
            return null;
        }
        return (Button) linearLayout.findViewById(this.tertiaryButtonId);
    }

    public int getVisibility() {
        return this.buttonContainer.getVisibility();
    }

    protected View inflateFooter(int i) {
        this.footerStub.setLayoutInflater(LayoutInflater.from(new ContextThemeWrapper(this.context, R$style.SucPartnerCustomizationButtonBar_Stackable)));
        this.footerStub.setLayoutResource(i);
        return this.footerStub.inflate();
    }

    protected boolean isFooterButtonAlignedEnd() {
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(this.context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ALIGNED_END;
        return partnerConfigHelper.isPartnerConfigAvailable(partnerConfig) ? PartnerConfigHelper.get(this.context).getBoolean(this.context, partnerConfig, false) : this.footerButtonAlignEnd;
    }

    protected boolean isFooterButtonsEvenlyWeighted() {
        if (!this.isSecondaryButtonInPrimaryStyle) {
            return false;
        }
        PartnerConfigHelper.get(this.context);
        return PartnerConfigHelper.isNeutralButtonStyleEnabled(this.context);
    }

    boolean isPrimaryButtonVisible() {
        return getPrimaryButtonView() != null && getPrimaryButtonView().getVisibility() == 0;
    }

    boolean isSecondaryButtonVisible() {
        return getSecondaryButtonView() != null && getSecondaryButtonView().getVisibility() == 0;
    }

    public void onAttachedToWindow() {
        this.metrics.logPrimaryButtonInitialStateVisibility(isPrimaryButtonVisible(), false);
        this.metrics.logSecondaryButtonInitialStateVisibility(isSecondaryButtonVisible(), false);
    }

    public void onDetachedFromWindow() {
        this.metrics.updateButtonVisibility(isPrimaryButtonVisible(), isSecondaryButtonVisible());
    }

    protected void onFooterBarApplyPartnerResource(LinearLayout linearLayout) {
        int dimension;
        if (linearLayout != null && this.applyPartnerResources) {
            if (!this.useFullDynamicColor) {
                linearLayout.setBackgroundColor(PartnerConfigHelper.get(this.context).getColor(this.context, PartnerConfig.CONFIG_FOOTER_BAR_BG_COLOR));
            }
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(this.context);
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_PADDING_TOP;
            if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                this.footerBarPaddingTop = (int) PartnerConfigHelper.get(this.context).getDimension(this.context, partnerConfig);
            }
            PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(this.context);
            PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_FOOTER_BUTTON_PADDING_BOTTOM;
            if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                this.footerBarPaddingBottom = (int) PartnerConfigHelper.get(this.context).getDimension(this.context, partnerConfig2);
            }
            PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(this.context);
            PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_FOOTER_BAR_PADDING_START;
            if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)) {
                this.footerBarPaddingStart = (int) PartnerConfigHelper.get(this.context).getDimension(this.context, partnerConfig3);
            }
            PartnerConfigHelper partnerConfigHelper4 = PartnerConfigHelper.get(this.context);
            PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_FOOTER_BAR_PADDING_END;
            if (partnerConfigHelper4.isPartnerConfigAvailable(partnerConfig4)) {
                this.footerBarPaddingEnd = (int) PartnerConfigHelper.get(this.context).getDimension(this.context, partnerConfig4);
            }
            updateFooterBarPadding(linearLayout, this.footerBarPaddingStart, this.footerBarPaddingTop, this.footerBarPaddingEnd, this.footerBarPaddingBottom);
            PartnerConfigHelper partnerConfigHelper5 = PartnerConfigHelper.get(this.context);
            PartnerConfig partnerConfig5 = PartnerConfig.CONFIG_FOOTER_BAR_MIN_HEIGHT;
            if (!partnerConfigHelper5.isPartnerConfigAvailable(partnerConfig5) || (dimension = (int) PartnerConfigHelper.get(this.context).getDimension(this.context, partnerConfig5)) <= 0) {
                return;
            }
            linearLayout.setMinimumHeight(dimension);
        }
    }

    protected void onFooterBarInflated(LinearLayout linearLayout) {
        if (linearLayout == null) {
            return;
        }
        linearLayout.setId(View.generateViewId());
        updateFooterBarPadding(linearLayout, this.footerBarPaddingStart, this.footerBarPaddingTop, this.footerBarPaddingEnd, this.footerBarPaddingBottom);
        if (isFooterButtonAlignedEnd()) {
            linearLayout.setGravity(8388629);
        }
    }

    protected void onFooterButtonInflated(Button button, int i) {
        if (!this.applyDynamicColor && i != 0) {
            FooterButtonStyleUtils.updateButtonBackground(button, i);
        }
        this.buttonContainer.addView(button);
        autoSetButtonBarVisibility();
    }

    protected void repopulateButtons() {
        FooterBarMixin footerBarMixin;
        LinearLayout linearLayoutEnsureFooterInflated = ensureFooterInflated();
        Button primaryButtonView = getPrimaryButtonView();
        Button secondaryButtonView = getSecondaryButtonView();
        Button tertiaryButtonView = getTertiaryButtonView();
        linearLayoutEnsureFooterInflated.removeAllViews();
        boolean zIsFooterButtonsEvenlyWeighted = isFooterButtonsEvenlyWeighted();
        if (this.context.getResources().getConfiguration().orientation == 2 && zIsFooterButtonsEvenlyWeighted && isFooterButtonAlignedEnd() && !PartnerConfigHelper.isGlifExpressiveEnabled(this.context)) {
            addSpace();
        }
        if (secondaryButtonView != null) {
            if (this.isSecondaryButtonInPrimaryStyle) {
                footerBarMixin = this;
                footerBarMixin.updateFooterBarPadding(linearLayoutEnsureFooterInflated, linearLayoutEnsureFooterInflated.getPaddingRight(), linearLayoutEnsureFooterInflated.getPaddingTop(), linearLayoutEnsureFooterInflated.getPaddingRight(), linearLayoutEnsureFooterInflated.getPaddingBottom());
            } else {
                footerBarMixin = this;
            }
            linearLayoutEnsureFooterInflated.addView(secondaryButtonView);
        } else {
            footerBarMixin = this;
        }
        if (!footerBarMixin.isFooterButtonAlignedEnd() && !PartnerConfigHelper.isGlifExpressiveEnabled(footerBarMixin.context)) {
            footerBarMixin.addSpace();
        }
        if (PartnerConfigHelper.isGlifExpressiveEnabled(footerBarMixin.context) && tertiaryButtonView != null) {
            if (footerBarMixin.isBothButtons(primaryButtonView, secondaryButtonView)) {
                linearLayoutEnsureFooterInflated.addView(tertiaryButtonView);
            } else {
                LOG.atDebug("Cannot add tertiary button when primary or secondary button is null.");
            }
        }
        if (primaryButtonView != null) {
            linearLayoutEnsureFooterInflated.addView(primaryButtonView);
        }
        footerBarMixin.setEvenlyWeightedButtons(primaryButtonView, secondaryButtonView, zIsFooterButtonsEvenlyWeighted);
        if (PartnerConfigHelper.isGlifExpressiveEnabled(footerBarMixin.context)) {
            footerBarMixin.setButtonWidthForExpressiveStyle();
        }
    }

    public void setButtonWidthForExpressiveStyle() {
        this.buttonContainer.post(new Runnable() { // from class: com.google.android.setupcompat.template.FooterBarMixin$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setButtonWidthForExpressiveStyle$3();
            }
        });
    }

    public void setPrimaryButton(FooterButton footerButton) {
        Preconditions.ensureOnMainThread("setPrimaryButton");
        ensureFooterInflated();
        int i = PartnerConfigHelper.isGlifExpressiveEnabled(this.context) ? R$style.SucGlifMaterialButton_Primary : R$style.SucPartnerCustomizationButton_Primary;
        FooterButtonPartnerConfig.Builder builder = new FooterButtonPartnerConfig.Builder(footerButton);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_BG_COLOR;
        FooterButtonPartnerConfig footerButtonPartnerConfigBuild = builder.setPartnerTheme(getPartnerTheme(footerButton, i, partnerConfig)).setButtonBackgroundConfig(partnerConfig).setButtonDisableAlphaConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_ALPHA).setButtonDisableBackgroundConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_BG_COLOR).setButtonDisableTextColorConfig(PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_DISABLED_TEXT_COLOR).setButtonIconConfig(getDrawablePartnerConfig(footerButton.getButtonType())).setButtonRadiusConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_RADIUS).setButtonRippleColorAlphaConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_RIPPLE_COLOR_ALPHA).setTextColorConfig(PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_TEXT_COLOR).setMarginStartConfig(PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_MARGIN_START).setTextSizeConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_SIZE).setButtonMinHeight(PartnerConfig.CONFIG_FOOTER_BUTTON_MIN_HEIGHT).setTextTypeFaceConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_FAMILY).setTextWeightConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_WEIGHT).setTextStyleConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_STYLE).build();
        Object objInflateButton = inflateButton(footerButton, footerButtonPartnerConfigBuild);
        final Button button = (Button) objInflateButton;
        this.primaryButtonId = button.getId();
        if (objInflateButton instanceof MaterialFooterActionButton) {
            ((MaterialFooterActionButton) objInflateButton).setPrimaryButtonStyle(true);
        } else if (button instanceof FooterActionButton) {
            ((FooterActionButton) objInflateButton).setPrimaryButtonStyle(true);
        } else {
            LOG.e("Set the primary button style error when setting primary button.");
        }
        this.primaryButton = footerButton;
        this.primaryButtonPartnerConfigForTesting = footerButtonPartnerConfigBuild;
        onFooterButtonInflated(button, this.footerBarPrimaryBackgroundColor);
        onFooterButtonApplyPartnerResource(button, footerButtonPartnerConfigBuild);
        if (PartnerConfigHelper.isGlifExpressiveEnabled(this.context)) {
            boolean zIsEnabled = this.primaryButton.isEnabled();
            updateTextColorForButton(button, zIsEnabled, zIsEnabled ? this.footerBarPrimaryButtonEnabledTextColor : this.footerBarPrimaryButtonDisabledTextColor);
        }
        repopulateButtons();
        button.post(new Runnable() { // from class: com.google.android.setupcompat.template.FooterBarMixin$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setPrimaryButton$0(button);
            }
        });
    }

    public void setSecondaryButton(FooterButton footerButton) {
        setSecondaryButton(footerButton, false);
    }

    public void setSecondaryButton(FooterButton footerButton, boolean z) {
        Preconditions.ensureOnMainThread("setSecondaryButton");
        this.isSecondaryButtonInPrimaryStyle = z;
        ensureFooterInflated();
        FooterButtonPartnerConfig footerButtonPartnerConfigBuild = new FooterButtonPartnerConfig.Builder(footerButton).setPartnerTheme(getPartnerTheme(footerButton, PartnerConfigHelper.isGlifExpressiveEnabled(this.context) ? z ? R$style.SucGlifMaterialButton_Primary : R$style.SucGlifMaterialButton_Secondary : z ? R$style.SucPartnerCustomizationButton_Primary : R$style.SucPartnerCustomizationButton_Secondary, z ? PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_BG_COLOR : PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_BG_COLOR)).setButtonBackgroundConfig(z ? PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_BG_COLOR : PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_BG_COLOR).setButtonDisableAlphaConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_ALPHA).setButtonDisableBackgroundConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_BG_COLOR).setButtonDisableTextColorConfig(z ? PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_DISABLED_TEXT_COLOR : PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_DISABLED_TEXT_COLOR).setButtonIconConfig(getDrawablePartnerConfig(footerButton.getButtonType())).setButtonRadiusConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_RADIUS).setButtonRippleColorAlphaConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_RIPPLE_COLOR_ALPHA).setTextColorConfig(z ? PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_TEXT_COLOR : PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_TEXT_COLOR).setMarginStartConfig(PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_MARGIN_START).setTextSizeConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_SIZE).setButtonMinHeight(PartnerConfig.CONFIG_FOOTER_BUTTON_MIN_HEIGHT).setTextTypeFaceConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_FAMILY).setTextWeightConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_WEIGHT).setTextStyleConfig(PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_STYLE).build();
        Object objInflateButton = inflateButton(footerButton, footerButtonPartnerConfigBuild);
        final Button button = (Button) objInflateButton;
        this.secondaryButtonId = button.getId();
        if (objInflateButton instanceof MaterialFooterActionButton) {
            ((MaterialFooterActionButton) objInflateButton).setPrimaryButtonStyle(z);
        } else if (button instanceof FooterActionButton) {
            ((FooterActionButton) objInflateButton).setPrimaryButtonStyle(z);
        } else {
            LOG.e("Set the primary button style error when setting secondary button.");
        }
        this.secondaryButton = footerButton;
        this.secondaryButtonPartnerConfigForTesting = footerButtonPartnerConfigBuild;
        onFooterButtonInflated(button, this.footerBarSecondaryBackgroundColor);
        onFooterButtonApplyPartnerResource(button, footerButtonPartnerConfigBuild);
        if (PartnerConfigHelper.isGlifExpressiveEnabled(this.context)) {
            boolean zIsEnabled = this.secondaryButton.isEnabled();
            if (z) {
                updateTextColorForButton(button, zIsEnabled, zIsEnabled ? this.footerBarPrimaryButtonEnabledTextColor : this.footerBarPrimaryButtonDisabledTextColor);
            } else {
                updateTextColorForButton(button, zIsEnabled, zIsEnabled ? this.footerBarSecondaryButtonEnabledTextColor : this.footerBarSecondaryButtonDisabledTextColor);
            }
        }
        repopulateButtons();
        button.post(new Runnable() { // from class: com.google.android.setupcompat.template.FooterBarMixin$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setSecondaryButton$1(button);
            }
        });
    }

    boolean stackButtonIfTextOverFlow(Button button, Button button2, float f, int i) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) button2.getLayoutParams();
        String string = button.getText().toString();
        Paint paint = new Paint();
        paint.setTypeface(button.getTypeface());
        paint.setTextSize(button.getTextSize());
        float fMeasureText = paint.measureText(string) + button.getPaddingLeft() + button.getPaddingRight() + button.getPaddingStart() + button.getPaddingEnd();
        boolean z = fMeasureText > f;
        Logger logger = LOG;
        logger.atDebug("isPrimaryButtonTextOverFlowing= " + z + ", primaryButtonWidth= " + fMeasureText + ", maxButtonWidth= " + f);
        String string2 = button2.getText().toString();
        Paint paint2 = new Paint();
        paint2.setTypeface(button2.getTypeface());
        paint2.setTextSize(button2.getTextSize());
        float fMeasureText2 = paint2.measureText(string2) + ((float) button2.getPaddingLeft()) + ((float) button2.getPaddingRight()) + ((float) button2.getPaddingStart()) + ((float) button2.getPaddingEnd());
        boolean z2 = fMeasureText2 > f;
        logger.atDebug("isSecondaryButtonTextOverFlowing= " + z2 + ", secondaryButtonWidth= " + fMeasureText2 + ", maxButtonWidth= " + f);
        if (z || z2) {
            LinearLayout linearLayout = this.buttonContainer;
            if (linearLayout instanceof ButtonBarLayout) {
                ((ButtonBarLayout) linearLayout).setStackedButtonForExpressiveStyle(true);
                int i2 = this.footerBarButtonMiddleSpacing / 2;
                layoutParams2.width = i;
                layoutParams2.topMargin = i2;
                button2.setLayoutParams(layoutParams2);
                layoutParams.width = i;
                layoutParams.bottomMargin = i2;
                button.setLayoutParams(layoutParams);
                return true;
            }
        }
        return false;
    }
}
