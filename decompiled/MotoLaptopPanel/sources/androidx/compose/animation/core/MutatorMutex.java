package androidx.compose.animation.core;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

/* JADX INFO: compiled from: InternalMutatorMutex.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MutatorMutex {
    private final AtomicReference currentMutator = new AtomicReference(null);
    private final Mutex mutex = MutexKt.Mutex$default(false, 1, null);

    /* JADX INFO: compiled from: InternalMutatorMutex.kt */
    final class Mutator {
        private final Job job;
        private final MutatePriority priority;

        public Mutator(MutatePriority mutatePriority, Job job) {
            this.priority = mutatePriority;
            this.job = job;
        }

        public final boolean canInterrupt(Mutator mutator) {
            return this.priority.compareTo(mutator.priority) >= 0;
        }

        public final void cancel() {
            this.job.cancel(new MutationInterruptedException());
        }
    }

    /* JADX INFO: renamed from: androidx.compose.animation.core.MutatorMutex$mutate$2, reason: invalid class name */
    /* JADX INFO: compiled from: InternalMutatorMutex.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $block;
        final /* synthetic */ MutatePriority $priority;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        final /* synthetic */ MutatorMutex this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(MutatePriority mutatePriority, MutatorMutex mutatorMutex, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$priority = mutatePriority;
            this.this$0 = mutatorMutex;
            this.$block = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$priority, this.this$0, this.$block, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [int, kotlinx.coroutines.sync.Mutex] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Mutator mutator;
            Mutex mutex;
            MutatorMutex mutatorMutex;
            Function1 function1;
            Throwable th;
            Mutator mutator2;
            MutatorMutex mutatorMutex2;
            Mutex mutex2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            ?? r1 = this.label;
            try {
                try {
                    if (r1 == 0) {
                        ResultKt.throwOnFailure(obj);
                        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                        MutatePriority mutatePriority = this.$priority;
                        CoroutineContext.Element element = coroutineScope.getCoroutineContext().get(Job.Key);
                        element.getClass();
                        mutator = new Mutator(mutatePriority, (Job) element);
                        this.this$0.tryMutateOrCancel(mutator);
                        mutex = this.this$0.mutex;
                        Function1 function12 = this.$block;
                        mutatorMutex = this.this$0;
                        this.L$0 = mutator;
                        this.L$1 = mutex;
                        this.L$2 = function12;
                        this.L$3 = mutatorMutex;
                        this.label = 1;
                        if (mutex.lock(null, this) != coroutine_suspended) {
                            function1 = function12;
                        }
                        return coroutine_suspended;
                    }
                    if (r1 != 1) {
                        if (r1 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        mutatorMutex2 = (MutatorMutex) this.L$2;
                        mutex2 = (Mutex) this.L$1;
                        mutator2 = (Mutator) this.L$0;
                        try {
                            ResultKt.throwOnFailure(obj);
                            mutatorMutex2.currentMutator.compareAndSet(mutator2, null);
                            mutex2.unlock(null);
                            return obj;
                        } catch (Throwable th2) {
                            th = th2;
                            mutatorMutex2.currentMutator.compareAndSet(mutator2, null);
                            throw th;
                        }
                    }
                    MutatorMutex mutatorMutex3 = (MutatorMutex) this.L$3;
                    function1 = (Function1) this.L$2;
                    Mutex mutex3 = (Mutex) this.L$1;
                    Mutator mutator3 = (Mutator) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    mutatorMutex = mutatorMutex3;
                    mutator = mutator3;
                    mutex = mutex3;
                    this.L$0 = mutator;
                    this.L$1 = mutex;
                    this.L$2 = mutatorMutex;
                    this.L$3 = null;
                    this.label = 2;
                    Object objInvoke = function1.invoke(this);
                    if (objInvoke != coroutine_suspended) {
                        Mutex mutex4 = mutex;
                        obj = objInvoke;
                        mutator2 = mutator;
                        mutex2 = mutex4;
                        mutatorMutex2 = mutatorMutex;
                        mutatorMutex2.currentMutator.compareAndSet(mutator2, null);
                        mutex2.unlock(null);
                        return obj;
                    }
                    return coroutine_suspended;
                } catch (Throwable th3) {
                    th = th3;
                    mutator2 = mutator;
                    mutatorMutex2 = mutatorMutex;
                    mutatorMutex2.currentMutator.compareAndSet(mutator2, null);
                    throw th;
                }
            } catch (Throwable th4) {
                r1.unlock(null);
                throw th4;
            }
        }
    }

    public static /* synthetic */ Object mutate$default(MutatorMutex mutatorMutex, MutatePriority mutatePriority, Function1 function1, Continuation continuation, int i, Object obj) {
        if ((i & 1) != 0) {
            mutatePriority = MutatePriority.Default;
        }
        return mutatorMutex.mutate(mutatePriority, function1, continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void tryMutateOrCancel(Mutator mutator) {
        Mutator mutator2;
        do {
            mutator2 = (Mutator) this.currentMutator.get();
            if (mutator2 != null && !mutator.canInterrupt(mutator2)) {
                throw new CancellationException("Current mutation had a higher priority");
            }
        } while (!this.currentMutator.compareAndSet(mutator2, mutator));
        if (mutator2 != null) {
            mutator2.cancel();
        }
    }

    public final Object mutate(MutatePriority mutatePriority, Function1 function1, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new AnonymousClass2(mutatePriority, this, function1, null), continuation);
    }
}
