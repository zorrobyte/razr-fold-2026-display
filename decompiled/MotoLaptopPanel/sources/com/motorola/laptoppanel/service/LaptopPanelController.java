package com.motorola.laptoppanel.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Display;
import com.motorola.laptoppanel.activity.LaptopPanelActivity;
import com.motorola.laptoppanel.service.PanelUiState;
import com.motorola.laptoppanel.util.LaptopPrefs;
import com.motorola.laptoppanel.util.Logger;
import com.motorola.laptoppanel.util.Posture;
import com.motorola.laptoppanel.util.PostureMonitor;
import com.motorola.laptoppanel.util.TaskUtilsKt;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import motorola.core_services.fold.MotoFoldManager;
import motorola.core_services.window.MotoWindowManager;

/* JADX INFO: compiled from: LaptopPanelController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LaptopPanelController {
    private final Map activePanelsMap;
    private PanelAppsManager appsManager;
    private final Context context;
    private DisplayRotationMonitor displayRotationMonitor;
    private final Lazy floatingIconController$delegate;
    private boolean isOrientationForced;
    private final MutableStateFlow isRecentsAnimationRunning;
    private LaptopPrefs laptopPrefs;
    private long lastVisibleStartTime;
    private boolean mPendingDismissForRecents;
    private Pair mPendingLaunch;
    private final LaptopPanelController$mRecentsAnimationReceiver$1 mRecentsAnimationReceiver;
    private final Lazy motoFM$delegate;
    private Job pendingLaunchJob;
    private PostureMonitor postureMonitor;
    private final CoroutineScope scope;
    private TopAppMonitor topAppMonitor;
    private PanelUiState uiState;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: LaptopPanelController.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: LaptopPanelController.kt */
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

    /* JADX INFO: renamed from: com.motorola.laptoppanel.service.LaptopPanelController$init$1, reason: invalid class name */
    /* JADX INFO: compiled from: LaptopPanelController.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ boolean Z$0;
        int label;

        AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return invoke(((Boolean) obj).booleanValue(), (Posture) obj2, (Continuation) obj3);
        }

        public final Object invoke(boolean z, Posture posture, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = LaptopPanelController.this.new AnonymousClass1(continuation);
            anonymousClass1.Z$0 = z;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

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
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            boolean z2 = z && LaptopPanelController.this.isLaptopPanelPosture();
            Logger logger = Logger.INSTANCE;
            LaptopPanelController laptopPanelController = LaptopPanelController.this;
            logger.d(laptopPanelController, "System state combined: isModeActive=" + z2 + " (enabled=" + z + ", isLaptopPanelPosture=" + laptopPanelController.isLaptopPanelPosture() + ")", new Object[0]);
            LaptopPanelController.this.checkLaptopMode();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.service.LaptopPanelController$init$2, reason: invalid class name */
    /* JADX INFO: compiled from: LaptopPanelController.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = LaptopPanelController.this.new AnonymousClass2(continuation);
            anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
        }

        public final Object invoke(boolean z, Continuation continuation) {
            return ((AnonymousClass2) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (this.Z$0) {
                Logger.INSTANCE.d(LaptopPanelController.this, "Entered landscape orientation, checking laptop mode.", new Object[0]);
                LaptopPanelController.this.checkLaptopMode();
            } else if (LaptopPanelController.this.uiState instanceof PanelUiState.IconVisible) {
                Logger.INSTANCE.d(LaptopPanelController.this, "Exited landscape while Icon visible. Dismissing icon.", new Object[0]);
                LaptopPanelController.this.onLaptopModeActiveChanged(false);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.service.LaptopPanelController$init$3, reason: invalid class name */
    /* JADX INFO: compiled from: LaptopPanelController.kt */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = LaptopPanelController.this.new AnonymousClass3(continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Set set, Continuation continuation) {
            return ((AnonymousClass3) create(set, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Set set = (Set) this.L$0;
            Pair pairSnapshotTop = LaptopPanelController.this.snapshotTop();
            String str = (String) pairSnapshotTop.component1();
            int iIntValue = ((Number) pairSnapshotTop.component2()).intValue();
            if (str != null) {
                PanelAppsManager panelAppsManager = LaptopPanelController.this.appsManager;
                if (panelAppsManager == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("appsManager");
                    panelAppsManager = null;
                }
                if (panelAppsManager.isPackageInSet(str, set)) {
                    LaptopPanelController.this.checkAndUpdatePanelState(str, iIntValue);
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.service.LaptopPanelController$launchLaptopPanel$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LaptopPanelController.kt */
    final class C01221 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $packageName;
        final /* synthetic */ int $taskId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01221(String str, int i, Continuation continuation) {
            super(2, continuation);
            this.$packageName = str;
            this.$taskId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C01221 c01221 = LaptopPanelController.this.new C01221(this.$packageName, this.$taskId, continuation);
            c01221.L$0 = obj;
            return c01221;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01221) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope;
            Exception e;
            Object objM2192constructorimpl;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope2 = (CoroutineScope) this.L$0;
                try {
                    Logger.INSTANCE.i(coroutineScope2, "Forcing landscape orientation for panel launch.", new Object[0]);
                    LaptopPanelController.this.getMotoFM().forceLandscapeOrientation();
                    LaptopPanelController.this.isOrientationForced = true;
                    if (!LaptopPanelController.this.isCurrentRotationLandscape()) {
                        this.L$0 = coroutineScope2;
                        this.label = 1;
                        if (DelayKt.delay(150L, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                    coroutineScope = coroutineScope2;
                } catch (Exception e2) {
                    coroutineScope = coroutineScope2;
                    e = e2;
                    Logger.INSTANCE.e(coroutineScope, e, "launchLaptopPanel: Coroutine failed to execute for (" + this.$packageName + ", " + this.$taskId + ")", new Object[0]);
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                coroutineScope = (CoroutineScope) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                } catch (Exception e3) {
                    e = e3;
                    Logger.INSTANCE.e(coroutineScope, e, "launchLaptopPanel: Coroutine failed to execute for (" + this.$packageName + ", " + this.$taskId + ")", new Object[0]);
                }
            }
            PackageManager packageManager = LaptopPanelController.this.context.getPackageManager();
            ComponentName componentName = new ComponentName("com.motorola.laptoppanel", "com.motorola.laptoppanel.activity.LaptopPanelActivity");
            try {
                packageManager.getActivityInfo(componentName, 0);
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.setComponent(componentName);
                intent.addFlags(337907712);
                Bundle bundle = new Bundle();
                bundle.putParcelable("intent", intent);
                bundle.putInt("position", 1);
                LaptopPanelController laptopPanelController = LaptopPanelController.this;
                try {
                    Result.Companion companion = Result.Companion;
                    objM2192constructorimpl = Result.m2192constructorimpl(laptopPanelController.context.getContentResolver().call(Uri.parse("content://com.motorola.launcher3.settings"), "launch_split", (String) null, bundle));
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.Companion;
                    objM2192constructorimpl = Result.m2192constructorimpl(ResultKt.createFailure(th));
                }
                LaptopPanelController laptopPanelController2 = LaptopPanelController.this;
                Throwable thM2193exceptionOrNullimpl = Result.m2193exceptionOrNullimpl(objM2192constructorimpl);
                if (thM2193exceptionOrNullimpl != null) {
                    Logger.INSTANCE.e(coroutineScope, thM2193exceptionOrNullimpl, "launchLaptopPanel: launch_split provider call fail", new Object[0]);
                    laptopPanelController2.setUiState(PanelUiState.Hidden.INSTANCE);
                }
                String str = this.$packageName;
                if (Result.m2195isSuccessimpl(objM2192constructorimpl)) {
                    Logger.INSTANCE.d(coroutineScope, "launchLaptopPanel: LaptopPanel launched for package: " + str, new Object[0]);
                }
                return Unit.INSTANCE;
            } catch (PackageManager.NameNotFoundException e4) {
                Logger.INSTANCE.e(coroutineScope, e4, "launchLaptopPanel: Activity not exist " + componentName, new Object[0]);
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.service.LaptopPanelController$scheduleDelayedPanelLaunch$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LaptopPanelController.kt */
    final class C01231 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $pkg;
        final /* synthetic */ int $taskId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01231(String str, int i, Continuation continuation) {
            super(2, continuation);
            this.$pkg = str;
            this.$taskId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LaptopPanelController.this.new C01231(this.$pkg, this.$taskId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01231) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x0082 A[Catch: all -> 0x0014, TryCatch #0 {all -> 0x0014, blocks: (B:5:0x0010, B:15:0x002d, B:17:0x0055, B:19:0x0064, B:21:0x006e, B:23:0x0078, B:24:0x0082, B:26:0x0093, B:12:0x0022), top: B:31:0x000c }] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                java.lang.String r2 = "scheduleDelayedPanelLaunch: Job completed"
                r3 = 1
                r4 = 0
                java.lang.String r5 = "LaptopPanelController-Coroutine"
                if (r1 == 0) goto L1f
                if (r1 != r3) goto L17
                kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L14
                goto L2d
            L14:
                r8 = move-exception
                goto La2
            L17:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L1f:
                kotlin.ResultKt.throwOnFailure(r9)
                r8.label = r3     // Catch: java.lang.Throwable -> L14
                r6 = 250(0xfa, double:1.235E-321)
                java.lang.Object r9 = kotlinx.coroutines.DelayKt.delay(r6, r8)     // Catch: java.lang.Throwable -> L14
                if (r9 != r0) goto L2d
                return r0
            L2d:
                com.motorola.laptoppanel.service.LaptopPanelController r9 = com.motorola.laptoppanel.service.LaptopPanelController.this     // Catch: java.lang.Throwable -> L14
                com.motorola.laptoppanel.service.PanelUiState r9 = com.motorola.laptoppanel.service.LaptopPanelController.access$getUiState$p(r9)     // Catch: java.lang.Throwable -> L14
                com.motorola.laptoppanel.util.Logger r0 = com.motorola.laptoppanel.util.Logger.INSTANCE     // Catch: java.lang.Throwable -> L14
                com.motorola.laptoppanel.service.LaptopPanelController r1 = com.motorola.laptoppanel.service.LaptopPanelController.this     // Catch: java.lang.Throwable -> L14
                com.motorola.laptoppanel.service.PanelUiState r1 = com.motorola.laptoppanel.service.LaptopPanelController.access$getUiState$p(r1)     // Catch: java.lang.Throwable -> L14
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L14
                r3.<init>()     // Catch: java.lang.Throwable -> L14
                java.lang.String r6 = "scheduleDelayedPanelLaunch: Executing delayed panel launch uiState="
                r3.append(r6)     // Catch: java.lang.Throwable -> L14
                r3.append(r1)     // Catch: java.lang.Throwable -> L14
                java.lang.String r1 = r3.toString()     // Catch: java.lang.Throwable -> L14
                java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L14
                r0.d(r5, r1, r3)     // Catch: java.lang.Throwable -> L14
                boolean r1 = r9 instanceof com.motorola.laptoppanel.service.PanelUiState.LaunchingDelayed     // Catch: java.lang.Throwable -> L14
                if (r1 == 0) goto L82
                r1 = r9
                com.motorola.laptoppanel.service.PanelUiState$LaunchingDelayed r1 = (com.motorola.laptoppanel.service.PanelUiState.LaunchingDelayed) r1     // Catch: java.lang.Throwable -> L14
                java.lang.String r1 = r1.getPkg()     // Catch: java.lang.Throwable -> L14
                java.lang.String r3 = r8.$pkg     // Catch: java.lang.Throwable -> L14
                boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r3)     // Catch: java.lang.Throwable -> L14
                if (r1 == 0) goto L82
                com.motorola.laptoppanel.service.PanelUiState$LaunchingDelayed r9 = (com.motorola.laptoppanel.service.PanelUiState.LaunchingDelayed) r9     // Catch: java.lang.Throwable -> L14
                int r9 = r9.getTaskId()     // Catch: java.lang.Throwable -> L14
                int r1 = r8.$taskId     // Catch: java.lang.Throwable -> L14
                if (r9 != r1) goto L82
                com.motorola.laptoppanel.service.LaptopPanelController r9 = com.motorola.laptoppanel.service.LaptopPanelController.this     // Catch: java.lang.Throwable -> L14
                java.lang.String r3 = r8.$pkg     // Catch: java.lang.Throwable -> L14
                boolean r9 = com.motorola.laptoppanel.service.LaptopPanelController.access$checkShouldShowPanel(r9, r3, r1)     // Catch: java.lang.Throwable -> L14
                if (r9 == 0) goto L82
                com.motorola.laptoppanel.service.LaptopPanelController r9 = com.motorola.laptoppanel.service.LaptopPanelController.this     // Catch: java.lang.Throwable -> L14
                java.lang.String r1 = r8.$pkg     // Catch: java.lang.Throwable -> L14
                int r8 = r8.$taskId     // Catch: java.lang.Throwable -> L14
                r9.launchLaptopPanel(r1, r8)     // Catch: java.lang.Throwable -> L14
                goto L9a
            L82:
                java.lang.String r9 = "scheduleDelayedPanelLaunch: Conditions changed or state is no longer LaunchingDelayed, skipping."
                java.lang.Object[] r1 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L14
                r0.d(r5, r9, r1)     // Catch: java.lang.Throwable -> L14
                com.motorola.laptoppanel.service.LaptopPanelController r9 = com.motorola.laptoppanel.service.LaptopPanelController.this     // Catch: java.lang.Throwable -> L14
                com.motorola.laptoppanel.service.PanelUiState r9 = com.motorola.laptoppanel.service.LaptopPanelController.access$getUiState$p(r9)     // Catch: java.lang.Throwable -> L14
                boolean r9 = r9 instanceof com.motorola.laptoppanel.service.PanelUiState.LaunchingDelayed     // Catch: java.lang.Throwable -> L14
                if (r9 == 0) goto L9a
                com.motorola.laptoppanel.service.LaptopPanelController r8 = com.motorola.laptoppanel.service.LaptopPanelController.this     // Catch: java.lang.Throwable -> L14
                com.motorola.laptoppanel.service.PanelUiState$Hidden r9 = com.motorola.laptoppanel.service.PanelUiState.Hidden.INSTANCE     // Catch: java.lang.Throwable -> L14
                com.motorola.laptoppanel.service.LaptopPanelController.access$setUiState(r8, r9)     // Catch: java.lang.Throwable -> L14
            L9a:
                java.lang.Object[] r8 = new java.lang.Object[r4]
                r0.d(r5, r2, r8)
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            La2:
                com.motorola.laptoppanel.util.Logger r9 = com.motorola.laptoppanel.util.Logger.INSTANCE
                java.lang.Object[] r0 = new java.lang.Object[r4]
                r9.d(r5, r2, r0)
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.service.LaptopPanelController.C01231.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: Type inference failed for: r1v9, types: [com.motorola.laptoppanel.service.LaptopPanelController$mRecentsAnimationReceiver$1] */
    public LaptopPanelController(Context context, CoroutineScope coroutineScope) {
        context.getClass();
        coroutineScope.getClass();
        this.context = context;
        this.scope = coroutineScope;
        this.motoFM$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.service.LaptopPanelController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return LaptopPanelController.motoFM_delegate$lambda$0(this.f$0);
            }
        });
        this.activePanelsMap = new LinkedHashMap();
        this.isRecentsAnimationRunning = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.uiState = PanelUiState.Hidden.INSTANCE;
        this.floatingIconController$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.service.LaptopPanelController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return LaptopPanelController.floatingIconController_delegate$lambda$2(this.f$0);
            }
        });
        this.mRecentsAnimationReceiver = new BroadcastReceiver() { // from class: com.motorola.laptoppanel.service.LaptopPanelController$mRecentsAnimationReceiver$1
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
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                context2.getClass();
                intent.getClass();
                if (Intrinsics.areEqual(intent.getAction(), "com.android.wm.shell.splitscreen.action.RECENTS_ANIMATION_STATE_CHANGED")) {
                    boolean booleanExtra = intent.getBooleanExtra("EXTRA_STATE_RUNNING", false);
                    this.this$0.isRecentsAnimationRunning().setValue(Boolean.valueOf(booleanExtra));
                    Logger logger = Logger.INSTANCE;
                    logger.d(this.this$0, "Recents animation running: " + booleanExtra, new Object[0]);
                    if (booleanExtra) {
                        return;
                    }
                    if (this.this$0.mPendingDismissForRecents) {
                        logger.d(this.this$0, "Recents animation finished, executing pending dismiss from Controller.", new Object[0]);
                        this.this$0.mPendingDismissForRecents = false;
                        this.this$0.dismissLaptopPanel();
                    }
                    Pair pair = this.this$0.mPendingLaunch;
                    if (pair != null) {
                        LaptopPanelController laptopPanelController = this.this$0;
                        String str = (String) pair.component1();
                        int iIntValue = ((Number) pair.component2()).intValue();
                        logger.d(laptopPanelController, "Recents animation finished, executing pending launch for (" + str + ", " + iIntValue + ").", new Object[0]);
                        laptopPanelController.mPendingLaunch = null;
                        laptopPanelController.launchLaptopPanel(str, iIntValue);
                    }
                }
            }
        };
    }

    private final void accumulateScreenOnTime() {
        if (this.lastVisibleStartTime <= 0) {
            return;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.lastVisibleStartTime;
        if (jElapsedRealtime > 0) {
            LaptopPrefs laptopPrefs = this.laptopPrefs;
            LaptopPrefs laptopPrefs2 = null;
            if (laptopPrefs == null) {
                Intrinsics.throwUninitializedPropertyAccessException("laptopPrefs");
                laptopPrefs = null;
            }
            laptopPrefs.setScreenOnTime(laptopPrefs.getScreenOnTime() + jElapsedRealtime);
            Logger logger = Logger.INSTANCE;
            LaptopPrefs laptopPrefs3 = this.laptopPrefs;
            if (laptopPrefs3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("laptopPrefs");
            } else {
                laptopPrefs2 = laptopPrefs3;
            }
            logger.d(this, "Accumulated SOT: +" + jElapsedRealtime + "ms (Total: " + laptopPrefs2.getScreenOnTime() + "ms)", new Object[0]);
        }
        this.lastVisibleStartTime = 0L;
    }

    private final void cancelDelayedPanelLaunch() {
        Logger.INSTANCE.d(this, "cancelDelayedPanelLaunch: Cancelling pending delayed panel launch", new Object[0]);
        Job job = this.pendingLaunchJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        this.pendingLaunchJob = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkAndUpdatePanelState(String str, int i) {
        Object next;
        boolean zCheckShouldShowPanel = checkShouldShowPanel(str, i);
        Object obj = this.uiState;
        if (obj instanceof PanelUiState.Hidden) {
            if (!zCheckShouldShowPanel || str == null) {
                return;
            }
            setUiState(new PanelUiState.LaunchingDelayed(str, i));
            scheduleDelayedPanelLaunch(str, i);
            return;
        }
        if (obj instanceof PanelUiState.LaunchingDelayed) {
            if (zCheckShouldShowPanel && str != null) {
                PanelUiState.LaunchingDelayed launchingDelayed = (PanelUiState.LaunchingDelayed) obj;
                if (!Intrinsics.areEqual(str, launchingDelayed.getPkg())) {
                    Logger.INSTANCE.d(this, "Switching delayed launch from " + launchingDelayed.getPkg() + " to " + str, new Object[0]);
                    setUiState(new PanelUiState.LaunchingDelayed(str, i));
                    scheduleDelayedPanelLaunch(str, i);
                    return;
                }
            }
            if (zCheckShouldShowPanel) {
                return;
            }
            setUiState(PanelUiState.Hidden.INSTANCE);
            return;
        }
        if (!(obj instanceof PanelUiState.PanelVisible) && !(obj instanceof PanelUiState.PanelPaused)) {
            if (!(obj instanceof PanelUiState.IconVisible)) {
                throw new NoWhenBranchMatchedException();
            }
            if (zCheckShouldShowPanel) {
                if (str == null) {
                    str = "";
                }
                setUiState(new PanelUiState.IconVisible(str, i));
                return;
            } else {
                setUiState(PanelUiState.Hidden.INSTANCE);
                dismissFloatingIcon();
                releaseOrientationLockIfNeeded();
                return;
            }
        }
        if (zCheckShouldShowPanel) {
            if (!isTaskFullScreen(i)) {
                if (str == null) {
                    str = "";
                }
                setUiState(new PanelUiState.PanelVisible(str, i));
                return;
            } else {
                Logger.INSTANCE.d(this, "App is full screen, relaunching panel to restore split-screen.", new Object[0]);
                setUiState(new PanelUiState.LaunchingDelayed(str == null ? "" : str, i));
                if (str == null) {
                    str = "";
                }
                launchLaptopPanel(str, i);
                return;
            }
        }
        boolean z = obj instanceof TopAppDataProvider;
        TopAppDataProvider topAppDataProvider = z ? (TopAppDataProvider) obj : null;
        String pkg = topAppDataProvider != null ? topAppDataProvider.getPkg() : null;
        TopAppDataProvider topAppDataProvider2 = z ? (TopAppDataProvider) obj : null;
        Integer numValueOf = topAppDataProvider2 != null ? Integer.valueOf(topAppDataProvider2.getTaskId()) : null;
        if (pkg == null || numValueOf == null) {
            Logger.INSTANCE.w(this, "No valid package/taskId in currentState to target dismissal. Performing general dismiss.", new Object[0]);
            dismissLaptopPanel();
        } else {
            Iterator it = this.activePanelsMap.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (Intrinsics.areEqual(((Map.Entry) next).getValue(), new Pair(pkg, numValueOf))) {
                        break;
                    }
                }
            }
            Map.Entry entry = (Map.Entry) next;
            Integer num = entry != null ? (Integer) entry.getKey() : null;
            if (num != null) {
                Logger.INSTANCE.d(this, "Dismissing specific panel for (" + pkg + ", " + numValueOf + ") with activityId " + num + ".", new Object[0]);
                dismissLaptopPanelInstance(num.intValue());
            } else {
                Logger.INSTANCE.w(this, "Could not find activityId for (" + pkg + ", " + numValueOf + ") to dismiss. Performing general dismiss.", new Object[0]);
                dismissLaptopPanel();
            }
        }
        setUiState(PanelUiState.Hidden.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkLaptopMode() {
        boolean zIsLaptopModeActive = isLaptopModeActive();
        Logger logger = Logger.INSTANCE;
        PanelAppsManager panelAppsManager = this.appsManager;
        DisplayRotationMonitor displayRotationMonitor = null;
        if (panelAppsManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appsManager");
            panelAppsManager = null;
        }
        Object value = panelAppsManager.isLaptopPanelEnabled().getValue();
        PostureMonitor postureMonitor = this.postureMonitor;
        if (postureMonitor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("postureMonitor");
            postureMonitor = null;
        }
        Object value2 = postureMonitor.getPosture().getValue();
        DisplayRotationMonitor displayRotationMonitor2 = this.displayRotationMonitor;
        if (displayRotationMonitor2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("displayRotationMonitor");
        } else {
            displayRotationMonitor = displayRotationMonitor2;
        }
        logger.d(this, "checkLaptopMode: isActive=" + zIsLaptopModeActive + " (enabled=" + value + ", posture=" + value2 + ", landscape=" + displayRotationMonitor.isLandscape().getValue() + ", uiState=" + this.uiState + ")", new Object[0]);
        onLaptopModeActiveChanged(zIsLaptopModeActive);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean checkShouldShowPanel(java.lang.String r12, int r13) {
        /*
            Method dump skipped, instruction units count: 279
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.service.LaptopPanelController.checkShouldShowPanel(java.lang.String, int):boolean");
    }

    private final synchronized void disableTopAppMonitoring() {
        try {
            Logger.INSTANCE.d(this, "Disable TopApp Monitoring", new Object[0]);
            TopAppMonitor topAppMonitor = this.topAppMonitor;
            if (topAppMonitor != null) {
                topAppMonitor.stop();
            }
            this.topAppMonitor = null;
        } catch (Throwable th) {
            throw th;
        }
    }

    private final void dismissFloatingIcon() {
        getFloatingIconController().remove();
        Logger.INSTANCE.d(this, "Floating icon has been removed.", new Object[0]);
    }

    private final void dismissLaptopPanelInstance(int i) {
        Logger.INSTANCE.d(this, "dismissLaptopPanelInstance: targetActivityId=" + i + ", uiState=" + this.uiState, new Object[0]);
        Intent intentPutExtra = new Intent(LaptopPanelActivity.ACTION_DISMISS_PANEL).setPackage(this.context.getPackageName()).putExtra(LaptopPanelActivity.EXTRA_TARGET_ACTIVITY_ID, i);
        intentPutExtra.getClass();
        this.context.sendBroadcast(intentPutExtra);
    }

    private final synchronized void enableTopAppMonitoring() {
        Logger.INSTANCE.d(this, "Enable TopApp Monitoring", new Object[0]);
        if (this.topAppMonitor != null) {
            return;
        }
        TopAppMonitor topAppMonitor = new TopAppMonitor(this.context, this.scope, new Function2() { // from class: com.motorola.laptoppanel.service.LaptopPanelController$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return LaptopPanelController.enableTopAppMonitoring$lambda$3(this.f$0, (String) obj, ((Integer) obj2).intValue());
            }
        });
        this.topAppMonitor = topAppMonitor;
        topAppMonitor.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit enableTopAppMonitoring$lambda$3(LaptopPanelController laptopPanelController, String str, int i) {
        str.getClass();
        laptopPanelController.onTopAppChanged(str, i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FloatingIconController floatingIconController_delegate$lambda$2(final LaptopPanelController laptopPanelController) {
        Logger.INSTANCE.d(laptopPanelController, "Creating FloatingIconController for the first time.", new Object[0]);
        Object systemService = laptopPanelController.context.getSystemService("display");
        systemService.getClass();
        Context contextCreateWindowContext = laptopPanelController.context.createDisplayContext(((DisplayManager) systemService).getDisplay(0)).createWindowContext(MotoWindowManager.LayoutParams.getConstantInt_TYPE_MOTO_TRUSTED_APPS_OVERLAY(), null);
        contextCreateWindowContext.getClass();
        return new FloatingIconController(contextCreateWindowContext, new Function0() { // from class: com.motorola.laptoppanel.service.LaptopPanelController$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return LaptopPanelController.floatingIconController_delegate$lambda$2$lambda$1(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit floatingIconController_delegate$lambda$2$lambda$1(LaptopPanelController laptopPanelController) {
        laptopPanelController.onFloatingIconClicked();
        return Unit.INSTANCE;
    }

    private final FloatingIconController getFloatingIconController() {
        return (FloatingIconController) this.floatingIconController$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MotoFoldManager getMotoFM() {
        return (MotoFoldManager) this.motoFM$delegate.getValue();
    }

    private final ActivityManager.RunningTaskInfo getRunningTaskInfo(int i) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        Object next;
        if (i < 0) {
            return null;
        }
        try {
            Object systemService = this.context.getSystemService("activity");
            systemService.getClass();
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) systemService).getRunningTasks(20);
            if (runningTasks != null) {
                Iterator<T> it = runningTasks.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    if (((ActivityManager.RunningTaskInfo) next).taskId == i) {
                        break;
                    }
                }
                runningTaskInfo = (ActivityManager.RunningTaskInfo) next;
            } else {
                runningTaskInfo = null;
            }
            if (runningTaskInfo == null) {
                Logger.INSTANCE.d(this, "getRunningTaskInfo: No task found with taskId=" + i + ".", new Object[0]);
            }
            return runningTaskInfo;
        } catch (SecurityException e) {
            Logger.INSTANCE.e(this, e, "getRunningTaskInfo: Permission denied. REAL_GET_TASKS is required.", new Object[0]);
            return null;
        } catch (Exception e2) {
            Logger.INSTANCE.e(this, e2, "getRunningTaskInfo: An unexpected error occurred.", new Object[0]);
            return null;
        }
    }

    private final void hideFloatingIcon() {
        getFloatingIconController().hide();
        Logger.INSTANCE.d(this, "Floating icon is now hidden.", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isCurrentRotationLandscape() {
        Object systemService = this.context.getSystemService("display");
        systemService.getClass();
        Display display = ((DisplayManager) systemService).getDisplay(0);
        int rotation = display != null ? display.getRotation() : 0;
        return rotation == 1 || rotation == 3;
    }

    private final boolean isLaptopModeActive() {
        DisplayRotationMonitor displayRotationMonitor = this.displayRotationMonitor;
        PanelAppsManager panelAppsManager = null;
        if (displayRotationMonitor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("displayRotationMonitor");
            displayRotationMonitor = null;
        }
        boolean z = isPanelLocked() || ((Boolean) displayRotationMonitor.isLandscape().getValue()).booleanValue();
        PanelAppsManager panelAppsManager2 = this.appsManager;
        if (panelAppsManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appsManager");
        } else {
            panelAppsManager = panelAppsManager2;
        }
        return ((Boolean) panelAppsManager.isLaptopPanelEnabled().getValue()).booleanValue() && isLaptopPanelPosture() && z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isLaptopPanelPosture() {
        PostureMonitor postureMonitor = this.postureMonitor;
        if (postureMonitor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("postureMonitor");
            postureMonitor = null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[((Posture) postureMonitor.getPosture().getValue()).ordinal()];
        if (i == 1) {
            return true;
        }
        if (i != 2) {
            return false;
        }
        return isPanelLocked();
    }

    private final boolean isPanelActivityVisible() {
        PanelUiState panelUiState = this.uiState;
        boolean z = panelUiState instanceof PanelUiState.PanelVisible;
        Logger.INSTANCE.d(this, "isPanelActivityVisible: returning state isVisible=" + z + " (uiState=" + panelUiState + ")", new Object[0]);
        return z;
    }

    private final boolean isPanelLocked() {
        PanelUiState panelUiState = this.uiState;
        return (panelUiState instanceof PanelUiState.PanelActive) || (panelUiState instanceof PanelUiState.IconVisible);
    }

    private final boolean isTaskFullScreen(int i) {
        ActivityManager.RunningTaskInfo runningTaskInfo = getRunningTaskInfo(i);
        if (runningTaskInfo != null) {
            return isTaskFullScreen(runningTaskInfo);
        }
        return false;
    }

    private final boolean isTaskFullScreen(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo == null) {
            Logger.INSTANCE.w(this, "isTopAppFullScreen: Received null taskInfo.", new Object[0]);
            return false;
        }
        int windowingMode = runningTaskInfo.configuration.windowConfiguration.getWindowingMode();
        boolean z = 1 == windowingMode;
        Logger logger = Logger.INSTANCE;
        int i = runningTaskInfo.taskId;
        ComponentName componentName = runningTaskInfo.topActivity;
        String packageName = componentName != null ? componentName.getPackageName() : null;
        logger.d(this, "isTopTaskFullScreen -> " + z + " | taskId=" + i + " pkg=" + packageName + " windowingMode=" + windowingModeToString(windowingMode), new Object[0]);
        return z;
    }

    private final boolean isTransientTask(int i) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        if (i < 0 || (runningTaskInfo = getRunningTaskInfo(i)) == null) {
            return false;
        }
        Intent intent = runningTaskInfo.baseIntent;
        String action = intent != null ? intent.getAction() : null;
        boolean z = Intrinsics.areEqual(action, "android.intent.action.SEND") || Intrinsics.areEqual(action, "android.intent.action.CHOOSER");
        if (z) {
            Logger.INSTANCE.d(this, "Task " + i + " is a transient task with action: " + action, new Object[0]);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MotoFoldManager motoFM_delegate$lambda$0(LaptopPanelController laptopPanelController) {
        return MotoFoldManager.getInstance(laptopPanelController.context);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void recordInteractedApp(java.lang.String r14) {
        /*
            r13 = this;
            int r0 = r14.length()
            if (r0 != 0) goto L7
            return
        L7:
            com.motorola.laptoppanel.util.LaptopPrefs r0 = r13.laptopPrefs
            r1 = 0
            java.lang.String r2 = "laptopPrefs"
            if (r0 != 0) goto L12
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            r0 = r1
        L12:
            java.lang.String r3 = r0.getInteractedApps()
            if (r3 == 0) goto L4e
            java.lang.String r0 = ","
            java.lang.String[] r4 = new java.lang.String[]{r0}
            r7 = 6
            r8 = 0
            r5 = 0
            r6 = 0
            java.util.List r0 = kotlin.text.StringsKt.split$default(r3, r4, r5, r6, r7, r8)
            if (r0 == 0) goto L4e
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.Iterator r0 = r0.iterator()
        L31:
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto L48
            java.lang.Object r4 = r0.next()
            r5 = r4
            java.lang.String r5 = (java.lang.String) r5
            boolean r5 = kotlin.text.StringsKt.isBlank(r5)
            if (r5 != 0) goto L31
            r3.add(r4)
            goto L31
        L48:
            java.util.Set r0 = kotlin.collections.CollectionsKt.toMutableSet(r3)
            if (r0 != 0) goto L53
        L4e:
            java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
            r0.<init>()
        L53:
            boolean r3 = r0.add(r14)
            if (r3 == 0) goto L97
            r4 = r0
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            r11 = 62
            r12 = 0
            java.lang.String r5 = ","
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            java.lang.String r0 = kotlin.collections.CollectionsKt.joinToString$default(r4, r5, r6, r7, r8, r9, r10, r11, r12)
            com.motorola.laptoppanel.util.LaptopPrefs r3 = r13.laptopPrefs
            if (r3 != 0) goto L72
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            goto L73
        L72:
            r1 = r3
        L73:
            r1.setInteractedApps(r0)
            com.motorola.laptoppanel.util.Logger r1 = com.motorola.laptoppanel.util.Logger.INSTANCE
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Recorded interacted app: "
            r2.append(r3)
            r2.append(r14)
            java.lang.String r14 = ". Total list: "
            r2.append(r14)
            r2.append(r0)
            java.lang.String r14 = r2.toString()
            r0 = 0
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r1.d(r13, r14, r0)
        L97:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.service.LaptopPanelController.recordInteractedApp(java.lang.String):void");
    }

    private final void releaseOrientationLockIfNeeded() {
        if (this.isOrientationForced) {
            getMotoFM().releaseLandscapeOrientation();
            this.isOrientationForced = false;
            Logger.INSTANCE.i(this, "Released orientation lock.", new Object[0]);
        }
    }

    private final void scheduleDelayedPanelLaunch(String str, int i) {
        Job job = this.pendingLaunchJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        Logger.INSTANCE.d(this, "scheduleDelayedPanelLaunch: Scheduling delayed panel launch for package: " + str, new Object[0]);
        this.pendingLaunchJob = BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01231(str, i, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setUiState(PanelUiState panelUiState) {
        PanelUiState panelUiState2 = this.uiState;
        if (Intrinsics.areEqual(panelUiState2, panelUiState)) {
            return;
        }
        if (panelUiState2 instanceof PanelUiState.PanelVisible) {
            accumulateScreenOnTime();
            recordInteractedApp(((PanelUiState.PanelVisible) panelUiState2).getPkg());
        }
        if (panelUiState instanceof PanelUiState.PanelVisible) {
            this.lastVisibleStartTime = SystemClock.elapsedRealtime();
        }
        Logger logger = Logger.INSTANCE;
        logger.d(this, "UI State Transition: " + panelUiState2 + " -> " + panelUiState, new Object[0]);
        this.uiState = panelUiState;
        if (panelUiState instanceof PanelUiState.Hidden) {
            logger.d(this, "Clean up app context", new Object[0]);
            cancelDelayedPanelLaunch();
        }
    }

    private final void showFloatingIcon() {
        getFloatingIconController().show();
        Logger.INSTANCE.d(this, "Floating icon is now shown.", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair snapshotTop() {
        ComponentName componentName;
        ActivityManager.RunningTaskInfo topRunningTask = TaskUtilsKt.getTopRunningTask(this.context);
        return new Pair((topRunningTask == null || (componentName = topRunningTask.topActivity) == null) ? null : componentName.getPackageName(), Integer.valueOf(topRunningTask != null ? topRunningTask.taskId : -1));
    }

    private final String windowingModeToString(int i) {
        if (i == 0) {
            return "UNDEFINED";
        }
        if (i == 1) {
            return "FULLSCREEN";
        }
        if (i == 2) {
            return "PINNED";
        }
        if (i == 5) {
            return "FREEFORM";
        }
        if (i == 6) {
            return "MULTI_WINDOW";
        }
        return "UNKNOWN (" + i + ")";
    }

    public final void dismissLaptopPanel() {
        if (((Boolean) this.isRecentsAnimationRunning.getValue()).booleanValue()) {
            Logger.INSTANCE.i(this, "Request to dismiss panel deferred due to ongoing Recents animation.", new Object[0]);
            this.mPendingDismissForRecents = true;
            return;
        }
        Logger.INSTANCE.i(this, "dismissLaptopPanel, uiState=" + this.uiState, new Object[0]);
        Intent intent = new Intent(LaptopPanelActivity.ACTION_DISMISS_PANEL).setPackage(this.context.getPackageName());
        intent.getClass();
        this.context.sendBroadcast(intent);
        releaseOrientationLockIfNeeded();
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        fileDescriptor.getClass();
        printWriter.getClass();
        strArr.getClass();
        printWriter.println("LaptopPanelController State:");
        printWriter.println("--------------------------------------------------");
        printWriter.println("  uiState: " + this.uiState);
        printWriter.println("  isLaptopPanelPosture (Computed): " + isLaptopPanelPosture());
        printWriter.println("  isLaptopModeActive (Computed): " + isLaptopModeActive());
        printWriter.println("  isOrientationForced: " + this.isOrientationForced);
        printWriter.println("  isRecentsAnimationRunning: " + this.isRecentsAnimationRunning.getValue());
        printWriter.println("  mPendingDismissForRecents: " + this.mPendingDismissForRecents);
        printWriter.println("  mPendingLaunch: " + this.mPendingLaunch);
        printWriter.println("  activePanelsMap (size=" + this.activePanelsMap.size() + "): " + this.activePanelsMap);
        long j = this.lastVisibleStartTime;
        StringBuilder sb = new StringBuilder();
        sb.append("  lastVisibleStartTime: ");
        sb.append(j);
        printWriter.println(sb.toString());
        printWriter.println();
        PanelAppsManager panelAppsManager = this.appsManager;
        DisplayRotationMonitor displayRotationMonitor = null;
        if (panelAppsManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appsManager");
            panelAppsManager = null;
        }
        panelAppsManager.dump(fileDescriptor, printWriter, strArr);
        PostureMonitor postureMonitor = this.postureMonitor;
        if (postureMonitor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("postureMonitor");
            postureMonitor = null;
        }
        postureMonitor.dump(printWriter);
        DisplayRotationMonitor displayRotationMonitor2 = this.displayRotationMonitor;
        if (displayRotationMonitor2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("displayRotationMonitor");
        } else {
            displayRotationMonitor = displayRotationMonitor2;
        }
        displayRotationMonitor.dump(printWriter);
        printWriter.println("--------------------------------------------------");
    }

    public final String getCurrentTopAppPackage() {
        Object obj = this.uiState;
        TopAppDataProvider topAppDataProvider = obj instanceof TopAppDataProvider ? (TopAppDataProvider) obj : null;
        if (topAppDataProvider != null) {
            return topAppDataProvider.getPkg();
        }
        return null;
    }

    public final int getCurrentTopAppTaskId() {
        Object obj = this.uiState;
        TopAppDataProvider topAppDataProvider = obj instanceof TopAppDataProvider ? (TopAppDataProvider) obj : null;
        if (topAppDataProvider != null) {
            return topAppDataProvider.getTaskId();
        }
        return -1;
    }

    public final void init() {
        Logger logger = Logger.INSTANCE;
        logger.d(this, "Initializing LaptopPanelController", new Object[0]);
        this.laptopPrefs = new LaptopPrefs(this.context);
        this.appsManager = new PanelAppsManager(this.context, this.scope);
        this.postureMonitor = new PostureMonitor(this.context, this.scope);
        DisplayRotationMonitor displayRotationMonitor = new DisplayRotationMonitor(this.context, this.scope);
        this.displayRotationMonitor = displayRotationMonitor;
        displayRotationMonitor.start();
        PanelAppsManager panelAppsManager = this.appsManager;
        if (panelAppsManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appsManager");
            panelAppsManager = null;
        }
        StateFlow stateFlowIsLaptopPanelEnabled = panelAppsManager.isLaptopPanelEnabled();
        PostureMonitor postureMonitor = this.postureMonitor;
        if (postureMonitor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("postureMonitor");
            postureMonitor = null;
        }
        FlowKt.launchIn(FlowKt.combine(stateFlowIsLaptopPanelEnabled, postureMonitor.getPosture(), new AnonymousClass1(null)), this.scope);
        DisplayRotationMonitor displayRotationMonitor2 = this.displayRotationMonitor;
        if (displayRotationMonitor2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("displayRotationMonitor");
            displayRotationMonitor2 = null;
        }
        FlowKt.launchIn(FlowKt.onEach(displayRotationMonitor2.isLandscape(), new AnonymousClass2(null)), this.scope);
        PanelAppsManager panelAppsManager2 = this.appsManager;
        if (panelAppsManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appsManager");
            panelAppsManager2 = null;
        }
        FlowKt.launchIn(FlowKt.onEach(panelAppsManager2.getDisabledAppsChanged(), new AnonymousClass3(null)), this.scope);
        this.context.registerReceiver(this.mRecentsAnimationReceiver, new IntentFilter("com.android.wm.shell.splitscreen.action.RECENTS_ANIMATION_STATE_CHANGED"), "com.motorola.permission.LAPTOP_PANEL_BROADCAST", null, 2);
        logger.d(this, "LaptopPanelController created and listeners are active", new Object[0]);
    }

    public final MutableStateFlow isRecentsAnimationRunning() {
        return this.isRecentsAnimationRunning;
    }

    public final void launchLaptopPanel(String str, int i) {
        str.getClass();
        PanelUiState panelUiState = this.uiState;
        Logger logger = Logger.INSTANCE;
        logger.d(this, "launchLaptopPanel: Entry - uiState=" + panelUiState + " for (" + str + ", " + i + ")", new Object[0]);
        if (!(panelUiState instanceof PanelUiState.PanelActive) && !(panelUiState instanceof PanelUiState.LaunchingDelayed)) {
            logger.d(this, "Skipped for the incorrect uiState: " + panelUiState, new Object[0]);
            return;
        }
        if (!((Boolean) this.isRecentsAnimationRunning.getValue()).booleanValue()) {
            BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01221(str, i, null), 3, null);
            return;
        }
        logger.i(this, "Request to launch panel for (" + str + ", " + i + ") deferred due to ongoing Recents animation.", new Object[0]);
        this.mPendingLaunch = new Pair(str, Integer.valueOf(i));
    }

    public final void launchLaptopPanelImmediately(String str, int i) {
        str.getClass();
        cancelDelayedPanelLaunch();
        launchLaptopPanel(str, i);
    }

    public final void onDestroy() {
        Logger.INSTANCE.d(this, "onDestroy: Destroying LaptopPanelController, uiState=" + this.uiState, new Object[0]);
        cancelDelayedPanelLaunch();
        PanelUiState panelUiState = this.uiState;
        if (panelUiState instanceof PanelUiState.PanelVisible) {
            accumulateScreenOnTime();
            recordInteractedApp(((PanelUiState.PanelVisible) panelUiState).getPkg());
        }
        this.context.unregisterReceiver(this.mRecentsAnimationReceiver);
        disableTopAppMonitoring();
        PanelAppsManager panelAppsManager = this.appsManager;
        DisplayRotationMonitor displayRotationMonitor = null;
        if (panelAppsManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appsManager");
            panelAppsManager = null;
        }
        panelAppsManager.destroy();
        DisplayRotationMonitor displayRotationMonitor2 = this.displayRotationMonitor;
        if (displayRotationMonitor2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("displayRotationMonitor");
        } else {
            displayRotationMonitor = displayRotationMonitor2;
        }
        displayRotationMonitor.stop();
        releaseOrientationLockIfNeeded();
        setUiState(PanelUiState.Hidden.INSTANCE);
        dismissLaptopPanel();
        dismissFloatingIcon();
    }

    public final void onFloatingIconClicked() {
        Logger.INSTANCE.d(this, "onFloatingIconClicked: uiState=" + this.uiState, new Object[0]);
        PanelUiState panelUiState = this.uiState;
        if (panelUiState instanceof PanelUiState.IconVisible) {
            PanelUiState.IconVisible iconVisible = (PanelUiState.IconVisible) panelUiState;
            setUiState(new PanelUiState.PanelVisible(iconVisible.getPkg(), iconVisible.getTaskId()));
            hideFloatingIcon();
            launchLaptopPanelImmediately(iconVisible.getPkg(), iconVisible.getTaskId());
        }
    }

    public final void onLaptopModeActiveChanged(boolean z) {
        if (z) {
            enableTopAppMonitoring();
            Pair pairSnapshotTop = snapshotTop();
            checkAndUpdatePanelState((String) pairSnapshotTop.component1(), ((Number) pairSnapshotTop.component2()).intValue());
        } else {
            disableTopAppMonitoring();
            setUiState(PanelUiState.Hidden.INSTANCE);
            dismissLaptopPanel();
            dismissFloatingIcon();
            cancelDelayedPanelLaunch();
        }
    }

    public final void onNavigationAway() {
        Logger.INSTANCE.d(this, "onNavigatingAway: uiState=" + this.uiState + ", Hiding all UI.", new Object[0]);
        setUiState(PanelUiState.Hidden.INSTANCE);
        dismissLaptopPanel();
        dismissFloatingIcon();
    }

    public final void onPanelDismissed(int i) {
        Logger logger = Logger.INSTANCE;
        logger.d(this, "onPanelDismissed: activityId=" + i + ", uiState=" + this.uiState, new Object[0]);
        if (this.activePanelsMap.remove(Integer.valueOf(i)) != null) {
            logger.d(this, "Panel instance " + i + " dismissed. Active panels: " + this.activePanelsMap.keySet(), new Object[0]);
        } else {
            logger.d(this, "Panel instance " + i + " not found in active panels (already removed or stale).", new Object[0]);
        }
        if (this.activePanelsMap.isEmpty() && (this.uiState instanceof PanelUiState.PanelActive)) {
            setUiState(PanelUiState.Hidden.INSTANCE);
        }
    }

    public final void onPanelDismissedByUser() {
        Logger.INSTANCE.d(this, "onPanelDismissedByUser: uiState=" + this.uiState, new Object[0]);
        Object obj = this.uiState;
        if (!(obj instanceof PanelUiState.PanelActive)) {
            setUiState(PanelUiState.Hidden.INSTANCE);
            dismissFloatingIcon();
        } else {
            PanelUiState.PanelActive panelActive = (PanelUiState.PanelActive) obj;
            setUiState(new PanelUiState.IconVisible(panelActive.getPkg(), panelActive.getTaskId()));
            showFloatingIcon();
        }
    }

    public final void onTopAppChanged(String str, int i) {
        Logger logger = Logger.INSTANCE;
        logger.d(this, "onTopAppChanged: package:" + str + ", taskId:" + i + ", uiState:" + this.uiState, new Object[0]);
        if (str == null || str.length() == 0 || i < 0) {
            return;
        }
        if (isTransientTask(i)) {
            logger.d(this, "Detected transient task (" + str + "), ignoring and keeping panel stable.", new Object[0]);
            return;
        }
        PanelAppsManager panelAppsManager = null;
        if (this.uiState instanceof PanelUiState.PanelActive) {
            PanelAppsManager panelAppsManager2 = this.appsManager;
            if (panelAppsManager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appsManager");
                panelAppsManager2 = null;
            }
            if (!panelAppsManager2.isAllowed(str)) {
                logger.d(this, "Top app changed to unallowed app (" + str + "). Keeping panel active for previous app.", new Object[0]);
                return;
            }
        }
        String currentTopAppPackage = getCurrentTopAppPackage();
        int currentTopAppTaskId = getCurrentTopAppTaskId();
        if (Intrinsics.areEqual(str, currentTopAppPackage) && i == currentTopAppTaskId) {
            checkAndUpdatePanelState(str, i);
            return;
        }
        if (Intrinsics.areEqual(str, this.context.getPackageName())) {
            if (this.uiState instanceof PanelUiState.PanelActive) {
                if (!isTaskFullScreen(i)) {
                    logger.d(this, "onTopAppChanged: Panel became top-app in multi-window mode. This is normal during launch. Ignoring.", new Object[0]);
                    return;
                }
                logger.w(this, "onTopAppChanged: Panel became top-app AND full-screen. This is an orphan state. Correcting to Hidden.", new Object[0]);
                setUiState(PanelUiState.Hidden.INSTANCE);
                dismissLaptopPanel();
                return;
            }
            return;
        }
        PanelAppsManager panelAppsManager3 = this.appsManager;
        if (panelAppsManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appsManager");
        } else {
            panelAppsManager = panelAppsManager3;
        }
        if (panelAppsManager.isHomeOrRecents(str) && (this.uiState instanceof PanelUiState.PanelActive)) {
            logger.d(this, "releasing orientation lock for Home/Recents", new Object[0]);
            releaseOrientationLockIfNeeded();
        } else {
            if (!(this.uiState instanceof PanelUiState.PanelActive) || !checkShouldShowPanel(str, i)) {
                checkAndUpdatePanelState(str, i);
                return;
            }
            logger.d(this, "onTopAppChanged: Switching panel to new app: " + str, new Object[0]);
            setUiState(new PanelUiState.LaunchingDelayed(str, i));
            scheduleDelayedPanelLaunch(str, i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x00ba, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(new kotlin.Pair(r5.getPkg(), java.lang.Integer.valueOf(r5.getTaskId())), r8) != false) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0268  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void setPanelVisible(boolean r8, int r9, java.lang.String r10, int r11) {
        /*
            Method dump skipped, instruction units count: 650
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.service.LaptopPanelController.setPanelVisible(boolean, int, java.lang.String, int):void");
    }
}
