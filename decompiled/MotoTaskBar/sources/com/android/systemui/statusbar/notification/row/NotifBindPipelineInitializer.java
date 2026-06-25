package com.android.systemui.statusbar.notification.row;

/* JADX INFO: loaded from: classes.dex */
public class NotifBindPipelineInitializer {
    NotifBindPipeline mNotifBindPipeline;
    RowContentBindStage mRowContentBindStage;

    NotifBindPipelineInitializer(NotifBindPipeline notifBindPipeline, RowContentBindStage rowContentBindStage) {
        this.mNotifBindPipeline = notifBindPipeline;
        this.mRowContentBindStage = rowContentBindStage;
    }

    public void initialize() {
        this.mNotifBindPipeline.setStage(this.mRowContentBindStage);
    }
}
