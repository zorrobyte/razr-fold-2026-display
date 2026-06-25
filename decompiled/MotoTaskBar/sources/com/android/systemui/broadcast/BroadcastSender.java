package com.android.systemui.broadcast;

import android.content.Context;
import com.android.systemui.util.wakelock.WakeLock$Builder;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: BroadcastSender.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BroadcastSender {
    public static final Companion Companion = new Companion(null);
    private final Executor bgExecutor;
    private final Context context;
    private final WakeLock$Builder wakeLockBuilder;

    /* JADX INFO: compiled from: BroadcastSender.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BroadcastSender(Context context, WakeLock$Builder wakeLock$Builder, Executor executor) {
        context.getClass();
        wakeLock$Builder.getClass();
        executor.getClass();
        this.context = context;
        this.wakeLockBuilder = wakeLock$Builder;
        this.bgExecutor = executor;
    }
}
