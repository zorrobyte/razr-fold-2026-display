package androidx.compose.runtime;

import androidx.compose.runtime.tooling.CompositionData;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Composer.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CompositionDataImpl implements CompositionData {
    private final Composition composition;

    public CompositionDataImpl(Composition composition) {
        this.composition = composition;
    }

    public boolean equals(Object obj) {
        return (obj instanceof CompositionDataImpl) && Intrinsics.areEqual(this.composition, ((CompositionDataImpl) obj).composition);
    }

    public int hashCode() {
        return this.composition.hashCode() * 31;
    }
}
