package com.android.systemui.statusbar.notification.data.repository;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: HeadsUpRepository.kt */
/* JADX INFO: loaded from: classes.dex */
public interface HeadsUpRepository {
    Flow getActiveHeadsUpRows();

    Flow getTopHeadsUpRow();

    StateFlow isHeadsUpAnimatingAway();

    void setHeadsUpAnimatingAway(boolean z);
}
