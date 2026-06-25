package androidx.window.embedding;

import android.graphics.Rect;
import androidx.window.core.Bounds;
import androidx.window.layout.FoldingFeature;
import androidx.window.layout.WindowLayoutInfo;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EmbeddingBounds.kt */
/* JADX INFO: loaded from: classes.dex */
public final class EmbeddingBounds {
    public static final EmbeddingBounds BOUNDS_EXPANDED;
    public static final EmbeddingBounds BOUNDS_HINGE_BOTTOM;
    public static final EmbeddingBounds BOUNDS_HINGE_LEFT;
    public static final EmbeddingBounds BOUNDS_HINGE_RIGHT;
    public static final EmbeddingBounds BOUNDS_HINGE_TOP;
    public static final Companion Companion = new Companion(null);
    private final Alignment alignment;
    private final Dimension height;
    private final Dimension width;

    /* JADX INFO: compiled from: EmbeddingBounds.kt */
    public final class Alignment {
        private final int value;
        public static final Companion Companion = new Companion(null);
        public static final Alignment ALIGN_LEFT = new Alignment(0);
        public static final Alignment ALIGN_TOP = new Alignment(1);
        public static final Alignment ALIGN_RIGHT = new Alignment(2);
        public static final Alignment ALIGN_BOTTOM = new Alignment(3);

        /* JADX INFO: compiled from: EmbeddingBounds.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public Alignment(int i) {
            this.value = i;
            if (i < 0 || i >= 4) {
                throw new IllegalArgumentException("Failed requirement.");
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Alignment) && this.value == ((Alignment) obj).value;
        }

        public final int getValue$window_release() {
            return this.value;
        }

        public int hashCode() {
            return this.value;
        }

        public String toString() {
            int i = this.value;
            if (i == 0) {
                return "left";
            }
            if (i == 1) {
                return "top";
            }
            if (i == 2) {
                return "right";
            }
            if (i == 3) {
                return "bottom";
            }
            return "unknown position:" + this.value;
        }
    }

    /* JADX INFO: compiled from: EmbeddingBounds.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Bounds offset(Bounds bounds, int i, int i2) {
            return new Bounds(bounds.getLeft() + i, bounds.getTop() + i2, bounds.getRight() + i, bounds.getBottom() + i2);
        }

        public final Bounds translateEmbeddingBounds$window_release(EmbeddingBounds embeddingBounds, Bounds bounds, WindowLayoutInfo windowLayoutInfo) {
            embeddingBounds.getClass();
            bounds.getClass();
            windowLayoutInfo.getClass();
            Dimension width = embeddingBounds.getWidth();
            Dimension dimension = Dimension.DIMENSION_EXPANDED;
            if (Intrinsics.areEqual(width, dimension) && Intrinsics.areEqual(embeddingBounds.getHeight(), dimension)) {
                return Bounds.Companion.getEMPTY_BOUNDS();
            }
            EmbeddingBounds embeddingBounds2 = new EmbeddingBounds(embeddingBounds.getAlignment(), embeddingBounds.shouldUseFallbackDimensionForWidth$window_release(windowLayoutInfo) ? Dimension.Companion.ratio(0.5f) : embeddingBounds.getWidth(), embeddingBounds.shouldUseFallbackDimensionForHeight$window_release(windowLayoutInfo) ? Dimension.Companion.ratio(0.5f) : embeddingBounds.getHeight());
            int widthInPixel$window_release = embeddingBounds2.getWidthInPixel$window_release(bounds, windowLayoutInfo);
            int heightInPixel$window_release = embeddingBounds2.getHeightInPixel$window_release(bounds, windowLayoutInfo);
            int width2 = bounds.getWidth();
            int height = bounds.getHeight();
            if (widthInPixel$window_release == width2 && heightInPixel$window_release == height) {
                return Bounds.Companion.getEMPTY_BOUNDS();
            }
            Bounds bounds2 = new Bounds(0, 0, widthInPixel$window_release, heightInPixel$window_release);
            Alignment alignment = embeddingBounds.getAlignment();
            if (Intrinsics.areEqual(alignment, Alignment.ALIGN_TOP)) {
                return EmbeddingBounds.Companion.offset(bounds2, (width2 - widthInPixel$window_release) / 2, 0);
            }
            if (Intrinsics.areEqual(alignment, Alignment.ALIGN_LEFT)) {
                return EmbeddingBounds.Companion.offset(bounds2, 0, (height - heightInPixel$window_release) / 2);
            }
            if (Intrinsics.areEqual(alignment, Alignment.ALIGN_BOTTOM)) {
                return EmbeddingBounds.Companion.offset(bounds2, (width2 - widthInPixel$window_release) / 2, height - heightInPixel$window_release);
            }
            if (Intrinsics.areEqual(alignment, Alignment.ALIGN_RIGHT)) {
                return EmbeddingBounds.Companion.offset(bounds2, width2 - widthInPixel$window_release, (height - heightInPixel$window_release) / 2);
            }
            throw new IllegalArgumentException("Unknown alignment: " + embeddingBounds.getAlignment());
        }

        public final Bounds translateEmbeddingBounds$window_release(EmbeddingBounds embeddingBounds, ParentContainerInfo parentContainerInfo) {
            embeddingBounds.getClass();
            parentContainerInfo.getClass();
            return translateEmbeddingBounds$window_release(embeddingBounds, parentContainerInfo.getWindowBounds(), parentContainerInfo.getWindowLayoutInfo());
        }
    }

    /* JADX INFO: compiled from: EmbeddingBounds.kt */
    public abstract class Dimension {
        public static final Companion Companion = new Companion(null);
        public static final Dimension DIMENSION_EXPANDED = new Ratio(1.0f);
        public static final Dimension DIMENSION_HINGE = new Dimension() { // from class: androidx.window.embedding.EmbeddingBounds$Dimension$Companion$DIMENSION_HINGE$1
        };
        private final String description;

