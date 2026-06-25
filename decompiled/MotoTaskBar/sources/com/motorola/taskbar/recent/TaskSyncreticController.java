package com.motorola.taskbar.recent;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.android.internal.content.PackageMonitor;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.statusbar.policy.CallbackController;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.bar.ExternalModeChooserActivity;
import com.motorola.taskbar.provider.ShortcutInfo;
import com.motorola.taskbar.provider.TaskbarProvider;
import com.motorola.taskbar.provider.TaskbarProviderManager;
import com.motorola.taskbar.recent.TaskController;
import com.motorola.taskbar.recent.TaskSyncreticIconCache;
import com.motorola.taskbar.util.DebugConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class TaskSyncreticController implements CallbackController {
    private static final boolean DEBUG = DebugConfig.DEBUG_TASK_CONTROLLER;
    private final Handler mBgHandler;
    private final Context mContext;
    private final DisplayManager mDisplayManager;
    private final ArrayList mListeners = new ArrayList();
    private final Handler mMainHandler;
    private final MotoFeature mMotoFeature;
    private final TaskController mTaskController;
    private final TaskSyncreticIconCache mTaskIconCache;
    private final TaskSyncreticItemData mTaskSyncreticItemData;
    private final WindowManager mWindowManager;

    class MyPackageMonitor extends PackageMonitor {
        private Context mCurrentUserContext;
        private boolean mRegistered = false;
        private final Context mSystemContext;
        private int mUserId;

        public MyPackageMonitor(Context context) {
            this.mSystemContext = context;
        }

        public void onPackageAdded(String str, int i) {
            UserHandle userHandleOf = UserHandle.of(getChangingUserId());
            if (TaskSyncreticController.DEBUG) {
                Log.d("TaskSyncreticController_PackageMonitor", "onPackageAdded: " + str + "; user: " + userHandleOf);
            }
        }

        public void onPackageModified(String str) {
            UserHandle userHandleOf = UserHandle.of(getChangingUserId());
            if (TaskSyncreticController.DEBUG) {
                Log.d("TaskSyncreticController_PackageMonitor", "onPackageModified: " + str + "; user: " + userHandleOf);
            }
            TaskbarProviderManager.removeIfInvalidShortcuts(this.mCurrentUserContext, TaskSyncreticController.this.mTaskSyncreticItemData.getShortcutList(str, userHandleOf.getIdentifier()));
        }

        public void onPackageRemoved(String str, int i) {
            UserHandle userHandleOf = UserHandle.of(getChangingUserId());
            if (TaskSyncreticController.DEBUG) {
                Log.d("TaskSyncreticController_PackageMonitor", "onPackageRemoved: " + str + "; user: " + userHandleOf);
            }
            TaskbarProviderManager.removeShortcutInfo(this.mCurrentUserContext, str, userHandleOf.getIdentifier());
        }

        public void onPackagesAvailable(String[] strArr) {
            UserHandle userHandleOf = UserHandle.of(getChangingUserId());
            boolean zIsReplacing = isReplacing();
            if (TaskSyncreticController.DEBUG) {
                for (String str : strArr) {
                    Log.d("TaskSyncreticController_PackageMonitor", "onPackagesAvailable: " + str + "; user: " + userHandleOf + "; replacing: " + zIsReplacing);
                }
            }
        }

        public void onPackagesUnavailable(String[] strArr) {
            UserHandle userHandleOf = UserHandle.of(getChangingUserId());
            boolean zIsReplacing = isReplacing();
            if (TaskSyncreticController.DEBUG) {
                for (String str : strArr) {
                    Log.d("TaskSyncreticController_PackageMonitor", "onPackagesUnavailable: " + str + "; user: " + userHandleOf + "; replacing: " + zIsReplacing);
                }
            }
            if (zIsReplacing) {
                return;
            }
            for (String str2 : strArr) {
                TaskbarProviderManager.removeShortcutInfo(this.mCurrentUserContext, str2, userHandleOf.getIdentifier());
            }
        }

        public void switchUser(int i, Handler handler) {
            if (!this.mRegistered) {
                this.mRegistered = true;
                register(TaskSyncreticController.this.mContext, handler.getLooper(), UserHandle.ALL, true);
            }
            if (TaskSyncreticController.DEBUG) {
                Log.d("TaskSyncreticController_PackageMonitor", "switchUser: " + i);
            }
            this.mCurrentUserContext = TaskSyncreticController.this.mContext.createContextAsUser(UserHandle.of(i), 0);
            this.mUserId = i;
        }
    }

    interface TaskSyncreticChangedListener {
        default void onSyncreticTaskCreated(int i, TaskSyncreticItem taskSyncreticItem) {
        }

        void onSyncreticTaskInfoChanged(int i, String str, TaskSyncreticItem taskSyncreticItem);

        default void onSyncreticTaskInserted(int i, TaskSyncreticItem taskSyncreticItem, int i2) {
        }

        default void onSyncreticTaskRemoved(int i, String str, int i2) {
        }

        default void onSyncreticTasksListReloaded() {
        }

        default void onTaskSnapshotChanged(int i, String str, TaskSyncreticItem taskSyncreticItem, TaskItem taskItem) {
        }
    }

    class TaskSyncreticItemData {
        private final ComponentName mChooserShortcut;
        private final Context mContext;
        private ContentResolver mCurrentUserContentResolver;
        private Context mCurrentUserContext;
        private MyPackageMonitor mPackageMonitor;
        private ContentObserver mShortcutContentObserver;
        private ArrayList mAllShortcuts = new ArrayList();
        private SparseArray mDisplaysShortcutInfos = new SparseArray();
        private Map mShortcutMap = new HashMap();
        private Map mShortcutTaskSyncreticItemMap = new HashMap();
        private ArrayList mAllTasks = new ArrayList();
        private Map mAllTasksMap = new HashMap();
        private SparseArray mTaskItemMap = new SparseArray();
        private SparseArray mDisplaysTasks = new SparseArray();
        private SparseIntArray mDisplayShortcutCount = new SparseIntArray();
        private int mLastUserId = -1;
        private final Object mLock = new Object();
        private boolean mWaitingOrderObserverChanged = false;
        TaskController.TaskChangedListener mTaskChangedListener = new TaskController.TaskChangedListener() { // from class: com.motorola.taskbar.recent.TaskSyncreticController.TaskSyncreticItemData.2
            @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
            public void onTaskCreated(int i, TaskItem taskItem) {
                Pair pairAddTaskItemLocked;
                synchronized (TaskSyncreticItemData.this.mLock) {
                    pairAddTaskItemLocked = TaskSyncreticItemData.this.addTaskItemLocked(taskItem);
                }
                if (pairAddTaskItemLocked == null) {
                    return;
                }
                if (((Boolean) pairAddTaskItemLocked.first).booleanValue()) {
                    TaskSyncreticItemData.this.notifySyncreticTaskCreated(((TaskSyncreticItem) pairAddTaskItemLocked.second).getDisplayId(), (TaskSyncreticItem) pairAddTaskItemLocked.second);
                } else {
                    TaskSyncreticItemData.this.notifySyncreticTaskInfoChanged(((TaskSyncreticItem) pairAddTaskItemLocked.second).getDisplayId(), (TaskSyncreticItem) pairAddTaskItemLocked.second);
                }
            }

            @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
            public void onTaskInfoChanged(int i, TaskItem taskItem) {
                TaskSyncreticItem taskSyncreticItem;
                synchronized (TaskSyncreticItemData.this.mLock) {
                    taskSyncreticItem = (TaskSyncreticItem) TaskSyncreticItemData.this.mTaskItemMap.get(taskItem.getTaskId());
                }
                if (taskSyncreticItem != null) {
                    taskSyncreticItem.updateTaskItem(taskItem);
                    TaskSyncreticItemData.this.notifySyncreticTaskInfoChanged(taskSyncreticItem.getDisplayId(), taskSyncreticItem);
                    return;
                }
                Log.w("TaskSyncreticController", "onTaskInfoChanged map empty TaskSyncreticItem: " + taskItem.getTaskId() + "; key: " + taskItem.getTaskKey());
            }

            @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
            public void onTaskRemoved(int i, int i2) {
                Pair pairRemoveTaskItemLocked;
                synchronized (TaskSyncreticItemData.this.mLock) {
                    pairRemoveTaskItemLocked = TaskSyncreticItemData.this.removeTaskItemLocked(i2);
                }
                if (pairRemoveTaskItemLocked == null) {
                    return;
                }
                if (((Boolean) pairRemoveTaskItemLocked.first).booleanValue()) {
                    TaskSyncreticItemData.this.notifySyncreticTaskRemoved(((TaskSyncreticItem) pairRemoveTaskItemLocked.second).getDisplayId(), ((TaskSyncreticItem) pairRemoveTaskItemLocked.second).getPackageName(), ((TaskSyncreticItem) pairRemoveTaskItemLocked.second).getUserId());
                } else {
                    TaskSyncreticItemData.this.notifySyncreticTaskInfoChanged(((TaskSyncreticItem) pairRemoveTaskItemLocked.second).getDisplayId(), (TaskSyncreticItem) pairRemoveTaskItemLocked.second);
                }
            }

            @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
            public void onTaskSnapshotChanged(int i, ThumbnailData thumbnailData) {
                TaskSyncreticItem taskSyncreticItem;
                synchronized (TaskSyncreticItemData.this.mLock) {
                    taskSyncreticItem = (TaskSyncreticItem) TaskSyncreticItemData.this.mTaskItemMap.get(i);
                }
                if (taskSyncreticItem == null) {
                    Log.w("TaskSyncreticController", "onTaskSnapshotChanged map empty TaskSyncreticItem: " + i);
                    return;
                }
                TaskItem taskItemUpdateTaskSnapshot = taskSyncreticItem.updateTaskSnapshot(i, thumbnailData);
                if (taskItemUpdateTaskSnapshot != null) {
                    TaskSyncreticItemData.this.notifyTaskSnapshotChanged(taskSyncreticItem.getDisplayId(), taskSyncreticItem, taskItemUpdateTaskSnapshot);
                    return;
                }
                Log.w("TaskSyncreticController", "onTaskSnapshotChanged map empty TaskItem: " + i);
            }

            @Override // com.motorola.taskbar.recent.TaskController.TaskChangedListener
            public void onTasksListReloaded() {
                TaskSyncreticItemData.this.reloadTasksList(true, false);
            }
        };

        class ShortcutContentObserver extends ContentObserver {
            public ShortcutContentObserver(Handler handler) {
                super(handler);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onChange$0(ShortcutInfo shortcutInfo) {
                TaskSyncreticItemData.this.removeShortcutInfo(shortcutInfo);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onChange$1(ShortcutInfo shortcutInfo) {
                TaskSyncreticItemData.this.addShortcutInfo(shortcutInfo);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onChange$2(Uri uri) {
                int iMatch = TaskbarProvider.mUriMatcher.match(uri);
                if ((iMatch == 1 || iMatch == 2) && TaskSyncreticItemData.this.mWaitingOrderObserverChanged) {
                    return;
                }
                if (iMatch == 3 || iMatch == 4) {
                    TaskSyncreticItemData.this.mWaitingOrderObserverChanged = false;
                    TaskSyncreticItemData.this.reloadTasksList(true, true);
                    return;
                }
                HashMap map = new HashMap(TaskSyncreticItemData.this.mShortcutMap);
                List<ShortcutInfo> shortcutsFromDb = TaskSyncreticItemData.this.readShortcutsFromDb();
                ArrayList arrayList = new ArrayList();
                for (ShortcutInfo shortcutInfo : shortcutsFromDb) {
                    if (map.remove(shortcutInfo.getKey()) == null) {
                        arrayList.add(shortcutInfo);
                    }
                }
                if (map.size() > 0) {
                    map.values().stream().forEach(new Consumer() { // from class: com.motorola.taskbar.recent.TaskSyncreticController$TaskSyncreticItemData$ShortcutContentObserver$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            this.f$0.lambda$onChange$0((ShortcutInfo) obj);
                        }
                    });
                }
                if (arrayList.size() > 0) {
                    arrayList.stream().forEach(new Consumer() { // from class: com.motorola.taskbar.recent.TaskSyncreticController$TaskSyncreticItemData$ShortcutContentObserver$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            this.f$0.lambda$onChange$1((ShortcutInfo) obj);
                        }
                    });
                }
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z, final Uri uri) {
                TaskSyncreticController.this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.recent.TaskSyncreticController$TaskSyncreticItemData$ShortcutContentObserver$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onChange$2(uri);
                    }
                });
            }
        }

        public TaskSyncreticItemData(Context context) {
            this.mContext = context;
            this.mChooserShortcut = new ComponentName(context.getPackageName(), ExternalModeChooserActivity.class.getName());
            updateCurrentUserContext();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addShortcutInfo(final ShortcutInfo shortcutInfo) {
            ArrayList arrayList;
            int iAddTaskSyncreticItemLocked;
            HashSet allDesktopDisplay = getAllDesktopDisplay();
            synchronized (this.mLock) {
                try {
                    this.mAllShortcuts.add(shortcutInfo);
                    this.mShortcutMap.put(shortcutInfo.getKey(), shortcutInfo);
                    String key = shortcutInfo.getKey();
                    if (this.mShortcutTaskSyncreticItemMap.containsKey(key)) {
                        arrayList = new ArrayList();
                        arrayList.addAll((Collection) this.mShortcutTaskSyncreticItemMap.get(key));
                    } else {
                        arrayList = null;
                    }
                } finally {
                }
            }
            if (arrayList != null) {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    TaskSyncreticItem taskSyncreticItem = (TaskSyncreticItem) obj;
                    allDesktopDisplay.remove(Integer.valueOf(taskSyncreticItem.getDisplayId()));
                    if (!ifIgnoreShortcutInfo(shortcutInfo, taskSyncreticItem.getDisplayId())) {
                        synchronized (this.mLock) {
                            try {
                                removeTaskSyncreticItemLocked(taskSyncreticItem);
                                List arrayList2 = (List) this.mDisplaysShortcutInfos.get(taskSyncreticItem.getDisplayId());
                                if (arrayList2 == null) {
                                    arrayList2 = new ArrayList();
                                    this.mDisplaysShortcutInfos.put(taskSyncreticItem.getDisplayId(), arrayList2);
                                }
                                arrayList2.add(shortcutInfo);
                            } finally {
                            }
                        }
                        notifySyncreticTaskRemoved(taskSyncreticItem.getDisplayId(), taskSyncreticItem.getPackageName(), taskSyncreticItem.getUserId());
                        synchronized (this.mLock) {
                            int i2 = this.mDisplayShortcutCount.get(taskSyncreticItem.getDisplayId());
                            taskSyncreticItem.setShortcutInfo(shortcutInfo);
                            iAddTaskSyncreticItemLocked = addTaskSyncreticItemLocked(taskSyncreticItem, i2);
                        }
                        notifySyncreticTaskInserted(taskSyncreticItem.getDisplayId(), taskSyncreticItem, iAddTaskSyncreticItemLocked);
                    }
                }
            }
            allDesktopDisplay.forEach(new Consumer() { // from class: com.motorola.taskbar.recent.TaskSyncreticController$TaskSyncreticItemData$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    this.f$0.lambda$addShortcutInfo$2(shortcutInfo, (Integer) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Pair addTaskItemLocked(TaskItem taskItem) {
            boolean z;
            String packageName = taskItem.getPackageName();
            if (TextUtils.isEmpty(packageName)) {
                Log.w("TaskSyncreticController", "addTaskItemLocked empty pkg: " + taskItem.getNativeTask());
                return null;
            }
            int displayId = taskItem.getDisplayId();
            initDisplayShortcutsLocked(displayId);
            TaskSyncreticItem taskSyncreticItem = (TaskSyncreticItem) this.mAllTasksMap.get(taskItem.getSyncreticKey());
            if (taskSyncreticItem == null) {
                taskSyncreticItem = new TaskSyncreticItem(packageName, taskItem.getTaskKey().userId, displayId);
                addTaskSyncreticItemLocked(taskSyncreticItem);
                z = true;
            } else {
                z = false;
            }
            this.mTaskItemMap.put(taskItem.getTaskId(), taskSyncreticItem);
            taskSyncreticItem.addTaskItem(taskItem);
            return new Pair(Boolean.valueOf(z), taskSyncreticItem);
        }

        private int addTaskSyncreticItemLocked(TaskSyncreticItem taskSyncreticItem) {
            return addTaskSyncreticItemLocked(taskSyncreticItem, -1);
        }

        private int addTaskSyncreticItemLocked(TaskSyncreticItem taskSyncreticItem, int i) {
            this.mAllTasksMap.put(taskSyncreticItem.getSyncreticKey(), taskSyncreticItem);
            this.mAllTasks.add(taskSyncreticItem);
            List arrayList = (List) this.mDisplaysTasks.get(taskSyncreticItem.getDisplayId());
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.mDisplaysTasks.put(taskSyncreticItem.getDisplayId(), arrayList);
            }
            if (i < 0 || i > arrayList.size()) {
                i = arrayList.size();
                arrayList.add(taskSyncreticItem);
            } else {
                arrayList.add(i, taskSyncreticItem);
            }
            List arrayList2 = (List) this.mShortcutTaskSyncreticItemMap.get(taskSyncreticItem.getShortcutKey());
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
                this.mShortcutTaskSyncreticItemMap.put(taskSyncreticItem.getShortcutKey(), arrayList2);
            }
            arrayList2.add(taskSyncreticItem);
            if (taskSyncreticItem.isShortcut()) {
                this.mDisplayShortcutCount.put(taskSyncreticItem.getDisplayId(), Math.max(this.mDisplayShortcutCount.get(taskSyncreticItem.getDisplayId()) + 1, 0));
            }
            return i;
        }

        private HashSet getAllDesktopDisplay() {
            HashSet hashSet = new HashSet();
            for (Display display : TaskSyncreticController.this.mDisplayManager.getDisplays()) {
                if (display.getDisplayId() != 0 && MotoFeature.isDesktopModeDisplay(display)) {
                    hashSet.add(Integer.valueOf(display.getDisplayId()));
                }
            }
            return hashSet;
        }

        private boolean ifIgnoreShortcutInfo(ShortcutInfo shortcutInfo, int i) {
            if (shortcutInfo.getComponentName().equals(this.mChooserShortcut)) {
                return !TaskSyncreticController.this.mMotoFeature.isValidHDMIOrWfdDisplay(i);
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initDisplayShortcutsLocked(int i) {
            if (((List) this.mDisplaysTasks.get(i)) != null) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            this.mDisplaysShortcutInfos.put(i, arrayList2);
            this.mDisplaysTasks.put(i, arrayList);
            ArrayList arrayList3 = this.mAllShortcuts;
            int size = arrayList3.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList3.get(i2);
                i2++;
                ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                if (!ifIgnoreShortcutInfo(shortcutInfo, i)) {
                    arrayList2.add(shortcutInfo);
                    addTaskSyncreticItemLocked(new TaskSyncreticItem(shortcutInfo, i));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addShortcutInfo$2(ShortcutInfo shortcutInfo, Integer num) {
            int iAddTaskSyncreticItemLocked;
            if (ifIgnoreShortcutInfo(shortcutInfo, num.intValue())) {
                return;
            }
            TaskSyncreticItem taskSyncreticItem = new TaskSyncreticItem(shortcutInfo, num.intValue());
            synchronized (this.mLock) {
                try {
                    iAddTaskSyncreticItemLocked = addTaskSyncreticItemLocked(taskSyncreticItem, this.mDisplayShortcutCount.get(num.intValue()));
                    List arrayList = (List) this.mDisplaysShortcutInfos.get(taskSyncreticItem.getDisplayId());
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        this.mDisplaysShortcutInfos.put(taskSyncreticItem.getDisplayId(), arrayList);
                    }
                    arrayList.add(shortcutInfo);
                } catch (Throwable th) {
                    throw th;
                }
            }
            notifySyncreticTaskInserted(taskSyncreticItem.getDisplayId(), taskSyncreticItem, iAddTaskSyncreticItemLocked);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$insertShortcut$1(int i, int i2, ComponentName componentName, int i3) {
            int iIndexOf;
            ArrayList arrayList = new ArrayList();
            int i4 = 0;
            if (i > 0) {
                synchronized (this.mLock) {
                    iIndexOf = this.mAllShortcuts.indexOf((ShortcutInfo) ((List) this.mDisplaysShortcutInfos.get(i2)).get(i - 1)) + 1;
                }
            } else {
                iIndexOf = 0;
            }
            synchronized (this.mLock) {
                try {
                    ArrayList arrayList2 = this.mAllShortcuts;
                    int size = arrayList2.size();
                    while (i4 < size) {
                        Object obj = arrayList2.get(i4);
                        i4++;
                        arrayList.add(Integer.valueOf(((ShortcutInfo) obj).getId()));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mWaitingOrderObserverChanged = true;
            int iAddShortcut = TaskbarProviderManager.addShortcut(this.mCurrentUserContext, componentName, i3);
            if (iAddShortcut != -1) {
                arrayList.add(iIndexOf, Integer.valueOf(iAddShortcut));
                TaskbarProviderManager.saveShortcutOrderByIds(this.mCurrentUserContext, arrayList);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$0(TaskController taskController) {
            reloadTasksList(true, false);
            taskController.addCallback(this.mTaskChangedListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifySyncreticTaskCreated(int i, TaskSyncreticItem taskSyncreticItem) {
            ArrayList tempListeners = TaskSyncreticController.this.getTempListeners();
            if (tempListeners.isEmpty()) {
                return;
            }
            for (int size = tempListeners.size() - 1; size >= 0; size--) {
                ((TaskSyncreticChangedListener) tempListeners.get(size)).onSyncreticTaskCreated(i, taskSyncreticItem);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifySyncreticTaskInfoChanged(int i, TaskSyncreticItem taskSyncreticItem) {
            ArrayList tempListeners = TaskSyncreticController.this.getTempListeners();
            if (tempListeners.isEmpty()) {
                return;
            }
            for (int size = tempListeners.size() - 1; size >= 0; size--) {
                ((TaskSyncreticChangedListener) tempListeners.get(size)).onSyncreticTaskInfoChanged(i, taskSyncreticItem.getPackageName(), taskSyncreticItem);
            }
        }

        private void notifySyncreticTaskInserted(int i, TaskSyncreticItem taskSyncreticItem, int i2) {
            ArrayList tempListeners = TaskSyncreticController.this.getTempListeners();
            if (tempListeners.isEmpty()) {
                return;
            }
            for (int size = tempListeners.size() - 1; size >= 0; size--) {
                ((TaskSyncreticChangedListener) tempListeners.get(size)).onSyncreticTaskInserted(i, taskSyncreticItem, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifySyncreticTaskRemoved(int i, String str, int i2) {
            ArrayList tempListeners = TaskSyncreticController.this.getTempListeners();
            if (tempListeners.isEmpty()) {
                return;
            }
            for (int size = tempListeners.size() - 1; size >= 0; size--) {
                ((TaskSyncreticChangedListener) tempListeners.get(size)).onSyncreticTaskRemoved(i, str, i2);
            }
        }

        private void notifySyncreticTasksListReloaded() {
            ArrayList tempListeners = TaskSyncreticController.this.getTempListeners();
            if (tempListeners.isEmpty()) {
                return;
            }
            for (int size = tempListeners.size() - 1; size >= 0; size--) {
                ((TaskSyncreticChangedListener) tempListeners.get(size)).onSyncreticTasksListReloaded();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyTaskSnapshotChanged(int i, TaskSyncreticItem taskSyncreticItem, TaskItem taskItem) {
            ArrayList tempListeners = TaskSyncreticController.this.getTempListeners();
            if (tempListeners.isEmpty()) {
                return;
            }
            for (int size = tempListeners.size() - 1; size >= 0; size--) {
                ((TaskSyncreticChangedListener) tempListeners.get(size)).onTaskSnapshotChanged(i, taskSyncreticItem.getPackageName(), taskSyncreticItem, taskItem);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public List readShortcutsFromDb() {
            ArrayList arrayList = new ArrayList();
            HashSet hashSet = new HashSet();
            Cursor cursorQuery = this.mCurrentUserContentResolver.query(TaskbarProvider.CONTENT_URI_SHORTCUT_INFO, null, null, null, null);
            if (cursorQuery != null) {
                if (cursorQuery.moveToFirst()) {
                    do {
                        ShortcutInfo shortcutInfo = new ShortcutInfo(cursorQuery);
                        if (!hashSet.contains(shortcutInfo.getKey())) {
                            arrayList.add(shortcutInfo);
                            hashSet.add(shortcutInfo.getKey());
                        }
                    } while (cursorQuery.moveToNext());
                }
                cursorQuery.close();
            }
            final SparseIntArray shortcutOrder = TaskbarProviderManager.getShortcutOrder(this.mCurrentUserContext);
            arrayList.sort(new Comparator(this) { // from class: com.motorola.taskbar.recent.TaskSyncreticController.TaskSyncreticItemData.1
                @Override // java.util.Comparator
                public int compare(ShortcutInfo shortcutInfo2, ShortcutInfo shortcutInfo3) {
                    int i = shortcutOrder.get(shortcutInfo2.getId(), Integer.MAX_VALUE);
                    int i2 = shortcutOrder.get(shortcutInfo3.getId(), Integer.MAX_VALUE);
                    return i == i2 ? shortcutInfo2.getId() - shortcutInfo3.getId() : i - i2;
                }
            });
            return arrayList;
        }

        private void reloadShortcuts() {
            synchronized (this.mLock) {
                try {
                    this.mAllShortcuts.clear();
                    this.mDisplaysShortcutInfos.clear();
                    this.mShortcutMap.clear();
                    List<ShortcutInfo> shortcutsFromDb = readShortcutsFromDb();
                    this.mAllShortcuts.addAll(shortcutsFromDb);
                    for (ShortcutInfo shortcutInfo : shortcutsFromDb) {
                        this.mShortcutMap.put(shortcutInfo.getKey(), shortcutInfo);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reloadTasksList(boolean z, boolean z2) {
            ContentObserver contentObserver;
            boolean zUpdateCurrentUserContext = updateCurrentUserContext();
            if (z) {
                if (zUpdateCurrentUserContext || z2 || this.mShortcutContentObserver == null) {
                    reloadShortcuts();
                }
                if (zUpdateCurrentUserContext && (contentObserver = this.mShortcutContentObserver) != null) {
                    this.mCurrentUserContentResolver.unregisterContentObserver(contentObserver);
                    this.mShortcutContentObserver = null;
                }
                if (this.mShortcutContentObserver == null) {
                    ShortcutContentObserver shortcutContentObserver = new ShortcutContentObserver(TaskSyncreticController.this.mBgHandler);
                    this.mShortcutContentObserver = shortcutContentObserver;
                    this.mCurrentUserContentResolver.registerContentObserver(TaskbarProvider.CONTENT_URI_SHORTCUT_INFO, true, shortcutContentObserver, ActivityManager.getCurrentUser());
                    this.mCurrentUserContentResolver.registerContentObserver(TaskbarProvider.CONTENT_URI_ORDER_INFO, true, this.mShortcutContentObserver, ActivityManager.getCurrentUser());
                }
                if (zUpdateCurrentUserContext || this.mPackageMonitor == null) {
                    if (this.mPackageMonitor == null) {
                        this.mPackageMonitor = TaskSyncreticController.this.new MyPackageMonitor(this.mContext);
                    }
                    this.mPackageMonitor.switchUser(this.mLastUserId, TaskSyncreticController.this.mBgHandler);
                }
            }
            synchronized (this.mLock) {
                try {
                    this.mAllTasks.clear();
                    this.mAllTasksMap.clear();
                    this.mDisplaysTasks.clear();
                    this.mDisplayShortcutCount.clear();
                    this.mTaskItemMap.clear();
                    this.mShortcutTaskSyncreticItemMap.clear();
                    ArrayList tasks = TaskSyncreticController.this.mTaskController.getTasks(-1);
                    int size = tasks.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = tasks.get(i);
                        i++;
                        addTaskItemLocked((TaskItem) obj);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            notifySyncreticTasksListReloaded();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeShortcutInfo(ShortcutInfo shortcutInfo) {
            ArrayList arrayList;
            synchronized (this.mLock) {
                try {
                    ShortcutInfo shortcutInfo2 = (ShortcutInfo) this.mShortcutMap.remove(shortcutInfo.getKey());
                    if (shortcutInfo2 == null) {
                        return;
                    }
                    this.mAllShortcuts.remove(shortcutInfo2);
                    int i = 0;
                    for (int i2 = 0; i2 < this.mDisplaysShortcutInfos.size(); i2++) {
                        ((List) this.mDisplaysShortcutInfos.valueAt(i2)).remove(shortcutInfo2);
                    }
                    if (this.mShortcutTaskSyncreticItemMap.containsKey(shortcutInfo.getKey())) {
                        arrayList = new ArrayList();
                        arrayList.addAll((Collection) this.mShortcutTaskSyncreticItemMap.get(shortcutInfo.getKey()));
                    } else {
                        arrayList = null;
                    }
                    if (arrayList != null) {
                        int size = arrayList.size();
                        while (i < size) {
                            Object obj = arrayList.get(i);
                            i++;
                            TaskSyncreticItem taskSyncreticItem = (TaskSyncreticItem) obj;
                            synchronized (this.mLock) {
                                removeTaskSyncreticItemLocked(taskSyncreticItem);
                            }
                            notifySyncreticTaskRemoved(taskSyncreticItem.getDisplayId(), taskSyncreticItem.getPackageName(), taskSyncreticItem.getUserId());
                            if (!taskSyncreticItem.isEmpty()) {
                                synchronized (this.mLock) {
                                    taskSyncreticItem.setShortcutInfo(null);
                                    addTaskSyncreticItemLocked(taskSyncreticItem);
                                }
                                notifySyncreticTaskCreated(taskSyncreticItem.getDisplayId(), taskSyncreticItem);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Pair removeTaskItemLocked(int i) {
            TaskSyncreticItem taskSyncreticItem = (TaskSyncreticItem) this.mTaskItemMap.get(i);
            if (taskSyncreticItem == null) {
                Log.w("TaskSyncreticController", "removeTaskItemLocked map empty TaskSyncreticItem: " + i);
                return null;
            }
            this.mTaskItemMap.remove(i);
            if (taskSyncreticItem.removeTaskItem(i) == null) {
                Log.w("TaskSyncreticController", "removeTaskItemLocked empty TaskItem: " + i);
                return null;
            }
            if (!taskSyncreticItem.isEmpty() || taskSyncreticItem.isShortcut()) {
                return new Pair(Boolean.FALSE, taskSyncreticItem);
            }
            removeTaskSyncreticItemLocked(taskSyncreticItem);
            return new Pair(Boolean.TRUE, taskSyncreticItem);
        }

        private void removeTaskSyncreticItemLocked(TaskSyncreticItem taskSyncreticItem) {
            this.mAllTasksMap.remove(taskSyncreticItem.getSyncreticKey());
            this.mAllTasks.remove(taskSyncreticItem);
            List list = (List) this.mDisplaysTasks.get(taskSyncreticItem.getDisplayId());
            if (list != null) {
                if (list.remove(taskSyncreticItem) && taskSyncreticItem.isShortcut()) {
                    this.mDisplayShortcutCount.put(taskSyncreticItem.getDisplayId(), Math.max(this.mDisplayShortcutCount.get(taskSyncreticItem.getDisplayId()) - 1, 0));
                }
                if (list.isEmpty()) {
                    this.mDisplaysTasks.remove(taskSyncreticItem.getDisplayId());
                    this.mDisplayShortcutCount.delete(taskSyncreticItem.getDisplayId());
                }
            }
            List list2 = (List) this.mShortcutTaskSyncreticItemMap.get(taskSyncreticItem.getShortcutKey());
            if (list2 != null) {
                list2.remove(taskSyncreticItem);
                if (list2.isEmpty()) {
                    this.mShortcutTaskSyncreticItemMap.remove(taskSyncreticItem.getShortcutKey());
                }
            }
        }

        private boolean updateCurrentUserContext() {
            int currentUser = ActivityManager.getCurrentUser();
            if (currentUser == this.mLastUserId) {
                return false;
            }
            this.mLastUserId = currentUser;
            Context contextCreateContextAsUser = this.mContext.createContextAsUser(UserHandle.of(currentUser), 0);
            this.mCurrentUserContext = contextCreateContextAsUser;
            this.mCurrentUserContentResolver = contextCreateContextAsUser.getContentResolver();
            return true;
        }

        public List getShortcutList(String str, int i) {
            ArrayList arrayList = new ArrayList();
            String key = ShortcutInfo.getKey(str, i);
            synchronized (this.mLock) {
                try {
                    ShortcutInfo shortcutInfo = (ShortcutInfo) this.mShortcutMap.get(key);
                    if (shortcutInfo != null) {
                        arrayList.add(shortcutInfo);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        public void insertShortcut(final int i, final int i2, final ComponentName componentName, final int i3) {
            TaskSyncreticController.this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.recent.TaskSyncreticController$TaskSyncreticItemData$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$insertShortcut$1(i3, i, componentName, i2);
                }
            });
        }

        public void start(final TaskController taskController) {
            TaskSyncreticController.this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.recent.TaskSyncreticController$TaskSyncreticItemData$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$start$0(taskController);
                }
            });
        }
    }

    public TaskSyncreticController(Context context, TaskController taskController, Looper looper, Looper looper2, MotoFeature motoFeature) {
        this.mContext = context;
        this.mMotoFeature = motoFeature;
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mTaskController = taskController;
        this.mMainHandler = new Handler(looper);
        this.mBgHandler = new Handler(looper2);
        HandlerThread handlerThread = new HandlerThread("TaskThumbnailIconCache", 10);
        handlerThread.start();
        this.mTaskIconCache = new TaskSyncreticIconCache(context, handlerThread.getLooper());
        TaskSyncreticItemData taskSyncreticItemData = new TaskSyncreticItemData(context);
        this.mTaskSyncreticItemData = taskSyncreticItemData;
        taskSyncreticItemData.start(taskController);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList getTempListeners() {
        ArrayList arrayList;
        synchronized (this.mListeners) {
            arrayList = (ArrayList) this.mListeners.clone();
        }
        return arrayList;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(TaskSyncreticChangedListener taskSyncreticChangedListener) {
        synchronized (this.mListeners) {
            this.mListeners.add(taskSyncreticChangedListener);
        }
    }

    public int getShortcutInfoCount() {
        int size;
        synchronized (this.mTaskSyncreticItemData.mLock) {
            size = this.mTaskSyncreticItemData.mAllShortcuts.size();
        }
        return size;
    }

    public List getTasks(int i) {
        synchronized (this.mTaskSyncreticItemData.mLock) {
            this.mTaskSyncreticItemData.initDisplayShortcutsLocked(i);
        }
        List list = (List) this.mTaskSyncreticItemData.mDisplaysTasks.get(i);
        return list == null ? new ArrayList() : list;
    }

    public void insertShortcut(int i, int i2, ComponentName componentName, int i3) {
        this.mTaskSyncreticItemData.insertShortcut(i, i2, componentName, i3);
    }

    public void launchTaskFromView(Task task, View view, int i) {
        this.mTaskController.launchTaskFromView(task, view, i);
    }

    public boolean moveTaskToBack(TaskItem taskItem) {
        return this.mTaskController.moveTaskToBack(taskItem);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(TaskSyncreticChangedListener taskSyncreticChangedListener) {
        synchronized (this.mListeners) {
            this.mListeners.remove(taskSyncreticChangedListener);
        }
    }

    public void removeTask(int i) {
        this.mTaskController.removeTask(i);
    }

    public TaskSyncreticIconCache.IconLoadRequest updateIconInBackground(TaskSyncreticItem taskSyncreticItem, Consumer consumer) {
        return this.mTaskIconCache.updateIconInBackground(taskSyncreticItem, consumer);
    }

    public void updateItemIconInBackground(TaskSyncreticItem taskSyncreticItem, Consumer consumer) {
        if (taskSyncreticItem == null) {
            return;
        }
        Iterator it = taskSyncreticItem.getTaskItemList().iterator();
        while (it.hasNext()) {
            this.mTaskController.updateIconInBackground(((TaskItem) it.next()).getNativeTask(), consumer);
        }
    }

    public void updateThumbnailInBackground(TaskSyncreticItem taskSyncreticItem, Consumer consumer) {
        if (taskSyncreticItem == null) {
            return;
        }
        Iterator it = taskSyncreticItem.getTaskItemList().iterator();
        while (it.hasNext()) {
            this.mTaskController.updateThumbnailInBackground(((TaskItem) it.next()).getNativeTask(), consumer);
        }
    }
}
