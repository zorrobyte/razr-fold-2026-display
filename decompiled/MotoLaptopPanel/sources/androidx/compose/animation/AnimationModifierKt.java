package androidx.compose.animation;

import androidx.compose.ui.unit.IntSize;

/* JADX INFO: compiled from: AnimationModifier.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AnimationModifierKt {
    private static final long InvalidSize;

    static {
        long j = Integer.MIN_VALUE;
        InvalidSize = IntSize.m1919constructorimpl((j & 4294967295L) | (j << 32));
    }

    public static final long getInvalidSize() {
        return InvalidSize;
    }

    /* JADX INFO: renamed from: isValid-ozmzZPI, reason: not valid java name */
    public static final boolean m22isValidozmzZPI(long j) {
        return !IntSize.m1921equalsimpl0(j, InvalidSize);
    }
}
