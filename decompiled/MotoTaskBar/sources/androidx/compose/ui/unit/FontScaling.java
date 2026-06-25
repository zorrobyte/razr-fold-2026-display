package androidx.compose.ui.unit;

import androidx.compose.ui.unit.fontscaling.FontScaleConverter;
import androidx.compose.ui.unit.fontscaling.FontScaleConverterFactory;

/* JADX INFO: compiled from: FontScaling.android.kt */
/* JADX INFO: loaded from: classes.dex */
public interface FontScaling {
    float getFontScale();

    /* JADX INFO: renamed from: toDp-GaN1DYA */
    default float mo527toDpGaN1DYA(long j) {
        FontScaleConverter fontScaleConverterForScale;
        if (!TextUnitType.m1032equalsimpl0(TextUnit.m1022getTypeUIouoOA(j), TextUnitType.Companion.m1037getSpUIouoOA())) {
            InlineClassHelperKt.throwIllegalStateException("Only Sp can convert to Px");
        }
        FontScaleConverterFactory fontScaleConverterFactory = FontScaleConverterFactory.INSTANCE;
        if (fontScaleConverterFactory.isNonLinearFontScalingActive(getFontScale()) && (fontScaleConverterForScale = fontScaleConverterFactory.forScale(getFontScale())) != null) {
            return Dp.m989constructorimpl(fontScaleConverterForScale.convertSpToDp(TextUnit.m1023getValueimpl(j)));
        }
        return Dp.m989constructorimpl(TextUnit.m1023getValueimpl(j) * getFontScale());
    }
}
