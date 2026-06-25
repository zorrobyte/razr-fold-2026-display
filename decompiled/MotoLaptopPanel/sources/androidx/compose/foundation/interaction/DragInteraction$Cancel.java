package androidx.compose.foundation.interaction;

/* JADX INFO: compiled from: DragInteraction.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DragInteraction$Cancel implements Interaction {
    private final DragInteraction$Start start;

    public DragInteraction$Cancel(DragInteraction$Start dragInteraction$Start) {
        this.start = dragInteraction$Start;
    }

    public final DragInteraction$Start getStart() {
        return this.start;
    }
}
