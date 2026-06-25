package androidx.compose.animation;

import androidx.compose.animation.core.Transition;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.ui.unit.IntSize;

/* JADX INFO: compiled from: AnimatedVisibility.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AnimatedVisibilityScopeImpl implements AnimatedVisibilityScope {
    private final MutableState targetSize = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(IntSize.m1918boximpl(IntSize.Companion.m1927getZeroYbymL2g()), null, 2, null);
    private Transition transition;

    public AnimatedVisibilityScopeImpl(Transition transition) {
        this.transition = transition;
    }

    public final MutableState getTargetSize$animation() {
        return this.targetSize;
    }
}
