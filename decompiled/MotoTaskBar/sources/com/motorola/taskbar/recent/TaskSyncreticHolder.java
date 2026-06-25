package com.motorola.taskbar.recent;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes2.dex */
public final class TaskSyncreticHolder extends RecyclerView.ViewHolder {
    private TaskSyncreticItem mTask;
    private final TaskSyncreticItemView mTaskSyncreticItemView;

    public TaskSyncreticHolder(View view) {
        super(view);
        if (view instanceof TaskSyncreticItemView) {
            this.mTaskSyncreticItemView = (TaskSyncreticItemView) view;
        } else {
            this.mTaskSyncreticItemView = null;
        }
    }

    public void bindTask(TaskSyncreticItem taskSyncreticItem) {
        this.mTask = taskSyncreticItem;
        this.mTaskSyncreticItemView.setTask(taskSyncreticItem);
    }

    public TaskSyncreticItemView getTaskSyncreticItemView() {
        return this.mTaskSyncreticItemView;
    }
}
