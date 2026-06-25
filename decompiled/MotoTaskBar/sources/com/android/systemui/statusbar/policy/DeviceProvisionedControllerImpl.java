package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.util.ArraySet;
import android.util.SparseBooleanArray;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: DeviceProvisionedControllerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public class DeviceProvisionedControllerImpl implements DeviceProvisionedController, DeviceProvisionedController.DeviceProvisionedListener, Dumpable {
    public static final Companion Companion = new Companion(null);
    private final HandlerExecutor backgroundExecutor;
    private final Handler backgroundHandler;
    private final AtomicBoolean deviceProvisioned;
    private final Uri deviceProvisionedUri;
    private final DumpManager dumpManager;
    private final GlobalSettings globalSettings;
    private final AtomicBoolean initted;
    private final ArraySet listeners;
    private final Object lock;
    private final Executor mainExecutor;
    private final DeviceProvisionedControllerImpl$observer$1 observer;
    private final SecureSettings secureSettings;
    private final DeviceProvisionedControllerImpl$userChangedCallback$1 userChangedCallback;
    private final SparseBooleanArray userSetupComplete;
    private final Uri userSetupUri;
    private final UserTracker userTracker;

    /* JADX INFO: compiled from: DeviceProvisionedControllerImpl.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl$onDeviceProvisionedChanged$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DeviceProvisionedControllerImpl.kt */
    final /* synthetic */ class C01251 extends FunctionReferenceImpl implements Function1 {
        public static final C01251 INSTANCE = new C01251();

        C01251() {
            super(1, DeviceProvisionedController.DeviceProvisionedListener.class, "onDeviceProvisionedChanged", "onDeviceProvisionedChanged()V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((DeviceProvisionedController.DeviceProvisionedListener) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(DeviceProvisionedController.DeviceProvisionedListener deviceProvisionedListener) {
            deviceProvisionedListener.getClass();
            deviceProvisionedListener.onDeviceProvisionedChanged();
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl$onUserSetupChanged$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DeviceProvisionedControllerImpl.kt */
    final /* synthetic */ class C01261 extends FunctionReferenceImpl implements Function1 {
        public static final C01261 INSTANCE = new C01261();

        C01261() {
            super(1, DeviceProvisionedController.DeviceProvisionedListener.class, "onUserSetupChanged", "onUserSetupChanged()V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((DeviceProvisionedController.DeviceProvisionedListener) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(DeviceProvisionedController.DeviceProvisionedListener deviceProvisionedListener) {
            deviceProvisionedListener.getClass();
            deviceProvisionedListener.onUserSetupChanged();
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl$onUserSwitched$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DeviceProvisionedControllerImpl.kt */
    final /* synthetic */ class C01271 extends FunctionReferenceImpl implements Function1 {
        public static final C01271 INSTANCE = new C01271();

        C01271() {
            super(1, DeviceProvisionedController.DeviceProvisionedListener.class, "onUserSwitched", "onUserSwitched()V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((DeviceProvisionedController.DeviceProvisionedListener) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(DeviceProvisionedController.DeviceProvisionedListener deviceProvisionedListener) {
            deviceProvisionedListener.getClass();
            deviceProvisionedListener.onUserSwitched();
        }
    }

    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl$observer$1] */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl$userChangedCallback$1] */
    public DeviceProvisionedControllerImpl(SecureSettings secureSettings, GlobalSettings globalSettings, UserTracker userTracker, DumpManager dumpManager, final Handler handler, Executor executor) {
        secureSettings.getClass();
        globalSettings.getClass();
        userTracker.getClass();
        dumpManager.getClass();
        handler.getClass();
        executor.getClass();
        this.secureSettings = secureSettings;
        this.globalSettings = globalSettings;
        this.userTracker = userTracker;
        this.dumpManager = dumpManager;
        this.backgroundHandler = handler;
        this.mainExecutor = executor;
        this.deviceProvisionedUri = globalSettings.getUriFor("device_provisioned");
        this.userSetupUri = secureSettings.getUriFor("user_setup_complete");
        this.deviceProvisioned = new AtomicBoolean(false);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        this.userSetupComplete = sparseBooleanArray;
        this.listeners = new ArraySet();
        this.lock = new Object();
        this.backgroundExecutor = new HandlerExecutor(handler);
        this.initted = new AtomicBoolean(false);
        this.observer = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl$observer$1
            public void onChange(boolean z, Collection collection, int i, int i2) {
                collection.getClass();
                boolean zContains = collection.contains(this.this$0.deviceProvisionedUri);
                if (!collection.contains(this.this$0.userSetupUri)) {
                    i2 = -2;
                }
                this.this$0.updateValues(zContains, i2);
                if (zContains) {
                    this.this$0.onDeviceProvisionedChanged();
                }
                if (i2 != -2) {
                    this.this$0.onUserSetupChanged();
                }
            }
        };
        this.userChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl$userChangedCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public void onProfilesChanged(List list) {
                list.getClass();
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public void onUserChanged(int i, Context context) {
                context.getClass();
                this.this$0.updateValues(false, i);
                this.this$0.onUserSwitched();
            }
        };
        sparseBooleanArray.put(getCurrentUser(), false);
    }

    private final int get_currentUser() {
        return this.userTracker.getUserId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateValues(boolean z, int i) {
        boolean z2 = true;
        if (z) {
            this.deviceProvisioned.set(this.globalSettings.getInt("device_provisioned", 0) != 0);
        }
        synchronized (this.lock) {
            try {
                if (i == -1) {
                    int size = this.userSetupComplete.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        int iKeyAt = this.userSetupComplete.keyAt(i2);
                        this.userSetupComplete.put(iKeyAt, this.secureSettings.getIntForUser("user_setup_complete", 0, iKeyAt) != 0);
                    }
                } else if (i != -2) {
                    if (this.secureSettings.getIntForUser("user_setup_complete", 0, i) == 0) {
                        z2 = false;
                    }
                    this.userSetupComplete.put(i, z2);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static /* synthetic */ void updateValues$default(DeviceProvisionedControllerImpl deviceProvisionedControllerImpl, boolean z, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateValues");
        }
        if ((i2 & 1) != 0) {
            z = true;
        }
        if ((i2 & 2) != 0) {
            i = -1;
        }
        deviceProvisionedControllerImpl.updateValues(z, i);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(DeviceProvisionedController.DeviceProvisionedListener deviceProvisionedListener) {
        deviceProvisionedListener.getClass();
        synchronized (this.lock) {
            this.listeners.add(deviceProvisionedListener);
        }
    }

    protected final void dispatchChange(final Function1 function1) {
        final ArrayList arrayList;
        function1.getClass();
        synchronized (this.lock) {
            arrayList = new ArrayList(this.listeners);
        }
        this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl.dispatchChange.1
            @Override // java.lang.Runnable
            public final void run() {
                ArrayList arrayList2 = arrayList;
                Function1 function12 = function1;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    function12.invoke(obj);
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController
    public int getCurrentUser() {
        return get_currentUser();
    }

    public void init() {
        if (this.initted.compareAndSet(false, true)) {
            this.dumpManager.registerDumpable(this);
            updateValues$default(this, false, 0, 3, null);
            this.userTracker.addCallback(this.userChangedCallback, this.backgroundExecutor);
            this.globalSettings.registerContentObserver(this.deviceProvisionedUri, this.observer);
            this.secureSettings.registerContentObserverForUser(this.userSetupUri, this.observer, -1);
        }
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController
    public boolean isCurrentUserSetup() {
        return isUserSetup(getCurrentUser());
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController
    public boolean isDeviceProvisioned() {
        return this.deviceProvisioned.get();
    }

    public boolean isUserSetup(int i) {
        int iIndexOfKey;
        boolean z;
        synchronized (this.lock) {
            iIndexOfKey = this.userSetupComplete.indexOfKey(i);
        }
        if (iIndexOfKey >= 0) {
            synchronized (this.lock) {
                z = this.userSetupComplete.get(i, false);
            }
            return z;
        }
        boolean z2 = this.secureSettings.getIntForUser("user_setup_complete", 0, i) != 0;
        synchronized (this.lock) {
            this.userSetupComplete.put(i, z2);
            Unit unit = Unit.INSTANCE;
        }
        return z2;
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public void onDeviceProvisionedChanged() {
        dispatchChange(C01251.INSTANCE);
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public void onUserSetupChanged() {
        dispatchChange(C01261.INSTANCE);
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public void onUserSwitched() {
        dispatchChange(C01271.INSTANCE);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(DeviceProvisionedController.DeviceProvisionedListener deviceProvisionedListener) {
        deviceProvisionedListener.getClass();
        synchronized (this.lock) {
            this.listeners.remove(deviceProvisionedListener);
        }
    }
}
