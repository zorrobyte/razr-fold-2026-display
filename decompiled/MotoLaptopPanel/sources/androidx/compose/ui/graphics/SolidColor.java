package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Brush.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SolidColor extends Brush {
    private final long value;

    private SolidColor(long j) {
        super(null);
        this.value = j;
    }

    public /* synthetic */ SolidColor(long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(j);
    }

    @Override // androidx.compose.ui.graphics.Brush
    /* JADX INFO: renamed from: applyTo-Pq9zytI */
    public void mo866applyToPq9zytI(long j, Paint paint, float f) {
        long jM880copywmQWz5c$default;
        paint.setAlpha(1.0f);
        if (f == 1.0f) {
            jM880copywmQWz5c$default = this.value;
        } else {
            long j2 = this.value;
            jM880copywmQWz5c$default = Color.m880copywmQWz5c$default(j2, Color.m883getAlphaimpl(j2) * f, 0.0f, 0.0f, 0.0f, 14, null);
        }
        paint.mo816setColor8_81llA(jM880copywmQWz5c$default);
        if (paint.getShader() != null) {
            paint.setShader(null);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SolidColor) && Color.m882equalsimpl0(this.value, ((SolidColor) obj).value);
    }

    /* JADX INFO: renamed from: getValue-0d7_KjU, reason: not valid java name */
    public final long m993getValue0d7_KjU() {
        return this.value;
    }

    public int hashCode() {
        return Color.m888hashCodeimpl(this.value);
    }

    public String toString() {
        return "SolidColor(value=" + ((Object) Color.m889toStringimpl(this.value)) + ')';
    }
}
