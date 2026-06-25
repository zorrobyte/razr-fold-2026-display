package androidx.compose.ui.draganddrop;

import androidx.compose.ui.geometry.Offset;

/* JADX INFO: compiled from: DragAndDrop.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DragAndDrop_androidKt {
    public static final long getPositionInRoot(DragAndDropEvent dragAndDropEvent) {
        float x = dragAndDropEvent.getDragEvent$ui_release().getX();
        float y = dragAndDropEvent.getDragEvent$ui_release().getY();
        return Offset.m182constructorimpl((((long) Float.floatToRawIntBits(x)) << 32) | (((long) Float.floatToRawIntBits(y)) & 4294967295L));
    }
}
