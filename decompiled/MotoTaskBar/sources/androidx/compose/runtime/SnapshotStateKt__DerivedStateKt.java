package androidx.compose.runtime;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.internal.SnapshotThreadLocal;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: DerivedState.kt */
/* JADX INFO: loaded from: classes.dex */
abstract /* synthetic */ class SnapshotStateKt__DerivedStateKt {
    private static final SnapshotThreadLocal calculationBlockNestedLevel = new SnapshotThreadLocal();
    private static final SnapshotThreadLocal derivedStateObservers = new SnapshotThreadLocal();

    public static final MutableVector derivedStateObservers() {
        SnapshotThreadLocal snapshotThreadLocal = derivedStateObservers;
        MutableVector mutableVector = (MutableVector) snapshotThreadLocal.get();
        if (mutableVector != null) {
            return mutableVector;
        }
        MutableVector mutableVector2 = new MutableVector(new DerivedStateObserver[0], 0);
        snapshotThreadLocal.set(mutableVector2);
        return mutableVector2;
    }

    public static final State derivedStateOf(Function0 function0) {
        return new DerivedSnapshotState(function0, null);
    }
}
