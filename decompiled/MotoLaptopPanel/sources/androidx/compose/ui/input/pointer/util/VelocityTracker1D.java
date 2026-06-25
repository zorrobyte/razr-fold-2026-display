package androidx.compose.ui.input.pointer.util;

import androidx.compose.ui.internal.InlineClassHelperKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: VelocityTracker.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VelocityTracker1D {
    private int index;
    private final boolean isDataDifferential;
    private final int minSampleSize;
    private final float[] reusableDataPointsArray;
    private final float[] reusableTimeArray;
    private final float[] reusableVelocityCoefficients;
    private final DataPointAtTime[] samples;
    private final Strategy strategy;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: VelocityTracker.kt */
    public final class Strategy {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ Strategy[] $VALUES;
        public static final Strategy Lsq2 = new Strategy("Lsq2", 0);
        public static final Strategy Impulse = new Strategy("Impulse", 1);

        private static final /* synthetic */ Strategy[] $values() {
            return new Strategy[]{Lsq2, Impulse};
        }

        static {
            Strategy[] strategyArr$values = $values();
            $VALUES = strategyArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(strategyArr$values);
        }

        private Strategy(String str, int i) {
        }

        public static Strategy valueOf(String str) {
            return (Strategy) Enum.valueOf(Strategy.class, str);
        }

        public static Strategy[] values() {
            return (Strategy[]) $VALUES.clone();
        }
    }

    /* JADX INFO: compiled from: VelocityTracker.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Strategy.values().length];
            try {
                iArr[Strategy.Impulse.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Strategy.Lsq2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public VelocityTracker1D(boolean z, Strategy strategy) {
        this.isDataDifferential = z;
        this.strategy = strategy;
        if (z && strategy.equals(Strategy.Lsq2)) {
            throw new IllegalStateException("Lsq2 not (yet) supported for differential axes");
        }
        int i = WhenMappings.$EnumSwitchMapping$0[strategy.ordinal()];
        int i2 = 2;
        if (i != 1) {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            i2 = 3;
        }
        this.minSampleSize = i2;
        this.samples = new DataPointAtTime[20];
        this.reusableDataPointsArray = new float[20];
        this.reusableTimeArray = new float[20];
        this.reusableVelocityCoefficients = new float[3];
    }

    public /* synthetic */ VelocityTracker1D(boolean z, Strategy strategy, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? Strategy.Lsq2 : strategy);
    }

    private final float calculateLeastSquaresVelocity(float[] fArr, float[] fArr2, int i) {
        try {
            return VelocityTrackerKt.polyFitLeastSquares(fArr2, fArr, i, 2, this.reusableVelocityCoefficients)[1];
        } catch (IllegalArgumentException unused) {
            return 0.0f;
        }
    }

    public final void addDataPoint(long j, float f) {
        int i = (this.index + 1) % 20;
        this.index = i;
        VelocityTrackerKt.set(this.samples, i, j, f);
    }

    public final float calculateVelocity() {
        float fCalculateImpulseVelocity;
        float[] fArr = this.reusableDataPointsArray;
        float[] fArr2 = this.reusableTimeArray;
        int i = this.index;
        DataPointAtTime dataPointAtTime = this.samples[i];
        if (dataPointAtTime == null) {
            return 0.0f;
        }
        int i2 = 0;
        DataPointAtTime dataPointAtTime2 = dataPointAtTime;
        while (true) {
            DataPointAtTime dataPointAtTime3 = this.samples[i];
            if (dataPointAtTime3 == null) {
                break;
            }
            float time = dataPointAtTime.getTime() - dataPointAtTime3.getTime();
            float fAbs = Math.abs(dataPointAtTime3.getTime() - dataPointAtTime2.getTime());
            DataPointAtTime dataPointAtTime4 = (this.strategy == Strategy.Lsq2 || this.isDataDifferential) ? dataPointAtTime3 : dataPointAtTime;
            if (time > 100.0f || fAbs > 40.0f) {
                break;
            }
            fArr[i2] = dataPointAtTime3.getDataPoint();
            fArr2[i2] = -time;
            if (i == 0) {
                i = 20;
            }
            i--;
            i2++;
            if (i2 >= 20) {
                break;
            }
            dataPointAtTime2 = dataPointAtTime4;
        }
        if (i2 < this.minSampleSize) {
            return 0.0f;
        }
        int i3 = WhenMappings.$EnumSwitchMapping$0[this.strategy.ordinal()];
        if (i3 == 1) {
            fCalculateImpulseVelocity = VelocityTrackerKt.calculateImpulseVelocity(fArr, fArr2, i2, this.isDataDifferential);
        } else {
            if (i3 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            fCalculateImpulseVelocity = calculateLeastSquaresVelocity(fArr, fArr2, i2);
        }
        return fCalculateImpulseVelocity * 1000;
    }

    public final float calculateVelocity(float f) {
        if (!(f > 0.0f)) {
            InlineClassHelperKt.throwIllegalStateException("maximumVelocity should be a positive value. You specified=" + f);
        }
        float fCalculateVelocity = calculateVelocity();
        if (fCalculateVelocity == 0.0f || Float.isNaN(fCalculateVelocity)) {
            return 0.0f;
        }
        return fCalculateVelocity > 0.0f ? RangesKt.coerceAtMost(fCalculateVelocity, f) : RangesKt.coerceAtLeast(fCalculateVelocity, -f);
    }

    public final void resetTracking() {
        ArraysKt.fill$default(this.samples, (Object) null, 0, 0, 6, (Object) null);
        this.index = 0;
    }
}
