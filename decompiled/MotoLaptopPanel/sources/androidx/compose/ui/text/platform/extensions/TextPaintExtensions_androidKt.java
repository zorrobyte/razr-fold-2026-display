package androidx.compose.ui.text.platform.extensions;

import android.graphics.Typeface;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.platform.AndroidTextPaint;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitType;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TextPaintExtensions.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TextPaintExtensions_androidKt {
    public static final SpanStyle applySpanStyle(AndroidTextPaint androidTextPaint, SpanStyle spanStyle, Function4 function4, Density density, boolean z) {
        long jM1936getTypeUIouoOA = TextUnit.m1936getTypeUIouoOA(spanStyle.m1572getFontSizeXSAIIZE());
        TextUnitType.Companion companion = TextUnitType.Companion;
        if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1952getSpUIouoOA())) {
            androidTextPaint.setTextSize(density.mo145toPxR2X_6o(spanStyle.m1572getFontSizeXSAIIZE()));
        } else if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1951getEmUIouoOA())) {
            androidTextPaint.setTextSize(androidTextPaint.getTextSize() * TextUnit.m1937getValueimpl(spanStyle.m1572getFontSizeXSAIIZE()));
        }
        if (hasFontAttributes(spanStyle)) {
            FontFamily fontFamily = spanStyle.getFontFamily();
            FontWeight fontWeight = spanStyle.getFontWeight();
            if (fontWeight == null) {
                fontWeight = FontWeight.Companion.getNormal();
            }
            FontStyle fontStyleM1573getFontStyle4Lr2A7w = spanStyle.m1573getFontStyle4Lr2A7w();
            FontStyle fontStyleM1626boximpl = FontStyle.m1626boximpl(fontStyleM1573getFontStyle4Lr2A7w != null ? fontStyleM1573getFontStyle4Lr2A7w.m1632unboximpl() : FontStyle.Companion.m1634getNormal_LCdwA());
            FontSynthesis fontSynthesisM1574getFontSynthesisZQGJjVo = spanStyle.m1574getFontSynthesisZQGJjVo();
            androidTextPaint.setTypeface((Typeface) function4.invoke(fontFamily, fontWeight, fontStyleM1626boximpl, FontSynthesis.m1635boximpl(fontSynthesisM1574getFontSynthesisZQGJjVo != null ? fontSynthesisM1574getFontSynthesisZQGJjVo.m1641unboximpl() : FontSynthesis.Companion.m1642getAllGVVA2EU())));
        }
        if (spanStyle.getLocaleList() != null && !Intrinsics.areEqual(spanStyle.getLocaleList(), LocaleList.Companion.getCurrent())) {
            LocaleListHelperMethods.INSTANCE.setTextLocales(androidTextPaint, spanStyle.getLocaleList());
        }
        if (spanStyle.getFontFeatureSettings() != null && !Intrinsics.areEqual(spanStyle.getFontFeatureSettings(), "")) {
            androidTextPaint.setFontFeatureSettings(spanStyle.getFontFeatureSettings());
        }
        if (spanStyle.getTextGeometricTransform() != null && !Intrinsics.areEqual(spanStyle.getTextGeometricTransform(), TextGeometricTransform.Companion.getNone$ui_text_release())) {
            androidTextPaint.setTextScaleX(androidTextPaint.getTextScaleX() * spanStyle.getTextGeometricTransform().getScaleX());
            androidTextPaint.setTextSkewX(androidTextPaint.getTextSkewX() + spanStyle.getTextGeometricTransform().getSkewX());
        }
        androidTextPaint.m1703setColor8_81llA(spanStyle.m1571getColor0d7_KjU());
        androidTextPaint.m1702setBrush12SF9DM(spanStyle.getBrush(), Size.Companion.m793getUnspecifiedNHjbRc(), spanStyle.getAlpha());
        androidTextPaint.setShadow(spanStyle.getShadow());
        androidTextPaint.setTextDecoration(spanStyle.getTextDecoration());
        androidTextPaint.setDrawStyle(spanStyle.getDrawStyle());
        if (TextUnitType.m1947equalsimpl0(TextUnit.m1936getTypeUIouoOA(spanStyle.m1575getLetterSpacingXSAIIZE()), companion.m1952getSpUIouoOA()) && TextUnit.m1937getValueimpl(spanStyle.m1575getLetterSpacingXSAIIZE()) != 0.0f) {
            float textSize = androidTextPaint.getTextSize() * androidTextPaint.getTextScaleX();
            float fMo145toPxR2X_6o = density.mo145toPxR2X_6o(spanStyle.m1575getLetterSpacingXSAIIZE());
            if (textSize != 0.0f) {
                androidTextPaint.setLetterSpacing(fMo145toPxR2X_6o / textSize);
            }
        } else if (TextUnitType.m1947equalsimpl0(TextUnit.m1936getTypeUIouoOA(spanStyle.m1575getLetterSpacingXSAIIZE()), companion.m1951getEmUIouoOA())) {
            androidTextPaint.setLetterSpacing(TextUnit.m1937getValueimpl(spanStyle.m1575getLetterSpacingXSAIIZE()));
        }
        return m1712generateFallbackSpanStyle62GTOB8(spanStyle.m1575getLetterSpacingXSAIIZE(), z, spanStyle.m1569getBackground0d7_KjU(), spanStyle.m1570getBaselineShift5SSeXJ0());
    }

    public static final float correctBlurRadius(float f) {
        if (f == 0.0f) {
            return Float.MIN_VALUE;
        }
        return f;
    }

    /* JADX INFO: renamed from: generateFallbackSpanStyle-62GTOB8, reason: not valid java name */
    private static final SpanStyle m1712generateFallbackSpanStyle62GTOB8(long j, boolean z, long j2, BaselineShift baselineShift) {
        long jM895getUnspecified0d7_KjU = j2;
        boolean z2 = false;
        boolean z3 = z && TextUnitType.m1947equalsimpl0(TextUnit.m1936getTypeUIouoOA(j), TextUnitType.Companion.m1952getSpUIouoOA()) && TextUnit.m1937getValueimpl(j) != 0.0f;
        Color.Companion companion = Color.Companion;
        boolean z4 = (Color.m882equalsimpl0(jM895getUnspecified0d7_KjU, companion.m895getUnspecified0d7_KjU()) || Color.m882equalsimpl0(jM895getUnspecified0d7_KjU, companion.m894getTransparent0d7_KjU())) ? false : true;
        if (baselineShift != null) {
            if (!BaselineShift.m1720equalsimpl0(baselineShift.m1723unboximpl(), BaselineShift.Companion.m1724getNoney9eOQZs())) {
                z2 = true;
            }
        }
        if (!z3 && !z4 && !z2) {
            return null;
        }
        long jM1941getUnspecifiedXSAIIZE = z3 ? j : TextUnit.Companion.m1941getUnspecifiedXSAIIZE();
        if (!z4) {
            jM895getUnspecified0d7_KjU = companion.m895getUnspecified0d7_KjU();
        }
        return new SpanStyle(0L, 0L, null, null, null, null, null, jM1941getUnspecifiedXSAIIZE, z2 ? baselineShift : null, null, null, jM895getUnspecified0d7_KjU, null, null, null, null, 63103, null);
    }

    public static final boolean hasFontAttributes(SpanStyle spanStyle) {
        return (spanStyle.getFontFamily() == null && spanStyle.m1573getFontStyle4Lr2A7w() == null && spanStyle.getFontWeight() == null) ? false : true;
    }

    public static final void setTextMotion(AndroidTextPaint androidTextPaint, TextMotion textMotion) {
        if (textMotion == null) {
            textMotion = TextMotion.Companion.getStatic();
        }
        androidTextPaint.setFlags(textMotion.getSubpixelTextPositioning$ui_text_release() ? androidTextPaint.getFlags() | 128 : androidTextPaint.getFlags() & (-129));
        int iM1829getLinearity4e0Vf04$ui_text_release = textMotion.m1829getLinearity4e0Vf04$ui_text_release();
        TextMotion.Linearity.Companion companion = TextMotion.Linearity.Companion;
        if (TextMotion.Linearity.m1833equalsimpl0(iM1829getLinearity4e0Vf04$ui_text_release, companion.m1838getLinear4e0Vf04())) {
            androidTextPaint.setFlags(androidTextPaint.getFlags() | 64);
            androidTextPaint.setHinting(0);
        } else if (TextMotion.Linearity.m1833equalsimpl0(iM1829getLinearity4e0Vf04$ui_text_release, companion.m1837getFontHinting4e0Vf04())) {
            androidTextPaint.getFlags();
            androidTextPaint.setHinting(1);
        } else if (!TextMotion.Linearity.m1833equalsimpl0(iM1829getLinearity4e0Vf04$ui_text_release, companion.m1839getNone4e0Vf04())) {
            androidTextPaint.getFlags();
        } else {
            androidTextPaint.getFlags();
            androidTextPaint.setHinting(0);
        }
    }
}
