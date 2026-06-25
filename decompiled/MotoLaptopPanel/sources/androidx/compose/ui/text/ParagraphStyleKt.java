package androidx.compose.ui.text;

import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDirection;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.unit.TextUnit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ParagraphStyle.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ParagraphStyleKt {
    private static final long DefaultLineHeight = TextUnit.Companion.m1941getUnspecifiedXSAIIZE();

    /* JADX INFO: renamed from: fastMerge-j5T8yCg, reason: not valid java name */
    public static final ParagraphStyle m1553fastMergej5T8yCg(ParagraphStyle paragraphStyle, int i, int i2, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion) {
        long j2;
        long j3;
        int iM1551getTextAligne0LSkKk = i;
        TextIndent textIndent2 = textIndent;
        TextAlign.Companion companion = TextAlign.Companion;
        if (TextAlign.m1801equalsimpl0(iM1551getTextAligne0LSkKk, companion.m1811getUnspecifiede0LSkKk()) || TextAlign.m1801equalsimpl0(iM1551getTextAligne0LSkKk, paragraphStyle.m1551getTextAligne0LSkKk())) {
            if (TextUnit.m1935getRawTypeimpl(j) == 0) {
                j2 = 0;
                j3 = j;
            } else {
                j2 = 0;
                j3 = j;
                if (TextUnit.m1934equalsimpl0(j3, paragraphStyle.m1550getLineHeightXSAIIZE())) {
                }
            }
            if ((textIndent2 == null || Intrinsics.areEqual(textIndent2, paragraphStyle.getTextIndent())) && ((TextDirection.m1815equalsimpl0(i2, TextDirection.Companion.m1824getUnspecifieds_7Xco()) || TextDirection.m1815equalsimpl0(i2, paragraphStyle.m1552getTextDirections_7Xco())) && ((platformParagraphStyle == null || Intrinsics.areEqual(platformParagraphStyle, paragraphStyle.getPlatformStyle())) && ((lineHeightStyle == null || Intrinsics.areEqual(lineHeightStyle, paragraphStyle.getLineHeightStyle())) && ((LineBreak.m1739equalsimpl0(i3, LineBreak.Companion.m1747getUnspecifiedrAG3T2k()) || LineBreak.m1739equalsimpl0(i3, paragraphStyle.m1549getLineBreakrAG3T2k())) && ((Hyphens.m1729equalsimpl0(i4, Hyphens.Companion.m1735getUnspecifiedvmbZdU8()) || Hyphens.m1729equalsimpl0(i4, paragraphStyle.m1548getHyphensvmbZdU8())) && (textMotion == null || Intrinsics.areEqual(textMotion, paragraphStyle.getTextMotion())))))))) {
                return paragraphStyle;
            }
        } else {
            j2 = 0;
            j3 = j;
        }
        long jM1550getLineHeightXSAIIZE = TextUnit.m1935getRawTypeimpl(j3) == j2 ? paragraphStyle.m1550getLineHeightXSAIIZE() : j3;
        if (textIndent2 == null) {
            textIndent2 = paragraphStyle.getTextIndent();
        }
        TextIndent textIndent3 = textIndent2;
        if (TextAlign.m1801equalsimpl0(iM1551getTextAligne0LSkKk, companion.m1811getUnspecifiede0LSkKk())) {
            iM1551getTextAligne0LSkKk = paragraphStyle.m1551getTextAligne0LSkKk();
        }
        return new ParagraphStyle(iM1551getTextAligne0LSkKk, !TextDirection.m1815equalsimpl0(i2, TextDirection.Companion.m1824getUnspecifieds_7Xco()) ? i2 : paragraphStyle.m1552getTextDirections_7Xco(), jM1550getLineHeightXSAIIZE, textIndent3, mergePlatformStyle(paragraphStyle, platformParagraphStyle), lineHeightStyle == null ? paragraphStyle.getLineHeightStyle() : lineHeightStyle, !LineBreak.m1739equalsimpl0(i3, LineBreak.Companion.m1747getUnspecifiedrAG3T2k()) ? i3 : paragraphStyle.m1549getLineBreakrAG3T2k(), !Hyphens.m1729equalsimpl0(i4, Hyphens.Companion.m1735getUnspecifiedvmbZdU8()) ? i4 : paragraphStyle.m1548getHyphensvmbZdU8(), textMotion == null ? paragraphStyle.getTextMotion() : textMotion, null);
    }

    private static final PlatformParagraphStyle mergePlatformStyle(ParagraphStyle paragraphStyle, PlatformParagraphStyle platformParagraphStyle) {
        return paragraphStyle.getPlatformStyle() == null ? platformParagraphStyle : platformParagraphStyle == null ? paragraphStyle.getPlatformStyle() : paragraphStyle.getPlatformStyle().merge(platformParagraphStyle);
    }

    public static final ParagraphStyle resolveParagraphStyleDefaults(ParagraphStyle paragraphStyle, LayoutDirection layoutDirection) {
        int iM1551getTextAligne0LSkKk = paragraphStyle.m1551getTextAligne0LSkKk();
        TextAlign.Companion companion = TextAlign.Companion;
        int iM1810getStarte0LSkKk = TextAlign.m1801equalsimpl0(iM1551getTextAligne0LSkKk, companion.m1811getUnspecifiede0LSkKk()) ? companion.m1810getStarte0LSkKk() : paragraphStyle.m1551getTextAligne0LSkKk();
        int iM1618resolveTextDirectionIhaHGbI = TextStyleKt.m1618resolveTextDirectionIhaHGbI(layoutDirection, paragraphStyle.m1552getTextDirections_7Xco());
        long jM1550getLineHeightXSAIIZE = TextUnit.m1935getRawTypeimpl(paragraphStyle.m1550getLineHeightXSAIIZE()) == 0 ? DefaultLineHeight : paragraphStyle.m1550getLineHeightXSAIIZE();
        TextIndent textIndent = paragraphStyle.getTextIndent();
        if (textIndent == null) {
            textIndent = TextIndent.Companion.getNone();
        }
        TextIndent textIndent2 = textIndent;
        PlatformParagraphStyle platformStyle = paragraphStyle.getPlatformStyle();
        LineHeightStyle lineHeightStyle = paragraphStyle.getLineHeightStyle();
        int iM1549getLineBreakrAG3T2k = paragraphStyle.m1549getLineBreakrAG3T2k();
        LineBreak.Companion companion2 = LineBreak.Companion;
        int iM1746getSimplerAG3T2k = LineBreak.m1739equalsimpl0(iM1549getLineBreakrAG3T2k, companion2.m1747getUnspecifiedrAG3T2k()) ? companion2.m1746getSimplerAG3T2k() : paragraphStyle.m1549getLineBreakrAG3T2k();
        int iM1548getHyphensvmbZdU8 = paragraphStyle.m1548getHyphensvmbZdU8();
        Hyphens.Companion companion3 = Hyphens.Companion;
        int iM1734getNonevmbZdU8 = Hyphens.m1729equalsimpl0(iM1548getHyphensvmbZdU8, companion3.m1735getUnspecifiedvmbZdU8()) ? companion3.m1734getNonevmbZdU8() : paragraphStyle.m1548getHyphensvmbZdU8();
        TextMotion textMotion = paragraphStyle.getTextMotion();
        if (textMotion == null) {
            textMotion = TextMotion.Companion.getStatic();
        }
        return new ParagraphStyle(iM1810getStarte0LSkKk, iM1618resolveTextDirectionIhaHGbI, jM1550getLineHeightXSAIIZE, textIndent2, platformStyle, lineHeightStyle, iM1746getSimplerAG3T2k, iM1734getNonevmbZdU8, textMotion, null);
    }
}
