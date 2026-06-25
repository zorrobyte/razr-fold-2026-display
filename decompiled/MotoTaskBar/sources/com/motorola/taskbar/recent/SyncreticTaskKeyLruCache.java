package com.motorola.taskbar.recent;

import android.util.Log;
import com.motorola.taskbar.recent.TaskSyncreticItem;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class SyncreticTaskKeyLruCache {
    private final MyLinkedHashMap mMap;

    class Entry {
        final TaskSyncreticItem.TaskIconCacheKey mKey;
        Object mValue;

        Entry(TaskSyncreticItem.TaskIconCacheKey taskIconCacheKey, Object obj) {
            this.mKey = taskIconCacheKey;
            this.mValue = obj;
        }

        public int hashCode() {
            return this.mKey.getId();
        }
    }

    class MyLinkedHashMap extends LinkedHashMap {
        private final int mMaxSize;

        MyLinkedHashMap(int i) {
            super(0, 0.75f, true);
            this.mMaxSize = i;
        }

        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry entry) {
            return size() > this.mMaxSize;
        }
    }

    public SyncreticTaskKeyLruCache(int i) {
        this.mMap = new MyLinkedHashMap(i);
    }

    public synchronized Object getAndInvalidateIfModified(TaskSyncreticItem.TaskIconCacheKey taskIconCacheKey) {
        Entry entry = (Entry) this.mMap.get(Integer.valueOf(taskIconCacheKey.getId()));
        if (entry != null) {
            return entry.mValue;
        }
        remove(taskIconCacheKey);
        return null;
    }

    public final synchronized void put(TaskSyncreticItem.TaskIconCacheKey taskIconCacheKey, Object obj) {
        try {
            if (taskIconCacheKey == null || obj == null) {
                Log.e("TaskKeyCache", "Unexpected null key or value: " + taskIconCacheKey + ", " + obj);
            } else {
                this.mMap.put(Integer.valueOf(taskIconCacheKey.getId()), new Entry(taskIconCacheKey, obj));
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void remove(TaskSyncreticItem.TaskIconCacheKey taskIconCacheKey) {
        this.mMap.remove(Integer.valueOf(taskIconCacheKey.getId()));
    }
}
