package com.motorola.systemui.desktop.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ScrollView;
import com.motorola.taskbar.R$dimen;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopNotificationPanelLayout extends ScrollView {
    private int mOutlineRadius;

    public DesktopNotificationPanelLayout(Context context) {
        super(context);
    }

    public DesktopNotificationPanelLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DesktopNotificationPanelLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public DesktopNotificationPanelLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.ScrollView, android.view.View
    public void draw(Canvas canvas) {
        int iMin = Math.min(getChildAt(0).getHeight(), getHeight());
        Path path = new Path();
        RectF rectF = new RectF(0.0f, getScrollY(), getWidth(), iMin + getScrollY());
        int i = this.mOutlineRadius;
        path.addRoundRect(rectF, i, i, Path.Direction.CW);
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
}
