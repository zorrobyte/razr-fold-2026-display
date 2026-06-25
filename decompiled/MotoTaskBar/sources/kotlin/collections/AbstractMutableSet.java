package kotlin.collections;

import java.util.Set;
import kotlin.jvm.internal.markers.KMutableSet;

/* JADX INFO: compiled from: AbstractMutableSet.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractMutableSet extends java.util.AbstractSet implements Set, KMutableSet {
    protected AbstractMutableSet() {
    }

    public abstract int getSize();

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final /* bridge */ int size() {
        return getSize();
    }
}
