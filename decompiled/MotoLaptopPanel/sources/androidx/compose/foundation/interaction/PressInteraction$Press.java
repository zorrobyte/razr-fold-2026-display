package androidx.compose.foundation.interaction;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PressInteraction.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PressInteraction$Press implements Interaction {
    private final long pressPosition;

    private PressInteraction$Press(long j) {
        this.pressPosition = j;
    }

    public /* synthetic */ PressInteraction$Press(long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(j);
    }

    /* JADX INFO: renamed from: getPressPosition-F1C5BW0, reason: not valid java name */
    public final long m155getPressPositionF1C5BW0() {
        return this.pressPosition;
    }
}
