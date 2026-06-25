package androidx.compose.foundation.text.modifiers;

import kotlin.text.StringsKt;

/* JADX INFO: compiled from: MinLinesConstrainer.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class MinLinesConstrainerKt {
    private static final String EmptyTextReplacement;
    private static final String TwoLineTextReplacement;

    static {
        String strRepeat = StringsKt.repeat("H", 10);
        EmptyTextReplacement = strRepeat;
        TwoLineTextReplacement = strRepeat + '\n' + strRepeat;
    }
}
