package com.android.systemui.shared.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.shared.R$styleable;
import com.android.systemui.shared.shadow.DoubleShadowTextHelper;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoubleShadowTextView.kt */
/* JADX INFO: loaded from: classes.dex */
public class DoubleShadowTextView extends TextView {
    private DoubleShadowTextHelper.ShadowInfo mAmbientShadowInfo;
    private DoubleShadowTextHelper.ShadowInfo mKeyShadowInfo;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DoubleShadowTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoubleShadowTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        context.getClass();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.DoubleShadowTextView, i, i2);
        typedArrayObtainStyledAttributes.getClass();
        updateShadowDrawables(typedArrayObtainStyledAttributes);
    }

    public /* synthetic */ DoubleShadowTextView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onDraw$lambda$0(DoubleShadowTextView doubleShadowTextView, Canvas canvas) {
        super.onDraw(canvas);
        return Unit.INSTANCE;
    }

    private final void updateShadowDrawables(TypedArray typedArray) {
        try {
            this.mKeyShadowInfo = new DoubleShadowTextHelper.ShadowInfo(typedArray.getDimension(R$styleable.DoubleShadowTextView_keyShadowBlur, 0.0f), typedArray.getDimension(R$styleable.DoubleShadowTextView_keyShadowOffsetX, 0.0f), typedArray.getDimension(R$styleable.DoubleShadowTextView_keyShadowOffsetY, 0.0f), typedArray.getFloat(R$styleable.DoubleShadowTextView_keyShadowAlpha, 0.0f));
            this.mAmbientShadowInfo = new DoubleShadowTextHelper.ShadowInfo(typedArray.getDimension(R$styleable.DoubleShadowTextView_ambientShadowBlur, 0.0f), typedArray.getDimension(R$styleable.DoubleShadowTextView_ambientShadowOffsetX, 0.0f), typedArray.getDimension(R$styleable.DoubleShadowTextView_ambientShadowOffsetY, 0.0f), typedArray.getFloat(R$styleable.DoubleShadowTextView_ambientShadowAlpha, 0.0f));
            int dimensionPixelSize = typedArray.getDimensionPixelSize(R$styleable.DoubleShadowTextView_drawableIconSize, 0);
            int dimensionPixelSize2 = typedArray.getDimensionPixelSize(R$styleable.DoubleShadowTextView_drawableIconInsetSize, 0);
            typedArray.recycle();
            Drawable[] drawableArr = new Drawable[4];
            drawableArr[0] = null;
            drawableArr[1] = null;
            drawableArr[2] = null;
            drawableArr[3] = null;
            Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
            compoundDrawablesRelative.getClass();
            int length = compoundDrawablesRelative.length;
            for (int i = 0; i < length; i++) {
                Drawable drawable = compoundDrawablesRelative[i];
                if (drawable != null) {
                    DoubleShadowTextHelper.ShadowInfo shadowInfo = this.mKeyShadowInfo;
                    if (shadowInfo == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mKeyShadowInfo");
                        shadowInfo = null;
                    }
                    DoubleShadowTextHelper.ShadowInfo shadowInfo2 = this.mAmbientShadowInfo;
                    if (shadowInfo2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mAmbientShadowInfo");
                        shadowInfo2 = null;
                    }
                    drawableArr[i] = new DoubleShadowIconDrawable(shadowInfo, shadowInfo2, drawable, dimensionPixelSize, dimensionPixelSize2);
                }
            }
            setCompoundDrawablesRelative(drawableArr[0], drawableArr[1], drawableArr[2], drawableArr[3]);
        } catch (Throwable th) {
            typedArray.recycle();
            throw th;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(final Canvas canvas) {
        canvas.getClass();
        DoubleShadowTextHelper doubleShadowTextHelper = DoubleShadowTextHelper.INSTANCE;
        DoubleShadowTextHelper.ShadowInfo shadowInfo = this.mKeyShadowInfo;
        DoubleShadowTextHelper.ShadowInfo shadowInfo2 = null;
        if (shadowInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mKeyShadowInfo");
            shadowInfo = null;
        }
        DoubleShadowTextHelper.ShadowInfo shadowInfo3 = this.mAmbientShadowInfo;
        if (shadowInfo3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAmbientShadowInfo");
        } else {
            shadowInfo2 = shadowInfo3;
        }
        doubleShadowTextHelper.applyShadows(shadowInfo, shadowInfo2, this, canvas, new Function0() { // from class: com.android.systemui.shared.shadow.DoubleShadowTextView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return DoubleShadowTextView.onDraw$lambda$0(this.f$0, canvas);
            }
        });
    }

    @Override // android.widget.TextView
    public void setTextAppearance(int i) {
        super.setTextAppearance(i);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(i, R$styleable.DoubleShadowTextView);
        typedArrayObtainStyledAttributes.getClass();
        updateShadowDrawables(typedArrayObtainStyledAttributes);
    }
}
