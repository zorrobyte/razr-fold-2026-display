package com.motorola.taskbar.recent;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.motorola.taskbar.provider.ShortcutInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class TaskSyncreticItem {
    public final TaskIconCacheKey iconCacheKey;
    public final TaskKey key;
    private Drawable mIcon;
    private ShortcutInfo mShortcutInfo;
    private String mTitleDescription;
    private final List mTaskItemList = new ArrayList();
    private SparseArray mTaskItemMap = new SparseArray();
    private boolean mIsFakeShortcutItem = false;

    public class TaskIconCacheKey {
        private int mHashCode;
        private int mId;
        private ShortcutInfo mShortcutInfo;
        private final TaskKey mTaskKey;

        public TaskIconCacheKey(TaskKey taskKey, ShortcutInfo shortcutInfo) {
            this.mTaskKey = taskKey;
            this.mShortcutInfo = shortcutInfo;
            updateId();
        }

        private void updateId() {
            TaskKey taskKey = this.mTaskKey;
            String str = taskKey.packageName;
            Integer numValueOf = Integer.valueOf(taskKey.displayId);
            Integer numValueOf2 = Integer.valueOf(this.mTaskKey.userId);
            ShortcutInfo shortcutInfo = this.mShortcutInfo;
            int iHash = Objects.hash(str, numValueOf, numValueOf2, shortcutInfo != null ? shortcutInfo.getClassName() : null);
            this.mHashCode = iHash;
            this.mId = iHash;
        }

        public boolean equals(Object obj) {
            return (obj instanceof TaskIconCacheKey) && this.mId == ((TaskIconCacheKey) obj).mId;
        }

        public int getId() {
            return this.mId;
        }

        public String getPackageName() {
            return this.mTaskKey.getPackageName();
        }

        public int getUserId() {
            return this.mTaskKey.userId;
        }

        public void setShortcutInfo(ShortcutInfo shortcutInfo) {
            this.mShortcutInfo = shortcutInfo;
            updateId();
        }
    }

    public class TaskKey {
        public final int displayId;
        public final int hashCode;
        public final int id;
        public final String packageName;
        public final int userId;

        TaskKey(String str, int i, int i2) {
            this.packageName = str;
            this.userId = i;
            this.displayId = i2;
            int iHash = Objects.hash(str, Integer.valueOf(i2), Integer.valueOf(i));
            this.hashCode = iHash;
            this.id = iHash;
        }

        public boolean equals(Object obj) {
            return (obj instanceof TaskKey) && this.id == ((TaskKey) obj).id;
        }

        public String getPackageName() {
            return this.packageName;
        }

        public int hashCode() {
            return this.hashCode;
        }

        public String toString() {
            return "packageName=" + this.packageName + " displayId=" + this.displayId + " user=" + this.userId;
        }
    }

    public TaskSyncreticItem(ShortcutInfo shortcutInfo, int i) {
        TaskKey taskKey = new TaskKey(shortcutInfo.getPackageName(), shortcutInfo.getUserId(), i);
        this.key = taskKey;
        this.mShortcutInfo = shortcutInfo;
        this.iconCacheKey = new TaskIconCacheKey(taskKey, shortcutInfo);
    }

    public TaskSyncreticItem(String str, int i, int i2) {
        TaskKey taskKey = new TaskKey(str, i, i2);
        this.key = taskKey;
        this.mShortcutInfo = null;
        this.iconCacheKey = new TaskIconCacheKey(taskKey, null);
    }

    private ArrayList copyOf(List list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add((TaskItem) list.get(i));
        }
        return arrayList;
    }

    public static final TaskSyncreticItem createEmptyShortcutItem() {
        TaskSyncreticItem taskSyncreticItem = new TaskSyncreticItem(null, -1, -1);
        taskSyncreticItem.mIsFakeShortcutItem = true;
        return taskSyncreticItem;
    }

    public void addTaskItem(TaskItem taskItem) {
        synchronized (this.mTaskItemList) {
            this.mTaskItemMap.put(taskItem.getTaskId(), taskItem);
            this.mTaskItemList.add(taskItem);
        }
    }

    public int getDisplayId() {
        return this.key.displayId;
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public String getPackageName() {
        return this.key.packageName;
    }

    public ShortcutInfo getShortcutInfo() {
        return this.mShortcutInfo;
    }

    public String getShortcutKey() {
        return ShortcutInfo.getKey(getPackageName(), getUserId());
    }

    public String getSyncreticKey() {
        return TaskItem.getSyncreticKey(getPackageName(), getDisplayId(), getUserId());
    }

    public List getTaskItemList() {
        ArrayList arrayListCopyOf;
        synchronized (this.mTaskItemList) {
            arrayListCopyOf = copyOf(this.mTaskItemList);
        }
        return arrayListCopyOf;
    }

    public String getTitleDescription() {
        return this.mTitleDescription;
    }

    public int getUserId() {
        return this.key.userId;
    }

    public boolean isEmpty() {
        boolean zIsEmpty;
        synchronized (this.mTaskItemList) {
            zIsEmpty = this.mTaskItemList.isEmpty();
        }
        return zIsEmpty;
    }

    public boolean isEmptyShortcutItem() {
        return this.mIsFakeShortcutItem;
    }

    public boolean isRunning() {
        return !isEmpty();
    }

    public boolean isShortcut() {
        return this.mShortcutInfo != null || this.mIsFakeShortcutItem;
    }

    public boolean isTop() {
        synchronized (this.mTaskItemList) {
            try {
                Iterator it = this.mTaskItemList.iterator();
                while (it.hasNext()) {
                    if (((TaskItem) it.next()).isTop()) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public TaskItem removeTaskItem(int i) {
        TaskItem taskItem;
        synchronized (this.mTaskItemList) {
            try {
                taskItem = (TaskItem) this.mTaskItemMap.get(i);
                if (taskItem != null) {
                    this.mTaskItemList.remove(taskItem);
                    this.mTaskItemMap.remove(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return taskItem;
    }

    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
    }

    public void setShortcutInfo(ShortcutInfo shortcutInfo) {
        this.mShortcutInfo = shortcutInfo;
        this.iconCacheKey.setShortcutInfo(shortcutInfo);
    }

    public void setTitleDescription(String str) {
        this.mTitleDescription = str;
    }

    public void updateTaskItem(TaskItem taskItem) {
        TaskItem taskItem2 = (TaskItem) this.mTaskItemMap.get(taskItem.getTaskId());
        if (taskItem2 != null) {
            taskItem2.updateFromSrc(taskItem);
        }
    }

    public TaskItem updateTaskSnapshot(int i, ThumbnailData thumbnailData) {
        TaskItem taskItem = (TaskItem) this.mTaskItemMap.get(i);
        if (taskItem != null) {
            taskItem.updateThumbnailData(thumbnailData);
        }
        return taskItem;
    }
}
