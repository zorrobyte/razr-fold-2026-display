package androidx.compose.runtime;

import java.util.ArrayList;

/* JADX INFO: compiled from: SlotTable.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class GroupSourceInformation {
    public abstract void addGroupAfter(SlotWriter slotWriter, int i, int i2);

    public abstract boolean getClosed();

    public abstract int getDataEndOffset();

    public abstract int getDataStartOffset();

    public abstract ArrayList getGroups();

    public abstract int getKey();

    public abstract String getSourceInformation();

    public abstract boolean removeAnchor(Anchor anchor);

    public abstract void reportGroup(SlotTable slotTable, int i);

    public abstract void reportGroup(SlotWriter slotWriter, int i);
}
