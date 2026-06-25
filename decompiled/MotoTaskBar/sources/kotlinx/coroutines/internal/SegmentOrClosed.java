package kotlinx.coroutines.internal;

/* JADX INFO: compiled from: ConcurrentLinkedList.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class SegmentOrClosed {
    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Object m2759constructorimpl(Object obj) {
        return obj;
    }

    /* JADX INFO: renamed from: getSegment-impl, reason: not valid java name */
    public static final Segment m2760getSegmentimpl(Object obj) {
        if (obj == ConcurrentLinkedListKt.CLOSED) {
            throw new IllegalStateException("Does not contain segment");
        }
        obj.getClass();
        return (Segment) obj;
    }

    /* JADX INFO: renamed from: isClosed-impl, reason: not valid java name */
    public static final boolean m2761isClosedimpl(Object obj) {
        return obj == ConcurrentLinkedListKt.CLOSED;
    }
}
