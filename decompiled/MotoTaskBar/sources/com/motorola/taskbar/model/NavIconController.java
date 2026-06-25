package com.motorola.taskbar.model;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.CurrentUserTracker;
import com.motorola.taskbar.TaskBarServiceCallBack;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.model.HardwareDisplayController;
import com.motorola.taskbar.util.DebugConfig;

/* JADX INFO: loaded from: classes2.dex */
public class NavIconController {
    private static final boolean DEBUG = DebugConfig.DEBUG_COMMON;
    private static final String TAG = "NavIconController";
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private final HardwareDisplayController mHardwareDisplayController;
    private boolean mImeShowInDefaultDisplay;
    private final Handler mMainHandler;
    protected final ContentObserver mSettingsObserver;
    private final TaskBarServiceProxy mTaskBarServiceProxy;
    private boolean mTrackActivityShow;
    private CurrentUserTracker mUserTracker;
    private HardwareDisplayController.HardwareDisplayListener mHardwareDisplayListener = new HardwareDisplayController.HardwareDisplayListener() { // from class: com.motorola.taskbar.model.NavIconController.3
        @Override // com.motorola.taskbar.model.HardwareDisplayController.HardwareDisplayListener
        public void onHardwareDisplayAdded(int i, int i2, boolean z) {
            NavIconController.this.handleHardwareDisplayAdded(i, i2, z);
        }

        @Override // com.motorola.taskbar.model.HardwareDisplayController.HardwareDisplayListener
        public void onHardwareDisplayIdChanged(int i, int i2, int i3, boolean z) {
            NavIconController.this.handleHardwareDisplayIdChanged(i, i2, i3, z);
        }

        @Override // com.motorola.taskbar.model.HardwareDisplayController.HardwareDisplayListener
        public void onHardwareDisplayRemoved(int i, int i2) {
            NavIconController.this.handleHardwareDisplayRemoved(i, i2);
        }
    };
    private TaskBarServiceCallBack mTaskBarServiceCallBack = new TaskBarServiceCallBack() { // from class: com.motorola.taskbar.model.NavIconController.4
        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onSystemUIReady() {
            NavIconController.this.updateNavIcon();
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void updateImeVisible(int i, boolean z) {
            if (NavIconController.DEBUG) {
                Log.d(NavIconController.TAG, "updateImeVisible, displayId :" + i + " visible :" + z);
            }
            if (i != 0 || NavIconController.this.mImeShowInDefaultDisplay == z) {
                return;
            }
            NavIconController.this.mImeShowInDefaultDisplay = z;
            NavIconController.this.updateNavIcon();
        }
    };
    private int mCurrentUserId = ActivityManager.getCurrentUser();
    private final Uri mDeviceProvisionedUri = Settings.Global.getUriFor("device_provisioned");
    private final Uri mUserSetupUri = Settings.Secure.getUriFor("user_setup_complete");

    public NavIconController(Context context, Handler handler, HardwareDisplayController hardwareDisplayController, TaskBarServiceProxy taskBarServiceProxy, BroadcastDispatcher broadcastDispatcher) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mHardwareDisplayController = hardwareDisplayController;
        this.mTaskBarServiceProxy = taskBarServiceProxy;
        this.mUserTracker = new CurrentUserTracker(broadcastDispatcher) { // from class: com.motorola.taskbar.model.NavIconController.1
            @Override // com.android.systemui.settings.CurrentUserTracker
            public void onUserSwitched(int i) {
                if (NavIconController.this.mCurrentUserId != i) {
                    NavIconController.this.mCurrentUserId = i;
                    NavIconController.this.stopListening();
                    NavIconController.this.startListening(i);
                    NavIconController.this.updateNavIcon();
                }
            }
        };
        this.mContentResolver = context.getContentResolver();
        this.mSettingsObserver = new ContentObserver(handler) { // from class: com.motorola.taskbar.model.NavIconController.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri, int i) {
                if (NavIconController.this.mCurrentUserId == i) {
                    NavIconController.this.updateNavIcon();
                }
            }
        };
    }

    private boolean isUserSetupCompleted(int i) {
        return (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0) && (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 0, i) != 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleHardwareDisplayAdded$0(boolean z) {
        onHardwareDisplayStatusChanged(true, z);
        updateNavIcon();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleHardwareDisplayIdChanged$2(boolean z) {
        onHardwareDisplayStatusChanged(true, z);
        updateNavIcon();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleHardwareDisplayRemoved$1() {
        onHardwareDisplayStatusChanged(false, false);
        updateNavIcon();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTrackpadStateChanged$3(boolean z) {
        if (z != this.mTrackActivityShow) {
            this.mTrackActivityShow = z;
            updateNavIcon();
        }
    }

    private void onHardwareDisplayStatusChanged(boolean z, boolean z2) {
        TaskBarServiceProxy taskBarServiceProxy = this.mTaskBarServiceProxy;
        if (taskBarServiceProxy == null) {
            return;
        }
        taskBarServiceProxy.onHardwareDisplayStatusChanged(z, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startListening(int i) {
        this.mContentResolver.registerContentObserver(this.mDeviceProvisionedUri, true, this.mSettingsObserver, 0);
        this.mContentResolver.registerContentObserver(this.mUserSetupUri, true, this.mSettingsObserver, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopListening() {
        this.mContentResolver.unregisterContentObserver(this.mSettingsObserver);
    }

    public void handleHardwareDisplayAdded(int i, int i2, final boolean z) {
        this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.model.NavIconController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleHardwareDisplayAdded$0(z);
            }
        });
    }

    public void handleHardwareDisplayIdChanged(int i, int i2, int i3, final boolean z) {
        this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.model.NavIconController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleHardwareDisplayIdChanged$2(z);
            }
        });
    }

    public void handleHardwareDisplayRemoved(int i, int i2) {
        this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.model.NavIconController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleHardwareDisplayRemoved$1();
            }
        });
    }

    public void init() {
        this.mHardwareDisplayController.addCallback(this.mHardwareDisplayListener);
        this.mTaskBarServiceProxy.addCallback(this.mTaskBarServiceCallBack);
        this.mUserTracker.startTracking();
        startListening(this.mCurrentUserId);
        updateNavIcon();
    }

    public void onTrackpadStateChanged(final boolean z) {
        this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.model.NavIconController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onTrackpadStateChanged$3(z);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void updateNavIcon() {
        boolean z = false;
        ?? IsDesktopMode = this.mHardwareDisplayController.getDisplayId() != -1 ? this.mHardwareDisplayController.isDesktopMode() : 0;
        boolean zIsUserSetupCompleted = isUserSetupCompleted(this.mCurrentUserId);
        if (IsDesktopMode != 0 && !this.mTrackActivityShow && zIsUserSetupCompleted && !this.mImeShowInDefaultDisplay) {
            z = true;
        }
        if (DEBUG) {
            Log.d(TAG, "updateNavIcon, isDesktopMode : " + ((boolean) IsDesktopMode) + " TrackActivityShow : " + this.mTrackActivityShow + " isUserSetupComplete : " + zIsUserSetupCompleted + " mImeShowInDefaultDisplay : " + this.mImeShowInDefaultDisplay + " mCurrentUserId : " + this.mCurrentUserId);
        }
        this.mTaskBarServiceProxy.requestNavIcon(z, IsDesktopMode);
    }
}
