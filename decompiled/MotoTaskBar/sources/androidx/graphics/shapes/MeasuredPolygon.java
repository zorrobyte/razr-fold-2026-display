package androidx.graphics.shapes;

import androidx.collection.FloatList;
import androidx.collection.MutableFloatList;
import androidx.graphics.shapes.Feature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.AbstractList;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: PolygonMeasure.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MeasuredPolygon extends AbstractList {
    public static final Companion Companion = new Companion(null);
    private final List cubics;
    private final List features;
    private final Measurer measurer;

    /* JADX INFO: compiled from: PolygonMeasure.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final MeasuredPolygon measurePolygon$graphics_shapes(Measurer measurer, RoundedPolygon roundedPolygon) {
            List listListOf;
            measurer.getClass();
            roundedPolygon.getClass();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int size = roundedPolygon.getFeatures().size();
            for (int i = 0; i < size; i++) {
                Feature feature = (Feature) roundedPolygon.getFeatures().get(i);
                int size2 = feature.getCubics().size();
                for (int i2 = 0; i2 < size2; i2++) {
                    if ((feature instanceof Feature.Corner) && i2 == feature.getCubics().size() / 2) {
                        arrayList2.add(TuplesKt.to(feature, Integer.valueOf(arrayList.size())));
                    }
                    arrayList.add(feature.getCubics().get(i2));
                }
            }
            Float fValueOf = Float.valueOf(0.0f);
            int iCollectionSizeOrDefault = CollectionsKt.collectionSizeOrDefault(arrayList, 9);
            if (iCollectionSizeOrDefault == 0) {
                listListOf = CollectionsKt.listOf(fValueOf);
            } else {
                ArrayList arrayList3 = new ArrayList(iCollectionSizeOrDefault + 1);
                arrayList3.add(fValueOf);
                int size3 = arrayList.size();
                int i3 = 0;
                while (i3 < size3) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    float fFloatValue = fValueOf.floatValue();
                    float fMeasureCubic = measurer.measureCubic((Cubic) obj);
                    if (fMeasureCubic < 0.0f) {
                        throw new IllegalArgumentException("Measured cubic is expected to be greater or equal to zero");
                    }
                    Unit unit = Unit.INSTANCE;
                    fValueOf = Float.valueOf(fFloatValue + fMeasureCubic);
                    arrayList3.add(fValueOf);
                }
                listListOf = arrayList3;
            }
            float fFloatValue2 = ((Number) CollectionsKt.last(listListOf)).floatValue();
            MutableFloatList mutableFloatList = new MutableFloatList(listListOf.size());
            int size4 = listListOf.size();
            for (int i4 = 0; i4 < size4; i4++) {
                mutableFloatList.add(((Number) listListOf.get(i4)).floatValue() / fFloatValue2);
            }
            String unused = PolygonMeasureKt.LOG_TAG;
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            int size5 = arrayList2.size();
            for (int i5 = 0; i5 < size5; i5++) {
                int iIntValue = ((Number) ((Pair) arrayList2.get(i5)).getSecond()).intValue();
                listCreateListBuilder.add(new ProgressableFeature(Utils.positiveModulo((mutableFloatList.get(iIntValue) + mutableFloatList.get(iIntValue + 1)) / 2, 1.0f), (Feature) ((Pair) arrayList2.get(i5)).getFirst()));
            }
            return new MeasuredPolygon(measurer, CollectionsKt.build(listCreateListBuilder), arrayList, mutableFloatList, null);
        }
    }

    /* JADX INFO: compiled from: PolygonMeasure.kt */
    public final class MeasuredCubic {
        private final Cubic cubic;
        private float endOutlineProgress;
        private final float measuredSize;
        private float startOutlineProgress;
        final /* synthetic */ MeasuredPolygon this$0;

        public MeasuredCubic(MeasuredPolygon measuredPolygon, Cubic cubic, float f, float f2) {
            cubic.getClass();
            this.this$0 = measuredPolygon;
            this.cubic = cubic;
            if (f2 < f) {
                throw new IllegalArgumentException("endOutlineProgress is expected to be equal or greater than startOutlineProgress");
            }
            this.measuredSize = measuredPolygon.measurer.measureCubic(cubic);
            this.startOutlineProgress = f;
            this.endOutlineProgress = f2;
        }

        public static /* synthetic */ void updateProgressRange$graphics_shapes$default(MeasuredCubic measuredCubic, float f, float f2, int i, Object obj) {
            if ((i & 1) != 0) {
                f = measuredCubic.startOutlineProgress;
            }
            if ((i & 2) != 0) {
                f2 = measuredCubic.endOutlineProgress;
            }
            measuredCubic.updateProgressRange$graphics_shapes(f, f2);
        }

        public final Pair cutAtProgress(float f) {
            float fCoerceIn = RangesKt.coerceIn(f, this.startOutlineProgress, this.endOutlineProgress);
            float f2 = this.endOutlineProgress;
            float f3 = this.startOutlineProgress;
            float fFindCubicCutPoint = this.this$0.measurer.findCubicCutPoint(this.cubic, ((fCoerceIn - f3) / (f2 - f3)) * this.measuredSize);
            if (0.0f > fFindCubicCutPoint || fFindCubicCutPoint > 1.0f) {
                throw new IllegalArgumentException("Cubic cut point is expected to be between 0 and 1");
            }
            String unused = PolygonMeasureKt.LOG_TAG;
            Pair pairSplit = this.cubic.split(fFindCubicCutPoint);
            return TuplesKt.to(new MeasuredCubic(this.this$0, (Cubic) pairSplit.component1(), this.startOutlineProgress, fCoerceIn), new MeasuredCubic(this.this$0, (Cubic) pairSplit.component2(), fCoerceIn, this.endOutlineProgress));
        }

        public final Cubic getCubic() {
            return this.cubic;
        }

        public final float getEndOutlineProgress() {
            return this.endOutlineProgress;
        }

        public final float getStartOutlineProgress() {
            return this.startOutlineProgress;
        }

        public String toString() {
            return "MeasuredCubic(outlineProgress=[" + this.startOutlineProgress + " .. " + this.endOutlineProgress + "], size=" + this.measuredSize + ", cubic=" + this.cubic + ')';
        }

        public final void updateProgressRange$graphics_shapes(float f, float f2) {
            if (f2 < f) {
                throw new IllegalArgumentException("endOutlineProgress is expected to be equal or greater than startOutlineProgress");
            }
            this.startOutlineProgress = f;
            this.endOutlineProgress = f2;
        }
    }

    private MeasuredPolygon(Measurer measurer, List list, List list2, FloatList floatList) {
        if (floatList.getSize() != list2.size() + 1) {
            throw new IllegalArgumentException("Outline progress size is expected to be the cubics size + 1");
        }
        if (floatList.first() != 0.0f) {
            throw new IllegalArgumentException("First outline progress value is expected to be zero");
        }
        if (floatList.last() != 1.0f) {
            throw new IllegalArgumentException("Last outline progress value is expected to be one");
        }
        this.measurer = measurer;
        this.features = list;
        ArrayList arrayList = new ArrayList();
        int size = list2.size();
        int i = 0;
        float f = 0.0f;
        while (i < size) {
            int i2 = i + 1;
            if (floatList.get(i2) - floatList.get(i) > 1.0E-4f) {
                arrayList.add(new MeasuredCubic(this, (Cubic) list2.get(i), f, floatList.get(i2)));
                f = floatList.get(i2);
            }
            i = i2;
        }
        MeasuredCubic.updateProgressRange$graphics_shapes$default((MeasuredCubic) arrayList.get(CollectionsKt.getLastIndex(arrayList)), 0.0f, 1.0f, 1, null);
        this.cubics = arrayList;
    }

    public /* synthetic */ MeasuredPolygon(Measurer measurer, List list, List list2, FloatList floatList, DefaultConstructorMarker defaultConstructorMarker) {
        this(measurer, list, list2, floatList);
    }

    public /* bridge */ boolean contains(MeasuredCubic measuredCubic) {
        return super.contains((Object) measuredCubic);
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.Set
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof MeasuredCubic) {
            return contains((MeasuredCubic) obj);
        }
        return false;
    }

    public final MeasuredPolygon cutAndShift(float f) {
        float fPositiveModulo;
        if (0.0f > f || f > 1.0f) {
            throw new IllegalArgumentException("Cutting point is expected to be between 0 and 1");
        }
        if (f < 1.0E-4f) {
            return this;
        }
        Iterator it = this.cubics.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            MeasuredCubic measuredCubic = (MeasuredCubic) it.next();
            float startOutlineProgress = measuredCubic.getStartOutlineProgress();
            if (f <= measuredCubic.getEndOutlineProgress() && startOutlineProgress <= f) {
                break;
            }
            i++;
        }
        Pair pairCutAtProgress = ((MeasuredCubic) this.cubics.get(i)).cutAtProgress(f);
        MeasuredCubic measuredCubic2 = (MeasuredCubic) pairCutAtProgress.component1();
        MeasuredCubic measuredCubic3 = (MeasuredCubic) pairCutAtProgress.component2();
        String unused = PolygonMeasureKt.LOG_TAG;
        List listMutableListOf = CollectionsKt.mutableListOf(measuredCubic3.getCubic());
        int size = this.cubics.size();
        for (int i2 = 1; i2 < size; i2++) {
            List list = this.cubics;
            listMutableListOf.add(((MeasuredCubic) list.get((i2 + i) % list.size())).getCubic());
        }
        listMutableListOf.add(measuredCubic2.getCubic());
        MutableFloatList mutableFloatList = new MutableFloatList(this.cubics.size() + 2);
        int size2 = this.cubics.size() + 2;
        for (int i3 = 0; i3 < size2; i3++) {
            if (i3 == 0) {
                fPositiveModulo = 0.0f;
            } else if (i3 == this.cubics.size() + 1) {
                fPositiveModulo = 1.0f;
            } else {
                fPositiveModulo = Utils.positiveModulo(((MeasuredCubic) this.cubics.get(((i + i3) - 1) % this.cubics.size())).getEndOutlineProgress() - f, 1.0f);
            }
            mutableFloatList.add(fPositiveModulo);
        }
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        int size3 = this.features.size();
        for (int i4 = 0; i4 < size3; i4++) {
            listCreateListBuilder.add(new ProgressableFeature(Utils.positiveModulo(((ProgressableFeature) this.features.get(i4)).getProgress() - f, 1.0f), ((ProgressableFeature) this.features.get(i4)).getFeature()));
        }
        return new MeasuredPolygon(this.measurer, CollectionsKt.build(listCreateListBuilder), listMutableListOf, mutableFloatList);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public MeasuredCubic get(int i) {
        return (MeasuredCubic) this.cubics.get(i);
    }

    public final List getFeatures() {
        return this.features;
    }

    @Override // kotlin.collections.AbstractCollection
    public int getSize() {
        return this.cubics.size();
    }

    public /* bridge */ int indexOf(MeasuredCubic measuredCubic) {
        return super.indexOf((Object) measuredCubic);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof MeasuredCubic) {
            return indexOf((MeasuredCubic) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(MeasuredCubic measuredCubic) {
        return super.lastIndexOf((Object) measuredCubic);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof MeasuredCubic) {
            return lastIndexOf((MeasuredCubic) obj);
        }
        return -1;
    }
}
