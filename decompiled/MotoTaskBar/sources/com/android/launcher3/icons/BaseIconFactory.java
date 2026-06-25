package com.android.launcher3.icons;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.InsetDrawable;
import android.os.UserHandle;
import android.util.Log;
import android.util.SparseArray;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.cache.CloneUtils;
import com.android.launcher3.icons.cache.VaultUtils;
import com.android.launcher3.util.FlagOp;
import com.android.launcher3.util.UserIconInfo;
import com.android.systemui.plugins.DarkIconDispatcher;

/* JADX INFO: loaded from: classes.dex */
public class BaseIconFactory implements AutoCloseable {
    private static final float LEGACY_ICON_SCALE = (1.0f / ((AdaptiveIconDrawable.getExtraInsetFraction() * 2.0f) + 1.0f)) * 0.7f;
    private static int PLACEHOLDER_BACKGROUND_COLOR = Color.rgb(245, 245, 245);
    private final SparseArray mCachedUserInfo;
    private final Canvas mCanvas;
    protected final Context mContext;
    private boolean mDisableColorExtractor;
    protected final int mFullResIconDpi;
    protected final int mIconBitmapSize;
    protected boolean mMonoIconEnabled;
    private IconNormalizer mNormalizer;
    private final Rect mOldBounds;
    private final PackageManager mPm;
    private ShadowGenerator mShadowGenerator;
    private final boolean mShapeDetection;
    private final boolean mShouldForceThemeIcon;
    private String mSystemState;
    private Bitmap mWhiteShadowLayer;
    private int mWrapperBackgroundColor;

    public class ClippedMonoDrawable extends InsetDrawable {
        private final AdaptiveIconDrawable mCrop;
        private Path mShape;

        public ClippedMonoDrawable(Drawable drawable) {
            super(drawable, -AdaptiveIconDrawable.getExtraInsetFraction());
            AdaptiveIconDrawable adaptiveIconDrawable = new AdaptiveIconDrawable(new ColorDrawable(DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT), null);
            this.mCrop = adaptiveIconDrawable;
            this.mShape = adaptiveIconDrawable.getIconMask();
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            this.mCrop.setBounds(getBounds());
            int iSave = canvas.save();
            canvas.clipPath(this.mShape);
            super.draw(canvas);
            canvas.restoreToCount(iSave);
        }

        public void setShape(Path path) {
            this.mShape = path;
        }
    }

    class EmptyWrapper extends DrawableWrapper {
        EmptyWrapper() {
            super(new ColorDrawable());
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public Drawable.ConstantState getConstantState() {
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return null;
            }
            return drawable.getConstantState();
        }
    }

    public class IconOptions {
        Integer mExtractedColor;
        int mGenerationMode = 2;
        boolean mIsInstantApp;
        Path mShape;
        UserHandle mUserHandle;
        UserIconInfo mUserIconInfo;

        public Path getShape() {
            return this.mShape;
        }

        public IconOptions setUser(UserHandle userHandle) {
            this.mUserHandle = userHandle;
            return this;
        }
    }

    class NoopDrawable extends ColorDrawable {
        private NoopDrawable() {
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return 1;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return 1;
        }
    }

    public BaseIconFactory(Context context, int i, int i2) {
        this(context, i, i2, false);
    }

    protected BaseIconFactory(Context context, int i, int i2, boolean z) {
        this.mOldBounds = new Rect();
        this.mCachedUserInfo = new SparseArray();
        this.mWrapperBackgroundColor = -1;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mShapeDetection = z;
        this.mFullResIconDpi = i;
        this.mIconBitmapSize = i2;
        this.mPm = applicationContext.getPackageManager();
        Canvas canvas = new Canvas();
        this.mCanvas = canvas;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        clear();
        this.mShouldForceThemeIcon = applicationContext.getResources().getBoolean(R$bool.enable_forced_themed_icon);
    }

