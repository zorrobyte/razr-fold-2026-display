package androidx.graphics.shapes;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: FeatureMapping.kt */
/* JADX INFO: loaded from: classes.dex */
final class MappingHelper {
    private final List mapping = new ArrayList();
    private final Set usedF1 = new LinkedHashSet();
    private final Set usedF2 = new LinkedHashSet();

    public final void addMapping(ProgressableFeature progressableFeature, ProgressableFeature progressableFeature2) {
        progressableFeature.getClass();
        progressableFeature2.getClass();
        if (this.usedF1.contains(progressableFeature) || this.usedF2.contains(progressableFeature2)) {
            return;
        }
        List list = this.mapping;
        final Float fValueOf = Float.valueOf(progressableFeature.getProgress());
        int iBinarySearch = CollectionsKt.binarySearch(list, 0, list.size(), new Function1() { // from class: androidx.graphics.shapes.MappingHelper$addMapping$$inlined$binarySearchBy$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Integer invoke(Object obj) {
                return Integer.valueOf(ComparisonsKt.compareValues((Float) ((Pair) obj).getFirst(), fValueOf));
            }
        });
        if (iBinarySearch >= 0) {
            throw new IllegalArgumentException("There can't be two features with the same progress");
        }
        int i = (-iBinarySearch) - 1;
        int size = this.mapping.size();
        if (size >= 1) {
            Pair pair = (Pair) this.mapping.get(((i + size) - 1) % size);
            float fFloatValue = ((Number) pair.component1()).floatValue();
            float fFloatValue2 = ((Number) pair.component2()).floatValue();
            Pair pair2 = (Pair) this.mapping.get(i % size);
            float fFloatValue3 = ((Number) pair2.component1()).floatValue();
            float fFloatValue4 = ((Number) pair2.component2()).floatValue();
            if (FloatMappingKt.progressDistance(progressableFeature.getProgress(), fFloatValue) < 1.0E-4f || FloatMappingKt.progressDistance(progressableFeature.getProgress(), fFloatValue3) < 1.0E-4f || FloatMappingKt.progressDistance(progressableFeature2.getProgress(), fFloatValue2) < 1.0E-4f || FloatMappingKt.progressDistance(progressableFeature2.getProgress(), fFloatValue4) < 1.0E-4f) {
                return;
            }
            if (size > 1 && !FloatMappingKt.progressInRange(progressableFeature2.getProgress(), fFloatValue2, fFloatValue4)) {
                return;
            }
        }
        this.mapping.add(i, TuplesKt.to(Float.valueOf(progressableFeature.getProgress()), Float.valueOf(progressableFeature2.getProgress())));
        this.usedF1.add(progressableFeature);
        this.usedF2.add(progressableFeature2);
    }

    public final List getMapping() {
        return this.mapping;
    }
}
