package androidx.compose.ui.layout;

/* JADX INFO: compiled from: AlignmentLine.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AlignmentLineKt {
    private static final HorizontalAlignmentLine FirstBaseline = new HorizontalAlignmentLine(AlignmentLineKt$FirstBaseline$1.INSTANCE);
    private static final HorizontalAlignmentLine LastBaseline = new HorizontalAlignmentLine(AlignmentLineKt$LastBaseline$1.INSTANCE);

    public static final int merge(AlignmentLine alignmentLine, int i, int i2) {
        return ((Number) alignmentLine.getMerger$ui_release().invoke(Integer.valueOf(i), Integer.valueOf(i2))).intValue();
    }
}
