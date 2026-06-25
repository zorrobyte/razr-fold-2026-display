package com.motorola.laptoppanel.checkin;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import com.motorola.laptoppanel.util.Logger;
import java.util.Calendar;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.SupervisorKt;

/* JADX INFO: compiled from: LaptopPanelCheckinService.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LaptopPanelCheckinService extends JobService {
    private final CoroutineScope serviceScope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob$default(null, 1, null).plus(Dispatchers.getIO()));
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: LaptopPanelCheckinService.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void schedule$default(Companion companion, Context context, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            companion.schedule(context, z);
        }

        public final void schedule(Context context, boolean z) {
            context.getClass();
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
            if (jobScheduler == null) {
                return;
            }
            if (z || jobScheduler.getPendingJob(1) == null) {
                ComponentName componentName = new ComponentName(context, (Class<?>) LaptopPanelCheckinService.class);
                Calendar calendar = Calendar.getInstance();
                calendar.add(6, 1);
                calendar.set(11, 0);
                calendar.set(12, 0);
                calendar.set(13, 0);
                calendar.set(14, 0);
                long timeInMillis = calendar.getTimeInMillis() - System.currentTimeMillis();
                jobScheduler.schedule(new JobInfo.Builder(1, componentName).setMinimumLatency(timeInMillis).setPersisted(true).build());
                Logger.INSTANCE.d("LaptopPanelCheckinService", "Check-in job scheduled for midnight (delay: " + (timeInMillis / ((long) 1000)) + "s)", new Object[0]);
            }
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.checkin.LaptopPanelCheckinService$onStartJob$1, reason: invalid class name */
    /* JADX INFO: compiled from: LaptopPanelCheckinService.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ JobParameters $params;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(JobParameters jobParameters, Continuation continuation) {
            super(2, continuation);
            this.$params = jobParameters;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LaptopPanelCheckinService.this.new AnonymousClass1(this.$params, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v4, types: [android.app.job.JobParameters] */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
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
            try {
                try {
                    DailyStatsCheckin dailyStatsCheckin = new DailyStatsCheckin();
                    Context applicationContext = LaptopPanelCheckinService.this.getApplicationContext();
                    applicationContext.getClass();
                    dailyStatsCheckin.collectAndPublish(applicationContext);
                } catch (Exception e) {
                    Logger.INSTANCE.e("LaptopPanelCheckinService", (Throwable) e, "Error during check-in", new Object[0]);
                }
                return Unit.INSTANCE;
            } finally {
                LaptopPanelCheckinService.this.jobFinished(this.$params, false);
            }
        }
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        jobParameters.getClass();
        Logger.INSTANCE.d("LaptopPanelCheckinService", "onStartJob: Starting check-in task", new Object[0]);
        Companion companion = Companion;
        Context applicationContext = getApplicationContext();
        applicationContext.getClass();
        companion.schedule(applicationContext, true);
        BuildersKt__Builders_commonKt.launch$default(this.serviceScope, null, null, new AnonymousClass1(jobParameters, null), 3, null);
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        jobParameters.getClass();
        return false;
    }
}
