package androidx.leanback.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.core.view.ViewCompat;
import androidx.leanback.R$color;
import androidx.leanback.R$dimen;
import androidx.leanback.R$drawable;

/* JADX INFO: loaded from: classes.dex */
public class PagingIndicator extends View {
    private static final TimeInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final Property DOT_ALPHA;
    private static final Property DOT_DIAMETER;
    private static final Property DOT_TRANSLATION_X;
    private final AnimatorSet mAnimator;
    Bitmap mArrow;
    final int mArrowDiameter;
    private final int mArrowGap;
    Paint mArrowPaint;
    final int mArrowRadius;
    final Rect mArrowRect;
    final float mArrowToBgRatio;
    final Paint mBgPaint;
    private int mCurrentPage;
    int mDotCenterY;
    final int mDotDiameter;
    int mDotFgSelectColor;
    private final int mDotGap;
    final int mDotRadius;
    private int[] mDotSelectedNextX;
    private int[] mDotSelectedPrevX;
    private int[] mDotSelectedX;
    private Dot[] mDots;
    final Paint mFgPaint;
    private final AnimatorSet mHideAnimator;
    boolean mIsLtr;
    private int mPageCount;
    private final int mShadowRadius;
    private final AnimatorSet mShowAnimator;

    public abstract class Dot {
    }

