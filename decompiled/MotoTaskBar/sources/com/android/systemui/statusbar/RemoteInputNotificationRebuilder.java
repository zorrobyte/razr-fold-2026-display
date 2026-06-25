package com.android.systemui.statusbar;

import android.app.Notification;
import android.app.RemoteInputHistoryItem;
import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes.dex */
public class RemoteInputNotificationRebuilder {
    private final Context mContext;

    /* JADX INFO: renamed from: $r8$lambda$CgmG-e09UZLqQrVHV_-fgR_fiy0, reason: not valid java name */
    public static /* synthetic */ RemoteInputHistoryItem m1644$r8$lambda$CgmGe09UZLqQrVHV_fgR_fiy0(Parcelable parcelable) {
        return (RemoteInputHistoryItem) parcelable;
    }

    /* JADX INFO: renamed from: $r8$lambda$FZ7pKqSY4nvjouZz9A5w-XBXu9k, reason: not valid java name */
    public static /* synthetic */ RemoteInputHistoryItem[] m1645$r8$lambda$FZ7pKqSY4nvjouZz9A5wXBXu9k(int i) {
        return new RemoteInputHistoryItem[i];
    }

    public static /* synthetic */ RemoteInputHistoryItem[] $r8$lambda$hHNnA8hB4RteBgTQf6WXwudi120(int i) {
        return new RemoteInputHistoryItem[i];
    }

    RemoteInputNotificationRebuilder(Context context) {
        this.mContext = context;
    }

    public StatusBarNotification rebuildForCanceledSmartReplies(NotificationEntry notificationEntry) {
        return rebuildWithExistingReplies(notificationEntry);
    }

    public StatusBarNotification rebuildForRemoteInputReply(NotificationEntry notificationEntry) {
        CharSequence charSequence = notificationEntry.remoteInputText;
        if (TextUtils.isEmpty(charSequence)) {
            charSequence = notificationEntry.remoteInputTextWhenReset;
        }
        return rebuildWithRemoteInputInserted(notificationEntry, charSequence, false, notificationEntry.remoteInputMimeType, notificationEntry.remoteInputUri);
    }

    public StatusBarNotification rebuildForSendingSmartReply(NotificationEntry notificationEntry, CharSequence charSequence) {
        return rebuildWithRemoteInputInserted(notificationEntry, charSequence, true, null, null);
    }

    public StatusBarNotification rebuildWithExistingReplies(NotificationEntry notificationEntry) {
        return rebuildWithRemoteInputInserted(notificationEntry, null, false, null, null);
    }

    StatusBarNotification rebuildWithRemoteInputInserted(NotificationEntry notificationEntry, CharSequence charSequence, boolean z, String str, Uri uri) {
        StatusBarNotification sbn = notificationEntry.getSbn();
        Notification.Builder builderRecoverBuilder = Notification.Builder.recoverBuilder(this.mContext, sbn.getNotification().clone());
        if (notificationEntry.remoteInputs == null) {
            notificationEntry.remoteInputs = new ArrayList();
        }
        if (charSequence != null || uri != null) {
            notificationEntry.remoteInputs.add(0, uri != null ? new RemoteInputHistoryItem(str, uri, charSequence) : new RemoteInputHistoryItem(charSequence));
        }
        Parcelable[] parcelableArray = sbn.getNotification().extras.getParcelableArray("android.remoteInputHistoryItems");
        builderRecoverBuilder.setRemoteInputHistory(parcelableArray != null ? (RemoteInputHistoryItem[]) Stream.concat(notificationEntry.remoteInputs.stream(), Arrays.stream(parcelableArray).map(new Function() { // from class: com.android.systemui.statusbar.RemoteInputNotificationRebuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return RemoteInputNotificationRebuilder.m1644$r8$lambda$CgmGe09UZLqQrVHV_fgR_fiy0((Parcelable) obj);
            }
        })).toArray(new IntFunction() { // from class: com.android.systemui.statusbar.RemoteInputNotificationRebuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return RemoteInputNotificationRebuilder.m1645$r8$lambda$FZ7pKqSY4nvjouZz9A5wXBXu9k(i);
            }
        }) : (RemoteInputHistoryItem[]) notificationEntry.remoteInputs.toArray(new IntFunction() { // from class: com.android.systemui.statusbar.RemoteInputNotificationRebuilder$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return RemoteInputNotificationRebuilder.$r8$lambda$hHNnA8hB4RteBgTQf6WXwudi120(i);
            }
        }));
        builderRecoverBuilder.setShowRemoteInputSpinner(z);
        builderRecoverBuilder.setHideSmartReplies(true);
        Notification notificationBuild = builderRecoverBuilder.build();
        notificationBuild.contentView = sbn.getNotification().contentView;
        notificationBuild.bigContentView = sbn.getNotification().bigContentView;
        notificationBuild.headsUpContentView = sbn.getNotification().headsUpContentView;
        return new StatusBarNotification(sbn.getPackageName(), sbn.getOpPkg(), sbn.getId(), sbn.getTag(), sbn.getUid(), sbn.getInitialPid(), notificationBuild, sbn.getUser(), sbn.getOverrideGroupKey(), sbn.getPostTime());
    }
}
