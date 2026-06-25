package com.motorola.plugin.core.utils;

import android.os.SystemClock;
import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.core.util.SparseArrayKt;
import androidx.core.util.SparseIntArrayKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* JADX INFO: compiled from: TimeoutRemoteCaller.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class TimeoutRemoteCaller implements Disposable, ISnapshotAware {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_CALL_TIMEOUT_MILLIS = 5000;
    private final SparseIntArray mAwaitedCalls;
    private final long mCallTimeoutMillis;
    private final Condition mCondition;
    private final ReentrantLock mLock;
    private final SparseArray mReceivedCalls;
    private int mSequenceCounter;
    private final String mToken;

    /* JADX INFO: compiled from: TimeoutRemoteCaller.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: TimeoutRemoteCaller.kt */
    final class TimeoutCallerSnapshot extends AbstractSnapshot {
        public List myAwaitedCalls;
        private int myCallerInstance;
        private boolean myHasWaiters;
        private boolean myLocked;
        private int myQueueSize;
        public List myReceivedCalls;
        private int mySequenceCounter;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TimeoutCallerSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final List getMyAwaitedCalls() {
            List list = this.myAwaitedCalls;
            if (list != null) {
                return list;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myAwaitedCalls");
            throw null;
        }

        public final int getMyCallerInstance() {
            return this.myCallerInstance;
        }

        public final boolean getMyHasWaiters() {
            return this.myHasWaiters;
        }

        public final boolean getMyLocked() {
            return this.myLocked;
        }

        public final int getMyQueueSize() {
            return this.myQueueSize;
        }

        public final List getMyReceivedCalls() {
            List list = this.myReceivedCalls;
            if (list != null) {
                return list;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myReceivedCalls");
            throw null;
        }

        public final int getMySequenceCounter() {
            return this.mySequenceCounter;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("Timeout caller", getMyCallerInstance()).newLine();
            iPrinter.increaseIndent();
            iPrinter.printPair("sequence", Integer.valueOf(getMySequenceCounter()));
            iPrinter.printPair("pendingAwaitedCalls", getMyAwaitedCalls());
            iPrinter.printPair("pendingReceivedCalls", getMyReceivedCalls());
            iPrinter.newLine();
            iPrinter.printPair("locked", Boolean.valueOf(getMyLocked()));
            iPrinter.printPair("hasWaiters", Boolean.valueOf(getMyHasWaiters()));
            iPrinter.printPair("estimateNumOfWaiters", Integer.valueOf(getMyQueueSize()));
            iPrinter.newLine();
            iPrinter.decreaseIndent();
        }

        public final void setMyAwaitedCalls(List list) {
            list.getClass();
            this.myAwaitedCalls = list;
        }

        public final void setMyCallerInstance(int i) {
            this.myCallerInstance = i;
        }

        public final void setMyHasWaiters(boolean z) {
            this.myHasWaiters = z;
        }

        public final void setMyLocked(boolean z) {
            this.myLocked = z;
        }

        public final void setMyQueueSize(int i) {
            this.myQueueSize = i;
        }

        public final void setMyReceivedCalls(List list) {
            list.getClass();
            this.myReceivedCalls = list;
        }

        public final void setMySequenceCounter(int i) {
            this.mySequenceCounter = i;
        }
    }

    /* JADX INFO: compiled from: TimeoutRemoteCaller.kt */
    public final class TimeoutRemoteCallException extends Exception {
        private final String message;
        private final int sequence;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TimeoutRemoteCallException(String str, int i) {
            super(str);
            str.getClass();
            this.message = str;
            this.sequence = i;
        }

        @Override // java.lang.Throwable
        public String getMessage() {
            return this.message;
        }

        public final int getSequence() {
            return this.sequence;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.utils.TimeoutRemoteCaller$getResultTimed$2, reason: invalid class name */
    /* JADX INFO: compiled from: TimeoutRemoteCaller.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $sequence;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(int i, Continuation continuation) {
            super(2, continuation);
            this.$sequence = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = TimeoutRemoteCaller.this.new AnonymousClass2(this.$sequence, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            final TimeoutRemoteCaller timeoutRemoteCaller;
            final int i;
            int iIndexOfKey;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            long jUptimeMillis = SystemClock.uptimeMillis();
            while (CoroutineScopeKt.isActive(coroutineScope)) {
                try {
                    ReentrantLock reentrantLock = TimeoutRemoteCaller.this.mLock;
                    timeoutRemoteCaller = TimeoutRemoteCaller.this;
                    i = this.$sequence;
                    reentrantLock.lock();
                    try {
                        iIndexOfKey = timeoutRemoteCaller.mReceivedCalls.indexOfKey(i);
                    } finally {
                        reentrantLock.unlock();
                    }
                } catch (InterruptedException unused) {
                }
                if (iIndexOfKey >= 0) {
                    Object objValueAt = timeoutRemoteCaller.mReceivedCalls.valueAt(iIndexOfKey);
                    timeoutRemoteCaller.mReceivedCalls.removeAt(iIndexOfKey);
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_CHANNEL, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.utils.TimeoutRemoteCaller$getResultTimed$2$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return '[' + timeoutRemoteCaller.mToken + "][" + i + "] Received result";
                        }
                    }, 60, null);
                    return objValueAt;
                }
                long jUptimeMillis2 = SystemClock.uptimeMillis() - jUptimeMillis;
                final long j = timeoutRemoteCaller.mCallTimeoutMillis - jUptimeMillis2;
                if (j <= 0) {
                    PluginConfigKt.trace$default(PluginConfigKt.TAG_CHANNEL, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.utils.TimeoutRemoteCaller$getResultTimed$2$1$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            return '[' + timeoutRemoteCaller.mToken + "][" + i + "] Response timeout, removed the awaited call sequence";
                        }
                    }, 60, null);
                    timeoutRemoteCaller.mAwaitedCalls.delete(i);
                    throw new TimeoutRemoteCallException('[' + timeoutRemoteCaller.mToken + "][" + i + "] No response for sequence: " + i + " after " + jUptimeMillis2, i);
                }
                Level level = Level.VERBOSE;
                PluginConfigKt.trace$default(PluginConfigKt.TAG_CHANNEL, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.utils.TimeoutRemoteCaller$getResultTimed$2$1$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return '[' + timeoutRemoteCaller.mToken + "][" + i + "] Will waiting response for " + j + " ms";
                    }
                }, 60, null);
                final long jUptimeMillis3 = SystemClock.uptimeMillis();
                timeoutRemoteCaller.mCondition.await(j, TimeUnit.MILLISECONDS);
                PluginConfigKt.trace$default(PluginConfigKt.TAG_CHANNEL, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.utils.TimeoutRemoteCaller$getResultTimed$2$1$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return '[' + timeoutRemoteCaller.mToken + "][" + i + "] Wakeup after " + (SystemClock.uptimeMillis() - jUptimeMillis3) + " ms";
                    }
                }, 60, null);
                Unit unit = Unit.INSTANCE;
            }
            TimeoutRemoteCaller.this.mAwaitedCalls.delete(this.$sequence);
            Level level2 = Level.VERBOSE;
            final TimeoutRemoteCaller timeoutRemoteCaller2 = TimeoutRemoteCaller.this;
            final int i2 = this.$sequence;
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CHANNEL, level2, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.utils.TimeoutRemoteCaller.getResultTimed.2.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return '[' + timeoutRemoteCaller2.mToken + "][" + i2 + "] Job has been canceled";
                }
            }, 60, null);
            throw new CancellationException();
        }
    }

    public TimeoutRemoteCaller() {
        this(0L, null, 3, null);
    }

    public TimeoutRemoteCaller(long j, String str) {
        str.getClass();
        this.mCallTimeoutMillis = j;
        this.mToken = str;
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mCondition = reentrantLock.newCondition();
        this.mAwaitedCalls = new SparseIntArray(1);
        this.mReceivedCalls = new SparseArray(1);
    }

    public /* synthetic */ TimeoutRemoteCaller(long j, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? DEFAULT_CALL_TIMEOUT_MILLIS : j, (i & 2) != 0 ? "" : str);
    }

    public final void cancel() {
        Level level = Level.VERBOSE;
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CHANNEL, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.utils.TimeoutRemoteCaller.cancel.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + TimeoutRemoteCaller.this.mToken + "] Try to cancel timeout caller ";
            }
        }, 60, null);
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            this.mCondition.signalAll();
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CHANNEL, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.utils.TimeoutRemoteCaller$cancel$2$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return '[' + this.this$0.mToken + "] Cancel timeout caller done";
                }
            }, 60, null);
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        cancel();
    }

    public final Object getResultTimed(int i, Continuation continuation) throws TimeoutRemoteCallException {
        return CoroutineScopeKt.coroutineScope(new AnonymousClass2(i, null), continuation);
    }

    public final int onBeforeRemoteCall() {
        int i;
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        do {
            try {
                i = this.mSequenceCounter;
                this.mSequenceCounter = i + 1;
            } finally {
                reentrantLock.unlock();
            }
        } while (this.mAwaitedCalls.get(i) != 0);
        this.mAwaitedCalls.put(i, 1);
        return i;
    }

    public final void onRemoteMethodResult(Object obj, final int i) {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            final boolean z = this.mAwaitedCalls.get(i) != 0;
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CHANNEL, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.utils.TimeoutRemoteCaller$onRemoteMethodResult$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return '[' + this.this$0.mToken + "][" + i + "] Remote service response, sequence active = " + z;
                }
            }, 62, null);
            if (z) {
                this.mAwaitedCalls.delete(i);
                this.mReceivedCalls.put(i, obj);
                this.mCondition.signalAll();
            }
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        TimeoutCallerSnapshot timeoutCallerSnapshot = new TimeoutCallerSnapshot(iSnapshot);
        timeoutCallerSnapshot.setMyCallerInstance(hashCode());
        timeoutCallerSnapshot.setMyLocked(this.mLock.isLocked());
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            timeoutCallerSnapshot.setMySequenceCounter(this.mSequenceCounter);
            timeoutCallerSnapshot.setMyAwaitedCalls(SequencesKt.toList(SequencesKt.asSequence(SparseIntArrayKt.keyIterator(this.mAwaitedCalls))));
            timeoutCallerSnapshot.setMyReceivedCalls(SequencesKt.toList(SequencesKt.asSequence(SparseArrayKt.keyIterator(this.mReceivedCalls))));
            timeoutCallerSnapshot.setMyHasWaiters(this.mLock.hasWaiters(this.mCondition));
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            timeoutCallerSnapshot.setMyQueueSize(this.mLock.getQueueLength());
            return timeoutCallerSnapshot;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }
}
