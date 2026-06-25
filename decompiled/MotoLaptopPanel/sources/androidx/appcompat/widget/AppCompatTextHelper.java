package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.LocaleList;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.collection.LruCache;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Pair;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
class AppCompatTextHelper {
    private boolean mAsyncFontPending;
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableStartTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private final TextView mView;
    private int mStyle = 0;
    private int mFontWeight = -1;
    private String mFontVariationSettings = null;

    abstract class Api24Impl {
        static LocaleList forLanguageTags(String str) {
            return LocaleList.forLanguageTags(str);
        }

        static void setTextLocales(TextView textView, LocaleList localeList) {
            textView.setTextLocales(localeList);
        }
    }

    abstract class Api26Impl {
        private static Paint sPaint;
        private static final LruCache sVariationsCache = new LruCache(30);

        static Typeface createVariationInstance(Typeface typeface, String str) {
            Pair pair = new Pair(typeface, str);
            LruCache lruCache = sVariationsCache;
            Typeface typeface2 = (Typeface) lruCache.get(pair);
            if (typeface2 != null) {
                return typeface2;
            }
            Paint paint = sPaint;
            if (paint == null) {
                paint = new Paint();
                sPaint = paint;
            }
            if (Objects.equals(paint.getFontVariationSettings(), str)) {
                paint.setFontVariationSettings(null);
            }
            paint.setTypeface(typeface);
            if (!paint.setFontVariationSettings(str)) {
                return null;
            }
            Typeface typeface3 = paint.getTypeface();
            lruCache.put(pair, typeface3);
            return typeface3;
        }

        static int getAutoSizeStepGranularity(TextView textView) {
            return textView.getAutoSizeStepGranularity();
        }

        static String getFontVariationSettings(TextView textView) {
            return textView.getFontVariationSettings();
        }

