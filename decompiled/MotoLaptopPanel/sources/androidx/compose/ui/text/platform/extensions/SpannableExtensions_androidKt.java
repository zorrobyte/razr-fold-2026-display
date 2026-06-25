package androidx.compose.ui.text.platform.extensions;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.MetricAffectingSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.ShaderBrush;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.AnnotatedStringKt;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.android.style.BaselineShiftSpan;
import androidx.compose.ui.text.android.style.FontFeatureSpan;
import androidx.compose.ui.text.android.style.LetterSpacingSpanEm;
import androidx.compose.ui.text.android.style.LetterSpacingSpanPx;
import androidx.compose.ui.text.android.style.LineHeightSpan;
import androidx.compose.ui.text.android.style.LineHeightStyleSpan;
import androidx.compose.ui.text.android.style.ShadowSpan;
import androidx.compose.ui.text.android.style.SkewXSpan;
import androidx.compose.ui.text.android.style.TextDecorationSpan;
import androidx.compose.ui.text.android.style.TypefaceSpan;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.platform.style.DrawStyleSpan;
import androidx.compose.ui.text.platform.style.ShaderBrushSpan;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SpannableExtensions.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SpannableExtensions_androidKt {
    /* JADX INFO: renamed from: createLetterSpacingSpan-eAf_CNQ, reason: not valid java name */
    private static final MetricAffectingSpan m1704createLetterSpacingSpaneAf_CNQ(long j, Density density) {
        long jM1936getTypeUIouoOA = TextUnit.m1936getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1952getSpUIouoOA())) {
            return new LetterSpacingSpanPx(density.mo145toPxR2X_6o(j));
        }
        if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1951getEmUIouoOA())) {
            return new LetterSpacingSpanEm(TextUnit.m1937getValueimpl(j));
        }
        return null;
    }

    public static final void flattenFontStylesAndApply(SpanStyle spanStyle, List list, Function3 function3) {
        if (list.size() <= 1) {
            if (list.isEmpty()) {
                return;
            }
            function3.invoke(merge(spanStyle, (SpanStyle) ((AnnotatedString.Range) list.get(0)).getItem()), Integer.valueOf(((AnnotatedString.Range) list.get(0)).getStart()), Integer.valueOf(((AnnotatedString.Range) list.get(0)).getEnd()));
            return;
        }
        int size = list.size();
        int i = size * 2;
        int[] iArr = new int[i];
        int size2 = list.size();
        for (int i2 = 0; i2 < size2; i2++) {
            AnnotatedString.Range range = (AnnotatedString.Range) list.get(i2);
            iArr[i2] = range.getStart();
            iArr[i2 + size] = range.getEnd();
        }
        ArraysKt.sort(iArr);
        int iFirst = ArraysKt.first(iArr);
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = iArr[i3];
            if (i4 != iFirst) {
                int size3 = list.size();
                SpanStyle spanStyleMerge = spanStyle;
                for (int i5 = 0; i5 < size3; i5++) {
                    AnnotatedString.Range range2 = (AnnotatedString.Range) list.get(i5);
                    if (range2.getStart() != range2.getEnd() && AnnotatedStringKt.intersect(iFirst, i4, range2.getStart(), range2.getEnd())) {
                        spanStyleMerge = merge(spanStyleMerge, (SpanStyle) range2.getItem());
                    }
                }
                if (spanStyleMerge != null) {
                    function3.invoke(spanStyleMerge, Integer.valueOf(iFirst), Integer.valueOf(i4));
                }
                iFirst = i4;
            }
        }
    }

    private static final boolean getNeedsLetterSpacingSpan(SpanStyle spanStyle) {
        long jM1936getTypeUIouoOA = TextUnit.m1936getTypeUIouoOA(spanStyle.m1575getLetterSpacingXSAIIZE());
        TextUnitType.Companion companion = TextUnitType.Companion;
        return TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1952getSpUIouoOA()) || TextUnitType.m1947equalsimpl0(TextUnit.m1936getTypeUIouoOA(spanStyle.m1575getLetterSpacingXSAIIZE()), companion.m1951getEmUIouoOA());
    }

    private static final boolean hasFontAttributes(TextStyle textStyle) {
        return TextPaintExtensions_androidKt.hasFontAttributes(textStyle.toSpanStyle()) || textStyle.m1610getFontSynthesisZQGJjVo() != null;
    }

    private static final boolean isNonLinearFontScalingActive(Density density) {
        return ((double) density.getFontScale()) > 1.05d;
    }

    private static final SpanStyle merge(SpanStyle spanStyle, SpanStyle spanStyle2) {
        return spanStyle == null ? spanStyle2 : spanStyle.merge(spanStyle2);
    }

    /* JADX INFO: renamed from: resolveLineHeightInPx-o2QH7mI, reason: not valid java name */
    private static final float m1705resolveLineHeightInPxo2QH7mI(long j, float f, Density density) {
        float fM1937getValueimpl;
        long jM1936getTypeUIouoOA = TextUnit.m1936getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1952getSpUIouoOA())) {
            if (!isNonLinearFontScalingActive(density)) {
                return density.mo145toPxR2X_6o(j);
            }
            fM1937getValueimpl = TextUnit.m1937getValueimpl(j) / TextUnit.m1937getValueimpl(density.mo149toSpkPz2Gy4(f));
        } else {
            if (!TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1951getEmUIouoOA())) {
                return Float.NaN;
            }
            fM1937getValueimpl = TextUnit.m1937getValueimpl(j);
        }
        return fM1937getValueimpl * f;
    }

    /* JADX INFO: renamed from: setBackground-RPmYEkk, reason: not valid java name */
    public static final void m1706setBackgroundRPmYEkk(Spannable spannable, long j, int i, int i2) {
        if (j != 16) {
            setSpan(spannable, new BackgroundColorSpan(ColorKt.m900toArgb8_81llA(j)), i, i2);
        }
    }

    /* JADX INFO: renamed from: setBaselineShift-0ocSgnM, reason: not valid java name */
    private static final void m1707setBaselineShift0ocSgnM(Spannable spannable, BaselineShift baselineShift, int i, int i2) {
        if (baselineShift != null) {
            setSpan(spannable, new BaselineShiftSpan(baselineShift.m1723unboximpl()), i, i2);
        }
    }

    private static final void setBrush(Spannable spannable, Brush brush, float f, int i, int i2) {
        if (brush != null) {
            if (brush instanceof SolidColor) {
                m1708setColorRPmYEkk(spannable, ((SolidColor) brush).m993getValue0d7_KjU(), i, i2);
            } else if (brush instanceof ShaderBrush) {
                setSpan(spannable, new ShaderBrushSpan((ShaderBrush) brush, f), i, i2);
            }
        }
    }

    public static final void setBulletSpans(Spannable spannable, List list, float f, Density density, TextIndent textIndent) {
        if (textIndent != null) {
            long jM1936getTypeUIouoOA = TextUnit.m1936getTypeUIouoOA(textIndent.m1827getFirstLineXSAIIZE());
            TextUnitType.Companion companion = TextUnitType.Companion;
            if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1952getSpUIouoOA())) {
                density.mo145toPxR2X_6o(textIndent.m1827getFirstLineXSAIIZE());
            } else if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1951getEmUIouoOA())) {
                TextUnit.m1937getValueimpl(textIndent.m1827getFirstLineXSAIIZE());
            }
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ((AnnotatedString.Range) list.get(i)).getItem();
        }
    }

    /* JADX INFO: renamed from: setColor-RPmYEkk, reason: not valid java name */
    public static final void m1708setColorRPmYEkk(Spannable spannable, long j, int i, int i2) {
        if (j != 16) {
            setSpan(spannable, new ForegroundColorSpan(ColorKt.m900toArgb8_81llA(j)), i, i2);
        }
    }

    private static final void setDrawStyle(Spannable spannable, DrawStyle drawStyle, int i, int i2) {
        if (drawStyle != null) {
            setSpan(spannable, new DrawStyleSpan(drawStyle), i, i2);
        }
    }

    private static final void setFontAttributes(final Spannable spannable, TextStyle textStyle, List list, final Function4 function4) {
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            AnnotatedString.Range range = (AnnotatedString.Range) list.get(i);
            if ((range.getItem() instanceof SpanStyle) && (TextPaintExtensions_androidKt.hasFontAttributes((SpanStyle) range.getItem()) || ((SpanStyle) range.getItem()).m1574getFontSynthesisZQGJjVo() != null)) {
                range.getClass();
                arrayList.add(range);
            }
        }
        flattenFontStylesAndApply(hasFontAttributes(textStyle) ? new SpanStyle(0L, 0L, textStyle.getFontWeight(), textStyle.m1609getFontStyle4Lr2A7w(), textStyle.m1610getFontSynthesisZQGJjVo(), textStyle.getFontFamily(), null, 0L, null, null, null, 0L, null, null, null, null, 65475, null) : null, arrayList, new Function3() { // from class: androidx.compose.ui.text.platform.extensions.SpannableExtensions_androidKt.setFontAttributes.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                invoke((SpanStyle) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(SpanStyle spanStyle, int i2, int i3) {
                Spannable spannable2 = spannable;
                Function4 function42 = function4;
                FontFamily fontFamily = spanStyle.getFontFamily();
                FontWeight fontWeight = spanStyle.getFontWeight();
                if (fontWeight == null) {
                    fontWeight = FontWeight.Companion.getNormal();
                }
                FontStyle fontStyleM1573getFontStyle4Lr2A7w = spanStyle.m1573getFontStyle4Lr2A7w();
                FontStyle fontStyleM1626boximpl = FontStyle.m1626boximpl(fontStyleM1573getFontStyle4Lr2A7w != null ? fontStyleM1573getFontStyle4Lr2A7w.m1632unboximpl() : FontStyle.Companion.m1634getNormal_LCdwA());
                FontSynthesis fontSynthesisM1574getFontSynthesisZQGJjVo = spanStyle.m1574getFontSynthesisZQGJjVo();
                spannable2.setSpan(new TypefaceSpan((Typeface) function42.invoke(fontFamily, fontWeight, fontStyleM1626boximpl, FontSynthesis.m1635boximpl(fontSynthesisM1574getFontSynthesisZQGJjVo != null ? fontSynthesisM1574getFontSynthesisZQGJjVo.m1641unboximpl() : FontSynthesis.Companion.m1642getAllGVVA2EU()))), i2, i3, 33);
            }
        });
    }

    private static final void setFontFeatureSettings(Spannable spannable, String str, int i, int i2) {
        if (str != null) {
            setSpan(spannable, new FontFeatureSpan(str), i, i2);
        }
    }

    /* JADX INFO: renamed from: setFontSize-KmRG4DE, reason: not valid java name */
    public static final void m1709setFontSizeKmRG4DE(Spannable spannable, long j, Density density, int i, int i2) {
        long jM1936getTypeUIouoOA = TextUnit.m1936getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1952getSpUIouoOA())) {
            setSpan(spannable, new AbsoluteSizeSpan(MathKt.roundToInt(density.mo145toPxR2X_6o(j)), false), i, i2);
        } else if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1951getEmUIouoOA())) {
            setSpan(spannable, new RelativeSizeSpan(TextUnit.m1937getValueimpl(j)), i, i2);
        }
    }

    private static final void setGeometricTransform(Spannable spannable, TextGeometricTransform textGeometricTransform, int i, int i2) {
        if (textGeometricTransform != null) {
            setSpan(spannable, new ScaleXSpan(textGeometricTransform.getScaleX()), i, i2);
            setSpan(spannable, new SkewXSpan(textGeometricTransform.getSkewX()), i, i2);
        }
    }

    /* JADX INFO: renamed from: setLineHeight-KmRG4DE, reason: not valid java name */
    public static final void m1710setLineHeightKmRG4DE(Spannable spannable, long j, float f, Density density, LineHeightStyle lineHeightStyle) {
        float fM1705resolveLineHeightInPxo2QH7mI = m1705resolveLineHeightInPxo2QH7mI(j, f, density);
        if (Float.isNaN(fM1705resolveLineHeightInPxo2QH7mI)) {
            return;
        }
        setSpan(spannable, new LineHeightStyleSpan(fM1705resolveLineHeightInPxo2QH7mI, 0, (spannable.length() == 0 || StringsKt.last(spannable) == '\n') ? spannable.length() + 1 : spannable.length(), LineHeightStyle.Trim.m1792isTrimFirstLineTopimpl$ui_text_release(lineHeightStyle.m1768getTrimEVpEnUU()), LineHeightStyle.Trim.m1793isTrimLastLineBottomimpl$ui_text_release(lineHeightStyle.m1768getTrimEVpEnUU()), lineHeightStyle.m1766getAlignmentPIaL0Z0(), LineHeightStyle.Mode.m1781equalsimpl0(lineHeightStyle.m1767getModelzQqcRY(), LineHeightStyle.Mode.Companion.m1786getMinimumlzQqcRY())), 0, spannable.length());
    }

    /* JADX INFO: renamed from: setLineHeight-r9BaKPg, reason: not valid java name */
    public static final void m1711setLineHeightr9BaKPg(Spannable spannable, long j, float f, Density density) {
        float fM1705resolveLineHeightInPxo2QH7mI = m1705resolveLineHeightInPxo2QH7mI(j, f, density);
        if (Float.isNaN(fM1705resolveLineHeightInPxo2QH7mI)) {
            return;
        }
        setSpan(spannable, new LineHeightSpan(fM1705resolveLineHeightInPxo2QH7mI), 0, spannable.length());
    }

    public static final void setLocaleList(Spannable spannable, LocaleList localeList, int i, int i2) {
        if (localeList != null) {
            setSpan(spannable, LocaleListHelperMethods.INSTANCE.localeSpan(localeList), i, i2);
        }
    }

    private static final void setShadow(Spannable spannable, Shadow shadow, int i, int i2) {
        if (shadow != null) {
            setSpan(spannable, new ShadowSpan(ColorKt.m900toArgb8_81llA(shadow.m981getColor0d7_KjU()), Float.intBitsToFloat((int) (shadow.m982getOffsetF1C5BW0() >> 32)), Float.intBitsToFloat((int) (shadow.m982getOffsetF1C5BW0() & 4294967295L)), TextPaintExtensions_androidKt.correctBlurRadius(shadow.getBlurRadius())), i, i2);
        }
    }

    public static final void setSpan(Spannable spannable, Object obj, int i, int i2) {
        spannable.setSpan(obj, i, i2, 33);
    }

    private static final void setSpanStyle(Spannable spannable, SpanStyle spanStyle, int i, int i2, Density density) {
        m1707setBaselineShift0ocSgnM(spannable, spanStyle.m1570getBaselineShift5SSeXJ0(), i, i2);
        m1708setColorRPmYEkk(spannable, spanStyle.m1571getColor0d7_KjU(), i, i2);
        setBrush(spannable, spanStyle.getBrush(), spanStyle.getAlpha(), i, i2);
        setTextDecoration(spannable, spanStyle.getTextDecoration(), i, i2);
        m1709setFontSizeKmRG4DE(spannable, spanStyle.m1572getFontSizeXSAIIZE(), density, i, i2);
        setFontFeatureSettings(spannable, spanStyle.getFontFeatureSettings(), i, i2);
        setGeometricTransform(spannable, spanStyle.getTextGeometricTransform(), i, i2);
        setLocaleList(spannable, spanStyle.getLocaleList(), i, i2);
        m1706setBackgroundRPmYEkk(spannable, spanStyle.m1569getBackground0d7_KjU(), i, i2);
        setShadow(spannable, spanStyle.getShadow(), i, i2);
        setDrawStyle(spannable, spanStyle.getDrawStyle(), i, i2);
    }

    public static final void setSpanStyles(Spannable spannable, TextStyle textStyle, List list, Density density, Function4 function4) {
        MetricAffectingSpan metricAffectingSpanM1704createLetterSpacingSpaneAf_CNQ;
        setFontAttributes(spannable, textStyle, list, function4);
        int size = list.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            AnnotatedString.Range range = (AnnotatedString.Range) list.get(i);
            if (range.getItem() instanceof SpanStyle) {
                int start = range.getStart();
                int end = range.getEnd();
                if (start >= 0 && start < spannable.length() && end > start && end <= spannable.length()) {
                    setSpanStyle(spannable, (SpanStyle) range.getItem(), start, end, density);
                    if (getNeedsLetterSpacingSpan((SpanStyle) range.getItem())) {
                        z = true;
                    }
                }
            }
        }
        if (z) {
            int size2 = list.size();
            for (int i2 = 0; i2 < size2; i2++) {
                AnnotatedString.Range range2 = (AnnotatedString.Range) list.get(i2);
                AnnotatedString.Annotation annotation = (AnnotatedString.Annotation) range2.getItem();
                if (annotation instanceof SpanStyle) {
                    int start2 = range2.getStart();
                    int end2 = range2.getEnd();
                    if (start2 >= 0 && start2 < spannable.length() && end2 > start2 && end2 <= spannable.length() && (metricAffectingSpanM1704createLetterSpacingSpaneAf_CNQ = m1704createLetterSpacingSpaneAf_CNQ(((SpanStyle) annotation).m1575getLetterSpacingXSAIIZE(), density)) != null) {
                        setSpan(spannable, metricAffectingSpanM1704createLetterSpacingSpaneAf_CNQ, start2, end2);
                    }
                }
            }
        }
    }

    public static final void setTextDecoration(Spannable spannable, TextDecoration textDecoration, int i, int i2) {
        if (textDecoration != null) {
            TextDecoration.Companion companion = TextDecoration.Companion;
            setSpan(spannable, new TextDecorationSpan(textDecoration.contains(companion.getUnderline()), textDecoration.contains(companion.getLineThrough())), i, i2);
        }
    }

    public static final void setTextIndent(Spannable spannable, TextIndent textIndent, float f, Density density) {
        if (textIndent != null) {
            if ((TextUnit.m1934equalsimpl0(textIndent.m1827getFirstLineXSAIIZE(), TextUnitKt.getSp(0)) && TextUnit.m1934equalsimpl0(textIndent.m1828getRestLineXSAIIZE(), TextUnitKt.getSp(0))) || TextUnit.m1935getRawTypeimpl(textIndent.m1827getFirstLineXSAIIZE()) == 0 || TextUnit.m1935getRawTypeimpl(textIndent.m1828getRestLineXSAIIZE()) == 0) {
                return;
            }
            long jM1936getTypeUIouoOA = TextUnit.m1936getTypeUIouoOA(textIndent.m1827getFirstLineXSAIIZE());
            TextUnitType.Companion companion = TextUnitType.Companion;
            float fM1937getValueimpl = 0.0f;
            float fMo145toPxR2X_6o = TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1952getSpUIouoOA()) ? density.mo145toPxR2X_6o(textIndent.m1827getFirstLineXSAIIZE()) : TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA, companion.m1951getEmUIouoOA()) ? TextUnit.m1937getValueimpl(textIndent.m1827getFirstLineXSAIIZE()) * f : 0.0f;
            long jM1936getTypeUIouoOA2 = TextUnit.m1936getTypeUIouoOA(textIndent.m1828getRestLineXSAIIZE());
            if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA2, companion.m1952getSpUIouoOA())) {
                fM1937getValueimpl = density.mo145toPxR2X_6o(textIndent.m1828getRestLineXSAIIZE());
            } else if (TextUnitType.m1947equalsimpl0(jM1936getTypeUIouoOA2, companion.m1951getEmUIouoOA())) {
                fM1937getValueimpl = TextUnit.m1937getValueimpl(textIndent.m1828getRestLineXSAIIZE()) * f;
            }
            setSpan(spannable, new LeadingMarginSpan.Standard((int) Math.ceil(fMo145toPxR2X_6o), (int) Math.ceil(fM1937getValueimpl)), 0, spannable.length());
        }
    }
}
