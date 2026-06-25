package androidx.compose.ui.graphics;

import android.graphics.Shader;

/* JADX INFO: compiled from: AndroidPaint.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AndroidPaint implements Paint {
    private int _blendMode;
    private ColorFilter internalColorFilter;
    private android.graphics.Paint internalPaint;
    private Shader internalShader;

    public AndroidPaint() {
        this(AndroidPaint_androidKt.makeNativePaint());
    }

    public AndroidPaint(android.graphics.Paint paint) {
        this.internalPaint = paint;
        this._blendMode = BlendMode.Companion.m862getSrcOver0nO6VwU();
    }

    @Override // androidx.compose.ui.graphics.Paint
    public android.graphics.Paint asFrameworkPaint() {
        return this.internalPaint;
    }

    @Override // androidx.compose.ui.graphics.Paint
    public float getAlpha() {
        return AndroidPaint_androidKt.getNativeAlpha(this.internalPaint);
    }

    @Override // androidx.compose.ui.graphics.Paint
    /* JADX INFO: renamed from: getBlendMode-0nO6VwU, reason: not valid java name */
    public int mo810getBlendMode0nO6VwU() {
        return this._blendMode;
    }

    @Override // androidx.compose.ui.graphics.Paint
    /* JADX INFO: renamed from: getColor-0d7_KjU, reason: not valid java name */
    public long mo811getColor0d7_KjU() {
        return AndroidPaint_androidKt.getNativeColor(this.internalPaint);
    }

    @Override // androidx.compose.ui.graphics.Paint
    public ColorFilter getColorFilter() {
        return this.internalColorFilter;
    }

    @Override // androidx.compose.ui.graphics.Paint
    /* JADX INFO: renamed from: getFilterQuality-f-v9h1I, reason: not valid java name */
    public int mo812getFilterQualityfv9h1I() {
        return AndroidPaint_androidKt.getNativeFilterQuality(this.internalPaint);
    }

    @Override // androidx.compose.ui.graphics.Paint
    public PathEffect getPathEffect() {
        return null;
    }

    @Override // androidx.compose.ui.graphics.Paint
    public Shader getShader() {
        return this.internalShader;
    }

    @Override // androidx.compose.ui.graphics.Paint
    /* JADX INFO: renamed from: getStrokeCap-KaPHkGw, reason: not valid java name */
    public int mo813getStrokeCapKaPHkGw() {
        return AndroidPaint_androidKt.getNativeStrokeCap(this.internalPaint);
    }

    @Override // androidx.compose.ui.graphics.Paint
    /* JADX INFO: renamed from: getStrokeJoin-LxFBmk8, reason: not valid java name */
    public int mo814getStrokeJoinLxFBmk8() {
        return AndroidPaint_androidKt.getNativeStrokeJoin(this.internalPaint);
    }

    @Override // androidx.compose.ui.graphics.Paint
    public float getStrokeMiterLimit() {
        return AndroidPaint_androidKt.getNativeStrokeMiterLimit(this.internalPaint);
    }

    @Override // androidx.compose.ui.graphics.Paint
    public float getStrokeWidth() {
        return AndroidPaint_androidKt.getNativeStrokeWidth(this.internalPaint);
    }

    @Override // androidx.compose.ui.graphics.Paint
    public void setAlpha(float f) {
        AndroidPaint_androidKt.setNativeAlpha(this.internalPaint, f);
    }

    @Override // androidx.compose.ui.graphics.Paint
    /* JADX INFO: renamed from: setBlendMode-s9anfk8, reason: not valid java name */
    public void mo815setBlendModes9anfk8(int i) {
        if (BlendMode.m832equalsimpl0(this._blendMode, i)) {
            return;
        }
        this._blendMode = i;
        AndroidPaint_androidKt.m821setNativeBlendModeGB0RdKg(this.internalPaint, i);
    }

    @Override // androidx.compose.ui.graphics.Paint
    /* JADX INFO: renamed from: setColor-8_81llA, reason: not valid java name */
    public void mo816setColor8_81llA(long j) {
        AndroidPaint_androidKt.m822setNativeColor4WTKRHQ(this.internalPaint, j);
    }

    @Override // androidx.compose.ui.graphics.Paint
    public void setColorFilter(ColorFilter colorFilter) {
        this.internalColorFilter = colorFilter;
        AndroidPaint_androidKt.setNativeColorFilter(this.internalPaint, colorFilter);
    }

    @Override // androidx.compose.ui.graphics.Paint
    /* JADX INFO: renamed from: setFilterQuality-vDHp3xo, reason: not valid java name */
    public void mo817setFilterQualityvDHp3xo(int i) {
        AndroidPaint_androidKt.m823setNativeFilterQuality50PEsBU(this.internalPaint, i);
    }

    @Override // androidx.compose.ui.graphics.Paint
    public void setPathEffect(PathEffect pathEffect) {
        AndroidPaint_androidKt.setNativePathEffect(this.internalPaint, pathEffect);
    }

    @Override // androidx.compose.ui.graphics.Paint
    public void setShader(Shader shader) {
        this.internalShader = shader;
        AndroidPaint_androidKt.setNativeShader(this.internalPaint, shader);
    }

    @Override // androidx.compose.ui.graphics.Paint
    /* JADX INFO: renamed from: setStrokeCap-BeK7IIE, reason: not valid java name */
    public void mo818setStrokeCapBeK7IIE(int i) {
        AndroidPaint_androidKt.m824setNativeStrokeCapCSYIeUk(this.internalPaint, i);
    }

    @Override // androidx.compose.ui.graphics.Paint
    /* JADX INFO: renamed from: setStrokeJoin-Ww9F2mQ, reason: not valid java name */
    public void mo819setStrokeJoinWw9F2mQ(int i) {
        AndroidPaint_androidKt.m825setNativeStrokeJoinkLtJ_vA(this.internalPaint, i);
    }

    @Override // androidx.compose.ui.graphics.Paint
    public void setStrokeMiterLimit(float f) {
        AndroidPaint_androidKt.setNativeStrokeMiterLimit(this.internalPaint, f);
    }

    @Override // androidx.compose.ui.graphics.Paint
    public void setStrokeWidth(float f) {
        AndroidPaint_androidKt.setNativeStrokeWidth(this.internalPaint, f);
    }

    @Override // androidx.compose.ui.graphics.Paint
    /* JADX INFO: renamed from: setStyle-k9PVt8s, reason: not valid java name */
    public void mo820setStylek9PVt8s(int i) {
        AndroidPaint_androidKt.m826setNativeStyle5YerkU(this.internalPaint, i);
    }
}
