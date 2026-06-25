package androidx.compose.ui.layout;

import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.node.MotionReferencePlacementDelegate;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Placeable.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Placeable implements Measured {
    private int height;
    private long measuredSize;
    private int width;
    private long measurementConstraints = PlaceableKt.DefaultConstraints;
    private long apparentToRealOffset = IntOffset.Companion.m1913getZeronOccac();

    /* JADX INFO: compiled from: Placeable.kt */
    public abstract class PlacementScope {
        private boolean motionFrameOfReferencePlacement;

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public final void handleMotionFrameOfReferencePlacement(Placeable placeable) {
            if (placeable instanceof MotionReferencePlacementDelegate) {
                ((MotionReferencePlacementDelegate) placeable).updatePlacedUnderMotionFrameOfReference(this.motionFrameOfReferencePlacement);
            }
        }

        public static /* synthetic */ void place$default(PlacementScope placementScope, Placeable placeable, int i, int i2, float f, int i3, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: place");
            }
            if ((i3 & 4) != 0) {
                f = 0.0f;
            }
            placementScope.place(placeable, i, i2, f);
        }

        /* JADX INFO: renamed from: place-70tqf50$default, reason: not valid java name */
        public static /* synthetic */ void m1295place70tqf50$default(PlacementScope placementScope, Placeable placeable, long j, float f, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: place-70tqf50");
            }
            if ((i & 2) != 0) {
                f = 0.0f;
            }
            placementScope.m1296place70tqf50(placeable, j, f);
        }

        public static /* synthetic */ void placeRelative$default(PlacementScope placementScope, Placeable placeable, int i, int i2, float f, int i3, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: placeRelative");
            }
            if ((i3 & 4) != 0) {
                f = 0.0f;
            }
            placementScope.placeRelative(placeable, i, i2, f);
        }

        public static /* synthetic */ void placeRelativeWithLayer$default(PlacementScope placementScope, Placeable placeable, int i, int i2, float f, Function1 function1, int i3, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: placeRelativeWithLayer");
            }
            if ((i3 & 4) != 0) {
                f = 0.0f;
            }
            float f2 = f;
            if ((i3 & 8) != 0) {
                function1 = PlaceableKt.DefaultLayerBlock;
            }
            placementScope.placeRelativeWithLayer(placeable, i, i2, f2, function1);
        }

        public static /* synthetic */ void placeWithLayer$default(PlacementScope placementScope, Placeable placeable, int i, int i2, float f, Function1 function1, int i3, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: placeWithLayer");
            }
            if ((i3 & 4) != 0) {
                f = 0.0f;
            }
            float f2 = f;
            if ((i3 & 8) != 0) {
                function1 = PlaceableKt.DefaultLayerBlock;
            }
            placementScope.placeWithLayer(placeable, i, i2, f2, function1);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public abstract LayoutDirection getParentLayoutDirection();

        /* JADX INFO: Access modifiers changed from: protected */
        public abstract int getParentWidth();

        public final void place(Placeable placeable, int i, int i2, float f) {
            long jM1902constructorimpl = IntOffset.m1902constructorimpl((((long) i2) & 4294967295L) | (((long) i) << 32));
            handleMotionFrameOfReferencePlacement(placeable);
            placeable.mo1292placeAtf8xVGno(IntOffset.m1909plusqkQi6aY(jM1902constructorimpl, placeable.apparentToRealOffset), f, (Function1) null);
        }

        /* JADX INFO: renamed from: place-70tqf50, reason: not valid java name */
        public final void m1296place70tqf50(Placeable placeable, long j, float f) {
            handleMotionFrameOfReferencePlacement(placeable);
            placeable.mo1292placeAtf8xVGno(IntOffset.m1909plusqkQi6aY(j, placeable.apparentToRealOffset), f, (Function1) null);
        }

        public final void placeRelative(Placeable placeable, int i, int i2, float f) {
            long jM1902constructorimpl = IntOffset.m1902constructorimpl((((long) i) << 32) | (((long) i2) & 4294967295L));
            if (getParentLayoutDirection() == LayoutDirection.Ltr || getParentWidth() == 0) {
                handleMotionFrameOfReferencePlacement(placeable);
                placeable.mo1292placeAtf8xVGno(IntOffset.m1909plusqkQi6aY(jM1902constructorimpl, placeable.apparentToRealOffset), f, (Function1) null);
            } else {
                long jM1902constructorimpl2 = IntOffset.m1902constructorimpl((((long) ((getParentWidth() - placeable.getWidth()) - IntOffset.m1905getXimpl(jM1902constructorimpl))) << 32) | (((long) IntOffset.m1906getYimpl(jM1902constructorimpl)) & 4294967295L));
                handleMotionFrameOfReferencePlacement(placeable);
                placeable.mo1292placeAtf8xVGno(IntOffset.m1909plusqkQi6aY(jM1902constructorimpl2, placeable.apparentToRealOffset), f, (Function1) null);
            }
        }

        public final void placeRelativeWithLayer(Placeable placeable, int i, int i2, float f, Function1 function1) {
            long jM1902constructorimpl = IntOffset.m1902constructorimpl((((long) i) << 32) | (((long) i2) & 4294967295L));
            if (getParentLayoutDirection() == LayoutDirection.Ltr || getParentWidth() == 0) {
                handleMotionFrameOfReferencePlacement(placeable);
                placeable.mo1292placeAtf8xVGno(IntOffset.m1909plusqkQi6aY(jM1902constructorimpl, placeable.apparentToRealOffset), f, function1);
            } else {
                long jM1902constructorimpl2 = IntOffset.m1902constructorimpl((((long) ((getParentWidth() - placeable.getWidth()) - IntOffset.m1905getXimpl(jM1902constructorimpl))) << 32) | (((long) IntOffset.m1906getYimpl(jM1902constructorimpl)) & 4294967295L));
                handleMotionFrameOfReferencePlacement(placeable);
                placeable.mo1292placeAtf8xVGno(IntOffset.m1909plusqkQi6aY(jM1902constructorimpl2, placeable.apparentToRealOffset), f, function1);
            }
        }

        public final void placeWithLayer(Placeable placeable, int i, int i2, float f, Function1 function1) {
            long jM1902constructorimpl = IntOffset.m1902constructorimpl((((long) i2) & 4294967295L) | (((long) i) << 32));
            handleMotionFrameOfReferencePlacement(placeable);
            placeable.mo1292placeAtf8xVGno(IntOffset.m1909plusqkQi6aY(jM1902constructorimpl, placeable.apparentToRealOffset), f, function1);
        }

        /* JADX INFO: renamed from: placeWithLayer-aW-9-wM, reason: not valid java name */
        public final void m1297placeWithLayeraW9wM(Placeable placeable, long j, float f, Function1 function1) {
            handleMotionFrameOfReferencePlacement(placeable);
            placeable.mo1292placeAtf8xVGno(IntOffset.m1909plusqkQi6aY(j, placeable.apparentToRealOffset), f, function1);
        }

        /* JADX INFO: renamed from: placeWithLayer-aW-9-wM, reason: not valid java name */
        public final void m1298placeWithLayeraW9wM(Placeable placeable, long j, GraphicsLayer graphicsLayer, float f) {
            handleMotionFrameOfReferencePlacement(placeable);
            placeable.mo1291placeAtf8xVGno(IntOffset.m1909plusqkQi6aY(j, placeable.apparentToRealOffset), f, graphicsLayer);
        }
    }

    public Placeable() {
        long j = 0;
        this.measuredSize = IntSize.m1919constructorimpl((j & 4294967295L) | (j << 32));
    }

    private final void onMeasuredSizeChanged() {
        this.width = RangesKt.coerceIn((int) (this.measuredSize >> 32), Constraints.m1862getMinWidthimpl(this.measurementConstraints), Constraints.m1860getMaxWidthimpl(this.measurementConstraints));
        int iCoerceIn = RangesKt.coerceIn((int) (this.measuredSize & 4294967295L), Constraints.m1861getMinHeightimpl(this.measurementConstraints), Constraints.m1859getMaxHeightimpl(this.measurementConstraints));
        this.height = iCoerceIn;
        int i = this.width;
        long j = this.measuredSize;
        this.apparentToRealOffset = IntOffset.m1902constructorimpl((((long) ((i - ((int) (j >> 32))) / 2)) << 32) | (4294967295L & ((long) ((iCoerceIn - ((int) (j & 4294967295L))) / 2))));
    }

    /* JADX INFO: renamed from: getApparentToRealOffset-nOcc-ac, reason: not valid java name */
    protected final long m1288getApparentToRealOffsetnOccac() {
        return this.apparentToRealOffset;
    }

    public final int getHeight() {
        return this.height;
    }

    public int getMeasuredHeight() {
        return (int) (this.measuredSize & 4294967295L);
    }

    /* JADX INFO: renamed from: getMeasuredSize-YbymL2g, reason: not valid java name */
    protected final long m1289getMeasuredSizeYbymL2g() {
        return this.measuredSize;
    }

    public int getMeasuredWidth() {
        return (int) (this.measuredSize >> 32);
    }

    /* JADX INFO: renamed from: getMeasurementConstraints-msEJaDk, reason: not valid java name */
    protected final long m1290getMeasurementConstraintsmsEJaDk() {
        return this.measurementConstraints;
    }

    public final int getWidth() {
        return this.width;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: placeAt-f8xVGno, reason: not valid java name */
    public void mo1291placeAtf8xVGno(long j, float f, GraphicsLayer graphicsLayer) {
        mo1292placeAtf8xVGno(j, f, (Function1) null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: placeAt-f8xVGno, reason: not valid java name */
    public abstract void mo1292placeAtf8xVGno(long j, float f, Function1 function1);

    /* JADX INFO: renamed from: setMeasuredSize-ozmzZPI, reason: not valid java name */
    protected final void m1293setMeasuredSizeozmzZPI(long j) {
        if (IntSize.m1921equalsimpl0(this.measuredSize, j)) {
            return;
        }
        this.measuredSize = j;
        onMeasuredSizeChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: setMeasurementConstraints-BRTryo0, reason: not valid java name */
    public final void m1294setMeasurementConstraintsBRTryo0(long j) {
        if (Constraints.m1854equalsimpl0(this.measurementConstraints, j)) {
            return;
        }
        this.measurementConstraints = j;
        onMeasuredSizeChanged();
    }
}
