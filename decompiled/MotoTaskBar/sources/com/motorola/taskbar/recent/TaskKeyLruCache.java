package com.motorola.taskbar.recent;

import android.util.Log;
import com.android.systemui.shared.recents.model.Task;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class TaskKeyLruCache {
    private final MyLinkedHashMap mMap;

    class Entry {
        final Task.TaskKey mKey;
        Object mValue;

        Entry(Task.TaskKey taskKey, Object obj) {
            this.mKey = taskKey;
            this.mValue = obj;
        }

        public int hashCode() {
            return this.mKey.id;
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

    public TaskKeyLruCache(int i) {
        this.mMap = new MyLinkedHashMap(i);
    }

    public synchronized Object getAndInvalidateIfModified(Task.TaskKey taskKey) {
        Entry entry = (Entry) this.mMap.get(Integer.valueOf(taskKey.id));
        if (entry != null) {
            Task.TaskKey taskKey2 = entry.mKey;
            if (taskKey2.windowingMode == taskKey.windowingMode && taskKey2.lastActiveTime == taskKey.lastActiveTime) {
                return entry.mValue;
            }
        }
        remove(taskKey);
        return null;
    }

    public final synchronized void put(Task.TaskKey taskKey, Object obj) {
        try {
            if (taskKey == null || obj == null) {
                Log.e("TaskKeyCache", "Unexpected null key or value: " + taskKey + ", " + obj);
            } else {
                this.mMap.put(Integer.valueOf(taskKey.id), new Entry(taskKey, obj));
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void remove(Task.TaskKey taskKey) {
        this.mMap.remove(Integer.valueOf(taskKey.id));
    }

    public synchronized void updateIfAlreadyInCache(int i, Object obj) {
        Entry entry = (Entry) this.mMap.get(Integer.valueOf(i));
        if (entry != null) {
            entry.mValue = obj;
        }
    }
}
