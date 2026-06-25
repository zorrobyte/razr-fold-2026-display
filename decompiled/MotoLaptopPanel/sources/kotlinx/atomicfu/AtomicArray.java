package kotlinx.atomicfu;

/* JADX INFO: compiled from: AtomicFU.common.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AtomicArray {
    private final AtomicRef[] array;

    public AtomicArray(int i) {
        AtomicRef[] atomicRefArr = new AtomicRef[i];
        for (int i2 = 0; i2 < i; i2++) {
            atomicRefArr[i2] = AtomicFU.atomic((Object) null);
        }
        this.array = atomicRefArr;
    }

    public final AtomicRef get(int i) {
        return this.array[i];
    }
}
