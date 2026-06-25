package androidx.compose.foundation.interaction;

/* JADX INFO: compiled from: HoverInteraction.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HoverInteraction$Exit implements Interaction {
    private final HoverInteraction$Enter enter;

    public HoverInteraction$Exit(HoverInteraction$Enter hoverInteraction$Enter) {
        this.enter = hoverInteraction$Enter;
    }

    public final HoverInteraction$Enter getEnter() {
        return this.enter;
    }
}
