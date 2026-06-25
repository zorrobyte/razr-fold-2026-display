package com.motorola.systemui.desktop.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopNotificationRootLayout extends FrameLayout {
    private QsNotificationCentralSurfaces mQsNotificationCentralSurfaces;

    public DesktopNotificationRootLayout(Context context) {
        super(context);
    }

    public DesktopNotificationRootLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DesktopNotificationRootLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public DesktopNotificationRootLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        QsNotificationCentralSurfaces qsNotificationCentralSurfaces;
        if (motionEvent.getAction() == 4 && (qsNotificationCentralSurfaces = this.mQsNotificationCentralSurfaces) != null && !qsNotificationCentralSurfaces.isRemoteInputActive()) {
            this.mQsNotificationCentralSurfaces.requestHidePanel();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (canvas.isHardwareAccelerated()) {
            super.draw(canvas);
        } else {
            Log.d("DesktopNotificationRootLayout", "Ignore draw");
        }
    }

    public void setQsNotificationCentralSurfaces(QsNotificationCentralSurfaces qsNotificationCentralSurfaces) {
        this.mQsNotificationCentralSurfaces = qsNotificationCentralSurfaces;
    }
}
