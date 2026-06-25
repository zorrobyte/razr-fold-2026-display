package com.motorola.taskbar.sysicons;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.statusbar.policy.KeyButtonDrawable;
import com.android.systemui.statusbar.policy.KeyButtonView;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$plurals;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.R$styleable;
import com.motorola.taskbar.bar.QSNotificationPanelController;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.taskbar.widget.TaskbarGuideArrowTipView;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class NotificationSysIconView extends FrameLayout {
    private static final boolean DEBUG = DebugConfig.DEBUG_TASK_SYSTEM_ICON;
    private ImageView mIndicate;
    private int mLayoutId;
    private KeyButtonView mMainIconView;
    private QSNotificationPanelController.QSNotificationPanelControllerCallBack mNotificationCallBack;
    private QSNotificationPanelController mQSNotificationPanelController;
    private TaskBarController mTaskBarController;
    private TaskBarController.TaskBarControllerListener mTaskBarControllerListener;
    private TaskbarGuideArrowTipView mTaskbarGuideArrowTipView;

    public NotificationSysIconView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationSysIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNotificationCallBack = new QSNotificationPanelController.QSNotificationPanelControllerCallBack() { // from class: com.motorola.taskbar.sysicons.NotificationSysIconView.1
            @Override // com.motorola.taskbar.bar.QSNotificationPanelController.QSNotificationPanelControllerCallBack
            public void onUnreadNotificationCountChanged(int i2, int i3) {
                if (NotificationSysIconView.this.getDisplay() == null) {
                    Log.w("NotificationSysIconView", "onUnreadNotificationCountChanged display is null");
                } else if (i2 == NotificationSysIconView.this.getDisplay().getDisplayId()) {
                    NotificationSysIconView.this.updateStatus(i3);
                }
            }
        };
        this.mTaskBarControllerListener = new TaskBarController.TaskBarControllerListener() { // from class: com.motorola.taskbar.sysicons.NotificationSysIconView.2
            @Override // com.motorola.taskbar.bar.TaskBarController.TaskBarControllerListener
            public void onOnBoardActivityShowingChanged(int i2, boolean z) {
                if (z && i2 == NotificationSysIconView.this.getDisplay().getDisplayId() && NotificationSysIconView.this.mTaskbarGuideArrowTipView != null && NotificationSysIconView.this.mTaskbarGuideArrowTipView.isOpened()) {
                    NotificationSysIconView.this.mTaskbarGuideArrowTipView.close(false, false);
                }
            }

            @Override // com.motorola.taskbar.bar.TaskBarController.TaskBarControllerListener
            public void onTaskBarReallyVisibilityChanged(int i2, boolean z) {
                if (z || i2 != NotificationSysIconView.this.getDisplay().getDisplayId() || NotificationSysIconView.this.mTaskbarGuideArrowTipView == null || !NotificationSysIconView.this.mTaskbarGuideArrowTipView.isOpened()) {
                    return;
                }
                NotificationSysIconView.this.mTaskbarGuideArrowTipView.close(false, false);
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CustomKeyButtonView, 0, 0);
        if (typedArrayObtainStyledAttributes != null) {
            this.mLayoutId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.CustomKeyButtonView_layoutId, R$layout.notification_sys_icon);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private KeyButtonDrawable getKeyButtonDrawable(int i) {
        return KeyButtonDrawable.create(((FrameLayout) this).mContext, i, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$0() {
        updateStatus(this.mQSNotificationPanelController.getUnreadNotificationCount(getContext().getDisplayId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$1() {
        updateStatus(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: showGuiderIfNeed, reason: merged with bridge method [inline-methods] */
    public void lambda$updateStatus$2() {
        Display display;
        TaskbarGuideArrowTipView taskbarGuideArrowTipView = this.mTaskbarGuideArrowTipView;
        if ((taskbarGuideArrowTipView != null && taskbarGuideArrowTipView.isAttachedToWindow()) || Prefs.getBoolean(getContext(), "NotificationPanelGuider", false) || (display = getDisplay()) == null || !this.mTaskBarController.isTaskBarReallyShowing(display.getDisplayId()) || this.mTaskBarController.isOnBoardActivityShowing(display.getDisplayId())) {
            return;
        }
        if (this.mTaskbarGuideArrowTipView == null) {
            TaskbarGuideArrowTipView taskbarGuideArrowTipView2 = new TaskbarGuideArrowTipView(getContext(), 3, R$layout.taskbar_icon_guide);
            this.mTaskbarGuideArrowTipView = taskbarGuideArrowTipView2;
            taskbarGuideArrowTipView2.setOnClosedCallback(new Consumer() { // from class: com.motorola.taskbar.sysicons.NotificationSysIconView.3
                @Override // java.util.function.Consumer
                public void accept(Boolean bool) {
                    if (bool.booleanValue()) {
                        Prefs.putBoolean(NotificationSysIconView.this.getContext(), "NotificationPanelGuider", true);
                    }
                    NotificationSysIconView.this.mTaskbarGuideArrowTipView = null;
                }
            });
        }
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        this.mTaskbarGuideArrowTipView.show(getContext().getString(R$string.arrow_toast_notification_panel_tip), 8388613, 0, getWidth() / 2, iArr[0] + (getWidth() / 2), iArr[1] - getResources().getDimensionPixelSize(R$dimen.taskbar_layout_vertical_padding), getLayoutDirection() == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStatus(int i) {
        boolean zIsMobileUiOrAppStreamDisplay = ((MotoFeature) Dependency.get(MotoFeature.class)).isMobileUiOrAppStreamDisplay(getContext());
        int i2 = i > 0 ? zIsMobileUiOrAppStreamDisplay ? R$drawable.ic_mobileui_new_notifications : R$drawable.taskbar_ic_new_notifications : zIsMobileUiOrAppStreamDisplay ? R$drawable.ic_mobileui_notifications : R$drawable.taskbar_ic_notifications_more_24px;
        if (i == 0) {
            this.mMainIconView.setToolTipText(getContext().getResources().getString(R$string.no_new_notification));
        } else {
            this.mMainIconView.setToolTipText(getContext().getResources().getQuantityString(R$plurals.taskbar_notification_num_devices, i, Integer.valueOf(i)));
        }
        this.mMainIconView.setImageDrawable(getKeyButtonDrawable(i2));
        setDarkIntensity(getResources().getFloat(R$dimen.taskbar_icon_dark_intensity));
        this.mIndicate.setVisibility(i > 0 ? 0 : 8);
        if (i >= 3) {
            post(new Runnable() { // from class: com.motorola.taskbar.sysicons.NotificationSysIconView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateStatus$2();
                }
            });
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        TaskBarController taskBarController = (TaskBarController) Dependency.get(TaskBarController.class);
        this.mTaskBarController = taskBarController;
        taskBarController.addCallback(this.mTaskBarControllerListener);
        QSNotificationPanelController qSNotificationPanelController = this.mQSNotificationPanelController;
        if (qSNotificationPanelController == null) {
            post(new Runnable() { // from class: com.motorola.taskbar.sysicons.NotificationSysIconView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAttachedToWindow$1();
                }
            });
        } else {
            qSNotificationPanelController.addCallback(this.mNotificationCallBack);
            post(new Runnable() { // from class: com.motorola.taskbar.sysicons.NotificationSysIconView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAttachedToWindow$0();
                }
            });
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        QSNotificationPanelController qSNotificationPanelController = this.mQSNotificationPanelController;
        if (qSNotificationPanelController != null) {
            qSNotificationPanelController.removeCallback(this.mNotificationCallBack);
        }
        TaskbarGuideArrowTipView taskbarGuideArrowTipView = this.mTaskbarGuideArrowTipView;
        if (taskbarGuideArrowTipView != null) {
            taskbarGuideArrowTipView.close(true, false);
            this.mTaskbarGuideArrowTipView = null;
        }
        this.mTaskBarController.removeCallback(this.mTaskBarControllerListener);
        removeCallbacks(null);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mQSNotificationPanelController = (QSNotificationPanelController) Dependency.get(QSNotificationPanelController.class);
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(this.mLayoutId, (ViewGroup) null);
        addView(viewGroup, -1, -1);
        this.mMainIconView = (KeyButtonView) viewGroup.findViewById(R$id.main_icon);
        this.mIndicate = (ImageView) viewGroup.findViewById(R$id.indicate);
        if (!((MotoFeature) Dependency.get(MotoFeature.class)).isMobileUiOrAppStreamDisplay(getContext())) {
            this.mIndicate.setImageResource(R$drawable.taskbar_ic_new_notifications_indicate);
            return;
        }
        Drawable drawable = getContext().getDrawable(R$drawable.ic_mobileui_notification_indicate);
        drawable.setTint(getResources().getColor(R$color.primary));
        this.mIndicate.setImageDrawable(drawable);
    }

    public void setDarkIntensity(float f) {
        KeyButtonView keyButtonView = this.mMainIconView;
        if (keyButtonView != null) {
            keyButtonView.setDarkIntensity(f);
        }
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        KeyButtonView keyButtonView = this.mMainIconView;
        if (keyButtonView != null) {
            keyButtonView.setOnClickListener(onClickListener);
        }
    }
}
