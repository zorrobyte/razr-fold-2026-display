package androidx.compose.runtime;

import androidx.compose.runtime.internal.PersistentCompositionLocalHashMap;
import androidx.compose.runtime.internal.PersistentCompositionLocalMapKt;

/* JADX INFO: compiled from: CompositionLocalMap.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CompositionLocalMapKt {
    public static final boolean contains(PersistentCompositionLocalMap persistentCompositionLocalMap, CompositionLocal compositionLocal) {
        compositionLocal.getClass();
        return persistentCompositionLocalMap.containsKey(compositionLocal);
    }

    public static final Object read(PersistentCompositionLocalMap persistentCompositionLocalMap, CompositionLocal compositionLocal) {
        compositionLocal.getClass();
        Object defaultValueHolder$runtime_release = persistentCompositionLocalMap.get((Object) compositionLocal);
        if (defaultValueHolder$runtime_release == null) {
            defaultValueHolder$runtime_release = compositionLocal.getDefaultValueHolder$runtime_release();
        }
        return ((ValueHolder) defaultValueHolder$runtime_release).readValue(persistentCompositionLocalMap);
    }

    public static final PersistentCompositionLocalMap updateCompositionMap(ProvidedValue[] providedValueArr, PersistentCompositionLocalMap persistentCompositionLocalMap, PersistentCompositionLocalMap persistentCompositionLocalMap2) {
        PersistentCompositionLocalHashMap.Builder builder = PersistentCompositionLocalMapKt.persistentCompositionLocalHashMapOf().builder();
        for (ProvidedValue providedValue : providedValueArr) {
            CompositionLocal compositionLocal = providedValue.getCompositionLocal();
            compositionLocal.getClass();
            ProvidableCompositionLocal providableCompositionLocal = (ProvidableCompositionLocal) compositionLocal;
            if (providedValue.getCanOverride() || !contains(persistentCompositionLocalMap, providableCompositionLocal)) {
                builder.put(providableCompositionLocal, providableCompositionLocal.updatedStateOf$runtime_release(providedValue, (ValueHolder) persistentCompositionLocalMap2.get((Object) providableCompositionLocal)));
            }
        }
        return builder.build();
    }

    public static /* synthetic */ PersistentCompositionLocalMap updateCompositionMap$default(ProvidedValue[] providedValueArr, PersistentCompositionLocalMap persistentCompositionLocalMap, PersistentCompositionLocalMap persistentCompositionLocalMap2, int i, Object obj) {
        if ((i & 4) != 0) {
            persistentCompositionLocalMap2 = PersistentCompositionLocalMapKt.persistentCompositionLocalHashMapOf();
        }
        return updateCompositionMap(providedValueArr, persistentCompositionLocalMap, persistentCompositionLocalMap2);
    }
}
