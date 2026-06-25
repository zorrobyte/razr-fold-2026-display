package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$id;

/* JADX INFO: loaded from: classes.dex */
public class QSFooterView extends FrameLayout {
    private PageIndicator mPageIndicator;

    public QSFooterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void updateResources() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.height = getResources().getDimensionPixelSize(R$dimen.qs_footer_height);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.qs_footer_margin);
        marginLayoutParams.leftMargin = dimensionPixelSize;
        marginLayoutParams.rightMargin = dimensionPixelSize;
        marginLayoutParams.bottomMargin = getResources().getDimensionPixelSize(com.android.systemui.res.R$dimen.qs_footers_margin_bottom);
        setLayoutParams(marginLayoutParams);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPageIndicator = (PageIndicator) findViewById(R$id.footer_page_indicator);
        updateResources();
        setImportantForAccessibility(1);
    }
}
