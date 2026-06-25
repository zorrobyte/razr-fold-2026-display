package androidx.compose.ui.text;

import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.platform.AndroidParagraphIntrinsics_androidKt;
import androidx.compose.ui.unit.Density;
import java.util.List;

/* JADX INFO: compiled from: ParagraphIntrinsics.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ParagraphIntrinsicsKt {
    public static final ParagraphIntrinsics ParagraphIntrinsics(String str, TextStyle textStyle, List list, Density density, FontFamily.Resolver resolver, List list2) {
        return AndroidParagraphIntrinsics_androidKt.ActualParagraphIntrinsics(str, textStyle, list, list2, density, resolver);
    }
}
