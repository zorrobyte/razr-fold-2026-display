package androidx.compose.foundation.interaction;

/* JADX INFO: compiled from: FocusInteraction.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FocusInteraction$Unfocus implements Interaction {
    private final FocusInteraction$Focus focus;

    public FocusInteraction$Unfocus(FocusInteraction$Focus focusInteraction$Focus) {
        this.focus = focusInteraction$Focus;
    }

    public final FocusInteraction$Focus getFocus() {
        return this.focus;
    }
}
