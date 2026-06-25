package kotlinx.coroutines.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.AtomicArray;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicFU_commonKt;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;

/* JADX INFO: compiled from: LockFreeTaskQueue.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class LockFreeTaskQueueCore {
    public static final Companion Companion = new Companion(null);
    public static final Symbol REMOVE_FROZEN = new Symbol("REMOVE_FROZEN");
    private final AtomicRef _next = AtomicFU.atomic((Object) null);
    private final AtomicLong _state = AtomicFU.atomic(0L);
    private final AtomicArray array;
    private final int capacity;
    private final int mask;
    private final boolean singleConsumer;

    /* JADX INFO: compiled from: LockFreeTaskQueue.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int addFailReason(long j) {
            return (j & 2305843009213693952L) != 0 ? 2 : 1;
        }

        public final long updateHead(long j, int i) {
            return wo(j, 1073741823L) | ((long) i);
        }

        public final long updateTail(long j, int i) {
            return wo(j, 1152921503533105152L) | (((long) i) << 30);
        }

        public final long wo(long j, long j2) {
            return j & (~j2);
        }
    }

    /* JADX INFO: compiled from: LockFreeTaskQueue.kt */
    public final class Placeholder {
        public final int index;

        public Placeholder(int i) {
            this.index = i;
        }
    }

    public LockFreeTaskQueueCore(int i, boolean z) {
        this.capacity = i;
        this.singleConsumer = z;
        int i2 = i - 1;
        this.mask = i2;
        this.array = AtomicFU_commonKt.atomicArrayOfNulls(i);
        if (i2 > 1073741823) {
            throw new IllegalStateException("Check failed.");
        }
        if ((i & i2) != 0) {
            throw new IllegalStateException("Check failed.");
        }
    }

    private final LockFreeTaskQueueCore allocateNextCopy(long j) {
        LockFreeTaskQueueCore lockFreeTaskQueueCore = new LockFreeTaskQueueCore(this.capacity * 2, this.singleConsumer);
        int i = (int) (1073741823 & j);
        int i2 = (int) ((1152921503533105152L & j) >> 30);
        while (true) {
            int i3 = this.mask;
            if ((i & i3) == (i2 & i3)) {
                lockFreeTaskQueueCore._state.setValue(Companion.wo(j, 1152921504606846976L));
                return lockFreeTaskQueueCore;
            }
            Object value = this.array.get(i3 & i).getValue();
            if (value == null) {
                value = new Placeholder(i);
            }
            lockFreeTaskQueueCore.array.get(lockFreeTaskQueueCore.mask & i).setValue(value);
            i++;
        }
    }

    private final LockFreeTaskQueueCore allocateOrGetNextCopy(long j) {
        AtomicRef atomicRef = this._next;
        while (true) {
            LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicRef.getValue();
            if (lockFreeTaskQueueCore != null) {
                return lockFreeTaskQueueCore;
            }
            this._next.compareAndSet(null, allocateNextCopy(j));
        }
    }

    private final LockFreeTaskQueueCore fillPlaceholder(int i, Object obj) {
        Object value = this.array.get(this.mask & i).getValue();
        if (!(value instanceof Placeholder) || ((Placeholder) value).index != i) {
            return null;
        }
        this.array.get(i & this.mask).setValue(obj);
        return this;
    }

    private final long markFrozen() {
        long value;
        long j;
        AtomicLong atomicLong = this._state;
        do {
            value = atomicLong.getValue();
            if ((value & 1152921504606846976L) != 0) {
                return value;
            }
            j = 1152921504606846976L | value;
        } while (!atomicLong.compareAndSet(value, j));
        return j;
    }

    private final LockFreeTaskQueueCore removeSlowPath(int i, int i2) {
        long value;
        int i3;
        AtomicLong atomicLong = this._state;
        do {
            value = atomicLong.getValue();
            i3 = (int) (1073741823 & value);
            if ((1152921504606846976L & value) != 0) {
                return next();
            }
        } while (!this._state.compareAndSet(value, Companion.updateHead(value, i2)));
        this.array.get(this.mask & i3).setValue(null);
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0055, code lost:
    
        return 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int addLast(java.lang.Object r13) {
        /*
            r12 = this;
            r13.getClass()
            kotlinx.atomicfu.AtomicLong r0 = r12._state
        L5:
            long r1 = r0.getValue()
            r3 = 3458764513820540928(0x3000000000000000, double:1.727233711018889E-77)
            long r3 = r3 & r1
            r5 = 0
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 == 0) goto L19
            kotlinx.coroutines.internal.LockFreeTaskQueueCore$Companion r12 = kotlinx.coroutines.internal.LockFreeTaskQueueCore.Companion
            int r12 = r12.addFailReason(r1)
            return r12
        L19:
            r3 = 1073741823(0x3fffffff, double:5.304989472E-315)
            long r3 = r3 & r1
            int r3 = (int) r3
            r7 = 1152921503533105152(0xfffffffc0000000, double:1.2882296003504729E-231)
            long r7 = r7 & r1
            r4 = 30
            long r7 = r7 >> r4
            int r4 = (int) r7
            int r7 = r12.mask
            int r8 = r4 + 2
            r8 = r8 & r7
            r9 = r3 & r7
            r10 = 1
            if (r8 != r9) goto L33
            return r10
        L33:
            boolean r8 = r12.singleConsumer
            r9 = 1073741823(0x3fffffff, float:1.9999999)
            if (r8 != 0) goto L56
            kotlinx.atomicfu.AtomicArray r8 = r12.array
            r11 = r4 & r7
            kotlinx.atomicfu.AtomicRef r8 = r8.get(r11)
            java.lang.Object r8 = r8.getValue()
            if (r8 == 0) goto L56
            int r1 = r12.capacity
            r2 = 1024(0x400, float:1.435E-42)
            if (r1 < r2) goto L55
            int r4 = r4 - r3
            r2 = r4 & r9
            int r1 = r1 >> 1
            if (r2 <= r1) goto L5
        L55:
            return r10
        L56:
            int r3 = r4 + 1
            r3 = r3 & r9
            kotlinx.atomicfu.AtomicLong r8 = r12._state
            kotlinx.coroutines.internal.LockFreeTaskQueueCore$Companion r9 = kotlinx.coroutines.internal.LockFreeTaskQueueCore.Companion
            long r9 = r9.updateTail(r1, r3)
            boolean r1 = r8.compareAndSet(r1, r9)
            if (r1 == 0) goto L5
            kotlinx.atomicfu.AtomicArray r0 = r12.array
            r1 = r4 & r7
            kotlinx.atomicfu.AtomicRef r0 = r0.get(r1)
            r0.setValue(r13)
        L72:
            kotlinx.atomicfu.AtomicLong r0 = r12._state
            long r0 = r0.getValue()
            r2 = 1152921504606846976(0x1000000000000000, double:1.2882297539194267E-231)
            long r0 = r0 & r2
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 == 0) goto L89
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r12 = r12.next()
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r12 = r12.fillPlaceholder(r4, r13)
            if (r12 != 0) goto L72
        L89:
            r12 = 0
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.LockFreeTaskQueueCore.addLast(java.lang.Object):int");
    }

    public final boolean close() {
        long value;
        AtomicLong atomicLong = this._state;
        do {
            value = atomicLong.getValue();
            if ((value & 2305843009213693952L) != 0) {
                return true;
            }
            if ((1152921504606846976L & value) != 0) {
                return false;
            }
        } while (!atomicLong.compareAndSet(value, 2305843009213693952L | value));
        return true;
    }

    public final int getSize() {
        long value = this._state.getValue();
        return 1073741823 & (((int) ((value & 1152921503533105152L) >> 30)) - ((int) (1073741823 & value)));
    }

    public final boolean isEmpty() {
        long value = this._state.getValue();
        return ((int) (1073741823 & value)) == ((int) ((value & 1152921503533105152L) >> 30));
    }

    public final LockFreeTaskQueueCore next() {
        return allocateOrGetNextCopy(markFrozen());
    }

    public final Object removeFirstOrNull() {
        AtomicLong atomicLong = this._state;
        while (true) {
            long value = atomicLong.getValue();
            if ((1152921504606846976L & value) != 0) {
                return REMOVE_FROZEN;
            }
            int i = (int) (1073741823 & value);
            int i2 = this.mask;
            if ((((int) ((1152921503533105152L & value) >> 30)) & i2) == (i & i2)) {
                return null;
            }
            Object value2 = this.array.get(i2 & i).getValue();
            if (value2 == null) {
                if (this.singleConsumer) {
                    return null;
                }
            } else {
                if (value2 instanceof Placeholder) {
                    return null;
                }
                int i3 = (i + 1) & 1073741823;
                if (this._state.compareAndSet(value, Companion.updateHead(value, i3))) {
                    this.array.get(this.mask & i).setValue(null);
                    return value2;
                }
                if (this.singleConsumer) {
                    do {
                        this = this.removeSlowPath(i, i3);
                    } while (this != null);
                    return value2;
                }
            }
        }
    }
}
