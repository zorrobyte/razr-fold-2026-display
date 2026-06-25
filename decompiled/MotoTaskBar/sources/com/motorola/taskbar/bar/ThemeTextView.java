package com.motorola.taskbar.bar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyButtonRipple;
import com.motorola.taskbar.R$color;

/* JADX INFO: loaded from: classes2.dex */
public class ThemeTextView extends TextView implements ConfigurationController.ConfigurationListener {
    private boolean mAttached;
    private KeyButtonRipple mRipple;

    public ThemeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ThemeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void setTextColor() {
        setTextColor(getContext().getColor(R$color.taskbar_text_color));
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mAttached) {
            return;
        }
        this.mAttached = true;
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAttached) {
            ((ConfigurationController) Dependency.get(ConfigurationController.class)).removeCallback(this);
            this.mAttached = false;
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        setTextColor();
        KeyButtonRipple keyButtonRipple = new KeyButtonRipple(getContext(), this);
        this.mRipple = keyButtonRipple;
        setBackground(keyButtonRipple);
        this.mRipple.setType(KeyButtonRipple.Type.ROUNDED_RECT);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onUiModeChanged() {
        setTextColor();
    }

    public void setDarkIntensity(float f) {
        this.mRipple.setDarkIntensity(f);
        setTextColor();
    }
}
