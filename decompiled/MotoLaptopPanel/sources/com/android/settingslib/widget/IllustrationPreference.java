package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.preference.Preference;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback;
import com.airbnb.lottie.R$styleable;
import com.android.settingslib.widget.preference.illustration.R$dimen;
import com.android.settingslib.widget.preference.illustration.R$layout;

/* JADX INFO: loaded from: classes.dex */
public class IllustrationPreference extends Preference {
    protected int fallbackResId;
    private final Animatable2.AnimationCallback mAnimationCallback;
    private final Animatable2Compat$AnimationCallback mAnimationCallbackCompat;
    private boolean mCacheComposition;
    private int mImageResId;
    private boolean mIsAutoScale;
    private boolean mIsTablet;
    private boolean mLottieDynamicColor;
    private int mMaxHeight;

    public IllustrationPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.fallbackResId = 0;
        this.mAnimationCallback = new Animatable2.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        this.mAnimationCallbackCompat = new Animatable2Compat$AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback
            public void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        setLayoutResource(R$layout.illustration_preference);
        boolean z = false;
        setSelectable(false);
        this.mIsAutoScale = false;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.LottieAnimationView, 0, 0);
            this.mImageResId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.LottieAnimationView_lottie_rawRes, 0);
            this.mCacheComposition = typedArrayObtainStyledAttributes.getBoolean(R$styleable.LottieAnimationView_lottie_cacheComposition, true);
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.settingslib.widget.preference.illustration.R$styleable.IllustrationPreference, 0, 0);
            this.mLottieDynamicColor = typedArrayObtainStyledAttributes2.getBoolean(com.android.settingslib.widget.preference.illustration.R$styleable.IllustrationPreference_dynamicColor, false);
            typedArrayObtainStyledAttributes2.recycle();
        }
        if (SettingsThemeHelper.isExpressiveTheme(context) && SettingsThemeHelper.isTablet(context)) {
            z = true;
        }
        this.mIsTablet = z;
        if (z) {
            setMaxHeight(context.getResources().getDimensionPixelSize(R$dimen.settingslib_illustration_height_tablet));
        }
    }

    public void setMaxHeight(int i) {
        if (i != this.mMaxHeight) {
            this.mMaxHeight = i;
            notifyChanged();
        }
    }
}
