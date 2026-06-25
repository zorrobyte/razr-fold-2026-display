package kotlinx.coroutines;

/* JADX INFO: compiled from: CancellableContinuationImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
final class Active implements NotCompleted {
    public static final Active INSTANCE = new Active();

    private Active() {
    }

    public String toString() {
        return "Active";
    }
}
