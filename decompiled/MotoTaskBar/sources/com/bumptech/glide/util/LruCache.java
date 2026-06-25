package com.bumptech.glide.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LruCache {
    private final Map cache = new LinkedHashMap(100, 0.75f, true);
    private long currentSize;
    private final long initialMaxSize;
    private long maxSize;

    final class Entry {
        final int size;
        final Object value;

        Entry(Object obj, int i) {
            this.value = obj;
            this.size = i;
        }
    }

    public LruCache(long j) {
        this.initialMaxSize = j;
        this.maxSize = j;
    }

    private void evict() {
        trimToSize(this.maxSize);
    }

    public void clearMemory() {
        trimToSize(0L);
    }

    public synchronized Object get(Object obj) {
        Entry entry;
        entry = (Entry) this.cache.get(obj);
        return entry != null ? entry.value : null;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    protected int getSize(Object obj) {
        return 1;
    }

    protected void onItemEvicted(Object obj, Object obj2) {
    }

    public synchronized Object put(Object obj, Object obj2) {
        int size = getSize(obj2);
        long j = size;
        if (j >= this.maxSize) {
            onItemEvicted(obj, obj2);
            return null;
        }
        if (obj2 != null) {
            this.currentSize += j;
        }
        Entry entry = (Entry) this.cache.put(obj, obj2 == null ? null : new Entry(obj2, size));
        if (entry != null) {
            this.currentSize -= (long) entry.size;
            if (!entry.value.equals(obj2)) {
                onItemEvicted(obj, entry.value);
            }
        }
        evict();
        return entry != null ? entry.value : null;
    }

    public synchronized Object remove(Object obj) {
        Entry entry = (Entry) this.cache.remove(obj);
        if (entry == null) {
            return null;
        }
        this.currentSize -= (long) entry.size;
        return entry.value;
    }

    public synchronized void setSizeMultiplier(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("Multiplier must be >= 0");
        }
        this.maxSize = Math.round(this.initialMaxSize * f);
        evict();
    }

    protected synchronized void trimToSize(long j) {
        while (this.currentSize > j) {
            Iterator it = this.cache.entrySet().iterator();
            Map.Entry entry = (Map.Entry) it.next();
            Entry entry2 = (Entry) entry.getValue();
            this.currentSize -= (long) entry2.size;
            Object key = entry.getKey();
            it.remove();
            onItemEvicted(key, entry2.value);
        }
    }
}
