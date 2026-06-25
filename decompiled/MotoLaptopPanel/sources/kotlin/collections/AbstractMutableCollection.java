package kotlin.collections;

import java.util.Collection;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: AbstractMutableCollection.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractMutableCollection extends java.util.AbstractCollection implements Collection, KMappedMarker {
    protected AbstractMutableCollection() {
    }

    public abstract int getSize();

    @Override // java.util.AbstractCollection, java.util.Collection
    public final /* bridge */ int size() {
        return getSize();
    }
}
