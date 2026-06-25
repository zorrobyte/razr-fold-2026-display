package com.motorola.taskbar.bar;

import android.view.View;
import com.motorola.taskbar.bar.TaskBarView;

/* JADX INFO: loaded from: classes2.dex */
public class TaskBarIconData {
    public int drawableId;
    public int layoutId;
    public TaskBarIconProvider provider;
    public Runnable runnable;
    public TaskBarView.DesktopStatusBarIconInfo statusBarIconInfo;
    public int tipId;
    public View view;

    public interface TaskBarIconProvider {
        View getDrawableView();

        View getTitleView();

        boolean onIconClick();

        void setDarkIntensity(float f);
    }

    public TaskBarIconData(int i, int i2, int i3, TaskBarIconProvider taskBarIconProvider, Runnable runnable) {
        this.layoutId = i;
        this.drawableId = i2;
        this.tipId = i3;
        this.provider = taskBarIconProvider;
        this.runnable = runnable;
    }

    public TaskBarIconData(int i, int i2, int i3, Runnable runnable) {
        this(i, i2, i3, null, runnable);
    }

    public TaskBarIconData(TaskBarView.DesktopStatusBarIconInfo desktopStatusBarIconInfo) {
        this.statusBarIconInfo = desktopStatusBarIconInfo;
    }
}
