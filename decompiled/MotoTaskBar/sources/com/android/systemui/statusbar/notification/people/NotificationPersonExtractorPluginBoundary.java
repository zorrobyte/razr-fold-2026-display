package com.android.systemui.statusbar.notification.people;

import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.NotificationPersonExtractorPlugin;
import com.android.systemui.statusbar.policy.ExtensionController;
import java.util.function.Consumer;

/* JADX INFO: compiled from: NotificationPersonExtractor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationPersonExtractorPluginBoundary implements NotificationPersonExtractor {
    private NotificationPersonExtractorPlugin plugin;

    public NotificationPersonExtractorPluginBoundary(ExtensionController extensionController) {
        extensionController.getClass();
        this.plugin = (NotificationPersonExtractorPlugin) extensionController.newExtension(NotificationPersonExtractorPlugin.class).withPlugin(NotificationPersonExtractorPlugin.class).withCallback(new Consumer() { // from class: com.android.systemui.statusbar.notification.people.NotificationPersonExtractorPluginBoundary.1
            @Override // java.util.function.Consumer
            public final void accept(NotificationPersonExtractorPlugin notificationPersonExtractorPlugin) {
                NotificationPersonExtractorPluginBoundary.this.plugin = notificationPersonExtractorPlugin;
            }
        }).build().get();
    }

    @Override // com.android.systemui.statusbar.notification.people.NotificationPersonExtractor
    public boolean isPersonNotification(StatusBarNotification statusBarNotification) {
        statusBarNotification.getClass();
        NotificationPersonExtractorPlugin notificationPersonExtractorPlugin = this.plugin;
        if (notificationPersonExtractorPlugin != null) {
            return notificationPersonExtractorPlugin.isPersonNotification(statusBarNotification);
        }
        return false;
    }
}
