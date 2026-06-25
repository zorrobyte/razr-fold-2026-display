package androidx.graphics.shapes;

/* JADX INFO: compiled from: PolygonMeasure.kt */
/* JADX INFO: loaded from: classes.dex */
public interface Measurer {
    float findCubicCutPoint(Cubic cubic, float f);

    float measureCubic(Cubic cubic);
}
