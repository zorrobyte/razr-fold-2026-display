package com.bumptech.glide.load.engine.bitmap_recycle;

/* JADX INFO: loaded from: classes.dex */
public interface ArrayPool {
    void clearMemory();

    Object get(int i, Class cls);

    Object getExact(int i, Class cls);

    void put(Object obj);

    void trimMemory(int i);
}
