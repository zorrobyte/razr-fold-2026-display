package com.android.systemui.statusbar.notification.stack.domain.interactor;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: NotificationStackInteractor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationStackInteractor {
    private final Flow isShowingOnLockscreen = FlowKt.flowOf(Boolean.FALSE);

    public final Flow isShowingOnLockscreen() {
        return this.isShowingOnLockscreen;
    }
}
