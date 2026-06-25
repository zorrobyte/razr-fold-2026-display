package androidx.compose.ui.graphics.layer;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.RectF;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.AndroidPaint_androidKt;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Paint;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.RenderEffect;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawContext;
import androidx.compose.ui.graphics.drawscope.DrawContextKt;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AndroidGraphicsLayer.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class GraphicsLayer {
    public static final Companion Companion = new Companion(null);
    private static final LayerSnapshotImpl SnapshotImpl;
    private Outline androidOutline;
    private final ChildLayerDependenciesTracker childDependenciesTracker;
    private boolean clip;
    private final GraphicsLayerImpl impl;
    private androidx.compose.ui.graphics.Outline internalOutline;
    private boolean isReleased;
    private Path outlinePath;
    private int parentLayerUsages;
    private RectF pathBounds;
    private long pivotOffset;
    private Path roundRectClipPath;
    private float roundRectCornerRadius;
    private long roundRectOutlineSize;
    private long roundRectOutlineTopLeft;
    private long size;
    private CanvasDrawScope softwareDrawScope;
    private Paint softwareLayerPaint;
    private long topLeft;
    private boolean usePathForClip;
    private Density density = DrawContextKt.getDefaultDensity();
    private LayoutDirection layoutDirection = LayoutDirection.Ltr;
    private Function1 drawBlock = new Function1() { // from class: androidx.compose.ui.graphics.layer.GraphicsLayer$drawBlock$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((DrawScope) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(DrawScope drawScope) {
        }
    };
    private final Function1 clipDrawBlock = new Function1() { // from class: androidx.compose.ui.graphics.layer.GraphicsLayer$clipDrawBlock$1
        {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((DrawScope) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(DrawScope drawScope) {
            Path path = this.this$0.outlinePath;
            if (!this.this$0.usePathForClip || !this.this$0.getClip() || path == null) {
                this.this$0.drawWithChildTracking(drawScope);
                return;
            }
            GraphicsLayer graphicsLayer = this.this$0;
            int iM271getIntersectrtfAjoo = ClipOp.Companion.m271getIntersectrtfAjoo();
            DrawContext drawContext = drawScope.getDrawContext();
            long jMo384getSizeNHjbRc = drawContext.mo384getSizeNHjbRc();
            drawContext.getCanvas().save();
            try {
                drawContext.getTransform().mo386clipPathmtrdDE(path, iM271getIntersectrtfAjoo);
                graphicsLayer.drawWithChildTracking(drawScope);
            } finally {
                drawContext.getCanvas().restore();
                drawContext.mo385setSizeuvyYCjk(jMo384getSizeNHjbRc);
            }
        }
    };
    private boolean outlineDirty = true;

    /* JADX INFO: compiled from: AndroidGraphicsLayer.android.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        SnapshotImpl = LayerManager.Companion.isRobolectric() ? LayerSnapshotV21.INSTANCE : LayerSnapshotV28.INSTANCE;
    }

    public GraphicsLayer(GraphicsLayerImpl graphicsLayerImpl, LayerManager layerManager) {
        this.impl = graphicsLayerImpl;
        Offset.Companion companion = Offset.Companion;
        this.roundRectOutlineTopLeft = companion.m195getZeroF1C5BW0();
        this.roundRectOutlineSize = Size.Companion.m210getUnspecifiedNHjbRc();
        this.childDependenciesTracker = new ChildLayerDependenciesTracker();
        graphicsLayerImpl.setClip(false);
        this.topLeft = IntOffset.Companion.m1002getZeronOccac();
        this.size = IntSize.Companion.m1014getZeroYbymL2g();
        this.pivotOffset = companion.m194getUnspecifiedF1C5BW0();
    }

    private final void addSubLayer(GraphicsLayer graphicsLayer) {
        if (this.childDependenciesTracker.onDependencyAdded(graphicsLayer)) {
            graphicsLayer.onAddedToParentLayer();
        }
    }

    private final void configureOutlineAndClip() {
        if (this.outlineDirty) {
            Outline outline = null;
            if (this.clip || getShadowElevation() > 0.0f) {
                Path path = this.outlinePath;
                if (path != null) {
                    RectF rectFObtainPathBounds = obtainPathBounds();
                    if (!(path instanceof AndroidPath)) {
                        throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
                    }
                    ((AndroidPath) path).getInternalPath().computeBounds(rectFObtainPathBounds, false);
                    Outline outlineUpdatePathOutline = updatePathOutline(path);
                    if (outlineUpdatePathOutline != null) {
                        outlineUpdatePathOutline.setAlpha(getAlpha());
                        outline = outlineUpdatePathOutline;
                    }
                    this.impl.mo419setOutlineO0kMr_c(outline, IntSize.m1008constructorimpl((4294967295L & ((long) Math.round(rectFObtainPathBounds.height()))) | (((long) Math.round(rectFObtainPathBounds.width())) << 32)));
                    if (this.usePathForClip && this.clip) {
                        this.impl.setClip(false);
                        this.impl.discardDisplayList();
                    } else {
                        this.impl.setClip(this.clip);
                    }
                } else {
                    this.impl.setClip(this.clip);
                    Size.Companion.m211getZeroNHjbRc();
                    Outline outlineObtainAndroidOutline = obtainAndroidOutline();
                    long jM1016toSizeozmzZPI = IntSizeKt.m1016toSizeozmzZPI(this.size);
                    long j = this.roundRectOutlineTopLeft;
                    long j2 = this.roundRectOutlineSize;
                    long j3 = j2 == 9205357640488583168L ? jM1016toSizeozmzZPI : j2;
                    int i = (int) (j >> 32);
                    int i2 = (int) (j & 4294967295L);
                    outlineObtainAndroidOutline.setRoundRect(Math.round(Float.intBitsToFloat(i)), Math.round(Float.intBitsToFloat(i2)), Math.round(Float.intBitsToFloat(i) + Float.intBitsToFloat((int) (j3 >> 32))), Math.round(Float.intBitsToFloat(i2) + Float.intBitsToFloat((int) (j3 & 4294967295L))), this.roundRectCornerRadius);
                    outlineObtainAndroidOutline.setAlpha(getAlpha());
                    this.impl.mo419setOutlineO0kMr_c(outlineObtainAndroidOutline, IntSizeKt.m1015roundToIntSizeuvyYCjk(j3));
                }
            } else {
                this.impl.setClip(false);
                this.impl.mo419setOutlineO0kMr_c(null, IntSize.Companion.m1014getZeroYbymL2g());
            }
        }
        this.outlineDirty = false;
    }

    private final void discardContentIfReleasedAndHaveNoParentLayerUsages() {
        if (this.isReleased && this.parentLayerUsages == 0) {
            discardDisplayList$ui_graphics_release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void drawWithChildTracking(androidx.compose.ui.graphics.drawscope.DrawScope r14) {
        /*
            r13 = this;
            androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker r0 = r13.childDependenciesTracker
            androidx.compose.ui.graphics.layer.GraphicsLayer r1 = androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker.access$getDependency$p(r0)
            androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker.access$setOldDependency$p(r0, r1)
            androidx.collection.MutableScatterSet r1 = androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker.access$getDependenciesSet$p(r0)
            if (r1 == 0) goto L28
            boolean r2 = r1.isNotEmpty()
            if (r2 == 0) goto L28
            androidx.collection.MutableScatterSet r2 = androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker.access$getOldDependenciesSet$p(r0)
            if (r2 != 0) goto L22
            androidx.collection.MutableScatterSet r2 = androidx.collection.ScatterSetKt.mutableScatterSetOf()
            androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker.access$setOldDependenciesSet$p(r0, r2)
        L22:
            r2.addAll(r1)
            r1.clear()
        L28:
            r1 = 1
            androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker.access$setTrackingInProgress$p(r0, r1)
            kotlin.jvm.functions.Function1 r13 = r13.drawBlock
            r13.invoke(r14)
            r13 = 0
            androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker.access$setTrackingInProgress$p(r0, r13)
            androidx.compose.ui.graphics.layer.GraphicsLayer r14 = androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker.access$getOldDependency$p(r0)
            if (r14 == 0) goto L3e
            r14.onRemovedFromParentLayer()
        L3e:
            androidx.collection.MutableScatterSet r14 = androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker.access$getOldDependenciesSet$p(r0)
            if (r14 == 0) goto L91
            boolean r0 = r14.isNotEmpty()
            if (r0 == 0) goto L91
            java.lang.Object[] r0 = r14.elements
            long[] r1 = r14.metadata
            int r2 = r1.length
            int r2 = r2 + (-2)
            if (r2 < 0) goto L8e
            r3 = r13
        L54:
            r4 = r1[r3]
            long r6 = ~r4
            r8 = 7
            long r6 = r6 << r8
            long r6 = r6 & r4
            r8 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r6 = r6 & r8
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 == 0) goto L89
            int r6 = r3 - r2
            int r6 = ~r6
            int r6 = r6 >>> 31
            r7 = 8
            int r6 = 8 - r6
            r8 = r13
        L6e:
            if (r8 >= r6) goto L87
            r9 = 255(0xff, double:1.26E-321)
            long r9 = r9 & r4
            r11 = 128(0x80, double:6.32E-322)
            int r9 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r9 >= 0) goto L83
            int r9 = r3 << 3
            int r9 = r9 + r8
            r9 = r0[r9]
            androidx.compose.ui.graphics.layer.GraphicsLayer r9 = (androidx.compose.ui.graphics.layer.GraphicsLayer) r9
            r9.onRemovedFromParentLayer()
        L83:
            long r4 = r4 >> r7
            int r8 = r8 + 1
            goto L6e
        L87:
            if (r6 != r7) goto L8e
        L89:
            if (r3 == r2) goto L8e
            int r3 = r3 + 1
            goto L54
        L8e:
            r14.clear()
        L91:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.layer.GraphicsLayer.drawWithChildTracking(androidx.compose.ui.graphics.drawscope.DrawScope):void");
    }

    private final Outline obtainAndroidOutline() {
        Outline outline = this.androidOutline;
        if (outline != null) {
            return outline;
        }
        Outline outline2 = new Outline();
        this.androidOutline = outline2;
        return outline2;
    }

    private final RectF obtainPathBounds() {
        RectF rectF = this.pathBounds;
        if (rectF != null) {
            return rectF;
        }
        RectF rectF2 = new RectF();
        this.pathBounds = rectF2;
        return rectF2;
    }

    private final void onAddedToParentLayer() {
        this.parentLayerUsages++;
    }

    private final void onRemovedFromParentLayer() {
        this.parentLayerUsages--;
        discardContentIfReleasedAndHaveNoParentLayerUsages();
    }

    private final void recordInternal() {
        this.impl.record(this.density, this.layoutDirection, this, this.clipDrawBlock);
    }

    private final void recreateDisplayListIfNeeded() {
        if (this.impl.getHasDisplayList()) {
            return;
        }
        try {
            recordInternal();
        } catch (Throwable unused) {
        }
    }

    private final void resetOutlineParams() {
        this.internalOutline = null;
        this.outlinePath = null;
        this.roundRectOutlineSize = Size.Companion.m210getUnspecifiedNHjbRc();
        this.roundRectOutlineTopLeft = Offset.Companion.m195getZeroF1C5BW0();
        this.roundRectCornerRadius = 0.0f;
        this.outlineDirty = true;
        this.usePathForClip = false;
    }

    /* JADX INFO: renamed from: setPosition-VbeCjmY, reason: not valid java name */
    private final void m397setPositionVbeCjmY(long j, long j2) {
        this.impl.mo421setPositionH0pRuoY(IntOffset.m997getXimpl(j), IntOffset.m998getYimpl(j), j2);
    }

    /* JADX INFO: renamed from: setSize-ozmzZPI, reason: not valid java name */
    private final void m398setSizeozmzZPI(long j) {
        if (IntSize.m1010equalsimpl0(this.size, j)) {
            return;
        }
        this.size = j;
        m397setPositionVbeCjmY(this.topLeft, j);
        if (this.roundRectOutlineSize == 9205357640488583168L) {
            this.outlineDirty = true;
            configureOutlineAndClip();
        }
    }

    private final void transformCanvas(Canvas canvas) {
        Canvas canvas2;
        float fM997getXimpl = IntOffset.m997getXimpl(this.topLeft);
        float fM998getYimpl = IntOffset.m998getYimpl(this.topLeft);
        float fM997getXimpl2 = IntOffset.m997getXimpl(this.topLeft) + ((int) (this.size >> 32));
        float fM998getYimpl2 = IntOffset.m998getYimpl(this.topLeft) + ((int) (this.size & 4294967295L));
        float alpha = getAlpha();
        getColorFilter();
        int iM399getBlendMode0nO6VwU = m399getBlendMode0nO6VwU();
        if (alpha < 1.0f || !BlendMode.m234equalsimpl0(iM399getBlendMode0nO6VwU, BlendMode.Companion.m262getSrcOver0nO6VwU()) || CompositingStrategy.m393equalsimpl0(m400getCompositingStrategyke2Ky5w(), CompositingStrategy.Companion.m396getOffscreenke2Ky5w())) {
            Paint Paint = this.softwareLayerPaint;
            if (Paint == null) {
                Paint = AndroidPaint_androidKt.Paint();
                this.softwareLayerPaint = Paint;
            }
            Paint.setAlpha(alpha);
            Paint.mo224setBlendModes9anfk8(iM399getBlendMode0nO6VwU);
            Paint.setColorFilter(null);
            canvas2 = canvas;
            canvas2.saveLayer(fM997getXimpl, fM998getYimpl, fM997getXimpl2, fM998getYimpl2, Paint.asFrameworkPaint());
        } else {
            canvas.save();
            canvas2 = canvas;
        }
        canvas2.translate(fM997getXimpl, fM998getYimpl);
        canvas2.concat(this.impl.calculateMatrix());
    }

    private final Outline updatePathOutline(Path path) {
        Outline outlineObtainAndroidOutline = obtainAndroidOutline();
        OutlineVerificationHelper.INSTANCE.setPath(outlineObtainAndroidOutline, path);
        this.usePathForClip = !outlineObtainAndroidOutline.canClip();
        this.outlinePath = path;
        return outlineObtainAndroidOutline;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void discardDisplayList$ui_graphics_release() {
        /*
            r15 = this;
            androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker r0 = r15.childDependenciesTracker
            androidx.compose.ui.graphics.layer.GraphicsLayer r1 = androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker.access$getDependency$p(r0)
            if (r1 == 0) goto Lf
            r1.onRemovedFromParentLayer()
            r1 = 0
            androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker.access$setDependency$p(r0, r1)
        Lf:
            androidx.collection.MutableScatterSet r0 = androidx.compose.ui.graphics.layer.ChildLayerDependenciesTracker.access$getDependenciesSet$p(r0)
            if (r0 == 0) goto L5d
            java.lang.Object[] r1 = r0.elements
            long[] r2 = r0.metadata
            int r3 = r2.length
            int r3 = r3 + (-2)
            if (r3 < 0) goto L5a
            r4 = 0
            r5 = r4
        L20:
            r6 = r2[r5]
            long r8 = ~r6
            r10 = 7
            long r8 = r8 << r10
            long r8 = r8 & r6
            r10 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r8 = r8 & r10
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 == 0) goto L55
            int r8 = r5 - r3
            int r8 = ~r8
            int r8 = r8 >>> 31
            r9 = 8
            int r8 = 8 - r8
            r10 = r4
        L3a:
            if (r10 >= r8) goto L53
            r11 = 255(0xff, double:1.26E-321)
            long r11 = r11 & r6
            r13 = 128(0x80, double:6.32E-322)
            int r11 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r11 >= 0) goto L4f
            int r11 = r5 << 3
            int r11 = r11 + r10
            r11 = r1[r11]
            androidx.compose.ui.graphics.layer.GraphicsLayer r11 = (androidx.compose.ui.graphics.layer.GraphicsLayer) r11
            r11.onRemovedFromParentLayer()
        L4f:
            long r6 = r6 >> r9
            int r10 = r10 + 1
            goto L3a
        L53:
            if (r8 != r9) goto L5a
        L55:
            if (r5 == r3) goto L5a
            int r5 = r5 + 1
            goto L20
        L5a:
            r0.clear()
        L5d:
            androidx.compose.ui.graphics.layer.GraphicsLayerImpl r15 = r15.impl
            r15.discardDisplayList()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.layer.GraphicsLayer.discardDisplayList$ui_graphics_release():void");
    }

    public final void draw$ui_graphics_release(androidx.compose.ui.graphics.Canvas canvas, GraphicsLayer graphicsLayer) {
        boolean z;
        boolean z2;
        if (this.isReleased) {
            return;
        }
        configureOutlineAndClip();
        recreateDisplayListIfNeeded();
        boolean z3 = getShadowElevation() > 0.0f;
        if (z3) {
            canvas.enableZ();
        }
        Canvas nativeCanvas = AndroidCanvas_androidKt.getNativeCanvas(canvas);
        boolean zIsHardwareAccelerated = nativeCanvas.isHardwareAccelerated();
        if (!zIsHardwareAccelerated) {
            transformCanvas(nativeCanvas);
        }
        boolean z4 = !zIsHardwareAccelerated && this.clip;
        if (z4) {
            canvas.save();
            androidx.compose.ui.graphics.Outline outline = getOutline();
            if (outline instanceof Outline.Rectangle) {
                androidx.compose.ui.graphics.Canvas.m266clipRectmtrdDE$default(canvas, outline.getBounds(), 0, 2, null);
            } else if (outline instanceof Outline.Rounded) {
                Path Path = this.roundRectClipPath;
                if (Path != null) {
                    Path.rewind();
                } else {
                    Path = AndroidPath_androidKt.Path();
                    this.roundRectClipPath = Path;
                }
                Path.addRoundRect$default(Path, ((Outline.Rounded) outline).getRoundRect(), null, 2, null);
                androidx.compose.ui.graphics.Canvas.m264clipPathmtrdDE$default(canvas, Path, 0, 2, null);
            } else if (outline instanceof Outline.Generic) {
                androidx.compose.ui.graphics.Canvas.m264clipPathmtrdDE$default(canvas, ((Outline.Generic) outline).getPath(), 0, 2, null);
            }
        }
        if (graphicsLayer != null) {
            graphicsLayer.addSubLayer(this);
        }
        if (AndroidCanvas_androidKt.getNativeCanvas(canvas).isHardwareAccelerated() || this.impl.getSupportsSoftwareRendering()) {
            z = z3;
            z2 = z4;
            this.impl.draw(canvas);
        } else {
            CanvasDrawScope canvasDrawScope = this.softwareDrawScope;
            if (canvasDrawScope == null) {
                canvasDrawScope = new CanvasDrawScope();
                this.softwareDrawScope = canvasDrawScope;
            }
            Density density = this.density;
            LayoutDirection layoutDirection = this.layoutDirection;
            long jM1016toSizeozmzZPI = IntSizeKt.m1016toSizeozmzZPI(this.size);
            Density density2 = canvasDrawScope.getDrawContext().getDensity();
            LayoutDirection layoutDirection2 = canvasDrawScope.getDrawContext().getLayoutDirection();
            androidx.compose.ui.graphics.Canvas canvas2 = canvasDrawScope.getDrawContext().getCanvas();
            long jMo384getSizeNHjbRc = canvasDrawScope.getDrawContext().mo384getSizeNHjbRc();
            z = z3;
            GraphicsLayer graphicsLayer2 = canvasDrawScope.getDrawContext().getGraphicsLayer();
            z2 = z4;
            DrawContext drawContext = canvasDrawScope.getDrawContext();
            drawContext.setDensity(density);
            drawContext.setLayoutDirection(layoutDirection);
            drawContext.setCanvas(canvas);
            drawContext.mo385setSizeuvyYCjk(jM1016toSizeozmzZPI);
            drawContext.setGraphicsLayer(this);
            canvas.save();
            try {
                drawWithChildTracking(canvasDrawScope);
            } finally {
                canvas.restore();
                DrawContext drawContext2 = canvasDrawScope.getDrawContext();
                drawContext2.setDensity(density2);
                drawContext2.setLayoutDirection(layoutDirection2);
                drawContext2.setCanvas(canvas2);
                drawContext2.mo385setSizeuvyYCjk(jMo384getSizeNHjbRc);
                drawContext2.setGraphicsLayer(graphicsLayer2);
            }
        }
        if (z2) {
            canvas.restore();
        }
        if (z) {
            canvas.disableZ();
        }
        if (zIsHardwareAccelerated) {
            return;
        }
        nativeCanvas.restore();
    }

    public final float getAlpha() {
        return this.impl.getAlpha();
    }

    /* JADX INFO: renamed from: getBlendMode-0nO6VwU, reason: not valid java name */
    public final int m399getBlendMode0nO6VwU() {
        return this.impl.mo413getBlendMode0nO6VwU();
    }

    public final boolean getClip() {
        return this.clip;
    }

    public final ColorFilter getColorFilter() {
        this.impl.getColorFilter();
        return null;
    }

    /* JADX INFO: renamed from: getCompositingStrategy-ke2Ky5w, reason: not valid java name */
    public final int m400getCompositingStrategyke2Ky5w() {
        return this.impl.mo414getCompositingStrategyke2Ky5w();
    }

    public final androidx.compose.ui.graphics.Outline getOutline() {
        androidx.compose.ui.graphics.Outline rectangle;
        androidx.compose.ui.graphics.Outline outline = this.internalOutline;
        Path path = this.outlinePath;
        if (outline != null) {
            return outline;
        }
        if (path != null) {
            Outline.Generic generic = new Outline.Generic(path);
            this.internalOutline = generic;
            return generic;
        }
        long jM1016toSizeozmzZPI = IntSizeKt.m1016toSizeozmzZPI(this.size);
        long j = this.roundRectOutlineTopLeft;
        long j2 = this.roundRectOutlineSize;
        if (j2 != 9205357640488583168L) {
            jM1016toSizeozmzZPI = j2;
        }
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        float fIntBitsToFloat3 = Float.intBitsToFloat((int) (jM1016toSizeozmzZPI >> 32)) + fIntBitsToFloat;
        float fIntBitsToFloat4 = fIntBitsToFloat2 + Float.intBitsToFloat((int) (jM1016toSizeozmzZPI & 4294967295L));
        float f = this.roundRectCornerRadius;
        if (f > 0.0f) {
            rectangle = new Outline.Rounded(RoundRectKt.m205RoundRectgG7oq9Y(fIntBitsToFloat, fIntBitsToFloat2, fIntBitsToFloat3, fIntBitsToFloat4, CornerRadius.m175constructorimpl((((long) Float.floatToRawIntBits(f)) << 32) | (4294967295L & ((long) Float.floatToRawIntBits(f))))));
        } else {
            rectangle = new Outline.Rectangle(new Rect(fIntBitsToFloat, fIntBitsToFloat2, fIntBitsToFloat3, fIntBitsToFloat4));
        }
        this.internalOutline = rectangle;
        return rectangle;
    }

    /* JADX INFO: renamed from: getPivotOffset-F1C5BW0, reason: not valid java name */
    public final long m401getPivotOffsetF1C5BW0() {
        return this.pivotOffset;
    }

    public final float getRotationX() {
        return this.impl.getRotationX();
    }

    public final float getRotationY() {
        return this.impl.getRotationY();
    }

    public final float getRotationZ() {
        return this.impl.getRotationZ();
    }

    public final float getScaleX() {
        return this.impl.getScaleX();
    }

    public final float getScaleY() {
        return this.impl.getScaleY();
    }

    public final float getShadowElevation() {
        return this.impl.getShadowElevation();
    }

    /* JADX INFO: renamed from: getSize-YbymL2g, reason: not valid java name */
    public final long m402getSizeYbymL2g() {
        return this.size;
    }

    public final float getTranslationX() {
        return this.impl.getTranslationX();
    }

    public final float getTranslationY() {
        return this.impl.getTranslationY();
    }

    public final boolean isReleased() {
        return this.isReleased;
    }

    /* JADX INFO: renamed from: record-mL-hObY, reason: not valid java name */
    public final void m403recordmLhObY(Density density, LayoutDirection layoutDirection, long j, Function1 function1) {
        m398setSizeozmzZPI(j);
        this.density = density;
        this.layoutDirection = layoutDirection;
        this.drawBlock = function1;
        this.impl.setInvalidated(true);
        recordInternal();
    }

    public final void release$ui_graphics_release() {
        if (this.isReleased) {
            return;
        }
        this.isReleased = true;
        discardContentIfReleasedAndHaveNoParentLayerUsages();
    }

    public final void setAlpha(float f) {
        if (this.impl.getAlpha() == f) {
            return;
        }
        this.impl.setAlpha(f);
    }

    /* JADX INFO: renamed from: setAmbientShadowColor-8_81llA, reason: not valid java name */
    public final void m404setAmbientShadowColor8_81llA(long j) {
        if (Color.m278equalsimpl0(j, this.impl.mo412getAmbientShadowColor0d7_KjU())) {
            return;
        }
        this.impl.mo416setAmbientShadowColor8_81llA(j);
    }

    /* JADX INFO: renamed from: setBlendMode-s9anfk8, reason: not valid java name */
    public final void m405setBlendModes9anfk8(int i) {
        if (BlendMode.m234equalsimpl0(this.impl.mo413getBlendMode0nO6VwU(), i)) {
            return;
        }
        this.impl.mo417setBlendModes9anfk8(i);
    }

    public final void setCameraDistance(float f) {
        if (this.impl.getCameraDistance() == f) {
            return;
        }
        this.impl.setCameraDistance(f);
    }

    public final void setClip(boolean z) {
        if (this.clip != z) {
            this.clip = z;
            this.outlineDirty = true;
            configureOutlineAndClip();
        }
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.impl.getColorFilter();
        if (Intrinsics.areEqual(null, colorFilter)) {
            return;
        }
        this.impl.setColorFilter(colorFilter);
    }

    /* JADX INFO: renamed from: setCompositingStrategy-Wpw9cng, reason: not valid java name */
    public final void m406setCompositingStrategyWpw9cng(int i) {
        if (CompositingStrategy.m393equalsimpl0(this.impl.mo414getCompositingStrategyke2Ky5w(), i)) {
            return;
        }
        this.impl.mo418setCompositingStrategyWpw9cng(i);
    }

    public final void setPathOutline(Path path) {
        resetOutlineParams();
        this.outlinePath = path;
        configureOutlineAndClip();
    }

    /* JADX INFO: renamed from: setPivotOffset-k-4lQ0M, reason: not valid java name */
    public final void m407setPivotOffsetk4lQ0M(long j) {
        if (Offset.m186equalsimpl0(this.pivotOffset, j)) {
            return;
        }
        this.pivotOffset = j;
        this.impl.mo420setPivotOffsetk4lQ0M(j);
    }

    /* JADX INFO: renamed from: setRectOutline-tz77jQw, reason: not valid java name */
    public final void m408setRectOutlinetz77jQw(long j, long j2) {
        m409setRoundRectOutlineTNW_H78(j, j2, 0.0f);
    }

    public final void setRenderEffect(RenderEffect renderEffect) {
        this.impl.getRenderEffect();
        if (Intrinsics.areEqual(null, renderEffect)) {
            return;
        }
        this.impl.setRenderEffect(renderEffect);
    }

    public final void setRotationX(float f) {
        if (this.impl.getRotationX() == f) {
            return;
        }
        this.impl.setRotationX(f);
    }

    public final void setRotationY(float f) {
        if (this.impl.getRotationY() == f) {
            return;
        }
        this.impl.setRotationY(f);
    }

    public final void setRotationZ(float f) {
        if (this.impl.getRotationZ() == f) {
            return;
        }
        this.impl.setRotationZ(f);
    }

    /* JADX INFO: renamed from: setRoundRectOutline-TNW_H78, reason: not valid java name */
    public final void m409setRoundRectOutlineTNW_H78(long j, long j2, float f) {
        if (Offset.m186equalsimpl0(this.roundRectOutlineTopLeft, j) && Size.m207equalsimpl0(this.roundRectOutlineSize, j2) && this.roundRectCornerRadius == f && this.outlinePath == null) {
            return;
        }
        resetOutlineParams();
        this.roundRectOutlineTopLeft = j;
        this.roundRectOutlineSize = j2;
        this.roundRectCornerRadius = f;
        configureOutlineAndClip();
    }

    public final void setScaleX(float f) {
        if (this.impl.getScaleX() == f) {
            return;
        }
        this.impl.setScaleX(f);
    }

    public final void setScaleY(float f) {
        if (this.impl.getScaleY() == f) {
            return;
        }
        this.impl.setScaleY(f);
    }

    public final void setShadowElevation(float f) {
        if (this.impl.getShadowElevation() == f) {
            return;
        }
        this.impl.setShadowElevation(f);
        this.outlineDirty = true;
        configureOutlineAndClip();
    }

    /* JADX INFO: renamed from: setSpotShadowColor-8_81llA, reason: not valid java name */
    public final void m410setSpotShadowColor8_81llA(long j) {
        if (Color.m278equalsimpl0(j, this.impl.mo415getSpotShadowColor0d7_KjU())) {
            return;
        }
        this.impl.mo422setSpotShadowColor8_81llA(j);
    }

    /* JADX INFO: renamed from: setTopLeft--gyyYBs, reason: not valid java name */
    public final void m411setTopLeftgyyYBs(long j) {
        if (IntOffset.m996equalsimpl0(this.topLeft, j)) {
            return;
        }
        this.topLeft = j;
        m397setPositionVbeCjmY(j, this.size);
    }

    public final void setTranslationX(float f) {
        if (this.impl.getTranslationX() == f) {
            return;
        }
        this.impl.setTranslationX(f);
    }

    public final void setTranslationY(float f) {
        if (this.impl.getTranslationY() == f) {
            return;
        }
        this.impl.setTranslationY(f);
    }
}
