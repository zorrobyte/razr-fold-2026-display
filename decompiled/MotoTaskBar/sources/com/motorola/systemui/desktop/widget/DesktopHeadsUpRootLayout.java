package com.motorola.systemui.desktop.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopHeadsUpRootLayout extends RelativeLayout {
    public DesktopHeadsUpRootLayout(Context context) {
        super(context);
    }

    public DesktopHeadsUpRootLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DesktopHeadsUpRootLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public DesktopHeadsUpRootLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (canvas.isHardwareAccelerated()) {
            super.draw(canvas);
        } else {
            Log.d("DesktopHeadsUpRootLayout", "Ignore draw");
        }
    }
}
