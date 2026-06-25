package com.bumptech.glide.load.data;

/* JADX INFO: loaded from: classes.dex */
public interface DataRewinder {

    public interface Factory {
        DataRewinder build(Object obj);

        Class getDataClass();
    }

    void cleanup();

    Object rewindAndGet();
}
