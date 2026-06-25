package com.motorola.taskbar.bar;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.trust.TrustManager;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.Toast;
import android.window.WindowContext;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.statusbar.policy.KeyButtonView;
import com.motorola.extendscreenshot.ScreenshotController;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.R$style;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.model.DisplayWindowManager;
import com.motorola.taskbar.panel.VolumePanelLayout;
import com.motorola.taskbar.recent.RecentsController;
import com.motorola.taskbar.settings.helper.KeyboardShortcutHelper;
import com.motorola.taskbar.shortcut.ShortcutKeyDispatcher;
import com.motorola.taskbar.util.AppUtils;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.taskbar.util.TooltipPopupManager;
import java.util.Calendar;
import java.util.List;
import motorola.core_services.activity.MotoActivityManager;

/* JADX INFO: loaded from: classes2.dex */
public class TaskBarButtonClickHelper {
    private static final Boolean DEBUG = Boolean.valueOf(DebugConfig.DEBUG_COMMON);
    private ActivityManager mActivityManager;
    private final Context mContext;
    private int mDisplayId;
    KeyguardManager mKeyguardManager;
    private AlertDialog mRestartDialog;
    private int mTooltipPopupOffsetY;
    private TrustManager mTrustManager;
    private UserManager mUserManager;
    private VolumePanelLayout mVolumePanelView;
    private WindowManager.LayoutParams mVolumePanelViewLayoutParams;
    private DisplayWindowManager.WindowViewManager mVolumePanelWindowViewManager;
    private Handler mUiHandler = new Handler();
    private boolean mIsVolumePanelViewShowing = false;
    private MirrorPhonePanelController mMirrorPhonePanelController = (MirrorPhonePanelController) Dependency.get(MirrorPhonePanelController.class);
    private ShortcutKeyDispatcher mShortcutKeyDispatcher = (ShortcutKeyDispatcher) Dependency.get(ShortcutKeyDispatcher.class);
    private QSNotificationPanelController mQSNotificationPanelController = (QSNotificationPanelController) Dependency.get(QSNotificationPanelController.class);

    public TaskBarButtonClickHelper(Context context, int i) {
        this.mTooltipPopupOffsetY = 0;
        this.mContext = context;
        this.mDisplayId = context.getDisplayId();
        this.mTooltipPopupOffsetY = i;
        this.mActivityManager = (ActivityManager) context.getSystemService("activity");
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mTrustManager = (TrustManager) context.getSystemService("trust");
        this.mKeyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
    }

    private void checkR4SettingsIfExist(Intent intent) {
        List listQueryIntentActivitiesAsUser = this.mContext.getPackageManager().queryIntentActivitiesAsUser(intent, 0, ActivityManager.getCurrentUser());
        if (listQueryIntentActivitiesAsUser == null || listQueryIntentActivitiesAsUser.size() <= 0) {
            Log.d("checkR4SettingsIfExist", "R4Settings is not exits");
            intent.setAction("com.motorola.taskbar.settings.OPEN_SETTING");
            intent.setPackage(this.mContext.getPackageName());
        }
    }

