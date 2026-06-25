package kotlinx.coroutines.sync;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.sync.MutexImpl;

/* JADX INFO: compiled from: Mutex.kt */
/* JADX INFO: loaded from: classes.dex */
public class MutexImpl extends SemaphoreAndMutexImpl implements Mutex {
    private final Function3 onSelectCancellationUnlockConstructor;
    private final AtomicRef owner;

    /* JADX INFO: compiled from: Mutex.kt */
    final class CancellableContinuationWithOwner implements CancellableContinuation, Waiter {
        public final CancellableContinuationImpl cont;
        public final Object owner;
        final /* synthetic */ MutexImpl this$0;

        public CancellableContinuationWithOwner(MutexImpl mutexImpl, CancellableContinuationImpl cancellableContinuationImpl, Object obj) {
            cancellableContinuationImpl.getClass();
            this.this$0 = mutexImpl;
            this.cont = cancellableContinuationImpl;
            this.owner = obj;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit resume$lambda$6(MutexImpl mutexImpl, CancellableContinuationWithOwner cancellableContinuationWithOwner, Throwable th) {
            th.getClass();
            mutexImpl.unlock(cancellableContinuationWithOwner.owner);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit tryResume$lambda$3(MutexImpl mutexImpl, CancellableContinuationWithOwner cancellableContinuationWithOwner, Throwable th, Unit unit, CoroutineContext coroutineContext) {
            th.getClass();
            unit.getClass();
            coroutineContext.getClass();
            mutexImpl.owner.setValue(cancellableContinuationWithOwner.owner);
            mutexImpl.unlock(cancellableContinuationWithOwner.owner);
            return Unit.INSTANCE;
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public boolean cancel(Throwable th) {
            return this.cont.cancel(th);
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public void completeResume(Object obj) {
            obj.getClass();
            this.cont.completeResume(obj);
        }

        @Override // kotlin.coroutines.Continuation
        public CoroutineContext getContext() {
            return this.cont.getContext();
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public void invokeOnCancellation(Function1 function1) {
            function1.getClass();
            this.cont.invokeOnCancellation(function1);
        }

        @Override // kotlinx.coroutines.Waiter
        public void invokeOnCancellation(Segment segment, int i) {
            segment.getClass();
            this.cont.invokeOnCancellation(segment, i);
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public void resume(Unit unit, Function3 function3) {
            unit.getClass();
            this.this$0.owner.setValue(this.owner);
            CancellableContinuationImpl cancellableContinuationImpl = this.cont;
            final MutexImpl mutexImpl = this.this$0;
            cancellableContinuationImpl.resume(unit, new Function1() { // from class: kotlinx.coroutines.sync.MutexImpl$CancellableContinuationWithOwner$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return MutexImpl.CancellableContinuationWithOwner.resume$lambda$6(mutexImpl, this, (Throwable) obj);
                }
            });
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public void resumeUndispatched(CoroutineDispatcher coroutineDispatcher, Unit unit) {
            coroutineDispatcher.getClass();
            unit.getClass();
            this.cont.resumeUndispatched(coroutineDispatcher, unit);
        }

        @Override // kotlin.coroutines.Continuation
        public void resumeWith(Object obj) {
            this.cont.resumeWith(obj);
        }

        @Override // kotlinx.coroutines.CancellableContinuation
        public Object tryResume(Unit unit, Object obj, Function3 function3) {
            unit.getClass();
            final MutexImpl mutexImpl = this.this$0;
            Object objTryResume = this.cont.tryResume(unit, obj, new Function3() { // from class: kotlinx.coroutines.sync.MutexImpl$CancellableContinuationWithOwner$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj2, Object obj3, Object obj4) {
                    return MutexImpl.CancellableContinuationWithOwner.tryResume$lambda$3(mutexImpl, this, (Throwable) obj2, (Unit) obj3, (CoroutineContext) obj4);
                }
            });
            if (objTryResume != null) {
                this.this$0.owner.setValue(this.owner);
            }
            return objTryResume;
        }
    }

    public MutexImpl(boolean z) {
        super(1, z ? 1 : 0);
        this.owner = AtomicFU.atomic(z ? null : MutexKt.NO_OWNER);
        this.onSelectCancellationUnlockConstructor = new Function3() { // from class: kotlinx.coroutines.sync.MutexImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return MutexImpl.onSelectCancellationUnlockConstructor$lambda$1(this.f$0, (SelectInstance) obj, obj2, obj3);
            }
        };
    }

    private final int holdsLockImpl(Object obj) {
        while (isLocked()) {
            Object value = this.owner.getValue();
            if (value != MutexKt.NO_OWNER) {
                return value == obj ? 1 : 2;
            }
        }
        return 0;
    }

    static /* synthetic */ Object lock$suspendImpl(MutexImpl mutexImpl, Object obj, Continuation continuation) {
        Object objLockSuspend;
        return (!mutexImpl.tryLock(obj) && (objLockSuspend = mutexImpl.lockSuspend(obj, continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objLockSuspend : Unit.INSTANCE;
    }

    private final Object lockSuspend(Object obj, Continuation continuation) {
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
        try {
            acquire(new CancellableContinuationWithOwner(this, orCreateCancellableContinuation, obj));
            Object result = orCreateCancellableContinuation.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
        } catch (Throwable th) {
            orCreateCancellableContinuation.releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Function3 onSelectCancellationUnlockConstructor$lambda$1(final MutexImpl mutexImpl, SelectInstance selectInstance, final Object obj, Object obj2) {
        selectInstance.getClass();
        return new Function3() { // from class: kotlinx.coroutines.sync.MutexImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj3, Object obj4, Object obj5) {
                return MutexImpl.onSelectCancellationUnlockConstructor$lambda$1$lambda$0(this.f$0, obj, (Throwable) obj3, obj4, (CoroutineContext) obj5);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onSelectCancellationUnlockConstructor$lambda$1$lambda$0(MutexImpl mutexImpl, Object obj, Throwable th, Object obj2, CoroutineContext coroutineContext) {
        th.getClass();
        coroutineContext.getClass();
        mutexImpl.unlock(obj);
        return Unit.INSTANCE;
    }

    private final int tryLockImpl(Object obj) {
        while (!tryAcquire()) {
            if (obj == null) {
                return 1;
            }
            int iHoldsLockImpl = holdsLockImpl(obj);
            if (iHoldsLockImpl == 1) {
                return 2;
            }
            if (iHoldsLockImpl == 2) {
                return 1;
            }
        }
        this.owner.setValue(obj);
        return 0;
    }

    @Override // kotlinx.coroutines.sync.Mutex
    public boolean isLocked() {
        return getAvailablePermits() == 0;
    }

    @Override // kotlinx.coroutines.sync.Mutex
    public Object lock(Object obj, Continuation continuation) {
        return lock$suspendImpl(this, obj, continuation);
    }

    public String toString() {
        return "Mutex@" + DebugStringsKt.getHexAddress(this) + "[isLocked=" + isLocked() + ",owner=" + this.owner.getValue() + "]";
    }

    public boolean tryLock(Object obj) {
        int iTryLockImpl = tryLockImpl(obj);
        if (iTryLockImpl == 0) {
            return true;
        }
        if (iTryLockImpl == 1) {
            return false;
        }
        if (iTryLockImpl != 2) {
            throw new IllegalStateException("unexpected");
        }
        throw new IllegalStateException(("This mutex is already locked by the specified owner: " + obj).toString());
    }

    @Override // kotlinx.coroutines.sync.Mutex
    public void unlock(Object obj) {
        while (isLocked()) {
            Object value = this.owner.getValue();
            if (value != MutexKt.NO_OWNER) {
                if (value != obj && obj != null) {
                    throw new IllegalStateException(("This mutex is locked by " + value + ", but " + obj + " is expected").toString());
                }
                if (this.owner.compareAndSet(value, MutexKt.NO_OWNER)) {
                    release();
                    return;
                }
            }
        }
        throw new IllegalStateException("This mutex is not locked");
    }
}
