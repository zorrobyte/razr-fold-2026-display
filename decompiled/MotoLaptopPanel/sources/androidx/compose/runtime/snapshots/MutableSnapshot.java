package androidx.compose.runtime.snapshots;

import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import androidx.compose.runtime.snapshots.SnapshotApplyResult;
import androidx.compose.runtime.snapshots.tooling.SnapshotInstanceObservers;
import androidx.compose.runtime.snapshots.tooling.SnapshotObserverKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Snapshot.kt */
/* JADX INFO: loaded from: classes.dex */
public class MutableSnapshot extends Snapshot {
    private boolean applied;
    private List merged;
    private MutableScatterSet modified;
    private SnapshotIdSet previousIds;
    private int[] previousPinnedSnapshots;
    private final Function1 readObserver;
    private int snapshots;
    private int writeCount;
    private final Function1 writeObserver;
    private static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final int[] EmptyIntArray = new int[0];

    /* JADX INFO: compiled from: Snapshot.kt */
    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MutableSnapshot(long j, SnapshotIdSet snapshotIdSet, Function1 function1, Function1 function12) {
        super(j, snapshotIdSet, null);
        this.readObserver = function1;
        this.writeObserver = function12;
        this.previousIds = SnapshotIdSet.Companion.getEMPTY();
        this.previousPinnedSnapshots = EmptyIntArray;
        this.snapshots = 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x007a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void abandon() {
        /*
            r17 = this;
            r0 = r17
            androidx.collection.MutableScatterSet r1 = r0.getModified$runtime_release()
            if (r1 == 0) goto L7f
            r0.validateNotApplied()
            r2 = 0
            r0.setModified(r2)
            long r2 = r0.getSnapshotId()
            java.lang.Object[] r4 = r1.elements
            long[] r1 = r1.metadata
            int r5 = r1.length
            int r5 = r5 + (-2)
            if (r5 < 0) goto L7f
            r6 = 0
            r7 = r6
        L1e:
            r8 = r1[r7]
            long r10 = ~r8
            r12 = 7
            long r10 = r10 << r12
            long r10 = r10 & r8
            r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r10 = r10 & r12
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 == 0) goto L7a
            int r10 = r7 - r5
            int r10 = ~r10
            int r10 = r10 >>> 31
            r11 = 8
            int r10 = 8 - r10
            r12 = r6
        L38:
            if (r12 >= r10) goto L78
            r13 = 255(0xff, double:1.26E-321)
            long r13 = r13 & r8
            r15 = 128(0x80, double:6.32E-322)
            int r13 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r13 >= 0) goto L74
            int r13 = r7 << 3
            int r13 = r13 + r12
            r13 = r4[r13]
            androidx.compose.runtime.snapshots.StateObject r13 = (androidx.compose.runtime.snapshots.StateObject) r13
            androidx.compose.runtime.snapshots.StateRecord r13 = r13.getFirstStateRecord()
        L4e:
            if (r13 == 0) goto L74
            long r14 = r13.getSnapshotId$runtime_release()
            int r14 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1))
            if (r14 == 0) goto L68
            androidx.compose.runtime.snapshots.SnapshotIdSet r14 = r0.previousIds
            long r15 = r13.getSnapshotId$runtime_release()
            java.lang.Long r15 = java.lang.Long.valueOf(r15)
            boolean r14 = kotlin.collections.CollectionsKt.contains(r14, r15)
            if (r14 == 0) goto L6f
        L68:
            long r14 = androidx.compose.runtime.snapshots.SnapshotKt.access$getINVALID_SNAPSHOT$p()
            r13.setSnapshotId$runtime_release(r14)
        L6f:
            androidx.compose.runtime.snapshots.StateRecord r13 = r13.getNext$runtime_release()
            goto L4e
        L74:
            long r8 = r8 >> r11
            int r12 = r12 + 1
            goto L38
        L78:
            if (r10 != r11) goto L7f
        L7a:
            if (r7 == r5) goto L7f
            int r7 = r7 + 1
            goto L1e
        L7f:
            r0.closeAndReleasePinning$runtime_release()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.snapshots.MutableSnapshot.abandon():void");
    }

    private final void releasePreviouslyPinnedSnapshotsLocked() {
        int length = this.previousPinnedSnapshots.length;
        for (int i = 0; i < length; i++) {
            SnapshotKt.releasePinningLocked(this.previousPinnedSnapshots[i]);
        }
    }

    private final void validateNotApplied() {
        if (this.applied) {
            PreconditionsKt.throwIllegalStateException("Unsupported operation on a snapshot that has been applied");
        }
    }

    private final void validateNotAppliedOrPinned() {
        if (!this.applied || ((Snapshot) this).pinningTrackingHandle >= 0) {
            return;
        }
        PreconditionsKt.throwIllegalStateException("Unsupported operation on a disposed or applied snapshot");
    }

    public final void advance$runtime_release() {
        recordPrevious$runtime_release(getSnapshotId());
        Unit unit = Unit.INSTANCE;
        if (getApplied$runtime_release() || getDisposed$runtime_release()) {
            return;
        }
        long snapshotId = getSnapshotId();
        synchronized (SnapshotKt.getLock()) {
            long j = SnapshotKt.nextSnapshotId;
            SnapshotKt.nextSnapshotId++;
            setSnapshotId$runtime_release(j);
            SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.set(getSnapshotId());
        }
        setInvalid$runtime_release(SnapshotKt.addRange(getInvalid$runtime_release(), snapshotId + 1, getSnapshotId()));
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0174  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public androidx.compose.runtime.snapshots.SnapshotApplyResult apply() {
        /*
            Method dump skipped, instruction units count: 410
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.snapshots.MutableSnapshot.apply():androidx.compose.runtime.snapshots.SnapshotApplyResult");
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public void closeLocked$runtime_release() {
        SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.clear(getSnapshotId()).andNot(this.previousIds);
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public void dispose() {
        if (getDisposed$runtime_release()) {
            return;
        }
        super.dispose();
        mo652nestedDeactivated$runtime_release(this);
        SnapshotObserverKt.dispatchObserverOnDispose(this);
    }

    public final boolean getApplied$runtime_release() {
        return this.applied;
    }

    public MutableScatterSet getModified$runtime_release() {
        return this.modified;
    }

    public final SnapshotIdSet getPreviousIds$runtime_release() {
        return this.previousIds;
    }

    public final int[] getPreviousPinnedSnapshots$runtime_release() {
        return this.previousPinnedSnapshots;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    /* JADX INFO: renamed from: getReadObserver$runtime_release */
    public Function1 getReadObserver() {
        return this.readObserver;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public boolean getReadOnly() {
        return false;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public int getWriteCount$runtime_release() {
        return this.writeCount;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public Function1 getWriteObserver$runtime_release() {
        return this.writeObserver;
    }

    public boolean hasPendingChanges() {
        MutableScatterSet modified$runtime_release = getModified$runtime_release();
        return modified$runtime_release != null && modified$runtime_release.isNotEmpty();
    }

    public final SnapshotApplyResult innerApplyLocked$runtime_release(long j, MutableScatterSet mutableScatterSet, Map map, SnapshotIdSet snapshotIdSet) {
        List listPlus;
        SnapshotIdSet snapshotIdSet2;
        Object[] objArr;
        long[] jArr;
        int i;
        Object[] objArr2;
        long[] jArr2;
        int i2;
        long j2;
        SnapshotIdSet snapshotIdSet3;
        ArrayList arrayList;
        StateRecord stateRecordMergeRecords;
        SnapshotIdSet snapshotIdSetOr = getInvalid$runtime_release().set(getSnapshotId()).or(this.previousIds);
        Object[] objArr3 = mutableScatterSet.elements;
        long[] jArr3 = mutableScatterSet.metadata;
        int length = jArr3.length - 2;
        ArrayList arrayList2 = null;
        if (length >= 0) {
            listPlus = null;
            int i3 = 0;
            while (true) {
                long j3 = jArr3[i3];
                List arrayList3 = listPlus;
                if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i4 = 8;
                    int i5 = 8 - ((~(i3 - length)) >>> 31);
                    int i6 = 0;
                    while (i6 < i5) {
                        if ((j3 & 255) < 128) {
                            i2 = i4;
                            StateObject stateObject = (StateObject) objArr3[(i3 << 3) + i6];
                            i = i6;
                            StateRecord firstStateRecord = stateObject.getFirstStateRecord();
                            objArr2 = objArr3;
                            jArr2 = jArr3;
                            StateRecord stateRecord = SnapshotKt.readable(firstStateRecord, j, snapshotIdSet);
                            if (stateRecord == null) {
                                j2 = j3;
                            } else {
                                j2 = j3;
                                StateRecord stateRecord2 = SnapshotKt.readable(firstStateRecord, getSnapshotId(), snapshotIdSetOr);
                                if (stateRecord2 != null && stateRecord2.getSnapshotId$runtime_release() != SnapshotId_jvmKt.toSnapshotId(1)) {
                                    if (Intrinsics.areEqual(stateRecord, stateRecord2)) {
                                        snapshotIdSet3 = snapshotIdSetOr;
                                        arrayList = arrayList2;
                                    } else {
                                        snapshotIdSet3 = snapshotIdSetOr;
                                        arrayList = arrayList2;
                                        StateRecord stateRecord3 = SnapshotKt.readable(firstStateRecord, getSnapshotId(), getInvalid$runtime_release());
                                        if (stateRecord3 == null) {
                                            SnapshotKt.readError();
                                            throw new KotlinNothingValueException();
                                        }
                                        if (map == null || (stateRecordMergeRecords = (StateRecord) map.get(stateRecord)) == null) {
                                            stateRecordMergeRecords = stateObject.mergeRecords(stateRecord2, stateRecord, stateRecord3);
                                        }
                                        if (stateRecordMergeRecords == null) {
                                            return new SnapshotApplyResult.Failure(this);
                                        }
                                        if (!Intrinsics.areEqual(stateRecordMergeRecords, stateRecord3)) {
                                            if (Intrinsics.areEqual(stateRecordMergeRecords, stateRecord)) {
                                                arrayList2 = arrayList == null ? new ArrayList() : arrayList;
                                                arrayList2.add(TuplesKt.to(stateObject, stateRecord.create(getSnapshotId())));
                                                if (arrayList3 == null) {
                                                    arrayList3 = new ArrayList();
                                                }
                                                List list = arrayList3;
                                                list.add(stateObject);
                                                arrayList3 = list;
                                            } else {
                                                arrayList2 = arrayList == null ? new ArrayList() : arrayList;
                                                arrayList2.add(!Intrinsics.areEqual(stateRecordMergeRecords, stateRecord2) ? TuplesKt.to(stateObject, stateRecordMergeRecords) : TuplesKt.to(stateObject, stateRecord2.create(getSnapshotId())));
                                            }
                                        }
                                    }
                                    arrayList2 = arrayList;
                                }
                                snapshotIdSetOr = snapshotIdSet3;
                                j3 = j2 >> i2;
                                i6 = i + 1;
                                i4 = i2;
                                objArr3 = objArr2;
                                jArr3 = jArr2;
                            }
                        } else {
                            i = i6;
                            objArr2 = objArr3;
                            jArr2 = jArr3;
                            i2 = i4;
                            j2 = j3;
                        }
                        snapshotIdSet3 = snapshotIdSetOr;
                        snapshotIdSetOr = snapshotIdSet3;
                        j3 = j2 >> i2;
                        i6 = i + 1;
                        i4 = i2;
                        objArr3 = objArr2;
                        jArr3 = jArr2;
                    }
                    snapshotIdSet2 = snapshotIdSetOr;
                    objArr = objArr3;
                    jArr = jArr3;
                    ArrayList arrayList4 = arrayList2;
                    if (i5 != i4) {
                        listPlus = arrayList3;
                        arrayList2 = arrayList4;
                        break;
                    }
                    arrayList2 = arrayList4;
                } else {
                    snapshotIdSet2 = snapshotIdSetOr;
                    objArr = objArr3;
                    jArr = jArr3;
                }
                listPlus = arrayList3;
                if (i3 == length) {
                    break;
                }
                i3++;
                snapshotIdSetOr = snapshotIdSet2;
                objArr3 = objArr;
                jArr3 = jArr;
            }
        } else {
            listPlus = null;
        }
        if (arrayList2 != null) {
            advance$runtime_release();
            int size = arrayList2.size();
            for (int i7 = 0; i7 < size; i7++) {
                Pair pair = (Pair) arrayList2.get(i7);
                StateObject stateObject2 = (StateObject) pair.component1();
                StateRecord stateRecord4 = (StateRecord) pair.component2();
                stateRecord4.setSnapshotId$runtime_release(j);
                synchronized (SnapshotKt.getLock()) {
                    stateRecord4.setNext$runtime_release(stateObject2.getFirstStateRecord());
                    stateObject2.prependStateRecord(stateRecord4);
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
        if (listPlus != null) {
            int size2 = listPlus.size();
            for (int i8 = 0; i8 < size2; i8++) {
                mutableScatterSet.remove((StateObject) listPlus.get(i8));
            }
            List list2 = this.merged;
            if (list2 != null) {
                listPlus = CollectionsKt.plus((Collection) list2, (Iterable) listPlus);
            }
            this.merged = listPlus;
        }
        return SnapshotApplyResult.Success.INSTANCE;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    /* JADX INFO: renamed from: nestedActivated$runtime_release */
    public void mo651nestedActivated$runtime_release(Snapshot snapshot) {
        this.snapshots++;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    /* JADX INFO: renamed from: nestedDeactivated$runtime_release */
    public void mo652nestedDeactivated$runtime_release(Snapshot snapshot) {
        if (!(this.snapshots > 0)) {
            PreconditionsKt.throwIllegalArgumentException("no pending nested snapshots");
        }
        int i = this.snapshots - 1;
        this.snapshots = i;
        if (i != 0 || this.applied) {
            return;
        }
        abandon();
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public void notifyObjectsInitialized$runtime_release() {
        if (this.applied || getDisposed$runtime_release()) {
            return;
        }
        advance$runtime_release();
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    /* JADX INFO: renamed from: recordModified$runtime_release */
    public void mo654recordModified$runtime_release(StateObject stateObject) {
        MutableScatterSet modified$runtime_release = getModified$runtime_release();
        if (modified$runtime_release == null) {
            modified$runtime_release = ScatterSetKt.mutableScatterSetOf();
            setModified(modified$runtime_release);
        }
        modified$runtime_release.add(stateObject);
    }

    public final void recordPrevious$runtime_release(long j) {
        synchronized (SnapshotKt.getLock()) {
            this.previousIds = this.previousIds.set(j);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void recordPreviousList$runtime_release(SnapshotIdSet snapshotIdSet) {
        synchronized (SnapshotKt.getLock()) {
            this.previousIds = this.previousIds.or(snapshotIdSet);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void recordPreviousPinnedSnapshot$runtime_release(int i) {
        if (i >= 0) {
            this.previousPinnedSnapshots = ArraysKt.plus(this.previousPinnedSnapshots, i);
        }
    }

    public final void recordPreviousPinnedSnapshots$runtime_release(int[] iArr) {
        if (iArr.length == 0) {
            return;
        }
        int[] iArr2 = this.previousPinnedSnapshots;
        if (iArr2.length != 0) {
            iArr = ArraysKt.plus(iArr2, iArr);
        }
        this.previousPinnedSnapshots = iArr;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public void releasePinnedSnapshotsForCloseLocked$runtime_release() {
        releasePreviouslyPinnedSnapshotsLocked();
        super.releasePinnedSnapshotsForCloseLocked$runtime_release();
    }

    public final void setApplied$runtime_release(boolean z) {
        this.applied = z;
    }

    public void setModified(MutableScatterSet mutableScatterSet) {
        this.modified = mutableScatterSet;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public void setWriteCount$runtime_release(int i) {
        this.writeCount = i;
    }

    public MutableSnapshot takeNestedMutableSnapshot(Function1 function1, Function1 function12) {
        Map map;
        NestedMutableSnapshot nestedMutableSnapshot;
        validateNotDisposed$runtime_release();
        validateNotAppliedOrPinned();
        PersistentList persistentList = SnapshotObserverKt.observers;
        Function1 function13 = function1;
        Function1 writeObserver = function12;
        if (persistentList != null) {
            Pair pairMergeObservers = SnapshotObserverKt.mergeObservers(persistentList, this, false, function13, writeObserver);
            SnapshotInstanceObservers snapshotInstanceObservers = (SnapshotInstanceObservers) pairMergeObservers.getFirst();
            Function1 readObserver = snapshotInstanceObservers.getReadObserver();
            writeObserver = snapshotInstanceObservers.getWriteObserver();
            map = (Map) pairMergeObservers.getSecond();
            function13 = readObserver;
        } else {
            map = null;
        }
        recordPrevious$runtime_release(getSnapshotId());
        synchronized (SnapshotKt.getLock()) {
            long j = SnapshotKt.nextSnapshotId;
            SnapshotKt.nextSnapshotId++;
            SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.set(j);
            SnapshotIdSet invalid$runtime_release = getInvalid$runtime_release();
            setInvalid$runtime_release(invalid$runtime_release.set(j));
            nestedMutableSnapshot = new NestedMutableSnapshot(j, SnapshotKt.addRange(invalid$runtime_release, getSnapshotId() + 1, j), SnapshotKt.mergedReadObserver$default(function13, getReadObserver(), false, 4, null), SnapshotKt.mergedWriteObserver(writeObserver, getWriteObserver$runtime_release()), this);
        }
        if (!getApplied$runtime_release() && !getDisposed$runtime_release()) {
            long snapshotId = getSnapshotId();
            synchronized (SnapshotKt.getLock()) {
                long j2 = SnapshotKt.nextSnapshotId;
                SnapshotKt.nextSnapshotId++;
                setSnapshotId$runtime_release(j2);
                SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.set(getSnapshotId());
                Unit unit = Unit.INSTANCE;
            }
            setInvalid$runtime_release(SnapshotKt.addRange(getInvalid$runtime_release(), snapshotId + 1, getSnapshotId()));
        }
        if (persistentList != null) {
            SnapshotObserverKt.dispatchCreatedObservers(persistentList, this, nestedMutableSnapshot, map);
        }
        return nestedMutableSnapshot;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public Snapshot takeNestedSnapshot(Function1 function1) {
        Function1 function12;
        Map map;
        NestedReadonlySnapshot nestedReadonlySnapshot;
        validateNotDisposed$runtime_release();
        validateNotAppliedOrPinned();
        long snapshotId = getSnapshotId();
        MutableSnapshot mutableSnapshot = this instanceof GlobalSnapshot ? null : this;
        PersistentList persistentList = SnapshotObserverKt.observers;
        if (persistentList != null) {
            Pair pairMergeObservers = SnapshotObserverKt.mergeObservers(persistentList, mutableSnapshot, true, function1, null);
            SnapshotInstanceObservers snapshotInstanceObservers = (SnapshotInstanceObservers) pairMergeObservers.getFirst();
            Function1 readObserver = snapshotInstanceObservers.getReadObserver();
            snapshotInstanceObservers.getWriteObserver();
            function12 = readObserver;
            map = (Map) pairMergeObservers.getSecond();
        } else {
            function12 = function1;
            map = null;
        }
        recordPrevious$runtime_release(getSnapshotId());
        synchronized (SnapshotKt.getLock()) {
            long j = SnapshotKt.nextSnapshotId;
            SnapshotKt.nextSnapshotId++;
            SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.set(j);
            nestedReadonlySnapshot = new NestedReadonlySnapshot(j, SnapshotKt.addRange(getInvalid$runtime_release(), snapshotId + 1, j), SnapshotKt.mergedReadObserver$default(function12, getReadObserver(), false, 4, null), this);
        }
        if (!getApplied$runtime_release() && !getDisposed$runtime_release()) {
            long snapshotId2 = getSnapshotId();
            synchronized (SnapshotKt.getLock()) {
                long j2 = SnapshotKt.nextSnapshotId;
                SnapshotKt.nextSnapshotId++;
                setSnapshotId$runtime_release(j2);
                SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.set(getSnapshotId());
                Unit unit = Unit.INSTANCE;
            }
            setInvalid$runtime_release(SnapshotKt.addRange(getInvalid$runtime_release(), snapshotId2 + 1, getSnapshotId()));
        }
        if (persistentList != null) {
            SnapshotObserverKt.dispatchCreatedObservers(persistentList, mutableSnapshot, nestedReadonlySnapshot, map);
        }
        return nestedReadonlySnapshot;
    }
}
