package com.motorola.taskbar.panel;

import android.content.Context;
import android.util.AttributeSet;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$layout;

/* JADX INFO: loaded from: classes2.dex */
public class MobileUiVolumePanelLayout extends VolumePanelLayout {
    public MobileUiVolumePanelLayout(Context context) {
        super(context);
    }

    public MobileUiVolumePanelLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.motorola.taskbar.panel.VolumePanelLayout
    protected int itemIconColor() {
        return getResources().getColor(R$color.on_surface);
    }

    @Override // com.motorola.taskbar.panel.VolumePanelLayout
    protected int mediaOutputItem() {
        return R$layout.mobileui_media_output_item;
    }

    @Override // com.motorola.taskbar.panel.VolumePanelLayout
    public void onUiModeChanged() {
        if (this.mVolumeChannelsGroup == null) {
            return;
        }
        setBackground(getContext().getDrawable(R$drawable.mobileui_volume_bg));
        this.mTitleView.setTextColor(getContext().getColor(R$color.on_surface));
        this.mDone.setTextColor(getContext().getColor(R$color.inverse_on_surface));
        this.mDone.setBackground(getContext().getDrawable(R$drawable.mobileui_volume_done_bg));
        this.mTitleIconView.getDrawable().setTint(titleIconColor());
        if (isAttachedToWindow()) {
            requestReInflateChannelsLayout(0L);
        }
    }

    @Override // com.motorola.taskbar.panel.VolumePanelLayout
    protected int titleIconColor() {
        return getResources().getColor(R$color.on_surface);
    }
}
