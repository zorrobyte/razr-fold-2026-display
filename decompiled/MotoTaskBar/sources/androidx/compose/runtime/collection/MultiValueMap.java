package androidx.compose.runtime.collection;

import androidx.collection.MutableObjectList;
import androidx.collection.MutableScatterMap;
import androidx.collection.ObjectListKt;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: MultiValueMap.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MultiValueMap {
    private final MutableScatterMap map;

    private /* synthetic */ MultiValueMap(MutableScatterMap mutableScatterMap) {
        this.map = mutableScatterMap;
    }

    /* JADX INFO: renamed from: add-impl, reason: not valid java name */
    public static final void m66addimpl(MutableScatterMap mutableScatterMap, Object obj, Object obj2) {
        int iFindInsertIndex = mutableScatterMap.findInsertIndex(obj);
        boolean z = iFindInsertIndex < 0;
        Object obj3 = z ? null : mutableScatterMap.values[iFindInsertIndex];
        TypeIntrinsics.isMutableList(obj3);
        if (obj3 != null) {
            if (obj3 instanceof MutableObjectList) {
                MutableObjectList mutableObjectList = (MutableObjectList) obj3;
                mutableObjectList.add(obj2);
                obj2 = mutableObjectList;
            } else {
                obj2 = ObjectListKt.mutableObjectListOf(obj3, obj2);
            }
        }
        if (!z) {
            mutableScatterMap.values[iFindInsertIndex] = obj2;
            return;
        }
        int i = ~iFindInsertIndex;
        mutableScatterMap.keys[i] = obj;
        mutableScatterMap.values[i] = obj2;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ MultiValueMap m67boximpl(MutableScatterMap mutableScatterMap) {
        return new MultiValueMap(mutableScatterMap);
    }

    /* JADX INFO: renamed from: clear-impl, reason: not valid java name */
    public static final void m68clearimpl(MutableScatterMap mutableScatterMap) {
        mutableScatterMap.clear();
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static MutableScatterMap m69constructorimpl(MutableScatterMap mutableScatterMap) {
        return mutableScatterMap;
    }

    /* JADX INFO: renamed from: constructor-impl$default, reason: not valid java name */
    public static /* synthetic */ MutableScatterMap m70constructorimpl$default(MutableScatterMap mutableScatterMap, int i, DefaultConstructorMarker defaultConstructorMarker) {
        int i2 = 1;
        if ((i & 1) != 0) {
            mutableScatterMap = new MutableScatterMap(0, i2, null);
        }
        return m69constructorimpl(mutableScatterMap);
    }

    /* JADX INFO: renamed from: contains-impl, reason: not valid java name */
    public static final boolean m71containsimpl(MutableScatterMap mutableScatterMap, Object obj) {
        return mutableScatterMap.contains(obj);
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m72equalsimpl(MutableScatterMap mutableScatterMap, Object obj) {
        return (obj instanceof MultiValueMap) && Intrinsics.areEqual(mutableScatterMap, ((MultiValueMap) obj).m81unboximpl());
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m73hashCodeimpl(MutableScatterMap mutableScatterMap) {
        return mutableScatterMap.hashCode();
    }

    /* JADX INFO: renamed from: isEmpty-impl, reason: not valid java name */
    public static final boolean m74isEmptyimpl(MutableScatterMap mutableScatterMap) {
        return mutableScatterMap.isEmpty();
    }

    /* JADX INFO: renamed from: isNotEmpty-impl, reason: not valid java name */
    public static final boolean m75isNotEmptyimpl(MutableScatterMap mutableScatterMap) {
        return mutableScatterMap.isNotEmpty();
    }

    /* JADX INFO: renamed from: removeFirst-impl, reason: not valid java name */
    public static final Object m76removeFirstimpl(MutableScatterMap mutableScatterMap, Object obj) {
        Object obj2 = mutableScatterMap.get(obj);
        if (obj2 == null) {
            return null;
        }
        if (!(obj2 instanceof MutableObjectList)) {
            mutableScatterMap.remove(obj);
            return obj2;
        }
        MutableObjectList mutableObjectList = (MutableObjectList) obj2;
        Object objRemoveAt = mutableObjectList.removeAt(0);
        if (mutableObjectList.isEmpty()) {
            mutableScatterMap.remove(obj);
        }
        if (mutableObjectList.getSize() == 1) {
            mutableScatterMap.set(obj, mutableObjectList.first());
        }
        return objRemoveAt;
    }

    /* JADX INFO: renamed from: removeLast-impl, reason: not valid java name */
    public static final Object m77removeLastimpl(MutableScatterMap mutableScatterMap, Object obj) {
        Object obj2 = mutableScatterMap.get(obj);
        if (obj2 == null) {
            return null;
        }
        if (!(obj2 instanceof MutableObjectList)) {
            mutableScatterMap.remove(obj);
            return obj2;
        }
        MutableObjectList mutableObjectList = (MutableObjectList) obj2;
        Object objRemoveLast = ExtensionsKt.removeLast(mutableObjectList);
        objRemoveLast.getClass();
        if (mutableObjectList.isEmpty()) {
            mutableScatterMap.remove(obj);
        }
        if (mutableObjectList.getSize() == 1) {
            mutableScatterMap.set(obj, mutableObjectList.first());
        }
        return objRemoveLast;
    }

    /* JADX INFO: renamed from: removeValueIf-impl, reason: not valid java name */
    public static final void m78removeValueIfimpl(MutableScatterMap mutableScatterMap, Object obj, Function1 function1) {
        Object obj2 = mutableScatterMap.get(obj);
        if (obj2 != null) {
            if (!(obj2 instanceof MutableObjectList)) {
                if (((Boolean) function1.invoke(obj2)).booleanValue()) {
                    mutableScatterMap.remove(obj);
                    return;
                }
                return;
            }
            MutableObjectList mutableObjectList = (MutableObjectList) obj2;
            int i = mutableObjectList._size;
            Object[] objArr = mutableObjectList.content;
            int i2 = 0;
            IntRange intRangeUntil = RangesKt.until(0, i);
            int first = intRangeUntil.getFirst();
            int last = intRangeUntil.getLast();
            if (first <= last) {
                while (true) {
                    objArr[first - i2] = objArr[first];
                    if (((Boolean) function1.invoke(objArr[first])).booleanValue()) {
                        i2++;
                    }
                    if (first == last) {
                        break;
                    } else {
                        first++;
                    }
                }
            }
            ArraysKt.fill(objArr, (Object) null, i - i2, i);
            mutableObjectList._size -= i2;
            if (mutableObjectList.isEmpty()) {
                mutableScatterMap.remove(obj);
            }
            if (mutableObjectList.getSize() == 0) {
                mutableScatterMap.set(obj, mutableObjectList.first());
            }
        }
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m79toStringimpl(MutableScatterMap mutableScatterMap) {
        return "MultiValueMap(map=" + mutableScatterMap + ')';
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0060  */
    /* JADX INFO: renamed from: values-impl, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final androidx.collection.ObjectList m80valuesimpl(androidx.collection.MutableScatterMap r14) {
        /*
            boolean r0 = r14.isEmpty()
            if (r0 == 0) goto Lb
            androidx.collection.ObjectList r14 = androidx.collection.ObjectListKt.emptyObjectList()
            return r14
        Lb:
            androidx.collection.MutableObjectList r0 = new androidx.collection.MutableObjectList
            r1 = 0
            r2 = 0
            r3 = 1
            r0.<init>(r2, r3, r1)
            java.lang.Object[] r1 = r14.values
            long[] r14 = r14.metadata
            int r3 = r14.length
            int r3 = r3 + (-2)
            if (r3 < 0) goto L65
            r4 = r2
        L1d:
            r5 = r14[r4]
            long r7 = ~r5
            r9 = 7
            long r7 = r7 << r9
            long r7 = r7 & r5
            r9 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r7 = r7 & r9
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 == 0) goto L60
            int r7 = r4 - r3
            int r7 = ~r7
            int r7 = r7 >>> 31
            r8 = 8
            int r7 = 8 - r7
            r9 = r2
        L37:
            if (r9 >= r7) goto L5e
            r10 = 255(0xff, double:1.26E-321)
            long r10 = r10 & r5
            r12 = 128(0x80, double:6.32E-322)
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 >= 0) goto L5a
            int r10 = r4 << 3
            int r10 = r10 + r9
            r10 = r1[r10]
            boolean r11 = r10 instanceof androidx.collection.MutableObjectList
            if (r11 == 0) goto L54
            r10.getClass()
            androidx.collection.MutableObjectList r10 = (androidx.collection.MutableObjectList) r10
            r0.addAll(r10)
            goto L5a
        L54:
            r10.getClass()
            r0.add(r10)
        L5a:
            long r5 = r5 >> r8
            int r9 = r9 + 1
            goto L37
        L5e:
            if (r7 != r8) goto L65
        L60:
            if (r4 == r3) goto L65
            int r4 = r4 + 1
            goto L1d
        L65:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.collection.MultiValueMap.m80valuesimpl(androidx.collection.MutableScatterMap):androidx.collection.ObjectList");
    }

    public boolean equals(Object obj) {
        return m72equalsimpl(this.map, obj);
    }

    public int hashCode() {
        return m73hashCodeimpl(this.map);
    }

    public String toString() {
        return m79toStringimpl(this.map);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ MutableScatterMap m81unboximpl() {
        return this.map;
    }
}
