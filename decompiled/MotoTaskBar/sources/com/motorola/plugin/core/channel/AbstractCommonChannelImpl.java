package com.motorola.plugin.core.channel;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.channel.AbstractCommonChannelImpl;
import com.motorola.plugin.core.channel.remote.RemoteChannelRequest;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.DeviceState;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.DisposableContainer;
import com.motorola.plugin.core.misc.DisposableKt;
import com.motorola.plugin.core.misc.FixedSizeCache;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.core.retry.Retryer;
import com.motorola.plugin.core.retry.RetryerBuilder;
import com.motorola.plugin.core.retry.StopStrategy;
import com.motorola.plugin.core.retry.WaitStrategy;
import com.motorola.plugin.core.utils.TimeoutRemoteCaller;
import com.motorola.plugin.sdk.channel.ClientId;
import com.motorola.plugin.sdk.channel.ConnectionException;
import com.motorola.plugin.sdk.channel.IDataSetChangedRegistry;
import com.motorola.plugin.sdk.channel.IRemoteCallback;
import com.motorola.plugin.sdk.channel.IRemoteChannel;
import com.motorola.plugin.sdk.channel.IRemoteChannelConnectionStatusCallback;
import com.motorola.plugin.sdk.channel.IRemoteChannelTransfer;
import com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo;
import com.motorola.plugin.sdk.channel.RemoteService;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

/* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractCommonChannelImpl implements IRemoteChannelExtension {
    private static final long AUTO_REBIND_SERVICE_DELAY_MS;
    private static final long AUTO_REBIND_SERVICE_DELAY_MS_LOW_RAM;
    private static final long AUTO_REBIND_SERVICE_DELAY_MS_RESTRICT;
    private static final long BIND_SERVICE_TIMEOUT_MS;
    private static final long CALL_SERVICE_TIMEOUT_MS;
    public static final Companion Companion = new Companion(null);
    private boolean _closed;
    private final String action;
    private final long bindServiceTimeout;
    private final ClientId clientId;
    private final Set connectionStatusCallbackList;
    private final Context context;
    private final DeviceState deviceState;
    private final AtomicLong disconnectTimes;
    private Job lastChannelTransferJob;
    private final AtomicReference lastDisconnectReason;
    private final Mutex lock;
    private final String logTag;
    private boolean mChannelStatePaused;
    private BitFlag mConnectionAliveRestrictedReason;
    private final DisposableContainer mDisposable;
    private final CommonChannelResponseHelper myChannelResponseHelper;
    private IDataSetChangedRegistryExtension myDataSetChangedRegistry;
    private final Lazy myRemoteChannelRequest$delegate;
    private final ClassLoader pluginClassLoader;
    private final PluginEvent pluginEvent;
    private final AtomicLong reconnectTimes;
    private final AtomicBoolean reconnecting;
    private final RemoteChannelCaller remoteChannelCaller;
    private IRemoteChannelTransfer remoteChannelTransfer;
    private final CoroutineScope remoteChannelTransferScope;
    private final Lazy retryCounter$delegate;
    private Job retryCounterJob;
    private final String token;
    private final long transferTimeoutPerAction;

    /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
    final class CommonChannelSnapshot extends AbstractSnapshot {
        private ISnapshot myDatasetChangedRegistry;
        private long myDisconnectTimes;
        private int myInstance;
        private boolean myIsClosed;
        private boolean myIsPaused;
        private int myKeepChannelConnectionAliveRestrictedReason;
        private String myLastDisconnectReason;
        public String myLastTransferJobStatus;
        public String myLockStatus;
        private long myReconnectTimes;
        private boolean myReconnecting;
        public String myRetryJobStatus;
        public ISnapshot myTimeoutCallerSnapshot;
        private boolean myTransferAlive;
        private boolean myTransferQueueActive;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CommonChannelSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
        }

        public final ISnapshot getMyDatasetChangedRegistry() {
            return this.myDatasetChangedRegistry;
        }

        public final long getMyDisconnectTimes() {
            return this.myDisconnectTimes;
        }

        public final int getMyInstance() {
            return this.myInstance;
        }

        public final boolean getMyIsClosed() {
            return this.myIsClosed;
        }

        public final boolean getMyIsPaused() {
            return this.myIsPaused;
        }

        public final int getMyKeepChannelConnectionAliveRestrictedReason() {
            return this.myKeepChannelConnectionAliveRestrictedReason;
        }

        public final String getMyLastDisconnectReason() {
            return this.myLastDisconnectReason;
        }

        public final String getMyLastTransferJobStatus() {
            String str = this.myLastTransferJobStatus;
            if (str != null) {
                return str;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myLastTransferJobStatus");
            throw null;
        }

        public final String getMyLockStatus() {
            String str = this.myLockStatus;
            if (str != null) {
                return str;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myLockStatus");
            throw null;
        }

        public final long getMyReconnectTimes() {
            return this.myReconnectTimes;
        }

        public final boolean getMyReconnecting() {
            return this.myReconnecting;
        }

        public final String getMyRetryJobStatus() {
            String str = this.myRetryJobStatus;
            if (str != null) {
                return str;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myRetryJobStatus");
            throw null;
        }

        public final ISnapshot getMyTimeoutCallerSnapshot() {
            ISnapshot iSnapshot = this.myTimeoutCallerSnapshot;
            if (iSnapshot != null) {
                return iSnapshot;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myTimeoutCallerSnapshot");
            throw null;
        }

        public final boolean getMyTransferAlive() {
            return this.myTransferAlive;
        }

        public final boolean getMyTransferQueueActive() {
            return this.myTransferQueueActive;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("Channel status", getMyInstance()).newLine();
            iPrinter.increaseIndent();
            iPrinter.printPair("closed", Boolean.valueOf(getMyIsClosed()));
            iPrinter.printPair("paused", Boolean.valueOf(getMyIsPaused()));
            iPrinter.printPair("lock", getMyLockStatus());
            iPrinter.printPair("reconnectTimes", Long.valueOf(getMyReconnectTimes()));
            iPrinter.printPair("reconnecting", Boolean.valueOf(getMyReconnecting()));
            iPrinter.printPair("disconnectTimes", Long.valueOf(getMyDisconnectTimes()));
            iPrinter.printPair("lastDisconnectReason", getMyLastDisconnectReason());
            iPrinter.printPair("transferQueueActive", Boolean.valueOf(getMyTransferQueueActive()));
            iPrinter.printPair("transferAlive", Boolean.valueOf(getMyTransferAlive()));
            iPrinter.printPair("transferJobStatus", getMyLastTransferJobStatus());
            iPrinter.printPair("retryJobStatus", getMyLastTransferJobStatus());
            iPrinter.newLine();
            if (getMyKeepChannelConnectionAliveRestrictedReason() == 0) {
                iPrinter.printPair("keepChannelConnectionAlive", Boolean.TRUE);
            } else {
                StringBuilder sb = new StringBuilder("keepChannelConnectionAlive restricted by: ");
                if ((getMyKeepChannelConnectionAliveRestrictedReason() & 2) != 0) {
                    sb.append("Host ");
                }
                if ((getMyKeepChannelConnectionAliveRestrictedReason() & 4) != 0) {
                    sb.append("Dataset ");
                }
                if ((getMyKeepChannelConnectionAliveRestrictedReason() & 8) != 0) {
                    sb.append("Plugin ");
                }
                String string = sb.toString();
                string.getClass();
                iPrinter.printSingle(string);
            }
            iPrinter.newLine();
            getMyTimeoutCallerSnapshot().onSnapshot(iPrinter);
            ISnapshot myDatasetChangedRegistry = getMyDatasetChangedRegistry();
            if (myDatasetChangedRegistry != null) {
                myDatasetChangedRegistry.onSnapshot(iPrinter);
            }
            iPrinter.decreaseIndent();
            iPrinter.newLine();
        }

        public final void setMyDatasetChangedRegistry(ISnapshot iSnapshot) {
            this.myDatasetChangedRegistry = iSnapshot;
        }

        public final void setMyDisconnectTimes(long j) {
            this.myDisconnectTimes = j;
        }

        public final void setMyInstance(int i) {
            this.myInstance = i;
        }

        public final void setMyIsClosed(boolean z) {
            this.myIsClosed = z;
        }

        public final void setMyIsPaused(boolean z) {
            this.myIsPaused = z;
        }

        public final void setMyKeepChannelConnectionAliveRestrictedReason(int i) {
            this.myKeepChannelConnectionAliveRestrictedReason = i;
        }

        public final void setMyLastDisconnectReason(String str) {
            this.myLastDisconnectReason = str;
        }

        public final void setMyLastTransferJobStatus(String str) {
            str.getClass();
            this.myLastTransferJobStatus = str;
        }

        public final void setMyLockStatus(String str) {
            str.getClass();
            this.myLockStatus = str;
        }

        public final void setMyReconnectTimes(long j) {
            this.myReconnectTimes = j;
        }

        public final void setMyReconnecting(boolean z) {
            this.myReconnecting = z;
        }

        public final void setMyRetryJobStatus(String str) {
            str.getClass();
            this.myRetryJobStatus = str;
        }

        public final void setMyTimeoutCallerSnapshot(ISnapshot iSnapshot) {
            iSnapshot.getClass();
            this.myTimeoutCallerSnapshot = iSnapshot;
        }

        public final void setMyTransferAlive(boolean z) {
            this.myTransferAlive = z;
        }

        public final void setMyTransferQueueActive(boolean z) {
            this.myTransferQueueActive = z;
        }
    }

    /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
    final class RemoteChannelCaller implements Disposable, ISnapshotAware {
        private final FixedSizeCache actionTimeRecords;
        private final WeakReference classLoaderRef;
        private final Mutex lock;
        private final String logTag;
        private int mSequenceCounter;
        private final long timeout;
        private final String token;

        /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
        final class ActionRecord {
            private final String action;
            private final long requestTime;
            private long responseTime;
            private final int sequence;

            public ActionRecord(String str, int i, long j) {
                str.getClass();
                this.action = str;
                this.sequence = i;
                this.requestTime = j;
                this.responseTime = -1L;
            }

            public static /* synthetic */ ActionRecord copy$default(ActionRecord actionRecord, String str, int i, long j, int i2, Object obj) {
                if ((i2 & 1) != 0) {
                    str = actionRecord.action;
                }
                if ((i2 & 2) != 0) {
                    i = actionRecord.sequence;
                }
                if ((i2 & 4) != 0) {
                    j = actionRecord.requestTime;
                }
                return actionRecord.copy(str, i, j);
            }

            public final String component1() {
                return this.action;
            }

            public final int component2() {
                return this.sequence;
            }

            public final long component3() {
                return this.requestTime;
            }

            public final ActionRecord copy(String str, int i, long j) {
                str.getClass();
                return new ActionRecord(str, i, j);
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof ActionRecord)) {
                    return false;
                }
                ActionRecord actionRecord = (ActionRecord) obj;
                return Intrinsics.areEqual(this.action, actionRecord.action) && this.sequence == actionRecord.sequence && this.requestTime == actionRecord.requestTime;
            }

            public final String getAction() {
                return this.action;
            }

            public final long getRequestTime() {
                return this.requestTime;
            }

            public final long getResponseTime() {
                return this.responseTime;
            }

            public final int getSequence() {
                return this.sequence;
            }

            public int hashCode() {
                return (((this.action.hashCode() * 31) + Integer.hashCode(this.sequence)) * 31) + Long.hashCode(this.requestTime);
            }

            public final void setResponseTime(long j) {
                this.responseTime = j;
            }

            public String toString() {
                return "ActionRecord(action=" + this.action + ", sequence=" + this.sequence + ", requestTime=" + this.requestTime + ')';
            }
        }

        /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
        final class IRemoteCallbackReference extends IRemoteCallback.Stub {
            private final WeakReference classLoaderRef;
            private final WeakReference continuationRef;
            private final String logTag;
            private final WeakReference recordRef;
            private final String token;

            public IRemoteCallbackReference(WeakReference weakReference, CancellableContinuation cancellableContinuation, ActionRecord actionRecord, String str, String str2) {
                weakReference.getClass();
                cancellableContinuation.getClass();
                actionRecord.getClass();
                str.getClass();
                str2.getClass();
                this.classLoaderRef = weakReference;
                this.token = str;
                this.logTag = str2;
                this.continuationRef = new WeakReference(cancellableContinuation);
                this.recordRef = new WeakReference(actionRecord);
            }

            @Override // com.motorola.plugin.sdk.channel.IRemoteCallback
            public void sendResult(Bundle bundle) {
                bundle.getClass();
                ClassLoader classLoader = (ClassLoader) this.classLoaderRef.get();
                CancellableContinuation cancellableContinuation = (CancellableContinuation) this.continuationRef.get();
                final ActionRecord actionRecord = (ActionRecord) this.recordRef.get();
                if (classLoader == null || cancellableContinuation == null || !cancellableContinuation.isActive()) {
                    PluginConfigKt.trace$default(this.logTag, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$IRemoteCallbackReference$sendResult$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* JADX INFO: renamed from: invoke */
                        public final Object mo2224invoke() {
                            StringBuilder sb = new StringBuilder();
                            sb.append('[');
                            sb.append(this.this$0.token);
                            sb.append("][");
                            AbstractCommonChannelImpl.RemoteChannelCaller.ActionRecord actionRecord2 = actionRecord;
                            sb.append(actionRecord2 == null ? null : Integer.valueOf(actionRecord2.getSequence()));
                            sb.append("] received results, but the source had been canceled");
                            sb.append(actionRecord == null ? "" : Intrinsics.stringPlus(", response time(ms): ", Long.valueOf(System.currentTimeMillis() - actionRecord.getRequestTime())));
                            return sb.toString();
                        }
                    }, 60, null);
                    if (cancellableContinuation == null) {
                        return;
                    }
                    DeadObjectException deadObjectException = new DeadObjectException();
                    Result.Companion companion = Result.Companion;
                    cancellableContinuation.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(deadObjectException)));
                    return;
                }
                bundle.setClassLoader(classLoader);
                final int i = bundle.getInt(RemoteService.EXTRA_SEQUENCE, -1);
                if (actionRecord != null) {
                    actionRecord.setResponseTime(System.currentTimeMillis());
                }
                PluginConfigKt.trace$default(this.logTag, Level.INFO, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$IRemoteCallbackReference$sendResult$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        String strValueOf;
                        StringBuilder sb = new StringBuilder();
                        sb.append('[');
                        sb.append(this.this$0.token);
                        sb.append("][");
                        AbstractCommonChannelImpl.RemoteChannelCaller.ActionRecord actionRecord2 = actionRecord;
                        sb.append(actionRecord2 == null ? null : Integer.valueOf(actionRecord2.getSequence()));
                        sb.append("] received remote channel service seq:");
                        sb.append(i);
                        sb.append(" results, response time(ms): ");
                        AbstractCommonChannelImpl.RemoteChannelCaller.ActionRecord actionRecord3 = actionRecord;
                        if (actionRecord3 == null) {
                            strValueOf = "released record";
                        } else if (actionRecord3.getSequence() != i) {
                            strValueOf = "mismatched sequence req: " + actionRecord.getSequence() + " vs res: " + i;
                        } else {
                            strValueOf = String.valueOf(actionRecord.getResponseTime() - actionRecord.getRequestTime());
                        }
                        sb.append(strValueOf);
                        return sb.toString();
                    }
                }, 56, null);
                cancellableContinuation.resumeWith(Result.m2707constructorimpl(bundle));
            }
        }

        /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
        public final class RemoteChannelCallTimeoutException extends CancellationException {
            private final int sequence;

            public RemoteChannelCallTimeoutException(int i, long j) {
                super('[' + i + "] Timed out waiting for " + j + " ms");
                this.sequence = i;
            }

            public final int getSequence() {
                return this.sequence;
            }
        }

        /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
        final class RemoteChannelCallerSnapshot extends AbstractSnapshot {
            public Map myActionTimeRecords;
            private int myCallerInstance;
            private int mySequenceCounter;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public RemoteChannelCallerSnapshot(ISnapshot iSnapshot) {
                super(iSnapshot);
                iSnapshot.getClass();
            }

            public final Map getMyActionTimeRecords() {
                Map map = this.myActionTimeRecords;
                if (map != null) {
                    return map;
                }
                Intrinsics.throwUninitializedPropertyAccessException("myActionTimeRecords");
                throw null;
            }

            public final int getMyCallerInstance() {
                return this.myCallerInstance;
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
                iPrinter.printPair("next sequence", Integer.valueOf(getMySequenceCounter()));
                iPrinter.newLine();
                iPrinter.printPair("possible action histories size", Integer.valueOf(getMyActionTimeRecords().size()));
                iPrinter.newLine();
                int i = 0;
                for (Object obj : getMyActionTimeRecords().keySet()) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    ActionRecord actionRecord = (ActionRecord) obj;
                    iPrinter.increaseIndent();
                    IPrinter iPrinter2 = iPrinter;
                    IPrinter.DefaultImpls.printIndex$default(iPrinter2, i, "", null, 4, null);
                    iPrinter2.printPair("sequence", Integer.valueOf(actionRecord.getSequence()));
                    iPrinter2.printPair("action", actionRecord.getAction());
                    iPrinter2.printPair("requestTime", ExtensionsKt.timestampWithZone(actionRecord.getRequestTime()));
                    iPrinter2.printPair("responseTime", ExtensionsKt.timestampWithZone(actionRecord.getResponseTime()));
                    iPrinter2.printPair("cost(ms)", actionRecord.getResponseTime() > 0 ? Long.valueOf(actionRecord.getResponseTime() - actionRecord.getRequestTime()) : "x no response");
                    iPrinter2.newLine();
                    iPrinter2.decreaseIndent();
                    iPrinter = iPrinter2;
                    i = i2;
                }
                iPrinter.decreaseIndent();
            }

            public final void setMyActionTimeRecords(Map map) {
                map.getClass();
                this.myActionTimeRecords = map;
            }

            public final void setMyCallerInstance(int i) {
                this.myCallerInstance = i;
            }

            public final void setMySequenceCounter(int i) {
                this.mySequenceCounter = i;
            }
        }

        public RemoteChannelCaller(ClassLoader classLoader, Mutex mutex, String str, String str2, long j) {
            classLoader.getClass();
            mutex.getClass();
            str.getClass();
            str2.getClass();
            this.lock = mutex;
            this.token = str;
            this.logTag = str2;
            this.timeout = j;
            this.classLoaderRef = new WeakReference(classLoader);
            this.actionTimeRecords = new FixedSizeCache(500);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Object executeRemoteCall(IRemoteChannelTransfer iRemoteChannelTransfer, RemoteChannelRequestInfo remoteChannelRequestInfo, final ActionRecord actionRecord, Continuation continuation) throws RemoteException {
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
            cancellableContinuationImpl.initCancellability();
            if (iRemoteChannelTransfer.asBinder().isBinderAlive()) {
                iRemoteChannelTransfer.transferRequest(remoteChannelRequestInfo, actionRecord.getSequence(), new IRemoteCallbackReference(this.classLoaderRef, cancellableContinuationImpl, actionRecord, this.token, this.logTag));
            } else {
                actionRecord.setResponseTime(System.currentTimeMillis());
                PluginConfigKt.trace$default(this.logTag, Level.WARN, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$executeRemoteCall$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return '[' + this.this$0.token + "][" + actionRecord.getSequence() + "] Execute remote call, but no valid target for execute";
                    }
                }, 56, null);
                DeadObjectException deadObjectException = new DeadObjectException();
                Result.Companion companion = Result.Companion;
                cancellableContinuationImpl.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(deadObjectException)));
            }
            Object result = cancellableContinuationImpl.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object generateActionRecord(com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo r17, kotlin.coroutines.Continuation r18) {
            /*
                Method dump skipped, instruction units count: 231
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.RemoteChannelCaller.generateActionRecord(com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo, kotlin.coroutines.Continuation):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object performRemoteCall(com.motorola.plugin.sdk.channel.IRemoteChannelTransfer r12, com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo r13, com.motorola.plugin.core.channel.AbstractCommonChannelImpl.RemoteChannelCaller.ActionRecord r14, kotlin.coroutines.Continuation r15) throws com.motorola.plugin.core.channel.AbstractCommonChannelImpl.RemoteChannelCaller.RemoteChannelCallTimeoutException {
            /*
                r11 = this;
                boolean r2 = r15 instanceof com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$performRemoteCall$1
                if (r2 == 0) goto L14
                r2 = r15
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$performRemoteCall$1 r2 = (com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$performRemoteCall$1) r2
                int r3 = r2.label
                r4 = -2147483648(0xffffffff80000000, float:-0.0)
                r5 = r3 & r4
                if (r5 == 0) goto L14
                int r3 = r3 - r4
                r2.label = r3
            L12:
                r6 = r2
                goto L1a
            L14:
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$performRemoteCall$1 r2 = new com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$performRemoteCall$1
                r2.<init>(r11, r15)
                goto L12
            L1a:
                java.lang.Object r0 = r6.result
                java.lang.Object r7 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r6.label
                r8 = 1
                if (r2 == 0) goto L40
                if (r2 != r8) goto L38
                java.lang.Object r1 = r6.L$1
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$ActionRecord r1 = (com.motorola.plugin.core.channel.AbstractCommonChannelImpl.RemoteChannelCaller.ActionRecord) r1
                java.lang.Object r2 = r6.L$0
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller r2 = (com.motorola.plugin.core.channel.AbstractCommonChannelImpl.RemoteChannelCaller) r2
                kotlin.ResultKt.throwOnFailure(r0)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L35
                r3 = r1
                r1 = r2
                goto L5e
            L35:
                r3 = r1
                r1 = r2
                goto L63
            L38:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L40:
                kotlin.ResultKt.throwOnFailure(r0)
                long r9 = r11.timeout     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L61
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$performRemoteCall$2 r0 = new com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$performRemoteCall$2     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L61
                r5 = 0
                r1 = r11
                r4 = r12
                r2 = r13
                r3 = r14
                r0.<init>(r1, r2, r3, r4, r5)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L61
                r6.L$0 = r11     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L61
                r6.L$1 = r14     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L61
                r6.label = r8     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L61
                java.lang.Object r0 = kotlinx.coroutines.TimeoutKt.withTimeout(r9, r0, r6)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L61
                if (r0 != r7) goto L5c
                return r7
            L5c:
                r1 = r11
                r3 = r14
            L5e:
                android.os.Bundle r0 = (android.os.Bundle) r0     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L63
                return r0
            L61:
                r1 = r11
                r3 = r14
            L63:
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$RemoteChannelCallTimeoutException r0 = new com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$RemoteChannelCallTimeoutException
                int r2 = r3.getSequence()
                long r3 = r1.timeout
                r0.<init>(r2, r3)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.RemoteChannelCaller.performRemoteCall(com.motorola.plugin.sdk.channel.IRemoteChannelTransfer, com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo, com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$ActionRecord, kotlin.coroutines.Continuation):java.lang.Object");
        }

        @Override // com.motorola.plugin.core.misc.Disposable
        public void dispose() {
            this.classLoaderRef.clear();
            this.actionTimeRecords.clear();
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object makeRemoteCall(com.motorola.plugin.sdk.channel.IRemoteChannelTransfer r6, com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo r7, kotlin.coroutines.Continuation r8) throws com.motorola.plugin.core.channel.AbstractCommonChannelImpl.RemoteChannelCaller.RemoteChannelCallTimeoutException, android.os.RemoteException {
            /*
                r5 = this;
                boolean r0 = r8 instanceof com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$makeRemoteCall$1
                if (r0 == 0) goto L13
                r0 = r8
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$makeRemoteCall$1 r0 = (com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$makeRemoteCall$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$makeRemoteCall$1 r0 = new com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$makeRemoteCall$1
                r0.<init>(r5, r8)
            L18:
                java.lang.Object r8 = r0.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r0.label
                r3 = 2
                r4 = 1
                if (r2 == 0) goto L46
                if (r2 == r4) goto L34
                if (r2 != r3) goto L2c
                kotlin.ResultKt.throwOnFailure(r8)
                return r8
            L2c:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L34:
                java.lang.Object r5 = r0.L$2
                r7 = r5
                com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo r7 = (com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo) r7
                java.lang.Object r5 = r0.L$1
                r6 = r5
                com.motorola.plugin.sdk.channel.IRemoteChannelTransfer r6 = (com.motorola.plugin.sdk.channel.IRemoteChannelTransfer) r6
                java.lang.Object r5 = r0.L$0
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller r5 = (com.motorola.plugin.core.channel.AbstractCommonChannelImpl.RemoteChannelCaller) r5
                kotlin.ResultKt.throwOnFailure(r8)
                goto L58
            L46:
                kotlin.ResultKt.throwOnFailure(r8)
                r0.L$0 = r5
                r0.L$1 = r6
                r0.L$2 = r7
                r0.label = r4
                java.lang.Object r8 = r5.generateActionRecord(r7, r0)
                if (r8 != r1) goto L58
                goto L69
            L58:
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$ActionRecord r8 = (com.motorola.plugin.core.channel.AbstractCommonChannelImpl.RemoteChannelCaller.ActionRecord) r8
                r2 = 0
                r0.L$0 = r2
                r0.L$1 = r2
                r0.L$2 = r2
                r0.label = r3
                java.lang.Object r5 = r5.performRemoteCall(r6, r7, r8, r0)
                if (r5 != r1) goto L6a
            L69:
                return r1
            L6a:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.RemoteChannelCaller.makeRemoteCall(com.motorola.plugin.sdk.channel.IRemoteChannelTransfer, com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo, kotlin.coroutines.Continuation):java.lang.Object");
        }

        @Override // com.motorola.plugin.core.misc.ISnapshotAware
        public ISnapshot snapshot(ISnapshot iSnapshot) {
            iSnapshot.getClass();
            RemoteChannelCallerSnapshot remoteChannelCallerSnapshot = new RemoteChannelCallerSnapshot(iSnapshot);
            remoteChannelCallerSnapshot.setMyCallerInstance(hashCode());
            if (!Mutex.DefaultImpls.tryLock$default(this.lock, null, 1, null)) {
                remoteChannelCallerSnapshot.setMySequenceCounter(-1);
                remoteChannelCallerSnapshot.setMyActionTimeRecords(MapsKt.emptyMap());
                return remoteChannelCallerSnapshot;
            }
            remoteChannelCallerSnapshot.setMySequenceCounter(this.mSequenceCounter);
            remoteChannelCallerSnapshot.setMyActionTimeRecords(MapsKt.toMap(this.actionTimeRecords));
            Mutex.DefaultImpls.unlock$default(this.lock, null, 1, null);
            return remoteChannelCallerSnapshot;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannel$1, reason: invalid class name */
    /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AbstractCommonChannelImpl.this.bindRemoteChannel(null, this);
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannelLocked$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
    final class C01421 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C01421(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AbstractCommonChannelImpl.this.bindRemoteChannelLocked(null, this);
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannelLocked$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
    final class C01432 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $reason;
        int label;
        final /* synthetic */ AbstractCommonChannelImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01432(String str, AbstractCommonChannelImpl abstractCommonChannelImpl, Continuation continuation) {
            super(2, continuation);
            this.$reason = str;
            this.this$0 = abstractCommonChannelImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01432(this.$reason, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01432) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Job job;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.$reason.length() > 0;
            if (!z && (job = this.this$0.retryCounterJob) != null) {
                Job.DefaultImpls.cancel$default(job, null, 1, null);
            }
            this.this$0.onConnect(z);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$optimisticBind$5, reason: invalid class name */
    /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        int label;

        /* JADX INFO: renamed from: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$optimisticBind$5$1, reason: invalid class name */
        /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ AbstractCommonChannelImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(AbstractCommonChannelImpl abstractCommonChannelImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = abstractCommonChannelImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    AbstractCommonChannelImpl abstractCommonChannelImpl = this.this$0;
                    this.label = 1;
                    if (abstractCommonChannelImpl.bindRemoteChannel("for optimistic bind", this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                String str = this.this$0.logTag;
                Level level = Level.INFO;
                final AbstractCommonChannelImpl abstractCommonChannelImpl2 = this.this$0;
                PluginConfigKt.trace$default(str, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.optimisticBind.5.1.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return '[' + abstractCommonChannelImpl2.getToken() + "] Optimistic bind succeeded";
                    }
                }, 60, null);
                return Boxing.boxBoolean(true);
            }
        }

        AnonymousClass5(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AbstractCommonChannelImpl.this.new AnonymousClass5(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass5) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x006a, code lost:
        
            if (r7.call(r1, r6) == r0) goto L22;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1e
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r7)
                goto L6d
            L12:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L1a:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L56
            L1e:
                kotlin.ResultKt.throwOnFailure(r7)
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl r7 = com.motorola.plugin.core.channel.AbstractCommonChannelImpl.this
                com.motorola.plugin.core.misc.DeviceState r7 = r7.getDeviceState()
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl r1 = com.motorola.plugin.core.channel.AbstractCommonChannelImpl.this
                android.content.Context r1 = r1.getContext()
                boolean r7 = r7.willRunInRestrictedMode(r1)
                if (r7 == 0) goto L38
                long r4 = com.motorola.plugin.core.channel.AbstractCommonChannelImpl.access$getAUTO_REBIND_SERVICE_DELAY_MS_RESTRICT$cp()
                goto L4d
            L38:
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl r7 = com.motorola.plugin.core.channel.AbstractCommonChannelImpl.this
                com.motorola.plugin.core.misc.DeviceState r7 = r7.getDeviceState()
                boolean r7 = r7.isLowRamDevice()
                if (r7 == 0) goto L49
                long r4 = com.motorola.plugin.core.channel.AbstractCommonChannelImpl.access$getAUTO_REBIND_SERVICE_DELAY_MS_LOW_RAM$cp()
                goto L4d
            L49:
                long r4 = com.motorola.plugin.core.channel.AbstractCommonChannelImpl.access$getAUTO_REBIND_SERVICE_DELAY_MS$cp()
            L4d:
                r6.label = r3
                java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r4, r6)
                if (r7 != r0) goto L56
                goto L6c
            L56:
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl r7 = com.motorola.plugin.core.channel.AbstractCommonChannelImpl.this
                com.motorola.plugin.core.retry.Retryer r7 = com.motorola.plugin.core.channel.AbstractCommonChannelImpl.access$getRetryCounter(r7)
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl$optimisticBind$5$1 r1 = new com.motorola.plugin.core.channel.AbstractCommonChannelImpl$optimisticBind$5$1
                com.motorola.plugin.core.channel.AbstractCommonChannelImpl r3 = com.motorola.plugin.core.channel.AbstractCommonChannelImpl.this
                r4 = 0
                r1.<init>(r3, r4)
                r6.label = r2
                java.lang.Object r6 = r7.call(r1, r6)
                if (r6 != r0) goto L6d
            L6c:
                return r0
            L6d:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.AnonymousClass5.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$transferNoThreadCheck$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
    final class C01541 extends SuspendLambda implements Function2 {
        final /* synthetic */ Bundle $dataSendToRemote;
        final /* synthetic */ Function1 $safetyRemoteErrorCallback;
        final /* synthetic */ Function1 $safetyRemoteResponseCallback;
        int label;

        /* JADX INFO: renamed from: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$transferNoThreadCheck$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
        final class C00371 extends SuspendLambda implements Function2 {
            final /* synthetic */ Bundle $result;
            final /* synthetic */ Function1 $safetyRemoteResponseCallback;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00371(Function1 function1, Bundle bundle, Continuation continuation) {
                super(2, continuation);
                this.$safetyRemoteResponseCallback = function1;
                this.$result = bundle;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00371(this.$safetyRemoteResponseCallback, this.$result, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00371) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$safetyRemoteResponseCallback.invoke(this.$result);
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$transferNoThreadCheck$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ CancellationException $e;
            final /* synthetic */ Function1 $safetyRemoteErrorCallback;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(Function1 function1, CancellationException cancellationException, Continuation continuation) {
                super(2, continuation);
                this.$safetyRemoteErrorCallback = function1;
                this.$e = cancellationException;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$safetyRemoteErrorCallback, this.$e, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$safetyRemoteErrorCallback.invoke(this.$e);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01541(Bundle bundle, Function1 function1, Function1 function12, Continuation continuation) {
            super(2, continuation);
            this.$dataSendToRemote = bundle;
            this.$safetyRemoteResponseCallback = function1;
            this.$safetyRemoteErrorCallback = function12;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AbstractCommonChannelImpl.this.new C01541(this.$dataSendToRemote, this.$safetyRemoteResponseCallback, this.$safetyRemoteErrorCallback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01541) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws ConnectionException {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    AbstractCommonChannelImpl abstractCommonChannelImpl = AbstractCommonChannelImpl.this;
                    RemoteChannelRequestInfo remoteAppRequestInfo = abstractCommonChannelImpl.toRemoteAppRequestInfo(abstractCommonChannelImpl.getMyRemoteChannelRequest(), this.$dataSendToRemote);
                    AbstractCommonChannelImpl abstractCommonChannelImpl2 = AbstractCommonChannelImpl.this;
                    this.label = 1;
                    obj = abstractCommonChannelImpl2.transferWithChannel(remoteAppRequestInfo, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                BuildersKt__Builders_commonKt.launch$default(AbstractCommonChannelImpl.this.getChannelScope(), null, null, new C00371(this.$safetyRemoteResponseCallback, (Bundle) obj, null), 3, null);
            } catch (CancellationException e) {
                if (CoroutineScopeKt.isActive(AbstractCommonChannelImpl.this.getChannelScope())) {
                    BuildersKt__Builders_commonKt.launch$default(AbstractCommonChannelImpl.this.getChannelScope(), null, null, new AnonymousClass2(this.$safetyRemoteErrorCallback, e, null), 3, null);
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$transferWithChannel$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
    final class C01551 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C01551(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AbstractCommonChannelImpl.this.transferWithChannel(null, this);
        }
    }

    static {
        boolean debug = PluginConfigKt.getDEBUG();
        long j = TimeoutRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS;
        BIND_SERVICE_TIMEOUT_MS = debug ? 10000L : 5000L;
        CALL_SERVICE_TIMEOUT_MS = PluginConfigKt.getDEBUG() ? 60000L : 30000L;
        if (PluginConfigKt.getDEBUG()) {
            j = 1000;
        }
        AUTO_REBIND_SERVICE_DELAY_MS = j;
        AUTO_REBIND_SERVICE_DELAY_MS_LOW_RAM = PluginConfigKt.getDEBUG() ? 2000L : 10000L;
        AUTO_REBIND_SERVICE_DELAY_MS_RESTRICT = PluginConfigKt.getDEBUG() ? 3000L : 15000L;
    }

    public AbstractCommonChannelImpl(String str, ChannelParams channelParams) {
        str.getClass();
        channelParams.getClass();
        this.logTag = str;
        this.context = channelParams.getContext();
        this.pluginEvent = channelParams.getPluginEvent();
        this.action = channelParams.getAction();
        this.pluginClassLoader = channelParams.getPluginClassLoader();
        this.deviceState = channelParams.getDeviceState();
        Long bindServiceTimeout = channelParams.getBindServiceTimeout();
        this.bindServiceTimeout = bindServiceTimeout == null ? BIND_SERVICE_TIMEOUT_MS : bindServiceTimeout.longValue();
        Long transferTimeoutPerAction = channelParams.getTransferTimeoutPerAction();
        long jLongValue = transferTimeoutPerAction == null ? CALL_SERVICE_TIMEOUT_MS : transferTimeoutPerAction.longValue();
        this.transferTimeoutPerAction = jLongValue;
        DisposableContainer disposableContainerNewDisposableContainer = DisposableKt.newDisposableContainer();
        this.mDisposable = disposableContainerNewDisposableContainer;
        Mutex mutexMutex$default = MutexKt.Mutex$default(false, 1, null);
        this.lock = mutexMutex$default;
        ClientId clientId = channelParams.getClientId();
        this.clientId = clientId;
        String str2 = ((Object) clientId.uniqueId) + '|' + channelParams.getServiceComponent().getPackageName() + '|' + ExtensionsKt.hashCodeHex(this);
        this.token = str2;
        this.connectionStatusCallbackList = new LinkedHashSet();
        CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob$default(null, 1, null).plus(Dispatchers.getMain().getImmediate()));
        this.remoteChannelTransferScope = CoroutineScope;
        RemoteChannelCaller remoteChannelCaller = new RemoteChannelCaller(channelParams.getPluginClassLoader(), mutexMutex$default, str2, str, jLongValue);
        this.remoteChannelCaller = remoteChannelCaller;
        this.mConnectionAliveRestrictedReason = BitFlag.Companion.wrap(4);
        this.reconnectTimes = new AtomicLong(0L);
        this.reconnecting = new AtomicBoolean(false);
        this.disconnectTimes = new AtomicLong(0L);
        this.lastDisconnectReason = new AtomicReference();
        this.myDataSetChangedRegistry = createDataSetChangedRegistry();
        this.myChannelResponseHelper = createChannelResponseHelper();
        this.myRemoteChannelRequest$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$myRemoteChannelRequest$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final RemoteChannelRequest mo2224invoke() {
                return this.this$0.generateRemoteChannelRequest();
            }
        });
        this.retryCounter$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$retryCounter$2
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Retryer mo2224invoke() {
                RetryerBuilder retryerBuilderNewBuilder = RetryerBuilder.Companion.newBuilder();
                retryerBuilderNewBuilder.withStopStrategy(StopStrategy.Companion.stopAfterAttempt(5));
                retryerBuilderNewBuilder.withWaitStrategy(WaitStrategy.Companion.fixedWait(500L, TimeUnit.MILLISECONDS));
                retryerBuilderNewBuilder.retryIfException();
                return retryerBuilderNewBuilder.build();
            }
        });
        disposableContainerNewDisposableContainer.add(this.myDataSetChangedRegistry);
        disposableContainerNewDisposableContainer.add(new Disposable() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$$ExternalSyntheticLambda0
            @Override // com.motorola.plugin.core.misc.Disposable
            public final void dispose() {
                AbstractCommonChannelImpl.m2199_init_$lambda0(this.f$0);
            }
        });
        DisposableKt.add(disposableContainerNewDisposableContainer, CoroutineScope);
        disposableContainerNewDisposableContainer.add(remoteChannelCaller);
        PluginConfigKt.trace$default(str, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.2
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + AbstractCommonChannelImpl.this.getToken() + "] Created channel instance: " + ExtensionsKt.hashCodeHex(AbstractCommonChannelImpl.this);
            }
        }, 60, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-0, reason: not valid java name */
    public static final void m2199_init_$lambda0(AbstractCommonChannelImpl abstractCommonChannelImpl) {
        abstractCommonChannelImpl.getClass();
        abstractCommonChannelImpl.connectionStatusCallbackList.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object bindRemoteChannel(java.lang.String r10, kotlin.coroutines.Continuation r11) throws java.lang.Throwable {
        /*
            r9 = this;
            boolean r0 = r11 instanceof com.motorola.plugin.core.channel.AbstractCommonChannelImpl.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r11
            com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannel$1 r0 = (com.motorola.plugin.core.channel.AbstractCommonChannelImpl.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannel$1 r0 = new com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannel$1
            r0.<init>(r11)
        L18:
            java.lang.Object r11 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            r5 = 0
            if (r2 == 0) goto L4d
            if (r2 == r4) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r9 = r0.L$0
            kotlinx.coroutines.sync.Mutex r9 = (kotlinx.coroutines.sync.Mutex) r9
            kotlin.ResultKt.throwOnFailure(r11)     // Catch: java.lang.Throwable -> L31
            goto L7c
        L31:
            r10 = move-exception
            goto L84
        L33:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L3b:
            java.lang.Object r9 = r0.L$2
            kotlinx.coroutines.sync.Mutex r9 = (kotlinx.coroutines.sync.Mutex) r9
            java.lang.Object r10 = r0.L$1
            java.lang.String r10 = (java.lang.String) r10
            java.lang.Object r2 = r0.L$0
            com.motorola.plugin.core.channel.AbstractCommonChannelImpl r2 = (com.motorola.plugin.core.channel.AbstractCommonChannelImpl) r2
            kotlin.ResultKt.throwOnFailure(r11)
            r11 = r9
            r9 = r2
            goto L61
        L4d:
            kotlin.ResultKt.throwOnFailure(r11)
            kotlinx.coroutines.sync.Mutex r11 = r9.lock
            r0.L$0 = r9
            r0.L$1 = r10
            r0.L$2 = r11
            r0.label = r4
            java.lang.Object r2 = r11.lock(r5, r0)
            if (r2 != r1) goto L61
            goto L78
        L61:
            long r6 = r9.getBindServiceTimeout()     // Catch: java.lang.Throwable -> L82
            com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannel$2$1 r2 = new com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannel$2$1     // Catch: java.lang.Throwable -> L82
            r2.<init>(r9, r10, r5)     // Catch: java.lang.Throwable -> L82
            r0.L$0 = r11     // Catch: java.lang.Throwable -> L82
            r0.L$1 = r5     // Catch: java.lang.Throwable -> L82
            r0.L$2 = r5     // Catch: java.lang.Throwable -> L82
            r0.label = r3     // Catch: java.lang.Throwable -> L82
            java.lang.Object r9 = kotlinx.coroutines.TimeoutKt.withTimeout(r6, r2, r0)     // Catch: java.lang.Throwable -> L82
            if (r9 != r1) goto L79
        L78:
            return r1
        L79:
            r8 = r11
            r11 = r9
            r9 = r8
        L7c:
            com.motorola.plugin.sdk.channel.IRemoteChannelTransfer r11 = (com.motorola.plugin.sdk.channel.IRemoteChannelTransfer) r11     // Catch: java.lang.Throwable -> L31
            r9.unlock(r5)
            return r11
        L82:
            r10 = move-exception
            r9 = r11
        L84:
            r9.unlock(r5)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.bindRemoteChannel(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    static /* synthetic */ Object bindRemoteChannel$default(AbstractCommonChannelImpl abstractCommonChannelImpl, String str, Continuation continuation, int i, Object obj) throws ConnectionException, TimeoutCancellationException {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: bindRemoteChannel");
        }
        if ((i & 1) != 0) {
            str = "";
        }
        return abstractCommonChannelImpl.bindRemoteChannel(str, continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object bindRemoteChannelLocked(java.lang.String r9, kotlin.coroutines.Continuation r10) throws com.motorola.plugin.sdk.channel.ConnectionException {
        /*
            r8 = this;
            boolean r0 = r10 instanceof com.motorola.plugin.core.channel.AbstractCommonChannelImpl.C01421
            if (r0 == 0) goto L13
            r0 = r10
            com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannelLocked$1 r0 = (com.motorola.plugin.core.channel.AbstractCommonChannelImpl.C01421) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannelLocked$1 r0 = new com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannelLocked$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            java.lang.Object r8 = r0.L$1
            r9 = r8
            java.lang.String r9 = (java.lang.String) r9
            java.lang.Object r8 = r0.L$0
            com.motorola.plugin.core.channel.AbstractCommonChannelImpl r8 = (com.motorola.plugin.core.channel.AbstractCommonChannelImpl) r8
            kotlin.ResultKt.throwOnFailure(r10)
            goto L62
        L32:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L3a:
            kotlin.ResultKt.throwOnFailure(r10)
            com.motorola.plugin.sdk.channel.IRemoteChannelTransfer r10 = r8.remoteChannelTransfer
            if (r10 != 0) goto L42
            goto L55
        L42:
            android.os.IBinder r10 = r10.asBinder()
            if (r10 != 0) goto L49
            goto L55
        L49:
            boolean r10 = r10.pingBinder()
            if (r10 != r3) goto L55
            com.motorola.plugin.sdk.channel.IRemoteChannelTransfer r8 = r8.remoteChannelTransfer
            r8.getClass()
            return r8
        L55:
            r0.L$0 = r8
            r0.L$1 = r9
            r0.label = r3
            java.lang.Object r10 = r8.getChannelTransferLocked(r9, r0)
            if (r10 != r1) goto L62
            return r1
        L62:
            com.motorola.plugin.sdk.channel.IRemoteChannelTransfer r10 = (com.motorola.plugin.sdk.channel.IRemoteChannelTransfer) r10
            r8.remoteChannelTransfer = r10
            java.util.concurrent.atomic.AtomicBoolean r0 = r8.reconnecting
            r1 = 0
            r0.getAndSet(r1)
            kotlinx.coroutines.CoroutineScope r2 = r8.getChannelScope()
            com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannelLocked$2 r5 = new com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannelLocked$2
            r0 = 0
            r5.<init>(r9, r8, r0)
            r6 = 3
            r7 = 0
            r3 = 0
            r4 = 0
            kotlinx.coroutines.BuildersKt.launch$default(r2, r3, r4, r5, r6, r7)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.bindRemoteChannelLocked(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final CommonChannelResponseHelper createChannelResponseHelper() {
        return new CommonChannelResponseHelper(this, this.pluginClassLoader);
    }

    private final IDataSetChangedRegistryExtension createDataSetChangedRegistry() {
        return new DefaultDataSetChangedRegistry(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final RemoteChannelRequest getMyRemoteChannelRequest() {
        return (RemoteChannelRequest) this.myRemoteChannelRequest$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Retryer getRetryCounter() {
        return (Retryer) this.retryCounter$delegate.getValue();
    }

    private final void handleDataSetChanged(List list, Bundle bundle) {
        if (list == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(this.remoteChannelTransferScope, null, null, new AbstractCommonChannelImpl$handleDataSetChanged$1$1(this, list, bundle, null), 3, null);
    }

    private final void handleDisconnection(final String str, boolean z) {
        Job job;
        PluginConfigKt.trace$default(this.logTag, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.handleDisconnection.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + AbstractCommonChannelImpl.this.getToken() + "] Disconnect to remote channel service due to " + str;
            }
        }, 60, null);
        PluginEvent.DefaultImpls.recordEvent$default(this.pluginEvent, this.token + " disconnect due to " + str, null, 2, null);
        try {
            IRemoteChannelTransfer iRemoteChannelTransfer = this.remoteChannelTransfer;
            if (iRemoteChannelTransfer != null) {
                iRemoteChannelTransfer.onDisconnect(this.clientId);
            }
        } catch (Exception e) {
            PluginConfigKt.trace$default(this.logTag, Level.WARN, false, e, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.handleDisconnection.2
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return '[' + AbstractCommonChannelImpl.this.getToken() + "] Disconnect error";
                }
            }, 52, null);
        }
        if (z && (job = this.lastChannelTransferJob) != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        this.disconnectTimes.incrementAndGet();
        this.lastDisconnectReason.getAndSet(str);
        this.connectionStatusCallbackList.removeIf(new Predicate() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractCommonChannelImpl.m2200handleDisconnection$lambda5((WeakReference) obj);
            }
        });
        Iterator it = this.connectionStatusCallbackList.iterator();
        while (it.hasNext()) {
            IRemoteChannelConnectionStatusCallback iRemoteChannelConnectionStatusCallback = (IRemoteChannelConnectionStatusCallback) ((WeakReference) it.next()).get();
            if (iRemoteChannelConnectionStatusCallback != null) {
                iRemoteChannelConnectionStatusCallback.onRemoteChannelDisconnected();
            }
        }
        onDisconnected(str);
        this.remoteChannelTransfer = null;
    }

    static /* synthetic */ void handleDisconnection$default(AbstractCommonChannelImpl abstractCommonChannelImpl, String str, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: handleDisconnection");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        abstractCommonChannelImpl.handleDisconnection(str, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleDisconnection$lambda-5, reason: not valid java name */
    public static final boolean m2200handleDisconnection$lambda5(WeakReference weakReference) {
        weakReference.getClass();
        return weakReference.get() == null;
    }

    private final void handleReceivedExtraData(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(this.remoteChannelTransferScope, null, null, new AbstractCommonChannelImpl$handleReceivedExtraData$1$1(this, bundle, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onConnect(boolean z) {
        PluginEvent.DefaultImpls.recordEvent$default(this.pluginEvent, this.token + ' ' + ((String) ExtensionsKt.ifElse(z, "re", "")) + "connect to remote", null, 2, null);
        if (z) {
            this.reconnectTimes.incrementAndGet();
            this.connectionStatusCallbackList.removeIf(new Predicate() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return AbstractCommonChannelImpl.m2201onConnect$lambda14((WeakReference) obj);
                }
            });
            Iterator it = this.connectionStatusCallbackList.iterator();
            while (it.hasNext()) {
                IRemoteChannelConnectionStatusCallback iRemoteChannelConnectionStatusCallback = (IRemoteChannelConnectionStatusCallback) ((WeakReference) it.next()).get();
                if (iRemoteChannelConnectionStatusCallback != null) {
                    iRemoteChannelConnectionStatusCallback.onRemoteChannelReconnected();
                }
            }
        }
    }

    static /* synthetic */ void onConnect$default(AbstractCommonChannelImpl abstractCommonChannelImpl, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onConnect");
        }
        if ((i & 1) != 0) {
            z = false;
        }
        abstractCommonChannelImpl.onConnect(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onConnect$lambda-14, reason: not valid java name */
    public static final boolean m2201onConnect$lambda14(WeakReference weakReference) {
        weakReference.getClass();
        return weakReference.get() == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onPostTransfer(Bundle bundle, final Throwable th) {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CHANNEL, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.onPostTransfer.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                StringBuilder sb = new StringBuilder();
                sb.append('[');
                sb.append(AbstractCommonChannelImpl.this.getToken());
                sb.append("] On finish transfer, [");
                Throwable th2 = th;
                sb.append(th2 != null ? Intrinsics.stringPlus("occur error ", th2) : "success");
                sb.append(']');
                return sb.toString();
            }
        }, 62, null);
    }

    private final void onPreTransfer(Bundle bundle) {
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CHANNEL, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.onPreTransfer.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + AbstractCommonChannelImpl.this.getToken() + "] On start transfer";
            }
        }, 62, null);
    }

    public static /* synthetic */ void reportDisconnection$default(AbstractCommonChannelImpl abstractCommonChannelImpl, String str, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: reportDisconnection");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        abstractCommonChannelImpl.reportDisconnection(str, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: requestClearPluginData$lambda-11$lambda-10, reason: not valid java name */
    public static final void m2202requestClearPluginData$lambda11$lambda10(final AbstractCommonChannelImpl abstractCommonChannelImpl, Bundle bundle) {
        abstractCommonChannelImpl.getClass();
        final int i = bundle.getInt(RemoteService.EXTRA_SEQUENCE, -1);
        final boolean z = bundle.getBoolean(RemoteService.EXTRA_RECEIPT, false);
        PluginConfigKt.trace$default(abstractCommonChannelImpl.logTag, Level.INFO, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$requestClearPluginData$1$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + this.this$0.getToken() + "][" + i + "] clear plugin data result = " + z;
            }
        }, 56, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: subscribeConnectStatus$lambda-12, reason: not valid java name */
    public static final boolean m2203subscribeConnectStatus$lambda12(WeakReference weakReference) {
        weakReference.getClass();
        return weakReference.get() == null;
    }

    private final void throwIfClosed() throws IllegalAccessException {
        if (isClosed()) {
            throw new IllegalAccessException('[' + this.token + "] Cannot transfer by an already closed channel");
        }
    }

    private final void throwIfNotMainThread() throws IllegalAccessException {
        if (Looper.getMainLooper().isCurrentThread()) {
            return;
        }
        throw new IllegalAccessException('[' + this.token + "] Transfer method can only invoke via main thread");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final RemoteChannelRequestInfo toRemoteAppRequestInfo(RemoteChannelRequest remoteChannelRequest, Bundle bundle) {
        Object parcelable = bundle.getParcelable(this.token, ClientId.class);
        parcelable.getClass();
        bundle.remove(this.token);
        return new RemoteChannelRequestInfo(remoteChannelRequest.getOrigIntent(), this.clientId, (ClientId) parcelable, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: transferNoThreadCheck$lambda-7, reason: not valid java name */
    public static final void m2204transferNoThreadCheck$lambda7(Function1 function1, Throwable th) {
        function1.getClass();
        function1.invoke(th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: transferNoThreadCheck$lambda-8, reason: not valid java name */
    public static final void m2205transferNoThreadCheck$lambda8(Function1 function1, Bundle bundle) {
        function1.getClass();
        function1.invoke(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object transferWithChannel(com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo r19, kotlin.coroutines.Continuation r20) throws com.motorola.plugin.sdk.channel.ConnectionException {
        /*
            Method dump skipped, instruction units count: 330
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.transferWithChannel(com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: unsubscribeConnectStatus$lambda-13, reason: not valid java name */
    public static final boolean m2206unsubscribeConnectStatus$lambda13(IRemoteChannelConnectionStatusCallback iRemoteChannelConnectionStatusCallback, WeakReference weakReference) {
        iRemoteChannelConnectionStatusCallback.getClass();
        weakReference.getClass();
        return weakReference.get() == null || Intrinsics.areEqual(weakReference.get(), iRemoteChannelConnectionStatusCallback);
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public final void dispose() throws IllegalAccessException {
        if (isClosed()) {
            PluginConfigKt.trace$default(this.logTag, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.dispose.1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return '[' + AbstractCommonChannelImpl.this.getToken() + "] Attempts to close an already closed channel";
                }
            }, 60, null);
            return;
        }
        PluginConfigKt.trace$default(this.logTag, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.dispose.2
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + AbstractCommonChannelImpl.this.getToken() + "] Closing channel";
            }
        }, 62, null);
        throwIfNotMainThread();
        this._closed = true;
        onClose();
        handleDisconnection$default(this, "close channel", false, 2, null);
        this.mDisposable.dispose();
        Job job = this.retryCounterJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        PluginConfigKt.trace$default(this.logTag, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.dispose.3
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + AbstractCommonChannelImpl.this.getToken() + "] Closed channel";
            }
        }, 60, null);
        PluginEvent.DefaultImpls.recordEvent$default(this.pluginEvent, Intrinsics.stringPlus(this.token, " close channel"), null, 2, null);
    }

    protected abstract RemoteChannelRequest generateRemoteChannelRequest();

    protected final String getAction() {
        return this.action;
    }

    protected final long getBindServiceTimeout() {
        return this.bindServiceTimeout;
    }

    @Override // com.motorola.plugin.core.channel.IRemoteChannelExtension
    public CoroutineScope getChannelScope() {
        return this.remoteChannelTransferScope;
    }

    protected abstract Object getChannelTransferLocked(String str, Continuation continuation);

    @Override // com.motorola.plugin.core.channel.IRemoteChannelExtension
    public final ClientId getClientId() {
        return this.clientId;
    }

    protected final Context getContext() {
        return this.context;
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannel
    public IDataSetChangedRegistry getDataSetChangedRegistry() {
        return this.myDataSetChangedRegistry;
    }

    protected final DeviceState getDeviceState() {
        return this.deviceState;
    }

    protected final DisposableContainer getMDisposable() {
        return this.mDisposable;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final CommonChannelResponseHelper getMyChannelResponseHelper() {
        return this.myChannelResponseHelper;
    }

    protected final ClassLoader getPluginClassLoader() {
        return this.pluginClassLoader;
    }

    protected final PluginEvent getPluginEvent() {
        return this.pluginEvent;
    }

    @Override // com.motorola.plugin.core.channel.IRemoteChannelExtension
    public final String getToken() {
        return this.token;
    }

    protected final IRemoteChannelTransfer getTransfer() {
        return this.remoteChannelTransfer;
    }

    protected final long getTransferTimeoutPerAction() {
        return this.transferTimeoutPerAction;
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannel
    public boolean isClosed() {
        return this._closed;
    }

    @Override // com.motorola.plugin.core.channel.IRemoteChannelExtension
    public void keepChannelConnectionAlive(final boolean z, final int i) throws IllegalAccessException {
        throwIfNotMainThread();
        PluginConfigKt.trace$default(this.logTag, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.keepChannelConnectionAlive.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + AbstractCommonChannelImpl.this.getToken() + "] Keep channel connection alive " + z + " by " + i;
            }
        }, 60, null);
        if (z) {
            this.mConnectionAliveRestrictedReason.del(i);
            if (this.disconnectTimes.get() <= 0 || this.reconnecting.get()) {
                return;
            }
            optimisticBind();
            return;
        }
        this.mConnectionAliveRestrictedReason.add(i);
        Job job = this.retryCounterJob;
        if (job == null) {
            return;
        }
        Job.DefaultImpls.cancel$default(job, null, 1, null);
    }

    @Override // com.motorola.plugin.core.channel.IRemoteChannelExtension
    public void notifyDataSetChanged(List list, Bundle bundle) {
        handleDataSetChanged(list, bundle);
    }

    @Override // com.motorola.plugin.core.channel.IRemoteChannelExtension
    public void notifyReceivedExtraData(Bundle bundle) {
        handleReceivedExtraData(bundle);
    }

    protected void onClose() {
    }

    protected void onDisconnected(String str) {
        str.getClass();
    }

    protected boolean onInterceptTransfer(Job job, Bundle bundle, IRemoteChannel.IOnRemoteError iOnRemoteError, IRemoteChannel.IOnRemoteResponse iOnRemoteResponse) {
        bundle.getClass();
        iOnRemoteError.getClass();
        iOnRemoteResponse.getClass();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void optimisticBind() {
        Job job = this.retryCounterJob;
        if (job != null && job.isActive()) {
            PluginConfigKt.trace$default(this.logTag, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.optimisticBind.1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return '[' + AbstractCommonChannelImpl.this.getToken() + "] Optimistic binding is activating";
                }
            }, 60, null);
            return;
        }
        if (this.mChannelStatePaused) {
            PluginConfigKt.trace$default(this.logTag, Level.DEBUG, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.optimisticBind.2
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return '[' + AbstractCommonChannelImpl.this.getToken() + "] Ignore optimistic bind due to channel is in pause state";
                }
            }, 60, null);
            return;
        }
        if (this.mConnectionAliveRestrictedReason.getAny()) {
            PluginConfigKt.trace$default(this.logTag, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.optimisticBind.3
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return '[' + AbstractCommonChannelImpl.this.getToken() + "] Ignore optimistic bind due to keep channel alive is disabled";
                }
            }, 60, null);
            return;
        }
        PluginConfigKt.trace$default(this.logTag, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.optimisticBind.4
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + AbstractCommonChannelImpl.this.getToken() + "] try to optimistic bind remote channel service";
            }
        }, 60, null);
        this.pluginEvent.recordEvent(Intrinsics.stringPlus(this.token, " Optimistic bind"), ISnapshotAware.DefaultImpls.snapshot$default(this, null, 1, null));
        AbstractCommonChannelImpl$optimisticBind$$inlined$CoroutineExceptionHandler$1 abstractCommonChannelImpl$optimisticBind$$inlined$CoroutineExceptionHandler$1 = new AbstractCommonChannelImpl$optimisticBind$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key, this);
        this.reconnecting.getAndSet(true);
        this.retryCounterJob = BuildersKt__Builders_commonKt.launch$default(getChannelScope(), Dispatchers.getDefault().plus(abstractCommonChannelImpl$optimisticBind$$inlined$CoroutineExceptionHandler$1), null, new AnonymousClass5(null), 2, null);
    }

    @Override // com.motorola.plugin.core.channel.IRemoteChannelExtension
    public void pause() throws IllegalAccessException {
        throwIfNotMainThread();
        this.mChannelStatePaused = true;
    }

    protected final void reportDataSetChanged(List list, Bundle bundle) {
        handleDataSetChanged(list, bundle);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void reportDisconnection(String str, boolean z) {
        str.getClass();
        handleDisconnection(str, z);
    }

    protected final void reportReceivedExtraData(Bundle bundle) {
        handleReceivedExtraData(bundle);
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannel
    public void requestClearPluginData(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString(RemoteService.EXTRA_METHOD, (String) ExtensionsKt.ifElse(z, RemoteService.METHOD_CLEAR_CACHE, RemoteService.METHOD_CLEAR_DATA));
        transfer(bundle, new IRemoteChannel.IOnRemoteResponse() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$$ExternalSyntheticLambda1
            @Override // com.motorola.plugin.sdk.channel.IRemoteChannel.IOnRemoteResponse
            public final void onRemoteResponse(Bundle bundle2) {
                AbstractCommonChannelImpl.m2202requestClearPluginData$lambda11$lambda10(this.f$0, bundle2);
            }
        });
    }

    @Override // com.motorola.plugin.core.channel.IRemoteChannelExtension
    public void resume() throws IllegalAccessException {
        throwIfNotMainThread();
        this.mChannelStatePaused = false;
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        String string;
        String string2;
        IBinder iBinderAsBinder;
        iSnapshot.getClass();
        CommonChannelSnapshot commonChannelSnapshot = new CommonChannelSnapshot(iSnapshot);
        commonChannelSnapshot.setMyInstance(hashCode());
        commonChannelSnapshot.setMyIsClosed(isClosed());
        commonChannelSnapshot.setMyIsPaused(this.mChannelStatePaused);
        commonChannelSnapshot.setMyLockStatus(this.lock.toString());
        commonChannelSnapshot.setMyReconnectTimes(this.reconnectTimes.get());
        commonChannelSnapshot.setMyReconnecting(this.reconnecting.get());
        commonChannelSnapshot.setMyDisconnectTimes(this.disconnectTimes.get());
        commonChannelSnapshot.setMyLastDisconnectReason((String) this.lastDisconnectReason.get());
        commonChannelSnapshot.setMyTransferQueueActive(CoroutineScopeKt.isActive(this.remoteChannelTransferScope));
        IRemoteChannelTransfer iRemoteChannelTransfer = this.remoteChannelTransfer;
        boolean zIsBinderAlive = false;
        if (iRemoteChannelTransfer != null && (iBinderAsBinder = iRemoteChannelTransfer.asBinder()) != null) {
            zIsBinderAlive = iBinderAsBinder.isBinderAlive();
        }
        commonChannelSnapshot.setMyTransferAlive(zIsBinderAlive);
        Job job = this.lastChannelTransferJob;
        String str = "<>";
        if (job == null || (string = job.toString()) == null) {
            string = "<>";
        }
        commonChannelSnapshot.setMyLastTransferJobStatus(string);
        Job job2 = this.retryCounterJob;
        if (job2 != null && (string2 = job2.toString()) != null) {
            str = string2;
        }
        commonChannelSnapshot.setMyRetryJobStatus(str);
        commonChannelSnapshot.setMyTimeoutCallerSnapshot(ISnapshotAware.DefaultImpls.snapshot$default(this.remoteChannelCaller, null, 1, null));
        commonChannelSnapshot.setMyDatasetChangedRegistry(ISnapshotAware.DefaultImpls.snapshot$default(this.myDataSetChangedRegistry, null, 1, null));
        commonChannelSnapshot.setMyKeepChannelConnectionAliveRestrictedReason(this.mConnectionAliveRestrictedReason.getMyFlags());
        return commonChannelSnapshot;
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannel
    public void subscribeConnectStatus(IRemoteChannelConnectionStatusCallback iRemoteChannelConnectionStatusCallback) {
        iRemoteChannelConnectionStatusCallback.getClass();
        this.connectionStatusCallbackList.removeIf(new Predicate() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractCommonChannelImpl.m2203subscribeConnectStatus$lambda12((WeakReference) obj);
            }
        });
        this.connectionStatusCallbackList.add(new WeakReference(iRemoteChannelConnectionStatusCallback));
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannel
    public final void transfer(Bundle bundle, IRemoteChannel.IOnRemoteError iOnRemoteError, IRemoteChannel.IOnRemoteResponse iOnRemoteResponse) throws IllegalAccessException {
        bundle.getClass();
        iOnRemoteResponse.getClass();
        throwIfNotMainThread();
        transferNoThreadCheck(bundle, iOnRemoteError, iOnRemoteResponse);
    }

    @Override // com.motorola.plugin.core.channel.IRemoteChannelExtension
    public void transferNoThreadCheck(Bundle bundle, final IRemoteChannel.IOnRemoteError iOnRemoteError, final IRemoteChannel.IOnRemoteResponse iOnRemoteResponse) throws IllegalAccessException {
        bundle.getClass();
        iOnRemoteResponse.getClass();
        throwIfClosed();
        onPreTransfer(bundle);
        final Function1 function1 = new Function1() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$transferNoThreadCheck$safetyRemoteErrorCallback$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Throwable) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(Throwable th) {
                th.getClass();
                IRemoteChannel.IOnRemoteError iOnRemoteError2 = iOnRemoteError;
                if (iOnRemoteError2 != null) {
                    iOnRemoteError2.onRemoteError(th);
                }
                this.onPostTransfer(null, th);
            }
        };
        final Function1 function12 = new Function1() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$transferNoThreadCheck$safetyRemoteResponseCallback$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Bundle) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(Bundle bundle2) {
                bundle2.getClass();
                iOnRemoteResponse.onRemoteResponse(bundle2);
                this.onPostTransfer(bundle2, null);
            }
        };
        if (onInterceptTransfer(this.lastChannelTransferJob, bundle, new IRemoteChannel.IOnRemoteError() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$$ExternalSyntheticLambda4
            @Override // com.motorola.plugin.sdk.channel.IRemoteChannel.IOnRemoteError
            public final void onRemoteError(Throwable th) {
                AbstractCommonChannelImpl.m2204transferNoThreadCheck$lambda7(function1, th);
            }
        }, new IRemoteChannel.IOnRemoteResponse() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$$ExternalSyntheticLambda5
            @Override // com.motorola.plugin.sdk.channel.IRemoteChannel.IOnRemoteResponse
            public final void onRemoteResponse(Bundle bundle2) {
                AbstractCommonChannelImpl.m2205transferNoThreadCheck$lambda8(function12, bundle2);
            }
        })) {
            return;
        }
        this.lastChannelTransferJob = BuildersKt__Builders_commonKt.launch$default(getChannelScope(), new AbstractCommonChannelImpl$transferNoThreadCheck$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key, this, function1), null, new C01541(bundle, function12, function1, null), 2, null);
    }

    @Override // com.motorola.plugin.sdk.channel.IRemoteChannel
    public void unsubscribeConnectStatus(final IRemoteChannelConnectionStatusCallback iRemoteChannelConnectionStatusCallback) {
        iRemoteChannelConnectionStatusCallback.getClass();
        this.connectionStatusCallbackList.removeIf(new Predicate() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractCommonChannelImpl.m2206unsubscribeConnectStatus$lambda13(iRemoteChannelConnectionStatusCallback, (WeakReference) obj);
            }
        });
    }
}
