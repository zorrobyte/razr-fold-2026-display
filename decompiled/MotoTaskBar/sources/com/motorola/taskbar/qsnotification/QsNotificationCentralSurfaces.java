package com.motorola.taskbar.qsnotification;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelController;
import com.android.systemui.qs.dagger.QSSceneComponent;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.collection.DesktopUnreadNotificationMonitor;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController;
import com.motorola.systemui.desktop.widget.DesktopNotificationPanelLayout;
import com.motorola.systemui.desktop.widget.DesktopNotificationRootLayout;
import com.motorola.systemui.desktop.widget.DesktopQSPanelLayout;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$style;
import com.motorola.taskbar.bar.QSNotificationPanelController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: loaded from: classes2.dex */
public class QsNotificationCentralSurfaces implements RemoteInputController.Callback {
    private final ConfigurationController mConfigurationController;
    private final Context mContext;
    private final DesktopHeadsUpController mDesktopHeadsUpController;
    private DesktopNotificationPanelLayout mDesktopNotificationPanelLayout;
    private DesktopQSPanelLayout mDesktopQSPanelLayout;
    private final DesktopUnreadNotificationMonitor mDesktopUnreadNotificationMonitor;
    private final int mDisplayId;
    private WindowManager mDisplayWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private final Handler mMainHandler;
    private final NotificationActivityStarter mNotificationActivityStarter;
    private final StatusBarNotificationPresenter mNotificationPresenter;
    private NotificationStackScrollLayout mNotificationStackScrollLayout;
    private final NotificationsController mNotificationsController;
    private ViewGroup mQSNContentView;
    private final QSNotificationPanelController mQSNotificationPanelController;
    private QSPanelController mQSPanelController;
    private QsNotificationDependency mQsNotificationDependency;
    private final QSSceneComponent.Factory mQsSceneComponentFactory;
    private final NotificationRemoteInputManager mRemoteInputManager;
    private final NotificationStackScrollLayoutController mStackScrollerController;
    private final VisualInterruptionDecisionProvider mVisualInterruptionDecisionProvider;
    private int mLayoutDirection = 0;
    private boolean mIsQSNShowing = false;
    private long mLastQSNToggleTime = 0;
    private boolean mIsRemoteInputActive = false;
    private boolean mIsNeedRequestLayout = false;
    private ConfigurationController.ConfigurationListener mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces.2
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onConfigChanged(Configuration configuration) {
            int layoutDirection = configuration.getLayoutDirection();
            if (layoutDirection != QsNotificationCentralSurfaces.this.mLayoutDirection) {
                QsNotificationCentralSurfaces.this.mLayoutDirection = layoutDirection;
                if (QsNotificationCentralSurfaces.this.mLayoutDirection == 1) {
                    QsNotificationCentralSurfaces.this.mLayoutParams.gravity = 51;
                    QsNotificationCentralSurfaces.this.mLayoutParams.windowAnimations = R$style.Animation_TaskbarNotificationPanelRTL;
                } else {
                    QsNotificationCentralSurfaces.this.mLayoutParams.gravity = 53;
                    QsNotificationCentralSurfaces.this.mLayoutParams.windowAnimations = R$style.Animation_TaskbarNotificationPanel;
                }
                if (QsNotificationCentralSurfaces.this.mQSNContentView.isAttachedToWindow()) {
                    QsNotificationCentralSurfaces.this.mDisplayWindowManager.updateViewLayout(QsNotificationCentralSurfaces.this.mQSNContentView, QsNotificationCentralSurfaces.this.mLayoutParams);
                }
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onDensityOrFontScaleChanged() {
            QsNotificationCentralSurfaces.this.mLayoutParams.width = QsNotificationCentralSurfaces.this.getQSPanelWidth();
            QsNotificationCentralSurfaces.this.mLayoutParams.height = -1;
            if (!QsNotificationCentralSurfaces.this.mQSNContentView.isAttachedToWindow()) {
                QsNotificationCentralSurfaces.this.mIsNeedRequestLayout = true;
            } else {
                QsNotificationCentralSurfaces.this.mDisplayWindowManager.updateViewLayout(QsNotificationCentralSurfaces.this.mQSNContentView, QsNotificationCentralSurfaces.this.mLayoutParams);
                QsNotificationCentralSurfaces.this.mQSNContentView.requestLayout();
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onUiModeChanged() {
            QsNotificationCentralSurfaces.this.updateResources();
        }
    };
    private final Function1 mMediaHostVisibilityListener = new Function1() { // from class: com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return this.f$0.lambda$new$4((Boolean) obj);
        }
    };
    private final DesktopUnreadNotificationMonitor.UnReadNotificationListener mUnReadNotificationListener = new DesktopUnreadNotificationMonitor.UnReadNotificationListener() { // from class: com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces.3
        @Override // com.android.systemui.statusbar.notification.collection.DesktopUnreadNotificationMonitor.UnReadNotificationListener
        public void onUnReadNotificationSizeChanged(int i) {
            QsNotificationCentralSurfaces.this.mQSNotificationPanelController.onUnReadNotificationSizeChanged(QsNotificationCentralSurfaces.this.mDisplayId, i);
        }
    };

    public QsNotificationCentralSurfaces(int i, Context context, NotificationsController notificationsController, NotificationRemoteInputManager notificationRemoteInputManager, DesktopUnreadNotificationMonitor desktopUnreadNotificationMonitor, NotificationStackScrollLayoutController notificationStackScrollLayoutController, StatusBarNotificationPresenter statusBarNotificationPresenter, NotificationActivityStarter notificationActivityStarter, ConfigurationController configurationController, VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, QSSceneComponent.Factory factory, HeadsUpManager headsUpManager, QSNotificationPanelController qSNotificationPanelController, Handler handler, DelayableExecutor delayableExecutor) {
        this.mDisplayId = i;
        this.mContext = context;
        this.mNotificationsController = notificationsController;
        this.mMainHandler = handler;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mDesktopUnreadNotificationMonitor = desktopUnreadNotificationMonitor;
        this.mStackScrollerController = notificationStackScrollLayoutController;
        this.mNotificationPresenter = statusBarNotificationPresenter;
        this.mNotificationActivityStarter = notificationActivityStarter;
        this.mConfigurationController = configurationController;
        this.mVisualInterruptionDecisionProvider = visualInterruptionDecisionProvider;
        this.mQsSceneComponentFactory = factory;
        this.mDesktopHeadsUpController = (DesktopHeadsUpController) headsUpManager;
        this.mQSNotificationPanelController = qSNotificationPanelController;
    }

    private void detachMedia() {
        QSPanelController qSPanelController = this.mQSPanelController;
        if (qSPanelController != null) {
            qSPanelController.getMediaHost().removeVisibilityChangeListener(this.mMediaHostVisibilityListener);
            this.mQSPanelController.getMediaHost().stopAllListeners();
        }
    }

    private WindowManager.LayoutParams genWindowManagerLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(getQSPanelWidth(), -1, 2041, 262248, -3);
        if (this.mLayoutDirection == 1) {
            layoutParams.gravity = 51;
            layoutParams.windowAnimations = R$style.Animation_TaskbarNotificationPanelRTL;
        } else {
            layoutParams.gravity = 53;
            layoutParams.windowAnimations = R$style.Animation_TaskbarNotificationPanel;
        }
        layoutParams.setTitle("DesktopQS: " + this.mDisplayId);
        return layoutParams;
    }

    private void hidePanelInternal(boolean z, boolean z2) {
        if (z || isValidRequest()) {
            this.mRemoteInputManager.closeRemoteInputs();
            this.mQSPanelController.setListening(false, false);
            if (z2) {
                this.mDisplayWindowManager.removeViewImmediate(this.mQSNContentView);
            } else {
                this.mDisplayWindowManager.removeView(this.mQSNContentView);
            }
            this.mIsQSNShowing = false;
        }
    }

    private boolean isValidRequest() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (jElapsedRealtime - this.mLastQSNToggleTime <= 300) {
            return false;
        }
        this.mLastQSNToggleTime = jElapsedRealtime;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$new$4(Boolean bool) {
        View viewFindViewById = this.mQSNContentView.findViewById(R$id.quick_settings_panel);
        if (!(viewFindViewById instanceof QSPanel)) {
            return null;
        }
        ((QSPanel) viewFindViewById).updateResources();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestHidePanel$1() {
        if (this.mIsQSNShowing) {
            hidePanelInternal(false, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestShowPanel$0() {
        if (this.mIsQSNShowing) {
            return;
        }
        showPanelInternal(false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestTogglePanel$2() {
        if (this.mIsQSNShowing) {
            hidePanelInternal(false, false);
        } else {
            showPanelInternal(false, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPanelInternal$3() {
        if (this.mIsQSNShowing) {
            this.mDesktopUnreadNotificationMonitor.resetUnReadNotificationSize();
        }
    }

    private boolean needQSPanelHorizontalPadding() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mContext.getDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels >= this.mContext.getResources().getDimensionPixelSize(R$dimen.desktop_qsn_panel_width) + this.mContext.getResources().getDimensionPixelSize(R$dimen.desktop_qs_panel_margin);
    }

    private void showPanelInternal(boolean z, boolean z2) {
        if (z || isValidRequest()) {
            this.mDisplayWindowManager.addView(this.mQSNContentView, this.mLayoutParams);
            if (this.mIsNeedRequestLayout) {
                this.mIsNeedRequestLayout = false;
                this.mQSNContentView.requestLayout();
            }
            this.mQSPanelController.setListening(true, true);
            this.mIsQSNShowing = true;
            this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showPanelInternal$3();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateResources() {
        this.mDesktopNotificationPanelLayout.setBackgroundColor(this.mContext.getColor(R$color.desktop_qs_notification_panel_bg));
    }

    public int getQSPanelWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mContext.getDisplay().getRealMetrics(displayMetrics);
        if (this.mContext.getDisplay().getDisplayId() == 0) {
            return displayMetrics.widthPixels;
        }
        return Math.min(displayMetrics.widthPixels, this.mContext.getResources().getDimensionPixelSize(R$dimen.desktop_qsn_panel_width) + this.mContext.getResources().getDimensionPixelSize(R$dimen.desktop_qs_panel_margin));
    }

    public QsNotificationDependency getQsNotificationDependency() {
        return this.mQsNotificationDependency;
    }

    public int getUnReadNotificationSize() {
        return this.mDesktopUnreadNotificationMonitor.getUnReadNotificationSize();
    }

    public boolean isMediaPanelVisible() {
        QSPanelController qSPanelController = this.mQSPanelController;
        if (qSPanelController != null) {
            return qSPanelController.getMediaHost().getVisible();
        }
        return false;
    }

    public boolean isRemoteInputActive() {
        return this.mIsRemoteInputActive;
    }

    @Override // com.android.systemui.statusbar.RemoteInputController.Callback
    public void onRemoteInputActive(boolean z) {
        ViewGroup viewGroup = this.mQSNContentView;
        if (viewGroup != null) {
            if (z) {
                this.mLayoutParams.flags &= -9;
            } else {
                this.mLayoutParams.flags |= 8;
            }
            if (viewGroup.isAttachedToWindow()) {
                this.mDisplayWindowManager.updateViewLayout(this.mQSNContentView, this.mLayoutParams);
            }
            this.mIsRemoteInputActive = z;
        }
    }

    public void requestHidePanel() {
        this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestHidePanel$1();
            }
        });
    }

    public void requestShowPanel() {
        this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestShowPanel$0();
            }
        });
    }

    public void requestTogglePanel() {
        this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestTogglePanel$2();
            }
        });
    }

    public void start(QsNotificationDependency qsNotificationDependency) {
        this.mVisualInterruptionDecisionProvider.start();
        this.mQsNotificationDependency = qsNotificationDependency;
        this.mDisplayWindowManager = (WindowManager) this.mContext.getSystemService("window");
        this.mLayoutDirection = this.mContext.getResources().getConfiguration().getLayoutDirection();
        this.mLayoutParams = genWindowManagerLayoutParams();
        this.mQSNContentView = (ViewGroup) LayoutInflater.from(this.mContext).inflate(R$layout.desktop_notifications_content, (ViewGroup) null);
        LayoutInflater layoutInflaterCloneInContext = LayoutInflater.from(this.mContext).cloneInContext(new ContextThemeWrapper(this.mContext, R$style.Theme_SystemUI_QuickSettings));
        int i = R$layout.qs_panel;
        ViewGroup viewGroup = this.mQSNContentView;
        int i2 = R$id.qs_frame;
        layoutInflaterCloneInContext.inflate(i, (ViewGroup) viewGroup.findViewById(i2), true);
        QSPanelController qSPanelController = this.mQsSceneComponentFactory.create(this.mQSNContentView).getQSPanelController();
        this.mQSPanelController = qSPanelController;
        qSPanelController.init();
        this.mQSPanelController.getMediaHost().addVisibilityChangeListener(this.mMediaHostVisibilityListener);
        ((DesktopNotificationRootLayout) this.mQSNContentView).setQsNotificationCentralSurfaces(this);
        if (!needQSPanelHorizontalPadding()) {
            this.mQSNContentView.setPaddingRelative(0, this.mQSNContentView.getPaddingTop(), 0, this.mQSNContentView.getPaddingBottom());
        }
        this.mDesktopNotificationPanelLayout = (DesktopNotificationPanelLayout) this.mQSNContentView.findViewById(R$id.notification_stack_panel);
        this.mNotificationStackScrollLayout = (NotificationStackScrollLayout) this.mQSNContentView.findViewById(R$id.notification_stack_scroller);
        this.mDesktopQSPanelLayout = (DesktopQSPanelLayout) this.mQSNContentView.findViewById(i2);
        this.mStackScrollerController.setUpView(this.mNotificationStackScrollLayout);
        this.mStackScrollerController.setIntrinsicPadding(this.mContext.getResources().getDimensionPixelSize(R$dimen.notification_side_paddings));
        this.mNotificationsController.initialize(this.mNotificationPresenter, this.mStackScrollerController.getNotificationListContainer(), this.mStackScrollerController.getNotifStackController(), this.mNotificationActivityStarter);
        this.mRemoteInputManager.addControllerCallback(this);
        this.mQSNContentView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces.1
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                View viewFindViewById = QsNotificationCentralSurfaces.this.mQSNContentView.findViewById(R$id.quick_settings_panel);
                if (viewFindViewById != null) {
                    int height = viewFindViewById.getHeight();
                    int paddingTop = QsNotificationCentralSurfaces.this.mDesktopQSPanelLayout.getPaddingTop();
                    int paddingBottom = QsNotificationCentralSurfaces.this.mDesktopQSPanelLayout.getPaddingBottom();
                    int height2 = QsNotificationCentralSurfaces.this.mQSNContentView.getHeight();
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    QsNotificationCentralSurfaces.this.mContext.getDisplay().getRealMetrics(displayMetrics);
                    int dimensionPixelSize = QsNotificationCentralSurfaces.this.mContext.getResources().getDimensionPixelSize(R$dimen.desktop_qs_notification_panel_gape);
                    int dimensionPixelSize2 = QsNotificationCentralSurfaces.this.mContext.getResources().getDimensionPixelSize(R$dimen.desktop_qs_panel_margin);
                    int dimensionPixelSize3 = QsNotificationCentralSurfaces.this.mContext.getResources().getDimensionPixelSize(17105911) + dimensionPixelSize2;
                    if (MotoFeature.isMobileUiMode(QsNotificationCentralSurfaces.this.mContext) || height2 < displayMetrics.heightPixels) {
                        dimensionPixelSize3 = dimensionPixelSize2;
                    }
                    QsNotificationCentralSurfaces.this.mDesktopNotificationPanelLayout.setTranslationY(height + dimensionPixelSize + paddingTop + paddingBottom + dimensionPixelSize2);
                    ViewGroup.LayoutParams layoutParams = QsNotificationCentralSurfaces.this.mDesktopNotificationPanelLayout.getLayoutParams();
                    int height3 = (((((QsNotificationCentralSurfaces.this.mQSNContentView.getHeight() - height) - dimensionPixelSize) - paddingTop) - paddingBottom) - dimensionPixelSize2) - dimensionPixelSize3;
                    if (layoutParams.height != height3) {
                        layoutParams.height = height3;
                        QsNotificationCentralSurfaces.this.mDesktopNotificationPanelLayout.setLayoutParams(layoutParams);
                        QsNotificationCentralSurfaces.this.mDesktopQSPanelLayout.setRealHeight(height + paddingTop + paddingBottom);
                    }
                }
            }
        });
        this.mDesktopHeadsUpController.attach();
        updateResources();
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        this.mDesktopUnreadNotificationMonitor.setUnReadNotificationListener(this.mUnReadNotificationListener);
        this.mDesktopUnreadNotificationMonitor.attach();
    }

    public void stop() {
        if (this.mIsQSNShowing) {
            hidePanelInternal(false, false);
        }
        detachMedia();
        this.mDesktopUnreadNotificationMonitor.detach();
        this.mDesktopHeadsUpController.detach();
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
    }
}
