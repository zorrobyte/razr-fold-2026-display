package androidx.compose.runtime;

/* JADX INFO: compiled from: RecomposeScopeImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public interface RecomposeScopeOwner {
    InvalidationResult invalidate(RecomposeScopeImpl recomposeScopeImpl, Object obj);

    void recomposeScopeReleased(RecomposeScopeImpl recomposeScopeImpl);

    void recordReadOf(Object obj);
}
