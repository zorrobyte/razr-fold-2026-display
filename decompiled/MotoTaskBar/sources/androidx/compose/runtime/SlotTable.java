package androidx.compose.runtime;

import androidx.collection.MutableIntObjectMap;
import androidx.compose.runtime.tooling.CompositionData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: SlotTable.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SlotTable implements CompositionData, Iterable, KMappedMarker {
    private MutableIntObjectMap calledByMap;
    private int groupsSize;
    private int readers;
    private int slotsSize;
    private HashMap sourceInformationMap;
    private int version;
    private boolean writer;
    private int[] groups = new int[0];
    private Object[] slots = new Object[0];
    private final Object lock = new Object();
    private ArrayList anchors = new ArrayList();

    private final Anchor tryAnchor(int i) {
        int i2;
        if (this.writer) {
            ComposerKt.composeImmediateRuntimeError("use active SlotWriter to crate an anchor for location instead");
        }
        if (i < 0 || i >= (i2 = this.groupsSize)) {
            return null;
        }
        return SlotTableKt.find(this.anchors, i, i2);
    }

    public final Anchor anchor(int i) {
        if (this.writer) {
            ComposerKt.composeImmediateRuntimeError("use active SlotWriter to create an anchor location instead");
        }
        boolean z = false;
        if (i >= 0 && i < this.groupsSize) {
            z = true;
        }
        if (!z) {
            PreconditionsKt.throwIllegalArgumentException("Parameter index is out of range");
        }
        ArrayList arrayList = this.anchors;
        int iSearch = SlotTableKt.search(arrayList, i, this.groupsSize);
        if (iSearch >= 0) {
            return (Anchor) arrayList.get(iSearch);
        }
        Anchor anchor = new Anchor(i);
        arrayList.add(-(iSearch + 1), anchor);
        return anchor;
    }

    public final int anchorIndex(Anchor anchor) {
        if (this.writer) {
            ComposerKt.composeImmediateRuntimeError("Use active SlotWriter to determine anchor location instead");
        }
        if (!anchor.getValid()) {
            PreconditionsKt.throwIllegalArgumentException("Anchor refers to a group that was removed");
        }
        return anchor.getLocation$runtime_release();
    }

    public final void close$runtime_release(SlotReader slotReader, HashMap map) {
        if (!(slotReader.getTable$runtime_release() == this && this.readers > 0)) {
            ComposerKt.composeImmediateRuntimeError("Unexpected reader close()");
        }
        this.readers--;
        if (map != null) {
            synchronized (this.lock) {
                try {
                    HashMap map2 = this.sourceInformationMap;
                    if (map2 != null) {
                        map2.putAll(map);
                    } else {
                        this.sourceInformationMap = map;
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void close$runtime_release(SlotWriter slotWriter, int[] iArr, int i, Object[] objArr, int i2, ArrayList arrayList, HashMap map, MutableIntObjectMap mutableIntObjectMap) {
        if (!(slotWriter.getTable$runtime_release() == this && this.writer)) {
            PreconditionsKt.throwIllegalArgumentException("Unexpected writer close()");
        }
        this.writer = false;
        setTo$runtime_release(iArr, i, objArr, i2, arrayList, map, mutableIntObjectMap);
    }

    public final void collectCalledByInformation() {
        this.calledByMap = new MutableIntObjectMap(0, 1, null);
    }

    public final void collectSourceInformation() {
        this.sourceInformationMap = new HashMap();
    }

    public final ArrayList getAnchors$runtime_release() {
        return this.anchors;
    }

    public final MutableIntObjectMap getCalledByMap$runtime_release() {
        return this.calledByMap;
    }

    public final int[] getGroups() {
        return this.groups;
    }

    public final int getGroupsSize() {
        return this.groupsSize;
    }

    public final Object[] getSlots() {
        return this.slots;
    }

    public final int getSlotsSize() {
        return this.slotsSize;
    }

    public final HashMap getSourceInformationMap$runtime_release() {
        return this.sourceInformationMap;
    }

    public final int getVersion$runtime_release() {
        return this.version;
    }

    public final boolean getWriter$runtime_release() {
        return this.writer;
    }

    public final boolean groupContainsAnchor(int i, Anchor anchor) {
        if (this.writer) {
            ComposerKt.composeImmediateRuntimeError("Writer is active");
        }
        if (!(i >= 0 && i < this.groupsSize)) {
            ComposerKt.composeImmediateRuntimeError("Invalid group index");
        }
        if (ownsAnchor(anchor)) {
            int iGroupSize = SlotTableKt.groupSize(this.groups, i) + i;
            int location$runtime_release = anchor.getLocation$runtime_release();
            if (i <= location$runtime_release && location$runtime_release < iGroupSize) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return this.groupsSize == 0;
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        return new GroupIterator(this, 0, this.groupsSize);
    }

    public final SlotReader openReader() {
        if (this.writer) {
            throw new IllegalStateException("Cannot read while a writer is pending");
        }
        this.readers++;
        return new SlotReader(this);
    }

    public final SlotWriter openWriter() {
        if (this.writer) {
            ComposerKt.composeImmediateRuntimeError("Cannot start a writer when another writer is pending");
        }
        if (!(this.readers <= 0)) {
            ComposerKt.composeImmediateRuntimeError("Cannot start a writer when a reader is pending");
        }
        this.writer = true;
        this.version++;
        return new SlotWriter(this);
    }

    public final boolean ownsAnchor(Anchor anchor) {
        int iSearch;
        return anchor.getValid() && (iSearch = SlotTableKt.search(this.anchors, anchor.getLocation$runtime_release(), this.groupsSize)) >= 0 && Intrinsics.areEqual(this.anchors.get(iSearch), anchor);
    }

    public final void setTo$runtime_release(int[] iArr, int i, Object[] objArr, int i2, ArrayList arrayList, HashMap map, MutableIntObjectMap mutableIntObjectMap) {
        this.groups = iArr;
        this.groupsSize = i;
        this.slots = objArr;
        this.slotsSize = i2;
        this.anchors = arrayList;
        this.sourceInformationMap = map;
        this.calledByMap = mutableIntObjectMap;
    }

    public final GroupSourceInformation sourceInformationOf(int i) {
        Anchor anchorTryAnchor;
        HashMap map = this.sourceInformationMap;
        if (map == null || (anchorTryAnchor = tryAnchor(i)) == null) {
            return null;
        }
        return (GroupSourceInformation) map.get(anchorTryAnchor);
    }
}
