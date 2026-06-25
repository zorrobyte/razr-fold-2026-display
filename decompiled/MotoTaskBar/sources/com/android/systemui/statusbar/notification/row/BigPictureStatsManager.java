package com.android.systemui.statusbar.notification.row;

import android.util.Log;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: BigPictureStatsManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BigPictureStatsManager implements Dumpable {
    private final List durations;
    private final LatencyTracker latencyTracker;
    private final Object lock;
    private final CoroutineDispatcher mainDispatcher;
    private final ConcurrentHashMap startTimes;

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.row.BigPictureStatsManager$trackEvent$3, reason: invalid class name */
    /* JADX INFO: compiled from: BigPictureStatsManager.kt */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $duration;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(int i, Continuation continuation) {
            super(2, continuation);
            this.$duration = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BigPictureStatsManager.this.new AnonymousClass3(this.$duration, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BigPictureStatsManager.this.latencyTracker.logAction(23, this.$duration);
            return Unit.INSTANCE;
        }
    }

    public BigPictureStatsManager(LatencyTracker latencyTracker, CoroutineDispatcher coroutineDispatcher, DumpManager dumpManager) {
        latencyTracker.getClass();
        coroutineDispatcher.getClass();
        dumpManager.getClass();
        this.latencyTracker = latencyTracker;
        this.mainDispatcher = coroutineDispatcher;
        dumpManager.registerNormalDumpable("BigPictureStatsManager", this);
        this.startTimes = new ConcurrentHashMap();
        this.durations = new ArrayList();
        this.lock = new Object();
    }

    public final void onBegin(String str) {
        str.getClass();
        if (!this.startTimes.contains(str)) {
            this.startTimes.put(str, Long.valueOf(System.nanoTime()));
            return;
        }
        Log.wtf("BigPictureStatsManager", "key " + str + " is already in use");
    }

    public final void onCancel(String str) {
        str.getClass();
        this.startTimes.remove(str);
    }

    public final Integer onEnd(String str) {
        str.getClass();
        Long l = (Long) this.startTimes.remove(str);
        if (l != null) {
            return Integer.valueOf((int) ((System.nanoTime() - l.longValue()) / ((long) 1000000)));
        }
        Log.wtf("BigPictureStatsManager", "No matching begin call for this " + str);
        return null;
    }

    public final Object trackEvent(int i, Continuation continuation) {
        synchronized (this.lock) {
            this.durations.add(Boxing.boxInt(i));
        }
        Object objWithContext = BuildersKt.withContext(this.mainDispatcher, new AnonymousClass3(i, null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
    }
}
