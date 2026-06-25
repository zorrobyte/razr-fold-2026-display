package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleLineViewModel;

/* JADX INFO: compiled from: SingleLineViewBinder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SingleLineViewBinder {
    public static final SingleLineViewBinder INSTANCE = new SingleLineViewBinder();

    private SingleLineViewBinder() {
    }

    public static final void bind(SingleLineViewModel singleLineViewModel, HybridNotificationView hybridNotificationView) {
        if (hybridNotificationView != null) {
            hybridNotificationView.bind(singleLineViewModel != null ? singleLineViewModel.getTitleText() : null, singleLineViewModel != null ? singleLineViewModel.getContentText() : null, null);
        }
    }
}
