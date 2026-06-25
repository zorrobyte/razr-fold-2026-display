package androidx.compose.runtime;

import java.util.Map;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableMap;

/* JADX INFO: compiled from: CompositionLocalMap.kt */
/* JADX INFO: loaded from: classes.dex */
public interface PersistentCompositionLocalMap extends Map, KMappedMarker, CompositionLocalMap, CompositionLocalAccessorScope {

    /* JADX INFO: compiled from: CompositionLocalMap.kt */
    public interface Builder extends Map, KMutableMap {
        PersistentCompositionLocalMap build();
    }

    Builder builder();

    @Override // androidx.compose.runtime.CompositionLocalAccessorScope
    default Object getCurrentValue(CompositionLocal compositionLocal) {
        return CompositionLocalMapKt.read(this, compositionLocal);
    }

    PersistentCompositionLocalMap putValue(CompositionLocal compositionLocal, ValueHolder valueHolder);
}
