package com.motorola.taskbar.bar.more;

import android.content.Context;
import android.view.View;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.bar.Clock;
import com.motorola.taskbar.bar.TaskBarIconData;

/* JADX INFO: loaded from: classes2.dex */
public class ClockLayoutProvider implements TaskBarIconData.TaskBarIconProvider {
    private Clock mClockView;
    private Context mContext;

    public ClockLayoutProvider(Context context) {
        this.mContext = context;
        Clock clock = (Clock) View.inflate(context, R$layout.desktop_clock_button, null);
        this.mClockView = clock;
        clock.setClickable(false);
    }

    @Override // com.motorola.taskbar.bar.TaskBarIconData.TaskBarIconProvider
    public View getDrawableView() {
        return null;
    }

    @Override // com.motorola.taskbar.bar.TaskBarIconData.TaskBarIconProvider
    public View getTitleView() {
        return this.mClockView;
    }

    @Override // com.motorola.taskbar.bar.TaskBarIconData.TaskBarIconProvider
    public boolean onIconClick() {
        return false;
    }

    @Override // com.motorola.taskbar.bar.TaskBarIconData.TaskBarIconProvider
    public void setDarkIntensity(float f) {
    }
}