        static void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int i, int i2, int i3, int i4) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
        }

        static void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, int[] iArr, int i) {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
        }

        static boolean setFontVariationSettings(TextView textView, String str) {
            if (Objects.equals(textView.getFontVariationSettings(), str)) {
                textView.setFontVariationSettings("");
            }
            return textView.setFontVariationSettings(str);
        }
    }

    abstract class Api28Impl {
        static Typeface create(Typeface typeface, int i, boolean z) {
            return Typeface.create(typeface, i, z);
        }
    }

    AppCompatTextHelper(TextView textView) {
        this.mView = textView;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(textView);
    }

    private void applyCompoundDrawableTint(Drawable drawable, TintInfo tintInfo) {
        if (drawable == null || tintInfo == null) {
            return;
        }
        AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState());
    }

    private void applyFontAndVariationSettings(boolean z) {
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            if (this.mFontWeight == -1) {
                this.mView.setTypeface(typeface, this.mStyle);
            } else {
                this.mView.setTypeface(typeface);
            }
        } else if (z) {
            this.mView.setTypeface(null);
        }
        String str = this.mFontVariationSettings;
        if (str != null) {
            Api26Impl.setFontVariationSettings(this.mView, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void applyNewTypefacePreservingVariationSettings(TextView textView, Typeface typeface, int i) {
        String fontVariationSettings = Api26Impl.getFontVariationSettings(textView);
        if (!TextUtils.isEmpty(fontVariationSettings)) {
            Api26Impl.setFontVariationSettings(textView, null);
        }
        textView.setTypeface(typeface, i);
        if (TextUtils.isEmpty(fontVariationSettings)) {
            return;
        }
        Api26Impl.setFontVariationSettings(textView, fontVariationSettings);
    }

    private static TintInfo createTintInfo(Context context, AppCompatDrawableManager appCompatDrawableManager, int i) {
        ColorStateList tintList = appCompatDrawableManager.getTintList(context, i);
        if (tintList == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = tintList;
        return tintInfo;
    }

    private ResourcesCompat.FontCallback makeFontCallback(final int i, final int i2) {
        final WeakReference weakReference = new WeakReference(this.mView);
        return new ResourcesCompat.FontCallback() { // from class: androidx.appcompat.widget.AppCompatTextHelper.1
            @Override // androidx.core.content.res.ResourcesCompat.FontCallback
            public void onFontRetrievalFailed(int i3) {
            }

            @Override // androidx.core.content.res.ResourcesCompat.FontCallback
            public void onFontRetrieved(Typeface typeface) {
                int i3 = i;
                if (i3 != -1) {
                    typeface = Api28Impl.create(typeface, i3, (i2 & 2) != 0);
                }
                AppCompatTextHelper.this.onAsyncTypefaceReceived(weakReference, typeface);
            }
        };
    }

    private void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, Drawable drawable6) {
        if (drawable5 != null || drawable6 != null) {
            Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
            if (drawable5 == null) {
                drawable5 = compoundDrawablesRelative[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative[1];
            }
            if (drawable6 == null) {
                drawable6 = compoundDrawablesRelative[2];
            }
            TextView textView = this.mView;
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative[3];
            }
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable2, drawable6, drawable4);
            return;
        }
        if (drawable == null && drawable2 == null && drawable3 == null && drawable4 == null) {
            return;
        }
        Drawable[] compoundDrawablesRelative2 = this.mView.getCompoundDrawablesRelative();
        Drawable drawable7 = compoundDrawablesRelative2[0];
        if (drawable7 != null || compoundDrawablesRelative2[2] != null) {
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative2[1];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative2[3];
            }
            this.mView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable7, drawable2, compoundDrawablesRelative2[2], drawable4);
            return;
        }
        Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
        TextView textView2 = this.mView;
        if (drawable == null) {
            drawable = compoundDrawables[0];
        }
        if (drawable2 == null) {
            drawable2 = compoundDrawables[1];
        }
        if (drawable3 == null) {
            drawable3 = compoundDrawables[2];
        }
        if (drawable4 == null) {
            drawable4 = compoundDrawables[3];
        }
        textView2.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
    }

    private void setTextSizeInternal(int i, float f) {
        this.mAutoSizeTextHelper.setTextSizeInternal(i, f);
    }

    private boolean updateTypefaceAndStyle(Context context, TintTypedArray tintTypedArray) {
        String string;
        Typeface typeface;
        this.mStyle = tintTypedArray.getInt(R$styleable.TextAppearance_android_textStyle, this.mStyle);
        int i = tintTypedArray.getInt(R$styleable.TextAppearance_android_textFontWeight, -1);
        this.mFontWeight = i;
        if (i != -1) {
            this.mStyle &= 2;
        }
        int i2 = R$styleable.TextAppearance_fontVariationSettings;
        if (tintTypedArray.hasValue(i2)) {
            this.mFontVariationSettings = tintTypedArray.getString(i2);
        }
        int i3 = R$styleable.TextAppearance_android_fontFamily;
        if (!tintTypedArray.hasValue(i3) && !tintTypedArray.hasValue(R$styleable.TextAppearance_fontFamily)) {
            int i4 = R$styleable.TextAppearance_android_typeface;
            if (!tintTypedArray.hasValue(i4)) {
                int i5 = this.mFontWeight;
                if (i5 == -1 || (typeface = this.mFontTypeface) == null) {
                    return false;
                }
                this.mFontTypeface = Api28Impl.create(typeface, i5, (this.mStyle & 2) != 0);
                return true;
            }
            this.mAsyncFontPending = false;
            int i6 = tintTypedArray.getInt(i4, 1);
            if (i6 == 1) {
                this.mFontTypeface = Typeface.SANS_SERIF;
            } else if (i6 == 2) {
                this.mFontTypeface = Typeface.SERIF;
            } else if (i6 == 3) {
                this.mFontTypeface = Typeface.MONOSPACE;
            }
            return true;
        }
        this.mFontTypeface = null;
        int i7 = R$styleable.TextAppearance_fontFamily;
        if (tintTypedArray.hasValue(i7)) {
            i3 = i7;
        }
        int i8 = this.mFontWeight;
        int i9 = this.mStyle;
        if (!context.isRestricted()) {
            try {
                Typeface font = tintTypedArray.getFont(i3, this.mStyle, makeFontCallback(i8, i9));
                if (font != null) {
                    if (this.mFontWeight != -1) {
                        this.mFontTypeface = Api28Impl.create(Typeface.create(font, 0), this.mFontWeight, (this.mStyle & 2) != 0);
                    } else {
                        this.mFontTypeface = font;
                    }
                }
                this.mAsyncFontPending = this.mFontTypeface == null;
            } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
            }
        }
        if (this.mFontTypeface == null && (string = tintTypedArray.getString(i3)) != null) {
            if (this.mFontWeight != -1) {
                this.mFontTypeface = Api28Impl.create(Typeface.create(string, 0), this.mFontWeight, (this.mStyle & 2) != 0);
            } else {
                this.mFontTypeface = Typeface.create(string, this.mStyle);
            }
        }
        return true;
    }

    void applyCompoundDrawablesTints() {
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
        if (this.mDrawableStartTint == null && this.mDrawableEndTint == null) {
            return;
        }
        Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
        applyCompoundDrawableTint(compoundDrawablesRelative[0], this.mDrawableStartTint);
        applyCompoundDrawableTint(compoundDrawablesRelative[2], this.mDrawableEndTint);
    }

    void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }

    int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void loadFromAttributes(android.util.AttributeSet r14, int r15) {
        /*
            Method dump skipped, instruction units count: 588
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatTextHelper.loadFromAttributes(android.util.AttributeSet, int):void");
    }

    void onAsyncTypefaceReceived(WeakReference weakReference, final Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mFontTypeface = typeface;
            final TextView textView = (TextView) weakReference.get();
            if (textView != null) {
                if (!textView.isAttachedToWindow()) {
                    applyNewTypefacePreservingVariationSettings(textView, typeface, this.mStyle);
                } else {
                    final int i = this.mStyle;
                    textView.post(new Runnable() { // from class: androidx.appcompat.widget.AppCompatTextHelper.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AppCompatTextHelper.applyNewTypefacePreservingVariationSettings(textView, typeface, i);
                        }
                    });
                }
            }
        }
    }

    void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (ViewUtils.SDK_LEVEL_SUPPORTS_AUTOSIZE) {
            return;
        }
        autoSizeText();
    }

    void onSetCompoundDrawables() {
        applyCompoundDrawablesTints();
    }

    void onSetTextAppearance(Context context, int i) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, R$styleable.TextAppearance);
        int i2 = R$styleable.TextAppearance_textAllCaps;
        if (tintTypedArrayObtainStyledAttributes.hasValue(i2)) {
            setAllCaps(tintTypedArrayObtainStyledAttributes.getBoolean(i2, false));
        }
        int i3 = R$styleable.TextAppearance_android_textSize;
        if (tintTypedArrayObtainStyledAttributes.hasValue(i3) && tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(i3, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        boolean zUpdateTypefaceAndStyle = updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes);
        tintTypedArrayObtainStyledAttributes.recycle();
        applyFontAndVariationSettings(zUpdateTypefaceAndStyle);
    }

    void populateSurroundingTextIfNeeded(TextView textView, InputConnection inputConnection, EditorInfo editorInfo) {
    }

    void setAllCaps(boolean z) {
        this.mView.setAllCaps(z);
    }

    void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
    }

    void setAutoSizeTextTypeUniformWithPresetSizes(int[] iArr, int i) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
    }

    void setAutoSizeTextTypeWithDefaults(int i) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(i);
    }

    void setTextSize(int i, float f) {
        if (ViewUtils.SDK_LEVEL_SUPPORTS_AUTOSIZE || isAutoSizeEnabled()) {
            return;
        }
        setTextSizeInternal(i, f);
    }
}
