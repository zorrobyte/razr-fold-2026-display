package androidx.window.embedding;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DividerAttributes.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DividerAttributes {
    public static final Companion Companion = new Companion(null);
    public static final DividerAttributes NO_DIVIDER = new DividerAttributes() { // from class: androidx.window.embedding.DividerAttributes$Companion$NO_DIVIDER$1
        public int hashCode() {
            return toString().hashCode();
        }

        @Override // androidx.window.embedding.DividerAttributes
        public String toString() {
            return "NO_DIVIDER";
        }
    };
    private final int color;
    private final int widthDp;

    /* JADX INFO: compiled from: DividerAttributes.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final int alpha(int i) {
            return i >>> 24;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void validateColor(int i) {
            if (alpha(i) == 255) {
                return;
            }
            throw new IllegalArgumentException(("Divider color must be opaque. Got: " + Integer.toHexString(i)).toString());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void validateWidth(int i) {
            if (i == -1 || i >= 0) {
                return;
            }
            throw new IllegalArgumentException(("widthDp must be greater than or equal to 0 or WIDTH_SYSTEM_DEFAULT. Got: " + i).toString());
        }
    }

    /* JADX INFO: compiled from: DividerAttributes.kt */
    public abstract class DragRange {
        public static final Companion Companion = new Companion(null);
        public static final DragRange DRAG_RANGE_SYSTEM_DEFAULT = new DragRange() { // from class: androidx.window.embedding.DividerAttributes$DragRange$Companion$DRAG_RANGE_SYSTEM_DEFAULT$1
            public String toString() {
                return "DRAG_RANGE_SYSTEM_DEFAULT";
            }
        };

        /* JADX INFO: compiled from: DividerAttributes.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        /* JADX INFO: compiled from: DividerAttributes.kt */
        public final class SplitRatioDragRange extends DragRange {
            private final float maxRatio;
            private final float minRatio;

            public SplitRatioDragRange(float f, float f2) {
                super(null);
                this.minRatio = f;
                this.maxRatio = f2;
                if (f <= 0.0d || f >= 1.0d) {
                    throw new IllegalArgumentException("minRatio must be in the interval (0.0, 1.0)");
                }
                if (f2 <= 0.0d || f2 >= 1.0d) {
                    throw new IllegalArgumentException("maxRatio must be in the interval (0.0, 1.0)");
                }
                if (f > f2) {
                    throw new IllegalArgumentException("minRatio must be less than or equal to maxRatio");
                }
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof SplitRatioDragRange)) {
                    return false;
                }
                SplitRatioDragRange splitRatioDragRange = (SplitRatioDragRange) obj;
                return this.minRatio == splitRatioDragRange.minRatio && this.maxRatio == splitRatioDragRange.maxRatio;
            }

            public int hashCode() {
                return (Float.hashCode(this.minRatio) * 31) + Float.hashCode(this.maxRatio);
            }

            public String toString() {
                return "SplitRatioDragRange[" + this.minRatio + ", " + this.maxRatio + ']';
            }
        }

        private DragRange() {
        }

        public /* synthetic */ DragRange(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: DividerAttributes.kt */
    public final class DraggableDividerAttributes extends DividerAttributes {
        private final DragRange dragRange;
        private final boolean isDraggingToFullscreenAllowed;

        /* JADX INFO: compiled from: DividerAttributes.kt */
        public final class Builder {
            private boolean isDraggingToFullscreenAllowed;
            private int widthDp = -1;
            private int color = -16777216;
            private DragRange dragRange = DragRange.DRAG_RANGE_SYSTEM_DEFAULT;

            public final DraggableDividerAttributes build() {
                return new DraggableDividerAttributes(this.widthDp, this.color, this.dragRange, this.isDraggingToFullscreenAllowed, null);
            }

            public final Builder setColor(int i) {
                DividerAttributes.Companion.validateColor(i);
                this.color = i;
                return this;
            }

            public final Builder setDragRange(DragRange dragRange) {
                dragRange.getClass();
                this.dragRange = dragRange;
                return this;
            }

            public final Builder setDraggingToFullscreenAllowed(boolean z) {
                this.isDraggingToFullscreenAllowed = z;
                return this;
            }

            public final Builder setWidthDp(int i) {
                DividerAttributes.Companion.validateWidth(i);
                this.widthDp = i;
                return this;
            }
        }

        private DraggableDividerAttributes(int i, int i2, DragRange dragRange, boolean z) {
            super(i, i2, null);
            this.dragRange = dragRange;
            this.isDraggingToFullscreenAllowed = z;
        }

        public /* synthetic */ DraggableDividerAttributes(int i, int i2, DragRange dragRange, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, dragRange, z);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DraggableDividerAttributes)) {
                return false;
            }
            DraggableDividerAttributes draggableDividerAttributes = (DraggableDividerAttributes) obj;
            return getWidthDp() == draggableDividerAttributes.getWidthDp() && getColor() == draggableDividerAttributes.getColor() && Intrinsics.areEqual(this.dragRange, draggableDividerAttributes.dragRange) && this.isDraggingToFullscreenAllowed == draggableDividerAttributes.isDraggingToFullscreenAllowed;
        }

        public int hashCode() {
            return (((((getWidthDp() * 31) + getColor()) * 31) + this.dragRange.hashCode()) * 31) + Boolean.hashCode(this.isDraggingToFullscreenAllowed);
        }

        @Override // androidx.window.embedding.DividerAttributes
        public String toString() {
            return DraggableDividerAttributes.class.getSimpleName() + "{width=" + getWidthDp() + ", color=" + getColor() + ", primaryContainerDragRange=" + this.dragRange + ", isDraggingToFullscreenAllowed=" + this.isDraggingToFullscreenAllowed + '}';
        }
    }

    /* JADX INFO: compiled from: DividerAttributes.kt */
    public final class FixedDividerAttributes extends DividerAttributes {

        /* JADX INFO: compiled from: DividerAttributes.kt */
        public final class Builder {
            private int widthDp = -1;
            private int color = -16777216;

            public final FixedDividerAttributes build() {
                return new FixedDividerAttributes(this.widthDp, this.color, null);
            }

            public final Builder setColor(int i) {
                DividerAttributes.Companion.validateColor(i);
                this.color = i;
                return this;
            }

            public final Builder setWidthDp(int i) {
                DividerAttributes.Companion.validateWidth(i);
                this.widthDp = i;
                return this;
            }
        }

        private FixedDividerAttributes(int i, int i2) {
            super(i, i2, null);
        }

        public /* synthetic */ FixedDividerAttributes(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FixedDividerAttributes)) {
                return false;
            }
            FixedDividerAttributes fixedDividerAttributes = (FixedDividerAttributes) obj;
            return getWidthDp() == fixedDividerAttributes.getWidthDp() && getColor() == fixedDividerAttributes.getColor();
        }

        public int hashCode() {
            return (getWidthDp() * 31) + getColor();
        }
    }

    private DividerAttributes(int i, int i2) {
        this.widthDp = i;
        this.color = i2;
    }

    /* synthetic */ DividerAttributes(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? -1 : i, (i3 & 2) != 0 ? -16777216 : i2);
    }

    public /* synthetic */ DividerAttributes(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2);
    }

    public final int getColor() {
        return this.color;
    }

    public final int getWidthDp() {
        return this.widthDp;
    }

    public String toString() {
        return DividerAttributes.class.getSimpleName() + "{width=" + this.widthDp + ", color=" + this.color + '}';
    }
}
