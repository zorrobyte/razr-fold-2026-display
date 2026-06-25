package com.motorola.taskbar.bar.more;

import android.content.Context;
import android.view.View;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.bar.DateView;
import com.motorola.taskbar.bar.TaskBarIconData;

/* JADX INFO: loaded from: classes2.dex */
public class DateLayoutProvider implements TaskBarIconData.TaskBarIconProvider {
    private Context mContext;
    private DateView mDateView;

    public DateLayoutProvider(Context context) {
        this.mContext = context;
        this.mDateView = (DateView) View.inflate(context, R$layout.desktop_date_button, null);
    }

    @Override // com.motorola.taskbar.bar.TaskBarIconData.TaskBarIconProvider
    public View getDrawableView() {
        return null;
    }

    @Override // com.motorola.taskbar.bar.TaskBarIconData.TaskBarIconProvider
    public View getTitleView() {
        return this.mDateView;
    }

    @Override // com.motorola.taskbar.bar.TaskBarIconData.TaskBarIconProvider
    public boolean onIconClick() {
        return false;
    }

    @Override // com.motorola.taskbar.bar.TaskBarIconData.TaskBarIconProvider
    public void setDarkIntensity(float f) {
    }
}
