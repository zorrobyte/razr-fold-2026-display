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
    private final float[] matrixCache = Matrix.m305constructorimpl$default(null, 1, null);
    private Density density = DensityKt.Density$default(1.0f, 0.0f, 2, null);
    private LayoutDirection layoutDirection = LayoutDirection.Ltr;
    private final CanvasDrawScope scope = new CanvasDrawScope();
    private long transformOrigin = TransformOrigin.Companion.m346getCenterSzJe1aQ();
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
        this.size = IntSize.m1008constructorimpl((j & 4294967295L) | (j << 32));
    }

    /* JADX INFO: renamed from: getInverseMatrix-3i98HWw, reason: not valid java name */
    private final float[] m715getInverseMatrix3i98HWw() {
        float[] fArrM305constructorimpl$default = this.inverseMatrixCache;
        if (fArrM305constructorimpl$default == null) {
            fArrM305constructorimpl$default = Matrix.m305constructorimpl$default(null, 1, null);
            this.inverseMatrixCache = fArrM305constructorimpl$default;
        }
        if (!this.isInverseMatrixDirty) {
            if (Float.isNaN(fArrM305constructorimpl$default[0])) {
                return null;
            }
            return fArrM305constructorimpl$default;
        }
        this.isInverseMatrixDirty = false;
        float[] fArrM716getMatrixsQKQjiQ = m716getMatrixsQKQjiQ();
        if (this.isIdentity) {
            return fArrM716getMatrixsQKQjiQ;
        }
        if (InvertMatrixKt.m717invertToJiSxe2E(fArrM716getMatrixsQKQjiQ, fArrM305constructorimpl$default)) {
            return fArrM305constructorimpl$default;
        }
        fArrM305constructorimpl$default[0] = Float.NaN;
        return null;
    }

    /* JADX INFO: renamed from: getMatrix-sQKQjiQ, reason: not valid java name */
    private final float[] m716getMatrixsQKQjiQ() {
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
            long jM212getCenteruvyYCjk = (graphicsLayer.m401getPivotOffsetF1C5BW0() & 9223372034707292159L) == 9205357640488583168L ? SizeKt.m212getCenteruvyYCjk(IntSizeKt.m1016toSizeozmzZPI(this.size)) : graphicsLayer.m401getPivotOffsetF1C5BW0();
            Matrix.m311resetToPivotedTransformimpl(this.matrixCache, Float.intBitsToFloat((int) (jM212getCenteruvyYCjk >> 32)), Float.intBitsToFloat((int) (jM212getCenteruvyYCjk & 4294967295L)), graphicsLayer.getTranslationX(), graphicsLayer.getTranslationY(), 1.0f, graphicsLayer.getRotationX(), graphicsLayer.getRotationY(), graphicsLayer.getRotationZ(), graphicsLayer.getScaleX(), graphicsLayer.getScaleY(), 1.0f);
            this.isMatrixDirty = false;
            this.isIdentity = MatrixKt.m317isIdentity58bKbWc(this.matrixCache);
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
    public float[] mo660getUnderlyingMatrixsQKQjiQ() {
        return m716getMatrixsQKQjiQ();
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
    public boolean mo661isInLayerk4lQ0M(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        if (this.graphicsLayer.getClip()) {
            return ShapeContainingUtilKt.isInOutline$default(this.graphicsLayer.getOutline(), fIntBitsToFloat, fIntBitsToFloat2, null, null, 24, null);
        }
        return true;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public void mapBounds(MutableRect mutableRect, boolean z) {
        float[] fArrM715getInverseMatrix3i98HWw = z ? m715getInverseMatrix3i98HWw() : m716getMatrixsQKQjiQ();
        if (this.isIdentity) {
            return;
        }
        if (fArrM715getInverseMatrix3i98HWw == null) {
            mutableRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        } else {
            Matrix.m309mapimpl(fArrM715getInverseMatrix3i98HWw, mutableRect);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* JADX INFO: renamed from: mapOffset-8S9VItk */
    public long mo662mapOffset8S9VItk(long j, boolean z) {
        float[] fArrM716getMatrixsQKQjiQ;
        if (z) {
            fArrM716getMatrixsQKQjiQ = m715getInverseMatrix3i98HWw();
            if (fArrM716getMatrixsQKQjiQ == null) {
                return Offset.Companion.m193getInfiniteF1C5BW0();
            }
        } else {
            fArrM716getMatrixsQKQjiQ = m716getMatrixsQKQjiQ();
        }
        return this.isIdentity ? j : Matrix.m308mapMKHz9U(fArrM716getMatrixsQKQjiQ, j);
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* JADX INFO: renamed from: move--gyyYBs */
    public void mo663movegyyYBs(long j) {
        this.graphicsLayer.m411setTopLeftgyyYBs(j);
        triggerRepaint();
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* JADX INFO: renamed from: resize-ozmzZPI */
    public void mo664resizeozmzZPI(long j) {
        if (IntSize.m1010equalsimpl0(j, this.size)) {
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
        Matrix.m310resetimpl(this.matrixCache);
        float[] fArr = this.inverseMatrixCache;
        if (fArr != null) {
            Matrix.m310resetimpl(fArr);
        }
        this.transformOrigin = TransformOrigin.Companion.m346getCenterSzJe1aQ();
        this.drawnWithEnabledZ = false;
        long j = Integer.MAX_VALUE;
        this.size = IntSize.m1008constructorimpl((j & 4294967295L) | (j << 32));
        this.outline = null;
        this.mutatedFields = 0;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public void updateDisplayList() {
        if (this.isDirty) {
            if (!TransformOrigin.m343equalsimpl0(this.transformOrigin, TransformOrigin.Companion.m346getCenterSzJe1aQ()) && !IntSize.m1010equalsimpl0(this.graphicsLayer.m402getSizeYbymL2g(), this.size)) {
                GraphicsLayer graphicsLayer = this.graphicsLayer;
                float fM344getPivotFractionXimpl = TransformOrigin.m344getPivotFractionXimpl(this.transformOrigin) * ((int) (this.size >> 32));
                graphicsLayer.m407setPivotOffsetk4lQ0M(Offset.m182constructorimpl((((long) Float.floatToRawIntBits(TransformOrigin.m345getPivotFractionYimpl(this.transformOrigin) * ((int) (this.size & 4294967295L)))) & 4294967295L) | (Float.floatToRawIntBits(fM344getPivotFractionXimpl) << 32)));
            }
            this.graphicsLayer.m403recordmLhObY(this.density, this.layoutDirection, this.size, this.recordLambda);
            setDirty(false);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public void updateLayerProperties(ReusableGraphicsLayerScope reusableGraphicsLayerScope) {
        int iM395getModulateAlphake2Ky5w;
        Function0 function0;
        int mutatedFields$ui_release = reusableGraphicsLayerScope.getMutatedFields$ui_release() | this.mutatedFields;
        this.layoutDirection = reusableGraphicsLayerScope.getLayoutDirection$ui_release();
        this.density = reusableGraphicsLayerScope.getGraphicsDensity$ui_release();
        int i = mutatedFields$ui_release & 4096;
        if (i != 0) {
            this.transformOrigin = reusableGraphicsLayerScope.mo302getTransformOriginSzJe1aQ();
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
                function0.mo2224invoke();
            }
        }
        if ((mutatedFields$ui_release & 64) != 0) {
            this.graphicsLayer.m404setAmbientShadowColor8_81llA(reusableGraphicsLayerScope.m329getAmbientShadowColor0d7_KjU());
        }
        if ((mutatedFields$ui_release & 128) != 0) {
            this.graphicsLayer.m410setSpotShadowColor8_81llA(reusableGraphicsLayerScope.m333getSpotShadowColor0d7_KjU());
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
            if (TransformOrigin.m343equalsimpl0(this.transformOrigin, TransformOrigin.Companion.m346getCenterSzJe1aQ())) {
                this.graphicsLayer.m407setPivotOffsetk4lQ0M(Offset.Companion.m194getUnspecifiedF1C5BW0());
            } else {
                this.graphicsLayer.m407setPivotOffsetk4lQ0M(Offset.m182constructorimpl((((long) Float.floatToRawIntBits(TransformOrigin.m345getPivotFractionYimpl(this.transformOrigin) * ((int) (this.size & 4294967295L)))) & 4294967295L) | (((long) Float.floatToRawIntBits(TransformOrigin.m344getPivotFractionXimpl(this.transformOrigin) * ((int) (this.size >> 32)))) << 32)));
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
            GraphicsLayer graphicsLayer2 = this.graphicsLayer;
            reusableGraphicsLayerScope.getColorFilter();
            graphicsLayer2.setColorFilter(null);
        }
        if ((524288 & mutatedFields$ui_release) != 0) {
            this.graphicsLayer.m405setBlendModes9anfk8(reusableGraphicsLayerScope.m330getBlendMode0nO6VwU());
        }
        if ((32768 & mutatedFields$ui_release) != 0) {
            GraphicsLayer graphicsLayer3 = this.graphicsLayer;
            int iM331getCompositingStrategyNrFUSI = reusableGraphicsLayerScope.m331getCompositingStrategyNrFUSI();
            CompositingStrategy.Companion companion = CompositingStrategy.Companion;
            if (CompositingStrategy.m294equalsimpl0(iM331getCompositingStrategyNrFUSI, companion.m295getAutoNrFUSI())) {
                iM395getModulateAlphake2Ky5w = androidx.compose.ui.graphics.layer.CompositingStrategy.Companion.m394getAutoke2Ky5w();
            } else if (CompositingStrategy.m294equalsimpl0(iM331getCompositingStrategyNrFUSI, companion.m297getOffscreenNrFUSI())) {
                iM395getModulateAlphake2Ky5w = androidx.compose.ui.graphics.layer.CompositingStrategy.Companion.m396getOffscreenke2Ky5w();
            } else {
                if (!CompositingStrategy.m294equalsimpl0(iM331getCompositingStrategyNrFUSI, companion.m296getModulateAlphaNrFUSI())) {
                    throw new IllegalStateException("Not supported composition strategy");
                }
                iM395getModulateAlphake2Ky5w = androidx.compose.ui.graphics.layer.CompositingStrategy.Companion.m395getModulateAlphake2Ky5w();
            }
            graphicsLayer3.m406setCompositingStrategyWpw9cng(iM395getModulateAlphake2Ky5w);
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
