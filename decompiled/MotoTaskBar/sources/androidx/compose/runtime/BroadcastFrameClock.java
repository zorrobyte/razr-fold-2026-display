package androidx.compose.runtime;

import androidx.compose.runtime.BroadcastFrameClock;
import androidx.compose.runtime.MonotonicFrameClock;
import androidx.compose.runtime.internal.AtomicInt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;

/* JADX INFO: compiled from: BroadcastFrameClock.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BroadcastFrameClock implements MonotonicFrameClock {
    private Throwable failureCause;
    private final Function0 onNewAwaiters;
    private final Object lock = new Object();
    private List awaiters = new ArrayList();
    private List spareList = new ArrayList();
    private final AtomicInt hasAwaitersUnlocked = new AtomicInt(0);

    /* JADX INFO: compiled from: BroadcastFrameClock.kt */
    final class FrameAwaiter {
        private final Continuation continuation;
        private final Function1 onFrame;

        public FrameAwaiter(Function1 function1, Continuation continuation) {
            this.onFrame = function1;
            this.continuation = continuation;
        }

        public final Continuation getContinuation() {
            return this.continuation;
        }

        public final void resume(long j) {
            Object objM2707constructorimpl;
            Continuation continuation = this.continuation;
            try {
                Result.Companion companion = Result.Companion;
                objM2707constructorimpl = Result.m2707constructorimpl(this.onFrame.invoke(Long.valueOf(j)));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
            }
            continuation.resumeWith(objM2707constructorimpl);
        }
    }

    public BroadcastFrameClock(Function0 function0) {
        this.onNewAwaiters = function0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fail(Throwable th) {
        synchronized (this.lock) {
            try {
                if (this.failureCause != null) {
                    return;
                }
                this.failureCause = th;
                List list = this.awaiters;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    Continuation continuation = ((FrameAwaiter) list.get(i)).getContinuation();
                    Result.Companion companion = Result.Companion;
                    continuation.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(th)));
                }
                this.awaiters.clear();
                this.hasAwaitersUnlocked.set(0);
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // kotlin.coroutines.CoroutineContext
    public Object fold(Object obj, Function2 function2) {
        return MonotonicFrameClock.DefaultImpls.fold(this, obj, function2);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
    public CoroutineContext.Element get(CoroutineContext.Key key) {
        return MonotonicFrameClock.DefaultImpls.get(this, key);
    }

    public final boolean getHasAwaiters() {
        return this.hasAwaitersUnlocked.get() != 0;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext minusKey(CoroutineContext.Key key) {
        return MonotonicFrameClock.DefaultImpls.minusKey(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext plus(CoroutineContext coroutineContext) {
        return MonotonicFrameClock.DefaultImpls.plus(this, coroutineContext);
    }

    public final void sendFrame(long j) {
        synchronized (this.lock) {
            try {
                List list = this.awaiters;
                this.awaiters = this.spareList;
                this.spareList = list;
                this.hasAwaitersUnlocked.set(0);
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    ((FrameAwaiter) list.get(i)).resume(j);
                }
                list.clear();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.compose.runtime.MonotonicFrameClock
    public Object withFrameNanos(Function1 function1, Continuation continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final FrameAwaiter frameAwaiter = new FrameAwaiter(function1, cancellableContinuationImpl);
        synchronized (this.lock) {
            Throwable th = this.failureCause;
            if (th != null) {
                Result.Companion companion = Result.Companion;
                cancellableContinuationImpl.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(th)));
            } else {
                boolean zIsEmpty = this.awaiters.isEmpty();
                this.awaiters.add(frameAwaiter);
                if (zIsEmpty) {
                    this.hasAwaitersUnlocked.set(1);
                }
                cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: androidx.compose.runtime.BroadcastFrameClock$withFrameNanos$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        invoke((Throwable) obj);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(Throwable th2) {
                        Object obj = this.this$0.lock;
                        BroadcastFrameClock broadcastFrameClock = this.this$0;
                        BroadcastFrameClock.FrameAwaiter frameAwaiter2 = frameAwaiter;
                        synchronized (obj) {
                            try {
                                broadcastFrameClock.awaiters.remove(frameAwaiter2);
                                if (broadcastFrameClock.awaiters.isEmpty()) {
                                    broadcastFrameClock.hasAwaitersUnlocked.set(0);
                                }
                                Unit unit = Unit.INSTANCE;
                            } catch (Throwable th3) {
                                throw th3;
                            }
                        }
                    }
                });
                if (zIsEmpty && this.onNewAwaiters != null) {
                    try {
                        this.onNewAwaiters.mo2224invoke();
                    } catch (Throwable th2) {
                        fail(th2);
                    }
                }
            }
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }
}
