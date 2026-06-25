package androidx.window.layout;

import android.graphics.Rect;
import androidx.window.core.Bounds;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WindowMetrics.kt */
/* JADX INFO: loaded from: classes.dex */
public final class WindowMetrics {
    private final Bounds _bounds;
    private final float density;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WindowMetrics(Rect rect, float f) {
        this(new Bounds(rect), f);
        rect.getClass();
    }

    public WindowMetrics(Bounds bounds, float f) {
        bounds.getClass();
        this._bounds = bounds;
        this.density = f;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(WindowMetrics.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        obj.getClass();
        WindowMetrics windowMetrics = (WindowMetrics) obj;
        return Intrinsics.areEqual(this._bounds, windowMetrics._bounds) && this.density == windowMetrics.density;
    }

    public final Rect getBounds() {
        return this._bounds.toRect();
    }

    public int hashCode() {
        return (this._bounds.hashCode() * 31) + Float.hashCode(this.density);
    }

    public String toString() {
        return "WindowMetrics(_bounds=" + this._bounds + ", density=" + this.density + ')';
    }
}
