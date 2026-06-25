package androidx.graphics.shapes;

import androidx.collection.MutableFloatList;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FloatMapping.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DoubleMapper {
    public static final Companion Companion = new Companion(null);
    public static final DoubleMapper Identity;
    private final MutableFloatList sourceValues;
    private final MutableFloatList targetValues;

    /* JADX INFO: compiled from: FloatMapping.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        Float fValueOf = Float.valueOf(0.0f);
        Pair pair = TuplesKt.to(fValueOf, fValueOf);
        Float fValueOf2 = Float.valueOf(0.5f);
        Identity = new DoubleMapper(pair, TuplesKt.to(fValueOf2, fValueOf2));
    }

    public DoubleMapper(Pair... pairArr) {
        pairArr.getClass();
        this.sourceValues = new MutableFloatList(pairArr.length);
        this.targetValues = new MutableFloatList(pairArr.length);
        int length = pairArr.length;
        for (int i = 0; i < length; i++) {
            this.sourceValues.add(((Number) pairArr[i].getFirst()).floatValue());
            this.targetValues.add(((Number) pairArr[i].getSecond()).floatValue());
        }
        FloatMappingKt.validateProgress(this.sourceValues);
        FloatMappingKt.validateProgress(this.targetValues);
    }

    public final float map(float f) {
        return FloatMappingKt.linearMap(this.sourceValues, this.targetValues, f);
    }

    public final float mapBack(float f) {
        return FloatMappingKt.linearMap(this.targetValues, this.sourceValues, f);
    }
}
