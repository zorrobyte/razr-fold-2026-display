package com.motorola.plugin.core.misc;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.PowerManager;
import android.os.Process;
import android.os.UserManager;
import androidx.core.content.ContextCompat;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.components.AppContext;
import com.motorola.plugin.core.components.PluginScope;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DeviceState.kt */
/* JADX INFO: loaded from: classes2.dex */
@PluginScope
public final class DeviceState implements Disposable, ISnapshotAware {
    private final Context context;
    private final boolean isLowRamDevice;
    private boolean isUserUnlocked;
    private final DisposableContainer mDisposable;
    private final Disposable mUserUnlockReceiverDisposable;
    private final ArrayList mUserUnlockedActions;
    private final BroadcastReceiver mUserUnlockedReceiver;

    /* JADX INFO: compiled from: DeviceState.kt */
    final class DeviceStateSnapshot extends AbstractSnapshot {
        private int instance;
        private boolean myBackgroundRestricted;
        private boolean myLowBattery;
        private boolean myLowRamDevice;
        private boolean myPowerSaveMode;
        private boolean myUserUnlocked;
        private int myUserUnlockedActionSize;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DeviceStateSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final int getInstance() {
            return this.instance;
        }

        public final boolean getMyBackgroundRestricted() {
            return this.myBackgroundRestricted;
        }

        public final boolean getMyLowBattery() {
            return this.myLowBattery;
        }

        public final boolean getMyLowRamDevice() {
            return this.myLowRamDevice;
        }

        public final boolean getMyPowerSaveMode() {
            return this.myPowerSaveMode;
        }

        public final boolean getMyUserUnlocked() {
            return this.myUserUnlocked;
        }

        public final int getMyUserUnlockedActionSize() {
            return this.myUserUnlockedActionSize;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("DeviceState", getInstance()).newLine();
            iPrinter.increaseIndent();
            iPrinter.printPair("isUserUnlocked", Boolean.valueOf(getMyUserUnlocked()));
            iPrinter.printPair("userUnlockActionSize", Integer.valueOf(getMyUserUnlockedActionSize()));
            iPrinter.newLine();
            iPrinter.printPair("isLowRamDevice", Boolean.valueOf(getMyLowRamDevice()));
            iPrinter.printPair("isBackgroundRestricted", Boolean.valueOf(getMyBackgroundRestricted()));
            iPrinter.printPair("isLowBattery", Boolean.valueOf(getMyLowBattery()));
            iPrinter.printPair("isPowerSaveMode", Boolean.valueOf(getMyPowerSaveMode()));
            iPrinter.newLine();
            iPrinter.decreaseIndent();
        }

        public final void setInstance(int i) {
            this.instance = i;
        }

        public final void setMyBackgroundRestricted(boolean z) {
            this.myBackgroundRestricted = z;
        }

        public final void setMyLowBattery(boolean z) {
            this.myLowBattery = z;
        }

        public final void setMyLowRamDevice(boolean z) {
            this.myLowRamDevice = z;
        }

        public final void setMyPowerSaveMode(boolean z) {
            this.myPowerSaveMode = z;
        }

        public final void setMyUserUnlocked(boolean z) {
            this.myUserUnlocked = z;
        }

        public final void setMyUserUnlockedActionSize(int i) {
            this.myUserUnlockedActionSize = i;
        }
    }

