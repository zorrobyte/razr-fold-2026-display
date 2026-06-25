package androidx.constraintlayout.widget;

import android.graphics.Canvas;
import android.view.View;
import j.C0141b;

/* JADX INFO: loaded from: classes.dex */
public class Guideline extends View {
    @Override // android.view.View
    public final void draw(Canvas canvas) {
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        setMeasuredDimension(0, 0);
    }

    public void setGuidelineBegin(int i2) {
        C0141b c0141b = (C0141b) getLayoutParams();
        c0141b.f2654a = i2;
        setLayoutParams(c0141b);
    }

    public void setGuidelineEnd(int i2) {
        C0141b c0141b = (C0141b) getLayoutParams();
        c0141b.f2655b = i2;
        setLayoutParams(c0141b);
    }

    public void setGuidelinePercent(float f2) {
        C0141b c0141b = (C0141b) getLayoutParams();
        c0141b.f2656c = f2;
        setLayoutParams(c0141b);
    }

    @Override // android.view.View
    public void setVisibility(int i2) {
    }
}
