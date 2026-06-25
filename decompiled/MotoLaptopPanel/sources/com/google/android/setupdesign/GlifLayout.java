package com.google.android.setupdesign;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.window.embedding.ActivityEmbeddingController;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.logging.MetricKey;
import com.google.android.setupcompat.logging.SetupMetricsLogger;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.StatusBarMixin;
import com.google.android.setupcompat.template.SystemNavBarMixin;
import com.google.android.setupcompat.util.ForceTwoPaneHelper;
import com.google.android.setupcompat.util.KeyboardHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.template.DescriptionMixin;
import com.google.android.setupdesign.template.FloatingBackButtonMixin;
import com.google.android.setupdesign.template.HeaderMixin;
import com.google.android.setupdesign.template.IconMixin;
import com.google.android.setupdesign.template.IllustrationProgressMixin;
import com.google.android.setupdesign.template.ProfileMixin;
import com.google.android.setupdesign.template.ProgressBarMixin;
import com.google.android.setupdesign.template.RequireScrollMixin;
import com.google.android.setupdesign.template.ScrollViewScrollHandlingDelegate;
import com.google.android.setupdesign.util.DescriptionStyler;
import com.google.android.setupdesign.util.LayoutStyler;

/* JADX INFO: loaded from: classes.dex */
public class GlifLayout extends PartnerCustomizationLayout {
    private static final Logger LOG = new Logger(GlifLayout.class);
    private boolean applyPartnerHeavyThemeResource;
    private ColorStateList backgroundBaseColor;
    private boolean backgroundPatterned;
    private ViewTreeObserver.OnScrollChangedListener onScrollChangedListener;
    private ColorStateList primaryColor;

    public GlifLayout(Context context) {
        this(context, 0, 0);
    }

    public GlifLayout(Context context, int i) {
        this(context, i, 0);
    }

