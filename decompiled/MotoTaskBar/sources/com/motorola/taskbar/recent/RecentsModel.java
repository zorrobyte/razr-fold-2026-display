package com.motorola.taskbar.recent;

import android.content.Context;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.android.systemui.Dependency;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.motorola.internal.app.MotoDesktopManager;
import com.motorola.taskbar.recent.TaskController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: RecentsModel.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RecentsModel extends ViewModel implements TaskController.TaskChangedListener {
    private final MutableLiveData _recentsSizeData;
    private final MutableLiveData _taskSnapshotData;
    private final Context context;
    private final int displayId;
    private final Lazy recentsController$delegate;
    private final TaskController taskController;

    public RecentsModel(int i, Context context) {
        context.getClass();
        this.displayId = i;
        this.context = context;
        Object obj = Dependency.get(TaskController.class);
        obj.getClass();
        TaskController taskController = (TaskController) obj;
        this.taskController = taskController;
        taskController.addCallback((TaskController.TaskChangedListener) this);
        this.recentsController$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.motorola.taskbar.recent.RecentsModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return RecentsModel.recentsController_delegate$lambda$0();
            }
        });
        this._recentsSizeData = new MutableLiveData(new ArrayList());
        this._taskSnapshotData = new MutableLiveData();
    }

    private final RecentsController getRecentsController() {
        return (RecentsController) this.recentsController$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final RecentsController recentsController_delegate$lambda$0() {
        return (RecentsController) Dependency.get(RecentsController.class);
    }

    private final void reloadTaskList() {
        this._recentsSizeData.postValue(this.taskController.getTasks(this.displayId));
    }

    public final void clearAllTask(View view) {
        view.getClass();
        List listEmptyList = (List) this._recentsSizeData.getValue();
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        Iterator it = listEmptyList.iterator();
        while (it.hasNext()) {
            this.taskController.removeTask(((TaskItem) it.next()).getTaskId());
        }
    }

    public final void closeTask(View view, int i) {
        view.getClass();
        this.taskController.removeTask(i);
    }

    public final LiveData getRecentsTasksListData() {
        return this._recentsSizeData;
    }

    public final TaskController getTaskController() {
        return this.taskController;
    }

    public final LiveData getTaskSnapshotData() {
        return this._taskSnapshotData;
    }

    public final void launchTask(View view, Task task, boolean z) {
        view.getClass();
        task.getClass();
        getRecentsController().hideRecents(this.displayId, z);
        this.taskController.launchTaskFromView(task, view, this.displayId);
    }

    @Override // androidx.lifecycle.ViewModel
    protected void onCleared() {
        this.taskController.removeCallback((TaskController.TaskChangedListener) this);
        super.onCleared();
    }

    @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
    public void onTaskCreated(int i, TaskItem taskItem) {
        taskItem.getClass();
        if (i != this.displayId) {
            return;
        }
        reloadTaskList();
    }

    @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
    public void onTaskRemoved(int i, int i2) {
        if (i != this.displayId) {
            return;
        }
        if (MotoDesktopManager.isAppStreamMode(this.context.getDisplay()) && this.taskController.getTasks(i).size() == 1) {
            getRecentsController().hideRecents(i, false);
        }
        reloadTaskList();
    }

    @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
    public void onTaskSnapshotChanged(int i, ThumbnailData thumbnailData) {
        thumbnailData.getClass();
        this._taskSnapshotData.setValue(new Pair(Integer.valueOf(i), thumbnailData));
    }

    @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
    public void onTasksListReloaded() {
        reloadTaskList();
    }

    public final void reloadHighRes() {
        this.taskController.getHighResLoadingState().setVisible(true);
        reloadTaskList();
    }
}
