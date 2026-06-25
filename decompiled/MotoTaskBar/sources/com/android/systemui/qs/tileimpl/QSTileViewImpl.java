package com.android.systemui.qs.tileimpl;

import android.animation.ArgbEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.android.app.tracing.TraceUtilsKt;
import com.android.settingslib.Utils;
import com.android.systemui.Flags;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.haptics.qs.QSLongPressEffect;
import com.android.systemui.haptics.qs.QSLongPressEffectViewBinder;
import com.android.systemui.qs.QSTile;
import com.android.systemui.qs.QSTileView;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$string;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.motorola.taskbar.R$attr;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.DisposableHandle;

/* JADX INFO: compiled from: QSTileViewImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public class QSTileViewImpl extends QSTileView implements HeightOverrideable {
    public static final Companion Companion = new Companion(null);
    private String accessibilityClass;
    private Drawable backgroundBaseDrawable;
    private int backgroundColor;
    private LayerDrawable backgroundDrawable;
    private int backgroundOverlayColor;
    private Drawable backgroundOverlayDrawable;
    private ImageView chevronView;
    private final boolean collapsed;
    private final int colorActive;
    private final ArgbEvaluator colorEvaluator;
    private final int colorInactive;
    private final int colorLabelActive;
    private final int colorLabelInactive;
    private final int colorLabelUnavailable;
    private final int colorSecondaryLabelActive;
    private final int colorSecondaryLabelInactive;
    private final int colorSecondaryLabelUnavailable;
    private final int colorUnavailable;
    private ImageView customDrawableView;
    private QSLongPressProperties finalLongPressProperties;
    private int heightOverride;
    private final QSIconViewImpl icon;
    private QSLongPressProperties initialLongPressProperties;
    private TextView label;
    private IgnorableChildLinearLayout labelContainer;
    private boolean lastDisabledByPolicy;
    private int lastIconTint;
    private int lastState;
    private CharSequence lastStateDescription;
    private final int[] locInScreen;
    private final QSLongPressEffect longPressEffect;
    private DisposableHandle longPressEffectHandle;
    private final int overlayColorActive;
    private final int overlayColorInactive;
    private int position;
    private LayerDrawable qsTileBackground;
    protected TextView secondaryLabel;
    private boolean showRippleEffect;
    protected ViewGroup sideView;
    private final ValueAnimator singleAnimator;
    private float squishinessFraction;
    private CharSequence stateDescriptionDeltas;
    private boolean tileState;

    /* JADX INFO: compiled from: QSTileViewImpl.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getTILE_STATE_RES_PREFIX$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core$annotations() {
        }
    }

    /* JADX INFO: compiled from: QSTileViewImpl.kt */
    public final class StateChangeRunnable implements Runnable {
        private final QSTile.State state;
        final /* synthetic */ QSTileViewImpl this$0;

        public StateChangeRunnable(QSTileViewImpl qSTileViewImpl, QSTile.State state) {
            state.getClass();
            this.this$0 = qSTileViewImpl;
            this.state = state;
        }

        public boolean equals(Object obj) {
            return obj instanceof StateChangeRunnable;
        }

        public int hashCode() {
            return Reflection.getOrCreateKotlinClass(StateChangeRunnable.class).hashCode();
        }

        @Override // java.lang.Runnable
        public void run() {
            QSTileViewImpl qSTileViewImpl = this.this$0;
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("QSTileViewImpl#handleStateChanged");
            }
            try {
                qSTileViewImpl.handleStateChanged(this.state);
                Unit unit = Unit.INSTANCE;
            } finally {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSTileViewImpl(Context context, boolean z, QSLongPressEffect qSLongPressEffect) {
        super(context);
        context.getClass();
        this.collapsed = z;
        this.longPressEffect = qSLongPressEffect;
        QSIconViewImpl qSIconViewImpl = new QSIconViewImpl(context);
        this.icon = qSIconViewImpl;
        this.position = -1;
        this.heightOverride = -1;
        this.squishinessFraction = 1.0f;
        this.colorActive = Utils.getColorAttrDefaultColor(context, R$attr.shadeActive);
        this.colorInactive = Utils.getColorAttrDefaultColor(context, R$attr.shadeInactive);
        this.colorUnavailable = Utils.getColorAttrDefaultColor(context, R$attr.shadeDisabled);
        int i = R$attr.onShadeActive;
        this.overlayColorActive = Utils.applyAlpha(0.11f, Utils.getColorAttrDefaultColor(context, i));
        int i2 = R$attr.onShadeInactive;
        this.overlayColorInactive = Utils.applyAlpha(0.08f, Utils.getColorAttrDefaultColor(context, i2));
        this.colorLabelActive = Utils.getColorAttrDefaultColor(context, i);
        this.colorLabelInactive = Utils.getColorAttrDefaultColor(context, i2);
        int i3 = R$attr.outline;
        this.colorLabelUnavailable = Utils.getColorAttrDefaultColor(context, i3);
        this.colorSecondaryLabelActive = Utils.getColorAttrDefaultColor(context, R$attr.onShadeActiveVariant);
        this.colorSecondaryLabelInactive = Utils.getColorAttrDefaultColor(context, R$attr.onShadeInactiveVariant);
        this.colorSecondaryLabelUnavailable = Utils.getColorAttrDefaultColor(context, i3);
        this.showRippleEffect = true;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(350L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$singleAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                valueAnimator2.getClass();
                QSTileViewImpl qSTileViewImpl = this.this$0;
                Object animatedValue = valueAnimator2.getAnimatedValue("background");
                animatedValue.getClass();
                int iIntValue = ((Integer) animatedValue).intValue();
                Object animatedValue2 = valueAnimator2.getAnimatedValue("label");
                animatedValue2.getClass();
                int iIntValue2 = ((Integer) animatedValue2).intValue();
                Object animatedValue3 = valueAnimator2.getAnimatedValue("secondaryLabel");
                animatedValue3.getClass();
                int iIntValue3 = ((Integer) animatedValue3).intValue();
                Object animatedValue4 = valueAnimator2.getAnimatedValue("chevron");
                animatedValue4.getClass();
                int iIntValue4 = ((Integer) animatedValue4).intValue();
                Object animatedValue5 = valueAnimator2.getAnimatedValue("overlay");
                animatedValue5.getClass();
                qSTileViewImpl.setAllColors(iIntValue, iIntValue2, iIntValue3, iIntValue4, ((Integer) animatedValue5).intValue());
            }
        });
        this.singleAnimator = valueAnimator;
        this.lastState = -1;
        this.locInScreen = new int[2];
        this.colorEvaluator = ArgbEvaluator.getInstance();
        if (!getContext().getTheme().resolveAttribute(R$attr.isQsTheme, new TypedValue(), true)) {
            throw new IllegalStateException("QSViewImpl must be inflated with a theme that contains Theme.SystemUI.QuickSettings");
        }
        setId(LinearLayout.generateViewId());
        setOrientation(0);
        setGravity(8388627);
        setImportantForAccessibility(1);
        setClipChildren(false);
        setClipToPadding(false);
        setFocusable(true);
        setBackground(createTileBackground());
        setColor(getBackgroundColorForState$default(this, 2, false, 2, null));
        int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.qs_tile_padding);
        setPaddingRelative(getResources().getDimensionPixelSize(R$dimen.qs_tile_start_padding), dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R$dimen.qs_icon_size);
        addView(qSIconViewImpl, new LinearLayout.LayoutParams(dimensionPixelSize2, dimensionPixelSize2));
        createAndAddLabels();
        createAndAddSideView();
    }

    private final void changeCornerRadius(float f) {
        LayerDrawable layerDrawable = this.backgroundDrawable;
        if (layerDrawable == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundDrawable");
            layerDrawable = null;
        }
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        for (int i = 0; i < numberOfLayers; i++) {
            LayerDrawable layerDrawable2 = this.backgroundDrawable;
            if (layerDrawable2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("backgroundDrawable");
                layerDrawable2 = null;
            }
            Drawable drawable = layerDrawable2.getDrawable(i);
            if (drawable instanceof GradientDrawable) {
                ((GradientDrawable) drawable).setCornerRadius(f);
            }
        }
    }

    private final void createAndAddLabels() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R$layout.qs_tile_label, (ViewGroup) this, false);
        viewInflate.getClass();
        IgnorableChildLinearLayout ignorableChildLinearLayout = (IgnorableChildLinearLayout) viewInflate;
        this.labelContainer = ignorableChildLinearLayout;
        this.label = (TextView) ignorableChildLinearLayout.requireViewById(R$id.tile_label);
        IgnorableChildLinearLayout ignorableChildLinearLayout2 = this.labelContainer;
        IgnorableChildLinearLayout ignorableChildLinearLayout3 = null;
        if (ignorableChildLinearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("labelContainer");
            ignorableChildLinearLayout2 = null;
        }
        setSecondaryLabel((TextView) ignorableChildLinearLayout2.requireViewById(R$id.app_label));
        if (this.collapsed) {
            IgnorableChildLinearLayout ignorableChildLinearLayout4 = this.labelContainer;
            if (ignorableChildLinearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("labelContainer");
                ignorableChildLinearLayout4 = null;
            }
            ignorableChildLinearLayout4.setIgnoreLastView(true);
            IgnorableChildLinearLayout ignorableChildLinearLayout5 = this.labelContainer;
            if (ignorableChildLinearLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("labelContainer");
                ignorableChildLinearLayout5 = null;
            }
            ignorableChildLinearLayout5.setForceUnspecifiedMeasure(true);
            getSecondaryLabel().setAlpha(0.0f);
        }
        setLabelColor(getLabelColorForState$default(this, 2, false, 2, null));
        setSecondaryLabelColor(getSecondaryLabelColorForState$default(this, 2, false, 2, null));
        IgnorableChildLinearLayout ignorableChildLinearLayout6 = this.labelContainer;
        if (ignorableChildLinearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("labelContainer");
        } else {
            ignorableChildLinearLayout3 = ignorableChildLinearLayout6;
        }
        addView(ignorableChildLinearLayout3);
    }

    private final void createAndAddSideView() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R$layout.qs_tile_side_icon, (ViewGroup) this, false);
        viewInflate.getClass();
        setSideView((ViewGroup) viewInflate);
        this.customDrawableView = (ImageView) getSideView().requireViewById(R$id.customDrawable);
        this.chevronView = (ImageView) getSideView().requireViewById(R$id.chevron);
        setChevronColor(getChevronColorForState$default(this, 2, false, 2, null));
        addView(getSideView());
    }

    private final Drawable createTileBackground() {
        LayerDrawable layerDrawable;
        if (Flags.qsTileFocusState()) {
            Drawable drawable = ((LinearLayout) this).mContext.getDrawable(R$drawable.qs_tile_background_flagged);
            drawable.getClass();
            layerDrawable = (LayerDrawable) drawable;
        } else {
            Drawable drawable2 = ((LinearLayout) this).mContext.getDrawable(R$drawable.qs_tile_background);
            drawable2.getClass();
            layerDrawable = (RippleDrawable) drawable2;
        }
        this.qsTileBackground = layerDrawable;
        Drawable drawableFindDrawableByLayerId = layerDrawable.findDrawableByLayerId(R$id.background);
        drawableFindDrawableByLayerId.getClass();
        LayerDrawable layerDrawable2 = (LayerDrawable) drawableFindDrawableByLayerId;
        this.backgroundDrawable = layerDrawable2;
        this.backgroundBaseDrawable = layerDrawable2.findDrawableByLayerId(R$id.qs_tile_background_base);
        LayerDrawable layerDrawable3 = this.backgroundDrawable;
        if (layerDrawable3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundDrawable");
            layerDrawable3 = null;
        }
        Drawable drawableFindDrawableByLayerId2 = layerDrawable3.findDrawableByLayerId(R$id.qs_tile_background_overlay);
        this.backgroundOverlayDrawable = drawableFindDrawableByLayerId2;
        if (drawableFindDrawableByLayerId2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundOverlayDrawable");
            drawableFindDrawableByLayerId2 = null;
        }
        drawableFindDrawableByLayerId2.mutate().setTintMode(PorterDuff.Mode.SRC);
        LayerDrawable layerDrawable4 = this.qsTileBackground;
        if (layerDrawable4 != null) {
            return layerDrawable4;
        }
        Intrinsics.throwUninitializedPropertyAccessException("qsTileBackground");
        return null;
    }

    private final int getBackgroundColorForState(int i, boolean z) {
        if (i == 0 || z) {
            return this.colorUnavailable;
        }
        if (i == 2) {
            return this.colorActive;
        }
        if (i == 1) {
            return this.colorInactive;
        }
        Log.e("QSTileViewImpl", "Invalid state " + i);
        return 0;
    }

    static /* synthetic */ int getBackgroundColorForState$default(QSTileViewImpl qSTileViewImpl, int i, boolean z, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getBackgroundColorForState");
        }
        if ((i2 & 2) != 0) {
            z = false;
        }
        return qSTileViewImpl.getBackgroundColorForState(i, z);
    }

    private final int getChevronColorForState(int i, boolean z) {
        return getSecondaryLabelColorForState(i, z);
    }

    static /* synthetic */ int getChevronColorForState$default(QSTileViewImpl qSTileViewImpl, int i, boolean z, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getChevronColorForState");
        }
        if ((i2 & 2) != 0) {
            z = false;
        }
        return qSTileViewImpl.getChevronColorForState(i, z);
    }

    private final int getLabelColorForState(int i, boolean z) {
        if (i == 0 || z) {
            return this.colorLabelUnavailable;
        }
        if (i == 2) {
            return this.colorLabelActive;
        }
        if (i == 1) {
            return this.colorLabelInactive;
        }
        Log.e("QSTileViewImpl", "Invalid state " + i);
        return 0;
    }

    static /* synthetic */ int getLabelColorForState$default(QSTileViewImpl qSTileViewImpl, int i, boolean z, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getLabelColorForState");
        }
        if ((i2 & 2) != 0) {
            z = false;
        }
        return qSTileViewImpl.getLabelColorForState(i, z);
    }

    private final int getOverlayColorForState(int i) {
        if (i == 1) {
            return this.overlayColorInactive;
        }
        if (i != 2) {
            return 0;
        }
        return this.overlayColorActive;
    }

    private final int getSecondaryLabelColorForState(int i, boolean z) {
        if (i == 0 || z) {
            return this.colorSecondaryLabelUnavailable;
        }
        if (i == 2) {
            return this.colorSecondaryLabelActive;
        }
        if (i == 1) {
            return this.colorSecondaryLabelInactive;
        }
        Log.e("QSTileViewImpl", "Invalid state " + i);
        return 0;
    }

    static /* synthetic */ int getSecondaryLabelColorForState$default(QSTileViewImpl qSTileViewImpl, int i, boolean z, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getSecondaryLabelColorForState");
        }
        if ((i2 & 2) != 0) {
            z = false;
        }
        return qSTileViewImpl.getSecondaryLabelColorForState(i, z);
    }

    private final String getUnavailableText(String str) {
        String str2 = getResources().getStringArray(SubtitleArrayMapping.INSTANCE.getSubtitleId(str))[0];
        str2.getClass();
        return str2;
    }

    private final void init(View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        setOnClickListener(onClickListener);
        setOnLongClickListener(onLongClickListener);
    }

    private final void initializeLongPressProperties() {
        Resources resources = getResources();
        int i = R$dimen.qs_corner_radius;
        this.initialLongPressProperties = new QSLongPressProperties(1.0f, 1.0f, resources.getDimensionPixelSize(i), getBackgroundColorForState$default(this, this.lastState, false, 2, null), getLabelColorForState$default(this, this.lastState, false, 2, null), getSecondaryLabelColorForState$default(this, this.lastState, false, 2, null), getChevronColorForState$default(this, this.lastState, false, 2, null), getOverlayColorForState(this.lastState), this.lastIconTint);
        this.finalLongPressProperties = new QSLongPressProperties(1.1f, 1.2f, getResources().getDimensionPixelSize(i) - 20, getBackgroundColorForState$default(this, 2, false, 2, null), getLabelColorForState$default(this, 2, false, 2, null), getSecondaryLabelColorForState$default(this, 2, false, 2, null), getChevronColorForState$default(this, 2, false, 2, null), getOverlayColorForState(2), Utils.getColorAttrDefaultColor(getContext(), R$attr.onShadeActive));
    }

    private final float interpolateFloat(float f, float f2, float f3) {
        return f2 + (f * (f3 - f2));
    }

    private final void loadSideViewDrawableIfNecessary(QSTile.State state) {
        ImageView imageView = null;
        if (state.sideViewCustomDrawable != null) {
            ImageView imageView2 = this.customDrawableView;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("customDrawableView");
                imageView2 = null;
            }
            imageView2.setImageDrawable(state.sideViewCustomDrawable);
            ImageView imageView3 = this.customDrawableView;
            if (imageView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("customDrawableView");
                imageView3 = null;
            }
            imageView3.setVisibility(0);
            ImageView imageView4 = this.chevronView;
            if (imageView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("chevronView");
            } else {
                imageView = imageView4;
            }
            imageView.setVisibility(8);
            return;
        }
        if (!(state instanceof QSTile.AdapterState) || ((QSTile.AdapterState) state).forceExpandIcon) {
            ImageView imageView5 = this.customDrawableView;
            if (imageView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("customDrawableView");
                imageView5 = null;
            }
            imageView5.setImageDrawable(null);
            ImageView imageView6 = this.customDrawableView;
            if (imageView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("customDrawableView");
                imageView6 = null;
            }
            imageView6.setVisibility(8);
            ImageView imageView7 = this.chevronView;
            if (imageView7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("chevronView");
            } else {
                imageView = imageView7;
            }
            imageView.setVisibility(0);
            return;
        }
        ImageView imageView8 = this.customDrawableView;
        if (imageView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("customDrawableView");
            imageView8 = null;
        }
        imageView8.setImageDrawable(null);
        ImageView imageView9 = this.customDrawableView;
        if (imageView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("customDrawableView");
            imageView9 = null;
        }
        imageView9.setVisibility(8);
        ImageView imageView10 = this.chevronView;
        if (imageView10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("chevronView");
        } else {
            imageView = imageView10;
        }
        imageView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setAllColors(int i, int i2, int i3, int i4, int i5) {
        setColor(i);
        setLabelColor(i2);
        setSecondaryLabelColor(i3);
        setChevronColor(i4);
        setOverlayColor(i5);
    }

    private final void setChevronColor(int i) {
        ImageView imageView = this.chevronView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("chevronView");
            imageView = null;
        }
        imageView.setImageTintList(ColorStateList.valueOf(i));
    }

    private final void setColor(int i) {
        Drawable drawable = this.backgroundBaseDrawable;
        if (drawable == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundBaseDrawable");
            drawable = null;
        }
        drawable.mutate().setTint(i);
        this.backgroundColor = i;
    }

    private final void setLabelColor(int i) {
        TextView textView = this.label;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("label");
            textView = null;
        }
        textView.setTextColor(i);
    }

    private final void setOverlayColor(int i) {
        Drawable drawable = this.backgroundOverlayDrawable;
        if (drawable == null) {
            Intrinsics.throwUninitializedPropertyAccessException("backgroundOverlayDrawable");
            drawable = null;
        }
        drawable.setTint(i);
        this.backgroundOverlayColor = i;
    }

    private final void setSecondaryLabelColor(int i) {
        getSecondaryLabel().setTextColor(i);
    }

    private final void unbindLongPressEffect() {
        DisposableHandle disposableHandle = this.longPressEffectHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
        this.longPressEffectHandle = null;
    }

    private final void updateHeight() {
        if (getScaleX() != 1.0f || getScaleY() != 1.0f) {
            resetLongPressEffectProperties();
        }
        int heightOverride = getHeightOverride() != -1 ? getHeightOverride() : getMeasuredHeight();
        setBottom(getTop() + ((int) (heightOverride * QSTileViewImplKt.constrainSquishiness(getSquishinessFraction()))));
        setScrollY((heightOverride - getHeight()) / 2);
    }

    protected boolean animationsEnabled() {
        if (isShown() && getAlpha() == 1.0f) {
            getLocationOnScreen(this.locInScreen);
            if (this.locInScreen[1] >= (-getHeight())) {
                return true;
            }
        }
        return false;
    }

    public final List getCurrentColors$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        Integer numValueOf = Integer.valueOf(this.backgroundColor);
        TextView textView = this.label;
        ImageView imageView = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("label");
            textView = null;
        }
        Integer numValueOf2 = Integer.valueOf(textView.getCurrentTextColor());
        Integer numValueOf3 = Integer.valueOf(getSecondaryLabel().getCurrentTextColor());
        ImageView imageView2 = this.chevronView;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("chevronView");
        } else {
            imageView = imageView2;
        }
        ColorStateList imageTintList = imageView.getImageTintList();
        return CollectionsKt.listOf((Object[]) new Integer[]{numValueOf, numValueOf2, numValueOf3, Integer.valueOf(imageTintList != null ? imageTintList.getDefaultColor() : 0)});
    }

    public int getHeightOverride() {
        return this.heightOverride;
    }

    protected final TextView getSecondaryLabel() {
        TextView textView = this.secondaryLabel;
        if (textView != null) {
            return textView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("secondaryLabel");
        return null;
    }

    protected final ViewGroup getSideView() {
        ViewGroup viewGroup = this.sideView;
        if (viewGroup != null) {
            return viewGroup;
        }
        Intrinsics.throwUninitializedPropertyAccessException("sideView");
        return null;
    }

    public float getSquishinessFraction() {
        return this.squishinessFraction;
    }

    protected void handleStateChanged(QSTile.State state) {
        QSTileViewImpl qSTileViewImpl;
        QSLongPressEffect qSLongPressEffect;
        boolean z;
        state.getClass();
        boolean zAnimationsEnabled = animationsEnabled();
        setClickable(state.state != 0);
        setLongClickable(state.handlesLongClick);
        this.icon.setIcon(state, zAnimationsEnabled);
        setContentDescription(state.contentDescription);
        StringBuilder sb = new StringBuilder();
        CharSequence stateText = state.getStateText(SubtitleArrayMapping.INSTANCE.getSubtitleId(state.spec), getResources());
        state.secondaryLabel = state.getSecondaryLabel(stateText);
        if (!TextUtils.isEmpty(stateText)) {
            sb.append(stateText);
        }
        if (state.disabledByPolicy && state.state != 0) {
            sb.append(", ");
            sb.append(getUnavailableText(state.spec));
        }
        if (!TextUtils.isEmpty(state.stateDescription)) {
            sb.append(", ");
            sb.append(state.stateDescription);
            int i = this.lastState;
            if (i != -1 && state.state == i && !Intrinsics.areEqual(state.stateDescription, this.lastStateDescription)) {
                this.stateDescriptionDeltas = state.stateDescription;
            }
        }
        setStateDescription(sb.toString());
        this.lastStateDescription = state.stateDescription;
        this.accessibilityClass = state.state == 0 ? null : state.expandedAccessibilityClassName;
        if ((state instanceof QSTile.AdapterState) && this.tileState != (z = ((QSTile.AdapterState) state).value)) {
            this.tileState = z;
        }
        TextView textView = this.label;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("label");
            textView = null;
        }
        if (!Objects.equals(textView.getText(), state.label)) {
            TextView textView2 = this.label;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("label");
                textView2 = null;
            }
            textView2.setText(state.label);
        }
        if (!Objects.equals(getSecondaryLabel().getText(), state.secondaryLabel)) {
            getSecondaryLabel().setText(state.secondaryLabel);
            getSecondaryLabel().setVisibility(TextUtils.isEmpty(state.secondaryLabel) ? 8 : 0);
        }
        if (state.state == this.lastState && state.disabledByPolicy == this.lastDisabledByPolicy) {
            qSTileViewImpl = this;
        } else {
            this.singleAnimator.cancel();
            if (zAnimationsEnabled) {
                ValueAnimator valueAnimator = this.singleAnimator;
                PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[5];
                propertyValuesHolderArr[0] = QSTileViewImplKt.colorValuesHolder("background", this.backgroundColor, getBackgroundColorForState(state.state, state.disabledByPolicy));
                TextView textView3 = this.label;
                if (textView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("label");
                    textView3 = null;
                }
                propertyValuesHolderArr[1] = QSTileViewImplKt.colorValuesHolder("label", textView3.getCurrentTextColor(), getLabelColorForState(state.state, state.disabledByPolicy));
                propertyValuesHolderArr[2] = QSTileViewImplKt.colorValuesHolder("secondaryLabel", getSecondaryLabel().getCurrentTextColor(), getSecondaryLabelColorForState(state.state, state.disabledByPolicy));
                ImageView imageView = this.chevronView;
                if (imageView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("chevronView");
                    imageView = null;
                }
                ColorStateList imageTintList = imageView.getImageTintList();
                propertyValuesHolderArr[3] = QSTileViewImplKt.colorValuesHolder("chevron", imageTintList != null ? imageTintList.getDefaultColor() : 0, getChevronColorForState(state.state, state.disabledByPolicy));
                propertyValuesHolderArr[4] = QSTileViewImplKt.colorValuesHolder("overlay", this.backgroundOverlayColor, getOverlayColorForState(state.state));
                valueAnimator.setValues(propertyValuesHolderArr);
                this.singleAnimator.start();
                qSTileViewImpl = this;
            } else {
                qSTileViewImpl = this;
                qSTileViewImpl.setAllColors(getBackgroundColorForState(state.state, state.disabledByPolicy), getLabelColorForState(state.state, state.disabledByPolicy), getSecondaryLabelColorForState(state.state, state.disabledByPolicy), getChevronColorForState(state.state, state.disabledByPolicy), getOverlayColorForState(state.state));
            }
        }
        qSTileViewImpl.loadSideViewDrawableIfNecessary(state);
        TextView textView4 = qSTileViewImpl.label;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("label");
            textView4 = null;
        }
        textView4.setEnabled(!state.disabledByPolicy);
        qSTileViewImpl.lastState = state.state;
        qSTileViewImpl.lastDisabledByPolicy = state.disabledByPolicy;
        qSTileViewImpl.lastIconTint = qSTileViewImpl.icon.getColor(state);
        if (state.handlesLongClick && (qSLongPressEffect = qSTileViewImpl.longPressEffect) != null && qSLongPressEffect.initializeEffect(qSTileViewImpl.getLongPressEffectDuration())) {
            if (!qSTileViewImpl.isLongPressEffectBound()) {
                qSTileViewImpl.longPressEffectHandle = QSLongPressEffectViewBinder.INSTANCE.bind(qSTileViewImpl, qSTileViewImpl.longPressEffect, state.spec);
            }
            qSTileViewImpl.showRippleEffect = false;
            qSTileViewImpl.initializeLongPressProperties();
            return;
        }
        qSTileViewImpl.setOnTouchListener(null);
        qSTileViewImpl.unbindLongPressEffect();
        qSTileViewImpl.showRippleEffect = qSTileViewImpl.isClickable();
        qSTileViewImpl.initialLongPressProperties = null;
        qSTileViewImpl.finalLongPressProperties = null;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.qs.QSTileView
    public void init(final QSTile qSTile) {
        qSTile.getClass();
        init(new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl.init.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                qSTile.click(this);
            }
        }, new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl.init.2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                qSTile.longClick(this);
                return true;
            }
        });
        if (Flags.quickSettingsVisualHapticsLongpress()) {
            setHapticFeedbackEnabled(false);
        }
    }

    public final boolean isLongPressEffectBound() {
        return this.longPressEffectHandle != null;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.getClass();
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (!TextUtils.isEmpty(this.accessibilityClass)) {
            accessibilityEvent.setClassName(this.accessibilityClass);
        }
        if (accessibilityEvent.getContentChangeTypes() != 64 || this.stateDescriptionDeltas == null) {
            return;
        }
        accessibilityEvent.getText().add(this.stateDescriptionDeltas);
        this.stateDescriptionDeltas = null;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        String strValueOf;
        accessibilityNodeInfo.getClass();
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setSelected(false);
        TextView textView = null;
        if (TextUtils.isEmpty(getSecondaryLabel().getText())) {
            TextView textView2 = this.label;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("label");
            } else {
                textView = textView2;
            }
            strValueOf = String.valueOf(textView.getText());
        } else {
            TextView textView3 = this.label;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("label");
            } else {
                textView = textView3;
            }
            strValueOf = ((Object) textView.getText()) + ", " + ((Object) getSecondaryLabel().getText());
        }
        accessibilityNodeInfo.setText(strValueOf);
        if (this.lastDisabledByPolicy) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), getResources().getString(R$string.accessibility_tile_disabled_by_policy_action_description)));
        }
        if (!TextUtils.isEmpty(this.accessibilityClass)) {
            accessibilityNodeInfo.setClassName(this.lastDisabledByPolicy ? Button.class.getName() : this.accessibilityClass);
            if (Intrinsics.areEqual(Switch.class.getName(), this.accessibilityClass)) {
                accessibilityNodeInfo.setChecked(this.tileState);
                accessibilityNodeInfo.setCheckable(true);
                if (isLongClickable()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK.getId(), getResources().getString(com.motorola.taskbar.R$string.accessibility_long_click_tile)));
                }
            }
        }
        if (this.position != -1) {
            accessibilityNodeInfo.setCollectionItemInfo(new AccessibilityNodeInfo.CollectionItemInfo(this.position, 1, 0, 1, false));
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateHeight();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        Trace.endSection();
    }

    @Override // com.android.systemui.qs.QSTileView
    public void onStateChanged(QSTile.State state) {
        state.getClass();
        QSTile.State stateCopy = state.copy();
        stateCopy.getClass();
        StateChangeRunnable stateChangeRunnable = new StateChangeRunnable(this, stateCopy);
        removeCallbacks(stateChangeRunnable);
        post(stateChangeRunnable);
    }

    public final void resetLongPressEffectProperties() {
        setScaleY(1.0f);
        setScaleX(1.0f);
        for (View view : ConvenienceExtensionsKt.getChildren(this)) {
            view.setScaleY(1.0f);
            view.setScaleX(1.0f);
        }
        changeCornerRadius(getResources().getDimensionPixelSize(R$dimen.qs_corner_radius));
        setAllColors(getBackgroundColorForState(this.lastState, this.lastDisabledByPolicy), getLabelColorForState(this.lastState, this.lastDisabledByPolicy), getSecondaryLabelColorForState(this.lastState, this.lastDisabledByPolicy), getChevronColorForState(this.lastState, this.lastDisabledByPolicy), getOverlayColorForState(this.lastState));
        QSIconViewImpl qSIconViewImpl = this.icon;
        View view2 = qSIconViewImpl.mIcon;
        view2.getClass();
        qSIconViewImpl.setTint((ImageView) view2, this.lastIconTint);
    }

    @Override // android.view.View
    public void setClickable(boolean z) {
        LayerDrawable layerDrawable;
        super.setClickable(z);
        if (Flags.qsTileFocusState()) {
            return;
        }
        LayerDrawable layerDrawable2 = null;
        if (z && this.showRippleEffect) {
            layerDrawable = this.qsTileBackground;
            if (layerDrawable == null) {
                Intrinsics.throwUninitializedPropertyAccessException("qsTileBackground");
                layerDrawable = null;
            }
            LayerDrawable layerDrawable3 = this.backgroundDrawable;
            if (layerDrawable3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("backgroundDrawable");
            } else {
                layerDrawable2 = layerDrawable3;
            }
            layerDrawable2.setCallback(layerDrawable);
        } else {
            LayerDrawable layerDrawable4 = this.backgroundDrawable;
            if (layerDrawable4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("backgroundDrawable");
            } else {
                layerDrawable2 = layerDrawable4;
            }
            layerDrawable = layerDrawable2;
        }
        setBackground(layerDrawable);
    }

    @Override // com.android.systemui.qs.QSTileView
    public void setPosition(int i) {
        this.position = i;
    }

    protected final void setSecondaryLabel(TextView textView) {
        textView.getClass();
        this.secondaryLabel = textView;
    }

    protected final void setSideView(ViewGroup viewGroup) {
        viewGroup.getClass();
        this.sideView = viewGroup;
    }

    @Override // com.android.systemui.qs.tileimpl.HeightOverrideable
    public void setSquishinessFraction(float f) {
        if (this.squishinessFraction == f) {
            return;
        }
        this.squishinessFraction = f;
        updateHeight();
    }

    @Override // android.view.View
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        int[] iArr = this.locInScreen;
        sb.append("locInScreen=(" + iArr[0] + ", " + iArr[1] + ")");
        QSIconViewImpl qSIconViewImpl = this.icon;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(", iconView=");
        sb2.append(qSIconViewImpl);
        sb.append(sb2.toString());
        sb.append(", tileState=" + this.tileState);
        sb.append("]");
        String string = sb.toString();
        string.getClass();
        return string;
    }

    @Override // com.android.systemui.qs.QSTileView
    public View updateAccessibilityOrder(View view) {
        setAccessibilityTraversalAfter(view != null ? view.getId() : 0);
        return this;
    }

    public final void updateLongPressEffectProperties(float f) {
        if (!isLongClickable() || this.longPressEffect == null) {
            return;
        }
        ArgbEvaluator argbEvaluator = this.colorEvaluator;
        QSLongPressProperties qSLongPressProperties = this.initialLongPressProperties;
        Integer numValueOf = Integer.valueOf(qSLongPressProperties != null ? qSLongPressProperties.getBackgroundColor() : 0);
        QSLongPressProperties qSLongPressProperties2 = this.finalLongPressProperties;
        Object objEvaluate = argbEvaluator.evaluate(f, numValueOf, Integer.valueOf(qSLongPressProperties2 != null ? qSLongPressProperties2.getBackgroundColor() : 0));
        objEvaluate.getClass();
        int iIntValue = ((Integer) objEvaluate).intValue();
        ArgbEvaluator argbEvaluator2 = this.colorEvaluator;
        QSLongPressProperties qSLongPressProperties3 = this.initialLongPressProperties;
        Integer numValueOf2 = Integer.valueOf(qSLongPressProperties3 != null ? qSLongPressProperties3.getLabelColor() : 0);
        QSLongPressProperties qSLongPressProperties4 = this.finalLongPressProperties;
        Object objEvaluate2 = argbEvaluator2.evaluate(f, numValueOf2, Integer.valueOf(qSLongPressProperties4 != null ? qSLongPressProperties4.getLabelColor() : 0));
        objEvaluate2.getClass();
        int iIntValue2 = ((Integer) objEvaluate2).intValue();
        ArgbEvaluator argbEvaluator3 = this.colorEvaluator;
        QSLongPressProperties qSLongPressProperties5 = this.initialLongPressProperties;
        Integer numValueOf3 = Integer.valueOf(qSLongPressProperties5 != null ? qSLongPressProperties5.getSecondaryLabelColor() : 0);
        QSLongPressProperties qSLongPressProperties6 = this.finalLongPressProperties;
        Object objEvaluate3 = argbEvaluator3.evaluate(f, numValueOf3, Integer.valueOf(qSLongPressProperties6 != null ? qSLongPressProperties6.getSecondaryLabelColor() : 0));
        objEvaluate3.getClass();
        int iIntValue3 = ((Integer) objEvaluate3).intValue();
        ArgbEvaluator argbEvaluator4 = this.colorEvaluator;
        QSLongPressProperties qSLongPressProperties7 = this.initialLongPressProperties;
        Integer numValueOf4 = Integer.valueOf(qSLongPressProperties7 != null ? qSLongPressProperties7.getChevronColor() : 0);
        QSLongPressProperties qSLongPressProperties8 = this.finalLongPressProperties;
        Object objEvaluate4 = argbEvaluator4.evaluate(f, numValueOf4, Integer.valueOf(qSLongPressProperties8 != null ? qSLongPressProperties8.getChevronColor() : 0));
        objEvaluate4.getClass();
        int iIntValue4 = ((Integer) objEvaluate4).intValue();
        ArgbEvaluator argbEvaluator5 = this.colorEvaluator;
        QSLongPressProperties qSLongPressProperties9 = this.initialLongPressProperties;
        Integer numValueOf5 = Integer.valueOf(qSLongPressProperties9 != null ? qSLongPressProperties9.getOverlayColor() : 0);
        QSLongPressProperties qSLongPressProperties10 = this.finalLongPressProperties;
        Object objEvaluate5 = argbEvaluator5.evaluate(f, numValueOf5, Integer.valueOf(qSLongPressProperties10 != null ? qSLongPressProperties10.getOverlayColor() : 0));
        objEvaluate5.getClass();
        setAllColors(iIntValue, iIntValue2, iIntValue3, iIntValue4, ((Integer) objEvaluate5).intValue());
        QSIconViewImpl qSIconViewImpl = this.icon;
        View view = qSIconViewImpl.mIcon;
        view.getClass();
        ImageView imageView = (ImageView) view;
        ArgbEvaluator argbEvaluator6 = this.colorEvaluator;
        QSLongPressProperties qSLongPressProperties11 = this.initialLongPressProperties;
        Integer numValueOf6 = Integer.valueOf(qSLongPressProperties11 != null ? qSLongPressProperties11.getIconColor() : 0);
        QSLongPressProperties qSLongPressProperties12 = this.finalLongPressProperties;
        Object objEvaluate6 = argbEvaluator6.evaluate(f, numValueOf6, Integer.valueOf(qSLongPressProperties12 != null ? qSLongPressProperties12.getIconColor() : 0));
        objEvaluate6.getClass();
        qSIconViewImpl.setTint(imageView, ((Integer) objEvaluate6).intValue());
        QSLongPressProperties qSLongPressProperties13 = this.initialLongPressProperties;
        float xScale = qSLongPressProperties13 != null ? qSLongPressProperties13.getXScale() : 1.0f;
        QSLongPressProperties qSLongPressProperties14 = this.finalLongPressProperties;
        float fInterpolateFloat = interpolateFloat(f, xScale, qSLongPressProperties14 != null ? qSLongPressProperties14.getXScale() : 1.0f);
        QSLongPressProperties qSLongPressProperties15 = this.initialLongPressProperties;
        float xScale2 = qSLongPressProperties15 != null ? qSLongPressProperties15.getXScale() : 1.0f;
        QSLongPressProperties qSLongPressProperties16 = this.finalLongPressProperties;
        float fInterpolateFloat2 = interpolateFloat(f, xScale2, qSLongPressProperties16 != null ? qSLongPressProperties16.getXScale() : 1.0f);
        QSLongPressProperties qSLongPressProperties17 = this.initialLongPressProperties;
        float cornerRadius = qSLongPressProperties17 != null ? qSLongPressProperties17.getCornerRadius() : 0.0f;
        QSLongPressProperties qSLongPressProperties18 = this.finalLongPressProperties;
        float fInterpolateFloat3 = interpolateFloat(f, cornerRadius, qSLongPressProperties18 != null ? qSLongPressProperties18.getCornerRadius() : 0.0f);
        setScaleX(fInterpolateFloat);
        setScaleY(fInterpolateFloat2);
        for (View view2 : ConvenienceExtensionsKt.getChildren(this)) {
            view2.setScaleX(1.0f / fInterpolateFloat);
            view2.setScaleY(1.0f / fInterpolateFloat2);
        }
        changeCornerRadius(fInterpolateFloat3);
    }

    public final void updateResources() {
        TextView textView = this.label;
        ImageView imageView = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("label");
            textView = null;
        }
        int i = com.motorola.taskbar.R$dimen.qs_tile_text_size;
        FontSizeUtils.updateFontSize(textView, i);
        FontSizeUtils.updateFontSize(getSecondaryLabel(), i);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R$dimen.qs_icon_size);
        ViewGroup.LayoutParams layoutParams = this.icon.getLayoutParams();
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R$dimen.qs_tile_padding);
        setPaddingRelative(getResources().getDimensionPixelSize(R$dimen.qs_tile_start_padding), dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R$dimen.qs_label_container_margin);
        IgnorableChildLinearLayout ignorableChildLinearLayout = this.labelContainer;
        if (ignorableChildLinearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("labelContainer");
            ignorableChildLinearLayout = null;
        }
        ViewGroup.LayoutParams layoutParams2 = ignorableChildLinearLayout.getLayoutParams();
        layoutParams2.getClass();
        ((ViewGroup.MarginLayoutParams) layoutParams2).setMarginStart(dimensionPixelSize3);
        ViewGroup.LayoutParams layoutParams3 = getSideView().getLayoutParams();
        layoutParams3.getClass();
        ((ViewGroup.MarginLayoutParams) layoutParams3).setMarginStart(dimensionPixelSize3);
        ImageView imageView2 = this.chevronView;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("chevronView");
            imageView2 = null;
        }
        ViewGroup.LayoutParams layoutParams4 = imageView2.getLayoutParams();
        layoutParams4.getClass();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams4;
        marginLayoutParams.height = dimensionPixelSize;
        marginLayoutParams.width = dimensionPixelSize;
        int dimensionPixelSize4 = getResources().getDimensionPixelSize(R$dimen.qs_drawable_end_margin);
        ImageView imageView3 = this.customDrawableView;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("customDrawableView");
        } else {
            imageView = imageView3;
        }
        ViewGroup.LayoutParams layoutParams5 = imageView.getLayoutParams();
        layoutParams5.getClass();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams5;
        marginLayoutParams2.height = dimensionPixelSize;
        marginLayoutParams2.setMarginEnd(dimensionPixelSize4);
        setBackground(createTileBackground());
        setColor(this.backgroundColor);
        setOverlayColor(this.backgroundOverlayColor);
    }
}
