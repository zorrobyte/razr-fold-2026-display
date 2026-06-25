package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class PreparationCoordinator_Factory implements Factory {
    private final Provider adjustmentProvider;
    private final Provider bindEventManagerProvider;
    private final Provider errorManagerProvider;
    private final Provider loggerProvider;
    private final Provider notifInflaterProvider;
    private final Provider serviceProvider;
    private final Provider viewBarnProvider;

    public PreparationCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.loggerProvider = provider;
        this.notifInflaterProvider = provider2;
        this.errorManagerProvider = provider3;
        this.viewBarnProvider = provider4;
        this.adjustmentProvider = provider5;
        this.serviceProvider = provider6;
        this.bindEventManagerProvider = provider7;
    }

    public static PreparationCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        return new PreparationCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static PreparationCoordinator newInstance(PreparationCoordinatorLogger preparationCoordinatorLogger, NotifInflater notifInflater, NotifInflationErrorManager notifInflationErrorManager, NotifViewBarn notifViewBarn, NotifUiAdjustmentProvider notifUiAdjustmentProvider, IStatusBarService iStatusBarService, BindEventManagerImpl bindEventManagerImpl) {
        return new PreparationCoordinator(preparationCoordinatorLogger, notifInflater, notifInflationErrorManager, notifViewBarn, notifUiAdjustmentProvider, iStatusBarService, bindEventManagerImpl);
    }

    @Override // javax.inject.Provider
    public PreparationCoordinator get() {
        return newInstance((PreparationCoordinatorLogger) this.loggerProvider.get(), (NotifInflater) this.notifInflaterProvider.get(), (NotifInflationErrorManager) this.errorManagerProvider.get(), (NotifViewBarn) this.viewBarnProvider.get(), (NotifUiAdjustmentProvider) this.adjustmentProvider.get(), (IStatusBarService) this.serviceProvider.get(), (BindEventManagerImpl) this.bindEventManagerProvider.get());
    }
}
