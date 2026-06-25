package com.android.systemui;

import android.app.INotificationManager;
import android.app.IWallpaperManager;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.IWindowManager;
import android.view.View;
import androidx.core.util.Preconditions;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.moto.ActivityStarter;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.motorola.extendscreenshot.ScreenshotController;
import com.motorola.glass.GlassesMonitor;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.WifiStatusMonitor;
import com.motorola.taskbar.bar.ExternalDisplayModeManager;
import com.motorola.taskbar.bar.ExternalDisplayTutorialManager;
import com.motorola.taskbar.bar.MirrorPhonePanelController;
import com.motorola.taskbar.bar.QSNotificationPanelController;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.taskbar.model.DisplayWindowManager;
import com.motorola.taskbar.model.HardwareDisplayController;
import com.motorola.taskbar.model.NavIconController;
import com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces;
import com.motorola.taskbar.qsnotification.QsNotificationComponentStarter;
import com.motorola.taskbar.qsnotification.QsNotificationDependency;
import com.motorola.taskbar.recent.RecentsController;
import com.motorola.taskbar.recent.TaskController;
import com.motorola.taskbar.recent.TaskSyncreticController;
import com.motorola.taskbar.shortcut.ShortcutKeyDispatcher;
import com.motorola.taskbar.shortcut.record.RecordManager;
import com.motorola.trackpad.ReadyForProxy;
import com.motorola.trackpad.TrackpadGestureHandler;
import dagger.Lazy;

/* JADX INFO: loaded from: classes.dex */
public class Dependency {
    private static Dependency sDependency;
    Lazy mActivityStarter;
    Lazy mAlarmManager;
    Lazy mBgHandler;
    Lazy mBgLooper;
    Lazy mBroadcastDispatcher;
    Lazy mConfigurationController;
    Lazy mDeviceProvisionedController;
    Lazy mDisplayMetrics;
    Lazy mDisplayWindowManager;
    Lazy mExternalDisplayModeManager;
    Lazy mExternalDisplayTutorialManager;
    Lazy mFeatureFlagsLazy;
    Lazy mGlassesMonitor;
    Lazy mHardwareDisplayController;
    Lazy mINotificationManager;
    Lazy mIStatusBarService;
    Lazy mIWindowManager;
    Lazy mMainHandler;
    Lazy mMainLooper;
    Lazy mMirrorPhonePanelController;
    Lazy mMotoFeature;
    Lazy mNavIconController;
    Lazy mQSNotificationPanelController;
    Lazy mQsNotificationComponentStarter;
    Lazy mReadyForProxy;
    Lazy mRecentsController;
    Lazy mRecordManager;
    Lazy mScreenshotController;
    Lazy mShortcutKeyDispatcher;
    Lazy mTaskBarController;
    Lazy mTaskBarServiceProxy;
    Lazy mTaskController;
    Lazy mTaskSyncreticController;
    Lazy mTimeTickHandler;
    Lazy mTrackpadGestureHandle;
    Lazy mUiEventLogger;
    Lazy mUiOffloadThread;
    Lazy mWallpaperManager;
    Lazy mWifiStatusMonitor;
    public static final DependencyKey BG_LOOPER = new DependencyKey("background_looper");
    public static final DependencyKey MAIN_LOOPER = new DependencyKey("main_looper");
    public static final DependencyKey TIME_TICK_HANDLER = new DependencyKey("time_tick_handler");
    public static final DependencyKey MAIN_HANDLER = new DependencyKey("main_handler");
    private final ArrayMap mDependencies = new ArrayMap();
    private final ArrayMap mProviders = new ArrayMap();

    public interface DependencyInjector {
        void createSystemUI(Dependency dependency);
    }

    public final class DependencyKey {
        private final String mDisplayName;

        public DependencyKey(String str) {
            this.mDisplayName = str;
        }

        public String toString() {
            return this.mDisplayName;
        }
    }

    interface LazyDependencyCreator {
        Object createDependency();
    }

    public static Object get(DependencyKey dependencyKey) {
        return sDependency.getDependency(dependencyKey);
    }

    public static Object get(Class cls) {
        return sDependency.getDependency(cls);
    }

    private synchronized Object getDependencyInner(Object obj) {
        Object objCreateDependency;
        objCreateDependency = this.mDependencies.get(obj);
        if (objCreateDependency == null) {
            objCreateDependency = createDependency(obj);
            this.mDependencies.put(obj, objCreateDependency);
        }
        return objCreateDependency;
    }

    public static Object getDisplay(int i, Class cls) {
        QsNotificationCentralSurfaces qsNotificationCentralSurfaces;
        QsNotificationDependency qsNotificationDependency;
        QsNotificationComponentStarter qsNotificationComponentStarter = (QsNotificationComponentStarter) sDependency.getDependency(QsNotificationComponentStarter.class);
        if (qsNotificationComponentStarter == null || (qsNotificationCentralSurfaces = qsNotificationComponentStarter.getQsNotificationCentralSurfaces(i)) == null || (qsNotificationDependency = qsNotificationCentralSurfaces.getQsNotificationDependency()) == null) {
            return null;
        }
        return qsNotificationDependency.get(cls);
    }

