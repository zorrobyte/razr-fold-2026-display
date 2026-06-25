package com.motorola.taskbar.bar;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.KeyButtonView;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.R$style;
import com.motorola.taskbar.bar.more.MoreItemLayout;
import com.motorola.taskbar.bar.more.VolumeLayoutProvider;
import com.motorola.taskbar.model.DisplayWindowManager;
import com.motorola.taskbar.panel.MorePanelLayout;
import com.motorola.taskbar.recent.TaskController;
import com.motorola.taskbar.recent.TaskItem;
import com.motorola.taskbar.sysicons.VolumeSysIconView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class MobileUiTaskBarView extends TaskBarView {
    private List mDisplayIconList;
    private int mDisplayId;
    private List mIconDataList;
    private boolean mImeIconShow;
    private boolean mIsMorePanelViewShowing;
    private int mMoreLayoutId;
    private MorePanelLayout mMorePanelLayout;
    private WindowManager.LayoutParams mMorePanelViewLayoutParams;
    private DisplayWindowManager.WindowViewManager mMorePanelWindowViewManager;
    private MoreItemLayout.OnItemClickedCallback mOnItemClickedCallback;
    private TaskBarIconData mRestartData;
    private int mShowIconCount;
    private final TaskController.TaskChangedListener mTaskChangedListener;
    private final Runnable mTaskChangedRunnable;
    private TaskController mTaskController;
    private final Runnable mTaskScreenshotRunnable;

    public MobileUiTaskBarView(Context context) {
        this(context, null);
    }

    public MobileUiTaskBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MobileUiTaskBarView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MobileUiTaskBarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMoreLayoutId = R$layout.mobile_more_button;
        this.mImeIconShow = false;
        this.mOnItemClickedCallback = new MoreItemLayout.OnItemClickedCallback() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.3
            @Override // com.motorola.taskbar.bar.more.MoreItemLayout.OnItemClickedCallback
            public void OnItemClicked() {
                MobileUiTaskBarView.this.hideMorePanel();
            }
        };
        this.mTaskChangedListener = new TaskController.TaskChangedListener() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.4
            @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
            public void onTaskInfoChanged(int i3, TaskItem taskItem) {
                if (i3 == MobileUiTaskBarView.this.mDisplayId) {
                    MobileUiTaskBarView.this.updateRestartView();
                }
            }
        };
        this.mTaskChangedRunnable = new Runnable() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.5
            @Override // java.lang.Runnable
            public void run() {
                MobileUiTaskBarView mobileUiTaskBarView = MobileUiTaskBarView.this;
                mobileUiTaskBarView.removeCallbacks(mobileUiTaskBarView.mTaskChangedRunnable);
                MobileUiTaskBarView mobileUiTaskBarView2 = MobileUiTaskBarView.this;
                mobileUiTaskBarView2.post(mobileUiTaskBarView2.mTaskChangedRunnable);
            }
        };
        this.mTaskScreenshotRunnable = new Runnable() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.6
            @Override // java.lang.Runnable
            public void run() {
                MobileUiTaskBarView mobileUiTaskBarView = MobileUiTaskBarView.this;
                mobileUiTaskBarView.mScreenshotController.takeScreenshot(2, ((FrameLayout) mobileUiTaskBarView).mContext.getDisplayId());
            }
        };
        this.mTaskController = (TaskController) Dependency.get(TaskController.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideMorePanel() {
        MorePanelLayout morePanelLayout = this.mMorePanelLayout;
        if (morePanelLayout != null) {
            this.mIsMorePanelViewShowing = false;
            this.mMorePanelWindowViewManager.removeView(morePanelLayout);
            this.mMorePanelLayout = null;
        }
    }

    private void initData() {
        this.mRestartData = new TaskBarIconData(R$layout.mobile_restart_button, R$drawable.ic_mobileui_restart, R$string.restart_decription, new Runnable() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.1
            @Override // java.lang.Runnable
            public void run() {
                MobileUiTaskBarView.this.mTaskBarButtonClickHelper.toggleRestartDialog();
            }
        });
        this.mIconDataList = new ArrayList() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.2
            {
                add(new TaskBarIconData(R$layout.mobile_back_button, R$drawable.ic_mobileui_back, R$string.back_description, new Runnable() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MobileUiTaskBarView mobileUiTaskBarView = MobileUiTaskBarView.this;
                        mobileUiTaskBarView.mTaskBarButtonClickHelper.triggerBackButton(mobileUiTaskBarView.mBackButton);
                    }
                }));
                add(new TaskBarIconData(R$layout.mobile_home_button, R$drawable.ic_mobileui_home, R$string.home_description, new Runnable() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        MobileUiTaskBarView.this.mTaskBarButtonClickHelper.handleAirRemoteHome();
                    }
                }));
                add(new TaskBarIconData(R$layout.mobile_recent_button, R$drawable.ic_mobileui_recents, R$string.recents_description, new Runnable() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.2.3
                    @Override // java.lang.Runnable
                    public void run() {
                        MobileUiTaskBarView.this.mTaskBarButtonClickHelper.toggleRecents();
                    }
                }));
                add(new TaskBarIconData(R$layout.mobile_setting_button, R$drawable.ic_mobileui_display_settings, R$string.desktop_setting_description, new Runnable() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.2.4
                    @Override // java.lang.Runnable
                    public void run() {
                        MobileUiTaskBarView.this.mTaskBarButtonClickHelper.startSettingsActivity();
                    }
                }));
                if (MobileUiTaskBarView.this.mImeIconShow) {
                    add(new TaskBarIconData(R$layout.mobile_ime_button, R$drawable.ic_keyboard_24px, R$string.ime_decription, new Runnable() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.2.5
                        @Override // java.lang.Runnable
                        public void run() {
                            MobileUiTaskBarView.this.mTaskBarButtonClickHelper.showIMEDialog();
                        }
                    }));
                }
                add(MobileUiTaskBarView.this.mRestartData);
                add(new TaskBarIconData(R$layout.mobile_screenshot_button, R$drawable.mobileui_screenshot, R$string.tooltips_screenshot_tools, new Runnable() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.2.6
                    @Override // java.lang.Runnable
                    public void run() {
                        MobileUiTaskBarView mobileUiTaskBarView = MobileUiTaskBarView.this;
                        mobileUiTaskBarView.removeCallbacks(mobileUiTaskBarView.mTaskScreenshotRunnable);
                        MobileUiTaskBarView mobileUiTaskBarView2 = MobileUiTaskBarView.this;
                        mobileUiTaskBarView2.postDelayed(mobileUiTaskBarView2.mTaskScreenshotRunnable, 100L);
                    }
                }));
                add(new TaskBarIconData(R$layout.mobile_lock_button, R$drawable.ic_mobileui_lock, R$string.lock_description, new Runnable() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.2.7
                    @Override // java.lang.Runnable
                    public void run() {
                        MobileUiTaskBarView.this.mTaskBarButtonClickHelper.lockDevice();
                    }
                }));
                if (MobileUiTaskBarView.this.mAudioIconShow) {
                    add(new TaskBarIconData(R$layout.mobile_volume_button, R$drawable.ic_mobileui_volume, R$string.volume_description, new VolumeLayoutProvider(((FrameLayout) MobileUiTaskBarView.this).mContext), new Runnable() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.2.8
                        @Override // java.lang.Runnable
                        public void run() {
                            MobileUiTaskBarView mobileUiTaskBarView = MobileUiTaskBarView.this;
                            mobileUiTaskBarView.mTaskBarButtonClickHelper.requestSwitchVolumeDialog(mobileUiTaskBarView.mTaskBarContentView);
                        }
                    }));
                }
                add(new TaskBarIconData(R$layout.mobile_notification_button, R$drawable.ic_mobileui_notifications, R$string.notification_description, new Runnable() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView.2.9
                    @Override // java.lang.Runnable
                    public void run() {
                        MobileUiTaskBarView.this.mTaskBarButtonClickHelper.requestSwitchNotificationPanel();
                    }
                }));
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reLayout$0(View view) {
        requestSwitchMorePanel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reLayout$1(View view) {
        if (this.mVolumeSysIconView.onClick()) {
            return;
        }
        this.mTaskBarButtonClickHelper.requestSwitchVolumeDialog(this.mTaskBarContentView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reLayout$2(View view) {
        this.mScreenshotController.takeScreenshot(2, ((FrameLayout) this).mContext.getDisplayId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showMorePanel$3(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 4) {
            return false;
        }
        hideMorePanel();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRestartView$4(View view) {
        this.mTaskBarButtonClickHelper.toggleRestartDialog();
    }

    private void reLayout() {
        initData();
        if (this.mTaskBarContentView == null) {
            return;
        }
        this.mDisplayIconList = new ArrayList(this.mIconDataList);
        this.mTaskBarButtonClickHelper = new TaskBarButtonClickHelper(getContext(), getResources().getDimensionPixelOffset(R$dimen.more_panel_offset_y));
        this.mDisplayId = getContext().getDisplayId();
        this.mTaskBarContentView.removeAllViews();
        Resources resources = getContext().getResources();
        int i = resources.getDisplayMetrics().widthPixels;
        int dimensionPixelSize = resources.getDimensionPixelSize(R$dimen.tablet_taskbar_appitem_width) + (resources.getDimensionPixelSize(R$dimen.mobile_taskbar_layout_horizontal_padding) * 2);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R$dimen.mobile_taskbar_divider_width);
        int size = this.mIconDataList.size();
        int iMin = Math.min(size, (i - dimensionPixelSize2) / dimensionPixelSize);
        this.mShowIconCount = iMin;
        boolean z = iMin != size;
        if (z) {
            iMin--;
        }
        this.mShowIconCount = iMin;
        if (z) {
            List list = this.mDisplayIconList;
            list.add(iMin - 1, (TaskBarIconData) list.get(size - 1));
            List list2 = this.mDisplayIconList;
            list2.remove(list2.size() - 1);
        }
        for (int i2 = 0; i2 < this.mShowIconCount; i2++) {
            this.mLayoutInflater.inflate(((TaskBarIconData) this.mDisplayIconList.get(i2)).layoutId, this.mTaskBarContentView, true);
            if (i2 == 2) {
                this.mLayoutInflater.inflate(R$layout.mobile_divider, this.mTaskBarContentView, true);
                this.mLayoutInflater.inflate(R$layout.mobile_empty_view, this.mTaskBarContentView, true);
            }
        }
        if (z) {
            this.mLayoutInflater.inflate(this.mMoreLayoutId, this.mTaskBarContentView, true);
        }
        initView();
        KeyButtonView keyButtonView = this.mMoreButton;
        if (keyButtonView != null) {
            keyButtonView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$reLayout$0(view);
                }
            });
        }
        VolumeSysIconView volumeSysIconView = this.mVolumeSysIconView;
        if (volumeSysIconView != null) {
            volumeSysIconView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$reLayout$1(view);
                }
            });
        }
        KeyButtonView keyButtonView2 = this.mScreenshotButton;
        if (keyButtonView2 != null) {
            keyButtonView2.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$reLayout$2(view);
                }
            });
        }
        updateRestartView();
    }

    private void requestSwitchMorePanel() {
        if (this.mMorePanelWindowViewManager == null || !this.mIsMorePanelViewShowing) {
            showMorePanel();
        } else {
            hideMorePanel();
        }
    }

    private void showMorePanel() {
        if (this.mMorePanelWindowViewManager == null) {
            this.mMorePanelWindowViewManager = DisplayWindowManager.getWindowViewManager(this.mDisplayId, 2041);
        }
        Resources resources = this.mMorePanelWindowViewManager.windowContext.getResources();
        Log.d("MobileUiTaskBarView", "showMorePanel: " + resources.getConfiguration());
        if (this.mMorePanelLayout == null) {
            this.mMorePanelLayout = (MorePanelLayout) LayoutInflater.from(this.mMorePanelWindowViewManager.windowContext).inflate(R$layout.more_panel, (ViewGroup) null);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(getResources().getDimensionPixelSize(R$dimen.more_panel_width), Math.min((resources.getDisplayMetrics().heightPixels - getHeight()) - getContext().getResources().getDimensionPixelSize(R$dimen.more_panel_offset_y), ((this.mDisplayIconList.size() - this.mShowIconCount) * resources.getDimensionPixelOffset(R$dimen.more_panel_item_height)) + (resources.getDimensionPixelOffset(R$dimen.more_panel_vertical_padding) * 2)), 2041, R.interpolator.launch_task_micro_alpha, -3);
            this.mMorePanelViewLayoutParams = layoutParams;
            layoutParams.setFitInsetsTypes(0);
            if (getLayoutDirection() == 0) {
                this.mMorePanelViewLayoutParams.gravity = 85;
            } else {
                this.mMorePanelViewLayoutParams.gravity = 83;
            }
            WindowManager.LayoutParams layoutParams2 = this.mMorePanelViewLayoutParams;
            layoutParams2.windowAnimations = R$style.Animation_MorePanel;
            layoutParams2.setTitle("DesktopMorePanel: " + this.mDisplayId);
            this.mMorePanelLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView$$ExternalSyntheticLambda4
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f$0.lambda$showMorePanel$3(view, motionEvent);
                }
            });
        }
        List list = this.mDisplayIconList;
        ArrayList arrayList = new ArrayList(list.subList(this.mShowIconCount, list.size()));
        Collections.reverse(arrayList);
        this.mMorePanelLayout.addItemViews(arrayList, this.mOnItemClickedCallback);
        this.mMorePanelViewLayoutParams.x = getContext().getResources().getDimensionPixelSize(R$dimen.more_panel_offset_x);
        this.mMorePanelViewLayoutParams.y = getHeight() + getContext().getResources().getDimensionPixelSize(R$dimen.more_panel_offset_y);
        this.mIsMorePanelViewShowing = true;
        this.mMorePanelWindowViewManager.addView(this.mMorePanelLayout, this.mMorePanelViewLayoutParams);
        updateRestartView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRestartView() {
        View view;
        boolean zIsBlackAppRunningTop = isBlackAppRunningTop();
        KeyButtonView keyButtonView = this.mRestartButton;
        if (keyButtonView != null) {
            keyButtonView.setOnClickListener(!zIsBlackAppRunningTop ? new View.OnClickListener() { // from class: com.motorola.taskbar.bar.MobileUiTaskBarView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$updateRestartView$4(view2);
                }
            } : null);
            this.mRestartButton.setDarkIntensity(zIsBlackAppRunningTop ? 0.7f : this.mDarkIntensity);
            this.mRestartButton.setNeedHover(!zIsBlackAppRunningTop);
            this.mRestartButton.setToolTipText(zIsBlackAppRunningTop ? "" : getResources().getString(R$string.restart_decription));
        }
        TaskBarIconData taskBarIconData = this.mRestartData;
        if (taskBarIconData == null || (view = taskBarIconData.view) == null) {
            return;
        }
        view.setEnabled(!zIsBlackAppRunningTop);
    }

    @Override // com.motorola.taskbar.bar.TaskBarView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mTaskController.addCallback(this.mTaskChangedListener);
        updateRestartView();
    }

    @Override // com.motorola.taskbar.bar.TaskBarView
    protected void onAudioStatusChanged() {
        super.onAudioStatusChanged();
        MorePanelLayout morePanelLayout = this.mMorePanelLayout;
        if (morePanelLayout != null && morePanelLayout.isAttachedToWindow()) {
            hideMorePanel();
        }
        reLayout();
    }

    @Override // com.motorola.taskbar.bar.TaskBarView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MorePanelLayout morePanelLayout = this.mMorePanelLayout;
        if (morePanelLayout != null && morePanelLayout.isAttachedToWindow()) {
            hideMorePanel();
        }
        this.mTaskController.removeCallback(this.mTaskChangedListener);
        removeCallbacks(this.mTaskChangedRunnable);
        removeCallbacks(this.mTaskScreenshotRunnable);
    }

    @Override // com.motorola.taskbar.bar.TaskBarView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        reLayout();
    }

    @Override // com.motorola.taskbar.bar.TaskBarView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean zOnInterceptTouchEvent = super.onInterceptTouchEvent(motionEvent);
        if (this.mIsMorePanelViewShowing && motionEvent.getAction() == 0) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int[] iArr = new int[2];
            this.mBackButton.getLocationInWindow(iArr);
            int i = iArr[0];
            Rect rect = new Rect(i, iArr[1], this.mBackButton.getWidth() + i, iArr[1] + this.mBackButton.getHeight());
            int[] iArr2 = new int[2];
            this.mMoreButton.getLocationInWindow(iArr2);
            int i2 = iArr2[0];
            Rect rect2 = new Rect(i2, iArr2[1], this.mMoreButton.getWidth() + i2, iArr2[1] + this.mMoreButton.getHeight());
            if (rect.contains(x, y)) {
                hideMorePanel();
                return true;
            }
            if (!rect2.contains(x, y)) {
                hideMorePanel();
            }
        }
        return zOnInterceptTouchEvent;
    }

    @Override // com.motorola.taskbar.bar.TaskBarView
    public void setTaskBarImeSwitchButtonVisible(boolean z) {
        if (this.mImeIconShow == z) {
            return;
        }
        this.mImeIconShow = z;
        MorePanelLayout morePanelLayout = this.mMorePanelLayout;
        if (morePanelLayout != null && morePanelLayout.isAttachedToWindow()) {
            hideMorePanel();
        }
        reLayout();
    }
}
