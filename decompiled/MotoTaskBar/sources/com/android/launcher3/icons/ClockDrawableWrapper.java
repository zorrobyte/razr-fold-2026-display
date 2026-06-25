package com.android.launcher3.icons;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.IconProvider;
import com.android.launcher3.icons.mono.ThemedIconDrawable;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes.dex */
public class ClockDrawableWrapper extends AdaptiveIconDrawable implements BitmapInfo.Extender {
    public static final long TICK_MS = TimeUnit.MINUTES.toMillis(1);
    public static boolean sRunningInTest = false;
    private final AnimationInfo mAnimationInfo;
    private AnimationInfo mThemeInfo;

    class AnimationInfo {
        public Drawable.ConstantState baseDrawableState;
        public int defaultHour;
        public int defaultMinute;
        public int defaultSecond;
        public int hourLayerIndex;
        public int minuteLayerIndex;
        public int secondLayerIndex;

        private AnimationInfo() {
        }

        boolean applyTime(Calendar calendar, LayerDrawable layerDrawable) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            int i = (calendar.get(10) + (12 - this.defaultHour)) % 12;
            int i2 = (calendar.get(12) + (60 - this.defaultMinute)) % 60;
            int i3 = (calendar.get(13) + (60 - this.defaultSecond)) % 60;
            int i4 = this.hourLayerIndex;
            boolean z = i4 != -1 && layerDrawable.getDrawable(i4).setLevel((i * 60) + calendar.get(12));
            int i5 = this.minuteLayerIndex;
            if (i5 != -1 && layerDrawable.getDrawable(i5).setLevel((calendar.get(10) * 60) + i2)) {
                z = true;
            }
            int i6 = this.secondLayerIndex;
            if (i6 == -1 || !layerDrawable.getDrawable(i6).setLevel(i3 * 10)) {
                return z;
            }
            return true;
        }

