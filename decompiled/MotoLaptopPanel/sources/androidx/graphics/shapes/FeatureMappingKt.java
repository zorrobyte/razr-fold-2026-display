package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import androidx.graphics.shapes.Feature;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;

/* JADX INFO: compiled from: FeatureMapping.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FeatureMappingKt {
    private static final List IdentityMapping;
    private static final String LOG_TAG;

    static {
        Float fValueOf = Float.valueOf(0.0f);
        Pair pair = TuplesKt.to(fValueOf, fValueOf);
        Float fValueOf2 = Float.valueOf(0.5f);
        IdentityMapping = CollectionsKt.listOf((Object[]) new Pair[]{pair, TuplesKt.to(fValueOf2, fValueOf2)});
        LOG_TAG = "FeatureMapping";
    }

    public static final List doMapping(List list, List list2) {
        list.getClass();
        list2.getClass();
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ProgressableFeature progressableFeature = (ProgressableFeature) it.next();
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                ProgressableFeature progressableFeature2 = (ProgressableFeature) it2.next();
                float fFeatureDistSquared = featureDistSquared(progressableFeature.getFeature(), progressableFeature2.getFeature());
                if (fFeatureDistSquared != Float.MAX_VALUE) {
                    listCreateListBuilder.add(new DistanceVertex(fFeatureDistSquared, progressableFeature, progressableFeature2));
                }
            }
        }
        List<DistanceVertex> listSortedWith = CollectionsKt.sortedWith(CollectionsKt.build(listCreateListBuilder), new Comparator() { // from class: androidx.graphics.shapes.FeatureMappingKt$doMapping$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt.compareValues(Float.valueOf(((DistanceVertex) obj).getDistance()), Float.valueOf(((DistanceVertex) obj2).getDistance()));
            }
        });
        if (listSortedWith.isEmpty()) {
            return IdentityMapping;
        }
        if (listSortedWith.size() == 1) {
            DistanceVertex distanceVertex = (DistanceVertex) CollectionsKt.first(listSortedWith);
            float progress = distanceVertex.getF1().getProgress();
            float progress2 = distanceVertex.getF2().getProgress();
            return CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(Float.valueOf(progress), Float.valueOf(progress2)), TuplesKt.to(Float.valueOf((progress + 0.5f) % 1.0f), Float.valueOf((progress2 + 0.5f) % 1.0f))});
        }
        MappingHelper mappingHelper = new MappingHelper();
        for (DistanceVertex distanceVertex2 : listSortedWith) {
            mappingHelper.addMapping(distanceVertex2.getF1(), distanceVertex2.getF2());
        }
        return mappingHelper.getMapping();
    }

    public static final float featureDistSquared(Feature feature, Feature feature2) {
        feature.getClass();
        feature2.getClass();
        if ((feature instanceof Feature.Corner) && (feature2 instanceof Feature.Corner) && ((Feature.Corner) feature).getConvex() != ((Feature.Corner) feature2).getConvex()) {
            return Float.MAX_VALUE;
        }
        return PointKt.m1982getDistanceSquaredDnnuFBc(PointKt.m1986minusybeJwSQ(featureRepresentativePoint(feature), featureRepresentativePoint(feature2)));
    }

    public static final DoubleMapper featureMapper(List list, List list2) {
        list.getClass();
        list2.getClass();
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (((ProgressableFeature) list.get(i)).getFeature() instanceof Feature.Corner) {
                listCreateListBuilder.add(list.get(i));
            }
        }
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        List listCreateListBuilder2 = CollectionsKt.createListBuilder();
        int size2 = list2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            if (((ProgressableFeature) list2.get(i2)).getFeature() instanceof Feature.Corner) {
                listCreateListBuilder2.add(list2.get(i2));
            }
        }
        Pair[] pairArr = (Pair[]) doMapping(listBuild, CollectionsKt.build(listCreateListBuilder2)).toArray(new Pair[0]);
        return new DoubleMapper((Pair[]) Arrays.copyOf(pairArr, pairArr.length));
    }

    public static final long featureRepresentativePoint(Feature feature) {
        feature.getClass();
        return FloatFloatPair.m11constructorimpl((((Cubic) CollectionsKt.first(feature.getCubics())).getAnchor0X() + ((Cubic) CollectionsKt.last(feature.getCubics())).getAnchor1X()) / 2.0f, (((Cubic) CollectionsKt.first(feature.getCubics())).getAnchor0Y() + ((Cubic) CollectionsKt.last(feature.getCubics())).getAnchor1Y()) / 2.0f);
    }
}
