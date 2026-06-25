package com.motorola.laptoppanel.service;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import com.motorola.laptoppanel.util.Logger;
import java.io.PrintWriter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: DisplayRotationMonitor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DisplayRotationMonitor {
    private final MutableStateFlow _isLandscape;
    private final Context context;
    private Job debounceJob;
    private final DisplayRotationMonitor$displayListener$1 displayListener;
    private final DisplayManager displayManager;
    private final StateFlow isLandscape;
    private final Handler mainHandler;
    private final CoroutineScope scope;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: DisplayRotationMonitor.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.service.DisplayRotationMonitor$checkRotation$1, reason: invalid class name */
    /* JADX INFO: compiled from: DisplayRotationMonitor.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $isLandscapeRotation;
        final /* synthetic */ int $rotation;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(boolean z, int i, Continuation continuation) {
            super(2, continuation);
            this.$isLandscapeRotation = z;
            this.$rotation = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DisplayRotationMonitor.this.new AnonymousClass1(this.$isLandscapeRotation, this.$rotation, continuation);
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
                this.label = 1;
                if (DelayKt.delay(300L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            boolean zBooleanValue = ((Boolean) DisplayRotationMonitor.this._isLandscape.getValue()).booleanValue();
            boolean z = this.$isLandscapeRotation;
            if (zBooleanValue != z) {
                Logger.INSTANCE.d(DisplayRotationMonitor.this, "Stable rotation detected: isLandscape=" + z + " (rot=" + this.$rotation + ")", new Object[0]);
                DisplayRotationMonitor.this._isLandscape.setValue(Boxing.boxBoolean(this.$isLandscapeRotation));
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.motorola.laptoppanel.service.DisplayRotationMonitor$displayListener$1] */
    public DisplayRotationMonitor(Context context, CoroutineScope coroutineScope) {
        context.getClass();
        coroutineScope.getClass();
        this.context = context;
        this.scope = coroutineScope;
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isLandscape = MutableStateFlow;
        this.isLandscape = FlowKt.asStateFlow(MutableStateFlow);
        Object systemService = context.getSystemService("display");
        systemService.getClass();
        this.displayManager = (DisplayManager) systemService;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.displayListener = new DisplayManager.DisplayListener() { // from class: com.motorola.laptoppanel.service.DisplayRotationMonitor$displayListener$1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
                if (i == 0) {
                    DisplayRotationMonitor.checkRotation$default(this.this$0, false, 1, null);
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
            }
        };
    }

    private final void checkRotation(boolean z) {
        Display display = this.displayManager.getDisplay(0);
        if (display == null) {
            return;
        }
        int rotation = display.getRotation();
        boolean z2 = rotation == 1 || rotation == 3;
        Job job = this.debounceJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        if (z2 == ((Boolean) this._isLandscape.getValue()).booleanValue()) {
            return;
        }
        if (!z) {
            this.debounceJob = BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new AnonymousClass1(z2, rotation, null), 3, null);
            return;
        }
        Logger.INSTANCE.d(this, "Immediate rotation update: isLandscape=" + z2 + " (rot=" + rotation + ")", new Object[0]);
        this._isLandscape.setValue(Boolean.valueOf(z2));
    }

    static /* synthetic */ void checkRotation$default(DisplayRotationMonitor displayRotationMonitor, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        displayRotationMonitor.checkRotation(z);
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.getClass();
        printWriter.println("DisplayRotationMonitor State:");
        printWriter.println("  isLandscape (StateFlow): " + this._isLandscape.getValue());
        boolean z = false;
        Display display = this.displayManager.getDisplay(0);
        Integer numValueOf = display != null ? Integer.valueOf(display.getRotation()) : null;
        if ((numValueOf != null && numValueOf.intValue() == 1) || (numValueOf != null && numValueOf.intValue() == 3)) {
            z = true;
        }
        printWriter.println("  isLandscape (Real-time): " + z + " (rotation=" + numValueOf + ")");
        printWriter.println("--------------------------------------------------");
    }

    public final StateFlow isLandscape() {
        return this.isLandscape;
    }

    public final void start() {
        Logger.INSTANCE.d(this, "Starting display rotation monitoring.", new Object[0]);
        this.displayManager.registerDisplayListener(this.displayListener, this.mainHandler);
        checkRotation(true);
    }

    public final void stop() {
        Logger.INSTANCE.d(this, "Stopping display rotation monitoring.", new Object[0]);
        this.displayManager.unregisterDisplayListener(this.displayListener);
        Job job = this.debounceJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
    }
}
