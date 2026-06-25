package androidx.window.core;

import android.graphics.Rect;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Bounds.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Bounds {
    public static final Companion Companion = new Companion(null);
    private static final Bounds EMPTY_BOUNDS = new Bounds(0, 0, 0, 0);
    private final int bottom;
    private final int left;
    private final int right;
    private final int top;

    /* JADX INFO: compiled from: Bounds.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Bounds getEMPTY_BOUNDS() {
            return Bounds.EMPTY_BOUNDS;
        }
    }

    public Bounds(int i, int i2, int i3, int i4) {
        this.left = i;
        this.top = i2;
        this.right = i3;
        this.bottom = i4;
        if (i > i3) {
            throw new IllegalArgumentException(("Left must be less than or equal to right, left: " + i + ", right: " + i3).toString());
        }
        if (i2 <= i4) {
            return;
        }
        throw new IllegalArgumentException(("top must be less than or equal to bottom, top: " + i2 + ", bottom: " + i4).toString());
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Bounds(Rect rect) {
        this(rect.left, rect.top, rect.right, rect.bottom);
        rect.getClass();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(Bounds.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        obj.getClass();
        Bounds bounds = (Bounds) obj;
        return this.left == bounds.left && this.top == bounds.top && this.right == bounds.right && this.bottom == bounds.bottom;
    }

    public final int getBottom() {
        return this.bottom;
    }

    public final int getHeight() {
        return this.bottom - this.top;
    }

    public final int getLeft() {
        return this.left;
    }

    public final int getRight() {
        return this.right;
    }

    public final int getTop() {
        return this.top;
    }

    public final int getWidth() {
        return this.right - this.left;
    }

    public int hashCode() {
        return (((((this.left * 31) + this.top) * 31) + this.right) * 31) + this.bottom;
    }

    public final boolean isZero() {
        return getHeight() == 0 && getWidth() == 0;
    }

    public final Rect toRect() {
        return new Rect(this.left, this.top, this.right, this.bottom);
    }

    public String toString() {
        return Bounds.class.getSimpleName() + " { [" + this.left + ',' + this.top + ',' + this.right + ',' + this.bottom + "] }";
    }
}
