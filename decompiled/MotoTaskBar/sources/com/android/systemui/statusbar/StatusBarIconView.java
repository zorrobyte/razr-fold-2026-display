package com.android.systemui.statusbar;

import android.R;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.FloatProperty;
import android.util.Log;
import android.util.Property;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import androidx.core.graphics.ColorUtils;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.Flags;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.res.R$bool;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$drawable;
import com.android.systemui.res.R$id;
import com.android.systemui.statusbar.notification.NotificationContentDescription;
import com.android.systemui.statusbar.notification.NotificationDozeHelper;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.util.drawable.DrawableSize;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarIconView extends AnimatedImageView implements DarkIconDispatcher.DarkReceiver {
    private final int ANIMATION_DURATION_FAST;
    private int mAnimationStartColor;
    private final boolean mBlocked;
    private int mCachedContrastBackgroundColor;
    private final ValueAnimator.AnimatorUpdateListener mColorUpdater;
    private Configuration mConfiguration;
    private int mContrastedDrawableColor;
    private int mCurrentSetColor;
    private int mDecorColor;
    private boolean mDismissed;
    private float mDotAppearAmount;
    private final Paint mDotPaint;
    private float mDotRadius;
    private float mDozeAmount;
    private final NotificationDozeHelper mDozer;
    private int mDrawableColor;
    private StatusBarIcon mIcon;
    private float mIconAppearAmount;
    private int mIconColor;
    private float mIconScale;
    private boolean mIncreasedSize;
    private Runnable mLayoutRunnable;
    private float[] mMatrix;
    private ColorMatrixColorFilter mMatrixColorFilter;
    int mNewStatusBarIconSize;
    private boolean mNightMode;
    private StatusBarNotification mNotification;
    private Drawable mNumberBackground;
    private Paint mNumberPain;
    private String mNumberText;
    private int mNumberX;
    private int mNumberY;
    private Runnable mOnDismissListener;
    int mOriginalStatusBarIconSize;
    float mScaleToFitNewIconSize;
    private boolean mShowsConversation;
    private String mSlot;
    private int mStaticDotRadius;
    int mStatusBarIconDrawingSize;
    private int mStatusBarIconDrawingSizeIncreased;
    private float mSystemIconDefaultScale;
    private float mSystemIconDesiredHeight;
    private float mSystemIconIntrinsicHeight;
    private int mVisibleState;
    private static final Property ICON_APPEAR_AMOUNT = new FloatProperty("iconAppearAmount") { // from class: com.android.systemui.statusbar.StatusBarIconView.1
        @Override // android.util.Property
        public Float get(StatusBarIconView statusBarIconView) {
            return Float.valueOf(statusBarIconView.getIconAppearAmount());
        }

        @Override // android.util.FloatProperty
        public void setValue(StatusBarIconView statusBarIconView, float f) {
            statusBarIconView.setIconAppearAmount(f);
        }
    };
    private static final Property DOT_APPEAR_AMOUNT = new FloatProperty("dot_appear_amount") { // from class: com.android.systemui.statusbar.StatusBarIconView.2
        @Override // android.util.Property
        public Float get(StatusBarIconView statusBarIconView) {
            return Float.valueOf(statusBarIconView.getDotAppearAmount());
        }

        @Override // android.util.FloatProperty
        public void setValue(StatusBarIconView statusBarIconView, float f) {
            statusBarIconView.setDotAppearAmount(f);
        }
    };

    public StatusBarIconView(Context context, String str, StatusBarNotification statusBarNotification) {
        this(context, str, statusBarNotification, false);
    }

    public StatusBarIconView(Context context, String str, StatusBarNotification statusBarNotification, boolean z) {
        super(context);
        this.mSystemIconDesiredHeight = 15.0f;
        this.mSystemIconIntrinsicHeight = 17.0f;
        this.mSystemIconDefaultScale = 15.0f / 17.0f;
        this.ANIMATION_DURATION_FAST = 100;
        this.mStatusBarIconDrawingSizeIncreased = 1;
        this.mStatusBarIconDrawingSize = 1;
        this.mOriginalStatusBarIconSize = 1;
        this.mNewStatusBarIconSize = 1;
        this.mScaleToFitNewIconSize = 1.0f;
        this.mIconScale = 1.0f;
        this.mDotPaint = new Paint(1);
        this.mVisibleState = 0;
        this.mIconAppearAmount = 1.0f;
        this.mCurrentSetColor = 0;
        this.mAnimationStartColor = 0;
        this.mColorUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.StatusBarIconView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$new$0(valueAnimator);
            }
        };
        this.mCachedContrastBackgroundColor = 0;
        this.mDozer = new NotificationDozeHelper();
        this.mBlocked = z;
        this.mSlot = str;
        Paint paint = new Paint();
        this.mNumberPain = paint;
        paint.setTextAlign(Paint.Align.CENTER);
        this.mNumberPain.setColor(context.getColor(R$drawable.notification_number_text_color));
        this.mNumberPain.setAntiAlias(true);
        setNotification(statusBarNotification);
        setScaleType(ImageView.ScaleType.CENTER);
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        this.mConfiguration = configuration;
        this.mNightMode = (configuration.uiMode & 48) == 32;
        initializeDecorColor();
        reloadDimens();
        maybeUpdateIconScaleDimens();
        if (Flags.statusBarMonochromeIconsFix()) {
            setCropToPadding(true);
        }
    }

    private Drawable getIcon(Context context, Context context2, StatusBarIcon statusBarIcon) {
        int identifier = statusBarIcon.user.getIdentifier();
        if (identifier == -1) {
            identifier = 0;
        }
        Drawable drawableLoadDrawableAsUser = statusBarIcon.icon.loadDrawableAsUser(context2, identifier);
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(R$dimen.status_bar_icon_scale_factor, typedValue, true);
        float f = typedValue.getFloat();
        if (drawableLoadDrawableAsUser != null) {
            boolean zIsLowRamDeviceStatic = ActivityManager.isLowRamDeviceStatic();
            Resources resources = context.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(zIsLowRamDeviceStatic ? R.dimen.preference_breadcrumb_paddingRight : R.dimen.preference_breadcrumb_paddingLeft);
            drawableLoadDrawableAsUser = DrawableSize.downscaleToSize(resources, drawableLoadDrawableAsUser, dimensionPixelSize, dimensionPixelSize);
        }
        return f == 1.0f ? drawableLoadDrawableAsUser : new ScalingDrawableWrapper(drawableLoadDrawableAsUser, f);
    }

    private float getIconHeight() {
        return getDrawable() != null ? getDrawable().getIntrinsicHeight() : this.mSystemIconIntrinsicHeight;
    }

    public static String getVisibleStateString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "UNKNOWN" : "HIDDEN" : "DOT" : "ICON";
    }

    private void initializeDecorColor() {
        if (isNotification()) {
            setDecorColor(getContext().getColor(this.mNightMode ? R.color.surface_effect_3_color : R.color.surface_header_dark));
        }
    }

    private boolean isNotification() {
        return this.mNotification != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        setColorInternal(NotificationUtils.interpolateColors(this.mAnimationStartColor, this.mIconColor, valueAnimator.getAnimatedFraction()));
    }

    private void reloadDimens() {
        boolean z = this.mDotRadius == ((float) this.mStaticDotRadius);
        Resources resources = getResources();
        this.mStaticDotRadius = resources.getDimensionPixelSize(R$dimen.overflow_dot_radius);
        this.mOriginalStatusBarIconSize = resources.getDimensionPixelSize(R$dimen.status_bar_icon_size);
        int dimensionPixelSize = resources.getDimensionPixelSize(R$dimen.status_bar_icon_size_sp);
        this.mNewStatusBarIconSize = dimensionPixelSize;
        this.mScaleToFitNewIconSize = dimensionPixelSize / this.mOriginalStatusBarIconSize;
        this.mStatusBarIconDrawingSizeIncreased = resources.getDimensionPixelSize(R$dimen.status_bar_icon_drawing_size_dark);
        this.mStatusBarIconDrawingSize = resources.getDimensionPixelSize(R$dimen.status_bar_icon_drawing_size);
        if (z) {
            this.mDotRadius = this.mStaticDotRadius;
        }
        this.mSystemIconDesiredHeight = resources.getDimension(R.dimen.text_size_menu_header_material);
        float dimension = resources.getDimension(R.dimen.text_size_medium_material);
        this.mSystemIconIntrinsicHeight = dimension;
        this.mSystemIconDefaultScale = this.mSystemIconDesiredHeight / dimension;
    }

    private void setColorInternal(int i) {
        this.mCurrentSetColor = i;
        updateIconColor();
    }

    private void updateContrastedStaticColor() {
        if (Color.alpha(this.mCachedContrastBackgroundColor) != 255) {
            this.mContrastedDrawableColor = this.mDrawableColor;
            return;
        }
        int iResolveContrastColor = this.mDrawableColor;
        if (!ContrastColorUtil.satisfiesTextContrast(this.mCachedContrastBackgroundColor, iResolveContrastColor)) {
            float[] fArr = new float[3];
            ColorUtils.colorToHSL(this.mDrawableColor, fArr);
            if (fArr[1] < 0.2f) {
                iResolveContrastColor = 0;
            }
            iResolveContrastColor = ContrastColorUtil.resolveContrastColor(((ImageView) this).mContext, iResolveContrastColor, this.mCachedContrastBackgroundColor, !ContrastColorUtil.isColorLight(this.mCachedContrastBackgroundColor));
        }
        this.mContrastedDrawableColor = iResolveContrastColor;
    }

    private void updateDecorColor() {
        int iInterpolateColors = NotificationUtils.interpolateColors(this.mDecorColor, -1, this.mDozeAmount);
        if (this.mDotPaint.getColor() != iInterpolateColors) {
            this.mDotPaint.setColor(iInterpolateColors);
            if (this.mDotAppearAmount != 0.0f) {
                invalidate();
            }
        }
    }

    private boolean updateDrawable(boolean z) {
        if (this.mIcon == null) {
            return false;
        }
        try {
            Trace.beginSection("StatusBarIconView#updateDrawable()");
            Drawable icon = getIcon(this.mIcon);
            if (icon != null) {
                if (z) {
                    setImageDrawable(null);
                }
                setImageDrawable(icon);
                return true;
            }
            Log.w("StatusBarIconView", "No icon for slot " + this.mSlot + "; " + this.mIcon.icon);
            return false;
        } catch (OutOfMemoryError unused) {
            Log.w("StatusBarIconView", "OOM while inflating " + this.mIcon.icon + " for slot " + this.mSlot);
            return false;
        } finally {
            Trace.endSection();
        }
    }

    private void updateIconColor() {
        if (this.mShowsConversation) {
            setColorFilter((ColorFilter) null);
            return;
        }
        if (this.mCurrentSetColor == 0) {
            this.mDozer.updateGrayscale(this, this.mDozeAmount);
            return;
        }
        if (this.mMatrixColorFilter == null) {
            this.mMatrix = new float[20];
            this.mMatrixColorFilter = new ColorMatrixColorFilter(this.mMatrix);
        }
        updateTintMatrix(this.mMatrix, NotificationUtils.interpolateColors(this.mCurrentSetColor, -1, this.mDozeAmount), this.mDozeAmount * 0.67f);
        this.mMatrixColorFilter.setColorMatrixArray(this.mMatrix);
        setColorFilter((ColorFilter) null);
        setColorFilter(this.mMatrixColorFilter);
    }

    private void updateIconScaleForNotifications() {
        int i;
        int i2;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        float fMin = 1.0f;
        if (getDrawable() != null && layoutParams != null && (i = layoutParams.width) > 0 && (i2 = layoutParams.height) > 0) {
            float intrinsicWidth = getDrawable().getIntrinsicWidth();
            float intrinsicHeight = getDrawable().getIntrinsicHeight();
            float fMin2 = Math.min(i / intrinsicWidth, i2 / intrinsicHeight);
            if (fMin2 > 1.0f) {
                fMin2 = 1.0f;
            }
            int i3 = this.mOriginalStatusBarIconSize;
            float fMin3 = Math.min(i3 / (intrinsicWidth * fMin2), i3 / (intrinsicHeight * fMin2));
            fMin = fMin3 > 1.0f ? Math.min(fMin3, 1.0f / fMin2) : fMin3;
        }
        this.mIconScale = fMin * ((this.mIncreasedSize ? this.mStatusBarIconDrawingSizeIncreased : this.mStatusBarIconDrawingSize) / this.mOriginalStatusBarIconSize) * this.mScaleToFitNewIconSize;
        updatePivot();
    }

    private void updateIconScaleForSystemIcons() {
        float iconHeight = getIconHeight();
        this.mIconScale = (iconHeight != 0.0f ? this.mSystemIconDesiredHeight / iconHeight : this.mSystemIconDefaultScale) * this.mScaleToFitNewIconSize;
    }

    private void updatePivot() {
        if (isLayoutRtl()) {
            setPivotX(((this.mIconScale + 1.0f) / 2.0f) * getWidth());
        } else {
            setPivotX(((1.0f - this.mIconScale) / 2.0f) * getWidth());
        }
        setPivotY((getHeight() - (this.mIconScale * getWidth())) / 2.0f);
    }

    private static void updateTintMatrix(float[] fArr, int i, float f) {
        Arrays.fill(fArr, 0.0f);
        fArr[4] = Color.red(i);
        fArr[9] = Color.green(i);
        fArr[14] = Color.blue(i);
        fArr[18] = (Color.alpha(i) / 255.0f) + f;
    }

    protected void debug(int i) {
        super.debug(i);
        Log.d("View", ImageView.debugIndent(i) + "slot=" + this.mSlot);
        Log.d("View", ImageView.debugIndent(i) + "icon=" + this.mIcon);
    }

    public boolean equalIcons(Icon icon, Icon icon2) {
        if (icon == icon2) {
            return true;
        }
        if (icon.getType() != icon2.getType()) {
            return false;
        }
        int type = icon.getType();
        if (type == 2) {
            return icon.getResPackage().equals(icon2.getResPackage()) && icon.getResId() == icon2.getResId();
        }
        if (type == 4 || type == 6) {
            return icon.getUriString().equals(icon2.getUriString());
        }
        return false;
    }

    public float getDotAppearAmount() {
        return this.mDotAppearAmount;
    }

    @Override // android.view.View
    public void getDrawingRect(Rect rect) {
        super.getDrawingRect(rect);
        float translationX = getTranslationX();
        float translationY = getTranslationY();
        rect.left = (int) (rect.left + translationX);
        rect.right = (int) (rect.right + translationX);
        rect.top = (int) (rect.top + translationY);
        rect.bottom = (int) (rect.bottom + translationY);
    }

    Drawable getIcon(StatusBarIcon statusBarIcon) {
        Context context = getContext();
        if (isNotification()) {
            context = this.mNotification.getPackageContext(getContext());
        }
        Context context2 = getContext();
        if (context == null) {
            context = getContext();
        }
        return getIcon(context2, context, statusBarIcon);
    }

    public float getIconAppearAmount() {
        return this.mIconAppearAmount;
    }

    public Icon getSourceIcon() {
        return this.mIcon.icon;
    }

    public int getVisibleState() {
        return this.mVisibleState;
    }

    @Override // com.android.systemui.statusbar.AnimatedImageView, android.widget.ImageView, android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void maybeUpdateIconScaleDimens() {
        if (isNotification()) {
            updateIconScaleForNotifications();
        } else {
            updateIconScaleForSystemIcons();
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int iDiff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        if ((iDiff & 1073745920) != 0) {
            updateIconDimens();
        }
        boolean z = (configuration.uiMode & 48) == 32;
        if (z != this.mNightMode) {
            this.mNightMode = z;
            initializeDecorColor();
        }
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public void onDarkChanged(ArrayList arrayList, float f, int i) {
        int tint = DarkIconDispatcher.getTint(arrayList, this, i);
        setImageTintList(ColorStateList.valueOf(tint));
        setDecorColor(tint);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        float fInterpolate;
        if (this.mIconAppearAmount > 0.0f) {
            canvas.save();
            int width = getWidth() / 2;
            int height = getHeight() / 2;
            float f = this.mIconScale;
            float f2 = this.mIconAppearAmount;
            canvas.scale(f * f2, f * f2, width, height);
            super.onDraw(canvas);
            canvas.restore();
        }
        Drawable drawable = this.mNumberBackground;
        if (drawable != null) {
            drawable.draw(canvas);
            canvas.drawText(this.mNumberText, this.mNumberX, this.mNumberY, this.mNumberPain);
        }
        if (this.mDotAppearAmount != 0.0f) {
            float fAlpha = Color.alpha(this.mDecorColor) / 255.0f;
            float f3 = this.mDotAppearAmount;
            if (f3 <= 1.0f) {
                fInterpolate = this.mDotRadius * f3;
            } else {
                float f4 = f3 - 1.0f;
                fAlpha *= 1.0f - f4;
                fInterpolate = NotificationUtils.interpolate(this.mDotRadius, getWidth() / 4, f4);
            }
            this.mDotPaint.setAlpha((int) (fAlpha * 255.0f));
            canvas.drawCircle(this.mNewStatusBarIconSize / 2, getHeight() / 2, fInterpolate, this.mDotPaint);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (isNotification()) {
            accessibilityEvent.setParcelableData(this.mNotification.getNotification());
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        Runnable runnable = this.mLayoutRunnable;
        if (runnable != null) {
            runnable.run();
            this.mLayoutRunnable = null;
        }
        updatePivot();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (isNotification()) {
            return;
        }
        setMeasuredDimension((int) (getMeasuredWidth() * this.mScaleToFitNewIconSize), getMeasuredHeight());
    }

    @Override // android.widget.ImageView, android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        updateDrawable();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mNumberBackground != null) {
            placeNumber();
        }
    }

    void placeNumber() {
        String string = this.mIcon.number > getContext().getResources().getInteger(R.integer.status_bar_notification_info_maxnum) ? getContext().getResources().getString(R.string.status_bar_notification_info_overflow) : NumberFormat.getIntegerInstance().format(this.mIcon.number);
        this.mNumberText = string;
        int width = getWidth();
        int height = getHeight();
        Rect rect = new Rect();
        this.mNumberPain.getTextBounds(string, 0, string.length(), rect);
        int i = rect.right - rect.left;
        int i2 = rect.bottom - rect.top;
        this.mNumberBackground.getPadding(rect);
        int minimumWidth = rect.left + i + rect.right;
        if (minimumWidth < this.mNumberBackground.getMinimumWidth()) {
            minimumWidth = this.mNumberBackground.getMinimumWidth();
        }
        int i3 = rect.right;
        this.mNumberX = (width - i3) - (((minimumWidth - i3) - rect.left) / 2);
        int minimumWidth2 = rect.top + i2 + rect.bottom;
        if (minimumWidth2 < this.mNumberBackground.getMinimumWidth()) {
            minimumWidth2 = this.mNumberBackground.getMinimumWidth();
        }
        int i4 = rect.bottom;
        this.mNumberY = (height - i4) - ((((minimumWidth2 - rect.top) - i2) - i4) / 2);
        this.mNumberBackground.setBounds(width - minimumWidth, height - minimumWidth2, width, height);
    }

    public boolean set(StatusBarIcon statusBarIcon) {
        StatusBarIcon statusBarIcon2 = this.mIcon;
        boolean z = statusBarIcon2 != null && equalIcons(statusBarIcon2.icon, statusBarIcon.icon);
        boolean z2 = z && this.mIcon.iconLevel == statusBarIcon.iconLevel;
        StatusBarIcon statusBarIcon3 = this.mIcon;
        boolean z3 = statusBarIcon3 != null && statusBarIcon3.visible == statusBarIcon.visible;
        boolean z4 = statusBarIcon3 != null && statusBarIcon3.number == statusBarIcon.number;
        this.mIcon = statusBarIcon.clone();
        setContentDescription(statusBarIcon.contentDescription);
        if (!z) {
            if (!updateDrawable(false)) {
                return false;
            }
            setTag(R$id.icon_is_grayscale, null);
            maybeUpdateIconScaleDimens();
        }
        if (!z2) {
            setImageLevel(statusBarIcon.iconLevel);
        }
        if (!z4) {
            if (statusBarIcon.number <= 0 || !getContext().getResources().getBoolean(R$bool.config_statusBarShowNumber)) {
                this.mNumberBackground = null;
                this.mNumberText = null;
            } else {
                if (this.mNumberBackground == null) {
                    this.mNumberBackground = getContext().getResources().getDrawable(R$drawable.ic_notification_overlay);
                }
                placeNumber();
            }
            invalidate();
        }
        if (!z3) {
            setVisibility((!statusBarIcon.visible || this.mBlocked) ? 8 : 0);
        }
        return true;
    }

    public void setDecorColor(int i) {
        this.mDecorColor = i;
        updateDecorColor();
    }

    public void setDismissed() {
        this.mDismissed = true;
        Runnable runnable = this.mOnDismissListener;
        if (runnable != null) {
            runnable.run();
        }
    }

    public void setDotAppearAmount(float f) {
        if (this.mDotAppearAmount != f) {
            this.mDotAppearAmount = f;
            invalidate();
        }
    }

    public void setIconAppearAmount(float f) {
        if (this.mIconAppearAmount != f) {
            this.mIconAppearAmount = f;
            invalidate();
        }
    }

    public void setIncreasedSize(boolean z) {
        this.mIncreasedSize = z;
        maybeUpdateIconScaleDimens();
    }

    public void setNotification(StatusBarNotification statusBarNotification) {
        setNotification(statusBarNotification, statusBarNotification != null ? NotificationContentDescription.contentDescForNotification(((ImageView) this).mContext, statusBarNotification.getNotification()) : null);
    }

    public void setNotification(StatusBarNotification statusBarNotification, CharSequence charSequence) {
        this.mNotification = statusBarNotification;
        if (!TextUtils.isEmpty(charSequence)) {
            setContentDescription(charSequence);
        }
        maybeUpdateIconScaleDimens();
    }

    public void setShowsConversation(boolean z) {
        if (this.mShowsConversation != z) {
            this.mShowsConversation = z;
            updateIconColor();
        }
    }

    public void setStaticDrawableColor(int i) {
        this.mDrawableColor = i;
        setColorInternal(i);
        updateContrastedStaticColor();
        this.mIconColor = i;
    }

    @Override // android.view.View
    public String toString() {
        return "StatusBarIconView(slot='" + this.mSlot + "' alpha=" + getAlpha() + " icon=" + this.mIcon + " visibleState=" + getVisibleStateString(getVisibleState()) + " iconColor=#" + Integer.toHexString(this.mIconColor) + " staticDrawableColor=#" + Integer.toHexString(this.mDrawableColor) + " decorColor=#" + Integer.toHexString(this.mDecorColor) + " animationStartColor=#" + Integer.toHexString(this.mAnimationStartColor) + " currentSetColor=#" + Integer.toHexString(this.mCurrentSetColor) + " notification=" + this.mNotification + ')';
    }

    public void updateDrawable() {
        updateDrawable(true);
    }

    public void updateIconDimens() {
        Trace.beginSection("StatusBarIconView#updateIconDimens");
        try {
            reloadDimens();
            updateDrawable();
            maybeUpdateIconScaleDimens();
        } finally {
            Trace.endSection();
        }
    }
}
