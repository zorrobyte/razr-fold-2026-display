package androidx.compose.runtime;

import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableScatterMap;
import androidx.compose.runtime.collection.MultiValueMap;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Composer.kt */
/* JADX INFO: loaded from: classes.dex */
final class Pending {
    private int groupIndex;
    private final MutableIntObjectMap groupInfos;
    private final List keyInfos;
    private final Lazy keyMap$delegate;
    private final int startIndex;
    private final List usedKeys;

    public Pending(List list, int i) {
        this.keyInfos = list;
        this.startIndex = i;
        if (!(i >= 0)) {
            PreconditionsKt.throwIllegalArgumentException("Invalid start index");
        }
        this.usedKeys = new ArrayList();
        MutableIntObjectMap mutableIntObjectMap = new MutableIntObjectMap(0, 1, null);
        int size = list.size();
        int nodes = 0;
        for (int i2 = 0; i2 < size; i2++) {
            KeyInfo keyInfo = (KeyInfo) this.keyInfos.get(i2);
            mutableIntObjectMap.set(keyInfo.getLocation(), new GroupInfo(i2, nodes, keyInfo.getNodes()));
            nodes += keyInfo.getNodes();
        }
        this.groupInfos = mutableIntObjectMap;
        this.keyMap$delegate = LazyKt.lazy(new Function0() { // from class: androidx.compose.runtime.Pending$keyMap$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Object invoke() {
                return MultiValueMap.m626boximpl(m592invokefVlnmYg());
            }

            /* JADX INFO: renamed from: invoke-fVlnmYg, reason: not valid java name */
            public final MutableScatterMap m592invokefVlnmYg() {
                MutableScatterMap mutableScatterMapMultiMap = ComposerKt.multiMap(this.this$0.getKeyInfos().size());
                Pending pending = this.this$0;
                int size2 = pending.getKeyInfos().size();
                for (int i3 = 0; i3 < size2; i3++) {
                    KeyInfo keyInfo2 = (KeyInfo) pending.getKeyInfos().get(i3);
                    MultiValueMap.m625addimpl(mutableScatterMapMultiMap, ComposerKt.getJoinedKey(keyInfo2), keyInfo2);
                }
                return mutableScatterMapMultiMap;
            }
        });
    }

    public final int getGroupIndex() {
        return this.groupIndex;
    }

    public final List getKeyInfos() {
        return this.keyInfos;
    }

    /* JADX INFO: renamed from: getKeyMap-fVlnmYg, reason: not valid java name */
    public final MutableScatterMap m591getKeyMapfVlnmYg() {
        return ((MultiValueMap) this.keyMap$delegate.getValue()).m640unboximpl();
    }

    public final KeyInfo getNext(int i, Object obj) {
        return (KeyInfo) MultiValueMap.m635removeFirstimpl(m591getKeyMapfVlnmYg(), obj != null ? new JoinedKey(Integer.valueOf(i), obj) : Integer.valueOf(i));
    }

    public final int getStartIndex() {
        return this.startIndex;
    }

    public final List getUsed() {
        return this.usedKeys;
    }

    public final int nodePositionOf(KeyInfo keyInfo) {
        GroupInfo groupInfo = (GroupInfo) this.groupInfos.get(keyInfo.getLocation());
        if (groupInfo != null) {
            return groupInfo.getNodeIndex();
        }
        return -1;
    }

    public final boolean recordUsed(KeyInfo keyInfo) {
        return this.usedKeys.add(keyInfo);
    }

    public final void registerInsert(KeyInfo keyInfo, int i) {
        this.groupInfos.set(keyInfo.getLocation(), new GroupInfo(-1, i, 0));
    }

    public final void registerMoveNode(int i, int i2, int i3) {
        char c;
        long j;
        char c2;
        long j2;
        char c3 = 7;
        long j3 = -9187201950435737472L;
        if (i > i2) {
            MutableIntObjectMap mutableIntObjectMap = this.groupInfos;
            Object[] objArr = mutableIntObjectMap.values;
            long[] jArr = mutableIntObjectMap.metadata;
            int length = jArr.length - 2;
            if (length < 0) {
                return;
            }
            int i4 = 0;
            while (true) {
                long j4 = jArr[i4];
                if ((((~j4) << c3) & j4 & j3) != j3) {
                    int i5 = 8 - ((~(i4 - length)) >>> 31);
                    int i6 = 0;
                    while (i6 < i5) {
                        if ((j4 & 255) < 128) {
                            c2 = c3;
                            GroupInfo groupInfo = (GroupInfo) objArr[(i4 << 3) + i6];
                            j2 = j3;
                            int nodeIndex = groupInfo.getNodeIndex();
                            if (i <= nodeIndex && nodeIndex < i + i3) {
                                groupInfo.setNodeIndex((nodeIndex - i) + i2);
                            } else if (i2 <= nodeIndex && nodeIndex < i) {
                                groupInfo.setNodeIndex(nodeIndex + i3);
                            }
                        } else {
                            c2 = c3;
                            j2 = j3;
                        }
                        j4 >>= 8;
                        i6++;
                        c3 = c2;
                        j3 = j2;
                    }
                    c = c3;
                    j = j3;
                    if (i5 != 8) {
                        return;
                    }
                } else {
                    c = c3;
                    j = j3;
                }
                if (i4 == length) {
                    return;
                }
                i4++;
                c3 = c;
                j3 = j;
            }
        } else {
            if (i2 <= i) {
                return;
            }
            MutableIntObjectMap mutableIntObjectMap2 = this.groupInfos;
            Object[] objArr2 = mutableIntObjectMap2.values;
            long[] jArr2 = mutableIntObjectMap2.metadata;
            int length2 = jArr2.length - 2;
            if (length2 < 0) {
                return;
            }
            int i7 = 0;
            while (true) {
                long j5 = jArr2[i7];
                if ((((~j5) << 7) & j5 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i8 = 8 - ((~(i7 - length2)) >>> 31);
                    for (int i9 = 0; i9 < i8; i9++) {
                        if ((j5 & 255) < 128) {
                            GroupInfo groupInfo2 = (GroupInfo) objArr2[(i7 << 3) + i9];
                            int nodeIndex2 = groupInfo2.getNodeIndex();
                            if (i <= nodeIndex2 && nodeIndex2 < i + i3) {
                                groupInfo2.setNodeIndex((nodeIndex2 - i) + i2);
                            } else if (i + 1 <= nodeIndex2 && nodeIndex2 < i2) {
                                groupInfo2.setNodeIndex(nodeIndex2 - i3);
                            }
                        }
                        j5 >>= 8;
                    }
                    if (i8 != 8) {
                        return;
                    }
                }
                if (i7 == length2) {
                    return;
                } else {
                    i7++;
                }
            }
        }
    }

    public final void registerMoveSlot(int i, int i2) {
        char c;
        long j;
        char c2;
        long j2;
        char c3 = 7;
        long j3 = -9187201950435737472L;
        if (i > i2) {
            MutableIntObjectMap mutableIntObjectMap = this.groupInfos;
            Object[] objArr = mutableIntObjectMap.values;
            long[] jArr = mutableIntObjectMap.metadata;
            int length = jArr.length - 2;
            if (length < 0) {
                return;
            }
            int i3 = 0;
            while (true) {
                long j4 = jArr[i3];
                if ((((~j4) << c3) & j4 & j3) != j3) {
                    int i4 = 8 - ((~(i3 - length)) >>> 31);
                    int i5 = 0;
                    while (i5 < i4) {
                        if ((j4 & 255) < 128) {
                            c2 = c3;
                            GroupInfo groupInfo = (GroupInfo) objArr[(i3 << 3) + i5];
                            j2 = j3;
                            int slotIndex = groupInfo.getSlotIndex();
                            if (slotIndex == i) {
                                groupInfo.setSlotIndex(i2);
                            } else if (i2 <= slotIndex && slotIndex < i) {
                                groupInfo.setSlotIndex(slotIndex + 1);
                            }
                        } else {
                            c2 = c3;
                            j2 = j3;
                        }
                        j4 >>= 8;
                        i5++;
                        c3 = c2;
                        j3 = j2;
                    }
                    c = c3;
                    j = j3;
                    if (i4 != 8) {
                        return;
                    }
                } else {
                    c = c3;
                    j = j3;
                }
                if (i3 == length) {
                    return;
                }
                i3++;
                c3 = c;
                j3 = j;
            }
        } else {
            if (i2 <= i) {
                return;
            }
            MutableIntObjectMap mutableIntObjectMap2 = this.groupInfos;
            Object[] objArr2 = mutableIntObjectMap2.values;
            long[] jArr2 = mutableIntObjectMap2.metadata;
            int length2 = jArr2.length - 2;
            if (length2 < 0) {
                return;
            }
            int i6 = 0;
            while (true) {
                long j5 = jArr2[i6];
                if ((((~j5) << 7) & j5 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i7 = 8 - ((~(i6 - length2)) >>> 31);
                    for (int i8 = 0; i8 < i7; i8++) {
                        if ((j5 & 255) < 128) {
                            GroupInfo groupInfo2 = (GroupInfo) objArr2[(i6 << 3) + i8];
                            int slotIndex2 = groupInfo2.getSlotIndex();
                            if (slotIndex2 == i) {
                                groupInfo2.setSlotIndex(i2);
                            } else if (i + 1 <= slotIndex2 && slotIndex2 < i2) {
                                groupInfo2.setSlotIndex(slotIndex2 - 1);
                            }
                        }
                        j5 >>= 8;
                    }
                    if (i7 != 8) {
                        return;
                    }
                }
                if (i6 == length2) {
                    return;
                } else {
                    i6++;
                }
            }
        }
    }

    public final void setGroupIndex(int i) {
        this.groupIndex = i;
    }

    public final int slotPositionOf(KeyInfo keyInfo) {
        GroupInfo groupInfo = (GroupInfo) this.groupInfos.get(keyInfo.getLocation());
        if (groupInfo != null) {
            return groupInfo.getSlotIndex();
        }
        return -1;
    }

    public final boolean updateNodeCount(int i, int i2) {
        int nodeIndex;
        GroupInfo groupInfo = (GroupInfo) this.groupInfos.get(i);
        if (groupInfo == null) {
            return false;
        }
        int nodeIndex2 = groupInfo.getNodeIndex();
        int nodeCount = i2 - groupInfo.getNodeCount();
        groupInfo.setNodeCount(i2);
        if (nodeCount == 0) {
            return true;
        }
        MutableIntObjectMap mutableIntObjectMap = this.groupInfos;
        Object[] objArr = mutableIntObjectMap.values;
        long[] jArr = mutableIntObjectMap.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return true;
        }
        int i3 = 0;
        while (true) {
            long j = jArr[i3];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i4 = 8 - ((~(i3 - length)) >>> 31);
                for (int i5 = 0; i5 < i4; i5++) {
                    if ((255 & j) < 128) {
                        GroupInfo groupInfo2 = (GroupInfo) objArr[(i3 << 3) + i5];
                        if (groupInfo2.getNodeIndex() >= nodeIndex2 && !Intrinsics.areEqual(groupInfo2, groupInfo) && (nodeIndex = groupInfo2.getNodeIndex() + nodeCount) >= 0) {
                            groupInfo2.setNodeIndex(nodeIndex);
                        }
                    }
                    j >>= 8;
                }
                if (i4 != 8) {
                    return true;
                }
            }
            if (i3 == length) {
                return true;
            }
            i3++;
        }
    }

    public final int updatedNodeCountOf(KeyInfo keyInfo) {
        GroupInfo groupInfo = (GroupInfo) this.groupInfos.get(keyInfo.getLocation());
        return groupInfo != null ? groupInfo.getNodeCount() : keyInfo.getNodes();
    }
}
