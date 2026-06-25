package com.motorola.taskbar.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.motorola.taskbar.guide.R$styleable;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: RoundRectImageView.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RoundedRectImageView extends AppCompatImageView {
    private final Path mPath;
    private float mRadius;
    private final Paint mRoundRectPaint;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RoundedRectImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RoundedRectImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        context.getClass();
        this.mPath = new Path();
        Paint paint = new Paint(0);
        this.mRoundRectPaint = paint;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RoundedRectImageView, i, 0);
        typedArrayObtainStyledAttributes.getClass();
        this.mRadius = typedArrayObtainStyledAttributes.getDimension(R$styleable.RoundedRectImageView_radius, 0.0f);
        Unit unit = Unit.INSTANCE;
        typedArrayObtainStyledAttributes.recycle();
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0f);
    }

    public /* synthetic */ RoundedRectImageView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    private final void updatePath(int i, int i2) {
        this.mPath.reset();
        int paddingRight = (i - getPaddingRight()) - getPaddingLeft();
        int paddingBottom = (i2 - getPaddingBottom()) - getPaddingTop();
        float f = this.mRadius;
        this.mPath.addRoundRect(getPaddingLeft(), getPaddingTop(), paddingRight, paddingBottom, f, f, Path.Direction.CW);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.getClass();
        if (this.mRadius != 0.0f) {
            canvas.clipPath(this.mPath);
        }
        super.onDraw(canvas);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updatePath(i, i2);
    }
}
