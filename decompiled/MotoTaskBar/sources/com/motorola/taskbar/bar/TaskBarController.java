package com.motorola.taskbar.bar;

import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.InsetsFrameProvider;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.TaskBarServiceCallBack;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.model.DisplayWindowManager;
import com.motorola.taskbar.model.NavIconController;
import com.motorola.taskbar.onboard.OnBoardActivity;
import com.motorola.taskbar.qs.PanelSizes;
import com.motorola.taskbar.qsnotification.QsNotificationComponentStarter;
import com.motorola.taskbar.reflect.com.android.internal.RR;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.taskbar.util.TooltipPopupManager;
import com.motorola.taskbar.utils.ExtendedFunction;
import com.motorola.trackpad.ReadyForProxy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public class TaskBarController implements TaskBarServiceCallBack, DeviceProvisionedController.DeviceProvisionedListener, CallbackController {
    private static final boolean DEBUG = DebugConfig.DEBUG_TASK_BAR_CONTROLLER;
    private static final String TAG = "TaskBarController";
    private final Context mContext;
    private final DeviceProvisionedController mDeviceProvisionedController;
    private final DisplayManager mDisplayManager;
    private final ExternalDisplayTutorialManager mExternalDisplayTutorialManager;
    private final Handler mHandler;
    private final NavIconController mNavIconController;
    private final QSNotificationPanelController mQSNotificationPanelController;
    private final QsNotificationComponentStarter mQsNotificationComponentStarter;
    private StatusBarManager mStatusBarManager;
    private TaskBarServiceProxy mTaskBarServiceProxy;
    private final WindowManager mWindowManager;
    private SparseArray mTaskBarViews = new SparseArray();
    private SparseArray mTooltipPopupManagers = new SparseArray();
    private SparseArray mTaskBarVisibilitiesFromSystemUI = new SparseArray();
    private SparseArray mImeButtonVisibilities = new SparseArray();
    private final ArrayList mListeners = new ArrayList();
    private SparseArray mTaskBarWindowStatesFromSystemUI = new SparseArray();
    private SparseArray mTaskBarVisibilities = new SparseArray();
    private SparseArray mTaskBarReallyVisibilities = new SparseArray();
    private SparseArray mDisplaysChooserMode = new SparseArray();
    Set mOnBoardActivityShowingDisplays = new HashSet();
    private boolean mIsSystemUIReady = false;
    private SparseArray mDisplayWindowViewManagerArray = new SparseArray();
    private final HardDisplayMode mCurrentHardDisplayMode = new HardDisplayMode();
    private SparseArray mTaskBarChangedArray = new SparseArray();
    private SparseArray mExternalChooserVisibilities = new SparseArray();
    private BroadcastReceiver mBroadcastReceiver = new AnonymousClass1();
    private final TaskBarCreateCallback mTaskBarCreateCallback = new TaskBarCreateCallback() { // from class: com.motorola.taskbar.bar.TaskBarController.2
        @Override // com.motorola.taskbar.bar.TaskBarController.TaskBarCreateCallback
        public void onTaskBarCreate(boolean z, int i) {
            if (!z || TaskBarController.this.mStatusBarManager == null) {
                return;
            }
            ExtendedFunction.refreshDesktopIcons(TaskBarController.this.mStatusBarManager, i);
        }
    };
    private final ReadyForProxy.OnModeChooserListener mChooserListener = new ReadyForProxy.OnModeChooserListener() { // from class: com.motorola.taskbar.bar.TaskBarController.4
        @Override // com.motorola.trackpad.ReadyForProxy.OnModeChooserListener
        public void onModeChooserAnimationStateChange(String str, int i, boolean z) {
            TaskBarView taskBarView = (TaskBarView) TaskBarController.this.mTaskBarViews.get(i);
            if (!"normal".equals(str) || taskBarView == null) {
                return;
            }
            if (z) {
                taskBarView.animate().setDuration(250L).translationY(0.0f).alpha(1.0f).start();
            } else {
                taskBarView.setAlpha(0.0f);
                taskBarView.setTranslationY(taskBarView.getHeight());
            }
        }

        @Override // com.motorola.trackpad.ReadyForProxy.OnModeChooserListener
        public void onModeChooserShowingStateChanged(int i, boolean z) {
            Log.d(TaskBarController.TAG, "onModeChooserShowingStateChanged, displayId :" + i + " showing:" + z);
            if (i == -1) {
                return;
            }
            TaskBarController.this.mExternalChooserVisibilities.put(i, Boolean.valueOf(z));
            TaskBarController.this.setTaskBarViewVisibility(i, z ? 8 : 0);
        }
    };
    private final ReadyForProxy.OnReadyForServiceStatusListener mReadyForServiceStatusListener = new ReadyForProxy.OnReadyForServiceStatusListener() { // from class: com.motorola.taskbar.bar.TaskBarController.5
        @Override // com.motorola.trackpad.ReadyForProxy.OnReadyForServiceStatusListener
        public void onStatusChanged(int i, boolean z) {
            if (z) {
                return;
            }
            TaskBarController.this.mChooserListener.onModeChooserShowingStateChanged(i, z);
        }
    };

    /* JADX INFO: renamed from: com.motorola.taskbar.bar.TaskBarController$1, reason: invalid class name */
    class AnonymousClass1 extends BroadcastReceiver {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(int i) {
            TaskBarController.this.notifiyOnBoardActivityShowingChanged(i, true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$1(int i) {
            TaskBarController.this.notifiyOnBoardActivityShowingChanged(i, false);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            final int intExtra;
            String action = intent.getAction();
            if (OnBoardActivity.ACTION_ON_BOARD_ACTIVITY_START.equals(action)) {
                final int intExtra2 = intent.getIntExtra("displayId", -1);
                if (intExtra2 > 0) {
                    TaskBarController.this.mOnBoardActivityShowingDisplays.add(Integer.valueOf(intExtra2));
                    TaskBarController.this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.bar.TaskBarController$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onReceive$0(intExtra2);
                        }
                    });
                    return;
                }
                return;
            }
            if (!OnBoardActivity.ACTION_ON_BOARD_ACTIVITY_STOP.equals(action) || (intExtra = intent.getIntExtra("displayId", -1)) <= 0) {
                return;
            }
            TaskBarController.this.mOnBoardActivityShowingDisplays.remove(Integer.valueOf(intExtra));
            TaskBarController.this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.bar.TaskBarController$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onReceive$1(intExtra);
                }
            });
        }
    }

    class CreateTaskBarConfigurationChanged implements DisplayWindowManager.ConfigurationChangedCallback {
        private final Runnable mCreateTaskbarRunnable;
        private final int mDisplayId;
        private final Handler mHandler;
        private final Runnable mReCreateTaskBarRunnable;
        private final TaskBarCreateCallback mTaskBarCreateCallback;
        private final DisplayWindowManager.WindowViewManager mWindowViewManager;
        private Configuration mConfiguration = new Configuration();
        private boolean mHasDoneConfigurationChanged = false;
        private long mLastConfigurationChangedTime = 0;

        public CreateTaskBarConfigurationChanged(int i, TaskBarCreateCallback taskBarCreateCallback, DisplayWindowManager.WindowViewManager windowViewManager) {
            Runnable runnable = new Runnable() { // from class: com.motorola.taskbar.bar.TaskBarController.CreateTaskBarConfigurationChanged.1
                @Override // java.lang.Runnable
                public void run() {
                    Log.d(TaskBarController.TAG, "delay create taskbar");
                    if (CreateTaskBarConfigurationChanged.this.mWindowViewManager == null || CreateTaskBarConfigurationChanged.this.mWindowViewManager.getContext() == null) {
                        return;
                    }
                    CreateTaskBarConfigurationChanged createTaskBarConfigurationChanged = CreateTaskBarConfigurationChanged.this;
                    createTaskBarConfigurationChanged.onConfigurationChanged(createTaskBarConfigurationChanged.mWindowViewManager.getContext().getResources().getConfiguration());
                }
            };
            this.mCreateTaskbarRunnable = runnable;
            this.mReCreateTaskBarRunnable = new Runnable() { // from class: com.motorola.taskbar.bar.TaskBarController.CreateTaskBarConfigurationChanged.3
                @Override // java.lang.Runnable
                public void run() {
                    CreateTaskBarConfigurationChanged createTaskBarConfigurationChanged = CreateTaskBarConfigurationChanged.this;
                    TaskBarController.this.reCreatTaskBar(createTaskBarConfigurationChanged.mDisplayId);
                }
            };
            this.mDisplayId = i;
            this.mTaskBarCreateCallback = taskBarCreateCallback;
            this.mWindowViewManager = windowViewManager;
            Handler handler = new Handler(Looper.getMainLooper());
            this.mHandler = handler;
            handler.postDelayed(runnable, 2000L);
            this.mConfiguration.setTo(windowViewManager.getContext().getResources().getConfiguration());
        }

        public boolean hasDoneConfigurationChanged() {
            return this.mHasDoneConfigurationChanged;
        }

        @Override // com.motorola.taskbar.model.DisplayWindowManager.ConfigurationChangedCallback
        public void onConfigurationChanged(Configuration configuration) {
            if (TaskBarController.DEBUG) {
                Log.d(TaskBarController.TAG, "create taskbar onConfigurationChanged: " + configuration);
            }
            DisplayWindowManager.WindowViewManager windowViewManager = this.mWindowViewManager;
            if (windowViewManager == null) {
                return;
            }
            this.mHasDoneConfigurationChanged = true;
            windowViewManager.removeConfigurationChangedCallbacks(this);
            this.mHandler.removeCallbacks(this.mCreateTaskbarRunnable);
            boolean zHandleCreateTaskBar = TaskBarController.this.handleCreateTaskBar(this.mDisplayId, this.mWindowViewManager);
            TaskBarCreateCallback taskBarCreateCallback = this.mTaskBarCreateCallback;
            if (taskBarCreateCallback != null) {
                taskBarCreateCallback.onTaskBarCreate(zHandleCreateTaskBar, this.mDisplayId);
            }
            this.mWindowViewManager.addConfigurationChangedCallbacks(new DisplayWindowManager.ConfigurationChangedCallback() { // from class: com.motorola.taskbar.bar.TaskBarController.CreateTaskBarConfigurationChanged.2
                @Override // com.motorola.taskbar.model.DisplayWindowManager.ConfigurationChangedCallback
                public void onConfigurationChanged(Configuration configuration2) {
                    if (TaskBarController.DEBUG) {
                        Log.d(TaskBarController.TAG, "Display onConfigurationChanged: " + configuration2);
                    }
                    Configuration configuration3 = new Configuration();
                    configuration3.setTo(CreateTaskBarConfigurationChanged.this.mConfiguration);
                    int iUpdateFrom = CreateTaskBarConfigurationChanged.this.mConfiguration.updateFrom(configuration2) & 1073755780;
                    if (iUpdateFrom != 0) {
                        if (iUpdateFrom == 1024 && configuration3.densityDpi == configuration2.densityDpi && configuration3.screenWidthDp == configuration2.screenWidthDp) {
                            return;
                        }
                        CreateTaskBarConfigurationChanged.this.mHandler.removeCallbacks(CreateTaskBarConfigurationChanged.this.mReCreateTaskBarRunnable);
                        CreateTaskBarConfigurationChanged.this.mHandler.postDelayed(CreateTaskBarConfigurationChanged.this.mReCreateTaskBarRunnable, 500L);
                    }
                }
            });
        }
    }

    class HardDisplayMode {
        public int displayId;
        public String mode;

        private HardDisplayMode() {
            this.displayId = -1;
            this.mode = null;
        }
    }

    public interface TaskBarControllerListener {
        void onOnBoardActivityShowingChanged(int i, boolean z);

        void onTaskBarReallyVisibilityChanged(int i, boolean z);
    }

    public interface TaskBarCreateCallback {
        void onTaskBarCreate(boolean z, int i);
    }

    public TaskBarController(Context context, Handler handler, TaskBarServiceProxy taskBarServiceProxy, QSNotificationPanelController qSNotificationPanelController, DeviceProvisionedController deviceProvisionedController, ExternalDisplayTutorialManager externalDisplayTutorialManager, QsNotificationComponentStarter qsNotificationComponentStarter, NavIconController navIconController) {
        this.mContext = context;
        this.mHandler = handler;
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mExternalDisplayTutorialManager = externalDisplayTutorialManager;
        this.mQSNotificationPanelController = qSNotificationPanelController;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mStatusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        this.mTaskBarServiceProxy = taskBarServiceProxy;
        this.mNavIconController = navIconController;
        this.mQsNotificationComponentStarter = qsNotificationComponentStarter;
    }

    private void createTaskBar(Display display) {
        createTaskBar(display, null);
    }

    private void createTaskBar(Display display, TaskBarCreateCallback taskBarCreateCallback) {
        if (display == null) {
            Log.w(TAG, "createTaskBar display is null");
            if (taskBarCreateCallback != null) {
                taskBarCreateCallback.onTaskBarCreate(false, -1);
                return;
            }
            return;
        }
        if (display.getDisplayId() == 0) {
            Log.w(TAG, "createTaskBar display is default");
            if (taskBarCreateCallback != null) {
                taskBarCreateCallback.onTaskBarCreate(false, 0);
                return;
            }
            return;
        }
        if (!MotoFeature.isDesktopModeDisplay(display)) {
            if (DEBUG) {
                Log.d(TAG, "createTaskBar is NOT DesktopModeDisplay: " + display.getDisplayId());
            }
            if (taskBarCreateCallback != null) {
                taskBarCreateCallback.onTaskBarCreate(false, display.getDisplayId());
                return;
            }
            return;
        }
        if (this.mTaskBarViews.get(display.getDisplayId()) != null) {
            if (DEBUG) {
                Log.w(TAG, "createTaskBar already created " + display.getDisplayId());
            }
            if (taskBarCreateCallback != null) {
                taskBarCreateCallback.onTaskBarCreate(false, display.getDisplayId());
                return;
            }
            return;
        }
        int displayId = display.getDisplayId();
        try {
            if (!WindowManagerGlobal.getWindowManagerService().hasNavigationBar(displayId)) {
                if (DEBUG) {
                    Log.d(TAG, "createTaskBar has not NavigationBar: " + display.getDisplayId());
                }
                if (taskBarCreateCallback != null) {
                    taskBarCreateCallback.onTaskBarCreate(false, displayId);
                    return;
                }
                return;
            }
            createTooltipPopupManagers(display);
            DisplayWindowManager.WindowViewManager windowViewManager = DisplayWindowManager.getWindowViewManager(displayId, 2019);
            if (windowViewManager == null) {
                Log.w(TAG, "createTaskBar create WindowViewManager failed, the display was removed? " + displayId);
                if (taskBarCreateCallback != null) {
                    taskBarCreateCallback.onTaskBarCreate(false, displayId);
                    return;
                }
                return;
            }
            CreateTaskBarConfigurationChanged createTaskBarConfigurationChanged = (CreateTaskBarConfigurationChanged) this.mTaskBarChangedArray.get(displayId);
            if (createTaskBarConfigurationChanged != null && !createTaskBarConfigurationChanged.hasDoneConfigurationChanged()) {
                Log.w(TAG, "createTaskBar wait for done configuration changed");
                return;
            }
            if (this.mDisplayWindowViewManagerArray.get(displayId) != null) {
                this.mTaskBarCreateCallback.onTaskBarCreate(handleCreateTaskBar(displayId, windowViewManager), displayId);
            } else {
                this.mDisplayWindowViewManagerArray.put(displayId, windowViewManager);
                CreateTaskBarConfigurationChanged createTaskBarConfigurationChanged2 = new CreateTaskBarConfigurationChanged(displayId, this.mTaskBarCreateCallback, windowViewManager);
                this.mTaskBarChangedArray.put(displayId, createTaskBarConfigurationChanged2);
                windowViewManager.addConfigurationChangedCallbacks(createTaskBarConfigurationChanged2);
            }
        } catch (RemoteException unused) {
            Log.w(TAG, "Cannot get WindowManager.");
            if (taskBarCreateCallback != null) {
                taskBarCreateCallback.onTaskBarCreate(false, displayId);
            }
        }
    }

    private void createTooltipPopupManagers(Display display) {
        if (display == null || this.mTooltipPopupManagers.get(display.getDisplayId()) != null) {
            return;
        }
        this.mTooltipPopupManagers.append(display.getDisplayId(), new TooltipPopupManager(this.mContext.createDisplayContext(display)));
    }

    private InsetsFrameProvider[] getInsetsFrameProvider(int i) {
        Binder binder = new Binder();
        InsetsFrameProvider insetsSizeOverrides = new InsetsFrameProvider(binder, 0, WindowInsets.Type.navigationBars()).setInsetsSizeOverrides(new InsetsFrameProvider.InsetsSizeOverride[]{new InsetsFrameProvider.InsetsSizeOverride(2011, (Insets) null)});
        insetsSizeOverrides.setInsetsSize(Insets.of(0, 0, 0, i));
        InsetsFrameProvider insetsFrameProvider = new InsetsFrameProvider(binder, 0, WindowInsets.Type.mandatorySystemGestures());
        insetsFrameProvider.setInsetsSize(Insets.of(0, 0, 0, this.mContext.getResources().getDimensionPixelSize(RR.dimen.navigation_bar_gesture_height)));
        return new InsetsFrameProvider[]{insetsSizeOverrides, new InsetsFrameProvider(binder, 0, WindowInsets.Type.tappableElement()), insetsFrameProvider};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleCreateTaskBar(int i, DisplayWindowManager.WindowViewManager windowViewManager) {
        int dimensionPixelSize = windowViewManager.windowContext.getResources().getDimensionPixelSize(RR.dimen.navigation_bar_frame_height);
        if (dimensionPixelSize <= 0) {
            dimensionPixelSize = windowViewManager.windowContext.getResources().getDimensionPixelSize(R$dimen.task_bar_height);
        }
        int i2 = dimensionPixelSize;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, i2, 2019, 545521768, -3);
        layoutParams.gravity = 80;
        layoutParams.providedInsets = getInsetsFrameProvider(i2);
        layoutParams.setTitle("MotoTaskBar: " + i);
        int i3 = R$layout.task_bar;
        Display display = this.mDisplayManager.getDisplay(i);
        DisplayMetrics displayMetrics = windowViewManager.windowContext.getResources().getDisplayMetrics();
        if (MotoFeature.isAppStreamMode(display)) {
            i3 = R$layout.app_steam_task_bar;
        } else if (MotoFeature.isMobileUiMode(display)) {
            i3 = displayMetrics.widthPixels > displayMetrics.heightPixels ? R$layout.tablet_task_bar : R$layout.mobile_task_bar;
        }
        TaskBarView taskBarView = (TaskBarView) windowViewManager.inflate(i3, null);
        taskBarView.addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.motorola.taskbar.bar.TaskBarController.3
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                int displayId = view.getDisplay().getDisplayId();
                int i12 = i7 - i5;
                if (i12 <= 0) {
                    return;
                }
                if (PanelSizes.getTaskbarCacheHeight(displayId) != i12) {
                    PanelSizes.setTaskbarHeight(displayId, i12);
                }
                view.removeOnLayoutChangeListener(this);
            }
        });
        Integer num = (Integer) this.mTaskBarVisibilitiesFromSystemUI.get(i, 0);
        if (!this.mDeviceProvisionedController.isDeviceProvisioned() || !this.mDeviceProvisionedController.isCurrentUserSetup()) {
            num = 8;
        }
        if (DEBUG) {
            Log.d(TAG, "createTaskBar display id: " + i + "; visibility: " + num + " density :" + displayMetrics.density);
        }
        try {
            this.mTaskBarViews.append(i, taskBarView);
            setTaskBarViewVisibilityInternal(i, num.intValue());
            updateTaskBarReallyVisibility(i);
            windowViewManager.addView(taskBarView, layoutParams);
            return true;
        } catch (WindowManager.InvalidDisplayException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifiyOnBoardActivityShowingChanged(int i, boolean z) {
        synchronized (this.mListeners) {
            for (int i2 = 0; i2 < this.mListeners.size(); i2++) {
                try {
                    ((TaskBarControllerListener) this.mListeners.get(i2)).onOnBoardActivityShowingChanged(i, z);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void notifiyTaskBarReallyVisibilityChanged(int i, boolean z) {
        synchronized (this.mListeners) {
            for (int i2 = 0; i2 < this.mListeners.size(); i2++) {
                try {
                    ((TaskBarControllerListener) this.mListeners.get(i2)).onTaskBarReallyVisibilityChanged(i, z);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reCreatTaskBar(int i) {
        if (((TaskBarView) this.mTaskBarViews.get(i)) == null) {
            return;
        }
        if (DEBUG) {
            Log.d(TAG, "reCreatTaskBar display id: " + i);
        }
        removeNavigationBar(i);
        createTaskBar(this.mDisplayManager.getDisplay(i), this.mTaskBarCreateCallback);
    }

    private void refreshDesktopIcons() {
        for (int i = 0; i < this.mTaskBarViews.size(); i++) {
            ((TaskBarView) this.mTaskBarViews.valueAt(i)).removeAllDesktopIcon();
        }
        ExtendedFunction.refreshDesktopIcons(this.mStatusBarManager, -1);
    }

    private void removeNavigationBar(int i) {
        TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
        if (taskBarView != null) {
            try {
                if (DEBUG) {
                    Log.d(TAG, "removeNavigationBar: " + i);
                }
                this.mWindowManager.removeViewImmediate(taskBarView);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            this.mTaskBarViews.remove(i);
        }
        if (this.mTooltipPopupManagers.get(i) != null) {
            ((TooltipPopupManager) this.mTooltipPopupManagers.get(i)).destroy();
            this.mTooltipPopupManagers.remove(i);
        }
    }

    private void setTaskBarViewVisibilityInternal(int i, int i2) {
        this.mTaskBarVisibilities.append(i, Integer.valueOf(i2));
        TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
        if (taskBarView != null) {
            taskBarView.setContentVisibility(i2);
            updateLayoutParam(i, i2);
        }
    }

    private void updateCurrentHardDisplayMode(int i, String str) {
        if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            HardDisplayMode hardDisplayMode = this.mCurrentHardDisplayMode;
            hardDisplayMode.displayId = i;
            hardDisplayMode.mode = str;
            MotorolaSettings.Global.putString(this.mContext.getContentResolver(), "taskbar_display_chooser_mode", i + "_" + str);
        }
    }

    private void updateLayoutParam(int i, int i2) {
        WindowManager.LayoutParams layoutParams;
        TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
        DisplayWindowManager.WindowViewManager windowViewManager = (DisplayWindowManager.WindowViewManager) this.mDisplayWindowViewManagerArray.get(i);
        if (taskBarView == null || windowViewManager == null || (layoutParams = (WindowManager.LayoutParams) taskBarView.getLayoutParams()) == null) {
            return;
        }
        layoutParams.flags = i2 == 0 ? layoutParams.flags & (-17) : layoutParams.flags | 16;
        windowViewManager.updateViewLayout(taskBarView, layoutParams);
    }

    private void updateTaskBarReallyVisibility(int i) {
        Integer num = (Integer) this.mTaskBarVisibilities.get(i);
        Integer num2 = (Integer) this.mTaskBarWindowStatesFromSystemUI.get(i);
        boolean z = (num == null || num.intValue() == 0) && (num2 == null || num2.intValue() == 0);
        Boolean boolValueOf = Boolean.valueOf(z);
        if (boolValueOf != this.mTaskBarReallyVisibilities.get(i)) {
            this.mTaskBarReallyVisibilities.put(i, boolValueOf);
            notifiyTaskBarReallyVisibilityChanged(i, z);
        }
    }

    private void updateTaskBarViewsVisibility() {
        boolean z = this.mDeviceProvisionedController.isDeviceProvisioned() && this.mDeviceProvisionedController.isCurrentUserSetup();
        for (int i = 0; i < this.mTaskBarViews.size(); i++) {
            int iKeyAt = this.mTaskBarViews.keyAt(i);
            Integer num = (Integer) this.mTaskBarVisibilitiesFromSystemUI.get(iKeyAt, 0);
            if (!z) {
                num = 8;
            }
            if (DEBUG) {
                Log.d(TAG, "updateTaskBarViewsVisibility display id: " + iKeyAt + ";visibility = " + num);
            }
            setTaskBarViewVisibilityInternal(iKeyAt, num.intValue());
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(TaskBarControllerListener taskBarControllerListener) {
        this.mListeners.add(taskBarControllerListener);
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void addDesktopIcon(String str, int i, StatusBarIcon statusBarIcon, PendingIntent pendingIntent) {
        Log.d(TAG, "addDesktopIcon key = " + str);
        if (i == -1) {
            for (int i2 = 0; i2 < this.mTaskBarViews.size(); i2++) {
                ((TaskBarView) this.mTaskBarViews.valueAt(i2)).addDesktopIcon(str, statusBarIcon, pendingIntent);
            }
            return;
        }
        TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
        if (taskBarView != null) {
            taskBarView.addDesktopIcon(str, statusBarIcon, pendingIntent);
        }
    }

    public String getDisplayChooserMode(int i) {
        String str = (String) this.mDisplaysChooserMode.get(i);
        return TextUtils.isEmpty(str) ? this.mTaskBarViews.get(i) != null ? "normal" : "mirror" : str;
    }

    public TooltipPopupManager getTooltipPopupManager(View view) {
        return (TooltipPopupManager) this.mTooltipPopupManagers.get(view.getContext().getDisplayId());
    }

    public boolean isImButtonShowing(int i) {
        Boolean bool = (Boolean) this.mImeButtonVisibilities.get(i);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public boolean isOnBoardActivityShowing(int i) {
        return this.mOnBoardActivityShowingDisplays.contains(Integer.valueOf(i));
    }

    public boolean isTaskBarReallyShowing(int i) {
        Boolean bool = (Boolean) this.mTaskBarReallyVisibilities.get(i);
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public void onDeviceProvisionedChanged() {
        updateTaskBarViewsVisibility();
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onDisplayReady(int i) {
        try {
            createTaskBar(this.mDisplayManager.getDisplay(i), this.mTaskBarCreateCallback);
            this.mQsNotificationComponentStarter.onDisplayReady(i);
        } catch (Exception e) {
            Log.w(TAG, "onDisplayReady: create task bar failed.", e);
        }
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onDisplayRemoved(int i) {
        removeNavigationBar(i);
        this.mImeButtonVisibilities.remove(i);
        this.mDisplayWindowViewManagerArray.remove(i);
        if (i == this.mCurrentHardDisplayMode.displayId) {
            updateCurrentHardDisplayMode(-1, null);
        }
        this.mTaskBarChangedArray.remove(i);
        this.mQsNotificationComponentStarter.onDisplayRemoved(i);
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onSystemUIGone() {
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onSystemUIReady() {
        if (this.mIsSystemUIReady) {
            Log.w(TAG, "SystemUI restarted");
            return;
        }
        updateCurrentHardDisplayMode(-1, null);
        for (Display display : this.mDisplayManager.getDisplays()) {
            if (display.getDisplayId() != 0) {
                createTaskBar(display);
            }
        }
        ExtendedFunction.refreshDesktopIcons(this.mStatusBarManager, -1);
        this.mQsNotificationComponentStarter.onSystemUIReady();
        this.mIsSystemUIReady = true;
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onTaskbarWindowStateChanged(int i, int i2) {
        if (DEBUG) {
            Log.d(TAG, "onTaskbarWindowStateChanged: " + i + "; state = " + StatusBarManager.windowStateToString(i2));
        }
        this.mTaskBarWindowStatesFromSystemUI.append(i, Integer.valueOf(i2));
        updateTaskBarReallyVisibility(i);
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onUpdateDisplayChooserModeFromSecondUser(int i, String str) {
        updateDisplayChooserMode(i, str);
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public void onUserSetupChanged() {
        updateTaskBarViewsVisibility();
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public void onUserSwitched() {
        Log.d(TAG, "onUserSwitched: ");
        updateTaskBarViewsVisibility();
        this.mTaskBarServiceProxy.requestNavTrackpadGuide(false);
        refreshDesktopIcons();
    }

    public SparseArray queryDesktopIconRect(String str, int i) {
        Rect desktopIconRect;
        String str2 = TAG;
        Log.d(str2, "queryDesktopIconRect key = " + str + "; displayId = " + i);
        SparseArray sparseArray = new SparseArray();
        if (i == -1) {
            for (int i2 = 0; i2 < this.mTaskBarViews.size(); i2++) {
                Rect desktopIconRect2 = ((TaskBarView) this.mTaskBarViews.valueAt(i2)).getDesktopIconRect(str);
                if (desktopIconRect2 != null) {
                    int iKeyAt = this.mTaskBarViews.keyAt(i2);
                    sparseArray.put(iKeyAt, desktopIconRect2);
                    Log.d(TAG, "queryDesktopIconRect add displayId: " + iKeyAt + "; rect: " + desktopIconRect2);
                }
            }
        } else {
            TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
            if (taskBarView != null && (desktopIconRect = taskBarView.getDesktopIconRect(str)) != null) {
                sparseArray.put(i, desktopIconRect);
                Log.d(str2, "queryDesktopIconRect add displayId: " + i + "; rect: " + desktopIconRect);
            }
        }
        return sparseArray;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(TaskBarControllerListener taskBarControllerListener) {
        this.mListeners.remove(taskBarControllerListener);
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void removeDesktopIcon(String str, int i) {
        Log.d(TAG, "removeDesktopIcon key = " + str);
        if (i == -1) {
            for (int i2 = 0; i2 < this.mTaskBarViews.size(); i2++) {
                ((TaskBarView) this.mTaskBarViews.valueAt(i2)).removeDesktopIcon(str);
            }
            return;
        }
        TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
        if (taskBarView != null) {
            taskBarView.removeDesktopIcon(str);
        }
    }

    public void requestHideVolumeDialog(int i) {
        TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
        if (taskBarView != null) {
            taskBarView.requestHideVolumeDialog();
        }
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void setTaskBarImeSwitchButtonVisible(int i, boolean z) {
        this.mImeButtonVisibilities.put(i, Boolean.valueOf(z));
        TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
        if (taskBarView != null) {
            taskBarView.setTaskBarImeSwitchButtonVisible(z);
        }
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void setTaskBarTransitionMode(int i, int i2) {
        TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
        if (taskBarView != null) {
            taskBarView.setTaskBarTransitionMode(i2);
        }
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void setTaskBarViewVisibility(int i, int i2) {
        this.mTaskBarVisibilitiesFromSystemUI.append(i, Integer.valueOf(i2));
        Boolean bool = (Boolean) this.mExternalChooserVisibilities.get(i);
        if (!this.mDeviceProvisionedController.isDeviceProvisioned() || !this.mDeviceProvisionedController.isCurrentUserSetup() || (bool != null && bool.booleanValue())) {
            i2 = 8;
        }
        if (DEBUG) {
            Log.d(TAG, "setTaskBarViewVisibility display id: " + i + ";visibility = " + i2);
        }
        setTaskBarViewVisibilityInternal(i, i2);
        updateTaskBarReallyVisibility(i);
    }

    public void start() {
        this.mDeviceProvisionedController.addCallback(this);
        this.mTaskBarServiceProxy.addCallback((TaskBarServiceCallBack) this);
        this.mExternalDisplayTutorialManager.init();
        this.mNavIconController.init();
        this.mQsNotificationComponentStarter.start();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this.mContext);
        IntentFilter intentFilter = new IntentFilter(OnBoardActivity.ACTION_ON_BOARD_ACTIVITY_START);
        intentFilter.addAction(OnBoardActivity.ACTION_ON_BOARD_ACTIVITY_STOP);
        localBroadcastManager.registerReceiver(this.mBroadcastReceiver, intentFilter);
        ((ReadyForProxy) Dependency.get(ReadyForProxy.class)).addModeChooserListener(this.mChooserListener);
        ((ReadyForProxy) Dependency.get(ReadyForProxy.class)).addReadyForServiceStatusListener(this.mReadyForServiceStatusListener);
    }

    public void toggleAppTray(int i) {
        TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
        if (taskBarView != null) {
            taskBarView.toggleAppTray();
        }
    }

    public void toggleShortcutHelper(int i) {
        TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
        if (taskBarView != null) {
            taskBarView.toggleShortcutHelper();
        }
    }

    public void triggerHomeButton(int i) {
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            TaskBarButtonClickHelper.triggerHomeButtonForSecondUser(this.mContext, i);
            return;
        }
        TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
        if (taskBarView != null) {
            taskBarView.triggerHomeButton();
        }
    }

    public void updateDisplayChooserMode(int i, String str) {
        this.mDisplaysChooserMode.put(i, str);
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            this.mTaskBarServiceProxy.updateDisplayChooserModeFromSecondUser(i, str);
            return;
        }
        updateCurrentHardDisplayMode(i, str);
        TaskBarView taskBarView = (TaskBarView) this.mTaskBarViews.get(i);
        if (taskBarView != null) {
            taskBarView.updateDisplayChooserMode(str);
        }
    }
}
