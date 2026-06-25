package androidx.compose.runtime;

/* JADX INFO: compiled from: SlotTable.kt */
/* JADX INFO: loaded from: classes.dex */
public final class KeyInfo {
    private final int index;
    private final int key;
    private final int location;
    private final int nodes;
    private final Object objectKey;

    public KeyInfo(int i, Object obj, int i2, int i3, int i4) {
        this.key = i;
        this.objectKey = obj;
        this.location = i2;
        this.nodes = i3;
        this.index = i4;
    }

    public final int getKey() {
        return this.key;
    }

    public final int getLocation() {
        return this.location;
    }

    public final int getNodes() {
        return this.nodes;
    }

    public final Object getObjectKey() {
        return this.objectKey;
    }
}
