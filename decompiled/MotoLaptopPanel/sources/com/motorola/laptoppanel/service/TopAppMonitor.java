package com.motorola.laptoppanel.service;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import com.motorola.laptoppanel.service.TopAppMonitor;
import com.motorola.laptoppanel.util.Logger;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import motorola.core_services.activity.MotoActivityTaskManager;

/* JADX INFO: compiled from: TopAppMonitor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TopAppMonitor {
    private final Function2 callback;
    private final Context context;
    private Job monitoringJob;
    private final Lazy motoAtm$delegate;
    private final CoroutineScope scope;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final Set IGNORED_PACKAGES = SetsKt.setOf((Object[]) new String[]{"com.motorola.mobiledesktop", "com.android.settings", "com.android.systemui", "com.android.intentresolver", "com.google.android.permissioncontroller", "com.motorola.screenshoteditor"});

    /* JADX INFO: compiled from: TopAppMonitor.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: TopAppMonitor.kt */
    final class TaskEvent {
        private final boolean isSelf;
        private final int taskId;

        public TaskEvent(int i, boolean z) {
            this.taskId = i;
            this.isSelf = z;
        }

        public final int component1() {
            return this.taskId;
        }

        public final boolean component2() {
            return this.isSelf;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TaskEvent)) {
                return false;
            }
            TaskEvent taskEvent = (TaskEvent) obj;
            return this.taskId == taskEvent.taskId && this.isSelf == taskEvent.isSelf;
        }

        public int hashCode() {
            return (Integer.hashCode(this.taskId) * 31) + Boolean.hashCode(this.isSelf);
        }

        public final boolean isSelf() {
            return this.isSelf;
        }

        public String toString() {
            return "TaskEvent(taskId=" + this.taskId + ", isSelf=" + this.isSelf + ")";
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$3, reason: invalid class name */
    /* JADX INFO: compiled from: TopAppMonitor.kt */
    final class AnonymousClass3 extends SuspendLambda implements Function3 {
        int I$0;
        int I$1;
        int I$2;
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        boolean Z$0;
        int label;

        AnonymousClass3(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(FlowCollector flowCollector, TaskEvent taskEvent, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = TopAppMonitor.this.new AnonymousClass3(continuation);
            anonymousClass3.L$0 = flowCollector;
            anonymousClass3.L$1 = taskEvent;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x0094, code lost:
        
            if (r9.emit(r14, r13) == r0) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x00ee, code lost:
        
            if (kotlinx.coroutines.DelayKt.delay(200, r13) == r0) goto L32;
         */
        /* JADX WARN: Path cross not found for [B:18:0x005b, B:25:0x009a], limit reached: 37 */
        /* JADX WARN: Removed duplicated region for block: B:27:0x009e  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x00dc  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00f3  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00f7  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x00da -> B:33:0x00f1). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x00ee -> B:33:0x00f1). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r14) {
            /*
                Method dump skipped, instruction units count: 289
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.service.TopAppMonitor.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.service.TopAppMonitor$start$1, reason: invalid class name */
    /* JADX INFO: compiled from: TopAppMonitor.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = TopAppMonitor.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ActivityManager.RunningTaskInfo runningTaskInfo, Continuation continuation) {
            return ((AnonymousClass1) create(runningTaskInfo, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            String packageName;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.L$0;
            Logger logger = Logger.INSTANCE;
            ComponentName componentName = runningTaskInfo.topActivity;
            String packageName2 = componentName != null ? componentName.getPackageName() : null;
            logger.d("TopAppMonitor", "notify pkg=" + packageName2 + ", taskId=" + runningTaskInfo.taskId, new Object[0]);
            ComponentName componentName2 = runningTaskInfo.topActivity;
            if (componentName2 != null && (packageName = componentName2.getPackageName()) != null) {
                TopAppMonitor.this.callback.invoke(packageName, Boxing.boxInt(runningTaskInfo.taskId));
            }
            return Unit.INSTANCE;
        }
    }

    public TopAppMonitor(Context context, CoroutineScope coroutineScope, Function2 function2) {
        context.getClass();
        coroutineScope.getClass();
        function2.getClass();
        this.context = context;
        this.scope = coroutineScope;
        this.callback = function2;
        this.motoAtm$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.service.TopAppMonitor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return TopAppMonitor.motoAtm_delegate$lambda$0(this.f$0);
            }
        });
    }

    private final Flow createTopAppFlow() {
        final Flow flowCallbackFlow = FlowKt.callbackFlow(new TopAppMonitor$createTopAppFlow$topActivityFlow$1(this, null));
        final Flow flowFlowOn = FlowKt.flowOn(FlowKt.transformLatest(FlowKt.debounce(FlowKt.flowOn(new Flow() { // from class: com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$map$1

            /* JADX INFO: renamed from: com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$map$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: Emitters.kt */
            public final class AnonymousClass2 implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;
                final /* synthetic */ TopAppMonitor this$0;

                /* JADX INFO: renamed from: com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, TopAppMonitor topAppMonitor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = topAppMonitor;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                    /*
                        r10 = this;
                        boolean r0 = r12 instanceof com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r12
                        com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$map$1$2$1 r0 = (com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$map$1$2$1 r0 = new com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$map$1$2$1
                        r0.<init>(r12)
                    L18:
                        java.lang.Object r12 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L32
                        if (r2 != r3) goto L2a
                        kotlin.ResultKt.throwOnFailure(r12)
                        goto Lbf
                    L2a:
                        java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                        java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                        r10.<init>(r11)
                        throw r10
                    L32:
                        kotlin.ResultKt.throwOnFailure(r12)
                        kotlinx.coroutines.flow.FlowCollector r12 = r10.$this_unsafeFlow
                        java.lang.Number r11 = (java.lang.Number) r11
                        int r11 = r11.intValue()
                        com.motorola.laptoppanel.service.TopAppMonitor r10 = r10.this$0
                        android.content.Context r10 = com.motorola.laptoppanel.service.TopAppMonitor.access$getContext$p(r10)
                        java.lang.String r2 = "activity"
                        java.lang.Object r10 = r10.getSystemService(r2)
                        boolean r2 = r10 instanceof android.app.ActivityManager
                        r4 = 0
                        if (r2 == 0) goto L51
                        android.app.ActivityManager r10 = (android.app.ActivityManager) r10
                        goto L52
                    L51:
                        r10 = r4
                    L52:
                        r2 = 0
                        if (r10 == 0) goto Lb1
                        java.util.List r10 = r10.getAppTasks()
                        if (r10 == 0) goto Lb1
                        boolean r5 = r10.isEmpty()
                        if (r5 == 0) goto L62
                        goto Lb1
                    L62:
                        java.util.Iterator r10 = r10.iterator()
                    L66:
                        boolean r5 = r10.hasNext()
                        if (r5 == 0) goto Lb1
                        java.lang.Object r5 = r10.next()
                        android.app.ActivityManager$AppTask r5 = (android.app.ActivityManager.AppTask) r5
                        android.app.ActivityManager$RecentTaskInfo r6 = r5.getTaskInfo()     // Catch: java.lang.IllegalArgumentException -> L7c
                        int r5 = r6.taskId     // Catch: java.lang.IllegalArgumentException -> L7c
                        if (r5 != r11) goto Lad
                        r5 = r3
                        goto Lae
                    L7c:
                        r6 = move-exception
                        com.motorola.laptoppanel.util.Logger r7 = com.motorola.laptoppanel.util.Logger.INSTANCE
                        android.app.ActivityManager$RecentTaskInfo r5 = r5.getTaskInfo()
                        android.content.Intent r5 = r5.baseIntent
                        if (r5 == 0) goto L92
                        android.content.ComponentName r5 = r5.getComponent()
                        if (r5 == 0) goto L92
                        java.lang.String r5 = r5.getPackageName()
                        goto L93
                    L92:
                        r5 = r4
                    L93:
                        java.lang.StringBuilder r8 = new java.lang.StringBuilder
                        r8.<init>()
                        java.lang.String r9 = "Failed to get taskId for appTask: "
                        r8.append(r9)
                        r8.append(r5)
                        java.lang.String r5 = r8.toString()
                        java.lang.Object[] r6 = new java.lang.Object[]{r6}
                        java.lang.String r8 = "TopAppMonitor"
                        r7.w(r8, r5, r6)
                    Lad:
                        r5 = r2
                    Lae:
                        if (r5 == 0) goto L66
                        r2 = r3
                    Lb1:
                        com.motorola.laptoppanel.service.TopAppMonitor$TaskEvent r10 = new com.motorola.laptoppanel.service.TopAppMonitor$TaskEvent
                        r10.<init>(r11, r2)
                        r0.label = r3
                        java.lang.Object r10 = r12.emit(r10, r0)
                        if (r10 != r1) goto Lbf
                        return r1
                    Lbf:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowCallbackFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        }, Dispatchers.getIO()), new Function1() { // from class: com.motorola.laptoppanel.service.TopAppMonitor$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Long.valueOf(TopAppMonitor.createTopAppFlow$lambda$3((TopAppMonitor.TaskEvent) obj));
            }
        }), new AnonymousClass3(null)), Dispatchers.getIO());
        return new Flow() { // from class: com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$filter$1

            /* JADX INFO: renamed from: com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$filter$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: Emitters.kt */
            public final class AnonymousClass2 implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* JADX INFO: renamed from: com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$filter$1$2$1 r0 = (com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$filter$1$2$1 r0 = new com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L58
                    L29:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L31:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        r6 = r5
                        android.app.ActivityManager$RunningTaskInfo r6 = (android.app.ActivityManager.RunningTaskInfo) r6
                        java.util.Set r2 = com.motorola.laptoppanel.service.TopAppMonitor.access$getIGNORED_PACKAGES$cp()
                        java.lang.Iterable r2 = (java.lang.Iterable) r2
                        android.content.ComponentName r6 = r6.topActivity
                        if (r6 == 0) goto L48
                        java.lang.String r6 = r6.getPackageName()
                        goto L49
                    L48:
                        r6 = 0
                    L49:
                        boolean r6 = kotlin.collections.CollectionsKt.contains(r2, r6)
                        if (r6 != 0) goto L58
                        r0.label = r3
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L58
                        return r1
                    L58:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowFlowOn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final long createTopAppFlow$lambda$3(TaskEvent taskEvent) {
        taskEvent.getClass();
        return taskEvent.isSelf() ? 600L : 300L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MotoActivityTaskManager getMotoAtm() {
        return (MotoActivityTaskManager) this.motoAtm$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MotoActivityTaskManager motoAtm_delegate$lambda$0(TopAppMonitor topAppMonitor) {
        return MotoActivityTaskManager.getInstance(topAppMonitor.context.getApplicationContext());
    }

    public final void start() {
        Job job = this.monitoringJob;
        if (job != null && job.isActive()) {
            Logger.INSTANCE.d("TopAppMonitor", "Monitor is already running.", new Object[0]);
        } else {
            this.monitoringJob = FlowKt.launchIn(FlowKt.onEach(createTopAppFlow(), new AnonymousClass1(null)), this.scope);
            Logger.INSTANCE.d("TopAppMonitor", "TopAppMonitor started", new Object[0]);
        }
    }

    public final void stop() {
        Job job = this.monitoringJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        this.monitoringJob = null;
        Logger.INSTANCE.d("TopAppMonitor", "TopAppMonitor stopped", new Object[0]);
    }
}
