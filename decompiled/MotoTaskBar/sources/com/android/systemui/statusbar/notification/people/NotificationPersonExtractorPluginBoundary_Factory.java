package com.android.systemui.statusbar.notification.people;

import com.android.systemui.statusbar.policy.ExtensionController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationPersonExtractorPluginBoundary_Factory implements Factory {
    private final Provider extensionControllerProvider;

    public NotificationPersonExtractorPluginBoundary_Factory(Provider provider) {
        this.extensionControllerProvider = provider;
    }

    public static NotificationPersonExtractorPluginBoundary_Factory create(Provider provider) {
        return new NotificationPersonExtractorPluginBoundary_Factory(provider);
    }

    public static NotificationPersonExtractorPluginBoundary newInstance(ExtensionController extensionController) {
        return new NotificationPersonExtractorPluginBoundary(extensionController);
    }

    @Override // javax.inject.Provider
    public NotificationPersonExtractorPluginBoundary get() {
        return newInstance((ExtensionController) this.extensionControllerProvider.get());
    }
}