        /* JADX INFO: compiled from: EmbeddingBounds.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final Dimension pixel(int i) {
                return new Pixel(i);
            }

            public final Dimension ratio(float f) {
                return new Ratio(f);
            }
        }

        /* JADX INFO: compiled from: EmbeddingBounds.kt */
        public final class Pixel extends Dimension {
            private final int value;

            public Pixel(int i) {
                super("dimension in pixel:" + i);
                this.value = i;
                if (i < 1) {
                    throw new IllegalArgumentException("Pixel value must be a positive integer.");
                }
            }

            public final int getValue$window_release() {
                return this.value;
            }
        }

        /* JADX INFO: compiled from: EmbeddingBounds.kt */
        public final class Ratio extends Dimension {
            private final float value;

            public Ratio(float f) {
                super("dimension in ratio:" + f);
                this.value = f;
                if (f <= 0.0d || f > 1.0d) {
                    throw new IllegalArgumentException("Ratio must be in range (0.0, 1.0]");
                }
            }

            public final int times$window_release(int i) {
                return (int) (this.value * i);
            }
        }

        public Dimension(String str) {
            str.getClass();
            this.description = str;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Dimension) {
                return Intrinsics.areEqual(this.description, ((Dimension) obj).description);
            }
            return false;
        }

        public int hashCode() {
            return this.description.hashCode();
        }

        public String toString() {
            return this.description;
        }
    }

    static {
        Alignment alignment = Alignment.ALIGN_TOP;
        Dimension dimension = Dimension.DIMENSION_EXPANDED;
        BOUNDS_EXPANDED = new EmbeddingBounds(alignment, dimension, dimension);
        Dimension dimension2 = Dimension.DIMENSION_HINGE;
        BOUNDS_HINGE_TOP = new EmbeddingBounds(alignment, dimension, dimension2);
        BOUNDS_HINGE_LEFT = new EmbeddingBounds(Alignment.ALIGN_LEFT, dimension2, dimension);
        BOUNDS_HINGE_BOTTOM = new EmbeddingBounds(Alignment.ALIGN_BOTTOM, dimension, dimension2);
        BOUNDS_HINGE_RIGHT = new EmbeddingBounds(Alignment.ALIGN_RIGHT, dimension2, dimension);
    }

    public EmbeddingBounds(Alignment alignment, Dimension dimension, Dimension dimension2) {
        alignment.getClass();
        dimension.getClass();
        dimension2.getClass();
        this.alignment = alignment;
        this.width = dimension;
        this.height = dimension2;
    }

    private final FoldingFeature getOnlyFoldingFeatureOrNull(WindowLayoutInfo windowLayoutInfo) {
        List displayFeatures = windowLayoutInfo.getDisplayFeatures();
        ArrayList arrayList = new ArrayList();
        for (Object obj : displayFeatures) {
            if (obj instanceof FoldingFeature) {
                arrayList.add(obj);
            }
        }
        if (arrayList.size() == 1) {
            return (FoldingFeature) arrayList.get(0);
        }
        return null;
    }

    private final boolean isHorizontal(WindowLayoutInfo windowLayoutInfo) {
        FoldingFeature onlyFoldingFeatureOrNull = getOnlyFoldingFeatureOrNull(windowLayoutInfo);
        if (onlyFoldingFeatureOrNull == null) {
            return false;
        }
        return Intrinsics.areEqual(onlyFoldingFeatureOrNull.getOrientation(), FoldingFeature.Orientation.HORIZONTAL);
    }

