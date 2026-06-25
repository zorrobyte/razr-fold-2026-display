package g;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* JADX INFO: renamed from: g.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0132a extends Drawable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public float f2540a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Paint f2541b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final RectF f2542c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final Rect f2543d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public float f2544e;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public ColorStateList f2547h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public PorterDuffColorFilter f2548i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public ColorStateList f2549j;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f2545f = false;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f2546g = true;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public PorterDuff.Mode f2550k = PorterDuff.Mode.SRC_IN;

    public C0132a(ColorStateList colorStateList, float f2) {
        this.f2540a = f2;
        Paint paint = new Paint(5);
        this.f2541b = paint;
        colorStateList = colorStateList == null ? ColorStateList.valueOf(0) : colorStateList;
        this.f2547h = colorStateList;
        paint.setColor(colorStateList.getColorForState(getState(), this.f2547h.getDefaultColor()));
        this.f2542c = new RectF();
        this.f2543d = new Rect();
    }

    public final PorterDuffColorFilter a(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    public final void b(Rect rect) {
        if (rect == null) {
            rect = getBounds();
        }
        RectF rectF = this.f2542c;
        rectF.set(rect.left, rect.top, rect.right, rect.bottom);
        Rect rect2 = this.f2543d;
        rect2.set(rect);
        if (this.f2545f) {
            rect2.inset((int) Math.ceil(AbstractC0133b.a(this.f2544e, this.f2540a, this.f2546g)), (int) Math.ceil(AbstractC0133b.b(this.f2544e, this.f2540a, this.f2546g)));
            rectF.set(rect2);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        boolean z2;
        Paint paint = this.f2541b;
        if (this.f2548i == null || paint.getColorFilter() != null) {
            z2 = false;
        } else {
            paint.setColorFilter(this.f2548i);
            z2 = true;
        }
        RectF rectF = this.f2542c;
        float f2 = this.f2540a;
        canvas.drawRoundRect(rectF, f2, f2, paint);
        if (z2) {
            paint.setColorFilter(null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void getOutline(Outline outline) {
        outline.setRoundRect(this.f2543d, this.f2540a);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = this.f2549j;
        return (colorStateList2 != null && colorStateList2.isStateful()) || ((colorStateList = this.f2547h) != null && colorStateList.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        b(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        ColorStateList colorStateList = this.f2547h;
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        Paint paint = this.f2541b;
        boolean z2 = colorForState != paint.getColor();
        if (z2) {
            paint.setColor(colorForState);
        }
        ColorStateList colorStateList2 = this.f2549j;
        if (colorStateList2 == null || (mode = this.f2550k) == null) {
            return z2;
        }
        this.f2548i = a(colorStateList2, mode);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i2) {
        this.f2541b.setAlpha(i2);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.f2541b.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        this.f2549j = colorStateList;
        this.f2548i = a(colorStateList, this.f2550k);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        this.f2550k = mode;
        this.f2548i = a(this.f2549j, mode);
        invalidateSelf();
    }
}
