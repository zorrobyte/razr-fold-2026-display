package com.motorola.taskbar.recent;

import android.graphics.drawable.Drawable;
import android.util.Log;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.motorola.taskbar.util.DebugConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class TaskItem implements Cloneable {
    private static final boolean DEBUG = DebugConfig.DEBUG_TASK_CONTROLLER;
    private boolean mIsTop;
    private Task mTask;

    public TaskItem(Task task) {
        this.mIsTop = false;
        this.mTask = task;
    }

    private TaskItem(TaskItem taskItem) {
        this.mIsTop = false;
        this.mTask = taskItem.mTask;
        this.mIsTop = taskItem.mIsTop;
    }

    public static String getSyncreticKey(String str, int i, int i2) {
        return str + "#" + i2 + "#" + i;
    }

    public static ArrayList obtainTaskItemList(List list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Task task = (Task) it.next();
                if (task.key.displayId < 0) {
                    Log.w("RecentTasksList", "obtainTaskItemList: with invalid display id " + RecentTasksList.taskToString(task));
                } else {
                    arrayList.add(new TaskItem(task));
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public TaskItem m2535clone() {
        return new TaskItem(this);
    }

    public int getDisplayId() {
        return this.mTask.key.displayId;
    }

    public Drawable getIcon() {
        return this.mTask.icon;
    }

    public Task getNativeTask() {
        return this.mTask;
    }

    public String getPackageName() {
        return getTaskKey().getPackageName();
    }

    public String getSyncreticKey() {
        return getSyncreticKey(getPackageName(), getDisplayId(), getTaskKey().userId);
    }

    public int getTaskId() {
        return this.mTask.key.id;
    }

    public Task.TaskKey getTaskKey() {
        return this.mTask.key;
    }

    public ThumbnailData getThumbnailData() {
        return this.mTask.thumbnail;
    }

    public String getTitleDescription() {
        return this.mTask.titleDescription;
    }

    public boolean isTop() {
        return this.mIsTop;
    }

    public void setIsTop(boolean z) {
        this.mIsTop = z;
        if (DEBUG) {
            Log.d("RecentTasksList", "setIsTop = " + z + ";key: " + this.mTask.key.getPackageName() + "; displayId: " + getDisplayId());
        }
    }

    public void setThumbnailData(ThumbnailData thumbnailData) {
        this.mTask.thumbnail = thumbnailData;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        sb.append("package=" + getPackageName());
        sb.append(", taskId=" + getTaskId());
        sb.append(", userId=" + getTaskKey().userId);
        sb.append(", displayId=" + getDisplayId());
        sb.append(", component=" + getTaskKey().getComponent());
        sb.append(", isTop=" + isTop());
        sb.append("]");
        return sb.toString();
    }

    public void updateFromSrc(TaskItem taskItem) {
        this.mIsTop = taskItem.mIsTop;
        this.mTask = taskItem.mTask;
    }

    public void updateThumbnailData(ThumbnailData thumbnailData) {
        this.mTask.thumbnail = thumbnailData;
    }
}
