package androidx.compose.ui.semantics;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: SemanticsProperties.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ProgressBarRangeInfo {
    public static final Companion Companion = new Companion(null);
    private static final ProgressBarRangeInfo Indeterminate = new ProgressBarRangeInfo(0.0f, RangesKt.rangeTo(0.0f, 0.0f), 0, 4, null);
    private final float current;
    private final ClosedFloatingPointRange range;
    private final int steps;

    /* JADX INFO: compiled from: SemanticsProperties.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ProgressBarRangeInfo getIndeterminate() {
            return ProgressBarRangeInfo.Indeterminate;
        }
    }

    public ProgressBarRangeInfo(float f, ClosedFloatingPointRange closedFloatingPointRange, int i) {
        this.current = f;
        this.range = closedFloatingPointRange;
        this.steps = i;
        if (Float.isNaN(f)) {
            throw new IllegalArgumentException("current must not be NaN");
        }
    }

    public /* synthetic */ ProgressBarRangeInfo(float f, ClosedFloatingPointRange closedFloatingPointRange, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, closedFloatingPointRange, (i2 & 4) != 0 ? 0 : i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProgressBarRangeInfo)) {
            return false;
        }
        ProgressBarRangeInfo progressBarRangeInfo = (ProgressBarRangeInfo) obj;
        return this.current == progressBarRangeInfo.current && Intrinsics.areEqual(this.range, progressBarRangeInfo.range) && this.steps == progressBarRangeInfo.steps;
    }

    public final float getCurrent() {
        return this.current;
    }

    public final ClosedFloatingPointRange getRange() {
        return this.range;
    }

    public final int getSteps() {
        return this.steps;
    }

    public int hashCode() {
        return (((Float.hashCode(this.current) * 31) + this.range.hashCode()) * 31) + this.steps;
    }

    public String toString() {
        return "ProgressBarRangeInfo(current=" + this.current + ", range=" + this.range + ", steps=" + this.steps + ')';
    }
}
