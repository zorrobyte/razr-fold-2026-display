package androidx.compose.runtime;

/* JADX INFO: compiled from: SnapshotLongState.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MutableLongState extends LongState, MutableState {
    @Override // androidx.compose.runtime.LongState
    long getLongValue();

    @Override // androidx.compose.runtime.State
    default Long getValue() {
        return Long.valueOf(getLongValue());
    }

    void setLongValue(long j);

    default void setValue(long j) {
        setLongValue(j);
    }

    @Override // androidx.compose.runtime.MutableState
    /* bridge */ /* synthetic */ default void setValue(Object obj) {
        setValue(((Number) obj).longValue());
    }
}
