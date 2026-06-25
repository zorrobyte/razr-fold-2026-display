package androidx.compose.runtime;

/* JADX INFO: compiled from: SnapshotFloatState.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SnapshotFloatState_androidKt {
    public static final MutableFloatState createSnapshotMutableFloatState(float f) {
        return new ParcelableSnapshotMutableFloatState(f);
    }
}
