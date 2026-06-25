package androidx.compose.ui.platform;

import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.CompositingStrategy;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.MatrixKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.TransformOrigin;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawContext;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.graphics.layer.GraphicsLayerKt;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.OwnedLayer;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GraphicsLayerOwnerLayer.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class GraphicsLayerOwnerLayer implements OwnedLayer {
    private final GraphicsContext context;
    private Function2 drawBlock;
    private boolean drawnWithEnabledZ;
    private GraphicsLayer graphicsLayer;
    private Function0 invalidateParentLayer;
    private float[] inverseMatrixCache;
    private boolean isDestroyed;
    private boolean isDirty;
    private boolean isInverseMatrixDirty;
    private boolean isMatrixDirty;
    private int mutatedFields;
    private Outline outline;
    private final AndroidComposeView ownerView;
    private long size;
    private final float[] matrixCache = Matrix.m941constructorimpl$default(null, 1, null);
    private Density density = DensityKt.Density$default(1.0f, 0.0f, 2, null);
    private LayoutDirection layoutDirection = LayoutDirection.Ltr;
    private final CanvasDrawScope scope = new CanvasDrawScope();
    private long transformOrigin = TransformOrigin.Companion.m1017getCenterSzJe1aQ();
    private boolean isIdentity = true;
    private final Function1 recordLambda = new Function1() { // from class: androidx.compose.ui.platform.GraphicsLayerOwnerLayer$recordLambda$1
        {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((DrawScope) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(DrawScope drawScope) {
            GraphicsLayerOwnerLayer graphicsLayerOwnerLayer = this.this$0;
            Canvas canvas = drawScope.getDrawContext().getCanvas();
            Function2 function2 = graphicsLayerOwnerLayer.drawBlock;
            if (function2 != null) {
                function2.invoke(canvas, drawScope.getDrawContext().getGraphicsLayer());
            }
        }
    };

    public GraphicsLayerOwnerLayer(GraphicsLayer graphicsLayer, GraphicsContext graphicsContext, AndroidComposeView androidComposeView, Function2 function2, Function0 function0) {
        this.graphicsLayer = graphicsLayer;
        this.context = graphicsContext;
        this.ownerView = androidComposeView;
        this.drawBlock = function2;
        this.invalidateParentLayer = function0;
        long j = Integer.MAX_VALUE;
        this.size = IntSize.m1919constructorimpl((j & 4294967295L) | (j << 32));
    }

    /* JADX INFO: renamed from: getInverseMatrix-3i98HWw, reason: not valid java name */
    private final float[] m1464getInverseMatrix3i98HWw() {
        float[] fArrM941constructorimpl$default = this.inverseMatrixCache;
        if (fArrM941constructorimpl$default == null) {
            fArrM941constructorimpl$default = Matrix.m941constructorimpl$default(null, 1, null);
            this.inverseMatrixCache = fArrM941constructorimpl$default;
        }
        if (!this.isInverseMatrixDirty) {
            if (Float.isNaN(fArrM941constructorimpl$default[0])) {
                return null;
            }
            return fArrM941constructorimpl$default;
        }
        this.isInverseMatrixDirty = false;
        float[] fArrM1465getMatrixsQKQjiQ = m1465getMatrixsQKQjiQ();
        if (this.isIdentity) {
            return fArrM1465getMatrixsQKQjiQ;
        }
        if (InvertMatrixKt.m1466invertToJiSxe2E(fArrM1465getMatrixsQKQjiQ, fArrM941constructorimpl$default)) {
            return fArrM941constructorimpl$default;
        }
        fArrM941constructorimpl$default[0] = Float.NaN;
        return null;
    }

    /* JADX INFO: renamed from: getMatrix-sQKQjiQ, reason: not valid java name */
    private final float[] m1465getMatrixsQKQjiQ() {
        updateMatrix();
        return this.matrixCache;
    }

    private final void setDirty(boolean z) {
        if (z != this.isDirty) {
            this.isDirty = z;
            this.ownerView.notifyLayerIsDirty$ui_release(this, z);
        }
    }

    private final void triggerRepaint() {
        WrapperRenderNodeLayerHelperMethods.INSTANCE.onDescendantInvalidated(this.ownerView);
    }

    private final void updateMatrix() {
        if (this.isMatrixDirty) {
            GraphicsLayer graphicsLayer = this.graphicsLayer;
            long jM795getCenteruvyYCjk = (graphicsLayer.m1098getPivotOffsetF1C5BW0() & 9223372034707292159L) == 9205357640488583168L ? SizeKt.m795getCenteruvyYCjk(IntSizeKt.m1930toSizeozmzZPI(this.size)) : graphicsLayer.m1098getPivotOffsetF1C5BW0();
            Matrix.m947resetToPivotedTransformimpl(this.matrixCache, Float.intBitsToFloat((int) (jM795getCenteruvyYCjk >> 32)), Float.intBitsToFloat((int) (jM795getCenteruvyYCjk & 4294967295L)), graphicsLayer.getTranslationX(), graphicsLayer.getTranslationY(), 1.0f, graphicsLayer.getRotationX(), graphicsLayer.getRotationY(), graphicsLayer.getRotationZ(), graphicsLayer.getScaleX(), graphicsLayer.getScaleY(), 1.0f);
            this.isMatrixDirty = false;
            this.isIdentity = MatrixKt.m955isIdentity58bKbWc(this.matrixCache);
        }
    }

    private final void updateOutline() {
        Outline outline = this.outline;
        if (outline == null) {
            return;
        }
        GraphicsLayerKt.setOutline(this.graphicsLayer, outline);
        boolean z = outline instanceof Outline.Generic;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public void destroy() {
        this.drawBlock = null;
        this.invalidateParentLayer = null;
        this.isDestroyed = true;
        setDirty(false);
        GraphicsContext graphicsContext = this.context;
        if (graphicsContext != null) {
            graphicsContext.releaseGraphicsLayer(this.graphicsLayer);
            this.ownerView.recycle$ui_release(this);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public void drawLayer(Canvas canvas, GraphicsLayer graphicsLayer) {
        updateDisplayList();
        this.drawnWithEnabledZ = this.graphicsLayer.getShadowElevation() > 0.0f;
        DrawContext drawContext = this.scope.getDrawContext();
        drawContext.setCanvas(canvas);
        drawContext.setGraphicsLayer(graphicsLayer);
        GraphicsLayerKt.drawLayer(this.scope, this.graphicsLayer);
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* JADX INFO: renamed from: getUnderlyingMatrix-sQKQjiQ */
    public float[] mo1406getUnderlyingMatrixsQKQjiQ() {
        return m1465getMatrixsQKQjiQ();
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public void invalidate() {
        if (this.isDirty || this.isDestroyed) {
            return;
        }
        this.ownerView.invalidate();
        setDirty(true);
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* JADX INFO: renamed from: isInLayer-k-4lQ0M */
    public boolean mo1407isInLayerk4lQ0M(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        if (this.graphicsLayer.getClip()) {
            return ShapeContainingUtilKt.isInOutline$default(this.graphicsLayer.getOutline(), fIntBitsToFloat, fIntBitsToFloat2, null, null, 24, null);
        }
        return true;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public void mapBounds(MutableRect mutableRect, boolean z) {
        float[] fArrM1464getInverseMatrix3i98HWw = z ? m1464getInverseMatrix3i98HWw() : m1465getMatrixsQKQjiQ();
        if (this.isIdentity) {
            return;
        }
        if (fArrM1464getInverseMatrix3i98HWw == null) {
            mutableRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        } else {
            Matrix.m945mapimpl(fArrM1464getInverseMatrix3i98HWw, mutableRect);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* JADX INFO: renamed from: mapOffset-8S9VItk */
    public long mo1408mapOffset8S9VItk(long j, boolean z) {
        float[] fArrM1465getMatrixsQKQjiQ;
        if (z) {
            fArrM1465getMatrixsQKQjiQ = m1464getInverseMatrix3i98HWw();
            if (fArrM1465getMatrixsQKQjiQ == null) {
                return Offset.Companion.m768getInfiniteF1C5BW0();
            }
        } else {
            fArrM1465getMatrixsQKQjiQ = m1465getMatrixsQKQjiQ();
        }
        return this.isIdentity ? j : Matrix.m944mapMKHz9U(fArrM1465getMatrixsQKQjiQ, j);
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* JADX INFO: renamed from: move--gyyYBs */
    public void mo1409movegyyYBs(long j) {
        this.graphicsLayer.m1108setTopLeftgyyYBs(j);
        triggerRepaint();
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* JADX INFO: renamed from: resize-ozmzZPI */
    public void mo1410resizeozmzZPI(long j) {
        if (IntSize.m1921equalsimpl0(j, this.size)) {
            return;
        }
        this.size = j;
        invalidate();
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public void reuseLayer(Function2 function2, Function0 function0) {
        GraphicsContext graphicsContext = this.context;
        if (graphicsContext == null) {
            InlineClassHelperKt.throwIllegalStateExceptionForNullCheck("currently reuse is only supported when we manage the layer lifecycle");
            throw new KotlinNothingValueException();
        }
        if (!this.graphicsLayer.isReleased()) {
            InlineClassHelperKt.throwIllegalArgumentException("layer should have been released before reuse");
        }
        this.graphicsLayer = graphicsContext.createGraphicsLayer();
        this.isDestroyed = false;
        this.drawBlock = function2;
        this.invalidateParentLayer = function0;
        this.isMatrixDirty = false;
        this.isInverseMatrixDirty = false;
        this.isIdentity = true;
        Matrix.m946resetimpl(this.matrixCache);
        float[] fArr = this.inverseMatrixCache;
        if (fArr != null) {
            Matrix.m946resetimpl(fArr);
        }
        this.transformOrigin = TransformOrigin.Companion.m1017getCenterSzJe1aQ();
        this.drawnWithEnabledZ = false;
        long j = Integer.MAX_VALUE;
        this.size = IntSize.m1919constructorimpl((j & 4294967295L) | (j << 32));
        this.outline = null;
        this.mutatedFields = 0;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public void updateDisplayList() {
        if (this.isDirty) {
            if (!TransformOrigin.m1011equalsimpl0(this.transformOrigin, TransformOrigin.Companion.m1017getCenterSzJe1aQ()) && !IntSize.m1921equalsimpl0(this.graphicsLayer.m1099getSizeYbymL2g(), this.size)) {
                GraphicsLayer graphicsLayer = this.graphicsLayer;
                float fM1012getPivotFractionXimpl = TransformOrigin.m1012getPivotFractionXimpl(this.transformOrigin) * ((int) (this.size >> 32));
                graphicsLayer.m1104setPivotOffsetk4lQ0M(Offset.m752constructorimpl((((long) Float.floatToRawIntBits(TransformOrigin.m1013getPivotFractionYimpl(this.transformOrigin) * ((int) (this.size & 4294967295L)))) & 4294967295L) | (Float.floatToRawIntBits(fM1012getPivotFractionXimpl) << 32)));
            }
            this.graphicsLayer.m1100recordmLhObY(this.density, this.layoutDirection, this.size, this.recordLambda);
            setDirty(false);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public void updateLayerProperties(ReusableGraphicsLayerScope reusableGraphicsLayerScope) {
        int iM1092getModulateAlphake2Ky5w;
        Function0 function0;
        int mutatedFields$ui_release = reusableGraphicsLayerScope.getMutatedFields$ui_release() | this.mutatedFields;
        this.layoutDirection = reusableGraphicsLayerScope.getLayoutDirection$ui_release();
        this.density = reusableGraphicsLayerScope.getGraphicsDensity$ui_release();
        int i = mutatedFields$ui_release & 4096;
        if (i != 0) {
            this.transformOrigin = reusableGraphicsLayerScope.mo919getTransformOriginSzJe1aQ();
        }
        if ((mutatedFields$ui_release & 1) != 0) {
            this.graphicsLayer.setScaleX(reusableGraphicsLayerScope.getScaleX());
        }
        if ((mutatedFields$ui_release & 2) != 0) {
            this.graphicsLayer.setScaleY(reusableGraphicsLayerScope.getScaleY());
        }
        if ((mutatedFields$ui_release & 4) != 0) {
            this.graphicsLayer.setAlpha(reusableGraphicsLayerScope.getAlpha());
        }
        if ((mutatedFields$ui_release & 8) != 0) {
            this.graphicsLayer.setTranslationX(reusableGraphicsLayerScope.getTranslationX());
        }
        if ((mutatedFields$ui_release & 16) != 0) {
            this.graphicsLayer.setTranslationY(reusableGraphicsLayerScope.getTranslationY());
        }
        if ((mutatedFields$ui_release & 32) != 0) {
            this.graphicsLayer.setShadowElevation(reusableGraphicsLayerScope.getShadowElevation());
            if (reusableGraphicsLayerScope.getShadowElevation() > 0.0f && !this.drawnWithEnabledZ && (function0 = this.invalidateParentLayer) != null) {
                function0.invoke();
            }
        }
        if ((mutatedFields$ui_release & 64) != 0) {
            this.graphicsLayer.m1101setAmbientShadowColor8_81llA(reusableGraphicsLayerScope.m975getAmbientShadowColor0d7_KjU());
        }
        if ((mutatedFields$ui_release & 128) != 0) {
            this.graphicsLayer.m1107setSpotShadowColor8_81llA(reusableGraphicsLayerScope.m979getSpotShadowColor0d7_KjU());
        }
        if ((mutatedFields$ui_release & 1024) != 0) {
            this.graphicsLayer.setRotationZ(reusableGraphicsLayerScope.getRotationZ());
        }
        if ((mutatedFields$ui_release & 256) != 0) {
            this.graphicsLayer.setRotationX(reusableGraphicsLayerScope.getRotationX());
        }
        if ((mutatedFields$ui_release & 512) != 0) {
            this.graphicsLayer.setRotationY(reusableGraphicsLayerScope.getRotationY());
        }
        if ((mutatedFields$ui_release & 2048) != 0) {
            this.graphicsLayer.setCameraDistance(reusableGraphicsLayerScope.getCameraDistance());
        }
        if (i != 0) {
            if (TransformOrigin.m1011equalsimpl0(this.transformOrigin, TransformOrigin.Companion.m1017getCenterSzJe1aQ())) {
                this.graphicsLayer.m1104setPivotOffsetk4lQ0M(Offset.Companion.m769getUnspecifiedF1C5BW0());
            } else {
                this.graphicsLayer.m1104setPivotOffsetk4lQ0M(Offset.m752constructorimpl((((long) Float.floatToRawIntBits(TransformOrigin.m1013getPivotFractionYimpl(this.transformOrigin) * ((int) (this.size & 4294967295L)))) & 4294967295L) | (((long) Float.floatToRawIntBits(TransformOrigin.m1012getPivotFractionXimpl(this.transformOrigin) * ((int) (this.size >> 32)))) << 32)));
            }
        }
        if ((mutatedFields$ui_release & 16384) != 0) {
            this.graphicsLayer.setClip(reusableGraphicsLayerScope.getClip());
        }
        if ((131072 & mutatedFields$ui_release) != 0) {
            GraphicsLayer graphicsLayer = this.graphicsLayer;
            reusableGraphicsLayerScope.getRenderEffect();
            graphicsLayer.setRenderEffect(null);
        }
        if ((262144 & mutatedFields$ui_release) != 0) {
            this.graphicsLayer.setColorFilter(reusableGraphicsLayerScope.getColorFilter());
        }
        if ((524288 & mutatedFields$ui_release) != 0) {
            this.graphicsLayer.m1102setBlendModes9anfk8(reusableGraphicsLayerScope.m976getBlendMode0nO6VwU());
        }
        if ((32768 & mutatedFields$ui_release) != 0) {
            GraphicsLayer graphicsLayer2 = this.graphicsLayer;
            int iM977getCompositingStrategyNrFUSI = reusableGraphicsLayerScope.m977getCompositingStrategyNrFUSI();
            CompositingStrategy.Companion companion = CompositingStrategy.Companion;
            if (CompositingStrategy.m903equalsimpl0(iM977getCompositingStrategyNrFUSI, companion.m906getAutoNrFUSI())) {
                iM1092getModulateAlphake2Ky5w = androidx.compose.ui.graphics.layer.CompositingStrategy.Companion.m1091getAutoke2Ky5w();
            } else if (CompositingStrategy.m903equalsimpl0(iM977getCompositingStrategyNrFUSI, companion.m908getOffscreenNrFUSI())) {
                iM1092getModulateAlphake2Ky5w = androidx.compose.ui.graphics.layer.CompositingStrategy.Companion.m1093getOffscreenke2Ky5w();
            } else {
                if (!CompositingStrategy.m903equalsimpl0(iM977getCompositingStrategyNrFUSI, companion.m907getModulateAlphaNrFUSI())) {
                    throw new IllegalStateException("Not supported composition strategy");
                }
                iM1092getModulateAlphake2Ky5w = androidx.compose.ui.graphics.layer.CompositingStrategy.Companion.m1092getModulateAlphake2Ky5w();
            }
            graphicsLayer2.m1103setCompositingStrategyWpw9cng(iM1092getModulateAlphake2Ky5w);
        }
        boolean z = true;
        if ((mutatedFields$ui_release & 7963) != 0) {
            this.isMatrixDirty = true;
            this.isInverseMatrixDirty = true;
        }
        if (Intrinsics.areEqual(this.outline, reusableGraphicsLayerScope.getOutline$ui_release())) {
            z = false;
        } else {
            this.outline = reusableGraphicsLayerScope.getOutline$ui_release();
            updateOutline();
        }
        this.mutatedFields = reusableGraphicsLayerScope.getMutatedFields$ui_release();
        if (mutatedFields$ui_release != 0 || z) {
            triggerRepaint();
        }
    }
}
