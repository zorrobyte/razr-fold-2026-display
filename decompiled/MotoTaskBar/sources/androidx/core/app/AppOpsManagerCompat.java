package androidx.core.app;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;

/* JADX INFO: loaded from: classes.dex */
public abstract class AppOpsManagerCompat {

    abstract class Api23Impl {
        static Object getSystemService(Context context, Class cls) {
            return context.getSystemService(cls);
        }

        static int noteProxyOpNoThrow(AppOpsManager appOpsManager, String str, String str2) {
            return appOpsManager.noteProxyOpNoThrow(str, str2);
        }

        static String permissionToOp(String str) {
            return AppOpsManager.permissionToOp(str);
        }
    }

    abstract class Api29Impl {
        static int checkOpNoThrow(AppOpsManager appOpsManager, String str, int i, String str2) {
            if (appOpsManager == null) {
                return 1;
            }
            return appOpsManager.checkOpNoThrow(str, i, str2);
        }

        static String getOpPackageName(Context context) {
            return context.getOpPackageName();
        }

        static AppOpsManager getSystemService(Context context) {
            return (AppOpsManager) context.getSystemService(AppOpsManager.class);
        }
    }

    public static int checkOrNoteProxyOp(Context context, int i, String str, String str2) {
        AppOpsManager systemService = Api29Impl.getSystemService(context);
        int iCheckOpNoThrow = Api29Impl.checkOpNoThrow(systemService, str, Binder.getCallingUid(), str2);
        return iCheckOpNoThrow != 0 ? iCheckOpNoThrow : Api29Impl.checkOpNoThrow(systemService, str, i, Api29Impl.getOpPackageName(context));
    }

    public static int noteProxyOpNoThrow(Context context, String str, String str2) {
        return Api23Impl.noteProxyOpNoThrow((AppOpsManager) Api23Impl.getSystemService(context, AppOpsManager.class), str, str2);
    }

    public static String permissionToOp(String str) {
        return Api23Impl.permissionToOp(str);
    }
}
