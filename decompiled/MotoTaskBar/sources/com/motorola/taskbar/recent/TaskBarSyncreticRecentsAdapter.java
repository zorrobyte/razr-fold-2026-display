package com.motorola.taskbar.recent;

import android.content.ComponentName;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.recent.TaskSyncreticController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class TaskBarSyncreticRecentsAdapter extends RecyclerView.Adapter {
    private float mDarkIntensity;
    private final int mDisplayId;
    private TaskSyncreticController mTaskController;
    private TaskList mTaskList = new TaskList();
    private Handler mHandler = new Handler();
    private int mInsertingPosition = -1;
    private TaskSyncreticController.TaskSyncreticChangedListener mTaskChangedListener = new AnonymousClass1();

    /* JADX INFO: renamed from: com.motorola.taskbar.recent.TaskBarSyncreticRecentsAdapter$1, reason: invalid class name */
    class AnonymousClass1 implements TaskSyncreticController.TaskSyncreticChangedListener {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSyncreticTaskCreated$1(TaskSyncreticItem taskSyncreticItem) {
            TaskBarSyncreticRecentsAdapter.this.mTaskList.add(taskSyncreticItem);
            TaskBarSyncreticRecentsAdapter.this.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSyncreticTaskInfoChanged$4(String str, TaskSyncreticItem taskSyncreticItem) {
            TaskBarSyncreticRecentsAdapter.this.updateInsertingStatus(1, -1);
            for (int i = 0; i < TaskBarSyncreticRecentsAdapter.this.mTaskList.size(); i++) {
                if (TaskBarSyncreticRecentsAdapter.this.mTaskList.get(i).getPackageName().equals(str) && TaskBarSyncreticRecentsAdapter.this.mTaskList.get(i).getUserId() == taskSyncreticItem.getUserId()) {
                    TaskBarSyncreticRecentsAdapter.this.mTaskList.remove(i);
                    TaskBarSyncreticRecentsAdapter.this.mTaskList.add(i, taskSyncreticItem);
                    TaskBarSyncreticRecentsAdapter.this.notifyDataSetChanged();
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSyncreticTaskInserted$2(int i, TaskSyncreticItem taskSyncreticItem) {
            TaskBarSyncreticRecentsAdapter.this.updateInsertingStatus(1, -1);
            if (i < 0 || i > TaskBarSyncreticRecentsAdapter.this.mTaskList.size()) {
                TaskBarSyncreticRecentsAdapter.this.mTaskList.add(taskSyncreticItem);
            } else {
                TaskBarSyncreticRecentsAdapter.this.mTaskList.add(i, taskSyncreticItem);
            }
            TaskBarSyncreticRecentsAdapter.this.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSyncreticTaskRemoved$3(String str, int i) {
            TaskBarSyncreticRecentsAdapter.this.updateInsertingStatus(1, -1);
            for (int i2 = 0; i2 < TaskBarSyncreticRecentsAdapter.this.mTaskList.size(); i2++) {
                if (TaskBarSyncreticRecentsAdapter.this.mTaskList.get(i2).getPackageName().equals(str) && TaskBarSyncreticRecentsAdapter.this.mTaskList.get(i2).getUserId() == i) {
                    TaskBarSyncreticRecentsAdapter.this.mTaskList.remove(i2);
                    TaskBarSyncreticRecentsAdapter.this.notifyDataSetChanged();
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSyncreticTasksListReloaded$0() {
            TaskBarSyncreticRecentsAdapter.this.updateInsertingStatus(1, -1);
            TaskBarSyncreticRecentsAdapter.this.mTaskList.clear();
            TaskBarSyncreticRecentsAdapter.this.mTaskList.addAll(TaskBarSyncreticRecentsAdapter.this.mTaskController.getTasks(TaskBarSyncreticRecentsAdapter.this.mDisplayId));
            TaskBarSyncreticRecentsAdapter.this.notifyDataSetChanged();
        }

        @Override // com.motorola.taskbar.recent.TaskSyncreticController.TaskSyncreticChangedListener
        public void onSyncreticTaskCreated(int i, final TaskSyncreticItem taskSyncreticItem) {
            if (TaskBarSyncreticRecentsAdapter.this.mDisplayId != i) {
                return;
            }
            TaskBarSyncreticRecentsAdapter.this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.recent.TaskBarSyncreticRecentsAdapter$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSyncreticTaskCreated$1(taskSyncreticItem);
                }
            });
        }

        @Override // com.motorola.taskbar.recent.TaskSyncreticController.TaskSyncreticChangedListener
        public void onSyncreticTaskInfoChanged(int i, final String str, final TaskSyncreticItem taskSyncreticItem) {
            if (TaskBarSyncreticRecentsAdapter.this.mDisplayId != i) {
                return;
            }
            TaskBarSyncreticRecentsAdapter.this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.recent.TaskBarSyncreticRecentsAdapter$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSyncreticTaskInfoChanged$4(str, taskSyncreticItem);
                }
            });
        }

        @Override // com.motorola.taskbar.recent.TaskSyncreticController.TaskSyncreticChangedListener
        public void onSyncreticTaskInserted(int i, final TaskSyncreticItem taskSyncreticItem, final int i2) {
            if (TaskBarSyncreticRecentsAdapter.this.mDisplayId != i) {
                return;
            }
            TaskBarSyncreticRecentsAdapter.this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.recent.TaskBarSyncreticRecentsAdapter$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSyncreticTaskInserted$2(i2, taskSyncreticItem);
                }
            });
        }

        @Override // com.motorola.taskbar.recent.TaskSyncreticController.TaskSyncreticChangedListener
        public void onSyncreticTaskRemoved(int i, final String str, final int i2) {
            if (TaskBarSyncreticRecentsAdapter.this.mDisplayId != i) {
                return;
            }
            TaskBarSyncreticRecentsAdapter.this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.recent.TaskBarSyncreticRecentsAdapter$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSyncreticTaskRemoved$3(str, i2);
                }
            });
        }

        @Override // com.motorola.taskbar.recent.TaskSyncreticController.TaskSyncreticChangedListener
        public void onSyncreticTasksListReloaded() {
            TaskBarSyncreticRecentsAdapter.this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.recent.TaskBarSyncreticRecentsAdapter$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSyncreticTasksListReloaded$0();
                }
            });
        }
    }

    class TaskList {
        private ArrayList mRealTaskList;
        private int mShortcutsSize;

        private TaskList(TaskBarSyncreticRecentsAdapter taskBarSyncreticRecentsAdapter) {
            this.mShortcutsSize = 0;
            this.mRealTaskList = new ArrayList();
        }

        public void add(int i, TaskSyncreticItem taskSyncreticItem) {
            if (taskSyncreticItem.isShortcut()) {
                this.mShortcutsSize++;
            }
            this.mRealTaskList.add(i, taskSyncreticItem);
        }

        public boolean add(TaskSyncreticItem taskSyncreticItem) {
            if (taskSyncreticItem.isShortcut()) {
                this.mShortcutsSize++;
            }
            return this.mRealTaskList.add(taskSyncreticItem);
        }

        public boolean addAll(Collection collection) {
            Iterator it = collection.iterator();
            while (it.hasNext() && ((TaskSyncreticItem) it.next()).isShortcut()) {
                this.mShortcutsSize++;
            }
            return this.mRealTaskList.addAll(collection);
        }

        public void clear() {
            this.mRealTaskList.clear();
            this.mShortcutsSize = 0;
        }

        public TaskSyncreticItem get(int i) {
            return (TaskSyncreticItem) this.mRealTaskList.get(i);
        }

        public int getItemCount() {
            return this.mRealTaskList.size() + (this.mShortcutsSize > 0 ? 1 : 0);
        }

        public TaskSyncreticItem getTaskSyncreticItem(int i) {
            int i2 = this.mShortcutsSize;
            if (i2 > 0 && i > i2) {
                i--;
            }
            return (TaskSyncreticItem) this.mRealTaskList.get(i);
        }

        public TaskSyncreticItem remove(int i) {
            TaskSyncreticItem taskSyncreticItem = (TaskSyncreticItem) this.mRealTaskList.remove(i);
            int i2 = this.mShortcutsSize;
            if (i < i2) {
                this.mShortcutsSize = i2 - 1;
            }
            return taskSyncreticItem;
        }

        public int size() {
            return this.mRealTaskList.size();
        }
    }

    public static /* synthetic */ void $r8$lambda$OI_UviS_aJGftCiUnJvNan2mr5c(TaskSyncreticItemView taskSyncreticItemView, TaskSyncreticItem taskSyncreticItem) {
        if (taskSyncreticItem.iconCacheKey.equals(taskSyncreticItemView.getTaskIconCacheKey())) {
            taskSyncreticItemView.setIcon(taskSyncreticItem.getIcon());
            taskSyncreticItemView.setContentDescription(taskSyncreticItem.getTitleDescription());
        }
    }

    public TaskBarSyncreticRecentsAdapter(int i, TaskSyncreticController taskSyncreticController, float f) {
        this.mDisplayId = i;
        this.mTaskController = taskSyncreticController;
        this.mTaskList.addAll(taskSyncreticController.getTasks(i));
        this.mTaskController.addCallback(this.mTaskChangedListener);
        this.mDarkIntensity = f;
    }

    public void destroy() {
        this.mTaskController.removeCallback(this.mTaskChangedListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mTaskList.getItemCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (this.mTaskList.mShortcutsSize <= 0 || i != this.mTaskList.mShortcutsSize) {
            return (this.mTaskList.mShortcutsSize <= 0 || i >= this.mTaskList.mShortcutsSize) ? 1 : 0;
        }
        return 2;
    }

    public int getShortcutsSize() {
        return this.mTaskList.mShortcutsSize;
    }

    public void insertShortcut(int i, ComponentName componentName, int i2) {
        this.mTaskController.insertShortcut(this.mDisplayId, i, componentName, i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        TaskSyncreticHolder taskSyncreticHolder = (TaskSyncreticHolder) viewHolder;
        final TaskSyncreticItemView taskSyncreticItemView = taskSyncreticHolder.getTaskSyncreticItemView();
        if (taskSyncreticItemView == null) {
            return;
        }
        TaskSyncreticItem taskSyncreticItem = this.mTaskList.getTaskSyncreticItem(i);
        taskSyncreticHolder.bindTask(taskSyncreticItem);
        this.mTaskController.updateIconInBackground(taskSyncreticItem, new Consumer() { // from class: com.motorola.taskbar.recent.TaskBarSyncreticRecentsAdapter$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskBarSyncreticRecentsAdapter.$r8$lambda$OI_UviS_aJGftCiUnJvNan2mr5c(taskSyncreticItemView, (TaskSyncreticItem) obj);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 2) {
            View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.taskbar_shortcuts_divider, viewGroup, false);
            viewInflate.setLayoutParams(new RecyclerView.LayoutParams(-2, -1));
            return new TaskSyncreticHolder(viewInflate);
        }
        TaskSyncreticItemView taskSyncreticItemView = (TaskSyncreticItemView) LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.task_syncretic_item_view, viewGroup, false);
        taskSyncreticItemView.setDarkIntensity(this.mDarkIntensity);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(taskSyncreticItemView.getContext().getResources().getDimensionPixelSize(R$dimen.taskbar_appitem_width), -1);
        int dimensionPixelSize = taskSyncreticItemView.getContext().getResources().getDimensionPixelSize(R$dimen.taskbar_appitem_gap);
        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = dimensionPixelSize;
        ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = dimensionPixelSize;
        taskSyncreticItemView.setLayoutParams(layoutParams);
        return new TaskSyncreticHolder(taskSyncreticItemView);
    }

    public void setDarkIntensity(float f) {
        this.mDarkIntensity = f;
    }

    public void updateInsertingStatus(int i, int i2) {
        if (i2 < 0) {
            i = 1;
        }
        if (i == 0) {
            int i3 = this.mInsertingPosition;
            if (i2 != i3) {
                this.mTaskList.add(i2, i3 >= 0 ? this.mTaskList.remove(i3) : TaskSyncreticItem.createEmptyShortcutItem());
                this.mInsertingPosition = i2;
                notifyDataSetChanged();
                return;
            }
            return;
        }
        if (i != 1) {
            if (i != 2) {
                return;
            }
            if (i2 >= 0) {
                this.mInsertingPosition = -1;
            }
        }
        int i4 = this.mInsertingPosition;
        if (i4 >= 0) {
            TaskSyncreticItem taskSyncreticItem = this.mTaskList.get(i4);
            if (taskSyncreticItem == null || !taskSyncreticItem.isEmptyShortcutItem()) {
                Log.w("TaskBarSyncreticRecentsAdapter", "updateInsertingStatus exit with wrong inserting item: " + taskSyncreticItem.key.toString());
            } else {
                this.mTaskList.remove(this.mInsertingPosition);
                notifyDataSetChanged();
            }
        }
        this.mInsertingPosition = -1;
    }
}
