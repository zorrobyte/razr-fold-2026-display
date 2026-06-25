package com.android.systemui.statusbar;

import android.app.Notification;
import android.app.RemoteInput;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class NotificationUiAdjustment {
    public final boolean isConversation;
    public final String key;
    public final List smartActions;
    public final List smartReplies;

    NotificationUiAdjustment(String str, List list, List list2, boolean z) {
        this.key = str;
        this.smartActions = list == null ? Collections.EMPTY_LIST : list;
        this.smartReplies = list2 == null ? Collections.EMPTY_LIST : list2;
        this.isConversation = z;
    }

    private static boolean areDifferent(Icon icon, Icon icon2) {
        if (icon == icon2) {
            return false;
        }
        if (icon == null || icon2 == null) {
            return true;
        }
        return !icon.sameAs(icon2);
    }

    public static boolean areDifferent(List list, List list2) {
        if (list == list2) {
            return false;
        }
        if (list == null || list2 == null || list.size() != list2.size()) {
            return true;
        }
        for (int i = 0; i < list.size(); i++) {
            Notification.Action action = (Notification.Action) list.get(i);
            Notification.Action action2 = (Notification.Action) list2.get(i);
            if (!TextUtils.equals(action.title, action2.title) || areDifferent(action.getIcon(), action2.getIcon()) || !Objects.equals(action.actionIntent, action2.actionIntent) || areDifferent(action.getRemoteInputs(), action2.getRemoteInputs())) {
                return true;
            }
        }
        return false;
    }

    private static boolean areDifferent(RemoteInput[] remoteInputArr, RemoteInput[] remoteInputArr2) {
        if (remoteInputArr == remoteInputArr2) {
            return false;
        }
        if (remoteInputArr == null || remoteInputArr2 == null || remoteInputArr.length != remoteInputArr2.length) {
            return true;
        }
        for (int i = 0; i < remoteInputArr.length; i++) {
            RemoteInput remoteInput = remoteInputArr[i];
            RemoteInput remoteInput2 = remoteInputArr2[i];
            if (!TextUtils.equals(remoteInput.getLabel(), remoteInput2.getLabel()) || areDifferent(remoteInput.getChoices(), remoteInput2.getChoices())) {
                return true;
            }
        }
        return false;
    }

    private static boolean areDifferent(CharSequence[] charSequenceArr, CharSequence[] charSequenceArr2) {
        if (charSequenceArr == charSequenceArr2) {
            return false;
        }
        if (charSequenceArr == null || charSequenceArr2 == null || charSequenceArr.length != charSequenceArr2.length) {
            return true;
        }
        for (int i = 0; i < charSequenceArr.length; i++) {
            if (!TextUtils.equals(charSequenceArr[i], charSequenceArr2[i])) {
                return true;
            }
        }
        return false;
    }
}
