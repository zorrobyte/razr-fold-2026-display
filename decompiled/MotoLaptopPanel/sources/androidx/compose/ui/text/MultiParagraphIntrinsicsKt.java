package androidx.compose.ui.text;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: MultiParagraphIntrinsics.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class MultiParagraphIntrinsicsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final List getLocalPlaceholders(List list, int i, int i2) {
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i3 = 0; i3 < size; i3++) {
            AnnotatedString.Range range = (AnnotatedString.Range) list.get(i3);
            if (AnnotatedStringKt.intersect(i, i2, range.getStart(), range.getEnd())) {
                if (!(i <= range.getStart() && range.getEnd() <= i2)) {
                    InlineClassHelperKt.throwIllegalArgumentException("placeholder can not overlap with paragraph.");
                }
                arrayList.add(new AnnotatedString.Range(range.getItem(), range.getStart() - i, range.getEnd() - i));
            }
        }
        return arrayList;
    }
}
