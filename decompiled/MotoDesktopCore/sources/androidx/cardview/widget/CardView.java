package androidx.cardview.widget;

import C.n;
import F.f;
import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.cardview.R$attr;
import androidx.cardview.R$color;
import androidx.cardview.R$style;
import androidx.cardview.R$styleable;
import g.C0132a;

/* JADX INFO: loaded from: classes.dex */
public class CardView extends FrameLayout {

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final int[] f1350f = {R.attr.colorBackground};

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static final n f1351g = new n(4);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f1352a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f1353b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Rect f1354c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final Rect f1355d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final f f1356e;

    /* JADX WARN: Illegal instructions before constructor call */
    public CardView(Context context, AttributeSet attributeSet) {
        ColorStateList colorStateListValueOf;
        int i2 = R$attr.cardViewStyle;
        super(context, attributeSet, i2);
        Rect rect = new Rect();
        this.f1354c = rect;
        this.f1355d = new Rect();
        f fVar = new f(this);
        this.f1356e = fVar;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CardView, i2, R$style.CardView);
        if (typedArrayObtainStyledAttributes.hasValue(R$styleable.CardView_cardBackgroundColor)) {
            colorStateListValueOf = typedArrayObtainStyledAttributes.getColorStateList(R$styleable.CardView_cardBackgroundColor);
        } else {
            TypedArray typedArrayObtainStyledAttributes2 = getContext().obtainStyledAttributes(f1350f);
            int color = typedArrayObtainStyledAttributes2.getColor(0, 0);
            typedArrayObtainStyledAttributes2.recycle();
            float[] fArr = new float[3];
            Color.colorToHSV(color, fArr);
            colorStateListValueOf = ColorStateList.valueOf(fArr[2] > 0.5f ? getResources().getColor(R$color.cardview_light_background) : getResources().getColor(R$color.cardview_dark_background));
        }
        float dimension = typedArrayObtainStyledAttributes.getDimension(R$styleable.CardView_cardCornerRadius, 0.0f);
        float dimension2 = typedArrayObtainStyledAttributes.getDimension(R$styleable.CardView_cardElevation, 0.0f);
        float dimension3 = typedArrayObtainStyledAttributes.getDimension(R$styleable.CardView_cardMaxElevation, 0.0f);
        this.f1352a = typedArrayObtainStyledAttributes.getBoolean(R$styleable.CardView_cardUseCompatPadding, false);
        this.f1353b = typedArrayObtainStyledAttributes.getBoolean(R$styleable.CardView_cardPreventCornerOverlap, true);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_contentPadding, 0);
        rect.left = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_contentPaddingLeft, dimensionPixelSize);
        rect.top = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_contentPaddingTop, dimensionPixelSize);
        rect.right = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_contentPaddingRight, dimensionPixelSize);
        rect.bottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_contentPaddingBottom, dimensionPixelSize);
        dimension3 = dimension2 > dimension3 ? dimension2 : dimension3;
        typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_android_minWidth, 0);
        typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.CardView_android_minHeight, 0);
        typedArrayObtainStyledAttributes.recycle();
        n nVar = f1351g;
        C0132a c0132a = new C0132a(colorStateListValueOf, dimension);
        fVar.f123a = c0132a;
        setBackgroundDrawable(c0132a);
        setClipToOutline(true);
        setElevation(dimension2);
        nVar.d(fVar, dimension3);
    }

    public ColorStateList getCardBackgroundColor() {
        return ((C0132a) ((Drawable) this.f1356e.f123a)).f2547h;
    }

    public float getCardElevation() {
        return ((CardView) this.f1356e.f124b).getElevation();
    }

    public int getContentPaddingBottom() {
        return this.f1354c.bottom;
    }

    public int getContentPaddingLeft() {
        return this.f1354c.left;
    }

    public int getContentPaddingRight() {
        return this.f1354c.right;
    }

    public int getContentPaddingTop() {
        return this.f1354c.top;
    }

    public float getMaxCardElevation() {
        return ((C0132a) ((Drawable) this.f1356e.f123a)).f2544e;
    }

    public boolean getPreventCornerOverlap() {
        return this.f1353b;
    }

    public float getRadius() {
        return ((C0132a) ((Drawable) this.f1356e.f123a)).f2540a;
    }

    public boolean getUseCompatPadding() {
        return this.f1352a;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
    }

    public void setCardBackgroundColor(int i2) {
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(i2);
        C0132a c0132a = (C0132a) ((Drawable) this.f1356e.f123a);
        if (colorStateListValueOf == null) {
            c0132a.getClass();
            colorStateListValueOf = ColorStateList.valueOf(0);
        }
        c0132a.f2547h = colorStateListValueOf;
        c0132a.f2541b.setColor(colorStateListValueOf.getColorForState(c0132a.getState(), c0132a.f2547h.getDefaultColor()));
        c0132a.invalidateSelf();
    }

    public void setCardBackgroundColor(ColorStateList colorStateList) {
        C0132a c0132a = (C0132a) ((Drawable) this.f1356e.f123a);
        if (colorStateList == null) {
            c0132a.getClass();
            colorStateList = ColorStateList.valueOf(0);
        }
        c0132a.f2547h = colorStateList;
        c0132a.f2541b.setColor(colorStateList.getColorForState(c0132a.getState(), c0132a.f2547h.getDefaultColor()));
        c0132a.invalidateSelf();
    }

    public void setCardElevation(float f2) {
        ((CardView) this.f1356e.f124b).setElevation(f2);
    }

    public void setMaxCardElevation(float f2) {
        f1351g.d(this.f1356e, f2);
    }

    @Override // android.view.View
    public void setMinimumHeight(int i2) {
        super.setMinimumHeight(i2);
    }

    @Override // android.view.View
    public void setMinimumWidth(int i2) {
        super.setMinimumWidth(i2);
    }

    @Override // android.view.View
    public final void setPadding(int i2, int i3, int i4, int i5) {
    }

    @Override // android.view.View
    public final void setPaddingRelative(int i2, int i3, int i4, int i5) {
    }

    public void setPreventCornerOverlap(boolean z2) {
        if (z2 != this.f1353b) {
            this.f1353b = z2;
            n nVar = f1351g;
            f fVar = this.f1356e;
            nVar.d(fVar, ((C0132a) ((Drawable) fVar.f123a)).f2544e);
        }
    }

    public void setRadius(float f2) {
        C0132a c0132a = (C0132a) ((Drawable) this.f1356e.f123a);
        if (f2 == c0132a.f2540a) {
            return;
        }
        c0132a.f2540a = f2;
        c0132a.b(null);
        c0132a.invalidateSelf();
    }

    public void setUseCompatPadding(boolean z2) {
        if (this.f1352a != z2) {
            this.f1352a = z2;
            n nVar = f1351g;
            f fVar = this.f1356e;
            nVar.d(fVar, ((C0132a) ((Drawable) fVar.f123a)).f2544e);
        }
    }
}