    public GlifLayout(Context context, int i, int i2) {
        super(context, i, i2);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        this.onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.google.android.setupdesign.GlifLayout.1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                if (GlifLayout.this.getScrollView() != null) {
                    GlifLayout.this.onScrolling(!r0.canScrollVertically(1));
                }
            }
        };
        init(null, R$attr.sudLayoutTheme);
    }

    public GlifLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        this.onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.google.android.setupdesign.GlifLayout.1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                if (GlifLayout.this.getScrollView() != null) {
                    GlifLayout.this.onScrolling(!r0.canScrollVertically(1));
                }
            }
        };
        init(attributeSet, R$attr.sudLayoutTheme);
    }

    public GlifLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        this.onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.google.android.setupdesign.GlifLayout.1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                if (GlifLayout.this.getScrollView() != null) {
                    GlifLayout.this.onScrolling(!r0.canScrollVertically(1));
                }
            }
        };
        init(attributeSet, i);
    }

    private void init(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SudGlifLayout, i, 0);
        this.applyPartnerHeavyThemeResource = shouldApplyPartnerResource() && typedArrayObtainStyledAttributes.getBoolean(R$styleable.SudGlifLayout_sudUsePartnerHeavyTheme, false);
        registerMixin(HeaderMixin.class, new HeaderMixin(this, attributeSet, i));
        registerMixin(DescriptionMixin.class, new DescriptionMixin(this, attributeSet, i));
        registerMixin(IconMixin.class, new IconMixin(this, attributeSet, i));
        registerMixin(ProfileMixin.class, new ProfileMixin(this, attributeSet, i));
        registerMixin(ProgressBarMixin.class, new ProgressBarMixin(this, attributeSet, i));
        registerMixin(IllustrationProgressMixin.class, new IllustrationProgressMixin(this));
        registerMixin(FloatingBackButtonMixin.class, new FloatingBackButtonMixin(this, attributeSet, i));
        RequireScrollMixin requireScrollMixin = new RequireScrollMixin(this);
        registerMixin(RequireScrollMixin.class, requireScrollMixin);
        ScrollView scrollView = getScrollView();
        if (scrollView != null) {
            requireScrollMixin.setScrollHandlingDelegate(new ScrollViewScrollHandlingDelegate(requireScrollMixin, scrollView));
        }
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(R$styleable.SudGlifLayout_sudColorPrimary);
        if (colorStateList != null) {
            setPrimaryColor(colorStateList);
        }
        if (shouldApplyPartnerHeavyThemeResource()) {
            updateContentBackgroundColorWithPartnerConfig();
        }
        View viewFindManagedViewById = findManagedViewById(R$id.sud_layout_content);
        if (viewFindManagedViewById != null) {
            if (shouldApplyPartnerResource()) {
                LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(viewFindManagedViewById);
            }
            tryApplyPartnerCustomizationContentPaddingTopStyle(viewFindManagedViewById);
        }
        updateLandscapeMiddleHorizontalSpacing();
        updateViewFocusable();
        setBackgroundBaseColor(typedArrayObtainStyledAttributes.getColorStateList(R$styleable.SudGlifLayout_sudBackgroundBaseColor));
        setBackgroundPatterned(typedArrayObtainStyledAttributes.getBoolean(R$styleable.SudGlifLayout_sudBackgroundPatterned, true));
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SudGlifLayout_sudStickyHeader, 0);
        if (resourceId != 0) {
            inflateStickyHeader(resourceId);
        }
        if (PartnerConfigHelper.isGlifExpressiveEnabled(getContext())) {
            initScrollingListener();
        }
        initBackButton();
        typedArrayObtainStyledAttributes.recycle();
    }

    private boolean isContentScrollable(ScrollView scrollView) {
        View childAt = scrollView.getChildAt(0);
        return childAt != null && childAt.getHeight() > scrollView.getHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initScrollingListener$0(ScrollView scrollView) {
        if (isContentScrollable(scrollView)) {
            onScrolling(false);
        }
    }

    private void tryApplyPartnerCustomizationStyleToShortDescription() {
        TextView textView = (TextView) findManagedViewById(R$id.sud_layout_description);
        if (textView != null) {
            if (this.applyPartnerHeavyThemeResource) {
                DescriptionStyler.applyPartnerCustomizationHeavyStyle(textView);
            } else if (shouldApplyPartnerResource()) {
                DescriptionStyler.applyPartnerCustomizationLightStyle(textView);
            }
        }
    }

    private void updateBackground() {
        int defaultColor;
        if (findManagedViewById(R$id.suc_layout_status) != null) {
            ColorStateList colorStateList = this.backgroundBaseColor;
            if (colorStateList != null) {
                defaultColor = colorStateList.getDefaultColor();
            } else {
                ColorStateList colorStateList2 = this.primaryColor;
                defaultColor = colorStateList2 != null ? colorStateList2.getDefaultColor() : 0;
            }
            ((StatusBarMixin) getMixin(StatusBarMixin.class)).setStatusBarBackground(this.backgroundPatterned ? new GlifPatternDrawable(defaultColor) : new ColorDrawable(defaultColor));
        }
    }

    private void updateContentBackgroundColorWithPartnerConfig() {
        if (useFullDynamicColor()) {
            return;
        }
        getRootView().setBackgroundColor(PartnerConfigHelper.get(getContext()).getColor(getContext(), PartnerConfig.CONFIG_LAYOUT_BACKGROUND_COLOR));
    }

    private void updateViewFocusable() {
        if (KeyboardHelper.isKeyboardFocusEnhancementEnabled(getContext())) {
            View viewFindManagedViewById = findManagedViewById(R$id.sud_header_scroll_view);
            if (viewFindManagedViewById != null) {
                viewFindManagedViewById.setFocusable(false);
            }
            View viewFindManagedViewById2 = findManagedViewById(R$id.sud_scroll_view);
            if (viewFindManagedViewById2 != null) {
                viewFindManagedViewById2.setFocusable(false);
            }
        }
    }

    @Override // com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    protected ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R$id.sud_layout_content;
        }
        return super.findContainer(i);
    }

    public int getFooterBackgroundColorFromStyle() {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R$attr.sudFooterBackgroundColor, typedValue, true);
        return typedValue.data;
    }

    public ScrollView getScrollView() {
        View viewFindManagedViewById = findManagedViewById(R$id.sud_scroll_view);
        if (viewFindManagedViewById instanceof ScrollView) {
            return (ScrollView) viewFindManagedViewById;
        }
        return null;
    }

    public View inflateStickyHeader(int i) {
        ViewStub viewStub = (ViewStub) findManagedViewById(R$id.sud_layout_sticky_header);
        viewStub.setLayoutResource(i);
        return viewStub.inflate();
    }

    protected void initBackButton() {
        if (!PartnerConfigHelper.isGlifExpressiveEnabled(getContext())) {
            LOG.atDebug("isGlifExpressiveEnabled is false");
            return;
        }
        final Activity activityLookupActivityFromContext = PartnerCustomizationLayout.lookupActivityFromContext(getContext());
        FloatingBackButtonMixin floatingBackButtonMixin = (FloatingBackButtonMixin) getMixin(FloatingBackButtonMixin.class);
        if (floatingBackButtonMixin == null) {
            LOG.w("FloatingBackButtonMixin button is null");
        } else {
            floatingBackButtonMixin.setVisibility(0);
            floatingBackButtonMixin.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.setupdesign.GlifLayout$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    activityLookupActivityFromContext.onBackPressed();
                }
            });
        }
    }

    protected void initScrollingListener() {
        final ScrollView scrollView = getScrollView();
        if (scrollView != null) {
            scrollView.getViewTreeObserver().addOnScrollChangedListener(this.onScrollChangedListener);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.google.android.setupdesign.GlifLayout$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$initScrollingListener$0(scrollView);
                }
            }, 100L);
        }
    }

    protected boolean isEmbeddedActivityOnePaneEnabled(Context context) {
        boolean zIsEmbeddedActivityOnePaneEnabled = PartnerConfigHelper.isEmbeddedActivityOnePaneEnabled(context);
        boolean zIsActivityEmbedded = ActivityEmbeddingController.getInstance(context).isActivityEmbedded(PartnerCustomizationLayout.lookupActivityFromContext(context));
        LOG.atVerbose("isEmbeddedActivityOnePaneEnabled = " + zIsEmbeddedActivityOnePaneEnabled + "; isActivityEmbedded = " + zIsActivityEmbedded);
        return zIsEmbeddedActivityOnePaneEnabled && zIsActivityEmbedded;
    }

    protected boolean isGlifExpressiveEnabled() {
        return PartnerConfigHelper.isGlifExpressiveEnabled(getContext());
    }

    @Override // com.google.android.setupcompat.PartnerCustomizationLayout, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (WizardManagerHelper.isAnySetupWizard(this.activity.getIntent()) && PartnerConfigHelper.isGlifExpressiveEnabled(getContext())) {
            FloatingBackButtonMixin floatingBackButtonMixin = (FloatingBackButtonMixin) getMixin(FloatingBackButtonMixin.class);
            CustomEvent customEventCreate = CustomEvent.create(MetricKey.get("SetupDesignMetrics", this.activity), floatingBackButtonMixin != null ? floatingBackButtonMixin.getMetrics() : PersistableBundle.EMPTY);
            SetupMetricsLogger.logCustomEvent(getContext(), customEventCreate);
            LOG.atVerbose("SetupDesignMetrics=" + CustomEvent.toBundle(customEventCreate));
        }
        ScrollView scrollView = getScrollView();
        if (scrollView != null) {
            scrollView.getViewTreeObserver().removeOnScrollChangedListener(this.onScrollChangedListener);
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        ((IconMixin) getMixin(IconMixin.class)).tryApplyPartnerCustomizationStyle();
        ((HeaderMixin) getMixin(HeaderMixin.class)).tryApplyPartnerCustomizationStyle();
        ((DescriptionMixin) getMixin(DescriptionMixin.class)).tryApplyPartnerCustomizationStyle();
        ((ProgressBarMixin) getMixin(ProgressBarMixin.class)).tryApplyPartnerCustomizationStyle();
        ((ProfileMixin) getMixin(ProfileMixin.class)).tryApplyPartnerCustomizationStyle();
        ((FloatingBackButtonMixin) getMixin(FloatingBackButtonMixin.class)).tryApplyPartnerCustomizationStyle();
        tryApplyPartnerCustomizationStyleToShortDescription();
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    protected View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = R$layout.sud_glif_template;
            if (isEmbeddedActivityOnePaneEnabled(getContext())) {
                i = isGlifExpressiveEnabled() ? R$layout.sud_glif_expressive_embedded_template : R$layout.sud_glif_embedded_template;
            } else if (isGlifExpressiveEnabled()) {
                i = R$layout.sud_glif_expressive_template;
            } else if (ForceTwoPaneHelper.isForceTwoPaneEnable(getContext())) {
                i = R$layout.sud_glif_template_two_pane;
            }
        }
        return inflateTemplate(layoutInflater, R$style.SudThemeGlif_Light, i);
    }

    protected void onScrolling(boolean z) {
        LinearLayout buttonContainer;
        FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
        SystemNavBarMixin systemNavBarMixin = (SystemNavBarMixin) getMixin(SystemNavBarMixin.class);
        if (footerBarMixin == null || (buttonContainer = footerBarMixin.getButtonContainer()) == null) {
            return;
        }
        if (z) {
            buttonContainer.setBackgroundColor(0);
            if (systemNavBarMixin != null) {
                systemNavBarMixin.setSystemNavBarBackground(0);
                return;
            }
            return;
        }
        buttonContainer.setBackgroundColor(getFooterBackgroundColorFromStyle());
        if (systemNavBarMixin != null) {
            systemNavBarMixin.setSystemNavBarBackground(getFooterBackgroundColorFromStyle());
        }
    }

    public void setBackgroundBaseColor(ColorStateList colorStateList) {
        this.backgroundBaseColor = colorStateList;
        updateBackground();
    }

    public void setBackgroundPatterned(boolean z) {
        this.backgroundPatterned = z;
        updateBackground();
    }

    public void setPrimaryColor(ColorStateList colorStateList) {
        this.primaryColor = colorStateList;
        updateBackground();
        ((ProgressBarMixin) getMixin(ProgressBarMixin.class)).setColor(colorStateList);
    }

    public boolean shouldApplyPartnerHeavyThemeResource() {
        if (this.applyPartnerHeavyThemeResource) {
            return true;
        }
        return shouldApplyPartnerResource() && PartnerConfigHelper.shouldApplyExtendedPartnerConfig(getContext());
    }

    protected void tryApplyPartnerCustomizationContentPaddingTopStyle(View view) {
        int dimension;
        Context context = view.getContext();
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_CONTENT_PADDING_TOP;
        boolean zIsPartnerConfigAvailable = partnerConfigHelper.isPartnerConfigAvailable(partnerConfig);
        if (shouldApplyPartnerResource() && zIsPartnerConfigAvailable && (dimension = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig)) != view.getPaddingTop()) {
            view.setPadding(view.getPaddingStart(), dimension, view.getPaddingEnd(), view.getPaddingBottom());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void updateLandscapeMiddleHorizontalSpacing() {
        /*
            Method dump skipped, instruction units count: 230
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupdesign.GlifLayout.updateLandscapeMiddleHorizontalSpacing():void");
    }
}
