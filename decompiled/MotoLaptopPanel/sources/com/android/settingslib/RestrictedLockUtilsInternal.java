package com.android.settingslib;

import android.app.admin.DevicePolicyManager;
import android.app.admin.EnforcingAdmin;
import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.settingslib.RestrictedLockUtils;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class RestrictedLockUtilsInternal extends RestrictedLockUtils {
    private static final boolean DEBUG = Log.isLoggable("RestrictedLockUtils", 3);
    static Proxy sProxy = new Proxy();

    class Proxy {
        Proxy() {
        }
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced(Context context, String str, int i) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            return null;
        }
        UserManager userManager = UserManager.get(context);
        UserHandle userHandleOf = UserHandle.of(i);
        List userRestrictionSources = userManager.getUserRestrictionSources(str, userHandleOf);
        if (userRestrictionSources.isEmpty()) {
            return null;
        }
        int size = userRestrictionSources.size();
        if (size <= 1) {
            UserManager.EnforcingUser enforcingUser = (UserManager.EnforcingUser) userRestrictionSources.get(0);
            if (enforcingUser.getUserRestrictionSource() == 1) {
                return null;
            }
            EnforcingAdmin enforcingAdmin = devicePolicyManager.getEnforcingAdmin(i, str);
            if (enforcingAdmin != null) {
                return new RestrictedLockUtils.EnforcedAdmin(enforcingAdmin.getComponentName(), str, enforcingAdmin.getUserHandle());
            }
            RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner = RestrictedLockUtils.getProfileOrDeviceOwner(context, str, enforcingUser.getUserHandle());
            return profileOrDeviceOwner != null ? profileOrDeviceOwner : RestrictedLockUtils.EnforcedAdmin.createDefaultEnforcedAdminWithRestriction(str);
        }
        RestrictedLockUtils.EnforcedAdmin enforcedAdminCreateDefaultEnforcedAdminWithRestriction = RestrictedLockUtils.EnforcedAdmin.createDefaultEnforcedAdminWithRestriction(str);
        enforcedAdminCreateDefaultEnforcedAdminWithRestriction.user = userHandleOf;
        if (DEBUG) {
            Log.d("RestrictedLockUtils", "Multiple (" + size + ") enforcing users for restriction '" + str + "' on user " + userHandleOf + "; returning default admin (" + enforcedAdminCreateDefaultEnforcedAdminWithRestriction + ")");
        }
        return enforcedAdminCreateDefaultEnforcedAdminWithRestriction;
    }

    public static boolean hasBaseUserRestriction(Context context, String str, int i) {
        return ((UserManager) context.getSystemService("user")).hasBaseUserRestriction(str, UserHandle.of(i));
    }
}
