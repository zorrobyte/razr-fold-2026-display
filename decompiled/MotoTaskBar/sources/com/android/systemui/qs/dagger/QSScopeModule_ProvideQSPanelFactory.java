package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.qs.QSPanel;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class QSScopeModule_ProvideQSPanelFactory implements Factory {
    private final Provider viewProvider;

    public QSScopeModule_ProvideQSPanelFactory(Provider provider) {
        this.viewProvider = provider;
    }

    public static QSScopeModule_ProvideQSPanelFactory create(Provider provider) {
        return new QSScopeModule_ProvideQSPanelFactory(provider);
    }

    public static QSPanel provideQSPanel(View view) {
        QSPanel qSPanelProvideQSPanel = QSScopeModule.provideQSPanel(view);
        qSPanelProvideQSPanel.getClass();
        return qSPanelProvideQSPanel;
    }

    @Override // javax.inject.Provider
    public QSPanel get() {
        return provideQSPanel((View) this.viewProvider.get());
    }
}
