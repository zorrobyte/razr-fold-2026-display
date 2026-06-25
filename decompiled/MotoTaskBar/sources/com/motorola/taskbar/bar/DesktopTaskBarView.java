package com.motorola.taskbar.bar;

import android.R;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.statusbar.policy.KeyButtonView;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.R$style;
import com.motorola.taskbar.bar.TaskBarView;
import com.motorola.taskbar.bar.more.ClockLayoutProvider;
import com.motorola.taskbar.bar.more.DateLayoutProvider;
import com.motorola.taskbar.bar.more.MoreItemLayout;
import com.motorola.taskbar.model.DisplayWindowManager;
import com.motorola.taskbar.panel.MorePanelLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopTaskBarView extends TaskBarView {
    private Runnable mArrayIconRunnable;
    private TaskBarIconData mClockIconDate;
    private ConnectivityManager mConnectivityManager;
    private TaskBarIconData mDateIconDate;
    private final ConnectivityManager.NetworkCallback mDefaultNetworkCallback;
    private List mDisplayIconList;
    private int mDisplayId;
    private List mDividerList;
    private ViewGroup mFlexibleIconsLayout;
    private boolean mIsEthernetConnected;
    private boolean mIsMorePanelViewShowing;
    private View mMoreLayout;
    private List mMorePanelIconList;
    private MorePanelLayout mMorePanelLayout;
    private WindowManager.LayoutParams mMorePanelViewLayoutParams;
    private DisplayWindowManager.WindowViewManager mMorePanelWindowViewManager;
    private MoreItemLayout.OnItemClickedCallback mOnItemClickedCallback;
    private Resources mResources;
    private View mRightFixedLayout;
    private boolean mShowClockInMore;
    private boolean mShowDateInMore;
    private int mShowIconCount;
    private int mUnSpecified;

    public DesktopTaskBarView(Context context) {
        this(context, null);
    }

    public DesktopTaskBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DesktopTaskBarView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DesktopTaskBarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMorePanelIconList = new ArrayList();
        this.mDividerList = new ArrayList();
        this.mShowDateInMore = false;
        this.mShowClockInMore = false;
        this.mOnItemClickedCallback = new MoreItemLayout.OnItemClickedCallback() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView.4
            @Override // com.motorola.taskbar.bar.more.MoreItemLayout.OnItemClickedCallback
            public void OnItemClicked() {
                DesktopTaskBarView.this.hideMorePanel();
            }
        };
        this.mArrayIconRunnable = new Runnable() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView.5
            @Override // java.lang.Runnable
            public void run() {
                DesktopTaskBarView.this.resetView();
                DesktopTaskBarView desktopTaskBarView = DesktopTaskBarView.this;
                desktopTaskBarView.mIsEthernetConnected = desktopTaskBarView.isEthernetConnected();
                DesktopTaskBarView.this.mMoreLayout.setVisibility(DesktopTaskBarView.this.needShowMoreIcon() ? 0 : 8);
                DesktopTaskBarView.this.arrangeDateClock();
                DesktopTaskBarView.this.arrangeDynamicSysIcons();
                DesktopTaskBarView.this.arrangeOtherIcons();
                DesktopTaskBarView.this.arrangeRecentLayout();
            }
        };
        this.mDefaultNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView.6
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                if (networkCapabilities.hasTransport(3) != DesktopTaskBarView.this.mIsEthernetConnected) {
                    DesktopTaskBarView.this.arrangeIcons();
                }
            }
        };
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        initData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void arrangeDateClock() {
        if (getFlexibleIconsSpareWidth() > 0) {
            return;
        }
        this.mShowDateInMore = true;
        this.mDateView.setVisibility(8);
        if (getFlexibleIconsSpareWidth() > 0) {
            return;
        }
        this.mShowClockInMore = true;
        this.mClockView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void arrangeDynamicSysIcons() {
        int flexibleIconsSpareWidth = getFlexibleIconsSpareWidth();
        int childCount = this.mDynamicSysiconsContainer.getChildCount();
        int iMin = Math.min(childCount, flexibleIconsSpareWidth / this.mResources.getDimensionPixelOffset(R$dimen.desktop_taskbar_left_icon_total_width));
        int i = 0;
        while (i < childCount) {
            boolean z = i <= iMin + (-1);
            View childAt = this.mDynamicSysiconsContainer.getChildAt(i);
            childAt.setVisibility(z ? 0 : 8);
            String str = (String) childAt.getTag(R$id.dynamic_sys_icon_key);
            TaskBarView.DesktopStatusBarIconInfo desktopStatusBarIconInfo = (TaskBarView.DesktopStatusBarIconInfo) this.mDesktopStatusBarIconInfoMap.get(str);
            if (!z && desktopStatusBarIconInfo != null && desktopStatusBarIconInfo.getSwitchIntent() != null) {
                this.mMorePanelIconList.add(new TaskBarIconData((TaskBarView.DesktopStatusBarIconInfo) this.mDesktopStatusBarIconInfoMap.get(str)));
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void arrangeIcons() {
        removeCallbacks(this.mArrayIconRunnable);
        postDelayed(this.mArrayIconRunnable, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void arrangeOtherIcons() {
        this.mFlexibleIconsLayout.removeAllViews();
        this.mShowIconCount = Math.min(this.mDisplayIconList.size(), (getFlexibleIconsSpareWidth() - getDynamicSysIconWidth()) / this.mResources.getDimensionPixelOffset(R$dimen.desktop_taskbar_left_icon_total_width));
        for (int i = 0; i < this.mDisplayIconList.size(); i++) {
            if ((((TaskBarIconData) this.mDisplayIconList.get(i)).layoutId != R$layout.desktop_mirror_phone_button || canShowMirrorPhoneButton()) && ((!this.mIsEthernetConnected || ((TaskBarIconData) this.mDisplayIconList.get(i)).layoutId != R$layout.desktop_wifi_button) && (this.mIsEthernetConnected || ((TaskBarIconData) this.mDisplayIconList.get(i)).layoutId != R$layout.desktop_ethernet_button))) {
                if (i <= this.mShowIconCount - 1) {
                    this.mLayoutInflater.inflate(((TaskBarIconData) this.mDisplayIconList.get(i)).layoutId, this.mFlexibleIconsLayout, true);
                } else {
                    this.mMorePanelIconList.add((TaskBarIconData) this.mDisplayIconList.get(i));
                }
            }
        }
        super.initView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void arrangeRecentLayout() {
        int i = this.mResources.getDisplayMetrics().widthPixels;
        int dimensionPixelOffset = this.mResources.getDimensionPixelOffset(R$dimen.desktop_taskbar_layout_start_padding) * 2;
        ViewGroup viewGroup = this.mTaskbarLeftContain;
        int i2 = this.mUnSpecified;
        viewGroup.measure(i2, i2);
        int measuredWidth = this.mTaskbarLeftContain.getMeasuredWidth();
        ViewGroup viewGroup2 = this.mTaskbarRightContain;
        int i3 = this.mUnSpecified;
        viewGroup2.measure(i3, i3);
        this.mTaskbarCenterContain.setVisibility(((i - dimensionPixelOffset) - measuredWidth) - this.mTaskbarRightContain.getMeasuredWidth() > ((this.mResources.getDimensionPixelOffset(R$dimen.taskbar_icon_divider_width) + (this.mResources.getDimensionPixelOffset(R$dimen.desktop_taskbar_center_divider_horizontal_margin) * 2)) + this.mResources.getDimensionPixelOffset(R$dimen.desktop_taskbar_right_layout_paddingStart)) + this.mTaskbarCenterContain.getHeight() ? 0 : 4);
    }

    private int getDynamicSysIconWidth() {
        this.mDynamicSysiconsContainer.setVisibility(0);
        ViewGroup viewGroup = this.mDynamicSysiconsContainer;
        int i = this.mUnSpecified;
        viewGroup.measure(i, i);
        return this.mDynamicSysiconsContainer.getMeasuredWidth();
    }

    private int getFlexibleIconsSpareWidth() {
        int i = this.mResources.getDisplayMetrics().widthPixels;
        int dimensionPixelOffset = this.mResources.getDimensionPixelOffset(R$dimen.desktop_taskbar_layout_start_padding) + this.mResources.getDimensionPixelOffset(R$dimen.desktop_taskbar_layout_end_padding);
        ViewGroup viewGroup = this.mTaskbarLeftContain;
        int i2 = this.mUnSpecified;
        viewGroup.measure(i2, i2);
        int measuredWidth = this.mTaskbarLeftContain.getMeasuredWidth();
        View view = this.mRightFixedLayout;
        int i3 = this.mUnSpecified;
        view.measure(i3, i3);
        return ((i - dimensionPixelOffset) - measuredWidth) - this.mRightFixedLayout.getMeasuredWidth();
    }

    private int getLeftIconShowCount() {
        int size = this.mDisplayIconList.size();
        return canShowMirrorPhoneButton() ? size - 1 : size - 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideMorePanel() {
        if (this.mIsMorePanelViewShowing) {
            this.mIsMorePanelViewShowing = false;
            this.mMorePanelWindowViewManager.removeView(this.mMorePanelLayout);
            this.mMorePanelLayout = null;
        }
    }

    private void initData() {
        this.mDisplayIconList = new ArrayList() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView.1
            {
                add(new TaskBarIconData(R$layout.desktop_lock_button, R$drawable.ic_lock_24px, R$string.lock_description, new Runnable() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DesktopTaskBarView.this.mTaskBarButtonClickHelper.lockDevice();
                    }
                }));
                add(new TaskBarIconData(R$layout.desktop_screenshot_button, R$drawable.ic_screenshot_24px, R$string.tooltips_screenshot_tools, new Runnable() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        DesktopTaskBarView desktopTaskBarView = DesktopTaskBarView.this;
                        KeyButtonView keyButtonView = desktopTaskBarView.mScreenshotButton;
                        if (desktopTaskBarView.indexOfChild(keyButtonView) == -1) {
                            keyButtonView = DesktopTaskBarView.this.mMoreButton;
                        }
                        DesktopTaskBarView.this.mTaskBarButtonClickHelper.showScreenshotTools(keyButtonView);
                    }
                }));
                add(new TaskBarIconData(R$layout.desktop_mirror_phone_button, R$drawable.ic_mirror_phone_24px, R$string.mirror_phone_setting_description, new Runnable() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView.1.3
                    @Override // java.lang.Runnable
                    public void run() {
                        DesktopTaskBarView.this.mTaskBarButtonClickHelper.requestSwitchMirrorPanel();
                    }
                }));
                add(new TaskBarIconData(R$layout.desktop_settings_button, R$drawable.ic_display_settings_24px, R$string.desktop_setting_description, new Runnable() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView.1.4
                    @Override // java.lang.Runnable
                    public void run() {
                        DesktopTaskBarView.this.mTaskBarButtonClickHelper.startSettingsActivity();
                    }
                }));
                add(new TaskBarIconData(R$layout.desktop_wifi_button, R$drawable.ic_wifi_signal_0, R$string.quick_settings_wifi_label, new Runnable() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView.1.5
                    @Override // java.lang.Runnable
                    public void run() {
                        DesktopTaskBarView.this.mTaskBarButtonClickHelper.startWifiSettingActivity();
                    }
                }));
                add(new TaskBarIconData(R$layout.desktop_ethernet_button, R$drawable.ic_ethernet, R$string.ethernet_decription, new Runnable() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView.1.6
                    @Override // java.lang.Runnable
                    public void run() {
                        DesktopTaskBarView.this.mTaskBarButtonClickHelper.startWifiSettingActivity();
                    }
                }));
            }
        };
        this.mDateIconDate = new TaskBarIconData(R$layout.desktop_date_button, R$drawable.ic_date_24dp, 0, new DateLayoutProvider(((FrameLayout) this).mContext), new Runnable() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView.2
            @Override // java.lang.Runnable
            public void run() {
                DesktopTaskBarView.this.mTaskBarButtonClickHelper.startCalendarActivity();
            }
        });
        this.mClockIconDate = new TaskBarIconData(R$layout.desktop_clock_button, R$drawable.ic_clock_24dp, 0, new ClockLayoutProvider(((FrameLayout) this).mContext), new Runnable() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView.3
            @Override // java.lang.Runnable
            public void run() {
                DesktopTaskBarView.this.mTaskBarButtonClickHelper.startClockActivity();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isEthernetConnected() {
        NetworkInfo activeNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 9;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        requestSwitchMorePanel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showMorePanel$1(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 4) {
            return false;
        }
        hideMorePanel();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean needShowMoreIcon() {
        return (getFlexibleIconsSpareWidth() - getDynamicSysIconWidth()) - (this.mResources.getDimensionPixelOffset(R$dimen.desktop_taskbar_left_icon_total_width) * getLeftIconShowCount()) < 0;
    }

    private void requestSwitchMorePanel() {
        if (this.mMorePanelWindowViewManager == null || !this.mIsMorePanelViewShowing) {
            showMorePanel();
        } else {
            hideMorePanel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetView() {
        this.mMorePanelIconList.clear();
        this.mDateView.setVisibility(0);
        this.mClockView.setVisibility(0);
        this.mShowDateInMore = false;
        this.mShowClockInMore = false;
    }

    private void showMorePanel() {
        if (this.mMorePanelWindowViewManager == null) {
            this.mMorePanelWindowViewManager = DisplayWindowManager.getWindowViewManager(this.mDisplayId, 2041);
        }
        Resources resources = this.mMorePanelWindowViewManager.windowContext.getResources();
        Log.d("DesktopTaskBarView", "showMorePanel: " + resources.getConfiguration());
        if (this.mMorePanelLayout == null) {
            if (this.mShowDateInMore && !this.mMorePanelIconList.contains(this.mDateIconDate)) {
                this.mMorePanelIconList.add(this.mDateIconDate);
            }
            if (this.mShowClockInMore && !this.mMorePanelIconList.contains(this.mClockIconDate)) {
                this.mMorePanelIconList.add(this.mClockIconDate);
            }
            this.mMorePanelLayout = (MorePanelLayout) LayoutInflater.from(this.mMorePanelWindowViewManager.windowContext).inflate(R$layout.more_panel, (ViewGroup) null);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(getResources().getDimensionPixelSize(R$dimen.more_panel_width), Math.min((resources.getDisplayMetrics().heightPixels - getHeight()) - getContext().getResources().getDimensionPixelSize(R$dimen.more_panel_offset_y), (this.mMorePanelIconList.size() * resources.getDimensionPixelOffset(R$dimen.more_panel_item_height)) + (resources.getDimensionPixelOffset(R$dimen.more_panel_vertical_padding) * 2)), 2041, R.interpolator.launch_task_micro_alpha, -3);
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
            this.mMorePanelLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f$0.lambda$showMorePanel$1(view, motionEvent);
                }
            });
        }
        this.mMorePanelLayout.addItemViews(this.mMorePanelIconList, this.mOnItemClickedCallback);
        this.mMorePanelViewLayoutParams.x = getContext().getResources().getDimensionPixelSize(R$dimen.more_panel_offset_x);
        this.mMorePanelViewLayoutParams.y = getHeight() + getContext().getResources().getDimensionPixelSize(R$dimen.more_panel_offset_y);
        this.mIsMorePanelViewShowing = true;
        this.mMorePanelWindowViewManager.addView(this.mMorePanelLayout, this.mMorePanelViewLayoutParams);
    }

    @Override // com.motorola.taskbar.bar.TaskBarView
    public void addDesktopIcon(String str, StatusBarIcon statusBarIcon, PendingIntent pendingIntent) {
        super.addDesktopIcon(str, statusBarIcon, pendingIntent);
        arrangeIcons();
    }

    @Override // com.motorola.taskbar.bar.TaskBarView
    protected void initView() {
        super.initView();
        this.mDisplayId = getContext().getDisplayId();
        this.mResources = getResources();
        this.mUnSpecified = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mRightFixedLayout = findViewById(R$id.fixed_layout);
        this.mFlexibleIconsLayout = (ViewGroup) findViewById(R$id.taskbar_icons_flexible_container);
        this.mMoreLayout = findViewById(R$id.more_layout);
        this.mMoreButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.DesktopTaskBarView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initView$0(view);
            }
        });
        this.mDividerList.clear();
        this.mDividerList.add(findViewById(R$id.taskbar_center_divider));
        this.mDividerList.add(findViewById(R$id.battery_divider));
        this.mDividerList.add(findViewById(R$id.notification_divider));
        this.mDividerList.add(findViewById(R$id.more_divider));
    }

    @Override // com.motorola.taskbar.bar.TaskBarView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mConnectivityManager.registerDefaultNetworkCallback(this.mDefaultNetworkCallback);
        arrangeIcons();
    }

    @Override // com.motorola.taskbar.bar.TaskBarView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mConnectivityManager.unregisterNetworkCallback(this.mDefaultNetworkCallback);
        removeCallbacks(null);
        hideMorePanel();
    }

    @Override // com.motorola.taskbar.bar.TaskBarView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean zOnInterceptTouchEvent = super.onInterceptTouchEvent(motionEvent);
        if (this.mIsMorePanelViewShowing && motionEvent.getAction() == 0) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int[] iArr = new int[2];
            this.mMoreButton.getLocationInWindow(iArr);
            int i = iArr[0];
            if (!new Rect(i, iArr[1], this.mMoreButton.getWidth() + i, iArr[1] + this.mMoreButton.getHeight()).contains(x, y)) {
                hideMorePanel();
            }
        }
        return zOnInterceptTouchEvent;
    }

    @Override // com.motorola.taskbar.bar.TaskBarView
    public void removeDesktopIcon(String str) {
        super.removeDesktopIcon(str);
        arrangeIcons();
    }

    @Override // com.motorola.taskbar.bar.TaskBarView
    public void setIconsDark() {
        super.setIconsDark();
        Iterator it = this.mDividerList.iterator();
        while (it.hasNext()) {
            ((View) it.next()).setBackground(getContext().getDrawable(R$drawable.taskbar_area_divider_bg));
        }
    }

    @Override // com.motorola.taskbar.bar.TaskBarView
    protected void updateMirrorPhoneButtonVisible() {
        super.updateMirrorPhoneButtonVisible();
        arrangeIcons();
    }
}
