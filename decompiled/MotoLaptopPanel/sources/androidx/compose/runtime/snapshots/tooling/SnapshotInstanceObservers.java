package androidx.compose.runtime.snapshots.tooling;

import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: SnapshotObserver.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SnapshotInstanceObservers {
    private final Function1 readObserver;
    private final Function1 writeObserver;

    public SnapshotInstanceObservers(Function1 function1, Function1 function12) {
        this.readObserver = function1;
        this.writeObserver = function12;
    }

    public final Function1 getReadObserver() {
        return this.readObserver;
    }

    public final Function1 getWriteObserver() {
        return this.writeObserver;
    }
}
