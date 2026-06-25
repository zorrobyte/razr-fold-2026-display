package androidx.compose.foundation.interaction;

/* JADX INFO: compiled from: PressInteraction.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PressInteraction$Release implements Interaction {
    private final PressInteraction$Press press;

    public PressInteraction$Release(PressInteraction$Press pressInteraction$Press) {
        this.press = pressInteraction$Press;
    }

    public final PressInteraction$Press getPress() {
        return this.press;
    }
}
