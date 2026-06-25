package androidx.compose.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: compiled from: SlotTable.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SlotReader {
    private boolean closed;
    private int currentEnd;
    private int currentGroup;
    private int currentSlot;
    private int currentSlotEnd;
    private final IntStack currentSlotStack;
    private int emptyCount;
    private final int[] groups;
    private final int groupsSize;
    private boolean hadNext;
    private int parent;
    private final Object[] slots;
    private final int slotsSize;
    private HashMap sourceInformationMap;
    private final SlotTable table;

    public SlotReader(SlotTable slotTable) {
        this.table = slotTable;
        this.groups = slotTable.getGroups();
        int groupsSize = slotTable.getGroupsSize();
        this.groupsSize = groupsSize;
        this.slots = slotTable.getSlots();
        this.slotsSize = slotTable.getSlotsSize();
        this.currentEnd = groupsSize;
        this.parent = -1;
        this.currentSlotStack = new IntStack();
    }

    private final Object aux(int[] iArr, int i) {
        return (iArr[(i * 5) + 1] & 268435456) != 0 ? this.slots[SlotTableKt.auxIndex(iArr, i)] : Composer.Companion.getEmpty();
    }

    private final Object node(int[] iArr, int i) {
        int i2 = i * 5;
        return (iArr[i2 + 1] & 1073741824) != 0 ? this.slots[iArr[i2 + 4]] : Composer.Companion.getEmpty();
    }

    private final Object objectKey(int[] iArr, int i) {
        if ((iArr[(i * 5) + 1] & 536870912) != 0) {
            return this.slots[SlotTableKt.objectKeyIndex(iArr, i)];
        }
        return null;
    }

    public final Anchor anchor(int i) {
        ArrayList anchors$runtime_release = this.table.getAnchors$runtime_release();
        int iSearch = SlotTableKt.search(anchors$runtime_release, i, this.groupsSize);
        if (iSearch >= 0) {
            return (Anchor) anchors$runtime_release.get(iSearch);
        }
        Anchor anchor = new Anchor(i);
        anchors$runtime_release.add(-(iSearch + 1), anchor);
        return anchor;
    }

    public final void beginEmpty() {
        this.emptyCount++;
    }

    public final void close() {
        this.closed = true;
        this.table.close$runtime_release(this, this.sourceInformationMap);
    }

    public final boolean containsMark(int i) {
        return (this.groups[(i * 5) + 1] & 67108864) != 0;
    }

    public final void endEmpty() {
        if (!(this.emptyCount > 0)) {
            PreconditionsKt.throwIllegalArgumentException("Unbalanced begin/end empty");
        }
        this.emptyCount--;
    }

    public final void endGroup() {
        if (this.emptyCount == 0) {
            if (!(this.currentGroup == this.currentEnd)) {
                ComposerKt.composeImmediateRuntimeError("endGroup() not called at the end of a group");
            }
            int[] iArr = this.groups;
            int i = iArr[(this.parent * 5) + 2];
            this.parent = i;
            this.currentEnd = i < 0 ? this.groupsSize : SlotTableKt.groupSize(iArr, i) + i;
            int iPop = this.currentSlotStack.pop();
            if (iPop < 0) {
                this.currentSlot = 0;
                this.currentSlotEnd = 0;
            } else {
                this.currentSlot = iPop;
                this.currentSlotEnd = i >= this.groupsSize - 1 ? this.slotsSize : this.groups[((i + 1) * 5) + 4];
            }
        }
    }

    public final List extractKeys() {
        ArrayList arrayList = new ArrayList();
        if (this.emptyCount <= 0) {
            int i = 0;
            int iGroupSize = this.currentGroup;
            while (true) {
                int i2 = i;
                if (iGroupSize >= this.currentEnd) {
                    break;
                }
                int[] iArr = this.groups;
                int i3 = iGroupSize * 5;
                int i4 = iArr[i3];
                Object objObjectKey = objectKey(iArr, iGroupSize);
                int i5 = 1;
                int i6 = this.groups[i3 + 1];
                if ((1073741824 & i6) == 0) {
                    i5 = i6 & 67108863;
                }
                i = i2 + 1;
                arrayList.add(new KeyInfo(i4, objObjectKey, iGroupSize, i5, i2));
                iGroupSize += SlotTableKt.groupSize(this.groups, iGroupSize);
            }
        }
        return arrayList;
    }

    public final boolean getClosed() {
        return this.closed;
    }

    public final int getCurrentGroup() {
        return this.currentGroup;
    }

    public final Object getGroupAux() {
        int i = this.currentGroup;
        if (i < this.currentEnd) {
            return aux(this.groups, i);
        }
        return 0;
    }

    public final int getGroupEnd() {
        return this.currentEnd;
    }

    public final int getGroupKey() {
        int i = this.currentGroup;
        if (i < this.currentEnd) {
            return this.groups[i * 5];
        }
        return 0;
    }

    public final Object getGroupObjectKey() {
        int i = this.currentGroup;
        if (i < this.currentEnd) {
            return objectKey(this.groups, i);
        }
        return null;
    }

    public final int getGroupSize() {
        return SlotTableKt.groupSize(this.groups, this.currentGroup);
    }

    public final int getGroupSlotIndex() {
        return this.currentSlot - SlotTableKt.slotAnchor(this.groups, this.parent);
    }

    public final boolean getHadNext() {
        return this.hadNext;
    }

    public final boolean getHasObjectKey() {
        int i = this.currentGroup;
        return i < this.currentEnd && (this.groups[(i * 5) + 1] & 536870912) != 0;
    }

    public final boolean getInEmpty() {
        return this.emptyCount > 0;
    }

    public final int getParent() {
        return this.parent;
    }

    public final int getParentNodes() {
        int i = this.parent;
        if (i >= 0) {
            return this.groups[(i * 5) + 1] & 67108863;
        }
        return 0;
    }

    public final int getRemainingSlots() {
        return this.currentSlotEnd - this.currentSlot;
    }

    public final int getSize() {
        return this.groupsSize;
    }

    public final int getSlot() {
        return this.currentSlot - SlotTableKt.slotAnchor(this.groups, this.parent);
    }

    public final SlotTable getTable$runtime_release() {
        return this.table;
    }

    public final Object groupAux(int i) {
        return aux(this.groups, i);
    }

    public final Object groupGet(int i) {
        return groupGet(this.currentGroup, i);
    }

    public final Object groupGet(int i, int i2) {
        int iSlotAnchor = SlotTableKt.slotAnchor(this.groups, i);
        int i3 = i + 1;
        int i4 = iSlotAnchor + i2;
        return i4 < (i3 < this.groupsSize ? this.groups[(i3 * 5) + 4] : this.slotsSize) ? this.slots[i4] : Composer.Companion.getEmpty();
    }

    public final int groupKey(int i) {
        return this.groups[i * 5];
    }

    public final Object groupObjectKey(int i) {
        return objectKey(this.groups, i);
    }

    public final int groupSize(int i) {
        return SlotTableKt.groupSize(this.groups, i);
    }

    public final boolean hasMark(int i) {
        return (this.groups[(i * 5) + 1] & 134217728) != 0;
    }

    public final boolean hasObjectKey(int i) {
        return (this.groups[(i * 5) + 1] & 536870912) != 0;
    }

    public final boolean isGroupEnd() {
        return getInEmpty() || this.currentGroup == this.currentEnd;
    }

    public final boolean isNode() {
        return (this.groups[(this.currentGroup * 5) + 1] & 1073741824) != 0;
    }

    public final boolean isNode(int i) {
        return (this.groups[(i * 5) + 1] & 1073741824) != 0;
    }

    public final Object next() {
        int i;
        if (this.emptyCount > 0 || (i = this.currentSlot) >= this.currentSlotEnd) {
            this.hadNext = false;
            return Composer.Companion.getEmpty();
        }
        this.hadNext = true;
        Object[] objArr = this.slots;
        this.currentSlot = i + 1;
        return objArr[i];
    }

    public final Object node(int i) {
        int[] iArr = this.groups;
        if ((iArr[(i * 5) + 1] & 1073741824) != 0) {
            return node(iArr, i);
        }
        return null;
    }

    public final int nodeCount(int i) {
        return this.groups[(i * 5) + 1] & 67108863;
    }

    public final int parent(int i) {
        return this.groups[(i * 5) + 2];
    }

    public final void reposition(int i) {
        if (!(this.emptyCount == 0)) {
            ComposerKt.composeImmediateRuntimeError("Cannot reposition while in an empty region");
        }
        this.currentGroup = i;
        int i2 = this.groupsSize;
        int i3 = i < i2 ? this.groups[(i * 5) + 2] : -1;
        if (i3 != this.parent) {
            this.parent = i3;
            if (i3 < 0) {
                this.currentEnd = i2;
            } else {
                this.currentEnd = i3 + SlotTableKt.groupSize(this.groups, i3);
            }
            this.currentSlot = 0;
            this.currentSlotEnd = 0;
        }
    }

    public final void restoreParent(int i) {
        int iGroupSize = SlotTableKt.groupSize(this.groups, i) + i;
        int i2 = this.currentGroup;
        if (!(i2 >= i && i2 <= iGroupSize)) {
            ComposerKt.composeImmediateRuntimeError("Index " + i + " is not a parent of " + i2);
        }
        this.parent = i;
        this.currentEnd = iGroupSize;
        this.currentSlot = 0;
        this.currentSlotEnd = 0;
    }

    public final int skipGroup() {
        if (!(this.emptyCount == 0)) {
            ComposerKt.composeImmediateRuntimeError("Cannot skip while in an empty region");
        }
        int[] iArr = this.groups;
        int i = this.currentGroup;
        int i2 = (iArr[(i * 5) + 1] & 1073741824) == 0 ? iArr[(i * 5) + 1] & 67108863 : 1;
        this.currentGroup = i + SlotTableKt.groupSize(iArr, i);
        return i2;
    }

    public final void skipToGroupEnd() {
        if (!(this.emptyCount == 0)) {
            ComposerKt.composeImmediateRuntimeError("Cannot skip the enclosing group while in an empty region");
        }
        this.currentGroup = this.currentEnd;
        this.currentSlot = 0;
        this.currentSlotEnd = 0;
    }

    public final int slotSize(int i) {
        int iSlotAnchor = SlotTableKt.slotAnchor(this.groups, i);
        int i2 = i + 1;
        return (i2 < this.groupsSize ? this.groups[(i2 * 5) + 4] : this.slotsSize) - iSlotAnchor;
    }

    public final void startGroup() {
        if (this.emptyCount <= 0) {
            int i = this.parent;
            int i2 = this.currentGroup;
            if (!(this.groups[(i2 * 5) + 2] == i)) {
                PreconditionsKt.throwIllegalArgumentException("Invalid slot table detected");
            }
            HashMap map = this.sourceInformationMap;
            if (map != null) {
            }
            IntStack intStack = this.currentSlotStack;
            int i3 = this.currentSlot;
            int i4 = this.currentSlotEnd;
            if (i3 == 0 && i4 == 0) {
                intStack.push(-1);
            } else {
                intStack.push(i3);
            }
            this.parent = i2;
            this.currentEnd = SlotTableKt.groupSize(this.groups, i2) + i2;
            int i5 = i2 + 1;
            this.currentGroup = i5;
            this.currentSlot = SlotTableKt.slotAnchor(this.groups, i2);
            this.currentSlotEnd = i2 >= this.groupsSize - 1 ? this.slotsSize : this.groups[(i5 * 5) + 4];
        }
    }

    public final void startNode() {
        if (this.emptyCount <= 0) {
            if (!((this.groups[(this.currentGroup * 5) + 1] & 1073741824) != 0)) {
                PreconditionsKt.throwIllegalArgumentException("Expected a node group");
            }
            startGroup();
        }
    }

    public String toString() {
        return "SlotReader(current=" + this.currentGroup + ", key=" + getGroupKey() + ", parent=" + this.parent + ", end=" + this.currentEnd + ')';
    }
}
