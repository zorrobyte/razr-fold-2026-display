package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class NotificationChildrenContainer$$ExternalSyntheticLambda0 implements NotificationHeaderViewWrapper.RoundnessChangedListener {
    public final /* synthetic */ NotificationChildrenContainer f$0;

    public /* synthetic */ NotificationChildrenContainer$$ExternalSyntheticLambda0(NotificationChildrenContainer notificationChildrenContainer) {
        this.f$0 = notificationChildrenContainer;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper.RoundnessChangedListener
    public final void applyRoundnessAndInvalidate() {
        this.f$0.invalidate();
    }
}
