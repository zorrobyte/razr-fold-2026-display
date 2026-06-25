package androidx.compose.ui.graphics;

/* JADX INFO: compiled from: PathMeasure.kt */
/* JADX INFO: loaded from: classes.dex */
public interface PathMeasure {
    float getLength();

    boolean getSegment(float f, float f2, Path path, boolean z);

    void setPath(Path path, boolean z);
}
