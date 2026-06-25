package androidx.compose.runtime;

/* JADX INFO: compiled from: SnapshotState.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MutableState extends State {
    @Override // androidx.compose.runtime.State
    Object getValue();

    void setValue(Object obj);
}
