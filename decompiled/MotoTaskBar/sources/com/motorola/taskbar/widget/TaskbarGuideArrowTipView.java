package com.motorola.taskbar.widget;

import android.content.Context;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$style;
import com.motorola.taskbar.model.DisplayWindowManager;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class TaskbarGuideArrowTipView extends LinearLayout {
    private int mDirection;
    private DisplayWindowManager.WindowViewManager mGuideTipWindowViewManager;
    protected boolean mIsOpen;
    private final WindowManager.LayoutParams mLayoutParams;
    private Consumer mOnClosed;

    public TaskbarGuideArrowTipView(Context context, int i, int i2) {
        super(context, null, 0);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mLayoutParams = layoutParams;
        this.mGuideTipWindowViewManager = DisplayWindowManager.getWindowViewManager(context.getDisplayId(), 2024);
        this.mDirection = i;
        layoutParams.setTitle("TaskbarGuideArrowTipView: " + context.getDisplay().getDisplayId());
        layoutParams.packageName = context.getOpPackageName();
        layoutParams.type = 2024;
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = R$style.Animation_Tooltip;
        layoutParams.flags = 8650760;
        layoutParams.privateFlags = 16;
        layoutParams.gravity = 8388659;
        layoutParams.setFitInsetsTypes(0);
        init(context, i2);
    }

    private void init(Context context, int i) {
        LinearLayout.inflate(context, i, this);
        setOrientation(1);
        findViewById(R$id.dismiss).setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.widget.TaskbarGuideArrowTipView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$init$0(view);
            }
        });
        View viewFindViewById = findViewById(R$id.arrow);
        ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
        ShapeDrawable shapeDrawable = new ShapeDrawable(TriangleShape.create(layoutParams.width, layoutParams.height, this.mDirection));
        Paint paint = shapeDrawable.getPaint();
        paint.setColor(context.getResources().getColor(R$color.taskbar_icon_guide_background));
        paint.setPathEffect(new CornerPathEffect(context.getResources().getDimension(R$dimen.arrow_toast_corner_radius)));
        viewFindViewById.setBackground(shapeDrawable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        close(true, true);
    }

    public void close(boolean z, boolean z2) {
        DisplayWindowManager.WindowViewManager windowViewManager;
        if (!this.mIsOpen || (windowViewManager = this.mGuideTipWindowViewManager) == null) {
            return;
        }
        if (z) {
            windowViewManager.removeView(this);
        } else {
            windowViewManager.removeViewImmediate(this);
        }
        Consumer consumer = this.mOnClosed;
        if (consumer != null) {
            consumer.accept(Boolean.valueOf(z2));
        }
        this.mIsOpen = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 4) {
            close(true, false);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean isOpened() {
        return this.mIsOpen;
    }

    public TaskbarGuideArrowTipView setOnClosedCallback(Consumer consumer) {
        this.mOnClosed = consumer;
        return this;
    }

    public TaskbarGuideArrowTipView show(String str, int i, int i2, int i3, int i4, int i5, boolean z) {
        if (!this.mIsOpen && this.mGuideTipWindowViewManager != null) {
            ((TextView) findViewById(R$id.text)).setText(str);
            int i6 = R$id.arrow;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) findViewById(i6).getLayoutParams();
            layoutParams.gravity = i;
            if (i == 8388613) {
                layoutParams.setMarginEnd(i3);
            } else if (i == 8388611) {
                layoutParams.setMarginStart(i2);
            }
            requestLayout();
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            measure(iMakeMeasureSpec, iMakeMeasureSpec);
            int measuredHeight = getMeasuredHeight();
            int measuredWidth = getMeasuredWidth();
            if (i == 8388613) {
                if (z) {
                    this.mLayoutParams.x = i4 - (i3 + (findViewById(i6).getMeasuredWidth() / 2));
                } else {
                    this.mLayoutParams.x = i4 - ((measuredWidth - i3) - (findViewById(i6).getMeasuredWidth() / 2));
                }
            } else if (i == 8388611) {
                if (z) {
                    this.mLayoutParams.x = i4 - ((measuredWidth - i2) - (findViewById(i6).getMeasuredWidth() / 2));
                } else {
                    this.mLayoutParams.x = i4 - (i2 + (findViewById(i6).getMeasuredWidth() / 2));
                }
            }
            if (this.mDirection == 3) {
                this.mLayoutParams.y = i5 - measuredHeight;
            } else {
                this.mLayoutParams.y = i5;
            }
            try {
                this.mGuideTipWindowViewManager.addView(this, this.mLayoutParams);
                this.mIsOpen = true;
                return this;
            } catch (WindowManager.InvalidDisplayException e) {
                e.printStackTrace();
                try {
                    this.mGuideTipWindowViewManager.removeView(this);
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return this;
    }
}
