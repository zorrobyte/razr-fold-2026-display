package com.motorola.taskbar.bar;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.motorola.taskbar.model.DisplayConfigurationController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class MirrorPhonePanelController_Factory implements Factory {
    private final Provider configurationControllerProvider;
    private final Provider contextProvider;
    private final Provider displayConfigurationControllerProvider;
    private final Provider handlerProvider;

    public MirrorPhonePanelController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.handlerProvider = provider2;
        this.configurationControllerProvider = provider3;
        this.displayConfigurationControllerProvider = provider4;
    }

    public static MirrorPhonePanelController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new MirrorPhonePanelController_Factory(provider, provider2, provider3, provider4);
    }

    public static MirrorPhonePanelController newInstance(Context context, Handler handler, ConfigurationController configurationController, DisplayConfigurationController displayConfigurationController) {
        return new MirrorPhonePanelController(context, handler, configurationController, displayConfigurationController);
    }

    @Override // javax.inject.Provider
    public MirrorPhonePanelController get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.handlerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (DisplayConfigurationController) this.displayConfigurationControllerProvider.get());
    }
}
