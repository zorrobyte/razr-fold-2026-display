package com.motorola.taskbar.bar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.motorola.taskbar.R$drawable;

/* JADX INFO: loaded from: classes2.dex */
public class TaskBarAreaDivider extends View implements ConfigurationController.ConfigurationListener {
    public TaskBarAreaDivider(Context context) {
        super(context);
    }

    public TaskBarAreaDivider(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TaskBarAreaDivider(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public TaskBarAreaDivider(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    private void setBackground() {
        setBackground(getContext().getDrawable(R$drawable.taskbar_area_divider_bg));
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setBackground();
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).removeCallback(this);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onUiModeChanged() {
        setBackground();
    }
}
