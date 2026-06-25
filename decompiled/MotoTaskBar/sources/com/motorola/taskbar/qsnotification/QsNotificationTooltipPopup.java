package com.motorola.taskbar.qsnotification;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.motorola.plugin.core.utils.TimeoutRemoteCaller;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.R$style;

/* JADX INFO: loaded from: classes2.dex */
class QsNotificationTooltipPopup {
    private ConfigurationController.ConfigurationListener mConfigurationListener;
    private QsNotificationTooltipPopupRootView mContentView;
    private final Context mContext;
    private ViewGroup mCustomContainer;
    private boolean mFocusable;
    private Handler mHandler;
    private final WindowManager.LayoutParams mLayoutParams;
    private ListView mListView;
    private TextView mMessageView;
    private final int mMinGap;
    private long mShowingId;
    private final int[] mTmpAnchorPos;
    private final Rect mTmpDisplayFrame;

    public QsNotificationTooltipPopup(Context context) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mLayoutParams = layoutParams;
        this.mTmpDisplayFrame = new Rect();
        this.mTmpAnchorPos = new int[2];
        this.mFocusable = false;
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.motorola.taskbar.qsnotification.QsNotificationTooltipPopup.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onUiModeChanged() {
                QsNotificationTooltipPopup.this.hide(true);
                QsNotificationTooltipPopup.this.reInflateView();
            }
        };
        this.mHandler = new Handler() { // from class: com.motorola.taskbar.qsnotification.QsNotificationTooltipPopup.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    if (QsNotificationTooltipPopup.this.mContentView.isOnTouchOrOnHover()) {
                        return;
                    }
                    QsNotificationTooltipPopup.this.hide(true);
                } else {
                    if (i != 2) {
                        return;
                    }
                    Log.d("TooltipPopup", "TIME OUT: " + QsNotificationTooltipPopup.this.mShowingId);
                    QsNotificationTooltipPopup.this.hide(true);
                }
            }
        };
        this.mContext = context;
        this.mMinGap = context.getResources().getDimensionPixelSize(R$dimen.tooltip_min_gap);
        reInflateView();
        layoutParams.setTitle(context.getString(R$string.tooltip_popup_title));
        layoutParams.type = 2041;
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = R$style.Animation_Tooltip;
        layoutParams.flags = 8;
        layoutParams.privateFlags = 16;
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this.mConfigurationListener);
    }

    private void beginShow(boolean z) {
        hide(true);
        this.mFocusable = z;
    }

    private void computeAndShow(long j, int i, View view, int i2, int i3) {
        this.mShowingId = j;
        this.mHandler.removeMessages(1);
        computePosition(view, i2, i3, i, this.mLayoutParams);
        try {
            ((WindowManager) this.mContext.getSystemService("window")).addView(this.mContentView, this.mLayoutParams);
            rePostTimeOut();
        } catch (WindowManager.InvalidDisplayException e) {
            e.printStackTrace();
        }
    }

    private void computePosition(View view, int i, int i2, int i3, WindowManager.LayoutParams layoutParams) {
        View windowView = WindowManagerGlobal.getInstance().getWindowView(view.getApplicationWindowToken());
        if (windowView == null) {
            Log.e("TooltipPopup", "Cannot find app view");
            return;
        }
        windowView.getWindowVisibleDisplayFrame(this.mTmpDisplayFrame);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mContentView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
        int measuredHeight = this.mContentView.getMeasuredHeight();
        int measuredWidth = this.mContentView.getMeasuredWidth();
        layoutParams.gravity = 51;
        view.getLocationOnScreen(this.mTmpAnchorPos);
        int width = (this.mTmpAnchorPos[0] + (view.getWidth() / 2)) - (measuredWidth / 2);
        if ((i3 & 4) != 0) {
            width = this.mTmpAnchorPos[0];
        } else if ((i3 & 8) != 0) {
            width = (this.mTmpAnchorPos[0] + view.getWidth()) - measuredWidth;
        }
        int i4 = width + i;
        int i5 = this.mMinGap;
        if (i4 < i5) {
            i4 = i5;
        } else {
            int i6 = i4 + measuredWidth;
            int i7 = this.mTmpDisplayFrame.right;
            if (i6 >= i7 - i5) {
                i4 = (i7 - measuredWidth) - i5;
            }
        }
        int i8 = this.mTmpAnchorPos[1];
        int measuredHeight2 = (i8 - measuredHeight) + i2;
        if ((i3 & 2) != 0) {
            measuredHeight2 = i8 + view.getMeasuredHeight() + i2;
        }
        layoutParams.x = i4;
        layoutParams.y = measuredHeight2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reInflateView() {
        QsNotificationTooltipPopupRootView qsNotificationTooltipPopupRootView = (QsNotificationTooltipPopupRootView) LayoutInflater.from(this.mContext).inflate(R$layout.qs_tooltip, (ViewGroup) null);
        this.mContentView = qsNotificationTooltipPopupRootView;
        qsNotificationTooltipPopupRootView.setTooltipPopup(this);
        this.mMessageView = (TextView) this.mContentView.findViewById(R$id.message);
        this.mListView = (ListView) this.mContentView.findViewById(R$id.list_view);
        this.mCustomContainer = (ViewGroup) this.mContentView.findViewById(R$id.custom_container);
    }

    private void reset() {
        this.mMessageView.setVisibility(8);
        this.mListView.setVisibility(8);
        this.mCustomContainer.removeAllViews();
        this.mCustomContainer.setVisibility(8);
        this.mListView.setOnItemClickListener(null);
        this.mListView.setAdapter((ListAdapter) null);
        this.mMessageView.setOnClickListener(null);
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public long getShowingId() {
        return this.mShowingId;
    }

    public void hide(boolean z) {
        hide(z, false);
    }

    public void hide(boolean z, boolean z2) {
        if (isShowing()) {
            WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
            this.mHandler.removeMessages(1);
            Log.d("TooltipPopup", "hide, force = " + z + "; mFocusable = " + this.mFocusable + "; forceDelay = " + z2);
            if (z2 && this.mFocusable) {
                this.mHandler.sendEmptyMessageDelayed(1, 500L);
                return;
            }
            if (z || !this.mFocusable) {
                reset();
                windowManager.removeView(this.mContentView);
                this.mShowingId = -1L;
            } else {
                if (this.mContentView.isOnTouchOrOnHover()) {
                    return;
                }
                this.mHandler.sendEmptyMessageDelayed(1, 500L);
            }
        }
    }

    public boolean isFocusable() {
        return this.mFocusable;
    }

    public boolean isShowing() {
        return this.mContentView.getParent() != null;
    }

    public void rePostTimeOut() {
        Log.d("TooltipPopup", "rePostTimeOut: " + this.mShowingId);
        this.mHandler.removeMessages(2);
        this.mHandler.sendEmptyMessageDelayed(2, TimeoutRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
    }

    public void show(long j, int i, View view, int i2, int i3, CharSequence charSequence, View.OnClickListener onClickListener) {
        beginShow(true);
        this.mMessageView.setVisibility(0);
        this.mMessageView.setText(charSequence);
        this.mMessageView.setOnClickListener(onClickListener);
        computeAndShow(j, i, view, i2, i3);
    }
}
