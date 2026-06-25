package androidx.compose.runtime.saveable;

import androidx.collection.MutableScatterMap;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ProvidableCompositionLocal;
import java.util.Map;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: SaveableStateRegistry.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SaveableStateRegistryKt {
    private static final ProvidableCompositionLocal LocalSaveableStateRegistry = CompositionLocalKt.staticCompositionLocalOf(new Function0() { // from class: androidx.compose.runtime.saveable.SaveableStateRegistryKt$LocalSaveableStateRegistry$1
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final SaveableStateRegistry mo2224invoke() {
            return null;
        }
    });

    public static final SaveableStateRegistry SaveableStateRegistry(Map map, Function1 function1) {
        return new SaveableStateRegistryImpl(map, function1);
    }

    public static final ProvidableCompositionLocal getLocalSaveableStateRegistry() {
        return LocalSaveableStateRegistry;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MutableScatterMap toMutableScatterMap(Map map) {
        MutableScatterMap mutableScatterMap = new MutableScatterMap(map.size());
        mutableScatterMap.putAll(map);
        return mutableScatterMap;
    }
}
