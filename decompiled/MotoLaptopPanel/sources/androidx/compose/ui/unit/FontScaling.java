package androidx.compose.ui.unit;

import androidx.compose.ui.unit.fontscaling.FontScaleConverter;
import androidx.compose.ui.unit.fontscaling.FontScaleConverterFactory;

/* JADX INFO: compiled from: FontScaling.android.kt */
/* JADX INFO: loaded from: classes.dex */
public interface FontScaling {
    float getFontScale();

    /* JADX INFO: renamed from: toDp-GaN1DYA */
    default float mo142toDpGaN1DYA(long j) {
        FontScaleConverter fontScaleConverterForScale;
        if (!TextUnitType.m1947equalsimpl0(TextUnit.m1936getTypeUIouoOA(j), TextUnitType.Companion.m1952getSpUIouoOA())) {
            InlineClassHelperKt.throwIllegalStateException("Only Sp can convert to Px");
        }
        FontScaleConverterFactory fontScaleConverterFactory = FontScaleConverterFactory.INSTANCE;
        if (fontScaleConverterFactory.isNonLinearFontScalingActive(getFontScale()) && (fontScaleConverterForScale = fontScaleConverterFactory.forScale(getFontScale())) != null) {
            return Dp.m1877constructorimpl(fontScaleConverterForScale.convertSpToDp(TextUnit.m1937getValueimpl(j)));
        }
        return Dp.m1877constructorimpl(TextUnit.m1937getValueimpl(j) * getFontScale());
    }

    /* JADX INFO: renamed from: toSp-0xMU5do */
    default long mo148toSp0xMU5do(float f) {
        FontScaleConverterFactory fontScaleConverterFactory = FontScaleConverterFactory.INSTANCE;
        if (!fontScaleConverterFactory.isNonLinearFontScalingActive(getFontScale())) {
            return TextUnitKt.getSp(f / getFontScale());
        }
        FontScaleConverter fontScaleConverterForScale = fontScaleConverterFactory.forScale(getFontScale());
        return TextUnitKt.getSp(fontScaleConverterForScale != null ? fontScaleConverterForScale.convertDpToSp(f) : f / getFontScale());
    }
}
