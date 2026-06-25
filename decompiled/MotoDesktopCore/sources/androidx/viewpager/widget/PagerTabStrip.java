package androidx.viewpager.widget;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

/* JADX INFO: loaded from: classes.dex */
public class PagerTabStrip extends PagerTitleStrip {

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f1945g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public boolean f1946h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f1947i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public boolean f1948j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public float f1949k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public float f1950l;

    @Override // androidx.viewpager.widget.PagerTitleStrip
    public final void c(int i2, float f2) {
        getHeight();
        throw null;
    }

    public boolean getDrawFullUnderline() {
        return this.f1946h;
    }

    @Override // androidx.viewpager.widget.PagerTitleStrip
    public int getMinHeight() {
        return Math.max(super.getMinHeight(), 0);
    }

    public int getTabIndicatorColor() {
        return this.f1945g;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getHeight();
        throw null;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0 && this.f1948j) {
            return false;
        }
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        if (action == 0) {
            this.f1949k = x2;
            this.f1950l = y2;
            this.f1948j = false;
        } else {
            if (action == 1) {
                throw null;
            }
            if (action == 2) {
                float f2 = 0;
                if (Math.abs(x2 - this.f1949k) > f2 || Math.abs(y2 - this.f1950l) > f2) {
                    this.f1948j = true;
                }
            }
        }
        return true;
    }

    @Override // android.view.View
    public void setBackgroundColor(int i2) {
        super.setBackgroundColor(i2);
        if (this.f1947i) {
            return;
        }
        this.f1946h = (i2 & (-16777216)) == 0;
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.f1947i) {
            return;
        }
        this.f1946h = drawable == null;
    }

    @Override // android.view.View
    public void setBackgroundResource(int i2) {
        super.setBackgroundResource(i2);
        if (this.f1947i) {
            return;
        }
        this.f1946h = i2 == 0;
    }

    public void setDrawFullUnderline(boolean z2) {
        this.f1946h = z2;
        this.f1947i = true;
        invalidate();
    }

    @Override // android.view.View
    public final void setPadding(int i2, int i3, int i4, int i5) {
        if (i5 < 0) {
            i5 = 0;
        }
        super.setPadding(i2, i3, i4, i5);
    }

    public void setTabIndicatorColor(int i2) {
        this.f1945g = i2;
        throw null;
    }

    public void setTabIndicatorColorResource(int i2) {
        setTabIndicatorColor(getContext().getColor(i2));
    }

    @Override // androidx.viewpager.widget.PagerTitleStrip
    public void setTextSpacing(int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        super.setTextSpacing(i2);
    }
}
