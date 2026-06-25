package com.motorola.taskbar.qsnotification.dagger;

import com.android.systemui.statusbar.policy.ConfigurationController;
import com.motorola.taskbar.qsnotification.QsConfigurationControllerImpl;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class QsNotificationComponent_QsNotificationBaseModule_ProvideQsConfigurationControllerFactory implements Factory {
    private final Provider qsConfigurationControllerProvider;

    public QsNotificationComponent_QsNotificationBaseModule_ProvideQsConfigurationControllerFactory(Provider provider) {
        this.qsConfigurationControllerProvider = provider;
    }

    public static QsNotificationComponent_QsNotificationBaseModule_ProvideQsConfigurationControllerFactory create(Provider provider) {
        return new QsNotificationComponent_QsNotificationBaseModule_ProvideQsConfigurationControllerFactory(provider);
    }

    public static ConfigurationController provideQsConfigurationController(QsConfigurationControllerImpl qsConfigurationControllerImpl) {
        ConfigurationController configurationControllerProvideQsConfigurationController = QsNotificationComponent.QsNotificationBaseModule.provideQsConfigurationController(qsConfigurationControllerImpl);
        configurationControllerProvideQsConfigurationController.getClass();
        return configurationControllerProvideQsConfigurationController;
    }

    @Override // javax.inject.Provider
    public ConfigurationController get() {
        return provideQsConfigurationController((QsConfigurationControllerImpl) this.qsConfigurationControllerProvider.get());
    }
}
