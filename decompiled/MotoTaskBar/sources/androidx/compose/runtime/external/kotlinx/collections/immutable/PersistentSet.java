package androidx.compose.runtime.external.kotlinx.collections.immutable;

/* JADX INFO: compiled from: ImmutableSet.kt */
/* JADX INFO: loaded from: classes.dex */
public interface PersistentSet extends ImmutableSet, ImmutableCollection {
    @Override // java.util.Set, java.util.Collection
    PersistentSet add(Object obj);

    @Override // java.util.Set, java.util.Collection
    PersistentSet remove(Object obj);
}
