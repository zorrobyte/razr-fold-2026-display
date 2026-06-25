package com.motorola.systemui.desktop.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.settingslib.Utils;
import com.motorola.taskbar.R$styleable;

/* JADX INFO: loaded from: classes2.dex */
public class RoundRectFrameLayout extends FrameLayout {
    private boolean mChipChildRect;
    private int mOutlineRadius;

    public RoundRectFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundRectFrameLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RoundRectFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mChipChildRect = true;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RoundRectFrameLayout, 0, 0);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i3 = 0; i3 < indexCount; i3++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i3);
                if (index == R$styleable.RoundRectFrameLayout_outlineRadius) {
                    this.mOutlineRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(i3, 0);
                } else if (index == R$styleable.RoundRectFrameLayout_chipChildRect) {
                    this.mChipChildRect = typedArrayObtainStyledAttributes.getBoolean(i3, true);
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        canvas.save();
        if (!this.mChipChildRect || getChildCount() <= 0) {
            Path path = new Path();
            RectF rectF = new RectF(0.0f, getScrollY(), getWidth(), getHeight() + getScrollY());
            int i = this.mOutlineRadius;
            path.addRoundRect(rectF, i, i, Path.Direction.CW);
            canvas.clipPath(path);
        } else {
            int iMin = Math.min(getChildAt(0).getHeight(), getHeight());
            Path path2 = new Path();
            RectF rectF2 = new RectF(0.0f, getScrollY(), getWidth(), iMin + getScrollY());
            int i2 = this.mOutlineRadius;
            path2.addRoundRect(rectF2, i2, i2, Path.Direction.CW);
            canvas.clipPath(path2);
        }
        super.draw(canvas);
        canvas.restore();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        setVerticalScrollBarEnabled(false);
        setWillNotDraw(false);
        if (this.mOutlineRadius == 0) {
            this.mOutlineRadius = getResources().getDimensionPixelSize(Utils.getThemeAttr(((FrameLayout) this).mContext, R.attr.dialogCornerRadius));
        }
    }
}