    private Drawable createScaledDrawable(Drawable drawable, float f) {
        float f2;
        float intrinsicHeight = drawable.getIntrinsicHeight();
        float intrinsicWidth = drawable.getIntrinsicWidth();
        if (intrinsicHeight <= intrinsicWidth || intrinsicWidth <= 0.0f) {
            f2 = (intrinsicWidth <= intrinsicHeight || intrinsicHeight <= 0.0f) ? f : (intrinsicHeight / intrinsicWidth) * f;
        } else {
            float f3 = (intrinsicWidth / intrinsicHeight) * f;
            f2 = f;
            f = f3;
        }
        float f4 = (1.0f - f) / 2.0f;
        float f5 = (1.0f - f2) / 2.0f;
        return new InsetDrawable(drawable, f4, f5, f4, f5);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00fa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawIconBitmap(android.graphics.Canvas r7, android.graphics.drawable.Drawable r8, float r9, int r10, android.graphics.Bitmap r11, boolean r12) {
        /*
            Method dump skipped, instruction units count: 306
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.launcher3.icons.BaseIconFactory.drawIconBitmap(android.graphics.Canvas, android.graphics.drawable.Drawable, float, int, android.graphics.Bitmap, boolean):void");
    }

    public static int getBadgeSizeForIconSize(int i) {
        return (int) (i * 0.444f);
    }

    private Drawable getShapedIcon(Drawable drawable, Path path) {
        if (path != null) {
            if (drawable instanceof AdaptiveIconDrawable) {
                AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable;
                Drawable background = adaptiveIconDrawable.getBackground();
                Drawable foreground = adaptiveIconDrawable.getForeground();
                return (background == null || foreground == null) ? drawable : new DynamicAdaptiveIconDrawable(background.getConstantState().newDrawable(), foreground.getConstantState().newDrawable(), path);
            }
            if (drawable instanceof ClippedMonoDrawable) {
                ((ClippedMonoDrawable) drawable).setShape(path);
            }
        }
        return drawable;
    }

    private Bitmap getWhiteShadowLayer(Path path) {
        return path == null ? getWhiteShadowLayer() : createScaledBitmapWithShadow(new DynamicAdaptiveIconDrawable(new ColorDrawable(-1), new ColorDrawable(0), path));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createIconBitmap$1(Drawable drawable, float f, int i, boolean z, Canvas canvas) {
        drawIconBitmap(canvas, drawable, f, i, null, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createScaledBitmapWithShadow$0(Bitmap bitmap, Canvas canvas) {
        getShadowGenerator().recreateIcon(bitmap, canvas);
    }

    private void resetConfig() {
        this.mWhiteShadowLayer = null;
    }

    private void resetConfigIfNeed() {
        String systemIconState = IconProvider.getSystemIconState(this.mContext);
        if (systemIconState.equals(this.mSystemState)) {
            return;
        }
        this.mSystemState = systemIconState;
        resetConfig();
    }

    protected void clear() {
        this.mWrapperBackgroundColor = -1;
        this.mDisableColorExtractor = false;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        clear();
    }

    public BitmapInfo createBadgedIconBitmap(Drawable drawable) {
        return createBadgedIconBitmap(drawable, null);
    }

    public BitmapInfo createBadgedIconBitmap(Drawable drawable, IconOptions iconOptions) {
        return createBadgedIconBitmap(drawable, iconOptions, null);
    }

    public BitmapInfo createBadgedIconBitmap(Drawable drawable, IconOptions iconOptions, float[] fArr) {
        return createBadgedIconBitmap(drawable, iconOptions, fArr, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BitmapInfo createBadgedIconBitmap(Drawable drawable, IconOptions iconOptions, float[] fArr, boolean z) {
        Drawable monochromeDrawable;
        Integer num;
        if (fArr == null) {
            fArr = new float[1];
        }
        UserHandle userHandle = null;
        Path shape = iconOptions == null ? null : iconOptions.getShape();
        Drawable drawableNormalizeAndWrapToAdaptiveIcon = normalizeAndWrapToAdaptiveIcon(CustomAppIcons.getInstance().loadIcon(this.mContext, drawable, this.mMonoIconEnabled), true, null, fArr);
        Bitmap bitmapCreateIconBitmap = createIconBitmap(getShapedIcon(drawableNormalizeAndWrapToAdaptiveIcon, shape), fArr[0], iconOptions == null ? 2 : iconOptions.mGenerationMode, z);
        int iFindDominantColorByHue = (iconOptions == null || (num = iconOptions.mExtractedColor) == null) ? ColorExtractor.findDominantColorByHue(bitmapCreateIconBitmap) : num.intValue();
        BitmapInfo bitmapInfoOf = BitmapInfo.of(bitmapCreateIconBitmap, iFindDominantColorByHue);
        if (drawableNormalizeAndWrapToAdaptiveIcon instanceof BitmapInfo.Extender) {
            bitmapInfoOf = ((BitmapInfo.Extender) drawableNormalizeAndWrapToAdaptiveIcon).getExtendedInfo(bitmapCreateIconBitmap, iFindDominantColorByHue, this, fArr[0]);
        } else if (IconProvider.ATLEAST_T && drawableNormalizeAndWrapToAdaptiveIcon != 0 && this.mMonoIconEnabled && (monochromeDrawable = getMonochromeDrawable(drawableNormalizeAndWrapToAdaptiveIcon)) != null) {
            bitmapInfoOf.setMonoIcon(createIconBitmap(monochromeDrawable, fArr[0], 1), getWhiteShadowLayer(shape));
        }
        BitmapInfo bitmapInfoWithFlags = bitmapInfoOf.withFlags(getBitmapFlagOp(iconOptions));
        if (iconOptions != null) {
            UserHandle userHandle2 = iconOptions.mUserHandle;
            if (userHandle2 == null) {
                UserIconInfo userIconInfo = iconOptions.mUserIconInfo;
                if (userIconInfo != null) {
                    userHandle = userIconInfo.user;
                }
            } else {
                userHandle = userHandle2;
            }
        }
        bitmapInfoWithFlags.setUserHandle(userHandle);
        return bitmapInfoWithFlags;
    }

    public Bitmap createIconBitmap(Drawable drawable, float f) {
        return createIconBitmap(drawable, f, 0);
    }

    public Bitmap createIconBitmap(Drawable drawable, float f, int i) {
        return createIconBitmap(drawable, f, i, false);
    }

    public Bitmap createIconBitmap(final Drawable drawable, final float f, final int i, final boolean z) {
        BaseIconFactory baseIconFactory;
        Drawable drawable2;
        float f2;
        int i2;
        boolean z2;
        Bitmap bitmapCreateBitmap;
        int i3 = this.mIconBitmapSize;
        if (i == 1) {
            baseIconFactory = this;
            drawable2 = drawable;
            f2 = f;
            i2 = i;
            z2 = z;
            bitmapCreateBitmap = Bitmap.createBitmap(i3, i3, Bitmap.Config.ALPHA_8);
        } else {
            if (i == 3 || i == 4) {
                return BitmapRenderer.createHardwareBitmap(i3, i3, new BitmapRenderer() { // from class: com.android.launcher3.icons.BaseIconFactory$$ExternalSyntheticLambda0
                    @Override // com.android.launcher3.icons.BitmapRenderer
                    public final void draw(Canvas canvas) {
                        this.f$0.lambda$createIconBitmap$1(drawable, f, i, z, canvas);
                    }
                });
            }
            bitmapCreateBitmap = Bitmap.createBitmap(i3, i3, Bitmap.Config.ARGB_8888);
            baseIconFactory = this;
            drawable2 = drawable;
            f2 = f;
            i2 = i;
            z2 = z;
        }
        if (drawable2 == null) {
            return bitmapCreateBitmap;
        }
        baseIconFactory.mCanvas.setBitmap(bitmapCreateBitmap);
        int i4 = i2;
        Bitmap bitmap = bitmapCreateBitmap;
        BaseIconFactory baseIconFactory2 = baseIconFactory;
        baseIconFactory2.drawIconBitmap(baseIconFactory.mCanvas, drawable2, f2, i4, bitmap, z2);
        baseIconFactory2.mCanvas.setBitmap(null);
        return bitmap;
    }

    public Bitmap createScaledBitmap(Drawable drawable, int i) {
        RectF rectF = new RectF();
        float[] fArr = new float[1];
        return createIconBitmap(normalizeAndWrapToAdaptiveIcon(drawable, true, rectF, fArr), Math.min(fArr[0], ShadowGenerator.getScaleForBounds(rectF)), i);
    }

    public Bitmap createScaledBitmapWithShadow(Drawable drawable) {
        final Bitmap bitmapCreateIconBitmap = createIconBitmap(drawable, getNormalizer().getScale(drawable, null, null, null));
        return BitmapRenderer.createHardwareBitmap(bitmapCreateIconBitmap.getWidth(), bitmapCreateIconBitmap.getHeight(), new BitmapRenderer() { // from class: com.android.launcher3.icons.BaseIconFactory$$ExternalSyntheticLambda1
            @Override // com.android.launcher3.icons.BitmapRenderer
            public final void draw(Canvas canvas) {
                this.f$0.lambda$createScaledBitmapWithShadow$0(bitmapCreateIconBitmap, canvas);
            }
        });
    }

    public FlagOp getBitmapFlagOp(IconOptions iconOptions) {
        UserHandle userHandle;
        FlagOp flagOpAddFlag = FlagOp.NO_OP;
        if (iconOptions != null) {
            if (iconOptions.mIsInstantApp) {
                flagOpAddFlag = flagOpAddFlag.addFlag(2);
            }
            UserIconInfo userInfo = iconOptions.mUserIconInfo;
            if (userInfo == null && (userHandle = iconOptions.mUserHandle) != null) {
                userInfo = getUserInfo(userHandle);
            }
            if (userInfo != null) {
                return userInfo.applyBitmapInfoFlags(flagOpAddFlag);
            }
        }
        return flagOpAddFlag;
    }

    protected Drawable getMonochromeDrawable(Drawable drawable) {
        Drawable monochrome;
        if (!(drawable instanceof AdaptiveIconDrawable) || (monochrome = ((AdaptiveIconDrawable) drawable).getMonochrome()) == null) {
            return null;
        }
        return new ClippedMonoDrawable(monochrome);
    }

    public IconNormalizer getNormalizer() {
        if (this.mNormalizer == null) {
            this.mNormalizer = new IconNormalizer(this.mContext, this.mIconBitmapSize, this.mShapeDetection);
        }
        return this.mNormalizer;
    }

    public ShadowGenerator getShadowGenerator() {
        if (this.mShadowGenerator == null) {
            this.mShadowGenerator = new ShadowGenerator(this.mIconBitmapSize);
        }
        return this.mShadowGenerator;
    }

    protected UserIconInfo getUserInfo(UserHandle userHandle) {
        int iHashCode = userHandle.hashCode();
        UserIconInfo userIconInfo = (UserIconInfo) this.mCachedUserInfo.get(iHashCode);
        if (userIconInfo != null) {
            return userIconInfo;
        }
        NoopDrawable noopDrawable = new NoopDrawable();
        UserIconInfo userIconInfo2 = new UserIconInfo(userHandle, noopDrawable != this.mPm.getUserBadgedIcon(noopDrawable, userHandle) ? VaultUtils.isVaultProfile(this.mContext, iHashCode) ? 4 : CloneUtils.isCloneAppUserProfile(userHandle, this.mContext) ? 2 : 1 : 0);
        this.mCachedUserInfo.put(iHashCode, userIconInfo2);
        return userIconInfo2;
    }

    public Bitmap getWhiteShadowLayer() {
        resetConfigIfNeed();
        if (this.mWhiteShadowLayer == null) {
            this.mWhiteShadowLayer = createScaledBitmap(new AdaptiveIconDrawable(new ColorDrawable(-1), null), 4);
        }
        return this.mWhiteShadowLayer;
    }

    public BitmapInfo makeDefaultIcon(IconProvider iconProvider) {
        return createBadgedIconBitmap(iconProvider.getFullResDefaultActivityIcon(this.mFullResIconDpi));
    }

    protected Drawable normalizeAndWrapToAdaptiveIcon(Drawable drawable, boolean z, RectF rectF, float[] fArr) {
        float scale;
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof AdaptiveIconDrawable) {
            scale = getNormalizer().getScale(drawable, rectF, null, null);
        } else {
            EmptyWrapper emptyWrapper = new EmptyWrapper();
            AdaptiveIconDrawable adaptiveIconDrawable = new AdaptiveIconDrawable(new ColorDrawable(this.mWrapperBackgroundColor), emptyWrapper);
            adaptiveIconDrawable.setBounds(0, 0, 1, 1);
            boolean[] zArr = new boolean[1];
            scale = getNormalizer().getScale(drawable, rectF, adaptiveIconDrawable.getIconMask(), zArr);
            if (!zArr[0]) {
                emptyWrapper.setDrawable(createScaledDrawable(drawable, scale * LEGACY_ICON_SCALE));
                scale = getNormalizer().getScale(adaptiveIconDrawable, rectF, null, null);
                drawable = adaptiveIconDrawable;
            }
        }
        fArr[0] = scale;
        return drawable;
    }

    public void resetColorExtraction() {
        this.mDisableColorExtractor = false;
    }

    public void setWrapperBackgroundColor(int i) {
        this.mWrapperBackgroundColor = Color.alpha(i) < 255 ? -1 : i;
        if (i != -1) {
            Log.d("CustomAppIcons", "setWrapperBackgroundColor color: " + Integer.toHexString(i) + ", mWrapperBackgroundColor: " + Integer.toHexString(this.mWrapperBackgroundColor) + ", stack: " + Log.getStackTraceString(new Exception()));
        }
    }
}
