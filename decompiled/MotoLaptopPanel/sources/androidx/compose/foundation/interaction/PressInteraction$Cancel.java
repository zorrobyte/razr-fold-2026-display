package androidx.compose.foundation.interaction;

/* JADX INFO: compiled from: PressInteraction.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PressInteraction$Cancel implements Interaction {
    private final PressInteraction$Press press;

    public PressInteraction$Cancel(PressInteraction$Press pressInteraction$Press) {
        this.press = pressInteraction$Press;
    }

    public final PressInteraction$Press getPress() {
        return this.press;
    }
}
