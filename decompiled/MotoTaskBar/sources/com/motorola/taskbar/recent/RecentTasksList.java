package com.motorola.taskbar.recent;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.Display;
import com.android.systemui.Dependency;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.BackgroundExecutor;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.motorola.taskbar.TaskBarServiceCallBack;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.util.ActivityManagerWrapperExt;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.taskbar.util.MainThreadExecutor;
import com.motorola.taskbar.util.TaskStackChangeListenerExt;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class RecentTasksList implements CallbackController, TaskBarServiceCallBack, TaskStackChangeListener {
    private static final boolean DEBUG = DebugConfig.DEBUG_TASK_CONTROLLER;
    private static final Set sIgnorePackageNames;
    private final Context mContext;
    private final DisplayManager mDisplayManager;
    private final KeyguardManager mKeyguardManager;
    private final ArrayList mListeners = new ArrayList();
    private final Object mLock = new Object();
    private ArrayList mAllTasks = new ArrayList();
    private SparseArray mAllTasksMap = new SparseArray();
    private SparseArray mDisplaysTasks = new SparseArray();
    private SparseArray mDisplayTopTasks = new SparseArray();
    private DeviceProvisionedController.DeviceProvisionedListener mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.motorola.taskbar.recent.RecentTasksList.1
        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onUserSwitched() {
            if (RecentTasksList.DEBUG) {
                Log.d("RecentTasksList", "onUserSwitched");
            }
            RecentTasksList.this.reLoadInBackground();
        }
    };
    private TaskStackChangeListenerExt mTaskStackChangeListenerExt = new TaskStackChangeListenerExt() { // from class: com.motorola.taskbar.recent.RecentTasksList.2
        @Override // com.motorola.taskbar.util.TaskStackChangeListenerExt
        public void onTaskFocusChanged(int i, boolean z) {
            if (RecentTasksList.DEBUG) {
                Log.d("RecentTasksList", "onTaskFocusChanged: " + i + "; focused" + z);
            }
            if (z) {
                RecentTasksList.this.onTaskMovedToFront(i);
            }
        }
    };
    private Runnable mRefreshTaskStacks = new Runnable() { // from class: com.motorola.taskbar.recent.RecentTasksList$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$2();
        }
    };
    private final MainThreadExecutor mMainThreadExecutor = new MainThreadExecutor();
    private final BackgroundExecutor mBgThreadExecutor = BackgroundExecutor.get();
    private final Handler mBgHandler = new Handler((Looper) Dependency.get(Dependency.BG_LOOPER));
    private final DeviceProvisionedController mDeviceProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);
    private final ActivityManagerWrapper mActivityManagerWrapper = ActivityManagerWrapper.getInstance();
    private final ActivityManagerWrapperExt mActivityManagerWrapperExt = ActivityManagerWrapperExt.getInstance();

    interface RecentTasksListChangedListener {
        void onTaskCreated(int i, int i2);

        void onTaskInfoChanged(int i, TaskItem taskItem);

        void onTaskRemoved(int i, int i2);

        void onTaskSnapshotChanged(int i, ThumbnailData thumbnailData);

        void onTasksListReloaded();
    }

    static {
        HashSet hashSet = new HashSet();
        sIgnorePackageNames = hashSet;
        hashSet.add("com.motorola.motcameradesktop");
        hashSet.add("com.microsoft.deviceexperiencesservice");
        hashSet.add("com.microsoft.android.cloudconnect");
    }

    public RecentTasksList(Context context) {
        this.mContext = context;
        this.mKeyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
    }

    private void addTaskLock(TaskItem taskItem) {
        if (taskItem == null) {
            return;
        }
        if (DEBUG) {
            Log.d("RecentTasksList", "addTaskLock: " + taskItem.toString());
        }
        int displayId = taskItem.getDisplayId();
        List arrayList = (List) this.mDisplaysTasks.get(taskItem.getDisplayId());
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mDisplaysTasks.append(displayId, arrayList);
        }
        arrayList.add(taskItem);
        this.mAllTasks.add(taskItem);
        this.mAllTasksMap.put(taskItem.getTaskId(), taskItem);
    }

    private void cleanTopTask(final int i) {
        if (DEBUG) {
            Log.d("RecentTasksList", "cleanTopTask displayId: " + i);
        }
        this.mBgThreadExecutor.submit(new Runnable() { // from class: com.motorola.taskbar.recent.RecentTasksList$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$cleanTopTask$3(i);
            }
        });
    }

    private ArrayList copyOf(List list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(((TaskItem) list.get(i)).m2535clone());
        }
        return arrayList;
    }

    private String getRecentTaskInfoPackageName(ActivityManager.RecentTaskInfo recentTaskInfo) {
        ComponentName componentName = recentTaskInfo.baseActivity;
        if (componentName != null) {
            return componentName.getPackageName();
        }
        ComponentName componentName2 = recentTaskInfo.origActivity;
        if (componentName2 != null) {
            return componentName2.getPackageName();
        }
        ComponentName componentName3 = recentTaskInfo.realActivity;
        if (componentName3 != null) {
            return componentName3.getPackageName();
        }
        return null;
    }

    private boolean isGameModeDashboard(ActivityManager.RecentTaskInfo recentTaskInfo) {
        try {
            if ("com.motorola.gamemode".equals(getRecentTaskInfoPackageName(recentTaskInfo)) && "com.motorola.gamemode.desktop.LAUNCH_DASHBOARD".equals(recentTaskInfo.baseIntent.getAction())) {
                return true;
            }
            if ("com.motorola.leanbacklauncher".equals(getRecentTaskInfoPackageName(recentTaskInfo))) {
                if ("com.motorola.leanbacklauncher.desktop.LAUNCH_DASHBOARD".equals(recentTaskInfo.baseIntent.getAction())) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    private boolean isSecondaryDisplayAppDrawer(ActivityManager.RecentTaskInfo recentTaskInfo) {
        try {
            return "com.motorola.launcher3.secondarydisplay.action.SECONDARY_DISPLAY_APP_DRAWER2".equals(recentTaskInfo.baseIntent.getAction());
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanTopTask$3(int i) {
        synchronized (this.mLock) {
            try {
                TaskItem taskItem = (TaskItem) this.mDisplayTopTasks.get(i);
                if (taskItem != null) {
                    this.mDisplayTopTasks.remove(i);
                    taskItem.setIsTop(false);
                    lambda$onTaskMovedToFront$4(taskItem);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        int i;
        TaskItem taskItem;
        Pair pairLoadTasksInBackground = loadTasksInBackground(Integer.MAX_VALUE);
        SparseArray sparseArray = (SparseArray) pairLoadTasksInBackground.second;
        ArrayList arrayList = (ArrayList) pairLoadTasksInBackground.first;
        if (arrayList == null) {
            return;
        }
        if (DEBUG) {
            Log.d("RecentTasksList", "onTaskStackChangedBackground begin");
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        SparseArray sparseArray2 = new SparseArray();
        synchronized (this.mLock) {
            try {
                SparseArray sparseArrayClone = this.mAllTasksMap.clone();
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    Task task = (Task) arrayList.get(size);
                    if (DEBUG) {
                        Log.d("RecentTasksList", "onTaskStackChangedBackground: " + taskToString(task));
                    }
                    int i2 = task.key.displayId;
                    if (i2 < 0) {
                        Log.w("RecentTasksList", "onTaskStackChangedBackground: with invalid display id " + taskToString(task));
                    } else {
                        Integer num = (Integer) sparseArray.get(i2);
                        if (num != null && task.key.id == num.intValue()) {
                            sparseArray2.put(task.key.displayId, task);
                        }
                        int i3 = ((Task) arrayList.get(size)).key.id;
                        sparseArrayClone.remove(i3);
                        TaskItem taskItem2 = (TaskItem) this.mAllTasksMap.get(i3);
                        if (taskItem2 == null) {
                            TaskItem taskItem3 = new TaskItem((Task) arrayList.get(size));
                            addTaskLock(taskItem3);
                            arrayList2.add(taskItem3);
                        } else if (taskItem2.getDisplayId() != ((Task) arrayList.get(size)).key.displayId) {
                            removeTaskLock(taskItem2);
                            arrayList3.add(taskItem2);
                            TaskItem taskItem4 = new TaskItem((Task) arrayList.get(size));
                            addTaskLock(taskItem4);
                            arrayList2.add(taskItem4);
                        }
                    }
                }
                for (int i4 = 0; i4 < sparseArrayClone.size(); i4++) {
                    TaskItem taskItem5 = (TaskItem) this.mAllTasksMap.get(sparseArrayClone.keyAt(i4));
                    if (taskItem5 != null) {
                        removeTaskLock(taskItem5);
                        arrayList3.add(taskItem5);
                    }
                }
            } finally {
            }
        }
        int size2 = arrayList3.size();
        int i5 = 0;
        while (i5 < size2) {
            Object obj = arrayList3.get(i5);
            i5++;
            TaskItem taskItem6 = (TaskItem) obj;
            notifyTaskRemoved(taskItem6.getDisplayId(), taskItem6.getTaskId());
        }
        int size3 = arrayList2.size();
        int i6 = 0;
        while (i6 < size3) {
            Object obj2 = arrayList2.get(i6);
            i6++;
            TaskItem taskItem7 = (TaskItem) obj2;
            notifyTaskCreated(taskItem7.getDisplayId(), taskItem7.getTaskId());
        }
        synchronized (this.mLock) {
            for (i = 0; i < this.mDisplaysTasks.size(); i++) {
                try {
                    List list = (List) this.mDisplaysTasks.valueAt(i);
                    int iKeyAt = this.mDisplaysTasks.keyAt(i);
                    Task task2 = (Task) sparseArray2.get(iKeyAt);
                    if (task2 != null) {
                        taskItem = (TaskItem) this.mAllTasksMap.get(task2.key.id);
                    } else {
                        list.size();
                        taskItem = null;
                    }
                    if (taskItem != null) {
                        taskItem.setIsTop(true);
                        onTaskMovedToFront(taskItem.getTaskId());
                    } else {
                        cleanTopTask(iKeyAt);
                    }
                } finally {
                }
            }
        }
        if (DEBUG) {
            Log.d("RecentTasksList", "onTaskStackChangedBackground end");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTaskMovedToFront$5(int i) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                TaskItem taskItem = (TaskItem) this.mAllTasksMap.get(i);
                if (taskItem == null) {
                    for (int i2 = 0; i2 < this.mDisplayTopTasks.size(); i2++) {
                        TaskItem taskItem2 = (TaskItem) this.mDisplayTopTasks.valueAt(i2);
                        taskItem2.setIsTop(false);
                        arrayList.add(taskItem2);
                    }
                    this.mDisplayTopTasks.clear();
                } else {
                    TaskItem taskItem3 = (TaskItem) this.mDisplayTopTasks.get(taskItem.getDisplayId());
                    if (taskItem3 != null && taskItem3.getTaskId() == i) {
                        return;
                    }
                    if (taskItem3 != null) {
                        taskItem3.setIsTop(false);
                        arrayList.add(taskItem3);
                    }
                    taskItem.setIsTop(true);
                    this.mDisplayTopTasks.put(taskItem.getDisplayId(), taskItem);
                }
                arrayList.forEach(new Consumer() { // from class: com.motorola.taskbar.recent.RecentTasksList$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$onTaskMovedToFront$4((TaskItem) obj);
                    }
                });
                if (taskItem != null) {
                    lambda$onTaskMovedToFront$4(taskItem);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTaskRemoved$1(int i) {
        synchronized (this.mLock) {
            try {
                TaskItem taskItem = (TaskItem) this.mAllTasksMap.get(i);
                if (taskItem == null) {
                    Log.w("RecentTasksList", "onTaskRemoved task not found: " + i);
                    return;
                }
                if (DEBUG) {
                    Log.d("RecentTasksList", "onTaskRemoved: " + taskItem.toString());
                }
                if (taskItem.getTaskId() != i) {
                    Log.e("RecentTasksList", "onTaskRemoved remove a wrong task taskId: " + i);
                    Log.d("RecentTasksList", "onTaskRemoved mAllTasksMap: " + this.mAllTasksMap.toString());
                    Log.d("RecentTasksList", "onTaskRemoved mAllTasks: " + this.mAllTasks.toString());
                }
                removeTaskLock(taskItem);
                notifyTaskRemoved(taskItem.getDisplayId(), taskItem.getTaskId());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reLoadInBackground$0() {
        TaskItem taskItem;
        Pair pairLoadTasksInBackground = loadTasksInBackground(Integer.MAX_VALUE);
        ArrayList arrayList = (ArrayList) pairLoadTasksInBackground.first;
        SparseArray sparseArray = (SparseArray) pairLoadTasksInBackground.second;
        synchronized (this.mLock) {
            try {
                this.mAllTasks.clear();
                this.mAllTasksMap.clear();
                this.mDisplaysTasks.clear();
                this.mDisplayTopTasks.clear();
                this.mAllTasks = TaskItem.obtainTaskItemList(arrayList);
                SparseArray sparseArray2 = new SparseArray();
                for (int i = 0; i < arrayList.size(); i++) {
                    Task task = (Task) arrayList.get(i);
                    int i2 = task.key.displayId;
                    if (i2 < 0) {
                        Log.w("RecentTasksList", "reLoadInBackground: with invalid display id " + taskToString(task));
                    } else {
                        Integer num = (Integer) sparseArray.get(i2);
                        if (num != null && task.key.id == num.intValue()) {
                            sparseArray2.put(task.key.displayId, task);
                        }
                    }
                }
                for (int i3 = 0; i3 < this.mAllTasks.size(); i3++) {
                    TaskItem taskItem2 = (TaskItem) this.mAllTasks.get(i3);
                    if (DEBUG) {
                        Log.d("RecentTasksList", "reLoadInBackground: " + taskItem2.toString());
                    }
                    this.mAllTasksMap.put(taskItem2.getTaskId(), taskItem2);
                    List arrayList2 = (List) this.mDisplaysTasks.get(taskItem2.getDisplayId());
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                        this.mDisplaysTasks.append(taskItem2.getDisplayId(), arrayList2);
                    }
                    arrayList2.add(taskItem2);
                }
                for (int i4 = 0; i4 < this.mDisplaysTasks.size(); i4++) {
                    List list = (List) this.mDisplaysTasks.valueAt(i4);
                    Task task2 = (Task) sparseArray2.get(this.mDisplaysTasks.keyAt(i4));
                    if (task2 != null) {
                        taskItem = (TaskItem) this.mAllTasksMap.get(task2.key.id);
                    } else {
                        list.size();
                        taskItem = null;
                    }
                    if (taskItem != null) {
                        taskItem.setIsTop(true);
                        this.mDisplayTopTasks.put(taskItem.getDisplayId(), taskItem);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        notifyTasksListReloaded();
    }

    private Pair loadTasksInBackground(int i) {
        int[] iArr;
        ArrayList arrayList = new ArrayList();
        SparseArray sparseArray = new SparseArray();
        Pair pair = new Pair(arrayList, sparseArray);
        List recentTasks = ActivityManagerWrapperExt.getInstance().getRecentTasks(i, -2);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray() { // from class: com.motorola.taskbar.recent.RecentTasksList.3
            @Override // android.util.SparseBooleanArray
            public boolean get(int i2) {
                if (indexOfKey(i2) < 0) {
                    put(i2, RecentTasksList.this.mKeyguardManager.isDeviceLocked());
                }
                return super.get(i2);
            }
        };
        int size = recentTasks.size();
        for (int i2 = 0; i2 < size; i2++) {
            ActivityManager.RecentTaskInfo recentTaskInfo = (ActivityManager.RecentTaskInfo) recentTasks.get(i2);
            if (!this.mContext.getPackageName().equals(getRecentTaskInfoPackageName(recentTaskInfo)) && !isSecondaryDisplayAppDrawer(recentTaskInfo) && !isGameModeDashboard(recentTaskInfo) && !sIgnorePackageNames.contains(getRecentTaskInfoPackageName(recentTaskInfo))) {
                Task.TaskKey taskKey = new Task.TaskKey(recentTaskInfo);
                arrayList.add(Task.from(taskKey, recentTaskInfo, sparseBooleanArray.get(taskKey.userId)));
            }
        }
        ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = this.mActivityManagerWrapperExt.getFocusedRootTaskInfo();
        if (focusedRootTaskInfo != null && (iArr = focusedRootTaskInfo.childTaskIds) != null && iArr.length > 0) {
            if (DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append("loadTasksInBackground StackInfo displayId: ");
                sb.append(focusedRootTaskInfo.displayId);
                sb.append("; taskId: ");
                sb.append(focusedRootTaskInfo.taskId);
                sb.append("; childTaskIds: ");
                sb.append(focusedRootTaskInfo.childTaskIds[r0.length - 1]);
                sb.append("; topActivity: ");
                sb.append(focusedRootTaskInfo.topActivity);
                Log.d("RecentTasksList", sb.toString());
            }
            sparseArray.put(focusedRootTaskInfo.displayId, Integer.valueOf(focusedRootTaskInfo.childTaskIds[r9.length - 1]));
        }
        return pair;
    }

    private void notifyTaskCreated(int i, int i2) {
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((RecentTasksListChangedListener) this.mListeners.get(size)).onTaskCreated(i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: notifyTaskInfoChanged, reason: merged with bridge method [inline-methods] */
    public void lambda$onTaskMovedToFront$4(TaskItem taskItem) {
        if (DEBUG) {
            Log.d("RecentTasksList", "notifyTaskInfoChanged: " + taskItem.toString());
        }
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((RecentTasksListChangedListener) this.mListeners.get(size)).onTaskInfoChanged(taskItem.getDisplayId(), taskItem);
        }
    }

    private void notifyTaskRemoved(int i, int i2) {
        if (DEBUG) {
            Log.d("RecentTasksList", "notifyTaskRemoved displayId: " + i + "taskId: " + i2);
        }
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((RecentTasksListChangedListener) this.mListeners.get(size)).onTaskRemoved(i, i2);
        }
    }

    private void notifyTaskSnapshotChanged(int i, ThumbnailData thumbnailData) {
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((RecentTasksListChangedListener) this.mListeners.get(size)).onTaskSnapshotChanged(i, thumbnailData);
        }
    }

    private void notifyTasksListReloaded() {
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((RecentTasksListChangedListener) this.mListeners.get(size)).onTasksListReloaded();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reLoadInBackground() {
        this.mBgThreadExecutor.submit(new Runnable() { // from class: com.motorola.taskbar.recent.RecentTasksList$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$reLoadInBackground$0();
            }
        });
    }

    private void removeTaskLock(TaskItem taskItem) {
        if (taskItem == null) {
            return;
        }
        if (DEBUG) {
            Log.d("RecentTasksList", "removeTaskLock: " + taskItem.toString());
        }
        List list = (List) this.mDisplaysTasks.get(taskItem.getDisplayId());
        if (list != null) {
            list.remove(taskItem);
        }
        this.mAllTasks.remove(taskItem);
        this.mAllTasksMap.remove(taskItem.getTaskId());
        int iIndexOfValue = this.mDisplayTopTasks.indexOfValue(taskItem);
        if (iIndexOfValue >= 0) {
            this.mDisplayTopTasks.removeAt(iIndexOfValue);
        }
    }

    public static String taskToString(Task task) {
        StringBuilder sb = new StringBuilder(Task.class.getSimpleName());
        sb.append('[');
        sb.append("package=" + task.key.getPackageName());
        sb.append(", taskId=" + task.key.id);
        sb.append(", userId=" + task.key.userId);
        sb.append(", displayId=" + task.key.displayId);
        sb.append(", component=" + task.key.getComponent());
        sb.append("]");
        return sb.toString();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(RecentTasksListChangedListener recentTasksListChangedListener) {
        this.mListeners.add(recentTasksListChangedListener);
    }

    public TaskItem getTask(int i) {
        synchronized (this.mLock) {
            try {
                TaskItem taskItem = (TaskItem) this.mAllTasksMap.get(i);
                if (taskItem == null) {
                    return null;
                }
                return taskItem.m2535clone();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ArrayList getTasks(int i) {
        synchronized (this.mLock) {
            try {
                if (i == -1) {
                    return copyOf(this.mAllTasks);
                }
                List list = (List) this.mDisplaysTasks.get(i);
                if (list != null) {
                    return copyOf(list);
                }
                return new ArrayList();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onDisplayReady(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mDisplaysTasks.get(i) == null) {
                    this.mDisplaysTasks.append(i, new ArrayList());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onDisplayRemoved(int i) {
        synchronized (this.mLock) {
            this.mDisplaysTasks.remove(i);
            this.mDisplayTopTasks.remove(i);
        }
    }

    @Override // com.android.systemui.shared.system.TaskStackChangeListener
    public void onRecentTaskListUpdated() {
        Log.d("RecentTasksList", "onRecentTaskListUpdated");
        onTaskStackChangedBackground();
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onSystemUIGone() {
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onSystemUIReady() {
        Display[] displays = this.mDisplayManager.getDisplays();
        synchronized (this.mLock) {
            try {
                for (Display display : displays) {
                    int displayId = display.getDisplayId();
                    if (this.mDisplaysTasks.get(displayId) == null) {
                        this.mDisplaysTasks.append(displayId, new ArrayList());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        reLoadInBackground();
    }

    @Override // com.android.systemui.shared.system.TaskStackChangeListener
    public void onTaskCreated(int i, ComponentName componentName) {
        if (DEBUG) {
            Log.d("RecentTasksList", "onTaskCreated taskId: " + i + "; componentName: " + componentName);
        }
    }

    @Override // com.android.systemui.shared.system.TaskStackChangeListener
    public void onTaskDisplayChanged(int i, int i2) {
        synchronized (this.mLock) {
            try {
                TaskItem taskItem = (TaskItem) this.mAllTasksMap.get(i);
                if (taskItem == null || taskItem.getDisplayId() != i2) {
                    Log.d("RecentTasksList", "onTaskDisplayChanged taskId: " + i + "; newDisplayId = " + i2);
                    onTaskStackChangedBackground();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.shared.system.TaskStackChangeListener
    public void onTaskMovedToFront(final int i) {
        if (DEBUG) {
            Log.d("RecentTasksList", "onTaskMovedToFront taskId: " + i);
        }
        this.mBgThreadExecutor.submit(new Runnable() { // from class: com.motorola.taskbar.recent.RecentTasksList$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onTaskMovedToFront$5(i);
            }
        });
    }

    @Override // com.android.systemui.shared.system.TaskStackChangeListener
    public void onTaskRemoved(final int i) {
        if (DEBUG) {
            Log.d("RecentTasksList", "onTaskRemoved taskId: " + i);
        }
        this.mBgThreadExecutor.submit(new Runnable() { // from class: com.motorola.taskbar.recent.RecentTasksList$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onTaskRemoved$1(i);
            }
        });
        onTaskStackChangedBackground();
    }

    @Override // com.android.systemui.shared.system.TaskStackChangeListener
    public boolean onTaskSnapshotChanged(int i, ThumbnailData thumbnailData) {
        if (DEBUG) {
            Log.d("RecentTasksList", "onTaskSnapshotChanged taskId: " + i);
        }
        TaskItem task = getTask(i);
        if (task != null) {
            task.updateThumbnailData(thumbnailData);
        }
        notifyTaskSnapshotChanged(i, thumbnailData);
        onTaskStackChangedBackground();
        return true;
    }

    @Override // com.android.systemui.shared.system.TaskStackChangeListener
    public void onTaskStackChangedBackground() {
        if (DEBUG) {
            Log.d("RecentTasksList", "onTaskStackChangedBackground");
        }
        if (this.mBgHandler.hasCallbacks(this.mRefreshTaskStacks)) {
            return;
        }
        this.mBgHandler.post(this.mRefreshTaskStacks);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(RecentTasksListChangedListener recentTasksListChangedListener) {
        this.mListeners.remove(recentTasksListChangedListener);
    }

    public void removeTask(int i) {
        if (DEBUG) {
            Log.d("RecentTasksList", "removeTask taskId: " + i);
        }
        this.mActivityManagerWrapper.removeTask(i);
        onTaskRemoved(i);
    }

    public void start() {
        this.mDeviceProvisionedController.addCallback(this.mDeviceProvisionedListener);
        TaskStackChangeListeners.getInstance().registerTaskStackListener(this);
        this.mActivityManagerWrapperExt.registerTaskStackListener(this.mTaskStackChangeListenerExt);
        ((TaskBarServiceProxy) Dependency.get(TaskBarServiceProxy.class)).addCallback((TaskBarServiceCallBack) this);
    }
}
