package androidx.compose.ui.draganddrop;

/* JADX INFO: compiled from: DragAndDropManager.kt */
/* JADX INFO: loaded from: classes.dex */
public interface DragAndDropManager {
    boolean isInterestedTarget(DragAndDropTarget dragAndDropTarget);

    void registerTargetInterest(DragAndDropTarget dragAndDropTarget);
}