    public static Intent getClockIntent() {
        return new Intent("android.intent.action.SHOW_ALARMS");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideVolumeDialog() {
        this.mIsVolumePanelViewShowing = false;
        this.mVolumePanelWindowViewManager.removeView(this.mVolumePanelView);
    }

    private void initCheckbox(CheckBox checkBox, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String desktopRestartModeByPackages = AppUtils.getDesktopRestartModeByPackages(str);
        if (DEBUG.booleanValue()) {
            Log.d("TaskBarButtonClickHelper", "toggleRestartDialog: " + desktopRestartModeByPackages + " topPackage :" + str);
        }
        desktopRestartModeByPackages.hashCode();
        switch (desktopRestartModeByPackages) {
            case "user_restart":
                checkBox.setChecked(true);
                break;
            case "force_continue":
                checkBox.setChecked(false);
                checkBox.setEnabled(false);
                break;
            case "user_continue":
                checkBox.setChecked(false);
                break;
            case "force_restart":
                checkBox.setChecked(true);
                checkBox.setEnabled(false);
                break;
        }
    }

    private boolean isKeyguardLocked() {
        KeyguardManager keyguardManager = this.mKeyguardManager;
        return keyguardManager != null && keyguardManager.isKeyguardLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showScreenshotTools$0(View view) {
        ((ScreenshotController) Dependency.get(ScreenshotController.class)).takeScreenshot(8, this.mDisplayId, view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showVolumeDialog$1(View view) {
        hideVolumeDialog();
    }

    private void lockProfiles() {
        int currentUser = ActivityManager.getCurrentUser();
        for (int i : this.mUserManager.getEnabledProfileIds(currentUser)) {
            if (i != currentUser) {
                this.mTrustManager.setDeviceLockedForUser(i, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restartTopApplication() {
        updateAppMode();
        int displayId = this.mContext.getDisplayId();
        if (MotoFeature.isAppStreamMode(this.mContext)) {
            Intent intent = new Intent();
            intent.setAction("com.motorola.mobiledesktop.action.RESTART_APP");
            intent.setPackage("com.motorola.mobiledesktop");
            intent.putExtra("display_id", displayId);
            this.mContext.startService(intent);
            return;
        }
        if (MotoFeature.isMobileUiMode(this.mContext.getDisplay())) {
            int topTaskId = AppUtils.getTopTaskId(displayId);
            if (topTaskId > 0) {
                MotoActivityManager.getInstance(this.mContext).killAndRestart(topTaskId, true);
            } else {
                Log.e("TaskBarButtonClickHelper", "restartTopApplication is error,  top taskid error.");
            }
        }
    }

    private static void sendEvent(int i, int i2, int i3, long j, long j2, int i4) {
        KeyEvent keyEvent = new KeyEvent(j2, j, i, i2, (i3 & 128) != 0 ? 1 : 0, 0, -1, 0, i3 | 72, 257);
        if (i4 != -1) {
            keyEvent.setDisplayId(i4);
        }
        InputManager.getInstance().injectInputEvent(keyEvent, 0);
    }

    private void showVolumeDialog(View view) {
        if (this.mVolumePanelWindowViewManager == null) {
            DisplayWindowManager.WindowViewManager windowViewManager = DisplayWindowManager.getWindowViewManager(this.mDisplayId, 2041);
            this.mVolumePanelWindowViewManager = windowViewManager;
            windowViewManager.addConfigurationChangedCallbacks(new DisplayWindowManager.ConfigurationChangedCallback() { // from class: com.motorola.taskbar.bar.TaskBarButtonClickHelper.1
                @Override // com.motorola.taskbar.model.DisplayWindowManager.ConfigurationChangedCallback
                public void onConfigurationChanged(Configuration configuration) {
                    if (TaskBarButtonClickHelper.this.mVolumePanelView != null) {
                        TaskBarButtonClickHelper.this.mVolumePanelView.onUiModeChanged();
                    }
                }
            });
        }
        boolean z = MotoFeature.isAppStreamMode(this.mContext) || MotoFeature.isMobileUiMode(this.mContext.getDisplay());
        if (this.mVolumePanelView == null) {
            int dimensionPixelSize = z ? -1 : this.mContext.getResources().getDimensionPixelSize(R$dimen.media_panel_width);
            VolumePanelLayout volumePanelLayout = (VolumePanelLayout) LayoutInflater.from(this.mVolumePanelWindowViewManager.windowContext).inflate(z ? R$layout.mobileui_volume_panel : R$layout.volume_panel, (ViewGroup) null);
            this.mVolumePanelView = volumePanelLayout;
            volumePanelLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.motorola.taskbar.bar.TaskBarButtonClickHelper.2
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    if (TaskBarButtonClickHelper.this.mVolumePanelView.isAttachedToWindow()) {
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        TaskBarButtonClickHelper.this.mContext.getDisplay().getRealMetrics(displayMetrics);
                        int height = TaskBarButtonClickHelper.this.mVolumePanelView.getHeight();
                        int i9 = displayMetrics.heightPixels - TaskBarButtonClickHelper.this.mVolumePanelViewLayoutParams.y;
                        if (height < i9) {
                            i9 = -2;
                        }
                        if (i9 != TaskBarButtonClickHelper.this.mVolumePanelViewLayoutParams.height) {
                            TaskBarButtonClickHelper.this.mVolumePanelViewLayoutParams.height = i9;
                            TaskBarButtonClickHelper.this.mVolumePanelWindowViewManager.updateViewLayout(TaskBarButtonClickHelper.this.mVolumePanelView, TaskBarButtonClickHelper.this.mVolumePanelViewLayoutParams);
                        }
                    }
                }
            });
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(dimensionPixelSize, -2, 2041, R.interpolator.launch_task_micro_alpha, -3);
            this.mVolumePanelViewLayoutParams = layoutParams;
            layoutParams.setFitInsetsTypes(0);
            WindowManager.LayoutParams layoutParams2 = this.mVolumePanelViewLayoutParams;
            layoutParams2.gravity = 83;
            layoutParams2.windowAnimations = R$style.Animation_VolumePanel;
            layoutParams2.setTitle("DesktopVolume: " + this.mDisplayId);
            this.mVolumePanelView.findViewById(R$id.done).setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarButtonClickHelper$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$showVolumeDialog$1(view2);
                }
            });
            this.mVolumePanelView.setOnTouchListener(new View.OnTouchListener() { // from class: com.motorola.taskbar.bar.TaskBarButtonClickHelper.3
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view2, MotionEvent motionEvent) {
                    if (motionEvent.getAction() != 4) {
                        return false;
                    }
                    TaskBarButtonClickHelper.this.hideVolumeDialog();
                    return false;
                }
            });
        }
        int[] locationOnScreen = view.getLocationOnScreen();
        WindowManager.LayoutParams layoutParams3 = this.mVolumePanelViewLayoutParams;
        layoutParams3.x = locationOnScreen[0] - (layoutParams3.width / 2);
        layoutParams3.y = view.getHeight() + (z ? 0 : this.mTooltipPopupOffsetY);
        this.mIsVolumePanelViewShowing = true;
        this.mVolumePanelWindowViewManager.addView(this.mVolumePanelView, this.mVolumePanelViewLayoutParams);
    }

    private void startCurrentUserActivity(Intent intent, Bundle bundle) {
        int currentUser = ActivityManager.getCurrentUser();
        List listQueryIntentActivitiesAsUser = this.mContext.getPackageManager().queryIntentActivitiesAsUser(intent, 0, currentUser);
        if (listQueryIntentActivitiesAsUser == null || listQueryIntentActivitiesAsUser.size() <= 0) {
            Toast.makeText(this.mContext, R$string.app_not_found, 0).show();
        } else {
            this.mContext.startActivityAsUser(intent, bundle, UserHandle.of(currentUser));
        }
    }

    public static void triggerHomeButtonForSecondUser(Context context, int i) {
        Intent intent = new Intent("com.motorola.launcher3.secondarydisplay.action.ALL_APPS_DISPLAY2");
        intent.setPackage("com.motorola.launcher3");
        intent.putExtra("show", false);
        context.sendBroadcastAsUser(intent, UserHandle.CURRENT);
        long jUptimeMillis = SystemClock.uptimeMillis();
        sendEvent(0, 3, 0, jUptimeMillis, jUptimeMillis, i);
        sendEvent(1, 3, 0, SystemClock.uptimeMillis(), jUptimeMillis, i);
    }

    private void updateAppMode() {
        AlertDialog alertDialog = this.mRestartDialog;
        if (alertDialog != null) {
            int i = R$id.checkbox;
            if (alertDialog.findViewById(i) != null) {
                CheckBox checkBox = (CheckBox) this.mRestartDialog.findViewById(i);
                if (checkBox.isEnabled()) {
                    String topTaskPackage = AppUtils.getTopTaskPackage(this.mContext.getDisplayId());
                    if (TextUtils.isEmpty(topTaskPackage)) {
                        return;
                    }
                    String desktopRestartModeByPackages = AppUtils.getDesktopRestartModeByPackages(topTaskPackage);
                    String str = checkBox.isChecked() ? "user_restart" : "user_continue";
                    if (str.equals(desktopRestartModeByPackages)) {
                        return;
                    }
                    AppUtils.setDesktopRestartModeByPackages(topTaskPackage, str);
                }
            }
        }
    }

    public Intent getCalendarDayIntent() {
        return getCalendarDayIntent(Calendar.getInstance().getTimeInMillis());
    }

    public Intent getCalendarDayIntent(long j) {
        Uri.Builder builderBuildUpon = CalendarContract.CONTENT_URI.buildUpon();
        builderBuildUpon.appendPath("time");
        ContentUris.appendId(builderBuildUpon, j);
        return new Intent("android.intent.action.VIEW").setData(builderBuildUpon.build());
    }

    public void handleAirRemoteHome() {
        this.mShortcutKeyDispatcher.handleAirRemoteHome(this.mDisplayId);
    }

    public void handleAirRemoteMenu() {
        this.mShortcutKeyDispatcher.handleAirRemoteMenu(this.mDisplayId);
    }

    public void hideRestartDialog() {
        AlertDialog alertDialog = this.mRestartDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mRestartDialog.dismiss();
    }

    public void lockDevice() {
        try {
            if (this.mActivityManager.getLockTaskModeState() != 0) {
                Toast.makeText(this.mContext, R$string.app_pinned_toast, 0).show();
                return;
            }
            if (new LockPatternUtils(this.mContext).isLockScreenDisabled(ActivityManager.getCurrentUser())) {
                ((PowerManager) this.mContext.getSystemService("power")).goToSleep(SystemClock.uptimeMillis(), 0, 256);
            } else if (((MotoFeature) Dependency.get(MotoFeature.class)).isRemoteDisplay(this.mContext) && isKeyguardLocked()) {
                ((TaskBarServiceProxy) Dependency.get(TaskBarServiceProxy.class)).forceShowKeyguardPresentation();
            } else {
                WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
                lockProfiles();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestHideVolumeDialog() {
        if (this.mVolumePanelView == null || !this.mIsVolumePanelViewShowing) {
            return;
        }
        hideVolumeDialog();
    }

    public void requestSwitchMirrorPanel() {
        this.mMirrorPhonePanelController.requestSwitchPanel(this.mDisplayId);
    }

    public void requestSwitchNotificationPanel() {
        Prefs.putBoolean(this.mContext, "NotificationPanelGuider", true);
        this.mQSNotificationPanelController.requestSwitchNotificationPanel(this.mDisplayId);
    }

    public void requestSwitchVolumeDialog(View view) {
        if (this.mVolumePanelView != null && this.mIsVolumePanelViewShowing) {
            hideVolumeDialog();
        } else {
            showVolumeDialog(view);
            Prefs.putBoolean(this.mContext, "VolumePanelGuider", true);
        }
    }

    public void rotateScreen() {
        Display display = this.mContext.getDisplay();
        if (display == null) {
            return;
        }
        try {
            WindowManagerGlobal.getWindowManagerService().freezeDisplayRotation(this.mContext.getDisplayId(), display.getRotation() == 0 ? 1 : 0, this.mContext.getPackageName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void showIMEDialog() {
        ((InputMethodManager) this.mContext.getSystemService(InputMethodManager.class)).showInputMethodPickerFromSystem(true, this.mDisplayId);
    }

    public void showScreenshotTools(final View view) {
        if (this.mDisplayId == 0) {
            return;
        }
        TooltipPopupManager tooltipPopupManager = ((TaskBarController) Dependency.get(TaskBarController.class)).getTooltipPopupManager(view);
        if (tooltipPopupManager.isShowing()) {
            tooltipPopupManager.hide(tooltipPopupManager.getShowingId(), true);
        }
        this.mUiHandler.postDelayed(new Runnable() { // from class: com.motorola.taskbar.bar.TaskBarButtonClickHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showScreenshotTools$0(view);
            }
        }, 200L);
    }

    public void startBatteryActivity() {
        Intent intent = new Intent("android.intent.action.POWER_USAGE_SUMMARY");
        intent.setFlags(268435456);
        intent.setFlags(32768);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchDisplayId(this.mDisplayId);
        startCurrentUserActivity(intent, activityOptionsMakeBasic.toBundle());
    }

    public void startCalendarActivity() {
        Intent calendarDayIntent = getCalendarDayIntent();
        calendarDayIntent.setFlags(268435456);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchDisplayId(this.mDisplayId);
        startCurrentUserActivity(calendarDayIntent, activityOptionsMakeBasic.toBundle());
    }

    public void startClockActivity() {
        Intent clockIntent = getClockIntent();
        clockIntent.setFlags(268435456);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchDisplayId(this.mDisplayId);
        startCurrentUserActivity(clockIntent, activityOptionsMakeBasic.toBundle());
    }

    public void startSearchActivity() {
        Intent intent = new Intent("android.intent.action.WEB_SEARCH");
        intent.setFlags(268435456);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchDisplayId(this.mDisplayId);
        try {
            startCurrentUserActivity(intent, activityOptionsMakeBasic.toBundle());
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void startSettingsActivity() {
        if (this.mDisplayId == 0) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.motorola.readyfor.settings.OPEN_SETTING");
        intent.setPackage("com.motorola.mobiledesktop");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(268435456);
        intent.putExtra("display_id", this.mDisplayId);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchDisplayId(this.mDisplayId);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mContext.getDisplay().getRealMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        activityOptionsMakeBasic.setLaunchBounds(new Rect((int) (i * 0.25f), (int) (i2 * 0.25f), (int) (i * 0.75f), (int) (i2 * 0.75f)));
        Bundle bundle = activityOptionsMakeBasic.toBundle();
        checkR4SettingsIfExist(intent);
        startCurrentUserActivity(intent, bundle);
    }

    public void startWifiSettingActivity() {
        Intent intent = new Intent("android.settings.WIFI_SETTINGS");
        intent.setFlags(268435456);
        intent.setFlags(32768);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchDisplayId(this.mDisplayId);
        startCurrentUserActivity(intent, activityOptionsMakeBasic.toBundle());
    }

    public void toggleAppTray() {
        if (this.mDisplayId == 0) {
            Log.d("toggleAppTray", "mDisplayId: " + this.mDisplayId + ",DEFAULT_DISPLAY0");
            return;
        }
        Intent intent = new Intent("com.motorola.launcher3.secondarydisplay.action.SECONDARY_DISPLAY_APP_DRAWER2");
        intent.setPackage("com.motorola.launcher3");
        if (this.mContext.getPackageManager().resolveActivity(intent, 0) == null) {
            Log.d("toggleAppTray", "resolveActivity ==null ");
            return;
        }
        intent.setFlags(268500992);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchDisplayId(this.mDisplayId);
        startCurrentUserActivity(intent, activityOptionsMakeBasic.toBundle());
    }

    public void toggleRecents() {
        ((RecentsController) Dependency.get(RecentsController.class)).toggleRecents(this.mDisplayId, false);
    }

    public void toggleRestartDialog() {
        AlertDialog alertDialog = this.mRestartDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mRestartDialog.dismiss();
            return;
        }
        String topTaskPackage = AppUtils.getTopTaskPackage(this.mContext.getDisplayId());
        if (TextUtils.isEmpty(topTaskPackage)) {
            Log.w("TaskBarButtonClickHelper", "toggleRestartDialog failed, top task package is empty");
            return;
        }
        Context context = this.mContext;
        WindowContext windowContextCreateWindowContext = context.createDisplayContext(context.getDisplay()).createWindowContext(2008, null);
        windowContextCreateWindowContext.setTheme(windowContextCreateWindowContext.getResources().getConfiguration().isNightModeActive() ? R.style.Theme.DeviceDefault.Dialog.Alert : R.style.Theme.DeviceDefault.Light.Dialog.Alert);
        AlertDialog.Builder builder = new AlertDialog.Builder(windowContextCreateWindowContext);
        builder.setTitle(this.mContext.getString(R$string.restart_dialog_title));
        View viewInflate = View.inflate(windowContextCreateWindowContext, R$layout.restart_dialog_checkbox, null);
        CheckBox checkBox = (CheckBox) viewInflate.findViewById(R$id.checkbox);
        checkBox.setText(this.mContext.getString(R$string.restart_dialog_checkbox_title));
        builder.setView(viewInflate);
        initCheckbox(checkBox, topTaskPackage);
        builder.setPositiveButton(this.mContext.getString(R$string.restart_dialog_btn_confirm), new DialogInterface.OnClickListener() { // from class: com.motorola.taskbar.bar.TaskBarButtonClickHelper.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                TaskBarButtonClickHelper.this.restartTopApplication();
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton(this.mContext.getString(R$string.restart_dialog_btn_cancel), new DialogInterface.OnClickListener(this) { // from class: com.motorola.taskbar.bar.TaskBarButtonClickHelper.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setCancelable(true);
        AlertDialog alertDialogCreate = builder.create();
        this.mRestartDialog = alertDialogCreate;
        alertDialogCreate.getWindow().setType(2008);
        this.mRestartDialog.getWindow().setTitle("Restart");
        this.mRestartDialog.show();
    }

    public void toggleShortcutHelper() {
        if (this.mDisplayId == 0) {
            return;
        }
        if (KeyboardShortcutHelper.isShowing()) {
            this.mContext.sendBroadcastAsUser(new Intent("com.motorola.taskbar.settings.DISMISS_HELPER"), UserHandle.CURRENT);
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.motorola.taskbar.settings.SHORTCUT_HELPER");
        intent.setPackage(this.mContext.getPackageName());
        intent.setFlags(268500992);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchWindowingMode(1);
        activityOptionsMakeBasic.setLaunchDisplayId(this.mDisplayId);
        startCurrentUserActivity(intent, activityOptionsMakeBasic.toBundle());
    }

    public void triggerBackButton(KeyButtonView keyButtonView) {
        keyButtonView.sendEvent(0, 4, 0);
        keyButtonView.sendEvent(1, 4, 0);
    }

    public void triggerHomeButton(KeyButtonView keyButtonView) {
        Intent intent = new Intent("com.motorola.launcher3.secondarydisplay.action.ALL_APPS_DISPLAY2");
        intent.setPackage("com.motorola.launcher3");
        intent.putExtra("show", false);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
        keyButtonView.sendEvent(0, 3, 0);
        keyButtonView.sendEvent(1, 3, 0);
    }
}
