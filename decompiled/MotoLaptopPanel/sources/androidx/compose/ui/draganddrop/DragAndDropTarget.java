package androidx.compose.ui.draganddrop;

/* JADX INFO: compiled from: DragAndDrop.kt */
/* JADX INFO: loaded from: classes.dex */
public interface DragAndDropTarget {
    boolean onDrop(DragAndDropEvent dragAndDropEvent);

    void onEnded(DragAndDropEvent dragAndDropEvent);

    void onEntered(DragAndDropEvent dragAndDropEvent);

    void onExited(DragAndDropEvent dragAndDropEvent);

    void onMoved(DragAndDropEvent dragAndDropEvent);

    void onStarted(DragAndDropEvent dragAndDropEvent);
}
