package com.android.systemui.qs;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.view.MediaHost;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class QSPanelController_Factory implements Factory {
    private final Provider dumpManagerProvider;
    private final Provider longPRessEffectProvider;
    private final Provider mediaHostProvider;
    private final Provider qsHostProvider;
    private final Provider usingMediaPlayerProvider;
    private final Provider viewProvider;

    public QSPanelController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.viewProvider = provider;
        this.qsHostProvider = provider2;
        this.usingMediaPlayerProvider = provider3;
        this.mediaHostProvider = provider4;
        this.dumpManagerProvider = provider5;
        this.longPRessEffectProvider = provider6;
    }

    public static QSPanelController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new QSPanelController_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static QSPanelController newInstance(QSPanel qSPanel, QSHost qSHost, boolean z, MediaHost mediaHost, DumpManager dumpManager, javax.inject.Provider provider) {
        return new QSPanelController(qSPanel, qSHost, z, mediaHost, dumpManager, provider);
    }

    @Override // javax.inject.Provider
    public QSPanelController get() {
        return newInstance((QSPanel) this.viewProvider.get(), (QSHost) this.qsHostProvider.get(), ((Boolean) this.usingMediaPlayerProvider.get()).booleanValue(), (MediaHost) this.mediaHostProvider.get(), (DumpManager) this.dumpManagerProvider.get(), this.longPRessEffectProvider);
    }
}
