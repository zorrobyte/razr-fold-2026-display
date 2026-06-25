package androidx.appcompat.widget;

import android.graphics.Canvas;
import c.AbstractC0123a;

/* JADX INFO: loaded from: classes.dex */
public final class H extends AbstractC0123a {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f994b;

    @Override // c.AbstractC0123a, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (this.f994b) {
            super.draw(canvas);
        }
    }

    @Override // c.AbstractC0123a, android.graphics.drawable.Drawable
    public final void setHotspot(float f2, float f3) {
        if (this.f994b) {
            super.setHotspot(f2, f3);
        }
    }

    @Override // c.AbstractC0123a, android.graphics.drawable.Drawable
    public final void setHotspotBounds(int i2, int i3, int i4, int i5) {
        if (this.f994b) {
            super.setHotspotBounds(i2, i3, i4, i5);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setState(int[] iArr) {
        if (this.f994b) {
            return this.f2015a.setState(iArr);
        }
        return false;
    }

    @Override // c.AbstractC0123a, android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z2, boolean z3) {
        if (this.f994b) {
            return super.setVisible(z2, z3);
        }
        return false;
    }
}
