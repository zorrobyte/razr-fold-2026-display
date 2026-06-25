package com.motorola.systemui.desktop.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.motorola.taskbar.R$dimen;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopQSPanelLayout extends FrameLayout {
    private int mOutlineRadius;
    private int mRealHeight;

    public DesktopQSPanelLayout(Context context) {
        super(context);
        this.mRealHeight = 0;
    }

    public DesktopQSPanelLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRealHeight = 0;
    }

    public DesktopQSPanelLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRealHeight = 0;
    }

    public DesktopQSPanelLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRealHeight = 0;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        int i = this.mRealHeight;
        Path path = new Path();
        RectF rectF = new RectF(0.0f, getScrollY(), getWidth(), i + getScrollY());
        int i2 = this.mOutlineRadius;
        path.addRoundRect(rectF, i2, i2, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(path);
        super.draw(canvas);
        canvas.restore();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        setVerticalScrollBarEnabled(false);
        setWillNotDraw(false);
        this.mOutlineRadius = getResources().getDimensionPixelSize(R$dimen.desktop_qs_notification_panel_radius);
    }

    public void setRealHeight(int i) {
        this.mRealHeight = i;
    }
}
