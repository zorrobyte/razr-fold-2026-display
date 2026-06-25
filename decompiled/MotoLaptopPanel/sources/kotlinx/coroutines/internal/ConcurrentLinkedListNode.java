package kotlinx.coroutines.internal;

import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;

/* JADX INFO: compiled from: ConcurrentLinkedList.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ConcurrentLinkedListNode {
    private final AtomicRef _next = AtomicFU.atomic((Object) null);
    private final AtomicRef _prev;

    public ConcurrentLinkedListNode(ConcurrentLinkedListNode concurrentLinkedListNode) {
        this._prev = AtomicFU.atomic(concurrentLinkedListNode);
    }

    private final ConcurrentLinkedListNode getAliveSegmentLeft() {
        ConcurrentLinkedListNode prev = getPrev();
        while (prev != null && prev.isRemoved()) {
            prev = (ConcurrentLinkedListNode) prev._prev.getValue();
        }
        return prev;
    }

    private final ConcurrentLinkedListNode getAliveSegmentRight() {
        ConcurrentLinkedListNode next;
        ConcurrentLinkedListNode next2 = getNext();
        next2.getClass();
        while (next2.isRemoved() && (next = next2.getNext()) != null) {
            next2 = next;
        }
        return next2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object getNextOrClosed() {
        return this._next.getValue();
    }

    public final void cleanPrev() {
        this._prev.lazySet(null);
    }

    public final ConcurrentLinkedListNode getNext() {
        Object nextOrClosed = getNextOrClosed();
        if (nextOrClosed == ConcurrentLinkedListKt.CLOSED) {
            return null;
        }
        return (ConcurrentLinkedListNode) nextOrClosed;
    }

    public final ConcurrentLinkedListNode getPrev() {
        return (ConcurrentLinkedListNode) this._prev.getValue();
    }

    public abstract boolean isRemoved();

    public final boolean isTail() {
        return getNext() == null;
    }

    public final boolean markAsClosed() {
        return this._next.compareAndSet(null, ConcurrentLinkedListKt.CLOSED);
    }

    public final void remove() {
        Object value;
        if (isTail()) {
            return;
        }
        while (true) {
            ConcurrentLinkedListNode aliveSegmentLeft = getAliveSegmentLeft();
            ConcurrentLinkedListNode aliveSegmentRight = getAliveSegmentRight();
            AtomicRef atomicRef = aliveSegmentRight._prev;
            do {
                value = atomicRef.getValue();
            } while (!atomicRef.compareAndSet(value, ((ConcurrentLinkedListNode) value) == null ? null : aliveSegmentLeft));
            if (aliveSegmentLeft != null) {
                aliveSegmentLeft._next.setValue(aliveSegmentRight);
            }
            if (!aliveSegmentRight.isRemoved() || aliveSegmentRight.isTail()) {
                if (aliveSegmentLeft == null || !aliveSegmentLeft.isRemoved()) {
                    return;
                }
            }
        }
    }

    public final boolean trySetNext(ConcurrentLinkedListNode concurrentLinkedListNode) {
        concurrentLinkedListNode.getClass();
        return this._next.compareAndSet(null, concurrentLinkedListNode);
    }
}
