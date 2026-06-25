package com.google.android.setupcompat;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import androidx.fragment.app.FragmentManager;
import com.google.android.setupcompat.internal.FocusChangedMetricHelper;
import com.google.android.setupcompat.internal.LifecycleFragment;
import com.google.android.setupcompat.internal.PersistableBundles;
import com.google.android.setupcompat.internal.SetupCompatServiceInvoker;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.logging.MetricKey;
import com.google.android.setupcompat.logging.SetupMetricsLogger;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.StatusBarMixin;
import com.google.android.setupcompat.template.SystemNavBarMixin;
import com.google.android.setupcompat.util.BuildCompatUtils;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;

/* JADX INFO: loaded from: classes.dex */
public abstract class PartnerCustomizationLayout extends TemplateLayout {
    private static final Logger LOG = new Logger("PartnerCustomizationLayout");
    protected Activity activity;
    private int footerBarPaddingBottom;
    FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks;
    private PersistableBundle layoutTypeBundle;
    private boolean useDynamicColor;
    private boolean useFullDynamicColorAttr;
    private boolean usePartnerResourceAttr;
    final ViewTreeObserver.OnWindowFocusChangeListener windowFocusChangeListener;

    public PartnerCustomizationLayout(Context context, int i, int i2) {
        super(context, i, i2);
        this.windowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener() { // from class: com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                this.f$0.onFocusChanged(z);
            }
        };
        init(null, R$attr.sucLayoutTheme);
    }

    public PartnerCustomizationLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.windowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener() { // from class: com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                this.f$0.onFocusChanged(z);
            }
        };
        init(attributeSet, R$attr.sucLayoutTheme);
    }

    public PartnerCustomizationLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.windowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener() { // from class: com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                this.f$0.onFocusChanged(z);
            }
        };
        init(attributeSet, i);
    }

    private void init(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SucPartnerCustomizationLayout, i, 0);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R$styleable.SucPartnerCustomizationLayout_sucLayoutFullscreen, true);
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = getContext().obtainStyledAttributes(attributeSet, R$styleable.SucFooterBarMixin, i, 0);
        this.footerBarPaddingBottom = typedArrayObtainStyledAttributes2.getDimensionPixelSize(R$styleable.SucFooterBarMixin_sucFooterBarPaddingBottom, typedArrayObtainStyledAttributes2.getDimensionPixelSize(R$styleable.SucFooterBarMixin_sucFooterBarPaddingVertical, 0));
        typedArrayObtainStyledAttributes2.recycle();
        if (z) {
            setSystemUiVisibility(1024);
        }
        registerMixin(StatusBarMixin.class, new StatusBarMixin(this, this.activity.getWindow(), attributeSet, i));
        registerMixin(SystemNavBarMixin.class, new SystemNavBarMixin(this, this.activity.getWindow()));
        registerMixin(FooterBarMixin.class, new FooterBarMixin(this, attributeSet, i));
        ((SystemNavBarMixin) getMixin(SystemNavBarMixin.class)).applyPartnerCustomizations(attributeSet, i);
        this.activity.getWindow().addFlags(Integer.MIN_VALUE);
        this.activity.getWindow().clearFlags(67108864);
        this.activity.getWindow().clearFlags(134217728);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logFooterButtonMetrics(PersistableBundle persistableBundle) {
        Activity activity = this.activity;
        if (activity != null && WizardManagerHelper.isAnySetupWizard(activity.getIntent()) && PartnerConfigHelper.isEnhancedSetupDesignMetricsEnabled(getContext())) {
            FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
            if (footerBarMixin == null || (footerBarMixin.getPrimaryButton() == null && footerBarMixin.getSecondaryButton() == null)) {
                LOG.atDebug("Skip footer button logging because no footer buttons.");
                return;
            }
            footerBarMixin.onDetachedFromWindow();
            FooterButton primaryButton = footerBarMixin.getPrimaryButton();
            FooterButton secondaryButton = footerBarMixin.getSecondaryButton();
            SetupMetricsLogger.logCustomEvent(getContext(), CustomEvent.create(MetricKey.get("FooterButtonMetrics", this.activity), PersistableBundles.mergeBundles(footerBarMixin.getLoggingMetrics(), primaryButton != null ? primaryButton.getMetrics("PrimaryFooterButton") : PersistableBundle.EMPTY, secondaryButton != null ? secondaryButton.getMetrics("SecondaryFooterButton") : PersistableBundle.EMPTY, persistableBundle)));
        }
    }

    public static Activity lookupActivityFromContext(Context context) {
        return PartnerConfigHelper.lookupActivityFromContext(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFocusChanged(boolean z) {
        SetupCompatServiceInvoker.get(getContext()).onFocusStatusChanged(FocusChangedMetricHelper.getScreenName(this.activity), FocusChangedMetricHelper.getExtraBundle(this.activity, this, z));
    }

    private void tryRegisterFragmentCallbacks(Activity activity) {
    }

    private void tryUnregisterFragmentCallbacks(Activity activity) {
    }

    protected boolean enablePartnerResourceLoading() {
        return true;
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    protected ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R$id.suc_layout_content;
        }
        return super.findContainer(i);
    }

    public PersistableBundle getLayoutTypeMetrics() {
        return this.layoutTypeBundle;
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (PartnerConfigHelper.isGlifExpressiveEnabled(getContext()) && windowInsets.getSystemWindowInsetBottom() > 0) {
            LOG.atDebug("NavigationBarHeight: " + windowInsets.getSystemWindowInsetBottom());
            FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
            LinearLayout buttonContainer = footerBarMixin.getButtonContainer();
            if (footerBarMixin.getButtonContainer() != null) {
                PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(getContext());
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_PADDING_BOTTOM;
                if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                    this.footerBarPaddingBottom = (int) PartnerConfigHelper.get(getContext()).getDimension(getContext(), partnerConfig);
                }
                buttonContainer.setPadding(buttonContainer.getPaddingLeft(), buttonContainer.getPaddingTop(), buttonContainer.getPaddingRight(), this.footerBarPaddingBottom + windowInsets.getSystemWindowInsetBottom());
            }
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (LifecycleFragment.attachNow(this.activity, new LifecycleFragment.OnFragmentLifecycleChangeListener() { // from class: com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda1
            @Override // com.google.android.setupcompat.internal.LifecycleFragment.OnFragmentLifecycleChangeListener
            public final void onStop(PersistableBundle persistableBundle) {
                this.f$0.logFooterButtonMetrics(persistableBundle);
            }
        }) == null) {
            Logger logger = LOG;
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to attach lifecycle fragment to the host activity. Activity=");
            Activity activity = this.activity;
            sb.append(activity != null ? activity.getClass().getSimpleName() : "null");
            logger.atDebug(sb.toString());
        }
        if (WizardManagerHelper.isAnySetupWizard(this.activity.getIntent())) {
            getViewTreeObserver().addOnWindowFocusChangeListener(this.windowFocusChangeListener);
        }
        ((FooterBarMixin) getMixin(FooterBarMixin.class)).onAttachedToWindow();
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    protected void onBeforeTemplateInflated(AttributeSet attributeSet, int i) {
        boolean z = true;
        this.usePartnerResourceAttr = true;
        this.activity = lookupActivityFromContext(getContext());
        Logger logger = LOG;
        logger.atDebug("Flag of isEnhancedSetupDesignMetricsEnabled=" + PartnerConfigHelper.isEnhancedSetupDesignMetricsEnabled(getContext()));
        if (PartnerConfigHelper.isEnhancedSetupDesignMetricsEnabled(getContext())) {
            tryRegisterFragmentCallbacks(this.activity);
        }
        WizardManagerHelper.isAnySetupWizard(this.activity.getIntent());
        boolean zIsAnySetupWizard = WizardManagerHelper.isAnySetupWizard(this.activity.getIntent());
        boolean zAllowCustomizationInSetupFlow = WizardManagerHelper.allowCustomizationInSetupFlow(this.activity.getIntent());
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SucPartnerCustomizationLayout, i, 0);
        int i2 = R$styleable.SucPartnerCustomizationLayout_sucUsePartnerResource;
        if (!typedArrayObtainStyledAttributes.hasValue(i2)) {
            logger.e("Attribute sucUsePartnerResource not found in " + this.activity.getComponentName());
        }
        if ((!zIsAnySetupWizard || zAllowCustomizationInSetupFlow) && !typedArrayObtainStyledAttributes.getBoolean(i2, true)) {
            z = false;
        }
        this.usePartnerResourceAttr = z;
        int i3 = R$styleable.SucPartnerCustomizationLayout_sucFullDynamicColor;
        this.useDynamicColor = typedArrayObtainStyledAttributes.hasValue(i3);
        this.useFullDynamicColorAttr = typedArrayObtainStyledAttributes.getBoolean(i3, false);
        typedArrayObtainStyledAttributes.recycle();
        logger.atDebug("activity=" + this.activity.getClass().getSimpleName() + " isSetupFlow=" + zIsAnySetupWizard + " enablePartnerResourceLoading=" + enablePartnerResourceLoading() + " usePartnerResourceAttr=" + this.usePartnerResourceAttr + " useDynamicColor=" + this.useDynamicColor + " useFullDynamicColorAttr=" + this.useFullDynamicColorAttr);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (WizardManagerHelper.isAnySetupWizard(this.activity.getIntent())) {
            FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
            footerBarMixin.onDetachedFromWindow();
            FooterButton primaryButton = footerBarMixin.getPrimaryButton();
            FooterButton secondaryButton = footerBarMixin.getSecondaryButton();
            PersistableBundle metrics = primaryButton != null ? primaryButton.getMetrics("PrimaryFooterButton") : PersistableBundle.EMPTY;
            PersistableBundle metrics2 = secondaryButton != null ? secondaryButton.getMetrics("SecondaryFooterButton") : PersistableBundle.EMPTY;
            PersistableBundle persistableBundle = this.layoutTypeBundle;
            if (persistableBundle == null) {
                persistableBundle = PersistableBundle.EMPTY;
            }
            SetupMetricsLogger.logCustomEvent(getContext(), CustomEvent.create(MetricKey.get("SetupCompatMetrics", this.activity), PersistableBundles.mergeBundles(footerBarMixin.getLoggingMetrics(), metrics, metrics2, persistableBundle)));
        }
        getViewTreeObserver().removeOnWindowFocusChangeListener(this.windowFocusChangeListener);
        if (PartnerConfigHelper.isEnhancedSetupDesignMetricsEnabled(getContext())) {
            tryUnregisterFragmentCallbacks(this.activity);
        }
    }

    public boolean shouldApplyDynamicColor() {
        if (BuildCompatUtils.isAtLeastS() && PartnerConfigHelper.get(getContext()).isAvailable()) {
            return this.useDynamicColor || PartnerConfigHelper.isSetupWizardDynamicColorEnabled(getContext());
        }
        return false;
    }

    public boolean shouldApplyPartnerResource() {
        return enablePartnerResourceLoading() && this.usePartnerResourceAttr && PartnerConfigHelper.get(getContext()).isAvailable();
    }

    public boolean useFullDynamicColor() {
        if (shouldApplyDynamicColor()) {
            return this.useFullDynamicColorAttr || PartnerConfigHelper.isSetupWizardFullDynamicColorEnabled(getContext());
        }
        return false;
    }
}
