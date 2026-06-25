package androidx.graphics.shapes;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FeatureMapping.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DistanceVertex {
    private final float distance;
    private final ProgressableFeature f1;
    private final ProgressableFeature f2;

    public DistanceVertex(float f, ProgressableFeature progressableFeature, ProgressableFeature progressableFeature2) {
        progressableFeature.getClass();
        progressableFeature2.getClass();
        this.distance = f;
        this.f1 = progressableFeature;
        this.f2 = progressableFeature2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DistanceVertex)) {
            return false;
        }
        DistanceVertex distanceVertex = (DistanceVertex) obj;
        return Float.compare(this.distance, distanceVertex.distance) == 0 && Intrinsics.areEqual(this.f1, distanceVertex.f1) && Intrinsics.areEqual(this.f2, distanceVertex.f2);
    }

    public final float getDistance() {
        return this.distance;
    }

    public final ProgressableFeature getF1() {
        return this.f1;
    }

    public final ProgressableFeature getF2() {
        return this.f2;
    }

    public int hashCode() {
        return (((Float.hashCode(this.distance) * 31) + this.f1.hashCode()) * 31) + this.f2.hashCode();
    }

    public String toString() {
        return "DistanceVertex(distance=" + this.distance + ", f1=" + this.f1 + ", f2=" + this.f2 + ')';
    }
}
