package androidx.compose.runtime;

import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: Applier.kt */
/* JADX INFO: loaded from: classes.dex */
public interface Applier {
    default void apply(Function2 function2, Object obj) {
        function2.invoke(getCurrent(), obj);
    }

    void clear();

    void down(Object obj);

    Object getCurrent();

    void insertBottomUp(int i, Object obj);

    void insertTopDown(int i, Object obj);

    void move(int i, int i2, int i3);

    default void onBeginChanges() {
    }

    default void onEndChanges() {
    }

    void remove(int i, int i2);

    default void reuse() {
        Object current = getCurrent();
        ComposeNodeLifecycleCallback composeNodeLifecycleCallback = current instanceof ComposeNodeLifecycleCallback ? (ComposeNodeLifecycleCallback) current : null;
        if (composeNodeLifecycleCallback != null) {
            composeNodeLifecycleCallback.onReuse();
        }
    }

    void up();
}
