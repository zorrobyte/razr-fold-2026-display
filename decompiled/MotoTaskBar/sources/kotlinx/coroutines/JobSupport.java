package kotlinx.coroutines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

/* JADX INFO: compiled from: JobSupport.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class JobSupport implements Job, ChildJob, ParentJob {
    private final AtomicRef _parentHandle;
    private final AtomicRef _state;

    /* JADX INFO: compiled from: JobSupport.kt */
    final class AwaitContinuation extends CancellableContinuationImpl {
        private final JobSupport job;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AwaitContinuation(Continuation continuation, JobSupport jobSupport) {
            super(continuation, 1);
            continuation.getClass();
            jobSupport.getClass();
            this.job = jobSupport;
        }

        @Override // kotlinx.coroutines.CancellableContinuationImpl
        public Throwable getContinuationCancellationCause(Job job) {
            Throwable rootCause;
            job.getClass();
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = this.job.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            return (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Finishing) || (rootCause = ((Finishing) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).getRootCause()) == null) ? state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof CompletedExceptionally ? ((CompletedExceptionally) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).cause : job.getCancellationException() : rootCause;
        }

        @Override // kotlinx.coroutines.CancellableContinuationImpl
        protected String nameString() {
            return "AwaitContinuation";
        }
    }

    /* JADX INFO: compiled from: JobSupport.kt */
    final class ChildCompletion extends JobNode {
        private final ChildHandleNode child;
        private final JobSupport parent;
        private final Object proposedUpdate;
        private final Finishing state;

        public ChildCompletion(JobSupport jobSupport, Finishing finishing, ChildHandleNode childHandleNode, Object obj) {
            jobSupport.getClass();
            finishing.getClass();
            childHandleNode.getClass();
            this.parent = jobSupport;
            this.state = finishing;
            this.child = childHandleNode;
            this.proposedUpdate = obj;
        }

        @Override // kotlinx.coroutines.JobNode
        public boolean getOnCancelling() {
            return false;
        }

        @Override // kotlinx.coroutines.JobNode
        public void invoke(Throwable th) {
            this.parent.continueCompleting(this.state, this.child, this.proposedUpdate);
        }
    }

    /* JADX INFO: compiled from: JobSupport.kt */
    final class Finishing implements Incomplete {
        private final AtomicRef _exceptionsHolder;
        private final AtomicBoolean _isCompleting;
        private final AtomicRef _rootCause;
        private final NodeList list;

        public Finishing(NodeList nodeList, boolean z, Throwable th) {
            nodeList.getClass();
            this.list = nodeList;
            this._isCompleting = AtomicFU.atomic(z);
            this._rootCause = AtomicFU.atomic(th);
            this._exceptionsHolder = AtomicFU.atomic((Object) null);
        }

        private final ArrayList allocateList() {
            return new ArrayList(4);
        }

        private final Object getExceptionsHolder() {
            return this._exceptionsHolder.getValue();
        }

        private final void setExceptionsHolder(Object obj) {
            this._exceptionsHolder.setValue(obj);
        }

        public final void addExceptionLocked(Throwable th) {
            th.getClass();
            Throwable rootCause = getRootCause();
            if (rootCause == null) {
                setRootCause(th);
                return;
            }
            if (th == rootCause) {
                return;
            }
            Object exceptionsHolder = getExceptionsHolder();
            if (exceptionsHolder == null) {
                setExceptionsHolder(th);
                return;
            }
            if (exceptionsHolder instanceof Throwable) {
                if (th == exceptionsHolder) {
                    return;
                }
                ArrayList arrayListAllocateList = allocateList();
                arrayListAllocateList.add(exceptionsHolder);
                arrayListAllocateList.add(th);
                setExceptionsHolder(arrayListAllocateList);
                return;
            }
            if (exceptionsHolder instanceof ArrayList) {
                ((ArrayList) exceptionsHolder).add(th);
                return;
            }
            throw new IllegalStateException(("State is " + exceptionsHolder).toString());
        }

        @Override // kotlinx.coroutines.Incomplete
        public NodeList getList() {
            return this.list;
        }

        public final Throwable getRootCause() {
            return (Throwable) this._rootCause.getValue();
        }

        @Override // kotlinx.coroutines.Incomplete
        public boolean isActive() {
            return getRootCause() == null;
        }

        public final boolean isCancelling() {
            return getRootCause() != null;
        }

        public final boolean isCompleting() {
            return this._isCompleting.getValue();
        }

        public final boolean isSealed() {
            return getExceptionsHolder() == JobSupportKt.SEALED;
        }

        public final List sealLocked(Throwable th) {
            ArrayList arrayListAllocateList;
            Object exceptionsHolder = getExceptionsHolder();
            if (exceptionsHolder == null) {
                arrayListAllocateList = allocateList();
            } else if (exceptionsHolder instanceof Throwable) {
                ArrayList arrayListAllocateList2 = allocateList();
                arrayListAllocateList2.add(exceptionsHolder);
                arrayListAllocateList = arrayListAllocateList2;
            } else {
                if (!(exceptionsHolder instanceof ArrayList)) {
                    throw new IllegalStateException(("State is " + exceptionsHolder).toString());
                }
                arrayListAllocateList = (ArrayList) exceptionsHolder;
            }
            Throwable rootCause = getRootCause();
            if (rootCause != null) {
                arrayListAllocateList.add(0, rootCause);
            }
            if (th != null && !Intrinsics.areEqual(th, rootCause)) {
                arrayListAllocateList.add(th);
            }
            setExceptionsHolder(JobSupportKt.SEALED);
            return arrayListAllocateList;
        }

        public final void setCompleting(boolean z) {
            this._isCompleting.setValue(z);
        }

        public final void setRootCause(Throwable th) {
            this._rootCause.setValue(th);
        }

        public String toString() {
            return "Finishing[cancelling=" + isCancelling() + ", completing=" + isCompleting() + ", rootCause=" + getRootCause() + ", exceptions=" + getExceptionsHolder() + ", list=" + getList() + "]";
        }
    }

    public JobSupport(boolean z) {
        this._state = AtomicFU.atomic(z ? JobSupportKt.EMPTY_ACTIVE : JobSupportKt.EMPTY_NEW);
        this._parentHandle = AtomicFU.atomic((Object) null);
    }

    private final void addSuppressedExceptions(Throwable th, List list) {
        if (list.size() <= 1) {
            return;
        }
        Set setNewSetFromMap = Collections.newSetFromMap(new IdentityHashMap(list.size()));
        setNewSetFromMap.getClass();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Throwable th2 = (Throwable) it.next();
            if (th2 != th && th2 != th && !(th2 instanceof CancellationException) && setNewSetFromMap.add(th2)) {
                kotlin.ExceptionsKt.addSuppressed(th, th2);
            }
        }
    }

    private final Object awaitSuspend(Continuation continuation) {
        AwaitContinuation awaitContinuation = new AwaitContinuation(IntrinsicsKt.intercepted(continuation), this);
        awaitContinuation.initCancellability();
        CancellableContinuationKt.disposeOnCancellation(awaitContinuation, JobKt__JobKt.invokeOnCompletion$default(this, false, new ResumeAwaitOnCompletion(awaitContinuation), 1, null));
        Object result = awaitContinuation.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    private final Object cancelMakeCompleting(Object obj) {
        Object objTryMakeCompleting;
        do {
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Incomplete) || ((state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Finishing) && ((Finishing) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).isCompleting())) {
                return JobSupportKt.COMPLETING_ALREADY;
            }
            objTryMakeCompleting = tryMakeCompleting(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, new CompletedExceptionally(createCauseException(obj), false, 2, null));
        } while (objTryMakeCompleting == JobSupportKt.COMPLETING_RETRY);
        return objTryMakeCompleting;
    }

    private final boolean cancelParent(Throwable th) {
        if (isScopedCoroutine()) {
            return true;
        }
        boolean z = th instanceof CancellationException;
        ChildHandle parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getParentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        return (parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == null || parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == NonDisposableHandle.INSTANCE) ? z : parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.childCancelled(th) || z;
    }

    private final void completeStateFinalization(Incomplete incomplete, Object obj) throws Throwable {
        ChildHandle parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getParentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        if (parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null) {
            parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.dispose();
            setParentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(NonDisposableHandle.INSTANCE);
        }
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        Throwable th = completedExceptionally != null ? completedExceptionally.cause : null;
        if (!(incomplete instanceof JobNode)) {
            NodeList list = incomplete.getList();
            if (list != null) {
                notifyCompletion(list, th);
                return;
            }
            return;
        }
        try {
            ((JobNode) incomplete).invoke(th);
        } catch (Throwable th2) {
            handleOnCompletionException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(new CompletionHandlerException("Exception in completion handler " + incomplete + " for " + this, th2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void continueCompleting(Finishing finishing, ChildHandleNode childHandleNode, Object obj) {
        ChildHandleNode childHandleNodeNextChild = nextChild(childHandleNode);
        if (childHandleNodeNextChild == null || !tryWaitForChild(finishing, childHandleNodeNextChild, obj)) {
            finishing.getList().close(2);
            ChildHandleNode childHandleNodeNextChild2 = nextChild(childHandleNode);
            if (childHandleNodeNextChild2 == null || !tryWaitForChild(finishing, childHandleNodeNextChild2, obj)) {
                afterCompletion(finalizeFinishingState(finishing, obj));
            }
        }
    }

    private final Throwable createCauseException(Object obj) {
        if (obj == null ? true : obj instanceof Throwable) {
            Throwable th = (Throwable) obj;
            return th == null ? new JobCancellationException(cancellationExceptionMessage(), null, this) : th;
        }
        obj.getClass();
        return ((ParentJob) obj).getChildJobCancellationCause();
    }

    private final Object finalizeFinishingState(Finishing finishing, Object obj) throws Throwable {
        boolean zIsCancelling;
        Throwable finalRootCause;
        DefaultConstructorMarker defaultConstructorMarker = null;
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        Throwable th = completedExceptionally != null ? completedExceptionally.cause : null;
        synchronized (finishing) {
            zIsCancelling = finishing.isCancelling();
            List listSealLocked = finishing.sealLocked(th);
            finalRootCause = getFinalRootCause(finishing, listSealLocked);
            if (finalRootCause != null) {
                addSuppressedExceptions(finalRootCause, listSealLocked);
            }
        }
        if (finalRootCause != null && finalRootCause != th) {
            obj = new CompletedExceptionally(finalRootCause, false, 2, defaultConstructorMarker);
        }
        if (finalRootCause != null && (cancelParent(finalRootCause) || handleJobException(finalRootCause))) {
            obj.getClass();
            ((CompletedExceptionally) obj).makeHandled();
        }
        if (!zIsCancelling) {
            onCancelling(finalRootCause);
        }
        onCompletionInternal(obj);
        this._state.compareAndSet(finishing, JobSupportKt.boxIncomplete(obj));
        completeStateFinalization(finishing, obj);
        return obj;
    }

    private final Throwable getExceptionOrNull(Object obj) {
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        if (completedExceptionally != null) {
            return completedExceptionally.cause;
        }
        return null;
    }

    private final Throwable getFinalRootCause(Finishing finishing, List list) {
        Object next;
        Object obj = null;
        if (list.isEmpty()) {
            if (finishing.isCancelling()) {
                return new JobCancellationException(cancellationExceptionMessage(), null, this);
            }
            return null;
        }
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (!(((Throwable) next) instanceof CancellationException)) {
                break;
            }
        }
        Throwable th = (Throwable) next;
        if (th != null) {
            return th;
        }
        Throwable th2 = (Throwable) list.get(0);
        if (th2 instanceof TimeoutCancellationException) {
            Iterator it2 = list.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object next2 = it2.next();
                Throwable th3 = (Throwable) next2;
                if (th3 != th2 && (th3 instanceof TimeoutCancellationException)) {
                    obj = next2;
                    break;
                }
            }
            Throwable th4 = (Throwable) obj;
            if (th4 != null) {
                return th4;
            }
        }
        return th2;
    }

    private final NodeList getOrPromoteCancellingList(Incomplete incomplete) {
        NodeList list = incomplete.getList();
        if (list != null) {
            return list;
        }
        if (incomplete instanceof Empty) {
            return new NodeList();
        }
        if (incomplete instanceof JobNode) {
            promoteSingleToNodeList((JobNode) incomplete);
            return null;
        }
        throw new IllegalStateException(("State should have list: " + incomplete).toString());
    }

    private final boolean joinInternal() {
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        do {
            state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Incomplete)) {
                return false;
            }
        } while (startInternal(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host) < 0);
        return true;
    }

    private final Object joinSuspend(Continuation continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        CancellableContinuationKt.disposeOnCancellation(cancellableContinuationImpl, JobKt__JobKt.invokeOnCompletion$default(this, false, new ResumeOnCompletion(cancellableContinuationImpl), 1, null));
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Object makeCancelling(Object obj) throws Throwable {
        Object[] objArr = 0;
        Throwable thCreateCauseException = null;
        while (true) {
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Finishing) {
                synchronized (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host) {
                    if (((Finishing) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).isSealed()) {
                        return JobSupportKt.TOO_LATE_TO_CANCEL;
                    }
                    boolean zIsCancelling = ((Finishing) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).isCancelling();
                    if (obj != null || !zIsCancelling) {
                        if (thCreateCauseException == null) {
                            thCreateCauseException = createCauseException(obj);
                        }
                        ((Finishing) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).addExceptionLocked(thCreateCauseException);
                    }
                    Throwable rootCause = zIsCancelling ? null : ((Finishing) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).getRootCause();
                    if (rootCause != null) {
                        notifyCancelling(((Finishing) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).getList(), rootCause);
                    }
                    return JobSupportKt.COMPLETING_ALREADY;
                }
            }
            if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Incomplete)) {
                return JobSupportKt.TOO_LATE_TO_CANCEL;
            }
            if (thCreateCauseException == null) {
                thCreateCauseException = createCauseException(obj);
            }
            Incomplete incomplete = (Incomplete) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
            if (!incomplete.isActive()) {
                Object objTryMakeCompleting = tryMakeCompleting(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, new CompletedExceptionally(thCreateCauseException, false, 2, objArr == true ? 1 : 0));
                if (objTryMakeCompleting == JobSupportKt.COMPLETING_ALREADY) {
                    throw new IllegalStateException(("Cannot happen in " + state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).toString());
                }
                if (objTryMakeCompleting != JobSupportKt.COMPLETING_RETRY) {
                    return objTryMakeCompleting;
                }
            } else if (tryMakeCancelling(incomplete, thCreateCauseException)) {
                return JobSupportKt.COMPLETING_ALREADY;
            }
        }
    }

    private final ChildHandleNode nextChild(LockFreeLinkedListNode lockFreeLinkedListNode) {
        while (lockFreeLinkedListNode.isRemoved()) {
            lockFreeLinkedListNode = lockFreeLinkedListNode.getPrevNode();
        }
        while (true) {
            lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode();
            if (!lockFreeLinkedListNode.isRemoved()) {
                if (lockFreeLinkedListNode instanceof ChildHandleNode) {
                    return (ChildHandleNode) lockFreeLinkedListNode;
                }
                if (lockFreeLinkedListNode instanceof NodeList) {
                    return null;
                }
            }
        }
    }

    private final void notifyCancelling(NodeList nodeList, Throwable th) throws Throwable {
        onCancelling(th);
        nodeList.close(4);
        Object next = nodeList.getNext();
        next.getClass();
        CompletionHandlerException completionHandlerException = null;
        for (LockFreeLinkedListNode nextNode = (LockFreeLinkedListNode) next; !Intrinsics.areEqual(nextNode, nodeList); nextNode = nextNode.getNextNode()) {
            if ((nextNode instanceof JobNode) && ((JobNode) nextNode).getOnCancelling()) {
                try {
                    ((JobNode) nextNode).invoke(th);
                } catch (Throwable th2) {
                    if (completionHandlerException != null) {
                        kotlin.ExceptionsKt.addSuppressed(completionHandlerException, th2);
                    } else {
                        completionHandlerException = new CompletionHandlerException("Exception in completion handler " + nextNode + " for " + this, th2);
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
        }
        if (completionHandlerException != null) {
            handleOnCompletionException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(completionHandlerException);
        }
        cancelParent(th);
    }

    private final void notifyCompletion(NodeList nodeList, Throwable th) throws Throwable {
        nodeList.close(1);
        Object next = nodeList.getNext();
        next.getClass();
        CompletionHandlerException completionHandlerException = null;
        for (LockFreeLinkedListNode nextNode = (LockFreeLinkedListNode) next; !Intrinsics.areEqual(nextNode, nodeList); nextNode = nextNode.getNextNode()) {
            if (nextNode instanceof JobNode) {
                try {
                    ((JobNode) nextNode).invoke(th);
                } catch (Throwable th2) {
                    if (completionHandlerException != null) {
                        kotlin.ExceptionsKt.addSuppressed(completionHandlerException, th2);
                    } else {
                        completionHandlerException = new CompletionHandlerException("Exception in completion handler " + nextNode + " for " + this, th2);
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
        }
        if (completionHandlerException != null) {
            handleOnCompletionException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(completionHandlerException);
        }
    }

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
    private final void promoteEmptyToNodeList(Empty empty) {
        NodeList nodeList = new NodeList();
        Object inactiveNodeList = nodeList;
        if (!empty.isActive()) {
            inactiveNodeList = new InactiveNodeList(nodeList);
        }
        this._state.compareAndSet(empty, inactiveNodeList);
    }

    private final void promoteSingleToNodeList(JobNode jobNode) {
        jobNode.addOneIfEmpty(new NodeList());
        this._state.compareAndSet(jobNode, jobNode.getNextNode());
    }

    private final int startInternal(Object obj) {
        if (obj instanceof Empty) {
            if (((Empty) obj).isActive()) {
                return 0;
            }
            if (!this._state.compareAndSet(obj, JobSupportKt.EMPTY_ACTIVE)) {
                return -1;
            }
            onStart();
            return 1;
        }
        if (!(obj instanceof InactiveNodeList)) {
            return 0;
        }
        if (!this._state.compareAndSet(obj, ((InactiveNodeList) obj).getList())) {
            return -1;
        }
        onStart();
        return 1;
    }

    private final String stateString(Object obj) {
        if (!(obj instanceof Finishing)) {
            return obj instanceof Incomplete ? ((Incomplete) obj).isActive() ? "Active" : "New" : obj instanceof CompletedExceptionally ? "Cancelled" : "Completed";
        }
        Finishing finishing = (Finishing) obj;
        return finishing.isCancelling() ? "Cancelling" : finishing.isCompleting() ? "Completing" : "Active";
    }

    public static /* synthetic */ CancellationException toCancellationException$default(JobSupport jobSupport, Throwable th, String str, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toCancellationException");
        }
        if ((i & 1) != 0) {
            str = null;
        }
        return jobSupport.toCancellationException(th, str);
    }

    private final boolean tryFinalizeSimpleState(Incomplete incomplete, Object obj) throws Throwable {
        if (!this._state.compareAndSet(incomplete, JobSupportKt.boxIncomplete(obj))) {
            return false;
        }
        onCancelling(null);
        onCompletionInternal(obj);
        completeStateFinalization(incomplete, obj);
        return true;
    }

    private final boolean tryMakeCancelling(Incomplete incomplete, Throwable th) throws Throwable {
        NodeList orPromoteCancellingList = getOrPromoteCancellingList(incomplete);
        if (orPromoteCancellingList == null) {
            return false;
        }
        if (!this._state.compareAndSet(incomplete, new Finishing(orPromoteCancellingList, false, th))) {
            return false;
        }
        notifyCancelling(orPromoteCancellingList, th);
        return true;
    }

    private final Object tryMakeCompleting(Object obj, Object obj2) {
        return !(obj instanceof Incomplete) ? JobSupportKt.COMPLETING_ALREADY : ((!(obj instanceof Empty) && !(obj instanceof JobNode)) || (obj instanceof ChildHandleNode) || (obj2 instanceof CompletedExceptionally)) ? tryMakeCompletingSlowPath((Incomplete) obj, obj2) : tryFinalizeSimpleState((Incomplete) obj, obj2) ? obj2 : JobSupportKt.COMPLETING_RETRY;
    }

    private final Object tryMakeCompletingSlowPath(Incomplete incomplete, Object obj) throws Throwable {
        NodeList orPromoteCancellingList = getOrPromoteCancellingList(incomplete);
        if (orPromoteCancellingList == null) {
            return JobSupportKt.COMPLETING_RETRY;
        }
        Finishing finishing = incomplete instanceof Finishing ? (Finishing) incomplete : null;
        if (finishing == null) {
            finishing = new Finishing(orPromoteCancellingList, false, null);
        }
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        synchronized (finishing) {
            if (finishing.isCompleting()) {
                return JobSupportKt.COMPLETING_ALREADY;
            }
            finishing.setCompleting(true);
            if (finishing != incomplete && !this._state.compareAndSet(incomplete, finishing)) {
                return JobSupportKt.COMPLETING_RETRY;
            }
            boolean zIsCancelling = finishing.isCancelling();
            CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
            if (completedExceptionally != null) {
                finishing.addExceptionLocked(completedExceptionally.cause);
            }
            Throwable rootCause = zIsCancelling ? null : finishing.getRootCause();
            ref$ObjectRef.element = rootCause;
            Unit unit = Unit.INSTANCE;
            if (rootCause != null) {
                notifyCancelling(orPromoteCancellingList, rootCause);
            }
            ChildHandleNode childHandleNodeNextChild = nextChild(orPromoteCancellingList);
            if (childHandleNodeNextChild != null && tryWaitForChild(finishing, childHandleNodeNextChild, obj)) {
                return JobSupportKt.COMPLETING_WAITING_CHILDREN;
            }
            orPromoteCancellingList.close(2);
            ChildHandleNode childHandleNodeNextChild2 = nextChild(orPromoteCancellingList);
            return (childHandleNodeNextChild2 == null || !tryWaitForChild(finishing, childHandleNodeNextChild2, obj)) ? finalizeFinishingState(finishing, obj) : JobSupportKt.COMPLETING_WAITING_CHILDREN;
        }
    }

    private final boolean tryWaitForChild(Finishing finishing, ChildHandleNode childHandleNode, Object obj) {
        while (JobKt.invokeOnCompletion(childHandleNode.childJob, false, new ChildCompletion(this, finishing, childHandleNode, obj)) == NonDisposableHandle.INSTANCE) {
            childHandleNode = nextChild(childHandleNode);
            if (childHandleNode == null) {
                return false;
            }
        }
        return true;
    }

    protected void afterCompletion(Object obj) {
    }

    @Override // kotlinx.coroutines.Job
    public final ChildHandle attachChild(ChildJob childJob) {
        childJob.getClass();
        ChildHandleNode childHandleNode = new ChildHandleNode(childJob);
        childHandleNode.setJob(this);
        while (true) {
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Empty) {
                Empty empty = (Empty) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
                if (!empty.isActive()) {
                    promoteEmptyToNodeList(empty);
                } else if (this._state.compareAndSet(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, childHandleNode)) {
                    return childHandleNode;
                }
            } else {
                if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Incomplete)) {
                    Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                    CompletedExceptionally completedExceptionally = state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 instanceof CompletedExceptionally ? (CompletedExceptionally) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 : null;
                    childHandleNode.invoke(completedExceptionally != null ? completedExceptionally.cause : null);
                    return NonDisposableHandle.INSTANCE;
                }
                NodeList list = ((Incomplete) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).getList();
                if (list != null) {
                    if (!list.addLast(childHandleNode, 7)) {
                        boolean zAddLast = list.addLast(childHandleNode, 3);
                        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host3 = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                        if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host3 instanceof Finishing) {
                            rootCause = ((Finishing) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host3).getRootCause();
                        } else {
                            CompletedExceptionally completedExceptionally2 = state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host3 instanceof CompletedExceptionally ? (CompletedExceptionally) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host3 : null;
                            if (completedExceptionally2 != null) {
                                rootCause = completedExceptionally2.cause;
                            }
                        }
                        childHandleNode.invoke(rootCause);
                        if (!zAddLast) {
                            return NonDisposableHandle.INSTANCE;
                        }
                    }
                    return childHandleNode;
                }
                state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.getClass();
                promoteSingleToNodeList((JobNode) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
            }
        }
    }

    protected final Object awaitInternal(Continuation continuation) throws Throwable {
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        do {
            state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Incomplete)) {
                if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof CompletedExceptionally) {
                    throw ((CompletedExceptionally) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).cause;
                }
                return JobSupportKt.unboxState(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
            }
        } while (startInternal(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host) < 0);
        return awaitSuspend(continuation);
    }

    @Override // kotlinx.coroutines.Job
    public void cancel(CancellationException cancellationException) throws Throwable {
        if (cancellationException == null) {
            cancellationException = new JobCancellationException(cancellationExceptionMessage(), null, this);
        }
        cancelInternal(cancellationException);
    }

    public final boolean cancelCoroutine(Throwable th) {
        return cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(th);
    }

    public final boolean cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) throws Throwable {
        Object objMakeCancelling = JobSupportKt.COMPLETING_ALREADY;
        if (getOnCancelComplete$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() && (objMakeCancelling = cancelMakeCompleting(obj)) == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return true;
        }
        if (objMakeCancelling == JobSupportKt.COMPLETING_ALREADY) {
            objMakeCancelling = makeCancelling(obj);
        }
        if (objMakeCancelling == JobSupportKt.COMPLETING_ALREADY || objMakeCancelling == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return true;
        }
        if (objMakeCancelling == JobSupportKt.TOO_LATE_TO_CANCEL) {
            return false;
        }
        afterCompletion(objMakeCancelling);
        return true;
    }

    public void cancelInternal(Throwable th) throws Throwable {
        th.getClass();
        cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(th);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String cancellationExceptionMessage() {
        return "Job was cancelled";
    }

    public boolean childCancelled(Throwable th) {
        th.getClass();
        if (th instanceof CancellationException) {
            return true;
        }
        return cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(th) && getHandlesException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
    }

    @Override // kotlin.coroutines.CoroutineContext
    public Object fold(Object obj, Function2 function2) {
        return Job.DefaultImpls.fold(this, obj, function2);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
    public CoroutineContext.Element get(CoroutineContext.Key key) {
        return Job.DefaultImpls.get(this, key);
    }

    @Override // kotlinx.coroutines.Job
    public final CancellationException getCancellationException() {
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Finishing)) {
            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Incomplete) {
                throw new IllegalStateException(("Job is still new or active: " + this).toString());
            }
            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof CompletedExceptionally) {
                return toCancellationException$default(this, ((CompletedExceptionally) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).cause, null, 1, null);
            }
            return new JobCancellationException(DebugStringsKt.getClassSimpleName(this) + " has completed normally", null, this);
        }
        Throwable rootCause = ((Finishing) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).getRootCause();
        if (rootCause != null) {
            CancellationException cancellationException = toCancellationException(rootCause, DebugStringsKt.getClassSimpleName(this) + " is cancelling");
            if (cancellationException != null) {
                return cancellationException;
            }
        }
        throw new IllegalStateException(("Job is still new or active: " + this).toString());
    }

    @Override // kotlinx.coroutines.ParentJob
    public CancellationException getChildJobCancellationCause() {
        Throwable rootCause;
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Finishing) {
            rootCause = ((Finishing) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).getRootCause();
        } else if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof CompletedExceptionally) {
            rootCause = ((CompletedExceptionally) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).cause;
        } else {
            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Incomplete) {
                throw new IllegalStateException(("Cannot be cancelling child in this state: " + state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).toString());
            }
            rootCause = null;
        }
        CancellationException cancellationException = rootCause instanceof CancellationException ? (CancellationException) rootCause : null;
        if (cancellationException != null) {
            return cancellationException;
        }
        return new JobCancellationException("Parent job is " + stateString(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host), rootCause, this);
    }

    @Override // kotlinx.coroutines.Job
    public final Sequence getChildren() {
        return SequencesKt.sequence(new JobSupport$children$1(this, null));
    }

    public boolean getHandlesException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return true;
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    public final CoroutineContext.Key getKey() {
        return Job.Key;
    }

    public boolean getOnCancelComplete$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return false;
    }

    public Job getParent() {
        ChildHandle parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getParentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        if (parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null) {
            return parentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.getParent();
        }
        return null;
    }

    public final ChildHandle getParentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return (ChildHandle) this._parentHandle.getValue();
    }

    public final Object getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this._state.getValue();
    }

    protected boolean handleJobException(Throwable th) {
        th.getClass();
        return false;
    }

    public void handleOnCompletionException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Throwable th) throws Throwable {
        th.getClass();
        throw th;
    }

    protected final void initParentJob(Job job) {
        if (job == null) {
            setParentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(NonDisposableHandle.INSTANCE);
            return;
        }
        job.start();
        ChildHandle childHandleAttachChild = job.attachChild(this);
        setParentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(childHandleAttachChild);
        if (isCompleted()) {
            childHandleAttachChild.dispose();
            setParentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(NonDisposableHandle.INSTANCE);
        }
    }

    @Override // kotlinx.coroutines.Job
    public final DisposableHandle invokeOnCompletion(Function1 function1) {
        function1.getClass();
        return invokeOnCompletionInternal$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(true, new InvokeOnCompletion(function1));
    }

    @Override // kotlinx.coroutines.Job
    public final DisposableHandle invokeOnCompletion(boolean z, boolean z2, Function1 function1) {
        function1.getClass();
        return invokeOnCompletionInternal$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(z2, z ? new InvokeOnCancelling(function1) : new InvokeOnCompletion(function1));
    }

    public final DisposableHandle invokeOnCompletionInternal$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(boolean z, JobNode jobNode) {
        boolean z2;
        boolean zAddLast;
        jobNode.getClass();
        jobNode.setJob(this);
        while (true) {
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            z2 = true;
            if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Empty)) {
                if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Incomplete)) {
                    z2 = false;
                    break;
                }
                Incomplete incomplete = (Incomplete) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
                NodeList list = incomplete.getList();
                if (list == null) {
                    state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.getClass();
                    promoteSingleToNodeList((JobNode) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
                } else {
                    if (jobNode.getOnCancelling()) {
                        Finishing finishing = incomplete instanceof Finishing ? (Finishing) incomplete : null;
                        Throwable rootCause = finishing != null ? finishing.getRootCause() : null;
                        if (rootCause != null) {
                            if (z) {
                                jobNode.invoke(rootCause);
                            }
                            return NonDisposableHandle.INSTANCE;
                        }
                        zAddLast = list.addLast(jobNode, 5);
                    } else {
                        zAddLast = list.addLast(jobNode, 1);
                    }
                    if (zAddLast) {
                        break;
                    }
                }
            } else {
                Empty empty = (Empty) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
                if (!empty.isActive()) {
                    promoteEmptyToNodeList(empty);
                } else if (this._state.compareAndSet(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, jobNode)) {
                    break;
                }
            }
        }
        if (z2) {
            return jobNode;
        }
        if (z) {
            Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            CompletedExceptionally completedExceptionally = state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 instanceof CompletedExceptionally ? (CompletedExceptionally) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host2 : null;
            jobNode.invoke(completedExceptionally != null ? completedExceptionally.cause : null);
        }
        return NonDisposableHandle.INSTANCE;
    }

    @Override // kotlinx.coroutines.Job
    public boolean isActive() {
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        return (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Incomplete) && ((Incomplete) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).isActive();
    }

    @Override // kotlinx.coroutines.Job
    public final boolean isCancelled() {
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof CompletedExceptionally) {
            return true;
        }
        return (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Finishing) && ((Finishing) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).isCancelling();
    }

    @Override // kotlinx.coroutines.Job
    public final boolean isCompleted() {
        return !(getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() instanceof Incomplete);
    }

    protected boolean isScopedCoroutine() {
        return false;
    }

    @Override // kotlinx.coroutines.Job
    public final Object join(Continuation continuation) {
        if (joinInternal()) {
            Object objJoinSuspend = joinSuspend(continuation);
            return objJoinSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objJoinSuspend : Unit.INSTANCE;
        }
        JobKt.ensureActive(continuation.getContext());
        return Unit.INSTANCE;
    }

    public final boolean makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        Object objTryMakeCompleting;
        do {
            objTryMakeCompleting = tryMakeCompleting(getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(), obj);
            if (objTryMakeCompleting == JobSupportKt.COMPLETING_ALREADY) {
                return false;
            }
            if (objTryMakeCompleting == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
                return true;
            }
        } while (objTryMakeCompleting == JobSupportKt.COMPLETING_RETRY);
        afterCompletion(objTryMakeCompleting);
        return true;
    }

    public final Object makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        Object objTryMakeCompleting;
        do {
            objTryMakeCompleting = tryMakeCompleting(getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(), obj);
            if (objTryMakeCompleting == JobSupportKt.COMPLETING_ALREADY) {
                throw new IllegalStateException("Job " + this + " is already complete or completing, but is being completed with " + obj, getExceptionOrNull(obj));
            }
        } while (objTryMakeCompleting == JobSupportKt.COMPLETING_RETRY);
        return objTryMakeCompleting;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext minusKey(CoroutineContext.Key key) {
        return Job.DefaultImpls.minusKey(this, key);
    }

    public String nameString$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return DebugStringsKt.getClassSimpleName(this);
    }

    protected void onCancelling(Throwable th) {
    }

    protected void onCompletionInternal(Object obj) {
    }

    protected void onStart() {
    }

    @Override // kotlinx.coroutines.ChildJob
    public final void parentCancelled(ParentJob parentJob) throws Throwable {
        parentJob.getClass();
        cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(parentJob);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext plus(CoroutineContext coroutineContext) {
        return Job.DefaultImpls.plus(this, coroutineContext);
    }

    public final void removeNode$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(JobNode jobNode) {
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        jobNode.getClass();
        do {
            state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof JobNode)) {
                if (!(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof Incomplete) || ((Incomplete) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).getList() == null) {
                    return;
                }
                jobNode.remove();
                return;
            }
            if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != jobNode) {
                return;
            }
        } while (!this._state.compareAndSet(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, JobSupportKt.EMPTY_ACTIVE));
    }

    public final void setParentHandle$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(ChildHandle childHandle) {
        this._parentHandle.setValue(childHandle);
    }

    @Override // kotlinx.coroutines.Job
    public final boolean start() {
        int iStartInternal;
        do {
            iStartInternal = startInternal(getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host());
            if (iStartInternal == 0) {
                return false;
            }
        } while (iStartInternal != 1);
        return true;
    }

    protected final CancellationException toCancellationException(Throwable th, String str) {
        th.getClass();
        CancellationException jobCancellationException = th instanceof CancellationException ? (CancellationException) th : null;
        if (jobCancellationException == null) {
            if (str == null) {
                str = cancellationExceptionMessage();
            }
            jobCancellationException = new JobCancellationException(str, th, this);
        }
        return jobCancellationException;
    }

    public final String toDebugString() {
        return nameString$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() + "{" + stateString(getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) + "}";
    }

    public String toString() {
        return toDebugString() + "@" + DebugStringsKt.getHexAddress(this);
    }
}
