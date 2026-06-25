package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class RankingCoordinator_Factory implements Factory {
    private final Provider alertingHeaderControllerProvider;
    private final Provider highPriorityProvider;
    private final Provider silentHeaderControllerProvider;
    private final Provider silentNodeControllerProvider;

    public RankingCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.highPriorityProvider = provider;
        this.alertingHeaderControllerProvider = provider2;
        this.silentHeaderControllerProvider = provider3;
        this.silentNodeControllerProvider = provider4;
    }

    public static RankingCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new RankingCoordinator_Factory(provider, provider2, provider3, provider4);
    }

    public static RankingCoordinator newInstance(HighPriorityProvider highPriorityProvider, NodeController nodeController, SectionHeaderController sectionHeaderController, NodeController nodeController2) {
        return new RankingCoordinator(highPriorityProvider, nodeController, sectionHeaderController, nodeController2);
    }

    @Override // javax.inject.Provider
    public RankingCoordinator get() {
        return newInstance((HighPriorityProvider) this.highPriorityProvider.get(), (NodeController) this.alertingHeaderControllerProvider.get(), (SectionHeaderController) this.silentHeaderControllerProvider.get(), (NodeController) this.silentNodeControllerProvider.get());
    }
}
