package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: compiled from: RemoteInputViewController.kt */
/* JADX INFO: loaded from: classes.dex */
public interface RemoteInputViewController {
    void bind();

    void close();

    void focus();

    PendingIntent getPendingIntent();

    RemoteInput getRemoteInput();

    RemoteInput[] getRemoteInputs();

    boolean isActive();

    void setBouncerChecker(NotificationRemoteInputManager.BouncerChecker bouncerChecker);

    void setEditedSuggestionInfo(NotificationEntry.EditedSuggestionInfo editedSuggestionInfo);

    void setPendingIntent(PendingIntent pendingIntent);

    void setRemoteInput(RemoteInput remoteInput);

    void setRemoteInputs(RemoteInput[] remoteInputArr);

    default void stealFocusFrom(RemoteInputViewController remoteInputViewController) {
        remoteInputViewController.getClass();
        remoteInputViewController.close();
        setRemoteInput(remoteInputViewController.getRemoteInput());
        setRemoteInputs(remoteInputViewController.getRemoteInputs());
        setPendingIntent(remoteInputViewController.getPendingIntent());
        focus();
    }

    void unbind();

    boolean updatePendingIntentFromActions(Notification.Action[] actionArr);
}
