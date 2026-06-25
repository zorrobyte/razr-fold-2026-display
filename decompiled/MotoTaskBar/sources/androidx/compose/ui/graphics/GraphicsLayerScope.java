package androidx.compose.ui.graphics;

import androidx.compose.ui.unit.Density;

/* JADX INFO: compiled from: GraphicsLayerScope.kt */
/* JADX INFO: loaded from: classes.dex */
public interface GraphicsLayerScope extends Density {
    float getCameraDistance();

    float getRotationX();

    float getRotationY();

    float getRotationZ();

    float getScaleX();

    float getScaleY();

    /* JADX INFO: renamed from: getTransformOrigin-SzJe1aQ, reason: not valid java name */
    long mo302getTransformOriginSzJe1aQ();

    float getTranslationX();

    float getTranslationY();
}
