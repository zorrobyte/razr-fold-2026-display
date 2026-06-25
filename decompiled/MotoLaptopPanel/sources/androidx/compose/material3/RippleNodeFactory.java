package androidx.compose.material3;

import androidx.compose.foundation.IndicationNodeFactory;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Ripple.kt */
/* JADX INFO: loaded from: classes.dex */
final class RippleNodeFactory implements IndicationNodeFactory {
    private final boolean bounded;
    private final long color;
    private final ColorProducer colorProducer;
    private final float radius;

    private RippleNodeFactory(boolean z, float f, long j) {
        this(z, f, (ColorProducer) null, j);
    }

    public /* synthetic */ RippleNodeFactory(boolean z, float f, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, f, j);
    }

    private RippleNodeFactory(boolean z, float f, ColorProducer colorProducer, long j) {
        this.bounded = z;
        this.radius = f;
        this.colorProducer = colorProducer;
        this.color = j;
    }

    @Override // androidx.compose.foundation.IndicationNodeFactory
    public DelegatableNode create(InteractionSource interactionSource) {
        ColorProducer colorProducer = this.colorProducer;
        if (colorProducer == null) {
            colorProducer = new ColorProducer() { // from class: androidx.compose.material3.RippleNodeFactory$create$colorProducer$1
                @Override // androidx.compose.ui.graphics.ColorProducer
                /* JADX INFO: renamed from: invoke-0d7_KjU */
                public final long mo289invoke0d7_KjU() {
                    return this.this$0.color;
                }
            };
        }
        return new DelegatingThemeAwareRippleNode(interactionSource, this.bounded, this.radius, colorProducer, null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RippleNodeFactory)) {
            return false;
        }
        RippleNodeFactory rippleNodeFactory = (RippleNodeFactory) obj;
        if (this.bounded == rippleNodeFactory.bounded && Dp.m1879equalsimpl0(this.radius, rippleNodeFactory.radius) && Intrinsics.areEqual(this.colorProducer, rippleNodeFactory.colorProducer)) {
            return Color.m882equalsimpl0(this.color, rippleNodeFactory.color);
        }
        return false;
    }

    @Override // androidx.compose.foundation.IndicationNodeFactory
    public int hashCode() {
        int iHashCode = ((Boolean.hashCode(this.bounded) * 31) + Dp.m1880hashCodeimpl(this.radius)) * 31;
        ColorProducer colorProducer = this.colorProducer;
        return ((iHashCode + (colorProducer != null ? colorProducer.hashCode() : 0)) * 31) + Color.m888hashCodeimpl(this.color);
    }
}
