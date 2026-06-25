package kotlinx.coroutines.channels;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import kotlin.ExceptionsKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.reflect.KFunction;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.internal.ConcurrentLinkedListKt;
import kotlinx.coroutines.internal.ConcurrentLinkedListNode;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.SegmentOrClosed;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectInstance;

/* JADX INFO: compiled from: BufferedChannel.kt */
/* JADX INFO: loaded from: classes2.dex */
public class BufferedChannel implements Channel {
    private final AtomicRef _closeCause;
    private final AtomicLong bufferEnd;
    private final AtomicRef bufferEndSegment;
    private final int capacity;
    private final AtomicRef closeHandler;
    private final AtomicLong completedExpandBuffersAndPauseFlag;
    public final Function1 onUndeliveredElement;
    private final Function3 onUndeliveredElementReceiveCancellationConstructor;
    private final AtomicRef receiveSegment;
    private final AtomicLong receivers;
    private final AtomicRef sendSegment;
    private final AtomicLong sendersAndCloseStatus;

    /* JADX INFO: compiled from: BufferedChannel.kt */
    final class BufferedChannelIterator implements ChannelIterator, Waiter {
        private CancellableContinuationImpl continuation;
        private Object receiveResult = BufferedChannelKt.NO_RECEIVE_RESULT;

        public BufferedChannelIterator() {
        }

