package androidx.compose.ui.text.platform;

import androidx.compose.ui.text.AndroidParagraph;
import androidx.compose.ui.text.Paragraph;
import androidx.compose.ui.text.ParagraphIntrinsics;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.unit.Density;
import java.util.List;

/* JADX INFO: compiled from: ActualParagraph.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AndroidParagraph_androidKt {
    /* JADX INFO: renamed from: ActualParagraph-4FmOz70, reason: not valid java name */
    public static final Paragraph m1698ActualParagraph4FmOz70(ParagraphIntrinsics paragraphIntrinsics, int i, int i2, long j) {
        paragraphIntrinsics.getClass();
        return new AndroidParagraph((AndroidParagraphIntrinsics) paragraphIntrinsics, i, i2, j, null);
    }

    /* JADX INFO: renamed from: ActualParagraph-XGqx6AY, reason: not valid java name */
    public static final Paragraph m1699ActualParagraphXGqx6AY(String str, TextStyle textStyle, List list, List list2, int i, int i2, long j, Density density, FontFamily.Resolver resolver) {
        return new AndroidParagraph(new AndroidParagraphIntrinsics(str, textStyle, list, list2, resolver, density), i, i2, j, null);
    }
}
