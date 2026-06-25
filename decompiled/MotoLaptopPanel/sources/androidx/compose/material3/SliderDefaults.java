package androidx.compose.material3;

import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.tokens.SliderTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ScaleKt;
import androidx.compose.ui.geometry.CornerRadiusKt;
import androidx.compose.ui.geometry.RectKt;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.RoundRectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SliderDefaults {
    public static final SliderDefaults INSTANCE = new SliderDefaults();
    private static final float TickSize;
    private static final float TrackStopIndicatorSize;
    private static final Path trackPath;

    static {
        SliderTokens sliderTokens = SliderTokens.INSTANCE;
        TrackStopIndicatorSize = sliderTokens.m476getStopIndicatorSizeD9Ej5fM();
        TickSize = sliderTokens.m476getStopIndicatorSizeD9Ej5fM();
        trackPath = AndroidPath_androidKt.Path();
    }

    private SliderDefaults() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: DrawTrack-J0vdD74, reason: not valid java name */
    public final void m320DrawTrackJ0vdD74(final SliderState sliderState, final float f, final Modifier modifier, final boolean z, final SliderColors sliderColors, final Function2 function2, final Function3 function3, final float f2, final float f3, final boolean z2, Composer composer, final int i, final int i2) {
        int i3;
        float f4;
        Modifier modifierScale;
        Composer composer2;
        Modifier modifier2;
        Composer composerStartRestartGroup = composer.startRestartGroup(-1066375183);
        if ((i & 6) == 0) {
            i3 = (composerStartRestartGroup.changedInstance(sliderState) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerStartRestartGroup.changed(f) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerStartRestartGroup.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= composerStartRestartGroup.changed(z) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i3 |= composerStartRestartGroup.changed(sliderColors) ? 16384 : 8192;
        }
        if ((196608 & i) == 0) {
            i3 |= composerStartRestartGroup.changedInstance(function2) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i3 |= composerStartRestartGroup.changedInstance(function3) ? 1048576 : 524288;
        }
        if ((12582912 & i) == 0) {
            f4 = f2;
            i3 |= composerStartRestartGroup.changed(f4) ? 8388608 : 4194304;
        } else {
            f4 = f2;
        }
        if ((i & 100663296) == 0) {
            i3 |= composerStartRestartGroup.changed(f3) ? 67108864 : 33554432;
        }
        if ((i & 805306368) == 0) {
            i3 |= composerStartRestartGroup.changed(z2) ? 536870912 : 268435456;
        }
        if ((i3 & 306783379) == 306783378 && (i2 & 1) == 0 && composerStartRestartGroup.getSkipping()) {
            composerStartRestartGroup.skipToGroupEnd();
            composer2 = composerStartRestartGroup;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1066375183, i3, i2, "androidx.compose.material3.SliderDefaults.DrawTrack (Slider.kt:1504)");
            }
            final long jM319trackColorWaAFU9c$material3_release = sliderColors.m319trackColorWaAFU9c$material3_release(z, false);
            int i4 = i3;
            final long jM319trackColorWaAFU9c$material3_release2 = sliderColors.m319trackColorWaAFU9c$material3_release(z, true);
            final long jM318tickColorWaAFU9c$material3_release = sliderColors.m318tickColorWaAFU9c$material3_release(z, false);
            final long jM318tickColorWaAFU9c$material3_release2 = sliderColors.m318tickColorWaAFU9c$material3_release(z, true);
            if (sliderState.getOrientation$material3_release() == Orientation.Vertical) {
                modifierScale = SizeKt.fillMaxHeight$default(SizeKt.m179width3ABfNKs(modifier, SliderKt.getTrackHeight()), 0.0f, 1, null);
                if (sliderState.getReverseVerticalDirection$material3_release()) {
                    modifierScale = ScaleKt.scale(modifierScale, 1.0f, -1.0f);
                }
            } else {
                Modifier modifierM172height3ABfNKs = SizeKt.m172height3ABfNKs(SizeKt.fillMaxWidth$default(modifier, 0.0f, 1, null), SliderKt.getTrackHeight());
                modifierScale = sliderState.isRtl$material3_release() ? ScaleKt.scale(modifierM172height3ABfNKs, -1.0f, 1.0f) : modifierM172height3ABfNKs;
            }
            composer2 = composerStartRestartGroup;
            boolean zChangedInstance = composer2.changedInstance(sliderState) | composer2.changed(jM319trackColorWaAFU9c$material3_release) | composer2.changed(jM319trackColorWaAFU9c$material3_release2) | composer2.changed(jM318tickColorWaAFU9c$material3_release) | composer2.changed(jM318tickColorWaAFU9c$material3_release2) | ((i4 & 29360128) == 8388608) | ((i4 & 234881024) == 67108864) | ((i4 & 112) == 32) | ((i4 & 458752) == 131072) | ((i4 & 3670016) == 1048576) | ((i4 & 1879048192) == 536870912);
            Object objRememberedValue = composer2.rememberedValue();
            if (zChangedInstance || objRememberedValue == Composer.Companion.getEmpty()) {
                modifier2 = modifierScale;
                final float f5 = f4;
                Function1 function1 = new Function1() { // from class: androidx.compose.material3.SliderDefaults$DrawTrack$3$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        invoke((DrawScope) obj);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(DrawScope drawScope) {
                        SliderDefaults.INSTANCE.m323drawTrackeniyc90(drawScope, sliderState.getTickFractions$material3_release(), 0.0f, sliderState.getCoercedValueAsFraction(), jM319trackColorWaAFU9c$material3_release, jM319trackColorWaAFU9c$material3_release2, jM318tickColorWaAFU9c$material3_release, jM318tickColorWaAFU9c$material3_release2, drawScope.mo144toDpu2uoSUM(sliderState.getTrackWidth$material3_release()), drawScope.mo144toDpu2uoSUM(sliderState.getTrackHeight$material3_release()), drawScope.mo144toDpu2uoSUM(0), drawScope.mo144toDpu2uoSUM(0), drawScope.mo144toDpu2uoSUM(sliderState.getThumbWidth$material3_release()), drawScope.mo144toDpu2uoSUM(sliderState.getThumbHeight$material3_release()), f5, f3, f, function2, function3, false, z2, sliderState.getOrientation$material3_release());
                    }
                };
                composer2 = composer2;
                composer2.updateRememberedValue(function1);
                objRememberedValue = function1;
            } else {
                modifier2 = modifierScale;
            }
            CanvasKt.Canvas(modifier2, (Function1) objRememberedValue, composer2, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2() { // from class: androidx.compose.material3.SliderDefaults$DrawTrack$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                    invoke((Composer) obj, ((Number) obj2).intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer3, int i5) {
                    this.$tmp1_rcvr.m320DrawTrackJ0vdD74(sliderState, f, modifier, z, sliderColors, function2, function3, f2, f3, z2, composer3, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01bd  */
    /* JADX INFO: renamed from: drawTrack-eniyc90, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void m323drawTrackeniyc90(androidx.compose.ui.graphics.drawscope.DrawScope r25, float[] r26, float r27, float r28, long r29, long r31, long r33, long r35, float r37, float r38, float r39, float r40, float r41, float r42, float r43, float r44, float r45, kotlin.jvm.functions.Function2 r46, kotlin.jvm.functions.Function3 r47, boolean r48, boolean r49, androidx.compose.foundation.gestures.Orientation r50) {
        /*
            Method dump skipped, instruction units count: 536
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderDefaults.m323drawTrackeniyc90(androidx.compose.ui.graphics.drawscope.DrawScope, float[], float, float, long, long, long, long, float, float, float, float, float, float, float, float, float, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, boolean, boolean, androidx.compose.foundation.gestures.Orientation):void");
    }

    /* JADX INFO: renamed from: drawTrackPath-zXTsYAs, reason: not valid java name */
    private final void m324drawTrackPathzXTsYAs(DrawScope drawScope, Orientation orientation, long j, long j2, long j3, float f, float f2) {
        long jCornerRadius = CornerRadiusKt.CornerRadius(f, f);
        long jCornerRadius2 = CornerRadiusKt.CornerRadius(f2, f2);
        RoundRect roundRectM780RoundRectZAM2FJo = orientation == Orientation.Vertical ? RoundRectKt.m780RoundRectZAM2FJo(RectKt.m775Recttz77jQw(j, androidx.compose.ui.geometry.SizeKt.Size(Size.m788getWidthimpl(j2), Size.m786getHeightimpl(j2))), jCornerRadius, jCornerRadius, jCornerRadius2, jCornerRadius2) : RoundRectKt.m780RoundRectZAM2FJo(RectKt.m775Recttz77jQw(j, androidx.compose.ui.geometry.SizeKt.Size(Size.m788getWidthimpl(j2), Size.m786getHeightimpl(j2))), jCornerRadius, jCornerRadius2, jCornerRadius2, jCornerRadius);
        Path path = trackPath;
        Path.addRoundRect$default(path, roundRectM780RoundRectZAM2FJo, null, 2, null);
        DrawScope.m1075drawPathLG529CI$default(drawScope, path, j3, 0.0f, null, null, 0, 60, null);
        path.rewind();
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:119:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x011b  */
    /* JADX INFO: renamed from: Thumb-9LiSoMs, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void m325Thumb9LiSoMs(final androidx.compose.foundation.interaction.MutableInteractionSource r24, androidx.compose.ui.Modifier r25, androidx.compose.material3.SliderColors r26, boolean r27, long r28, androidx.compose.runtime.Composer r30, final int r31, final int r32) {
        /*
            Method dump skipped, instruction units count: 418
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderDefaults.m325Thumb9LiSoMs(androidx.compose.foundation.interaction.MutableInteractionSource, androidx.compose.ui.Modifier, androidx.compose.material3.SliderColors, boolean, long, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:165:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0116  */
    /* JADX INFO: renamed from: Track-4EFweAY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void m326Track4EFweAY(final androidx.compose.material3.SliderState r23, androidx.compose.ui.Modifier r24, boolean r25, androidx.compose.material3.SliderColors r26, kotlin.jvm.functions.Function2 r27, kotlin.jvm.functions.Function3 r28, float r29, float r30, androidx.compose.runtime.Composer r31, final int r32, final int r33) {
        /*
            Method dump skipped, instruction units count: 565
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderDefaults.m326Track4EFweAY(androidx.compose.material3.SliderState, androidx.compose.ui.Modifier, boolean, androidx.compose.material3.SliderColors, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, float, float, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x01ee  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x023a  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:176:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00ff  */
    /* JADX INFO: renamed from: Track-mnvyFg4, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void m327TrackmnvyFg4(final androidx.compose.material3.SliderState r23, final float r24, androidx.compose.ui.Modifier r25, boolean r26, androidx.compose.material3.SliderColors r27, kotlin.jvm.functions.Function2 r28, kotlin.jvm.functions.Function3 r29, float r30, float r31, androidx.compose.runtime.Composer r32, final int r33, final int r34) {
        /*
            Method dump skipped, instruction units count: 603
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderDefaults.m327TrackmnvyFg4(androidx.compose.material3.SliderState, float, androidx.compose.ui.Modifier, boolean, androidx.compose.material3.SliderColors, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, float, float, androidx.compose.runtime.Composer, int, int):void");
    }

    public final SliderColors colors(Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(1376295968, i, -1, "androidx.compose.material3.SliderDefaults.colors (Slider.kt:1080)");
        }
        SliderColors defaultSliderColors$material3_release = getDefaultSliderColors$material3_release(MaterialTheme.INSTANCE.getColorScheme(composer, 6));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return defaultSliderColors$material3_release;
    }

    /* JADX INFO: renamed from: drawStopIndicator-x3O1jOs, reason: not valid java name */
    public final void m328drawStopIndicatorx3O1jOs(DrawScope drawScope, long j, float f, long j2) {
        DrawScope.m1072drawCircleVaOC9Bg$default(drawScope, j2, drawScope.mo146toPx0680j_4(f) / 2.0f, j, 0.0f, null, null, 0, 120, null);
    }

    public final SliderColors getDefaultSliderColors$material3_release(ColorScheme colorScheme) {
        SliderColors defaultSliderColorsCached$material3_release = colorScheme.getDefaultSliderColorsCached$material3_release();
        if (defaultSliderColorsCached$material3_release != null) {
            return defaultSliderColorsCached$material3_release;
        }
        SliderTokens sliderTokens = SliderTokens.INSTANCE;
        SliderColors sliderColors = new SliderColors(ColorSchemeKt.fromToken(colorScheme, sliderTokens.getHandleColor()), ColorSchemeKt.fromToken(colorScheme, sliderTokens.getActiveTrackColor()), ColorSchemeKt.fromToken(colorScheme, sliderTokens.getInactiveTrackColor()), ColorSchemeKt.fromToken(colorScheme, sliderTokens.getInactiveTrackColor()), ColorSchemeKt.fromToken(colorScheme, sliderTokens.getActiveTrackColor()), ColorKt.m899compositeOverOWjLjI(Color.m880copywmQWz5c$default(ColorSchemeKt.fromToken(colorScheme, sliderTokens.getDisabledHandleColor()), sliderTokens.getDisabledHandleOpacity(), 0.0f, 0.0f, 0.0f, 14, null), colorScheme.m268getSurface0d7_KjU()), Color.m880copywmQWz5c$default(ColorSchemeKt.fromToken(colorScheme, sliderTokens.getDisabledActiveTrackColor()), sliderTokens.getDisabledActiveTrackOpacity(), 0.0f, 0.0f, 0.0f, 14, null), Color.m880copywmQWz5c$default(ColorSchemeKt.fromToken(colorScheme, sliderTokens.getDisabledInactiveTrackColor()), sliderTokens.getDisabledInactiveTrackOpacity(), 0.0f, 0.0f, 0.0f, 14, null), Color.m880copywmQWz5c$default(ColorSchemeKt.fromToken(colorScheme, sliderTokens.getDisabledInactiveTrackColor()), sliderTokens.getDisabledInactiveTrackOpacity(), 0.0f, 0.0f, 0.0f, 14, null), Color.m880copywmQWz5c$default(ColorSchemeKt.fromToken(colorScheme, sliderTokens.getDisabledActiveTrackColor()), sliderTokens.getDisabledActiveTrackOpacity(), 0.0f, 0.0f, 0.0f, 14, null), null);
        colorScheme.setDefaultSliderColorsCached$material3_release(sliderColors);
        return sliderColors;
    }

    /* JADX INFO: renamed from: getTickSize-D9Ej5fM, reason: not valid java name */
    public final float m329getTickSizeD9Ej5fM() {
        return TickSize;
    }

    /* JADX INFO: renamed from: getTrackStopIndicatorSize-D9Ej5fM, reason: not valid java name */
    public final float m330getTrackStopIndicatorSizeD9Ej5fM() {
        return TrackStopIndicatorSize;
    }
}
