package kotlin.ranges;

/* JADX INFO: compiled from: Ranges.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ClosedFloatingPointRange extends ClosedRange {
    boolean contains(Comparable comparable);

    boolean isEmpty();

    boolean lessThanOrEquals(Comparable comparable, Comparable comparable2);
}
