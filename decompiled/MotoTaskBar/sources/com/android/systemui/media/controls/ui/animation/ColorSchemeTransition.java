package com.android.systemui.media.controls.ui.animation;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.widget.ImageButton;
import com.android.settingslib.Utils;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect;
import com.android.systemui.surfaceeffects.ripple.MultiRippleController;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController;
import com.google.android.material.R$color;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ColorSchemeTransition.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ColorSchemeTransition {
    private final AnimatingColorTransition accentPrimary;
    private final AnimatingColorTransition accentSecondary;
    private final int bgColor;
    private final AnimatingColorTransition colorSeamless;
    private final AnimatingColorTransition[] colorTransitions;
    private final Context context;
    private LoadingEffect loadingEffect;
    private final MediaViewHolder mediaViewHolder;
    private final MultiRippleController multiRippleController;
    private final AnimatingColorTransition surfaceColor;
    private final AnimatingColorTransition textPrimary;
    private final AnimatingColorTransition textPrimaryInverse;
    private final AnimatingColorTransition textSecondary;
    private final AnimatingColorTransition textTertiary;
    private final TurbulenceNoiseController turbulenceNoiseController;

    /* JADX INFO: renamed from: com.android.systemui.media.controls.ui.animation.ColorSchemeTransition$1, reason: invalid class name */
    /* JADX INFO: compiled from: ColorSchemeTransition.kt */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function3 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(3, AnimatingColorTransition.class, "<init>", "<init>(ILkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", 0);
        }

        public final AnimatingColorTransition invoke(int i, Function1 function1, Function1 function12) {
            function1.getClass();
            function12.getClass();
            return new AnimatingColorTransition(i, function1, function12);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return invoke(((Number) obj).intValue(), (Function1) obj2, (Function1) obj3);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ColorSchemeTransition(Context context, MediaViewHolder mediaViewHolder, MultiRippleController multiRippleController, TurbulenceNoiseController turbulenceNoiseController) {
        this(context, mediaViewHolder, multiRippleController, turbulenceNoiseController, AnonymousClass1.INSTANCE);
        context.getClass();
        mediaViewHolder.getClass();
        multiRippleController.getClass();
        turbulenceNoiseController.getClass();
    }

    public ColorSchemeTransition(Context context, MediaViewHolder mediaViewHolder, MultiRippleController multiRippleController, TurbulenceNoiseController turbulenceNoiseController, Function3 function3) {
        context.getClass();
        mediaViewHolder.getClass();
        multiRippleController.getClass();
        turbulenceNoiseController.getClass();
        function3.getClass();
        this.context = context;
        this.mediaViewHolder = mediaViewHolder;
        this.multiRippleController = multiRippleController;
        this.turbulenceNoiseController = turbulenceNoiseController;
        int color = context.getColor(R$color.material_dynamic_neutral20);
        this.bgColor = color;
        AnimatingColorTransition animatingColorTransition = (AnimatingColorTransition) function3.invoke(Integer.valueOf(color), ColorSchemeTransition$surfaceColor$1.INSTANCE, new Function1() { // from class: com.android.systemui.media.controls.ui.animation.ColorSchemeTransition$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ColorSchemeTransition.surfaceColor$lambda$0(this.f$0, ((Integer) obj).intValue());
            }
        });
        this.surfaceColor = animatingColorTransition;
        AnimatingColorTransition animatingColorTransition2 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(R.attr.textColorPrimary)), ColorSchemeTransition$accentPrimary$1.INSTANCE, new Function1() { // from class: com.android.systemui.media.controls.ui.animation.ColorSchemeTransition$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ColorSchemeTransition.accentPrimary$lambda$1(this.f$0, ((Integer) obj).intValue());
            }
        });
        this.accentPrimary = animatingColorTransition2;
        AnimatingColorTransition animatingColorTransition3 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(R.attr.textColorPrimary)), ColorSchemeTransition$accentSecondary$1.INSTANCE, new Function1() { // from class: com.android.systemui.media.controls.ui.animation.ColorSchemeTransition$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ColorSchemeTransition.accentSecondary$lambda$3(this.f$0, ((Integer) obj).intValue());
            }
        });
        this.accentSecondary = animatingColorTransition3;
        AnimatingColorTransition animatingColorTransition4 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(R.attr.textColorPrimary)), new Function1() { // from class: com.android.systemui.media.controls.ui.animation.ColorSchemeTransition$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Integer.valueOf(ColorSchemeTransition.colorSeamless$lambda$4(this.f$0, (ColorScheme) obj));
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.ui.animation.ColorSchemeTransition$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ColorSchemeTransition.colorSeamless$lambda$5(this.f$0, ((Integer) obj).intValue());
            }
        });
        this.colorSeamless = animatingColorTransition4;
        AnimatingColorTransition animatingColorTransition5 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(R.attr.textColorPrimary)), ColorSchemeTransition$textPrimary$1.INSTANCE, new Function1() { // from class: com.android.systemui.media.controls.ui.animation.ColorSchemeTransition$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ColorSchemeTransition.textPrimary$lambda$6(this.f$0, ((Integer) obj).intValue());
            }
        });
        this.textPrimary = animatingColorTransition5;
        AnimatingColorTransition animatingColorTransition6 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(R.attr.textColorPrimaryInverse)), ColorSchemeTransition$textPrimaryInverse$1.INSTANCE, new Function1() { // from class: com.android.systemui.media.controls.ui.animation.ColorSchemeTransition$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ColorSchemeTransition.textPrimaryInverse$lambda$7(this.f$0, ((Integer) obj).intValue());
            }
        });
        this.textPrimaryInverse = animatingColorTransition6;
        AnimatingColorTransition animatingColorTransition7 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(R.attr.textColorSecondary)), ColorSchemeTransition$textSecondary$1.INSTANCE, new Function1() { // from class: com.android.systemui.media.controls.ui.animation.ColorSchemeTransition$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ColorSchemeTransition.textSecondary$lambda$8(this.f$0, ((Integer) obj).intValue());
            }
        });
        this.textSecondary = animatingColorTransition7;
        AnimatingColorTransition animatingColorTransition8 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(R.attr.textColorTertiary)), ColorSchemeTransition$textTertiary$1.INSTANCE, new Function1() { // from class: com.android.systemui.media.controls.ui.animation.ColorSchemeTransition$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ColorSchemeTransition.textTertiary$lambda$9(this.f$0, ((Integer) obj).intValue());
            }
        });
        this.textTertiary = animatingColorTransition8;
        this.colorTransitions = new AnimatingColorTransition[]{animatingColorTransition, animatingColorTransition4, animatingColorTransition2, animatingColorTransition3, animatingColorTransition5, animatingColorTransition6, animatingColorTransition7, animatingColorTransition8};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit accentPrimary$lambda$1(ColorSchemeTransition colorSchemeTransition, int i) {
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(i);
        colorStateListValueOf.getClass();
        colorSchemeTransition.mediaViewHolder.getActionPlayPause().setBackgroundTintList(colorStateListValueOf);
        colorSchemeTransition.mediaViewHolder.getGutsViewHolder().setAccentPrimaryColor(i);
        colorSchemeTransition.multiRippleController.updateColor(i);
        colorSchemeTransition.turbulenceNoiseController.updateNoiseColor(i);
        LoadingEffect loadingEffect = colorSchemeTransition.loadingEffect;
        if (loadingEffect != null) {
            loadingEffect.updateColor(i);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit accentSecondary$lambda$3(ColorSchemeTransition colorSchemeTransition, int i) {
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(i);
        colorStateListValueOf.getClass();
        Drawable background = colorSchemeTransition.mediaViewHolder.getSeamlessButton().getBackground();
        RippleDrawable rippleDrawable = background instanceof RippleDrawable ? (RippleDrawable) background : null;
        if (rippleDrawable != null) {
            rippleDrawable.setColor(colorStateListValueOf);
            rippleDrawable.setEffectColor(colorStateListValueOf);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int colorSeamless$lambda$4(ColorSchemeTransition colorSchemeTransition, ColorScheme colorScheme) {
        colorScheme.getClass();
        return (colorSchemeTransition.context.getResources().getConfiguration().uiMode & 48) == 32 ? colorScheme.getAccent1().getS100() : colorScheme.getAccent1().getS200();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit colorSeamless$lambda$5(ColorSchemeTransition colorSchemeTransition, int i) {
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(i);
        colorStateListValueOf.getClass();
        colorSchemeTransition.mediaViewHolder.getSeamlessButton().setBackgroundTintList(colorStateListValueOf);
        return Unit.INSTANCE;
    }

    private final int loadDefaultColor(int i) {
        return Utils.getColorAttr(this.context, i).getDefaultColor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit surfaceColor$lambda$0(ColorSchemeTransition colorSchemeTransition, int i) {
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(i);
        colorStateListValueOf.getClass();
        colorSchemeTransition.mediaViewHolder.getSeamlessIcon().setImageTintList(colorStateListValueOf);
        colorSchemeTransition.mediaViewHolder.getSeamlessText().setTextColor(i);
        colorSchemeTransition.mediaViewHolder.getAlbumView().setBackgroundTintList(colorStateListValueOf);
        colorSchemeTransition.mediaViewHolder.getGutsViewHolder().setSurfaceColor(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit textPrimary$lambda$6(ColorSchemeTransition colorSchemeTransition, int i) {
        colorSchemeTransition.mediaViewHolder.getTitleText().setTextColor(i);
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(i);
        colorStateListValueOf.getClass();
        colorSchemeTransition.mediaViewHolder.getSeekBar().getThumb().setTintList(colorStateListValueOf);
        colorSchemeTransition.mediaViewHolder.getSeekBar().setProgressTintList(colorStateListValueOf);
        colorSchemeTransition.mediaViewHolder.getScrubbingElapsedTimeView().setTextColor(colorStateListValueOf);
        colorSchemeTransition.mediaViewHolder.getScrubbingTotalTimeView().setTextColor(colorStateListValueOf);
        Iterator it = colorSchemeTransition.mediaViewHolder.getTransparentActionButtons().iterator();
        while (it.hasNext()) {
            ((ImageButton) it.next()).setImageTintList(colorStateListValueOf);
        }
        colorSchemeTransition.mediaViewHolder.getGutsViewHolder().setTextPrimaryColor(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit textPrimaryInverse$lambda$7(ColorSchemeTransition colorSchemeTransition, int i) {
        colorSchemeTransition.mediaViewHolder.getActionPlayPause().setImageTintList(ColorStateList.valueOf(i));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit textSecondary$lambda$8(ColorSchemeTransition colorSchemeTransition, int i) {
        colorSchemeTransition.mediaViewHolder.getArtistText().setTextColor(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit textTertiary$lambda$9(ColorSchemeTransition colorSchemeTransition, int i) {
        colorSchemeTransition.mediaViewHolder.getSeekBar().setProgressBackgroundTintList(ColorStateList.valueOf(i));
        return Unit.INSTANCE;
    }

    public final AnimatingColorTransition getAccentPrimary() {
        return this.accentPrimary;
    }

    public final void setLoadingEffect(LoadingEffect loadingEffect) {
        this.loadingEffect = loadingEffect;
    }

    public final boolean updateColorScheme(ColorScheme colorScheme) {
        boolean z = false;
        for (AnimatingColorTransition animatingColorTransition : this.colorTransitions) {
            boolean zUpdateColorScheme = animatingColorTransition.updateColorScheme(colorScheme);
            if (!Intrinsics.areEqual(animatingColorTransition, this.colorSeamless)) {
                z = zUpdateColorScheme || z;
            }
        }
        if (colorScheme != null) {
            this.mediaViewHolder.getGutsViewHolder().setColorScheme(colorScheme);
        }
        return z;
    }
}
