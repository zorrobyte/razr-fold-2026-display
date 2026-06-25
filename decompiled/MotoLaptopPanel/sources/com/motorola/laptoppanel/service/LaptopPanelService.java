package com.motorola.laptoppanel.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.motorola.laptoppanel.ILaptopPanelController;
import com.motorola.laptoppanel.checkin.LaptopPanelCheckinService;
import com.motorola.laptoppanel.service.LaptopPanelService;
import com.motorola.laptoppanel.util.Logger;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.SupervisorKt;

/* JADX INFO: compiled from: LaptopPanelService.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LaptopPanelService extends Service {
    public static final String ACTION_BIND_LOCAL = "com.motorola.laptoppanel.BIND_LOCAL";
    private static final String TAG = "LaptopPanelService";
    private LaptopPanelController controller;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private final LocalBinder localBinder = new LocalBinder();
    private final CoroutineScope serviceScope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob$default(null, 1, null).plus(Dispatchers.getMain()));
    private final LaptopPanelService$apiBinder$1 apiBinder = new ILaptopPanelController.Stub() { // from class: com.motorola.laptoppanel.service.LaptopPanelService$apiBinder$1
        @Override // com.motorola.laptoppanel.ILaptopPanelController
        public void setLaptopPostureEnabled(boolean z) {
            Logger.INSTANCE.d(this, "setLaptopPostureEnabled(" + z + ")", new Object[0]);
            LaptopPanelService.Gate.INSTANCE.setPostureEnabled(z);
            if (z) {
                this.this$0.ensureController();
            } else {
                this.this$0.stopSelf();
            }
        }
    };

    /* JADX INFO: compiled from: LaptopPanelService.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: LaptopPanelService.kt */
    public final class Gate {
        private static volatile boolean postureEnabled;
        public static final Gate INSTANCE = new Gate();
        public static final int $stable = 8;

        private Gate() {
        }

        public final void setPostureEnabled(boolean z) {
            postureEnabled = z;
        }
    }

    /* JADX INFO: compiled from: LaptopPanelService.kt */
    public final class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public final LaptopPanelService getService() {
            return LaptopPanelService.this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void ensureController() {
        if (this.controller == null) {
            Context applicationContext = getApplicationContext();
            applicationContext.getClass();
            LaptopPanelController laptopPanelController = new LaptopPanelController(applicationContext, this.serviceScope);
            laptopPanelController.init();
            this.controller = laptopPanelController;
        }
    }

    @Override // android.app.Service
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        fileDescriptor.getClass();
        printWriter.getClass();
        strArr.getClass();
        super.dump(fileDescriptor, printWriter, strArr);
        if (this.controller != null) {
            getController().dump(fileDescriptor, printWriter, strArr);
        }
    }

    public final LaptopPanelController getController() {
        LaptopPanelController laptopPanelController = this.controller;
        if (laptopPanelController != null) {
            return laptopPanelController;
        }
        Intrinsics.throwUninitializedPropertyAccessException("controller");
        return null;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return Intrinsics.areEqual(intent != null ? intent.getAction() : null, ACTION_BIND_LOCAL) ? this.localBinder : this.apiBinder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        ensureController();
        LaptopPanelCheckinService.Companion.schedule$default(LaptopPanelCheckinService.Companion, this, false, 2, null);
        Logger.INSTANCE.i(this, "onCreate", new Object[0]);
    }

    @Override // android.app.Service
    public void onDestroy() {
        Logger.INSTANCE.i(this, "onDestroy", new Object[0]);
        if (this.controller != null) {
            getController().onDestroy();
        }
        CoroutineScopeKt.cancel$default(this.serviceScope, null, 1, null);
        super.onDestroy();
    }
}