    static {
        Class<Float> cls = Float.class;
        DOT_ALPHA = new Property(cls, "alpha") { // from class: androidx.leanback.widget.PagingIndicator.1
            public Float get(Dot dot) {
                throw null;
            }

            @Override // android.util.Property
            public /* bridge */ /* synthetic */ Object get(Object obj) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                return get((Dot) null);
            }

            public void set(Dot dot, Float f) {
                f.floatValue();
                throw null;
            }

            @Override // android.util.Property
            public /* bridge */ /* synthetic */ void set(Object obj, Object obj2) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                set((Dot) null, (Float) obj2);
            }
        };
        DOT_DIAMETER = new Property(cls, "diameter") { // from class: androidx.leanback.widget.PagingIndicator.2
            public Float get(Dot dot) {
                throw null;
            }

            @Override // android.util.Property
            public /* bridge */ /* synthetic */ Object get(Object obj) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                return get((Dot) null);
            }

            public void set(Dot dot, Float f) {
                f.floatValue();
                throw null;
            }

            @Override // android.util.Property
            public /* bridge */ /* synthetic */ void set(Object obj, Object obj2) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                set((Dot) null, (Float) obj2);
            }
        };
        DOT_TRANSLATION_X = new Property(cls, "translation_x") { // from class: androidx.leanback.widget.PagingIndicator.3
            public Float get(Dot dot) {
                throw null;
            }

            @Override // android.util.Property
            public /* bridge */ /* synthetic */ Object get(Object obj) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                return get((Dot) null);
            }

            public void set(Dot dot, Float f) {
                f.floatValue();
                throw null;
            }

            @Override // android.util.Property
            public /* bridge */ /* synthetic */ void set(Object obj, Object obj2) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                set((Dot) null, (Float) obj2);
            }
        };
    }

    public PagingIndicator(Context context) {
        this(context, null, 0);
    }

    public PagingIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PagingIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        AnimatorSet animatorSet = new AnimatorSet();
        this.mAnimator = animatorSet;
        Resources resources = getResources();
        int[] iArr = androidx.leanback.R$styleable.PagingIndicator;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArrayObtainStyledAttributes, i, 0);
        int dimensionFromTypedArray = getDimensionFromTypedArray(typedArrayObtainStyledAttributes, androidx.leanback.R$styleable.PagingIndicator_lbDotRadius, R$dimen.lb_page_indicator_dot_radius);
        this.mDotRadius = dimensionFromTypedArray;
        this.mDotDiameter = dimensionFromTypedArray * 2;
        int dimensionFromTypedArray2 = getDimensionFromTypedArray(typedArrayObtainStyledAttributes, androidx.leanback.R$styleable.PagingIndicator_arrowRadius, R$dimen.lb_page_indicator_arrow_radius);
        this.mArrowRadius = dimensionFromTypedArray2;
        int i2 = dimensionFromTypedArray2 * 2;
        this.mArrowDiameter = i2;
        this.mDotGap = getDimensionFromTypedArray(typedArrayObtainStyledAttributes, androidx.leanback.R$styleable.PagingIndicator_dotToDotGap, R$dimen.lb_page_indicator_dot_gap);
        this.mArrowGap = getDimensionFromTypedArray(typedArrayObtainStyledAttributes, androidx.leanback.R$styleable.PagingIndicator_dotToArrowGap, R$dimen.lb_page_indicator_arrow_gap);
        int colorFromTypedArray = getColorFromTypedArray(typedArrayObtainStyledAttributes, androidx.leanback.R$styleable.PagingIndicator_dotBgColor, R$color.lb_page_indicator_dot);
        Paint paint = new Paint(1);
        this.mBgPaint = paint;
        paint.setColor(colorFromTypedArray);
        this.mDotFgSelectColor = getColorFromTypedArray(typedArrayObtainStyledAttributes, androidx.leanback.R$styleable.PagingIndicator_arrowBgColor, R$color.lb_page_indicator_arrow_background);
        if (this.mArrowPaint == null) {
            int i3 = androidx.leanback.R$styleable.PagingIndicator_arrowColor;
            if (typedArrayObtainStyledAttributes.hasValue(i3)) {
                setArrowColor(typedArrayObtainStyledAttributes.getColor(i3, 0));
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        this.mIsLtr = resources.getConfiguration().getLayoutDirection() == 0;
        int color = resources.getColor(R$color.lb_page_indicator_arrow_shadow);
        int dimensionPixelSize = resources.getDimensionPixelSize(R$dimen.lb_page_indicator_arrow_shadow_radius);
        this.mShadowRadius = dimensionPixelSize;
        Paint paint2 = new Paint(1);
        this.mFgPaint = paint2;
        float dimensionPixelSize2 = resources.getDimensionPixelSize(R$dimen.lb_page_indicator_arrow_shadow_offset);
        paint2.setShadowLayer(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize2, color);
        this.mArrow = loadArrow();
        this.mArrowRect = new Rect(0, 0, this.mArrow.getWidth(), this.mArrow.getHeight());
        this.mArrowToBgRatio = this.mArrow.getWidth() / i2;
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mShowAnimator = animatorSet2;
        animatorSet2.playTogether(createDotAlphaAnimator(0.0f, 1.0f), createDotDiameterAnimator(dimensionFromTypedArray * 2, dimensionFromTypedArray2 * 2), createDotTranslationXAnimator());
        AnimatorSet animatorSet3 = new AnimatorSet();
        this.mHideAnimator = animatorSet3;
        animatorSet3.playTogether(createDotAlphaAnimator(1.0f, 0.0f), createDotDiameterAnimator(dimensionFromTypedArray2 * 2, dimensionFromTypedArray * 2), createDotTranslationXAnimator());
        animatorSet.playTogether(animatorSet2, animatorSet3);
        setLayerType(1, null);
    }

    private void adjustDotPosition() {
        int i = this.mCurrentPage;
        if (i > 0) {
            Dot dot = this.mDots[0];
            throw null;
        }
        Dot dot2 = this.mDots[i];
        throw null;
    }

    private void calculateDotPositions() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int width = getWidth() - getPaddingRight();
        int requiredWidth = getRequiredWidth();
        int i = (paddingLeft + width) / 2;
        int i2 = this.mPageCount;
        int[] iArr = new int[i2];
        this.mDotSelectedX = iArr;
        int[] iArr2 = new int[i2];
        this.mDotSelectedPrevX = iArr2;
        int[] iArr3 = new int[i2];
        this.mDotSelectedNextX = iArr3;
        int i3 = 1;
        if (this.mIsLtr) {
            int i4 = i - (requiredWidth / 2);
            int i5 = this.mDotRadius;
            int i6 = this.mDotGap;
            int i7 = this.mArrowGap;
            iArr[0] = ((i4 + i5) - i6) + i7;
            iArr2[0] = i4 + i5;
            iArr3[0] = ((i4 + i5) - (i6 * 2)) + (i7 * 2);
            while (i3 < this.mPageCount) {
                int[] iArr4 = this.mDotSelectedX;
                int[] iArr5 = this.mDotSelectedPrevX;
                int i8 = i3 - 1;
                int i9 = iArr5[i8];
                int i10 = this.mArrowGap;
                iArr4[i3] = i9 + i10;
                iArr5[i3] = iArr5[i8] + this.mDotGap;
                this.mDotSelectedNextX[i3] = iArr4[i8] + i10;
                i3++;
            }
        } else {
            int i11 = i + (requiredWidth / 2);
            int i12 = this.mDotRadius;
            int i13 = this.mDotGap;
            int i14 = this.mArrowGap;
            iArr[0] = ((i11 - i12) + i13) - i14;
            iArr2[0] = i11 - i12;
            iArr3[0] = ((i11 - i12) + (i13 * 2)) - (i14 * 2);
            while (i3 < this.mPageCount) {
                int[] iArr6 = this.mDotSelectedX;
                int[] iArr7 = this.mDotSelectedPrevX;
                int i15 = i3 - 1;
                int i16 = iArr7[i15];
                int i17 = this.mArrowGap;
                iArr6[i3] = i16 - i17;
                iArr7[i3] = iArr7[i15] - this.mDotGap;
                this.mDotSelectedNextX[i3] = iArr6[i15] - i17;
                i3++;
            }
        }
        this.mDotCenterY = paddingTop + this.mArrowRadius;
        adjustDotPosition();
    }

    private Animator createDotAlphaAnimator(float f, float f2) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat((Object) null, (Property<Object, Float>) DOT_ALPHA, f, f2);
        objectAnimatorOfFloat.setDuration(167L);
        objectAnimatorOfFloat.setInterpolator(DECELERATE_INTERPOLATOR);
        return objectAnimatorOfFloat;
    }

    private Animator createDotDiameterAnimator(float f, float f2) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat((Object) null, (Property<Object, Float>) DOT_DIAMETER, f, f2);
        objectAnimatorOfFloat.setDuration(417L);
        objectAnimatorOfFloat.setInterpolator(DECELERATE_INTERPOLATOR);
        return objectAnimatorOfFloat;
    }

    private Animator createDotTranslationXAnimator() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat((Object) null, (Property<Object, Float>) DOT_TRANSLATION_X, (-this.mArrowGap) + this.mDotGap, 0.0f);
        objectAnimatorOfFloat.setDuration(417L);
        objectAnimatorOfFloat.setInterpolator(DECELERATE_INTERPOLATOR);
        return objectAnimatorOfFloat;
    }

    private int getColorFromTypedArray(TypedArray typedArray, int i, int i2) {
        return typedArray.getColor(i, getResources().getColor(i2));
    }

    private int getDesiredHeight() {
        return getPaddingTop() + this.mArrowDiameter + getPaddingBottom() + this.mShadowRadius;
    }

    private int getDesiredWidth() {
        return getPaddingLeft() + getRequiredWidth() + getPaddingRight();
    }

    private int getDimensionFromTypedArray(TypedArray typedArray, int i, int i2) {
        return typedArray.getDimensionPixelOffset(i, getResources().getDimensionPixelOffset(i2));
    }

    private int getRequiredWidth() {
        return (this.mDotRadius * 2) + (this.mArrowGap * 2) + ((this.mPageCount - 3) * this.mDotGap);
    }

    private Bitmap loadArrow() {
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(getResources(), R$drawable.lb_ic_nav_arrow);
        if (this.mIsLtr) {
            return bitmapDecodeResource;
        }
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(bitmapDecodeResource, 0, 0, bitmapDecodeResource.getWidth(), bitmapDecodeResource.getHeight(), matrix, false);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mPageCount <= 0) {
            return;
        }
        Dot dot = this.mDots[0];
        throw null;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int desiredHeight = getDesiredHeight();
        int mode = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            desiredHeight = Math.min(desiredHeight, View.MeasureSpec.getSize(i2));
        } else if (mode == 1073741824) {
            desiredHeight = View.MeasureSpec.getSize(i2);
        }
        int desiredWidth = getDesiredWidth();
        int mode2 = View.MeasureSpec.getMode(i);
        if (mode2 == Integer.MIN_VALUE) {
            desiredWidth = Math.min(desiredWidth, View.MeasureSpec.getSize(i));
        } else if (mode2 == 1073741824) {
            desiredWidth = View.MeasureSpec.getSize(i);
        }
        setMeasuredDimension(desiredWidth, desiredHeight);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        boolean z = i == 0;
        if (this.mIsLtr != z) {
            this.mIsLtr = z;
            this.mArrow = loadArrow();
            Dot[] dotArr = this.mDots;
            if (dotArr != null && dotArr.length > 0) {
                Dot dot = dotArr[0];
                throw null;
            }
            calculateDotPositions();
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        setMeasuredDimension(i, i2);
        calculateDotPositions();
    }

    public void setArrowColor(int i) {
        if (this.mArrowPaint == null) {
            this.mArrowPaint = new Paint();
        }
        this.mArrowPaint.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN));
    }
}
