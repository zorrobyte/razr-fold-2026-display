package kotlinx.coroutines.internal;

import kotlin.jvm.internal.PropertyReference0Impl;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.DebugStringsKt;

/* JADX INFO: compiled from: LockFreeLinkedList.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class LockFreeLinkedListNode {
    private final AtomicRef _next = AtomicFU.atomic(this);
    private final AtomicRef _prev = AtomicFU.atomic(this);
    private final AtomicRef _removedRef = AtomicFU.atomic((Object) null);

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001f, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0037, code lost:
    
        if (r3._next.compareAndSet(r2, ((kotlinx.coroutines.internal.Removed) r4).ref) != false) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final kotlinx.coroutines.internal.LockFreeLinkedListNode correctPrev() {
        /*
            r7 = this;
        L0:
            kotlinx.atomicfu.AtomicRef r0 = r7._prev
            java.lang.Object r0 = r0.getValue()
            kotlinx.coroutines.internal.LockFreeLinkedListNode r0 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r0
            r1 = 0
            r2 = r0
        La:
            r3 = r1
        Lb:
            kotlinx.atomicfu.AtomicRef r4 = r2._next
            java.lang.Object r4 = r4.getValue()
            if (r4 != r7) goto L20
            if (r0 != r2) goto L16
            goto L1f
        L16:
            kotlinx.atomicfu.AtomicRef r1 = r7._prev
            boolean r0 = r1.compareAndSet(r0, r2)
            if (r0 != 0) goto L1f
            goto L0
        L1f:
            return r2
        L20:
            boolean r5 = r7.isRemoved()
            if (r5 == 0) goto L27
            return r1
        L27:
            boolean r5 = r4 instanceof kotlinx.coroutines.internal.Removed
            if (r5 == 0) goto L45
            if (r3 == 0) goto L3c
            kotlinx.atomicfu.AtomicRef r5 = r3._next
            kotlinx.coroutines.internal.Removed r4 = (kotlinx.coroutines.internal.Removed) r4
            kotlinx.coroutines.internal.LockFreeLinkedListNode r4 = r4.ref
            boolean r2 = r5.compareAndSet(r2, r4)
            if (r2 != 0) goto L3a
            goto L0
        L3a:
            r2 = r3
            goto La
        L3c:
            kotlinx.atomicfu.AtomicRef r2 = r2._prev
            java.lang.Object r2 = r2.getValue()
            kotlinx.coroutines.internal.LockFreeLinkedListNode r2 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r2
            goto Lb
        L45:
            r4.getClass()
            r3 = r4
            kotlinx.coroutines.internal.LockFreeLinkedListNode r3 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r3
            r6 = r3
            r3 = r2
            r2 = r6
            goto Lb
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.LockFreeLinkedListNode.correctPrev():kotlinx.coroutines.internal.LockFreeLinkedListNode");
    }

    private final LockFreeLinkedListNode findPrevNonRemoved(LockFreeLinkedListNode lockFreeLinkedListNode) {
        while (lockFreeLinkedListNode.isRemoved()) {
            lockFreeLinkedListNode = (LockFreeLinkedListNode) lockFreeLinkedListNode._prev.getValue();
        }
        return lockFreeLinkedListNode;
    }

    private final void finishAdd(LockFreeLinkedListNode lockFreeLinkedListNode) {
        LockFreeLinkedListNode lockFreeLinkedListNode2;
        AtomicRef atomicRef = lockFreeLinkedListNode._prev;
        do {
            lockFreeLinkedListNode2 = (LockFreeLinkedListNode) atomicRef.getValue();
            if (getNext() != lockFreeLinkedListNode) {
                return;
            }
        } while (!lockFreeLinkedListNode._prev.compareAndSet(lockFreeLinkedListNode2, this));
        if (isRemoved()) {
            lockFreeLinkedListNode.correctPrev();
        }
    }

    private final Removed removed() {
        Removed removed = (Removed) this._removedRef.getValue();
        if (removed != null) {
            return removed;
        }
        Removed removed2 = new Removed(this);
        this._removedRef.lazySet(removed2);
        return removed2;
    }

    public final boolean addLast(LockFreeLinkedListNode lockFreeLinkedListNode, int i) {
        LockFreeLinkedListNode prevNode;
        lockFreeLinkedListNode.getClass();
        do {
            prevNode = getPrevNode();
            if (prevNode instanceof ListClosed) {
                return (((ListClosed) prevNode).forbiddenElementsBitmask & i) == 0 && prevNode.addLast(lockFreeLinkedListNode, i);
            }
        } while (!prevNode.addNext(lockFreeLinkedListNode, this));
        return true;
    }

    public final boolean addNext(LockFreeLinkedListNode lockFreeLinkedListNode, LockFreeLinkedListNode lockFreeLinkedListNode2) {
        lockFreeLinkedListNode.getClass();
        lockFreeLinkedListNode2.getClass();
        lockFreeLinkedListNode._prev.lazySet(this);
        lockFreeLinkedListNode._next.lazySet(lockFreeLinkedListNode2);
        if (!this._next.compareAndSet(lockFreeLinkedListNode2, lockFreeLinkedListNode)) {
            return false;
        }
        lockFreeLinkedListNode.finishAdd(lockFreeLinkedListNode2);
        return true;
    }

    public final boolean addOneIfEmpty(LockFreeLinkedListNode lockFreeLinkedListNode) {
        lockFreeLinkedListNode.getClass();
        lockFreeLinkedListNode._prev.lazySet(this);
        lockFreeLinkedListNode._next.lazySet(this);
        while (getNext() == this) {
            if (this._next.compareAndSet(this, lockFreeLinkedListNode)) {
                lockFreeLinkedListNode.finishAdd(this);
                return true;
            }
        }
        return false;
    }

    public final void close(int i) {
        addLast(new ListClosed(i), i);
    }

    public final Object getNext() {
        return this._next.getValue();
    }

    public final LockFreeLinkedListNode getNextNode() {
        LockFreeLinkedListNode lockFreeLinkedListNode;
        Object next = getNext();
        Removed removed = next instanceof Removed ? (Removed) next : null;
        if (removed != null && (lockFreeLinkedListNode = removed.ref) != null) {
            return lockFreeLinkedListNode;
        }
        next.getClass();
        return (LockFreeLinkedListNode) next;
    }

    public final LockFreeLinkedListNode getPrevNode() {
        LockFreeLinkedListNode lockFreeLinkedListNodeCorrectPrev = correctPrev();
        return lockFreeLinkedListNodeCorrectPrev == null ? findPrevNonRemoved((LockFreeLinkedListNode) this._prev.getValue()) : lockFreeLinkedListNodeCorrectPrev;
    }

    public boolean isRemoved() {
        return getNext() instanceof Removed;
    }

    public boolean remove() {
        return removeOrNext() == null;
    }

    public final LockFreeLinkedListNode removeOrNext() {
        Object next;
        LockFreeLinkedListNode lockFreeLinkedListNode;
        do {
            next = getNext();
            if (next instanceof Removed) {
                return ((Removed) next).ref;
            }
            if (next == this) {
                return (LockFreeLinkedListNode) next;
            }
            next.getClass();
            lockFreeLinkedListNode = (LockFreeLinkedListNode) next;
        } while (!this._next.compareAndSet(next, lockFreeLinkedListNode.removed()));
        lockFreeLinkedListNode.correctPrev();
        return null;
    }

    public String toString() {
        return new PropertyReference0Impl(this) { // from class: kotlinx.coroutines.internal.LockFreeLinkedListNode.toString.1
            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return DebugStringsKt.getClassSimpleName(this.receiver);
            }
        } + "@" + DebugStringsKt.getHexAddress(this);
    }
}
