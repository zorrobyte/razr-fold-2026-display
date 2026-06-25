package com.motorola.taskbar.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/* JADX INFO: loaded from: classes2.dex */
public class WindowViewWapper {
    private boolean mIsAddedToWindow;
    private final View mRootView;
    private final WindowManager mWindowManager;

    public WindowViewWapper(WindowManager windowManager, View view) {
        this.mWindowManager = windowManager;
        this.mRootView = view;
    }

    public void addView(ViewGroup.LayoutParams layoutParams) {
        if (this.mIsAddedToWindow) {
            return;
        }
        this.mWindowManager.addView(this.mRootView, layoutParams);
        this.mIsAddedToWindow = true;
    }

    public boolean isAddedToWindow() {
        return this.mIsAddedToWindow;
    }

    public void removeView() {
        if (this.mIsAddedToWindow) {
            this.mWindowManager.removeView(this.mRootView);
            this.mIsAddedToWindow = false;
        }
    }
}