    public DeviceState(@AppContext Context context) {
        context.getClass();
        this.context = context;
        DisposableContainer disposableContainerNewDisposableContainer = DisposableKt.newDisposableContainer();
        this.mDisposable = disposableContainerNewDisposableContainer;
        this.mUserUnlockedActions = new ArrayList();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.motorola.plugin.core.misc.DeviceState$mUserUnlockedReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                context2.getClass();
                intent.getClass();
                if (Intrinsics.areEqual("android.intent.action.USER_UNLOCKED", intent.getAction())) {
                    this.this$0.isUserUnlocked = true;
                    this.this$0.notifyUserUnlocked();
                }
            }
        };
        this.mUserUnlockedReceiver = broadcastReceiver;
        Object systemService = ContextCompat.getSystemService(context, ActivityManager.class);
        systemService.getClass();
        this.isLowRamDevice = ((ActivityManager) systemService).isLowRamDevice();
        Disposable disposable = new Disposable() { // from class: com.motorola.plugin.core.misc.DeviceState$$ExternalSyntheticLambda0
            @Override // com.motorola.plugin.core.misc.Disposable
            public final void dispose() {
                DeviceState.m2220mUserUnlockReceiverDisposable$lambda0(this.f$0);
            }
        };
        this.mUserUnlockReceiverDisposable = disposable;
        Object systemService2 = ContextCompat.getSystemService(context, UserManager.class);
        systemService2.getClass();
        boolean zIsUserUnlocked = ((UserManager) systemService2).isUserUnlocked(Process.myUserHandle());
        this.isUserUnlocked = zIsUserUnlocked;
        if (!zIsUserUnlocked) {
            context.registerReceiver(broadcastReceiver, new IntentFilter("android.intent.action.USER_UNLOCKED"));
        }
        disposableContainerNewDisposableContainer.add(disposable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: mUserUnlockReceiverDisposable$lambda-0, reason: not valid java name */
    public static final void m2220mUserUnlockReceiverDisposable$lambda0(DeviceState deviceState) {
        deviceState.getClass();
        ExtensionsKt.unregisterReceiverSafely(deviceState.mUserUnlockedReceiver, deviceState.context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyUserUnlocked() {
        ArrayList arrayList = this.mUserUnlockedActions;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Runnable) obj).run();
        }
        this.mUserUnlockedActions.clear();
        this.mUserUnlockReceiverDisposable.dispose();
        this.mDisposable.remove(this.mUserUnlockReceiverDisposable);
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        this.mDisposable.dispose();
    }

    public final boolean isBackgroundRestricted(Context context) {
        context.getClass();
        return ((ActivityManager) context.getSystemService(ActivityManager.class)).isBackgroundRestricted();
    }

    public final boolean isLowBattery(Context context) {
        context.getClass();
        Object systemService = ContextCompat.getSystemService(context, BatteryManager.class);
        systemService.getClass();
        return ((BatteryManager) systemService).getIntProperty(4) <= 30;
    }

    public final boolean isLowRamDevice() {
        return this.isLowRamDevice;
    }

    public final boolean isPowerSaveMode(Context context) {
        context.getClass();
        Object systemService = ContextCompat.getSystemService(context, PowerManager.class);
        systemService.getClass();
        return ((PowerManager) systemService).isPowerSaveMode();
    }

    public final boolean isUserUnlocked() {
        return this.isUserUnlocked;
    }

    public final void runOnUserUnlocked(Runnable runnable) {
        runnable.getClass();
        if (this.isUserUnlocked) {
            runnable.run();
        } else {
            this.mUserUnlockedActions.add(runnable);
        }
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        DeviceStateSnapshot deviceStateSnapshot = new DeviceStateSnapshot(iSnapshot);
        deviceStateSnapshot.setInstance(hashCode());
        deviceStateSnapshot.setMyUserUnlocked(isUserUnlocked());
        deviceStateSnapshot.setMyUserUnlockedActionSize(this.mUserUnlockedActions.size());
        deviceStateSnapshot.setMyLowRamDevice(isLowRamDevice());
        deviceStateSnapshot.setMyBackgroundRestricted(isBackgroundRestricted(this.context));
        deviceStateSnapshot.setMyLowBattery(isLowBattery(this.context));
        deviceStateSnapshot.setMyPowerSaveMode(isPowerSaveMode(this.context));
        return deviceStateSnapshot;
    }

    public final boolean willRunInRestrictedMode(Context context) {
        context.getClass();
        return isPowerSaveMode(context) || isLowBattery(context) || isBackgroundRestricted(context);
    }
}
