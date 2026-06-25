package androidx.compose.runtime.changelist;

import androidx.compose.runtime.Applier;
import androidx.compose.runtime.RememberManager;
import androidx.compose.runtime.SlotWriter;
import kotlin.collections.ArraysKt;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Operations.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Operations extends OperationsDebugStringFormattable {
    public int intArgsSize;
    public int objectArgsSize;
    public int opCodesSize;
    private int pushedIntMask;
    public Operation[] opCodes = new Operation[16];
    public int[] intArgs = new int[16];
    public Object[] objectArgs = new Object[16];

    /* JADX INFO: compiled from: Operations.kt */
    public final class OpIterator implements OperationArgContainer {
        private int intIdx;
        private int objIdx;
        private int opIdx;

        public OpIterator() {
        }

        @Override // androidx.compose.runtime.changelist.OperationArgContainer
        public int getInt(int i) {
            return Operations.this.intArgs[this.intIdx + i];
        }

        @Override // androidx.compose.runtime.changelist.OperationArgContainer
        /* JADX INFO: renamed from: getObject-31yXWZQ */
        public Object mo619getObject31yXWZQ(int i) {
            return Operations.this.objectArgs[this.objIdx + i];
        }

        public final Operation getOperation() {
            return Operations.this.opCodes[this.opIdx];
        }

        public final boolean next() {
            if (this.opIdx >= Operations.this.opCodesSize) {
                return false;
            }
            Operation operation = getOperation();
            this.intIdx += operation.getInts();
            this.objIdx += operation.getObjects();
            int i = this.opIdx + 1;
            this.opIdx = i;
            return i < Operations.this.opCodesSize;
        }
    }

    /* JADX INFO: compiled from: Operations.kt */
    public abstract class WriteScope {
        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        public static Operations m620constructorimpl(Operations operations) {
            return operations;
        }

        /* JADX INFO: renamed from: setObject-DKhxnng, reason: not valid java name */
        public static final void m621setObjectDKhxnng(Operations operations, int i, Object obj) {
            operations.objectArgs[(operations.objectArgsSize - operations.opCodes[operations.opCodesSize - 1].getObjects()) + i] = obj;
        }

        /* JADX INFO: renamed from: setObjects-4uCC6AY, reason: not valid java name */
        public static final void m622setObjects4uCC6AY(Operations operations, int i, Object obj, int i2, Object obj2) {
            int objects = operations.objectArgsSize - operations.opCodes[operations.opCodesSize - 1].getObjects();
            Object[] objArr = operations.objectArgs;
            objArr[i + objects] = obj;
            objArr[objects + i2] = obj2;
        }

        /* JADX INFO: renamed from: setObjects-OGa0p1M, reason: not valid java name */
        public static final void m623setObjectsOGa0p1M(Operations operations, int i, Object obj, int i2, Object obj2, int i3, Object obj3, int i4, Object obj4) {
            int objects = operations.objectArgsSize - operations.opCodes[operations.opCodesSize - 1].getObjects();
            Object[] objArr = operations.objectArgs;
            objArr[i + objects] = obj;
            objArr[i2 + objects] = obj2;
            objArr[i3 + objects] = obj3;
            objArr[objects + i4] = obj4;
        }

        /* JADX INFO: renamed from: setObjects-t7hvbck, reason: not valid java name */
        public static final void m624setObjectst7hvbck(Operations operations, int i, Object obj, int i2, Object obj2, int i3, Object obj3) {
            int objects = operations.objectArgsSize - operations.opCodes[operations.opCodesSize - 1].getObjects();
            Object[] objArr = operations.objectArgs;
            objArr[i + objects] = obj;
            objArr[i2 + objects] = obj2;
            objArr[objects + i3] = obj3;
        }
    }

    private final int determineNewSize(int i, int i2) {
        return RangesKt.coerceAtLeast(i + RangesKt.coerceAtMost(i, 1024), i2);
    }

    private final void resizeIntArgs(int i, int i2) {
        int[] iArr = new int[determineNewSize(i, i2)];
        ArraysKt.copyInto(this.intArgs, iArr, 0, 0, i);
        this.intArgs = iArr;
    }

    private final void resizeObjectArgs(int i, int i2) {
        Object[] objArr = new Object[determineNewSize(i, i2)];
        System.arraycopy(this.objectArgs, 0, objArr, 0, i);
        this.objectArgs = objArr;
    }

    private final void resizeOpCodes() {
        int iCoerceAtMost = RangesKt.coerceAtMost(this.opCodesSize, 1024);
        int i = this.opCodesSize;
        Operation[] operationArr = new Operation[iCoerceAtMost + i];
        System.arraycopy(this.opCodes, 0, operationArr, 0, i);
        this.opCodes = operationArr;
    }

    public final void clear() {
        this.opCodesSize = 0;
        this.intArgsSize = 0;
        ArraysKt.fill(this.objectArgs, (Object) null, 0, this.objectArgsSize);
        this.objectArgsSize = 0;
    }

    public final void ensureAllArgumentsPushedFor(Operation operation) {
        int i = this.pushedIntMask;
        int ints = operation.getInts();
        if (i == ((ints == 0 ? 0 : -1) >>> (32 - ints))) {
            operation.getObjects();
        }
    }

    public final void executeAndFlushAllPendingOperations(Applier applier, SlotWriter slotWriter, RememberManager rememberManager, OperationErrorContext operationErrorContext) {
        if (isNotEmpty()) {
            OpIterator opIterator = new OpIterator();
            while (true) {
                Applier applier2 = applier;
                SlotWriter slotWriter2 = slotWriter;
                RememberManager rememberManager2 = rememberManager;
                OperationErrorContext operationErrorContext2 = operationErrorContext;
                opIterator.getOperation().executeWithComposeStackTrace(opIterator, applier2, slotWriter2, rememberManager2, operationErrorContext2);
                if (!opIterator.next()) {
                    break;
                }
                applier = applier2;
                slotWriter = slotWriter2;
                rememberManager = rememberManager2;
                operationErrorContext = operationErrorContext2;
            }
        }
        clear();
    }

    public final int getSize() {
        return this.opCodesSize;
    }

    public final boolean isEmpty() {
        return getSize() == 0;
    }

    public final boolean isNotEmpty() {
        return getSize() != 0;
    }

    public final void popInto(Operations operations) {
        Operation[] operationArr = this.opCodes;
        int i = this.opCodesSize - 1;
        this.opCodesSize = i;
        Operation operation = operationArr[i];
        operationArr[i] = null;
        operations.pushOp(operation);
        Object[] objArr = this.objectArgs;
        Object[] objArr2 = operations.objectArgs;
        int objects = operations.objectArgsSize - operation.getObjects();
        int objects2 = this.objectArgsSize - operation.getObjects();
        System.arraycopy(objArr, objects2, objArr2, objects, this.objectArgsSize - objects2);
        ArraysKt.fill(this.objectArgs, (Object) null, this.objectArgsSize - operation.getObjects(), this.objectArgsSize);
        ArraysKt.copyInto(this.intArgs, operations.intArgs, operations.intArgsSize - operation.getInts(), this.intArgsSize - operation.getInts(), this.intArgsSize);
        this.objectArgsSize -= operation.getObjects();
        this.intArgsSize -= operation.getInts();
    }

    public final void push(Operation operation) {
        pushOp(operation);
    }

    public final void pushOp(Operation operation) {
        if (this.opCodesSize == this.opCodes.length) {
            resizeOpCodes();
        }
        int ints = this.intArgsSize + operation.getInts();
        int length = this.intArgs.length;
        if (ints > length) {
            resizeIntArgs(length, ints);
        }
        int objects = this.objectArgsSize + operation.getObjects();
        int length2 = this.objectArgs.length;
        if (objects > length2) {
            resizeObjectArgs(length2, objects);
        }
        Operation[] operationArr = this.opCodes;
        int i = this.opCodesSize;
        this.opCodesSize = i + 1;
        operationArr[i] = operation;
        this.intArgsSize += operation.getInts();
        this.objectArgsSize += operation.getObjects();
    }

    public String toString() {
        return super.toString();
    }
}
