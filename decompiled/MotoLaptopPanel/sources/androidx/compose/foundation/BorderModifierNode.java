package androidx.compose.foundation;

import androidx.compose.ui.draw.CacheDrawModifierNode;
import androidx.compose.ui.draw.CacheDrawScope;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.draw.DrawResult;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.RoundRectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawContext;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.unit.Dp;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Border.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BorderModifierNode extends DelegatingNode {
    private BorderCache borderCache;
    private Brush brush;
    private final CacheDrawModifierNode drawWithCacheModifierNode;
    private Shape shape;
    private float width;

    private BorderModifierNode(float f, Brush brush, Shape shape) {
        this.width = f;
        this.brush = brush;
        this.shape = shape;
        this.drawWithCacheModifierNode = (CacheDrawModifierNode) delegate(DrawModifierKt.CacheDrawModifierNode(new Function1() { // from class: androidx.compose.foundation.BorderModifierNode$drawWithCacheModifierNode$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final DrawResult invoke(CacheDrawScope cacheDrawScope) {
                if (cacheDrawScope.mo146toPx0680j_4(this.this$0.m93getWidthD9Ej5fM()) < 0.0f || Size.m787getMinDimensionimpl(cacheDrawScope.m678getSizeNHjbRc()) <= 0.0f) {
                    return BorderKt.drawContentWithoutBorder(cacheDrawScope);
                }
                float f2 = 2;
                float fMin = Math.min(Dp.m1879equalsimpl0(this.this$0.m93getWidthD9Ej5fM(), Dp.Companion.m1884getHairlineD9Ej5fM()) ? 1.0f : (float) Math.ceil(cacheDrawScope.mo146toPx0680j_4(this.this$0.m93getWidthD9Ej5fM())), (float) Math.ceil(Size.m787getMinDimensionimpl(cacheDrawScope.m678getSizeNHjbRc()) / f2));
                float f3 = fMin / f2;
                long jM752constructorimpl = Offset.m752constructorimpl((((long) Float.floatToRawIntBits(f3)) & 4294967295L) | (((long) Float.floatToRawIntBits(f3)) << 32));
                long jM783constructorimpl = Size.m783constructorimpl((((long) Float.floatToRawIntBits(Float.intBitsToFloat((int) (cacheDrawScope.m678getSizeNHjbRc() & 4294967295L)) - fMin)) & 4294967295L) | (((long) Float.floatToRawIntBits(Float.intBitsToFloat((int) (cacheDrawScope.m678getSizeNHjbRc() >> 32)) - fMin)) << 32));
                boolean z = f2 * fMin > Size.m787getMinDimensionimpl(cacheDrawScope.m678getSizeNHjbRc());
                Outline outlineMo189createOutlinePq9zytI = this.this$0.getShape().mo189createOutlinePq9zytI(cacheDrawScope.m678getSizeNHjbRc(), cacheDrawScope.getLayoutDirection(), cacheDrawScope);
                if (outlineMo189createOutlinePq9zytI instanceof Outline.Generic) {
                    BorderModifierNode borderModifierNode = this.this$0;
                    return borderModifierNode.drawGenericBorder(cacheDrawScope, borderModifierNode.getBrush(), (Outline.Generic) outlineMo189createOutlinePq9zytI, z, fMin);
                }
                if (outlineMo189createOutlinePq9zytI instanceof Outline.Rounded) {
                    BorderModifierNode borderModifierNode2 = this.this$0;
                    return borderModifierNode2.m92drawRoundRectBorderJqoCqck(cacheDrawScope, borderModifierNode2.getBrush(), (Outline.Rounded) outlineMo189createOutlinePq9zytI, jM752constructorimpl, jM783constructorimpl, z, fMin);
                }
                if (outlineMo189createOutlinePq9zytI instanceof Outline.Rectangle) {
                    return BorderKt.m89drawRectBorderNsqcLGU(cacheDrawScope, this.this$0.getBrush(), jM752constructorimpl, jM783constructorimpl, z, fMin);
                }
                throw new NoWhenBranchMatchedException();
            }
        }));
    }

    public /* synthetic */ BorderModifierNode(float f, Brush brush, Shape shape, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, brush, shape);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0154  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final androidx.compose.ui.draw.DrawResult drawGenericBorder(androidx.compose.ui.draw.CacheDrawScope r39, final androidx.compose.ui.graphics.Brush r40, final androidx.compose.ui.graphics.Outline.Generic r41, boolean r42, float r43) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 746
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.BorderModifierNode.drawGenericBorder(androidx.compose.ui.draw.CacheDrawScope, androidx.compose.ui.graphics.Brush, androidx.compose.ui.graphics.Outline$Generic, boolean, float):androidx.compose.ui.draw.DrawResult");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: drawRoundRectBorder-JqoCqck, reason: not valid java name */
    public final DrawResult m92drawRoundRectBorderJqoCqck(CacheDrawScope cacheDrawScope, final Brush brush, Outline.Rounded rounded, final long j, final long j2, final boolean z, final float f) {
        if (RoundRectKt.isSimple(rounded.getRoundRect())) {
            final long jM778getTopLeftCornerRadiuskKHJgLs = rounded.getRoundRect().m778getTopLeftCornerRadiuskKHJgLs();
            final float f2 = f / 2;
            final Stroke stroke = new Stroke(f, 0.0f, 0, 0, null, 30, null);
            return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    invoke((ContentDrawScope) obj);
                    return Unit.INSTANCE;
                }

                public final void invoke(ContentDrawScope contentDrawScope) {
                    contentDrawScope.drawContent();
                    if (z) {
                        DrawScope.m1078drawRoundRectZuiqVtQ$default(contentDrawScope, brush, 0L, 0L, jM778getTopLeftCornerRadiuskKHJgLs, 0.0f, null, null, 0, 246, null);
                        return;
                    }
                    float fIntBitsToFloat = Float.intBitsToFloat((int) (jM778getTopLeftCornerRadiuskKHJgLs >> 32));
                    float f3 = f2;
                    if (fIntBitsToFloat >= f3) {
                        DrawScope.m1078drawRoundRectZuiqVtQ$default(contentDrawScope, brush, j, j2, BorderKt.m90shrinkKibmq7A(jM778getTopLeftCornerRadiuskKHJgLs, f3), 0.0f, stroke, null, 0, 208, null);
                        return;
                    }
                    float f4 = f;
                    float fIntBitsToFloat2 = Float.intBitsToFloat((int) (contentDrawScope.mo1082getSizeNHjbRc() >> 32)) - f;
                    float fIntBitsToFloat3 = Float.intBitsToFloat((int) (contentDrawScope.mo1082getSizeNHjbRc() & 4294967295L)) - f;
                    int iM874getDifferencertfAjoo = ClipOp.Companion.m874getDifferencertfAjoo();
                    Brush brush2 = brush;
                    long j3 = jM778getTopLeftCornerRadiuskKHJgLs;
                    DrawContext drawContext = contentDrawScope.getDrawContext();
                    long jMo1065getSizeNHjbRc = drawContext.mo1065getSizeNHjbRc();
                    drawContext.getCanvas().save();
                    try {
                        drawContext.getTransform().mo1068clipRectN_I0leg(f4, f4, fIntBitsToFloat2, fIntBitsToFloat3, iM874getDifferencertfAjoo);
                        DrawScope.m1078drawRoundRectZuiqVtQ$default(contentDrawScope, brush2, 0L, 0L, j3, 0.0f, null, null, 0, 246, null);
                    } finally {
                        drawContext.getCanvas().restore();
                        drawContext.mo1066setSizeuvyYCjk(jMo1065getSizeNHjbRc);
                    }
                }
            });
        }
        if (this.borderCache == null) {
            this.borderCache = new BorderCache(null, null, null, null, 15, null);
        }
        BorderCache borderCache = this.borderCache;
        borderCache.getClass();
        final Path pathCreateRoundRectPath = BorderKt.createRoundRectPath(borderCache.obtainPath(), rounded.getRoundRect(), f, z);
        return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((ContentDrawScope) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(ContentDrawScope contentDrawScope) {
                contentDrawScope.drawContent();
                DrawScope.m1074drawPathGBMwjPU$default(contentDrawScope, pathCreateRoundRectPath, brush, 0.0f, null, null, 0, 60, null);
            }
        });
    }

    public final Brush getBrush() {
        return this.brush;
    }

    public final Shape getShape() {
        return this.shape;
    }

    /* JADX INFO: renamed from: getWidth-D9Ej5fM, reason: not valid java name */
    public final float m93getWidthD9Ej5fM() {
        return this.width;
    }

    public final void setBrush(Brush brush) {
        if (Intrinsics.areEqual(this.brush, brush)) {
            return;
        }
        this.brush = brush;
        this.drawWithCacheModifierNode.invalidateDrawCache();
    }

    public final void setShape(Shape shape) {
        if (Intrinsics.areEqual(this.shape, shape)) {
            return;
        }
        this.shape = shape;
        this.drawWithCacheModifierNode.invalidateDrawCache();
    }

    /* JADX INFO: renamed from: setWidth-0680j_4, reason: not valid java name */
    public final void m94setWidth0680j_4(float f) {
        if (Dp.m1879equalsimpl0(this.width, f)) {
            return;
        }
        this.width = f;
        this.drawWithCacheModifierNode.invalidateDrawCache();
    }
}
