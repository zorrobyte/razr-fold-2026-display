package com.motorola.taskbar.qsnotification;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: classes2.dex */
class QsNotificationTooltipPopupRootView extends LinearLayout {
    private static boolean DEBUG = !Build.IS_USER;
    private boolean mIsInTouch;
    private boolean mIsOnHover;
    private QsNotificationTooltipPopup mQsNotificationTooltipPopup;

    public QsNotificationTooltipPopupRootView(Context context) {
        super(context);
        this.mIsInTouch = false;
        this.mIsOnHover = false;
    }

    public QsNotificationTooltipPopupRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsInTouch = false;
        this.mIsOnHover = false;
    }

    public QsNotificationTooltipPopupRootView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsInTouch = false;
        this.mIsOnHover = false;
    }

    public QsNotificationTooltipPopupRootView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsInTouch = false;
        this.mIsOnHover = false;
    }

    private void checkIfDismissPopup() {
        if (isOnTouchOrOnHover()) {
            return;
        }
        this.mQsNotificationTooltipPopup.hide(true, true);
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
                    if (this.mQsNotificationTooltipPopup.isFocusable()) {
                        this.mQsNotificationTooltipPopup.rePostTimeOut();
                    }
                } else if (action == 10) {
                    this.mIsOnHover = false;
                    checkIfDismissPopup();
                }
            } else if (this.mQsNotificationTooltipPopup.isFocusable()) {
                this.mQsNotificationTooltipPopup.rePostTimeOut();
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
            if (this.mQsNotificationTooltipPopup.isFocusable()) {
                this.mQsNotificationTooltipPopup.rePostTimeOut();
            }
            this.mIsInTouch = true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void setTooltipPopup(QsNotificationTooltipPopup qsNotificationTooltipPopup) {
        this.mQsNotificationTooltipPopup = qsNotificationTooltipPopup;
    }
}
