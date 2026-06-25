package androidx.compose.ui.focus;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FocusProperties.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CancelIndicatingFocusBoundaryScope implements FocusEnterExitScope {
    private boolean isCanceled;
    private final int requestedFocusDirection;

    private CancelIndicatingFocusBoundaryScope(int i) {
        this.requestedFocusDirection = i;
    }

    public /* synthetic */ CancelIndicatingFocusBoundaryScope(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }

    public final boolean isCanceled() {
        return this.isCanceled;
    }
}
