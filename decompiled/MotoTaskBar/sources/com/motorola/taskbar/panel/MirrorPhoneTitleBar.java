package com.motorola.taskbar.panel;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;

/* JADX INFO: loaded from: classes2.dex */
public class MirrorPhoneTitleBar extends LinearLayout {
    private float mDownRawX;
    private float mDownRawY;
    private boolean mIsClickAction;
    private OnPositionUpdateListener mOnPositionUpdateListener;
    private float mStartX;
    private float mStartY;
    private int mTouchSlop;

    public interface OnPositionUpdateListener {
        void onPositionUpdated(int i, int i2, boolean z);
    }

    public MirrorPhoneTitleBar(Context context) {
        super(context);
    }

    public MirrorPhoneTitleBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void updatePosition(float f, float f2, boolean z) {
        this.mOnPositionUpdateListener.onPositionUpdated((int) (f - this.mStartX), (int) (f2 - this.mStartY), z);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mIsClickAction = true;
            this.mStartX = motionEvent.getX();
            this.mStartY = motionEvent.getY();
            this.mDownRawX = rawX;
            this.mDownRawY = rawY;
        } else if (action == 1) {
            updatePosition(rawX, rawY, true);
            this.mStartY = 0.0f;
            this.mStartX = 0.0f;
        } else if (action != 2) {
            Log.d("MirrorPhoneTitleBar", "onTouchEvent: " + MotionEvent.actionToString(motionEvent.getAction()));
        } else {
            if (this.mIsClickAction && (Math.abs(this.mDownRawX - rawX) > this.mTouchSlop || Math.abs(this.mDownRawY - rawY) > this.mTouchSlop)) {
                this.mIsClickAction = false;
            }
            updatePosition(rawX, rawY, false);
        }
        if (this.mIsClickAction) {
            super.onTouchEvent(motionEvent);
        }
        return true;
    }

    public void onUiModeChanged() {
        ((TextView) findViewById(R$id.title_text)).setTextColor(getContext().getColor(R$color.mirror_phone_titlebar_text));
        ((ImageView) findViewById(R$id.title_close_icon)).setImageResource(R$drawable.ic_tilebar_close_24px);
    }

    public void setOnPositionUpdateListener(OnPositionUpdateListener onPositionUpdateListener) {
        this.mOnPositionUpdateListener = onPositionUpdateListener;
    }
}
