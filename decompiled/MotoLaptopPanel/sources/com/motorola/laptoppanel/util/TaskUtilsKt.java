package com.motorola.laptoppanel.util;

import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Rect;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TaskUtils.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TaskUtilsKt {
    private static final String activityTypeToString(int i) {
        if (i == 0) {
            return "UNDEFINED";
        }
        if (i == 1) {
            return "STANDARD";
        }
        if (i == 2) {
            return "HOME";
        }
        if (i == 3) {
            return "RECENTS";
        }
        if (i == 4) {
            return "ASSISTANT";
        }
        if (i == 5) {
            return "DREAM";
        }
        return "UNKNOWN (" + i + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int getTaskPriority(ActivityManager.RunningTaskInfo runningTaskInfo, String str) {
        ComponentName componentName = runningTaskInfo.topActivity;
        if (Intrinsics.areEqual(componentName != null ? componentName.getPackageName() : null, str)) {
            return 1;
        }
        return isCoreSystemUiTask(runningTaskInfo) ? 2 : 0;
    }

    private static final Rect getTopHalfRect(Context context) {
        try {
            Rect bounds = WindowMetricsCalculator.Companion.getOrCreate().computeMaximumWindowMetrics(context).getBounds();
            WindowMetrics currentWindowMetrics = ((WindowManager) context.getSystemService(WindowManager.class)).getCurrentWindowMetrics();
            currentWindowMetrics.getClass();
            Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
            insetsIgnoringVisibility.getClass();
            Integer num = (Integer) CollectionsKt.maxOrNull(CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(insetsIgnoringVisibility.left), Integer.valueOf(insetsIgnoringVisibility.top), Integer.valueOf(insetsIgnoringVisibility.right), Integer.valueOf(insetsIgnoringVisibility.bottom)}));
            int iIntValue = num != null ? num.intValue() : 0;
            Rect rect = new Rect(bounds.left + insetsIgnoringVisibility.left, bounds.top + insetsIgnoringVisibility.top, bounds.right - insetsIgnoringVisibility.right, bounds.bottom - insetsIgnoringVisibility.bottom);
            if (rect.isEmpty()) {
                Logger.INSTANCE.w("TaskUtils", "getTopHalfRect: Calculated stable bounds are empty.", new Object[0]);
                return new Rect();
            }
            Rect rect2 = new Rect(rect.left, rect.top, rect.right, rect.centerY());
            rect2.inset(iIntValue, iIntValue);
            if (rect2.isEmpty()) {
                Logger.INSTANCE.w("TaskUtils", "getTopHalfRect: Rectangle became empty after applying insets.", new Object[0]);
                return new Rect();
            }
            Logger.INSTANCE.v("TaskUtils", "getTopHalfRect: Calculated usable top half: " + rect2, new Object[0]);
            return rect2;
        } catch (Exception e) {
            Logger.INSTANCE.e("TaskUtils", (Throwable) e, "getTopHalfRect: Failed to calculate usable top half rect.", new Object[0]);
            return new Rect();
        }
    }

    public static final ActivityManager.RunningTaskInfo getTopRunningTask(Context context) {
        Configuration configuration;
        WindowConfiguration windowConfiguration;
        Rect bounds;
        context.getClass();
        try {
            Object systemService = context.getSystemService("activity");
            systemService.getClass();
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) systemService).getRunningTasks(20);
            if (runningTasks != null && !runningTasks.isEmpty()) {
                logRunningTasks(runningTasks, context);
                Rect topHalfRect = getTopHalfRect(context);
                ArrayList arrayList = new ArrayList();
                for (Object obj : runningTasks) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
                    if (runningTaskInfo.isVisible && (configuration = runningTaskInfo.configuration) != null && (windowConfiguration = configuration.windowConfiguration) != null && (bounds = windowConfiguration.getBounds()) != null && !bounds.isEmpty() && taskCoversRect(runningTaskInfo, topHalfRect)) {
                        arrayList.add(obj);
                    }
                }
                if (arrayList.isEmpty()) {
                    Logger.INSTANCE.d("TaskUtils", "getTopRunningTask: No visible tasks found covering the top half rect.", new Object[0]);
                    return null;
                }
                logVisibleTasks(arrayList, context);
                if (arrayList.size() == 1) {
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) CollectionsKt.first(arrayList);
                    Logger logger = Logger.INSTANCE;
                    ComponentName componentName = runningTaskInfo2.topActivity;
                    String packageName = componentName != null ? componentName.getPackageName() : null;
                    logger.d("TaskUtils", "getTopRunningTask: Only one visible task found is " + packageName + "(" + runningTaskInfo2.taskId + ")", new Object[0]);
                    return runningTaskInfo2;
                }
                final String packageName2 = context.getPackageName();
                List listSortedWith = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: com.motorola.laptoppanel.util.TaskUtilsKt$getTopRunningTask$$inlined$compareBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj2, Object obj3) {
                        ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) obj2;
                        runningTaskInfo3.getClass();
                        packageName2.getClass();
                        Integer numValueOf = Integer.valueOf(TaskUtilsKt.getTaskPriority(runningTaskInfo3, packageName2));
                        ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) obj3;
                        runningTaskInfo4.getClass();
                        packageName2.getClass();
                        return ComparisonsKt.compareValues(numValueOf, Integer.valueOf(TaskUtilsKt.getTaskPriority(runningTaskInfo4, packageName2)));
                    }
                });
                logPrioritizedTasks(listSortedWith, context);
                ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) CollectionsKt.first(listSortedWith);
                Logger logger2 = Logger.INSTANCE;
                ComponentName componentName2 = runningTaskInfo3.topActivity;
                String packageName3 = componentName2 != null ? componentName2.getPackageName() : null;
                logger2.d("TaskUtils", "getTopRunningTask: Final choice is " + packageName3 + "(" + runningTaskInfo3.taskId + ")", new Object[0]);
                return runningTaskInfo3;
            }
            Logger.INSTANCE.w("TaskUtils", "getTopRunningTask: getRunningTasks() returned an empty or null list.", new Object[0]);
            return null;
        } catch (SecurityException e) {
            Logger.INSTANCE.e("TaskUtils", (Throwable) e, "getTopRunningTask: Permission denied. REAL_GET_TASKS is required.", new Object[0]);
            return null;
        } catch (Exception e2) {
            Logger.INSTANCE.e("TaskUtils", (Throwable) e2, "getTopRunningTask: An unexpected error occurred while getting tasks.", new Object[0]);
            return null;
        }
    }

    private static final boolean isCoreSystemUiTask(ActivityManager.RunningTaskInfo runningTaskInfo) {
        return runningTaskInfo.getActivityType() == 2 || runningTaskInfo.getActivityType() == 3;
    }

    private static final void logPrioritizedTasks(List list, Context context) {
        Logger.INSTANCE.v("TaskUtils", "Prioritized task queue has " + list.size() + " items:", new Object[0]);
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
            ComponentName componentName = runningTaskInfo.topActivity;
            String packageName = componentName != null ? componentName.getPackageName() : null;
            String packageName2 = context.getPackageName();
            packageName2.getClass();
            int taskPriority = getTaskPriority(runningTaskInfo, packageName2);
            Logger.INSTANCE.v("TaskUtils", "  -> Index " + i + ": taskId=" + runningTaskInfo.taskId + ", pkg=" + packageName + ", priority=" + taskPriority + ", bounds=" + runningTaskInfo.configuration.windowConfiguration.getBounds() + ", mode=" + windowingModeToString(runningTaskInfo.configuration.windowConfiguration.getWindowingMode()) + ", type=" + activityTypeToString(runningTaskInfo.getActivityType()), new Object[0]);
            i = i2;
        }
    }

    private static final void logRunningTasks(List list, Context context) {
        Logger.INSTANCE.d("TaskUtils", "getTopRunningTask: Found " + list.size() + " running tasks.", new Object[0]);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) it.next();
            ComponentName componentName = runningTaskInfo.topActivity;
            if (componentName != null) {
                componentName.getPackageName();
            }
            Logger logger = Logger.INSTANCE;
            int i = runningTaskInfo.taskId;
            ComponentName componentName2 = runningTaskInfo.topActivity;
            String packageName = componentName2 != null ? componentName2.getPackageName() : null;
            logger.d("TaskUtils", "  -> Running Task: taskId=" + i + ", pkg=" + packageName + ", visible=" + runningTaskInfo.isVisible + ", bounds=" + runningTaskInfo.configuration.windowConfiguration.getBounds() + ", mode=" + windowingModeToString(runningTaskInfo.configuration.windowConfiguration.getWindowingMode()) + ", type=" + activityTypeToString(runningTaskInfo.getActivityType()), new Object[0]);
        }
    }

    private static final void logVisibleTasks(List list, Context context) {
        Logger.INSTANCE.v("TaskUtils", "getTopRunningTask: Found " + list.size() + " visible tasks.", new Object[0]);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) it.next();
            ComponentName componentName = runningTaskInfo.topActivity;
            if (componentName != null) {
                componentName.getPackageName();
            }
            Logger logger = Logger.INSTANCE;
            int i = runningTaskInfo.taskId;
            ComponentName componentName2 = runningTaskInfo.topActivity;
            String packageName = componentName2 != null ? componentName2.getPackageName() : null;
            logger.v("TaskUtils", "  -> Visible Task: taskId=" + i + ", pkg=" + packageName + ", bounds=" + runningTaskInfo.configuration.windowConfiguration.getBounds() + ", mode=" + windowingModeToString(runningTaskInfo.configuration.windowConfiguration.getWindowingMode()) + ", type=" + activityTypeToString(runningTaskInfo.getActivityType()), new Object[0]);
        }
    }

    private static final boolean taskCoversRect(ActivityManager.RunningTaskInfo runningTaskInfo, Rect rect) {
        Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
        bounds.getClass();
        return bounds.contains(rect);
    }

    private static final String windowingModeToString(int i) {
        if (i == 0) {
            return "UNDEFINED";
        }
        if (i == 1) {
            return "FULLSCREEN";
        }
        if (i == 2) {
            return "PINNED";
        }
        if (i == 5) {
            return "FREEFORM";
        }
        if (i == 6) {
            return "MULTI_WINDOW";
        }
        return "UNKNOWN (" + i + ")";
    }
}
