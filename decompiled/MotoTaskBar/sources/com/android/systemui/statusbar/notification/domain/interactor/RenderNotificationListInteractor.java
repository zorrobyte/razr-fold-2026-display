package com.android.systemui.statusbar.notification.domain.interactor;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.MutableStateFlow;

/* JADX INFO: compiled from: RenderNotificationListInteractor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RenderNotificationListInteractor {
    private final ActiveNotificationListRepository repository;
    private final SectionStyleProvider sectionStyleProvider;

    public RenderNotificationListInteractor(ActiveNotificationListRepository activeNotificationListRepository, SectionStyleProvider sectionStyleProvider) {
        activeNotificationListRepository.getClass();
        sectionStyleProvider.getClass();
        this.repository = activeNotificationListRepository;
        this.sectionStyleProvider = sectionStyleProvider;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setRenderedList$lambda$2$lambda$1$lambda$0(List list, ActiveNotificationsStoreBuilder activeNotificationsStoreBuilder) {
        activeNotificationsStoreBuilder.getClass();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            activeNotificationsStoreBuilder.addListEntry((ListEntry) it.next());
        }
        activeNotificationsStoreBuilder.setRankingsMap(list);
        return Unit.INSTANCE;
    }

    public final void setRenderedList(final List list) {
        Object value;
        list.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("RenderNotificationListInteractor.setRenderedList");
        }
        try {
            MutableStateFlow activeNotifications = this.repository.getActiveNotifications();
            do {
                value = activeNotifications.getValue();
            } while (!activeNotifications.compareAndSet(value, RenderNotificationListInteractorKt.buildActiveNotificationsStore((ActiveNotificationsStore) value, this.sectionStyleProvider, new Function1() { // from class: com.android.systemui.statusbar.notification.domain.interactor.RenderNotificationListInteractor$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return RenderNotificationListInteractor.setRenderedList$lambda$2$lambda$1$lambda$0(list, (ActiveNotificationsStoreBuilder) obj);
                }
            })));
            Unit unit = Unit.INSTANCE;
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }
}
