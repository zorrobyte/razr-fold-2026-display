package com.motorola.plugin.core.retry;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* JADX INFO: compiled from: Retryer.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class Retryer {
    private final AttemptTimeLimiter attemptTimeLimiter;
    private final BlockStrategy blockStrategy;
    private final Collection listeners;
    private final RetryStrategy retryStrategy;
    private final StopStrategy stopStrategy;
    private final WaitStrategy waitStrategy;

    /* JADX INFO: compiled from: Retryer.kt */
    final class ExceptionAttempt implements Attempt {
        private final long attemptNumber;
        private final ExecutionException e;
        private final long timeInMillsSinceFirstAttempt;

        public ExceptionAttempt(Throwable th, long j, long j2) {
            this.attemptNumber = j;
            this.timeInMillsSinceFirstAttempt = j2;
            this.e = new ExecutionException(th);
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public Object get() throws ExecutionException {
            throw this.e;
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public long getAttemptNumber() {
            return this.attemptNumber;
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public Throwable getExceptionCause() {
            return this.e.getCause();
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public Object getResult() {
            throw new IllegalStateException("The attempt resulted in an exception, not in a result");
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public long getTimeInMillsSinceFirstAttempt() {
            return this.timeInMillsSinceFirstAttempt;
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public boolean isException() {
            return true;
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public boolean isResult() {
            return false;
        }
    }

    /* JADX INFO: compiled from: Retryer.kt */
    final class ResultAttempt implements Attempt {
        private final long attemptNumber;
        private final Object result;
        private final long timeInMillsSinceFirstAttempt;

        public ResultAttempt(Object obj, long j, long j2) {
            this.result = obj;
            this.attemptNumber = j;
            this.timeInMillsSinceFirstAttempt = j2;
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public Object get() throws ExecutionException {
            return getResult();
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public long getAttemptNumber() {
            return this.attemptNumber;
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public Throwable getExceptionCause() {
            throw new IllegalStateException("The attempt resulted in a result, not in an exception");
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public Object getResult() {
            return this.result;
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public long getTimeInMillsSinceFirstAttempt() {
            return this.timeInMillsSinceFirstAttempt;
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public boolean isException() {
            return false;
        }

        @Override // com.motorola.plugin.core.retry.Attempt
        public boolean isResult() {
            return true;
        }
    }

    /* JADX INFO: compiled from: Retryer.kt */
    final class RetryerCallable implements Callable {
        private final Callable callable;
        private final Retryer retryer;

        public RetryerCallable(Retryer retryer, Callable callable) {
            retryer.getClass();
            callable.getClass();
            this.retryer = retryer;
            this.callable = callable;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws ExecutionException, RetryException {
            return RetryerKt.callBlocking(this.retryer, new Function0() { // from class: com.motorola.plugin.core.retry.Retryer$RetryerCallable$call$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return this.this$0.callable.call();
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.retry.Retryer$call$2, reason: invalid class name */
    /* JADX INFO: compiled from: Retryer.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $callable;
        int I$0;
        long J$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$callable = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = Retryer.this.new AnonymousClass2(this.$callable, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:37:0x00ee, code lost:
        
            if (r15.block(r8, r14) == r1) goto L38;
         */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0052 A[Catch: all -> 0x0036, TRY_ENTER, TryCatch #1 {all -> 0x0036, blocks: (B:19:0x0052, B:22:0x0072, B:12:0x0031), top: B:48:0x0031 }] */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00a9 A[LOOP:0: B:26:0x00a3->B:28:0x00a9, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:31:0x00bf  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00c4  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x0108  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x00ee -> B:8:0x001d). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r15) throws com.motorola.plugin.core.retry.RetryException {
            /*
                Method dump skipped, instruction units count: 272
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.retry.Retryer.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public Retryer(AttemptTimeLimiter attemptTimeLimiter, StopStrategy stopStrategy, WaitStrategy waitStrategy, BlockStrategy blockStrategy, RetryStrategy retryStrategy, Collection collection) {
        attemptTimeLimiter.getClass();
        stopStrategy.getClass();
        waitStrategy.getClass();
        blockStrategy.getClass();
        retryStrategy.getClass();
        collection.getClass();
        this.attemptTimeLimiter = attemptTimeLimiter;
        this.stopStrategy = stopStrategy;
        this.waitStrategy = waitStrategy;
        this.blockStrategy = blockStrategy;
        this.retryStrategy = retryStrategy;
        this.listeners = collection;
    }

    public /* synthetic */ Retryer(AttemptTimeLimiter attemptTimeLimiter, StopStrategy stopStrategy, WaitStrategy waitStrategy, BlockStrategy blockStrategy, RetryStrategy retryStrategy, Collection collection, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? AttemptTimeLimiter.Companion.noTimeLimit() : attemptTimeLimiter, (i & 2) != 0 ? StopStrategy.Companion.neverStop() : stopStrategy, (i & 4) != 0 ? WaitStrategy.Companion.noWait() : waitStrategy, (i & 8) != 0 ? BlockStrategy.Companion.coroutineDelayStrategy() : blockStrategy, retryStrategy, collection);
    }

    public final Object call(Function2 function2, Continuation continuation) throws ExecutionException, RetryException {
        return CoroutineScopeKt.coroutineScope(new AnonymousClass2(function2, null), continuation);
    }

    public final Callable wrap(Callable callable) {
        callable.getClass();
        return new RetryerCallable(this, callable);
    }
}
