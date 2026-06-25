package com.motorola.laptoppanel.ui.compose;

import android.content.Context;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SliderColors;
import androidx.compose.material3.SliderDefaults;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.CacheDrawScope;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.draw.DrawResult;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.unit.Dp;
import com.motorola.laptoppanel.ui.compose.SliderKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SliderKt {

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.compose.SliderKt$Slider$6, reason: invalid class name */
    /* JADX INFO: compiled from: Slider.kt */
    final class AnonymousClass6 implements Function3 {
        final /* synthetic */ long $activeIconColor;
        final /* synthetic */ SliderColors $colors;
        final /* synthetic */ long $inactiveIconColor;
        final /* synthetic */ Function4 $trackIcon;

        AnonymousClass6(long j, long j2, SliderColors sliderColors, Function4 function4) {
            this.$activeIconColor = j;
            this.$inactiveIconColor = j2;
            this.$colors = sliderColors;
            this.$trackIcon = function4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean invoke$lambda$1(MutableState mutableState) {
            return ((Boolean) mutableState.getValue()).booleanValue();
        }

        private static final void invoke$lambda$2(MutableState mutableState, boolean z) {
            mutableState.setValue(Boolean.valueOf(z));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invoke$lambda$7$lambda$6(SliderState sliderState, Function4 function4, long j, Animatable animatable, long j2, Animatable animatable2, MutableState mutableState, ContentDrawScope contentDrawScope) {
            contentDrawScope.getClass();
            contentDrawScope.drawContent();
            float f = 2;
            float fIntBitsToFloat = Float.intBitsToFloat((int) (contentDrawScope.mo1082getSizeNHjbRc() & 4294967295L)) / f;
            Dimensions dimensions = Dimensions.INSTANCE;
            float fIntBitsToFloat2 = fIntBitsToFloat - (Float.intBitsToFloat((int) (contentDrawScope.mo147toSizeXkaWNTQ(dimensions.m2144getIconSizeMYxV2XQ()) & 4294967295L)) / f);
            float fIntBitsToFloat3 = (Float.intBitsToFloat((int) (contentDrawScope.mo1082getSizeNHjbRc() >> 32)) * sliderState.getCoercedValueAsFraction()) - contentDrawScope.mo146toPx0680j_4(dimensions.m2148getThumbTrackGapSizeD9Ej5fM());
            float fMo146toPx0680j_4 = (contentDrawScope.mo146toPx0680j_4(dimensions.m2148getThumbTrackGapSizeD9Ej5fM()) * f) + fIntBitsToFloat3;
            float fIntBitsToFloat4 = Float.intBitsToFloat((int) (contentDrawScope.mo1082getSizeNHjbRc() >> 32)) - fMo146toPx0680j_4;
            if (Float.intBitsToFloat((int) (contentDrawScope.mo147toSizeXkaWNTQ(dimensions.m2144getIconSizeMYxV2XQ()) >> 32)) < (fIntBitsToFloat3 - 0.0f) - (contentDrawScope.mo146toPx0680j_4(dimensions.m2143getIconPaddingD9Ej5fM()) * f)) {
                invoke$lambda$2(mutableState, true);
                function4.invoke(contentDrawScope, Offset.m751boximpl(Offset.m752constructorimpl((((long) Float.floatToRawIntBits(fIntBitsToFloat2)) & 4294967295L) | (Float.floatToRawIntBits(0.0f) << 32))), Color.m876boximpl(j), animatable.getValue());
            } else if (Float.intBitsToFloat((int) (contentDrawScope.mo147toSizeXkaWNTQ(dimensions.m2144getIconSizeMYxV2XQ()) >> 32)) < fIntBitsToFloat4 - (contentDrawScope.mo146toPx0680j_4(dimensions.m2143getIconPaddingD9Ej5fM()) * f)) {
                invoke$lambda$2(mutableState, false);
                function4.invoke(contentDrawScope, Offset.m751boximpl(Offset.m752constructorimpl((((long) Float.floatToRawIntBits(fIntBitsToFloat2)) & 4294967295L) | (Float.floatToRawIntBits(fMo146toPx0680j_4) << 32))), Color.m876boximpl(j2), animatable2.getValue());
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            invoke((SliderState) obj, (Composer) obj2, ((Number) obj3).intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(final SliderState sliderState, Composer composer, int i) {
            int i2;
            Object obj;
            int i3;
            sliderState.getClass();
            if ((i & 6) == 0) {
                i2 = i | ((i & 8) == 0 ? composer.changed(sliderState) : composer.changedInstance(sliderState) ? 4 : 2);
            } else {
                i2 = i;
            }
            if ((i2 & 19) == 18 && composer.getSkipping()) {
                composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(169296218, i2, -1, "com.motorola.laptoppanel.ui.compose.Slider.<anonymous> (Slider.kt:159)");
            }
            composer.startReplaceGroup(2079629473);
            Object objRememberedValue = composer.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (objRememberedValue == companion.getEmpty()) {
                objRememberedValue = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(Boolean.TRUE, null, 2, null);
                composer.updateRememberedValue(objRememberedValue);
            }
            final MutableState mutableState = (MutableState) objRememberedValue;
            composer.endReplaceGroup();
            composer.startReplaceGroup(2079632151);
            Object objRememberedValue2 = composer.rememberedValue();
            if (objRememberedValue2 == companion.getEmpty()) {
                Object animatable = new Animatable(Float.valueOf(1.0f), VectorConvertersKt.getVectorConverter(FloatCompanionObject.INSTANCE), null, "iconActiveAlpha", 4, null);
                composer.updateRememberedValue(animatable);
                objRememberedValue2 = animatable;
            }
            final Animatable animatable2 = (Animatable) objRememberedValue2;
            composer.endReplaceGroup();
            composer.startReplaceGroup(2079640569);
            Object objRememberedValue3 = composer.rememberedValue();
            if (objRememberedValue3 == companion.getEmpty()) {
                Object animatable3 = new Animatable(Float.valueOf(0.0f), VectorConvertersKt.getVectorConverter(FloatCompanionObject.INSTANCE), null, "iconInactiveAlpha", 4, null);
                composer.updateRememberedValue(animatable3);
                objRememberedValue3 = animatable3;
            }
            final Animatable animatable4 = (Animatable) objRememberedValue3;
            composer.endReplaceGroup();
            Boolean boolValueOf = Boolean.valueOf(invoke$lambda$1(mutableState));
            composer.startReplaceGroup(2079650898);
            boolean zChangedInstance = composer.changedInstance(animatable2) | composer.changedInstance(animatable4);
            Object objRememberedValue4 = composer.rememberedValue();
            if (zChangedInstance || objRememberedValue4 == companion.getEmpty()) {
                objRememberedValue4 = new SliderKt$Slider$6$1$1(mutableState, animatable2, animatable4, null);
                composer.updateRememberedValue(objRememberedValue4);
            }
            composer.endReplaceGroup();
            int i4 = Animatable.$stable;
            EffectsKt.LaunchedEffect(animatable2, animatable4, boolValueOf, (Function2) objRememberedValue4, composer, (i4 << 3) | i4);
            SliderDefaults sliderDefaults = SliderDefaults.INSTANCE;
            Modifier modifierM172height3ABfNKs = SizeKt.m172height3ABfNKs(Modifier.Companion, Dp.m1877constructorimpl(40));
            composer.startReplaceGroup(2079671051);
            int i5 = i2 & 14;
            boolean zChanged = (i5 == 4 || ((i2 & 8) != 0 && composer.changedInstance(sliderState))) | composer.changed(this.$activeIconColor) | composer.changedInstance(animatable2) | composer.changed(this.$inactiveIconColor) | composer.changedInstance(animatable4);
            final Function4 function4 = this.$trackIcon;
            final long j = this.$activeIconColor;
            final long j2 = this.$inactiveIconColor;
            Object objRememberedValue5 = composer.rememberedValue();
            if (zChanged || objRememberedValue5 == companion.getEmpty()) {
                i3 = i5;
                obj = new Function1() { // from class: com.motorola.laptoppanel.ui.compose.SliderKt$Slider$6$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        return SliderKt.AnonymousClass6.invoke$lambda$7$lambda$6(sliderState, function4, j, animatable2, j2, animatable4, mutableState, (ContentDrawScope) obj2);
                    }
                };
                composer.updateRememberedValue(obj);
            } else {
                obj = objRememberedValue5;
                i3 = i5;
            }
            composer.endReplaceGroup();
            Modifier modifierDrawWithContent = DrawModifierKt.drawWithContent(modifierM172height3ABfNKs, (Function1) obj);
            Dimensions dimensions = Dimensions.INSTANCE;
            float fM2147getSliderTrackRoundedCornerD9Ej5fM = dimensions.m2147getSliderTrackRoundedCornerD9Ej5fM();
            float fM1877constructorimpl = Dp.m1877constructorimpl(2);
            float fM2148getThumbTrackGapSizeD9Ej5fM = dimensions.m2148getThumbTrackGapSizeD9Ej5fM();
            SliderColors sliderColors = this.$colors;
            composer.startReplaceGroup(2079735433);
            Object objRememberedValue6 = composer.rememberedValue();
            if (objRememberedValue6 == companion.getEmpty()) {
                objRememberedValue6 = new Function3() { // from class: com.motorola.laptoppanel.ui.compose.SliderKt$Slider$6$3$1
                    @Override // kotlin.jvm.functions.Function3
                    public /* bridge */ /* synthetic */ Object invoke(Object obj2, Object obj3, Object obj4) {
                        m2154invokewPWG1Vc((DrawScope) obj2, ((Offset) obj3).m767unboximpl(), ((Color) obj4).m890unboximpl());
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke-wPWG1Vc, reason: not valid java name */
                    public final void m2154invokewPWG1Vc(DrawScope drawScope, long j3, long j4) {
                        drawScope.getClass();
                    }
                };
                composer.updateRememberedValue(objRememberedValue6);
            }
            composer.endReplaceGroup();
            sliderDefaults.m327TrackmnvyFg4(sliderState, fM2147getSliderTrackRoundedCornerD9Ej5fM, modifierDrawWithContent, false, sliderColors, null, (Function3) objRememberedValue6, fM2148getThumbTrackGapSizeD9Ej5fM, fM1877constructorimpl, composer, i3 | 920322096 | SliderState.$stable, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x031c  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0325  */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void Slider(final com.motorola.laptoppanel.ui.compose.SliderModel r29, androidx.compose.ui.Modifier r30, androidx.compose.runtime.Composer r31, final int r32, final int r33) {
        /*
            Method dump skipped, instruction units count: 814
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.compose.SliderKt.Slider(com.motorola.laptoppanel.ui.compose.SliderModel, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float Slider$lambda$1(State state) {
        return ((Number) state.getValue()).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int Slider$lambda$10$lambda$9(Ref$ObjectRef ref$ObjectRef, Ref$ObjectRef ref$ObjectRef2, SliderModel sliderModel, MutableFloatState mutableFloatState) {
        return ((Number) ((Function2) ref$ObjectRef2.element).invoke(sliderModel, Float.valueOf(((mutableFloatState.getFloatValue() - ((Number) ((ClosedFloatingPointRange) ref$ObjectRef.element).getStart()).floatValue()) * 100.0f) / (((Number) ((ClosedFloatingPointRange) ref$ObjectRef.element).getEndInclusive()).floatValue() - ((Number) ((ClosedFloatingPointRange) ref$ObjectRef.element).getStart()).floatValue())))).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int Slider$lambda$11(State state) {
        return ((Number) state.getValue()).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Painter Slider$lambda$13(State state) {
        return (Painter) state.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean Slider$lambda$16(MutableState mutableState) {
        return ((Boolean) mutableState.getValue()).booleanValue();
    }

    private static final void Slider$lambda$17(MutableState mutableState, boolean z) {
        mutableState.setValue(Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit Slider$lambda$19$lambda$18(CoroutineScope coroutineScope, MutableState mutableState, SliderModel sliderModel, float f) {
        Slider$lambda$17(mutableState, true);
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new SliderKt$Slider$onDrag$1$1$1(sliderModel, f, null), 3, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit Slider$lambda$21$lambda$20(CoroutineScope coroutineScope, MutableState mutableState, SliderModel sliderModel, float f) {
        Slider$lambda$17(mutableState, false);
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new SliderKt$Slider$onStop$1$1$1(sliderModel, f, null), 3, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit Slider$lambda$23(Ref$ObjectRef ref$ObjectRef, MutableFloatState mutableFloatState, float f) {
        mutableFloatState.setFloatValue(f);
        ((Function1) ref$ObjectRef.element).invoke(Float.valueOf(mutableFloatState.getFloatValue()));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit Slider$lambda$24(Ref$ObjectRef ref$ObjectRef, MutableFloatState mutableFloatState) {
        ((Function1) ref$ObjectRef.element).invoke(Float.valueOf(mutableFloatState.getFloatValue()));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit Slider$lambda$25(SliderModel sliderModel, Modifier modifier, int i, int i2, Composer composer, int i3) {
        Slider(sliderModel, modifier, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
        return Unit.INSTANCE;
    }

    private static final float Slider$lambda$5(State state) {
        return ((Number) state.getValue()).floatValue();
    }

    private static final long Slider$lambda$7(State state) {
        return ((Color) state.getValue()).m890unboximpl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object appear(Animatable animatable, Continuation continuation) {
        return Animatable.animateTo$default(animatable, Boxing.boxFloat(1.0f), AnimationSpecs.INSTANCE.getIconAppearSpec(), null, null, continuation, 12, null);
    }

    private static final SliderColors colors(Context context, Composer composer, int i) {
        composer.startReplaceGroup(1746318273);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(1746318273, i, -1, "com.motorola.laptoppanel.ui.compose.colors (Slider.kt:258)");
        }
        SliderColors sliderColorsColors = SliderDefaults.INSTANCE.colors(composer, 6);
        MaterialTheme materialTheme = MaterialTheme.INSTANCE;
        int i2 = MaterialTheme.$stable;
        long jM277getSurfaceVariant0d7_KjU = materialTheme.getColorScheme(composer, i2).m277getSurfaceVariant0d7_KjU();
        SliderColors sliderColorsM311copyK518z4$default = SliderColors.m311copyK518z4$default(sliderColorsColors, materialTheme.getColorScheme(composer, i2).m263getPrimary0d7_KjU(), materialTheme.getColorScheme(composer, i2).m263getPrimary0d7_KjU(), materialTheme.getColorScheme(composer, i2).m253getOnPrimary0d7_KjU(), jM277getSurfaceVariant0d7_KjU, materialTheme.getColorScheme(composer, i2).m257getOnSurface0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 992, null);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composer.endReplaceGroup();
        return sliderColorsM311copyK518z4$default;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object disappear(Animatable animatable, Continuation continuation) {
        return Animatable.animateTo$default(animatable, Boxing.boxFloat(0.0f), AnimationSpecs.INSTANCE.getIconDisappearSpec(), null, null, continuation, 12, null);
    }

    /* JADX INFO: renamed from: sliderBackground-4WTKRHQ, reason: not valid java name */
    private static final Modifier m2152sliderBackground4WTKRHQ(Modifier modifier, final long j) {
        return DrawModifierKt.drawWithCache(modifier, new Function1() { // from class: com.motorola.laptoppanel.ui.compose.SliderKt$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return SliderKt.sliderBackground_4WTKRHQ$lambda$27(j, (CacheDrawScope) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DrawResult sliderBackground_4WTKRHQ$lambda$27(final long j, CacheDrawScope cacheDrawScope) {
        cacheDrawScope.getClass();
        Dimensions dimensions = Dimensions.INSTANCE;
        long jMo147toSizeXkaWNTQ = cacheDrawScope.mo147toSizeXkaWNTQ(dimensions.m2145getSliderBackgroundFrameSizeMYxV2XQ());
        float f = 2;
        int i = (int) (jMo147toSizeXkaWNTQ >> 32);
        int i2 = (int) (jMo147toSizeXkaWNTQ & 4294967295L);
        Size.m783constructorimpl((((long) Float.floatToRawIntBits(Float.intBitsToFloat((int) (cacheDrawScope.m678getSizeNHjbRc() >> 32)) + (Float.intBitsToFloat(i) * f))) << 32) | (((long) Float.floatToRawIntBits(Float.intBitsToFloat((int) (cacheDrawScope.m678getSizeNHjbRc() & 4294967295L)) + (f * Float.intBitsToFloat(i2)))) & 4294967295L));
        final long jM752constructorimpl = Offset.m752constructorimpl((((long) Float.floatToRawIntBits(-Float.intBitsToFloat(i))) << 32) | (((long) Float.floatToRawIntBits(-Float.intBitsToFloat(i2))) & 4294967295L));
        float fMo146toPx0680j_4 = cacheDrawScope.mo146toPx0680j_4(dimensions.m2146getSliderBackgroundRoundedCornerD9Ej5fM());
        final long jM745constructorimpl = CornerRadius.m745constructorimpl((((long) Float.floatToRawIntBits(fMo146toPx0680j_4)) << 32) | (((long) Float.floatToRawIntBits(fMo146toPx0680j_4)) & 4294967295L));
        return cacheDrawScope.onDrawBehind(new Function1() { // from class: com.motorola.laptoppanel.ui.compose.SliderKt$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return SliderKt.sliderBackground_4WTKRHQ$lambda$27$lambda$26(j, jM752constructorimpl, jM745constructorimpl, (DrawScope) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit sliderBackground_4WTKRHQ$lambda$27$lambda$26(long j, long j2, long j3, DrawScope drawScope) {
        drawScope.getClass();
        DrawScope.m1079drawRoundRectuAw5IA$default(drawScope, j, j2, drawScope.mo1082getSizeNHjbRc(), j3, null, 0.0f, null, 0, 240, null);
        return Unit.INSTANCE;
    }
}