    public static Object getDisplay(View view, Class cls) {
        return getDisplay(view.getContext().getDisplayId(), cls);
    }

    protected boolean autoRegisterModulesForDump() {
        return true;
    }

    protected Object createDependency(Object obj) {
        Preconditions.checkArgument((obj instanceof DependencyKey) || (obj instanceof Class));
        LazyDependencyCreator lazyDependencyCreator = (LazyDependencyCreator) this.mProviders.get(obj);
        if (lazyDependencyCreator != null) {
            return lazyDependencyCreator.createDependency();
        }
        throw new IllegalArgumentException("Unsupported dependency " + obj + ". " + this.mProviders.size() + " providers known.");
    }

    protected final Object getDependency(DependencyKey dependencyKey) {
        return getDependencyInner(dependencyKey);
    }

    protected final Object getDependency(Class cls) {
        return getDependencyInner(cls);
    }

    protected void start() {
        ArrayMap arrayMap = this.mProviders;
        DependencyKey dependencyKey = TIME_TICK_HANDLER;
        final Lazy lazy = this.mTimeTickHandler;
        lazy.getClass();
        arrayMap.put(dependencyKey, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy.get();
            }
        });
        ArrayMap arrayMap2 = this.mProviders;
        DependencyKey dependencyKey2 = BG_LOOPER;
        final Lazy lazy2 = this.mBgLooper;
        lazy2.getClass();
        arrayMap2.put(dependencyKey2, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy2.get();
            }
        });
        ArrayMap arrayMap3 = this.mProviders;
        DependencyKey dependencyKey3 = MAIN_LOOPER;
        final Lazy lazy3 = this.mMainLooper;
        lazy3.getClass();
        arrayMap3.put(dependencyKey3, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy3.get();
            }
        });
        ArrayMap arrayMap4 = this.mProviders;
        DependencyKey dependencyKey4 = MAIN_HANDLER;
        final Lazy lazy4 = this.mMainHandler;
        lazy4.getClass();
        arrayMap4.put(dependencyKey4, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy4.get();
            }
        });
        ArrayMap arrayMap5 = this.mProviders;
        final Lazy lazy5 = this.mActivityStarter;
        lazy5.getClass();
        arrayMap5.put(ActivityStarter.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy5.get();
            }
        });
        ArrayMap arrayMap6 = this.mProviders;
        final Lazy lazy6 = this.mTaskBarController;
        lazy6.getClass();
        arrayMap6.put(TaskBarController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy6.get();
            }
        });
        ArrayMap arrayMap7 = this.mProviders;
        final Lazy lazy7 = this.mTaskController;
        lazy7.getClass();
        arrayMap7.put(TaskController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy7.get();
            }
        });
        ArrayMap arrayMap8 = this.mProviders;
        final Lazy lazy8 = this.mTaskSyncreticController;
        lazy8.getClass();
        arrayMap8.put(TaskSyncreticController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy8.get();
            }
        });
        ArrayMap arrayMap9 = this.mProviders;
        final Lazy lazy9 = this.mDeviceProvisionedController;
        lazy9.getClass();
        arrayMap9.put(DeviceProvisionedController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy9.get();
            }
        });
        ArrayMap arrayMap10 = this.mProviders;
        final Lazy lazy10 = this.mTaskBarServiceProxy;
        lazy10.getClass();
        arrayMap10.put(TaskBarServiceProxy.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy10.get();
            }
        });
        ArrayMap arrayMap11 = this.mProviders;
        final Lazy lazy11 = this.mWifiStatusMonitor;
        lazy11.getClass();
        arrayMap11.put(WifiStatusMonitor.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy11.get();
            }
        });
        ArrayMap arrayMap12 = this.mProviders;
        final Lazy lazy12 = this.mMotoFeature;
        lazy12.getClass();
        arrayMap12.put(MotoFeature.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy12.get();
            }
        });
        ArrayMap arrayMap13 = this.mProviders;
        final Lazy lazy13 = this.mBroadcastDispatcher;
        lazy13.getClass();
        arrayMap13.put(BroadcastDispatcher.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy13.get();
            }
        });
        ArrayMap arrayMap14 = this.mProviders;
        final Lazy lazy14 = this.mQSNotificationPanelController;
        lazy14.getClass();
        arrayMap14.put(QSNotificationPanelController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy14.get();
            }
        });
        ArrayMap arrayMap15 = this.mProviders;
        final Lazy lazy15 = this.mShortcutKeyDispatcher;
        lazy15.getClass();
        arrayMap15.put(ShortcutKeyDispatcher.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy15.get();
            }
        });
        ArrayMap arrayMap16 = this.mProviders;
        final Lazy lazy16 = this.mMirrorPhonePanelController;
        lazy16.getClass();
        arrayMap16.put(MirrorPhonePanelController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy16.get();
            }
        });
        ArrayMap arrayMap17 = this.mProviders;
        final Lazy lazy17 = this.mRecentsController;
        lazy17.getClass();
        arrayMap17.put(RecentsController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy17.get();
            }
        });
        ArrayMap arrayMap18 = this.mProviders;
        final Lazy lazy18 = this.mScreenshotController;
        lazy18.getClass();
        arrayMap18.put(ScreenshotController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy18.get();
            }
        });
        ArrayMap arrayMap19 = this.mProviders;
        final Lazy lazy19 = this.mIWindowManager;
        lazy19.getClass();
        arrayMap19.put(IWindowManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy19.get();
            }
        });
        ArrayMap arrayMap20 = this.mProviders;
        final Lazy lazy20 = this.mIStatusBarService;
        lazy20.getClass();
        arrayMap20.put(IStatusBarService.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy20.get();
            }
        });
        ArrayMap arrayMap21 = this.mProviders;
        final Lazy lazy21 = this.mDisplayMetrics;
        lazy21.getClass();
        arrayMap21.put(DisplayMetrics.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy21.get();
            }
        });
        ArrayMap arrayMap22 = this.mProviders;
        final Lazy lazy22 = this.mINotificationManager;
        lazy22.getClass();
        arrayMap22.put(INotificationManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy22.get();
            }
        });
        ArrayMap arrayMap23 = this.mProviders;
        final Lazy lazy23 = this.mWallpaperManager;
        lazy23.getClass();
        arrayMap23.put(IWallpaperManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy23.get();
            }
        });
        ArrayMap arrayMap24 = this.mProviders;
        final Lazy lazy24 = this.mConfigurationController;
        lazy24.getClass();
        arrayMap24.put(ConfigurationController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy24.get();
            }
        });
        ArrayMap arrayMap25 = this.mProviders;
        final Lazy lazy25 = this.mExternalDisplayModeManager;
        lazy25.getClass();
        arrayMap25.put(ExternalDisplayModeManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy25.get();
            }
        });
        ArrayMap arrayMap26 = this.mProviders;
        final Lazy lazy26 = this.mExternalDisplayTutorialManager;
        lazy26.getClass();
        arrayMap26.put(ExternalDisplayTutorialManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy26.get();
            }
        });
        ArrayMap arrayMap27 = this.mProviders;
        final Lazy lazy27 = this.mHardwareDisplayController;
        lazy27.getClass();
        arrayMap27.put(HardwareDisplayController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy27.get();
            }
        });
        ArrayMap arrayMap28 = this.mProviders;
        final Lazy lazy28 = this.mDisplayWindowManager;
        lazy28.getClass();
        arrayMap28.put(DisplayWindowManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy28.get();
            }
        });
        ArrayMap arrayMap29 = this.mProviders;
        final Lazy lazy29 = this.mNavIconController;
        lazy29.getClass();
        arrayMap29.put(NavIconController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy29.get();
            }
        });
        ArrayMap arrayMap30 = this.mProviders;
        final Lazy lazy30 = this.mRecordManager;
        lazy30.getClass();
        arrayMap30.put(RecordManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy30.get();
            }
        });
        ArrayMap arrayMap31 = this.mProviders;
        final Lazy lazy31 = this.mGlassesMonitor;
        lazy31.getClass();
        arrayMap31.put(GlassesMonitor.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy31.get();
            }
        });
        ArrayMap arrayMap32 = this.mProviders;
        final Lazy lazy32 = this.mReadyForProxy;
        lazy32.getClass();
        arrayMap32.put(ReadyForProxy.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy32.get();
            }
        });
        ArrayMap arrayMap33 = this.mProviders;
        final Lazy lazy33 = this.mTrackpadGestureHandle;
        lazy33.getClass();
        arrayMap33.put(TrackpadGestureHandler.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy33.get();
            }
        });
        ArrayMap arrayMap34 = this.mProviders;
        final Lazy lazy34 = this.mFeatureFlagsLazy;
        lazy34.getClass();
        arrayMap34.put(FeatureFlags.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy34.get();
            }
        });
        ArrayMap arrayMap35 = this.mProviders;
        final Lazy lazy35 = this.mQsNotificationComponentStarter;
        lazy35.getClass();
        arrayMap35.put(QsNotificationComponentStarter.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy35.get();
            }
        });
        ArrayMap arrayMap36 = this.mProviders;
        final Lazy lazy36 = this.mUiOffloadThread;
        lazy36.getClass();
        arrayMap36.put(UiOffloadThread.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy36.get();
            }
        });
        ArrayMap arrayMap37 = this.mProviders;
        final Lazy lazy37 = this.mUiEventLogger;
        lazy37.getClass();
        arrayMap37.put(UiEventLogger.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy37.get();
            }
        });
        sDependency = this;
    }
}
