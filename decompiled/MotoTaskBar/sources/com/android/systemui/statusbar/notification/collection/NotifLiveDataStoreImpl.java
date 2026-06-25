package com.android.systemui.statusbar.notification.collection;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.util.Assert;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: NotifLiveDataStoreImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifLiveDataStoreImpl implements NotifLiveDataStore {
    private final NotifLiveData activeNotifCount;
    private final NotifLiveDataImpl activeNotifCountPrivate;
    private final NotifLiveData activeNotifList;
    private final NotifLiveDataImpl activeNotifListPrivate;
    private final NotifLiveData hasActiveNotifs;
    private final NotifLiveDataImpl hasActiveNotifsPrivate;
    private final Executor mainExecutor;

    public NotifLiveDataStoreImpl(Executor executor) {
        executor.getClass();
        this.mainExecutor = executor;
        NotifLiveDataImpl notifLiveDataImpl = new NotifLiveDataImpl("hasActiveNotifs", Boolean.FALSE, executor);
        this.hasActiveNotifsPrivate = notifLiveDataImpl;
        NotifLiveDataImpl notifLiveDataImpl2 = new NotifLiveDataImpl("activeNotifCount", 0, executor);
        this.activeNotifCountPrivate = notifLiveDataImpl2;
        NotifLiveDataImpl notifLiveDataImpl3 = new NotifLiveDataImpl("activeNotifList", CollectionsKt.emptyList(), executor);
        this.activeNotifListPrivate = notifLiveDataImpl3;
        this.hasActiveNotifs = notifLiveDataImpl;
        this.activeNotifCount = notifLiveDataImpl2;
        this.activeNotifList = notifLiveDataImpl3;
    }

    @Override // com.android.systemui.statusbar.notification.collection.NotifLiveDataStore
    public NotifLiveData getActiveNotifCount() {
        return this.activeNotifCount;
    }

    @Override // com.android.systemui.statusbar.notification.collection.NotifLiveDataStore
    public NotifLiveData getActiveNotifList() {
        return this.activeNotifList;
    }

    public final void setActiveNotifList(List list) {
        list.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("NotifLiveDataStore.setActiveNotifList");
        }
        try {
            Assert.isMainThread();
            List listUnmodifiableList = Collections.unmodifiableList(CollectionsKt.toList(list));
            NotifLiveDataImpl notifLiveDataImpl = this.activeNotifListPrivate;
            listUnmodifiableList.getClass();
            Iterator it = CollectionsKt.listOf((Object[]) new Function0[]{notifLiveDataImpl.setValueAndProvideDispatcher(listUnmodifiableList), this.activeNotifCountPrivate.setValueAndProvideDispatcher(Integer.valueOf(listUnmodifiableList.size())), this.hasActiveNotifsPrivate.setValueAndProvideDispatcher(Boolean.valueOf(!listUnmodifiableList.isEmpty()))}).iterator();
            while (it.hasNext()) {
                ((Function0) it.next()).mo2224invoke();
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }
}
