package com.android.systemui.shared.rotation;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.shared.navigationbar.KeyButtonRipple;

/* JADX INFO: loaded from: classes.dex */
public class FloatingRotationButtonView extends ImageView {
    private int mDiameter;
    private final Configuration mLastConfiguration;
    private final Paint mOvalBgPaint;
    private KeyButtonRipple mRipple;

    public FloatingRotationButtonView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FloatingRotationButtonView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOvalBgPaint = new Paint(3);
        this.mLastConfiguration = getResources().getConfiguration();
        setClickable(true);
        setWillNotDraw(false);
        forceHasOverlappingRendering(false);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        float fMin = Math.min(getWidth(), getHeight());
        canvas.drawOval(0.0f, 0.0f, fMin, fMin, this.mOvalBgPaint);
        super.draw(canvas);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        KeyButtonRipple keyButtonRipple;
        int iUpdateFrom = this.mLastConfiguration.updateFrom(configuration);
        if (((iUpdateFrom & 1024) == 0 && (iUpdateFrom & 4096) == 0) || (keyButtonRipple = this.mRipple) == null) {
            return;
        }
        keyButtonRipple.updateResources();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int i3 = this.mDiameter;
        setMeasuredDimension(i3, i3);
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i != 0) {
            jumpDrawablesToCurrentState();
        }
    }
}
