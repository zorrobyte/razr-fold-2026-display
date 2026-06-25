package com.motorola.taskbar.bar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.motorola.taskbar.sysicons.VolumeSysIconView;

/* JADX INFO: loaded from: classes2.dex */
public class TabletTaskBarView extends TaskBarView {
    public TabletTaskBarView(Context context) {
        super(context);
    }

    public TabletTaskBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TabletTaskBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public TabletTaskBarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$0(View view) {
        this.mTaskBarButtonClickHelper.requestSwitchVolumeDialog(this.mVolumeSysIconView);
    }

    @Override // com.motorola.taskbar.bar.TaskBarView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        VolumeSysIconView volumeSysIconView = this.mVolumeSysIconView;
        if (volumeSysIconView != null) {
            volumeSysIconView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TabletTaskBarView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onFinishInflate$0(view);
                }
            });
        }
    }
}
