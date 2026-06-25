package androidx.compose.runtime.external.kotlinx.collections.immutable;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableList;

/* JADX INFO: compiled from: ImmutableList.kt */
/* JADX INFO: loaded from: classes.dex */
public interface PersistentList extends ImmutableList, ImmutableCollection {

    /* JADX INFO: compiled from: ImmutableList.kt */
    public interface Builder extends List, Collection, KMappedMarker, KMutableList {
        PersistentList build();
    }

    @Override // java.util.List
    PersistentList add(int i, Object obj);

    @Override // java.util.List, java.util.Collection
    PersistentList add(Object obj);

    @Override // java.util.List, java.util.Collection
    PersistentList addAll(Collection collection);

    Builder builder();

    @Override // java.util.List, java.util.Collection
    PersistentList remove(Object obj);

    @Override // java.util.List, java.util.Collection
    PersistentList removeAll(Collection collection);

    PersistentList removeAll(Function1 function1);

    PersistentList removeAt(int i);

    @Override // java.util.List
    PersistentList set(int i, Object obj);
}
