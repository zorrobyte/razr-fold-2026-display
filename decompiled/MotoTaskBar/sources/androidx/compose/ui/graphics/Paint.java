package androidx.compose.ui.graphics;

import android.graphics.Shader;

/* JADX INFO: compiled from: Paint.kt */
/* JADX INFO: loaded from: classes.dex */
public interface Paint {
    android.graphics.Paint asFrameworkPaint();

    /* JADX INFO: renamed from: getBlendMode-0nO6VwU */
    int mo221getBlendMode0nO6VwU();

    /* JADX INFO: renamed from: getColor-0d7_KjU */
    long mo222getColor0d7_KjU();

    ColorFilter getColorFilter();

    /* JADX INFO: renamed from: getFilterQuality-f-v9h1I */
    int mo223getFilterQualityfv9h1I();

    Shader getShader();

    void setAlpha(float f);

    /* JADX INFO: renamed from: setBlendMode-s9anfk8 */
    void mo224setBlendModes9anfk8(int i);

    /* JADX INFO: renamed from: setColor-8_81llA */
    void mo225setColor8_81llA(long j);

    void setColorFilter(ColorFilter colorFilter);

    /* JADX INFO: renamed from: setFilterQuality-vDHp3xo */
    void mo226setFilterQualityvDHp3xo(int i);

    void setShader(Shader shader);

    void setStrokeWidth(float f);

    /* JADX INFO: renamed from: setStyle-k9PVt8s */
    void mo227setStylek9PVt8s(int i);
}
