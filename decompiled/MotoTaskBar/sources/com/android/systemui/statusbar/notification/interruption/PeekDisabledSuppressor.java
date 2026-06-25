package com.android.systemui.statusbar.notification.interruption;

import android.database.ContentObserver;
import android.os.Handler;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.settings.GlobalSettings;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PeekDisabledSuppressor extends VisualInterruptionCondition {
    private final GlobalSettings globalSettings;
    private final HeadsUpManager headsUpManager;
    private boolean isEnabled;
    private final VisualInterruptionDecisionLogger logger;
    private final Handler mainHandler;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeekDisabledSuppressor(GlobalSettings globalSettings, HeadsUpManager headsUpManager, VisualInterruptionDecisionLogger visualInterruptionDecisionLogger, Handler handler) {
        super(SetsKt.setOf(VisualInterruptionType.PEEK), "peek disabled by global setting");
        globalSettings.getClass();
        headsUpManager.getClass();
        visualInterruptionDecisionLogger.getClass();
        handler.getClass();
        this.globalSettings = globalSettings;
        this.headsUpManager = headsUpManager;
        this.logger = visualInterruptionDecisionLogger;
        this.mainHandler = handler;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionCondition
    public boolean shouldSuppress() {
        return !this.isEnabled;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionSuppressor
    public void start() {
        final Handler handler = this.mainHandler;
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.notification.interruption.PeekDisabledSuppressor$start$observer$1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                boolean z2 = this.this$0.isEnabled;
                PeekDisabledSuppressor peekDisabledSuppressor = this.this$0;
                peekDisabledSuppressor.isEnabled = peekDisabledSuppressor.globalSettings.getInt("heads_up_notifications_enabled", 0) != 0;
                this.this$0.logger.logHeadsUpFeatureChanged(this.this$0.isEnabled);
                if (!z2 || this.this$0.isEnabled) {
                    return;
                }
                this.this$0.logger.logWillDismissAll();
                this.this$0.headsUpManager.releaseAllImmediately();
            }
        };
        GlobalSettings globalSettings = this.globalSettings;
        globalSettings.registerContentObserver(globalSettings.getUriFor("heads_up_notifications_enabled"), true, contentObserver);
        contentObserver.onChange(true);
    }
}
