package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger;

/* JADX INFO: compiled from: ConversationNotifications.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ConversationNotificationProcessor {
    private final ConversationNotificationManager conversationNotificationManager;
    private final LauncherApps launcherApps;

    public ConversationNotificationProcessor(LauncherApps launcherApps, ConversationNotificationManager conversationNotificationManager) {
        launcherApps.getClass();
        conversationNotificationManager.getClass();
        this.launcherApps = launcherApps;
        this.conversationNotificationManager = conversationNotificationManager;
    }

    public final Notification.MessagingStyle processNotification(NotificationEntry notificationEntry, Notification.Builder builder, NotificationContentInflaterLogger notificationContentInflaterLogger) {
        notificationEntry.getClass();
        builder.getClass();
        notificationContentInflaterLogger.getClass();
        Notification.Style style = builder.getStyle();
        Notification.MessagingStyle messagingStyle = style instanceof Notification.MessagingStyle ? (Notification.MessagingStyle) style : null;
        if (messagingStyle == null) {
            return null;
        }
        messagingStyle.setConversationType(notificationEntry.getRanking().getChannel().isImportantConversation() ? 2 : 1);
        ShortcutInfo conversationShortcutInfo = notificationEntry.getRanking().getConversationShortcutInfo();
        if (conversationShortcutInfo != null) {
            notificationContentInflaterLogger.logAsyncTaskProgress(notificationEntry, "getting shortcut icon");
            messagingStyle.setShortcutIcon(this.launcherApps.getShortcutIcon(conversationShortcutInfo));
            CharSequence label = conversationShortcutInfo.getLabel();
            if (label != null) {
                messagingStyle.setConversationTitle(label);
            }
        }
        messagingStyle.setUnreadMessageCount(this.conversationNotificationManager.getUnreadCount(notificationEntry, builder));
        return messagingStyle;
    }
}
