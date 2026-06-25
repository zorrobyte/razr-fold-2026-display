package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawContext;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: LayoutNodeDrawScope.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LayoutNodeDrawScope implements DrawScope, ContentDrawScope {
    private final CanvasDrawScope canvasDrawScope;
    private DrawModifierNode drawNode;

    public LayoutNodeDrawScope(CanvasDrawScope canvasDrawScope) {
        this.canvasDrawScope = canvasDrawScope;
    }

    public /* synthetic */ LayoutNodeDrawScope(CanvasDrawScope canvasDrawScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new CanvasDrawScope() : canvasDrawScope);
    }

    /* JADX INFO: renamed from: draw-eZhPAX0$ui_release, reason: not valid java name */
    public final void m1335draweZhPAX0$ui_release(Canvas canvas, long j, NodeCoordinator nodeCoordinator, Modifier.Node node, GraphicsLayer graphicsLayer) {
        int iM1404constructorimpl = NodeKind.m1404constructorimpl(4);
        Modifier.Node nodePop = node;
        MutableVector mutableVector = null;
        while (nodePop != null) {
            if (nodePop instanceof DrawModifierNode) {
                m1336drawDirecteZhPAX0$ui_release(canvas, j, nodeCoordinator, (DrawModifierNode) nodePop, graphicsLayer);
            } else if ((nodePop.getKindSet$ui_release() & iM1404constructorimpl) != 0 && (nodePop instanceof DelegatingNode)) {
                int i = 0;
                for (Modifier.Node delegate$ui_release = ((DelegatingNode) nodePop).getDelegate$ui_release(); delegate$ui_release != null; delegate$ui_release = delegate$ui_release.getChild$ui_release()) {
                    if ((delegate$ui_release.getKindSet$ui_release() & iM1404constructorimpl) != 0) {
                        i++;
                        if (i == 1) {
                            nodePop = delegate$ui_release;
                        } else {
                            if (mutableVector == null) {
                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                            }
                            if (nodePop != null) {
                                mutableVector.add(nodePop);
                                nodePop = null;
                            }
                            mutableVector.add(delegate$ui_release);
                        }
                    }
                }
                if (i == 1) {
                }
            }
            nodePop = DelegatableNodeKt.pop(mutableVector);
        }
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* JADX INFO: renamed from: drawCircle-VaOC9Bg */
    public void mo1054drawCircleVaOC9Bg(long j, float f, long j2, float f2, DrawStyle drawStyle, ColorFilter colorFilter, int i) {
        this.canvasDrawScope.mo1054drawCircleVaOC9Bg(j, f, j2, f2, drawStyle, colorFilter, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v5, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r5v6 */
    @Override // androidx.compose.ui.graphics.drawscope.ContentDrawScope
    public void drawContent() {
        Canvas canvas = getDrawContext().getCanvas();
        DrawModifierNode drawModifierNode = this.drawNode;
        if (drawModifierNode == null) {
            InlineClassHelperKt.throwIllegalStateExceptionForNullCheck("Attempting to drawContent for a `null` node. This usually means that a call to ContentDrawScope#drawContent() has been captured inside a lambda, and is being invoked outside of the draw pass. Capturing the scope this way is unsupported - if you are trying to record drawContent with graphicsLayer.record(), make sure you are using the GraphicsLayer#record function within DrawScope, instead of the member function on GraphicsLayer.");
            throw new KotlinNothingValueException();
        }
        ?? NextDrawNode = LayoutNodeDrawScopeKt.nextDrawNode(drawModifierNode);
        if (NextDrawNode == 0) {
            NodeCoordinator nodeCoordinatorM1308requireCoordinator64DMado = DelegatableNodeKt.m1308requireCoordinator64DMado(drawModifierNode, NodeKind.m1404constructorimpl(4));
            if (nodeCoordinatorM1308requireCoordinator64DMado.getTail() == drawModifierNode.getNode()) {
                nodeCoordinatorM1308requireCoordinator64DMado = nodeCoordinatorM1308requireCoordinator64DMado.getWrapped$ui_release();
                nodeCoordinatorM1308requireCoordinator64DMado.getClass();
            }
            nodeCoordinatorM1308requireCoordinator64DMado.performDraw(canvas, getDrawContext().getGraphicsLayer());
            return;
        }
        int iM1404constructorimpl = NodeKind.m1404constructorimpl(4);
        ?? mutableVector = 0;
        while (NextDrawNode != 0) {
            if (NextDrawNode instanceof DrawModifierNode) {
                performDraw((DrawModifierNode) NextDrawNode, canvas, getDrawContext().getGraphicsLayer());
            } else if ((NextDrawNode.getKindSet$ui_release() & iM1404constructorimpl) != 0 && (NextDrawNode instanceof DelegatingNode)) {
                Modifier.Node delegate$ui_release = ((DelegatingNode) NextDrawNode).getDelegate$ui_release();
                int i = 0;
                NextDrawNode = NextDrawNode;
                mutableVector = mutableVector;
                while (delegate$ui_release != null) {
                    if ((delegate$ui_release.getKindSet$ui_release() & iM1404constructorimpl) != 0) {
                        i++;
                        mutableVector = mutableVector;
                        if (i == 1) {
                            NextDrawNode = delegate$ui_release;
                        } else {
                            if (mutableVector == 0) {
                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                            }
                            if (NextDrawNode != 0) {
                                mutableVector.add(NextDrawNode);
                                NextDrawNode = 0;
                            }
                            mutableVector.add(delegate$ui_release);
                        }
                    }
                    delegate$ui_release = delegate$ui_release.getChild$ui_release();
                    NextDrawNode = NextDrawNode;
                    mutableVector = mutableVector;
                }
                if (i == 1) {
                }
            }
            NextDrawNode = DelegatableNodeKt.pop(mutableVector);
        }
    }

    /* JADX INFO: renamed from: drawDirect-eZhPAX0$ui_release, reason: not valid java name */
    public final void m1336drawDirecteZhPAX0$ui_release(Canvas canvas, long j, NodeCoordinator nodeCoordinator, DrawModifierNode drawModifierNode, GraphicsLayer graphicsLayer) {
        DrawModifierNode drawModifierNode2 = this.drawNode;
        this.drawNode = drawModifierNode;
        CanvasDrawScope canvasDrawScope = this.canvasDrawScope;
        LayoutDirection layoutDirection = nodeCoordinator.getLayoutDirection();
        Density density = canvasDrawScope.getDrawContext().getDensity();
        LayoutDirection layoutDirection2 = canvasDrawScope.getDrawContext().getLayoutDirection();
        Canvas canvas2 = canvasDrawScope.getDrawContext().getCanvas();
        long jMo1065getSizeNHjbRc = canvasDrawScope.getDrawContext().mo1065getSizeNHjbRc();
        GraphicsLayer graphicsLayer2 = canvasDrawScope.getDrawContext().getGraphicsLayer();
        DrawContext drawContext = canvasDrawScope.getDrawContext();
        drawContext.setDensity(nodeCoordinator);
        drawContext.setLayoutDirection(layoutDirection);
        drawContext.setCanvas(canvas);
        drawContext.mo1066setSizeuvyYCjk(j);
        drawContext.setGraphicsLayer(graphicsLayer);
        canvas.save();
        try {
            drawModifierNode.draw(this);
            canvas.restore();
            DrawContext drawContext2 = canvasDrawScope.getDrawContext();
            drawContext2.setDensity(density);
            drawContext2.setLayoutDirection(layoutDirection2);
            drawContext2.setCanvas(canvas2);
            drawContext2.mo1066setSizeuvyYCjk(jMo1065getSizeNHjbRc);
            drawContext2.setGraphicsLayer(graphicsLayer2);
            this.drawNode = drawModifierNode2;
        } catch (Throwable th) {
            canvas.restore();
            DrawContext drawContext3 = canvasDrawScope.getDrawContext();
            drawContext3.setDensity(density);
            drawContext3.setLayoutDirection(layoutDirection2);
            drawContext3.setCanvas(canvas2);
            drawContext3.mo1066setSizeuvyYCjk(jMo1065getSizeNHjbRc);
            drawContext3.setGraphicsLayer(graphicsLayer2);
            throw th;
        }
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* JADX INFO: renamed from: drawImage-AZ2fEMs */
    public void mo1055drawImageAZ2fEMs(ImageBitmap imageBitmap, long j, long j2, long j3, long j4, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2) {
        this.canvasDrawScope.mo1055drawImageAZ2fEMs(imageBitmap, j, j2, j3, j4, f, drawStyle, colorFilter, i, i2);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* JADX INFO: renamed from: drawPath-GBMwjPU */
    public void mo1056drawPathGBMwjPU(Path path, Brush brush, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i) {
        this.canvasDrawScope.mo1056drawPathGBMwjPU(path, brush, f, drawStyle, colorFilter, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* JADX INFO: renamed from: drawPath-LG529CI */
    public void mo1057drawPathLG529CI(Path path, long j, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i) {
        this.canvasDrawScope.mo1057drawPathLG529CI(path, j, f, drawStyle, colorFilter, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* JADX INFO: renamed from: drawRect-AsUm42w */
    public void mo1058drawRectAsUm42w(Brush brush, long j, long j2, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i) {
        this.canvasDrawScope.mo1058drawRectAsUm42w(brush, j, j2, f, drawStyle, colorFilter, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* JADX INFO: renamed from: drawRect-n-J9OG0 */
    public void mo1059drawRectnJ9OG0(long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i) {
        this.canvasDrawScope.mo1059drawRectnJ9OG0(j, j2, j3, f, drawStyle, colorFilter, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* JADX INFO: renamed from: drawRoundRect-ZuiqVtQ */
    public void mo1060drawRoundRectZuiqVtQ(Brush brush, long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i) {
        this.canvasDrawScope.mo1060drawRoundRectZuiqVtQ(brush, j, j2, j3, f, drawStyle, colorFilter, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* JADX INFO: renamed from: drawRoundRect-u-Aw5IA */
    public void mo1061drawRoundRectuAw5IA(long j, long j2, long j3, long j4, DrawStyle drawStyle, float f, ColorFilter colorFilter, int i) {
        this.canvasDrawScope.mo1061drawRoundRectuAw5IA(j, j2, j3, j4, drawStyle, f, colorFilter, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* JADX INFO: renamed from: getCenter-F1C5BW0 */
    public long mo1081getCenterF1C5BW0() {
        return this.canvasDrawScope.mo1081getCenterF1C5BW0();
    }

    @Override // androidx.compose.ui.unit.Density
    public float getDensity() {
        return this.canvasDrawScope.getDensity();
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    public DrawContext getDrawContext() {
        return this.canvasDrawScope.getDrawContext();
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public float getFontScale() {
        return this.canvasDrawScope.getFontScale();
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    public LayoutDirection getLayoutDirection() {
        return this.canvasDrawScope.getLayoutDirection();
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* JADX INFO: renamed from: getSize-NH-jbRc */
    public long mo1082getSizeNHjbRc() {
        return this.canvasDrawScope.mo1082getSizeNHjbRc();
    }

    public final void performDraw(DrawModifierNode drawModifierNode, Canvas canvas, GraphicsLayer graphicsLayer) {
        NodeCoordinator nodeCoordinatorM1308requireCoordinator64DMado = DelegatableNodeKt.m1308requireCoordinator64DMado(drawModifierNode, NodeKind.m1404constructorimpl(4));
        nodeCoordinatorM1308requireCoordinator64DMado.getLayoutNode().getMDrawScope$ui_release().m1336drawDirecteZhPAX0$ui_release(canvas, IntSizeKt.m1930toSizeozmzZPI(nodeCoordinatorM1308requireCoordinator64DMado.mo1278getSizeYbymL2g()), nodeCoordinatorM1308requireCoordinator64DMado, drawModifierNode, graphicsLayer);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* JADX INFO: renamed from: record-JVtK1S4 */
    public void mo1083recordJVtK1S4(GraphicsLayer graphicsLayer, long j, final Function1 function1) {
        final DrawModifierNode drawModifierNode = this.drawNode;
        graphicsLayer.m1100recordmLhObY(this, getLayoutDirection(), j, new Function1() { // from class: androidx.compose.ui.node.LayoutNodeDrawScope$record$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) throws Throwable {
                invoke((DrawScope) obj);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v0, types: [androidx.compose.ui.node.DrawModifierNode] */
            /* JADX WARN: Type inference failed for: r2v1 */
            /* JADX WARN: Type inference failed for: r2v2, types: [androidx.compose.ui.node.DrawModifierNode] */
            /* JADX WARN: Type inference failed for: r2v3 */
            /* JADX WARN: Type inference failed for: r2v4, types: [androidx.compose.ui.graphics.drawscope.DrawContext] */
            /* JADX WARN: Type inference failed for: r2v5 */
            public final void invoke(DrawScope drawScope) throws Throwable {
                LayoutNodeDrawScope layoutNodeDrawScope;
                Density density;
                LayoutDirection layoutDirection;
                Canvas canvas;
                long jMo1065getSizeNHjbRc;
                GraphicsLayer graphicsLayer2;
                Function1 function12;
                Density density2;
                LayoutDirection layoutDirection2;
                Canvas canvas2;
                long jMo1065getSizeNHjbRc2;
                GraphicsLayer graphicsLayer3;
                ?? drawContext = this.this$0.drawNode;
                this.this$0.drawNode = drawModifierNode;
                try {
                    layoutNodeDrawScope = this.this$0;
                    density = drawScope.getDrawContext().getDensity();
                    layoutDirection = drawScope.getDrawContext().getLayoutDirection();
                    canvas = drawScope.getDrawContext().getCanvas();
                    jMo1065getSizeNHjbRc = drawScope.getDrawContext().mo1065getSizeNHjbRc();
                    graphicsLayer2 = drawScope.getDrawContext().getGraphicsLayer();
                    function12 = function1;
                    density2 = layoutNodeDrawScope.getDrawContext().getDensity();
                    layoutDirection2 = layoutNodeDrawScope.getDrawContext().getLayoutDirection();
                    canvas2 = layoutNodeDrawScope.getDrawContext().getCanvas();
                    jMo1065getSizeNHjbRc2 = layoutNodeDrawScope.getDrawContext().mo1065getSizeNHjbRc();
                    graphicsLayer3 = layoutNodeDrawScope.getDrawContext().getGraphicsLayer();
                } catch (Throwable th) {
                    th = th;
                    this.this$0.drawNode = drawContext;
                    throw th;
                }
                try {
                    drawContext = layoutNodeDrawScope.getDrawContext();
                    drawContext.setDensity(density);
                    drawContext.setLayoutDirection(layoutDirection);
                    drawContext.setCanvas(canvas);
                    drawContext.mo1066setSizeuvyYCjk(jMo1065getSizeNHjbRc);
                    drawContext.setGraphicsLayer(graphicsLayer2);
                    canvas.save();
                    try {
                        function12.invoke(layoutNodeDrawScope);
                        canvas.restore();
                        DrawContext drawContext2 = layoutNodeDrawScope.getDrawContext();
                        drawContext2.setDensity(density2);
                        drawContext2.setLayoutDirection(layoutDirection2);
                        drawContext2.setCanvas(canvas2);
                        drawContext2.mo1066setSizeuvyYCjk(jMo1065getSizeNHjbRc2);
                        drawContext2.setGraphicsLayer(graphicsLayer3);
                        this.this$0.drawNode = drawContext;
                    } finally {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    drawContext = drawContext;
                    this.this$0.drawNode = drawContext;
                    throw th;
                }
            }
        });
    }

    @Override // androidx.compose.ui.unit.Density
    /* JADX INFO: renamed from: roundToPx-0680j_4 */
    public int mo141roundToPx0680j_4(float f) {
        return this.canvasDrawScope.mo141roundToPx0680j_4(f);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* JADX INFO: renamed from: toDp-GaN1DYA */
    public float mo142toDpGaN1DYA(long j) {
        return this.canvasDrawScope.mo142toDpGaN1DYA(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* JADX INFO: renamed from: toDp-u2uoSUM */
    public float mo143toDpu2uoSUM(float f) {
        return this.canvasDrawScope.mo143toDpu2uoSUM(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* JADX INFO: renamed from: toDp-u2uoSUM */
    public float mo144toDpu2uoSUM(int i) {
        return this.canvasDrawScope.mo144toDpu2uoSUM(i);
    }

    @Override // androidx.compose.ui.unit.Density
    /* JADX INFO: renamed from: toPx--R2X_6o */
    public float mo145toPxR2X_6o(long j) {
        return this.canvasDrawScope.mo145toPxR2X_6o(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* JADX INFO: renamed from: toPx-0680j_4 */
    public float mo146toPx0680j_4(float f) {
        return this.canvasDrawScope.mo146toPx0680j_4(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* JADX INFO: renamed from: toSize-XkaWNTQ */
    public long mo147toSizeXkaWNTQ(long j) {
        return this.canvasDrawScope.mo147toSizeXkaWNTQ(j);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* JADX INFO: renamed from: toSp-0xMU5do */
    public long mo148toSp0xMU5do(float f) {
        return this.canvasDrawScope.mo148toSp0xMU5do(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* JADX INFO: renamed from: toSp-kPz2Gy4 */
    public long mo149toSpkPz2Gy4(float f) {
        return this.canvasDrawScope.mo149toSpkPz2Gy4(f);
    }
}
