package androidx.compose.ui.layout;

/* JADX INFO: compiled from: ContentScale.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FixedScale implements ContentScale {
    private final float value;

    public FixedScale(float f) {
        this.value = f;
    }

    @Override // androidx.compose.ui.layout.ContentScale
    /* JADX INFO: renamed from: computeScaleFactor-H7hwNQA */
    public long mo1273computeScaleFactorH7hwNQA(long j, long j2) {
        float f = this.value;
        return ScaleFactor.m1299constructorimpl((((long) Float.floatToRawIntBits(f)) << 32) | (((long) Float.floatToRawIntBits(f)) & 4294967295L));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FixedScale) && Float.compare(this.value, ((FixedScale) obj).value) == 0;
    }

    public int hashCode() {
        return Float.hashCode(this.value);
    }

    public String toString() {
        return "FixedScale(value=" + this.value + ')';
    }
}
