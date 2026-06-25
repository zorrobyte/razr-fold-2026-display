package androidx.compose.runtime;

/* JADX INFO: compiled from: SnapshotIntState.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MutableIntState extends IntState, MutableState {
    @Override // androidx.compose.runtime.IntState
    int getIntValue();

    @Override // androidx.compose.runtime.State
    default Integer getValue() {
        return Integer.valueOf(getIntValue());
    }

    void setIntValue(int i);

    default void setValue(int i) {
        setIntValue(i);
    }

    @Override // androidx.compose.runtime.MutableState
    /* bridge */ /* synthetic */ default void setValue(Object obj) {
        setValue(((Number) obj).intValue());
    }
}
