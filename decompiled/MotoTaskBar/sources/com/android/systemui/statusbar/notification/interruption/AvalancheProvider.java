package com.android.systemui.statusbar.notification.interruption;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.clocks.WeatherData;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AvalancheProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AvalancheProvider {
    private final String TAG;
    private final Set avalancheTriggerIntents;
    private final BroadcastDispatcher broadcastDispatcher;
    private final BroadcastReceiver broadcastReceiver;
    private final VisualInterruptionDecisionLogger logger;
    private long startTime;
    private final int timeoutMs;

    public AvalancheProvider(BroadcastDispatcher broadcastDispatcher, VisualInterruptionDecisionLogger visualInterruptionDecisionLogger) {
        broadcastDispatcher.getClass();
        visualInterruptionDecisionLogger.getClass();
        this.broadcastDispatcher = broadcastDispatcher;
        this.logger = visualInterruptionDecisionLogger;
        this.TAG = "AvalancheProvider";
        this.timeoutMs = 120000;
        this.avalancheTriggerIntents = SetsKt.mutableSetOf("android.intent.action.AIRPLANE_MODE", "android.intent.action.BOOT_COMPLETED", "android.intent.action.MANAGED_PROFILE_AVAILABLE", "android.intent.action.USER_SWITCHED");
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.interruption.AvalancheProvider$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                context.getClass();
                intent.getClass();
                if (CollectionsKt.contains(this.this$0.avalancheTriggerIntents, intent.getAction())) {
                    if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.AIRPLANE_MODE") && intent.getBooleanExtra(WeatherData.STATE_KEY, false)) {
                        Log.d(this.this$0.getTAG(), "broadcastReceiver: ignore airplane mode on");
                        return;
                    }
                    Log.d(this.this$0.getTAG(), "broadcastReceiver received intent.action=" + intent.getAction());
                    this.this$0.setStartTime(System.currentTimeMillis());
                }
            }
        };
    }

    public final long getStartTime() {
        return this.startTime;
    }

    public final String getTAG() {
        return this.TAG;
    }

    public final int getTimeoutMs() {
        return this.timeoutMs;
    }

    public final void register() {
        IntentFilter intentFilter = new IntentFilter();
        Iterator it = this.avalancheTriggerIntents.iterator();
        while (it.hasNext()) {
            intentFilter.addAction((String) it.next());
        }
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.broadcastReceiver, intentFilter, null, null, 12, null);
    }

    public final void setStartTime(long j) {
        this.startTime = j;
    }
}