    private final boolean isVertical(WindowLayoutInfo windowLayoutInfo) {
        FoldingFeature onlyFoldingFeatureOrNull = getOnlyFoldingFeatureOrNull(windowLayoutInfo);
        if (onlyFoldingFeatureOrNull == null) {
            return false;
        }
        return Intrinsics.areEqual(onlyFoldingFeatureOrNull.getOrientation(), FoldingFeature.Orientation.VERTICAL);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EmbeddingBounds)) {
            return false;
        }
        EmbeddingBounds embeddingBounds = (EmbeddingBounds) obj;
        return Intrinsics.areEqual(this.alignment, embeddingBounds.alignment) && Intrinsics.areEqual(this.width, embeddingBounds.width) && Intrinsics.areEqual(this.height, embeddingBounds.height);
    }

    public final Alignment getAlignment() {
        return this.alignment;
    }

    public final Dimension getHeight() {
        return this.height;
    }

    public final int getHeightInPixel$window_release(Bounds bounds, WindowLayoutInfo windowLayoutInfo) {
        bounds.getClass();
        windowLayoutInfo.getClass();
        int height = bounds.getHeight();
        Dimension dimensionRatio = shouldUseFallbackDimensionForHeight$window_release(windowLayoutInfo) ? Dimension.Companion.ratio(0.5f) : this.height;
        if (dimensionRatio instanceof Dimension.Ratio) {
            return ((Dimension.Ratio) dimensionRatio).times$window_release(height);
        }
        if (dimensionRatio instanceof Dimension.Pixel) {
            return Math.min(height, ((Dimension.Pixel) dimensionRatio).getValue$window_release());
        }
        if (!Intrinsics.areEqual(dimensionRatio, Dimension.DIMENSION_HINGE)) {
            throw new IllegalArgumentException("Unhandled width dimension=" + this.width);
        }
        FoldingFeature onlyFoldingFeatureOrNull = getOnlyFoldingFeatureOrNull(windowLayoutInfo);
        onlyFoldingFeatureOrNull.getClass();
        Rect bounds2 = onlyFoldingFeatureOrNull.getBounds();
        Alignment alignment = this.alignment;
        if (Intrinsics.areEqual(alignment, Alignment.ALIGN_TOP)) {
            return bounds2.top - bounds.getTop();
        }
        if (Intrinsics.areEqual(alignment, Alignment.ALIGN_BOTTOM)) {
            return bounds.getBottom() - bounds2.bottom;
        }
        throw new IllegalStateException("Unhandled condition to get height in pixel! embeddingBounds=" + this + " taskBounds=" + bounds + " windowLayoutInfo=" + windowLayoutInfo);
    }

    public final Dimension getWidth() {
        return this.width;
    }

    public final int getWidthInPixel$window_release(Bounds bounds, WindowLayoutInfo windowLayoutInfo) {
        bounds.getClass();
        windowLayoutInfo.getClass();
        int width = bounds.getWidth();
        Dimension dimensionRatio = shouldUseFallbackDimensionForWidth$window_release(windowLayoutInfo) ? Dimension.Companion.ratio(0.5f) : this.width;
        if (dimensionRatio instanceof Dimension.Ratio) {
            return ((Dimension.Ratio) dimensionRatio).times$window_release(width);
        }
        if (dimensionRatio instanceof Dimension.Pixel) {
            return Math.min(width, ((Dimension.Pixel) dimensionRatio).getValue$window_release());
        }
        if (!Intrinsics.areEqual(dimensionRatio, Dimension.DIMENSION_HINGE)) {
            throw new IllegalArgumentException("Unhandled width dimension=" + this.width);
        }
        FoldingFeature onlyFoldingFeatureOrNull = getOnlyFoldingFeatureOrNull(windowLayoutInfo);
        onlyFoldingFeatureOrNull.getClass();
        Rect bounds2 = onlyFoldingFeatureOrNull.getBounds();
        Alignment alignment = this.alignment;
        if (Intrinsics.areEqual(alignment, Alignment.ALIGN_LEFT)) {
            return bounds2.left - bounds.getLeft();
        }
        if (Intrinsics.areEqual(alignment, Alignment.ALIGN_RIGHT)) {
            return bounds.getRight() - bounds2.right;
        }
        throw new IllegalStateException("Unhandled condition to get height in pixel! embeddingBounds=" + this + " taskBounds=" + bounds + " windowLayoutInfo=" + windowLayoutInfo);
    }

    public int hashCode() {
        return (((this.alignment.hashCode() * 31) + this.width.hashCode()) * 31) + this.height.hashCode();
    }

    public final boolean shouldUseFallbackDimensionForHeight$window_release(WindowLayoutInfo windowLayoutInfo) {
        windowLayoutInfo.getClass();
        if (Intrinsics.areEqual(this.height, Dimension.DIMENSION_HINGE)) {
            return !isHorizontal(windowLayoutInfo) || CollectionsKt.listOf((Object[]) new Alignment[]{Alignment.ALIGN_LEFT, Alignment.ALIGN_RIGHT}).contains(this.alignment);
        }
        return false;
    }

    public final boolean shouldUseFallbackDimensionForWidth$window_release(WindowLayoutInfo windowLayoutInfo) {
        windowLayoutInfo.getClass();
        if (Intrinsics.areEqual(this.width, Dimension.DIMENSION_HINGE)) {
            return !isVertical(windowLayoutInfo) || CollectionsKt.listOf((Object[]) new Alignment[]{Alignment.ALIGN_TOP, Alignment.ALIGN_BOTTOM}).contains(this.alignment);
        }
        return false;
    }

    public String toString() {
        return "Bounds:{alignment=" + this.alignment + ", width=" + this.width + ", height=" + this.height + '}';
    }
}
