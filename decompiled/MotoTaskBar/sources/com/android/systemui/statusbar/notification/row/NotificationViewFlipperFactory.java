package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewFlipper;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.NotificationViewFlipperViewModel;
import com.android.systemui.statusbar.notification.shared.NotificationViewFlipperPausing;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotificationViewFlipperFactory.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationViewFlipperFactory implements NotifRemoteViewsFactory {
    private final NotificationViewFlipperViewModel viewModel;

    public NotificationViewFlipperFactory(NotificationViewFlipperViewModel notificationViewFlipperViewModel) {
        notificationViewFlipperViewModel.getClass();
        this.viewModel = notificationViewFlipperViewModel;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationViewFlipperPausing notificationViewFlipperPausing = NotificationViewFlipperPausing.INSTANCE;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactory
    public View instantiate(ExpandableNotificationRow expandableNotificationRow, int i, View view, String str, Context context, AttributeSet attributeSet) {
        expandableNotificationRow.getClass();
        str.getClass();
        context.getClass();
        attributeSet.getClass();
        if (!Intrinsics.areEqual(str, ViewFlipper.class.getName()) && !Intrinsics.areEqual(str, ViewFlipper.class.getSimpleName())) {
            return null;
        }
        ViewFlipper viewFlipper = new ViewFlipper(context, attributeSet);
        NotificationViewFlipperBinder.INSTANCE.bindWhileAttached(viewFlipper, this.viewModel);
        return viewFlipper;
    }
}
