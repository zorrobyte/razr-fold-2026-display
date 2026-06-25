package com.motorola.taskbar.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: classes2.dex */
class TooltipPopupRootView extends LinearLayout {
    private static boolean DEBUG = DebugConfig.DEBUG_RECENT_TASK_VIEW;
    private boolean mIsInTouch;
    private boolean mIsOnHover;
    private TooltipPopup mTooltipPopup;

    public TooltipPopupRootView(Context context) {
        super(context);
        this.mIsInTouch = false;
        this.mIsOnHover = false;
    }

    public TooltipPopupRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsInTouch = false;
        this.mIsOnHover = false;
    }

    public TooltipPopupRootView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsInTouch = false;
        this.mIsOnHover = false;
    }

    public TooltipPopupRootView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsInTouch = false;
        this.mIsOnHover = false;
    }

    private void checkIfDismissPopup() {
        if (isOnTouchOrOnHover()) {
            return;
        }
        this.mTooltipPopup.hide(true, true);
    }

    public boolean isOnTouchOrOnHover() {
        return this.mIsOnHover || this.mIsInTouch;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (motionEvent.isFromSource(8194)) {
            int action = motionEvent.getAction();
            if (DEBUG) {
                Log.d("TooltipPopupRootView", "onHoverEvent = " + MotionEvent.actionToString(action));
            }
            if (action != 7) {
                if (action == 9) {
                    this.mIsOnHover = true;
                    if (this.mTooltipPopup.isFocusable()) {
                        this.mTooltipPopup.rePostTimeOut();
                    }
                } else if (action == 10) {
                    this.mIsOnHover = false;
                    checkIfDismissPopup();
                }
            } else if (this.mTooltipPopup.isFocusable()) {
                this.mTooltipPopup.rePostTimeOut();
            }
        }
        return true;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (DEBUG) {
            Log.d("TooltipPopupRootView", "onInterceptTouchEvent = " + MotionEvent.actionToString(motionEvent.getAction()));
        }
        int action = motionEvent.getAction();
        if (action == 1 || action == 3) {
            this.mIsInTouch = false;
            checkIfDismissPopup();
        } else {
            if (this.mTooltipPopup.isFocusable()) {
                this.mTooltipPopup.rePostTimeOut();
            }
            this.mIsInTouch = true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void setTooltipPopup(TooltipPopup tooltipPopup) {
        this.mTooltipPopup = tooltipPopup;
    }
}
