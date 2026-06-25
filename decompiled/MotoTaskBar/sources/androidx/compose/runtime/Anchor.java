package androidx.compose.runtime;

/* JADX INFO: compiled from: SlotTable.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Anchor {
    private int location;

    public Anchor(int i) {
        this.location = i;
    }

    public final int getLocation$runtime_release() {
        return this.location;
    }

    public final boolean getValid() {
        return this.location != Integer.MIN_VALUE;
    }

    public final void setLocation$runtime_release(int i) {
        this.location = i;
    }

    public final int toIndexFor(SlotTable slotTable) {
        return slotTable.anchorIndex(this);
    }

    public final int toIndexFor(SlotWriter slotWriter) {
        return slotWriter.anchorIndex(this);
    }

    public String toString() {
        return super.toString() + "{ location = " + this.location + " }";
    }
}
