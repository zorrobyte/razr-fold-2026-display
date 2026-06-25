package com.android.systemui.statusbar.notification.collection.notifcollection;

/* JADX INFO: compiled from: NotifEvent.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RankingAppliedEvent extends NotifEvent {
    public RankingAppliedEvent() {
        super("onRankingApplied", null);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.getClass();
        notifCollectionListener.onRankingApplied();
    }
}
