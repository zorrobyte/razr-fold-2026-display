package com.motorola.taskbar.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.Dependency;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$styleable;
import com.motorola.taskbar.bar.TaskBarController;

/* JADX INFO: loaded from: classes2.dex */
public class HoverWrapper {
    private final Context mContext;
    private final int mHoveredColor;
    private final boolean mIsDrawManual;
    private final boolean mIsRoundBg;
    private Paint mPaint;
    private RectF mRectF;
    private String mToolTipText;
    private final TooltipPopupManager mTooltipPopupManager;
    private final int mTooltipPopupOffsetY;
    private final View mView;
    private long mTooltipPopupShowingId = -1;
    private boolean mIsHovered = false;
    private View.OnHoverListener mOnHoverListener = new View.OnHoverListener() { // from class: com.motorola.taskbar.util.HoverWrapper.1
        @Override // android.view.View.OnHoverListener
        public boolean onHover(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == 7) {
                HoverWrapper.this.onHoverMove();
            } else if (action == 9) {
                HoverWrapper.this.onHoverEnter();
            } else if (action == 10) {
                HoverWrapper.this.onHoverExit();
            }
            HoverWrapper.this.mView.invalidate();
            return false;
        }
    };

    private HoverWrapper(View view, String str, boolean z, boolean z2) {
        this.mView = view;
        Context context = view.getContext();
        this.mContext = context;
        this.mTooltipPopupManager = ((TaskBarController) Dependency.get(TaskBarController.class)).getTooltipPopupManager(view);
        this.mToolTipText = str;
        this.mIsDrawManual = z;
        this.mIsRoundBg = z2;
        int color = context.getColor(R$color.inverse_primary);
        this.mHoveredColor = color;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(color);
        this.mRectF = new RectF();
        if (!z) {
            view.setBackgroundResource(R$drawable.taskbar_item_background);
        }
        view.setOnHoverListener(this.mOnHoverListener);
        view.setContentDescription(str);
        this.mTooltipPopupOffsetY = -context.getResources().getDimensionPixelSize(R$dimen.task_bar_tooltip_app_offset_y);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onHoverEnter() {
        this.mIsHovered = true;
        if (this.mTooltipPopupManager != null) {
            if (TextUtils.isEmpty(this.mToolTipText)) {
                return;
            }
            this.mTooltipPopupShowingId = this.mTooltipPopupManager.show(1, this.mView, this.mTooltipPopupOffsetY, false, (CharSequence) this.mToolTipText);
        } else {
            Log.w("HoverWrapper", "onHoverEnter displayId: " + this.mContext.getDisplayId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onHoverExit() {
        this.mIsHovered = false;
        TooltipPopupManager tooltipPopupManager = this.mTooltipPopupManager;
        if (tooltipPopupManager != null) {
            tooltipPopupManager.hide(this.mTooltipPopupShowingId, true);
            return;
        }
        Log.w("HoverWrapper", "onHoverExit displayId: " + this.mContext.getDisplayId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onHoverMove() {
        TooltipPopupManager tooltipPopupManager = this.mTooltipPopupManager;
        if (tooltipPopupManager != null) {
            tooltipPopupManager.rePostTimeOut(this.mTooltipPopupShowingId);
            return;
        }
        Log.w("HoverWrapper", "onHoverMove displayId: " + this.mContext.getDisplayId());
    }

    public static HoverWrapper register(View view, AttributeSet attributeSet, boolean z, boolean z2) {
        TypedArray typedArrayObtainStyledAttributes = view.getContext().obtainStyledAttributes(attributeSet, R$styleable.HoverWrapper, 0, 0);
        CharSequence text = typedArrayObtainStyledAttributes.getText(R$styleable.HoverWrapper_tooltipText);
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        typedArrayObtainStyledAttributes.recycle();
        return new HoverWrapper(view, text.toString(), z, z2);
    }

    public static HoverWrapper register(View view, String str, boolean z, boolean z2) {
        return new HoverWrapper(view, str, z, z2);
    }

    public void drawBackground(Canvas canvas) {
        if (this.mIsDrawManual && this.mIsHovered) {
            if (!this.mIsRoundBg) {
                canvas.drawColor(this.mHoveredColor);
                return;
            }
            this.mRectF.set(0.0f, 0.0f, this.mView.getWidth(), this.mView.getHeight());
            float fMin = Math.min(this.mView.getWidth(), this.mView.getHeight());
            canvas.drawRoundRect(this.mRectF, fMin, fMin, this.mPaint);
        }
    }

    public void forceHideTooltip() {
        onHoverExit();
    }

    public void setToolTipText(String str) {
        this.mToolTipText = str;
        this.mView.setContentDescription(str);
    }
}
