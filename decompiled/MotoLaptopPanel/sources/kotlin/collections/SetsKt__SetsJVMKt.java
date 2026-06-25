package kotlin.collections;

import java.util.Collections;
import java.util.Set;
import kotlin.collections.builders.SetBuilder;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: SetsJVM.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SetsKt__SetsJVMKt {
    public static Set build(Set set) {
        set.getClass();
        return ((SetBuilder) set).build();
    }

    public static Set createSetBuilder(int i) {
        return new SetBuilder(i);
    }

    public static Set setOf(Object obj) {
        Set setSingleton = Collections.singleton(obj);
        setSingleton.getClass();
        return setSingleton;
    }
}
