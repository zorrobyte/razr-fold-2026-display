package com.motorola.laptoppanel.activity;

import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.activity.ComponentActivity;
import androidx.activity.compose.ComponentActivityKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import androidx.lifecycle.viewmodel.compose.ViewModelKt;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.laptoppanel.activity.LaptopPanelActivity;
import com.motorola.laptoppanel.service.LaptopPanelController;
import com.motorola.laptoppanel.service.LaptopPanelService;
import com.motorola.laptoppanel.ui.panel.LaptopPanelKt;
import com.motorola.laptoppanel.ui.panel.LaptopPanelModel;
import com.motorola.laptoppanel.ui.theme.ThemeKt;
import com.motorola.laptoppanel.util.Logger;
import com.motorola.laptoppanel.util.TaskUtilsKt;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import motorola.core_services.virtualdevices.MotoVirtualDevicesManager;

/* JADX INFO: compiled from: LaptopPanelActivity.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LaptopPanelActivity extends ComponentActivity {
    public static final String ACTION_DISMISS_PANEL = "com.motorola.laptoppanel.ACTION_DISMISS_PANEL";
    private static final float DEFAULT_CURSOR_SPEED_MULTIPLIER = 2.0f;
    private static final float DEFAULT_SCROLL_SPEED_MULTIPLIER = 3.0f;
    public static final String EXTRA_TARGET_ACTIVITY_ID = "com.motorola.laptoppanel.EXTRA_TARGET_ACTIVITY_ID";
    private BroadcastReceiver dismissReceiver;
    private boolean hasBeenInMultiWindow;
    private boolean isBound;
    private boolean mIsRecentsAnimationInProgress;
    private boolean mPendingFinish;
    private LaptopPanelModel panelModel;
    private LaptopPanelService service;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private final Lazy screenshotHelper$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.activity.LaptopPanelActivity$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return LaptopPanelActivity.screenshotHelper_delegate$lambda$0(this.f$0);
        }
    });
    private String pkgName = "";
    private int topAppTaskId = -1;
    private final Lazy activityId$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.activity.LaptopPanelActivity$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(System.identityHashCode(this.f$0));
        }
    });
    private final LaptopPanelActivity$mRecentsAnimationReceiver$1 mRecentsAnimationReceiver = new BroadcastReceiver() { // from class: com.motorola.laptoppanel.activity.LaptopPanelActivity$mRecentsAnimationReceiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            context.getClass();
            intent.getClass();
            if (Intrinsics.areEqual(intent.getAction(), "com.android.wm.shell.splitscreen.action.RECENTS_ANIMATION_STATE_CHANGED")) {
                boolean booleanExtra = intent.getBooleanExtra("EXTRA_STATE_RUNNING", false);
                this.this$0.mIsRecentsAnimationInProgress = booleanExtra;
                Logger logger = Logger.INSTANCE;
                logger.d(this.this$0, "Recents animation running: " + booleanExtra, new Object[0]);
                if (booleanExtra || !this.this$0.mPendingFinish) {
                    return;
                }
                logger.d(this.this$0, "Recents animation finished, executing pending finish.", new Object[0]);
                this.this$0.mPendingFinish = false;
                this.this$0.finishAndRemoveTask();
            }
        }
    };
    private final LaptopPanelActivity$conn$1 conn = new ServiceConnection() { // from class: com.motorola.laptoppanel.activity.LaptopPanelActivity$conn$1
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$ArrayArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LaptopPanelActivity laptopPanelActivity = this.this$0;
            iBinder.getClass();
            laptopPanelActivity.service = ((LaptopPanelService.LocalBinder) iBinder).getService();
            this.this$0.isBound = true;
            this.this$0.updateTopAppInfo();
            Logger logger = Logger.INSTANCE;
            LaptopPanelActivity laptopPanelActivity2 = this.this$0;
            logger.d(laptopPanelActivity2, "Service connected. ActivityId=" + laptopPanelActivity2.getActivityId(), new Object[0]);
            if (this.this$0.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                logger.d(this.this$0, "Service connected while STARTED/RESUMED. Syncing controller state.", new Object[0]);
                this.this$0.syncControllerStateOnResume();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            this.this$0.service = null;
            this.this$0.isBound = false;
            Logger.INSTANCE.d(this.this$0, "Service disconnected", new Object[0]);
        }
    };

    /* JADX INFO: compiled from: LaptopPanelActivity.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getActivityId() {
        return ((Number) this.activityId$delegate.getValue()).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LaptopScreenshotHelper getScreenshotHelper() {
        return (LaptopScreenshotHelper) this.screenshotHelper$delegate.getValue();
    }

    private final void getTopAppInfo() {
        String packageName;
        ComponentName componentName;
        Context applicationContext = getApplicationContext();
        applicationContext.getClass();
        ActivityManager.RunningTaskInfo topRunningTask = TaskUtilsKt.getTopRunningTask(applicationContext);
        if (topRunningTask == null || (componentName = topRunningTask.topActivity) == null || (packageName = componentName.getPackageName()) == null) {
            packageName = "";
        }
        this.pkgName = packageName;
        int i = topRunningTask != null ? topRunningTask.taskId : -1;
        this.topAppTaskId = i;
        Logger.INSTANCE.d(this, "getTopAppInfo: pkgName=" + packageName + ", topAppTaskId=" + i, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void launchAppOnSplitScreen() {
        Logger logger = Logger.INSTANCE;
        logger.d(this, "LaunchAppOnSplitScreen", new Object[0]);
        ActivityManager.RunningTaskInfo topRunningTask = TaskUtilsKt.getTopRunningTask(this);
        if (topRunningTask != null) {
            ComponentName componentName = topRunningTask.topActivity;
            String packageName = componentName != null ? componentName.getPackageName() : null;
            logger.d(this, "Sending task to launcher: packageName = " + packageName + ", taskId = " + topRunningTask.taskId, new Object[0]);
            Bundle bundle = new Bundle();
            bundle.putParcelable("taskInfo", topRunningTask);
            getContentResolver().call(Uri.parse("content://com.motorola.launcher3.settings"), "launch_split_select_home", (String) null, bundle);
        }
    }

    private final void registerDismissReceiver() {
        if (this.dismissReceiver != null) {
            return;
        }
        this.dismissReceiver = new BroadcastReceiver() { // from class: com.motorola.laptoppanel.activity.LaptopPanelActivity.registerDismissReceiver.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                context.getClass();
                intent.getClass();
                Logger logger = Logger.INSTANCE;
                logger.d(this, "Intent " + intent.getAction() + " is received..", new Object[0]);
                if (!Intrinsics.areEqual(intent.getAction(), LaptopPanelActivity.ACTION_DISMISS_PANEL)) {
                    if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
                        String stringExtra = intent.getStringExtra("reason");
                        logger.d(this, "Close reason: " + stringExtra, new Object[0]);
                        if (Intrinsics.areEqual(stringExtra, "recentapps")) {
                            logger.d(this, "Recent apps pressed. Set Animation Running", new Object[0]);
                            LaptopPanelActivity.this.mIsRecentsAnimationInProgress = true;
                            return;
                        } else {
                            if (Intrinsics.areEqual(stringExtra, "homekey")) {
                                logger.d(this, "Home key pressed. Doing nothing.", new Object[0]);
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                int intExtra = intent.getIntExtra(LaptopPanelActivity.EXTRA_TARGET_ACTIVITY_ID, -1);
                if (intExtra != -1 && intExtra != LaptopPanelActivity.this.getActivityId()) {
                    logger.d(this, "Ignoring dismiss for activityId=" + LaptopPanelActivity.this.getActivityId() + ". Target=" + intExtra + " (does not match)", new Object[0]);
                    return;
                }
                logger.d(this, "finishAndRemoveTask() for activityId=" + LaptopPanelActivity.this.getActivityId() + ". Target=" + intExtra, new Object[0]);
                if (LaptopPanelActivity.this.mIsRecentsAnimationInProgress) {
                    logger.d(LaptopPanelActivity.this, "Recents animation in progress, deferring finish.", new Object[0]);
                    LaptopPanelActivity.this.mPendingFinish = true;
                } else {
                    logger.d(LaptopPanelActivity.this, "No recents animation, finishing immediately.", new Object[0]);
                    LaptopPanelActivity.this.finishAndRemoveTask();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(ACTION_DISMISS_PANEL);
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        registerReceiver(this.dismissReceiver, intentFilter, 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LaptopScreenshotHelper screenshotHelper_delegate$lambda$0(LaptopPanelActivity laptopPanelActivity) {
        Context applicationContext = laptopPanelActivity.getApplicationContext();
        applicationContext.getClass();
        return new LaptopScreenshotHelper(applicationContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void syncControllerStateOnResume() {
        LaptopPanelController controller;
        LaptopPanelService laptopPanelService = this.service;
        if (laptopPanelService != null && (controller = laptopPanelService.getController()) != null) {
            controller.setPanelVisible(true, getActivityId(), this.pkgName, this.topAppTaskId);
        }
        if (isInMultiWindowMode()) {
            this.hasBeenInMultiWindow = true;
        }
    }

    private final void unregisterDismissReceiver() {
        BroadcastReceiver broadcastReceiver = this.dismissReceiver;
        if (broadcastReceiver != null) {
            try {
                unregisterReceiver(broadcastReceiver);
            } catch (Throwable unused) {
            }
        }
        this.dismissReceiver = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateTopAppInfo() {
        LaptopPanelModel laptopPanelModel;
        LaptopPanelService laptopPanelService = this.service;
        LaptopPanelModel laptopPanelModel2 = null;
        LaptopPanelController controller = laptopPanelService != null ? laptopPanelService.getController() : null;
        String currentTopAppPackage = controller != null ? controller.getCurrentTopAppPackage() : null;
        if (currentTopAppPackage == null) {
            currentTopAppPackage = "";
        }
        int currentTopAppTaskId = controller != null ? controller.getCurrentTopAppTaskId() : -1;
        Logger logger = Logger.INSTANCE;
        logger.d(this, "updateTopAppInfo: oldPkg (" + this.pkgName + ") -> newPkg (" + currentTopAppPackage + "), oldTaskId (" + this.topAppTaskId + ") -> newTaskId (" + currentTopAppTaskId + ")", new Object[0]);
        if (currentTopAppPackage.length() > 0 && !Intrinsics.areEqual(currentTopAppPackage, this.pkgName)) {
            this.pkgName = currentTopAppPackage;
        }
        if (currentTopAppTaskId != -1 && currentTopAppTaskId != this.topAppTaskId) {
            this.topAppTaskId = currentTopAppTaskId;
        }
        LaptopPanelService laptopPanelService2 = this.service;
        if (laptopPanelService2 != null && (laptopPanelModel = this.panelModel) != null) {
            if (laptopPanelModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("panelModel");
            } else {
                laptopPanelModel2 = laptopPanelModel;
            }
            laptopPanelModel2.setPackageName(this.pkgName);
            return;
        }
        logger.d(this, "updateCurrentPackageName: Service = " + laptopPanelService2 + ", ::panelModel.isInitialized = " + (this.panelModel != null), new Object[0]);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        LaptopPanelController controller;
        LaptopPanelController controller2;
        configuration.getClass();
        super.onConfigurationChanged(configuration);
        Logger logger = Logger.INSTANCE;
        logger.d(this, "onConfigurationChanged: " + configuration, new Object[0]);
        int windowingMode = configuration.windowConfiguration.getWindowingMode();
        if (windowingMode == 1) {
            logger.i(this, "Windowing mode = FULLSCREEN -> finish panel", new Object[0]);
            if (isFinishing() || isDestroyed() || !this.hasBeenInMultiWindow) {
                return;
            }
            LaptopPanelService laptopPanelService = this.service;
            if (laptopPanelService != null && (controller = laptopPanelService.getController()) != null) {
                controller.setPanelVisible(false, getActivityId(), this.pkgName, this.topAppTaskId);
            }
            finishAndRemoveTask();
            return;
        }
        if (windowingMode != 6) {
            return;
        }
        if (!getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            logger.d(this, "Windowing mode = MULTI_WINDOW, but activity is not started. Skipping focus restore.", new Object[0]);
            return;
        }
        logger.d(this, "Windowing mode = MULTI_WINDOW", new Object[0]);
        LaptopPanelService laptopPanelService2 = this.service;
        if (laptopPanelService2 != null && (controller2 = laptopPanelService2.getController()) != null) {
            controller2.setPanelVisible(true, getActivityId(), this.pkgName, this.topAppTaskId);
        }
        this.hasBeenInMultiWindow = true;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Logger.INSTANCE.d(this, "onCreate: binding the service. ActivityId=" + getActivityId(), new Object[0]);
        getTopAppInfo();
        MotoVirtualDevicesManager motoVirtualDevicesManager = MotoVirtualDevicesManager.getInstance(getApplicationContext());
        motoVirtualDevicesManager.setCursorSpeedMultiplier(DEFAULT_CURSOR_SPEED_MULTIPLIER);
        motoVirtualDevicesManager.setScrollSpeedMultiplier(DEFAULT_SCROLL_SPEED_MULTIPLIER);
        Intent intent = new Intent(LaptopPanelService.ACTION_BIND_LOCAL).setClass(this, LaptopPanelService.class);
        intent.getClass();
        bindService(intent, this.conn, 1);
        registerDismissReceiver();
        registerReceiver(this.mRecentsAnimationReceiver, new IntentFilter("com.android.wm.shell.splitscreen.action.RECENTS_ANIMATION_STATE_CHANGED"), "com.motorola.permission.LAPTOP_PANEL_BROADCAST", null, 2);
        getWindow().addFlags(8);
        ComponentActivityKt.setContent$default(this, null, ComposableLambdaKt.composableLambdaInstance(1645081809, true, new Function2() { // from class: com.motorola.laptoppanel.activity.LaptopPanelActivity.onCreate.1

            /* JADX INFO: renamed from: com.motorola.laptoppanel.activity.LaptopPanelActivity$onCreate$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: LaptopPanelActivity.kt */
            final class C00171 implements Function2 {
                final /* synthetic */ LaptopPanelActivity this$0;

                C00171(LaptopPanelActivity laptopPanelActivity) {
                    this.this$0 = laptopPanelActivity;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static final Unit invoke$lambda$1$lambda$0(LaptopPanelActivity laptopPanelActivity) {
                    LaptopPanelController controller;
                    Logger.INSTANCE.d(laptopPanelActivity, "onClose: user tapped close button", new Object[0]);
                    LaptopPanelService laptopPanelService = laptopPanelActivity.service;
                    if (laptopPanelService != null && (controller = laptopPanelService.getController()) != null) {
                        controller.onPanelDismissedByUser();
                    }
                    laptopPanelActivity.finishAndRemoveTask();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static final Unit invoke$lambda$3$lambda$2(LaptopPanelActivity laptopPanelActivity) {
                    laptopPanelActivity.launchAppOnSplitScreen();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static final Unit invoke$lambda$5$lambda$4(LaptopPanelActivity laptopPanelActivity) {
                    Logger.INSTANCE.d(laptopPanelActivity, "onTakeScreenshot: user tapped screenshot button", new Object[0]);
                    laptopPanelActivity.getScreenshotHelper().takeScreenshot();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static final Unit invoke$lambda$7$lambda$6(final LaptopPanelActivity laptopPanelActivity) {
                    LaptopPanelController controller;
                    LaptopPanelService laptopPanelService = laptopPanelActivity.service;
                    if (laptopPanelService != null && (controller = laptopPanelService.getController()) != null) {
                        controller.onNavigationAway();
                    }
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.motorola.laptoppanel.activity.LaptopPanelActivity$onCreate$1$1$4$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Intent intentAddCategory = new Intent("com.motorola.settings.LAPTOP_MODE_SETTINGS").addCategory("android.intent.category.DEFAULT");
                            intentAddCategory.getClass();
                            Intent intentPutExtra = new Intent("android.settings.SETTINGS_EMBED_DEEP_LINK_ACTIVITY").setPackage("com.android.settings").addFlags(268435456).putExtra("android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI", intentAddCategory.toUri(1));
                            intentPutExtra.getClass();
                            laptopPanelActivity.startActivity(intentPutExtra);
                        }
                    }, 150L);
                    return Unit.INSTANCE;
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                    invoke((Composer) obj, ((Number) obj2).intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    LaptopPanelModel laptopPanelModel;
                    if ((i & 3) == 2 && composer.getSkipping()) {
                        composer.skipToGroupEnd();
                        return;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(652659869, i, -1, "com.motorola.laptoppanel.activity.LaptopPanelActivity.onCreate.<anonymous>.<anonymous> (LaptopPanelActivity.kt:203)");
                    }
                    LaptopPanelModel laptopPanelModel2 = this.this$0.panelModel;
                    if (laptopPanelModel2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("panelModel");
                        laptopPanelModel = null;
                    } else {
                        laptopPanelModel = laptopPanelModel2;
                    }
                    composer.startReplaceGroup(-537567250);
                    boolean zChangedInstance = composer.changedInstance(this.this$0);
                    final LaptopPanelActivity laptopPanelActivity = this.this$0;
                    Object objRememberedValue = composer.rememberedValue();
                    if (zChangedInstance || objRememberedValue == Composer.Companion.getEmpty()) {
                        objRememberedValue = new Function0() { // from class: com.motorola.laptoppanel.activity.LaptopPanelActivity$onCreate$1$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return LaptopPanelActivity.AnonymousClass1.C00171.invoke$lambda$1$lambda$0(laptopPanelActivity);
                            }
                        };
                        composer.updateRememberedValue(objRememberedValue);
                    }
                    Function0 function0 = (Function0) objRememberedValue;
                    composer.endReplaceGroup();
                    composer.startReplaceGroup(-537556321);
                    boolean zChangedInstance2 = composer.changedInstance(this.this$0);
                    final LaptopPanelActivity laptopPanelActivity2 = this.this$0;
                    Object objRememberedValue2 = composer.rememberedValue();
                    if (zChangedInstance2 || objRememberedValue2 == Composer.Companion.getEmpty()) {
                        objRememberedValue2 = new Function0() { // from class: com.motorola.laptoppanel.activity.LaptopPanelActivity$onCreate$1$1$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return LaptopPanelActivity.AnonymousClass1.C00171.invoke$lambda$3$lambda$2(laptopPanelActivity2);
                            }
                        };
                        composer.updateRememberedValue(objRememberedValue2);
                    }
                    Function0 function02 = (Function0) objRememberedValue2;
                    composer.endReplaceGroup();
                    composer.startReplaceGroup(-537552606);
                    boolean zChangedInstance3 = composer.changedInstance(this.this$0);
                    final LaptopPanelActivity laptopPanelActivity3 = this.this$0;
                    Object objRememberedValue3 = composer.rememberedValue();
                    if (zChangedInstance3 || objRememberedValue3 == Composer.Companion.getEmpty()) {
                        objRememberedValue3 = new Function0() { // from class: com.motorola.laptoppanel.activity.LaptopPanelActivity$onCreate$1$1$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return LaptopPanelActivity.AnonymousClass1.C00171.invoke$lambda$5$lambda$4(laptopPanelActivity3);
                            }
                        };
                        composer.updateRememberedValue(objRememberedValue3);
                    }
                    Function0 function03 = (Function0) objRememberedValue3;
                    composer.endReplaceGroup();
                    composer.startReplaceGroup(-537545243);
                    boolean zChangedInstance4 = composer.changedInstance(this.this$0);
                    final LaptopPanelActivity laptopPanelActivity4 = this.this$0;
                    Object objRememberedValue4 = composer.rememberedValue();
                    if (zChangedInstance4 || objRememberedValue4 == Composer.Companion.getEmpty()) {
                        objRememberedValue4 = new Function0() { // from class: com.motorola.laptoppanel.activity.LaptopPanelActivity$onCreate$1$1$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return LaptopPanelActivity.AnonymousClass1.C00171.invoke$lambda$7$lambda$6(laptopPanelActivity4);
                            }
                        };
                        composer.updateRememberedValue(objRememberedValue4);
                    }
                    composer.endReplaceGroup();
                    LaptopPanelKt.LaptopPanel(laptopPanelModel, function0, function02, function03, (Function0) objRememberedValue4, SizeKt.fillMaxSize$default(Modifier.Companion, 0.0f, 1, null), composer, 196608, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                invoke((Composer) obj, ((Number) obj2).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(Composer composer, int i) {
                if ((i & 3) == 2 && composer.getSkipping()) {
                    composer.skipToGroupEnd();
                    return;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(1645081809, i, -1, "com.motorola.laptoppanel.activity.LaptopPanelActivity.onCreate.<anonymous> (LaptopPanelActivity.kt:198)");
                }
                LaptopPanelActivity laptopPanelActivity = LaptopPanelActivity.this;
                LaptopPanelModel.Companion companion = LaptopPanelModel.Companion;
                Application application = laptopPanelActivity.getApplication();
                application.getClass();
                ViewModelProvider.Factory factoryProvideFactory = companion.provideFactory(application, LaptopPanelActivity.this.pkgName);
                composer.startReplaceableGroup(1729797275);
                ViewModelStoreOwner current = LocalViewModelStoreOwner.INSTANCE.getCurrent(composer, 6);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                ViewModel viewModel = ViewModelKt.viewModel(Reflection.getOrCreateKotlinClass(LaptopPanelModel.class), current, null, factoryProvideFactory, current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE, composer, 0, 0);
                composer.endReplaceableGroup();
                laptopPanelActivity.panelModel = (LaptopPanelModel) viewModel;
                ThemeKt.MyApplicationTheme(false, false, ComposableLambdaKt.rememberComposableLambda(652659869, true, new C00171(LaptopPanelActivity.this), composer, 54), composer, 384, 3);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }), 1, null);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        Object objM2192constructorimpl;
        LaptopPanelController controller;
        Logger.INSTANCE.d(this, "onDestroy: unbind the service. ActivityId=" + getActivityId(), new Object[0]);
        LaptopPanelService laptopPanelService = this.service;
        if (laptopPanelService != null && (controller = laptopPanelService.getController()) != null) {
            controller.onPanelDismissed(getActivityId());
        }
        unregisterDismissReceiver();
        unregisterReceiver(this.mRecentsAnimationReceiver);
        if (this.isBound) {
            try {
                Result.Companion companion = Result.Companion;
                unbindService(this.conn);
                objM2192constructorimpl = Result.m2192constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM2192constructorimpl = Result.m2192constructorimpl(ResultKt.createFailure(th));
            }
            Throwable thM2193exceptionOrNullimpl = Result.m2193exceptionOrNullimpl(objM2192constructorimpl);
            if (thM2193exceptionOrNullimpl != null) {
                Logger.INSTANCE.e(this, thM2193exceptionOrNullimpl, "Failed to unbind service", new Object[0]);
            }
            this.isBound = false;
        }
        MotorolaSettings.Secure.putInt(getContentResolver(), "laptop_panel_in_use", 0);
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        Logger.INSTANCE.d(this, "onPause: Activity is no longer in the foreground", new Object[0]);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        Logger.INSTANCE.d(this, "onResume: resume the activity. ActivityId=" + getActivityId(), new Object[0]);
        if (this.isBound) {
            updateTopAppInfo();
        }
        syncControllerStateOnResume();
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        MotorolaSettings.Secure.putInt(getContentResolver(), "laptop_panel_in_use", 1);
        Logger.INSTANCE.d(this, "onStart: Activity is becoming visible. ActivityId=" + getActivityId(), new Object[0]);
    }

    @Override // android.app.Activity
    protected void onStop() {
        LaptopPanelController controller;
        Logger.INSTANCE.d(this, "onStop: Activity is invisible. ActivityId=" + getActivityId(), new Object[0]);
        LaptopPanelService laptopPanelService = this.service;
        if (laptopPanelService != null && (controller = laptopPanelService.getController()) != null) {
            controller.setPanelVisible(false, getActivityId(), this.pkgName, this.topAppTaskId);
        }
        MotorolaSettings.Secure.putInt(getContentResolver(), "laptop_panel_in_use", 0);
        super.onStop();
    }
}
