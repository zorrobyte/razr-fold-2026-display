package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.shared.NotificationViewFlipperPausing;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackInteractor;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlinx.coroutines.flow.Flow;

/* JADX INFO: compiled from: NotificationViewFlipperViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationViewFlipperViewModel extends FlowDumperImpl {
    private final Flow isPaused;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public NotificationViewFlipperViewModel(DumpManager dumpManager, NotificationStackInteractor notificationStackInteractor) {
        super(dumpManager, null, 2, 0 == true ? 1 : 0);
        dumpManager.getClass();
        notificationStackInteractor.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationViewFlipperPausing notificationViewFlipperPausing = NotificationViewFlipperPausing.INSTANCE;
        this.isPaused = dumpWhileCollecting(notificationStackInteractor.isShowingOnLockscreen(), "isPaused");
    }

    public final Flow isPaused() {
        return this.isPaused;
    }
}
