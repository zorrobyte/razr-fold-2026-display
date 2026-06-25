package com.google.android.setupcompat.template;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/* JADX INFO: loaded from: classes.dex */
public class FooterActionButton extends Button implements IFooterActionButton {
    FooterButton footerButton;
    private boolean isPrimaryButtonStyle;

    public FooterActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isPrimaryButtonStyle = false;
    }

    public boolean isPrimaryButtonStyle() {
        return this.isPrimaryButtonStyle;
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        FooterButton footerButton;
        View.OnClickListener onClickListenerWhenDisabled;
        if (motionEvent.getAction() == 0 && (footerButton = this.footerButton) != null && !footerButton.isEnabled() && this.footerButton.getVisibility() == 0 && (onClickListenerWhenDisabled = this.footerButton.getOnClickListenerWhenDisabled()) != null) {
            onClickListenerWhenDisabled.onClick(this);
        }
        return super.onTouchEvent(motionEvent);
    }

    void setFooterButton(FooterButton footerButton) {
        this.footerButton = footerButton;
    }

    void setPrimaryButtonStyle(boolean z) {
        this.isPrimaryButtonStyle = z;
    }
}
