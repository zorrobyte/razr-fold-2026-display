package com.motorola.laptoppanel.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import com.motorola.laptoppanel.ILaptopPanelController;
import com.motorola.laptoppanel.util.Logger;
import com.motorola.laptoppanel.util.Posture;
import com.motorola.laptoppanel.util.PostureMonitor;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: LaptopPanelSystemServerBindService.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LaptopPanelSystemServerBindService extends Service {
    private volatile boolean isBinding;
    private volatile ILaptopPanelController lpsApi;
    private volatile boolean lpsBound;
    private PostureMonitor postureMonitor;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private final Binder mssBinder = new Binder();
    private final CoroutineScope serviceScope = CoroutineScopeKt.MainScope();
    private final LaptopPanelSystemServerBindService$lpsConn$1 lpsConn = new ServiceConnection() { // from class: com.motorola.laptoppanel.service.LaptopPanelSystemServerBindService$lpsConn$1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Object objM2192constructorimpl;
            Unit unit;
            componentName.getClass();
            iBinder.getClass();
            this.this$0.isBinding = false;
            this.this$0.lpsApi = ILaptopPanelController.Stub.asInterface(iBinder);
            LaptopPanelSystemServerBindService laptopPanelSystemServerBindService = this.this$0;
            try {
                Result.Companion companion = Result.Companion;
                ILaptopPanelController iLaptopPanelController = laptopPanelSystemServerBindService.lpsApi;
                if (iLaptopPanelController != null) {
                    iLaptopPanelController.setLaptopPostureEnabled(true);
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                objM2192constructorimpl = Result.m2192constructorimpl(unit);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM2192constructorimpl = Result.m2192constructorimpl(ResultKt.createFailure(th));
            }
            Throwable thM2193exceptionOrNullimpl = Result.m2193exceptionOrNullimpl(objM2192constructorimpl);
            if (thM2193exceptionOrNullimpl != null) {
                Logger.INSTANCE.e("LaptopPanelSystemBind", "AIDL enable failed", thM2193exceptionOrNullimpl);
            }
            this.this$0.lpsBound = true;
            Logger.INSTANCE.i("LaptopPanelSystemBind", "LPS bound & initialized", new Object[0]);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            componentName.getClass();
            this.this$0.resetLpsState();
            Logger.INSTANCE.i("LaptopPanelSystemBind", "LPS disconnected", new Object[0]);
        }
    };

    /* JADX INFO: compiled from: LaptopPanelSystemServerBindService.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.service.LaptopPanelSystemServerBindService$onCreate$1, reason: invalid class name */
    /* JADX INFO: compiled from: LaptopPanelSystemServerBindService.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* JADX INFO: renamed from: com.motorola.laptoppanel.service.LaptopPanelSystemServerBindService$onCreate$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: LaptopPanelSystemServerBindService.kt */
        final class C00181 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ LaptopPanelSystemServerBindService this$0;

            /* JADX INFO: renamed from: com.motorola.laptoppanel.service.LaptopPanelSystemServerBindService$onCreate$1$1$WhenMappings */
            /* JADX INFO: compiled from: LaptopPanelSystemServerBindService.kt */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[Posture.values().length];
                    try {
                        iArr[Posture.LAPTOP.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[Posture.BOOK.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00181(LaptopPanelSystemServerBindService laptopPanelSystemServerBindService, Continuation continuation) {
                super(2, continuation);
                this.this$0 = laptopPanelSystemServerBindService;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00181 c00181 = new C00181(this.this$0, continuation);
                c00181.L$0 = obj;
                return c00181;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Posture posture, Continuation continuation) {
                return ((C00181) create(posture, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Posture posture = (Posture) this.L$0;
                int i = WhenMappings.$EnumSwitchMapping$0[posture.ordinal()];
                if (i == 1) {
                    Logger.INSTANCE.d("LaptopPanelSystemBind", "Laptop posture -> scheduling bind", new Object[0]);
                    this.this$0.bindLaptopPanelService();
                } else if (i != 2) {
                    Logger.INSTANCE.d("LaptopPanelSystemBind", "Laptop/BOOK posture OFF (posture=" + posture + ") -> requesting stop", new Object[0]);
                    this.this$0.requestLaptopPanelStopAndUnbind();
                } else {
                    Logger.INSTANCE.d("LaptopPanelSystemBind", "Book posture -> do nothing", new Object[0]);
                }
                return Unit.INSTANCE;
            }
        }

        AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LaptopPanelSystemServerBindService.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PostureMonitor postureMonitor = LaptopPanelSystemServerBindService.this.postureMonitor;
                if (postureMonitor == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("postureMonitor");
                    postureMonitor = null;
                }
                StateFlow posture = postureMonitor.getPosture();
                C00181 c00181 = new C00181(LaptopPanelSystemServerBindService.this, null);
                this.label = 1;
                if (FlowKt.collectLatest(posture, c00181, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void bindLaptopPanelService() {
        Object objM2192constructorimpl;
        if (this.lpsBound || this.isBinding) {
            return;
        }
        this.isBinding = true;
        ComponentName componentName = new ComponentName(this, "com.motorola.laptoppanel.service.LaptopPanelService");
        try {
            Result.Companion companion = Result.Companion;
            objM2192constructorimpl = Result.m2192constructorimpl(Boolean.valueOf(bindService(new Intent().setComponent(componentName), this.lpsConn, 1)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2192constructorimpl = Result.m2192constructorimpl(ResultKt.createFailure(th));
        }
        Boolean bool = Boolean.FALSE;
        if (Result.m2194isFailureimpl(objM2192constructorimpl)) {
            objM2192constructorimpl = bool;
        }
        if (((Boolean) objM2192constructorimpl).booleanValue()) {
            return;
        }
        this.isBinding = false;
        Logger.INSTANCE.w("LaptopPanelSystemBind", "bindService to LPS returned false", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestLaptopPanelStopAndUnbind() {
        Object objM2192constructorimpl;
        Unit unit;
        try {
            Result.Companion companion = Result.Companion;
            ILaptopPanelController iLaptopPanelController = this.lpsApi;
            if (iLaptopPanelController != null) {
                iLaptopPanelController.setLaptopPostureEnabled(false);
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            objM2192constructorimpl = Result.m2192constructorimpl(unit);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2192constructorimpl = Result.m2192constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2193exceptionOrNullimpl = Result.m2193exceptionOrNullimpl(objM2192constructorimpl);
        if (thM2193exceptionOrNullimpl != null) {
            Logger.INSTANCE.w("LaptopPanelSystemBind", "AIDL disable failed", thM2193exceptionOrNullimpl);
        }
        if (this.lpsBound || this.isBinding) {
            try {
                unbindService(this.lpsConn);
                Result.m2192constructorimpl(Unit.INSTANCE);
            } catch (Throwable th2) {
                Result.Companion companion3 = Result.Companion;
                Result.m2192constructorimpl(ResultKt.createFailure(th2));
            }
            resetLpsState();
            Logger.INSTANCE.d("LaptopPanelSystemBind", "LPS unbound", new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void resetLpsState() {
        this.isBinding = false;
        this.lpsBound = false;
        this.lpsApi = null;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        intent.getClass();
        PostureMonitor postureMonitor = null;
        if (!Intrinsics.areEqual(intent.getAction(), "com.motorola.intent.action.EXPERIENCE")) {
            return null;
        }
        PostureMonitor postureMonitor2 = this.postureMonitor;
        if (postureMonitor2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("postureMonitor");
        } else {
            postureMonitor = postureMonitor2;
        }
        if (postureMonitor.getPosture().getValue() == Posture.LAPTOP) {
            bindLaptopPanelService();
        }
        return this.mssBinder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Context applicationContext = getApplicationContext();
        applicationContext.getClass();
        this.postureMonitor = new PostureMonitor(applicationContext, this.serviceScope);
        BuildersKt__Builders_commonKt.launch$default(this.serviceScope, null, null, new AnonymousClass1(null), 3, null);
        Logger.INSTANCE.d("LaptopPanelSystemBind", "onCreate: posture listener started", new Object[0]);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        CoroutineScopeKt.cancel$default(this.serviceScope, null, 1, null);
        requestLaptopPanelStopAndUnbind();
        Logger.INSTANCE.d("LaptopPanelSystemBind", "onDestroy: posture listener stopped & LPS unbound", new Object[0]);
    }
}
