package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository;
import com.android.systemui.statusbar.notification.shared.HeadsUpRowKey;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: HeadsUpNotificationInteractor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpNotificationInteractor {
    private final Flow hasPinnedRows;
    private final Flow isHeadsUpOrAnimatingAway;
    private final Flow pinnedHeadsUpRows;
    private final HeadsUpRepository repository;
    private final Flow topHeadsUpRow;

    public HeadsUpNotificationInteractor(HeadsUpRepository headsUpRepository) {
        headsUpRepository.getClass();
        this.repository = headsUpRepository;
        this.topHeadsUpRow = headsUpRepository.getTopHeadsUpRow();
        this.pinnedHeadsUpRows = FlowKt.transformLatest(headsUpRepository.getActiveHeadsUpRows(), new HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$1(null));
        Flow flowTransformLatest = FlowKt.transformLatest(headsUpRepository.getActiveHeadsUpRows(), new HeadsUpNotificationInteractor$special$$inlined$flatMapLatest$2(null));
        this.hasPinnedRows = flowTransformLatest;
        this.isHeadsUpOrAnimatingAway = FlowKt.combine(flowTransformLatest, headsUpRepository.isHeadsUpAnimatingAway(), new HeadsUpNotificationInteractor$isHeadsUpOrAnimatingAway$1(null));
    }

    public final Object elementKeyFor(HeadsUpRowKey headsUpRowKey) {
        headsUpRowKey.getClass();
        return ((HeadsUpRowRepository) headsUpRowKey).getElementKey();
    }

    public final Flow getHasPinnedRows() {
        return this.hasPinnedRows;
    }

    public final Flow getPinnedHeadsUpRows() {
        return this.pinnedHeadsUpRows;
    }

    public final Flow getTopHeadsUpRow() {
        return this.topHeadsUpRow;
    }

    public final void setHeadsUpAnimatingAway(boolean z) {
        this.repository.setHeadsUpAnimatingAway(z);
    }
}
