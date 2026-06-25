package androidx.compose.ui.text.android;

import android.text.Layout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;

/* JADX INFO: compiled from: LayoutCompat.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LayoutCompat {
    public static final LayoutCompat INSTANCE = new LayoutCompat();
    private static final Layout.Alignment DEFAULT_LAYOUT_ALIGNMENT = Layout.Alignment.ALIGN_NORMAL;
    private static final TextDirectionHeuristic DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristics.FIRSTSTRONG_LTR;
    public static final int $stable = 8;

    private LayoutCompat() {
    }

    public final Layout.Alignment getDEFAULT_LAYOUT_ALIGNMENT$ui_text_release() {
        return DEFAULT_LAYOUT_ALIGNMENT;
    }

    public final TextDirectionHeuristic getDEFAULT_TEXT_DIRECTION_HEURISTIC$ui_text_release() {
        return DEFAULT_TEXT_DIRECTION_HEURISTIC;
    }
}
