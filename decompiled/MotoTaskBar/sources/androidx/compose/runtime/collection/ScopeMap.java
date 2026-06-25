package androidx.compose.runtime.collection;

import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterMapKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ScopeMap.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ScopeMap {
    /* JADX INFO: renamed from: add-impl, reason: not valid java name */
    public static final void m82addimpl(MutableScatterMap mutableScatterMap, Object obj, Object obj2) {
        int iFindInsertIndex = mutableScatterMap.findInsertIndex(obj);
        boolean z = iFindInsertIndex < 0;
        Object obj3 = z ? null : mutableScatterMap.values[iFindInsertIndex];
        if (obj3 != null) {
            if (obj3 instanceof MutableScatterSet) {
                ((MutableScatterSet) obj3).add(obj2);
            } else if (obj3 != obj2) {
                MutableScatterSet mutableScatterSet = new MutableScatterSet(0, 1, null);
                mutableScatterSet.add(obj3);
                mutableScatterSet.add(obj2);
                obj2 = mutableScatterSet;
            }
            obj2 = obj3;
        }
        if (!z) {
            mutableScatterMap.values[iFindInsertIndex] = obj2;
            return;
        }
        int i = ~iFindInsertIndex;
        mutableScatterMap.keys[i] = obj;
        mutableScatterMap.values[i] = obj2;
    }

    /* JADX INFO: renamed from: clear-impl, reason: not valid java name */
    public static final void m83clearimpl(MutableScatterMap mutableScatterMap) {
        mutableScatterMap.clear();
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static MutableScatterMap m84constructorimpl(MutableScatterMap mutableScatterMap) {
        return mutableScatterMap;
    }

    /* JADX INFO: renamed from: constructor-impl$default, reason: not valid java name */
    public static /* synthetic */ MutableScatterMap m85constructorimpl$default(MutableScatterMap mutableScatterMap, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            mutableScatterMap = ScatterMapKt.mutableScatterMapOf();
        }
        return m84constructorimpl(mutableScatterMap);
    }

    /* JADX INFO: renamed from: contains-impl, reason: not valid java name */
    public static final boolean m86containsimpl(MutableScatterMap mutableScatterMap, Object obj) {
        return mutableScatterMap.containsKey(obj);
    }

    /* JADX INFO: renamed from: getSize-impl, reason: not valid java name */
    public static final int m87getSizeimpl(MutableScatterMap mutableScatterMap) {
        return mutableScatterMap.getSize();
    }

    /* JADX INFO: renamed from: remove-impl, reason: not valid java name */
    public static final boolean m88removeimpl(MutableScatterMap mutableScatterMap, Object obj, Object obj2) {
        Object obj3 = mutableScatterMap.get(obj);
        if (obj3 == null) {
            return false;
        }
        if (!(obj3 instanceof MutableScatterSet)) {
            if (!Intrinsics.areEqual(obj3, obj2)) {
                return false;
            }
            mutableScatterMap.remove(obj);
            return true;
        }
        MutableScatterSet mutableScatterSet = (MutableScatterSet) obj3;
        boolean zRemove = mutableScatterSet.remove(obj2);
        if (zRemove && mutableScatterSet.isEmpty()) {
            mutableScatterMap.remove(obj);
        }
        return zRemove;
    }

    /* JADX INFO: renamed from: removeScope-impl, reason: not valid java name */
    public static final void m89removeScopeimpl(MutableScatterMap mutableScatterMap, Object obj) {
        boolean zIsEmpty;
        long[] jArr = mutableScatterMap.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        int i4 = (i << 3) + i3;
                        Object obj2 = mutableScatterMap.keys[i4];
                        Object obj3 = mutableScatterMap.values[i4];
                        if (obj3 instanceof MutableScatterSet) {
                            obj3.getClass();
                            MutableScatterSet mutableScatterSet = (MutableScatterSet) obj3;
                            mutableScatterSet.remove(obj);
                            zIsEmpty = mutableScatterSet.isEmpty();
                        } else {
                            zIsEmpty = obj3 == obj;
                        }
                        if (zIsEmpty) {
                            mutableScatterMap.removeValueAt(i4);
                        }
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    /* JADX INFO: renamed from: set-impl, reason: not valid java name */
    public static final void m90setimpl(MutableScatterMap mutableScatterMap, Object obj, Object obj2) {
        mutableScatterMap.set(obj, obj2);
    }
}
