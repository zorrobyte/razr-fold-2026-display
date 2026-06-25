package androidx.compose.ui.text;

import android.text.Spannable;
import android.text.SpannableString;
import androidx.compose.ui.text.android.SpannedExtensions_androidKt;
import androidx.compose.ui.text.android.TextLayout;
import androidx.compose.ui.text.android.style.IndentationFixSpan;
import androidx.compose.ui.text.platform.extensions.SpannableExtensions_androidKt;
import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;

/* JADX INFO: compiled from: AndroidParagraph.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AndroidParagraph_androidKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence attachIndentationFixSpan(CharSequence charSequence) {
        if (charSequence.length() == 0) {
            return charSequence;
        }
        Spannable spannableString = charSequence instanceof Spannable ? (Spannable) charSequence : null;
        if (spannableString == null) {
            spannableString = new SpannableString(charSequence);
        }
        if (!SpannedExtensions_androidKt.hasSpan(spannableString, IndentationFixSpan.class)) {
            SpannableExtensions_androidKt.setSpan(spannableString, new IndentationFixSpan(), spannableString.length() - 1, spannableString.length() - 1);
        }
        return spannableString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int numberOfLinesThatFitMaxHeight(TextLayout textLayout, int i) {
        int lineCount = textLayout.getLineCount();
        for (int i2 = 0; i2 < lineCount; i2++) {
            if (textLayout.getLineBottom(i2) > i) {
                return i2;
            }
        }
        return textLayout.getLineCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean shouldAttachIndentationFixSpan(TextStyle textStyle, boolean z) {
        if (z && !TextUnit.m1934equalsimpl0(textStyle.m1612getLetterSpacingXSAIIZE(), TextUnitKt.getSp(0)) && !TextUnit.m1934equalsimpl0(textStyle.m1612getLetterSpacingXSAIIZE(), TextUnit.Companion.m1941getUnspecifiedXSAIIZE())) {
            int iM1615getTextAligne0LSkKk = textStyle.m1615getTextAligne0LSkKk();
            TextAlign.Companion companion = TextAlign.Companion;
            if (!TextAlign.m1801equalsimpl0(iM1615getTextAligne0LSkKk, companion.m1811getUnspecifiede0LSkKk()) && !TextAlign.m1801equalsimpl0(textStyle.m1615getTextAligne0LSkKk(), companion.m1810getStarte0LSkKk()) && !TextAlign.m1801equalsimpl0(textStyle.m1615getTextAligne0LSkKk(), companion.m1807getJustifye0LSkKk())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: toLayoutAlign-aXe7zB0, reason: not valid java name */
    public static final int m1521toLayoutAlignaXe7zB0(int i) {
        TextAlign.Companion companion = TextAlign.Companion;
        if (TextAlign.m1801equalsimpl0(i, companion.m1808getLefte0LSkKk())) {
            return 3;
        }
        if (TextAlign.m1801equalsimpl0(i, companion.m1809getRighte0LSkKk())) {
            return 4;
        }
        if (TextAlign.m1801equalsimpl0(i, companion.m1805getCentere0LSkKk())) {
            return 2;
        }
        return (!TextAlign.m1801equalsimpl0(i, companion.m1810getStarte0LSkKk()) && TextAlign.m1801equalsimpl0(i, companion.m1806getEnde0LSkKk())) ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: toLayoutBreakStrategy-xImikfE, reason: not valid java name */
    public static final int m1522toLayoutBreakStrategyxImikfE(int i) {
        LineBreak.Strategy.Companion companion = LineBreak.Strategy.Companion;
        if (LineBreak.Strategy.m1749equalsimpl0(i, companion.m1753getSimplefcGXIks())) {
            return 0;
        }
        if (LineBreak.Strategy.m1749equalsimpl0(i, companion.m1752getHighQualityfcGXIks())) {
            return 1;
        }
        return LineBreak.Strategy.m1749equalsimpl0(i, companion.m1751getBalancedfcGXIks()) ? 2 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: toLayoutHyphenationFrequency--3fSNIE, reason: not valid java name */
    public static final int m1523toLayoutHyphenationFrequency3fSNIE(int i) {
        Hyphens.Companion companion = Hyphens.Companion;
        if (Hyphens.m1729equalsimpl0(i, companion.m1733getAutovmbZdU8())) {
            return 4;
        }
        Hyphens.m1729equalsimpl0(i, companion.m1734getNonevmbZdU8());
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: toLayoutLineBreakStyle-hpcqdu8, reason: not valid java name */
    public static final int m1524toLayoutLineBreakStylehpcqdu8(int i) {
        LineBreak.Strictness.Companion companion = LineBreak.Strictness.Companion;
        if (LineBreak.Strictness.m1755equalsimpl0(i, companion.m1757getDefaultusljTpc())) {
            return 0;
        }
        if (LineBreak.Strictness.m1755equalsimpl0(i, companion.m1758getLooseusljTpc())) {
            return 1;
        }
        if (LineBreak.Strictness.m1755equalsimpl0(i, companion.m1759getNormalusljTpc())) {
            return 2;
        }
        return LineBreak.Strictness.m1755equalsimpl0(i, companion.m1760getStrictusljTpc()) ? 3 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: toLayoutLineBreakWordStyle-wPN0Rpw, reason: not valid java name */
    public static final int m1525toLayoutLineBreakWordStylewPN0Rpw(int i) {
        LineBreak.WordBreak.Companion companion = LineBreak.WordBreak.Companion;
        return (!LineBreak.WordBreak.m1762equalsimpl0(i, companion.m1764getDefaultjp8hJ3c()) && LineBreak.WordBreak.m1762equalsimpl0(i, companion.m1765getPhrasejp8hJ3c())) ? 1 : 0;
    }
}
