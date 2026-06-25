package com.motorola.plugin.core.components.impls;

import android.content.Context;
import android.util.AtomicFile;
import androidx.core.util.AtomicFileKt;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.components.AppContext;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.components.impls.PluginEventImpl;
import com.motorola.plugin.core.misc.AbstractSnapshot;
import com.motorola.plugin.core.misc.FixedSizeCache;
import com.motorola.plugin.core.misc.IPrinter;
import com.motorola.plugin.core.misc.ISnapshot;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.core.misc.IndentingPrintWriter;
import java.io.File;
import java.io.StringWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt__JobKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

/* JADX INFO: compiled from: PluginEventImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginEventImpl implements PluginEvent {
    public static final Companion Companion = new Companion(null);
    private static final long DEFAULT_DISK_WRITE_DELAY = TimeUnit.MINUTES.toMillis(2);
    private static final String EVENTS_FILE_NAME = "plugin_events.dump";
    private final Context appContext;
    private final CoroutineScope myCoroutineScope;
    private final Channel myEventChannel;
    private final EventLogCache myEventLog;
    private Job myLastJob;
    private final Mutex myMutex;

    /* JADX INFO: compiled from: PluginEventImpl.kt */
    final class CacheEntry {
        private final String event;
        private final ISnapshot snapshot;
        private final long timestamp;

        public CacheEntry(String str, ISnapshot iSnapshot, long j) {
            str.getClass();
            this.event = str;
            this.snapshot = iSnapshot;
            this.timestamp = j;
        }

        public static /* synthetic */ CacheEntry copy$default(CacheEntry cacheEntry, String str, ISnapshot iSnapshot, long j, int i, Object obj) {
            if ((i & 1) != 0) {
                str = cacheEntry.event;
            }
            if ((i & 2) != 0) {
                iSnapshot = cacheEntry.snapshot;
            }
            if ((i & 4) != 0) {
                j = cacheEntry.timestamp;
            }
            return cacheEntry.copy(str, iSnapshot, j);
        }

        public final String component1() {
            return this.event;
        }

        public final ISnapshot component2() {
            return this.snapshot;
        }

        public final long component3() {
            return this.timestamp;
        }

        public final CacheEntry copy(String str, ISnapshot iSnapshot, long j) {
            str.getClass();
            return new CacheEntry(str, iSnapshot, j);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CacheEntry)) {
                return false;
            }
            CacheEntry cacheEntry = (CacheEntry) obj;
            return Intrinsics.areEqual(this.event, cacheEntry.event) && Intrinsics.areEqual(this.snapshot, cacheEntry.snapshot) && this.timestamp == cacheEntry.timestamp;
        }

        public final String getEvent() {
            return this.event;
        }

        public final ISnapshot getSnapshot() {
            return this.snapshot;
        }

        public final long getTimestamp() {
            return this.timestamp;
        }

        public int hashCode() {
            int iHashCode = this.event.hashCode() * 31;
            ISnapshot iSnapshot = this.snapshot;
            return ((iHashCode + (iSnapshot == null ? 0 : iSnapshot.hashCode())) * 31) + Long.hashCode(this.timestamp);
        }

        public String toString() {
            return "CacheEntry(event=" + this.event + ", snapshot=" + this.snapshot + ", timestamp=" + this.timestamp + ')';
        }
    }

    /* JADX INFO: compiled from: PluginEventImpl.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final AtomicFile buildEventsFile(Context context) {
            return new AtomicFile(new File(context.getCacheDir(), PluginEventImpl.EVENTS_FILE_NAME));
        }
    }

    /* JADX INFO: compiled from: PluginEventImpl.kt */
    final class EventLogCache extends FixedSizeCache {
        public EventLogCache(int i) {
            super(i);
        }

        public final void add(CacheEntry cacheEntry) {
            cacheEntry.getClass();
            put(cacheEntry, Unit.INSTANCE);
        }

        public /* bridge */ boolean containsKey(CacheEntry cacheEntry) {
            return super.containsKey((Object) cacheEntry);
        }

        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ boolean containsKey(Object obj) {
            if (obj instanceof CacheEntry) {
                return containsKey((CacheEntry) obj);
            }
            return false;
        }

        public /* bridge */ Unit get(CacheEntry cacheEntry) {
            return (Unit) super.get((Object) cacheEntry);
        }

        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ Unit get(Object obj) {
            if (obj instanceof CacheEntry) {
                return get((CacheEntry) obj);
            }
            return null;
        }

        public /* bridge */ void getOrDefault(CacheEntry cacheEntry, Unit unit) {
            super.getOrDefault((Object) cacheEntry, unit);
        }

        public final /* bridge */ void getOrDefault(Object obj, Unit unit) {
            if (obj instanceof CacheEntry) {
                getOrDefault((CacheEntry) obj, unit);
            }
        }

        public /* bridge */ Unit remove(CacheEntry cacheEntry) {
            return (Unit) super.remove((Object) cacheEntry);
        }

        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ Unit remove(Object obj) {
            if (obj instanceof CacheEntry) {
                return remove((CacheEntry) obj);
            }
            return null;
        }
    }

    /* JADX INFO: compiled from: PluginEventImpl.kt */
    final class PluginEventSnapshot extends AbstractSnapshot {
        private List myEvents;
        private AtomicFile myHistoryFile;
        private int myInstance;
        public String myMutexState;
        public String myMutexStatePostfix;
        private List mySnapshots;
        private final long myTime;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PluginEventSnapshot(ISnapshot iSnapshot) {
            super(iSnapshot);
            iSnapshot.getClass();
            this.myTime = System.currentTimeMillis();
            this.myEvents = CollectionsKt.emptyList();
            this.mySnapshots = CollectionsKt.emptyList();
        }

        public final List getMyEvents() {
            return this.myEvents;
        }

        public final AtomicFile getMyHistoryFile() {
            return this.myHistoryFile;
        }

        public final int getMyInstance() {
            return this.myInstance;
        }

        public final String getMyMutexState() {
            String str = this.myMutexState;
            if (str != null) {
                return str;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myMutexState");
            throw null;
        }

        public final String getMyMutexStatePostfix() {
            String str = this.myMutexStatePostfix;
            if (str != null) {
                return str;
            }
            Intrinsics.throwUninitializedPropertyAccessException("myMutexStatePostfix");
            throw null;
        }

        public final List getMySnapshots() {
            return this.mySnapshots;
        }

        public final long getMyTime() {
            return this.myTime;
        }

        @Override // com.motorola.plugin.core.misc.AbstractSnapshot, com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
            super.onSnapshot(iPrinter);
            iPrinter.printHexPair("PluginEvents", getMyInstance()).newLine();
            iPrinter.increaseIndent();
            iPrinter.printPair("Snap time", ExtensionsKt.timestampWithZone(getMyTime())).newLine();
            iPrinter.printPair("Mutex state", getMyMutexState()).newLine();
            iPrinter.printPair("Mutex state(2)", getMyMutexStatePostfix()).newLine();
            iPrinter.printSingle("History events:");
            iPrinter.printPair("size", Integer.valueOf(getMyEvents().size())).newLine();
            iPrinter.increaseIndent();
            for (CacheEntry cacheEntry : getMyEvents()) {
                iPrinter.printTimed(cacheEntry.getTimestamp(), cacheEntry.getEvent()).newLine();
            }
            iPrinter.decreaseIndent();
            iPrinter.printSingle("Snapshots:");
            iPrinter.printPair("size", Integer.valueOf(getMySnapshots().size())).newLine();
            iPrinter.increaseIndent();
            for (CacheEntry cacheEntry2 : getMySnapshots()) {
                iPrinter.printSingle("snapshot @" + Instant.ofEpochMilli(cacheEntry2.getTimestamp()) + ':');
                iPrinter.printPair("event", cacheEntry2.getEvent());
                iPrinter.newLine();
                ISnapshot snapshot = cacheEntry2.getSnapshot();
                if (snapshot != null) {
                    snapshot.onSnapshot(iPrinter);
                }
            }
            iPrinter.decreaseIndent();
            AtomicFile myHistoryFile = getMyHistoryFile();
            if (myHistoryFile != null) {
                iPrinter.printSingle("History in cache:").newLine();
                try {
                    Result.Companion companion = Result.Companion;
                    iPrinter.increaseIndent();
                    iPrinter.printSingle(AtomicFileKt.readText$default(myHistoryFile, null, 1, null)).newLine();
                    iPrinter.decreaseIndent();
                    Result.m2707constructorimpl(Unit.INSTANCE);
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.Companion;
                    Result.m2707constructorimpl(ResultKt.createFailure(th));
                }
            }
            iPrinter.decreaseIndent();
        }

        public final void setMyEvents(List list) {
            list.getClass();
            this.myEvents = list;
        }

        public final void setMyHistoryFile(AtomicFile atomicFile) {
            this.myHistoryFile = atomicFile;
        }

        public final void setMyInstance(int i) {
            this.myInstance = i;
        }

        public final void setMyMutexState(String str) {
            str.getClass();
            this.myMutexState = str;
        }

        public final void setMyMutexStatePostfix(String str) {
            str.getClass();
            this.myMutexStatePostfix = str;
        }

        public final void setMySnapshots(List list) {
            list.getClass();
            this.mySnapshots = list;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginEventImpl$onInitialized$1, reason: invalid class name */
    /* JADX INFO: compiled from: PluginEventImpl.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PluginEventImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x007a, code lost:
        
            if (r5.lock(null, r8) == r0) goto L20;
         */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0053  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x005d  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0094  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x007a -> B:7:0x0022). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                r2 = 2
                r3 = 1
                r4 = 0
                if (r1 == 0) goto L35
                if (r1 == r3) goto L2c
                if (r1 != r2) goto L24
                java.lang.Object r1 = r8.L$3
                com.motorola.plugin.core.components.impls.PluginEventImpl r1 = (com.motorola.plugin.core.components.impls.PluginEventImpl) r1
                java.lang.Object r5 = r8.L$2
                kotlinx.coroutines.sync.Mutex r5 = (kotlinx.coroutines.sync.Mutex) r5
                java.lang.Object r6 = r8.L$1
                com.motorola.plugin.core.components.impls.PluginEventImpl$CacheEntry r6 = (com.motorola.plugin.core.components.impls.PluginEventImpl.CacheEntry) r6
                java.lang.Object r7 = r8.L$0
                kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
                kotlin.ResultKt.throwOnFailure(r9)
            L22:
                r9 = r7
                goto L7d
            L24:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L2c:
                java.lang.Object r1 = r8.L$0
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                kotlin.ResultKt.throwOnFailure(r9)
                r7 = r1
                goto L55
            L35:
                kotlin.ResultKt.throwOnFailure(r9)
                com.motorola.plugin.core.components.impls.PluginEventImpl r9 = com.motorola.plugin.core.components.impls.PluginEventImpl.this
                kotlinx.coroutines.channels.Channel r9 = com.motorola.plugin.core.components.impls.PluginEventImpl.access$getMyEventChannel$p(r9)
                kotlinx.coroutines.channels.ChannelIterator r9 = r9.iterator()
            L42:
                r8.L$0 = r9
                r8.L$1 = r4
                r8.L$2 = r4
                r8.L$3 = r4
                r8.label = r3
                java.lang.Object r1 = r9.hasNext(r8)
                if (r1 != r0) goto L53
                goto L7c
            L53:
                r7 = r9
                r9 = r1
            L55:
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                if (r9 == 0) goto L94
                java.lang.Object r9 = r7.next()
                r6 = r9
                com.motorola.plugin.core.components.impls.PluginEventImpl$CacheEntry r6 = (com.motorola.plugin.core.components.impls.PluginEventImpl.CacheEntry) r6
                com.motorola.plugin.core.components.impls.PluginEventImpl r9 = com.motorola.plugin.core.components.impls.PluginEventImpl.this
                kotlinx.coroutines.sync.Mutex r5 = com.motorola.plugin.core.components.impls.PluginEventImpl.access$getMyMutex$p(r9)
                com.motorola.plugin.core.components.impls.PluginEventImpl r1 = com.motorola.plugin.core.components.impls.PluginEventImpl.this
                r8.L$0 = r7
                r8.L$1 = r6
                r8.L$2 = r5
                r8.L$3 = r1
                r8.label = r2
                java.lang.Object r9 = r5.lock(r4, r8)
                if (r9 != r0) goto L22
            L7c:
                return r0
            L7d:
                com.motorola.plugin.core.components.impls.PluginEventImpl$EventLogCache r1 = com.motorola.plugin.core.components.impls.PluginEventImpl.access$getMyEventLog$p(r1)     // Catch: java.lang.Throwable -> L8f
                r1.add(r6)     // Catch: java.lang.Throwable -> L8f
                kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L8f
                r5.unlock(r4)
                com.motorola.plugin.core.components.impls.PluginEventImpl r1 = com.motorola.plugin.core.components.impls.PluginEventImpl.this
                com.motorola.plugin.core.components.impls.PluginEventImpl.access$scheduleWrite(r1)
                goto L42
            L8f:
                r8 = move-exception
                r5.unlock(r4)
                throw r8
            L94:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.components.impls.PluginEventImpl.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginEventImpl$recordEvent$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PluginEventImpl.kt */
    final class C01611 extends SuspendLambda implements Function2 {
        final /* synthetic */ CacheEntry $entry;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01611(CacheEntry cacheEntry, Continuation continuation) {
            super(2, continuation);
            this.$entry = cacheEntry;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PluginEventImpl.this.new C01611(this.$entry, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01611) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Channel channel = PluginEventImpl.this.myEventChannel;
                CacheEntry cacheEntry = this.$entry;
                this.label = 1;
                if (channel.send(cacheEntry, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.motorola.plugin.core.components.impls.PluginEventImpl$scheduleWrite$4, reason: invalid class name */
    /* JADX INFO: compiled from: PluginEventImpl.kt */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;

        AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PluginEventImpl.this.new AnonymousClass4(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j = PluginEventImpl.DEFAULT_DISK_WRITE_DELAY;
                this.label = 1;
                if (DelayKt.delay(j, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            PluginEventImpl.this.writeToFile();
            return Unit.INSTANCE;
        }
    }

    public PluginEventImpl(@AppContext Context context) {
        context.getClass();
        this.appContext = context;
        this.myEventLog = new EventLogCache(100);
        this.myCoroutineScope = CoroutineScopeKt.CoroutineScope(JobKt__JobKt.Job$default(null, 1, null).plus(Dispatchers.getIO()));
        this.myMutex = MutexKt.Mutex$default(false, 1, null);
        this.myEventChannel = ChannelKt.Channel$default(100, BufferOverflow.DROP_OLDEST, null, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void scheduleWrite() {
        Job job = this.myLastJob;
        if (job != null) {
            if (job.isCancelled()) {
                job = null;
            }
            if (job != null) {
                if (job.isCompleted()) {
                    job = null;
                }
                if (job != null) {
                    if (!job.isActive()) {
                        job = null;
                    }
                    if (job != null) {
                        Job.DefaultImpls.cancel$default(job, null, 1, null);
                    }
                }
            }
        }
        this.myLastJob = BuildersKt__Builders_commonKt.launch$default(this.myCoroutineScope, null, null, new AnonymousClass4(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeToFile() {
        Object objM2707constructorimpl;
        StringWriter stringWriter = new StringWriter();
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(stringWriter, null, 0, 6, null);
        PluginEventSnapshot pluginEventSnapshot = (PluginEventSnapshot) ISnapshotAware.DefaultImpls.snapshot$default(this, null, 1, null);
        pluginEventSnapshot.setMyHistoryFile(null);
        pluginEventSnapshot.onSnapshot(indentingPrintWriter);
        AtomicFile atomicFileBuildEventsFile = Companion.buildEventsFile(this.appContext);
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.VERBOSE, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginEventImpl.writeToFile.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "start write plugin events";
            }
        }, 56, null);
        try {
            Result.Companion companion = Result.Companion;
            String string = stringWriter.toString();
            string.getClass();
            AtomicFileKt.writeText$default(atomicFileBuildEventsFile, string, null, 2, null);
            objM2707constructorimpl = Result.m2707constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m2712isSuccessimpl(objM2707constructorimpl)) {
            if (Mutex.DefaultImpls.tryLock$default(this.myMutex, null, 1, null)) {
                this.myEventLog.clear();
                Mutex.DefaultImpls.unlock$default(this.myMutex, null, 1, null);
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.VERBOSE, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginEventImpl$writeToFile$3$1
                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "write plugin events file done";
                    }
                }, 56, null);
            } else {
                PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.WARN, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginEventImpl$writeToFile$3$2
                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "write plugin events file failed cause no valid lock";
                    }
                }, 56, null);
            }
        }
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl != null) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_MANAGER, Level.VERBOSE, false, thM2709exceptionOrNullimpl, false, null, new Function0() { // from class: com.motorola.plugin.core.components.impls.PluginEventImpl$writeToFile$4$1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "failed to write the plugin events";
                }
            }, 52, null);
        }
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        CoroutineScopeKt.cancel$default(this.myCoroutineScope, null, 1, null);
        writeToFile();
    }

    @Override // com.motorola.plugin.core.components.PluginEvent
    public void flush() {
        dispose();
    }

    @Override // com.motorola.plugin.core.components.OnInitializedAware
    public void onInitialized() {
        BuildersKt__Builders_commonKt.launch$default(this.myCoroutineScope, Dispatchers.getDefault(), null, new AnonymousClass1(null), 2, null);
    }

    @Override // com.motorola.plugin.core.components.PluginEvent
    public void recordEvent(String str, ISnapshot iSnapshot) {
        str.getClass();
        BuildersKt__Builders_commonKt.launch$default(this.myCoroutineScope, Dispatchers.getDefault(), null, new C01611(new CacheEntry(str, iSnapshot, System.currentTimeMillis()), null), 2, null);
    }

    @Override // com.motorola.plugin.core.misc.ISnapshotAware
    public ISnapshot snapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        PluginEventSnapshot pluginEventSnapshot = new PluginEventSnapshot(iSnapshot);
        pluginEventSnapshot.setMyInstance(hashCode());
        pluginEventSnapshot.setMyMutexState(this.myMutex.toString());
        if (Mutex.DefaultImpls.tryLock$default(this.myMutex, null, 1, null)) {
            Set setKeySet = this.myEventLog.keySet();
            setKeySet.getClass();
            Set<CacheEntry> set = setKeySet;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
            for (CacheEntry cacheEntry : set) {
                cacheEntry.getClass();
                arrayList.add(CacheEntry.copy$default(cacheEntry, null, null, 0L, 7, null));
            }
            pluginEventSnapshot.setMyEvents(CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: com.motorola.plugin.core.components.impls.PluginEventImpl$snapshot$lambda-11$$inlined$sortedByDescending$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt.compareValues(Long.valueOf(((PluginEventImpl.CacheEntry) obj2).getTimestamp()), Long.valueOf(((PluginEventImpl.CacheEntry) obj).getTimestamp()));
                }
            }));
            Set setKeySet2 = this.myEventLog.keySet();
            setKeySet2.getClass();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : setKeySet2) {
                if (((CacheEntry) obj).getSnapshot() != null) {
                    arrayList2.add(obj);
                }
            }
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                CacheEntry cacheEntry2 = (CacheEntry) obj2;
                cacheEntry2.getClass();
                arrayList3.add(CacheEntry.copy$default(cacheEntry2, null, null, 0L, 7, null));
            }
            pluginEventSnapshot.setMySnapshots(CollectionsKt.sortedWith(arrayList3, new Comparator() { // from class: com.motorola.plugin.core.components.impls.PluginEventImpl$snapshot$lambda-11$$inlined$sortedByDescending$2
                @Override // java.util.Comparator
                public final int compare(Object obj3, Object obj4) {
                    return ComparisonsKt.compareValues(Long.valueOf(((PluginEventImpl.CacheEntry) obj4).getTimestamp()), Long.valueOf(((PluginEventImpl.CacheEntry) obj3).getTimestamp()));
                }
            }));
            Mutex.DefaultImpls.unlock$default(this.myMutex, null, 1, null);
        } else {
            pluginEventSnapshot.setMyEvents(CollectionsKt.emptyList());
            pluginEventSnapshot.setMySnapshots(CollectionsKt.emptyList());
        }
        pluginEventSnapshot.setMyHistoryFile(Companion.buildEventsFile(this.appContext));
        pluginEventSnapshot.setMyMutexStatePostfix(this.myMutex.toString());
        return pluginEventSnapshot;
    }
}
