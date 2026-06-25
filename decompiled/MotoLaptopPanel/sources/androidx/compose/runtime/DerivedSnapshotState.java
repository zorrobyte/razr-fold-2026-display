package androidx.compose.runtime;

import androidx.collection.MutableObjectIntMap;
import androidx.collection.ObjectIntMap;
import androidx.collection.ObjectIntMapKt;
import androidx.compose.runtime.DerivedState;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.internal.IntRef;
import androidx.compose.runtime.internal.System_jvmKt;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.SnapshotKt;
import androidx.compose.runtime.snapshots.StateObject;
import androidx.compose.runtime.snapshots.StateObjectImpl;
import androidx.compose.runtime.snapshots.StateRecord;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: DerivedState.kt */
/* JADX INFO: loaded from: classes.dex */
final class DerivedSnapshotState extends StateObjectImpl implements DerivedState {
    private final Function0 calculation;
    private ResultRecord first = new ResultRecord(SnapshotKt.currentSnapshot().getSnapshotId());
    private final SnapshotMutationPolicy policy;

    /* JADX INFO: compiled from: DerivedState.kt */
    public final class ResultRecord extends StateRecord implements DerivedState.Record {
        private ObjectIntMap dependencies;
        private Object result;
        private int resultHash;
        private long validSnapshotId;
        private int validSnapshotWriteCount;
        public static final Companion Companion = new Companion(null);
        public static final int $stable = 8;
        private static final Object Unset = new Object();