        private final Object hasNextOnNoWaiterSuspend(ChannelSegment channelSegment, int i, long j, Continuation continuation) {
            Boolean boolBoxBoolean;
            ChannelSegment channelSegment2;
            BufferedChannel bufferedChannel = BufferedChannel.this;
            CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
            try {
                this.continuation = orCreateCancellableContinuation;
                Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i, j, this);
                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                    bufferedChannel.prepareReceiverForSuspension(this, channelSegment, i);
                } else {
                    Function3 function3BindCancellationFun = null;
                    if (objUpdateCellReceive == BufferedChannelKt.FAILED) {
                        if (j < bufferedChannel.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            channelSegment.cleanPrev();
                        }
                        ChannelSegment channelSegment3 = (ChannelSegment) bufferedChannel.receiveSegment.getValue();
                        while (true) {
                            if (bufferedChannel.isClosedForReceive()) {
                                onClosedHasNextNoWaiterSuspend();
                                break;
                            }
                            long andIncrement = bufferedChannel.receivers.getAndIncrement();
                            int i2 = BufferedChannelKt.SEGMENT_SIZE;
                            long j2 = andIncrement / ((long) i2);
                            int i3 = (int) (andIncrement % ((long) i2));
                            if (channelSegment3.id != j2) {
                                ChannelSegment channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j2, channelSegment3);
                                if (channelSegmentFindSegmentReceive != null) {
                                    channelSegment2 = channelSegmentFindSegmentReceive;
                                }
                            } else {
                                channelSegment2 = channelSegment3;
                            }
                            Object objUpdateCellReceive2 = bufferedChannel.updateCellReceive(channelSegment2, i3, andIncrement, this);
                            ChannelSegment channelSegment4 = channelSegment2;
                            if (objUpdateCellReceive2 == BufferedChannelKt.SUSPEND) {
                                bufferedChannel.prepareReceiverForSuspension(this, channelSegment4, i3);
                                break;
                            }
                            if (objUpdateCellReceive2 == BufferedChannelKt.FAILED) {
                                if (andIncrement < bufferedChannel.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                    channelSegment4.cleanPrev();
                                }
                                channelSegment3 = channelSegment4;
                            } else {
                                if (objUpdateCellReceive2 == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                    throw new IllegalStateException("unexpected");
                                }
                                channelSegment4.cleanPrev();
                                this.receiveResult = objUpdateCellReceive2;
                                this.continuation = null;
                                boolBoxBoolean = Boxing.boxBoolean(true);
                                Function1 function1 = bufferedChannel.onUndeliveredElement;
                                if (function1 != null) {
                                    function3BindCancellationFun = bufferedChannel.bindCancellationFun(function1, objUpdateCellReceive2);
                                }
                            }
                        }
                    } else {
                        channelSegment.cleanPrev();
                        this.receiveResult = objUpdateCellReceive;
                        this.continuation = null;
                        boolBoxBoolean = Boxing.boxBoolean(true);
                        Function1 function12 = bufferedChannel.onUndeliveredElement;
                        if (function12 != null) {
                            function3BindCancellationFun = bufferedChannel.bindCancellationFun(function12, objUpdateCellReceive);
                        }
                    }
                    orCreateCancellableContinuation.resume(boolBoxBoolean, function3BindCancellationFun);
                }
                Object result = orCreateCancellableContinuation.getResult();
                if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    DebugProbesKt.probeCoroutineSuspended(continuation);
                }
                return result;
            } catch (Throwable th) {
                orCreateCancellableContinuation.releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                throw th;
            }
        }

        private final boolean onClosedHasNext() throws Throwable {
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable closeCause = BufferedChannel.this.getCloseCause();
            if (closeCause == null) {
                return false;
            }
            throw StackTraceRecoveryKt.recoverStackTrace(closeCause);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void onClosedHasNextNoWaiterSuspend() {
            CancellableContinuationImpl cancellableContinuationImpl = this.continuation;
            cancellableContinuationImpl.getClass();
            this.continuation = null;
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable closeCause = BufferedChannel.this.getCloseCause();
            if (closeCause == null) {
                Result.Companion companion = Result.Companion;
                cancellableContinuationImpl.resumeWith(Result.m2707constructorimpl(Boolean.FALSE));
            } else {
                Result.Companion companion2 = Result.Companion;
                cancellableContinuationImpl.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(closeCause)));
            }
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        public Object hasNext(Continuation continuation) throws Throwable {
            ChannelSegment channelSegmentFindSegmentReceive;
            boolean zOnClosedHasNext = true;
            if (this.receiveResult == BufferedChannelKt.NO_RECEIVE_RESULT || this.receiveResult == BufferedChannelKt.getCHANNEL_CLOSED()) {
                BufferedChannel bufferedChannel = BufferedChannel.this;
                ChannelSegment channelSegment = (ChannelSegment) bufferedChannel.receiveSegment.getValue();
                while (true) {
                    if (bufferedChannel.isClosedForReceive()) {
                        zOnClosedHasNext = onClosedHasNext();
                        break;
                    }
                    long andIncrement = bufferedChannel.receivers.getAndIncrement();
                    int i = BufferedChannelKt.SEGMENT_SIZE;
                    long j = andIncrement / ((long) i);
                    int i2 = (int) (andIncrement % ((long) i));
                    if (channelSegment.id != j) {
                        channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j, channelSegment);
                        if (channelSegmentFindSegmentReceive == null) {
                            continue;
                        }
                    } else {
                        channelSegmentFindSegmentReceive = channelSegment;
                    }
                    Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegmentFindSegmentReceive, i2, andIncrement, null);
                    if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                        throw new IllegalStateException("unreachable");
                    }
                    if (objUpdateCellReceive == BufferedChannelKt.FAILED) {
                        if (andIncrement < bufferedChannel.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            channelSegmentFindSegmentReceive.cleanPrev();
                        }
                        channelSegment = channelSegmentFindSegmentReceive;
                    } else {
                        if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                            return hasNextOnNoWaiterSuspend(channelSegmentFindSegmentReceive, i2, andIncrement, continuation);
                        }
                        channelSegmentFindSegmentReceive.cleanPrev();
                        this.receiveResult = objUpdateCellReceive;
                    }
                }
            }
            return Boxing.boxBoolean(zOnClosedHasNext);
        }

        @Override // kotlinx.coroutines.Waiter
        public void invokeOnCancellation(Segment segment, int i) {
            segment.getClass();
            CancellableContinuationImpl cancellableContinuationImpl = this.continuation;
            if (cancellableContinuationImpl != null) {
                cancellableContinuationImpl.invokeOnCancellation(segment, i);
            }
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        public Object next() throws Throwable {
            Object obj = this.receiveResult;
            if (obj == BufferedChannelKt.NO_RECEIVE_RESULT) {
                throw new IllegalStateException("`hasNext()` has not been invoked");
            }
            this.receiveResult = BufferedChannelKt.NO_RECEIVE_RESULT;
            if (obj != BufferedChannelKt.getCHANNEL_CLOSED()) {
                return obj;
            }
            throw StackTraceRecoveryKt.recoverStackTrace(BufferedChannel.this.getReceiveException());
        }

        public final boolean tryResumeHasNext(Object obj) {
            CancellableContinuationImpl cancellableContinuationImpl = this.continuation;
            cancellableContinuationImpl.getClass();
            this.continuation = null;
            this.receiveResult = obj;
            Boolean bool = Boolean.TRUE;
            BufferedChannel bufferedChannel = BufferedChannel.this;
            Function1 function1 = bufferedChannel.onUndeliveredElement;
            return BufferedChannelKt.tryResume0(cancellableContinuationImpl, bool, function1 != null ? bufferedChannel.bindCancellationFun(function1, obj) : null);
        }

        public final void tryResumeHasNextOnClosedChannel() {
            CancellableContinuationImpl cancellableContinuationImpl = this.continuation;
            cancellableContinuationImpl.getClass();
            this.continuation = null;
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable closeCause = BufferedChannel.this.getCloseCause();
            if (closeCause == null) {
                Result.Companion companion = Result.Companion;
                cancellableContinuationImpl.resumeWith(Result.m2707constructorimpl(Boolean.FALSE));
            } else {
                Result.Companion companion2 = Result.Companion;
                cancellableContinuationImpl.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(closeCause)));
            }
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.BufferedChannel$bindCancellationFun$2, reason: invalid class name */
    /* JADX INFO: compiled from: BufferedChannel.kt */
    final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function3 {
        AnonymousClass2(Object obj) {
            super(3, obj, BufferedChannel.class, "onCancellationImplDoNotCall", "onCancellationImplDoNotCall(Ljava/lang/Throwable;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            invoke((Throwable) obj, obj2, (CoroutineContext) obj3);
            return Unit.INSTANCE;
        }

        public final void invoke(Throwable th, Object obj, CoroutineContext coroutineContext) {
            th.getClass();
            coroutineContext.getClass();
            ((BufferedChannel) this.receiver).onCancellationImplDoNotCall(th, obj, coroutineContext);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.channels.BufferedChannel$bindCancellationFunResult$1, reason: invalid class name */
    /* JADX INFO: compiled from: BufferedChannel.kt */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function3 {
        AnonymousClass1(Object obj) {
            super(3, obj, BufferedChannel.class, "onCancellationChannelResultImplDoNotCall", "onCancellationChannelResultImplDoNotCall-5_sEAP8(Ljava/lang/Throwable;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            m2738invoke5_sEAP8((Throwable) obj, ((ChannelResult) obj2).m2748unboximpl(), (CoroutineContext) obj3);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke-5_sEAP8, reason: not valid java name */
        public final void m2738invoke5_sEAP8(Throwable th, Object obj, CoroutineContext coroutineContext) {
            th.getClass();
            coroutineContext.getClass();
            ((BufferedChannel) this.receiver).m2731onCancellationChannelResultImplDoNotCall5_sEAP8(th, obj, coroutineContext);
        }
    }

    public BufferedChannel(int i, Function1 function1) {
        this.capacity = i;
        this.onUndeliveredElement = function1;
        if (i < 0) {
            throw new IllegalArgumentException(("Invalid channel capacity: " + i + ", should be >=0").toString());
        }
        this.sendersAndCloseStatus = AtomicFU.atomic(0L);
        this.receivers = AtomicFU.atomic(0L);
        this.bufferEnd = AtomicFU.atomic(BufferedChannelKt.initialBufferEnd(i));
        this.completedExpandBuffersAndPauseFlag = AtomicFU.atomic(getBufferEndCounter());
        ChannelSegment channelSegment = new ChannelSegment(0L, null, this, 3);
        this.sendSegment = AtomicFU.atomic(channelSegment);
        this.receiveSegment = AtomicFU.atomic(channelSegment);
        if (isRendezvousOrUnlimited()) {
            channelSegment = BufferedChannelKt.NULL_SEGMENT;
            channelSegment.getClass();
        }
        this.bufferEndSegment = AtomicFU.atomic(channelSegment);
        this.onUndeliveredElementReceiveCancellationConstructor = function1 != null ? new Function3() { // from class: kotlinx.coroutines.channels.BufferedChannel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                BufferedChannel bufferedChannel = this.f$0;
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                return BufferedChannel.onUndeliveredElementReceiveCancellationConstructor$lambda$57$lambda$56(bufferedChannel, null, obj2, obj3);
            }
        } : null;
        this._closeCause = AtomicFU.atomic(BufferedChannelKt.NO_CLOSE_CAUSE);
        this.closeHandler = AtomicFU.atomic((Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Function3 bindCancellationFun(final Function1 function1, final Object obj) {
        return new Function3() { // from class: kotlinx.coroutines.channels.BufferedChannel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                return BufferedChannel.bindCancellationFun$lambda$89(function1, obj, (Throwable) obj2, obj3, (CoroutineContext) obj4);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KFunction bindCancellationFun(Function1 function1) {
        return new AnonymousClass2(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit bindCancellationFun$lambda$89(Function1 function1, Object obj, Throwable th, Object obj2, CoroutineContext coroutineContext) {
        th.getClass();
        coroutineContext.getClass();
        OnUndeliveredElementKt.callUndeliveredElement(function1, obj, coroutineContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KFunction bindCancellationFunResult(Function1 function1) {
        return new AnonymousClass1(this);
    }

    private final boolean bufferOrRendezvousSend(long j) {
        return j < getBufferEndCounter() || j < getReceiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() + ((long) this.capacity);
    }

    private final void cancelSuspendedReceiveRequests(ChannelSegment channelSegment, long j) {
        Object objM2757constructorimpl$default = InlineList.m2757constructorimpl$default(null, 1, null);
        loop0: while (channelSegment != null) {
            for (int i = BufferedChannelKt.SEGMENT_SIZE - 1; -1 < i; i--) {
                if ((channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE)) + ((long) i) < j) {
                    break loop0;
                }
                while (true) {
                    Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null && state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.IN_BUFFER) {
                        if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof WaiterEB)) {
                            if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Waiter)) {
                                break;
                            }
                            if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.getCHANNEL_CLOSED())) {
                                objM2757constructorimpl$default = InlineList.m2758plusFjFbRPM(objM2757constructorimpl$default, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
                                channelSegment.onCancelledRequest(i, true);
                                break;
                            }
                        } else {
                            if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.getCHANNEL_CLOSED())) {
                                objM2757constructorimpl$default = InlineList.m2758plusFjFbRPM(objM2757constructorimpl$default, ((WaiterEB) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).waiter);
                                channelSegment.onCancelledRequest(i, true);
                                break;
                            }
                        }
                    } else {
                        if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.getCHANNEL_CLOSED())) {
                            channelSegment.onSlotCleaned();
                            break;
                        }
                    }
                }
            }
            channelSegment = (ChannelSegment) channelSegment.getPrev();
        }
        if (objM2757constructorimpl$default != null) {
            if (!(objM2757constructorimpl$default instanceof ArrayList)) {
                resumeReceiverOnClosedChannel((Waiter) objM2757constructorimpl$default);
                return;
            }
            ArrayList arrayList = (ArrayList) objM2757constructorimpl$default;
            for (int size = arrayList.size() - 1; -1 < size; size--) {
                resumeReceiverOnClosedChannel((Waiter) arrayList.get(size));
            }
        }
    }

    private final ChannelSegment closeLinkedList() {
        Object value = this.bufferEndSegment.getValue();
        ChannelSegment channelSegment = (ChannelSegment) this.sendSegment.getValue();
        if (channelSegment.id > ((ChannelSegment) value).id) {
            value = channelSegment;
        }
        ChannelSegment channelSegment2 = (ChannelSegment) this.receiveSegment.getValue();
        if (channelSegment2.id > ((ChannelSegment) value).id) {
            value = channelSegment2;
        }
        return (ChannelSegment) ConcurrentLinkedListKt.close((ConcurrentLinkedListNode) value);
    }

    private final void completeCancel(long j) {
        removeUnprocessedElements(completeClose(j));
    }

    private final ChannelSegment completeClose(long j) {
        ChannelSegment channelSegmentCloseLinkedList = closeLinkedList();
        if (isConflatedDropOldest()) {
            long jMarkAllEmptyCellsAsClosed = markAllEmptyCellsAsClosed(channelSegmentCloseLinkedList);
            if (jMarkAllEmptyCellsAsClosed != -1) {
                dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(jMarkAllEmptyCellsAsClosed);
            }
        }
        cancelSuspendedReceiveRequests(channelSegmentCloseLinkedList, j);
        return channelSegmentCloseLinkedList;
    }

    private final void completeCloseOrCancel() {
        isClosedForSend();
    }

    private final void expandBuffer() {
        BufferedChannel bufferedChannel;
        if (isRendezvousOrUnlimited()) {
            return;
        }
        ChannelSegment channelSegment = (ChannelSegment) this.bufferEndSegment.getValue();
        while (true) {
            long andIncrement = this.bufferEnd.getAndIncrement();
            int i = BufferedChannelKt.SEGMENT_SIZE;
            long j = andIncrement / ((long) i);
            if (this.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() <= andIncrement) {
                if (channelSegment.id < j && channelSegment.getNext() != null) {
                    this.moveSegmentBufferEndToSpecifiedOrLast(j, channelSegment);
                }
                incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
                return;
            }
            if (channelSegment.id != j) {
                bufferedChannel = this;
                ChannelSegment channelSegmentFindSegmentBufferEnd = bufferedChannel.findSegmentBufferEnd(j, channelSegment, andIncrement);
                if (channelSegmentFindSegmentBufferEnd == null) {
                    continue;
                    this = bufferedChannel;
                } else {
                    channelSegment = channelSegmentFindSegmentBufferEnd;
                }
            } else {
                bufferedChannel = this;
            }
            if (bufferedChannel.updateCellExpandBuffer(channelSegment, (int) (andIncrement % ((long) i)), andIncrement)) {
                incCompletedExpandBufferAttempts$default(bufferedChannel, 0L, 1, null);
                return;
            } else {
                incCompletedExpandBufferAttempts$default(bufferedChannel, 0L, 1, null);
                this = bufferedChannel;
            }
        }
    }

    private final ChannelSegment findSegmentBufferEnd(long j, ChannelSegment channelSegment, long j2) {
        Object objFindSegmentInternal;
        AtomicRef atomicRef = this.bufferEndSegment;
        Function2 function2 = (Function2) BufferedChannelKt.createSegmentFunction();
        loop0: while (true) {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(channelSegment, j, function2);
            if (!SegmentOrClosed.m2761isClosedimpl(objFindSegmentInternal)) {
                Segment segmentM2760getSegmentimpl = SegmentOrClosed.m2760getSegmentimpl(objFindSegmentInternal);
                while (true) {
                    Segment segment = (Segment) atomicRef.getValue();
                    if (segment.id >= segmentM2760getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!segmentM2760getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        break;
                    }
                    if (atomicRef.compareAndSet(segment, segmentM2760getSegmentimpl)) {
                        if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            segment.remove();
                        }
                    } else if (segmentM2760getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        segmentM2760getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        if (SegmentOrClosed.m2761isClosedimpl(objFindSegmentInternal)) {
            completeCloseOrCancel();
            moveSegmentBufferEndToSpecifiedOrLast(j, channelSegment);
            incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
            return null;
        }
        ChannelSegment channelSegment2 = (ChannelSegment) SegmentOrClosed.m2760getSegmentimpl(objFindSegmentInternal);
        long j3 = channelSegment2.id;
        if (j3 <= j) {
            return channelSegment2;
        }
        int i = BufferedChannelKt.SEGMENT_SIZE;
        if (this.bufferEnd.compareAndSet(1 + j2, j3 * ((long) i))) {
            incCompletedExpandBufferAttempts((channelSegment2.id * ((long) i)) - j2);
        } else {
            incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ChannelSegment findSegmentReceive(long j, ChannelSegment channelSegment) {
        Object objFindSegmentInternal;
        AtomicRef atomicRef = this.receiveSegment;
        Function2 function2 = (Function2) BufferedChannelKt.createSegmentFunction();
        loop0: while (true) {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(channelSegment, j, function2);
            if (!SegmentOrClosed.m2761isClosedimpl(objFindSegmentInternal)) {
                Segment segmentM2760getSegmentimpl = SegmentOrClosed.m2760getSegmentimpl(objFindSegmentInternal);
                while (true) {
                    Segment segment = (Segment) atomicRef.getValue();
                    if (segment.id >= segmentM2760getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!segmentM2760getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        break;
                    }
                    if (atomicRef.compareAndSet(segment, segmentM2760getSegmentimpl)) {
                        if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            segment.remove();
                        }
                    } else if (segmentM2760getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        segmentM2760getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        if (SegmentOrClosed.m2761isClosedimpl(objFindSegmentInternal)) {
            completeCloseOrCancel();
            if (channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE) < getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                channelSegment.cleanPrev();
            }
            return null;
        }
        ChannelSegment channelSegment2 = (ChannelSegment) SegmentOrClosed.m2760getSegmentimpl(objFindSegmentInternal);
        if (!isRendezvousOrUnlimited() && j <= getBufferEndCounter() / ((long) BufferedChannelKt.SEGMENT_SIZE)) {
            AtomicRef atomicRef2 = this.bufferEndSegment;
            while (true) {
                Segment segment2 = (Segment) atomicRef2.getValue();
                if (segment2.id >= channelSegment2.id || !channelSegment2.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    break;
                }
                if (atomicRef2.compareAndSet(segment2, channelSegment2)) {
                    if (segment2.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        segment2.remove();
                    }
                } else if (channelSegment2.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    channelSegment2.remove();
                }
            }
        }
        long j2 = channelSegment2.id;
        if (j2 <= j) {
            return channelSegment2;
        }
        int i = BufferedChannelKt.SEGMENT_SIZE;
        updateReceiversCounterIfLower(j2 * ((long) i));
        if (channelSegment2.id * ((long) i) < getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
            channelSegment2.cleanPrev();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ChannelSegment findSegmentSend(long j, ChannelSegment channelSegment) {
        Object objFindSegmentInternal;
        AtomicRef atomicRef = this.sendSegment;
        Function2 function2 = (Function2) BufferedChannelKt.createSegmentFunction();
        loop0: while (true) {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(channelSegment, j, function2);
            if (!SegmentOrClosed.m2761isClosedimpl(objFindSegmentInternal)) {
                Segment segmentM2760getSegmentimpl = SegmentOrClosed.m2760getSegmentimpl(objFindSegmentInternal);
                while (true) {
                    Segment segment = (Segment) atomicRef.getValue();
                    if (segment.id >= segmentM2760getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!segmentM2760getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        break;
                    }
                    if (atomicRef.compareAndSet(segment, segmentM2760getSegmentimpl)) {
                        if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            segment.remove();
                        }
                    } else if (segmentM2760getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        segmentM2760getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        if (SegmentOrClosed.m2761isClosedimpl(objFindSegmentInternal)) {
            completeCloseOrCancel();
            if (channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE) < getReceiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                channelSegment.cleanPrev();
            }
            return null;
        }
        ChannelSegment channelSegment2 = (ChannelSegment) SegmentOrClosed.m2760getSegmentimpl(objFindSegmentInternal);
        long j2 = channelSegment2.id;
        if (j2 <= j) {
            return channelSegment2;
        }
        int i = BufferedChannelKt.SEGMENT_SIZE;
        updateSendersCounterIfLower(j2 * ((long) i));
        if (channelSegment2.id * ((long) i) < getReceiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
            channelSegment2.cleanPrev();
        }
        return null;
    }

    private final long getBufferEndCounter() {
        return this.bufferEnd.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Throwable getReceiveException() {
        Throwable closeCause = getCloseCause();
        return closeCause == null ? new ClosedReceiveChannelException("Channel was closed") : closeCause;
    }

    private final void incCompletedExpandBufferAttempts(long j) {
        if ((this.completedExpandBuffersAndPauseFlag.addAndGet(j) & 4611686018427387904L) != 0) {
            while ((this.completedExpandBuffersAndPauseFlag.getValue() & 4611686018427387904L) != 0) {
            }
        }
    }

    static /* synthetic */ void incCompletedExpandBufferAttempts$default(BufferedChannel bufferedChannel, long j, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: incCompletedExpandBufferAttempts");
        }
        if ((i & 1) != 0) {
            j = 1;
        }
        bufferedChannel.incCompletedExpandBufferAttempts(j);
    }

    private final void invokeCloseHandler() {
        Object value;
        AtomicRef atomicRef = this.closeHandler;
        do {
            value = atomicRef.getValue();
        } while (!atomicRef.compareAndSet(value, value == null ? BufferedChannelKt.CLOSE_HANDLER_CLOSED : BufferedChannelKt.CLOSE_HANDLER_INVOKED));
        if (value == null) {
            return;
        }
        ((Function1) value).invoke(getCloseCause());
    }

    private final boolean isCellNonEmpty(ChannelSegment channelSegment, int i, long j) {
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        do {
            state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null && state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.IN_BUFFER) {
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.BUFFERED) {
                    return true;
                }
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.INTERRUPTED_SEND || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.getCHANNEL_CLOSED() || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.DONE_RCV || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.POISONED) {
                    return false;
                }
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.RESUMING_BY_EB) {
                    return true;
                }
                return state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.RESUMING_BY_RCV && j == getReceiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            }
        } while (!channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.POISONED));
        expandBuffer();
        return false;
    }

    private final boolean isClosed(long j, boolean z) {
        int i = (int) (j >> 60);
        if (i == 0 || i == 1) {
            return false;
        }
        if (i == 2) {
            completeClose(j & 1152921504606846975L);
            return (z && hasElements$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) ? false : true;
        }
        if (i == 3) {
            completeCancel(j & 1152921504606846975L);
            return true;
        }
        throw new IllegalStateException(("unexpected close status: " + i).toString());
    }

    private final boolean isClosedForReceive0(long j) {
        return isClosed(j, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isClosedForSend0(long j) {
        return isClosed(j, false);
    }

    private final boolean isRendezvousOrUnlimited() {
        long bufferEndCounter = getBufferEndCounter();
        return bufferEndCounter == 0 || bufferEndCounter == Long.MAX_VALUE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x003c, code lost:
    
        r8 = (kotlinx.coroutines.channels.ChannelSegment) r8.getPrev();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final long markAllEmptyCellsAsClosed(kotlinx.coroutines.channels.ChannelSegment r8) {
        /*
            r7 = this;
        L0:
            int r0 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            int r0 = r0 + (-1)
        L4:
            r1 = -1
            r3 = -1
            if (r3 >= r0) goto L3c
            long r3 = r8.id
            int r5 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r5 = (long) r5
            long r3 = r3 * r5
            long r5 = (long) r0
            long r3 = r3 + r5
            long r5 = r7.getReceiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 >= 0) goto L1a
            return r1
        L1a:
            java.lang.Object r1 = r8.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r0)
            if (r1 == 0) goto L2c
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.access$getIN_BUFFER$p()
            if (r1 != r2) goto L27
            goto L2c
        L27:
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.BUFFERED
            if (r1 != r2) goto L39
            return r3
        L2c:
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r1 = r8.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r0, r1, r2)
            if (r1 == 0) goto L1a
            r8.onSlotCleaned()
        L39:
            int r0 = r0 + (-1)
            goto L4
        L3c:
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r8 = r8.getPrev()
            kotlinx.coroutines.channels.ChannelSegment r8 = (kotlinx.coroutines.channels.ChannelSegment) r8
            if (r8 != 0) goto L0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.markAllEmptyCellsAsClosed(kotlinx.coroutines.channels.ChannelSegment):long");
    }

    private final void markCancellationStarted() {
        long value;
        AtomicLong atomicLong = this.sendersAndCloseStatus;
        do {
            value = atomicLong.getValue();
            if (((int) (value >> 60)) != 0) {
                return;
            }
        } while (!atomicLong.compareAndSet(value, BufferedChannelKt.constructSendersAndCloseStatus(1152921504606846975L & value, 1)));
    }

    private final void markCancelled() {
        long value;
        AtomicLong atomicLong = this.sendersAndCloseStatus;
        do {
            value = atomicLong.getValue();
        } while (!atomicLong.compareAndSet(value, BufferedChannelKt.constructSendersAndCloseStatus(1152921504606846975L & value, 3)));
    }

    private final void markClosed() {
        long value;
        long jConstructSendersAndCloseStatus;
        AtomicLong atomicLong = this.sendersAndCloseStatus;
        do {
            value = atomicLong.getValue();
            int i = (int) (value >> 60);
            if (i == 0) {
                jConstructSendersAndCloseStatus = BufferedChannelKt.constructSendersAndCloseStatus(value & 1152921504606846975L, 2);
            } else if (i != 1) {
                return;
            } else {
                jConstructSendersAndCloseStatus = BufferedChannelKt.constructSendersAndCloseStatus(value & 1152921504606846975L, 3);
            }
        } while (!atomicLong.compareAndSet(value, jConstructSendersAndCloseStatus));
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0011, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void moveSegmentBufferEndToSpecifiedOrLast(long r5, kotlinx.coroutines.channels.ChannelSegment r7) {
        /*
            r4 = this;
        L0:
            long r0 = r7.id
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 >= 0) goto L11
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r0 = r7.getNext()
            kotlinx.coroutines.channels.ChannelSegment r0 = (kotlinx.coroutines.channels.ChannelSegment) r0
            if (r0 != 0) goto Lf
            goto L11
        Lf:
            r7 = r0
            goto L0
        L11:
            boolean r5 = r7.isRemoved()
            if (r5 == 0) goto L22
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r5 = r7.getNext()
            kotlinx.coroutines.channels.ChannelSegment r5 = (kotlinx.coroutines.channels.ChannelSegment) r5
            if (r5 != 0) goto L20
            goto L22
        L20:
            r7 = r5
            goto L11
        L22:
            kotlinx.atomicfu.AtomicRef r5 = r4.bufferEndSegment
        L24:
            java.lang.Object r6 = r5.getValue()
            kotlinx.coroutines.internal.Segment r6 = (kotlinx.coroutines.internal.Segment) r6
            long r0 = r6.id
            long r2 = r7.id
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 < 0) goto L33
            return
        L33:
            boolean r0 = r7.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()
            if (r0 != 0) goto L3a
            goto L11
        L3a:
            boolean r0 = r5.compareAndSet(r6, r7)
            if (r0 == 0) goto L4a
            boolean r4 = r6.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()
            if (r4 == 0) goto L49
            r6.remove()
        L49:
            return
        L4a:
            boolean r6 = r7.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()
            if (r6 == 0) goto L24
            r7.remove()
            goto L24
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.moveSegmentBufferEndToSpecifiedOrLast(long, kotlinx.coroutines.channels.ChannelSegment):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCancellationChannelResultImplDoNotCall-5_sEAP8, reason: not valid java name */
    public final void m2731onCancellationChannelResultImplDoNotCall5_sEAP8(Throwable th, Object obj, CoroutineContext coroutineContext) {
        Function1 function1 = this.onUndeliveredElement;
        function1.getClass();
        Object objM2743getOrNullimpl = ChannelResult.m2743getOrNullimpl(obj);
        objM2743getOrNullimpl.getClass();
        OnUndeliveredElementKt.callUndeliveredElement(function1, objM2743getOrNullimpl, coroutineContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onCancellationImplDoNotCall(Throwable th, Object obj, CoroutineContext coroutineContext) {
        Function1 function1 = this.onUndeliveredElement;
        function1.getClass();
        OnUndeliveredElementKt.callUndeliveredElement(function1, obj, coroutineContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClosedReceiveCatchingOnNoWaiterSuspend(CancellableContinuation cancellableContinuation) {
        Result.Companion companion = Result.Companion;
        cancellableContinuation.resumeWith(Result.m2707constructorimpl(ChannelResult.m2739boximpl(ChannelResult.Companion.m2749closedJP2dKIU(getCloseCause()))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClosedReceiveOnNoWaiterSuspend(CancellableContinuation cancellableContinuation) {
        Result.Companion companion = Result.Companion;
        cancellableContinuation.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(getReceiveException())));
    }

    private final Object onClosedSend(Object obj, Continuation continuation) {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        Function1 function1 = this.onUndeliveredElement;
        if (function1 == null || (undeliveredElementExceptionCallUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, obj, null, 2, null)) == null) {
            Throwable sendException = getSendException();
            Result.Companion companion = Result.Companion;
            cancellableContinuationImpl.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(sendException)));
        } else {
            ExceptionsKt.addSuppressed(undeliveredElementExceptionCallUndeliveredElementCatchingException$default, getSendException());
            Result.Companion companion2 = Result.Companion;
            cancellableContinuationImpl.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(undeliveredElementExceptionCallUndeliveredElementCatchingException$default)));
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClosedSendOnNoWaiterSuspend(Object obj, CancellableContinuation cancellableContinuation) {
        Function1 function1 = this.onUndeliveredElement;
        if (function1 != null) {
            OnUndeliveredElementKt.callUndeliveredElement(function1, obj, cancellableContinuation.getContext());
        }
        Throwable sendException = getSendException();
        Result.Companion companion = Result.Companion;
        cancellableContinuation.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(sendException)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Function3 onUndeliveredElementReceiveCancellationConstructor$lambda$57$lambda$56(final BufferedChannel bufferedChannel, final SelectInstance selectInstance, Object obj, final Object obj2) {
        selectInstance.getClass();
        return new Function3(obj2, bufferedChannel, selectInstance) { // from class: kotlinx.coroutines.channels.BufferedChannel$$ExternalSyntheticLambda2
            public final /* synthetic */ Object f$0;
            public final /* synthetic */ BufferedChannel f$1;

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj3, Object obj4, Object obj5) {
                return BufferedChannel.onUndeliveredElementReceiveCancellationConstructor$lambda$57$lambda$56$lambda$55(this.f$0, this.f$1, null, (Throwable) obj3, obj4, (CoroutineContext) obj5);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onUndeliveredElementReceiveCancellationConstructor$lambda$57$lambda$56$lambda$55(Object obj, BufferedChannel bufferedChannel, SelectInstance selectInstance, Throwable th, Object obj2, CoroutineContext coroutineContext) {
        th.getClass();
        coroutineContext.getClass();
        if (obj != BufferedChannelKt.getCHANNEL_CLOSED()) {
            OnUndeliveredElementKt.callUndeliveredElement(bufferedChannel.onUndeliveredElement, obj, selectInstance.getContext());
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void prepareReceiverForSuspension(Waiter waiter, ChannelSegment channelSegment, int i) {
        onReceiveEnqueued();
        waiter.invokeOnCancellation(channelSegment, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void prepareSenderForSuspension(Waiter waiter, ChannelSegment channelSegment, int i) {
        waiter.invokeOnCancellation(channelSegment, i + BufferedChannelKt.SEGMENT_SIZE);
    }

    static /* synthetic */ Object receive$suspendImpl(BufferedChannel bufferedChannel, Continuation continuation) throws Throwable {
        ChannelSegment channelSegment;
        ChannelSegment channelSegment2 = (ChannelSegment) bufferedChannel.receiveSegment.getValue();
        while (!bufferedChannel.isClosedForReceive()) {
            long andIncrement = bufferedChannel.receivers.getAndIncrement();
            int i = BufferedChannelKt.SEGMENT_SIZE;
            long j = andIncrement / ((long) i);
            int i2 = (int) (andIncrement % ((long) i));
            if (channelSegment2.id != j) {
                ChannelSegment channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j, channelSegment2);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            } else {
                channelSegment = channelSegment2;
            }
            BufferedChannel bufferedChannel2 = bufferedChannel;
            Object objUpdateCellReceive = bufferedChannel2.updateCellReceive(channelSegment, i2, andIncrement, null);
            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                throw new IllegalStateException("unexpected");
            }
            if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    return bufferedChannel2.receiveOnNoWaiterSuspend(channelSegment, i2, andIncrement, continuation);
                }
                channelSegment.cleanPrev();
                return objUpdateCellReceive;
            }
            if (andIncrement < bufferedChannel2.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                channelSegment.cleanPrev();
            }
            bufferedChannel = bufferedChannel2;
            channelSegment2 = channelSegment;
        }
        throw StackTraceRecoveryKt.recoverStackTrace(bufferedChannel.getReceiveException());
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /* JADX INFO: renamed from: receiveCatching-JP2dKIU$suspendImpl, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static /* synthetic */ java.lang.Object m2732receiveCatchingJP2dKIU$suspendImpl(kotlinx.coroutines.channels.BufferedChannel r13, kotlin.coroutines.Continuation r14) {
        /*
            boolean r0 = r14 instanceof kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1
            if (r0 == 0) goto L14
            r0 = r14
            kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1 r0 = (kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r6 = r0
            goto L1a
        L14:
            kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1 r0 = new kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1
            r0.<init>(r13, r14)
            goto L12
        L1a:
            java.lang.Object r14 = r6.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r6.label
            r2 = 1
            if (r1 == 0) goto L39
            if (r1 != r2) goto L31
            kotlin.ResultKt.throwOnFailure(r14)
            kotlinx.coroutines.channels.ChannelResult r14 = (kotlinx.coroutines.channels.ChannelResult) r14
            java.lang.Object r13 = r14.m2748unboximpl()
            return r13
        L31:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L39:
            kotlin.ResultKt.throwOnFailure(r14)
            kotlinx.atomicfu.AtomicRef r14 = access$getReceiveSegment$p(r13)
            java.lang.Object r14 = r14.getValue()
            kotlinx.coroutines.channels.ChannelSegment r14 = (kotlinx.coroutines.channels.ChannelSegment) r14
        L46:
            boolean r1 = r13.isClosedForReceive()
            if (r1 == 0) goto L57
            kotlinx.coroutines.channels.ChannelResult$Companion r14 = kotlinx.coroutines.channels.ChannelResult.Companion
            java.lang.Throwable r13 = r13.getCloseCause()
            java.lang.Object r13 = r14.m2749closedJP2dKIU(r13)
            return r13
        L57:
            kotlinx.atomicfu.AtomicLong r1 = access$getReceivers$p(r13)
            long r4 = r1.getAndIncrement()
            int r1 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r7 = (long) r1
            long r7 = r4 / r7
            long r9 = (long) r1
            long r9 = r4 % r9
            int r3 = (int) r9
            long r9 = r14.id
            int r1 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r1 == 0) goto L77
            kotlinx.coroutines.channels.ChannelSegment r1 = access$findSegmentReceive(r13, r7, r14)
            if (r1 != 0) goto L75
            goto L46
        L75:
            r8 = r1
            goto L78
        L77:
            r8 = r14
        L78:
            r12 = 0
            r7 = r13
            r9 = r3
            r10 = r4
            java.lang.Object r13 = access$updateCellReceive(r7, r8, r9, r10, r12)
            r1 = r7
            kotlinx.coroutines.internal.Symbol r14 = kotlinx.coroutines.channels.BufferedChannelKt.access$getSUSPEND$p()
            if (r13 == r14) goto Lb6
            kotlinx.coroutines.internal.Symbol r14 = kotlinx.coroutines.channels.BufferedChannelKt.access$getFAILED$p()
            if (r13 != r14) goto L9b
            long r13 = r1.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()
            int r13 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r13 >= 0) goto L98
            r8.cleanPrev()
        L98:
            r13 = r1
            r14 = r8
            goto L46
        L9b:
            kotlinx.coroutines.internal.Symbol r14 = kotlinx.coroutines.channels.BufferedChannelKt.access$getSUSPEND_NO_WAITER$p()
            if (r13 != r14) goto Lac
            r6.label = r2
            r2 = r8
            java.lang.Object r13 = r1.m2733receiveCatchingOnNoWaiterSuspendGKJJFZk(r2, r3, r4, r6)
            if (r13 != r0) goto Lab
            return r0
        Lab:
            return r13
        Lac:
            r8.cleanPrev()
            kotlinx.coroutines.channels.ChannelResult$Companion r14 = kotlinx.coroutines.channels.ChannelResult.Companion
            java.lang.Object r13 = r14.m2751successJP2dKIU(r13)
            return r13
        Lb6:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "unexpected"
            r13.<init>(r14)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m2732receiveCatchingJP2dKIU$suspendImpl(kotlinx.coroutines.channels.BufferedChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX INFO: renamed from: receiveCatchingOnNoWaiterSuspend-GKJJFZk, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object m2733receiveCatchingOnNoWaiterSuspendGKJJFZk(kotlinx.coroutines.channels.ChannelSegment r11, int r12, long r13, kotlin.coroutines.Continuation r15) {
        /*
            Method dump skipped, instruction units count: 308
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m2733receiveCatchingOnNoWaiterSuspendGKJJFZk(kotlinx.coroutines.channels.ChannelSegment, int, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final Object receiveOnNoWaiterSuspend(ChannelSegment channelSegment, int i, long j, Continuation continuation) {
        Function3 function3;
        ChannelSegment channelSegment2;
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
        try {
            Object objUpdateCellReceive = updateCellReceive(channelSegment, i, j, orCreateCancellableContinuation);
            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                prepareReceiverForSuspension(orCreateCancellableContinuation, channelSegment, i);
            } else {
                if (objUpdateCellReceive == BufferedChannelKt.FAILED) {
                    if (j < getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        channelSegment.cleanPrev();
                    }
                    ChannelSegment channelSegment3 = (ChannelSegment) this.receiveSegment.getValue();
                    while (true) {
                        if (isClosedForReceive()) {
                            onClosedReceiveOnNoWaiterSuspend(orCreateCancellableContinuation);
                            break;
                        }
                        long andIncrement = this.receivers.getAndIncrement();
                        int i2 = BufferedChannelKt.SEGMENT_SIZE;
                        long j2 = andIncrement / ((long) i2);
                        int i3 = (int) (andIncrement % ((long) i2));
                        if (channelSegment3.id != j2) {
                            ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(j2, channelSegment3);
                            if (channelSegmentFindSegmentReceive != null) {
                                channelSegment2 = channelSegmentFindSegmentReceive;
                            }
                        } else {
                            channelSegment2 = channelSegment3;
                        }
                        objUpdateCellReceive = updateCellReceive(channelSegment2, i3, andIncrement, orCreateCancellableContinuation);
                        ChannelSegment channelSegment4 = channelSegment2;
                        if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                            CancellableContinuationImpl cancellableContinuationImpl = orCreateCancellableContinuation != null ? orCreateCancellableContinuation : null;
                            if (cancellableContinuationImpl != null) {
                                prepareReceiverForSuspension(cancellableContinuationImpl, channelSegment4, i3);
                            }
                        } else if (objUpdateCellReceive == BufferedChannelKt.FAILED) {
                            if (andIncrement < getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                channelSegment4.cleanPrev();
                            }
                            channelSegment3 = channelSegment4;
                        } else {
                            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                throw new IllegalStateException("unexpected");
                            }
                            channelSegment4.cleanPrev();
                            Function1 function1 = this.onUndeliveredElement;
                            function3 = (Function3) (function1 != null ? bindCancellationFun(function1) : null);
                        }
                    }
                } else {
                    channelSegment.cleanPrev();
                    Function1 function12 = this.onUndeliveredElement;
                    function3 = (Function3) (function12 != null ? bindCancellationFun(function12) : null);
                }
                orCreateCancellableContinuation.resume(objUpdateCellReceive, function3);
            }
            Object result = orCreateCancellableContinuation.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result;
        } catch (Throwable th) {
            orCreateCancellableContinuation.releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b3, code lost:
    
        r12 = (kotlinx.coroutines.channels.ChannelSegment) r12.getPrev();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void removeUnprocessedElements(kotlinx.coroutines.channels.ChannelSegment r12) {
        /*
            Method dump skipped, instruction units count: 224
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.removeUnprocessedElements(kotlinx.coroutines.channels.ChannelSegment):void");
    }

    private final void resumeReceiverOnClosedChannel(Waiter waiter) {
        resumeWaiterOnClosedChannel(waiter, true);
    }

    private final void resumeSenderOnCancelledChannel(Waiter waiter) {
        resumeWaiterOnClosedChannel(waiter, false);
    }

    private final void resumeWaiterOnClosedChannel(Waiter waiter, boolean z) {
        if (waiter instanceof CancellableContinuation) {
            Continuation continuation = (Continuation) waiter;
            Result.Companion companion = Result.Companion;
            continuation.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(z ? getReceiveException() : getSendException())));
        } else if (waiter instanceof ReceiveCatching) {
            CancellableContinuationImpl cancellableContinuationImpl = ((ReceiveCatching) waiter).cont;
            Result.Companion companion2 = Result.Companion;
            cancellableContinuationImpl.resumeWith(Result.m2707constructorimpl(ChannelResult.m2739boximpl(ChannelResult.Companion.m2749closedJP2dKIU(getCloseCause()))));
        } else {
            if (waiter instanceof BufferedChannelIterator) {
                ((BufferedChannelIterator) waiter).tryResumeHasNextOnClosedChannel();
                return;
            }
            throw new IllegalStateException(("Unexpected waiter: " + waiter).toString());
        }
    }

    static /* synthetic */ Object send$suspendImpl(BufferedChannel bufferedChannel, Object obj, Continuation continuation) {
        ChannelSegment channelSegment;
        ChannelSegment channelSegment2 = (ChannelSegment) bufferedChannel.sendSegment.getValue();
        while (true) {
            long andIncrement = bufferedChannel.sendersAndCloseStatus.getAndIncrement();
            long j = andIncrement & 1152921504606846975L;
            boolean zIsClosedForSend0 = bufferedChannel.isClosedForSend0(andIncrement);
            int i = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = j / ((long) i);
            int i2 = (int) (j % ((long) i));
            if (channelSegment2.id != j2) {
                ChannelSegment channelSegmentFindSegmentSend = bufferedChannel.findSegmentSend(j2, channelSegment2);
                if (channelSegmentFindSegmentSend != null) {
                    channelSegment = channelSegmentFindSegmentSend;
                } else if (zIsClosedForSend0) {
                    Object objOnClosedSend = bufferedChannel.onClosedSend(obj, continuation);
                    if (objOnClosedSend == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        return objOnClosedSend;
                    }
                }
            } else {
                channelSegment = channelSegment2;
            }
            BufferedChannel bufferedChannel2 = bufferedChannel;
            Object obj2 = obj;
            int iUpdateCellSend = bufferedChannel2.updateCellSend(channelSegment, i2, obj2, j, null, zIsClosedForSend0);
            if (iUpdateCellSend == 0) {
                channelSegment.cleanPrev();
                break;
            }
            if (iUpdateCellSend == 1) {
                break;
            }
            if (iUpdateCellSend != 2) {
                if (iUpdateCellSend == 3) {
                    Object objSendOnNoWaiterSuspend = bufferedChannel2.sendOnNoWaiterSuspend(channelSegment, i2, obj2, j, continuation);
                    if (objSendOnNoWaiterSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        return objSendOnNoWaiterSuspend;
                    }
                } else if (iUpdateCellSend != 4) {
                    if (iUpdateCellSend == 5) {
                        channelSegment.cleanPrev();
                    }
                    bufferedChannel = bufferedChannel2;
                    channelSegment2 = channelSegment;
                    obj = obj2;
                } else {
                    if (j < bufferedChannel2.getReceiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        channelSegment.cleanPrev();
                    }
                    Object objOnClosedSend2 = bufferedChannel2.onClosedSend(obj2, continuation);
                    if (objOnClosedSend2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        return objOnClosedSend2;
                    }
                }
            } else if (zIsClosedForSend0) {
                channelSegment.onSlotCleaned();
                Object objOnClosedSend3 = bufferedChannel2.onClosedSend(obj2, continuation);
                if (objOnClosedSend3 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    return objOnClosedSend3;
                }
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0104 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0105  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final java.lang.Object sendOnNoWaiterSuspend(kotlinx.coroutines.channels.ChannelSegment r16, int r17, java.lang.Object r18, long r19, kotlin.coroutines.Continuation r21) {
        /*
            Method dump skipped, instruction units count: 268
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.sendOnNoWaiterSuspend(kotlinx.coroutines.channels.ChannelSegment, int, java.lang.Object, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final boolean shouldSendSuspend(long j) {
        if (isClosedForSend0(j)) {
            return false;
        }
        return !bufferOrRendezvousSend(j & 1152921504606846975L);
    }

    private final boolean tryResumeReceiver(Object obj, Object obj2) {
        if (obj instanceof ReceiveCatching) {
            obj.getClass();
            CancellableContinuationImpl cancellableContinuationImpl = ((ReceiveCatching) obj).cont;
            ChannelResult channelResultM2739boximpl = ChannelResult.m2739boximpl(ChannelResult.Companion.m2751successJP2dKIU(obj2));
            Function1 function1 = this.onUndeliveredElement;
            return BufferedChannelKt.tryResume0(cancellableContinuationImpl, channelResultM2739boximpl, (Function3) (function1 != null ? bindCancellationFunResult(function1) : null));
        }
        if (obj instanceof BufferedChannelIterator) {
            obj.getClass();
            return ((BufferedChannelIterator) obj).tryResumeHasNext(obj2);
        }
        if (obj instanceof CancellableContinuation) {
            obj.getClass();
            CancellableContinuation cancellableContinuation = (CancellableContinuation) obj;
            Function1 function12 = this.onUndeliveredElement;
            return BufferedChannelKt.tryResume0(cancellableContinuation, obj2, (Function3) (function12 != null ? bindCancellationFun(function12) : null));
        }
        throw new IllegalStateException(("Unexpected receiver type: " + obj).toString());
    }

    private final boolean tryResumeSender(Object obj, ChannelSegment channelSegment, int i) {
        if (obj instanceof CancellableContinuation) {
            obj.getClass();
            return BufferedChannelKt.tryResume0$default((CancellableContinuation) obj, Unit.INSTANCE, null, 2, null);
        }
        throw new IllegalStateException(("Unexpected waiter: " + obj).toString());
    }

    private final boolean updateCellExpandBuffer(ChannelSegment channelSegment, int i, long j) {
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
        if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Waiter) || j < this.receivers.getValue() || !channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.RESUMING_BY_EB)) {
            return updateCellExpandBufferSlow(channelSegment, i, j);
        }
        if (tryResumeSender(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, channelSegment, i)) {
            channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.BUFFERED);
            return true;
        }
        channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.INTERRUPTED_SEND);
        channelSegment.onCancelledRequest(i, false);
        return false;
    }

    private final boolean updateCellExpandBufferSlow(ChannelSegment channelSegment, int i, long j) {
        while (true) {
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Waiter) {
                if (j < this.receivers.getValue()) {
                    if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, new WaiterEB((Waiter) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host))) {
                        return true;
                    }
                } else if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.RESUMING_BY_EB)) {
                    if (tryResumeSender(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, channelSegment, i)) {
                        channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.BUFFERED);
                        return true;
                    }
                    channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.INTERRUPTED_SEND);
                    channelSegment.onCancelledRequest(i, false);
                    return false;
                }
            } else {
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.INTERRUPTED_SEND) {
                    return false;
                }
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == null) {
                    if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.IN_BUFFER)) {
                        return true;
                    }
                } else {
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.BUFFERED || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.POISONED || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.DONE_RCV || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.INTERRUPTED_RCV || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.getCHANNEL_CLOSED()) {
                        return true;
                    }
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.RESUMING_BY_RCV) {
                        throw new IllegalStateException(("Unexpected cell state: " + state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).toString());
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object updateCellReceive(ChannelSegment channelSegment, int i, long j, Object obj) {
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
        if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == null) {
            if (j >= (this.sendersAndCloseStatus.getValue() & 1152921504606846975L)) {
                if (obj == null) {
                    return BufferedChannelKt.SUSPEND_NO_WAITER;
                }
                if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, obj)) {
                    expandBuffer();
                    return BufferedChannelKt.SUSPEND;
                }
            }
        } else if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.BUFFERED && channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.DONE_RCV)) {
            expandBuffer();
            return channelSegment.retrieveElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
        }
        return updateCellReceiveSlow(channelSegment, i, j, obj);
    }

    private final Object updateCellReceiveSlow(ChannelSegment channelSegment, int i, long j, Object obj) {
        while (true) {
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == null || state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.IN_BUFFER) {
                if (j < (this.sendersAndCloseStatus.getValue() & 1152921504606846975L)) {
                    if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.POISONED)) {
                        expandBuffer();
                        return BufferedChannelKt.FAILED;
                    }
                } else {
                    if (obj == null) {
                        return BufferedChannelKt.SUSPEND_NO_WAITER;
                    }
                    if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, obj)) {
                        expandBuffer();
                        return BufferedChannelKt.SUSPEND;
                    }
                }
            } else {
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.BUFFERED) {
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.INTERRUPTED_SEND && state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.POISONED) {
                        if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.getCHANNEL_CLOSED()) {
                            expandBuffer();
                            return BufferedChannelKt.FAILED;
                        }
                        if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.RESUMING_BY_EB && channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.RESUMING_BY_RCV)) {
                            boolean z = state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof WaiterEB;
                            if (z) {
                                state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ((WaiterEB) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).waiter;
                            }
                            if (tryResumeSender(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, channelSegment, i)) {
                                channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.DONE_RCV);
                                expandBuffer();
                                return channelSegment.retrieveElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
                            }
                            channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.INTERRUPTED_SEND);
                            channelSegment.onCancelledRequest(i, false);
                            if (z) {
                                expandBuffer();
                            }
                            return BufferedChannelKt.FAILED;
                        }
                    }
                    return BufferedChannelKt.FAILED;
                }
                if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.DONE_RCV)) {
                    expandBuffer();
                    return channelSegment.retrieveElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int updateCellSend(ChannelSegment channelSegment, int i, Object obj, long j, Object obj2, boolean z) {
        channelSegment.storeElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, obj);
        if (z) {
            return updateCellSendSlow(channelSegment, i, obj, j, obj2, z);
        }
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
        if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == null) {
            if (bufferOrRendezvousSend(j)) {
                if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, null, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else {
                if (obj2 == null) {
                    return 3;
                }
                if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, null, obj2)) {
                    return 2;
                }
            }
        } else if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Waiter) {
            channelSegment.cleanElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
            if (tryResumeReceiver(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, obj)) {
                channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.DONE_RCV);
                onReceiveDequeued();
                return 0;
            }
            if (channelSegment.getAndSetState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.INTERRUPTED_RCV) == BufferedChannelKt.INTERRUPTED_RCV) {
                return 5;
            }
            channelSegment.onCancelledRequest(i, true);
            return 5;
        }
        return updateCellSendSlow(channelSegment, i, obj, j, obj2, z);
    }

    private final int updateCellSendSlow(ChannelSegment channelSegment, int i, Object obj, long j, Object obj2, boolean z) {
        while (true) {
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = channelSegment.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == null) {
                if (!bufferOrRendezvousSend(j) || z) {
                    if (z) {
                        if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, null, BufferedChannelKt.INTERRUPTED_SEND)) {
                            channelSegment.onCancelledRequest(i, false);
                            return 4;
                        }
                    } else {
                        if (obj2 == null) {
                            return 3;
                        }
                        if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, null, obj2)) {
                            return 2;
                        }
                    }
                } else if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, null, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else {
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != BufferedChannelKt.IN_BUFFER) {
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.INTERRUPTED_RCV) {
                        channelSegment.cleanElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
                        return 5;
                    }
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.POISONED) {
                        channelSegment.cleanElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
                        return 5;
                    }
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == BufferedChannelKt.getCHANNEL_CLOSED()) {
                        channelSegment.cleanElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
                        completeCloseOrCancel();
                        return 4;
                    }
                    channelSegment.cleanElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
                    if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof WaiterEB) {
                        state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ((WaiterEB) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).waiter;
                    }
                    if (tryResumeReceiver(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, obj)) {
                        channelSegment.setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.DONE_RCV);
                        onReceiveDequeued();
                        return 0;
                    }
                    if (channelSegment.getAndSetState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, BufferedChannelKt.INTERRUPTED_RCV) != BufferedChannelKt.INTERRUPTED_RCV) {
                        channelSegment.onCancelledRequest(i, true);
                    }
                    return 5;
                }
                if (channelSegment.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i, state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            }
        }
    }

    private final void updateReceiversCounterIfLower(long j) {
        long value;
        AtomicLong atomicLong = this.receivers;
        do {
            value = atomicLong.getValue();
            if (value >= j) {
                return;
            }
        } while (!this.receivers.compareAndSet(value, j));
    }

    private final void updateSendersCounterIfLower(long j) {
        long value;
        long j2;
        AtomicLong atomicLong = this.sendersAndCloseStatus;
        do {
            value = atomicLong.getValue();
            j2 = 1152921504606846975L & value;
            if (j2 >= j) {
                return;
            }
        } while (!this.sendersAndCloseStatus.compareAndSet(value, BufferedChannelKt.constructSendersAndCloseStatus(j2, (int) (value >> 60))));
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final void cancel(CancellationException cancellationException) {
        cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(cancellationException);
    }

    public boolean cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Throwable th) {
        if (th == null) {
            th = new CancellationException("Channel was cancelled");
        }
        return closeOrCancelImpl(th, true);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean close(Throwable th) {
        return closeOrCancelImpl(th, false);
    }

    protected boolean closeOrCancelImpl(Throwable th, boolean z) {
        if (z) {
            markCancellationStarted();
        }
        boolean zCompareAndSet = this._closeCause.compareAndSet(BufferedChannelKt.NO_CLOSE_CAUSE, th);
        if (z) {
            markCancelled();
        } else {
            markClosed();
        }
        completeCloseOrCancel();
        onClosedIdempotent();
        if (zCompareAndSet) {
            invokeCloseHandler();
        }
        return zCompareAndSet;
    }

    protected final void dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(long j) {
        ChannelSegment channelSegment;
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
        ChannelSegment channelSegment2 = (ChannelSegment) this.receiveSegment.getValue();
        while (true) {
            long value = this.receivers.getValue();
            if (j < Math.max(((long) this.capacity) + value, this.getBufferEndCounter())) {
                return;
            }
            if (this.receivers.compareAndSet(value, 1 + value)) {
                int i = BufferedChannelKt.SEGMENT_SIZE;
                long j2 = value / ((long) i);
                int i2 = (int) (value % ((long) i));
                if (channelSegment2.id != j2) {
                    ChannelSegment channelSegmentFindSegmentReceive = this.findSegmentReceive(j2, channelSegment2);
                    if (channelSegmentFindSegmentReceive != null) {
                        channelSegment = channelSegmentFindSegmentReceive;
                    }
                } else {
                    channelSegment = channelSegment2;
                }
                BufferedChannel bufferedChannel = this;
                Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i2, value, null);
                if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                    channelSegment.cleanPrev();
                    Function1 function1 = bufferedChannel.onUndeliveredElement;
                    if (function1 != null && (undeliveredElementExceptionCallUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, objUpdateCellReceive, null, 2, null)) != null) {
                        throw undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
                    }
                } else if (value < bufferedChannel.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    channelSegment.cleanPrev();
                }
                this = bufferedChannel;
                channelSegment2 = channelSegment;
            }
            this = this;
        }
    }

    protected final Throwable getCloseCause() {
        return (Throwable) this._closeCause.getValue();
    }

    public final long getReceiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this.receivers.getValue();
    }

    protected final Throwable getSendException() {
        Throwable closeCause = getCloseCause();
        return closeCause == null ? new ClosedSendChannelException("Channel was closed") : closeCause;
    }

    public final long getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this.sendersAndCloseStatus.getValue() & 1152921504606846975L;
    }

    public final boolean hasElements$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        while (true) {
            ChannelSegment channelSegmentFindSegmentReceive = (ChannelSegment) this.receiveSegment.getValue();
            long receiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getReceiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            if (getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() <= receiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host) {
                return false;
            }
            int i = BufferedChannelKt.SEGMENT_SIZE;
            long j = receiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host / ((long) i);
            if (channelSegmentFindSegmentReceive.id == j || (channelSegmentFindSegmentReceive = findSegmentReceive(j, channelSegmentFindSegmentReceive)) != null) {
                channelSegmentFindSegmentReceive.cleanPrev();
                if (isCellNonEmpty(channelSegmentFindSegmentReceive, (int) (receiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host % ((long) i)), receiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host)) {
                    return true;
                }
                this.receivers.compareAndSet(receiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, 1 + receiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
            } else if (((ChannelSegment) this.receiveSegment.getValue()).id < j) {
                return false;
            }
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public void invokeOnClose(Function1 function1) {
        function1.getClass();
        if (this.closeHandler.compareAndSet(null, function1)) {
            return;
        }
        AtomicRef atomicRef = this.closeHandler;
        do {
            Object value = atomicRef.getValue();
            if (value != BufferedChannelKt.CLOSE_HANDLER_CLOSED) {
                if (value == BufferedChannelKt.CLOSE_HANDLER_INVOKED) {
                    throw new IllegalStateException("Another handler was already registered and successfully invoked");
                }
                throw new IllegalStateException(("Another handler is already registered: " + value).toString());
            }
        } while (!this.closeHandler.compareAndSet(BufferedChannelKt.CLOSE_HANDLER_CLOSED, BufferedChannelKt.CLOSE_HANDLER_INVOKED));
        function1.invoke(getCloseCause());
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public boolean isClosedForReceive() {
        return isClosedForReceive0(this.sendersAndCloseStatus.getValue());
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean isClosedForSend() {
        return isClosedForSend0(this.sendersAndCloseStatus.getValue());
    }

    protected boolean isConflatedDropOldest() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public boolean isEmpty() {
        if (isClosedForReceive() || hasElements$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
            return false;
        }
        return !isClosedForReceive();
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public ChannelIterator iterator() {
        return new BufferedChannelIterator();
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean offer(Object obj) {
        return Channel.DefaultImpls.offer(this, obj);
    }

    protected void onClosedIdempotent() {
    }

    protected void onReceiveDequeued() {
    }

    protected void onReceiveEnqueued() {
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public Object receive(Continuation continuation) {
        return receive$suspendImpl(this, continuation);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    /* JADX INFO: renamed from: receiveCatching-JP2dKIU, reason: not valid java name */
    public Object mo2734receiveCatchingJP2dKIU(Continuation continuation) {
        return m2732receiveCatchingJP2dKIU$suspendImpl(this, continuation);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public Object send(Object obj, Continuation continuation) {
        return send$suspendImpl(this, obj, continuation);
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x01aa, code lost:
    
        r4 = (kotlinx.coroutines.channels.ChannelSegment) r4.getNext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01b1, code lost:
    
        if (r4 != null) goto L80;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String toString() {
        /*
            Method dump skipped, instruction units count: 477
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.toString():java.lang.String");
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    /* JADX INFO: renamed from: tryReceive-PtdJZtk, reason: not valid java name */
    public Object mo2735tryReceivePtdJZtk() {
        ChannelSegment channelSegmentFindSegmentReceive;
        long value = this.receivers.getValue();
        long value2 = this.sendersAndCloseStatus.getValue();
        if (isClosedForReceive0(value2)) {
            return ChannelResult.Companion.m2749closedJP2dKIU(getCloseCause());
        }
        if (value >= (value2 & 1152921504606846975L)) {
            return ChannelResult.Companion.m2750failurePtdJZtk();
        }
        Object obj = BufferedChannelKt.INTERRUPTED_RCV;
        ChannelSegment channelSegment = (ChannelSegment) this.receiveSegment.getValue();
        while (!this.isClosedForReceive()) {
            long andIncrement = this.receivers.getAndIncrement();
            int i = BufferedChannelKt.SEGMENT_SIZE;
            long j = andIncrement / ((long) i);
            int i2 = (int) (andIncrement % ((long) i));
            if (channelSegment.id != j) {
                channelSegmentFindSegmentReceive = this.findSegmentReceive(j, channelSegment);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                }
            } else {
                channelSegmentFindSegmentReceive = channelSegment;
            }
            BufferedChannel bufferedChannel = this;
            Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegmentFindSegmentReceive, i2, andIncrement, obj);
            channelSegment = channelSegmentFindSegmentReceive;
            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                Waiter waiter = obj instanceof Waiter ? (Waiter) obj : null;
                if (waiter != null) {
                    bufferedChannel.prepareReceiverForSuspension(waiter, channelSegment, i2);
                }
                bufferedChannel.waitExpandBufferCompletion$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(andIncrement);
                channelSegment.onSlotCleaned();
                return ChannelResult.Companion.m2750failurePtdJZtk();
            }
            if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    throw new IllegalStateException("unexpected");
                }
                channelSegment.cleanPrev();
                return ChannelResult.Companion.m2751successJP2dKIU(objUpdateCellReceive);
            }
            if (andIncrement < bufferedChannel.getSendersCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                channelSegment.cleanPrev();
            }
            this = bufferedChannel;
        }
        return ChannelResult.Companion.m2749closedJP2dKIU(this.getCloseCause());
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    /* JADX INFO: renamed from: trySend-JP2dKIU, reason: not valid java name */
    public Object mo2736trySendJP2dKIU(Object obj) {
        Object obj2;
        int i;
        ChannelSegment channelSegment;
        BufferedChannel bufferedChannel;
        if (shouldSendSuspend(this.sendersAndCloseStatus.getValue())) {
            return ChannelResult.Companion.m2750failurePtdJZtk();
        }
        Object obj3 = BufferedChannelKt.INTERRUPTED_SEND;
        ChannelSegment channelSegment2 = (ChannelSegment) this.sendSegment.getValue();
        while (true) {
            long andIncrement = this.sendersAndCloseStatus.getAndIncrement();
            long j = andIncrement & 1152921504606846975L;
            boolean zIsClosedForSend0 = this.isClosedForSend0(andIncrement);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = j / ((long) i2);
            int i3 = (int) (j % ((long) i2));
            if (channelSegment2.id != j2) {
                ChannelSegment channelSegmentFindSegmentSend = this.findSegmentSend(j2, channelSegment2);
                if (channelSegmentFindSegmentSend != null) {
                    i = i3;
                    channelSegment = channelSegmentFindSegmentSend;
                    bufferedChannel = this;
                    obj2 = obj;
                } else if (zIsClosedForSend0) {
                    return ChannelResult.Companion.m2749closedJP2dKIU(this.getSendException());
                }
            } else {
                obj2 = obj;
                i = i3;
                channelSegment = channelSegment2;
                bufferedChannel = this;
            }
            int iUpdateCellSend = bufferedChannel.updateCellSend(channelSegment, i, obj2, j, obj3, zIsClosedForSend0);
            BufferedChannel bufferedChannel2 = bufferedChannel;
            channelSegment2 = channelSegment;
            if (iUpdateCellSend == 0) {
                channelSegment2.cleanPrev();
                return ChannelResult.Companion.m2751successJP2dKIU(Unit.INSTANCE);
            }
            if (iUpdateCellSend == 1) {
                return ChannelResult.Companion.m2751successJP2dKIU(Unit.INSTANCE);
            }
            if (iUpdateCellSend == 2) {
                if (zIsClosedForSend0) {
                    channelSegment2.onSlotCleaned();
                    return ChannelResult.Companion.m2749closedJP2dKIU(bufferedChannel2.getSendException());
                }
                Waiter waiter = obj3 instanceof Waiter ? (Waiter) obj3 : null;
                if (waiter != null) {
                    bufferedChannel2.prepareSenderForSuspension(waiter, channelSegment2, i);
                }
                channelSegment2.onSlotCleaned();
                return ChannelResult.Companion.m2750failurePtdJZtk();
            }
            if (iUpdateCellSend == 3) {
                throw new IllegalStateException("unexpected");
            }
            if (iUpdateCellSend == 4) {
                if (j < bufferedChannel2.getReceiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    channelSegment2.cleanPrev();
                }
                return ChannelResult.Companion.m2749closedJP2dKIU(bufferedChannel2.getSendException());
            }
            if (iUpdateCellSend == 5) {
                channelSegment2.cleanPrev();
            }
            this = bufferedChannel2;
            obj = obj2;
        }
    }

    /* JADX INFO: renamed from: trySendDropOldest-JP2dKIU, reason: not valid java name */
    protected final Object m2737trySendDropOldestJP2dKIU(Object obj) {
        ChannelSegment channelSegmentFindSegmentSend;
        int i;
        BufferedChannel bufferedChannel;
        Object obj2 = BufferedChannelKt.BUFFERED;
        ChannelSegment channelSegment = (ChannelSegment) this.sendSegment.getValue();
        while (true) {
            long andIncrement = this.sendersAndCloseStatus.getAndIncrement();
            long j = andIncrement & 1152921504606846975L;
            boolean zIsClosedForSend0 = this.isClosedForSend0(andIncrement);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = j / ((long) i2);
            int i3 = (int) (j % ((long) i2));
            if (channelSegment.id != j2) {
                channelSegmentFindSegmentSend = this.findSegmentSend(j2, channelSegment);
                if (channelSegmentFindSegmentSend != null) {
                    bufferedChannel = this;
                    i = i3;
                } else if (zIsClosedForSend0) {
                    return ChannelResult.Companion.m2749closedJP2dKIU(this.getSendException());
                }
            } else {
                channelSegmentFindSegmentSend = channelSegment;
                i = i3;
                bufferedChannel = this;
            }
            Object obj3 = obj;
            int iUpdateCellSend = bufferedChannel.updateCellSend(channelSegmentFindSegmentSend, i, obj3, j, obj2, zIsClosedForSend0);
            BufferedChannel bufferedChannel2 = bufferedChannel;
            channelSegment = channelSegmentFindSegmentSend;
            if (iUpdateCellSend == 0) {
                channelSegment.cleanPrev();
                return ChannelResult.Companion.m2751successJP2dKIU(Unit.INSTANCE);
            }
            if (iUpdateCellSend == 1) {
                return ChannelResult.Companion.m2751successJP2dKIU(Unit.INSTANCE);
            }
            if (iUpdateCellSend == 2) {
                if (zIsClosedForSend0) {
                    channelSegment.onSlotCleaned();
                    return ChannelResult.Companion.m2749closedJP2dKIU(bufferedChannel2.getSendException());
                }
                Waiter waiter = obj2 instanceof Waiter ? (Waiter) obj2 : null;
                if (waiter != null) {
                    bufferedChannel2.prepareSenderForSuspension(waiter, channelSegment, i);
                }
                bufferedChannel2.dropFirstElementUntilTheSpecifiedCellIsInTheBuffer((channelSegment.id * ((long) i2)) + ((long) i));
                return ChannelResult.Companion.m2751successJP2dKIU(Unit.INSTANCE);
            }
            if (iUpdateCellSend == 3) {
                throw new IllegalStateException("unexpected");
            }
            if (iUpdateCellSend == 4) {
                if (j < bufferedChannel2.getReceiversCounter$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    channelSegment.cleanPrev();
                }
                return ChannelResult.Companion.m2749closedJP2dKIU(bufferedChannel2.getSendException());
            }
            if (iUpdateCellSend == 5) {
                channelSegment.cleanPrev();
            }
            this = bufferedChannel2;
            obj = obj3;
        }
    }

    public final void waitExpandBufferCompletion$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(long j) {
        long value;
        long value2;
        if (isRendezvousOrUnlimited()) {
            return;
        }
        while (getBufferEndCounter() <= j) {
        }
        int i = BufferedChannelKt.EXPAND_BUFFER_COMPLETION_WAIT_ITERATIONS;
        for (int i2 = 0; i2 < i; i2++) {
            long bufferEndCounter = getBufferEndCounter();
            if (bufferEndCounter == (4611686018427387903L & this.completedExpandBuffersAndPauseFlag.getValue()) && bufferEndCounter == getBufferEndCounter()) {
                return;
            }
        }
        AtomicLong atomicLong = this.completedExpandBuffersAndPauseFlag;
        do {
            value = atomicLong.getValue();
        } while (!atomicLong.compareAndSet(value, BufferedChannelKt.constructEBCompletedAndPauseFlag(value & 4611686018427387903L, true)));
        while (true) {
            long bufferEndCounter2 = getBufferEndCounter();
            long value3 = this.completedExpandBuffersAndPauseFlag.getValue();
            long j2 = value3 & 4611686018427387903L;
            boolean z = (4611686018427387904L & value3) != 0;
            if (bufferEndCounter2 == j2 && bufferEndCounter2 == getBufferEndCounter()) {
                break;
            } else if (!z) {
                this.completedExpandBuffersAndPauseFlag.compareAndSet(value3, BufferedChannelKt.constructEBCompletedAndPauseFlag(j2, true));
            }
        }
        AtomicLong atomicLong2 = this.completedExpandBuffersAndPauseFlag;
        do {
            value2 = atomicLong2.getValue();
        } while (!atomicLong2.compareAndSet(value2, BufferedChannelKt.constructEBCompletedAndPauseFlag(value2 & 4611686018427387903L, false)));
    }
}
