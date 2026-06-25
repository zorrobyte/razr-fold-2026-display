package com.android.systemui.statusbar.notification.data.repository;

import com.android.systemui.statusbar.notification.shared.HeadsUpRowKey;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: HeadsUpRowRepository.kt */
/* JADX INFO: loaded from: classes.dex */
public interface HeadsUpRowRepository extends HeadsUpRowKey {
    Object getElementKey();

    StateFlow isPinned();
}