        /* JADX INFO: compiled from: DerivedState.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final Object getUnset() {
                return ResultRecord.Unset;
            }
        }

        public ResultRecord(long j) {
            super(j);
            this.dependencies = ObjectIntMapKt.emptyObjectIntMap();
            this.result = Unset;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public void assign(StateRecord stateRecord) {
            stateRecord.getClass();
            ResultRecord resultRecord = (ResultRecord) stateRecord;
            setDependencies(resultRecord.getDependencies());
            this.result = resultRecord.result;
            this.resultHash = resultRecord.resultHash;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public StateRecord create(long j) {
            return new ResultRecord(j);
        }

        @Override // androidx.compose.runtime.DerivedState.Record
        public Object getCurrentValue() {
            return this.result;
        }

        @Override // androidx.compose.runtime.DerivedState.Record
        public ObjectIntMap getDependencies() {
            return this.dependencies;
        }

        public final Object getResult() {
            return this.result;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x001e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final boolean isValid(androidx.compose.runtime.DerivedState r6, androidx.compose.runtime.snapshots.Snapshot r7) {
            /*
                r5 = this;
                java.lang.Object r0 = androidx.compose.runtime.snapshots.SnapshotKt.getLock()
                monitor-enter(r0)
                long r1 = r5.validSnapshotId     // Catch: java.lang.Throwable -> L1c
                long r3 = r7.getSnapshotId()     // Catch: java.lang.Throwable -> L1c
                int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                r2 = 1
                r3 = 0
                if (r1 != 0) goto L1e
                int r1 = r5.validSnapshotWriteCount     // Catch: java.lang.Throwable -> L1c
                int r4 = r7.getWriteCount$runtime_release()     // Catch: java.lang.Throwable -> L1c
                if (r1 == r4) goto L1a
                goto L1e
            L1a:
                r1 = r3
                goto L1f
            L1c:
                r5 = move-exception
                goto L4f
            L1e:
                r1 = r2
            L1f:
                monitor-exit(r0)
                java.lang.Object r0 = r5.result
                java.lang.Object r4 = androidx.compose.runtime.DerivedSnapshotState.ResultRecord.Unset
                if (r0 == r4) goto L31
                if (r1 == 0) goto L32
                int r0 = r5.resultHash
                int r6 = r5.readableHash(r6, r7)
                if (r0 != r6) goto L31
                goto L32
            L31:
                r2 = r3
            L32:
                if (r2 == 0) goto L4e
                if (r1 == 0) goto L4e
                java.lang.Object r6 = androidx.compose.runtime.snapshots.SnapshotKt.getLock()
                monitor-enter(r6)
                long r0 = r7.getSnapshotId()     // Catch: java.lang.Throwable -> L4b
                r5.validSnapshotId = r0     // Catch: java.lang.Throwable -> L4b
                int r7 = r7.getWriteCount$runtime_release()     // Catch: java.lang.Throwable -> L4b
                r5.validSnapshotWriteCount = r7     // Catch: java.lang.Throwable -> L4b
                kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L4b
                monitor-exit(r6)
                return r2
            L4b:
                r5 = move-exception
                monitor-exit(r6)
                throw r5
            L4e:
                return r2
            L4f:
                monitor-exit(r0)
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.DerivedSnapshotState.ResultRecord.isValid(androidx.compose.runtime.DerivedState, androidx.compose.runtime.snapshots.Snapshot):boolean");
        }

        public final int readableHash(DerivedState derivedState, Snapshot snapshot) {
            ObjectIntMap dependencies;
            int iIdentityHashCode;
            int i;
            int i2;
            synchronized (SnapshotKt.getLock()) {
                dependencies = getDependencies();
            }
            int i3 = 7;
            if (!dependencies.isNotEmpty()) {
                return 7;
            }
            MutableVector mutableVectorDerivedStateObservers = SnapshotStateKt.derivedStateObservers();
            Object[] objArr = mutableVectorDerivedStateObservers.content;
            int size = mutableVectorDerivedStateObservers.getSize();
            for (int i4 = 0; i4 < size; i4++) {
                ((DerivedStateObserver) objArr[i4]).start(derivedState);
            }
            try {
                Object[] objArr2 = dependencies.keys;
                int[] iArr = dependencies.values;
                long[] jArr = dependencies.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    iIdentityHashCode = 7;
                    int i5 = 0;
                    while (true) {
                        long j = jArr[i5];
                        if ((((~j) << i3) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i6 = 8;
                            int i7 = 8 - ((~(i5 - length)) >>> 31);
                            i = i3;
                            int i8 = 0;
                            while (i8 < i7) {
                                if ((j & 255) < 128) {
                                    int i9 = (i5 << 3) + i8;
                                    Object obj = objArr2[i9];
                                    int i10 = iArr[i9];
                                    i2 = i6;
                                    StateObject stateObject = (StateObject) obj;
                                    if (i10 == 1) {
                                        StateRecord stateRecordCurrent = stateObject instanceof DerivedSnapshotState ? ((DerivedSnapshotState) stateObject).current(snapshot) : SnapshotKt.current(stateObject.getFirstStateRecord(), snapshot);
                                        iIdentityHashCode = (((iIdentityHashCode * 31) + System_jvmKt.identityHashCode(stateRecordCurrent)) * 31) + Long.hashCode(stateRecordCurrent.getSnapshotId$runtime_release());
                                    }
                                } else {
                                    i2 = i6;
                                }
                                j >>= i2;
                                i8++;
                                i6 = i2;
                            }
                            if (i7 != i6) {
                                break;
                            }
                        } else {
                            i = i3;
                        }
                        if (i5 == length) {
                            i3 = iIdentityHashCode;
                            break;
                        }
                        i5++;
                        i3 = i;
                    }
                }
                iIdentityHashCode = i3;
                Unit unit = Unit.INSTANCE;
                Object[] objArr3 = mutableVectorDerivedStateObservers.content;
                int size2 = mutableVectorDerivedStateObservers.getSize();
                for (int i11 = 0; i11 < size2; i11++) {
                    ((DerivedStateObserver) objArr3[i11]).done(derivedState);
                }
                return iIdentityHashCode;
            } catch (Throwable th) {
                Object[] objArr4 = mutableVectorDerivedStateObservers.content;
                int size3 = mutableVectorDerivedStateObservers.getSize();
                for (int i12 = 0; i12 < size3; i12++) {
                    ((DerivedStateObserver) objArr4[i12]).done(derivedState);
                }
                throw th;
            }
        }

        public void setDependencies(ObjectIntMap objectIntMap) {
            this.dependencies = objectIntMap;
        }

        public final void setResult(Object obj) {
            this.result = obj;
        }

        public final void setResultHash(int i) {
            this.resultHash = i;
        }

        public final void setValidSnapshotId(long j) {
            this.validSnapshotId = j;
        }

        public final void setValidSnapshotWriteCount(int i) {
            this.validSnapshotWriteCount = i;
        }
    }

    public DerivedSnapshotState(Function0 function0, SnapshotMutationPolicy snapshotMutationPolicy) {
        this.calculation = function0;
        this.policy = snapshotMutationPolicy;
    }

    private final ResultRecord currentRecord(ResultRecord resultRecord, Snapshot snapshot, boolean z, Function0 function0) throws Throwable {
        Snapshot.Companion companion;
        SnapshotMutationPolicy policy;
        boolean z2;
        int i;
        ResultRecord resultRecord2 = resultRecord;
        boolean z3 = true;
        int i2 = 0;
        if (!resultRecord2.isValid(this, snapshot)) {
            final MutableObjectIntMap mutableObjectIntMap = new MutableObjectIntMap(0, 1, null);
            final IntRef intRef = (IntRef) SnapshotStateKt__DerivedStateKt.calculationBlockNestedLevel.get();
            if (intRef == null) {
                intRef = new IntRef(0);
                SnapshotStateKt__DerivedStateKt.calculationBlockNestedLevel.set(intRef);
            }
            final int element = intRef.getElement();
            MutableVector mutableVectorDerivedStateObservers = SnapshotStateKt.derivedStateObservers();
            Object[] objArr = mutableVectorDerivedStateObservers.content;
            int size = mutableVectorDerivedStateObservers.getSize();
            for (int i3 = 0; i3 < size; i3++) {
                ((DerivedStateObserver) objArr[i3]).start(this);
            }
            try {
                intRef.setElement(element + 1);
                Object objObserve = Snapshot.Companion.observe(new Function1() { // from class: androidx.compose.runtime.DerivedSnapshotState$currentRecord$result$1$1$result$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        m586invoke(obj);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                    public final void m586invoke(Object obj) {
                        if (obj == this.this$0) {
                            throw new IllegalStateException("A derived state calculation cannot read itself");
                        }
                        if (obj instanceof StateObject) {
                            int element2 = intRef.getElement();
                            MutableObjectIntMap mutableObjectIntMap2 = mutableObjectIntMap;
                            mutableObjectIntMap2.set(obj, Math.min(element2 - element, mutableObjectIntMap2.getOrDefault(obj, Integer.MAX_VALUE)));
                        }
                    }
                }, null, function0);
                intRef.setElement(element);
                Object[] objArr2 = mutableVectorDerivedStateObservers.content;
                int size2 = mutableVectorDerivedStateObservers.getSize();
                for (int i4 = 0; i4 < size2; i4++) {
                    ((DerivedStateObserver) objArr2[i4]).done(this);
                }
                synchronized (SnapshotKt.getLock()) {
                    try {
                        companion = Snapshot.Companion;
                        Snapshot current = companion.getCurrent();
                        if (resultRecord2.getResult() == ResultRecord.Companion.getUnset() || (policy = getPolicy()) == null || !policy.equivalent(objObserve, resultRecord2.getResult())) {
                            resultRecord2 = (ResultRecord) SnapshotKt.newWritableRecord(this.first, this, current);
                            resultRecord2.setDependencies(mutableObjectIntMap);
                            resultRecord2.setResultHash(resultRecord2.readableHash(this, current));
                            resultRecord2.setResult(objObserve);
                        } else {
                            resultRecord2.setDependencies(mutableObjectIntMap);
                            resultRecord2.setResultHash(resultRecord2.readableHash(this, current));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                IntRef intRef2 = (IntRef) SnapshotStateKt__DerivedStateKt.calculationBlockNestedLevel.get();
                if (intRef2 == null || intRef2.getElement() != 0) {
                    return resultRecord2;
                }
                companion.notifyObjectsInitialized();
                synchronized (SnapshotKt.getLock()) {
                    Snapshot current2 = companion.getCurrent();
                    resultRecord2.setValidSnapshotId(current2.getSnapshotId());
                    resultRecord2.setValidSnapshotWriteCount(current2.getWriteCount$runtime_release());
                    Unit unit = Unit.INSTANCE;
                }
                return resultRecord2;
            } catch (Throwable th2) {
                Object[] objArr3 = mutableVectorDerivedStateObservers.content;
                int size3 = mutableVectorDerivedStateObservers.getSize();
                for (int i5 = 0; i5 < size3; i5++) {
                    ((DerivedStateObserver) objArr3[i5]).done(this);
                }
                throw th2;
            }
        }
        if (z) {
            MutableVector mutableVectorDerivedStateObservers2 = SnapshotStateKt.derivedStateObservers();
            Object[] objArr4 = mutableVectorDerivedStateObservers2.content;
            int size4 = mutableVectorDerivedStateObservers2.getSize();
            for (int i6 = 0; i6 < size4; i6++) {
                ((DerivedStateObserver) objArr4[i6]).start(this);
            }
            try {
                ObjectIntMap dependencies = resultRecord2.getDependencies();
                IntRef intRef3 = (IntRef) SnapshotStateKt__DerivedStateKt.calculationBlockNestedLevel.get();
                if (intRef3 == null) {
                    intRef3 = new IntRef(0);
                    SnapshotStateKt__DerivedStateKt.calculationBlockNestedLevel.set(intRef3);
                }
                int element2 = intRef3.getElement();
                Object[] objArr5 = dependencies.keys;
                int[] iArr = dependencies.values;
                long[] jArr = dependencies.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i7 = 0;
                    while (true) {
                        long j = jArr[i7];
                        boolean z4 = z3;
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i8 = 8;
                            int i9 = 8 - ((~(i7 - length)) >>> 31);
                            z2 = z4;
                            int i10 = i2;
                            while (i10 < i9) {
                                if ((j & 255) < 128) {
                                    int i11 = (i7 << 3) + i10;
                                    try {
                                        StateObject stateObject = (StateObject) objArr5[i11];
                                        i = i8;
                                        intRef3.setElement(element2 + iArr[i11]);
                                        Function1 readObserver = snapshot.getReadObserver();
                                        if (readObserver != null) {
                                            readObserver.invoke(stateObject);
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                        Object[] objArr6 = mutableVectorDerivedStateObservers2.content;
                                        int size5 = mutableVectorDerivedStateObservers2.getSize();
                                        for (int i12 = 0; i12 < size5; i12++) {
                                            ((DerivedStateObserver) objArr6[i12]).done(this);
                                        }
                                        throw th;
                                    }
                                } else {
                                    i = i8;
                                }
                                j >>= i;
                                i10++;
                                i8 = i;
                            }
                            if (i9 != i8) {
                                break;
                            }
                        } else {
                            z2 = z4;
                        }
                        if (i7 == length) {
                            break;
                        }
                        i7++;
                        z3 = z2;
                        i2 = 0;
                    }
                }
                intRef3.setElement(element2);
                Unit unit2 = Unit.INSTANCE;
                Object[] objArr7 = mutableVectorDerivedStateObservers2.content;
                int size6 = mutableVectorDerivedStateObservers2.getSize();
                for (int i13 = 0; i13 < size6; i13++) {
                    ((DerivedStateObserver) objArr7[i13]).done(this);
                }
            } catch (Throwable th4) {
                th = th4;
            }
        }
        return resultRecord2;
    }

    private final String displayValue() {
        ResultRecord resultRecord = (ResultRecord) SnapshotKt.current(this.first);
        return resultRecord.isValid(this, Snapshot.Companion.getCurrent()) ? String.valueOf(resultRecord.getResult()) : "<Not calculated>";
    }

    public final StateRecord current(Snapshot snapshot) {
        return currentRecord((ResultRecord) SnapshotKt.current(this.first, snapshot), snapshot, false, this.calculation);
    }

    @Override // androidx.compose.runtime.DerivedState
    public DerivedState.Record getCurrentRecord() {
        Snapshot current = Snapshot.Companion.getCurrent();
        return currentRecord((ResultRecord) SnapshotKt.current(this.first, current), current, false, this.calculation);
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public StateRecord getFirstStateRecord() {
        return this.first;
    }

    @Override // androidx.compose.runtime.DerivedState
    public SnapshotMutationPolicy getPolicy() {
        return this.policy;
    }

    @Override // androidx.compose.runtime.State
    public Object getValue() {
        Snapshot.Companion companion = Snapshot.Companion;
        Function1 readObserver = companion.getCurrent().getReadObserver();
        if (readObserver != null) {
            readObserver.invoke(this);
        }
        Snapshot current = companion.getCurrent();
        return currentRecord((ResultRecord) SnapshotKt.current(this.first, current), current, true, this.calculation).getResult();
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public void prependStateRecord(StateRecord stateRecord) {
        stateRecord.getClass();
        this.first = (ResultRecord) stateRecord;
    }

    public String toString() {
        return "DerivedState(value=" + displayValue() + ")@" + hashCode();
    }
}
