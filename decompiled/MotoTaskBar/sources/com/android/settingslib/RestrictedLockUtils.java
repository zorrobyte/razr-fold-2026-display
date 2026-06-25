package com.android.settingslib;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

/* JADX INFO: loaded from: classes.dex */
public abstract class RestrictedLockUtils {

    public abstract class EnforcedAdmin {
    }

    public static Intent getShowAdminSupportDetailsIntent(Context context, EnforcedAdmin enforcedAdmin) {
        return new Intent("android.settings.SHOW_ADMIN_SUPPORT_DETAILS");
    }

    public static void sendShowAdminSupportDetailsIntent(Context context, EnforcedAdmin enforcedAdmin) {
        context.startActivityAsUser(getShowAdminSupportDetailsIntent(context, enforcedAdmin), UserHandle.of(UserHandle.myUserId()));
    }
}
