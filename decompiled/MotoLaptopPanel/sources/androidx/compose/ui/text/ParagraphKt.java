package androidx.compose.ui.text;

import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.unit.Density;
import java.util.List;

/* JADX INFO: compiled from: Paragraph.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ParagraphKt {
    /* JADX INFO: renamed from: Paragraph-Ul8oQg4, reason: not valid java name */
    public static final Paragraph m1543ParagraphUl8oQg4(String str, TextStyle textStyle, long j, Density density, FontFamily.Resolver resolver, List list, List list2, int i, int i2) {
        return androidx.compose.ui.text.platform.AndroidParagraph_androidKt.m1699ActualParagraphXGqx6AY(str, textStyle, list, list2, i, i2, j, density, resolver);
    }

    /* JADX INFO: renamed from: Paragraph-czeN-Hc, reason: not valid java name */
    public static final Paragraph m1545ParagraphczeNHc(ParagraphIntrinsics paragraphIntrinsics, long j, int i, int i2) {
        return androidx.compose.ui.text.platform.AndroidParagraph_androidKt.m1698ActualParagraph4FmOz70(paragraphIntrinsics, i, i2, j);
    }

    public static final int ceilToInt(float f) {
        return (int) Math.ceil(f);
    }
}
