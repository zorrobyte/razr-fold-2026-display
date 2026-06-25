package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.policy.AvalancheController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class AmbientState_Factory implements Factory {
    private final Provider avalancheControllerProvider;
    private final Provider contextProvider;
    private final Provider dumpManagerProvider;
    private final Provider sectionProvider;

    public AmbientState_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.dumpManagerProvider = provider2;
        this.sectionProvider = provider3;
        this.avalancheControllerProvider = provider4;
    }

    public static AmbientState_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new AmbientState_Factory(provider, provider2, provider3, provider4);
    }

    public static AmbientState newInstance(Context context, DumpManager dumpManager, StackScrollAlgorithm.SectionProvider sectionProvider, AvalancheController avalancheController) {
        return new AmbientState(context, dumpManager, sectionProvider, avalancheController);
    }

    @Override // javax.inject.Provider
    public AmbientState get() {
        return newInstance((Context) this.contextProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (StackScrollAlgorithm.SectionProvider) this.sectionProvider.get(), (AvalancheController) this.avalancheControllerProvider.get());
    }
}
