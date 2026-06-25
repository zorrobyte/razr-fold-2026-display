package androidx.compose.ui.draganddrop;

import android.view.DragEvent;

/* JADX INFO: compiled from: DragAndDrop.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DragAndDropEvent {
    private final DragEvent dragEvent;

    public DragAndDropEvent(DragEvent dragEvent) {
        this.dragEvent = dragEvent;
    }

    public final DragEvent getDragEvent$ui_release() {
        return this.dragEvent;
    }
}
