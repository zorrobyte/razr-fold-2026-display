package com.motorola.taskbar.recent;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.policy.CallbackController;
import com.motorola.taskbar.recent.RecentTasksList;
import com.motorola.taskbar.recent.TaskIconCache;
import com.motorola.taskbar.recent.TaskThumbnailCache;
import com.motorola.taskbar.reflect.android.app.IActivityTaskManagerR;
import com.motorola.taskbar.util.DebugConfig;
import java.util.ArrayList;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class TaskController implements CallbackController {
    private static final boolean DEBUG = DebugConfig.DEBUG_TASK_CONTROLLER;
    private final Handler mBgHandler;
    private final Context mContext;
    private final Handler mMainHandler;
    private final RecentTasksList mRecentTasksList;
    private final TaskIconCache mTaskIconCache;
    private final TaskThumbnailCache mThumbnailCache;
    private final ArrayList mListeners = new ArrayList();
    private RecentTasksList.RecentTasksListChangedListener mRecentTasksListChangedListener = new AnonymousClass1();
    private final IActivityTaskManagerR mIActivityTaskManagerR = new IActivityTaskManagerR(ActivityTaskManager.getService());

    /* JADX INFO: renamed from: com.motorola.taskbar.recent.TaskController$1, reason: invalid class name */
    class AnonymousClass1 implements RecentTasksList.RecentTasksListChangedListener {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskInfoChanged$0(int i, TaskItem taskItem) {
            TaskController.this.notifyTaskInfoChanged(i, taskItem);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskInfoChanged$1(TaskItem taskItem) {
            if (taskItem == null || taskItem.getNativeTask() == null) {
                return;
            }
            TaskController.this.mThumbnailCache.updateThumbnailInCache(taskItem.getNativeTask(), true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskSnapshotChanged$2(int i, ThumbnailData thumbnailData) {
            TaskController.this.mThumbnailCache.updateTaskSnapShot(i, thumbnailData);
            TaskController.this.notifyTaskSnapshotChanged(i, thumbnailData);
        }

        @Override // com.motorola.taskbar.recent.RecentTasksList.RecentTasksListChangedListener
        public void onTaskCreated(int i, int i2) {
            TaskController.this.notifyTaskCreated(i, i2);
        }

        @Override // com.motorola.taskbar.recent.RecentTasksList.RecentTasksListChangedListener
        public void onTaskInfoChanged(final int i, final TaskItem taskItem) {
            if (Looper.myLooper() == TaskController.this.mMainHandler.getLooper()) {
                TaskController.this.notifyTaskInfoChanged(i, taskItem);
            } else {
                TaskController.this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.recent.TaskController$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onTaskInfoChanged$0(i, taskItem);
                    }
                });
            }
            TaskController.this.mMainHandler.postDelayed(new Runnable() { // from class: com.motorola.taskbar.recent.TaskController$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onTaskInfoChanged$1(taskItem);
                }
            }, 1000L);
        }

        @Override // com.motorola.taskbar.recent.RecentTasksList.RecentTasksListChangedListener
        public void onTaskRemoved(int i, int i2) {
            Task.TaskKey taskKey = new Task.TaskKey(i2, 0, null, null, 0, 0L);
            TaskController.this.mThumbnailCache.remove(taskKey);
            TaskController.this.mTaskIconCache.onTaskRemoved(taskKey);
            TaskController.this.notifyTaskRemoved(i, i2);
        }

        @Override // com.motorola.taskbar.recent.RecentTasksList.RecentTasksListChangedListener
        public void onTaskSnapshotChanged(final int i, final ThumbnailData thumbnailData) {
            if (Looper.myLooper() != TaskController.this.mMainHandler.getLooper()) {
                TaskController.this.mMainHandler.post(new Runnable() { // from class: com.motorola.taskbar.recent.TaskController$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onTaskSnapshotChanged$2(i, thumbnailData);
                    }
                });
            } else {
                TaskController.this.mThumbnailCache.updateTaskSnapShot(i, thumbnailData);
                TaskController.this.notifyTaskSnapshotChanged(i, thumbnailData);
            }
        }

        @Override // com.motorola.taskbar.recent.RecentTasksList.RecentTasksListChangedListener
        public void onTasksListReloaded() {
            TaskController.this.notifyTasksListReloaded();
        }
    }

    public interface TaskChangedListener {
        default void onTaskCreated(int i, TaskItem taskItem) {
        }

        default void onTaskInfoChanged(int i, TaskItem taskItem) {
        }

        default void onTaskRemoved(int i, int i2) {
        }

        default void onTaskSnapshotChanged(int i, ThumbnailData thumbnailData) {
        }

        default void onTasksListReloaded() {
        }
    }

    public TaskController(Context context, Looper looper, Looper looper2) {
        this.mContext = context;
        this.mMainHandler = new Handler(looper);
        this.mBgHandler = new Handler(looper2);
        HandlerThread handlerThread = new HandlerThread("TaskThumbnailIconCache", 10);
        handlerThread.start();
        this.mTaskIconCache = new TaskIconCache(context, handlerThread.getLooper());
        this.mThumbnailCache = new TaskThumbnailCache(context, handlerThread.getLooper());
        RecentTasksList recentTasksList = new RecentTasksList(context);
        this.mRecentTasksList = recentTasksList;
        recentTasksList.addCallback(this.mRecentTasksListChangedListener);
        recentTasksList.start();
        if (DEBUG) {
            TaskStackChangeLogOutput.startChangeMonitor();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTaskCreated(int i, int i2) {
        TaskItem task = this.mRecentTasksList.getTask(i2);
        if (task == null) {
            Log.w("TaskController", "notifyTaskCreated newTask is null");
            return;
        }
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((TaskChangedListener) this.mListeners.get(size)).onTaskCreated(i, task.m2535clone());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTaskInfoChanged(int i, TaskItem taskItem) {
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((TaskChangedListener) this.mListeners.get(size)).onTaskInfoChanged(i, taskItem);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTaskRemoved(int i, int i2) {
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((TaskChangedListener) this.mListeners.get(size)).onTaskRemoved(i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTaskSnapshotChanged(int i, ThumbnailData thumbnailData) {
        TaskItem task = getTask(i);
        if (task != null) {
            this.mThumbnailCache.onTaskSnapshotChanged(task.getTaskKey(), thumbnailData);
        }
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((TaskChangedListener) this.mListeners.get(size)).onTaskSnapshotChanged(i, thumbnailData);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTasksListReloaded() {
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((TaskChangedListener) this.mListeners.get(size)).onTasksListReloaded();
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(TaskChangedListener taskChangedListener) {
        this.mListeners.add(taskChangedListener);
    }

    public TaskThumbnailCache.HighResLoadingState getHighResLoadingState() {
        return this.mThumbnailCache.getHighResLoadingState();
    }

    public TaskItem getTask(int i) {
        return this.mRecentTasksList.getTask(i);
    }

    public ArrayList getTasks(int i) {
        return this.mRecentTasksList.getTasks(i);
    }

    public void launchTaskFromView(Task task, View view, int i) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        Task.TaskKey taskKey = task.key;
        ActivityOptions activityOptionsMakeClipRevealAnimation = ActivityOptions.makeClipRevealAnimation(view, 0, 0, measuredWidth, measuredHeight);
        activityOptionsMakeClipRevealAnimation.setLaunchDisplayId(i);
        activityOptionsMakeClipRevealAnimation.setLaunchActivityType(0);
        ActivityManagerWrapper.getInstance().startActivityFromRecents(taskKey, activityOptionsMakeClipRevealAnimation);
    }

    public boolean moveTaskToBack(TaskItem taskItem) {
        return this.mIActivityTaskManagerR.moveTaskToBack(taskItem.getTaskId());
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(TaskChangedListener taskChangedListener) {
        this.mListeners.remove(taskChangedListener);
    }

    public void removeTask(int i) {
        this.mRecentTasksList.removeTask(i);
    }

    public TaskIconCache.IconLoadRequest updateIconInBackground(Task task, Consumer consumer) {
        return this.mTaskIconCache.updateIconInBackground(task, consumer);
    }

    public TaskThumbnailCache.ThumbnailLoadRequest updateThumbnailInBackground(Task task, Consumer consumer) {
        return this.mThumbnailCache.updateThumbnailInBackground(task, consumer);
    }
}