        public AnimationInfo copyForIcon(Drawable drawable) {
            AnimationInfo animationInfo = new AnimationInfo();
            animationInfo.baseDrawableState = drawable.getConstantState();
            animationInfo.defaultHour = this.defaultHour;
            animationInfo.defaultMinute = this.defaultMinute;
            animationInfo.defaultSecond = this.defaultSecond;
            animationInfo.hourLayerIndex = this.hourLayerIndex;
            animationInfo.minuteLayerIndex = this.minuteLayerIndex;
            animationInfo.secondLayerIndex = this.secondLayerIndex;
            return animationInfo;
        }
    }

    class ClockBitmapInfo extends BitmapInfo {
        public final AnimationInfo animInfo;
        public final float boundsOffset;
        public final Bitmap mFlattenedBackground;
        public final Bitmap themeBackground;
        public final AnimationInfo themeData;

        ClockBitmapInfo(Bitmap bitmap, int i, float f, AnimationInfo animationInfo, Bitmap bitmap2, AnimationInfo animationInfo2, Bitmap bitmap3) {
            super(bitmap, i);
            this.boundsOffset = Math.max(0.035f, (1.0f - f) / 2.0f);
            this.animInfo = animationInfo;
            this.mFlattenedBackground = bitmap2;
            this.themeData = animationInfo2;
            this.themeBackground = bitmap3;
        }

        @Override // com.android.launcher3.icons.BitmapInfo
        /* JADX INFO: renamed from: clone */
        public BitmapInfo mo1131clone() {
            return copyInternalsTo(new ClockBitmapInfo(this.icon, this.color, 1.0f - (this.boundsOffset * 2.0f), this.animInfo, this.mFlattenedBackground, this.themeData, this.themeBackground));
        }

        @Override // com.android.launcher3.icons.BitmapInfo
        public FastBitmapDrawable newIcon(Context context, int i) {
            AnimationInfo animationInfoCopyForIcon;
            Bitmap bitmap;
            int i2;
            BlendModeColorFilter blendModeColorFilter;
            if ((i & 1) == 0 || this.themeData == null) {
                animationInfoCopyForIcon = this.animInfo;
                bitmap = this.mFlattenedBackground;
                i2 = -1;
                blendModeColorFilter = null;
            } else {
                int[] colors = ThemedIconDrawable.getColors(context);
                Drawable drawableMutate = this.themeData.baseDrawableState.newDrawable().mutate();
                i2 = colors[1];
                drawableMutate.setTint(i2);
                animationInfoCopyForIcon = this.themeData.copyForIcon(drawableMutate);
                bitmap = this.themeBackground;
                blendModeColorFilter = new BlendModeColorFilter(colors[0], BlendMode.SRC_IN);
            }
            AnimationInfo animationInfo = animationInfoCopyForIcon;
            int i3 = i2;
            Bitmap bitmap2 = bitmap;
            BlendModeColorFilter blendModeColorFilter2 = blendModeColorFilter;
            if (animationInfo == null) {
                return super.newIcon(context, i);
            }
            FastBitmapDrawable fastBitmapDrawableNewDrawable = new ClockIconDrawable.ClockConstantState(this.icon, this.color, i3, this.boundsOffset, animationInfo, bitmap2, blendModeColorFilter2).newDrawable();
            applyFlags(context, fastBitmapDrawableNewDrawable, i);
            return fastBitmapDrawableNewDrawable;
        }
    }

    public class ClockIconDrawable extends FastBitmapDrawable implements Runnable {
        private final AnimationInfo mAnimInfo;
        private final Bitmap mBG;
        private final ColorFilter mBgFilter;
        private final Paint mBgPaint;
        private final float mBoundsOffset;
        private final float mCanvasScale;
        private final LayerDrawable mFG;
        private final AdaptiveIconDrawable mFullDrawable;
        private final int mThemedFgColor;
        private final Calendar mTime;

        class ClockConstantState extends FastBitmapDrawable.FastBitmapConstantState {
            private final AnimationInfo mAnimInfo;
            private final Bitmap mBG;
            private final ColorFilter mBgFilter;
            private final float mBoundsOffset;
            private final int mThemedFgColor;

            ClockConstantState(Bitmap bitmap, int i, int i2, float f, AnimationInfo animationInfo, Bitmap bitmap2, ColorFilter colorFilter) {
                super(bitmap, i);
                this.mBoundsOffset = f;
                this.mAnimInfo = animationInfo;
                this.mBG = bitmap2;
                this.mBgFilter = colorFilter;
                this.mThemedFgColor = i2;
            }

            @Override // com.android.launcher3.icons.FastBitmapDrawable.FastBitmapConstantState
            public FastBitmapDrawable createDrawable() {
                return new ClockIconDrawable(this);
            }
        }

        ClockIconDrawable(ClockConstantState clockConstantState) {
            super(clockConstantState.mBitmapInfo);
            Calendar calendar = Calendar.getInstance();
            this.mTime = calendar;
            Paint paint = new Paint(3);
            this.mBgPaint = paint;
            float f = clockConstantState.mBoundsOffset;
            this.mBoundsOffset = f;
            AnimationInfo animationInfo = clockConstantState.mAnimInfo;
            this.mAnimInfo = animationInfo;
            this.mBG = clockConstantState.mBG;
            this.mBgFilter = clockConstantState.mBgFilter;
            paint.setColorFilter(clockConstantState.mBgFilter);
            this.mThemedFgColor = clockConstantState.mThemedFgColor;
            AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) animationInfo.baseDrawableState.newDrawable().mutate();
            this.mFullDrawable = adaptiveIconDrawable;
            LayerDrawable layerDrawable = (LayerDrawable) adaptiveIconDrawable.getForeground();
            this.mFG = layerDrawable;
            animationInfo.applyTime(calendar, layerDrawable);
            this.mCanvasScale = 1.0f - (f * 2.0f);
        }

        private void reschedule() {
            if (isVisible()) {
                if (ClockDrawableWrapper.sRunningInTest) {
                    Log.d("b/319168409", "unScheduling self this: " + this);
                }
                unscheduleSelf(this);
                long jUptimeMillis = SystemClock.uptimeMillis();
                long j = ClockDrawableWrapper.TICK_MS;
                if (ClockDrawableWrapper.sRunningInTest) {
                    Log.d("b/319168409", "scheduling self this: " + this, new Throwable());
                }
                scheduleSelf(this, (jUptimeMillis - (jUptimeMillis % j)) + j);
            }
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable
        public void drawInternal(Canvas canvas, Rect rect) {
            if (this.mAnimInfo == null) {
                super.drawInternal(canvas, rect);
                return;
            }
            try {
                canvas.drawBitmap(this.mBG, (Rect) null, rect, this.mBgPaint);
            } catch (IllegalArgumentException unused) {
                canvas.drawBitmap(this.mBG.copy(Bitmap.Config.ARGB_4444, false), (Rect) null, rect, this.mBgPaint);
            }
            this.mAnimInfo.applyTime(this.mTime, this.mFG);
            int iSave = canvas.save();
            canvas.translate(rect.left, rect.top);
            float f = this.mCanvasScale;
            canvas.scale(f, f, rect.width() / 2, rect.height() / 2);
            canvas.clipPath(this.mFullDrawable.getIconMask());
            this.mFG.draw(canvas);
            canvas.restoreToCount(iSave);
            reschedule();
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable
        public FastBitmapDrawable.FastBitmapConstantState newConstantState() {
            BitmapInfo bitmapInfo = this.mBitmapInfo;
            return new ClockConstantState(bitmapInfo.icon, bitmapInfo.color, this.mThemedFgColor, this.mBoundsOffset, this.mAnimInfo, this.mBG, this.mBgPaint.getColorFilter());
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable, android.graphics.drawable.Drawable
        protected void onBoundsChange(Rect rect) {
            super.onBoundsChange(rect);
            this.mFullDrawable.setBounds(0, 0, rect.width(), rect.height());
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ClockDrawableWrapper.sRunningInTest) {
                Log.d("b/319168409", "running this: " + this);
            }
            if (this.mAnimInfo.applyTime(this.mTime, this.mFG)) {
                invalidateSelf();
            } else {
                reschedule();
            }
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable, android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            super.setAlpha(i);
            this.mBgPaint.setAlpha(i);
            this.mFG.setAlpha(i);
        }

        @Override // android.graphics.drawable.Drawable
        public boolean setVisible(boolean z, boolean z2) {
            boolean visible = super.setVisible(z, z2);
            if (z) {
                reschedule();
                return visible;
            }
            if (ClockDrawableWrapper.sRunningInTest) {
                Log.d("b/319168409", "unScheduling self invisible this: " + this);
            }
            unscheduleSelf(this);
            return visible;
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable
        protected void updateFilter() {
            super.updateFilter();
            setAlpha(this.mIsDisabled ? (int) (this.mDisabledAlpha * 255.0f) : 255);
            this.mBgPaint.setColorFilter(this.mIsDisabled ? FastBitmapDrawable.getDisabledColorFilter() : this.mBgFilter);
            this.mFG.setColorFilter(this.mIsDisabled ? FastBitmapDrawable.getDisabledColorFilter() : null);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$ah5w40R8Ty3QlvDTg9N-60e1hPk, reason: not valid java name */
    public static /* synthetic */ Drawable m1133$r8$lambda$ah5w40R8Ty3QlvDTg9N60e1hPk(IconProvider.ThemeData themeData, int i) {
        return new AdaptiveIconDrawable(new ColorDrawable(-1), themeData.mResources.getDrawable(i).mutate());
    }

    private ClockDrawableWrapper(AdaptiveIconDrawable adaptiveIconDrawable) {
        super(adaptiveIconDrawable.getBackground(), adaptiveIconDrawable.getForeground());
        this.mAnimationInfo = new AnimationInfo();
        this.mThemeInfo = null;
    }

    private void applyThemeData(final IconProvider.ThemeData themeData) {
        if (IconProvider.ATLEAST_T && this.mThemeInfo == null) {
            try {
                TypedArray typedArrayObtainTypedArray = themeData.mResources.obtainTypedArray(themeData.mResID);
                int length = typedArrayObtainTypedArray.length();
                Bundle bundle = new Bundle();
                for (int i = 0; i < length; i += 2) {
                    TypedValue typedValuePeekValue = typedArrayObtainTypedArray.peekValue(i + 1);
                    String string = typedArrayObtainTypedArray.getString(i);
                    int i2 = typedValuePeekValue.type;
                    bundle.putInt(string, (i2 < 16 || i2 > 31) ? typedValuePeekValue.resourceId : typedValuePeekValue.data);
                }
                typedArrayObtainTypedArray.recycle();
                ClockDrawableWrapper clockDrawableWrapperForExtras = forExtras(bundle, new IntFunction() { // from class: com.android.launcher3.icons.ClockDrawableWrapper$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i3) {
                        return ClockDrawableWrapper.m1133$r8$lambda$ah5w40R8Ty3QlvDTg9N60e1hPk(themeData, i3);
                    }
                }, null);
                if (clockDrawableWrapperForExtras != null) {
                    this.mThemeInfo = clockDrawableWrapperForExtras.mAnimationInfo;
                }
            } catch (Exception e) {
                Log.e("ClockDrawableWrapper", "Error loading themed clock", e);
            }
        }
    }

    private static ClockDrawableWrapper forExtras(Bundle bundle, IntFunction intFunction, Drawable drawable) {
        int i;
        if (!(drawable instanceof AdaptiveIconDrawable)) {
            if (bundle == null || (i = bundle.getInt("com.android.launcher3.LEVEL_PER_TICK_ICON_ROUND", 0)) == 0) {
                return null;
            }
            drawable = ((Drawable) intFunction.apply(i)).mutate();
        }
        if (!(drawable instanceof AdaptiveIconDrawable)) {
            return null;
        }
        AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable;
        ClockDrawableWrapper clockDrawableWrapper = new ClockDrawableWrapper(adaptiveIconDrawable);
        AnimationInfo animationInfo = clockDrawableWrapper.mAnimationInfo;
        animationInfo.baseDrawableState = drawable.getConstantState();
        animationInfo.hourLayerIndex = bundle.getInt("com.android.launcher3.HOUR_LAYER_INDEX", -1);
        animationInfo.minuteLayerIndex = bundle.getInt("com.android.launcher3.MINUTE_LAYER_INDEX", -1);
        animationInfo.secondLayerIndex = bundle.getInt("com.android.launcher3.SECOND_LAYER_INDEX", -1);
        animationInfo.defaultHour = bundle.getInt("com.android.launcher3.DEFAULT_HOUR", 0);
        animationInfo.defaultMinute = bundle.getInt("com.android.launcher3.DEFAULT_MINUTE", 0);
        animationInfo.defaultSecond = bundle.getInt("com.android.launcher3.DEFAULT_SECOND", 0);
        LayerDrawable layerDrawable = (LayerDrawable) clockDrawableWrapper.getForeground();
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        int i2 = animationInfo.hourLayerIndex;
        if (i2 < 0 || i2 >= numberOfLayers) {
            animationInfo.hourLayerIndex = -1;
        }
        int i3 = animationInfo.minuteLayerIndex;
        if (i3 < 0 || i3 >= numberOfLayers) {
            animationInfo.minuteLayerIndex = -1;
        }
        int i4 = animationInfo.secondLayerIndex;
        if (i4 < 0 || i4 >= numberOfLayers) {
            animationInfo.secondLayerIndex = -1;
        } else {
            layerDrawable.setDrawable(i4, null);
            animationInfo.secondLayerIndex = -1;
        }
        if (IconProvider.ATLEAST_T && (adaptiveIconDrawable.getMonochrome() instanceof LayerDrawable)) {
            clockDrawableWrapper.mThemeInfo = animationInfo.copyForIcon(new AdaptiveIconDrawable(new ColorDrawable(-1), adaptiveIconDrawable.getMonochrome().mutate()));
        }
        animationInfo.applyTime(Calendar.getInstance(), layerDrawable);
        return clockDrawableWrapper;
    }

    public static ClockDrawableWrapper forPackage(Context context, String str, final int i, IconProvider.ThemeData themeData, Drawable drawable) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 8320);
            final Resources resourcesForApplication = packageManager.getResourcesForApplication(applicationInfo);
            ClockDrawableWrapper clockDrawableWrapperForExtras = forExtras(applicationInfo.metaData, new IntFunction() { // from class: com.android.launcher3.icons.ClockDrawableWrapper$$ExternalSyntheticLambda0
                @Override // java.util.function.IntFunction
                public final Object apply(int i2) {
                    return resourcesForApplication.getDrawableForDensity(i2, i);
                }
            }, drawable);
            if (clockDrawableWrapperForExtras != null && themeData != null) {
                clockDrawableWrapperForExtras.applyThemeData(themeData);
            }
            return clockDrawableWrapperForExtras;
        } catch (Exception e) {
            Log.d("ClockDrawableWrapper", "Unable to load clock drawable info", e);
            return null;
        }
    }

    private void resetLevel(LayerDrawable layerDrawable, int i) {
        if (i != -1) {
            layerDrawable.getDrawable(i).setLevel(0);
        }
    }

    @Override // com.android.launcher3.icons.BitmapInfo.Extender
    public void drawForPersistence(Canvas canvas) {
        LayerDrawable layerDrawable = (LayerDrawable) getForeground();
        resetLevel(layerDrawable, this.mAnimationInfo.hourLayerIndex);
        resetLevel(layerDrawable, this.mAnimationInfo.minuteLayerIndex);
        resetLevel(layerDrawable, this.mAnimationInfo.secondLayerIndex);
        draw(canvas);
        this.mAnimationInfo.applyTime(Calendar.getInstance(), (LayerDrawable) getForeground());
    }

    @Override // com.android.launcher3.icons.BitmapInfo.Extender
    public ClockBitmapInfo getExtendedInfo(Bitmap bitmap, int i, BaseIconFactory baseIconFactory, float f) {
        Bitmap bitmapCreateScaledBitmap = baseIconFactory.createScaledBitmap(new AdaptiveIconDrawable(getBackground().getConstantState().newDrawable(), null), 4);
        AnimationInfo animationInfo = baseIconFactory.mMonoIconEnabled ? this.mThemeInfo : null;
        Bitmap whiteShadowLayer = animationInfo != null ? baseIconFactory.getWhiteShadowLayer() : null;
        baseIconFactory.resetColorExtraction();
        return new ClockBitmapInfo(bitmap, i, f, this.mAnimationInfo, bitmapCreateScaledBitmap, animationInfo, whiteShadowLayer);
    }

    @Override // android.graphics.drawable.AdaptiveIconDrawable
    public Drawable getMonochrome() {
        AnimationInfo animationInfo = this.mThemeInfo;
        if (animationInfo == null) {
            return null;
        }
        Drawable drawableMutate = animationInfo.baseDrawableState.newDrawable().mutate();
        if (!(drawableMutate instanceof AdaptiveIconDrawable)) {
            return null;
        }
        Drawable foreground = ((AdaptiveIconDrawable) drawableMutate).getForeground();
        this.mThemeInfo.applyTime(Calendar.getInstance(), (LayerDrawable) foreground);
        return foreground;
    }
}
