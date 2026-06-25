package com.motorola.taskbar.bar.more;

import android.content.Context;
import android.view.View;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.bar.TaskBarIconData;
import com.motorola.taskbar.sysicons.VolumeSysIconView;

/* JADX INFO: loaded from: classes2.dex */
public class VolumeLayoutProvider implements TaskBarIconData.TaskBarIconProvider {
    private Context mContext;
    private VolumeSysIconView mVolumeView;

    public VolumeLayoutProvider(Context context) {
        this.mContext = context;
        this.mVolumeView = (VolumeSysIconView) View.inflate(context, R$layout.more_volume_layout, null);
    }

    @Override // com.motorola.taskbar.bar.TaskBarIconData.TaskBarIconProvider
    public View getDrawableView() {
        return this.mVolumeView;
    }

    @Override // com.motorola.taskbar.bar.TaskBarIconData.TaskBarIconProvider
    public View getTitleView() {
        return null;
    }

    @Override // com.motorola.taskbar.bar.TaskBarIconData.TaskBarIconProvider
    public boolean onIconClick() {
        return this.mVolumeView.onClick();
    }

    @Override // com.motorola.taskbar.bar.TaskBarIconData.TaskBarIconProvider
    public void setDarkIntensity(float f) {
        this.mVolumeView.setDarkIntensity(f);
    }
}
