package com.motorola.mobiledesktop.core.desktop.tutorial;

import X.v0;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import c0.a;
import com.duolingo.open.rtlviewpager.RtlViewPager;
import com.motorola.mobiledesktop.core.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SlideView extends RtlViewPager {

    /* JADX INFO: renamed from: g0, reason: collision with root package name */
    public List f2329g0;
    public a h0;

    /* JADX INFO: renamed from: i0, reason: collision with root package name */
    public final Context f2330i0;

    public SlideView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f2330i0 = context;
    }

    @Override // com.duolingo.open.rtlviewpager.RtlViewPager, androidx.viewpager.widget.ViewPager, android.view.View
    public final void onMeasure(int i2, int i3) {
        Context context = this.f2330i0;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.helper_dialog_slide_port_height);
        if (getResources().getConfiguration().orientation == 2) {
            dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.helper_dialog_slide_land_height);
            Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getRealMetrics(displayMetrics);
            int i4 = (int) (((double) displayMetrics.heightPixels) * 0.4d);
            if (dimensionPixelSize > i4) {
                dimensionPixelSize = i4;
            }
        }
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(new DisplayMetrics());
        v0.a("SlideView", "onMeasure - height:" + dimensionPixelSize);
        super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(dimensionPixelSize, 1073741824));
    }
}
