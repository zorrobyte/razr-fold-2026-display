package com.motorola.taskbar.sysicons;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.policy.KeyButtonDrawable;
import com.android.systemui.statusbar.policy.KeyButtonView;
import com.motorola.plugin.core.utils.TimeoutRemoteCaller;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.taskbar.panel.VolumePanelLayout;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.taskbar.widget.TaskbarGuideArrowTipView;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class VolumeSysIconView extends KeyButtonView {
    private static final boolean DEBUG = DebugConfig.DEBUG_TASK_SYSTEM_ICON;
    private long mAttachedTime;
    private AudioManager mAudioManager;
    private ContentObserver mContentObserver;
    private Context mCurrentUserContext;
    private boolean mIsMobileUiOrAppStream;
    private boolean mIsRdpDisplay;
    private boolean mNeedRequestPermission;
    private int mRetryUpdateCount;
    private TaskBarController mTaskBarController;
    private TaskBarController.TaskBarControllerListener mTaskBarControllerListener;
    private TaskbarGuideArrowTipView mTaskbarGuideArrowTipView;
    private Handler mUIHandler;
    private CurrentUserTracker mUserTracker;
    private VolumeBroadcastReceiver mVolumeBroadcastReceiver;

    /* JADX INFO: renamed from: com.motorola.taskbar.sysicons.VolumeSysIconView$3, reason: invalid class name */
    class AnonymousClass3 extends ContentObserver {
        AnonymousClass3(Handler handler) {
            super(handler);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChange$0() {
            VolumeSysIconView.this.showGuiderIfNeed();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            VolumeSysIconView.this.updateStatusByProvider();
            VolumeSysIconView.this.post(new Runnable() { // from class: com.motorola.taskbar.sysicons.VolumeSysIconView$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onChange$0();
                }
            });
        }
    }

    class VolumeBroadcastReceiver extends BroadcastReceiver {
        private final BroadcastDispatcher mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);

        public VolumeBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (VolumeSysIconView.DEBUG) {
                Log.d("VolumeSysIconView", "onReceive intent: " + intent);
            }
            VolumeSysIconView.this.updateStatusByProvider();
        }

        public void registerReceiver(Context context) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            intentFilter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
            intentFilter.addAction("android.media.MASTER_MUTE_CHANGED_ACTION");
            this.mBroadcastDispatcher.registerReceiver(this, intentFilter);
        }

        public void unregisterReceiver(Context context) {
            this.mBroadcastDispatcher.unregisterReceiver(this);
        }
    }

    public VolumeSysIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRetryUpdateCount = 0;
        this.mVolumeBroadcastReceiver = new VolumeBroadcastReceiver();
        this.mIsRdpDisplay = false;
        this.mNeedRequestPermission = false;
        this.mUIHandler = new Handler() { // from class: com.motorola.taskbar.sysicons.VolumeSysIconView.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                VolumeSysIconView.this.updateStatusByProvider();
            }
        };
        this.mContentObserver = new AnonymousClass3(this.mUIHandler);
        this.mTaskBarControllerListener = new TaskBarController.TaskBarControllerListener() { // from class: com.motorola.taskbar.sysicons.VolumeSysIconView.4
            @Override // com.motorola.taskbar.bar.TaskBarController.TaskBarControllerListener
            public void onOnBoardActivityShowingChanged(int i, boolean z) {
                if (z && i == VolumeSysIconView.this.getDisplay().getDisplayId() && VolumeSysIconView.this.mTaskbarGuideArrowTipView != null && VolumeSysIconView.this.mTaskbarGuideArrowTipView.isOpened()) {
                    VolumeSysIconView.this.mTaskbarGuideArrowTipView.close(false, false);
                }
            }

            @Override // com.motorola.taskbar.bar.TaskBarController.TaskBarControllerListener
            public void onTaskBarReallyVisibilityChanged(int i, boolean z) {
                if (z || i != VolumeSysIconView.this.getDisplay().getDisplayId() || VolumeSysIconView.this.mTaskbarGuideArrowTipView == null || !VolumeSysIconView.this.mTaskbarGuideArrowTipView.isOpened()) {
                    return;
                }
                VolumeSysIconView.this.mTaskbarGuideArrowTipView.close(false, false);
            }
        };
    }

    public VolumeSysIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRetryUpdateCount = 0;
        this.mVolumeBroadcastReceiver = new VolumeBroadcastReceiver();
        this.mIsRdpDisplay = false;
        this.mNeedRequestPermission = false;
        this.mUIHandler = new Handler() { // from class: com.motorola.taskbar.sysicons.VolumeSysIconView.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                VolumeSysIconView.this.updateStatusByProvider();
            }
        };
        this.mContentObserver = new AnonymousClass3(this.mUIHandler);
        this.mTaskBarControllerListener = new TaskBarController.TaskBarControllerListener() { // from class: com.motorola.taskbar.sysicons.VolumeSysIconView.4
            @Override // com.motorola.taskbar.bar.TaskBarController.TaskBarControllerListener
            public void onOnBoardActivityShowingChanged(int i2, boolean z) {
                if (z && i2 == VolumeSysIconView.this.getDisplay().getDisplayId() && VolumeSysIconView.this.mTaskbarGuideArrowTipView != null && VolumeSysIconView.this.mTaskbarGuideArrowTipView.isOpened()) {
                    VolumeSysIconView.this.mTaskbarGuideArrowTipView.close(false, false);
                }
            }

            @Override // com.motorola.taskbar.bar.TaskBarController.TaskBarControllerListener
            public void onTaskBarReallyVisibilityChanged(int i2, boolean z) {
                if (z || i2 != VolumeSysIconView.this.getDisplay().getDisplayId() || VolumeSysIconView.this.mTaskbarGuideArrowTipView == null || !VolumeSysIconView.this.mTaskbarGuideArrowTipView.isOpened()) {
                    return;
                }
                VolumeSysIconView.this.mTaskbarGuideArrowTipView.close(false, false);
            }
        };
    }

    public VolumeSysIconView(Context context, AttributeSet attributeSet, int i, InputManager inputManager) {
        super(context, attributeSet, i, inputManager);
        this.mRetryUpdateCount = 0;
        this.mVolumeBroadcastReceiver = new VolumeBroadcastReceiver();
        this.mIsRdpDisplay = false;
        this.mNeedRequestPermission = false;
        this.mUIHandler = new Handler() { // from class: com.motorola.taskbar.sysicons.VolumeSysIconView.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                VolumeSysIconView.this.updateStatusByProvider();
            }
        };
        this.mContentObserver = new AnonymousClass3(this.mUIHandler);
        this.mTaskBarControllerListener = new TaskBarController.TaskBarControllerListener() { // from class: com.motorola.taskbar.sysicons.VolumeSysIconView.4
            @Override // com.motorola.taskbar.bar.TaskBarController.TaskBarControllerListener
            public void onOnBoardActivityShowingChanged(int i2, boolean z) {
                if (z && i2 == VolumeSysIconView.this.getDisplay().getDisplayId() && VolumeSysIconView.this.mTaskbarGuideArrowTipView != null && VolumeSysIconView.this.mTaskbarGuideArrowTipView.isOpened()) {
                    VolumeSysIconView.this.mTaskbarGuideArrowTipView.close(false, false);
                }
            }

            @Override // com.motorola.taskbar.bar.TaskBarController.TaskBarControllerListener
            public void onTaskBarReallyVisibilityChanged(int i2, boolean z) {
                if (z || i2 != VolumeSysIconView.this.getDisplay().getDisplayId() || VolumeSysIconView.this.mTaskbarGuideArrowTipView == null || !VolumeSysIconView.this.mTaskbarGuideArrowTipView.isOpened()) {
                    return;
                }
                VolumeSysIconView.this.mTaskbarGuideArrowTipView.close(false, false);
            }
        };
    }

    private KeyButtonDrawable getKeyButtonDrawable(int i) {
        return KeyButtonDrawable.create(((ImageView) this).mContext, i, false);
    }

    private KeyButtonDrawable getKeyButtonDrawable(Drawable drawable) {
        return KeyButtonDrawable.create(((ImageView) this).mContext, drawable, false);
    }

    private void requestUpdateStatusByProvider(long j) {
        if (this.mUIHandler.hasMessages(1)) {
            return;
        }
        this.mUIHandler.sendEmptyMessageDelayed(1, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGuiderIfNeed() {
        Display display;
        if (MotoFeature.isMobileUiMode(((ImageView) this).mContext.getDisplay()) || MotoFeature.isAppStreamMode(((ImageView) this).mContext)) {
            return;
        }
        TaskbarGuideArrowTipView taskbarGuideArrowTipView = this.mTaskbarGuideArrowTipView;
        if ((taskbarGuideArrowTipView == null || !taskbarGuideArrowTipView.isAttachedToWindow()) && !Prefs.getBoolean(getContext(), "VolumePanelGuider", false) && this.mNeedHover && (display = getDisplay()) != null && SystemClock.elapsedRealtime() - this.mAttachedTime >= TimeoutRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS && this.mTaskBarController.isTaskBarReallyShowing(display.getDisplayId()) && !this.mTaskBarController.isOnBoardActivityShowing(display.getDisplayId())) {
            if (this.mTaskbarGuideArrowTipView == null) {
                TaskbarGuideArrowTipView taskbarGuideArrowTipView2 = new TaskbarGuideArrowTipView(getContext(), 3, R$layout.taskbar_icon_guide);
                this.mTaskbarGuideArrowTipView = taskbarGuideArrowTipView2;
                taskbarGuideArrowTipView2.setOnClosedCallback(new Consumer() { // from class: com.motorola.taskbar.sysicons.VolumeSysIconView.5
                    @Override // java.util.function.Consumer
                    public void accept(Boolean bool) {
                        if (bool.booleanValue()) {
                            Prefs.putBoolean(VolumeSysIconView.this.getContext(), "VolumePanelGuider", true);
                        }
                        VolumeSysIconView.this.mTaskbarGuideArrowTipView = null;
                    }
                });
            }
            int[] iArr = new int[2];
            getLocationOnScreen(iArr);
            this.mTaskbarGuideArrowTipView.show(getContext().getString(R$string.arrow_toast_volume_panel_tip), 8388613, 0, getWidth() / 2, iArr[0] + (getWidth() / 2), iArr[1] - getResources().getDimensionPixelSize(R$dimen.taskbar_layout_vertical_padding), getLayoutDirection() == 1);
        }
    }

    private void updateStatusByDeprecated() {
        int i;
        int streamMaxVolume = this.mAudioManager.getStreamMaxVolume(3);
        int streamMinVolume = this.mAudioManager.getStreamMinVolume(3);
        int streamVolume = this.mAudioManager.getStreamVolume(3);
        boolean z = this.mAudioManager.isStreamMute(3) || streamVolume <= streamMinVolume;
        if (DEBUG) {
            Log.d("VolumeSysIconView", "updateStatus max: " + streamMaxVolume + "; min: " + streamMinVolume + "; cur: " + streamVolume + "; isMute: " + z);
        }
        boolean z2 = this.mIsMobileUiOrAppStream;
        if (z) {
            i = z2 ? R$drawable.ic_mobileui_volume_mute : R$drawable.ic_volume_off_24px;
            setToolTipText(getContext().getString(R$string.volume_ringer_hint_mute));
        } else {
            float f = (streamVolume - streamMinVolume) / (streamMaxVolume - streamMinVolume);
            i = ((double) f) < 0.5d ? z2 ? R$drawable.ic_mobileui_volume : R$drawable.ic_volume_down_24px : z2 ? R$drawable.ic_mobileui_volume : R$drawable.ic_volume_up_24px;
            setToolTipText(getContext().getString(R$string.accessibility_volume_level, Integer.valueOf((int) (f * 100.0f))));
        }
        setImageDrawable(getKeyButtonDrawable(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:58:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00a1 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateStatusByProvider() {
        /*
            Method dump skipped, instruction units count: 368
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.taskbar.sysicons.VolumeSysIconView.updateStatusByProvider():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUserContext(int i) {
        this.mCurrentUserContext = getContext().createContextAsUser(UserHandle.of(i), 0);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedTime = SystemClock.elapsedRealtime();
        TaskBarController taskBarController = (TaskBarController) Dependency.get(TaskBarController.class);
        this.mTaskBarController = taskBarController;
        taskBarController.addCallback(this.mTaskBarControllerListener);
        this.mVolumeBroadcastReceiver.registerReceiver(getContext());
        this.mUserTracker.startTracking();
        updateUserContext(this.mUserTracker.getCurrentUserId());
        updateStatusByProvider();
        try {
            this.mCurrentUserContext.getContentResolver().registerContentObserver(VolumePanelLayout.CONTENT_URI_DEVICES, true, this.mContentObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onClick() {
        if (this.mNeedRequestPermission) {
            try {
                Intent intent = new Intent("com.motorola.mobiledesktop.core.action.REQUEST_PERMISSION");
                intent.putExtra("permission_type", 0);
                intent.setPackage("com.motorola.mobiledesktop.core");
                intent.setFlags(268435456);
                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                activityOptionsMakeBasic.setLaunchDisplayId(getContext().getDisplay().getDisplayId());
                getContext().startActivityAsUser(intent, activityOptionsMakeBasic.toBundle(), UserHandle.CURRENT);
                return true;
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.KeyButtonView, android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mTaskBarController.removeCallback(this.mTaskBarControllerListener);
        this.mVolumeBroadcastReceiver.unregisterReceiver(getContext());
        this.mCurrentUserContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        this.mUserTracker.stopTracking();
        TaskbarGuideArrowTipView taskbarGuideArrowTipView = this.mTaskbarGuideArrowTipView;
        if (taskbarGuideArrowTipView != null) {
            taskbarGuideArrowTipView.close(true, false);
            this.mTaskbarGuideArrowTipView = null;
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        MotoFeature motoFeature = (MotoFeature) Dependency.get(MotoFeature.class);
        this.mIsMobileUiOrAppStream = motoFeature.isMobileUiOrAppStreamDisplay(getContext());
        this.mIsRdpDisplay = motoFeature.isRdpDesktopDisplay(getContext().getDisplay().getDisplayId());
        this.mAudioManager = (AudioManager) getContext().getSystemService("audio");
        this.mUserTracker = new CurrentUserTracker((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)) { // from class: com.motorola.taskbar.sysicons.VolumeSysIconView.1
            @Override // com.android.systemui.settings.CurrentUserTracker
            public void onUserSwitched(int i) {
                boolean zIsAttachedToWindow = VolumeSysIconView.this.isAttachedToWindow();
                if (zIsAttachedToWindow) {
                    VolumeSysIconView.this.mCurrentUserContext.getContentResolver().unregisterContentObserver(VolumeSysIconView.this.mContentObserver);
                }
                VolumeSysIconView.this.updateUserContext(i);
                if (zIsAttachedToWindow) {
                    VolumeSysIconView.this.mCurrentUserContext.getContentResolver().registerContentObserver(VolumePanelLayout.CONTENT_URI_DEVICES, true, VolumeSysIconView.this.mContentObserver);
                    VolumeSysIconView.this.updateStatusByProvider();
                }
            }
        };
        updateUserContext(ActivityManager.getCurrentUser());
    }

    @Override // android.widget.ImageView, android.view.View
    public void setVisibility(int i) {
        TaskbarGuideArrowTipView taskbarGuideArrowTipView;
        super.setVisibility(i);
        if (i == 0 || (taskbarGuideArrowTipView = this.mTaskbarGuideArrowTipView) == null || !taskbarGuideArrowTipView.isOpened()) {
            return;
        }
        this.mTaskbarGuideArrowTipView.close(false, false);
    }
}
