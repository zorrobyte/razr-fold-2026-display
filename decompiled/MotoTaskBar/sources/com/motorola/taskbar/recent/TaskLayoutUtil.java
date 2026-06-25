package com.motorola.taskbar.recent;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import com.android.systemui.shared.recents.model.Task;
import com.motorola.taskbar.R$dimen;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TaskLayoutUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class TaskLayoutUtil {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: TaskLayoutUtil.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final float getTaskCornerRadius(Context context) {
            context.getClass();
            return context.getResources().getDimension(R$dimen.task_view_corner_radius);
        }

        public final CharSequence getTitle(Context context, Task task) {
            Object objM2707constructorimpl;
            context.getClass();
            task.getClass();
            Object systemService = context.getSystemService((Class<Object>) LauncherApps.class);
            systemService.getClass();
            LauncherApps launcherApps = (LauncherApps) systemService;
            PackageManager packageManager = context.getPackageManager();
            UserHandle userHandleOf = UserHandle.of(task.key.userId);
            userHandleOf.getClass();
            try {
                Result.Companion companion = Result.Companion;
                ApplicationInfo applicationInfo = launcherApps.getApplicationInfo(task.getTopComponent().getPackageName(), 0, userHandleOf);
                applicationInfo.getClass();
                objM2707constructorimpl = Result.m2707constructorimpl(packageManager.getUserBadgedLabel(applicationInfo.loadLabel(packageManager), userHandleOf));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
            }
            if (Result.m2711isFailureimpl(objM2707constructorimpl)) {
                objM2707constructorimpl = null;
            }
            return (CharSequence) objM2707constructorimpl;
        }
    }
}
