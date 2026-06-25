package com.android.systemui.shared.shadow;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextClock;
import com.android.systemui.shared.R$bool;
import com.android.systemui.shared.R$styleable;
import com.android.systemui.shared.shadow.DoubleShadowTextHelper;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoubleShadowTextClock.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DoubleShadowTextClock extends TextClock {
    public static final Companion Companion = new Companion(null);
    private static final int paddingDividedOffset = 2;
    private TypedArray attributesInput;
    private DoubleShadowTextHelper.ShadowInfo mAmbientShadowInfo;
    private DoubleShadowTextHelper.ShadowInfo mKeyShadowInfo;
    private Resources resources;

    /* JADX INFO: compiled from: DoubleShadowTextClock.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DoubleShadowTextClock(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoubleShadowTextClock(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        context.getClass();
        initializeAttributes(attributeSet, i, i2);
    }

    public /* synthetic */ DoubleShadowTextClock(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    private final void initializeAttributes(AttributeSet attributeSet, int i, int i2) {
        TypedArray typedArrayObtainStyledAttributes = this.attributesInput;
        if (typedArrayObtainStyledAttributes == null) {
            typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.DoubleShadowTextClock, i, i2);
            typedArrayObtainStyledAttributes.getClass();
        }
        Resources resources = this.resources;
        if (resources == null) {
            resources = getContext().getResources();
            resources.getClass();
        }
        try {
            this.mKeyShadowInfo = new DoubleShadowTextHelper.ShadowInfo(typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.DoubleShadowTextClock_keyShadowBlur, 0), typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.DoubleShadowTextClock_keyShadowOffsetX, 0), typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.DoubleShadowTextClock_keyShadowOffsetY, 0), typedArrayObtainStyledAttributes.getFloat(R$styleable.DoubleShadowTextClock_keyShadowAlpha, 0.0f));
            this.mAmbientShadowInfo = new DoubleShadowTextHelper.ShadowInfo(typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.DoubleShadowTextClock_ambientShadowBlur, 0), typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.DoubleShadowTextClock_ambientShadowOffsetX, 0), typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.DoubleShadowTextClock_ambientShadowOffsetY, 0), typedArrayObtainStyledAttributes.getFloat(R$styleable.DoubleShadowTextClock_ambientShadowAlpha, 0.0f));
            boolean z = typedArrayObtainStyledAttributes.getBoolean(R$styleable.DoubleShadowTextClock_removeTextDescent, false);
            int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.DoubleShadowTextClock_textDescentExtraPadding, 0);
            if (z) {
                boolean z2 = resources.getBoolean(R$bool.dream_overlay_complication_clock_bottom_padding);
                Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
                setPaddingRelative(0, 0, 0, z2 ? dimensionPixelSize + (((int) Math.floor(fontMetrics.descent)) / paddingDividedOffset) : dimensionPixelSize - ((int) Math.floor(fontMetrics.descent)));
            }
            typedArrayObtainStyledAttributes.recycle();
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onDraw$lambda$0(DoubleShadowTextClock doubleShadowTextClock, Canvas canvas) {
        super.onDraw(canvas);
        return Unit.INSTANCE;
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
        doubleShadowTextHelper.applyShadows(shadowInfo, shadowInfo2, this, canvas, new Function0() { // from class: com.android.systemui.shared.shadow.DoubleShadowTextClock$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return DoubleShadowTextClock.onDraw$lambda$0(this.f$0, canvas);
            }
        });
    }
}
